package com.truward.scv.plugin.support.output2;

import javax.annotation.Nonnull;

/**
 * Represents common information about the particular file
 *
 * @author Alexander Shabanov
 */
@Deprecated
public interface FileType {

  @Nonnull
  String getExtension();
}
