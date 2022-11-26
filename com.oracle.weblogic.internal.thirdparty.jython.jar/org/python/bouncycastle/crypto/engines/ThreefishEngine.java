package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.TweakableBlockCipherParameters;

public class ThreefishEngine implements BlockCipher {
   public static final int BLOCKSIZE_256 = 256;
   public static final int BLOCKSIZE_512 = 512;
   public static final int BLOCKSIZE_1024 = 1024;
   private static final int TWEAK_SIZE_BYTES = 16;
   private static final int TWEAK_SIZE_WORDS = 2;
   private static final int ROUNDS_256 = 72;
   private static final int ROUNDS_512 = 72;
   private static final int ROUNDS_1024 = 80;
   private static final int MAX_ROUNDS = 80;
   private static final long C_240 = 2004413935125273122L;
   private static int[] MOD9 = new int[80];
   private static int[] MOD17;
   private static int[] MOD5;
   private static int[] MOD3;
   private int blocksizeBytes;
   private int blocksizeWords;
   private long[] currentBlock;
   private long[] t = new long[5];
   private long[] kw;
   private ThreefishCipher cipher;
   private boolean forEncryption;

   public ThreefishEngine(int var1) {
      this.blocksizeBytes = var1 / 8;
      this.blocksizeWords = this.blocksizeBytes / 8;
      this.currentBlock = new long[this.blocksizeWords];
      this.kw = new long[2 * this.blocksizeWords + 1];
      switch (var1) {
         case 256:
            this.cipher = new Threefish256Cipher(this.kw, this.t);
            break;
         case 512:
            this.cipher = new Threefish512Cipher(this.kw, this.t);
            break;
         case 1024:
            this.cipher = new Threefish1024Cipher(this.kw, this.t);
            break;
         default:
            throw new IllegalArgumentException("Invalid blocksize - Threefish is defined with block size of 256, 512, or 1024 bits");
      }

   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      byte[] var4;
      byte[] var5;
      if (var2 instanceof TweakableBlockCipherParameters) {
         TweakableBlockCipherParameters var3 = (TweakableBlockCipherParameters)var2;
         var4 = var3.getKey().getKey();
         var5 = var3.getTweak();
      } else {
         if (!(var2 instanceof KeyParameter)) {
            throw new IllegalArgumentException("Invalid parameter passed to Threefish init - " + var2.getClass().getName());
         }

         var4 = ((KeyParameter)var2).getKey();
         var5 = null;
      }

      long[] var8 = null;
      long[] var6 = null;
      if (var4 != null) {
         if (var4.length != this.blocksizeBytes) {
            throw new IllegalArgumentException("Threefish key must be same size as block (" + this.blocksizeBytes + " bytes)");
         }

         var8 = new long[this.blocksizeWords];

         for(int var7 = 0; var7 < var8.length; ++var7) {
            var8[var7] = bytesToWord(var4, var7 * 8);
         }
      }

      if (var5 != null) {
         if (var5.length != 16) {
            throw new IllegalArgumentException("Threefish tweak must be 16 bytes");
         }

         var6 = new long[]{bytesToWord(var5, 0), bytesToWord(var5, 8)};
      }

      this.init(var1, var8, var6);
   }

   public void init(boolean var1, long[] var2, long[] var3) {
      this.forEncryption = var1;
      if (var2 != null) {
         this.setKey(var2);
      }

      if (var3 != null) {
         this.setTweak(var3);
      }

   }

   private void setKey(long[] var1) {
      if (var1.length != this.blocksizeWords) {
         throw new IllegalArgumentException("Threefish key must be same size as block (" + this.blocksizeWords + " words)");
      } else {
         long var2 = 2004413935125273122L;

         for(int var4 = 0; var4 < this.blocksizeWords; ++var4) {
            this.kw[var4] = var1[var4];
            var2 ^= this.kw[var4];
         }

         this.kw[this.blocksizeWords] = var2;
         System.arraycopy(this.kw, 0, this.kw, this.blocksizeWords + 1, this.blocksizeWords);
      }
   }

   private void setTweak(long[] var1) {
      if (var1.length != 2) {
         throw new IllegalArgumentException("Tweak must be 2 words.");
      } else {
         this.t[0] = var1[0];
         this.t[1] = var1[1];
         this.t[2] = this.t[0] ^ this.t[1];
         this.t[3] = this.t[0];
         this.t[4] = this.t[1];
      }
   }

   public String getAlgorithmName() {
      return "Threefish-" + this.blocksizeBytes * 8;
   }

   public int getBlockSize() {
      return this.blocksizeBytes;
   }

   public void reset() {
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      if (var4 + this.blocksizeBytes > var3.length) {
         throw new DataLengthException("Output buffer too short");
      } else if (var2 + this.blocksizeBytes > var1.length) {
         throw new DataLengthException("Input buffer too short");
      } else {
         int var5;
         for(var5 = 0; var5 < this.blocksizeBytes; var5 += 8) {
            this.currentBlock[var5 >> 3] = bytesToWord(var1, var2 + var5);
         }

         this.processBlock(this.currentBlock, this.currentBlock);

         for(var5 = 0; var5 < this.blocksizeBytes; var5 += 8) {
            wordToBytes(this.currentBlock[var5 >> 3], var3, var4 + var5);
         }

         return this.blocksizeBytes;
      }
   }

