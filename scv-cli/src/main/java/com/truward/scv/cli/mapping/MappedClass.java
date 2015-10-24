package com.truward.scv.cli.mapping;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public interface MappedClass {
  @Nonnull
  Class<?> getSourceClass();

  @Nonnull
  String getTargetName();

  @Nonnull
  MappedTarget getMappedTarget();
}
