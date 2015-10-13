package com.truward.scv.plugin.api.output;

import com.truward.scv.plugin.api.name.FqName;

import javax.annotation.Nonnull;

/**
 * Represents project structure
 *
 * @author Alexander Shabanov
 */
public interface Project {
  @Nonnull
  <T extends ProjectFile> ProjectFile addProjectFile(@Nonnull FqName name, @Nonnull Class<T> projectFileClass);
}
