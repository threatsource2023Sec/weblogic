package com.bea.security.utils.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

final class KeyStoreFactoryForFileBased extends AbstractKeyStoreFactoryForType {
   private File checkFileSource(String keyStoreType, String source, KeyStoreLogger logger) {
      try {
         if (null == source) {
            throw new IllegalArgumentException("Illegal null keystore source.");
         } else {
            File sourceFile = new File(source);
            if (!sourceFile.isFile()) {
               throw new IllegalArgumentException("Expected source to be a file.");
            } else if (!sourceFile.isAbsolute()) {
               throw new IllegalArgumentException("Expected source to be absolute file path.");
            } else if (!sourceFile.exists()) {
               throw new IllegalArgumentException("Expected source file to exist.");
            } else {
               return sourceFile;
            }
         }
      } catch (Exception var6) {
         if (null != logger) {
            logger.logBadKeyStoreSource(keyStoreType, source, var6.getClass().getName(), var6.getMessage());
         }

         return null;
      }
   }

   public boolean loadKeyStoreInstance(String keyStoreType, KeyStore keyStore, String source, char[] passphrase, KeyStoreLogger logger) throws IllegalArgumentException {
      if (null == keyStore) {
         throw new IllegalArgumentException("Illegal null KeyStore.");
      } else {
         File sourceFile = this.checkFileSource(keyStoreType, source, logger);
         if (null == sourceFile) {
            return false;
         } else {
            FileInputStream fis = null;

            boolean var9;
            try {
               fis = new FileInputStream(sourceFile);
               keyStore.load(fis, passphrase);
               return true;
            } catch (Exception var19) {
               if (null != logger) {
                  logger.logCantLoadKeyStore(keyStoreType, sourceFile.toString(), var19.getClass().getName(), var19.getMessage());
               }

               var9 = false;
            } finally {
               if (null != fis) {
                  try {
                     fis.close();
                  } catch (IOException var18) {
                  }
               }

            }

            return var9;
         }
      }
   }

   public long getKeyStoreLastModified(String keyStoreType, String source, KeyStoreLogger logger) {
      long ERROR = 0L;
      File sourceFile = this.checkFileSource(keyStoreType, source, logger);
      return null == sourceFile ? 0L : sourceFile.lastModified();
   }
}
