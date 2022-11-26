package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.math.ec.WNafUtil;
import org.python.bouncycastle.util.BigIntegers;

class DHParametersHelper {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);

   static BigInteger[] generateSafePrimes(int var0, int var1, SecureRandom var2) {
      int var3 = var0 - 1;
      int var4 = var0 >>> 2;

      BigInteger var5;
      BigInteger var6;
      do {
         do {
            while(true) {
               var5 = new BigInteger(var3, 2, var2);
               var6 = var5.shiftLeft(1).add(ONE);
               if (!var6.isProbablePrime(var1)) {
                  continue;
               }
               break;
            }
         } while(var1 > 2 && !var5.isProbablePrime(var1 - 2));
      } while(WNafUtil.getNafWeight(var6) < var4);

      return new BigInteger[]{var6, var5};
   }

   static BigInteger selectGenerator(BigInteger var0, BigInteger var1, SecureRandom var2) {
      BigInteger var3 = var0.subtract(TWO);

      BigInteger var5;
      do {
         BigInteger var4 = BigIntegers.createRandomInRange(TWO, var3, var2);
         var5 = var4.modPow(TWO, var0);
      } while(var5.equals(ONE));

      return var5;
   }
}
