package com.truward.scv.plugin.support.java.visitor.parent;

import com.truward.scv.plugin.support.java.Jst;

import javax.annotation.Nonnull;

/**
 * Interface to the parent-provider manager.
 *
 * @author Alexander Shabanov
 */
public interface ParentProvider {

  @Nonnull
  Jst.Node getParent();
}
