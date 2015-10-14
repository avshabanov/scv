package com.truward.scv.plugin.support.output2;

import javax.annotation.Nonnull;

/**
 * Contains common file extensions
 *
 * @author Alexander Shabanov
 */
@Deprecated
public enum StandardFileType implements FileType {
  NONE(""),
  JAVA("java"),
  PROPERTIES("properties"),
  JS("js"),
  XML("xml"),
  JSON("json"),
  SQL("sql"),
  TXT("txt"),
  HTML("html");
  
  @Nonnull
  private final String extension;

  @Override
  @Nonnull
  public String getExtension() {
    return extension;
  }

  StandardFileType(@Nonnull String extension) {
    this.extension = extension;
  }
}
