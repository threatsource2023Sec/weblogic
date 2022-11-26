package weblogic.security;

import java.security.NoSuchAlgorithmException;

public class HMAC {
   public static boolean verify(byte[] signature, byte[] data, byte[] secret, byte[] salt) {
      return com.bea.common.security.utils.HMAC.verify(signature, data, secret, salt);
   }

   public static byte[] digest(byte[] data, byte[] secret, byte[] salt) {
      return com.bea.common.security.utils.HMAC.digest(data, secret, salt);
   }

   public static void init() throws NoSuchAlgorithmException {
      com.bea.common.security.utils.HMAC.init();
   }
}
