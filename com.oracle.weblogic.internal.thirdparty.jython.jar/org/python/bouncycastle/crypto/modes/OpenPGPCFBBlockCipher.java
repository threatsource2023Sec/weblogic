package org.python.bouncycastle.crypto.modes;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;

public class OpenPGPCFBBlockCipher implements BlockCipher {
   private byte[] IV;
   private byte[] FR;
   private byte[] FRE;
   private BlockCipher cipher;
   private int count;
   private int blockSize;
   private boolean forEncryption;

   public OpenPGPCFBBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      this.blockSize = var1.getBlockSize();
      this.IV = new byte[this.blockSize];
      this.FR = new byte[this.blockSize];
      this.FRE = new byte[this.blockSize];
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public String getAlgorithmName() {
      return this.cipher.getAlgorithmName() + "/OpenPGPCFB";
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      return this.forEncryption ? this.encryptBlock(var1, var2, var3, var4) : this.decryptBlock(var1, var2, var3, var4);
   }

   public void reset() {
      this.count = 0;
      System.arraycopy(this.IV, 0, this.FR, 0, this.FR.length);
      this.cipher.reset();
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      this.reset();
      this.cipher.init(true, var2);
   }

   private byte encryptByte(byte var1, int var2) {
      return (byte)(this.FRE[var2] ^ var1);
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      if (var2 + this.blockSize > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var4 + this.blockSize > var3.length) {
         throw new DataLengthException("output buffer too short");
      } else {
         int var5;
         if (this.count > this.blockSize) {
            this.FR[this.blockSize - 2] = var3[var4] = this.encryptByte(var1[var2], this.blockSize - 2);
            this.FR[this.blockSize - 1] = var3[var4 + 1] = this.encryptByte(var1[var2 + 1], this.blockSize - 1);
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);

            for(var5 = 2; var5 < this.blockSize; ++var5) {
               this.FR[var5 - 2] = var3[var4 + var5] = this.encryptByte(var1[var2 + var5], var5 - 2);
            }
         } else if (this.count == 0) {
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);

            for(var5 = 0; var5 < this.blockSize; ++var5) {
               this.FR[var5] = var3[var4 + var5] = this.encryptByte(var1[var2 + var5], var5);
            }

            this.count += this.blockSize;
         } else if (this.count == this.blockSize) {
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);
            var3[var4] = this.encryptByte(var1[var2], 0);
            var3[var4 + 1] = this.encryptByte(var1[var2 + 1], 1);
            System.arraycopy(this.FR, 2, this.FR, 0, this.blockSize - 2);
            System.arraycopy(var3, var4, this.FR, this.blockSize - 2, 2);
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);

            for(var5 = 2; var5 < this.blockSize; ++var5) {
               this.FR[var5 - 2] = var3[var4 + var5] = this.encryptByte(var1[var2 + var5], var5 - 2);
            }

            this.count += this.blockSize;
         }

         return this.blockSize;
      }
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      if (var2 + this.blockSize > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var4 + this.blockSize > var3.length) {
         throw new DataLengthException("output buffer too short");
      } else {
         int var5;
         if (this.count > this.blockSize) {
            var5 = var1[var2];
            this.FR[this.blockSize - 2] = (byte)var5;
            var3[var4] = this.encryptByte((byte)var5, this.blockSize - 2);
            var5 = var1[var2 + 1];
            this.FR[this.blockSize - 1] = (byte)var5;
            var3[var4 + 1] = this.encryptByte((byte)var5, this.blockSize - 1);
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);

            for(int var6 = 2; var6 < this.blockSize; ++var6) {
               var5 = var1[var2 + var6];
               this.FR[var6 - 2] = (byte)var5;
               var3[var4 + var6] = this.encryptByte((byte)var5, var6 - 2);
            }
         } else if (this.count == 0) {
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);

            for(var5 = 0; var5 < this.blockSize; ++var5) {
               this.FR[var5] = var1[var2 + var5];
               var3[var5] = this.encryptByte(var1[var2 + var5], var5);
            }

            this.count += this.blockSize;
         } else if (this.count == this.blockSize) {
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);
            byte var9 = var1[var2];
            byte var10 = var1[var2 + 1];
            var3[var4] = this.encryptByte(var9, 0);
            var3[var4 + 1] = this.encryptByte(var10, 1);
            System.arraycopy(this.FR, 2, this.FR, 0, this.blockSize - 2);
            this.FR[this.blockSize - 2] = var9;
            this.FR[this.blockSize - 1] = var10;
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);

            for(int var7 = 2; var7 < this.blockSize; ++var7) {
               byte var8 = var1[var2 + var7];
               this.FR[var7 - 2] = var8;
               var3[var4 + var7] = this.encryptByte(var8, var7 - 2);
            }

            this.count += this.blockSize;
         }

         return this.blockSize;
      }
   }
}
