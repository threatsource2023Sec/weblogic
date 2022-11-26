package com.bea.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageDispatcher;

public class LoggerWrapper implements MessageDispatcher {
   private String name;
   private Logger loggerRef = null;

   public LoggerWrapper(String name) {
      this.name = name;
      this.loggerRef = LoggingService.getInstance().getLogger(name);
   }

   public String getName() {
      return this.name;
   }

   public boolean isSeverityEnabled(int severity) {
      Level level = LogLevel.getLevel(severity);
      return this.loggerRef.isLoggable(level);
   }

   public void log(String subsystem, int severityLevel, String message) {
      this.log(subsystem, severityLevel, message, (Throwable)null);
   }

   public void log(String subsystem, int severityLevel, String message, Throwable throwable) {
      this.log(new LogMessage((String)null, (String)null, subsystem, severityLevel, message, throwable));
   }

   public void log(LogMessage logMessage) {
      BaseLogRecordFactory b = LoggingService.getInstance().getBaseLogRecordFactory();
      BaseLogRecord rec = b.createBaseLogRecord(logMessage);
      this.loggerRef.log(rec);
   }

   public int getSeverity() {
      Level level = this.loggerRef.getLevel();
      if (level == null) {
         level = LogLevel.INFO;
      }

      return LogLevel.getSeverity((Level)level);
   }

   public void setSeverity(int severity) {
      Level level = LogLevel.getLevel(severity);
      this.loggerRef.setLevel(level);
   }
}
