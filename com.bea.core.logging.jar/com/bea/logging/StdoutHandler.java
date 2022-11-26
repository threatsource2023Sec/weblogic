package com.bea.logging;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import weblogic.i18n.logging.Severities;

public class StdoutHandler extends StreamHandler {
   private LogMessageFormatter logMsgFormatter = (LogMessageFormatter)super.getFormatter();
   private String severity = "Notice";

   public StdoutHandler() {
      super(System.out, new LogMessageFormatter());
      this.setLevel(LogLevel.NOTICE);
   }

   public synchronized void publish(LogRecord rec) {
      super.publish(rec);
      super.flush();
   }

   public void close() {
      super.flush();
   }

   public boolean isStackTraceEnabled() {
      return this.logMsgFormatter.isStackTraceEnabled();
   }

   public void setStackTraceEnabled(boolean stack) {
      this.logMsgFormatter.setStackTraceEnabled(stack);
   }

   public int getStackTraceDepth() {
      return this.logMsgFormatter.getStackTraceDepth();
   }

   public void setStackTraceDepth(int depth) {
      this.logMsgFormatter.setStackTraceDepth(depth);
   }

   public String getSeverity() {
      return this.severity;
   }

   public void setSeverity(String severity) {
      this.setLevel(LogLevel.getLevel(Severities.severityStringToNum(severity)));
      this.severity = severity;
   }
}
