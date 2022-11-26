package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.util.Arrays;

public class KeccakDigest implements ExtendedDigest {
   private static long[] KeccakRoundConstants = keccakInitializeRoundConstants();
   private static int[] KeccakRhoOffsets = keccakInitializeRhoOffsets();
   protected byte[] state;
   protected byte[] dataQueue;
   protected int rate;
   protected int bitsInQueue;
   protected int fixedOutputLength;
   protected boolean squeezing;
   protected int bitsAvailableForSqueezing;
   protected byte[] chunk;
   protected byte[] oneByte;
   long[] C;
   long[] tempA;
   long[] chiC;

   private static long[] keccakInitializeRoundConstants() {
      long[] var0 = new long[24];
      byte[] var1 = new byte[]{1};

      for(int var2 = 0; var2 < 24; ++var2) {
         var0[var2] = 0L;

         for(int var3 = 0; var3 < 7; ++var3) {
            int var4 = (1 << var3) - 1;
            if (LFSR86540(var1)) {
               var0[var2] ^= 1L << var4;
            }
         }
      }

      return var0;
   }

   private static boolean LFSR86540(byte[] var0) {
      boolean var1 = (var0[0] & 1) != 0;
      if ((var0[0] & 128) != 0) {
         var0[0] = (byte)(var0[0] << 1 ^ 113);
      } else {
         var0[0] = (byte)(var0[0] << 1);
      }

      return var1;
   }

   private static int[] keccakInitializeRhoOffsets() {
      int[] var0 = new int[25];
      var0[0] = 0;
      int var1 = 1;
      int var2 = 0;

      for(int var3 = 0; var3 < 24; ++var3) {
         var0[var1 % 5 + 5 * (var2 % 5)] = (var3 + 1) * (var3 + 2) / 2 % 64;
         int var4 = (0 * var1 + 1 * var2) % 5;
         int var5 = (2 * var1 + 3 * var2) % 5;
         var1 = var4;
         var2 = var5;
      }

      return var0;
   }

   private void clearDataQueueSection(int var1, int var2) {
      for(int var3 = var1; var3 != var1 + var2; ++var3) {
         this.dataQueue[var3] = 0;
      }

   }

   public KeccakDigest() {
      this(288);
   }

   public KeccakDigest(int var1) {
      this.state = new byte[200];
      this.dataQueue = new byte[192];
      this.C = new long[5];
      this.tempA = new long[25];
      this.chiC = new long[5];
      this.init(var1);
   }

   public KeccakDigest(KeccakDigest var1) {
      this.state = new byte[200];
      this.dataQueue = new byte[192];
      this.C = new long[5];
      this.tempA = new long[25];
      this.chiC = new long[5];
      System.arraycopy(var1.state, 0, this.state, 0, var1.state.length);
      System.arraycopy(var1.dataQueue, 0, this.dataQueue, 0, var1.dataQueue.length);
      this.rate = var1.rate;
      this.bitsInQueue = var1.bitsInQueue;
      this.fixedOutputLength = var1.fixedOutputLength;
      this.squeezing = var1.squeezing;
      this.bitsAvailableForSqueezing = var1.bitsAvailableForSqueezing;
      this.chunk = Arrays.clone(var1.chunk);
      this.oneByte = Arrays.clone(var1.oneByte);
   }

   public String getAlgorithmName() {
      return "Keccak-" + this.fixedOutputLength;
   }

   public int getDigestSize() {
      return this.fixedOutputLength / 8;
   }

   public void update(byte var1) {
      this.oneByte[0] = var1;
      this.absorb(this.oneByte, 0, 8L);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.absorb(var1, var2, (long)var3 * 8L);
   }

   public int doFinal(byte[] var1, int var2) {
      this.squeeze(var1, var2, (long)this.fixedOutputLength);
      this.reset();
      return this.getDigestSize();
   }

   protected int doFinal(byte[] var1, int var2, byte var3, int var4) {
      if (var4 > 0) {
         this.oneByte[0] = var3;
         this.absorb(this.oneByte, 0, (long)var4);
      }

      this.squeeze(var1, var2, (long)this.fixedOutputLength);
      this.reset();
      return this.getDigestSize();
   }

   public void reset() {
      this.init(this.fixedOutputLength);
   }

   public int getByteLength() {
      return this.rate / 8;
   }

   private void init(int var1) {
      switch (var1) {
         case 128:
            this.initSponge(1344, 256);
            break;
         case 224:
            this.initSponge(1152, 448);
            break;
         case 256:
            this.initSponge(1088, 512);
            break;
         case 288:
            this.initSponge(1024, 576);
            break;
         case 384:
            this.initSponge(832, 768);
            break;
         case 512:
            this.initSponge(576, 1024);
            break;
         default:
            throw new IllegalArgumentException("bitLength must be one of 128, 224, 256, 288, 384, or 512.");
      }

   }

