package com.truward.scv.specification.annotation;

import java.lang.annotation.*;

/**
 * Identifies mapping between interfaces and target classes.
 * Every specification class should be annotated with this.
 *
 * @author Alexander Shabanov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TargetMapping {

  TargetMappingEntry[] value();
}
