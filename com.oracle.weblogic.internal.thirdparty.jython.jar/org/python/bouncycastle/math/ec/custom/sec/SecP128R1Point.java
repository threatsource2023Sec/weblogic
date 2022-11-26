package org.python.bouncycastle.math.ec.custom.sec;

import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat128;

public class SecP128R1Point extends ECPoint.AbstractFp {
   /** @deprecated */
   public SecP128R1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
      this(var1, var2, var3, false);
   }

   /** @deprecated */
   public SecP128R1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
      super(var1, var2, var3);
      if (var2 == null != (var3 == null)) {
         throw new IllegalArgumentException("Exactly one of the field elements is null");
      } else {
         this.withCompression = var4;
      }
   }

   SecP128R1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
      super(var1, var2, var3, var4);
      this.withCompression = var5;
   }

   protected ECPoint detach() {
      return new SecP128R1Point((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
   }

   public ECPoint add(ECPoint var1) {
      if (this.isInfinity()) {
         return var1;
      } else if (var1.isInfinity()) {
         return this;
      } else if (this == var1) {
         return this.twice();
      } else {
         ECCurve var2 = this.getCurve();
         SecP128R1FieldElement var3 = (SecP128R1FieldElement)this.x;
         SecP128R1FieldElement var4 = (SecP128R1FieldElement)this.y;
         SecP128R1FieldElement var5 = (SecP128R1FieldElement)var1.getXCoord();
         SecP128R1FieldElement var6 = (SecP128R1FieldElement)var1.getYCoord();
         SecP128R1FieldElement var7 = (SecP128R1FieldElement)this.zs[0];
         SecP128R1FieldElement var8 = (SecP128R1FieldElement)var1.getZCoord(0);
         int[] var9 = Nat128.createExt();
         int[] var10 = Nat128.create();
         int[] var11 = Nat128.create();
         int[] var12 = Nat128.create();
         boolean var13 = var7.isOne();
         int[] var14;
         int[] var15;
         if (var13) {
            var14 = var5.x;
            var15 = var6.x;
         } else {
            var15 = var11;
            SecP128R1Field.square(var7.x, var11);
            var14 = var10;
            SecP128R1Field.multiply(var11, var5.x, var10);
            SecP128R1Field.multiply(var11, var7.x, var11);
            SecP128R1Field.multiply(var11, var6.x, var11);
         }

         boolean var16 = var8.isOne();
         int[] var17;
         int[] var18;
         if (var16) {
            var17 = var3.x;
            var18 = var4.x;
         } else {
            var18 = var12;
            SecP128R1Field.square(var8.x, var12);
            var17 = var9;
            SecP128R1Field.multiply(var12, var3.x, var9);
            SecP128R1Field.multiply(var12, var8.x, var12);
            SecP128R1Field.multiply(var12, var4.x, var12);
         }

         int[] var19 = Nat128.create();
         SecP128R1Field.subtract(var17, var14, var19);
         SecP128R1Field.subtract(var18, var15, var10);
         if (Nat128.isZero(var19)) {
            return Nat128.isZero(var10) ? this.twice() : var2.getInfinity();
         } else {
            SecP128R1Field.square(var19, var11);
            int[] var22 = Nat128.create();
            SecP128R1Field.multiply(var11, var19, var22);
            SecP128R1Field.multiply(var11, var17, var11);
            SecP128R1Field.negate(var22, var22);
            Nat128.mul(var18, var22, var9);
            int var24 = Nat128.addBothTo(var11, var11, var22);
            SecP128R1Field.reduce32(var24, var22);
            SecP128R1FieldElement var25 = new SecP128R1FieldElement(var12);
            SecP128R1Field.square(var10, var25.x);
            SecP128R1Field.subtract(var25.x, var22, var25.x);
            SecP128R1FieldElement var26 = new SecP128R1FieldElement(var22);
            SecP128R1Field.subtract(var11, var25.x, var26.x);
            SecP128R1Field.multiplyAddToExt(var26.x, var10, var9);
            SecP128R1Field.reduce(var9, var26.x);
            SecP128R1FieldElement var27 = new SecP128R1FieldElement(var19);
            if (!var13) {
               SecP128R1Field.multiply(var27.x, var7.x, var27.x);
            }

            if (!var16) {
               SecP128R1Field.multiply(var27.x, var8.x, var27.x);
            }

            ECFieldElement[] var28 = new ECFieldElement[]{var27};
            return new SecP128R1Point(var2, var25, var26, var28, this.withCompression);
         }
      }
   }

   public ECPoint twice() {
      if (this.isInfinity()) {
         return this;
      } else {
         ECCurve var1 = this.getCurve();
         SecP128R1FieldElement var2 = (SecP128R1FieldElement)this.y;
         if (var2.isZero()) {
            return var1.getInfinity();
         } else {
            SecP128R1FieldElement var3 = (SecP128R1FieldElement)this.x;
            SecP128R1FieldElement var4 = (SecP128R1FieldElement)this.zs[0];
            int[] var5 = Nat128.create();
            int[] var6 = Nat128.create();
            int[] var7 = Nat128.create();
            SecP128R1Field.square(var2.x, var7);
            int[] var8 = Nat128.create();
            SecP128R1Field.square(var7, var8);
            boolean var9 = var4.isOne();
            int[] var10 = var4.x;
            if (!var9) {
               var10 = var6;
               SecP128R1Field.square(var4.x, var6);
            }

            SecP128R1Field.subtract(var3.x, var10, var5);
            SecP128R1Field.add(var3.x, var10, var6);
            SecP128R1Field.multiply(var6, var5, var6);
            int var12 = Nat128.addBothTo(var6, var6, var6);
            SecP128R1Field.reduce32(var12, var6);
            SecP128R1Field.multiply(var7, var3.x, var7);
            var12 = Nat.shiftUpBits(4, var7, 2, 0);
            SecP128R1Field.reduce32(var12, var7);
            var12 = Nat.shiftUpBits(4, var8, 3, 0, var5);
            SecP128R1Field.reduce32(var12, var5);
            SecP128R1FieldElement var14 = new SecP128R1FieldElement(var8);
            SecP128R1Field.square(var6, var14.x);
            SecP128R1Field.subtract(var14.x, var7, var14.x);
            SecP128R1Field.subtract(var14.x, var7, var14.x);
            SecP128R1FieldElement var15 = new SecP128R1FieldElement(var7);
            SecP128R1Field.subtract(var7, var14.x, var15.x);
            SecP128R1Field.multiply(var15.x, var6, var15.x);
            SecP128R1Field.subtract(var15.x, var5, var15.x);
            SecP128R1FieldElement var16 = new SecP128R1FieldElement(var6);
            SecP128R1Field.twice(var2.x, var16.x);
            if (!var9) {
               SecP128R1Field.multiply(var16.x, var4.x, var16.x);
            }

            return new SecP128R1Point(var1, var14, var15, new ECFieldElement[]{var16}, this.withCompression);
         }
      }
   }

   public ECPoint twicePlus(ECPoint var1) {
      if (this == var1) {
         return this.threeTimes();
      } else if (this.isInfinity()) {
         return var1;
      } else if (var1.isInfinity()) {
         return this.twice();
      } else {
         ECFieldElement var2 = this.y;
         return var2.isZero() ? var1 : this.twice().add(var1);
      }
   }

   public ECPoint threeTimes() {
      return (ECPoint)(!this.isInfinity() && !this.y.isZero() ? this.twice().add(this) : this);
   }

   public ECPoint negate() {
      return this.isInfinity() ? this : new SecP128R1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
   }
}
