package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.params.KeyParameter;

public class IDEAEngine implements BlockCipher {
   protected static final int BLOCK_SIZE = 8;
   private int[] workingKey = null;
   private static final int MASK = 65535;
   private static final int BASE = 65537;

   public void init(boolean var1, CipherParameters var2) {
      if (var2 instanceof KeyParameter) {
         this.workingKey = this.generateWorkingKey(var1, ((KeyParameter)var2).getKey());
      } else {
         throw new IllegalArgumentException("invalid parameter passed to IDEA init - " + var2.getClass().getName());
      }
   }

   public String getAlgorithmName() {
      return "IDEA";
   }

   public int getBlockSize() {
      return 8;
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if (this.workingKey == null) {
         throw new IllegalStateException("IDEA engine not initialised");
      } else if (var2 + 8 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var4 + 8 > var3.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         this.ideaFunc(this.workingKey, var1, var2, var3, var4);
         return 8;
      }
   }

   public void reset() {
   }

   private int bytesToWord(byte[] var1, int var2) {
      return (var1[var2] << 8 & '\uff00') + (var1[var2 + 1] & 255);
   }

   private void wordToBytes(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)(var1 >>> 8);
      var2[var3 + 1] = (byte)var1;
   }

   private int mul(int var1, int var2) {
      if (var1 == 0) {
         var1 = 65537 - var2;
      } else if (var2 == 0) {
         var1 = 65537 - var1;
      } else {
         int var3 = var1 * var2;
         var2 = var3 & '\uffff';
         var1 = var3 >>> 16;
         var1 = var2 - var1 + (var2 < var1 ? 1 : 0);
      }

      return var1 & '\uffff';
   }

   private void ideaFunc(int[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      int var6 = 0;
      int var7 = this.bytesToWord(var2, var3);
      int var8 = this.bytesToWord(var2, var3 + 2);
      int var9 = this.bytesToWord(var2, var3 + 4);
      int var10 = this.bytesToWord(var2, var3 + 6);

      for(int var11 = 0; var11 < 8; ++var11) {
         var7 = this.mul(var7, var1[var6++]);
         var8 += var1[var6++];
         var8 &= 65535;
         var9 += var1[var6++];
         var9 &= 65535;
         var10 = this.mul(var10, var1[var6++]);
         int var12 = var8;
         int var13 = var9;
         var9 ^= var7;
         var8 ^= var10;
         var9 = this.mul(var9, var1[var6++]);
         var8 += var9;
         var8 &= 65535;
         var8 = this.mul(var8, var1[var6++]);
         var9 += var8;
         var9 &= 65535;
         var7 ^= var8;
         var10 ^= var9;
         var8 ^= var13;
         var9 ^= var12;
      }

      this.wordToBytes(this.mul(var7, var1[var6++]), var4, var5);
      this.wordToBytes(var9 + var1[var6++], var4, var5 + 2);
      this.wordToBytes(var8 + var1[var6++], var4, var5 + 4);
      this.wordToBytes(this.mul(var10, var1[var6]), var4, var5 + 6);
   }

   private int[] expandKey(byte[] var1) {
      int[] var2 = new int[52];
      if (var1.length < 16) {
         byte[] var3 = new byte[16];
         System.arraycopy(var1, 0, var3, var3.length - var1.length, var1.length);
         var1 = var3;
      }

      int var4;
      for(var4 = 0; var4 < 8; ++var4) {
         var2[var4] = this.bytesToWord(var1, var4 * 2);
      }

      for(var4 = 8; var4 < 52; ++var4) {
         if ((var4 & 7) < 6) {
            var2[var4] = ((var2[var4 - 7] & 127) << 9 | var2[var4 - 6] >> 7) & '\uffff';
         } else if ((var4 & 7) == 6) {
            var2[var4] = ((var2[var4 - 7] & 127) << 9 | var2[var4 - 14] >> 7) & '\uffff';
         } else {
            var2[var4] = ((var2[var4 - 15] & 127) << 9 | var2[var4 - 14] >> 7) & '\uffff';
         }
      }

      return var2;
   }

   private int mulInv(int var1) {
      if (var1 < 2) {
         return var1;
      } else {
         int var2 = 1;
         int var3 = 65537 / var1;

         int var5;
         for(int var4 = 65537 % var1; var4 != 1; var3 = var3 + var2 * var5 & '\uffff') {
            var5 = var1 / var4;
            var1 %= var4;
            var2 = var2 + var3 * var5 & '\uffff';
            if (var1 == 1) {
               return var2;
            }

            var5 = var4 / var1;
            var4 %= var1;
         }

         return 1 - var3 & '\uffff';
      }
   }

   int addInv(int var1) {
      return 0 - var1 & '\uffff';
   }

   private int[] invertKey(int[] var1) {
      int var2 = 52;
      int[] var3 = new int[52];
      int var4 = 0;
      int var5 = this.mulInv(var1[var4++]);
      int var6 = this.addInv(var1[var4++]);
      int var7 = this.addInv(var1[var4++]);
      int var8 = this.mulInv(var1[var4++]);
      --var2;
      var3[var2] = var8;
      --var2;
      var3[var2] = var7;
      --var2;
      var3[var2] = var6;
      --var2;
      var3[var2] = var5;

      for(int var9 = 1; var9 < 8; ++var9) {
         var5 = var1[var4++];
         var6 = var1[var4++];
         --var2;
         var3[var2] = var6;
         --var2;
         var3[var2] = var5;
         var5 = this.mulInv(var1[var4++]);
         var6 = this.addInv(var1[var4++]);
         var7 = this.addInv(var1[var4++]);
         var8 = this.mulInv(var1[var4++]);
         --var2;
         var3[var2] = var8;
         --var2;
         var3[var2] = var6;
         --var2;
         var3[var2] = var7;
         --var2;
         var3[var2] = var5;
      }

      var5 = var1[var4++];
      var6 = var1[var4++];
      --var2;
      var3[var2] = var6;
      --var2;
      var3[var2] = var5;
      var5 = this.mulInv(var1[var4++]);
      var6 = this.addInv(var1[var4++]);
      var7 = this.addInv(var1[var4++]);
      var8 = this.mulInv(var1[var4]);
      --var2;
      var3[var2] = var8;
      --var2;
      var3[var2] = var7;
      --var2;
      var3[var2] = var6;
      --var2;
      var3[var2] = var5;
      return var3;
   }

   private int[] generateWorkingKey(boolean var1, byte[] var2) {
      return var1 ? this.expandKey(var2) : this.invertKey(this.expandKey(var2));
   }
}
