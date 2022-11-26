package weblogic.security;

import weblogic.security.provider.JavaMD2;
import weblogic.security.provider.JavaMD5;
import weblogic.security.provider.JavaSHA;

public final class WLMessageDigest {
   public static MessageDigest getInstance(String alg) {
      if (alg.equalsIgnoreCase("MD5")) {
         return new JavaMD5();
      } else if (alg.equalsIgnoreCase("SHA")) {
         return new JavaSHA();
      } else if (alg.equalsIgnoreCase("MD2")) {
         return new JavaMD2();
      } else {
         throw new AssertionError();
      }
   }
}
