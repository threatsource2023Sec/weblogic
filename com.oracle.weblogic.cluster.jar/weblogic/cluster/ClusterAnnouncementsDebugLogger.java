package weblogic.cluster;

import weblogic.diagnostics.debug.DebugLogger;

public class ClusterAnnouncementsDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugClusterAnnouncements");

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
