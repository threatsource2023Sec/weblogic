package weblogic.security.shared;

import java.util.HashMap;

public final class LoggerWrapper {
   Object logger = null;
   private static RuntimeUtilities runtimeUtilities = null;
   private static HashMap wrappers = new HashMap();
   private static LoggerWrapper nullWrapper = null;
   private static LoggerAdapter adapter = null;

   private LoggerWrapper() {
   }

   private LoggerWrapper(Object theLogger) {
      this.logger = theLogger;
   }

   public static synchronized LoggerWrapper getInstance(Class clazz) throws IllegalStateException {
      return clazz != null ? getInstance(clazz.getName()) : getInstance((String)null);
   }

   public static synchronized LoggerWrapper getInstance(String loggerName) throws IllegalStateException {
      if (adapter == null) {
         runtimeUtilities = RuntimeEnvironment.getRuntimeUtilities();
         adapter = runtimeUtilities.getLoggerAdapter();
      }

      if (loggerName != null) {
         LoggerWrapper theWrapper = (LoggerWrapper)wrappers.get(loggerName);
         if (theWrapper != null) {
            return theWrapper;
         } else {
            theWrapper = new LoggerWrapper(adapter.getLogger(loggerName));
            wrappers.put(loggerName, theWrapper);
            return theWrapper;
         }
      } else {
         if (nullWrapper == null) {
            nullWrapper = new LoggerWrapper(adapter.getLogger((String)null));
         }

         return nullWrapper;
      }
   }

   public boolean isDebugEnabled() {
      return adapter != null ? adapter.isDebugEnabled(this.logger) : false;
   }

   public void debug(Object msg) {
      if (adapter != null) {
         adapter.debug(this.logger, msg);
      }

   }

   public void debug(Object msg, Throwable th) {
      if (adapter != null) {
         adapter.debug(this.logger, msg, th);
      }

   }

   public void info(Object msg) {
      if (adapter != null) {
         adapter.info(this.logger, msg);
      }

   }

   public void info(Object msg, Throwable th) {
      if (adapter != null) {
         adapter.info(this.logger, msg, th);
      }

   }

   public void warn(Object msg) {
      if (adapter != null) {
         adapter.warn(this.logger, msg);
      }

   }

   public void warn(Object msg, Throwable th) {
      if (adapter != null) {
         adapter.warn(this.logger, msg, th);
      }

   }

   public void error(Object msg) {
      if (adapter != null) {
         adapter.error(this.logger, msg);
      }

   }

   public void error(Object msg, Throwable th) {
      if (adapter != null) {
         adapter.error(this.logger, msg, th);
      }

   }

   public void severe(Object msg) {
      if (adapter != null) {
         adapter.severe(this.logger, msg);
      }

   }

   public void severe(Object msg, Throwable th) {
      if (adapter != null) {
         adapter.severe(this.logger, msg, th);
      }

   }
}
