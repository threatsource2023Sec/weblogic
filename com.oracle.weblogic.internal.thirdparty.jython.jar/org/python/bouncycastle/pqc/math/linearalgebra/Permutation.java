package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class Permutation {
   private int[] perm;

   public Permutation(int var1) {
      if (var1 <= 0) {
         throw new IllegalArgumentException("invalid length");
      } else {
         this.perm = new int[var1];

         for(int var2 = var1 - 1; var2 >= 0; this.perm[var2] = var2--) {
         }

      }
   }

   public Permutation(int[] var1) {
      if (!this.isPermutation(var1)) {
         throw new IllegalArgumentException("array is not a permutation vector");
      } else {
         this.perm = IntUtils.clone(var1);
      }
   }

   public Permutation(byte[] var1) {
      if (var1.length <= 4) {
         throw new IllegalArgumentException("invalid encoding");
      } else {
         int var2 = LittleEndianConversions.OS2IP(var1, 0);
         int var3 = IntegerFunctions.ceilLog256(var2 - 1);
         if (var1.length != 4 + var2 * var3) {
            throw new IllegalArgumentException("invalid encoding");
         } else {
            this.perm = new int[var2];

            for(int var4 = 0; var4 < var2; ++var4) {
               this.perm[var4] = LittleEndianConversions.OS2IP(var1, 4 + var4 * var3, var3);
            }

            if (!this.isPermutation(this.perm)) {
               throw new IllegalArgumentException("invalid encoding");
            }
         }
      }
   }

   public Permutation(int var1, SecureRandom var2) {
      if (var1 <= 0) {
         throw new IllegalArgumentException("invalid length");
      } else {
         this.perm = new int[var1];
         int[] var3 = new int[var1];

         int var4;
         for(var4 = 0; var4 < var1; var3[var4] = var4++) {
         }

         var4 = var1;

         for(int var5 = 0; var5 < var1; ++var5) {
            int var6 = RandUtils.nextInt(var2, var4);
            --var4;
            this.perm[var5] = var3[var6];
            var3[var6] = var3[var4];
         }

      }
   }

   public byte[] getEncoded() {
      int var1 = this.perm.length;
      int var2 = IntegerFunctions.ceilLog256(var1 - 1);
      byte[] var3 = new byte[4 + var1 * var2];
      LittleEndianConversions.I2OSP(var1, var3, 0);

      for(int var4 = 0; var4 < var1; ++var4) {
         LittleEndianConversions.I2OSP(this.perm[var4], var3, 4 + var4 * var2, var2);
      }

      return var3;
   }

   public int[] getVector() {
      return IntUtils.clone(this.perm);
   }

   public Permutation computeInverse() {
      Permutation var1 = new Permutation(this.perm.length);

      for(int var2 = this.perm.length - 1; var2 >= 0; var1.perm[this.perm[var2]] = var2--) {
      }

      return var1;
   }

   public Permutation rightMultiply(Permutation var1) {
      if (var1.perm.length != this.perm.length) {
         throw new IllegalArgumentException("length mismatch");
      } else {
         Permutation var2 = new Permutation(this.perm.length);

         for(int var3 = this.perm.length - 1; var3 >= 0; --var3) {
            var2.perm[var3] = this.perm[var1.perm[var3]];
         }

         return var2;
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Permutation)) {
         return false;
      } else {
         Permutation var2 = (Permutation)var1;
         return IntUtils.equals(this.perm, var2.perm);
      }
   }

   public String toString() {
      String var1 = "[" + this.perm[0];

      for(int var2 = 1; var2 < this.perm.length; ++var2) {
         var1 = var1 + ", " + this.perm[var2];
      }

      var1 = var1 + "]";
      return var1;
   }

   public int hashCode() {
      return this.perm.hashCode();
   }

   private boolean isPermutation(int[] var1) {
      int var2 = var1.length;
      boolean[] var3 = new boolean[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         if (var1[var4] < 0 || var1[var4] >= var2 || var3[var1[var4]]) {
            return false;
         }

         var3[var1[var4]] = true;
      }

      return true;
   }
}
