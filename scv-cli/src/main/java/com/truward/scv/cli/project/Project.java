package com.truward.scv.cli.project;

import com.truward.scv.plugin.api.name.FqName;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Represents project structure.
 * TODO: rethink
 *
 * @author Alexander Shabanov
 */
@Deprecated
public interface Project {
  @Nonnull
  <T extends ProjectFile> T addFile(@Nonnull FqName name, @Nonnull Class<T> projectFileClass);

  @Nonnull
  List<ProjectFile> getFiles();
}
