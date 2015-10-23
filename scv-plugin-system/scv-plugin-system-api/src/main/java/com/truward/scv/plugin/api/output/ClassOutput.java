package com.truward.scv.plugin.api.output;

import javax.annotation.Nonnull;

/**
 * Returns an output object for a given class.
 */
public interface ClassOutput {

  @Nonnull
  Class<?> getAssociatedClass();
}
