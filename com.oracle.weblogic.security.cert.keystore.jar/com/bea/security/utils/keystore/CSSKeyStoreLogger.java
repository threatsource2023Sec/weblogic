package com.bea.security.utils.keystore;

import com.bea.common.logger.spi.LoggerSpi;
import java.util.logging.Level;

final class CSSKeyStoreLogger extends KeyStoreLogger {
   private final LoggerSpi logger;

   static CSSKeyStoreLogger getInstance(LoggerSpi cssLogger) {
      if (null == cssLogger) {
         throw new IllegalArgumentException("Illegal null LoggerSpi.");
      } else {
         return new CSSKeyStoreLogger(cssLogger);
      }
   }

   private CSSKeyStoreLogger(LoggerSpi logger) {
      if (null == logger) {
         throw new IllegalArgumentException("Illegal null LoggerSpi.");
      } else {
         this.logger = logger;
      }
   }

   public boolean isLoggable(Level level) {
      return this.logger.isDebugEnabled();
   }

   public void log(Level level, Throwable throwable, String msg, Object... params) {
      if (this.isLoggable(level)) {
         String formattedMsg = formatMessage(msg, params);
         if (null != throwable) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(formattedMsg, throwable);
            }
         } else if (this.logger.isDebugEnabled()) {
            this.logger.debug(formattedMsg);
         }

      }
   }
}
