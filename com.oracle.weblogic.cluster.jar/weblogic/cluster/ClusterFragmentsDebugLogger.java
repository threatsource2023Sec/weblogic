package weblogic.cluster;

import weblogic.diagnostics.debug.DebugLogger;

public class ClusterFragmentsDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugClusterFragments");

   public static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   public static final void debug(String msg) {
      logger.debug(msg);
   }

   public static final void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }
}
