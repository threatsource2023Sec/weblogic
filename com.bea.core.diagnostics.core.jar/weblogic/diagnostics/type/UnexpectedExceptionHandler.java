package weblogic.diagnostics.type;

import weblogic.diagnostics.i18n.DiagnosticsLogger;

public class UnexpectedExceptionHandler {
   private UnexpectedExceptionHandler() {
   }

   public static void handle(String message, Throwable error) {
      DiagnosticsLogger.logUnexpectedException(message, error);
   }
}
