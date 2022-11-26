package weblogic.security.utils;

import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import weblogic.security.shared.LoggerWrapper;

public class SSLSetupLogging {
   public static final int DEBUG_FATAL = 0;
   public static final int DEBUG_ERROR = 1;
   public static final int DEBUG_WARN = 2;
   public static final int DEBUG_INFO = 3;
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecuritySSL");
   private static LoggerWrapper EATENLOGGER = LoggerWrapper.getInstance("SecuritySSLEaten");
   private static int debugLevel = 0;

   public static boolean getDebugEaten() {
      return EATENLOGGER.isDebugEnabled();
   }

   public static final boolean isDebugEnabled() {
      return LOGGER.isDebugEnabled();
   }

   public static final boolean isDebugEnabled(int level) {
      return LOGGER.isDebugEnabled();
   }

   public static final void info(String message) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(message);
      }

   }

   public static final void info(Throwable ex, String message) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(message, ex);
      }

   }

   public static final void debug(int level, String message) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(message);
      }

   }

   public static final void debug(int level, boolean showStack, String message) {
      if (LOGGER.isDebugEnabled()) {
         if (showStack) {
            Throwable th = new Throwable("Stack trace");
            LOGGER.debug(message, th);
         } else {
            LOGGER.debug(message);
         }

      }
   }

   public static final void debug(int level, Throwable ex, String message) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(message, ex);
      }

   }

   protected static synchronized void debug(String message, Throwable ex) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(message, ex);
      }

   }

   public static boolean logSSLRejections() {
      return true;
   }

   public static void debugPrivateKey(PrivateKey theKey) {
      if (isDebugEnabled()) {
         String pkInfo = "Private key dump\n   Key info: " + theKey;
         if (theKey instanceof RSAPrivateCrtKey) {
            pkInfo = pkInfo + "   is a java.security.interfaces.RSAPrivateCrtKey";
         } else if (theKey instanceof RSAPrivateKey) {
            pkInfo = pkInfo + "   is a java.security.interfaces.RSAPrivateKey";
         }

         debug(3, pkInfo);
      }

   }
}
