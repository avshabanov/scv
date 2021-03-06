package com.truward.scv.cli.processor;

import com.truward.di.InjectionContext;
import com.truward.di.InjectionException;
import com.truward.scv.cli.mapping.TargetMappingProcessor;
import com.truward.scv.plugin.api.SpecificationParameterProvider;
import com.truward.scv.plugin.api.SpecificationState;
import com.truward.scv.plugin.api.SpecificationStateAware;
import com.truward.scv.specification.annotation.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Handler for methods, annotated with {@link Specification} annotations.
 */
public final class SpecificationHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Resource
  private InjectionContext injectionContext;

  @Resource
  private TargetMappingProcessor targetMappingProcessor;

  @Nullable
  public <T> T parseClass(@Nonnull Class<T> specificationClass) {
    log.debug("Parsing {}", specificationClass);

    if (specificationClass.isInterface() || Modifier.isAbstract(specificationClass.getModifiers())) {
      log.warn("Skipping {}: interface or abstract class can't be processed", specificationClass);
      return null;
    }

    targetMappingProcessor.addMappingsFrom(specificationClass);

    final List<Method> specificationMethods = new ArrayList<>();
    for (final Method method : specificationClass.getMethods()) {
      if (method.getAnnotation(Specification.class) != null) {
        specificationMethods.add(method);
      }
    }

    if (specificationMethods.isEmpty()) {
      log.warn("No specification methods in {}", specificationClass);
      return null;
    }

    try {
      final T instance = specificationClass.newInstance();
      final List<Object> resources = provideResources(specificationClass, instance);
      final List<SpecificationStateAware> stateAwareBeans = new ArrayList<>();
      for (final Object resource : resources) {
        if (resource instanceof SpecificationStateAware) {
          stateAwareBeans.add((SpecificationStateAware) resource);
        }
      }

      // sort by ordinals
      Collections.sort(specificationMethods, SpecificationOrderComparator.INSTANCE);

      // invoke in the given order
      invokeSpecificationMethods(specificationMethods, instance, stateAwareBeans);

      log.debug("{} has been successfully processed", specificationClass);
      return instance;
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Uninstantiable " + specificationClass + ": no public default constructor", e);
    }
  }

  public void done() {
    notifyStateChanged(injectionContext.getBeans(SpecificationStateAware.class), SpecificationState.COMPLETED);
  }

  //
  // Private
  //

  /**
   * Compares two methods by looking to their ordinals.
   */
  private static final class SpecificationOrderComparator implements Comparator<Method> {
    static final SpecificationOrderComparator INSTANCE = new SpecificationOrderComparator();

    @Override
    public int compare(Method lhs, Method rhs) {
      return Integer.compare(getPriority(rhs), getPriority(lhs));
    }

    private static int getPriority(Method method) {
      final Specification specification = method.getAnnotation(Specification.class);
      return specification != null ? specification.priority() : Integer.MAX_VALUE;
    }
  }

  private void invokeSpecificationMethods(@Nonnull List<Method> specificationMethods,
                                          @Nonnull Object instance,
                                          @Nonnull List<SpecificationStateAware> stateAwareBeans) {
    for (final Method method : specificationMethods) {
      log.debug("Invoking specification method {}", method);

      notifyStateChanged(stateAwareBeans, SpecificationState.RECORDING);
      try {
        invokeMethod(instance, method);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException("Error while invoking " + method, e);
      }
      notifyStateChanged(stateAwareBeans, SpecificationState.SUBMITTED);
    }
  }

  private void invokeMethod(Object instance, Method method) throws InvocationTargetException, IllegalAccessException {
    final Specification specification = method.getAnnotation(Specification.class);
    if (specification == null) {
      return;
    }

    if (!Modifier.isPublic(method.getModifiers())) {
      throw new RuntimeException("Non-public specification method: " + method);
    }

    // fetch parameters
    final Object[] parameters;
    final Class<?>[] parameterTypes = method.getParameterTypes();
    final int parameterCount = parameterTypes.length;
    if (parameterCount > 0) {
      final Annotation[][] paramListAnnotations = method.getParameterAnnotations();
      parameters = new Object[parameterCount];
      final List<SpecificationParameterProvider> parameterProviders = injectionContext
          .getBeans(SpecificationParameterProvider.class);
      if (parameterProviders.size() == 0) {
        throw new RuntimeException("No specification parameter provides; method parameters will be left unprovided");
      }

      // provide parameters
      for (int i = 0; i < parameterCount; ++i) {
        boolean provided = false;
        final Class<?> parameterType = parameterTypes[i];
        final List<Annotation> paramAnnotations = Collections.unmodifiableList(Arrays.asList(paramListAnnotations[i]));
        for (final SpecificationParameterProvider parameterProvider : parameterProviders) {
          if (parameterProvider.canProvideParameter(paramAnnotations, parameterType)) {
            if (!provided) {
              parameters[i] = parameterProvider.provideParameter(paramAnnotations, parameterType);
              provided = true;
            } else {
              log.error("Multiple specifiers can provide the same parameter annotated with {} for method {}",
                  paramAnnotations, method);
            }
          }
        }

        if (!provided) {
          throw new RuntimeException("Unable to provide parameter for specification method");
        }
      }
    } else {
      parameters = new Object[0];
    }

    // unexpected result
    if (!void.class.equals(method.getReturnType())) {
      log.warn("Result of the specification method {} will be ignored", method);
    }

    method.invoke(instance, parameters);
  }

  @Nonnull
  private List<Object> provideResources(@Nonnull Class<?> clazz, @Nonnull Object instance) {
    final List<Object> providedResources = new ArrayList<>();
    try {
      // for @Resource fields
      for (final Field field : clazz.getDeclaredFields()) {
        final Object providedResource = provideFieldValue(clazz, instance, field);
        if (providedResource != null) {
          providedResources.add(providedResource);
        }
      }

      // for @Resource setters
      for (final Method method : clazz.getMethods()) {
        final Object providedResource = provideMethodValue(clazz, instance, method);
        if (providedResource != null) {
          providedResources.add(providedResource);
        }
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException("Unable to set field value", e);
    }
    return Collections.unmodifiableList(providedResources);
  }

  @Nullable
  private Object provideMethodValue(Class<?> clazz, Object instance, Method method)
      throws InvocationTargetException, IllegalAccessException {
    final Resource resource = method.getAnnotation(Resource.class);
    if (resource == null) {
      return null;
    }

    warnIfMappedNameSet(resource, clazz, method);

    final Class[] parameterTypes = method.getParameterTypes();
    if (parameterTypes.length != 1) {
      throw new IllegalStateException("Method annotated with @Resource should take exactly one parameter");
    }

    final Class<?> type = parameterTypes[0];

    final Object injectedBean;
    try {
      injectedBean = injectionContext.getBean(type);
    } catch (InjectionException e) {
      throw new RuntimeException(String.format("Can't inject bean of class %s in method %s of %s", type, method,
          clazz), e);
    }

    method.invoke(instance, injectedBean);

    return injectedBean;
  }

  @Nullable
  private Object provideFieldValue(Class<?> clazz, Object instance, Field field) throws IllegalAccessException {
    final Resource resource = field.getAnnotation(Resource.class);
    if (resource == null) {
      return null;
    }

    warnIfMappedNameSet(resource, clazz, field);

    boolean wasAccessible = field.isAccessible();
    field.setAccessible(true);

    assert field.get(instance) == null : "Unexpected assigned value";

    final Object injectedBean;
    try {
      injectedBean = injectionContext.getBean(field.getType());
    } catch (InjectionException e) {
      throw new RuntimeException(String.format("Can't inject bean into %s in %s", field, clazz), e);
    }
    field.set(instance, injectedBean);

    if (!wasAccessible) {
      field.setAccessible(false);
    }

    return injectedBean;
  }

  private void warnIfMappedNameSet(Resource resource, Class<?> clazz, Object fieldOrMethod) {
    if (resource.mappedName().length() > 0) {
      log.warn("Resource name ignored: {} for {} in {}", resource.mappedName(), fieldOrMethod, clazz);
    }
  }

  private static void notifyStateChanged(@Nonnull Collection<? extends SpecificationStateAware> stateAwareBeans,
                                        @Nonnull SpecificationState state) {
    for (final SpecificationStateAware bean : stateAwareBeans) {
      bean.notifyStateChanged(state);
    }
  }
}
