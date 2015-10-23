package com.truward.scv.cli.project;

import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.plugin.support.java.Jst;

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
      return projectFileClass.cast(new JavaProjectFileImpl(this, fqName));
    }

    throw new UnsupportedOperationException("Unsupported project fileClass=" + projectFileClass);
  }

  private static class AbstractProjectFile implements ProjectFile {
    final Project project;
    final FqName fqName;

    public AbstractProjectFile(Project project, FqName fqName) {
      this.project = project;
      this.fqName = fqName;
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

  private static class JavaProjectFileImpl extends AbstractProjectFile implements JavaProjectFile {
    private Jst.Unit unit; // = factory.jstUnit()

    public JavaProjectFileImpl(Project project, FqName fqName) {
      super(project, fqName);
    }

    @Override
    public Jst.Unit getUnit() {
      return unit;
    }
  }
}
