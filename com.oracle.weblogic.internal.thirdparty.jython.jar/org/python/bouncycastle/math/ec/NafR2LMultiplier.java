package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class NafR2LMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      int[] var3 = WNafUtil.generateCompactNaf(var2);
      ECPoint var4 = var1.getCurve().getInfinity();
      ECPoint var5 = var1;
      int var6 = 0;

      for(int var7 = 0; var7 < var3.length; ++var7) {
         int var8 = var3[var7];
         int var9 = var8 >> 16;
         var6 += var8 & '\uffff';
         var5 = var5.timesPow2(var6);
         var4 = var4.add(var9 < 0 ? var5.negate() : var5);
         var6 = 1;
      }

      return var4;
   }
}
