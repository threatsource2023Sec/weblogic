package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2nPolynomialField extends GF2nField {
   GF2Polynomial[] squaringMatrix;
   private boolean isTrinomial = false;
   private boolean isPentanomial = false;
   private int tc;
   private int[] pc = new int[3];

   public GF2nPolynomialField(int var1, SecureRandom var2) {
      super(var2);
      if (var1 < 3) {
         throw new IllegalArgumentException("k must be at least 3");
      } else {
         this.mDegree = var1;
         this.computeFieldPolynomial();
         this.computeSquaringMatrix();
         this.fields = new java.util.Vector();
         this.matrices = new java.util.Vector();
      }
   }

   public GF2nPolynomialField(int var1, SecureRandom var2, boolean var3) {
      super(var2);
      if (var1 < 3) {
         throw new IllegalArgumentException("k must be at least 3");
      } else {
         this.mDegree = var1;
         if (var3) {
            this.computeFieldPolynomial();
         } else {
            this.computeFieldPolynomial2();
         }

         this.computeSquaringMatrix();
         this.fields = new java.util.Vector();
         this.matrices = new java.util.Vector();
      }
   }

   public GF2nPolynomialField(int var1, SecureRandom var2, GF2Polynomial var3) throws RuntimeException {
      super(var2);
      if (var1 < 3) {
         throw new IllegalArgumentException("degree must be at least 3");
      } else if (var3.getLength() != var1 + 1) {
         throw new RuntimeException();
      } else if (!var3.isIrreducible()) {
         throw new RuntimeException();
      } else {
         this.mDegree = var1;
         this.fieldPolynomial = var3;
         this.computeSquaringMatrix();
         int var4 = 2;

         for(int var5 = 1; var5 < this.fieldPolynomial.getLength() - 1; ++var5) {
            if (this.fieldPolynomial.testBit(var5)) {
               ++var4;
               if (var4 == 3) {
                  this.tc = var5;
               }

               if (var4 <= 5) {
                  this.pc[var4 - 3] = var5;
               }
            }
         }

         if (var4 == 3) {
            this.isTrinomial = true;
         }

         if (var4 == 5) {
            this.isPentanomial = true;
         }

         this.fields = new java.util.Vector();
         this.matrices = new java.util.Vector();
      }
   }

   public boolean isTrinomial() {
      return this.isTrinomial;
   }

   public boolean isPentanomial() {
      return this.isPentanomial;
   }

   public int getTc() throws RuntimeException {
      if (!this.isTrinomial) {
         throw new RuntimeException();
      } else {
         return this.tc;
      }
   }

   public int[] getPc() throws RuntimeException {
      if (!this.isPentanomial) {
         throw new RuntimeException();
      } else {
         int[] var1 = new int[3];
         System.arraycopy(this.pc, 0, var1, 0, 3);
         return var1;
      }
   }

   public GF2Polynomial getSquaringVector(int var1) {
      return new GF2Polynomial(this.squaringMatrix[var1]);
   }

   protected GF2nElement getRandomRoot(GF2Polynomial var1) {
      GF2nPolynomial var2 = new GF2nPolynomial(var1, this);

      for(int var3 = var2.getDegree(); var3 > 1; var3 = var2.getDegree()) {
         GF2nPolynomial var8;
         int var9;
         do {
            GF2nPolynomialElement var4 = new GF2nPolynomialElement(this, this.random);
            GF2nPolynomial var5 = new GF2nPolynomial(2, GF2nPolynomialElement.ZERO(this));
            var5.set(1, var4);
            GF2nPolynomial var6 = new GF2nPolynomial(var5);

            for(int var7 = 1; var7 <= this.mDegree - 1; ++var7) {
               var6 = var6.multiplyAndReduce(var6, var2);
               var6 = var6.add(var5);
            }

            var8 = var6.gcd(var2);
            var9 = var8.getDegree();
            var3 = var2.getDegree();
         } while(var9 == 0 || var9 == var3);

         if (var9 << 1 > var3) {
            var2 = var2.quotient(var8);
         } else {
            var2 = new GF2nPolynomial(var8);
         }
      }

      return var2.at(0);
   }

   protected void computeCOBMatrix(GF2nField var1) {
      if (this.mDegree != var1.mDegree) {
         throw new IllegalArgumentException("GF2nPolynomialField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
      } else if (var1 instanceof GF2nONBField) {
         var1.computeCOBMatrix(this);
      } else {
         GF2Polynomial[] var2 = new GF2Polynomial[this.mDegree];

         int var3;
         for(var3 = 0; var3 < this.mDegree; ++var3) {
            var2[var3] = new GF2Polynomial(this.mDegree);
         }

         GF2nElement var4;
         do {
            var4 = var1.getRandomRoot(this.fieldPolynomial);
         } while(var4.isZero());

         Object var5;
         if (var4 instanceof GF2nONBElement) {
            var5 = new GF2nONBElement[this.mDegree];
            ((Object[])var5)[this.mDegree - 1] = GF2nONBElement.ONE((GF2nONBField)var1);
         } else {
            var5 = new GF2nPolynomialElement[this.mDegree];
            ((Object[])var5)[this.mDegree - 1] = GF2nPolynomialElement.ONE((GF2nPolynomialField)var1);
         }

         ((Object[])var5)[this.mDegree - 2] = var4;

         for(var3 = this.mDegree - 3; var3 >= 0; --var3) {
            ((Object[])var5)[var3] = (GF2nElement)((GF2nElement)((Object[])var5)[var3 + 1]).multiply(var4);
         }

         int var6;
         if (var1 instanceof GF2nONBField) {
            for(var3 = 0; var3 < this.mDegree; ++var3) {
               for(var6 = 0; var6 < this.mDegree; ++var6) {
                  if (((GF2nElement)((Object[])var5)[var3]).testBit(this.mDegree - var6 - 1)) {
                     var2[this.mDegree - var6 - 1].setBit(this.mDegree - var3 - 1);
                  }
               }
            }
         } else {
            for(var3 = 0; var3 < this.mDegree; ++var3) {
               for(var6 = 0; var6 < this.mDegree; ++var6) {
                  if (((GF2nElement)((Object[])var5)[var3]).testBit(var6)) {
                     var2[this.mDegree - var6 - 1].setBit(this.mDegree - var3 - 1);
                  }
               }
            }
         }

         this.fields.addElement(var1);
         this.matrices.addElement(var2);
         var1.fields.addElement(this);
         var1.matrices.addElement(this.invertMatrix(var2));
      }
   }

   private void computeSquaringMatrix() {
      GF2Polynomial[] var1 = new GF2Polynomial[this.mDegree - 1];
      this.squaringMatrix = new GF2Polynomial[this.mDegree];

      int var2;
      for(var2 = 0; var2 < this.squaringMatrix.length; ++var2) {
         this.squaringMatrix[var2] = new GF2Polynomial(this.mDegree, "ZERO");
      }

      for(var2 = 0; var2 < this.mDegree - 1; ++var2) {
         var1[var2] = (new GF2Polynomial(1, "ONE")).shiftLeft(this.mDegree + var2).remainder(this.fieldPolynomial);
      }

      for(var2 = 1; var2 <= Math.abs(this.mDegree >> 1); ++var2) {
         for(int var3 = 1; var3 <= this.mDegree; ++var3) {
            if (var1[this.mDegree - (var2 << 1)].testBit(this.mDegree - var3)) {
               this.squaringMatrix[var3 - 1].setBit(this.mDegree - var2);
            }
         }
      }

      for(var2 = Math.abs(this.mDegree >> 1) + 1; var2 <= this.mDegree; ++var2) {
         this.squaringMatrix[(var2 << 1) - this.mDegree - 1].setBit(this.mDegree - var2);
      }

   }

   protected void computeFieldPolynomial() {
      if (!this.testTrinomials()) {
         if (!this.testPentanomials()) {
            this.testRandom();
         }
      }
   }

   protected void computeFieldPolynomial2() {
      if (!this.testTrinomials()) {
         if (!this.testPentanomials()) {
            this.testRandom();
         }
      }
   }

   private boolean testTrinomials() {
      boolean var1 = false;
      int var2 = 0;
      this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1);
      this.fieldPolynomial.setBit(0);
      this.fieldPolynomial.setBit(this.mDegree);

      for(int var3 = 1; var3 < this.mDegree && !var1; ++var3) {
         this.fieldPolynomial.setBit(var3);
         var1 = this.fieldPolynomial.isIrreducible();
         ++var2;
         if (var1) {
            this.isTrinomial = true;
            this.tc = var3;
            return var1;
         }

         this.fieldPolynomial.resetBit(var3);
         var1 = this.fieldPolynomial.isIrreducible();
      }

      return var1;
   }

   private boolean testPentanomials() {
      boolean var1 = false;
      int var2 = 0;
      this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1);
      this.fieldPolynomial.setBit(0);
      this.fieldPolynomial.setBit(this.mDegree);

      for(int var3 = 1; var3 <= this.mDegree - 3 && !var1; ++var3) {
         this.fieldPolynomial.setBit(var3);

         for(int var4 = var3 + 1; var4 <= this.mDegree - 2 && !var1; ++var4) {
            this.fieldPolynomial.setBit(var4);

            for(int var5 = var4 + 1; var5 <= this.mDegree - 1 && !var1; ++var5) {
               this.fieldPolynomial.setBit(var5);
               if ((this.mDegree & 1) != 0 | (var3 & 1) != 0 | (var4 & 1) != 0 | (var5 & 1) != 0) {
                  var1 = this.fieldPolynomial.isIrreducible();
                  ++var2;
                  if (var1) {
                     this.isPentanomial = true;
                     this.pc[0] = var3;
                     this.pc[1] = var4;
                     this.pc[2] = var5;
                     return var1;
                  }
               }

               this.fieldPolynomial.resetBit(var5);
            }

            this.fieldPolynomial.resetBit(var4);
         }

         this.fieldPolynomial.resetBit(var3);
      }

      return var1;
   }

   private boolean testRandom() {
      boolean var1 = false;
      this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1);
      int var2 = 0;

      do {
         if (var1) {
            return var1;
         }

         ++var2;
         this.fieldPolynomial.randomize();
         this.fieldPolynomial.setBit(this.mDegree);
         this.fieldPolynomial.setBit(0);
      } while(!this.fieldPolynomial.isIrreducible());

      var1 = true;
      return var1;
   }
}