   private void initSponge(int var1, int var2) {
      if (var1 + var2 != 1600) {
         throw new IllegalStateException("rate + capacity != 1600");
      } else if (var1 > 0 && var1 < 1600 && var1 % 64 == 0) {
         this.rate = var1;
         Arrays.fill((byte[])this.state, (byte)0);
         Arrays.fill((byte[])this.dataQueue, (byte)0);
         this.bitsInQueue = 0;
         this.squeezing = false;
         this.bitsAvailableForSqueezing = 0;
         this.fixedOutputLength = var2 / 2;
         this.chunk = new byte[var1 / 8];
         this.oneByte = new byte[1];
      } else {
         throw new IllegalStateException("invalid rate value");
      }
   }

   private void absorbQueue() {
      this.KeccakAbsorb(this.state, this.dataQueue, this.rate / 8);
      this.bitsInQueue = 0;
   }

   protected void absorb(byte[] var1, int var2, long var3) {
      if (this.bitsInQueue % 8 != 0) {
         throw new IllegalStateException("attempt to absorb with odd length queue");
      } else if (this.squeezing) {
         throw new IllegalStateException("attempt to absorb while squeezing");
      } else {
         long var5 = 0L;

         while(true) {
            while(var5 < var3) {
               if (this.bitsInQueue == 0 && var3 >= (long)this.rate && var5 <= var3 - (long)this.rate) {
                  long var7 = (var3 - var5) / (long)this.rate;

                  for(long var9 = 0L; var9 < var7; ++var9) {
                     System.arraycopy(var1, (int)((long)var2 + var5 / 8L + var9 * (long)this.chunk.length), this.chunk, 0, this.chunk.length);
                     this.KeccakAbsorb(this.state, this.chunk, this.chunk.length);
                  }

                  var5 += var7 * (long)this.rate;
               } else {
                  int var11 = (int)(var3 - var5);
                  if (var11 + this.bitsInQueue > this.rate) {
                     var11 = this.rate - this.bitsInQueue;
                  }

                  int var12 = var11 % 8;
                  var11 -= var12;
                  System.arraycopy(var1, var2 + (int)(var5 / 8L), this.dataQueue, this.bitsInQueue / 8, var11 / 8);
                  this.bitsInQueue += var11;
                  var5 += (long)var11;
                  if (this.bitsInQueue == this.rate) {
                     this.absorbQueue();
                  }

                  if (var12 > 0) {
                     int var13 = (1 << var12) - 1;
                     this.dataQueue[this.bitsInQueue / 8] = (byte)(var1[var2 + (int)(var5 / 8L)] & var13);
                     this.bitsInQueue += var12;
                     var5 += (long)var12;
                  }
               }
            }

            return;
         }
      }
   }

   private void padAndSwitchToSqueezingPhase() {
      byte[] var10000;
      int var10001;
      if (this.bitsInQueue + 1 == this.rate) {
         var10000 = this.dataQueue;
         var10001 = this.bitsInQueue / 8;
         var10000[var10001] = (byte)(var10000[var10001] | 1 << this.bitsInQueue % 8);
         this.absorbQueue();
         this.clearDataQueueSection(0, this.rate / 8);
      } else {
         this.clearDataQueueSection((this.bitsInQueue + 7) / 8, this.rate / 8 - (this.bitsInQueue + 7) / 8);
         var10000 = this.dataQueue;
         var10001 = this.bitsInQueue / 8;
         var10000[var10001] = (byte)(var10000[var10001] | 1 << this.bitsInQueue % 8);
      }

      var10000 = this.dataQueue;
      var10001 = (this.rate - 1) / 8;
      var10000[var10001] = (byte)(var10000[var10001] | 1 << (this.rate - 1) % 8);
      this.absorbQueue();
      if (this.rate == 1024) {
         this.KeccakExtract1024bits(this.state, this.dataQueue);
         this.bitsAvailableForSqueezing = 1024;
      } else {
         this.KeccakExtract(this.state, this.dataQueue, this.rate / 64);
         this.bitsAvailableForSqueezing = this.rate;
      }

      this.squeezing = true;
   }

