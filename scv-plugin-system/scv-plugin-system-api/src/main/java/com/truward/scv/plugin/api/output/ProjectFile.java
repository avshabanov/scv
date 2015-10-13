package com.truward.scv.plugin.api.output;

import com.truward.scv.plugin.api.name.FqName;

import javax.annotation.Nonnull;

/**
 * Represents a file in the project.
 *
 * @author Alexander Shabanov
 */
public interface ProjectFile {

  @Nonnull
  Project getProject();

  @Nonnull
  FqName getFqName();
}
