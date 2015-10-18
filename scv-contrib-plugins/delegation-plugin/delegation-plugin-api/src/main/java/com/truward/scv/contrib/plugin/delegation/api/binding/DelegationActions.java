package com.truward.scv.contrib.plugin.delegation.api.binding;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public interface DelegationActions<Filter extends MethodFilter> {
  @Nonnull
  Filter returnConstant(Object constant);

  @Nonnull
  Filter throwException(@Nonnull Class<? extends RuntimeException> exceptionClass);
}
