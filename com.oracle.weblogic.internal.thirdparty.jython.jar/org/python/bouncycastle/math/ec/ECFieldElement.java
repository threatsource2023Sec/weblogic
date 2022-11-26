package org.python.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import org.python.bouncycastle.math.raw.Mod;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;

public abstract class ECFieldElement implements ECConstants {
   public abstract BigInteger toBigInteger();

   public abstract String getFieldName();

   public abstract int getFieldSize();

   public abstract ECFieldElement add(ECFieldElement var1);

   public abstract ECFieldElement addOne();

   public abstract ECFieldElement subtract(ECFieldElement var1);

   public abstract ECFieldElement multiply(ECFieldElement var1);

   public abstract ECFieldElement divide(ECFieldElement var1);

   public abstract ECFieldElement negate();

   public abstract ECFieldElement square();

   public abstract ECFieldElement invert();

   public abstract ECFieldElement sqrt();

   public int bitLength() {
      return this.toBigInteger().bitLength();
   }

   public boolean isOne() {
      return this.bitLength() == 1;
   }

   public boolean isZero() {
      return 0 == this.toBigInteger().signum();
   }

   public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      return this.multiply(var1).subtract(var2.multiply(var3));
   }

   public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      return this.multiply(var1).add(var2.multiply(var3));
   }

   public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
      return this.square().subtract(var1.multiply(var2));
   }

   public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
      return this.square().add(var1.multiply(var2));
   }

   public ECFieldElement squarePow(int var1) {
      ECFieldElement var2 = this;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 = var2.square();
      }

      return var2;
   }

   public boolean testBitZero() {
      return this.toBigInteger().testBit(0);
   }

   public String toString() {
      return this.toBigInteger().toString(16);
   }

   public byte[] getEncoded() {
      return BigIntegers.asUnsignedByteArray((this.getFieldSize() + 7) / 8, this.toBigInteger());
   }

   public static class F2m extends ECFieldElement {
      public static final int GNB = 1;
      public static final int TPB = 2;
      public static final int PPB = 3;
      private int representation;
      private int m;
      private int[] ks;
      private LongArray x;

      /** @deprecated */
      public F2m(int var1, int var2, int var3, int var4, BigInteger var5) {
         if (var5 != null && var5.signum() >= 0 && var5.bitLength() <= var1) {
            if (var3 == 0 && var4 == 0) {
               this.representation = 2;
               this.ks = new int[]{var2};
            } else {
               if (var3 >= var4) {
                  throw new IllegalArgumentException("k2 must be smaller than k3");
               }

               if (var3 <= 0) {
                  throw new IllegalArgumentException("k2 must be larger than 0");
               }

               this.representation = 3;
               this.ks = new int[]{var2, var3, var4};
            }

            this.m = var1;
            this.x = new LongArray(var5);
         } else {
            throw new IllegalArgumentException("x value invalid in F2m field element");
         }
      }

      /** @deprecated */
      public F2m(int var1, int var2, BigInteger var3) {
         this(var1, var2, 0, 0, var3);
      }

      private F2m(int var1, int[] var2, LongArray var3) {
         this.m = var1;
         this.representation = var2.length == 1 ? 2 : 3;
         this.ks = var2;
         this.x = var3;
      }

      public int bitLength() {
         return this.x.degree();
      }

      public boolean isOne() {
         return this.x.isOne();
      }

      public boolean isZero() {
         return this.x.isZero();
      }

      public boolean testBitZero() {
         return this.x.testBitZero();
      }

      public BigInteger toBigInteger() {
         return this.x.toBigInteger();
      }

      public String getFieldName() {
         return "F2m";
      }

      public int getFieldSize() {
         return this.m;
      }

      public static void checkFieldElements(ECFieldElement var0, ECFieldElement var1) {
         if (var0 instanceof F2m && var1 instanceof F2m) {
            F2m var2 = (F2m)var0;
            F2m var3 = (F2m)var1;
            if (var2.representation != var3.representation) {
               throw new IllegalArgumentException("One of the F2m field elements has incorrect representation");
            } else if (var2.m != var3.m || !Arrays.areEqual(var2.ks, var3.ks)) {
               throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
            }
         } else {
            throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
         }
      }

      public ECFieldElement add(ECFieldElement var1) {
         LongArray var2 = (LongArray)this.x.clone();
         F2m var3 = (F2m)var1;
         var2.addShiftedByWords(var3.x, 0);
         return new F2m(this.m, this.ks, var2);
      }

      public ECFieldElement addOne() {
         return new F2m(this.m, this.ks, this.x.addOne());
      }

      public ECFieldElement subtract(ECFieldElement var1) {
         return this.add(var1);
      }

      public ECFieldElement multiply(ECFieldElement var1) {
         return new F2m(this.m, this.ks, this.x.modMultiply(((F2m)var1).x, this.m, this.ks));
      }

      public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
         return this.multiplyPlusProduct(var1, var2, var3);
      }

      public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
         LongArray var4 = this.x;
         LongArray var5 = ((F2m)var1).x;
         LongArray var6 = ((F2m)var2).x;
         LongArray var7 = ((F2m)var3).x;
         LongArray var8 = var4.multiply(var5, this.m, this.ks);
         LongArray var9 = var6.multiply(var7, this.m, this.ks);
         if (var8 == var4 || var8 == var5) {
            var8 = (LongArray)var8.clone();
         }

         var8.addShiftedByWords(var9, 0);
         var8.reduce(this.m, this.ks);
         return new F2m(this.m, this.ks, var8);
      }

      public ECFieldElement divide(ECFieldElement var1) {
         ECFieldElement var2 = var1.invert();
         return this.multiply(var2);
      }

      public ECFieldElement negate() {
         return this;
      }

      public ECFieldElement square() {
         return new F2m(this.m, this.ks, this.x.modSquare(this.m, this.ks));
      }

      public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
         return this.squarePlusProduct(var1, var2);
      }

      public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
         LongArray var3 = this.x;
         LongArray var4 = ((F2m)var1).x;
         LongArray var5 = ((F2m)var2).x;
         LongArray var6 = var3.square(this.m, this.ks);
         LongArray var7 = var4.multiply(var5, this.m, this.ks);
         if (var6 == var3) {
            var6 = (LongArray)var6.clone();
         }

         var6.addShiftedByWords(var7, 0);
         var6.reduce(this.m, this.ks);
         return new F2m(this.m, this.ks, var6);
      }

      public ECFieldElement squarePow(int var1) {
         return var1 < 1 ? this : new F2m(this.m, this.ks, this.x.modSquareN(var1, this.m, this.ks));
      }

      public ECFieldElement invert() {
         return new F2m(this.m, this.ks, this.x.modInverse(this.m, this.ks));
      }

      public ECFieldElement sqrt() {
         return (ECFieldElement)(!this.x.isZero() && !this.x.isOne() ? this.squarePow(this.m - 1) : this);
      }

      public int getRepresentation() {
         return this.representation;
      }

      public int getM() {
         return this.m;
      }

      public int getK1() {
         return this.ks[0];
      }

      public int getK2() {
         return this.ks.length >= 2 ? this.ks[1] : 0;
      }

      public int getK3() {
         return this.ks.length >= 3 ? this.ks[2] : 0;
      }

      public boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof F2m)) {
            return false;
         } else {
            F2m var2 = (F2m)var1;
            return this.m == var2.m && this.representation == var2.representation && Arrays.areEqual(this.ks, var2.ks) && this.x.equals(var2.x);
         }
      }

      public int hashCode() {
         return this.x.hashCode() ^ this.m ^ Arrays.hashCode(this.ks);
      }
   }

   public static class Fp extends ECFieldElement {
      BigInteger q;
      BigInteger r;
      BigInteger x;

      static BigInteger calculateResidue(BigInteger var0) {
         int var1 = var0.bitLength();
         if (var1 >= 96) {
            BigInteger var2 = var0.shiftRight(var1 - 64);
            if (var2.longValue() == -1L) {
               return ONE.shiftLeft(var1).subtract(var0);
            }
         }

         return null;
      }

      /** @deprecated */
      public Fp(BigInteger var1, BigInteger var2) {
         this(var1, calculateResidue(var1), var2);
      }

      Fp(BigInteger var1, BigInteger var2, BigInteger var3) {
         if (var3 != null && var3.signum() >= 0 && var3.compareTo(var1) < 0) {
            this.q = var1;
            this.r = var2;
            this.x = var3;
         } else {
            throw new IllegalArgumentException("x value invalid in Fp field element");
         }
      }

      public BigInteger toBigInteger() {
         return this.x;
      }

      public String getFieldName() {
         return "Fp";
      }

      public int getFieldSize() {
         return this.q.bitLength();
      }

      public BigInteger getQ() {
         return this.q;
      }

      public ECFieldElement add(ECFieldElement var1) {
         return new Fp(this.q, this.r, this.modAdd(this.x, var1.toBigInteger()));
      }

      public ECFieldElement addOne() {
         BigInteger var1 = this.x.add(ECConstants.ONE);
         if (var1.compareTo(this.q) == 0) {
            var1 = ECConstants.ZERO;
         }

         return new Fp(this.q, this.r, var1);
      }

      public ECFieldElement subtract(ECFieldElement var1) {
         return new Fp(this.q, this.r, this.modSubtract(this.x, var1.toBigInteger()));
      }

      public ECFieldElement multiply(ECFieldElement var1) {
         return new Fp(this.q, this.r, this.modMult(this.x, var1.toBigInteger()));
      }

      public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
         BigInteger var4 = this.x;
         BigInteger var5 = var1.toBigInteger();
         BigInteger var6 = var2.toBigInteger();
         BigInteger var7 = var3.toBigInteger();
         BigInteger var8 = var4.multiply(var5);
         BigInteger var9 = var6.multiply(var7);
         return new Fp(this.q, this.r, this.modReduce(var8.subtract(var9)));
      }

      public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
         BigInteger var4 = this.x;
         BigInteger var5 = var1.toBigInteger();
         BigInteger var6 = var2.toBigInteger();
         BigInteger var7 = var3.toBigInteger();
         BigInteger var8 = var4.multiply(var5);
         BigInteger var9 = var6.multiply(var7);
         return new Fp(this.q, this.r, this.modReduce(var8.add(var9)));
      }

      public ECFieldElement divide(ECFieldElement var1) {
         return new Fp(this.q, this.r, this.modMult(this.x, this.modInverse(var1.toBigInteger())));
      }

      public ECFieldElement negate() {
         return this.x.signum() == 0 ? this : new Fp(this.q, this.r, this.q.subtract(this.x));
      }

      public ECFieldElement square() {
         return new Fp(this.q, this.r, this.modMult(this.x, this.x));
      }

      public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
         BigInteger var3 = this.x;
         BigInteger var4 = var1.toBigInteger();
         BigInteger var5 = var2.toBigInteger();
         BigInteger var6 = var3.multiply(var3);
         BigInteger var7 = var4.multiply(var5);
         return new Fp(this.q, this.r, this.modReduce(var6.subtract(var7)));
      }

      public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
         BigInteger var3 = this.x;
         BigInteger var4 = var1.toBigInteger();
         BigInteger var5 = var2.toBigInteger();
         BigInteger var6 = var3.multiply(var3);
         BigInteger var7 = var4.multiply(var5);
         return new Fp(this.q, this.r, this.modReduce(var6.add(var7)));
      }

      public ECFieldElement invert() {
         return new Fp(this.q, this.r, this.modInverse(this.x));
      }

      public ECFieldElement sqrt() {
         if (!this.isZero() && !this.isOne()) {
            if (!this.q.testBit(0)) {
               throw new RuntimeException("not done yet");
            } else {
               BigInteger var1;
               if (this.q.testBit(1)) {
                  var1 = this.q.shiftRight(2).add(ECConstants.ONE);
                  return this.checkSqrt(new Fp(this.q, this.r, this.x.modPow(var1, this.q)));
               } else {
                  BigInteger var2;
                  BigInteger var3;
                  BigInteger var4;
                  BigInteger var5;
                  if (this.q.testBit(2)) {
                     var1 = this.x.modPow(this.q.shiftRight(3), this.q);
                     var2 = this.modMult(var1, this.x);
                     var3 = this.modMult(var2, var1);
                     if (var3.equals(ECConstants.ONE)) {
                        return this.checkSqrt(new Fp(this.q, this.r, var2));
                     } else {
                        var4 = ECConstants.TWO.modPow(this.q.shiftRight(2), this.q);
                        var5 = this.modMult(var2, var4);
                        return this.checkSqrt(new Fp(this.q, this.r, var5));
                     }
                  } else {
                     var1 = this.q.shiftRight(1);
                     if (!this.x.modPow(var1, this.q).equals(ECConstants.ONE)) {
                        return null;
                     } else {
                        var2 = this.x;
                        var3 = this.modDouble(this.modDouble(var2));
                        var4 = var1.add(ECConstants.ONE);
                        var5 = this.q.subtract(ECConstants.ONE);
                        Random var6 = new Random();

                        while(true) {
                           BigInteger var7;
                           do {
                              var7 = new BigInteger(this.q.bitLength(), var6);
                           } while(var7.compareTo(this.q) >= 0);

                           if (this.modReduce(var7.multiply(var7).subtract(var3)).modPow(var1, this.q).equals(var5)) {
                              BigInteger[] var8 = this.lucasSequence(var7, var2, var4);
                              BigInteger var9 = var8[0];
                              BigInteger var10 = var8[1];
                              if (this.modMult(var10, var10).equals(var3)) {
                                 return new Fp(this.q, this.r, this.modHalfAbs(var10));
                              }

                              if (!var9.equals(ECConstants.ONE) && !var9.equals(var5)) {
                                 return null;
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else {
            return this;
         }
      }

      private ECFieldElement checkSqrt(ECFieldElement var1) {
         return var1.square().equals(this) ? var1 : null;
      }

      private BigInteger[] lucasSequence(BigInteger var1, BigInteger var2, BigInteger var3) {
         int var4 = var3.bitLength();
         int var5 = var3.getLowestSetBit();
         BigInteger var6 = ECConstants.ONE;
         BigInteger var7 = ECConstants.TWO;
         BigInteger var8 = var1;
         BigInteger var9 = ECConstants.ONE;
         BigInteger var10 = ECConstants.ONE;

         int var11;
         for(var11 = var4 - 1; var11 >= var5 + 1; --var11) {
            var9 = this.modMult(var9, var10);
            if (var3.testBit(var11)) {
               var10 = this.modMult(var9, var2);
               var6 = this.modMult(var6, var8);
               var7 = this.modReduce(var8.multiply(var7).subtract(var1.multiply(var9)));
               var8 = this.modReduce(var8.multiply(var8).subtract(var10.shiftLeft(1)));
            } else {
               var10 = var9;
               var6 = this.modReduce(var6.multiply(var7).subtract(var9));
               var8 = this.modReduce(var8.multiply(var7).subtract(var1.multiply(var9)));
               var7 = this.modReduce(var7.multiply(var7).subtract(var9.shiftLeft(1)));
            }
         }

         var9 = this.modMult(var9, var10);
         var10 = this.modMult(var9, var2);
         var6 = this.modReduce(var6.multiply(var7).subtract(var9));
         var7 = this.modReduce(var8.multiply(var7).subtract(var1.multiply(var9)));
         var9 = this.modMult(var9, var10);

         for(var11 = 1; var11 <= var5; ++var11) {
            var6 = this.modMult(var6, var7);
            var7 = this.modReduce(var7.multiply(var7).subtract(var9.shiftLeft(1)));
            var9 = this.modMult(var9, var9);
         }

         return new BigInteger[]{var6, var7};
      }

      protected BigInteger modAdd(BigInteger var1, BigInteger var2) {
         BigInteger var3 = var1.add(var2);
         if (var3.compareTo(this.q) >= 0) {
            var3 = var3.subtract(this.q);
         }

         return var3;
      }

      protected BigInteger modDouble(BigInteger var1) {
         BigInteger var2 = var1.shiftLeft(1);
         if (var2.compareTo(this.q) >= 0) {
            var2 = var2.subtract(this.q);
         }

         return var2;
      }

      protected BigInteger modHalf(BigInteger var1) {
         if (var1.testBit(0)) {
            var1 = this.q.add(var1);
         }

         return var1.shiftRight(1);
      }

      protected BigInteger modHalfAbs(BigInteger var1) {
         if (var1.testBit(0)) {
            var1 = this.q.subtract(var1);
         }

         return var1.shiftRight(1);
      }

      protected BigInteger modInverse(BigInteger var1) {
         int var2 = this.getFieldSize();
         int var3 = var2 + 31 >> 5;
         int[] var4 = Nat.fromBigInteger(var2, this.q);
         int[] var5 = Nat.fromBigInteger(var2, var1);
         int[] var6 = Nat.create(var3);
         Mod.invert(var4, var5, var6);
         return Nat.toBigInteger(var3, var6);
      }

      protected BigInteger modMult(BigInteger var1, BigInteger var2) {
         return this.modReduce(var1.multiply(var2));
      }

      protected BigInteger modReduce(BigInteger var1) {
         if (this.r != null) {
            boolean var2 = var1.signum() < 0;
            if (var2) {
               var1 = var1.abs();
            }

            int var3 = this.q.bitLength();

            BigInteger var5;
            BigInteger var6;
            for(boolean var4 = this.r.equals(ECConstants.ONE); var1.bitLength() > var3 + 1; var1 = var5.add(var6)) {
               var5 = var1.shiftRight(var3);
               var6 = var1.subtract(var5.shiftLeft(var3));
               if (!var4) {
                  var5 = var5.multiply(this.r);
               }
            }

            while(var1.compareTo(this.q) >= 0) {
               var1 = var1.subtract(this.q);
            }

            if (var2 && var1.signum() != 0) {
               var1 = this.q.subtract(var1);
            }
         } else {
            var1 = var1.mod(this.q);
         }

         return var1;
      }

      protected BigInteger modSubtract(BigInteger var1, BigInteger var2) {
         BigInteger var3 = var1.subtract(var2);
         if (var3.signum() < 0) {
            var3 = var3.add(this.q);
         }

         return var3;
      }

      public boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof Fp)) {
            return false;
         } else {
            Fp var2 = (Fp)var1;
            return this.q.equals(var2.q) && this.x.equals(var2.x);
         }
      }

      public int hashCode() {
         return this.q.hashCode() ^ this.x.hashCode();
      }
   }
}
