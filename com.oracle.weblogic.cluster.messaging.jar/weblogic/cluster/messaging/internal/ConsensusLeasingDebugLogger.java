package weblogic.cluster.messaging.internal;

import weblogic.diagnostics.debug.DebugLogger;

public class ConsensusLeasingDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugConsensusLeasing");

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
