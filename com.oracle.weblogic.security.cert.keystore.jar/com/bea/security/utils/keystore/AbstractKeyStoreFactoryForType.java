package com.bea.security.utils.keystore;

import java.security.KeyStore;

abstract class AbstractKeyStoreFactoryForType {
   public KeyStore instantiateKeyStore(String keyStoreType, String provider, KeyStoreLogger logger) {
      try {
         KeyStore ks;
         if (null == provider) {
            ks = KeyStore.getInstance(keyStoreType);
         } else {
            ks = KeyStore.getInstance(keyStoreType, provider);
         }

         if (null == ks) {
            throw new IllegalStateException("Unexpected null KeyStore instance.");
         } else {
            return ks;
         }
      } catch (Exception var6) {
         if (null != logger) {
            logger.logCantInstantiateKeyStore(keyStoreType, provider, var6.getClass().getName(), var6.getMessage());
         }

         return null;
      }
   }

   public abstract boolean loadKeyStoreInstance(String var1, KeyStore var2, String var3, char[] var4, KeyStoreLogger var5) throws IllegalArgumentException;

   public abstract long getKeyStoreLastModified(String var1, String var2, KeyStoreLogger var3);
}
