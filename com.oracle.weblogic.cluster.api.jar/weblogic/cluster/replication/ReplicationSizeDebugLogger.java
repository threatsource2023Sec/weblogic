package weblogic.cluster.replication;

import weblogic.diagnostics.debug.DebugLogger;

public class ReplicationSizeDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugReplicationSize");

   public static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   public static void debug(ROID id, String msg, Throwable t) {
      logger.debug("[roid:" + id + "] " + msg, t);
   }
}
