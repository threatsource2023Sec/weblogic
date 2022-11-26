package weblogic.security.pki.keystore;

import com.bea.security.utils.keystore.KeyStoreLogger;
import java.util.logging.Level;
import weblogic.security.SecurityLogger;
import weblogic.security.shared.LoggerWrapper;

final class WLSKeyStoreLogger extends KeyStoreLogger {
   private static final WLSKeyStoreLogger IMPL = new WLSKeyStoreLogger();
   private static final LoggerWrapper WLS_LOGGER = LoggerWrapper.getInstance("SecurityKeyStore");

   static WLSKeyStoreLogger getInstance() {
      return IMPL;
   }

   private WLSKeyStoreLogger() {
   }

   public boolean isLoggable(Level level) {
      return WLS_LOGGER.isDebugEnabled();
   }

   public void log(Level level, Throwable throwable, String msg, Object... params) {
      if (this.isLoggable(level)) {
         String formattedMsg = formatMessage(msg, params);
         if (null != throwable) {
            if (WLS_LOGGER.isDebugEnabled()) {
               WLS_LOGGER.debug(formattedMsg, throwable);
            }
         } else if (WLS_LOGGER.isDebugEnabled()) {
            WLS_LOGGER.debug(formattedMsg);
         }

      }
   }

   public void logBadKeyStoreSource(String type, String source, String exceptionName, String exceptionMessage) {
      SecurityLogger.logBadKeyStoreSource(type, source, exceptionName, exceptionMessage);
   }

   public void logBadKeyStoreType(String type, String source) {
      SecurityLogger.logBadKeyStoreType(type, source);
   }

   public void logCantInstantiateKeyStore(String type, String provider, String exceptionName, String exceptionMessage) {
      SecurityLogger.logCantInstantiateKeyStore(type, provider, exceptionName, exceptionMessage);
   }

   public void logCantLoadKeyStore(String type, String source, String exceptionName, String exceptionMessage) {
      SecurityLogger.logCantLoadKeyStore(type, source, exceptionName, exceptionMessage);
   }
}
