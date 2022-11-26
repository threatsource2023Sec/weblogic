package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class HC256Engine implements StreamCipher {
   private int[] p = new int[1024];
   private int[] q = new int[1024];
   private int cnt = 0;
   private byte[] key;
   private byte[] iv;
   private boolean initialised;
   private byte[] buf = new byte[4];
   private int idx = 0;

   private int step() {
      int var1 = this.cnt & 1023;
      int[] var10000;
      int var2;
      int var3;
      int var4;
      if (this.cnt < 1024) {
         var2 = this.p[var1 - 3 & 1023];
         var3 = this.p[var1 - 1023 & 1023];
         var10000 = this.p;
         var10000[var1] += this.p[var1 - 10 & 1023] + (rotateRight(var2, 10) ^ rotateRight(var3, 23)) + this.q[(var2 ^ var3) & 1023];
         var2 = this.p[var1 - 12 & 1023];
         var4 = this.q[var2 & 255] + this.q[(var2 >> 8 & 255) + 256] + this.q[(var2 >> 16 & 255) + 512] + this.q[(var2 >> 24 & 255) + 768] ^ this.p[var1];
      } else {
         var2 = this.q[var1 - 3 & 1023];
         var3 = this.q[var1 - 1023 & 1023];
         var10000 = this.q;
         var10000[var1] += this.q[var1 - 10 & 1023] + (rotateRight(var2, 10) ^ rotateRight(var3, 23)) + this.p[(var2 ^ var3) & 1023];
         var2 = this.q[var1 - 12 & 1023];
         var4 = this.p[var2 & 255] + this.p[(var2 >> 8 & 255) + 256] + this.p[(var2 >> 16 & 255) + 512] + this.p[(var2 >> 24 & 255) + 768] ^ this.q[var1];
      }

      this.cnt = this.cnt + 1 & 2047;
      return var4;
   }

   private void init() {
      if (this.key.length != 32 && this.key.length != 16) {
         throw new IllegalArgumentException("The key must be 128/256 bits long");
      } else if (this.iv.length < 16) {
         throw new IllegalArgumentException("The IV must be at least 128 bits long");
      } else {
         byte[] var1;
         if (this.key.length != 32) {
            var1 = new byte[32];
            System.arraycopy(this.key, 0, var1, 0, this.key.length);
            System.arraycopy(this.key, 0, var1, 16, this.key.length);
            this.key = var1;
         }

         if (this.iv.length < 32) {
            var1 = new byte[32];
            System.arraycopy(this.iv, 0, var1, 0, this.iv.length);
            System.arraycopy(this.iv, 0, var1, this.iv.length, var1.length - this.iv.length);
            this.iv = var1;
         }

         this.idx = 0;
         this.cnt = 0;
         int[] var5 = new int[2560];

         int var2;
         for(var2 = 0; var2 < 32; ++var2) {
            var5[var2 >> 2] |= (this.key[var2] & 255) << 8 * (var2 & 3);
         }

         for(var2 = 0; var2 < 32; ++var2) {
            var5[(var2 >> 2) + 8] |= (this.iv[var2] & 255) << 8 * (var2 & 3);
         }

         for(var2 = 16; var2 < 2560; ++var2) {
            int var3 = var5[var2 - 2];
            int var4 = var5[var2 - 15];
            var5[var2] = (rotateRight(var3, 17) ^ rotateRight(var3, 19) ^ var3 >>> 10) + var5[var2 - 7] + (rotateRight(var4, 7) ^ rotateRight(var4, 18) ^ var4 >>> 3) + var5[var2 - 16] + var2;
         }

         System.arraycopy(var5, 512, this.p, 0, 1024);
         System.arraycopy(var5, 1536, this.q, 0, 1024);

         for(var2 = 0; var2 < 4096; ++var2) {
            this.step();
         }

         this.cnt = 0;
      }
   }

   public String getAlgorithmName() {
      return "HC-256";
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      CipherParameters var3 = var2;
      if (var2 instanceof ParametersWithIV) {
         this.iv = ((ParametersWithIV)var2).getIV();
         var3 = ((ParametersWithIV)var2).getParameters();
      } else {
         this.iv = new byte[0];
      }

      if (var3 instanceof KeyParameter) {
         this.key = ((KeyParameter)var3).getKey();
         this.init();
         this.initialised = true;
      } else {
         throw new IllegalArgumentException("Invalid parameter passed to HC256 init - " + var2.getClass().getName());
      }
   }

   private byte getByte() {
      if (this.idx == 0) {
         int var1 = this.step();
         this.buf[0] = (byte)(var1 & 255);
         var1 >>= 8;
         this.buf[1] = (byte)(var1 & 255);
         var1 >>= 8;
         this.buf[2] = (byte)(var1 & 255);
         var1 >>= 8;
         this.buf[3] = (byte)(var1 & 255);
      }

      byte var2 = this.buf[this.idx];
      this.idx = this.idx + 1 & 3;
      return var2;
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
      if (!this.initialised) {
         throw new IllegalStateException(this.getAlgorithmName() + " not initialised");
      } else if (var2 + var3 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var5 + var3 > var4.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         for(int var6 = 0; var6 < var3; ++var6) {
            var4[var5 + var6] = (byte)(var1[var2 + var6] ^ this.getByte());
         }

         return var3;
      }
   }

   public void reset() {
      this.init();
   }

   public byte returnByte(byte var1) {
      return (byte)(var1 ^ this.getByte());
   }

   private static int rotateRight(int var0, int var1) {
      return var0 >>> var1 | var0 << -var1;
   }
}
