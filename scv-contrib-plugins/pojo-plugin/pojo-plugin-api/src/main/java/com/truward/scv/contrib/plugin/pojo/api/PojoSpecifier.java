package com.truward.scv.contrib.plugin.pojo.api;

import com.truward.scv.contrib.plugin.pojo.api.binding.MultiplePojoTargets;
import com.truward.scv.contrib.plugin.pojo.api.binding.PojoTarget;

import javax.annotation.Nonnull;

/**
 * POJO specification creator.
 */
public interface PojoSpecifier {

  @Nonnull
  <T> PojoTarget<T> forClass(@Nonnull Class<T> clazz);

  @Nonnull
  MultiplePojoTargets forClasses(@Nonnull Class... classes);
}
