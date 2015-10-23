package com.truward.scv.cli.processor;

import com.truward.scv.specification.annotation.TargetMapping;

import javax.annotation.Nonnull;

/**
 * A processor for {@link TargetMapping} annotations.
 */
public final class TargetMappingProcessor {

  public void setTargetMapping(@Nonnull TargetMapping targetMapping) {

  }

  public void setNoTargetMapping() {
    // set no mappings
  }

  public void finalizeMappings() {
    // establish associations
  }
}
