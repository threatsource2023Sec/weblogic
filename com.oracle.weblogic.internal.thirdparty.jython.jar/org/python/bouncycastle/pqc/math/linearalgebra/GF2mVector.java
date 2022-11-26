package org.python.bouncycastle.pqc.math.linearalgebra;

public class GF2mVector extends Vector {
   private GF2mField field;
   private int[] vector;

   public GF2mVector(GF2mField var1, byte[] var2) {
      this.field = new GF2mField(var1);
      int var3 = 8;

      int var4;
      for(var4 = 1; var1.getDegree() > var3; var3 += 8) {
         ++var4;
      }

      if (var2.length % var4 != 0) {
         throw new IllegalArgumentException("Byte array is not an encoded vector over the given finite field.");
      } else {
         this.length = var2.length / var4;
         this.vector = new int[this.length];
         var4 = 0;

         for(int var5 = 0; var5 < this.vector.length; ++var5) {
            for(int var6 = 0; var6 < var3; var6 += 8) {
               int[] var10000 = this.vector;
               var10000[var5] |= (var2[var4++] & 255) << var6;
            }

            if (!var1.isElementOfThisField(this.vector[var5])) {
               throw new IllegalArgumentException("Byte array is not an encoded vector over the given finite field.");
            }
         }

      }
   }

   public GF2mVector(GF2mField var1, int[] var2) {
      this.field = var1;
      this.length = var2.length;

      for(int var3 = var2.length - 1; var3 >= 0; --var3) {
         if (!var1.isElementOfThisField(var2[var3])) {
            throw new ArithmeticException("Element array is not specified over the given finite field.");
         }
      }

      this.vector = IntUtils.clone(var2);
   }

   public GF2mVector(GF2mVector var1) {
      this.field = new GF2mField(var1.field);
      this.length = var1.length;
      this.vector = IntUtils.clone(var1.vector);
   }

   public GF2mField getField() {
      return this.field;
   }

   public int[] getIntArrayForm() {
      return IntUtils.clone(this.vector);
   }

   public byte[] getEncoded() {
      int var1 = 8;

      int var2;
      for(var2 = 1; this.field.getDegree() > var1; var1 += 8) {
         ++var2;
      }

      byte[] var3 = new byte[this.vector.length * var2];
      var2 = 0;

      for(int var4 = 0; var4 < this.vector.length; ++var4) {
         for(int var5 = 0; var5 < var1; var5 += 8) {
            var3[var2++] = (byte)(this.vector[var4] >>> var5);
         }
      }

      return var3;
   }

   public boolean isZero() {
      for(int var1 = this.vector.length - 1; var1 >= 0; --var1) {
         if (this.vector[var1] != 0) {
            return false;
         }
      }

      return true;
   }

   public Vector add(Vector var1) {
      throw new RuntimeException("not implemented");
   }

   public Vector multiply(Permutation var1) {
      int[] var2 = var1.getVector();
      if (this.length != var2.length) {
         throw new ArithmeticException("permutation size and vector size mismatch");
      } else {
         int[] var3 = new int[this.length];

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3[var4] = this.vector[var2[var4]];
         }

         return new GF2mVector(this.field, var3);
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof GF2mVector)) {
         return false;
      } else {
         GF2mVector var2 = (GF2mVector)var1;
         return !this.field.equals(var2.field) ? false : IntUtils.equals(this.vector, var2.vector);
      }
   }

   public int hashCode() {
      int var1 = this.field.hashCode();
      var1 = var1 * 31 + this.vector.hashCode();
      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.vector.length; ++var2) {
         for(int var3 = 0; var3 < this.field.getDegree(); ++var3) {
            int var4 = var3 & 31;
            int var5 = 1 << var4;
            int var6 = this.vector[var2] & var5;
            if (var6 != 0) {
               var1.append('1');
            } else {
               var1.append('0');
            }
         }

         var1.append(' ');
      }

      return var1.toString();
   }
}
