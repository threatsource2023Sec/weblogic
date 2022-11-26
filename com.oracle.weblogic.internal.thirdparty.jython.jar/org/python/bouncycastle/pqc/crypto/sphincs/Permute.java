package org.python.bouncycastle.pqc.crypto.sphincs;

import org.python.bouncycastle.util.Pack;

class Permute {
   private static final int CHACHA_ROUNDS = 12;

   protected static int rotl(int var0, int var1) {
      return var0 << var1 | var0 >>> -var1;
   }

   public static void permute(int var0, int[] var1) {
      if (var1.length != 16) {
         throw new IllegalArgumentException();
      } else if (var0 % 2 != 0) {
         throw new IllegalArgumentException("Number of rounds must be even");
      } else {
         int var2 = var1[0];
         int var3 = var1[1];
         int var4 = var1[2];
         int var5 = var1[3];
         int var6 = var1[4];
         int var7 = var1[5];
         int var8 = var1[6];
         int var9 = var1[7];
         int var10 = var1[8];
         int var11 = var1[9];
         int var12 = var1[10];
         int var13 = var1[11];
         int var14 = var1[12];
         int var15 = var1[13];
         int var16 = var1[14];
         int var17 = var1[15];

         for(int var18 = var0; var18 > 0; var18 -= 2) {
            var2 += var6;
            var14 = rotl(var14 ^ var2, 16);
            var10 += var14;
            var6 = rotl(var6 ^ var10, 12);
            var2 += var6;
            var14 = rotl(var14 ^ var2, 8);
            var10 += var14;
            var6 = rotl(var6 ^ var10, 7);
            var3 += var7;
            var15 = rotl(var15 ^ var3, 16);
            var11 += var15;
            var7 = rotl(var7 ^ var11, 12);
            var3 += var7;
            var15 = rotl(var15 ^ var3, 8);
            var11 += var15;
            var7 = rotl(var7 ^ var11, 7);
            var4 += var8;
            var16 = rotl(var16 ^ var4, 16);
            var12 += var16;
            var8 = rotl(var8 ^ var12, 12);
            var4 += var8;
            var16 = rotl(var16 ^ var4, 8);
            var12 += var16;
            var8 = rotl(var8 ^ var12, 7);
            var5 += var9;
            var17 = rotl(var17 ^ var5, 16);
            var13 += var17;
            var9 = rotl(var9 ^ var13, 12);
            var5 += var9;
            var17 = rotl(var17 ^ var5, 8);
            var13 += var17;
            var9 = rotl(var9 ^ var13, 7);
            var2 += var7;
            var17 = rotl(var17 ^ var2, 16);
            var12 += var17;
            var7 = rotl(var7 ^ var12, 12);
            var2 += var7;
            var17 = rotl(var17 ^ var2, 8);
            var12 += var17;
            var7 = rotl(var7 ^ var12, 7);
            var3 += var8;
            var14 = rotl(var14 ^ var3, 16);
            var13 += var14;
            var8 = rotl(var8 ^ var13, 12);
            var3 += var8;
            var14 = rotl(var14 ^ var3, 8);
            var13 += var14;
            var8 = rotl(var8 ^ var13, 7);
            var4 += var9;
            var15 = rotl(var15 ^ var4, 16);
            var10 += var15;
            var9 = rotl(var9 ^ var10, 12);
            var4 += var9;
            var15 = rotl(var15 ^ var4, 8);
            var10 += var15;
            var9 = rotl(var9 ^ var10, 7);
            var5 += var6;
            var16 = rotl(var16 ^ var5, 16);
            var11 += var16;
            var6 = rotl(var6 ^ var11, 12);
            var5 += var6;
            var16 = rotl(var16 ^ var5, 8);
            var11 += var16;
            var6 = rotl(var6 ^ var11, 7);
         }

         var1[0] = var2;
         var1[1] = var3;
         var1[2] = var4;
         var1[3] = var5;
         var1[4] = var6;
         var1[5] = var7;
         var1[6] = var8;
         var1[7] = var9;
         var1[8] = var10;
         var1[9] = var11;
         var1[10] = var12;
         var1[11] = var13;
         var1[12] = var14;
         var1[13] = var15;
         var1[14] = var16;
         var1[15] = var17;
      }
   }

   void chacha_permute(byte[] var1, byte[] var2) {
      int[] var3 = new int[16];

      int var4;
      for(var4 = 0; var4 < 16; ++var4) {
         var3[var4] = Pack.littleEndianToInt(var2, 4 * var4);
      }

      permute(12, var3);

      for(var4 = 0; var4 < 16; ++var4) {
         Pack.intToLittleEndian(var3[var4], var1, 4 * var4);
      }

   }
}
