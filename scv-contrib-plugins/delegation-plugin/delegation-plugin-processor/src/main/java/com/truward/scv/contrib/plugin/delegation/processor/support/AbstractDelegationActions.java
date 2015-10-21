package com.truward.scv.contrib.plugin.delegation.processor.support;

import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationActions;
import com.truward.scv.plugin.api.lifecycle.FilterFinalizer;
import com.truward.scv.specification.Target;
import com.truward.scv.specification.filter.MethodFilter;

import javax.annotation.Nonnull;

/**
 */
public class AbstractDelegationActions<Filter extends MethodFilter> implements DelegationActions<Filter> {
  private final Target targetFile;
  private final FilterFinalizerImpl filterFinalizer = new FilterFinalizerImpl();

  public AbstractDelegationActions(Target targetFile) {
    this.targetFile = targetFile;
  }

  @Nonnull
  @Override
  public Filter returnConstant(Object constant) {
    return null;
  }

  @Nonnull
  @Override
  public Filter throwException(@Nonnull Class<? extends RuntimeException> exceptionClass) {
    return null;
  }

  @Nonnull
  @Override
  public Filter delegateCall() {
    return null;
  }

  @Nonnull
  @Override
  public Filter bindAspect(Class<?> aspectClass) {
    return null;
  }

  private static final class Action {

  }

  private Filter checkAndCreateFilter(Action action) {
    if (!filterFinalizer.filterSet) {
      // should be set as a result of a previous call
    }

    throw new UnsupportedOperationException();
  }

  // abstract?
  protected Filter createFilter() {
    throw new UnsupportedOperationException();
  }

  private final class FilterFinalizerImpl implements FilterFinalizer {
    boolean filterSet = true;
    // TODO: also add PreDestroy action to listen when filter gets killed

    @Override
    public void onFilterSet() {
      if (filterSet) {
        throw new IllegalStateException("Filter has been set more than once");
      }
      filterSet = true;
    }
  }
}
