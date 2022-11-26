package com.bea.core.repackaged.aspectj.weaver.tools;

public interface Trace {
   void enter(String var1, Object var2, Object[] var3);

   void enter(String var1, Object var2);

   void exit(String var1, Object var2);

   void exit(String var1, Throwable var2);

   void exit(String var1);

   void event(String var1);

   void event(String var1, Object var2, Object[] var3);

   void debug(String var1);

   void info(String var1);

   void warn(String var1);

   void warn(String var1, Throwable var2);

   void error(String var1);

   void error(String var1, Throwable var2);

   void fatal(String var1);

   void fatal(String var1, Throwable var2);

   void enter(String var1, Object var2, Object var3);

   void enter(String var1, Object var2, boolean var3);

   void exit(String var1, boolean var2);

   void exit(String var1, int var2);

   void event(String var1, Object var2, Object var3);

   boolean isTraceEnabled();

   void setTraceEnabled(boolean var1);
}
