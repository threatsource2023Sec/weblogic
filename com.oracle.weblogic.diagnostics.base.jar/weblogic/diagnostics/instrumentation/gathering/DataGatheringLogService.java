package weblogic.diagnostics.instrumentation.gathering;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Handler;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.logging.JDKLoggerFactory;
import weblogic.logging.LoggingHelper;

final class DataGatheringLogService {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static Object logHandler = null;

   static void registerToServerLogger(int severity) {
      int registerSeverity = Math.max(severity, 64);
      if (JDKLoggerFactory.isLog4jEnabled()) {
         try {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("DataGatheringLogService.registerToServerLogger(" + severity + ") registering log4j appender with severity " + registerSeverity);
            }

            Class logHelperClass = Class.forName("weblogic.logging.log4j.Log4jLoggingHelper", true, Thread.currentThread().getContextClassLoader());
            Method loggerGetter = logHelperClass.getMethod("getLog4jServerLogger");
            Object logger = loggerGetter.invoke((Object)null);
            Class loggerClass = Class.forName("org.apache.log4j.Logger", true, Thread.currentThread().getContextClassLoader());
            Class appenderClass = Class.forName("weblogic.diagnostics.instrumentation.gathering.DataGatheringAppender", true, Thread.currentThread().getContextClassLoader());
            Constructor cons = appenderClass.getConstructor(loggerClass, Integer.TYPE);
            Object appender = cons.newInstance(logger, new Integer(registerSeverity));
            Method addAppMethod = loggerClass.getMethod("addAppender", Class.forName("org.apache.log4j.Appender", true, Thread.currentThread().getContextClassLoader()));
            addAppMethod.invoke(logger, appender);
            logHandler = appender;
            return;
         } catch (Exception var10) {
            DiagnosticsLogger.logErrorRegisteringLog4jDataGatheringAppender(var10);
         }
      } else {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("DataGatheringLogService.registerToServerLogger(" + severity + ") registering log handler with severity " + registerSeverity);
         }

         logHandler = new DataGatheringHandler(registerSeverity);
         LoggingHelper.getServerLogger().addHandler((Handler)logHandler);
      }

   }

   static void deregisterFromServerLogger() {
      if (debugLog.isDebugEnabled()) {
         debugLog.debug("DataGatheringLogService.deregisterFromServerLogger()");
      }

      if (logHandler != null) {
         if (JDKLoggerFactory.isLog4jEnabled()) {
            try {
               Class logHelperClass = Class.forName("weblogic.logging.log4j.Log4jLoggingHelper", true, Thread.currentThread().getContextClassLoader());
               Method loggerGetter = logHelperClass.getMethod("getLog4jServerLogger");
               Object logger = loggerGetter.invoke((Object)null);
               Class loggerClass = Class.forName("org.apache.log4j.Logger", true, Thread.currentThread().getContextClassLoader());
               Method removeAppMethod = loggerClass.getMethod("removeAppender", Class.forName("org.apache.log4j.Appender", true, Thread.currentThread().getContextClassLoader()));
               removeAppMethod.invoke(logger, logHandler);
            } catch (Exception var5) {
               DiagnosticsLogger.logErrorRegisteringLog4jDataGatheringAppender(var5);
            }
         } else {
            LoggingHelper.getServerLogger().removeHandler((Handler)logHandler);
         }

      }
   }
}
