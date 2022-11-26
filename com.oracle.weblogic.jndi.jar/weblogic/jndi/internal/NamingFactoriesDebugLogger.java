package weblogic.jndi.internal;

import weblogic.diagnostics.debug.DebugLogger;

public final class NamingFactoriesDebugLogger {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugJNDIFactories");

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
