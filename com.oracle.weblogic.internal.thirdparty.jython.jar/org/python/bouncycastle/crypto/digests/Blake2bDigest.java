package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.util.Arrays;

public class Blake2bDigest implements ExtendedDigest {
   private static final long[] blake2b_IV = new long[]{7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L, 5840696475078001361L, -7276294671716946913L, 2270897969802886507L, 6620516959819538809L};
   private static final byte[][] blake2b_sigma = new byte[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3}, {11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4}, {7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8}, {9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13}, {2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9}, {12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11}, {13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10}, {6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5}, {10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0}, {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3}};
   private static int rOUNDS = 12;
   private static final int BLOCK_LENGTH_BYTES = 128;
   private int digestLength;
   private int keyLength;
   private byte[] salt;
   private byte[] personalization;
   private byte[] key;
   private byte[] buffer;
   private int bufferPos;
   private long[] internalState;
   private long[] chainValue;
   private long t0;
   private long t1;
   private long f0;

   public Blake2bDigest() {
      this(512);
   }

   public Blake2bDigest(Blake2bDigest var1) {
      this.digestLength = 64;
      this.keyLength = 0;
      this.salt = null;
      this.personalization = null;
      this.key = null;
      this.buffer = null;
      this.bufferPos = 0;
      this.internalState = new long[16];
      this.chainValue = null;
      this.t0 = 0L;
      this.t1 = 0L;
      this.f0 = 0L;
      this.bufferPos = var1.bufferPos;
      this.buffer = Arrays.clone(var1.buffer);
      this.keyLength = var1.keyLength;
      this.key = Arrays.clone(var1.key);
      this.digestLength = var1.digestLength;
      this.chainValue = Arrays.clone(var1.chainValue);
      this.personalization = Arrays.clone(var1.personalization);
      this.salt = Arrays.clone(var1.salt);
      this.t0 = var1.t0;
      this.t1 = var1.t1;
      this.f0 = var1.f0;
   }

   public Blake2bDigest(int var1) {
      this.digestLength = 64;
      this.keyLength = 0;
      this.salt = null;
      this.personalization = null;
      this.key = null;
      this.buffer = null;
      this.bufferPos = 0;
      this.internalState = new long[16];
      this.chainValue = null;
      this.t0 = 0L;
      this.t1 = 0L;
      this.f0 = 0L;
      if (var1 != 160 && var1 != 256 && var1 != 384 && var1 != 512) {
         throw new IllegalArgumentException("Blake2b digest restricted to one of [160, 256, 384, 512]");
      } else {
         this.buffer = new byte[128];
         this.keyLength = 0;
         this.digestLength = var1 / 8;
         this.init();
      }
   }

   public Blake2bDigest(byte[] var1) {
      this.digestLength = 64;
      this.keyLength = 0;
      this.salt = null;
      this.personalization = null;
      this.key = null;
      this.buffer = null;
      this.bufferPos = 0;
      this.internalState = new long[16];
      this.chainValue = null;
      this.t0 = 0L;
      this.t1 = 0L;
      this.f0 = 0L;
      this.buffer = new byte[128];
      if (var1 != null) {
         this.key = new byte[var1.length];
         System.arraycopy(var1, 0, this.key, 0, var1.length);
         if (var1.length > 64) {
            throw new IllegalArgumentException("Keys > 64 are not supported");
         }

         this.keyLength = var1.length;
         System.arraycopy(var1, 0, this.buffer, 0, var1.length);
         this.bufferPos = 128;
      }

      this.digestLength = 64;
      this.init();
   }

   public Blake2bDigest(byte[] var1, int var2, byte[] var3, byte[] var4) {
      this.digestLength = 64;
      this.keyLength = 0;
      this.salt = null;
      this.personalization = null;
      this.key = null;
      this.buffer = null;
      this.bufferPos = 0;
      this.internalState = new long[16];
      this.chainValue = null;
      this.t0 = 0L;
      this.t1 = 0L;
      this.f0 = 0L;
      this.buffer = new byte[128];
      if (var2 >= 1 && var2 <= 64) {
         this.digestLength = var2;
         if (var3 != null) {
            if (var3.length != 16) {
               throw new IllegalArgumentException("salt length must be exactly 16 bytes");
            }

            this.salt = new byte[16];
            System.arraycopy(var3, 0, this.salt, 0, var3.length);
         }

         if (var4 != null) {
            if (var4.length != 16) {
               throw new IllegalArgumentException("personalization length must be exactly 16 bytes");
            }

            this.personalization = new byte[16];
            System.arraycopy(var4, 0, this.personalization, 0, var4.length);
         }

         if (var1 != null) {
            this.key = new byte[var1.length];
            System.arraycopy(var1, 0, this.key, 0, var1.length);
            if (var1.length > 64) {
               throw new IllegalArgumentException("Keys > 64 are not supported");
            }

            this.keyLength = var1.length;
            System.arraycopy(var1, 0, this.buffer, 0, var1.length);
            this.bufferPos = 128;
         }

         this.init();
      } else {
         throw new IllegalArgumentException("Invalid digest length (required: 1 - 64)");
      }
   }

   private void init() {
      if (this.chainValue == null) {
         this.chainValue = new long[8];
         this.chainValue[0] = blake2b_IV[0] ^ (long)(this.digestLength | this.keyLength << 8 | 16842752);
         this.chainValue[1] = blake2b_IV[1];
         this.chainValue[2] = blake2b_IV[2];
         this.chainValue[3] = blake2b_IV[3];
         this.chainValue[4] = blake2b_IV[4];
         this.chainValue[5] = blake2b_IV[5];
         long[] var10000;
         if (this.salt != null) {
            var10000 = this.chainValue;
            var10000[4] ^= this.bytes2long(this.salt, 0);
            var10000 = this.chainValue;
            var10000[5] ^= this.bytes2long(this.salt, 8);
         }

         this.chainValue[6] = blake2b_IV[6];
         this.chainValue[7] = blake2b_IV[7];
         if (this.personalization != null) {
            var10000 = this.chainValue;
            var10000[6] ^= this.bytes2long(this.personalization, 0);
            var10000 = this.chainValue;
            var10000[7] ^= this.bytes2long(this.personalization, 8);
         }
      }

   }

   private void initializeInternalState() {
      System.arraycopy(this.chainValue, 0, this.internalState, 0, this.chainValue.length);
      System.arraycopy(blake2b_IV, 0, this.internalState, this.chainValue.length, 4);
      this.internalState[12] = this.t0 ^ blake2b_IV[4];
      this.internalState[13] = this.t1 ^ blake2b_IV[5];
      this.internalState[14] = this.f0 ^ blake2b_IV[6];
      this.internalState[15] = blake2b_IV[7];
   }

   public void update(byte var1) {
      boolean var2 = false;
      int var3 = 128 - this.bufferPos;
      if (var3 == 0) {
         this.t0 += 128L;
         if (this.t0 == 0L) {
            ++this.t1;
         }

         this.compress(this.buffer, 0);
         Arrays.fill((byte[])this.buffer, (byte)0);
         this.buffer[0] = var1;
         this.bufferPos = 1;
      } else {
         this.buffer[this.bufferPos] = var1;
         ++this.bufferPos;
      }
   }

   public void update(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         int var4 = 0;
         if (this.bufferPos != 0) {
            var4 = 128 - this.bufferPos;
            if (var4 >= var3) {
               System.arraycopy(var1, var2, this.buffer, this.bufferPos, var3);
               this.bufferPos += var3;
               return;
            }

            System.arraycopy(var1, var2, this.buffer, this.bufferPos, var4);
            this.t0 += 128L;
            if (this.t0 == 0L) {
               ++this.t1;
            }

            this.compress(this.buffer, 0);
            this.bufferPos = 0;
            Arrays.fill((byte[])this.buffer, (byte)0);
         }

         int var5 = var2 + var3 - 128;

         int var6;
         for(var6 = var2 + var4; var6 < var5; var6 += 128) {
            this.t0 += 128L;
            if (this.t0 == 0L) {
               ++this.t1;
            }

            this.compress(var1, var6);
         }

         System.arraycopy(var1, var6, this.buffer, 0, var2 + var3 - var6);
         this.bufferPos += var2 + var3 - var6;
      }
   }

   public int doFinal(byte[] var1, int var2) {
      this.f0 = -1L;
      this.t0 += (long)this.bufferPos;
      if (this.t0 < 0L && (long)this.bufferPos > -this.t0) {
         ++this.t1;
      }

      this.compress(this.buffer, 0);
      Arrays.fill((byte[])this.buffer, (byte)0);
      Arrays.fill(this.internalState, 0L);

      for(int var3 = 0; var3 < this.chainValue.length && var3 * 8 < this.digestLength; ++var3) {
         byte[] var4 = this.long2bytes(this.chainValue[var3]);
         if (var3 * 8 < this.digestLength - 8) {
            System.arraycopy(var4, 0, var1, var2 + var3 * 8, 8);
         } else {
            System.arraycopy(var4, 0, var1, var2 + var3 * 8, this.digestLength - var3 * 8);
         }
      }

      Arrays.fill(this.chainValue, 0L);
      this.reset();
      return this.digestLength;
   }

   public void reset() {
      this.bufferPos = 0;
      this.f0 = 0L;
      this.t0 = 0L;
      this.t1 = 0L;
      this.chainValue = null;
      Arrays.fill((byte[])this.buffer, (byte)0);
      if (this.key != null) {
         System.arraycopy(this.key, 0, this.buffer, 0, this.key.length);
         this.bufferPos = 128;
      }

      this.init();
   }

   private void compress(byte[] var1, int var2) {
      this.initializeInternalState();
      long[] var3 = new long[16];

      int var4;
      for(var4 = 0; var4 < 16; ++var4) {
         var3[var4] = this.bytes2long(var1, var2 + var4 * 8);
      }

      for(var4 = 0; var4 < rOUNDS; ++var4) {
         this.G(var3[blake2b_sigma[var4][0]], var3[blake2b_sigma[var4][1]], 0, 4, 8, 12);
         this.G(var3[blake2b_sigma[var4][2]], var3[blake2b_sigma[var4][3]], 1, 5, 9, 13);
         this.G(var3[blake2b_sigma[var4][4]], var3[blake2b_sigma[var4][5]], 2, 6, 10, 14);
         this.G(var3[blake2b_sigma[var4][6]], var3[blake2b_sigma[var4][7]], 3, 7, 11, 15);
         this.G(var3[blake2b_sigma[var4][8]], var3[blake2b_sigma[var4][9]], 0, 5, 10, 15);
         this.G(var3[blake2b_sigma[var4][10]], var3[blake2b_sigma[var4][11]], 1, 6, 11, 12);
         this.G(var3[blake2b_sigma[var4][12]], var3[blake2b_sigma[var4][13]], 2, 7, 8, 13);
         this.G(var3[blake2b_sigma[var4][14]], var3[blake2b_sigma[var4][15]], 3, 4, 9, 14);
      }

      for(var4 = 0; var4 < this.chainValue.length; ++var4) {
         this.chainValue[var4] = this.chainValue[var4] ^ this.internalState[var4] ^ this.internalState[var4 + 8];
      }

   }

   private void G(long var1, long var3, int var5, int var6, int var7, int var8) {
      this.internalState[var5] = this.internalState[var5] + this.internalState[var6] + var1;
      this.internalState[var8] = this.rotr64(this.internalState[var8] ^ this.internalState[var5], 32);
      this.internalState[var7] += this.internalState[var8];
      this.internalState[var6] = this.rotr64(this.internalState[var6] ^ this.internalState[var7], 24);
      this.internalState[var5] = this.internalState[var5] + this.internalState[var6] + var3;
      this.internalState[var8] = this.rotr64(this.internalState[var8] ^ this.internalState[var5], 16);
      this.internalState[var7] += this.internalState[var8];
      this.internalState[var6] = this.rotr64(this.internalState[var6] ^ this.internalState[var7], 63);
   }

   private long rotr64(long var1, int var3) {
      return var1 >>> var3 | var1 << 64 - var3;
   }

   private final byte[] long2bytes(long var1) {
      return new byte[]{(byte)((int)var1), (byte)((int)(var1 >> 8)), (byte)((int)(var1 >> 16)), (byte)((int)(var1 >> 24)), (byte)((int)(var1 >> 32)), (byte)((int)(var1 >> 40)), (byte)((int)(var1 >> 48)), (byte)((int)(var1 >> 56))};
   }

   private final long bytes2long(byte[] var1, int var2) {
      return (long)var1[var2] & 255L | ((long)var1[var2 + 1] & 255L) << 8 | ((long)var1[var2 + 2] & 255L) << 16 | ((long)var1[var2 + 3] & 255L) << 24 | ((long)var1[var2 + 4] & 255L) << 32 | ((long)var1[var2 + 5] & 255L) << 40 | ((long)var1[var2 + 6] & 255L) << 48 | ((long)var1[var2 + 7] & 255L) << 56;
   }

   public String getAlgorithmName() {
      return "Blake2b";
   }

   public int getDigestSize() {
      return this.digestLength;
   }

   public int getByteLength() {
      return 128;
   }

   public void clearKey() {
      if (this.key != null) {
         Arrays.fill((byte[])this.key, (byte)0);
         Arrays.fill((byte[])this.buffer, (byte)0);
      }

   }

   public void clearSalt() {
      if (this.salt != null) {
         Arrays.fill((byte[])this.salt, (byte)0);
      }

   }
}
