package com.truward.scv.plugin.api.spi;

import com.truward.scv.plugin.api.SpecificationProcessorContext;

import javax.annotation.Nonnull;

/**
 * Core interface to non-DI injected plugin which contains all the required classes, that implement the required
 * plugin interaction logic.
 *
 * @author Alexander Shabanov
 */
public interface SpecificationPlugin {
  void joinTo(@Nonnull SpecificationProcessorContext module);
}
