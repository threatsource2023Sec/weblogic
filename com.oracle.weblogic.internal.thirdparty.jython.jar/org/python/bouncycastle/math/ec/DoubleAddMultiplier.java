package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class DoubleAddMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      ECPoint[] var3 = new ECPoint[]{var1.getCurve().getInfinity(), var1};
      int var4 = var2.bitLength();

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var2.testBit(var5) ? 1 : 0;
         int var7 = 1 - var6;
         var3[var7] = var3[var7].twicePlus(var3[var6]);
      }

      return var3[0];
   }
}
