package weblogic.diagnostics.watch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Handler;
import java.util.logging.Logger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.utils.LogEventRulesEvaluator;
import weblogic.logging.JDKLoggerFactory;
import weblogic.logging.LoggerNotAvailableException;
import weblogic.logging.LoggingHelper;

final class WatchLogService {
   static void registerToDomainLogger(LogEventRulesEvaluator evaluator, int severity) throws LoggerNotAvailableException {
      registerToLogger(evaluator, severity, 4);
   }

   static void registerToServerLogger(LogEventRulesEvaluator evaluator, int severity) throws LoggerNotAvailableException {
      registerToLogger(evaluator, severity, 1);
   }

   private static void registerToLogger(LogEventRulesEvaluator evaluator, int severity, int logRuleType) throws LoggerNotAvailableException {
      if (JDKLoggerFactory.isLog4jEnabled()) {
         try {
            Class logHelperClass = Class.forName("weblogic.logging.log4j.Log4jLoggingHelper", true, Thread.currentThread().getContextClassLoader());
            String methodName = logRuleType == 4 ? "getLog4jDomainLogger" : "getLog4jServerLogger";
            Method loggerGetter = logHelperClass.getMethod(methodName);
            Object logger = loggerGetter.invoke((Object)null);
            Class loggerClass = Class.forName("org.apache.log4j.Logger", true, Thread.currentThread().getContextClassLoader());
            Class appenderClass = Class.forName("weblogic.diagnostics.watch.WatchLogAppender", true, Thread.currentThread().getContextClassLoader());
            Constructor cons = appenderClass.getConstructor(loggerClass, LogEventRulesEvaluator.class, Integer.TYPE, Integer.TYPE);
            Object appender = cons.newInstance(logger, evaluator, severity, logRuleType);
            Method addAppMethod = loggerClass.getMethod("addAppender", Class.forName("org.apache.log4j.Appender", true, Thread.currentThread().getContextClassLoader()));
            addAppMethod.invoke(logger, appender);
            return;
         } catch (Exception var12) {
            DiagnosticsLogger.logWatchErrorInvokingLog4j(var12);
         }
      } else {
         WatchLogHandler logHandler = new WatchLogHandler(evaluator, severity, logRuleType);
         Logger logger = logRuleType == 4 ? LoggingHelper.getDomainLogger() : LoggingHelper.getServerLogger();
         logger.addHandler(logHandler);
      }

   }

   static void deregisterFromServerLogger() throws LoggerNotAvailableException {
      deregisterFromLogger(1);
   }

   static void deregisterFromDomainLogger() throws LoggerNotAvailableException {
      deregisterFromLogger(4);
   }

   private static void deregisterFromLogger(int logRuleType) throws LoggerNotAvailableException {
      if (JDKLoggerFactory.isLog4jEnabled()) {
         try {
            Class logHelperClass = Class.forName("weblogic.logging.log4j.Log4jLoggingHelper", true, Thread.currentThread().getContextClassLoader());
            String methodName = logRuleType == 4 ? "getLog4jDomainLogger" : "getLog4jServerLogger";
            Method loggerGetter = logHelperClass.getMethod(methodName);
            Object logger = loggerGetter.invoke((Object)null);
            Class loggerClass = Class.forName("org.apache.log4j.Logger", true, Thread.currentThread().getContextClassLoader());
            Method getAppender = loggerClass.getMethod("getAppender", String.class);
            Object logAppender = getAppender.invoke(logger, "WatchLogAppender");
            Method removeAppMethod = loggerClass.getMethod("removeAppender", Class.forName("org.apache.log4j.Appender", true, Thread.currentThread().getContextClassLoader()));
            removeAppMethod.invoke(logger, logAppender);
         } catch (Exception var9) {
            DiagnosticsLogger.logWatchErrorInvokingLog4j(var9);
         }
      } else {
         Logger logger = logRuleType == 4 ? LoggingHelper.getDomainLogger() : LoggingHelper.getServerLogger();
         Handler watchLogHandler = null;
         Handler[] var12 = logger.getHandlers();
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            Handler h = var12[var14];
            if (h.getClass().equals(WatchLogHandler.class)) {
               watchLogHandler = h;
            }
         }

         if (watchLogHandler != null) {
            logger.removeHandler(watchLogHandler);
         }
      }

   }
}
