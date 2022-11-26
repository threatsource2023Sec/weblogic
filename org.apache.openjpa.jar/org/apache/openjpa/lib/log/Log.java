package org.apache.openjpa.lib.log;

public interface Log {
   short TRACE = 1;
   short INFO = 3;
   short WARN = 4;
   short ERROR = 5;
   short FATAL = 6;

   boolean isTraceEnabled();

   boolean isInfoEnabled();

   boolean isWarnEnabled();

   boolean isErrorEnabled();

   boolean isFatalEnabled();

   void trace(Object var1);

   void trace(Object var1, Throwable var2);

   void info(Object var1);

   void info(Object var1, Throwable var2);

   void warn(Object var1);

   void warn(Object var1, Throwable var2);

   void error(Object var1);

   void error(Object var1, Throwable var2);

   void fatal(Object var1);

   void fatal(Object var1, Throwable var2);
}
