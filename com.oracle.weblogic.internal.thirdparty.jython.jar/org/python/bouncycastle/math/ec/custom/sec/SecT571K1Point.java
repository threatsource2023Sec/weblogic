package org.python.bouncycastle.math.ec.custom.sec;

import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.raw.Nat576;

public class SecT571K1Point extends ECPoint.AbstractF2m {
   /** @deprecated */
   public SecT571K1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
      this(var1, var2, var3, false);
   }

   /** @deprecated */
   public SecT571K1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
      super(var1, var2, var3);
      if (var2 == null != (var3 == null)) {
         throw new IllegalArgumentException("Exactly one of the field elements is null");
      } else {
         this.withCompression = var4;
      }
   }

   SecT571K1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
      super(var1, var2, var3, var4);
      this.withCompression = var5;
   }

   protected ECPoint detach() {
      return new SecT571K1Point((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
   }

   public ECFieldElement getYCoord() {
      ECFieldElement var1 = this.x;
      ECFieldElement var2 = this.y;
      if (!this.isInfinity() && !var1.isZero()) {
         ECFieldElement var3 = var2.add(var1).multiply(var1);
         ECFieldElement var4 = this.zs[0];
         if (!var4.isOne()) {
            var3 = var3.divide(var4);
         }

         return var3;
      } else {
         return var2;
      }
   }

   protected boolean getCompressionYTilde() {
      ECFieldElement var1 = this.getRawXCoord();
      if (var1.isZero()) {
         return false;
      } else {
         ECFieldElement var2 = this.getRawYCoord();
         return var2.testBitZero() != var1.testBitZero();
      }
   }

   public ECPoint add(ECPoint var1) {
      if (this.isInfinity()) {
         return var1;
      } else if (var1.isInfinity()) {
         return this;
      } else {
         ECCurve var2 = this.getCurve();
         SecT571FieldElement var3 = (SecT571FieldElement)this.x;
         SecT571FieldElement var4 = (SecT571FieldElement)var1.getRawXCoord();
         if (var3.isZero()) {
            return var4.isZero() ? var2.getInfinity() : var1.add(this);
         } else {
            SecT571FieldElement var5 = (SecT571FieldElement)this.y;
            SecT571FieldElement var6 = (SecT571FieldElement)this.zs[0];
            SecT571FieldElement var7 = (SecT571FieldElement)var1.getRawYCoord();
            SecT571FieldElement var8 = (SecT571FieldElement)var1.getZCoord(0);
            long[] var9 = Nat576.create64();
            long[] var10 = Nat576.create64();
            long[] var11 = Nat576.create64();
            long[] var12 = Nat576.create64();
            long[] var13 = var6.isOne() ? null : SecT571Field.precompMultiplicand(var6.x);
            long[] var14;
            long[] var15;
            if (var13 == null) {
               var14 = var4.x;
               var15 = var7.x;
            } else {
               var14 = var10;
               SecT571Field.multiplyPrecomp(var4.x, var13, var10);
               var15 = var12;
               SecT571Field.multiplyPrecomp(var7.x, var13, var12);
            }

            long[] var16 = var8.isOne() ? null : SecT571Field.precompMultiplicand(var8.x);
            long[] var17;
            long[] var18;
            if (var16 == null) {
               var17 = var3.x;
               var18 = var5.x;
            } else {
               var17 = var9;
               SecT571Field.multiplyPrecomp(var3.x, var16, var9);
               var18 = var11;
               SecT571Field.multiplyPrecomp(var5.x, var16, var11);
            }

            SecT571Field.add(var18, var15, var11);
            SecT571Field.add(var17, var14, var12);
            if (Nat576.isZero64(var12)) {
               return Nat576.isZero64(var11) ? this.twice() : var2.getInfinity();
            } else {
               SecT571FieldElement var25;
               SecT571FieldElement var27;
               SecT571FieldElement var28;
               if (var4.isZero()) {
                  ECPoint var21 = this.normalize();
                  var3 = (SecT571FieldElement)var21.getXCoord();
                  ECFieldElement var22 = var21.getYCoord();
                  ECFieldElement var24 = var22.add(var7).divide(var3);
                  var25 = (SecT571FieldElement)var24.square().add(var24).add(var3);
                  if (var25.isZero()) {
                     return new SecT571K1Point(var2, var25, var2.getB(), this.withCompression);
                  }

                  ECFieldElement var26 = var24.multiply(var3.add(var25)).add(var25).add(var22);
                  var27 = (SecT571FieldElement)var26.divide(var25).add(var25);
                  var28 = (SecT571FieldElement)var2.fromBigInteger(ECConstants.ONE);
               } else {
                  SecT571Field.square(var12, var12);
                  long[] var29 = SecT571Field.precompMultiplicand(var11);
                  SecT571Field.multiplyPrecomp(var17, var29, var9);
                  SecT571Field.multiplyPrecomp(var14, var29, var10);
                  var25 = new SecT571FieldElement(var9);
                  SecT571Field.multiply(var9, var10, var25.x);
                  if (var25.isZero()) {
                     return new SecT571K1Point(var2, var25, var2.getB(), this.withCompression);
                  }

                  var28 = new SecT571FieldElement(var11);
                  SecT571Field.multiplyPrecomp(var12, var29, var28.x);
                  if (var16 != null) {
                     SecT571Field.multiplyPrecomp(var28.x, var16, var28.x);
                  }

                  long[] var30 = Nat576.createExt64();
                  SecT571Field.add(var10, var12, var12);
                  SecT571Field.squareAddToExt(var12, var30);
                  SecT571Field.add(var5.x, var6.x, var12);
                  SecT571Field.multiplyAddToExt(var12, var28.x, var30);
                  var27 = new SecT571FieldElement(var12);
                  SecT571Field.reduce(var30, var27.x);
                  if (var13 != null) {
                     SecT571Field.multiplyPrecomp(var28.x, var13, var28.x);
                  }
               }

               return new SecT571K1Point(var2, var25, var27, new ECFieldElement[]{var28}, this.withCompression);
            }
         }
      }
   }

   public ECPoint twice() {
      if (this.isInfinity()) {
         return this;
      } else {
         ECCurve var1 = this.getCurve();
         ECFieldElement var2 = this.x;
         if (var2.isZero()) {
            return var1.getInfinity();
         } else {
            ECFieldElement var3 = this.y;
            ECFieldElement var4 = this.zs[0];
            boolean var5 = var4.isOne();
            ECFieldElement var6 = var5 ? var4 : var4.square();
            ECFieldElement var7;
            if (var5) {
               var7 = var3.square().add(var3);
            } else {
               var7 = var3.add(var4).multiply(var3);
            }

            if (var7.isZero()) {
               return new SecT571K1Point(var1, var7, var1.getB(), this.withCompression);
            } else {
               ECFieldElement var8 = var7.square();
               ECFieldElement var9 = var5 ? var7 : var7.multiply(var6);
               ECFieldElement var10 = var3.add(var2).square();
               ECFieldElement var11 = var5 ? var4 : var6.square();
               ECFieldElement var12 = var10.add(var7).add(var6).multiply(var10).add(var11).add(var8).add(var9);
               return new SecT571K1Point(var1, var8, var12, new ECFieldElement[]{var9}, this.withCompression);
            }
         }
      }
   }

   public ECPoint twicePlus(ECPoint var1) {
      if (this.isInfinity()) {
         return var1;
      } else if (var1.isInfinity()) {
         return this.twice();
      } else {
         ECCurve var2 = this.getCurve();
         ECFieldElement var3 = this.x;
         if (var3.isZero()) {
            return var1;
         } else {
            ECFieldElement var4 = var1.getRawXCoord();
            ECFieldElement var5 = var1.getZCoord(0);
            if (!var4.isZero() && var5.isOne()) {
               ECFieldElement var6 = this.y;
               ECFieldElement var7 = this.zs[0];
               ECFieldElement var8 = var1.getRawYCoord();
               ECFieldElement var9 = var3.square();
               ECFieldElement var10 = var6.square();
               ECFieldElement var11 = var7.square();
               ECFieldElement var12 = var6.multiply(var7);
               ECFieldElement var13 = var10.add(var12);
               ECFieldElement var14 = var8.addOne();
               ECFieldElement var15 = var14.multiply(var11).add(var10).multiplyPlusProduct(var13, var9, var11);
               ECFieldElement var16 = var4.multiply(var11);
               ECFieldElement var17 = var16.add(var13).square();
               if (var17.isZero()) {
                  return var15.isZero() ? var1.twice() : var2.getInfinity();
               } else if (var15.isZero()) {
                  return new SecT571K1Point(var2, var15, var2.getB(), this.withCompression);
               } else {
                  ECFieldElement var18 = var15.square().multiply(var16);
                  ECFieldElement var19 = var15.multiply(var17).multiply(var11);
                  ECFieldElement var20 = var15.add(var17).square().multiplyPlusProduct(var13, var14, var19);
                  return new SecT571K1Point(var2, var18, var20, new ECFieldElement[]{var19}, this.withCompression);
               }
            } else {
               return this.twice().add(var1);
            }
         }
      }
   }

   public ECPoint negate() {
      if (this.isInfinity()) {
         return this;
      } else {
         ECFieldElement var1 = this.x;
         if (var1.isZero()) {
            return this;
         } else {
            ECFieldElement var2 = this.y;
            ECFieldElement var3 = this.zs[0];
            return new SecT571K1Point(this.curve, var1, var2.add(var3), new ECFieldElement[]{var3}, this.withCompression);
         }
      }
   }
}
