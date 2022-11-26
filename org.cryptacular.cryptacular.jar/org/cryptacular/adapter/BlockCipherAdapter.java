package org.cryptacular.adapter;

import org.cryptacular.CryptoException;

public interface BlockCipherAdapter extends CipherAdapter {
   int getOutputSize(int var1);

   int doFinal(byte[] var1, int var2) throws CryptoException;
}
