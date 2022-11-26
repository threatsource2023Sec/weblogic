package weblogic.diagnostics.debug;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public final class DebugLoggerRepository {
   static final String DEFAULT_CMDLINE_OVERRIDE_PREFIX = "weblogic.debug.";
   private Map debugLoggers = new ConcurrentHashMap();
   private Logger jdkLogger = createAndInitAnonymousLogger();
   private String cmdlineOveridePrifix;
   private Properties debugLoggerParameters = new Properties();

   DebugLoggerRepository() {
      try {
         this.cmdlineOveridePrifix = System.getProperty("weblogic.diagnostics.debug.DefaultCommandLinePrefix", "weblogic.debug.");
      } catch (Exception var2) {
         this.cmdlineOveridePrifix = "weblogic.debug.";
      }

   }

   DebugLoggerRepository(String propPrefix) {
      this.cmdlineOveridePrifix = propPrefix;
   }

   public Logger getLogger() {
      return this.jdkLogger;
   }

   public synchronized void setLogger(Logger l) {
      this.jdkLogger = l;
   }

   public synchronized DebugLogger getDebugLogger(String loggerName) {
      DebugLogger logger = (DebugLogger)this.debugLoggers.get(loggerName);
      if (logger == null) {
         logger = new DebugLogger(loggerName, this);
         this.debugLoggers.put(loggerName, logger);

         try {
            boolean debugEnabled = Boolean.getBoolean(this.cmdlineOveridePrifix + loggerName);
            logger.setDebugEnabled(debugEnabled);
         } catch (SecurityException var4) {
         }
      }

      return logger;
   }

   private static Logger createAndInitAnonymousLogger() {
      Logger logger = Logger.getAnonymousLogger();
      logger.setUseParentHandlers(false);
      logger.setLevel(Level.FINE);
      StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter()) {
         public void publish(LogRecord lr) {
            super.publish(lr);
            super.flush();
         }

         public void close() {
            super.flush();
         }
      };

      try {
         handler.setLevel(Level.FINE);
      } catch (SecurityException var3) {
      }

      logger.addHandler(handler);
      return logger;
   }

   public Properties getDebugLoggerParameters() {
      return this.debugLoggerParameters;
   }

   public void setDebugLoggerParameters(Properties params) {
      this.debugLoggerParameters = params;
      Iterator var2 = this.debugLoggers.values().iterator();

      Object key;
      while(var2.hasNext()) {
         key = var2.next();
         DebugLogger dl = (DebugLogger)key;
         dl.getDebugParameters().clear();
      }

      if (this.debugLoggerParameters != null) {
         var2 = this.debugLoggerParameters.keySet().iterator();

         while(var2.hasNext()) {
            key = var2.next();
            if (key != null) {
               String keyName = key.toString();
               int index = keyName.indexOf(46);
               if (index >= 0) {
                  String debugLoggerName = keyName.substring(0, index);
                  DebugLogger dl = this.getDebugLogger(debugLoggerName);
                  if (keyName.length() > index + 1) {
                     String paramName = keyName.substring(index + 1);
                     String value = this.debugLoggerParameters.get(key).toString();
                     dl.getDebugParameters().put(paramName, value);
                  }
               }
            }
         }

      }
   }
}
