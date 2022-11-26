package com.bea.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BaseLogger extends Logger {
   private static final int EMPTY_SRC_CLASS_METHOD_LEN = "[:] ".length();
   private int severity = -1;
   private static final SimpleFormatter SF = new SimpleFormatter();
   private static final String passwordTypeStartFlag = "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">";
   private static final String passwordTypeEndFlag = "</wsse:Password>";
   private static final String asterisks = "******";
   private static final String httpAdapterLoggerName = "com.sun.xml.ws.transport.http.HttpAdapter";

   public BaseLogger(String name) {
      super(name, (String)null);
      super.setFilter(new ParentDelegatingFilter(this));
   }

   public int getSeverity() {
      return this.severity;
   }

   public void setSeverity(int severity) {
      this.severity = severity;
   }

   public void setLevel(Level level) {
      super.setLevel(level);
      if (level != null) {
         this.severity = LogLevel.getSeverity(level);
      } else {
         this.severity = -1;
      }

   }

   public void log(LogRecord record) {
      if (!(record instanceof BaseLogRecord)) {
         int severity = LogLevel.getSeverity(((LogRecord)record).getLevel());
         String msg = formatMessage((LogRecord)record);
         Throwable th = ((LogRecord)record).getThrown();
         BaseLogRecord blr = new BaseLogRecord(severity, msg, th);
         blr.setLoggerName(((LogRecord)record).getLoggerName());
         blr.setThreadName("ThreadId:" + ((LogRecord)record).getThreadID());
         record = blr;
      }

      if (((LogRecord)record).getLevel().intValue() <= Level.FINE.intValue() && "com.sun.xml.ws.transport.http.HttpAdapter".equals(((LogRecord)record).getLoggerName())) {
         StringBuffer msgBuf = new StringBuffer(((LogRecord)record).getMessage());
         replacePlainPassword(msgBuf);
         ((LogRecord)record).setMessage(msgBuf.toString());
      }

      super.log((LogRecord)record);
   }

   protected static String formatMessage(LogRecord record) {
      String msg = SF.formatMessage(record);
      int levelValue = record.getLevel().intValue();
      if (levelValue <= Level.FINE.intValue()) {
         String srcClass = record.getSourceClassName();
         String srcMethod = record.getSourceMethodName();
         StringBuilder buf = new StringBuilder();
         buf.append("[");
         buf.append(srcClass != null ? srcClass : "");
         buf.append(":");
         buf.append(srcMethod != null ? srcMethod : "");
         buf.append("] ");
         if (buf.length() > EMPTY_SRC_CLASS_METHOD_LEN) {
            buf.append(msg);
            msg = buf.toString();
         }
      }

      return msg;
   }

   private static void replacePlainPassword(StringBuffer target) {
      int passwordTypeStartFlagLen = "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">".length();
      int passwordTypeEndFlagLen = "</wsse:Password>".length();
      int asterisksLen = "******".length();

      for(int index = target.indexOf("<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"); index != -1; index = target.indexOf("<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">", index + passwordTypeStartFlagLen + asterisksLen + passwordTypeEndFlagLen)) {
         int end = target.indexOf("</wsse:Password>", index + passwordTypeStartFlagLen);
         target.replace(index + passwordTypeStartFlagLen, end, "******");
      }

   }
}
