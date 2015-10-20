package com.truward.scv.contrib.plugin.pojo.api.binding;

import com.truward.scv.specification.filter.MethodFilter;

import javax.annotation.Nonnull;

/**
 * Defines POJO actions that can be done on this kind
 */
public interface PojoActions<Filter extends MethodFilter> {

  //
  // Class-level actions
  //

  void makeDataClass();

  void assignBuilder();

  void addEqualsAndHashCode();

  void addToString();

  // TODO: add overloaded version that takes modifier, @Param param-name annotations (a-la Jackson @JsonProperty)
  void addAllArgsConstructor();

  // Mark methods as non-supported
  @Nonnull
  Filter throwException(@Nonnull Class<? extends Exception> exceptionClass);

  @Nonnull
  Filter delegateToStatic(@Nonnull Class<?> helperClass, String methodName);
}
