package com.sun.faces.util;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.FacesException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class ByteArrayGuardAESCTR {
   private static final Logger LOGGER;
   private static final int KEY_LENGTH = 128;
   private static final int IV_LENGTH = 16;
   private static final String KEY_ALGORITHM = "AES";
   private static final String CIPHER_CODE = "AES/CTR/NoPadding";
   private SecretKey sk;
   private Charset utf8;

   public ByteArrayGuardAESCTR() {
      try {
         this.setupKeyAndCharset();
      } catch (Exception var2) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unexpected exception initializing encryption.  No encryption will be performed.", var2);
         }

         System.err.println("ERROR: Initializing Ciphers");
      }

   }

   public String encrypt(String value) {
      String securedata = null;
      byte[] bytes = value.getBytes(this.utf8);

      try {
         SecureRandom rand = new SecureRandom();
         byte[] iv = new byte[16];
         rand.nextBytes(iv);
         IvParameterSpec ivspec = new IvParameterSpec(iv);
         Cipher encryptCipher = Cipher.getInstance("AES/CTR/NoPadding");
         encryptCipher.init(1, this.sk, ivspec);
         byte[] encdata = encryptCipher.doFinal(bytes);
         byte[] temp = concatBytes(iv, encdata);
         securedata = Base64.getEncoder().encodeToString(temp);
         return securedata;
      } catch (NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException var10) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unexpected exception initializing encryption.  No encryption will be performed.", var10);
         }

         return null;
      }
   }

   public String decrypt(String value) throws InvalidKeyException {
      byte[] bytes = Base64.getDecoder().decode(value);

      try {
         byte[] iv = new byte[16];
         System.arraycopy(bytes, 0, iv, 0, iv.length);
         IvParameterSpec ivspec = new IvParameterSpec(iv);
         Cipher decryptCipher = Cipher.getInstance("AES/CTR/NoPadding");
         decryptCipher.init(2, this.sk, ivspec);
         byte[] encBytes = new byte[bytes.length - 16];
         System.arraycopy(bytes, 16, encBytes, 0, encBytes.length);
         byte[] plaindata = decryptCipher.doFinal(encBytes);
         byte[] var8 = plaindata;
         int var9 = plaindata.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            byte cur = var8[var10];
            if (cur < 0 || cur > 127) {
               throw new InvalidKeyException("Invalid characters in decrypted value");
            }
         }

         return new String(plaindata, this.utf8);
      } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException var12) {
         throw new InvalidKeyException(var12);
      }
   }

   private void setupKeyAndCharset() {
      try {
         InitialContext context = new InitialContext();
         String encodedKeyArray = (String)context.lookup("java:comp/env/jsf/FlashSecretKey");
         if (null != encodedKeyArray) {
            byte[] keyArray = Base64.getDecoder().decode(encodedKeyArray);
            if (keyArray.length < 16) {
               throw new FacesException("key must be at least 16 bytes long.");
            }

            this.sk = new SecretKeySpec(keyArray, "AES");
         }
      } catch (NamingException var5) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Unable to find the encoded key.", var5);
         }
      } catch (FacesException var6) {
         throw new FacesException(var6);
      }

      if (null == this.sk) {
         try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            this.sk = kg.generateKey();
         } catch (Exception var4) {
            throw new FacesException(var4);
         }
      }

      SortedMap availableCharsets = Charset.availableCharsets();
      if (availableCharsets.containsKey("UTF-8")) {
         this.utf8 = (Charset)availableCharsets.get("UTF-8");
      } else {
         if (!availableCharsets.containsKey("UTF8")) {
            throw new FacesException("Unable to get UTF-8 Charset.");
         }

         this.utf8 = (Charset)availableCharsets.get("UTF8");
      }

   }

   private static byte[] concatBytes(byte[] array1, byte[] array2) {
      byte[] cBytes = new byte[array1.length + array2.length];

      try {
         System.arraycopy(array1, 0, cBytes, 0, array1.length);
         System.arraycopy(array2, 0, cBytes, array1.length, array2.length);
         return cBytes;
      } catch (Exception var4) {
         throw new FacesException(var4);
      }
   }

   static {
      LOGGER = FacesLogger.RENDERKIT.getLogger();
   }
}
