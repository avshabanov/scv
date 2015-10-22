package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.ClassDelegationAction;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationActions;

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
  public <T> ClassDelegationAction<T> forClass(@Nonnull Class<T> interfaceClass) {
    return delegationSpecifier.forClass(interfaceClass);
  }

  @Nonnull
  @Override
  public MultipleDelegationActions forClasses(@Nonnull Class... interfaceClasses) {
    return delegationSpecifier.forClasses(interfaceClasses);
  }
}
