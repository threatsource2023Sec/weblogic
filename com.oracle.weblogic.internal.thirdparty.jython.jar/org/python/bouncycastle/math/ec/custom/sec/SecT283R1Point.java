package org.python.bouncycastle.math.ec.custom.sec;

import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;

public class SecT283R1Point extends ECPoint.AbstractF2m {
   /** @deprecated */
   public SecT283R1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
      this(var1, var2, var3, false);
   }

   /** @deprecated */
   public SecT283R1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
      super(var1, var2, var3);
      if (var2 == null != (var3 == null)) {
         throw new IllegalArgumentException("Exactly one of the field elements is null");
      } else {
         this.withCompression = var4;
      }
   }

   SecT283R1Point(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
      super(var1, var2, var3, var4);
      this.withCompression = var5;
   }

   protected ECPoint detach() {
      return new SecT283R1Point((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
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
         ECFieldElement var3 = this.x;
         ECFieldElement var4 = var1.getRawXCoord();
         if (var3.isZero()) {
            return var4.isZero() ? var2.getInfinity() : var1.add(this);
         } else {
            ECFieldElement var5 = this.y;
            ECFieldElement var6 = this.zs[0];
            ECFieldElement var7 = var1.getRawYCoord();
            ECFieldElement var8 = var1.getZCoord(0);
            boolean var9 = var6.isOne();
            ECFieldElement var10 = var4;
            ECFieldElement var11 = var7;
            if (!var9) {
               var10 = var4.multiply(var6);
               var11 = var7.multiply(var6);
            }

            boolean var12 = var8.isOne();
            ECFieldElement var13 = var3;
            ECFieldElement var14 = var5;
            if (!var12) {
               var13 = var3.multiply(var8);
               var14 = var5.multiply(var8);
            }

            ECFieldElement var15 = var14.add(var11);
            ECFieldElement var16 = var13.add(var10);
            if (var16.isZero()) {
               return var15.isZero() ? this.twice() : var2.getInfinity();
            } else {
               ECFieldElement var18;
               ECFieldElement var21;
               ECFieldElement var23;
               ECFieldElement var24;
               if (var4.isZero()) {
                  ECPoint var17 = this.normalize();
                  var3 = var17.getXCoord();
                  var18 = var17.getYCoord();
                  ECFieldElement var20 = var18.add(var7).divide(var3);
                  var21 = var20.square().add(var20).add(var3).addOne();
                  if (var21.isZero()) {
                     return new SecT283R1Point(var2, var21, var2.getB().sqrt(), this.withCompression);
                  }

                  ECFieldElement var22 = var20.multiply(var3.add(var21)).add(var21).add(var18);
                  var23 = var22.divide(var21).add(var21);
                  var24 = var2.fromBigInteger(ECConstants.ONE);
               } else {
                  var16 = var16.square();
                  ECFieldElement var25 = var15.multiply(var13);
                  var18 = var15.multiply(var10);
                  var21 = var25.multiply(var18);
                  if (var21.isZero()) {
                     return new SecT283R1Point(var2, var21, var2.getB().sqrt(), this.withCompression);
                  }

                  ECFieldElement var19 = var15.multiply(var16);
                  if (!var12) {
                     var19 = var19.multiply(var8);
                  }

                  var23 = var18.add(var16).squarePlusProduct(var19, var5.add(var6));
                  var24 = var19;
                  if (!var9) {
                     var24 = var19.multiply(var6);
                  }
               }

               return new SecT283R1Point(var2, var21, var23, new ECFieldElement[]{var24}, this.withCompression);
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
            ECFieldElement var6 = var5 ? var3 : var3.multiply(var4);
            ECFieldElement var7 = var5 ? var4 : var4.square();
            ECFieldElement var8 = var3.square().add(var6).add(var7);
            if (var8.isZero()) {
               return new SecT283R1Point(var1, var8, var1.getB().sqrt(), this.withCompression);
            } else {
               ECFieldElement var9 = var8.square();
               ECFieldElement var10 = var5 ? var8 : var8.multiply(var7);
               ECFieldElement var11 = var5 ? var2 : var2.multiply(var4);
               ECFieldElement var12 = var11.squarePlusProduct(var8, var6).add(var9).add(var10);
               return new SecT283R1Point(var1, var9, var12, new ECFieldElement[]{var10}, this.withCompression);
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
               ECFieldElement var13 = var11.add(var10).add(var12);
               ECFieldElement var14 = var8.multiply(var11).add(var10).multiplyPlusProduct(var13, var9, var11);
               ECFieldElement var15 = var4.multiply(var11);
               ECFieldElement var16 = var15.add(var13).square();
               if (var16.isZero()) {
                  return var14.isZero() ? var1.twice() : var2.getInfinity();
               } else if (var14.isZero()) {
                  return new SecT283R1Point(var2, var14, var2.getB().sqrt(), this.withCompression);
               } else {
                  ECFieldElement var17 = var14.square().multiply(var15);
                  ECFieldElement var18 = var14.multiply(var16).multiply(var11);
                  ECFieldElement var19 = var14.add(var16).square().multiplyPlusProduct(var13, var8.addOne(), var18);
                  return new SecT283R1Point(var2, var17, var19, new ECFieldElement[]{var18}, this.withCompression);
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
            return new SecT283R1Point(this.curve, var1, var2.add(var3), new ECFieldElement[]{var3}, this.withCompression);
         }
      }
   }
}
