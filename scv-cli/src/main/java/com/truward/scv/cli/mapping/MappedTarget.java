package com.truward.scv.cli.mapping;

import com.truward.scv.plugin.api.name.FqName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Alexander Shabanov
 */
public interface MappedTarget {

  @Nonnull
  List<MappedClass> getMappedClasses();

  @Nullable
  MappedClass getPrimaryMappedClass();

  @Nonnull
  FqName getTargetName();
}
