package com.truward.scv.cli.support;

import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.plugin.api.output.Project;
import com.truward.scv.plugin.api.output.ProjectFile;
import com.truward.scv.plugin.support.java.JavaProjectFile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default project implementation.
 */
public class DefaultProject implements Project {
  private Map<FqName, ProjectFile> files = new HashMap<>();

  @Nonnull
  @Override
  public <T extends ProjectFile> T addFile(@Nonnull FqName name, @Nonnull Class<T> projectFileClass) {
    if (files.containsKey(name)) {
      throw new IllegalStateException("Duplicate file entry for name=" + name);
    }
    final T projectFile = createProjectFile(name, projectFileClass);
    files.put(name, projectFile);
    return projectFile;
  }

  @Nonnull
  @Override
  public List<ProjectFile> getFiles() {
    return Collections.unmodifiableList(new ArrayList<>(files.values()));
  }

  //
  // Create instance
  //

  private <T extends ProjectFile> T createProjectFile(@Nonnull FqName fqName, @Nonnull Class<T> projectFileClass) {
    if (projectFileClass == JavaProjectFile.class) {
      return projectFileClass.cast(new JavaProjectFile(this, fqName));
    }

    throw new UnsupportedOperationException("Unsupported project fileClass=" + projectFileClass);
  }
}
