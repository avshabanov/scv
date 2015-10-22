package com.truward.scv.contrib.plugin.delegation.processor.support;

import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationActions;
import com.truward.scv.contrib.plugin.delegation.processor.action.Action;
import com.truward.scv.specification.filter.MethodFilter;

import javax.annotation.Nonnull;

/**
 * Base class for delegation actions
 */
public abstract class AbstractDelegationActions<Filter extends MethodFilter> implements DelegationActions<Filter> {

  @Nonnull
  @Override
  public Filter returnConstant(Object constant) {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  @Override
  public Filter throwException(@Nonnull Class<? extends RuntimeException> exceptionClass) {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  @Override
  public Filter delegateCall() {
    return checkAndCreateFilter(new Action.DelegateCall());
  }

  @Nonnull
  @Override
  public Filter bindAspect(Class<?> aspectClass) {
    throw new UnsupportedOperationException();
  }

  private Filter checkAndCreateFilter(Action action) {

    return createFilter(action);
  }

  protected abstract Filter createFilter(Action action);

  // TODO: check if filter has been set
//  private final class FilterFinalizerImpl implements FilterFinalizer {
//    boolean filterSet = true;
//    // TODO: also add PreDestroy action to listen if last filter has not been set
//
//    @Override
//    public void onFilterSet() {
//      if (filterSet) {
//        throw new IllegalStateException("Filter has been set more than once");
//      }
//      filterSet = true;
//    }
//  }
}
