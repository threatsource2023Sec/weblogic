package com.bea.security.utils.keystore;

import java.text.MessageFormat;
import java.util.logging.Level;

public abstract class KeyStoreLogger {
   protected static final String formatMessage(String msg, Object... params) {
      String formattedMsg;
      if (null != msg) {
         formattedMsg = MessageFormat.format(msg, params);
      } else {
         formattedMsg = "";
      }

      return formattedMsg;
   }

   public abstract boolean isLoggable(Level var1);

   public abstract void log(Level var1, Throwable var2, String var3, Object... var4);

   public final void log(Level level, String msg, Object... params) {
      this.log(level, (Throwable)null, msg, params);
   }

   public void logBadKeyStoreSource(String type, String source, String exceptionName, String exceptionMessage) {
      this.log(Level.FINE, "Illegal key store source value [keyStoreType=\"{0}\", source=\"{1}\", exception=\"{2}\", message=\"{3}\"]", type, source, exceptionName, exceptionMessage);
   }

   public void logBadKeyStoreType(String type, String source) {
      this.log(Level.FINE, "Illegal or unknown key store type value [keyStoreType=\"{0}\", source=\"{1}\"]", type, source);
   }

   public void logCantInstantiateKeyStore(String type, String provider, String exceptionName, String exceptionMessage) {
      this.log(Level.FINE, "Unable to instantiate key store [keyStoreType=\"{0}\", provider=\"{1}\", exception=\"{2}\", message=\"{3}\"]", type, provider, exceptionName, exceptionMessage);
   }

   public void logCantLoadKeyStore(String type, String source, String exceptionName, String exceptionMessage) {
      this.log(Level.FINE, "Unable to load key store [keyStoreType=\"{0}\", source=\"{1}\", exception=\"{2}\", message=\"{3}\"]", type, source, exceptionName, exceptionMessage);
   }
}
