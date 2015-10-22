package com.truward.scv.contrib.plugin.delegation.api.binding;

import com.truward.scv.specification.filter.ClassMethodFilter;

/**
 * Represents specific delegation target.
 *
 * @author Alexander Shabanov
 */
public interface ClassDelegationAction<T> extends DelegationActions<ClassMethodFilter<T>> {
}
