package com.sun.faces.renderkit;

import com.sun.faces.util.FacesLogger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.faces.FacesException;

public final class ByteArrayGuard {
   private static final Logger LOGGER;
   private static final int IV_LENGTH = 8;
   private static final int KEY_LENGTH = 24;
   private static Cipher NULL_CIPHER;
   private Cipher decryptCipher;
   private Cipher encryptCipher;

   public ByteArrayGuard(String password) {
      this.decryptCipher = NULL_CIPHER;
      this.encryptCipher = NULL_CIPHER;
      if (password != null) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Client state saving encryption enabled.");
         }

         byte[] passwordKey = this.convertPasswordToKey(password.getBytes());

         try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            SecretKeyFactory keygen = SecretKeyFactory.getInstance("DESede");
            this.encryptCipher = this.getBlockCipherForEncryption(keygen, prng, passwordKey);
            byte[] iVector = this.encryptCipher.getIV();
            this.decryptCipher = this.getBlockCipherForDecryption(keygen, prng, passwordKey, iVector);
         } catch (Exception var6) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unexpected exception initializing encryption.  No encryption will be performed.", var6);
            }

            this.encryptCipher = NULL_CIPHER;
            this.decryptCipher = NULL_CIPHER;
         }
      }

   }

   public Cipher getEncryptionCipher() {
      return this.encryptCipher;
   }

   public Cipher getDecryptionCipher() {
      return this.decryptCipher;
   }

   private byte[] convertPasswordToKey(byte[] password) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA");
         byte[] seed = md.digest(password);
         SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
         random.setSeed(seed);
         byte[] rawkey = new byte[24];
         random.nextBytes(rawkey);
         return rawkey;
      } catch (Exception var6) {
         throw new FacesException(var6);
      }
   }

   private Cipher getBlockCipherForDecryption(SecretKeyFactory keyGen, SecureRandom random, byte[] rawKey, byte[] iv) {
      try {
         DESedeKeySpec keyspec = new DESedeKeySpec(rawKey);
         Key key = keyGen.generateSecret(keyspec);
         Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
         IvParameterSpec ivspec = new IvParameterSpec(iv);
         cipher.init(2, key, ivspec, random);
         return cipher;
      } catch (Exception var9) {
         throw new FacesException(var9);
      }
   }

   private Cipher getBlockCipherForEncryption(SecretKeyFactory keyGen, SecureRandom random, byte[] rawKey) {
      try {
         DESedeKeySpec keyspec = new DESedeKeySpec(rawKey);
         Key key = keyGen.generateSecret(keyspec);
         Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
         byte[] iv = new byte[8];
         random.nextBytes(iv);
         IvParameterSpec ivspec = new IvParameterSpec(iv);
         cipher.init(1, key, ivspec, random);
         return cipher;
      } catch (Exception var9) {
         throw new FacesException(var9);
      }
   }

   static {
      LOGGER = FacesLogger.RENDERKIT.getLogger();
      NULL_CIPHER = new NullCipher();
   }
}
