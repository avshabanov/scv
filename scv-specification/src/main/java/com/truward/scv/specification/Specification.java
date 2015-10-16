package com.truward.scv.specification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates specification method.
 * Any method annotated with {@code @Specification} will be a subject to special treatment to specification processing
 * framework.
 *
 * @author Alexander Shabanov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Specification {

  /**
   * This ordinal assigns the priority for specification methods in the specification class
   * which may be needed to establish a predictable order of processing specification methods.
   * <p>
   * The bigger number means higher priority (these methods will be processed first) and the smaller number
   * means conversely lower priority.
   * </p>
   * Default value is {@link Integer#MAX_VALUE}.
   *
   * @return Priority of this specification method, bigger number means bigger priority.
   */
  int priority() default Integer.MAX_VALUE;
}
