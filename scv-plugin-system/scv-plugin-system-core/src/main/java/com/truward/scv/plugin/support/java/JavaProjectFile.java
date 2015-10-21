package com.truward.scv.plugin.support.java;

import com.truward.scv.plugin.api.name.FqName;
import com.truward.scv.plugin.api.output.Project;
import com.truward.scv.plugin.support.AbstractProjectFile;
import com.truward.scv.plugin.support.java.jst.Jst;

/**
 * Java project file.
 */
public class JavaProjectFile extends AbstractProjectFile {

  private Jst.Unit unit; // used in code generation step

  public JavaProjectFile(Project project, FqName fqName) {
    super(project, fqName);
  }

  public Jst.Unit getUnit() {
    return unit;
  }

  public void setUnit(Jst.Unit unit) {
    this.unit = unit;
  }
}
