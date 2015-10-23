package com.truward.scv.cli.project;

import com.truward.scv.plugin.api.name.FqName;

import javax.annotation.Nonnull;

/**
 * Represents a file in the project.
 * TODO: rethink
 *
 * @author Alexander Shabanov
 */
@Deprecated
public interface ProjectFile {

  @Nonnull
  Project getProject();

  @Nonnull
  FqName getFqName();
}
