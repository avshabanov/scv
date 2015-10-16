package com.truward.scv.cli;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link App}.
 */
public final class AppTest {

  private ByteArrayOutputStream baosOut;
  private PrintStream out;

  private ByteArrayOutputStream baosErr;
  private PrintStream err;

  @Before
  public void init() throws UnsupportedEncodingException {
    baosOut = new ByteArrayOutputStream();
    out = new PrintStream(baosOut, true, StandardCharsets.UTF_8.name());

    baosErr = new ByteArrayOutputStream();
    err = new PrintStream(baosErr, true, StandardCharsets.UTF_8.name());
  }

  @Test
  public void shouldProduceHelpOutput() {
    // Given:
    final String[] args = {"-h"};

    // When:
    new App(out, err, args).run();

    // Then:
    final String outContents = getOut();
    assertTrue(outContents.contains("Usage:"));
    assertTrue(getErr().isEmpty());
  }

  @Test
  public void shouldExitAbnormally() {
    // Given:
    final String[] args = {"-t"};

    // When:
    try {
      new App(out, err, args).run();
      fail();
    } catch (App.AbnormalExitException ignored) {
      // ok
    }

    // Then:
    final String errContents = getErr();
    assertTrue(errContents.contains("Argument -t expects subsequent parameter to be passed"));
  }

  //
  // Private
  //

  private String getOut() {
    return new String(baosOut.toByteArray(), StandardCharsets.UTF_8);
  }

  private String getErr() {
    return new String(baosErr.toByteArray(), StandardCharsets.UTF_8);
  }
}
