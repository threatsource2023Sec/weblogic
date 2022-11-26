package com.bea.security.utils.encryption;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class EncryptedStreamFactory {
   public static final String DES_ALGORITHM = "DESede/CBC/PKCS5Padding";
   public static final String DES_KEY_ALGORITHM = "DESede";
   public static final String AES_ALGORITHM = "AES";
   public static final String HASH_FUNCTION = "SHA1";
   private static final int ITERATIONS = 20;

   private EncryptedStreamFactory() {
   }

   public static OutputStream getEncryptedOutputStream(OutputStream out, char[] password, String algorithm) {
      try {
         Cipher cipher = createCipher(password, algorithm, 1);
         return new CipherOutputStream(out, cipher);
      } catch (GeneralSecurityException var4) {
         throw new RuntimeException("Caught security exception", var4);
      } catch (UnsupportedEncodingException var5) {
         throw new RuntimeException(var5);
      }
   }

   public static InputStream getDecryptedInputStream(InputStream in, char[] password, String algorithm) {
      try {
         Cipher cipher = createCipher(password, algorithm, 2);
         return new CipherInputStream(in, cipher);
      } catch (GeneralSecurityException var4) {
         throw new RuntimeException("Caught security exception", var4);
      } catch (UnsupportedEncodingException var5) {
         throw new RuntimeException(var5);
      }
   }

   public static Cipher createCipher(char[] password, String algorithm, int mode) throws GeneralSecurityException, UnsupportedEncodingException {
      if ("AES".equals(algorithm)) {
         return createAESCipher(password, mode);
      } else if ("DES".equals(algorithm)) {
         return createDESCipher(password, mode);
      } else {
         throw new GeneralSecurityException("The algorithm [" + algorithm + "] is not supported.");
      }
   }

   private static Cipher createDESCipher(char[] password, int mode) throws GeneralSecurityException, UnsupportedEncodingException {
      byte[] passBytes = (new String(password)).getBytes("UTF-8");
      byte[] output = new byte[40];
      digest(passBytes, output, 0);
      digest(passBytes, output, 20);
      DESedeKeySpec keySpec = new DESedeKeySpec(output);
      Key key = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
      IvParameterSpec iv = new IvParameterSpec(output, 24, 8);
      Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      cipher.init(mode, key, iv);
      return cipher;
   }

   private static Cipher createAESCipher(char[] password, int mode) throws GeneralSecurityException, UnsupportedEncodingException {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(128, new SecureRandom((new String(password)).getBytes("UTF-8")));
      Key key = keyGen.generateKey();
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(mode, key);
      return cipher;
   }

   private static void digest(byte[] passBytes, byte[] output, int offset) throws NoSuchAlgorithmException {
      MessageDigest digest = MessageDigest.getInstance("SHA1");
      digest.update(passBytes);
      digest.update((byte)offset);

      for(int i = 0; i < 20; ++i) {
         byte[] result = digest.digest();
         digest.reset();
         digest.update(passBytes);
         digest.update(result);
      }

      byte[] result = digest.digest();
      System.arraycopy(result, 0, output, offset, result.length);
   }
}
