package org.python.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.util.Random;

public class GF2nPolynomialElement extends GF2nElement {
   private static final int[] bitMask = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, Integer.MIN_VALUE, 0};
   private GF2Polynomial polynomial;

   public GF2nPolynomialElement(GF2nPolynomialField var1, Random var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.polynomial = new GF2Polynomial(this.mDegree);
      this.randomize(var2);
   }

   public GF2nPolynomialElement(GF2nPolynomialField var1, GF2Polynomial var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.polynomial = new GF2Polynomial(var2);
      this.polynomial.expandN(this.mDegree);
   }

   public GF2nPolynomialElement(GF2nPolynomialField var1, byte[] var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.polynomial = new GF2Polynomial(this.mDegree, var2);
      this.polynomial.expandN(this.mDegree);
   }

   public GF2nPolynomialElement(GF2nPolynomialField var1, int[] var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.polynomial = new GF2Polynomial(this.mDegree, var2);
      this.polynomial.expandN(var1.mDegree);
   }

   public GF2nPolynomialElement(GF2nPolynomialElement var1) {
      this.mField = var1.mField;
      this.mDegree = var1.mDegree;
      this.polynomial = new GF2Polynomial(var1.polynomial);
   }

   public Object clone() {
      return new GF2nPolynomialElement(this);
   }

   void assignZero() {
      this.polynomial.assignZero();
   }

   public static GF2nPolynomialElement ZERO(GF2nPolynomialField var0) {
      GF2Polynomial var1 = new GF2Polynomial(var0.getDegree());
      return new GF2nPolynomialElement(var0, var1);
   }

   public static GF2nPolynomialElement ONE(GF2nPolynomialField var0) {
      GF2Polynomial var1 = new GF2Polynomial(var0.getDegree(), new int[]{1});
      return new GF2nPolynomialElement(var0, var1);
   }

   void assignOne() {
      this.polynomial.assignOne();
   }

   private void randomize(Random var1) {
      this.polynomial.expandN(this.mDegree);
      this.polynomial.randomize(var1);
   }

   public boolean isZero() {
      return this.polynomial.isZero();
   }

   public boolean isOne() {
      return this.polynomial.isOne();
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GF2nPolynomialElement) {
         GF2nPolynomialElement var2 = (GF2nPolynomialElement)var1;
         return this.mField != var2.mField && !this.mField.getFieldPolynomial().equals(var2.mField.getFieldPolynomial()) ? false : this.polynomial.equals(var2.polynomial);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mField.hashCode() + this.polynomial.hashCode();
   }

   private GF2Polynomial getGF2Polynomial() {
      return new GF2Polynomial(this.polynomial);
   }

   boolean testBit(int var1) {
      return this.polynomial.testBit(var1);
   }

   public boolean testRightmostBit() {
      return this.polynomial.testBit(0);
   }

   public GFElement add(GFElement var1) throws RuntimeException {
      GF2nPolynomialElement var2 = new GF2nPolynomialElement(this);
      var2.addToThis(var1);
      return var2;
   }

   public void addToThis(GFElement var1) throws RuntimeException {
      if (!(var1 instanceof GF2nPolynomialElement)) {
         throw new RuntimeException();
      } else if (!this.mField.equals(((GF2nPolynomialElement)var1).mField)) {
         throw new RuntimeException();
      } else {
         this.polynomial.addToThis(((GF2nPolynomialElement)var1).polynomial);
      }
   }

   public GF2nElement increase() {
      GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);
      var1.increaseThis();
      return var1;
   }

   public void increaseThis() {
      this.polynomial.increaseThis();
   }

   public GFElement multiply(GFElement var1) throws RuntimeException {
      GF2nPolynomialElement var2 = new GF2nPolynomialElement(this);
      var2.multiplyThisBy(var1);
      return var2;
   }

   public void multiplyThisBy(GFElement var1) throws RuntimeException {
      if (!(var1 instanceof GF2nPolynomialElement)) {
         throw new RuntimeException();
      } else if (!this.mField.equals(((GF2nPolynomialElement)var1).mField)) {
         throw new RuntimeException();
      } else if (this.equals(var1)) {
         this.squareThis();
      } else {
         this.polynomial = this.polynomial.multiply(((GF2nPolynomialElement)var1).polynomial);
         this.reduceThis();
      }
   }

   public GFElement invert() throws ArithmeticException {
      return this.invertMAIA();
   }

   public GF2nPolynomialElement invertEEA() throws ArithmeticException {
      if (this.isZero()) {
         throw new ArithmeticException();
      } else {
         GF2Polynomial var1 = new GF2Polynomial(this.mDegree + 32, "ONE");
         var1.reduceN();
         GF2Polynomial var2 = new GF2Polynomial(this.mDegree + 32);
         var2.reduceN();
         GF2Polynomial var3 = this.getGF2Polynomial();
         GF2Polynomial var4 = this.mField.getFieldPolynomial();
         var3.reduceN();

         while(!var3.isOne()) {
            var3.reduceN();
            var4.reduceN();
            int var5 = var3.getLength() - var4.getLength();
            if (var5 < 0) {
               GF2Polynomial var6 = var3;
               var3 = var4;
               var4 = var6;
               var6 = var1;
               var1 = var2;
               var2 = var6;
               var5 = -var5;
               var6.reduceN();
            }

            var3.shiftLeftAddThis(var4, var5);
            var1.shiftLeftAddThis(var2, var5);
         }

         var1.reduceN();
         return new GF2nPolynomialElement((GF2nPolynomialField)this.mField, var1);
      }
   }

   public GF2nPolynomialElement invertSquare() throws ArithmeticException {
      if (this.isZero()) {
         throw new ArithmeticException();
      } else {
         int var1 = this.mField.getDegree() - 1;
         GF2nPolynomialElement var2 = new GF2nPolynomialElement(this);
         var2.polynomial.expandN((this.mDegree << 1) + 32);
         var2.polynomial.reduceN();
         int var3 = 1;

         for(int var4 = IntegerFunctions.floorLog(var1) - 1; var4 >= 0; --var4) {
            GF2nPolynomialElement var5 = new GF2nPolynomialElement(var2);

            for(int var6 = 1; var6 <= var3; ++var6) {
               var5.squareThisPreCalc();
            }

            var2.multiplyThisBy(var5);
            var3 <<= 1;
            if ((var1 & bitMask[var4]) != 0) {
               var2.squareThisPreCalc();
               var2.multiplyThisBy(this);
               ++var3;
            }
         }

         var2.squareThisPreCalc();
         return var2;
      }
   }

   public GF2nPolynomialElement invertMAIA() throws ArithmeticException {
      if (this.isZero()) {
         throw new ArithmeticException();
      } else {
         GF2Polynomial var1 = new GF2Polynomial(this.mDegree, "ONE");
         GF2Polynomial var2 = new GF2Polynomial(this.mDegree);
         GF2Polynomial var3 = this.getGF2Polynomial();
         GF2Polynomial var4 = this.mField.getFieldPolynomial();

         while(true) {
            while(var3.testBit(0)) {
               if (var3.isOne()) {
                  return new GF2nPolynomialElement((GF2nPolynomialField)this.mField, var1);
               }

               var3.reduceN();
               var4.reduceN();
               if (var3.getLength() < var4.getLength()) {
                  GF2Polynomial var5 = var3;
                  var3 = var4;
                  var4 = var5;
                  var5 = var1;
                  var1 = var2;
                  var2 = var5;
               }

               var3.addToThis(var4);
               var1.addToThis(var2);
            }

            var3.shiftRightThis();
            if (!var1.testBit(0)) {
               var1.shiftRightThis();
            } else {
               var1.addToThis(this.mField.getFieldPolynomial());
               var1.shiftRightThis();
            }
         }
      }
   }

   public GF2nElement square() {
      return this.squarePreCalc();
   }

   public void squareThis() {
      this.squareThisPreCalc();
   }

   public GF2nPolynomialElement squareMatrix() {
      GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);
      var1.squareThisMatrix();
      var1.reduceThis();
      return var1;
   }

   public void squareThisMatrix() {
      GF2Polynomial var1 = new GF2Polynomial(this.mDegree);

      for(int var2 = 0; var2 < this.mDegree; ++var2) {
         if (this.polynomial.vectorMult(((GF2nPolynomialField)this.mField).squaringMatrix[this.mDegree - var2 - 1])) {
            var1.setBit(var2);
         }
      }

      this.polynomial = var1;
   }

   public GF2nPolynomialElement squareBitwise() {
      GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);
      var1.squareThisBitwise();
      var1.reduceThis();
      return var1;
   }

   public void squareThisBitwise() {
      this.polynomial.squareThisBitwise();
      this.reduceThis();
   }

   public GF2nPolynomialElement squarePreCalc() {
      GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);
      var1.squareThisPreCalc();
      var1.reduceThis();
      return var1;
   }

   public void squareThisPreCalc() {
      this.polynomial.squareThisPreCalc();
      this.reduceThis();
   }

   public GF2nPolynomialElement power(int var1) {
      if (var1 == 1) {
         return new GF2nPolynomialElement(this);
      } else {
         GF2nPolynomialElement var2 = ONE((GF2nPolynomialField)this.mField);
         if (var1 == 0) {
            return var2;
         } else {
            GF2nPolynomialElement var3 = new GF2nPolynomialElement(this);
            var3.polynomial.expandN((var3.mDegree << 1) + 32);
            var3.polynomial.reduceN();

            for(int var4 = 0; var4 < this.mDegree; ++var4) {
               if ((var1 & 1 << var4) != 0) {
                  var2.multiplyThisBy(var3);
               }

               var3.square();
            }

            return var2;
         }
      }
   }

   public GF2nElement squareRoot() {
      GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);
      var1.squareRootThis();
      return var1;
   }

   public void squareRootThis() {
      this.polynomial.expandN((this.mDegree << 1) + 32);
      this.polynomial.reduceN();

      for(int var1 = 0; var1 < this.mField.getDegree() - 1; ++var1) {
         this.squareThis();
      }

   }

   public GF2nElement solveQuadraticEquation() throws RuntimeException {
      if (this.isZero()) {
         return ZERO((GF2nPolynomialField)this.mField);
      } else if ((this.mDegree & 1) == 1) {
         return this.halfTrace();
      } else {
         GF2nPolynomialElement var2;
         GF2nPolynomialElement var3;
         do {
            GF2nPolynomialElement var1 = new GF2nPolynomialElement((GF2nPolynomialField)this.mField, new Random());
            var2 = ZERO((GF2nPolynomialField)this.mField);
            var3 = (GF2nPolynomialElement)var1.clone();

            for(int var4 = 1; var4 < this.mDegree; ++var4) {
               var2.squareThis();
               var3.squareThis();
               var2.addToThis(var3.multiply(this));
               var3.addToThis(var1);
            }
         } while(var3.isZero());

         if (!this.equals(var2.square().add(var2))) {
            throw new RuntimeException();
         } else {
            return var2;
         }
      }
   }

   public int trace() {
      GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);

      for(int var2 = 1; var2 < this.mDegree; ++var2) {
         var1.squareThis();
         var1.addToThis(this);
      }

      return var1.isOne() ? 1 : 0;
   }

   private GF2nPolynomialElement halfTrace() throws RuntimeException {
      if ((this.mDegree & 1) == 0) {
         throw new RuntimeException();
      } else {
         GF2nPolynomialElement var1 = new GF2nPolynomialElement(this);

         for(int var2 = 1; var2 <= this.mDegree - 1 >> 1; ++var2) {
            var1.squareThis();
            var1.squareThis();
            var1.addToThis(this);
         }

         return var1;
      }
   }

   private void reduceThis() {
      if (this.polynomial.getLength() > this.mDegree) {
         if (((GF2nPolynomialField)this.mField).isTrinomial()) {
            int var5;
            try {
               var5 = ((GF2nPolynomialField)this.mField).getTc();
            } catch (RuntimeException var3) {
               throw new RuntimeException("GF2nPolynomialElement.reduce: the field polynomial is not a trinomial");
            }

            if (this.mDegree - var5 > 32 && this.polynomial.getLength() <= this.mDegree << 1) {
               this.polynomial.reduceTrinomial(this.mDegree, var5);
            } else {
               this.reduceTrinomialBitwise(var5);
            }
         } else if (((GF2nPolynomialField)this.mField).isPentanomial()) {
            int[] var1;
            try {
               var1 = ((GF2nPolynomialField)this.mField).getPc();
            } catch (RuntimeException var4) {
               throw new RuntimeException("GF2nPolynomialElement.reduce: the field polynomial is not a pentanomial");
            }

            if (this.mDegree - var1[2] > 32 && this.polynomial.getLength() <= this.mDegree << 1) {
               this.polynomial.reducePentanomial(this.mDegree, var1);
            } else {
               this.reducePentanomialBitwise(var1);
            }
         } else {
            this.polynomial = this.polynomial.remainder(this.mField.getFieldPolynomial());
            this.polynomial.expandN(this.mDegree);
         }
      } else {
         if (this.polynomial.getLength() < this.mDegree) {
            this.polynomial.expandN(this.mDegree);
         }

      }
   }

   private void reduceTrinomialBitwise(int var1) {
      int var2 = this.mDegree - var1;

      for(int var3 = this.polynomial.getLength() - 1; var3 >= this.mDegree; --var3) {
         if (this.polynomial.testBit(var3)) {
            this.polynomial.xorBit(var3);
            this.polynomial.xorBit(var3 - var2);
            this.polynomial.xorBit(var3 - this.mDegree);
         }
      }

      this.polynomial.reduceN();
      this.polynomial.expandN(this.mDegree);
   }

   private void reducePentanomialBitwise(int[] var1) {
      int var2 = this.mDegree - var1[2];
      int var3 = this.mDegree - var1[1];
      int var4 = this.mDegree - var1[0];

      for(int var5 = this.polynomial.getLength() - 1; var5 >= this.mDegree; --var5) {
         if (this.polynomial.testBit(var5)) {
            this.polynomial.xorBit(var5);
            this.polynomial.xorBit(var5 - var2);
            this.polynomial.xorBit(var5 - var3);
            this.polynomial.xorBit(var5 - var4);
            this.polynomial.xorBit(var5 - this.mDegree);
         }
      }

      this.polynomial.reduceN();
      this.polynomial.expandN(this.mDegree);
   }

   public String toString() {
      return this.polynomial.toString(16);
   }

   public String toString(int var1) {
      return this.polynomial.toString(var1);
   }

   public byte[] toByteArray() {
      return this.polynomial.toByteArray();
   }

   public BigInteger toFlexiBigInt() {
      return this.polynomial.toFlexiBigInt();
   }
}
