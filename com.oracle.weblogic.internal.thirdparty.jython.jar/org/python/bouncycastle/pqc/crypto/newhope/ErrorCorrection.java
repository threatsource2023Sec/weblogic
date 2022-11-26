package org.python.bouncycastle.pqc.crypto.newhope;

import org.python.bouncycastle.util.Arrays;

class ErrorCorrection {
   static int abs(int var0) {
      int var1 = var0 >> 31;
      return (var0 ^ var1) - var1;
   }

   static int f(int[] var0, int var1, int var2, int var3) {
      int var4 = var3 * 2730;
      int var5 = var4 >> 25;
      var4 = var3 - var5 * 12289;
      var4 = 12288 - var4;
      var4 >>= 31;
      var5 -= var4;
      int var6 = var5 & 1;
      int var7 = var5 >> 1;
      var0[var1] = var7 + var6;
      --var5;
      var6 = var5 & 1;
      var0[var2] = (var5 >> 1) + var6;
      return abs(var3 - var0[var1] * 2 * 12289);
   }

   static int g(int var0) {
      int var1 = var0 * 2730;
      int var2 = var1 >> 27;
      var1 = var0 - var2 * '쀄';
      var1 = '쀃' - var1;
      var1 >>= 31;
      var2 -= var1;
      int var3 = var2 & 1;
      var2 = (var2 >> 1) + var3;
      var2 *= 98312;
      return abs(var2 - var0);
   }

   static void helpRec(short[] var0, short[] var1, byte[] var2, byte var3) {
      byte[] var4 = new byte[8];
      var4[0] = var3;
      byte[] var5 = new byte[32];
      ChaCha20.process(var2, var4, var5, 0, var5.length);
      int[] var6 = new int[8];
      int[] var7 = new int[4];

      for(int var8 = 0; var8 < 256; ++var8) {
         int var9 = var5[var8 >>> 3] >>> (var8 & 7) & 1;
         int var10 = f(var6, 0, 4, 8 * var1[0 + var8] + 4 * var9);
         var10 += f(var6, 1, 5, 8 * var1[256 + var8] + 4 * var9);
         var10 += f(var6, 2, 6, 8 * var1[512 + var8] + 4 * var9);
         var10 += f(var6, 3, 7, 8 * var1[768 + var8] + 4 * var9);
         var10 = 24577 - var10 >> 31;
         var7[0] = ~var10 & var6[0] ^ var10 & var6[4];
         var7[1] = ~var10 & var6[1] ^ var10 & var6[5];
         var7[2] = ~var10 & var6[2] ^ var10 & var6[6];
         var7[3] = ~var10 & var6[3] ^ var10 & var6[7];
         var0[0 + var8] = (short)(var7[0] - var7[3] & 3);
         var0[256 + var8] = (short)(var7[1] - var7[3] & 3);
         var0[512 + var8] = (short)(var7[2] - var7[3] & 3);
         var0[768 + var8] = (short)(-var10 + 2 * var7[3] & 3);
      }

   }

   static short LDDecode(int var0, int var1, int var2, int var3) {
      int var4 = g(var0);
      var4 += g(var1);
      var4 += g(var2);
      var4 += g(var3);
      var4 -= 98312;
      return (short)(var4 >>> 31);
   }

   static void rec(byte[] var0, short[] var1, short[] var2) {
      Arrays.fill((byte[])var0, (byte)0);
      int[] var3 = new int[4];

      for(int var4 = 0; var4 < 256; ++var4) {
         var3[0] = 196624 + 8 * var1[0 + var4] - 12289 * (2 * var2[0 + var4] + var2[768 + var4]);
         var3[1] = 196624 + 8 * var1[256 + var4] - 12289 * (2 * var2[256 + var4] + var2[768 + var4]);
         var3[2] = 196624 + 8 * var1[512 + var4] - 12289 * (2 * var2[512 + var4] + var2[768 + var4]);
         var3[3] = 196624 + 8 * var1[768 + var4] - 12289 * var2[768 + var4];
         var0[var4 >>> 3] = (byte)(var0[var4 >>> 3] | LDDecode(var3[0], var3[1], var3[2], var3[3]) << (var4 & 7));
      }

   }
}
