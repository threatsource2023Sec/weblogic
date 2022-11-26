package org.python.bouncycastle.pqc.crypto.sphincs;

class Tree {
   static void l_tree(HashFunctions var0, byte[] var1, int var2, byte[] var3, int var4, byte[] var5, int var6) {
      int var7 = 67;
      boolean var8 = false;

      for(int var9 = 0; var9 < 7; ++var9) {
         for(int var10 = 0; var10 < var7 >>> 1; ++var10) {
            var0.hash_2n_n_mask(var3, var4 + var10 * 32, var3, var4 + var10 * 2 * 32, var5, var6 + var9 * 2 * 32);
         }

         if ((var7 & 1) != 0) {
            System.arraycopy(var3, var4 + (var7 - 1) * 32, var3, var4 + (var7 >>> 1) * 32, 32);
            var7 = (var7 >>> 1) + 1;
         } else {
            var7 >>>= 1;
         }
      }

      System.arraycopy(var3, var4, var1, var2, 32);
   }

   static void treehash(HashFunctions var0, byte[] var1, int var2, int var3, byte[] var4, leafaddr var5, byte[] var6, int var7) {
      leafaddr var8 = new leafaddr(var5);
      byte[] var9 = new byte[(var3 + 1) * 32];
      int[] var10 = new int[var3 + 1];
      int var11 = 0;

      for(int var12 = (int)(var8.subleaf + (long)(1 << var3)); var8.subleaf < (long)var12; ++var8.subleaf) {
         gen_leaf_wots(var0, var9, var11 * 32, var6, var7, var4, var8);
         var10[var11] = 0;
         ++var11;

         while(var11 > 1 && var10[var11 - 1] == var10[var11 - 2]) {
            int var13 = 2 * (var10[var11 - 1] + 7) * 32;
            var0.hash_2n_n_mask(var9, (var11 - 2) * 32, var9, (var11 - 2) * 32, var6, var7 + var13);
            ++var10[var11 - 2];
            --var11;
         }
      }

      for(int var14 = 0; var14 < 32; ++var14) {
         var1[var2 + var14] = var9[var14];
      }

   }

   static void gen_leaf_wots(HashFunctions var0, byte[] var1, int var2, byte[] var3, int var4, byte[] var5, leafaddr var6) {
      byte[] var7 = new byte[32];
      byte[] var8 = new byte[2144];
      Wots var9 = new Wots();
      Seed.get_seed(var0, var7, 0, var5, var6);
      var9.wots_pkgen(var0, var8, 0, var7, 0, var3, var4);
      l_tree(var0, var1, var2, var8, 0, var3, var4);
   }

   static class leafaddr {
      int level;
      long subtree;
      long subleaf;

      public leafaddr() {
      }

      public leafaddr(leafaddr var1) {
         this.level = var1.level;
         this.subtree = var1.subtree;
         this.subleaf = var1.subleaf;
      }
   }
}
