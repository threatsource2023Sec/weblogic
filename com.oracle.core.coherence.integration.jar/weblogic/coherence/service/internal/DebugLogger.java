package weblogic.coherence.service.internal;

public class DebugLogger {
   private static final weblogic.diagnostics.debug.DebugLogger logger = weblogic.diagnostics.debug.DebugLogger.getDebugLogger("DebugCoherence");

   public static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   public static void debug(String msg) {
      logger.debug(msg);
   }

   public static void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }
}
