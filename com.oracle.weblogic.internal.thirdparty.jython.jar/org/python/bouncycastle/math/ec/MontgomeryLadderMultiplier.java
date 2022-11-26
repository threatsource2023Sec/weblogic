package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class MontgomeryLadderMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      ECPoint[] var3 = new ECPoint[]{var1.getCurve().getInfinity(), var1};
      int var4 = var2.bitLength();
      int var5 = var4;

      while(true) {
         --var5;
         if (var5 < 0) {
            return var3[0];
         }

         int var6 = var2.testBit(var5) ? 1 : 0;
         int var7 = 1 - var6;
         var3[var7] = var3[var7].add(var3[var6]);
         var3[var6] = var3[var6].twice();
      }
   }
}
