package com.truward.scv.specification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a particular mapping for a given classes to the target class name.
 *
 * @author Alexander Shabanov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TargetMappingEntry {

  /**
   * A list of classes mapped to certain target.
   *
   * @return List of source classes
   */
  Class[] source() default {};

  /**
   * Specially coded target name, e.g. {@code com.mycompany.generated.FooImpl}
   *
   * @return Coded target name
   */
  String targetName() default "";

  String targetPackageName() default "";

  Class[] targetPackageClass() default {};
}
