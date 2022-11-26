package org.cryptacular.adapter;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.cryptacular.CryptoException;

public class BufferedBlockCipherAdapter implements BlockCipherAdapter {
   private final BufferedBlockCipher cipherDelegate;

   public BufferedBlockCipherAdapter(BufferedBlockCipher delegate) {
      this.cipherDelegate = delegate;
   }

   public int getOutputSize(int len) {
      return this.cipherDelegate.getOutputSize(len);
   }

   public void init(boolean forEncryption, CipherParameters params) throws CryptoException {
      try {
         this.cipherDelegate.init(forEncryption, params);
      } catch (RuntimeException var4) {
         throw new CryptoException("Cipher initialization error", var4);
      }
   }

   public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff) throws CryptoException {
      try {
         return this.cipherDelegate.processBytes(in, inOff, len, out, outOff);
      } catch (RuntimeException var7) {
         throw new CryptoException("Cipher processing error", var7);
      }
   }

   public int doFinal(byte[] out, int outOff) throws CryptoException {
      try {
         return this.cipherDelegate.doFinal(out, outOff);
      } catch (InvalidCipherTextException var4) {
         throw new CryptoException("Error finalizing cipher", var4);
      }
   }

   public void reset() {
      this.cipherDelegate.reset();
   }
}
