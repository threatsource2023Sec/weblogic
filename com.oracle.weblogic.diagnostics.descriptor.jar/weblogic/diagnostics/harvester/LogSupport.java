package weblogic.diagnostics.harvester;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public final class LogSupport implements I18NConstants {
   private static final DebugLogger DBG = DebugLogger.getDebugLogger("DebugDiagnosticsHarvester");
   private static final String GENERIC_PROBLEM_TEXT = I18NSupport.formatter().getGenericHarvesterProblemMessage();

   public static void logUnexpectedProblem(String debugText) {
      DiagnosticsLogger.logGenericHarvesterProblem(getGenericHarvesterProblemText(debugText));
   }

   public static void logExpectedProblem(String i18NText) {
      DiagnosticsLogger.logGenericHarvesterProblem(i18NText);
   }

   public static String getGenericHarvesterProblemText(String debugText) {
      if (DBG.isDebugEnabled()) {
         DBG.debug("*** ERROR *** " + debugText);
      }

      return GENERIC_PROBLEM_TEXT;
   }

   public static void logUnexpectedException(String debugText, Throwable x) {
      UnexpectedExceptionHandler.handle(getGenericHarvesterProblemText(debugText), x);
   }

   public static void assertCondition(boolean value, String debugText) {
      if (!value) {
         throw new AssertionError(getGenericHarvesterProblemText(debugText));
      }
   }
}
