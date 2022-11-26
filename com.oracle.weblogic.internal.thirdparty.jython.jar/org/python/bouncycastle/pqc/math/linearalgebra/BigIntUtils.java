package org.python.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;

public final class BigIntUtils {
   private BigIntUtils() {
   }

   public static boolean equals(BigInteger[] var0, BigInteger[] var1) {
      int var2 = 0;
      if (var0.length != var1.length) {
         return false;
      } else {
         for(int var3 = 0; var3 < var0.length; ++var3) {
            var2 |= var0[var3].compareTo(var1[var3]);
         }

         return var2 == 0;
      }
   }

   public static void fill(BigInteger[] var0, BigInteger var1) {
      for(int var2 = var0.length - 1; var2 >= 0; --var2) {
         var0[var2] = var1;
      }

   }

   public static BigInteger[] subArray(BigInteger[] var0, int var1, int var2) {
      BigInteger[] var3 = new BigInteger[var2 - var1];
      System.arraycopy(var0, var1, var3, 0, var2 - var1);
      return var3;
   }

   public static int[] toIntArray(BigInteger[] var0) {
      int[] var1 = new int[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = var0[var2].intValue();
      }

      return var1;
   }

   public static int[] toIntArrayModQ(int var0, BigInteger[] var1) {
      BigInteger var2 = BigInteger.valueOf((long)var0);
      int[] var3 = new int[var1.length];

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3[var4] = var1[var4].mod(var2).intValue();
      }

      return var3;
   }

   public static byte[] toMinimalByteArray(BigInteger var0) {
      byte[] var1 = var0.toByteArray();
      if (var1.length != 1 && (var0.bitLength() & 7) == 0) {
         byte[] var2 = new byte[var0.bitLength() >> 3];
         System.arraycopy(var1, 1, var2, 0, var2.length);
         return var2;
      } else {
         return var1;
      }
   }
}
