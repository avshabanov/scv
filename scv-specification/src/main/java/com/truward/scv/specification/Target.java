package com.truward.scv.specification;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Specifies implementation target, that should emerge after code generation.
 *
 * @author Alexander Shabanov
 */
public final class Target {
  private final String className;

  private Target(String className) {
    // TODO: validation
    this.className = Objects.requireNonNull(className, "className");
  }

  /**
   * Creates target from full class name.
   * <p>
   * For example, {code Target.fromClassName("com.myproject.model.GeneratedUser")} specifies
   * a class {@code GeneratedUser} within the package {@code "com.myproject.model"}.
   * </p>
   *
   * @param className Full class name in standard java notation, e.g. {@code com.myapp.Foo}
   * @return Target instance
   */
  @Nonnull
  public static Target fromClassName(@Nonnull String className) {
    return new Target(className);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Target)) return false;

    Target target = (Target) o;

    return className.equals(target.className);

  }

  @Override
  public int hashCode() {
    return className.hashCode();
  }

  @Override
  public String toString() {
    return "Target{" +
        "className='" + className + '\'' +
        '}';
  }
}
