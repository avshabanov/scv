package com.truward.scv.contrib.plugin.delegation.api.binding;

import com.truward.scv.specification.filter.MethodFilter;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public interface DelegationActions<Filter extends MethodFilter> {

  @Nonnull
  Filter returnConstant(Object constant);

  @Nonnull
  Filter throwException(@Nonnull Class<? extends RuntimeException> exceptionClass);

  /**
   * Creates a field in the generated class that will be used in all the generated methods to handle the calls
   *
   * @return Method filter
   */
  @Nonnull
  Filter delegateCall();

  /**
   * Binds an aspect to the class.
   * Aspect instance will be expected to be passed in as a construct parameter.
   * The code analyzer will try to "guess" what aspect method should do depending on its name.
   * At the moment code analyzer understands the following method names: {@code before}, {@code after}, {@code around}
   *
   * TODO: more documentation
   *
   * @param aspectClass Aspect class
   * @return Method filter
   */
  @Nonnull
  Filter bindAspect(Class<?> aspectClass);
}
