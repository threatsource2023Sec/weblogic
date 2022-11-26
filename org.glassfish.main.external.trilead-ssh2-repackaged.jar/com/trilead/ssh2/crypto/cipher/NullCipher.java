package com.trilead.ssh2.crypto.cipher;

public class NullCipher implements BlockCipher {
   private int blockSize = 8;

   public NullCipher() {
   }

   public NullCipher(int blockSize) {
      this.blockSize = blockSize;
   }

   public void init(boolean forEncryption, byte[] key) {
   }

   public int getBlockSize() {
      return this.blockSize;
   }

   public void transformBlock(byte[] src, int srcoff, byte[] dst, int dstoff) {
      System.arraycopy(src, srcoff, dst, dstoff, this.blockSize);
   }
}
