package com.bea.security.utils.keystore;

import java.security.KeyStore;

final class KeyStoreFactoryForKSS extends AbstractKeyStoreFactoryForType {
   static final String KSS_KEYSTORE_TYPE = "KSS";

   private static boolean checkSource(String source, KeyStoreLogger logger) {
      try {
         if (null != source && 0 != source.length()) {
            return true;
         } else {
            throw new IllegalArgumentException("Illegal null or empty source.");
         }
      } catch (Exception var3) {
         if (null != logger) {
            logger.logBadKeyStoreSource("KSS", source, var3.getClass().getName(), var3.getMessage());
         }

         return false;
      }
   }

   public KeyStore instantiateKeyStore(String keyStoreType, String provider, KeyStoreLogger logger) {
      return super.instantiateKeyStore("KSS", provider, logger);
   }

   public boolean loadKeyStoreInstance(String keyStoreType, KeyStore keyStore, String source, char[] passphrase, KeyStoreLogger logger) throws IllegalArgumentException {
      if (null == keyStore) {
         throw new IllegalArgumentException("Illegal null KeyStore.");
      } else if (!checkSource(source, logger)) {
         return false;
      } else {
         try {
            KeyStore.LoadStoreParameter params = KssAccessor.getKSSLoadStoreParameterInstance(source, passphrase);
            keyStore.load(params);
            return true;
         } catch (Exception var7) {
            if (null != logger) {
               logger.logCantLoadKeyStore("KSS", source, var7.getClass().getName(), var7.getMessage());
            }

            return false;
         }
      }
   }

   public long getKeyStoreLastModified(String keyStoreType, String source, KeyStoreLogger logger) {
      long ERROR = 0L;
      if (!checkSource(source, logger)) {
         return 0L;
      } else {
         try {
            return KssAccessor.getLastModified(source);
         } catch (Exception var7) {
            if (null != logger) {
               logger.logCantLoadKeyStore("KSS", source, var7.getClass().getName(), var7.getMessage());
            }

            return 0L;
         }
      }
   }
}