   public int processBlock(long[] var1, long[] var2) throws DataLengthException, IllegalStateException {
      if (this.kw[this.blocksizeWords] == 0L) {
         throw new IllegalStateException("Threefish engine not initialised");
      } else if (var1.length != this.blocksizeWords) {
         throw new DataLengthException("Input buffer too short");
      } else if (var2.length != this.blocksizeWords) {
         throw new DataLengthException("Output buffer too short");
      } else {
         if (this.forEncryption) {
            this.cipher.encryptBlock(var1, var2);
         } else {
            this.cipher.decryptBlock(var1, var2);
         }

         return this.blocksizeWords;
      }
   }

   public static long bytesToWord(byte[] var0, int var1) {
      if (var1 + 8 > var0.length) {
         throw new IllegalArgumentException();
      } else {
         long var2 = 0L;
         int var4 = var1 + 1;
         var2 = (long)var0[var1] & 255L;
         var2 |= ((long)var0[var4++] & 255L) << 8;
         var2 |= ((long)var0[var4++] & 255L) << 16;
         var2 |= ((long)var0[var4++] & 255L) << 24;
         var2 |= ((long)var0[var4++] & 255L) << 32;
         var2 |= ((long)var0[var4++] & 255L) << 40;
         var2 |= ((long)var0[var4++] & 255L) << 48;
         var2 |= ((long)var0[var4++] & 255L) << 56;
         return var2;
      }
   }

   public static void wordToBytes(long var0, byte[] var2, int var3) {
      if (var3 + 8 > var2.length) {
         throw new IllegalArgumentException();
      } else {
         int var4 = var3 + 1;
         var2[var3] = (byte)((int)var0);
         var2[var4++] = (byte)((int)(var0 >> 8));
         var2[var4++] = (byte)((int)(var0 >> 16));
         var2[var4++] = (byte)((int)(var0 >> 24));
         var2[var4++] = (byte)((int)(var0 >> 32));
         var2[var4++] = (byte)((int)(var0 >> 40));
         var2[var4++] = (byte)((int)(var0 >> 48));
         var2[var4++] = (byte)((int)(var0 >> 56));
      }
   }

   static long rotlXor(long var0, int var2, long var3) {
      return (var0 << var2 | var0 >>> -var2) ^ var3;
   }

   static long xorRotr(long var0, int var2, long var3) {
      long var5 = var0 ^ var3;
      return var5 >>> var2 | var5 << -var2;
   }

   static {
      MOD17 = new int[MOD9.length];
      MOD5 = new int[MOD9.length];
      MOD3 = new int[MOD9.length];

      for(int var0 = 0; var0 < MOD9.length; ++var0) {
         MOD17[var0] = var0 % 17;
         MOD9[var0] = var0 % 9;
         MOD5[var0] = var0 % 5;
         MOD3[var0] = var0 % 3;
      }

   }

   private static final class Threefish1024Cipher extends ThreefishCipher {
      private static final int ROTATION_0_0 = 24;
      private static final int ROTATION_0_1 = 13;
      private static final int ROTATION_0_2 = 8;
      private static final int ROTATION_0_3 = 47;
      private static final int ROTATION_0_4 = 8;
      private static final int ROTATION_0_5 = 17;
      private static final int ROTATION_0_6 = 22;
      private static final int ROTATION_0_7 = 37;
      private static final int ROTATION_1_0 = 38;
      private static final int ROTATION_1_1 = 19;
      private static final int ROTATION_1_2 = 10;
      private static final int ROTATION_1_3 = 55;
      private static final int ROTATION_1_4 = 49;
      private static final int ROTATION_1_5 = 18;
      private static final int ROTATION_1_6 = 23;
      private static final int ROTATION_1_7 = 52;
      private static final int ROTATION_2_0 = 33;
      private static final int ROTATION_2_1 = 4;
      private static final int ROTATION_2_2 = 51;
      private static final int ROTATION_2_3 = 13;
      private static final int ROTATION_2_4 = 34;
      private static final int ROTATION_2_5 = 41;
      private static final int ROTATION_2_6 = 59;
      private static final int ROTATION_2_7 = 17;
      private static final int ROTATION_3_0 = 5;
      private static final int ROTATION_3_1 = 20;
      private static final int ROTATION_3_2 = 48;
      private static final int ROTATION_3_3 = 41;
      private static final int ROTATION_3_4 = 47;
      private static final int ROTATION_3_5 = 28;
      private static final int ROTATION_3_6 = 16;
      private static final int ROTATION_3_7 = 25;
      private static final int ROTATION_4_0 = 41;
      private static final int ROTATION_4_1 = 9;
      private static final int ROTATION_4_2 = 37;
      private static final int ROTATION_4_3 = 31;
      private static final int ROTATION_4_4 = 12;
      private static final int ROTATION_4_5 = 47;
      private static final int ROTATION_4_6 = 44;
      private static final int ROTATION_4_7 = 30;
      private static final int ROTATION_5_0 = 16;
      private static final int ROTATION_5_1 = 34;
      private static final int ROTATION_5_2 = 56;
      private static final int ROTATION_5_3 = 51;
      private static final int ROTATION_5_4 = 4;
      private static final int ROTATION_5_5 = 53;
      private static final int ROTATION_5_6 = 42;
      private static final int ROTATION_5_7 = 41;
      private static final int ROTATION_6_0 = 31;
      private static final int ROTATION_6_1 = 44;
      private static final int ROTATION_6_2 = 47;
      private static final int ROTATION_6_3 = 46;
      private static final int ROTATION_6_4 = 19;
      private static final int ROTATION_6_5 = 42;
      private static final int ROTATION_6_6 = 44;
      private static final int ROTATION_6_7 = 25;
      private static final int ROTATION_7_0 = 9;
      private static final int ROTATION_7_1 = 48;
      private static final int ROTATION_7_2 = 35;
      private static final int ROTATION_7_3 = 52;
      private static final int ROTATION_7_4 = 23;
      private static final int ROTATION_7_5 = 31;
      private static final int ROTATION_7_6 = 37;
      private static final int ROTATION_7_7 = 20;

