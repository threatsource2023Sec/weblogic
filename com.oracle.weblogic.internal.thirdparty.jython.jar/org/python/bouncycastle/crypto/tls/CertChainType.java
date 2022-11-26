package org.python.bouncycastle.crypto.tls;

public class CertChainType {
   public static final short individual_certs = 0;
   public static final short pkipath = 1;

   public static boolean isValid(short var0) {
      return var0 >= 0 && var0 <= 1;
   }
}
