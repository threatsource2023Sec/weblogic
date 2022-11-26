package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2mField {
   private int degree = 0;
   private int polynomial;

   public GF2mField(int var1) {
      if (var1 >= 32) {
         throw new IllegalArgumentException(" Error: the degree of field is too large ");
      } else if (var1 < 1) {
         throw new IllegalArgumentException(" Error: the degree of field is non-positive ");
      } else {
         this.degree = var1;
         this.polynomial = PolynomialRingGF2.getIrreduciblePolynomial(var1);
      }
   }

   public GF2mField(int var1, int var2) {
      if (var1 != PolynomialRingGF2.degree(var2)) {
         throw new IllegalArgumentException(" Error: the degree is not correct");
      } else if (!PolynomialRingGF2.isIrreducible(var2)) {
         throw new IllegalArgumentException(" Error: given polynomial is reducible");
      } else {
         this.degree = var1;
         this.polynomial = var2;
      }
   }

   public GF2mField(byte[] var1) {
      if (var1.length != 4) {
         throw new IllegalArgumentException("byte array is not an encoded finite field");
      } else {
         this.polynomial = LittleEndianConversions.OS2IP(var1);
         if (!PolynomialRingGF2.isIrreducible(this.polynomial)) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
         } else {
            this.degree = PolynomialRingGF2.degree(this.polynomial);
         }
      }
   }

   public GF2mField(GF2mField var1) {
      this.degree = var1.degree;
      this.polynomial = var1.polynomial;
   }

   public int getDegree() {
      return this.degree;
   }

   public int getPolynomial() {
      return this.polynomial;
   }

   public byte[] getEncoded() {
      return LittleEndianConversions.I2OSP(this.polynomial);
   }

   public int add(int var1, int var2) {
      return var1 ^ var2;
   }

   public int mult(int var1, int var2) {
      return PolynomialRingGF2.modMultiply(var1, var2, this.polynomial);
   }

   public int exp(int var1, int var2) {
      if (var2 == 0) {
         return 1;
      } else if (var1 == 0) {
         return 0;
      } else if (var1 == 1) {
         return 1;
      } else {
         int var3 = 1;
         if (var2 < 0) {
            var1 = this.inverse(var1);
            var2 = -var2;
         }

         while(var2 != 0) {
            if ((var2 & 1) == 1) {
               var3 = this.mult(var3, var1);
            }

            var1 = this.mult(var1, var1);
            var2 >>>= 1;
         }

         return var3;
      }
   }

   public int inverse(int var1) {
      int var2 = (1 << this.degree) - 2;
      return this.exp(var1, var2);
   }

   public int sqRoot(int var1) {
      for(int var2 = 1; var2 < this.degree; ++var2) {
         var1 = this.mult(var1, var1);
      }

      return var1;
   }

   public int getRandomElement(SecureRandom var1) {
      int var2 = RandUtils.nextInt(var1, 1 << this.degree);
      return var2;
   }

   public int getRandomNonZeroElement() {
      return this.getRandomNonZeroElement(new SecureRandom());
   }

   public int getRandomNonZeroElement(SecureRandom var1) {
      int var2 = 1048576;
      int var3 = 0;

      int var4;
      for(var4 = RandUtils.nextInt(var1, 1 << this.degree); var4 == 0 && var3 < var2; ++var3) {
         var4 = RandUtils.nextInt(var1, 1 << this.degree);
      }

      if (var3 == var2) {
         var4 = 1;
      }

      return var4;
   }

   public boolean isElementOfThisField(int var1) {
      if (this.degree == 31) {
         return var1 >= 0;
      } else {
         return var1 >= 0 && var1 < 1 << this.degree;
      }
   }

   public String elementToStr(int var1) {
      String var2 = "";

      for(int var3 = 0; var3 < this.degree; ++var3) {
         if (((byte)var1 & 1) == 0) {
            var2 = "0" + var2;
         } else {
            var2 = "1" + var2;
         }

         var1 >>>= 1;
      }

      return var2;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GF2mField) {
         GF2mField var2 = (GF2mField)var1;
         return this.degree == var2.degree && this.polynomial == var2.polynomial;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.polynomial;
   }

   public String toString() {
      String var1 = "Finite Field GF(2^" + this.degree + ") = " + "GF(2)[X]/<" + polyToString(this.polynomial) + "> ";
      return var1;
   }

   private static String polyToString(int var0) {
      String var1 = "";
      if (var0 == 0) {
         var1 = "0";
      } else {
         byte var2 = (byte)(var0 & 1);
         if (var2 == 1) {
            var1 = "1";
         }

         var0 >>>= 1;

         for(int var3 = 1; var0 != 0; ++var3) {
            var2 = (byte)(var0 & 1);
            if (var2 == 1) {
               var1 = var1 + "+x^" + var3;
            }

            var0 >>>= 1;
         }
      }

      return var1;
   }
}
