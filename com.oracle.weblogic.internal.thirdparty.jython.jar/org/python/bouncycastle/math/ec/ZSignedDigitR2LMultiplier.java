package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class ZSignedDigitR2LMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      ECPoint var3 = var1.getCurve().getInfinity();
      int var5 = var2.bitLength();
      int var6 = var2.getLowestSetBit();
      ECPoint var4 = var1.timesPow2(var6);
      int var7 = var6;

      while(true) {
         ++var7;
         if (var7 >= var5) {
            var3 = var3.add(var4);
            return var3;
         }

         var3 = var3.add(var2.testBit(var7) ? var4 : var4.negate());
         var4 = var4.twice();
      }
   }
}
