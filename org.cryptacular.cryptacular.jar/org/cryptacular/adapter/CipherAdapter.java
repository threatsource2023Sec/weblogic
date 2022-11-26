package org.cryptacular.adapter;

import org.bouncycastle.crypto.CipherParameters;
import org.cryptacular.CryptoException;

public interface CipherAdapter {
   void init(boolean var1, CipherParameters var2) throws CryptoException;

   int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws CryptoException;

   void reset();
}
