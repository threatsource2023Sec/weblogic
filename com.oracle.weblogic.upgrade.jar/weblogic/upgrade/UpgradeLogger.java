package weblogic.upgrade;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpgradeLogger extends Logger {
   Logger delegateLogger;

   UpgradeLogger(String name, Logger logger, String resourceBundleName) {
      super(name, resourceBundleName);
      this.delegateLogger = logger;
   }

   public void log(Level level, String msg) {
      this.logCauseActionThrow(level, msg, (Object[])null, (Throwable)null);
   }

   public void log(Level level, String msg, Throwable thrown) {
      this.logCauseActionThrow(level, msg, (Object[])null, thrown);
   }

   public void log(Level level, String msg, Object param1) {
      this.logCauseActionThrow(level, msg, new Object[]{param1}, (Throwable)null);
   }

   public void log(Level level, String msg, Object[] params) {
      this.logCauseActionThrow(level, msg, params, (Throwable)null);
   }

   public void log(Level level, String msg, Object param1, Throwable thrown) {
      this.logCauseActionThrow(level, msg, new Object[]{param1}, thrown);
   }

   public void log(Level level, String msg, Object[] params, Throwable thrown) {
      this.logCauseActionThrow(level, msg, params, thrown);
   }

   private void logCauseActionThrow(Level level, String inMsg, Object[] params, Throwable thrown) {
      if (level != Level.FINEST && level != Level.FINE) {
         ResourceBundle rb = this.getResourceBundle();
         if (rb != null) {
            try {
               String cause = rb.getString(inMsg + "-CAUSE");
               String action = rb.getString(inMsg + "-ACTION");
               String outMsg = "Cause: " + cause + "\nAction:" + action;
               this.delegateLogger.log(level, outMsg);
            } catch (Throwable var9) {
            }
         }

         if (thrown != null) {
            this.delegateLogger.log(level, "", thrown);
         }

      } else {
         if (thrown != null) {
            this.delegateLogger.log(level, "", thrown);
         }

      }
   }
}
