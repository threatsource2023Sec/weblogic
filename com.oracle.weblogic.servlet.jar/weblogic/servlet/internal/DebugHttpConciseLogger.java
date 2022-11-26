package weblogic.servlet.internal;

import weblogic.diagnostics.debug.DebugLogger;

public final class DebugHttpConciseLogger {
   public static final DebugLogger DEBUG_HTTP_CONCISE = DebugLogger.getDebugLogger("DebugHttpConcise");

   public static final boolean isEnabled() {
      return DEBUG_HTTP_CONCISE.isDebugEnabled();
   }

   public static final void debug(String msg) {
      DEBUG_HTTP_CONCISE.debug(msg);
   }

   public static final void debug(String msg, Exception e) {
      DEBUG_HTTP_CONCISE.debug(msg, e);
   }
}
