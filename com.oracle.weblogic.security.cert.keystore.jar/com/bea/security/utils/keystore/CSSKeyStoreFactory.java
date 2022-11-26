package com.bea.security.utils.keystore;

import com.bea.common.logger.spi.LoggerSpi;
import java.security.KeyStore;

public final class CSSKeyStoreFactory {
   private static final CSSKeyStoreFactoryImpl IMPL = new CSSKeyStoreFactoryImpl();

   private static KeyStoreLogger convertLogger(LoggerSpi cssLogger) {
      KeyStoreLogger logger = null;
      if (null != cssLogger) {
         logger = CSSKeyStoreLogger.getInstance(cssLogger);
      }

      return logger;
   }

   public static KeyStore getKeyStoreInstance(String provider, String keyStoreType, String source, char[] passphrase, LoggerSpi cssLogger) {
      return IMPL.getKeyStoreInstance(provider, keyStoreType, source, passphrase, convertLogger(cssLogger));
   }

   public static KeyStore getKeyStoreInstance(String keyStoreType, String source, char[] passphrase, LoggerSpi cssLogger) {
      return IMPL.getKeyStoreInstance((String)null, keyStoreType, source, passphrase, convertLogger(cssLogger));
   }

   public static long getLastModified(String keyStoreType, String source, LoggerSpi cssLogger) {
      return IMPL.getLastModified(keyStoreType, source, convertLogger(cssLogger));
   }

   public static boolean isFileBasedKeyStore(String keyStoreType) {
      return IMPL.isFileBasedKeyStore(keyStoreType);
   }

   private CSSKeyStoreFactory() {
   }

   private static class CSSKeyStoreFactoryImpl extends AbstractKeyStoreFactory {
      private CSSKeyStoreFactoryImpl() {
      }

      // $FF: synthetic method
      CSSKeyStoreFactoryImpl(Object x0) {
         this();
      }
   }
}
