package com.truward.scv.contrib.plugin.delegation.processor.support;

import com.truward.scv.contrib.plugin.delegation.api.DelegationSpecifier;
import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationTarget;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationTargets;
import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.plugin.api.output.Project;
import com.truward.scv.plugin.support.java.JavaProjectFile;
import com.truward.scv.specification.Target;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Default implementation of delegation specifier.
 */
public final class DefaultDelegationSpecifier implements DelegationSpecifier {
  @Resource
  private Project project;

  @Nonnull
  @Override
  public <T> DelegationTarget<T> create(@Nonnull Target target, @Nonnull Class<T> clazz) {
    final JavaProjectFile pf = project.addFile(FqName.valueOf(target.getClassName()), JavaProjectFile.class);
    throw new UnsupportedOperationException();
  }

  @Nonnull
  @Override
  public MultipleDelegationTargets create(@Nonnull Target target, @Nonnull Class... classes) {
    throw new UnsupportedOperationException();
  }
}
