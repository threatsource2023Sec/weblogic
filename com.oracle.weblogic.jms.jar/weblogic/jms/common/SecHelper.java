package weblogic.jms.common;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;

public class SecHelper {
   private static final int SALT_SIZE = 8;
   private static final int OLD_ITERATIONS = 13;
   private static final int ITERATIONS = 1024;
   private static final int AES_KEY_SIZE = 128;
   private static final String OLD_BLOCK_CIPHER_ALGORITHM = "PBEWithMD5AndDES";
   private static final String BLOCK_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
   private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
   private static final String ALGORITHM_TOKEN = "{Algorithm}";
   private static final String SALT_TOKEN = "{Salt}";
   private static final String IV_TOKEN = "{IV}";
   private static final String DATA_TOKEN = "{Data}";

   public static String encryptString(char[] key, String toEncrypt) throws GeneralSecurityException {
      return toEncrypt == null ? null : encryptPassword(key, toEncrypt.toCharArray());
   }

   private static byte[] charsToBytes(char[] changeMe) {
      if (changeMe == null) {
         return null;
      } else {
         int byteLength = changeMe.length * 2;
         byte[] retVal = new byte[byteLength];

         for(int lcv = 0; lcv < changeMe.length; ++lcv) {
            int numericValue = changeMe[lcv] & '\uffff';
            retVal[lcv * 2] = (byte)((numericValue & '\uff00') >> 8);
            retVal[lcv * 2 + 1] = (byte)(numericValue & 255);
         }

         return retVal;
      }
   }

   private static char[] bytesToChars(byte[] changeMe) {
      if (changeMe == null) {
         return null;
      } else if (changeMe.length % 2 != 0) {
         throw new AssertionError("Invalid number of bytes: " + changeMe.length);
      } else {
         int charLength = changeMe.length / 2;
         char[] retVal = new char[charLength];

         for(int lcv = 0; lcv < charLength; ++lcv) {
            retVal[lcv] = (char)((changeMe[lcv * 2] & 255) << 8 | changeMe[lcv * 2 + 1] & 255);
         }

         return retVal;
      }
   }

   private static SecretKey generateSecretKey(char[] key, byte[] salt) throws GeneralSecurityException {
      PBEKeySpec pbKey = new PBEKeySpec(key, salt, 1024, 128);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      SecretKey tmp = keyFactory.generateSecret(pbKey);
      SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
      return secretKey;
   }

   public static String encryptPassword(char[] key, char[] toEncrypt) throws GeneralSecurityException {
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[8];
      random.nextBytes(salt);
      SecretKey secretKey = generateSecretKey(key, salt);
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(1, secretKey);
      AlgorithmParameters params = cipher.getParameters();
      byte[] iv = ((IvParameterSpec)params.getParameterSpec(IvParameterSpec.class)).getIV();
      byte[] encryptedText = cipher.doFinal(charsToBytes(toEncrypt));
      BASE64Encoder encoder = new BASE64Encoder();
      String saltPrintable = encoder.encodeBuffer(salt);
      String ivPrintable = encoder.encodeBuffer(iv);
      String encryptedPrintable = encoder.encodeBuffer(encryptedText);
      return "{Algorithm}AES/CBC/PKCS5Padding{Salt}" + saltPrintable + "{IV}" + ivPrintable + "{Data}" + encryptedPrintable;
   }

   public static char[] decryptString(char[] key, String formattedText) throws GeneralSecurityException, IOException {
      int algorithmStart = formattedText.indexOf("{Algorithm}");
      int algorithmEnd = algorithmStart + "{Algorithm}".length();
      int saltStart = formattedText.indexOf("{Salt}");
      int saltEnd = saltStart + "{Salt}".length();
      int dataStart = formattedText.indexOf("{Data}");
      int dataEnd = dataStart + "{Data}".length();
      if (algorithmStart < 0) {
         throw new GeneralSecurityException("Algorithm cannot be found");
      } else {
         String algorithm = formattedText.substring(algorithmEnd, saltStart);
         if (!"AES/CBC/PKCS5Padding".equals(algorithm) && !"PBEWithMD5AndDES".equals(algorithm)) {
            throw new GeneralSecurityException("algorithm " + algorithm + " is not supported");
         } else if (saltStart < 0) {
            throw new GeneralSecurityException("Salt cannot be found");
         } else if (dataStart < 0) {
            throw new GeneralSecurityException("Encrypted data cannot be found");
         } else {
            SecretKey secretKey = null;
            AlgorithmParameterSpec params = null;
            byte[] decodedEncryptedData = null;
            byte[] decodedEncryptedData;
            if ("AES/CBC/PKCS5Padding".equals(algorithm)) {
               int ivStart = formattedText.indexOf("{IV}");
               int ivEnd = ivStart + "{IV}".length();
               if (ivStart < 0) {
                  throw new GeneralSecurityException("Initialzation vector cannot be found for " + algorithm);
               }

               String salt = formattedText.substring(saltEnd, ivStart);
               String iv = formattedText.substring(ivEnd, dataStart);
               String encryptedData = formattedText.substring(dataEnd);
               BASE64Decoder decoder = new BASE64Decoder();
               byte[] decodedSalt = decoder.decodeBuffer(salt);
               byte[] decodedIV = decoder.decodeBuffer(iv);
               decodedEncryptedData = decoder.decodeBuffer(encryptedData);
               secretKey = generateSecretKey(key, decodedSalt);
               params = new IvParameterSpec(decodedIV);
            } else {
               String salt = formattedText.substring(saltEnd, dataStart);
               String encryptedData = formattedText.substring(dataEnd);
               BASE64Decoder decoder = new BASE64Decoder();
               byte[] decodedSalt = decoder.decodeBuffer(salt);
               decodedEncryptedData = decoder.decodeBuffer(encryptedData);
               params = new PBEParameterSpec(decodedSalt, 13);
               PBEKeySpec pbKey = new PBEKeySpec(key);
               SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
               secretKey = keyFactory.generateSecret(pbKey);
            }

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, secretKey, (AlgorithmParameterSpec)params);

            try {
               byte[] decryptedText = cipher.doFinal(decodedEncryptedData);
               return bytesToChars(decryptedText);
            } catch (Exception var20) {
               throw new GeneralSecurityException("Error occured during decryption");
            }
         }
      }
   }
}
