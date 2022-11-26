package weblogic.work;

import weblogic.diagnostics.debug.DebugLogger;

public class SelfTuningDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugSelfTuning");

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
