package com.truward.scv.plugin.api;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 */
public interface SpecificationParameterProvider {

  <R> boolean canProvideParameter(@Nonnull List<? extends Annotation> annotations,
                                  @Nonnull Class<R> resultType);

  @Nonnull
  <R> R provideParameter(@Nonnull List<? extends Annotation> annotations,
                         @Nonnull Class<R> resultType);
}
