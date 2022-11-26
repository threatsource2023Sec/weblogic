package com.bea.security.xacml;

public interface Logger {
   boolean isDebugEnabled();

   void debug(Object var1);

   void debug(Object var1, Throwable var2);

   void info(Object var1);

   void info(Object var1, Throwable var2);

   void warn(Object var1);

   void warn(Object var1, Throwable var2);

   void error(Object var1);

   void error(Object var1, Throwable var2);

   void severe(Object var1);

   void severe(Object var1, Throwable var2);
}
