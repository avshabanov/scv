package com.truward.scv.contrib.plugin.delegation.processor.support;

import com.truward.scv.contrib.plugin.delegation.api.DelegationSpecifier;
import com.truward.scv.contrib.plugin.delegation.api.binding.ClassDelegationAction;
import com.truward.scv.contrib.plugin.delegation.api.binding.MultipleDelegationActions;
import com.truward.scv.contrib.plugin.delegation.processor.action.Action;
import com.truward.scv.plugin.api.output.Project;
import com.truward.scv.plugin.support.filter.DefaultClassMethodFilter;
import com.truward.scv.specification.filter.ClassMethodFilter;

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
  public <T> ClassDelegationAction<T> forClass(@Nonnull Class<T> interfaceClass) {
    // TODO: remove target
    return new DefaultClassDelegationAction<>(interfaceClass);
  }

  @Nonnull
  @Override
  public MultipleDelegationActions forClasses(@Nonnull Class... interfaceClasses) {
    throw new UnsupportedOperationException();
  }

  //
  // Private
  //

  private static final class DefaultClassDelegationAction<T> extends AbstractDelegationActions<ClassMethodFilter<T>> implements ClassDelegationAction<T> {
    private final Class<T> interfaceClass;

    public DefaultClassDelegationAction(Class<T> interfaceClass) {
      this.interfaceClass = interfaceClass;
    }

    @Override
    protected ClassMethodFilter<T> createFilter(Action action) {
      return new DefaultClassMethodFilter(interfaceClass);
    }
  }
}
