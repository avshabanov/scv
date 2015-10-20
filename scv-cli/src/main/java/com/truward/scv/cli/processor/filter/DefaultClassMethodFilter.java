package com.truward.scv.cli.processor.filter;

import com.truward.scv.specification.filter.ClassMethodFilter;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public class DefaultClassMethodFilter extends AbstractMethodFilter implements ClassMethodFilter<Object> {

  @Nonnull
  @Override
  public Object forMethod() {
    // call method and notify
    throw new UnsupportedOperationException();
  }
}
