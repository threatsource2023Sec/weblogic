package weblogic.security.SSL.jsseadapter;

import java.text.MessageFormat;
import java.util.logging.Level;
import weblogic.security.utils.SSLSetupLogging;

final class JaLogger {
   private static final String LOGGER_NAME = JaLogger.class.getPackage().getName();

   static boolean isLoggable(Level level) {
      return SSLSetupLogging.isDebugEnabled();
   }

   static void log(Level level, Component component, String msg, Object... params) {
      log(level, component, (Throwable)null, msg, params);
   }

   static void log(Level level, Component component, Throwable throwable, String msg, Object... params) {
      if (isLoggable(level)) {
         if (null == component) {
            component = JaLogger.Component.UNKNOWN;
         }

         if (null != msg) {
            msg = MessageFormat.format(msg, params);
         } else {
            msg = "";
         }

         String PATTERN = "[{0}]{1}: {2}: {3}";
         String formattedMsg = MessageFormat.format("[{0}]{1}: {2}: {3}", Thread.currentThread().toString(), LOGGER_NAME, component, msg);
         if (null != throwable) {
            SSLSetupLogging.debug(toSSLSetupLoggingLevel(level), throwable, formattedMsg);
         } else {
            SSLSetupLogging.debug(toSSLSetupLoggingLevel(level), formattedMsg);
         }

      }
   }

   private static int toSSLSetupLoggingLevel(Level level) {
      if (null == level) {
         throw new IllegalArgumentException("Illegal log level: null");
      } else if (level == Level.OFF) {
         throw new IllegalArgumentException("Illegal log level: " + Level.OFF);
      } else if (level == Level.SEVERE) {
         return 0;
      } else if (level == Level.WARNING) {
         return 2;
      } else if (level == Level.INFO) {
         return 3;
      } else if (level == Level.CONFIG) {
         return 3;
      } else if (level == Level.FINE) {
         return 1;
      } else if (level == Level.FINER) {
         return 2;
      } else if (level == Level.FINEST) {
         return 3;
      } else if (level == Level.ALL) {
         throw new IllegalArgumentException("Illegal log level: " + Level.ALL);
      } else {
         return 2;
      }
   }

   static enum Component {
      KEYSTORE,
      KEYSTORE_MANAGER,
      TRUSTSTORE,
      TRUSTSTORE_MANAGER,
      SSLCONTEXT,
      SSLENGINE,
      SSLSERVERSOCKETFACTORY,
      SSLSERVERSOCKET,
      NIOSSLSERVERSOCKET,
      SSLSOCKETFACTORY,
      SSLSOCKET,
      UNKNOWN;
   }
}
