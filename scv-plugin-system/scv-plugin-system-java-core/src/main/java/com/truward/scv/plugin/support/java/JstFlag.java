package com.truward.scv.plugin.support.java;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Represents an attribute, associated with the particular named statement (variable, class or method)
 *
 * @author Alexander Shabanov
 */
public enum JstFlag {
  /** The modifier {@code public} */                PUBLIC,
  /** The modifier {@code protected} */             PROTECTED,
  /** The modifier {@code private} */               PRIVATE,
  /** The modifier {@code abstract} */              ABSTRACT,
  /** The modifier {@code static} */                STATIC,
  /** The modifier {@code final} */                 FINAL,
  /** The modifier {@code transient} */             TRANSIENT,
  /** The modifier {@code volatile} */              VOLATILE,
  /** The modifier {@code synchronized} */          SYNCHRONIZED,
  /** The modifier {@code native} */                NATIVE,
  /** The modifier {@code strictfp} */              STRICTFP,

  /** Non-modifier - designates interface */        INTERFACE,
  /** Non-modifier - designates enumeration */      ENUM,
  /** Non-modifier - designates annotation */       ANNOTATION,
  /** Non-modifier - designates anonymous class */  ANONYMOUS;

  private String lowercase;
  private Boolean modifier;

  public static final Set<JstFlag> MODIFIERS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(PUBLIC,
      PROTECTED, PRIVATE, ABSTRACT, STATIC, FINAL, TRANSIENT, VOLATILE, SYNCHRONIZED, NATIVE, STRICTFP)));

  public boolean isModifier() {
    if (modifier == null) {
      modifier = MODIFIERS.contains(this);
    }

    return modifier;
  }

  /**
   * @return Lowercase representation of a flag.
   */
  @Nonnull
  @Override
  public String toString() {
    if (lowercase == null) {
      lowercase = this.name().toLowerCase(Locale.US);
    }
    return lowercase;
  }
}
