package org.python.bouncycastle.pqc.math.linearalgebra;

public final class CharUtils {
   private CharUtils() {
   }

   public static char[] clone(char[] var0) {
      char[] var1 = new char[var0.length];
      System.arraycopy(var0, 0, var1, 0, var0.length);
      return var1;
   }

   public static byte[] toByteArray(char[] var0) {
      byte[] var1 = new byte[var0.length];

      for(int var2 = var0.length - 1; var2 >= 0; --var2) {
         var1[var2] = (byte)var0[var2];
      }

      return var1;
   }

   public static byte[] toByteArrayForPBE(char[] var0) {
      byte[] var1 = new byte[var0.length];

      int var2;
      for(var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (byte)var0[var2];
      }

      var2 = var1.length * 2;
      byte[] var3 = new byte[var2 + 2];
      boolean var4 = false;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         int var6 = var5 * 2;
         var3[var6] = 0;
         var3[var6 + 1] = var1[var5];
      }

      var3[var2] = 0;
      var3[var2 + 1] = 0;
      return var3;
   }

   public static boolean equals(char[] var0, char[] var1) {
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
}
