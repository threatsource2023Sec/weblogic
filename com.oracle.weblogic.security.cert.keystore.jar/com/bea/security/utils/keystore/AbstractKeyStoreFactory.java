package com.bea.security.utils.keystore;

import java.security.KeyStore;
import java.util.logging.Level;

public abstract class AbstractKeyStoreFactory {
   private static final AbstractKeyStoreFactoryForType FILE_BASED_KEYSTORE_FACTORY = new KeyStoreFactoryForFileBased();
   private static final AbstractKeyStoreFactoryForType KSS_KEYSTORE_FACTORY = new KeyStoreFactoryForKSS();

   private AbstractKeyStoreFactoryForType getKeyStoreFactory(String keyStoreType, String source, KeyStoreLogger logger) {
      if (this.isFileBasedKeyStore(keyStoreType)) {
         return FILE_BASED_KEYSTORE_FACTORY;
      } else if ("KSS".equalsIgnoreCase(keyStoreType)) {
         return KSS_KEYSTORE_FACTORY;
      } else {
         if (null != logger) {
            logger.logBadKeyStoreType(keyStoreType, source);
         }

         return null;
      }
   }

   protected KeyStore wrap(KeyStore ks) {
      return ks;
   }

   protected final boolean checkKeyStoreType(String type, String source, KeyStoreLogger logger) {
      if (null != type && 0 != type.trim().length()) {
         return true;
      } else {
         if (null != logger) {
            logger.logBadKeyStoreType(type, source);
         }

         return false;
      }
   }

   public final KeyStore getKeyStoreInstance(String provider, String keyStoreType, String source, char[] passphrase, KeyStoreLogger logger) {
      if (!this.checkKeyStoreType(keyStoreType, source, logger)) {
         return null;
      } else {
         AbstractKeyStoreFactoryForType typeFactory = this.getKeyStoreFactory(keyStoreType, source, logger);
         if (null == typeFactory) {
            return null;
         } else {
            KeyStore ks = typeFactory.instantiateKeyStore(keyStoreType, provider, logger);
            if (null == ks) {
               return null;
            } else {
               ks = this.wrap(ks);
               if (null == ks) {
                  if (null != logger && logger.isLoggable(Level.FINE)) {
                     logger.log(Level.FINE, "Wrap returned NULL, type={0}, source={1}", keyStoreType, source);
                  }

                  return null;
               } else if (!typeFactory.loadKeyStoreInstance(keyStoreType, ks, source, passphrase, logger)) {
                  return null;
               } else {
                  if (null != logger && logger.isLoggable(Level.FINEST)) {
                     logger.log(Level.FINEST, "Instantiated and loaded KeyStore, type={0}, source={1}", keyStoreType, source);
                  }

                  return ks;
               }
            }
         }
      }
   }

   public final long getLastModified(String keyStoreType, String source, KeyStoreLogger logger) {
      long ERROR = 0L;
      if (!this.checkKeyStoreType(keyStoreType, source, logger)) {
         return 0L;
      } else {
         AbstractKeyStoreFactoryForType typeFactory = this.getKeyStoreFactory(keyStoreType, source, logger);
         return null == typeFactory ? 0L : typeFactory.getKeyStoreLastModified(keyStoreType, source, logger);
      }
   }

   public final boolean isFileBasedKeyStore(String keyStoreType) {
      if (!this.checkKeyStoreType(keyStoreType, (String)null, (KeyStoreLogger)null)) {
         return false;
      } else {
         return !"KSS".equalsIgnoreCase(keyStoreType);
      }
   }
}
