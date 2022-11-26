package org.python.bouncycastle.pqc.crypto.sphincs;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.pqc.crypto.MessageSigner;
import org.python.bouncycastle.util.Pack;

public class SPHINCS256Signer implements MessageSigner {
   private final HashFunctions hashFunctions;
   private byte[] keyData;

   public SPHINCS256Signer(Digest var1, Digest var2) {
      if (var1.getDigestSize() != 32) {
         throw new IllegalArgumentException("n-digest needs to produce 32 bytes of output");
      } else if (var2.getDigestSize() != 64) {
         throw new IllegalArgumentException("2n-digest needs to produce 64 bytes of output");
      } else {
         this.hashFunctions = new HashFunctions(var1, var2);
      }
   }

   public void init(boolean var1, CipherParameters var2) {
      if (var1) {
         this.keyData = ((SPHINCSPrivateKeyParameters)var2).getKeyData();
      } else {
         this.keyData = ((SPHINCSPublicKeyParameters)var2).getKeyData();
      }

   }

   public byte[] generateSignature(byte[] var1) {
      return this.crypto_sign(this.hashFunctions, var1, this.keyData);
   }

   public boolean verifySignature(byte[] var1, byte[] var2) {
      return this.verify(this.hashFunctions, var1, var2, this.keyData);
   }

   static void validate_authpath(HashFunctions var0, byte[] var1, byte[] var2, int var3, byte[] var4, int var5, byte[] var6, int var7) {
      byte[] var8 = new byte[64];
      int var9;
      if ((var3 & 1) != 0) {
         for(var9 = 0; var9 < 32; ++var9) {
            var8[32 + var9] = var2[var9];
         }

         for(var9 = 0; var9 < 32; ++var9) {
            var8[var9] = var4[var5 + var9];
         }
      } else {
         for(var9 = 0; var9 < 32; ++var9) {
            var8[var9] = var2[var9];
         }

         for(var9 = 0; var9 < 32; ++var9) {
            var8[32 + var9] = var4[var5 + var9];
         }
      }

      int var10 = var5 + 32;

      for(int var11 = 0; var11 < var7 - 1; ++var11) {
         var3 >>>= 1;
         if ((var3 & 1) != 0) {
            var0.hash_2n_n_mask(var8, 32, var8, 0, var6, 2 * (7 + var11) * 32);

            for(var9 = 0; var9 < 32; ++var9) {
               var8[var9] = var4[var10 + var9];
            }
         } else {
            var0.hash_2n_n_mask(var8, 0, var8, 0, var6, 2 * (7 + var11) * 32);

            for(var9 = 0; var9 < 32; ++var9) {
               var8[var9 + 32] = var4[var10 + var9];
            }
         }

         var10 += 32;
      }

      var0.hash_2n_n_mask(var1, 0, var8, 0, var6, 2 * (7 + var7 - 1) * 32);
   }

   static void compute_authpath_wots(HashFunctions var0, byte[] var1, byte[] var2, int var3, Tree.leafaddr var4, byte[] var5, byte[] var6, int var7) {
      Tree.leafaddr var8 = new Tree.leafaddr(var4);
      byte[] var9 = new byte[2048];
      byte[] var10 = new byte[1024];
      byte[] var11 = new byte[68608];

      for(var8.subleaf = 0L; var8.subleaf < 32L; ++var8.subleaf) {
         Seed.get_seed(var0, var10, (int)(var8.subleaf * 32L), var5, var8);
      }

      Wots var12 = new Wots();

      for(var8.subleaf = 0L; var8.subleaf < 32L; ++var8.subleaf) {
         var12.wots_pkgen(var0, var11, (int)(var8.subleaf * 67L * 32L), var10, (int)(var8.subleaf * 32L), var6, 0);
      }

      for(var8.subleaf = 0L; var8.subleaf < 32L; ++var8.subleaf) {
         Tree.l_tree(var0, var9, (int)(1024L + var8.subleaf * 32L), var11, (int)(var8.subleaf * 67L * 32L), var6, 0);
      }

      int var13 = 0;

      int var14;
      for(var14 = 32; var14 > 0; var14 >>>= 1) {
         for(int var15 = 0; var15 < var14; var15 += 2) {
            var0.hash_2n_n_mask(var9, (var14 >>> 1) * 32 + (var15 >>> 1) * 32, var9, var14 * 32 + var15 * 32, var6, 2 * (7 + var13) * 32);
         }

         ++var13;
      }

      int var16 = (int)var4.subleaf;

      for(var14 = 0; var14 < var7; ++var14) {
         System.arraycopy(var9, (32 >>> var14) * 32 + (var16 >>> var14 ^ 1) * 32, var2, var3 + var14 * 32, 32);
      }

      System.arraycopy(var9, 32, var1, 0, 32);
   }

