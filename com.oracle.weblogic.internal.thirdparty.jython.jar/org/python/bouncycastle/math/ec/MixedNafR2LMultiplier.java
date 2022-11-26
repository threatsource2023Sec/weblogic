package org.python.bouncycastle.math.ec;

import java.math.BigInteger;

public class MixedNafR2LMultiplier extends AbstractECMultiplier {
   protected int additionCoord;
   protected int doublingCoord;

   public MixedNafR2LMultiplier() {
      this(2, 4);
   }

   public MixedNafR2LMultiplier(int var1, int var2) {
      this.additionCoord = var1;
      this.doublingCoord = var2;
   }

   protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
      ECCurve var3 = var1.getCurve();
      ECCurve var4 = this.configureCurve(var3, this.additionCoord);
      ECCurve var5 = this.configureCurve(var3, this.doublingCoord);
      int[] var6 = WNafUtil.generateCompactNaf(var2);
      ECPoint var7 = var4.getInfinity();
      ECPoint var8 = var5.importPoint(var1);
      int var9 = 0;

      for(int var10 = 0; var10 < var6.length; ++var10) {
         int var11 = var6[var10];
         int var12 = var11 >> 16;
         var9 += var11 & '\uffff';
         var8 = var8.timesPow2(var9);
         ECPoint var13 = var4.importPoint(var8);
         if (var12 < 0) {
            var13 = var13.negate();
         }

         var7 = var7.add(var13);
         var9 = 1;
      }

      return var3.importPoint(var7);
   }

   protected ECCurve configureCurve(ECCurve var1, int var2) {
      if (var1.getCoordinateSystem() == var2) {
         return var1;
      } else if (!var1.supportsCoordinateSystem(var2)) {
         throw new IllegalArgumentException("Coordinate system " + var2 + " not supported by this curve");
      } else {
         return var1.configure().setCoordinateSystem(var2).create();
      }
   }
}
