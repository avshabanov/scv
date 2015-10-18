package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationTarget;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationTargets;
import com.truward.scv.specification.Target;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Helper class for specification classes.
 *
 * @author Alexander Shabanov
 */
public abstract class DelegationSpecifierSupport implements DelegationSpecifier {
  @Resource
  private DelegationSpecifier delegationSpecifier;

  @Nonnull
  @Override
  public <T> DelegationTarget<T> create(@Nonnull Target target, @Nonnull Class<T> clazz) {
    return delegationSpecifier.create(target, clazz);
  }

  @Nonnull
  @Override
  public MultipleDelegationTargets create(@Nonnull Target target, @Nonnull Class... classes) {
    return delegationSpecifier.create(target, classes);
  }
}
