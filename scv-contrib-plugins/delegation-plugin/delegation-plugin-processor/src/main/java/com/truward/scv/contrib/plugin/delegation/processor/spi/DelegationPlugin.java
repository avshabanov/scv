package com.truward.scv.contrib.plugin.delegation.processor.spi;

import com.truward.scv.contrib.plugin.delegation.processor.support.DefaultDelegationSpecifier;
import com.truward.scv.plugin.api.SpecificationProcessorContext;
import com.truward.scv.plugin.api.spi.SpecificationPlugin;

import javax.annotation.Nonnull;

/**
 *
 * @author Alexander Shabanov
 */
public final class DelegationPlugin implements SpecificationPlugin {

  @Override
  public void joinTo(@Nonnull SpecificationProcessorContext module) {
    module.registerBean(DefaultDelegationSpecifier.class);
  }
}