   protected void squeeze(byte[] var1, int var2, long var3) {
      if (!this.squeezing) {
         this.padAndSwitchToSqueezingPhase();
      }

      if (var3 % 8L != 0L) {
         throw new IllegalStateException("outputLength not a multiple of 8");
      } else {
         int var7;
         for(long var5 = 0L; var5 < var3; var5 += (long)var7) {
            if (this.bitsAvailableForSqueezing == 0) {
               this.keccakPermutation(this.state);
               if (this.rate == 1024) {
                  this.KeccakExtract1024bits(this.state, this.dataQueue);
                  this.bitsAvailableForSqueezing = 1024;
               } else {
                  this.KeccakExtract(this.state, this.dataQueue, this.rate / 64);
                  this.bitsAvailableForSqueezing = this.rate;
               }
            }

            var7 = this.bitsAvailableForSqueezing;
            if ((long)var7 > var3 - var5) {
               var7 = (int)(var3 - var5);
            }

            System.arraycopy(this.dataQueue, (this.rate - this.bitsAvailableForSqueezing) / 8, var1, var2 + (int)(var5 / 8L), var7 / 8);
            this.bitsAvailableForSqueezing -= var7;
         }

      }
   }

   private void fromBytesToWords(long[] var1, byte[] var2) {
      for(int var3 = 0; var3 < 25; ++var3) {
         var1[var3] = 0L;
         int var4 = var3 * 8;

         for(int var5 = 0; var5 < 8; ++var5) {
            var1[var3] |= ((long)var2[var4 + var5] & 255L) << 8 * var5;
         }
      }

   }

   private void fromWordsToBytes(byte[] var1, long[] var2) {
      for(int var3 = 0; var3 < 25; ++var3) {
         int var4 = var3 * 8;

         for(int var5 = 0; var5 < 8; ++var5) {
            var1[var4 + var5] = (byte)((int)(var2[var3] >>> 8 * var5 & 255L));
         }
      }

   }

   private void keccakPermutation(byte[] var1) {
      long[] var2 = new long[var1.length / 8];
      this.fromBytesToWords(var2, var1);
      this.keccakPermutationOnWords(var2);
      this.fromWordsToBytes(var1, var2);
   }

   private void keccakPermutationAfterXor(byte[] var1, byte[] var2, int var3) {
      for(int var4 = 0; var4 < var3; ++var4) {
         var1[var4] ^= var2[var4];
      }

      this.keccakPermutation(var1);
   }

   private void keccakPermutationOnWords(long[] var1) {
      for(int var2 = 0; var2 < 24; ++var2) {
         this.theta(var1);
         this.rho(var1);
         this.pi(var1);
         this.chi(var1);
         this.iota(var1, var2);
      }

   }

   private void theta(long[] var1) {
      int var2;
      for(var2 = 0; var2 < 5; ++var2) {
         this.C[var2] = 0L;

         for(int var3 = 0; var3 < 5; ++var3) {
            long[] var10000 = this.C;
            var10000[var2] ^= var1[var2 + 5 * var3];
         }
      }

      for(var2 = 0; var2 < 5; ++var2) {
         long var4 = this.C[(var2 + 1) % 5] << 1 ^ this.C[(var2 + 1) % 5] >>> 63 ^ this.C[(var2 + 4) % 5];

         for(int var6 = 0; var6 < 5; ++var6) {
            var1[var2 + 5 * var6] ^= var4;
         }
      }

   }

   private void rho(long[] var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         for(int var3 = 0; var3 < 5; ++var3) {
            int var4 = var2 + 5 * var3;
            var1[var4] = KeccakRhoOffsets[var4] != 0 ? var1[var4] << KeccakRhoOffsets[var4] ^ var1[var4] >>> 64 - KeccakRhoOffsets[var4] : var1[var4];
         }
      }

   }

   private void pi(long[] var1) {
      System.arraycopy(var1, 0, this.tempA, 0, this.tempA.length);

      for(int var2 = 0; var2 < 5; ++var2) {
         for(int var3 = 0; var3 < 5; ++var3) {
            var1[var3 + 5 * ((2 * var2 + 3 * var3) % 5)] = this.tempA[var2 + 5 * var3];
         }
      }

   }

   private void chi(long[] var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         int var3;
         for(var3 = 0; var3 < 5; ++var3) {
            this.chiC[var3] = var1[var3 + 5 * var2] ^ ~var1[(var3 + 1) % 5 + 5 * var2] & var1[(var3 + 2) % 5 + 5 * var2];
         }

         for(var3 = 0; var3 < 5; ++var3) {
            var1[var3 + 5 * var2] = this.chiC[var3];
         }
      }

   }

   private void iota(long[] var1, int var2) {
      var1[0] ^= KeccakRoundConstants[var2];
   }

   private void KeccakAbsorb(byte[] var1, byte[] var2, int var3) {
      this.keccakPermutationAfterXor(var1, var2, var3);
   }

   private void KeccakExtract1024bits(byte[] var1, byte[] var2) {
      System.arraycopy(var1, 0, var2, 0, 128);
   }

   private void KeccakExtract(byte[] var1, byte[] var2, int var3) {
      System.arraycopy(var1, 0, var2, 0, var3 * 8);
   }
}
