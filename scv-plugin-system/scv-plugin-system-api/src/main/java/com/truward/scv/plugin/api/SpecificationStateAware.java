package com.truward.scv.plugin.api;

import javax.annotation.Nonnull;

/**
 * A listener to those classes that need to be notified about changed state.
 */
public interface SpecificationStateAware {

  void notifyStateChanged(@Nonnull SpecificationState state);
}
