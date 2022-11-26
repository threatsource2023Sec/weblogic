package org.python.bouncycastle.pqc.crypto.sphincs;

class Wots {
   static final int WOTS_LOGW = 4;
   static final int WOTS_W = 16;
   static final int WOTS_L1 = 64;
   static final int WOTS_L = 67;
   static final int WOTS_LOG_L = 7;
   static final int WOTS_SIGBYTES = 2144;

   static void expand_seed(byte[] var0, int var1, byte[] var2, int var3) {
      clear(var0, var1, 2144);
      Seed.prg(var0, var1, 2144L, var2, var3);
   }

   private static void clear(byte[] var0, int var1, int var2) {
      for(int var3 = 0; var3 != var2; ++var3) {
         var0[var3 + var1] = 0;
      }

   }

   static void gen_chain(HashFunctions var0, byte[] var1, int var2, byte[] var3, int var4, byte[] var5, int var6, int var7) {
      for(int var8 = 0; var8 < 32; ++var8) {
         var1[var8 + var2] = var3[var8 + var4];
      }

      for(int var9 = 0; var9 < var7 && var9 < 16; ++var9) {
         var0.hash_n_n_mask(var1, var2, var1, var2, var5, var6 + var9 * 32);
      }

   }

   void wots_pkgen(HashFunctions var1, byte[] var2, int var3, byte[] var4, int var5, byte[] var6, int var7) {
      expand_seed(var2, var3, var4, var5);

      for(int var8 = 0; var8 < 67; ++var8) {
         gen_chain(var1, var2, var3 + var8 * 32, var2, var3 + var8 * 32, var6, var7, 15);
      }

   }

   void wots_sign(HashFunctions var1, byte[] var2, int var3, byte[] var4, byte[] var5, byte[] var6) {
      int[] var7 = new int[67];
      int var8 = 0;

      int var9;
      for(var9 = 0; var9 < 64; var9 += 2) {
         var7[var9] = var4[var9 / 2] & 15;
         var7[var9 + 1] = (var4[var9 / 2] & 255) >>> 4;
         var8 += 15 - var7[var9];
         var8 += 15 - var7[var9 + 1];
      }

      while(var9 < 67) {
         var7[var9] = var8 & 15;
         var8 >>>= 4;
         ++var9;
      }

      expand_seed(var2, var3, var5, 0);

      for(var9 = 0; var9 < 67; ++var9) {
         gen_chain(var1, var2, var3 + var9 * 32, var2, var3 + var9 * 32, var6, 0, var7[var9]);
      }

   }

   void wots_verify(HashFunctions var1, byte[] var2, byte[] var3, int var4, byte[] var5, byte[] var6) {
      int[] var7 = new int[67];
      int var8 = 0;

      int var9;
      for(var9 = 0; var9 < 64; var9 += 2) {
         var7[var9] = var5[var9 / 2] & 15;
         var7[var9 + 1] = (var5[var9 / 2] & 255) >>> 4;
         var8 += 15 - var7[var9];
         var8 += 15 - var7[var9 + 1];
      }

      while(var9 < 67) {
         var7[var9] = var8 & 15;
         var8 >>>= 4;
         ++var9;
      }

      for(var9 = 0; var9 < 67; ++var9) {
         gen_chain(var1, var2, var9 * 32, var3, var4 + var9 * 32, var6, var7[var9] * 32, 15 - var7[var9]);
      }

   }
}
