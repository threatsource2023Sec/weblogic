package weblogic.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
   public static final String HMAC_ALGORITHM = "HmacSHA256";

   public static boolean verify(byte[] signature, byte[] data, byte[] secret, byte[] salt) {
      return Arrays.equals(digest(data, secret, salt), signature);
   }

   public static byte[] digest(byte[] data, byte[] secret, byte[] salt) {
      byte[] hmacResult = null;

      try {
         SecretKey key = new SecretKeySpec(secret, "HmacSHA256");
         Mac mac = Mac.getInstance("HmacSHA256");
         mac.init(key);
         mac.update(data);
         mac.update(salt);
         byte[] hmacResult = mac.doFinal();
         return hmacResult;
      } catch (InvalidKeyException | NoSuchAlgorithmException var6) {
         throw new IllegalStateException(var6);
      }
   }
}
