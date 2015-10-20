package com.truward.scv.contrib.plugin.pojo.api;

import com.truward.scv.contrib.plugin.pojo.api.binding.MultiplePojoTargets;
import com.truward.scv.contrib.plugin.pojo.api.binding.PojoTarget;
import com.truward.scv.specification.Target;

import javax.annotation.Nonnull;

/**
 * POJO specification creator.
 */
public interface PojoSpecifier {

  @Nonnull
  <T> PojoTarget<T> create(@Nonnull Target target, @Nonnull Class<T> clazz);

  @Nonnull
  MultiplePojoTargets create(@Nonnull Target target, @Nonnull Class... classes);
}
