package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public final class GoppaCode {
   private GoppaCode() {
   }

   public static GF2Matrix createCanonicalCheckMatrix(GF2mField var0, PolynomialGF2mSmallM var1) {
      int var2 = var0.getDegree();
      int var3 = 1 << var2;
      int var4 = var1.getDegree();
      int[][] var5 = new int[var4][var3];
      int[][] var6 = new int[var4][var3];

      int var7;
      for(var7 = 0; var7 < var3; ++var7) {
         var6[0][var7] = var0.inverse(var1.evaluateAt(var7));
      }

      int var8;
      for(var7 = 1; var7 < var4; ++var7) {
         for(var8 = 0; var8 < var3; ++var8) {
            var6[var7][var8] = var0.mult(var6[var7 - 1][var8], var8);
         }
      }

      int var9;
      for(var7 = 0; var7 < var4; ++var7) {
         for(var8 = 0; var8 < var3; ++var8) {
            for(var9 = 0; var9 <= var7; ++var9) {
               var5[var7][var8] = var0.add(var5[var7][var8], var0.mult(var6[var9][var8], var1.getCoefficient(var4 + var9 - var7)));
            }
         }
      }

      int[][] var16 = new int[var4 * var2][var3 + 31 >>> 5];

      for(var8 = 0; var8 < var3; ++var8) {
         var9 = var8 >>> 5;
         int var10 = 1 << (var8 & 31);

         for(int var11 = 0; var11 < var4; ++var11) {
            int var12 = var5[var11][var8];

            for(int var13 = 0; var13 < var2; ++var13) {
               int var14 = var12 >>> var13 & 1;
               if (var14 != 0) {
                  int var15 = (var11 + 1) * var2 - var13 - 1;
                  var16[var15][var9] ^= var10;
               }
            }
         }
      }

      return new GF2Matrix(var3, var16);
   }

   public static MaMaPe computeSystematicForm(GF2Matrix var0, SecureRandom var1) {
      int var2 = var0.getNumColumns();
      GF2Matrix var3 = null;
      boolean var4 = false;

      Permutation var5;
      GF2Matrix var6;
      GF2Matrix var7;
      do {
         var5 = new Permutation(var2, var1);
         var6 = (GF2Matrix)var0.rightMultiply(var5);
         var7 = var6.getLeftSubMatrix();

         try {
            var4 = true;
            var3 = (GF2Matrix)var7.computeInverse();
         } catch (ArithmeticException var10) {
            var4 = false;
         }
      } while(!var4);

      GF2Matrix var8 = (GF2Matrix)var3.rightMultiply((Matrix)var6);
      GF2Matrix var9 = var8.getRightSubMatrix();
      return new MaMaPe(var7, var9, var5);
   }

   public static GF2Vector syndromeDecode(GF2Vector var0, GF2mField var1, PolynomialGF2mSmallM var2, PolynomialGF2mSmallM[] var3) {
      int var4 = 1 << var1.getDegree();
      GF2Vector var5 = new GF2Vector(var4);
      if (!var0.isZero()) {
         PolynomialGF2mSmallM var6 = new PolynomialGF2mSmallM(var0.toExtensionFieldVector(var1));
         PolynomialGF2mSmallM var7 = var6.modInverse(var2);
         PolynomialGF2mSmallM var8 = var7.addMonomial(1);
         var8 = var8.modSquareRootMatrix(var3);
         PolynomialGF2mSmallM[] var9 = var8.modPolynomialToFracton(var2);
         PolynomialGF2mSmallM var10 = var9[0].multiply(var9[0]);
         PolynomialGF2mSmallM var11 = var9[1].multiply(var9[1]);
         PolynomialGF2mSmallM var12 = var11.multWithMonomial(1);
         PolynomialGF2mSmallM var13 = var10.add(var12);
         int var14 = var13.getHeadCoefficient();
         int var15 = var1.inverse(var14);
         PolynomialGF2mSmallM var16 = var13.multWithElement(var15);

         for(int var17 = 0; var17 < var4; ++var17) {
            int var18 = var16.evaluateAt(var17);
            if (var18 == 0) {
               var5.setBit(var17);
            }
         }
      }

      return var5;
   }

   public static class MaMaPe {
      private GF2Matrix s;
      private GF2Matrix h;
      private Permutation p;

      public MaMaPe(GF2Matrix var1, GF2Matrix var2, Permutation var3) {
         this.s = var1;
         this.h = var2;
         this.p = var3;
      }

      public GF2Matrix getFirstMatrix() {
         return this.s;
      }

      public GF2Matrix getSecondMatrix() {
         return this.h;
      }

      public Permutation getPermutation() {
         return this.p;
      }
   }

   public static class MatrixSet {
      private GF2Matrix g;
      private int[] setJ;

      public MatrixSet(GF2Matrix var1, int[] var2) {
         this.g = var1;
         this.setJ = var2;
      }

      public GF2Matrix getG() {
         return this.g;
      }

      public int[] getSetJ() {
         return this.setJ;
      }
   }
}
