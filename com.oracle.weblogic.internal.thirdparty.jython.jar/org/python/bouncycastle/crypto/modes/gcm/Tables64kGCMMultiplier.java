package org.python.bouncycastle.crypto.modes.gcm;

import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Pack;

public class Tables64kGCMMultiplier implements GCMMultiplier {
   private byte[] H;
   private int[][][] M;

   public void init(byte[] var1) {
      if (this.M == null) {
         this.M = new int[16][256][4];
      } else if (Arrays.areEqual(this.H, var1)) {
         return;
      }

      this.H = Arrays.clone(var1);
      GCMUtil.asInts(var1, this.M[0][128]);

      int var2;
      for(var2 = 64; var2 >= 1; var2 >>= 1) {
         GCMUtil.multiplyP(this.M[0][var2 + var2], this.M[0][var2]);
      }

      var2 = 0;

      while(true) {
         int var3;
         for(var3 = 2; var3 < 256; var3 += var3) {
            for(int var4 = 1; var4 < var3; ++var4) {
               GCMUtil.xor(this.M[var2][var3], this.M[var2][var4], this.M[var2][var3 + var4]);
            }
         }

         ++var2;
         if (var2 == 16) {
            return;
         }

         for(var3 = 128; var3 > 0; var3 >>= 1) {
            GCMUtil.multiplyP8(this.M[var2 - 1][var3], this.M[var2][var3]);
         }
      }
   }

   public void multiplyH(byte[] var1) {
      int[] var2 = new int[4];

      for(int var3 = 15; var3 >= 0; --var3) {
         int[] var4 = this.M[var3][var1[var3] & 255];
         var2[0] ^= var4[0];
         var2[1] ^= var4[1];
         var2[2] ^= var4[2];
         var2[3] ^= var4[3];
      }

      Pack.intToBigEndian(var2, var1, 0);
   }
}
