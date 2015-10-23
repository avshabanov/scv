package com.truward.scv.plugin.support.filter;

import com.truward.scv.specification.filter.MethodFilter;
import org.slf4j.LoggerFactory;

/**
 * @author Alexander Shabanov
 */
public abstract class AbstractMethodFilter implements MethodFilter {
  // notify that all the methods selected

  @Override
  public void forAllMethods() {
    // TODO: impl
    LoggerFactory.getLogger(getClass()).warn("TODO: impl forAllMethods()");
  }
}
