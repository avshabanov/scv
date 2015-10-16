package com.truward.scv.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

/**
 * Entry point for command line tool.
 */
public final class App {
  private final PrintStream out;
  private final PrintStream err;
  private final String[] args;

  // VisibleForTesting
  App(PrintStream out, PrintStream err, String[] args) {
    this.out = out;
    this.err = err;
    this.args = args;
  }

  public static void main(String[] args) {
    try {
      new App(System.out, System.err, args).run();
    } catch (AbnormalExitException ignored) {
      System.exit(-1);
    }
  }

  // VisibleForTesting
  void run() {
    final CliOptionsParser parser = new CliOptionsParser(args);
    final CliOptionsParser.Result result = parser.parse();
    result.apply(new CliOptionsParser.ResultVisitor() {
      @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
      @Override
      public void visitError(CliOptionsParser.ErrorResult result) {
        err.println("Error while running application");
        if (result.getError() != null) {
          out.println("Message: " + result.getError());
        }
        if (result.getException() != null) {
          result.getException().printStackTrace(err);
        }
        CliOptionsParser.showUsage(out);
        throw new AbnormalExitException();
      }

      @Override
      public void visitShowHelp(CliOptionsParser.ShowHelpResult result) {
        CliOptionsParser.showUsage(out);
      }

      @Override
      public void visitShowVersion(CliOptionsParser.ShowVersionResult result) {
        final Properties properties = new Properties();
        try {
          final InputStream inputStream = getClass().getResourceAsStream("/app.properties");
          if (inputStream == null) {
            throw new IOException("No app.properties");
          }
          properties.load(inputStream);
          final String version = properties.getProperty("polymer.cli.app.version");
          // TODO: fetch version from resources
          out.println("Version: " + version);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public void visitProcessSpec(CliOptionsParser.ProcessSpecResult result) {
        try {
          generateCode(result);
        } catch (RuntimeException e) {
          err.println("Error while generating code");
          if (e.getMessage() != null) {
            err.println("Reason: " + e.getMessage());
          }
          e.printStackTrace(err);
          throw new AbnormalExitException();
        }
      }
    });
  }

  // VisibleForTesting
  void generateCode(CliOptionsParser.ProcessSpecResult result) {
    out.println("Run App: target=" + result.getTargetDir() +
        ", specificationPackage=" + result.getSpecificationPackage() +
        ", specificationClasses=" + result.getSpecificationClasses());
  }

  /**
   * An exception, that designates abnormal exit but does not imply that any other exception was thrown.
   */
  // VisibleForTesting
  class AbnormalExitException extends RuntimeException {
  }
}
