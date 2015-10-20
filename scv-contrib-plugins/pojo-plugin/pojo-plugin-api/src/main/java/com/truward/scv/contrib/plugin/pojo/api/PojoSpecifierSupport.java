package com.truward.scv.contrib.plugin.pojo.api;

import com.truward.scv.contrib.plugin.pojo.api.binding.MultiplePojoTargets;
import com.truward.scv.contrib.plugin.pojo.api.binding.PojoTarget;
import com.truward.scv.specification.Target;

import javax.annotation.Nonnull;

/**
 * Helper for pojo specification classes.
 */
public abstract class PojoSpecifierSupport implements PojoSpecifier {
  private PojoSpecifier pojoSpecifier;

  public void setPojoSpecifier(@Nonnull PojoSpecifier specifier) {
    this.pojoSpecifier = specifier;
  }

  @Nonnull
  @Override
  public <T> PojoTarget<T> create(@Nonnull Target target, @Nonnull Class<T> clazz) {
    return pojoSpecifier.create(target, clazz);
  }

  @Nonnull
  @Override
  public MultiplePojoTargets create(@Nonnull Target target, @Nonnull Class... classes) {
    return pojoSpecifier.create(target, classes);
  }
}
