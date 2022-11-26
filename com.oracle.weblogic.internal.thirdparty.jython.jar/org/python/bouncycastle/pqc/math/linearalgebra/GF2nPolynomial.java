package org.python.bouncycastle.pqc.math.linearalgebra;

public class GF2nPolynomial {
   private GF2nElement[] coeff;
   private int size;

   public GF2nPolynomial(int var1, GF2nElement var2) {
      this.size = var1;
      this.coeff = new GF2nElement[this.size];

      for(int var3 = 0; var3 < this.size; ++var3) {
         this.coeff[var3] = (GF2nElement)var2.clone();
      }

   }

   private GF2nPolynomial(int var1) {
      this.size = var1;
      this.coeff = new GF2nElement[this.size];
   }

   public GF2nPolynomial(GF2nPolynomial var1) {
      this.coeff = new GF2nElement[var1.size];
      this.size = var1.size;

      for(int var2 = 0; var2 < this.size; ++var2) {
         this.coeff[var2] = (GF2nElement)var1.coeff[var2].clone();
      }

   }

   public GF2nPolynomial(GF2Polynomial var1, GF2nField var2) {
      this.size = var2.getDegree() + 1;
      this.coeff = new GF2nElement[this.size];
      int var3;
      if (var2 instanceof GF2nONBField) {
         for(var3 = 0; var3 < this.size; ++var3) {
            if (var1.testBit(var3)) {
               this.coeff[var3] = GF2nONBElement.ONE((GF2nONBField)var2);
            } else {
               this.coeff[var3] = GF2nONBElement.ZERO((GF2nONBField)var2);
            }
         }
      } else {
         if (!(var2 instanceof GF2nPolynomialField)) {
            throw new IllegalArgumentException("PolynomialGF2n(Bitstring, GF2nField): B1 must be an instance of GF2nONBField or GF2nPolynomialField!");
         }

         for(var3 = 0; var3 < this.size; ++var3) {
            if (var1.testBit(var3)) {
               this.coeff[var3] = GF2nPolynomialElement.ONE((GF2nPolynomialField)var2);
            } else {
               this.coeff[var3] = GF2nPolynomialElement.ZERO((GF2nPolynomialField)var2);
            }
         }
      }

   }

   public final void assignZeroToElements() {
      for(int var1 = 0; var1 < this.size; ++var1) {
         this.coeff[var1].assignZero();
      }

   }

   public final int size() {
      return this.size;
   }

   public final int getDegree() {
      for(int var1 = this.size - 1; var1 >= 0; --var1) {
         if (!this.coeff[var1].isZero()) {
            return var1;
         }
      }

      return -1;
   }

   public final void enlarge(int var1) {
      if (var1 > this.size) {
         GF2nElement[] var2 = new GF2nElement[var1];
         System.arraycopy(this.coeff, 0, var2, 0, this.size);
         GF2nField var3 = this.coeff[0].getField();
         int var4;
         if (this.coeff[0] instanceof GF2nPolynomialElement) {
            for(var4 = this.size; var4 < var1; ++var4) {
               var2[var4] = GF2nPolynomialElement.ZERO((GF2nPolynomialField)var3);
            }
         } else if (this.coeff[0] instanceof GF2nONBElement) {
            for(var4 = this.size; var4 < var1; ++var4) {
               var2[var4] = GF2nONBElement.ZERO((GF2nONBField)var3);
            }
         }

         this.size = var1;
         this.coeff = var2;
      }
   }

   public final void shrink() {
      int var1;
      for(var1 = this.size - 1; this.coeff[var1].isZero() && var1 > 0; --var1) {
      }

      ++var1;
      if (var1 < this.size) {
         GF2nElement[] var2 = new GF2nElement[var1];
         System.arraycopy(this.coeff, 0, var2, 0, var1);
         this.coeff = var2;
         this.size = var1;
      }

   }

   public final void set(int var1, GF2nElement var2) {
      if (!(var2 instanceof GF2nPolynomialElement) && !(var2 instanceof GF2nONBElement)) {
         throw new IllegalArgumentException("PolynomialGF2n.set f must be an instance of either GF2nPolynomialElement or GF2nONBElement!");
      } else {
         this.coeff[var1] = (GF2nElement)var2.clone();
      }
   }

   public final GF2nElement at(int var1) {
      return this.coeff[var1];
   }

