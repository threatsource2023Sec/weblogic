package org.python.bouncycastle.pqc.math.linearalgebra;

public class PolynomialRingGF2m {
   private GF2mField field;
   private PolynomialGF2mSmallM p;
   protected PolynomialGF2mSmallM[] sqMatrix;
   protected PolynomialGF2mSmallM[] sqRootMatrix;

   public PolynomialRingGF2m(GF2mField var1, PolynomialGF2mSmallM var2) {
      this.field = var1;
      this.p = var2;
      this.computeSquaringMatrix();
      this.computeSquareRootMatrix();
   }

   public PolynomialGF2mSmallM[] getSquaringMatrix() {
      return this.sqMatrix;
   }

   public PolynomialGF2mSmallM[] getSquareRootMatrix() {
      return this.sqRootMatrix;
   }

   private void computeSquaringMatrix() {
      int var1 = this.p.getDegree();
      this.sqMatrix = new PolynomialGF2mSmallM[var1];

      int var2;
      int[] var3;
      for(var2 = 0; var2 < var1 >> 1; ++var2) {
         var3 = new int[(var2 << 1) + 1];
         var3[var2 << 1] = 1;
         this.sqMatrix[var2] = new PolynomialGF2mSmallM(this.field, var3);
      }

      for(var2 = var1 >> 1; var2 < var1; ++var2) {
         var3 = new int[(var2 << 1) + 1];
         var3[var2 << 1] = 1;
         PolynomialGF2mSmallM var4 = new PolynomialGF2mSmallM(this.field, var3);
         this.sqMatrix[var2] = var4.mod(this.p);
      }

   }

   private void computeSquareRootMatrix() {
      int var1 = this.p.getDegree();
      PolynomialGF2mSmallM[] var2 = new PolynomialGF2mSmallM[var1];

      int var3;
      for(var3 = var1 - 1; var3 >= 0; --var3) {
         var2[var3] = new PolynomialGF2mSmallM(this.sqMatrix[var3]);
      }

      this.sqRootMatrix = new PolynomialGF2mSmallM[var1];

      for(var3 = var1 - 1; var3 >= 0; --var3) {
         this.sqRootMatrix[var3] = new PolynomialGF2mSmallM(this.field, var3);
      }

      for(var3 = 0; var3 < var1; ++var3) {
         int var5;
         if (var2[var3].getCoefficient(var3) == 0) {
            boolean var4 = false;

            for(var5 = var3 + 1; var5 < var1; ++var5) {
               if (var2[var5].getCoefficient(var3) != 0) {
                  var4 = true;
                  swapColumns(var2, var3, var5);
                  swapColumns(this.sqRootMatrix, var3, var5);
                  var5 = var1;
               }
            }

            if (!var4) {
               throw new ArithmeticException("Squaring matrix is not invertible.");
            }
         }

         int var9 = var2[var3].getCoefficient(var3);
         var5 = this.field.inverse(var9);
         var2[var3].multThisWithElement(var5);
         this.sqRootMatrix[var3].multThisWithElement(var5);

         for(int var6 = 0; var6 < var1; ++var6) {
            if (var6 != var3) {
               var9 = var2[var6].getCoefficient(var3);
               if (var9 != 0) {
                  PolynomialGF2mSmallM var7 = var2[var3].multWithElement(var9);
                  PolynomialGF2mSmallM var8 = this.sqRootMatrix[var3].multWithElement(var9);
                  var2[var6].addToThis(var7);
                  this.sqRootMatrix[var6].addToThis(var8);
               }
            }
         }
      }

   }

   private static void swapColumns(PolynomialGF2mSmallM[] var0, int var1, int var2) {
      PolynomialGF2mSmallM var3 = var0[var1];
      var0[var1] = var0[var2];
      var0[var2] = var3;
   }
}
