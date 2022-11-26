package org.python.bouncycastle.crypto.modes;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.StreamBlockCipher;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.util.Arrays;

public class CFBBlockCipher extends StreamBlockCipher {
   private byte[] IV;
   private byte[] cfbV;
   private byte[] cfbOutV;
   private byte[] inBuf;
   private int blockSize;
   private BlockCipher cipher = null;
   private boolean encrypting;
   private int byteCount;

   public CFBBlockCipher(BlockCipher var1, int var2) {
      super(var1);
      this.cipher = var1;
      this.blockSize = var2 / 8;
      this.IV = new byte[var1.getBlockSize()];
      this.cfbV = new byte[var1.getBlockSize()];
      this.cfbOutV = new byte[var1.getBlockSize()];
      this.inBuf = new byte[this.blockSize];
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.encrypting = var1;
      if (var2 instanceof ParametersWithIV) {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         if (var4.length < this.IV.length) {
            System.arraycopy(var4, 0, this.IV, this.IV.length - var4.length, var4.length);

            for(int var5 = 0; var5 < this.IV.length - var4.length; ++var5) {
               this.IV[var5] = 0;
            }
         } else {
            System.arraycopy(var4, 0, this.IV, 0, this.IV.length);
         }

         this.reset();
         if (var3.getParameters() != null) {
            this.cipher.init(true, var3.getParameters());
         }
      } else {
         this.reset();
         if (var2 != null) {
            this.cipher.init(true, var2);
         }
      }

   }

   public String getAlgorithmName() {
      return this.cipher.getAlgorithmName() + "/CFB" + this.blockSize * 8;
   }

   protected byte calculateByte(byte var1) throws DataLengthException, IllegalStateException {
      return this.encrypting ? this.encryptByte(var1) : this.decryptByte(var1);
   }

   private byte encryptByte(byte var1) {
      if (this.byteCount == 0) {
         this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
      }

      byte var2 = (byte)(this.cfbOutV[this.byteCount] ^ var1);
      this.inBuf[this.byteCount++] = var2;
      if (this.byteCount == this.blockSize) {
         this.byteCount = 0;
         System.arraycopy(this.cfbV, this.blockSize, this.cfbV, 0, this.cfbV.length - this.blockSize);
         System.arraycopy(this.inBuf, 0, this.cfbV, this.cfbV.length - this.blockSize, this.blockSize);
      }

      return var2;
   }

   private byte decryptByte(byte var1) {
      if (this.byteCount == 0) {
         this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
      }

      this.inBuf[this.byteCount] = var1;
      byte var2 = (byte)(this.cfbOutV[this.byteCount++] ^ var1);
      if (this.byteCount == this.blockSize) {
         this.byteCount = 0;
         System.arraycopy(this.cfbV, this.blockSize, this.cfbV, 0, this.cfbV.length - this.blockSize);
         System.arraycopy(this.inBuf, 0, this.cfbV, this.cfbV.length - this.blockSize, this.blockSize);
      }

      return var2;
   }

   public int getBlockSize() {
      return this.blockSize;
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      this.processBytes(var1, var2, this.blockSize, var3, var4);
      return this.blockSize;
   }

   public int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      this.processBytes(var1, var2, this.blockSize, var3, var4);
      return this.blockSize;
   }

   public int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      this.processBytes(var1, var2, this.blockSize, var3, var4);
      return this.blockSize;
   }

   public byte[] getCurrentIV() {
      return Arrays.clone(this.cfbV);
   }

   public void reset() {
      System.arraycopy(this.IV, 0, this.cfbV, 0, this.IV.length);
      Arrays.fill((byte[])this.inBuf, (byte)0);
      this.byteCount = 0;
      this.cipher.reset();
   }
}
