package weblogic.jndi.internal;

import weblogic.diagnostics.debug.DebugLogger;

public class NamingDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugJNDI");

   public static final boolean isDebugEnabled() {
      return logger.isDebugEnabled();
   }

   public static final void debug(String msg) {
      logger.debug(msg);
   }

   public static final void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }

   public static final void debugIfEnable(String msg) {
      if (isDebugEnabled()) {
         logger.debug(msg);
      }

   }
}
