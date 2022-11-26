package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.engines.Salsa20Engine;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Pack;

public class SCrypt {
   public static byte[] generate(byte[] var0, byte[] var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         throw new IllegalArgumentException("Passphrase P must be provided.");
      } else if (var1 == null) {
         throw new IllegalArgumentException("Salt S must be provided.");
      } else if (var2 <= 1) {
         throw new IllegalArgumentException("Cost parameter N must be > 1.");
      } else if (var3 == 1 && var2 > 65536) {
         throw new IllegalArgumentException("Cost parameter N must be > 1 and < 65536.");
      } else if (var3 < 1) {
         throw new IllegalArgumentException("Block size r must be >= 1.");
      } else {
         int var6 = Integer.MAX_VALUE / (128 * var3 * 8);
         if (var4 >= 1 && var4 <= var6) {
            if (var5 < 1) {
               throw new IllegalArgumentException("Generated key length dkLen must be >= 1.");
            } else {
               return MFcrypt(var0, var1, var2, var3, var4, var5);
            }
         } else {
            throw new IllegalArgumentException("Parallelisation parameter p must be >= 1 and <= " + var6 + " (based on block size r of " + var3 + ")");
         }
      }
   }

   private static byte[] MFcrypt(byte[] var0, byte[] var1, int var2, int var3, int var4, int var5) {
      int var6 = var3 * 128;
      byte[] var7 = SingleIterationPBKDF2(var0, var1, var4 * var6);
      int[] var8 = null;

      try {
         int var9 = var7.length >>> 2;
         var8 = new int[var9];
         Pack.littleEndianToInt(var7, 0, var8);
         int var10 = var6 >>> 2;

         for(int var11 = 0; var11 < var9; var11 += var10) {
            SMix(var8, var11, var2, var3);
         }

         Pack.intToLittleEndian(var8, var7, 0);
         byte[] var15 = SingleIterationPBKDF2(var0, var7, var5);
         return var15;
      } finally {
         Clear(var7);
         Clear(var8);
      }
   }

   private static byte[] SingleIterationPBKDF2(byte[] var0, byte[] var1, int var2) {
      PKCS5S2ParametersGenerator var3 = new PKCS5S2ParametersGenerator(new SHA256Digest());
      var3.init(var0, var1, 1);
      KeyParameter var4 = (KeyParameter)var3.generateDerivedMacParameters(var2 * 8);
      return var4.getKey();
   }

   private static void SMix(int[] var0, int var1, int var2, int var3) {
      int var4 = var3 * 32;
      int[] var5 = new int[16];
      int[] var6 = new int[16];
      int[] var7 = new int[var4];
      int[] var8 = new int[var4];
      int[][] var9 = new int[var2][];

      try {
         System.arraycopy(var0, var1, var8, 0, var4);

         int var10;
         for(var10 = 0; var10 < var2; ++var10) {
            var9[var10] = Arrays.clone(var8);
            BlockMix(var8, var5, var6, var7, var3);
         }

         var10 = var2 - 1;

         for(int var11 = 0; var11 < var2; ++var11) {
            int var12 = var8[var4 - 16] & var10;
            Xor(var8, var9[var12], 0, var8);
            BlockMix(var8, var5, var6, var7, var3);
         }

         System.arraycopy(var8, 0, var0, var1, var4);
      } finally {
         ClearAll(var9);
         ClearAll(new int[][]{var8, var5, var6, var7});
      }
   }

   private static void BlockMix(int[] var0, int[] var1, int[] var2, int[] var3, int var4) {
      System.arraycopy(var0, var0.length - 16, var1, 0, 16);
      int var5 = 0;
      int var6 = 0;
      int var7 = var0.length >>> 1;

      for(int var8 = 2 * var4; var8 > 0; --var8) {
         Xor(var1, var0, var5, var2);
         Salsa20Engine.salsaCore(8, var2, var1);
         System.arraycopy(var1, 0, var3, var6, 16);
         var6 = var7 + var5 - var6;
         var5 += 16;
      }

      System.arraycopy(var3, 0, var0, 0, var3.length);
   }

   private static void Xor(int[] var0, int[] var1, int var2, int[] var3) {
      for(int var4 = var3.length - 1; var4 >= 0; --var4) {
         var3[var4] = var0[var4] ^ var1[var2 + var4];
      }

   }

   private static void Clear(byte[] var0) {
      if (var0 != null) {
         Arrays.fill((byte[])var0, (byte)0);
      }

   }

   private static void Clear(int[] var0) {
      if (var0 != null) {
         Arrays.fill((int[])var0, (int)0);
      }

   }

   private static void ClearAll(int[][] var0) {
      for(int var1 = 0; var1 < var0.length; ++var1) {
         Clear(var0[var1]);
      }

   }
}
