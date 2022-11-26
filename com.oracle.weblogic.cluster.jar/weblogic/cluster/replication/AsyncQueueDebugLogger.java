package weblogic.cluster.replication;

import weblogic.diagnostics.debug.DebugLogger;

public class AsyncQueueDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugAsyncQueue");

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
