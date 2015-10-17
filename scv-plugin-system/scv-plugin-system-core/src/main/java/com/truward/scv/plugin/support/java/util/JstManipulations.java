package com.truward.scv.plugin.support.java.util;

import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.plugin.support.java.Jst;
import com.truward.scv.plugin.support.java.JstFlag;

import javax.annotation.Nonnull;

/**
 * Utility class for manipulating on JST entities.
 */
public final class JstManipulations {
  private JstManipulations() {} // hidden

  /**
   * Tries to get a unit name, inferred from the class names declared in this unit.
   * Throws an exception if this unit doesn't have any classes or more than just one public class.
   *
   * @param unit Unit to find a name from
   * @return Fully qualified name
   */
  @Nonnull
  public static FqName getUnitName(@Nonnull Jst.Unit unit) {
    Jst.ClassDeclaration publicClass = null;
    Jst.ClassDeclaration candidateClass = null;
    for (final Jst.ClassDeclaration c : unit.getClasses()) {
      if (c.getFlags().contains(JstFlag.PUBLIC)) {
        if (publicClass != null) {
          throw new IllegalStateException("Unit " + unit.getPackageName() + " has more than one public class");
        }
        publicClass = c;
      } else if (candidateClass == null) {
        candidateClass = c;
      }
    }

    if (publicClass != null) {
      return unit.getPackageName().append(publicClass.getName());
    }

    if (candidateClass != null) {
      return unit.getPackageName().append(candidateClass.getName());
    }

    throw new IllegalStateException("There are no classes declared in unit " + unit.getPackageName());
  }
}
