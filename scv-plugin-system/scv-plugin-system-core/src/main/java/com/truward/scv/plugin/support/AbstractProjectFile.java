package com.truward.scv.plugin.support;

import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.plugin.api.output.Project;
import com.truward.scv.plugin.api.output.ProjectFile;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Standard project file.
 */
public abstract class AbstractProjectFile implements ProjectFile {
  private final Project project;
  private final FqName fqName;

  public AbstractProjectFile(Project project, FqName fqName) {
    this.project = Objects.requireNonNull(project);
    this.fqName = Objects.requireNonNull(fqName);
  }

  @Nonnull
  @Override
  public Project getProject() {
    return project;
  }

  @Nonnull
  @Override
  public FqName getFqName() {
    return fqName;
  }
}
