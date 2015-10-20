package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationTarget;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationTargets;
import com.truward.scv.specification.Target;

import javax.annotation.Nonnull;

/**
 * A specification creator for delegation targets.
 *
 * @author Alexander Shabanov
 */
public interface DelegationSpecifier {

  @Nonnull
  <T> DelegationTarget<T> create(@Nonnull Target target, @Nonnull Class<T> clazz);

  @Nonnull
  MultipleDelegationTargets create(@Nonnull Target target, @Nonnull Class... classes);
}
