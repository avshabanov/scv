package com.truward.scv.cli.project;

import com.truward.scv.plugin.support.java.Jst;

/**
 * Java project file.
 * TODO: rethink
 */
@Deprecated
public interface JavaProjectFile extends ProjectFile {

  Jst.Unit getUnit();
}
