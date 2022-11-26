package org.python.netty.util.internal.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class JdkLogger extends AbstractInternalLogger {
   private static final long serialVersionUID = -1767272577989225979L;
   final transient Logger logger;
   static final String SELF = JdkLogger.class.getName();
   static final String SUPER = AbstractInternalLogger.class.getName();

   JdkLogger(Logger logger) {
      super(logger.getName());
      this.logger = logger;
   }

   public boolean isTraceEnabled() {
      return this.logger.isLoggable(Level.FINEST);
   }

   public void trace(String msg) {
      if (this.logger.isLoggable(Level.FINEST)) {
         this.log(SELF, Level.FINEST, msg, (Throwable)null);
      }

   }

   public void trace(String format, Object arg) {
      if (this.logger.isLoggable(Level.FINEST)) {
         FormattingTuple ft = MessageFormatter.format(format, arg);
         this.log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
      }

   }

   public void trace(String format, Object argA, Object argB) {
      if (this.logger.isLoggable(Level.FINEST)) {
         FormattingTuple ft = MessageFormatter.format(format, argA, argB);
         this.log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
      }

   }

   public void trace(String format, Object... argArray) {
      if (this.logger.isLoggable(Level.FINEST)) {
         FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
         this.log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
      }

   }

   public void trace(String msg, Throwable t) {
      if (this.logger.isLoggable(Level.FINEST)) {
         this.log(SELF, Level.FINEST, msg, t);
      }

   }

   public boolean isDebugEnabled() {
      return this.logger.isLoggable(Level.FINE);
   }

   public void debug(String msg) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.log(SELF, Level.FINE, msg, (Throwable)null);
      }

   }

   public void debug(String format, Object arg) {
      if (this.logger.isLoggable(Level.FINE)) {
         FormattingTuple ft = MessageFormatter.format(format, arg);
         this.log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
      }

   }

   public void debug(String format, Object argA, Object argB) {
      if (this.logger.isLoggable(Level.FINE)) {
         FormattingTuple ft = MessageFormatter.format(format, argA, argB);
         this.log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
      }

   }

   public void debug(String format, Object... argArray) {
      if (this.logger.isLoggable(Level.FINE)) {
         FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
         this.log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
      }

   }

   public void debug(String msg, Throwable t) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.log(SELF, Level.FINE, msg, t);
      }

   }

   public boolean isInfoEnabled() {
      return this.logger.isLoggable(Level.INFO);
   }

   public void info(String msg) {
      if (this.logger.isLoggable(Level.INFO)) {
         this.log(SELF, Level.INFO, msg, (Throwable)null);
      }

   }

   public void info(String format, Object arg) {
      if (this.logger.isLoggable(Level.INFO)) {
         FormattingTuple ft = MessageFormatter.format(format, arg);
         this.log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
      }

   }

   public void info(String format, Object argA, Object argB) {
      if (this.logger.isLoggable(Level.INFO)) {
         FormattingTuple ft = MessageFormatter.format(format, argA, argB);
         this.log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
      }

   }

   public void info(String format, Object... argArray) {
      if (this.logger.isLoggable(Level.INFO)) {
         FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
         this.log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
      }

   }

   public void info(String msg, Throwable t) {
      if (this.logger.isLoggable(Level.INFO)) {
         this.log(SELF, Level.INFO, msg, t);
      }

   }

   public boolean isWarnEnabled() {
      return this.logger.isLoggable(Level.WARNING);
   }

   public void warn(String msg) {
      if (this.logger.isLoggable(Level.WARNING)) {
         this.log(SELF, Level.WARNING, msg, (Throwable)null);
      }

   }

   public void warn(String format, Object arg) {
      if (this.logger.isLoggable(Level.WARNING)) {
         FormattingTuple ft = MessageFormatter.format(format, arg);
         this.log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
      }

   }

   public void warn(String format, Object argA, Object argB) {
      if (this.logger.isLoggable(Level.WARNING)) {
         FormattingTuple ft = MessageFormatter.format(format, argA, argB);
         this.log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
      }

   }

   public void warn(String format, Object... argArray) {
      if (this.logger.isLoggable(Level.WARNING)) {
         FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
         this.log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
      }

   }

   public void warn(String msg, Throwable t) {
      if (this.logger.isLoggable(Level.WARNING)) {
         this.log(SELF, Level.WARNING, msg, t);
      }

   }

   public boolean isErrorEnabled() {
      return this.logger.isLoggable(Level.SEVERE);
   }

   public void error(String msg) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         this.log(SELF, Level.SEVERE, msg, (Throwable)null);
      }

   }

   public void error(String format, Object arg) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         FormattingTuple ft = MessageFormatter.format(format, arg);
         this.log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
      }

   }

   public void error(String format, Object argA, Object argB) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         FormattingTuple ft = MessageFormatter.format(format, argA, argB);
         this.log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
      }

   }

   public void error(String format, Object... arguments) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
         this.log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
      }

   }

   public void error(String msg, Throwable t) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         this.log(SELF, Level.SEVERE, msg, t);
      }

   }

   private void log(String callerFQCN, Level level, String msg, Throwable t) {
      LogRecord record = new LogRecord(level, msg);
      record.setLoggerName(this.name());
      record.setThrown(t);
      fillCallerData(callerFQCN, record);
      this.logger.log(record);
   }

   private static void fillCallerData(String callerFQCN, LogRecord record) {
      StackTraceElement[] steArray = (new Throwable()).getStackTrace();
      int selfIndex = -1;

      int found;
      for(found = 0; found < steArray.length; ++found) {
         String className = steArray[found].getClassName();
         if (className.equals(callerFQCN) || className.equals(SUPER)) {
            selfIndex = found;
            break;
         }
      }

      found = -1;

      for(int i = selfIndex + 1; i < steArray.length; ++i) {
         String className = steArray[i].getClassName();
         if (!className.equals(callerFQCN) && !className.equals(SUPER)) {
            found = i;
            break;
         }
      }

      if (found != -1) {
         StackTraceElement ste = steArray[found];
         record.setSourceClassName(ste.getClassName());
         record.setSourceMethodName(ste.getMethodName());
      }

   }
}
