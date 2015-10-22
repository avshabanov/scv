package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.ClassDelegationAction;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationActions;

import javax.annotation.Nonnull;

/**
 * A specification creator for delegation targets.
 *
 * @author Alexander Shabanov
 */
public interface DelegationSpecifier {

  @Nonnull
  <T> ClassDelegationAction<T> forClass(@Nonnull Class<T> interfaceClass);

  @Nonnull
  MultipleDelegationActions forClasses(@Nonnull Class... interfaceClasses);
}
