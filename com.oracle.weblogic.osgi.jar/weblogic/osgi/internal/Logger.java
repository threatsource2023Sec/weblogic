package weblogic.osgi.internal;

import java.util.Date;
import weblogic.diagnostics.debug.DebugLogger;

public class Logger {
   private static final boolean logToStdout = System.getProperty("weblogic.osgi.debug.DebugOSGi.stdout", "false").equals("true");
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("OSGiForApps");
   private static final Logger INSTANCE = new Logger();

   public static Logger getLogger() {
      return INSTANCE;
   }

   public static boolean isDebugEnabled() {
      return logToStdout || debugLogger.isDebugEnabled();
   }

   public void debug(String msg) {
      this.debug(msg, (Throwable)null);
   }

   public void debug(String msg, Throwable th) {
      if (logToStdout) {
         System.out.println("<OSGiForAppsDebug " + new Date(System.currentTimeMillis()) + ">" + msg);
      }

      if (th == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(msg);
         }

      } else {
         if (logToStdout) {
            Throwable cause = th;

            for(int lcv = 0; cause != null; cause = cause.getCause()) {
               System.out.println("<OSGiForAppsDebug " + new Date(System.currentTimeMillis()) + " exception-level=\"" + lcv++ + "\" message=\"" + cause.getMessage() + "\">");
               cause.printStackTrace();
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(msg, th);
         }

      }
   }

   public static void printThrowable(Throwable th) {
      for(int lcv = 0; th != null; th = th.getCause()) {
         System.out.println("[" + lcv++ + "]=" + th.getMessage());
         th.printStackTrace();
      }

   }
}
