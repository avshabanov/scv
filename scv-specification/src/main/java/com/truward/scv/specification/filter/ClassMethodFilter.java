package com.truward.scv.specification.filter;

import javax.annotation.Nonnull;

/**
 * Extends method filters with a lookup method for a particular class.
 *
 * @author Alexander Shabanov
 */
public interface ClassMethodFilter<T> extends MethodFilter {

  @Nonnull
  T forMethod();
}
