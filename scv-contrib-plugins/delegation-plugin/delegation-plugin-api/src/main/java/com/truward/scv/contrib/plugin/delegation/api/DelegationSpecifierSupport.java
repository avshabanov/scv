package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationTarget;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationTargets;
import com.truward.scv.specification.Target;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Helper for delegation specification classes.
 *
 * @author Alexander Shabanov
 */
public abstract class DelegationSpecifierSupport implements DelegationSpecifier {
  private DelegationSpecifier delegationSpecifier;

  @Resource
  public void setDelegationSpecifier(DelegationSpecifier specifier) {
    this.delegationSpecifier = specifier;
  }

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
