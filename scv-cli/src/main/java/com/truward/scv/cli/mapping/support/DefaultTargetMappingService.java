package com.truward.scv.cli.mapping.support;

import com.truward.scv.cli.mapping.MappedClass;
import com.truward.scv.cli.mapping.MappedTarget;
import com.truward.scv.cli.mapping.TargetMappingProcessor;
import com.truward.scv.cli.mapping.TargetMappingProvider;
import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.specification.annotation.TargetMapping;
import com.truward.scv.specification.annotation.TargetMappingEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Shabanov
 */
public final class DefaultTargetMappingService implements TargetMappingProcessor, TargetMappingProvider {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final Map<Class<?>, MappedTargetImpl> mappings = new HashMap<>(500);

  @Override
  public void addMappingsFrom(@Nonnull Class<?> specificationClass) {
    log.debug("Setting target mappings for {}", specificationClass);

    final TargetMapping targetMapping = specificationClass.getAnnotation(TargetMapping.class);
    if (targetMapping == null) {
      return; // no mappings for the given class
    }

    for (final TargetMappingEntry entry : targetMapping.value()) {
      final Class[] interfaceClasses = entry.source();
      final String targetName = entry.targetName();

      if (interfaceClasses.length == 0) {
        log.warn("Ignoring mapping in {} - no source classes associated with a target {}",
            specificationClass, targetName);
        continue;
      }

      if (interfaceClasses.length == 1) {
        // special case: target class hosts given interface
        addSingleEntry(specificationClass, interfaceClasses[0], targetName);
        continue;
      }

      addMultipleEntries(specificationClass, interfaceClasses, targetName);
    }
  }

  @Override
  public List<MappedTarget> getMappedTargets() {
    final Set<MappedTargetImpl> mappedTargets = new HashSet<>(mappings.values());
    return Collections.unmodifiableList(new ArrayList<MappedTarget>(mappedTargets));
  }

  //
  // Private
  //

  private void addSingleEntry(@Nonnull Class<?> specificationClass,
                              @Nonnull Class<?> interfaceEntry,
                              @Nonnull String targetName) {
    MappedTargetImpl target = mappings.get(interfaceEntry);
    if (target != null) {
      if (!target.getTargetName().toString().equals(targetName)) {
        throw new RuntimeException("Specification class " + specificationClass + " contains conflicting target for " +
            interfaceEntry + ", previous=" + target.getTargetName() + ", given=" + targetName);
      }

      return;
    }

    target = new MappedTargetImpl(FqName.valueOf(targetName));
    target.primaryMappedClass = new MappedClassImpl(interfaceEntry, "", target);

    mappings.put(interfaceEntry, target);
  }

  private void addMultipleEntries(@Nonnull Class<?> specificationClass,
                                  @Nonnull Class[] interfaceEntries,
                                  @Nonnull String targetName) {
    MappedTargetImpl target = null;
    final FqName targetFqName = FqName.valueOf(targetName);
    for (final MappedTargetImpl t : mappings.values()) {
      if (t.getTargetName().equals(targetFqName)) {
        target = t;
        break;
      }
    }

    if (target == null) {
      target = new MappedTargetImpl(targetFqName);
      target.mappedClasses = new ArrayList<>(interfaceEntries.length);
    } else if (target.primaryMappedClass != null) {
      throw new RuntimeException("Target " + targetName + " has already been associated with a class " +
          target.primaryMappedClass.getSourceClass());
    }

    for (final Class<?> interfaceEntry : interfaceEntries) {
      final MappedTargetImpl anotherTarget = mappings.get(interfaceEntry);
      if (anotherTarget != null) {
        if (anotherTarget != target) {
          throw new RuntimeException("Conflicting target definition in " + specificationClass + " for " +
              interfaceEntry + ", previous=" + anotherTarget + ", given=" + targetName);
        }
        continue; // already mapped to this target
      }

      target.mappedClasses.add(new MappedClassImpl(interfaceEntry, getUniqueName(interfaceEntry, target), target));
    }
  }

  private static String getUniqueName(Class<?> sourceClass, MappedTarget target) {
    for (int i = 0;; ++i) {
      final String innerClassName = (i > 0 ? sourceClass.getSimpleName() + i : sourceClass.getSimpleName());
      boolean found = false;
      for (MappedClass mappedClass : target.getMappedClasses()) {
        if (mappedClass.getTargetName().equals(innerClassName)) {
          found = true;
          break;
        }
      }

      if (!found) {
        return innerClassName;
      }
    }
  }

  private static final class MappedClassImpl implements MappedClass {
    private final Class<?> sourceClass;
    private final String targetName;
    private final MappedTargetImpl mappedTarget;

    public MappedClassImpl(Class<?> sourceClass, String targetName, MappedTargetImpl mappedTarget) {
      this.sourceClass = sourceClass;
      this.targetName = targetName;
      this.mappedTarget = mappedTarget;
    }

    @Nonnull
    @Override
    public Class<?> getSourceClass() {
      return sourceClass;
    }

    @Nonnull
    @Override
    public String getTargetName() {
      return targetName;
    }

    @Nonnull
    @Override
    public MappedTargetImpl getMappedTarget() {
      return mappedTarget;
    }
  }

  private static final class MappedTargetImpl implements MappedTarget {
    private List<MappedClassImpl> mappedClasses = Collections.emptyList();
    private MappedClassImpl primaryMappedClass;
    private final FqName targetName;

    public MappedTargetImpl(FqName targetName) {
      this.targetName = targetName;
    }

    @Nonnull
    @Override
    public List<MappedClass> getMappedClasses() {
      return Collections.unmodifiableList(new ArrayList<MappedClass>(mappedClasses));
    }

    @Nullable
    @Override
    public MappedClass getPrimaryMappedClass() {
      return primaryMappedClass;
    }

    @Nonnull
    @Override
    public FqName getTargetName() {
      return targetName;
    }
  }
}
