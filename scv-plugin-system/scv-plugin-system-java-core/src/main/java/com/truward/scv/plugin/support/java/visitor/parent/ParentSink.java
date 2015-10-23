package com.truward.scv.plugin.support.java.visitor.parent;

import com.truward.scv.plugin.support.java.Jst;

import javax.annotation.Nonnull;

/**
 * Write interface to provider manager.
 *
 * @author Alexander Shabanov
 */
public interface ParentSink {

  void push(@Nonnull Jst.Node node);

  void pop();
}
