package org.python.bouncycastle.pqc.crypto.sphincs;

class Horst {
   static final int HORST_LOGT = 16;
   static final int HORST_T = 65536;
   static final int HORST_K = 32;
   static final int HORST_SKBYTES = 32;
   static final int HORST_SIGBYTES = 13312;
   static final int N_MASKS = 32;

   static void expand_seed(byte[] var0, byte[] var1) {
      Seed.prg(var0, 0, 2097152L, var1, 0);
   }

   static int horst_sign(HashFunctions var0, byte[] var1, int var2, byte[] var3, byte[] var4, byte[] var5, byte[] var6) {
      byte[] var7 = new byte[2097152];
      int var8 = var2;
      byte[] var9 = new byte[4194272];
      expand_seed(var7, var4);

      int var10;
      for(var10 = 0; var10 < 65536; ++var10) {
         var0.hash_n_n(var9, ('\uffff' + var10) * 32, var7, var10 * 32);
      }

      int var15;
      for(var10 = 0; var10 < 16; ++var10) {
         long var11 = (long)((1 << 16 - var10) - 1);
         long var13 = (long)((1 << 16 - var10 - 1) - 1);

         for(var15 = 0; var15 < 1 << 16 - var10 - 1; ++var15) {
            var0.hash_2n_n_mask(var9, (int)((var13 + (long)var15) * 32L), var9, (int)((var11 + (long)(2 * var15)) * 32L), var5, 2 * var10 * 32);
         }
      }

      for(var15 = 2016; var15 < 4064; ++var15) {
         var1[var8++] = var9[var15];
      }

      for(var10 = 0; var10 < 32; ++var10) {
         int var16 = (var6[2 * var10] & 255) + ((var6[2 * var10 + 1] & 255) << 8);

         int var17;
         for(var17 = 0; var17 < 32; ++var17) {
            var1[var8++] = var7[var16 * 32 + var17];
         }

         var16 += 65535;

         for(var15 = 0; var15 < 10; ++var15) {
            var16 = (var16 & 1) != 0 ? var16 + 1 : var16 - 1;

            for(var17 = 0; var17 < 32; ++var17) {
               var1[var8++] = var9[var16 * 32 + var17];
            }

            var16 = (var16 - 1) / 2;
         }
      }

      for(var10 = 0; var10 < 32; ++var10) {
         var3[var10] = var9[var10];
      }

      return 13312;
   }

   static int horst_verify(HashFunctions var0, byte[] var1, byte[] var2, int var3, byte[] var4, byte[] var5) {
      byte[] var6 = new byte[1024];
      int var7 = var3 + 2048;

      int var11;
      for(int var8 = 0; var8 < 32; ++var8) {
         int var9 = (var5[2 * var8] & 255) + ((var5[2 * var8 + 1] & 255) << 8);
         int var10;
         if ((var9 & 1) == 0) {
            var0.hash_n_n(var6, 0, var2, var7);

            for(var10 = 0; var10 < 32; ++var10) {
               var6[32 + var10] = var2[var7 + 32 + var10];
            }
         } else {
            var0.hash_n_n(var6, 32, var2, var7);

            for(var10 = 0; var10 < 32; ++var10) {
               var6[var10] = var2[var7 + 32 + var10];
            }
         }

         var7 += 64;

         for(var11 = 1; var11 < 10; ++var11) {
            var9 >>>= 1;
            if ((var9 & 1) == 0) {
               var0.hash_2n_n_mask(var6, 0, var6, 0, var4, 2 * (var11 - 1) * 32);

               for(var10 = 0; var10 < 32; ++var10) {
                  var6[32 + var10] = var2[var7 + var10];
               }
            } else {
               var0.hash_2n_n_mask(var6, 32, var6, 0, var4, 2 * (var11 - 1) * 32);

               for(var10 = 0; var10 < 32; ++var10) {
                  var6[var10] = var2[var7 + var10];
               }
            }

            var7 += 32;
         }

         var9 >>>= 1;
         var0.hash_2n_n_mask(var6, 0, var6, 0, var4, 576);

         for(var10 = 0; var10 < 32; ++var10) {
            if (var2[var3 + var9 * 32 + var10] != var6[var10]) {
               for(var10 = 0; var10 < 32; ++var10) {
                  var1[var10] = 0;
               }

               return -1;
            }
         }
      }

      for(var11 = 0; var11 < 32; ++var11) {
         var0.hash_2n_n_mask(var6, var11 * 32, var2, var3 + 2 * var11 * 32, var4, 640);
      }

      for(var11 = 0; var11 < 16; ++var11) {
         var0.hash_2n_n_mask(var6, var11 * 32, var6, 2 * var11 * 32, var4, 704);
      }

      for(var11 = 0; var11 < 8; ++var11) {
         var0.hash_2n_n_mask(var6, var11 * 32, var6, 2 * var11 * 32, var4, 768);
      }

      for(var11 = 0; var11 < 4; ++var11) {
         var0.hash_2n_n_mask(var6, var11 * 32, var6, 2 * var11 * 32, var4, 832);
      }

      for(var11 = 0; var11 < 2; ++var11) {
         var0.hash_2n_n_mask(var6, var11 * 32, var6, 2 * var11 * 32, var4, 896);
      }

      var0.hash_2n_n_mask(var1, 0, var6, 0, var4, 960);
      return 0;
   }
}
