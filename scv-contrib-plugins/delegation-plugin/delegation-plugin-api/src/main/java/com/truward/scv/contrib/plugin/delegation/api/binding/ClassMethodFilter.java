package com.truward.scv.contrib.plugin.delegation.api.binding;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public interface ClassMethodFilter<T> extends MethodFilter {

  @Nonnull
  T forMethod();
}
