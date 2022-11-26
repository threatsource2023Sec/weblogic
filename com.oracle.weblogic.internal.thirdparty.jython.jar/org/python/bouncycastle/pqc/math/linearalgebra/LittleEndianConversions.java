package org.python.bouncycastle.pqc.math.linearalgebra;

public final class LittleEndianConversions {
   private LittleEndianConversions() {
   }

   public static int OS2IP(byte[] var0) {
      return var0[0] & 255 | (var0[1] & 255) << 8 | (var0[2] & 255) << 16 | (var0[3] & 255) << 24;
   }

   public static int OS2IP(byte[] var0, int var1) {
      int var2 = var0[var1++] & 255;
      var2 |= (var0[var1++] & 255) << 8;
      var2 |= (var0[var1++] & 255) << 16;
      var2 |= (var0[var1] & 255) << 24;
      return var2;
   }

   public static int OS2IP(byte[] var0, int var1, int var2) {
      int var3 = 0;

      for(int var4 = var2 - 1; var4 >= 0; --var4) {
         var3 |= (var0[var1 + var4] & 255) << 8 * var4;
      }

      return var3;
   }

   public static long OS2LIP(byte[] var0, int var1) {
      long var2 = (long)(var0[var1++] & 255);
      var2 |= (long)((var0[var1++] & 255) << 8);
      var2 |= (long)((var0[var1++] & 255) << 16);
      var2 |= ((long)var0[var1++] & 255L) << 24;
      var2 |= ((long)var0[var1++] & 255L) << 32;
      var2 |= ((long)var0[var1++] & 255L) << 40;
      var2 |= ((long)var0[var1++] & 255L) << 48;
      var2 |= ((long)var0[var1++] & 255L) << 56;
      return var2;
   }

   public static byte[] I2OSP(int var0) {
      byte[] var1 = new byte[]{(byte)var0, (byte)(var0 >>> 8), (byte)(var0 >>> 16), (byte)(var0 >>> 24)};
      return var1;
   }

   public static void I2OSP(int var0, byte[] var1, int var2) {
      var1[var2++] = (byte)var0;
      var1[var2++] = (byte)(var0 >>> 8);
      var1[var2++] = (byte)(var0 >>> 16);
      var1[var2++] = (byte)(var0 >>> 24);
   }

   public static void I2OSP(int var0, byte[] var1, int var2, int var3) {
      for(int var4 = var3 - 1; var4 >= 0; --var4) {
         var1[var2 + var4] = (byte)(var0 >>> 8 * var4);
      }

   }

   public static byte[] I2OSP(long var0) {
      byte[] var2 = new byte[]{(byte)((int)var0), (byte)((int)(var0 >>> 8)), (byte)((int)(var0 >>> 16)), (byte)((int)(var0 >>> 24)), (byte)((int)(var0 >>> 32)), (byte)((int)(var0 >>> 40)), (byte)((int)(var0 >>> 48)), (byte)((int)(var0 >>> 56))};
      return var2;
   }

   public static void I2OSP(long var0, byte[] var2, int var3) {
      var2[var3++] = (byte)((int)var0);
      var2[var3++] = (byte)((int)(var0 >>> 8));
      var2[var3++] = (byte)((int)(var0 >>> 16));
      var2[var3++] = (byte)((int)(var0 >>> 24));
      var2[var3++] = (byte)((int)(var0 >>> 32));
      var2[var3++] = (byte)((int)(var0 >>> 40));
      var2[var3++] = (byte)((int)(var0 >>> 48));
      var2[var3] = (byte)((int)(var0 >>> 56));
   }

   public static byte[] toByteArray(int[] var0, int var1) {
      int var2 = var0.length;
      byte[] var3 = new byte[var1];
      int var4 = 0;

      for(int var5 = 0; var5 <= var2 - 2; var4 += 4) {
         I2OSP(var0[var5], var3, var4);
         ++var5;
      }

      I2OSP(var0[var2 - 1], var3, var4, var1 - var4);
      return var3;
   }

   public static int[] toIntArray(byte[] var0) {
      int var1 = (var0.length + 3) / 4;
      int var2 = var0.length & 3;
      int[] var3 = new int[var1];
      int var4 = 0;

      for(int var5 = 0; var5 <= var1 - 2; var4 += 4) {
         var3[var5] = OS2IP(var0, var4);
         ++var5;
      }

      if (var2 != 0) {
         var3[var1 - 1] = OS2IP(var0, var4, var2);
      } else {
         var3[var1 - 1] = OS2IP(var0, var4);
      }

      return var3;
   }
}
