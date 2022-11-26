package org.python.bouncycastle.math.ec.custom.djb;

import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.raw.Nat256;

public class Curve25519Point extends ECPoint.AbstractFp {
   /** @deprecated */
   public Curve25519Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
      this(var1, var2, var3, false);
   }

   /** @deprecated */
   public Curve25519Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
      super(var1, var2, var3);
      if (var2 == null != (var3 == null)) {
         throw new IllegalArgumentException("Exactly one of the field elements is null");
      } else {
         this.withCompression = var4;
      }
   }

   Curve25519Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
      super(var1, var2, var3, var4);
      this.withCompression = var5;
   }

   protected ECPoint detach() {
      return new Curve25519Point((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
   }

   public ECFieldElement getZCoord(int var1) {
      return (ECFieldElement)(var1 == 1 ? this.getJacobianModifiedW() : super.getZCoord(var1));
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
         Curve25519FieldElement var3 = (Curve25519FieldElement)this.x;
         Curve25519FieldElement var4 = (Curve25519FieldElement)this.y;
         Curve25519FieldElement var5 = (Curve25519FieldElement)this.zs[0];
         Curve25519FieldElement var6 = (Curve25519FieldElement)var1.getXCoord();
         Curve25519FieldElement var7 = (Curve25519FieldElement)var1.getYCoord();
         Curve25519FieldElement var8 = (Curve25519FieldElement)var1.getZCoord(0);
         int[] var9 = Nat256.createExt();
         int[] var10 = Nat256.create();
         int[] var11 = Nat256.create();
         int[] var12 = Nat256.create();
         boolean var13 = var5.isOne();
         int[] var14;
         int[] var15;
         if (var13) {
            var14 = var6.x;
            var15 = var7.x;
         } else {
            var15 = var11;
            Curve25519Field.square(var5.x, var11);
            var14 = var10;
            Curve25519Field.multiply(var11, var6.x, var10);
            Curve25519Field.multiply(var11, var5.x, var11);
            Curve25519Field.multiply(var11, var7.x, var11);
         }

         boolean var16 = var8.isOne();
         int[] var17;
         int[] var18;
         if (var16) {
            var17 = var3.x;
            var18 = var4.x;
         } else {
            var18 = var12;
            Curve25519Field.square(var8.x, var12);
            var17 = var9;
            Curve25519Field.multiply(var12, var3.x, var9);
            Curve25519Field.multiply(var12, var8.x, var12);
            Curve25519Field.multiply(var12, var4.x, var12);
         }

         int[] var19 = Nat256.create();
         Curve25519Field.subtract(var17, var14, var19);
         Curve25519Field.subtract(var18, var15, var10);
         if (Nat256.isZero(var19)) {
            return Nat256.isZero(var10) ? this.twice() : var2.getInfinity();
         } else {
            int[] var21 = Nat256.create();
            Curve25519Field.square(var19, var21);
            int[] var22 = Nat256.create();
            Curve25519Field.multiply(var21, var19, var22);
            Curve25519Field.multiply(var21, var17, var11);
            Curve25519Field.negate(var22, var22);
            Nat256.mul(var18, var22, var9);
            int var24 = Nat256.addBothTo(var11, var11, var22);
            Curve25519Field.reduce27(var24, var22);
            Curve25519FieldElement var25 = new Curve25519FieldElement(var12);
            Curve25519Field.square(var10, var25.x);
            Curve25519Field.subtract(var25.x, var22, var25.x);
            Curve25519FieldElement var26 = new Curve25519FieldElement(var22);
            Curve25519Field.subtract(var11, var25.x, var26.x);
            Curve25519Field.multiplyAddToExt(var26.x, var10, var9);
            Curve25519Field.reduce(var9, var26.x);
            Curve25519FieldElement var27 = new Curve25519FieldElement(var19);
            if (!var13) {
               Curve25519Field.multiply(var27.x, var5.x, var27.x);
            }

            if (!var16) {
               Curve25519Field.multiply(var27.x, var8.x, var27.x);
            }

            int[] var28 = var13 && var16 ? var21 : null;
            Curve25519FieldElement var29 = this.calculateJacobianModifiedW(var27, var28);
            ECFieldElement[] var30 = new ECFieldElement[]{var27, var29};
            return new Curve25519Point(var2, var25, var26, var30, this.withCompression);
         }
      }
   }

   public ECPoint twice() {
      if (this.isInfinity()) {
         return this;
      } else {
         ECCurve var1 = this.getCurve();
         ECFieldElement var2 = this.y;
         return (ECPoint)(var2.isZero() ? var1.getInfinity() : this.twiceJacobianModified(true));
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
         return var2.isZero() ? var1 : this.twiceJacobianModified(false).add(var1);
      }
   }

   public ECPoint threeTimes() {
      if (this.isInfinity()) {
         return this;
      } else {
         ECFieldElement var1 = this.y;
         return (ECPoint)(var1.isZero() ? this : this.twiceJacobianModified(false).add(this));
      }
   }

   public ECPoint negate() {
      return this.isInfinity() ? this : new Curve25519Point(this.getCurve(), this.x, this.y.negate(), this.zs, this.withCompression);
   }

   protected Curve25519FieldElement calculateJacobianModifiedW(Curve25519FieldElement var1, int[] var2) {
      Curve25519FieldElement var3 = (Curve25519FieldElement)this.getCurve().getA();
      if (var1.isOne()) {
         return var3;
      } else {
         Curve25519FieldElement var4 = new Curve25519FieldElement();
         if (var2 == null) {
            var2 = var4.x;
            Curve25519Field.square(var1.x, var2);
         }

         Curve25519Field.square(var2, var4.x);
         Curve25519Field.multiply(var4.x, var3.x, var4.x);
         return var4;
      }
   }

   protected Curve25519FieldElement getJacobianModifiedW() {
      Curve25519FieldElement var1 = (Curve25519FieldElement)this.zs[1];
      if (var1 == null) {
         this.zs[1] = var1 = this.calculateJacobianModifiedW((Curve25519FieldElement)this.zs[0], (int[])null);
      }

      return var1;
   }

   protected Curve25519Point twiceJacobianModified(boolean var1) {
      Curve25519FieldElement var2 = (Curve25519FieldElement)this.x;
      Curve25519FieldElement var3 = (Curve25519FieldElement)this.y;
      Curve25519FieldElement var4 = (Curve25519FieldElement)this.zs[0];
      Curve25519FieldElement var5 = this.getJacobianModifiedW();
      int[] var6 = Nat256.create();
      Curve25519Field.square(var2.x, var6);
      int var7 = Nat256.addBothTo(var6, var6, var6);
      var7 += Nat256.addTo(var5.x, var6);
      Curve25519Field.reduce27(var7, var6);
      int[] var8 = Nat256.create();
      Curve25519Field.twice(var3.x, var8);
      int[] var9 = Nat256.create();
      Curve25519Field.multiply(var8, var3.x, var9);
      int[] var10 = Nat256.create();
      Curve25519Field.multiply(var9, var2.x, var10);
      Curve25519Field.twice(var10, var10);
      int[] var11 = Nat256.create();
      Curve25519Field.square(var9, var11);
      Curve25519Field.twice(var11, var11);
      Curve25519FieldElement var12 = new Curve25519FieldElement(var9);
      Curve25519Field.square(var6, var12.x);
      Curve25519Field.subtract(var12.x, var10, var12.x);
      Curve25519Field.subtract(var12.x, var10, var12.x);
      Curve25519FieldElement var13 = new Curve25519FieldElement(var10);
      Curve25519Field.subtract(var10, var12.x, var13.x);
      Curve25519Field.multiply(var13.x, var6, var13.x);
      Curve25519Field.subtract(var13.x, var11, var13.x);
      Curve25519FieldElement var14 = new Curve25519FieldElement(var8);
      if (!Nat256.isOne(var4.x)) {
         Curve25519Field.multiply(var14.x, var4.x, var14.x);
      }

      Curve25519FieldElement var15 = null;
      if (var1) {
         var15 = new Curve25519FieldElement(var11);
         Curve25519Field.multiply(var15.x, var5.x, var15.x);
         Curve25519Field.twice(var15.x, var15.x);
      }

      return new Curve25519Point(this.getCurve(), var12, var13, new ECFieldElement[]{var14, var15}, this.withCompression);
   }
}