      public Threefish1024Cipher(long[] var1, long[] var2) {
         super(var1, var2);
      }

      void encryptBlock(long[] var1, long[] var2) {
         long[] var3 = this.kw;
         long[] var4 = this.t;
         int[] var5 = ThreefishEngine.MOD17;
         int[] var6 = ThreefishEngine.MOD3;
         if (var3.length != 33) {
            throw new IllegalArgumentException();
         } else if (var4.length != 5) {
            throw new IllegalArgumentException();
         } else {
            long var7 = var1[0];
            long var9 = var1[1];
            long var11 = var1[2];
            long var13 = var1[3];
            long var15 = var1[4];
            long var17 = var1[5];
            long var19 = var1[6];
            long var21 = var1[7];
            long var23 = var1[8];
            long var25 = var1[9];
            long var27 = var1[10];
            long var29 = var1[11];
            long var31 = var1[12];
            long var33 = var1[13];
            long var35 = var1[14];
            long var37 = var1[15];
            var7 += var3[0];
            var9 += var3[1];
            var11 += var3[2];
            var13 += var3[3];
            var15 += var3[4];
            var17 += var3[5];
            var19 += var3[6];
            var21 += var3[7];
            var23 += var3[8];
            var25 += var3[9];
            var27 += var3[10];
            var29 += var3[11];
            var31 += var3[12];
            var33 += var3[13] + var4[0];
            var35 += var3[14] + var4[1];
            var37 += var3[15];

            for(int var39 = 1; var39 < 20; var39 += 2) {
               int var40 = var5[var39];
               int var41 = var6[var39];
               var9 = ThreefishEngine.rotlXor(var9, 24, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 13, var11 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 8, var15 += var17);
               var21 = ThreefishEngine.rotlXor(var21, 47, var19 += var21);
               var25 = ThreefishEngine.rotlXor(var25, 8, var23 += var25);
               var29 = ThreefishEngine.rotlXor(var29, 17, var27 += var29);
               var33 = ThreefishEngine.rotlXor(var33, 22, var31 += var33);
               var37 = ThreefishEngine.rotlXor(var37, 37, var35 += var37);
               var25 = ThreefishEngine.rotlXor(var25, 38, var7 += var25);
               var33 = ThreefishEngine.rotlXor(var33, 19, var11 += var33);
               var29 = ThreefishEngine.rotlXor(var29, 10, var19 += var29);
               var37 = ThreefishEngine.rotlXor(var37, 55, var15 += var37);
               var21 = ThreefishEngine.rotlXor(var21, 49, var27 += var21);
               var13 = ThreefishEngine.rotlXor(var13, 18, var31 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 23, var35 += var17);
               var9 = ThreefishEngine.rotlXor(var9, 52, var23 += var9);
               var21 = ThreefishEngine.rotlXor(var21, 33, var7 += var21);
               var17 = ThreefishEngine.rotlXor(var17, 4, var11 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 51, var15 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 13, var19 += var9);
               var37 = ThreefishEngine.rotlXor(var37, 34, var31 += var37);
               var33 = ThreefishEngine.rotlXor(var33, 41, var35 += var33);
               var29 = ThreefishEngine.rotlXor(var29, 59, var23 += var29);
               var25 = ThreefishEngine.rotlXor(var25, 17, var27 += var25);
               var37 = ThreefishEngine.rotlXor(var37, 5, var7 += var37);
               var29 = ThreefishEngine.rotlXor(var29, 20, var11 += var29);
               var33 = ThreefishEngine.rotlXor(var33, 48, var19 += var33);
               var25 = ThreefishEngine.rotlXor(var25, 41, var15 += var25);
               var9 = ThreefishEngine.rotlXor(var9, 47, var35 += var9);
               var17 = ThreefishEngine.rotlXor(var17, 28, var23 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 16, var27 += var13);
               var21 = ThreefishEngine.rotlXor(var21, 25, var31 += var21);
               var7 += var3[var40];
               var9 += var3[var40 + 1];
               var11 += var3[var40 + 2];
               var13 += var3[var40 + 3];
               var15 += var3[var40 + 4];
               var17 += var3[var40 + 5];
               var19 += var3[var40 + 6];
               var21 += var3[var40 + 7];
               var23 += var3[var40 + 8];
               var25 += var3[var40 + 9];
               var27 += var3[var40 + 10];
               var29 += var3[var40 + 11];
               var31 += var3[var40 + 12];
               var33 += var3[var40 + 13] + var4[var41];
               var35 += var3[var40 + 14] + var4[var41 + 1];
               var37 += var3[var40 + 15] + (long)var39;
               var9 = ThreefishEngine.rotlXor(var9, 41, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 9, var11 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 37, var15 += var17);
               var21 = ThreefishEngine.rotlXor(var21, 31, var19 += var21);
               var25 = ThreefishEngine.rotlXor(var25, 12, var23 += var25);
               var29 = ThreefishEngine.rotlXor(var29, 47, var27 += var29);
               var33 = ThreefishEngine.rotlXor(var33, 44, var31 += var33);
               var37 = ThreefishEngine.rotlXor(var37, 30, var35 += var37);
               var25 = ThreefishEngine.rotlXor(var25, 16, var7 += var25);
               var33 = ThreefishEngine.rotlXor(var33, 34, var11 += var33);
               var29 = ThreefishEngine.rotlXor(var29, 56, var19 += var29);
               var37 = ThreefishEngine.rotlXor(var37, 51, var15 += var37);
               var21 = ThreefishEngine.rotlXor(var21, 4, var27 += var21);
               var13 = ThreefishEngine.rotlXor(var13, 53, var31 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 42, var35 += var17);
               var9 = ThreefishEngine.rotlXor(var9, 41, var23 += var9);
               var21 = ThreefishEngine.rotlXor(var21, 31, var7 += var21);
               var17 = ThreefishEngine.rotlXor(var17, 44, var11 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 47, var15 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 46, var19 += var9);
               var37 = ThreefishEngine.rotlXor(var37, 19, var31 += var37);
               var33 = ThreefishEngine.rotlXor(var33, 42, var35 += var33);
               var29 = ThreefishEngine.rotlXor(var29, 44, var23 += var29);
               var25 = ThreefishEngine.rotlXor(var25, 25, var27 += var25);
               var37 = ThreefishEngine.rotlXor(var37, 9, var7 += var37);
               var29 = ThreefishEngine.rotlXor(var29, 48, var11 += var29);
               var33 = ThreefishEngine.rotlXor(var33, 35, var19 += var33);
               var25 = ThreefishEngine.rotlXor(var25, 52, var15 += var25);
               var9 = ThreefishEngine.rotlXor(var9, 23, var35 += var9);
               var17 = ThreefishEngine.rotlXor(var17, 31, var23 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 37, var27 += var13);
               var21 = ThreefishEngine.rotlXor(var21, 20, var31 += var21);
               var7 += var3[var40 + 1];
               var9 += var3[var40 + 2];
               var11 += var3[var40 + 3];
               var13 += var3[var40 + 4];
               var15 += var3[var40 + 5];
               var17 += var3[var40 + 6];
               var19 += var3[var40 + 7];
               var21 += var3[var40 + 8];
               var23 += var3[var40 + 9];
               var25 += var3[var40 + 10];
               var27 += var3[var40 + 11];
               var29 += var3[var40 + 12];
               var31 += var3[var40 + 13];
               var33 += var3[var40 + 14] + var4[var41 + 1];
               var35 += var3[var40 + 15] + var4[var41 + 2];
               var37 += var3[var40 + 16] + (long)var39 + 1L;
            }

            var2[0] = var7;
            var2[1] = var9;
            var2[2] = var11;
            var2[3] = var13;
            var2[4] = var15;
            var2[5] = var17;
            var2[6] = var19;
            var2[7] = var21;
            var2[8] = var23;
            var2[9] = var25;
            var2[10] = var27;
            var2[11] = var29;
            var2[12] = var31;
            var2[13] = var33;
            var2[14] = var35;
            var2[15] = var37;
         }
      }

