package com.truward.scv.plugin.api;

import javax.annotation.Nonnull;

/**
 * Facade, to specification processor's module.
 *
 * @author Alexander Shabanov
 */
public interface SpecificationProcessorContext {
  void registerBean(@Nonnull Class<?> implementationClass);
}
