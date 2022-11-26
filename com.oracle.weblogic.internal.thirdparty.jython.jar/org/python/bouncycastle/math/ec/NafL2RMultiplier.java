package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class NafL2RMultiplier extends AbstractECMultiplier {
   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      int[] var3 = WNafUtil.generateCompactNaf(var2);
      ECPoint var4 = var1.normalize();
      ECPoint var5 = var4.negate();
      ECPoint var6 = var1.getCurve().getInfinity();
      int var7 = var3.length;

      while(true) {
         --var7;
         if (var7 < 0) {
            return var6;
         }

         int var8 = var3[var7];
         int var9 = var8 >> 16;
         int var10 = var8 & '\uffff';
         var6 = var6.twicePlus(var9 < 0 ? var5 : var4);
         var6 = var6.timesPow2(var10);
      }
   }
}
