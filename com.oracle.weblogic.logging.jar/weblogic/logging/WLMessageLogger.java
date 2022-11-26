package weblogic.logging;

import com.bea.logging.LogLevel;
import java.util.logging.Level;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.kernel.KernelLogManager;

public class WLMessageLogger implements weblogic.i18n.logging.MessageLogger, MessageDispatcher {
   public boolean isSeverityEnabled(String subSystem, int messageSeverity) {
      return this.isSeverityEnabled(messageSeverity);
   }

   public void log(String subsystem, int severityLevel, String message) {
      this.log(subsystem, severityLevel, message, (Throwable)null);
   }

   public void log(String subsystem, int severityLevel, String message, Throwable throwable) {
      MessageLogger.log(new LogMessage((String)null, (String)null, subsystem, severityLevel, message, throwable));
   }

   public void log(LogMessage logMessage) {
      MessageLogger.log(logMessage);
   }

   public MessageDispatcher getMessageDispatcher(String name) {
      return this;
   }

   public String getName() {
      return "";
   }

   public boolean isSeverityEnabled(int severity) {
      Level level = WLLevel.getLevel(severity);
      return KernelLogManager.getLogger().isLoggable(level);
   }

   public int getSeverity() {
      Level level = KernelLogManager.getLogger().getLevel();
      if (level == null) {
         level = Level.INFO;
      }

      return LogLevel.getSeverity(level);
   }

   public void setSeverity(int severity) {
      KernelLogManager.getLogger().setLevel(LogLevel.getLevel(severity));
   }
}