   byte[] crypto_sign(HashFunctions var1, byte[] var2, byte[] var3) {
      byte[] var4 = new byte['ꀨ'];
      byte[] var5 = new byte[32];
      byte[] var6 = new byte[64];
      long[] var7 = new long[8];
      byte[] var8 = new byte[32];
      byte[] var9 = new byte[32];
      byte[] var10 = new byte[1024];
      byte[] var11 = new byte[1088];

      int var12;
      for(var12 = 0; var12 < 1088; ++var12) {
         var11[var12] = var3[var12];
      }

      char var13 = 'ꀈ';
      System.arraycopy(var11, 1056, var4, var13, 32);
      Digest var14 = var1.getMessageHash();
      byte[] var15 = new byte[var14.getDigestSize()];
      var14.update(var4, var13, 32);
      var14.update(var2, 0, var2.length);
      var14.doFinal(var15, 0);
      this.zerobytes(var4, var13, 32);

      int var16;
      for(var16 = 0; var16 != var7.length; ++var16) {
         var7[var16] = Pack.littleEndianToLong(var15, var16 * 8);
      }

      long var17 = var7[0] & 1152921504606846975L;
      System.arraycopy(var15, 16, var5, 0, 32);
      var13 = '鯨';
      System.arraycopy(var5, 0, var4, var13, 32);
      Tree.leafaddr var24 = new Tree.leafaddr();
      var24.level = 11;
      var24.subtree = 0L;
      var24.subleaf = 0L;
      int var19 = var13 + 32;
      System.arraycopy(var11, 32, var4, var19, 1024);
      Tree.treehash(var1, var4, var19 + 1024, 5, var11, var24, var4, var19);
      var14 = var1.getMessageHash();
      var14.update(var4, var13, 1088);
      var14.update(var2, 0, var2.length);
      var14.doFinal(var6, 0);
      Tree.leafaddr var21 = new Tree.leafaddr();
      var21.level = 12;
      var21.subleaf = (long)((int)(var17 & 31L));
      var21.subtree = var17 >>> 5;

      for(var12 = 0; var12 < 32; ++var12) {
         var4[var12] = var5[var12];
      }

      int var22 = 32;
      System.arraycopy(var11, 32, var10, 0, 1024);

      for(var12 = 0; var12 < 8; ++var12) {
         var4[var22 + var12] = (byte)((int)(var17 >>> 8 * var12 & 255L));
      }

      var22 += 8;
      Seed.get_seed(var1, var9, 0, var11, var21);
      Horst var23 = new Horst();
      var16 = Horst.horst_sign(var1, var4, var22, var8, var9, var10, var6);
      var22 += var16;
      Wots var20 = new Wots();

      for(var12 = 0; var12 < 12; ++var12) {
         var21.level = var12;
         Seed.get_seed(var1, var9, 0, var11, var21);
         var20.wots_sign(var1, var4, var22, var8, var9, var10);
         var22 += 2144;
         compute_authpath_wots(var1, var8, var4, var22, var21, var11, var10, 5);
         var22 += 160;
         var21.subleaf = (long)((int)(var21.subtree & 31L));
         var21.subtree >>>= 5;
      }

      this.zerobytes(var11, 0, 1088);
      return var4;
   }

   private void zerobytes(byte[] var1, int var2, int var3) {
      for(int var4 = 0; var4 != var3; ++var4) {
         var1[var2 + var4] = 0;
      }

   }

   boolean verify(HashFunctions var1, byte[] var2, byte[] var3, byte[] var4) {
      int var5 = var3.length;
      long var6 = 0L;
      byte[] var8 = new byte[2144];
      byte[] var9 = new byte[32];
      byte[] var10 = new byte[32];
      byte[] var11 = new byte['ꀨ'];
      byte[] var12 = new byte[1056];
      if (var5 != 41000) {
         throw new IllegalArgumentException("signature wrong size");
      } else {
         byte[] var13 = new byte[64];

         int var14;
         for(var14 = 0; var14 < 1056; ++var14) {
            var12[var14] = var4[var14];
         }

         byte[] var15 = new byte[32];

         for(var14 = 0; var14 < 32; ++var14) {
            var15[var14] = var3[var14];
         }

         System.arraycopy(var3, 0, var11, 0, 41000);
         Digest var16 = var1.getMessageHash();
         var16.update(var15, 0, 32);
         var16.update(var12, 0, 1056);
         var16.update(var2, 0, var2.length);
         var16.doFinal(var13, 0);
         int var17 = 0;
         var17 += 32;
         var5 -= 32;

         for(var14 = 0; var14 < 8; ++var14) {
            var6 ^= (long)(var11[var17 + var14] & 255) << 8 * var14;
         }

         new Horst();
         Horst.horst_verify(var1, var10, var11, var17 + 8, var12, var13);
         var17 += 8;
         var5 -= 8;
         var17 += 13312;
         var5 -= 13312;
         Wots var18 = new Wots();

         for(var14 = 0; var14 < 12; ++var14) {
            var18.wots_verify(var1, var8, var11, var17, var10, var12);
            var17 += 2144;
            var5 -= 2144;
            Tree.l_tree(var1, var9, 0, var8, 0, var12, 0);
            validate_authpath(var1, var10, var9, (int)(var6 & 31L), var11, var17, var12, 5);
            var6 >>= 5;
            var17 += 160;
            var5 -= 160;
         }

         boolean var19 = true;

         for(var14 = 0; var14 < 32; ++var14) {
            if (var10[var14] != var12[var14 + 1024]) {
               var19 = false;
            }
         }

         return var19;
      }
   }
}
