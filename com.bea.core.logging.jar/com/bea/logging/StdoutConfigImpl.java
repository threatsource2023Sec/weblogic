package com.bea.logging;

import java.io.Serializable;
import java.util.logging.Filter;

public class StdoutConfigImpl implements StdoutConfig, Serializable {
   private static final long serialVersionUID = 358456108050885279L;
   private static final boolean DEBUG = false;
   private transient StdoutHandler stdoutHandler = LoggingService.getInstance().getStdoutHandler();
   private String stdoutFilterName;
   private LogFilterExpressionConfig stdoutFilter;

   public String getStdoutFilterName() {
      return this.stdoutFilterName;
   }

   public void setStdoutFilterName(String stdoutFilterName) {
      this.stdoutFilterName = stdoutFilterName;
   }

   StdoutHandler getStdoutHandler() {
      return this.stdoutHandler;
   }

   void setStdoutHandler(StdoutHandler stdoutHandler) {
      this.stdoutHandler = stdoutHandler;
   }

   public String getStdoutSeverity() {
      return this.stdoutHandler.getSeverity();
   }

   public void setStdoutSeverity(String severity) {
      this.stdoutHandler.setSeverity(severity);
   }

   public boolean isStackTraceEnabled() {
      return this.stdoutHandler.isStackTraceEnabled();
   }

   public void setStackTraceEnabled(boolean stack) {
      this.stdoutHandler.setStackTraceEnabled(stack);
   }

   public int getStackTraceDepth() {
      return this.stdoutHandler.getStackTraceDepth();
   }

   public void setStackTraceDepth(int depth) {
      this.stdoutHandler.setStackTraceDepth(depth);
   }

   public LogFilterExpressionConfig getStdoutFilter() {
      return this.stdoutFilter;
   }

   public void setStdoutFilter(LogFilterExpressionConfig stdoutFilter) {
      this.stdoutFilter = stdoutFilter;
      Filter f = null;
      if (stdoutFilter != null) {
         String filterExpression = stdoutFilter.getLogFilterExpression();
         LogFilterFactory ff = LogFilterFactory.getInstance();
         if (ff != null) {
            f = ff.createFilter(filterExpression);
         }
      } else {
         f = null;
      }

      this.stdoutHandler.setFilter(f);
   }
}
