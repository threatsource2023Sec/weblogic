package org.python.bouncycastle.math.ec.custom.sec;

import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat160;

public class SecP160K1Point extends ECPoint.AbstractFp {
   /** @deprecated */
   public SecP160K1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
      this(var1, var2, var3, false);
   }

   /** @deprecated */
   public SecP160K1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
      super(var1, var2, var3);
      if (var2 == null != (var3 == null)) {
         throw new IllegalArgumentException("Exactly one of the field elements is null");
      } else {
         this.withCompression = var4;
      }
   }

   SecP160K1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
      super(var1, var2, var3, var4);
      this.withCompression = var5;
   }

   protected ECPoint detach() {
      return new SecP160K1Point((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
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
         SecP160R2FieldElement var3 = (SecP160R2FieldElement)this.x;
         SecP160R2FieldElement var4 = (SecP160R2FieldElement)this.y;
         SecP160R2FieldElement var5 = (SecP160R2FieldElement)var1.getXCoord();
         SecP160R2FieldElement var6 = (SecP160R2FieldElement)var1.getYCoord();
         SecP160R2FieldElement var7 = (SecP160R2FieldElement)this.zs[0];
         SecP160R2FieldElement var8 = (SecP160R2FieldElement)var1.getZCoord(0);
         int[] var9 = Nat160.createExt();
         int[] var10 = Nat160.create();
         int[] var11 = Nat160.create();
         int[] var12 = Nat160.create();
         boolean var13 = var7.isOne();
         int[] var14;
         int[] var15;
         if (var13) {
            var14 = var5.x;
            var15 = var6.x;
         } else {
            var15 = var11;
            SecP160R2Field.square(var7.x, var11);
            var14 = var10;
            SecP160R2Field.multiply(var11, var5.x, var10);
            SecP160R2Field.multiply(var11, var7.x, var11);
            SecP160R2Field.multiply(var11, var6.x, var11);
         }

         boolean var16 = var8.isOne();
         int[] var17;
         int[] var18;
         if (var16) {
            var17 = var3.x;
            var18 = var4.x;
         } else {
            var18 = var12;
            SecP160R2Field.square(var8.x, var12);
            var17 = var9;
            SecP160R2Field.multiply(var12, var3.x, var9);
            SecP160R2Field.multiply(var12, var8.x, var12);
            SecP160R2Field.multiply(var12, var4.x, var12);
         }

         int[] var19 = Nat160.create();
         SecP160R2Field.subtract(var17, var14, var19);
         SecP160R2Field.subtract(var18, var15, var10);
         if (Nat160.isZero(var19)) {
            return Nat160.isZero(var10) ? this.twice() : var2.getInfinity();
         } else {
            SecP160R2Field.square(var19, var11);
            int[] var22 = Nat160.create();
            SecP160R2Field.multiply(var11, var19, var22);
            SecP160R2Field.multiply(var11, var17, var11);
            SecP160R2Field.negate(var22, var22);
            Nat160.mul(var18, var22, var9);
            int var24 = Nat160.addBothTo(var11, var11, var22);
            SecP160R2Field.reduce32(var24, var22);
            SecP160R2FieldElement var25 = new SecP160R2FieldElement(var12);
            SecP160R2Field.square(var10, var25.x);
            SecP160R2Field.subtract(var25.x, var22, var25.x);
            SecP160R2FieldElement var26 = new SecP160R2FieldElement(var22);
            SecP160R2Field.subtract(var11, var25.x, var26.x);
            SecP160R2Field.multiplyAddToExt(var26.x, var10, var9);
            SecP160R2Field.reduce(var9, var26.x);
            SecP160R2FieldElement var27 = new SecP160R2FieldElement(var19);
            if (!var13) {
               SecP160R2Field.multiply(var27.x, var7.x, var27.x);
            }

            if (!var16) {
               SecP160R2Field.multiply(var27.x, var8.x, var27.x);
            }

            ECFieldElement[] var28 = new ECFieldElement[]{var27};
            return new SecP160K1Point(var2, var25, var26, var28, this.withCompression);
         }
      }
   }

   public ECPoint twice() {
      if (this.isInfinity()) {
         return this;
      } else {
         ECCurve var1 = this.getCurve();
         SecP160R2FieldElement var2 = (SecP160R2FieldElement)this.y;
         if (var2.isZero()) {
            return var1.getInfinity();
         } else {
            SecP160R2FieldElement var3 = (SecP160R2FieldElement)this.x;
            SecP160R2FieldElement var4 = (SecP160R2FieldElement)this.zs[0];
            int[] var5 = Nat160.create();
            SecP160R2Field.square(var2.x, var5);
            int[] var6 = Nat160.create();
            SecP160R2Field.square(var5, var6);
            int[] var7 = Nat160.create();
            SecP160R2Field.square(var3.x, var7);
            int var8 = Nat160.addBothTo(var7, var7, var7);
            SecP160R2Field.reduce32(var8, var7);
            SecP160R2Field.multiply(var5, var3.x, var5);
            var8 = Nat.shiftUpBits(5, var5, 2, 0);
            SecP160R2Field.reduce32(var8, var5);
            int[] var10 = Nat160.create();
            var8 = Nat.shiftUpBits(5, var6, 3, 0, var10);
            SecP160R2Field.reduce32(var8, var10);
            SecP160R2FieldElement var11 = new SecP160R2FieldElement(var6);
            SecP160R2Field.square(var7, var11.x);
            SecP160R2Field.subtract(var11.x, var5, var11.x);
            SecP160R2Field.subtract(var11.x, var5, var11.x);
            SecP160R2FieldElement var12 = new SecP160R2FieldElement(var5);
            SecP160R2Field.subtract(var5, var11.x, var12.x);
            SecP160R2Field.multiply(var12.x, var7, var12.x);
            SecP160R2Field.subtract(var12.x, var10, var12.x);
            SecP160R2FieldElement var13 = new SecP160R2FieldElement(var7);
            SecP160R2Field.twice(var2.x, var13.x);
            if (!var4.isOne()) {
               SecP160R2Field.multiply(var13.x, var4.x, var13.x);
            }

            return new SecP160K1Point(var1, var11, var12, new ECFieldElement[]{var13}, this.withCompression);
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
      return this.isInfinity() ? this : new SecP160K1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
   }
}
