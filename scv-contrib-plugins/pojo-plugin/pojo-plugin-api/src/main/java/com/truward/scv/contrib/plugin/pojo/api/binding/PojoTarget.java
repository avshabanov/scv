package com.truward.scv.contrib.plugin.pojo.api.binding;

import com.truward.scv.specification.filter.ClassMethodFilter;

/**
 * Represents single POJO target
 *
 * @author Alexander Shabanov
 */
public interface PojoTarget<T> extends PojoActions<ClassMethodFilter<T>> {
}