      void decryptBlock(long[] var1, long[] var2) {
         long[] var3 = this.kw;
         long[] var4 = this.t;
         int[] var5 = ThreefishEngine.MOD17;
         int[] var6 = ThreefishEngine.MOD3;
         if (var3.length != 33) {
            throw new IllegalArgumentException();
         } else if (var4.length != 5) {
            throw new IllegalArgumentException();
         } else {
            long var7 = var1[0];
            long var9 = var1[1];
            long var11 = var1[2];
            long var13 = var1[3];
            long var15 = var1[4];
            long var17 = var1[5];
            long var19 = var1[6];
            long var21 = var1[7];
            long var23 = var1[8];
            long var25 = var1[9];
            long var27 = var1[10];
            long var29 = var1[11];
            long var31 = var1[12];
            long var33 = var1[13];
            long var35 = var1[14];
            long var37 = var1[15];

            for(int var39 = 19; var39 >= 1; var39 -= 2) {
               int var40 = var5[var39];
               int var41 = var6[var39];
               var7 -= var3[var40 + 1];
               var9 -= var3[var40 + 2];
               var11 -= var3[var40 + 3];
               var13 -= var3[var40 + 4];
               var15 -= var3[var40 + 5];
               var17 -= var3[var40 + 6];
               var19 -= var3[var40 + 7];
               var21 -= var3[var40 + 8];
               var23 -= var3[var40 + 9];
               var25 -= var3[var40 + 10];
               var27 -= var3[var40 + 11];
               var29 -= var3[var40 + 12];
               var31 -= var3[var40 + 13];
               var33 -= var3[var40 + 14] + var4[var41 + 1];
               var35 -= var3[var40 + 15] + var4[var41 + 2];
               var37 -= var3[var40 + 16] + (long)var39 + 1L;
               var37 = ThreefishEngine.xorRotr(var37, 9, var7);
               var7 -= var37;
               var29 = ThreefishEngine.xorRotr(var29, 48, var11);
               var11 -= var29;
               var33 = ThreefishEngine.xorRotr(var33, 35, var19);
               var19 -= var33;
               var25 = ThreefishEngine.xorRotr(var25, 52, var15);
               var15 -= var25;
               var9 = ThreefishEngine.xorRotr(var9, 23, var35);
               var35 -= var9;
               var17 = ThreefishEngine.xorRotr(var17, 31, var23);
               var23 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 37, var27);
               var27 -= var13;
               var21 = ThreefishEngine.xorRotr(var21, 20, var31);
               var31 -= var21;
               var21 = ThreefishEngine.xorRotr(var21, 31, var7);
               var7 -= var21;
               var17 = ThreefishEngine.xorRotr(var17, 44, var11);
               var11 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 47, var15);
               var15 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 46, var19);
               var19 -= var9;
               var37 = ThreefishEngine.xorRotr(var37, 19, var31);
               var31 -= var37;
               var33 = ThreefishEngine.xorRotr(var33, 42, var35);
               var35 -= var33;
               var29 = ThreefishEngine.xorRotr(var29, 44, var23);
               var23 -= var29;
               var25 = ThreefishEngine.xorRotr(var25, 25, var27);
               var27 -= var25;
               var25 = ThreefishEngine.xorRotr(var25, 16, var7);
               var7 -= var25;
               var33 = ThreefishEngine.xorRotr(var33, 34, var11);
               var11 -= var33;
               var29 = ThreefishEngine.xorRotr(var29, 56, var19);
               var19 -= var29;
               var37 = ThreefishEngine.xorRotr(var37, 51, var15);
               var15 -= var37;
               var21 = ThreefishEngine.xorRotr(var21, 4, var27);
               var27 -= var21;
               var13 = ThreefishEngine.xorRotr(var13, 53, var31);
               var31 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 42, var35);
               var35 -= var17;
               var9 = ThreefishEngine.xorRotr(var9, 41, var23);
               var23 -= var9;
               var9 = ThreefishEngine.xorRotr(var9, 41, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 9, var11);
               var11 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 37, var15);
               var15 -= var17;
               var21 = ThreefishEngine.xorRotr(var21, 31, var19);
               var19 -= var21;
               var25 = ThreefishEngine.xorRotr(var25, 12, var23);
               var23 -= var25;
               var29 = ThreefishEngine.xorRotr(var29, 47, var27);
               var27 -= var29;
               var33 = ThreefishEngine.xorRotr(var33, 44, var31);
               var31 -= var33;
               var37 = ThreefishEngine.xorRotr(var37, 30, var35);
               var35 -= var37;
               var7 -= var3[var40];
               var9 -= var3[var40 + 1];
               var11 -= var3[var40 + 2];
               var13 -= var3[var40 + 3];
               var15 -= var3[var40 + 4];
               var17 -= var3[var40 + 5];
               var19 -= var3[var40 + 6];
               var21 -= var3[var40 + 7];
               var23 -= var3[var40 + 8];
               var25 -= var3[var40 + 9];
               var27 -= var3[var40 + 10];
               var29 -= var3[var40 + 11];
               var31 -= var3[var40 + 12];
               var33 -= var3[var40 + 13] + var4[var41];
               var35 -= var3[var40 + 14] + var4[var41 + 1];
               var37 -= var3[var40 + 15] + (long)var39;
               var37 = ThreefishEngine.xorRotr(var37, 5, var7);
               var7 -= var37;
               var29 = ThreefishEngine.xorRotr(var29, 20, var11);
               var11 -= var29;
               var33 = ThreefishEngine.xorRotr(var33, 48, var19);
               var19 -= var33;
               var25 = ThreefishEngine.xorRotr(var25, 41, var15);
               var15 -= var25;
               var9 = ThreefishEngine.xorRotr(var9, 47, var35);
               var35 -= var9;
               var17 = ThreefishEngine.xorRotr(var17, 28, var23);
               var23 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 16, var27);
               var27 -= var13;
               var21 = ThreefishEngine.xorRotr(var21, 25, var31);
               var31 -= var21;
               var21 = ThreefishEngine.xorRotr(var21, 33, var7);
               var7 -= var21;
               var17 = ThreefishEngine.xorRotr(var17, 4, var11);
               var11 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 51, var15);
               var15 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 13, var19);
               var19 -= var9;
               var37 = ThreefishEngine.xorRotr(var37, 34, var31);
               var31 -= var37;
               var33 = ThreefishEngine.xorRotr(var33, 41, var35);
               var35 -= var33;
               var29 = ThreefishEngine.xorRotr(var29, 59, var23);
               var23 -= var29;
               var25 = ThreefishEngine.xorRotr(var25, 17, var27);
               var27 -= var25;
               var25 = ThreefishEngine.xorRotr(var25, 38, var7);
               var7 -= var25;
               var33 = ThreefishEngine.xorRotr(var33, 19, var11);
               var11 -= var33;
               var29 = ThreefishEngine.xorRotr(var29, 10, var19);
               var19 -= var29;
               var37 = ThreefishEngine.xorRotr(var37, 55, var15);
               var15 -= var37;
               var21 = ThreefishEngine.xorRotr(var21, 49, var27);
               var27 -= var21;
               var13 = ThreefishEngine.xorRotr(var13, 18, var31);
               var31 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 23, var35);
               var35 -= var17;
               var9 = ThreefishEngine.xorRotr(var9, 52, var23);
               var23 -= var9;
               var9 = ThreefishEngine.xorRotr(var9, 24, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 13, var11);
               var11 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 8, var15);
               var15 -= var17;
               var21 = ThreefishEngine.xorRotr(var21, 47, var19);
               var19 -= var21;
               var25 = ThreefishEngine.xorRotr(var25, 8, var23);
               var23 -= var25;
               var29 = ThreefishEngine.xorRotr(var29, 17, var27);
               var27 -= var29;
               var33 = ThreefishEngine.xorRotr(var33, 22, var31);
               var31 -= var33;
               var37 = ThreefishEngine.xorRotr(var37, 37, var35);
               var35 -= var37;
            }

            var7 -= var3[0];
            var9 -= var3[1];
            var11 -= var3[2];
            var13 -= var3[3];
            var15 -= var3[4];
            var17 -= var3[5];
            var19 -= var3[6];
            var21 -= var3[7];
            var23 -= var3[8];
            var25 -= var3[9];
            var27 -= var3[10];
            var29 -= var3[11];
            var31 -= var3[12];
            var33 -= var3[13] + var4[0];
            var35 -= var3[14] + var4[1];
            var37 -= var3[15];
            var2[0] = var7;
            var2[1] = var9;
            var2[2] = var11;
            var2[3] = var13;
            var2[4] = var15;
            var2[5] = var17;
            var2[6] = var19;
            var2[7] = var21;
            var2[8] = var23;
            var2[9] = var25;
            var2[10] = var27;
            var2[11] = var29;
            var2[12] = var31;
            var2[13] = var33;
            var2[14] = var35;
            var2[15] = var37;
         }
      }
   }

   private static final class Threefish256Cipher extends ThreefishCipher {
      private static final int ROTATION_0_0 = 14;
      private static final int ROTATION_0_1 = 16;
      private static final int ROTATION_1_0 = 52;
      private static final int ROTATION_1_1 = 57;
      private static final int ROTATION_2_0 = 23;
      private static final int ROTATION_2_1 = 40;
      private static final int ROTATION_3_0 = 5;
      private static final int ROTATION_3_1 = 37;
      private static final int ROTATION_4_0 = 25;
      private static final int ROTATION_4_1 = 33;
      private static final int ROTATION_5_0 = 46;
      private static final int ROTATION_5_1 = 12;
      private static final int ROTATION_6_0 = 58;
      private static final int ROTATION_6_1 = 22;
      private static final int ROTATION_7_0 = 32;
      private static final int ROTATION_7_1 = 32;

      public Threefish256Cipher(long[] var1, long[] var2) {
         super(var1, var2);
      }

      void encryptBlock(long[] var1, long[] var2) {
         long[] var3 = this.kw;
         long[] var4 = this.t;
         int[] var5 = ThreefishEngine.MOD5;
         int[] var6 = ThreefishEngine.MOD3;
         if (var3.length != 9) {
            throw new IllegalArgumentException();
         } else if (var4.length != 5) {
            throw new IllegalArgumentException();
         } else {
            long var7 = var1[0];
            long var9 = var1[1];
            long var11 = var1[2];
            long var13 = var1[3];
            var7 += var3[0];
            var9 += var3[1] + var4[0];
            var11 += var3[2] + var4[1];
            var13 += var3[3];

            for(int var15 = 1; var15 < 18; var15 += 2) {
               int var16 = var5[var15];
               int var17 = var6[var15];
               var9 = ThreefishEngine.rotlXor(var9, 14, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 16, var11 += var13);
               var13 = ThreefishEngine.rotlXor(var13, 52, var7 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 57, var11 += var9);
               var9 = ThreefishEngine.rotlXor(var9, 23, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 40, var11 += var13);
               var13 = ThreefishEngine.rotlXor(var13, 5, var7 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 37, var11 += var9);
               var7 += var3[var16];
               var9 += var3[var16 + 1] + var4[var17];
               var11 += var3[var16 + 2] + var4[var17 + 1];
               var13 += var3[var16 + 3] + (long)var15;
               var9 = ThreefishEngine.rotlXor(var9, 25, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 33, var11 += var13);
               var13 = ThreefishEngine.rotlXor(var13, 46, var7 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 12, var11 += var9);
               var9 = ThreefishEngine.rotlXor(var9, 58, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 22, var11 += var13);
               var13 = ThreefishEngine.rotlXor(var13, 32, var7 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 32, var11 += var9);
               var7 += var3[var16 + 1];
               var9 += var3[var16 + 2] + var4[var17 + 1];
               var11 += var3[var16 + 3] + var4[var17 + 2];
               var13 += var3[var16 + 4] + (long)var15 + 1L;
            }

            var2[0] = var7;
            var2[1] = var9;
            var2[2] = var11;
            var2[3] = var13;
         }
      }

      void decryptBlock(long[] var1, long[] var2) {
         long[] var3 = this.kw;
         long[] var4 = this.t;
         int[] var5 = ThreefishEngine.MOD5;
         int[] var6 = ThreefishEngine.MOD3;
         if (var3.length != 9) {
            throw new IllegalArgumentException();
         } else if (var4.length != 5) {
            throw new IllegalArgumentException();
         } else {
            long var7 = var1[0];
            long var9 = var1[1];
            long var11 = var1[2];
            long var13 = var1[3];

            for(int var15 = 17; var15 >= 1; var15 -= 2) {
               int var16 = var5[var15];
               int var17 = var6[var15];
               var7 -= var3[var16 + 1];
               var9 -= var3[var16 + 2] + var4[var17 + 1];
               var11 -= var3[var16 + 3] + var4[var17 + 2];
               var13 -= var3[var16 + 4] + (long)var15 + 1L;
               var13 = ThreefishEngine.xorRotr(var13, 32, var7);
               var7 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 32, var11);
               var11 -= var9;
               var9 = ThreefishEngine.xorRotr(var9, 58, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 22, var11);
               var11 -= var13;
               var13 = ThreefishEngine.xorRotr(var13, 46, var7);
               var7 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 12, var11);
               var11 -= var9;
               var9 = ThreefishEngine.xorRotr(var9, 25, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 33, var11);
               var11 -= var13;
               var7 -= var3[var16];
               var9 -= var3[var16 + 1] + var4[var17];
               var11 -= var3[var16 + 2] + var4[var17 + 1];
               var13 -= var3[var16 + 3] + (long)var15;
               var13 = ThreefishEngine.xorRotr(var13, 5, var7);
               var7 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 37, var11);
               var11 -= var9;
               var9 = ThreefishEngine.xorRotr(var9, 23, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 40, var11);
               var11 -= var13;
               var13 = ThreefishEngine.xorRotr(var13, 52, var7);
               var7 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 57, var11);
               var11 -= var9;
               var9 = ThreefishEngine.xorRotr(var9, 14, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 16, var11);
               var11 -= var13;
            }

            var7 -= var3[0];
            var9 -= var3[1] + var4[0];
            var11 -= var3[2] + var4[1];
            var13 -= var3[3];
            var2[0] = var7;
            var2[1] = var9;
            var2[2] = var11;
            var2[3] = var13;
         }
      }
   }

   private static final class Threefish512Cipher extends ThreefishCipher {
      private static final int ROTATION_0_0 = 46;
      private static final int ROTATION_0_1 = 36;
      private static final int ROTATION_0_2 = 19;
      private static final int ROTATION_0_3 = 37;
      private static final int ROTATION_1_0 = 33;
      private static final int ROTATION_1_1 = 27;
      private static final int ROTATION_1_2 = 14;
      private static final int ROTATION_1_3 = 42;
      private static final int ROTATION_2_0 = 17;
      private static final int ROTATION_2_1 = 49;
      private static final int ROTATION_2_2 = 36;
      private static final int ROTATION_2_3 = 39;
      private static final int ROTATION_3_0 = 44;
      private static final int ROTATION_3_1 = 9;
      private static final int ROTATION_3_2 = 54;
      private static final int ROTATION_3_3 = 56;
      private static final int ROTATION_4_0 = 39;
      private static final int ROTATION_4_1 = 30;
      private static final int ROTATION_4_2 = 34;
      private static final int ROTATION_4_3 = 24;
      private static final int ROTATION_5_0 = 13;
      private static final int ROTATION_5_1 = 50;
      private static final int ROTATION_5_2 = 10;
      private static final int ROTATION_5_3 = 17;
      private static final int ROTATION_6_0 = 25;
      private static final int ROTATION_6_1 = 29;
      private static final int ROTATION_6_2 = 39;
      private static final int ROTATION_6_3 = 43;
      private static final int ROTATION_7_0 = 8;
      private static final int ROTATION_7_1 = 35;
      private static final int ROTATION_7_2 = 56;
      private static final int ROTATION_7_3 = 22;

      protected Threefish512Cipher(long[] var1, long[] var2) {
         super(var1, var2);
      }

      public void encryptBlock(long[] var1, long[] var2) {
         long[] var3 = this.kw;
         long[] var4 = this.t;
         int[] var5 = ThreefishEngine.MOD9;
         int[] var6 = ThreefishEngine.MOD3;
         if (var3.length != 17) {
            throw new IllegalArgumentException();
         } else if (var4.length != 5) {
            throw new IllegalArgumentException();
         } else {
            long var7 = var1[0];
            long var9 = var1[1];
            long var11 = var1[2];
            long var13 = var1[3];
            long var15 = var1[4];
            long var17 = var1[5];
            long var19 = var1[6];
            long var21 = var1[7];
            var7 += var3[0];
            var9 += var3[1];
            var11 += var3[2];
            var13 += var3[3];
            var15 += var3[4];
            var17 += var3[5] + var4[0];
            var19 += var3[6] + var4[1];
            var21 += var3[7];

            for(int var23 = 1; var23 < 18; var23 += 2) {
               int var24 = var5[var23];
               int var25 = var6[var23];
               var9 = ThreefishEngine.rotlXor(var9, 46, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 36, var11 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 19, var15 += var17);
               var21 = ThreefishEngine.rotlXor(var21, 37, var19 += var21);
               var9 = ThreefishEngine.rotlXor(var9, 33, var11 += var9);
               var21 = ThreefishEngine.rotlXor(var21, 27, var15 += var21);
               var17 = ThreefishEngine.rotlXor(var17, 14, var19 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 42, var7 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 17, var15 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 49, var19 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 36, var7 += var17);
               var21 = ThreefishEngine.rotlXor(var21, 39, var11 += var21);
               var9 = ThreefishEngine.rotlXor(var9, 44, var19 += var9);
               var21 = ThreefishEngine.rotlXor(var21, 9, var7 += var21);
               var17 = ThreefishEngine.rotlXor(var17, 54, var11 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 56, var15 += var13);
               var7 += var3[var24];
               var9 += var3[var24 + 1];
               var11 += var3[var24 + 2];
               var13 += var3[var24 + 3];
               var15 += var3[var24 + 4];
               var17 += var3[var24 + 5] + var4[var25];
               var19 += var3[var24 + 6] + var4[var25 + 1];
               var21 += var3[var24 + 7] + (long)var23;
               var9 = ThreefishEngine.rotlXor(var9, 39, var7 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 30, var11 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 34, var15 += var17);
               var21 = ThreefishEngine.rotlXor(var21, 24, var19 += var21);
               var9 = ThreefishEngine.rotlXor(var9, 13, var11 += var9);
               var21 = ThreefishEngine.rotlXor(var21, 50, var15 += var21);
               var17 = ThreefishEngine.rotlXor(var17, 10, var19 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 17, var7 += var13);
               var9 = ThreefishEngine.rotlXor(var9, 25, var15 += var9);
               var13 = ThreefishEngine.rotlXor(var13, 29, var19 += var13);
               var17 = ThreefishEngine.rotlXor(var17, 39, var7 += var17);
               var21 = ThreefishEngine.rotlXor(var21, 43, var11 += var21);
               var9 = ThreefishEngine.rotlXor(var9, 8, var19 += var9);
               var21 = ThreefishEngine.rotlXor(var21, 35, var7 += var21);
               var17 = ThreefishEngine.rotlXor(var17, 56, var11 += var17);
               var13 = ThreefishEngine.rotlXor(var13, 22, var15 += var13);
               var7 += var3[var24 + 1];
               var9 += var3[var24 + 2];
               var11 += var3[var24 + 3];
               var13 += var3[var24 + 4];
               var15 += var3[var24 + 5];
               var17 += var3[var24 + 6] + var4[var25 + 1];
               var19 += var3[var24 + 7] + var4[var25 + 2];
               var21 += var3[var24 + 8] + (long)var23 + 1L;
            }

            var2[0] = var7;
            var2[1] = var9;
            var2[2] = var11;
            var2[3] = var13;
            var2[4] = var15;
            var2[5] = var17;
            var2[6] = var19;
            var2[7] = var21;
         }
      }

      public void decryptBlock(long[] var1, long[] var2) {
         long[] var3 = this.kw;
         long[] var4 = this.t;
         int[] var5 = ThreefishEngine.MOD9;
         int[] var6 = ThreefishEngine.MOD3;
         if (var3.length != 17) {
            throw new IllegalArgumentException();
         } else if (var4.length != 5) {
            throw new IllegalArgumentException();
         } else {
            long var7 = var1[0];
            long var9 = var1[1];
            long var11 = var1[2];
            long var13 = var1[3];
            long var15 = var1[4];
            long var17 = var1[5];
            long var19 = var1[6];
            long var21 = var1[7];

            for(int var23 = 17; var23 >= 1; var23 -= 2) {
               int var24 = var5[var23];
               int var25 = var6[var23];
               var7 -= var3[var24 + 1];
               var9 -= var3[var24 + 2];
               var11 -= var3[var24 + 3];
               var13 -= var3[var24 + 4];
               var15 -= var3[var24 + 5];
               var17 -= var3[var24 + 6] + var4[var25 + 1];
               var19 -= var3[var24 + 7] + var4[var25 + 2];
               var21 -= var3[var24 + 8] + (long)var23 + 1L;
               var9 = ThreefishEngine.xorRotr(var9, 8, var19);
               var19 -= var9;
               var21 = ThreefishEngine.xorRotr(var21, 35, var7);
               var7 -= var21;
               var17 = ThreefishEngine.xorRotr(var17, 56, var11);
               var11 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 22, var15);
               var15 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 25, var15);
               var15 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 29, var19);
               var19 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 39, var7);
               var7 -= var17;
               var21 = ThreefishEngine.xorRotr(var21, 43, var11);
               var11 -= var21;
               var9 = ThreefishEngine.xorRotr(var9, 13, var11);
               var11 -= var9;
               var21 = ThreefishEngine.xorRotr(var21, 50, var15);
               var15 -= var21;
               var17 = ThreefishEngine.xorRotr(var17, 10, var19);
               var19 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 17, var7);
               var7 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 39, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 30, var11);
               var11 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 34, var15);
               var15 -= var17;
               var21 = ThreefishEngine.xorRotr(var21, 24, var19);
               var19 -= var21;
               var7 -= var3[var24];
               var9 -= var3[var24 + 1];
               var11 -= var3[var24 + 2];
               var13 -= var3[var24 + 3];
               var15 -= var3[var24 + 4];
               var17 -= var3[var24 + 5] + var4[var25];
               var19 -= var3[var24 + 6] + var4[var25 + 1];
               var21 -= var3[var24 + 7] + (long)var23;
               var9 = ThreefishEngine.xorRotr(var9, 44, var19);
               var19 -= var9;
               var21 = ThreefishEngine.xorRotr(var21, 9, var7);
               var7 -= var21;
               var17 = ThreefishEngine.xorRotr(var17, 54, var11);
               var11 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 56, var15);
               var15 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 17, var15);
               var15 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 49, var19);
               var19 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 36, var7);
               var7 -= var17;
               var21 = ThreefishEngine.xorRotr(var21, 39, var11);
               var11 -= var21;
               var9 = ThreefishEngine.xorRotr(var9, 33, var11);
               var11 -= var9;
               var21 = ThreefishEngine.xorRotr(var21, 27, var15);
               var15 -= var21;
               var17 = ThreefishEngine.xorRotr(var17, 14, var19);
               var19 -= var17;
               var13 = ThreefishEngine.xorRotr(var13, 42, var7);
               var7 -= var13;
               var9 = ThreefishEngine.xorRotr(var9, 46, var7);
               var7 -= var9;
               var13 = ThreefishEngine.xorRotr(var13, 36, var11);
               var11 -= var13;
               var17 = ThreefishEngine.xorRotr(var17, 19, var15);
               var15 -= var17;
               var21 = ThreefishEngine.xorRotr(var21, 37, var19);
               var19 -= var21;
            }

            var7 -= var3[0];
            var9 -= var3[1];
            var11 -= var3[2];
            var13 -= var3[3];
            var15 -= var3[4];
            var17 -= var3[5] + var4[0];
            var19 -= var3[6] + var4[1];
            var21 -= var3[7];
            var2[0] = var7;
            var2[1] = var9;
            var2[2] = var11;
            var2[3] = var13;
            var2[4] = var15;
            var2[5] = var17;
            var2[6] = var19;
            var2[7] = var21;
         }
      }
   }

   private abstract static class ThreefishCipher {
      protected final long[] t;
      protected final long[] kw;

      protected ThreefishCipher(long[] var1, long[] var2) {
         this.kw = var1;
         this.t = var2;
      }

      abstract void encryptBlock(long[] var1, long[] var2);

      abstract void decryptBlock(long[] var1, long[] var2);
   }
}
