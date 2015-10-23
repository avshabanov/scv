package com.truward.scv.plugin.support.filter;

import com.truward.scv.specification.filter.ClassMethodFilter;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Alexander Shabanov
 */
public class DefaultClassMethodFilter<T> extends AbstractMethodFilter implements ClassMethodFilter<T> {
  private final Class<T> interfaceClass;

  public DefaultClassMethodFilter(Class<T> interfaceClass) {
    this.interfaceClass = interfaceClass;
  }

  @Nonnull
  @Override
  public T forMethod() {
    // call method and notify
    LoggerFactory.getLogger(getClass()).warn("TODO: impl forMethod()");

    return interfaceClass.cast(Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] {interfaceClass},
        new HandlerImpl()));
  }

  //
  // Private
  //

  private static final class HandlerImpl implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      throw new UnsupportedOperationException();
    }
  }
}
