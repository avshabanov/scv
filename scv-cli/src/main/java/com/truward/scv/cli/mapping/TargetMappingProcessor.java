package com.truward.scv.cli.mapping;

import javax.annotation.Nonnull;

/**
 * Abstraction over the service, that processes mapping annotations.
 *
 * @author Alexander Shabanov
 */
public interface TargetMappingProcessor {

  void addMappingsFrom(@Nonnull Class<?> specificationClass);
}
