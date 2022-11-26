package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithSBox;

public class GOST28147Mac implements Mac {
   private int blockSize = 8;
   private int macSize = 4;
   private int bufOff;
   private byte[] buf;
   private byte[] mac;
   private boolean firstStep = true;
   private int[] workingKey = null;
   private byte[] S = new byte[]{9, 6, 3, 2, 8, 11, 1, 7, 10, 4, 14, 15, 12, 0, 13, 5, 3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1, 14, 4, 6, 2, 11, 3, 13, 8, 12, 15, 5, 10, 0, 7, 1, 9, 14, 7, 10, 12, 13, 1, 3, 9, 0, 2, 11, 4, 15, 8, 5, 6, 11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6, 3, 10, 13, 12, 1, 2, 0, 11, 7, 5, 9, 4, 8, 15, 14, 6, 1, 13, 2, 9, 7, 10, 6, 0, 8, 12, 4, 5, 15, 3, 11, 14, 11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4};

   public GOST28147Mac() {
      this.mac = new byte[this.blockSize];
      this.buf = new byte[this.blockSize];
      this.bufOff = 0;
   }

   private int[] generateWorkingKey(byte[] var1) {
      if (var1.length != 32) {
         throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
      } else {
         int[] var2 = new int[8];

         for(int var3 = 0; var3 != 8; ++var3) {
            var2[var3] = this.bytesToint(var1, var3 * 4);
         }

         return var2;
      }
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      this.reset();
      this.buf = new byte[this.blockSize];
      if (var1 instanceof ParametersWithSBox) {
         ParametersWithSBox var2 = (ParametersWithSBox)var1;
         System.arraycopy(var2.getSBox(), 0, this.S, 0, var2.getSBox().length);
         if (var2.getParameters() != null) {
            this.workingKey = this.generateWorkingKey(((KeyParameter)var2.getParameters()).getKey());
         }
      } else {
         if (!(var1 instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + var1.getClass().getName());
         }

         this.workingKey = this.generateWorkingKey(((KeyParameter)var1).getKey());
      }

   }

   public String getAlgorithmName() {
      return "GOST28147Mac";
   }

   public int getMacSize() {
      return this.macSize;
   }

   private int gost28147_mainStep(int var1, int var2) {
      int var3 = var2 + var1;
      int var4 = this.S[0 + (var3 >> 0 & 15)] << 0;
      var4 += this.S[16 + (var3 >> 4 & 15)] << 4;
      var4 += this.S[32 + (var3 >> 8 & 15)] << 8;
      var4 += this.S[48 + (var3 >> 12 & 15)] << 12;
      var4 += this.S[64 + (var3 >> 16 & 15)] << 16;
      var4 += this.S[80 + (var3 >> 20 & 15)] << 20;
      var4 += this.S[96 + (var3 >> 24 & 15)] << 24;
      var4 += this.S[112 + (var3 >> 28 & 15)] << 28;
      return var4 << 11 | var4 >>> 21;
   }

   private void gost28147MacFunc(int[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      int var6 = this.bytesToint(var2, var3);
      int var7 = this.bytesToint(var2, var3 + 4);

      for(int var8 = 0; var8 < 2; ++var8) {
         for(int var9 = 0; var9 < 8; ++var9) {
            int var10 = var6;
            var6 = var7 ^ this.gost28147_mainStep(var6, var1[var9]);
            var7 = var10;
         }
      }

      this.intTobytes(var6, var4, var5);
      this.intTobytes(var7, var4, var5 + 4);
   }

   private int bytesToint(byte[] var1, int var2) {
      return (var1[var2 + 3] << 24 & -16777216) + (var1[var2 + 2] << 16 & 16711680) + (var1[var2 + 1] << 8 & '\uff00') + (var1[var2] & 255);
   }

   private void intTobytes(int var1, byte[] var2, int var3) {
      var2[var3 + 3] = (byte)(var1 >>> 24);
      var2[var3 + 2] = (byte)(var1 >>> 16);
      var2[var3 + 1] = (byte)(var1 >>> 8);
      var2[var3] = (byte)var1;
   }

   private byte[] CM5func(byte[] var1, int var2, byte[] var3) {
      byte[] var4 = new byte[var1.length - var2];
      System.arraycopy(var1, var2, var4, 0, var3.length);

      for(int var5 = 0; var5 != var3.length; ++var5) {
         var4[var5] ^= var3[var5];
      }

      return var4;
   }

   public void update(byte var1) throws IllegalStateException {
      if (this.bufOff == this.buf.length) {
         byte[] var2 = new byte[this.buf.length];
         System.arraycopy(this.buf, 0, var2, 0, this.mac.length);
         if (this.firstStep) {
            this.firstStep = false;
         } else {
            var2 = this.CM5func(this.buf, 0, this.mac);
         }

         this.gost28147MacFunc(this.workingKey, var2, 0, this.mac, 0);
         this.bufOff = 0;
      }

      this.buf[this.bufOff++] = var1;
   }

   public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException {
      if (var3 < 0) {
         throw new IllegalArgumentException("Can't have a negative input length!");
      } else {
         int var4 = this.blockSize - this.bufOff;
         if (var3 > var4) {
            System.arraycopy(var1, var2, this.buf, this.bufOff, var4);
            byte[] var5 = new byte[this.buf.length];
            System.arraycopy(this.buf, 0, var5, 0, this.mac.length);
            if (this.firstStep) {
               this.firstStep = false;
            } else {
               var5 = this.CM5func(this.buf, 0, this.mac);
            }

            this.gost28147MacFunc(this.workingKey, var5, 0, this.mac, 0);
            this.bufOff = 0;
            var3 -= var4;

            for(var2 += var4; var3 > this.blockSize; var2 += this.blockSize) {
               var5 = this.CM5func(var1, var2, this.mac);
               this.gost28147MacFunc(this.workingKey, var5, 0, this.mac, 0);
               var3 -= this.blockSize;
            }
         }

         System.arraycopy(var1, var2, this.buf, this.bufOff, var3);
         this.bufOff += var3;
      }
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException {
      while(this.bufOff < this.blockSize) {
         this.buf[this.bufOff] = 0;
         ++this.bufOff;
      }

      byte[] var3 = new byte[this.buf.length];
      System.arraycopy(this.buf, 0, var3, 0, this.mac.length);
      if (this.firstStep) {
         this.firstStep = false;
      } else {
         var3 = this.CM5func(this.buf, 0, this.mac);
      }

      this.gost28147MacFunc(this.workingKey, var3, 0, this.mac, 0);
      System.arraycopy(this.mac, this.mac.length / 2 - this.macSize, var1, var2, this.macSize);
      this.reset();
      return this.macSize;
   }

   public void reset() {
      for(int var1 = 0; var1 < this.buf.length; ++var1) {
         this.buf[var1] = 0;
      }

      this.bufOff = 0;
      this.firstStep = true;
   }
}
