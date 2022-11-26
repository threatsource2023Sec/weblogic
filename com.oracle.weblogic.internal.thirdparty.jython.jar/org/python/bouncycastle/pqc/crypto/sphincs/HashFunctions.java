package org.python.bouncycastle.pqc.crypto.sphincs;

import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.util.Strings;

class HashFunctions {
   private static final byte[] hashc = Strings.toByteArray("expand 32-byte to 64-byte state!");
   private final Digest dig256;
   private final Digest dig512;
   private final Permute perm;

   HashFunctions(Digest var1) {
      this(var1, (Digest)null);
   }

   HashFunctions(Digest var1, Digest var2) {
      this.perm = new Permute();
      this.dig256 = var1;
      this.dig512 = var2;
   }

   int varlen_hash(byte[] var1, int var2, byte[] var3, int var4) {
      this.dig256.update(var3, 0, var4);
      this.dig256.doFinal(var1, var2);
      return 0;
   }

   Digest getMessageHash() {
      return this.dig512;
   }

   int hash_2n_n(byte[] var1, int var2, byte[] var3, int var4) {
      byte[] var5 = new byte[64];

      int var6;
      for(var6 = 0; var6 < 32; ++var6) {
         var5[var6] = var3[var4 + var6];
         var5[var6 + 32] = hashc[var6];
      }

      this.perm.chacha_permute(var5, var5);

      for(var6 = 0; var6 < 32; ++var6) {
         var5[var6] ^= var3[var4 + var6 + 32];
      }

      this.perm.chacha_permute(var5, var5);

      for(var6 = 0; var6 < 32; ++var6) {
         var1[var2 + var6] = var5[var6];
      }

      return 0;
   }

   int hash_2n_n_mask(byte[] var1, int var2, byte[] var3, int var4, byte[] var5, int var6) {
      byte[] var7 = new byte[64];

      for(int var8 = 0; var8 < 64; ++var8) {
         var7[var8] = (byte)(var3[var4 + var8] ^ var5[var6 + var8]);
      }

      int var9 = this.hash_2n_n(var1, var2, var7, 0);
      return var9;
   }

   int hash_n_n(byte[] var1, int var2, byte[] var3, int var4) {
      byte[] var5 = new byte[64];

      int var6;
      for(var6 = 0; var6 < 32; ++var6) {
         var5[var6] = var3[var4 + var6];
         var5[var6 + 32] = hashc[var6];
      }

      this.perm.chacha_permute(var5, var5);

      for(var6 = 0; var6 < 32; ++var6) {
         var1[var2 + var6] = var5[var6];
      }

      return 0;
   }

   int hash_n_n_mask(byte[] var1, int var2, byte[] var3, int var4, byte[] var5, int var6) {
      byte[] var7 = new byte[32];

      for(int var8 = 0; var8 < 32; ++var8) {
         var7[var8] = (byte)(var3[var4 + var8] ^ var5[var6 + var8]);
      }

      return this.hash_n_n(var1, var2, var7, 0);
   }
}
