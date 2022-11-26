package weblogic.security.internal.encryption;

import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.SecurityLogger;

final class JSafeSecretKeyEncryptor {
   private static final String KEY_ALGORITHM = "PBE/SHA1/RC2/CBC/PKCS12PBE-5-128";
   private static final int SALT_LENGTH = 8;
   private static DebugLogger logger = DebugLogger.getDebugLogger("DebugSecurityEncryptionService");

   static byte[] encryptSecretKey(JSAFE_SecretKey key, char[] password, byte[] salt) throws EncryptionServiceException {
      byte[] expandedSalt = doubleSalt(salt);
      byte[] keyMaterial = key.getSecretKeyData();
      log("Key material length: " + keyMaterial.length);
      byte[] cipherBytes = null;
      JSAFE_SecretKey secretKey = null;
      JSAFE_SymmetricCipher cipher = null;
      boolean var14 = false;

      int i;
      byte[] cipherBytes;
      try {
         var14 = true;
         cipher = JSafeEncryptionServiceImpl.getSymmetricCipher("PBE/SHA1/RC2/CBC/PKCS12PBE-5-128", "Java");
         cipher.setSalt(salt, 0, salt.length);
         secretKey = cipher.getBlankKey();
         secretKey.setPassword(password, 0, password.length);
         cipher.encryptInit(secretKey);
         cipherBytes = new byte[cipher.getOutputBufferSize(keyMaterial.length)];
         i = cipher.encryptUpdate(keyMaterial, 0, keyMaterial.length, cipherBytes, 0);
         cipher.encryptFinal(cipherBytes, i);
         var14 = false;
      } catch (Exception var15) {
         throw new EncryptionServiceException(var15.toString());
      } finally {
         if (var14) {
            cipher.clearSensitiveData();
            secretKey.clearSensitiveData();

            for(int i = 0; i < password.length; ++i) {
               password[i] = 0;
            }

         }
      }

      cipher.clearSensitiveData();
      secretKey.clearSensitiveData();

      for(i = 0; i < password.length; ++i) {
         password[i] = 0;
      }

      return cipherBytes;
   }

   static JSAFE_SecretKey decryptSecretKey(String algorithm, byte[] encryptedKey, char[] password, byte[] salt) throws EncryptionServiceException {
      byte[] expandedSalt = doubleSalt(salt);
      byte[] keyMaterial = new byte[encryptedKey.length];
      log("key material length: " + keyMaterial.length);
      JSAFE_SecretKey secretKey = null;
      JSAFE_SymmetricCipher cipher = null;
      JSAFE_SecretKey returnKey = null;
      boolean var17 = false;

      int i;
      try {
         var17 = true;
         cipher = JSafeEncryptionServiceImpl.getSymmetricCipher("PBE/SHA1/RC2/CBC/PKCS12PBE-5-128", "Java");
         cipher.setSalt(salt, 0, salt.length);
         secretKey = cipher.getBlankKey();
         secretKey.setPassword(password, 0, password.length);
         cipher.decryptInit(secretKey);
         i = cipher.decryptUpdate(encryptedKey, 0, encryptedKey.length, keyMaterial, 0);
         int finalOut = cipher.decryptFinal(keyMaterial, i);
         int totalOut = i + finalOut;
         log(totalOut + " bytes of the array filled");
         byte[] actualMaterial = new byte[totalOut];
         System.arraycopy(keyMaterial, 0, actualMaterial, 0, actualMaterial.length);
         log("getting cipher to generate key");
         cipher = JSAFE_SymmetricCipher.getInstance(algorithm, "Java");
         log("blank key from cipher");
         returnKey = cipher.getBlankKey();
         log("setting key data: " + actualMaterial.length);
         returnKey.setSecretKeyData(actualMaterial, 0, actualMaterial.length);
         var17 = false;
      } catch (Exception var18) {
         throw new EncryptionServiceException(SecurityLogger.getErrorDecryptingKey(var18.toString()));
      } finally {
         if (var17) {
            if (secretKey != null) {
               secretKey.clearSensitiveData();
            }

            if (cipher != null) {
               cipher.clearSensitiveData();
            }

            for(int i = 0; i < password.length; ++i) {
               password[i] = 0;
            }

         }
      }

      if (secretKey != null) {
         secretKey.clearSensitiveData();
      }

      if (cipher != null) {
         cipher.clearSensitiveData();
      }

      for(i = 0; i < password.length; ++i) {
         password[i] = 0;
      }

      return returnKey;
   }

   static byte[] doubleSalt(byte[] salt) {
      if (salt.length == 8) {
         return salt;
      } else {
         byte[] doubleSalt = new byte[8];
         System.arraycopy(salt, 0, doubleSalt, 0, 4);
         System.arraycopy(salt, 0, doubleSalt, 4, 4);
         return doubleSalt;
      }
   }

   public static void log(String msg) {
      if (logger.isDebugEnabled()) {
         logger.debug(System.currentTimeMillis() + " : " + Thread.currentThread().getName() + " : " + msg);
      }

   }
}
