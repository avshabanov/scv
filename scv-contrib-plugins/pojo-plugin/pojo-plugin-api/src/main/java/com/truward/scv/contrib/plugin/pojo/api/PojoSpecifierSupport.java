package com.truward.scv.contrib.plugin.pojo.api;

import com.truward.scv.contrib.plugin.pojo.api.binding.MultiplePojoTargets;
import com.truward.scv.contrib.plugin.pojo.api.binding.PojoTarget;

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
  public <T> PojoTarget<T> forClass(@Nonnull Class<T> clazz) {
    return pojoSpecifier.forClass(clazz);
  }

  @Nonnull
  @Override
  public MultiplePojoTargets forClasses(@Nonnull Class... classes) {
    return pojoSpecifier.forClasses(classes);
  }
}
