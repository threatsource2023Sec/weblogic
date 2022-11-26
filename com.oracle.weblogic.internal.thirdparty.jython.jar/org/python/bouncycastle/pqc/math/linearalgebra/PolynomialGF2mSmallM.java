package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class PolynomialGF2mSmallM {
   private GF2mField field;
   private int degree;
   private int[] coefficients;
   public static final char RANDOM_IRREDUCIBLE_POLYNOMIAL = 'I';

   public PolynomialGF2mSmallM(GF2mField var1) {
      this.field = var1;
      this.degree = -1;
      this.coefficients = new int[1];
   }

   public PolynomialGF2mSmallM(GF2mField var1, int var2, char var3, SecureRandom var4) {
      this.field = var1;
      switch (var3) {
         case 'I':
            this.coefficients = this.createRandomIrreduciblePolynomial(var2, var4);
            this.computeDegree();
            return;
         default:
            throw new IllegalArgumentException(" Error: type " + var3 + " is not defined for GF2smallmPolynomial");
      }
   }

   private int[] createRandomIrreduciblePolynomial(int var1, SecureRandom var2) {
      int[] var3 = new int[var1 + 1];
      var3[var1] = 1;
      var3[0] = this.field.getRandomNonZeroElement(var2);

      int var4;
      for(var4 = 1; var4 < var1; ++var4) {
         var3[var4] = this.field.getRandomElement(var2);
      }

      while(!this.isIrreducible(var3)) {
         var4 = RandUtils.nextInt(var2, var1);
         if (var4 == 0) {
            var3[0] = this.field.getRandomNonZeroElement(var2);
         } else {
            var3[var4] = this.field.getRandomElement(var2);
         }
      }

      return var3;
   }

   public PolynomialGF2mSmallM(GF2mField var1, int var2) {
      this.field = var1;
      this.degree = var2;
      this.coefficients = new int[var2 + 1];
      this.coefficients[var2] = 1;
   }

   public PolynomialGF2mSmallM(GF2mField var1, int[] var2) {
      this.field = var1;
      this.coefficients = normalForm(var2);
      this.computeDegree();
   }

   public PolynomialGF2mSmallM(GF2mField var1, byte[] var2) {
      this.field = var1;
      int var3 = 8;

      int var4;
      for(var4 = 1; var1.getDegree() > var3; var3 += 8) {
         ++var4;
      }

      if (var2.length % var4 != 0) {
         throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
      } else {
         this.coefficients = new int[var2.length / var4];
         var4 = 0;

         for(int var5 = 0; var5 < this.coefficients.length; ++var5) {
            for(int var6 = 0; var6 < var3; var6 += 8) {
               int[] var10000 = this.coefficients;
               var10000[var5] ^= (var2[var4++] & 255) << var6;
            }

            if (!this.field.isElementOfThisField(this.coefficients[var5])) {
               throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
            }
         }

         if (this.coefficients.length != 1 && this.coefficients[this.coefficients.length - 1] == 0) {
            throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
         } else {
            this.computeDegree();
         }
      }
   }

   public PolynomialGF2mSmallM(PolynomialGF2mSmallM var1) {
      this.field = var1.field;
      this.degree = var1.degree;
      this.coefficients = IntUtils.clone(var1.coefficients);
   }

   public PolynomialGF2mSmallM(GF2mVector var1) {
      this(var1.getField(), var1.getIntArrayForm());
   }

   public int getDegree() {
      int var1 = this.coefficients.length - 1;
      return this.coefficients[var1] == 0 ? -1 : var1;
   }

   public int getHeadCoefficient() {
      return this.degree == -1 ? 0 : this.coefficients[this.degree];
   }

   private static int headCoefficient(int[] var0) {
      int var1 = computeDegree(var0);
      return var1 == -1 ? 0 : var0[var1];
   }

   public int getCoefficient(int var1) {
      return var1 >= 0 && var1 <= this.degree ? this.coefficients[var1] : 0;
   }

   public byte[] getEncoded() {
      int var1 = 8;

      int var2;
      for(var2 = 1; this.field.getDegree() > var1; var1 += 8) {
         ++var2;
      }

      byte[] var3 = new byte[this.coefficients.length * var2];
      var2 = 0;

      for(int var4 = 0; var4 < this.coefficients.length; ++var4) {
         for(int var5 = 0; var5 < var1; var5 += 8) {
            var3[var2++] = (byte)(this.coefficients[var4] >>> var5);
         }
      }

      return var3;
   }

   public int evaluateAt(int var1) {
      int var2 = this.coefficients[this.degree];

      for(int var3 = this.degree - 1; var3 >= 0; --var3) {
         var2 = this.field.mult(var2, var1) ^ this.coefficients[var3];
      }

      return var2;
   }

   public PolynomialGF2mSmallM add(PolynomialGF2mSmallM var1) {
      int[] var2 = this.add(this.coefficients, var1.coefficients);
      return new PolynomialGF2mSmallM(this.field, var2);
   }

   public void addToThis(PolynomialGF2mSmallM var1) {
      this.coefficients = this.add(this.coefficients, var1.coefficients);
      this.computeDegree();
   }

   private int[] add(int[] var1, int[] var2) {
      int[] var3;
      int[] var4;
      if (var1.length < var2.length) {
         var3 = new int[var2.length];
         System.arraycopy(var2, 0, var3, 0, var2.length);
         var4 = var1;
      } else {
         var3 = new int[var1.length];
         System.arraycopy(var1, 0, var3, 0, var1.length);
         var4 = var2;
      }

      for(int var5 = var4.length - 1; var5 >= 0; --var5) {
         var3[var5] = this.field.add(var3[var5], var4[var5]);
      }

      return var3;
   }

   public PolynomialGF2mSmallM addMonomial(int var1) {
      int[] var2 = new int[var1 + 1];
      var2[var1] = 1;
      int[] var3 = this.add(this.coefficients, var2);
      return new PolynomialGF2mSmallM(this.field, var3);
   }

   public PolynomialGF2mSmallM multWithElement(int var1) {
      if (!this.field.isElementOfThisField(var1)) {
         throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
      } else {
         int[] var2 = this.multWithElement(this.coefficients, var1);
         return new PolynomialGF2mSmallM(this.field, var2);
      }
   }

   public void multThisWithElement(int var1) {
      if (!this.field.isElementOfThisField(var1)) {
         throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
      } else {
         this.coefficients = this.multWithElement(this.coefficients, var1);
         this.computeDegree();
      }
   }

   private int[] multWithElement(int[] var1, int var2) {
      int var3 = computeDegree(var1);
      if (var3 != -1 && var2 != 0) {
         if (var2 == 1) {
            return IntUtils.clone(var1);
         } else {
            int[] var4 = new int[var3 + 1];

            for(int var5 = var3; var5 >= 0; --var5) {
               var4[var5] = this.field.mult(var1[var5], var2);
            }

            return var4;
         }
      } else {
         return new int[1];
      }
   }

   public PolynomialGF2mSmallM multWithMonomial(int var1) {
      int[] var2 = multWithMonomial(this.coefficients, var1);
      return new PolynomialGF2mSmallM(this.field, var2);
   }

   private static int[] multWithMonomial(int[] var0, int var1) {
      int var2 = computeDegree(var0);
      if (var2 == -1) {
         return new int[1];
      } else {
         int[] var3 = new int[var2 + var1 + 1];
         System.arraycopy(var0, 0, var3, var1, var2 + 1);
         return var3;
      }
   }

   public PolynomialGF2mSmallM[] div(PolynomialGF2mSmallM var1) {
      int[][] var2 = this.div(this.coefficients, var1.coefficients);
      return new PolynomialGF2mSmallM[]{new PolynomialGF2mSmallM(this.field, var2[0]), new PolynomialGF2mSmallM(this.field, var2[1])};
   }

   private int[][] div(int[] var1, int[] var2) {
      int var3 = computeDegree(var2);
      int var4 = computeDegree(var1) + 1;
      if (var3 == -1) {
         throw new ArithmeticException("Division by zero.");
      } else {
         int[][] var5 = new int[][]{new int[1], new int[var4]};
         int var6 = headCoefficient(var2);
         var6 = this.field.inverse(var6);
         var5[0][0] = 0;
         System.arraycopy(var1, 0, var5[1], 0, var5[1].length);

         while(var3 <= computeDegree(var5[1])) {
            int[] var7 = new int[]{this.field.mult(headCoefficient(var5[1]), var6)};
            int[] var8 = this.multWithElement(var2, var7[0]);
            int var9 = computeDegree(var5[1]) - var3;
            var8 = multWithMonomial(var8, var9);
            var7 = multWithMonomial(var7, var9);
            var5[0] = this.add(var7, var5[0]);
            var5[1] = this.add(var8, var5[1]);
         }

         return var5;
      }
   }

   public PolynomialGF2mSmallM gcd(PolynomialGF2mSmallM var1) {
      int[] var2 = this.gcd(this.coefficients, var1.coefficients);
      return new PolynomialGF2mSmallM(this.field, var2);
   }

   private int[] gcd(int[] var1, int[] var2) {
      int[] var3 = var1;
      int[] var4 = var2;
      if (computeDegree(var1) == -1) {
         return var2;
      } else {
         while(computeDegree(var4) != -1) {
            int[] var5 = this.mod(var3, var4);
            var3 = new int[var4.length];
            System.arraycopy(var4, 0, var3, 0, var3.length);
            var4 = new int[var5.length];
            System.arraycopy(var5, 0, var4, 0, var4.length);
         }

         int var6 = this.field.inverse(headCoefficient(var3));
         return this.multWithElement(var3, var6);
      }
   }

   public PolynomialGF2mSmallM multiply(PolynomialGF2mSmallM var1) {
      int[] var2 = this.multiply(this.coefficients, var1.coefficients);
      return new PolynomialGF2mSmallM(this.field, var2);
   }

   private int[] multiply(int[] var1, int[] var2) {
      int[] var3;
      int[] var4;
      if (computeDegree(var1) < computeDegree(var2)) {
         var3 = var2;
         var4 = var1;
      } else {
         var3 = var1;
         var4 = var2;
      }

      var3 = normalForm(var3);
      var4 = normalForm(var4);
      if (var4.length == 1) {
         return this.multWithElement(var3, var4[0]);
      } else {
         int var5 = var3.length;
         int var6 = var4.length;
         int[] var7 = new int[var5 + var6 - 1];
         int[] var9;
         if (var6 != var5) {
            int[] var8 = new int[var6];
            var9 = new int[var5 - var6];
            System.arraycopy(var3, 0, var8, 0, var8.length);
            System.arraycopy(var3, var6, var9, 0, var9.length);
            var8 = this.multiply(var8, var4);
            var9 = this.multiply(var9, var4);
            var9 = multWithMonomial(var9, var6);
            var7 = this.add(var8, var9);
         } else {
            var6 = var5 + 1 >>> 1;
            int var18 = var5 - var6;
            var9 = new int[var6];
            int[] var10 = new int[var6];
            int[] var11 = new int[var18];
            int[] var12 = new int[var18];
            System.arraycopy(var3, 0, var9, 0, var9.length);
            System.arraycopy(var3, var6, var11, 0, var11.length);
            System.arraycopy(var4, 0, var10, 0, var10.length);
            System.arraycopy(var4, var6, var12, 0, var12.length);
            int[] var13 = this.add(var9, var11);
            int[] var14 = this.add(var10, var12);
            int[] var15 = this.multiply(var9, var10);
            int[] var16 = this.multiply(var13, var14);
            int[] var17 = this.multiply(var11, var12);
            var16 = this.add(var16, var15);
            var16 = this.add(var16, var17);
            var17 = multWithMonomial(var17, var6);
            var7 = this.add(var16, var17);
            var7 = multWithMonomial(var7, var6);
            var7 = this.add(var7, var15);
         }

         return var7;
      }
   }

   private boolean isIrreducible(int[] var1) {
      if (var1[0] == 0) {
         return false;
      } else {
         int var2 = computeDegree(var1) >> 1;
         int[] var3 = new int[]{0, 1};
         int[] var4 = new int[]{0, 1};
         int var5 = this.field.getDegree();

         for(int var6 = 0; var6 < var2; ++var6) {
            for(int var7 = var5 - 1; var7 >= 0; --var7) {
               var3 = this.modMultiply(var3, var3, var1);
            }

            var3 = normalForm(var3);
            int[] var8 = this.gcd(this.add(var3, var4), var1);
            if (computeDegree(var8) != 0) {
               return false;
            }
         }

         return true;
      }
   }

   public PolynomialGF2mSmallM mod(PolynomialGF2mSmallM var1) {
      int[] var2 = this.mod(this.coefficients, var1.coefficients);
      return new PolynomialGF2mSmallM(this.field, var2);
   }

   private int[] mod(int[] var1, int[] var2) {
      int var3 = computeDegree(var2);
      if (var3 == -1) {
         throw new ArithmeticException("Division by zero");
      } else {
         int[] var4 = new int[var1.length];
         int var5 = headCoefficient(var2);
         var5 = this.field.inverse(var5);
         System.arraycopy(var1, 0, var4, 0, var4.length);

         while(var3 <= computeDegree(var4)) {
            int var6 = this.field.mult(headCoefficient(var4), var5);
            int[] var7 = multWithMonomial(var2, computeDegree(var4) - var3);
            var7 = this.multWithElement(var7, var6);
            var4 = this.add(var7, var4);
         }

         return var4;
      }
   }

   public PolynomialGF2mSmallM modMultiply(PolynomialGF2mSmallM var1, PolynomialGF2mSmallM var2) {
      int[] var3 = this.modMultiply(this.coefficients, var1.coefficients, var2.coefficients);
      return new PolynomialGF2mSmallM(this.field, var3);
   }

   public PolynomialGF2mSmallM modSquareMatrix(PolynomialGF2mSmallM[] var1) {
      int var2 = var1.length;
      int[] var3 = new int[var2];
      int[] var4 = new int[var2];

      int var5;
      for(var5 = 0; var5 < this.coefficients.length; ++var5) {
         var4[var5] = this.field.mult(this.coefficients[var5], this.coefficients[var5]);
      }

      for(var5 = 0; var5 < var2; ++var5) {
         for(int var6 = 0; var6 < var2; ++var6) {
            if (var5 < var1[var6].coefficients.length) {
               int var7 = this.field.mult(var1[var6].coefficients[var5], var4[var6]);
               var3[var5] = this.field.add(var3[var5], var7);
            }
         }
      }

      return new PolynomialGF2mSmallM(this.field, var3);
   }

   private int[] modMultiply(int[] var1, int[] var2, int[] var3) {
      return this.mod(this.multiply(var1, var2), var3);
   }

   public PolynomialGF2mSmallM modSquareRoot(PolynomialGF2mSmallM var1) {
      int[] var2 = IntUtils.clone(this.coefficients);

      for(int[] var3 = this.modMultiply(var2, var2, var1.coefficients); !isEqual(var3, this.coefficients); var3 = this.modMultiply(var2, var2, var1.coefficients)) {
         var2 = normalForm(var3);
      }

      return new PolynomialGF2mSmallM(this.field, var2);
   }

   public PolynomialGF2mSmallM modSquareRootMatrix(PolynomialGF2mSmallM[] var1) {
      int var2 = var1.length;
      int[] var3 = new int[var2];

      int var4;
      for(var4 = 0; var4 < var2; ++var4) {
         for(int var5 = 0; var5 < var2; ++var5) {
            if (var4 < var1[var5].coefficients.length && var5 < this.coefficients.length) {
               int var6 = this.field.mult(var1[var5].coefficients[var4], this.coefficients[var5]);
               var3[var4] = this.field.add(var3[var4], var6);
            }
         }
      }

      for(var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.field.sqRoot(var3[var4]);
      }

      return new PolynomialGF2mSmallM(this.field, var3);
   }

   public PolynomialGF2mSmallM modDiv(PolynomialGF2mSmallM var1, PolynomialGF2mSmallM var2) {
      int[] var3 = this.modDiv(this.coefficients, var1.coefficients, var2.coefficients);
      return new PolynomialGF2mSmallM(this.field, var3);
   }

   private int[] modDiv(int[] var1, int[] var2, int[] var3) {
      int[] var4 = normalForm(var3);
      int[] var5 = this.mod(var2, var3);
      int[] var6 = new int[]{0};

      int[] var9;
      for(int[] var7 = this.mod(var1, var3); computeDegree(var5) != -1; var7 = normalForm(var9)) {
         int[][] var8 = this.div(var4, var5);
         var4 = normalForm(var5);
         var5 = normalForm(var8[1]);
         var9 = this.add(var6, this.modMultiply(var8[0], var7, var3));
         var6 = normalForm(var7);
      }

      int var10 = headCoefficient(var4);
      var6 = this.multWithElement(var6, this.field.inverse(var10));
      return var6;
   }

   public PolynomialGF2mSmallM modInverse(PolynomialGF2mSmallM var1) {
      int[] var2 = new int[]{1};
      int[] var3 = this.modDiv(var2, this.coefficients, var1.coefficients);
      return new PolynomialGF2mSmallM(this.field, var3);
   }

   public PolynomialGF2mSmallM[] modPolynomialToFracton(PolynomialGF2mSmallM var1) {
      int var2 = var1.degree >> 1;
      int[] var3 = normalForm(var1.coefficients);
      int[] var4 = this.mod(this.coefficients, var1.coefficients);
      int[] var5 = new int[]{0};

      int[] var6;
      int[] var8;
      for(var6 = new int[]{1}; computeDegree(var4) > var2; var6 = var8) {
         int[][] var7 = this.div(var3, var4);
         var3 = var4;
         var4 = var7[1];
         var8 = this.add(var5, this.modMultiply(var7[0], var6, var1.coefficients));
         var5 = var6;
      }

      return new PolynomialGF2mSmallM[]{new PolynomialGF2mSmallM(this.field, var4), new PolynomialGF2mSmallM(this.field, var6)};
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PolynomialGF2mSmallM) {
         PolynomialGF2mSmallM var2 = (PolynomialGF2mSmallM)var1;
         return this.field.equals(var2.field) && this.degree == var2.degree && isEqual(this.coefficients, var2.coefficients);
      } else {
         return false;
      }
   }

   private static boolean isEqual(int[] var0, int[] var1) {
      int var2 = computeDegree(var0);
      int var3 = computeDegree(var1);
      if (var2 != var3) {
         return false;
      } else {
         for(int var4 = 0; var4 <= var2; ++var4) {
            if (var0[var4] != var1[var4]) {
               return false;
            }
         }

         return true;
      }
   }

   public int hashCode() {
      int var1 = this.field.hashCode();

      for(int var2 = 0; var2 < this.coefficients.length; ++var2) {
         var1 = var1 * 31 + this.coefficients[var2];
      }

      return var1;
   }

   public String toString() {
      String var1 = " Polynomial over " + this.field.toString() + ": \n";

      for(int var2 = 0; var2 < this.coefficients.length; ++var2) {
         var1 = var1 + this.field.elementToStr(this.coefficients[var2]) + "Y^" + var2 + "+";
      }

      var1 = var1 + ";";
      return var1;
   }

   private void computeDegree() {
      for(this.degree = this.coefficients.length - 1; this.degree >= 0 && this.coefficients[this.degree] == 0; --this.degree) {
      }

   }

   private static int computeDegree(int[] var0) {
      int var1;
      for(var1 = var0.length - 1; var1 >= 0 && var0[var1] == 0; --var1) {
      }

      return var1;
   }

   private static int[] normalForm(int[] var0) {
      int var1 = computeDegree(var0);
      if (var1 == -1) {
         return new int[1];
      } else if (var0.length == var1 + 1) {
         return IntUtils.clone(var0);
      } else {
         int[] var2 = new int[var1 + 1];
         System.arraycopy(var0, 0, var2, 0, var1 + 1);
         return var2;
      }
   }
}
