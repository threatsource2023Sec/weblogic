package weblogic.security.debug;

import weblogic.diagnostics.debug.DebugLogger;

public class SecurityDebugLogger implements SecurityLogger {
   private DebugLogger debug;

   public SecurityDebugLogger(String loggerName) {
      this.debug = DebugLogger.getDebugLogger(loggerName);
   }

   public final boolean isDebugEnabled() {
      return this.debug.isDebugEnabled();
   }

   public void debug(String s) {
      if (this.isDebugEnabled()) {
         this.debug.debug(s);
      }

   }

   public void debug(String s, Exception e) {
      if (this.isDebugEnabled()) {
         this.debug.debug(s, e);
      }

   }
}
