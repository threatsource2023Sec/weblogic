package weblogic.ldap;

import com.octetstring.vde.util.ExternalLogger;
import com.octetstring.vde.util.Logger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.LogOutputStream;
import weblogic.utils.StackTraceUtils;

public class EmbeddedLDAPServiceLogger implements ExternalLogger {
   private static final LogOutputStream logOut = new LogOutputStream("EmbeddedLDAP");
   private static final DebugLogger log = DebugLogger.getDebugLogger("DebugEmbeddedLDAP");
   private Logger logger = Logger.getInstance();

   public EmbeddedLDAPServiceLogger() {
      this.logger.setExternalLogger(this);
   }

   public void log(int level, String Component, String message) {
      switch (level) {
         case 0:
            logOut.error(message);
            break;
         case 1:
         case 2:
         case 4:
         case 5:
         case 6:
         case 8:
         case 10:
         default:
            logOut.info(message);
            break;
         case 3:
            logOut.warning(message);
            break;
         case 7:
         case 9:
         case 11:
            log.debug(message);
      }

   }

   public static DebugLogger getDebugLogger() {
      return log;
   }

   public static LogOutputStream getLogOutputStream() {
      return logOut;
   }

   public Logger getLogger() {
      return this.logger;
   }

   public void printStackTrace(Throwable t) {
      logOut.critical(StackTraceUtils.throwable2StackTrace(t));
   }

   public void printStackTraceLog(Throwable t) {
      logOut.critical(StackTraceUtils.throwable2StackTrace(t));
   }

   public void printStackTraceConsole(Throwable t) {
      logOut.critical(StackTraceUtils.throwable2StackTrace(t));
   }
}
