package com.truward.scv.cli.mapping;

import java.util.List;

/**
 * Interface to retrieve mapped targets.
 */
public interface TargetMappingProvider {
  List<MappedTarget> getMappedTargets();
}
