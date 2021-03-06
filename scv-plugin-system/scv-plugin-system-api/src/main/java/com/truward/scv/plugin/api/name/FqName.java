package com.truward.scv.plugin.api.name;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents fully qualified name, e.g. <code>com.mycompany.ClassName</code>.
 *
 * @author Alexander Shabanov
 */
public final class FqName implements Comparable<FqName>, Serializable {
  private static final long serialVersionUID = 1034564186589752765L;

  private final String name;
  private final FqName parent;
  /** Precalculated hash code, optimized for immutable FqName, default to 0 */
  private transient int hash;

  @Nonnull
  public static FqName valueOf(@Nonnull String fqName) {
    int dotIndex;
    int nextIndex = 0;

    FqName next = null;
    for (;;) {
      dotIndex = fqName.indexOf('.', nextIndex);
      final String part;
      if (dotIndex < 0) {
        part = fqName.substring(nextIndex);
      } else {
        part = fqName.substring(nextIndex, dotIndex);
        nextIndex = dotIndex + 1;
      }
      if (part.isEmpty()) {
        throw new IllegalArgumentException("Empty name part in the qualified name: " + fqName);
      }
      next = new FqName(part, next);

      if (dotIndex < 0) {
        break;
      }
    }

    return next;
  }

  public FqName(@Nonnull String name, @Nullable FqName parent) {
    this.name = name;
    this.parent = parent;
  }

  @Nonnull
  public String getName() {
    return name;
  }

  @Nonnull
  public FqName getParent() {
    if (parent == null) {
      throw new IllegalStateException("There is no parent of the root fqName");
    }
    return parent;
  }

  @Nonnull
  public List<String> toList() {
    final int count = count();
    if (count == 1) {
      return Collections.singletonList(getName());
    }

    final List<String> names = new ArrayList<>(count());
    for (FqName fqn = this;; fqn = fqn.getParent()) {
      names.add(0, fqn.getName());
      if (fqn.isRoot()) {
        break;
      }
    }
    return names;
  }

  @Nonnull
  public FqName append(@Nonnull String name) {
    return new FqName(name, this);
  }

  @Nonnull
  public FqName append(@Nonnull FqName fqName) {
    FqName result = this;

    // get unfolded methods
    for (final String name : fqName.toList()) {
      result = result.append(name);
    }

    return result;
  }

  public boolean isRoot() {
    return parent == null;
  }

  @Override
  public int compareTo(@Nonnull FqName o) {
    int r = getName().compareTo(o.getName());
    if (r != 0) {
      return r;
    }

    if (isRoot()) {
      return o.isRoot() ? 0 : -1;
    } else if (o.isRoot()) {
      return 1;
    }

    return getParent().compareTo(o.getParent());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FqName)) {
      return false;
    }

    FqName other = (FqName) o;
    FqName self = this;
    for (;;) {
      if (!other.getName().equals(self.getName())) {
        return false;
      }

      other = other.parent;
      self = self.parent;
      if (other == self) {
        return true; // matched parents
      } else if (other == null || self == null) {
        return false;
      }
    }
  }

  @Override
  public int hashCode() {
    if (hash != 0) {
      return hash; // optimized access
    }

    int result = name.hashCode();
    for (FqName i = parent; i != null; i = i.parent) {
      result = 31 * result + i.name.hashCode();
    }

    hash = result;
    return hash;
  }


  @Override
  public String toString() {
    if (isRoot()) {
      return getName();
    }

    // append all name parts
    final int symCount = length();
    final StringBuilder builder = new StringBuilder(symCount);
    try {
      appendTo(builder);
    } catch (IOException e) {
      throw new IllegalStateException(e); // should never happen
    }

    assert builder.length() == symCount;
    return builder.toString();
  }

  /**
   * @return Count of chars in the toString representation, separated by dots
   */
  public int length() {
    int symCount = 0;
    FqName i = this;
    for (;; i = i.getParent()) {
      if (symCount > 0) {
        ++symCount; // separator char - 'dot'
      }
      symCount += i.getName().length();
      if (i.isRoot()) {
        break;
      }
    }
    return symCount;
  }

  /**
   * @return Count of parts in this name
   */
  public int count() {
    int partCount = 0;
    FqName i = this;
    for (;; i = i.getParent()) {
      ++partCount;
      if (i.isRoot()) {
        break;
      }
    }
    return partCount;
  }

  public void appendTo(@Nonnull Appendable appendable) throws IOException {
    appendTo(appendable, '.');
  }

  public void appendTo(@Nonnull Appendable appendable, char separator) throws IOException {
    if (!isRoot()) {
      getParent().appendTo(appendable, separator);
      appendable.append(separator);
    }
    appendable.append(getName());
  }
}
