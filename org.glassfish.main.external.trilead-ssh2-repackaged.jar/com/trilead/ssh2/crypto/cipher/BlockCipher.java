package com.trilead.ssh2.crypto.cipher;

public interface BlockCipher {
   void init(boolean var1, byte[] var2);

   int getBlockSize();

   void transformBlock(byte[] var1, int var2, byte[] var3, int var4);
}
