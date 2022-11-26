package org.python.bouncycastle.pqc.math.linearalgebra;

public final class IntUtils {
   private IntUtils() {
   }

   public static boolean equals(int[] var0, int[] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         boolean var2 = true;

         for(int var3 = var0.length - 1; var3 >= 0; --var3) {
            var2 &= var0[var3] == var1[var3];
         }

         return var2;
      }
   }

   public static int[] clone(int[] var0) {
      int[] var1 = new int[var0.length];
      System.arraycopy(var0, 0, var1, 0, var0.length);
      return var1;
   }

   public static void fill(int[] var0, int var1) {
      for(int var2 = var0.length - 1; var2 >= 0; --var2) {
         var0[var2] = var1;
      }

   }

   public static void quicksort(int[] var0) {
      quicksort(var0, 0, var0.length - 1);
   }

   public static void quicksort(int[] var0, int var1, int var2) {
      if (var2 > var1) {
         int var3 = partition(var0, var1, var2, var2);
         quicksort(var0, var1, var3 - 1);
         quicksort(var0, var3 + 1, var2);
      }

   }

   private static int partition(int[] var0, int var1, int var2, int var3) {
      int var4 = var0[var3];
      var0[var3] = var0[var2];
      var0[var2] = var4;
      int var5 = var1;

      int var6;
      for(var6 = var1; var6 < var2; ++var6) {
         if (var0[var6] <= var4) {
            int var7 = var0[var5];
            var0[var5] = var0[var6];
            var0[var6] = var7;
            ++var5;
         }
      }

      var6 = var0[var5];
      var0[var5] = var0[var2];
      var0[var2] = var6;
      return var5;
   }

   public static int[] subArray(int[] var0, int var1, int var2) {
      int[] var3 = new int[var2 - var1];
      System.arraycopy(var0, var1, var3, 0, var2 - var1);
      return var3;
   }

   public static String toString(int[] var0) {
      String var1 = "";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 = var1 + var0[var2] + " ";
      }

      return var1;
   }

   public static String toHexString(int[] var0) {
      return ByteUtils.toHexString(BigEndianConversions.toByteArray(var0));
   }
}
