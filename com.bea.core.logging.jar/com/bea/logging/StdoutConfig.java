package com.bea.logging;

public interface StdoutConfig {
   String getStdoutSeverity();

   void setStdoutSeverity(String var1);

   boolean isStackTraceEnabled();

   void setStackTraceEnabled(boolean var1);

   int getStackTraceDepth();

   void setStackTraceDepth(int var1);

   LogFilterExpressionConfig getStdoutFilter();

   void setStdoutFilter(LogFilterExpressionConfig var1);

   String getStdoutFilterName();

   void setStdoutFilterName(String var1);
}
