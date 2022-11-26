package weblogic.restart;

import weblogic.diagnostics.debug.DebugLogger;

public class RPDebug {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugRestartInPlace");

   public static final void debug(String msg) {
      logger.debug(msg);
   }

   public static final void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }

   public static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }
}
