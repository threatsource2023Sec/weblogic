package com.bea.core.security.legacy.internal;

import java.util.Date;
import weblogic.diagnostics.debug.DebugLogger;

public class Logger {
   private static final boolean logToStdout = System.getProperty("com.bea.core.debug.DebugLegacy.stdout", "false").equals("true");
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugLegacy");
   private static Logger INSTANCE = new Logger();

   public static Logger logger() {
      return INSTANCE;
   }

   public void debug(String debugMe) {
      this.debug(debugMe, (Throwable)null);
   }

   private final void printThrowable(Throwable th) {
      int lcv = 0;

      for(Throwable cause = th; cause != null; cause = cause.getCause()) {
         System.out.println("[" + lcv++ + "]=" + cause.getMessage());
         cause.printStackTrace();
      }

   }

   public void debug(String debugMe, Throwable th) {
      if (logToStdout) {
         System.out.println("<LegacyDebug " + new Date(System.currentTimeMillis()) + ">" + debugMe);
      }

      if (th == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(debugMe);
         }

      } else {
         if (logToStdout) {
            this.printThrowable(th);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(debugMe, th);
         }

      }
   }

   public static boolean isDebugEnabled() {
      return logToStdout || debugLogger.isDebugEnabled();
   }
}