   public final boolean isZero() {
      for(int var1 = 0; var1 < this.size; ++var1) {
         if (this.coeff[var1] != null && !this.coeff[var1].isZero()) {
            return false;
         }
      }

      return true;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GF2nPolynomial) {
         GF2nPolynomial var2 = (GF2nPolynomial)var1;
         if (this.getDegree() != var2.getDegree()) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.size; ++var3) {
               if (!this.coeff[var3].equals(var2.coeff[var3])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getDegree() + this.coeff.hashCode();
   }

   public final GF2nPolynomial add(GF2nPolynomial var1) throws RuntimeException {
      GF2nPolynomial var2;
      int var3;
      if (this.size() >= var1.size()) {
         var2 = new GF2nPolynomial(this.size());

         for(var3 = 0; var3 < var1.size(); ++var3) {
            var2.coeff[var3] = (GF2nElement)this.coeff[var3].add(var1.coeff[var3]);
         }

         while(var3 < this.size()) {
            var2.coeff[var3] = this.coeff[var3];
            ++var3;
         }
      } else {
         var2 = new GF2nPolynomial(var1.size());

         for(var3 = 0; var3 < this.size(); ++var3) {
            var2.coeff[var3] = (GF2nElement)this.coeff[var3].add(var1.coeff[var3]);
         }

         while(var3 < var1.size()) {
            var2.coeff[var3] = var1.coeff[var3];
            ++var3;
         }
      }

      return var2;
   }

   public final GF2nPolynomial scalarMultiply(GF2nElement var1) throws RuntimeException {
      GF2nPolynomial var2 = new GF2nPolynomial(this.size());

      for(int var3 = 0; var3 < this.size(); ++var3) {
         var2.coeff[var3] = (GF2nElement)this.coeff[var3].multiply(var1);
      }

      return var2;
   }

   public final GF2nPolynomial multiply(GF2nPolynomial var1) throws RuntimeException {
      int var2 = this.size();
      int var3 = var1.size();
      if (var2 != var3) {
         throw new IllegalArgumentException("PolynomialGF2n.multiply: this and b must have the same size!");
      } else {
         GF2nPolynomial var4 = new GF2nPolynomial((var2 << 1) - 1);

         for(int var5 = 0; var5 < this.size(); ++var5) {
            for(int var6 = 0; var6 < var1.size(); ++var6) {
               if (var4.coeff[var5 + var6] == null) {
                  var4.coeff[var5 + var6] = (GF2nElement)this.coeff[var5].multiply(var1.coeff[var6]);
               } else {
                  var4.coeff[var5 + var6] = (GF2nElement)var4.coeff[var5 + var6].add(this.coeff[var5].multiply(var1.coeff[var6]));
               }
            }
         }

         return var4;
      }
   }

   public final GF2nPolynomial multiplyAndReduce(GF2nPolynomial var1, GF2nPolynomial var2) throws RuntimeException, ArithmeticException {
      return this.multiply(var1).reduce(var2);
   }

   public final GF2nPolynomial reduce(GF2nPolynomial var1) throws RuntimeException, ArithmeticException {
      return this.remainder(var1);
   }

   public final void shiftThisLeft(int var1) {
      if (var1 > 0) {
         int var2 = this.size;
         GF2nField var3 = this.coeff[0].getField();
         this.enlarge(this.size + var1);

         int var4;
         for(var4 = var2 - 1; var4 >= 0; --var4) {
            this.coeff[var4 + var1] = this.coeff[var4];
         }

         if (this.coeff[0] instanceof GF2nPolynomialElement) {
            for(var4 = var1 - 1; var4 >= 0; --var4) {
               this.coeff[var4] = GF2nPolynomialElement.ZERO((GF2nPolynomialField)var3);
            }
         } else if (this.coeff[0] instanceof GF2nONBElement) {
            for(var4 = var1 - 1; var4 >= 0; --var4) {
               this.coeff[var4] = GF2nONBElement.ZERO((GF2nONBField)var3);
            }
         }
      }

   }

   public final GF2nPolynomial shiftLeft(int var1) {
      if (var1 <= 0) {
         return new GF2nPolynomial(this);
      } else {
         GF2nPolynomial var2 = new GF2nPolynomial(this.size + var1, this.coeff[0]);
         var2.assignZeroToElements();

         for(int var3 = 0; var3 < this.size; ++var3) {
            var2.coeff[var3 + var1] = this.coeff[var3];
         }

         return var2;
      }
   }

   public final GF2nPolynomial[] divide(GF2nPolynomial var1) throws RuntimeException, ArithmeticException {
      GF2nPolynomial[] var2 = new GF2nPolynomial[2];
      GF2nPolynomial var3 = new GF2nPolynomial(this);
      var3.shrink();
      int var4 = var1.getDegree();
      GF2nElement var5 = (GF2nElement)var1.coeff[var4].invert();
      if (var3.getDegree() < var4) {
         var2[0] = new GF2nPolynomial(this);
         var2[0].assignZeroToElements();
         var2[0].shrink();
         var2[1] = new GF2nPolynomial(this);
         var2[1].shrink();
         return var2;
      } else {
         var2[0] = new GF2nPolynomial(this);
         var2[0].assignZeroToElements();

         for(int var6 = var3.getDegree() - var4; var6 >= 0; var6 = var3.getDegree() - var4) {
            GF2nElement var7 = (GF2nElement)var3.coeff[var3.getDegree()].multiply(var5);
            GF2nPolynomial var8 = var1.scalarMultiply(var7);
            var8.shiftThisLeft(var6);
            var3 = var3.add(var8);
            var3.shrink();
            var2[0].coeff[var6] = (GF2nElement)var7.clone();
         }

         var2[1] = var3;
         var2[0].shrink();
         return var2;
      }
   }

   public final GF2nPolynomial remainder(GF2nPolynomial var1) throws RuntimeException, ArithmeticException {
      GF2nPolynomial[] var2 = new GF2nPolynomial[2];
      var2 = this.divide(var1);
      return var2[1];
   }

   public final GF2nPolynomial quotient(GF2nPolynomial var1) throws RuntimeException, ArithmeticException {
      GF2nPolynomial[] var2 = new GF2nPolynomial[2];
      var2 = this.divide(var1);
      return var2[0];
   }

   public final GF2nPolynomial gcd(GF2nPolynomial var1) throws RuntimeException, ArithmeticException {
      GF2nPolynomial var2 = new GF2nPolynomial(this);
      GF2nPolynomial var3 = new GF2nPolynomial(var1);
      var2.shrink();
      var3.shrink();

      while(!var3.isZero()) {
         GF2nPolynomial var4 = var2.remainder(var3);
         var2 = var3;
         var3 = var4;
      }

      GF2nElement var5 = var2.coeff[var2.getDegree()];
      GF2nPolynomial var6 = var2.scalarMultiply((GF2nElement)var5.invert());
      return var6;
   }
}
