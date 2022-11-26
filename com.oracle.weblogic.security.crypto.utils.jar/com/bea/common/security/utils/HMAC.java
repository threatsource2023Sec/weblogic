package com.bea.common.security.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
   public static final String HMAC_ALGORITHM = "HmacSHA256";
   public static final String OLD_VERIFY_PROP = "weblogic.security.crypto.verifyPriorDigest";
   public static final String OLD_DIGEST_PROP = "weblogic.security.crypto.generatePriorDigest";
   private static final boolean useOldDigest = Boolean.getBoolean("weblogic.security.crypto.generatePriorDigest");
   private static final boolean useOldVerify;

   public static void init() throws NoSuchAlgorithmException {
      Mac mac = Mac.getInstance("HmacSHA256");
   }

   public static boolean verify(byte[] signature, byte[] data, byte[] secret, byte[] salt) {
      boolean result = false;
      if (signature.length == 32) {
         result = Arrays.equals(digest(data, secret, salt), signature);
      } else if (signature.length == 16 && useOldVerify) {
         result = Arrays.equals(oldDigest(data, secret, salt), signature);
      }

      return result;
   }

   public static byte[] digest(byte[] data, byte[] secret, byte[] salt) {
      byte[] hmacResult = null;
      byte[] hmacResult;
      if (useOldDigest) {
         hmacResult = oldDigest(data, secret, salt);
      } else {
         try {
            SecretKey key = new SecretKeySpec(secret, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            mac.update(data);
            mac.update(salt);
            hmacResult = mac.doFinal();
         } catch (InvalidKeyException | NoSuchAlgorithmException var6) {
            throw new IllegalStateException(var6);
         }
      }

      return hmacResult;
   }

   static byte[] oldDigest(byte[] data, byte[] secret, byte[] salt) {
      MessageDigest md5 = new JavaMD5();
      byte[] ipad = new byte[65];
      byte[] opad = new byte[65];
      if (secret.length > 64) {
         md5.reset();
         md5.update(secret);
         secret = md5.digest();
      }

      System.arraycopy(secret, 0, ipad, 0, secret.length);
      System.arraycopy(secret, 0, opad, 0, secret.length);

      for(int i = 0; i < 64; ++i) {
         ipad[i] = (byte)(ipad[i] ^ 54);
         opad[i] = (byte)(opad[i] ^ 92);
      }

      md5.reset();
      md5.update(ipad);
      md5.update(salt);
      md5.update(data);
      byte[] digested = md5.digest();
      md5.reset();
      md5.update(opad);
      md5.update(digested);
      return md5.digest();
   }

   static {
      useOldVerify = System.getProperty("weblogic.security.crypto.verifyPriorDigest") != null ? Boolean.getBoolean("weblogic.security.crypto.verifyPriorDigest") : useOldDigest;
   }
}
