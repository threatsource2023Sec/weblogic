package weblogic.security;

import com.bea.security.utils.random.SecureRandomData;

public final class Salt {
   public static byte[] getRandomBytes(int length) {
      return SecureRandomData.getInstance().getRandomBytes(length);
   }
}
