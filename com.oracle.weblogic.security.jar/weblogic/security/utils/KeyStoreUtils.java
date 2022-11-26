package weblogic.security.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.cert.CertificateException;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.utils.LocatorUtilities;

public class KeyStoreUtils {
   public static File getFile(String location) {
      if (location != null && location.length() != 0) {
         File file = new File(location);
         if (file.exists()) {
            return file;
         } else {
            file = new File(KeyStoreUtils.SecurityRuntimeAccessService.runtimeAccess.getServer().getRootDirectory(), location);
            return file.exists() ? file : null;
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getLocationNullOrEmpty());
      }
   }

   public static KeyStore load(File file, String passphrase, String type) {
      char[] password = passphrase != null && passphrase.length() != 0 ? passphrase.toCharArray() : null;
      return load(file, password, type);
   }

   public static KeyStore load(File file, char[] password, String type) {
      if (file == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullFile());
      } else {
         KeyStore keystore = null;

         try {
            keystore = KeyStore.getInstance(type);
         } catch (KeyStoreException var23) {
            SecurityLogger.logLoadKeyStoreKeyStoreException(type, var23.toString());
            return null;
         }

         FileInputStream is = null;

         try {
            is = new FileInputStream(file);
         } catch (FileNotFoundException var22) {
            SecurityLogger.logLoadKeyStoreFileNotFoundException(file.getAbsolutePath(), var22.toString());
            return null;
         }

         Object var6;
         try {
            keystore.load(is, password);
            return keystore;
         } catch (CertificateException var24) {
            SecurityLogger.logLoadKeyStoreCertificateException(file.getAbsolutePath(), type, var24.toString());
            var6 = null;
            return (KeyStore)var6;
         } catch (NoSuchAlgorithmException var25) {
            SecurityLogger.logLoadKeyStoreNoSuchAlgorithmException(file.getAbsolutePath(), type, var25.toString());
            var6 = null;
         } catch (IOException var26) {
            SecurityLogger.logLoadKeyStoreIOException(file.getAbsolutePath(), type, var26.toString());
            var6 = null;
            return (KeyStore)var6;
         } finally {
            try {
               is.close();
            } catch (IOException var21) {
            }

         }

         return (KeyStore)var6;
      }
   }

   public static KeyStore load(File file, String passphrase, String type, String provider) {
      char[] password = passphrase != null && passphrase.length() != 0 ? passphrase.toCharArray() : null;
      return load(file, password, type, provider);
   }

   public static KeyStore load(File file, char[] password, String type, String provider) {
      if (file == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullFile());
      } else {
         KeyStore keystore = null;

         try {
            keystore = KeyStore.getInstance(type, provider);
         } catch (NoSuchProviderException var25) {
            SecurityLogger.logLoadKeyStoreException(type, provider, var25.toString());
            return null;
         } catch (KeyStoreException var26) {
            SecurityLogger.logLoadKeyStoreException(type, provider, var26.toString());
            return null;
         }

         FileInputStream is = null;

         try {
            is = new FileInputStream(file);
         } catch (FileNotFoundException var24) {
            SecurityLogger.logLoadKeyStoreFileNotFoundException(file.getAbsolutePath(), var24.toString());
            return null;
         }

         Object var7;
         try {
            keystore.load(is, password);
            return keystore;
         } catch (CertificateException var27) {
            SecurityLogger.logLoadKeyStoreCertificateException(file.getAbsolutePath(), type, var27.toString());
            var7 = null;
         } catch (NoSuchAlgorithmException var28) {
            SecurityLogger.logLoadKeyStoreNoSuchAlgorithmException(file.getAbsolutePath(), type, var28.toString());
            var7 = null;
            return (KeyStore)var7;
         } catch (IOException var29) {
            SecurityLogger.logLoadKeyStoreIOException(file.getAbsolutePath(), type, var29.toString());
            var7 = null;
            return (KeyStore)var7;
         } finally {
            try {
               is.close();
            } catch (IOException var23) {
            }

         }

         return (KeyStore)var7;
      }
   }

   public static boolean store(KeyStore keystore, File file, String passphrase) {
      if (keystore == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullKeystore());
      } else if (file == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullFile());
      } else if (passphrase != null && passphrase.length() != 0) {
         FileOutputStream os = null;

         try {
            os = new FileOutputStream(file);
         } catch (FileNotFoundException var23) {
            SecurityLogger.logStoreKeyStoreFileNotFoundException(file.getAbsolutePath(), var23.toString());
            return false;
         }

         boolean var6;
         try {
            Exception exception = null;

            try {
               keystore.store(os, passphrase.toCharArray());
               return true;
            } catch (KeyStoreException var24) {
               SecurityLogger.logStoreKeyStoreKeyStoreException(file.getAbsolutePath(), keystore.getType(), var24.toString());
               var6 = false;
            } catch (CertificateException var25) {
               SecurityLogger.logStoreKeyStoreCertificateException(file.getAbsolutePath(), keystore.getType(), var25.toString());
               var6 = false;
               return var6;
            } catch (NoSuchAlgorithmException var26) {
               SecurityLogger.logStoreKeyStoreNoSuchAlgorithmException(file.getAbsolutePath(), keystore.getType(), var26.toString());
               var6 = false;
               return var6;
            } catch (IOException var27) {
               SecurityLogger.logStoreKeyStoreIOException(file.getAbsolutePath(), keystore.getType(), var27.toString());
               var6 = false;
               return var6;
            }
         } finally {
            try {
               os.close();
            } catch (IOException var22) {
            }

         }

         return var6;
      } else {
         throw new IllegalArgumentException(SecurityLogger.getNullOrEmptyPassPhrase());
      }
   }

   private static final class SecurityRuntimeAccessService {
      private static final SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
   }
}
