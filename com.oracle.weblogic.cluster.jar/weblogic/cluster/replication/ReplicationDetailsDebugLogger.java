package weblogic.cluster.replication;

import weblogic.diagnostics.debug.DebugLogger;

public class ReplicationDetailsDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugReplicationDetails");

   public static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   public static void debug(String msg) {
      logger.debug(msg);
   }

   public static void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }

   public static void debug(ROID id, String msg) {
      logger.debug(Thread.currentThread().getName() + " [roid:" + id + "] " + msg);
   }

   public static void debug(ROID id, String msg, Throwable t) {
      logger.debug(Thread.currentThread().getName() + " [roid:" + id + "] " + msg, t);
   }
}
