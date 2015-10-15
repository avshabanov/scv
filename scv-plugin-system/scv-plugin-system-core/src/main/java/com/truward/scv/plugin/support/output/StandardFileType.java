package com.truward.scv.plugin.support.output;

import javax.annotation.Nonnull;

/**
 * Contains common file extensions
 *
 * @author Alexander Shabanov
 */
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
