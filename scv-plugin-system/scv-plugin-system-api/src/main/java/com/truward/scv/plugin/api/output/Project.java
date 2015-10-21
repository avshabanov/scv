package com.truward.scv.plugin.api.output;

import com.truward.scv.plugin.api.name.FqName;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Represents project structure
 *
 * @author Alexander Shabanov
 */
public interface Project {
  @Nonnull
  <T extends ProjectFile> T addFile(@Nonnull FqName name, @Nonnull Class<T> projectFileClass);

  @Nonnull
  List<ProjectFile> getFiles();
}
