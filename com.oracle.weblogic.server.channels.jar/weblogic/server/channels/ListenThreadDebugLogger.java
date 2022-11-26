package weblogic.server.channels;

import weblogic.diagnostics.debug.DebugLogger;

class ListenThreadDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("ListenThreadDebug");

   static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   static void debug(String msg) {
      logger.debug(msg);
   }

   static void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }
}
