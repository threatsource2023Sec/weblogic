package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class ZSignedDigitL2RMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      ECPoint var3 = var1.normalize();
      ECPoint var4 = var3.negate();
      ECPoint var5 = var3;
      int var6 = var2.bitLength();
      int var7 = var2.getLowestSetBit();
      int var8 = var6;

      while(true) {
         --var8;
         if (var8 <= var7) {
            var5 = var5.timesPow2(var7);
            return var5;
         }

         var5 = var5.twicePlus(var2.testBit(var8) ? var3 : var4);
      }
   }
}
