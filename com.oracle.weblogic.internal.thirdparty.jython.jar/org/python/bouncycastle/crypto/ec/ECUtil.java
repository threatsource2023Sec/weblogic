package org.python.bouncycastle.crypto.ec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.math.ec.ECConstants;

class ECUtil {
   static BigInteger generateK(BigInteger var0, SecureRandom var1) {
      int var2 = var0.bitLength();

      BigInteger var3;
      do {
         do {
            var3 = new BigInteger(var2, var1);
         } while(var3.equals(ECConstants.ZERO));
      } while(var3.compareTo(var0) >= 0);

      return var3;
   }
}
