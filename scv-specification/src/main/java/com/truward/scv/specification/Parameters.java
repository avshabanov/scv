package com.truward.scv.specification;

/**
 * @author Alexander Shabanov
 */
public class Parameters {
  public static <T> T any(Class<T> parameterClass) {
    return null;
  }

  public static int anyInt() {
    return 0;
  }
}
