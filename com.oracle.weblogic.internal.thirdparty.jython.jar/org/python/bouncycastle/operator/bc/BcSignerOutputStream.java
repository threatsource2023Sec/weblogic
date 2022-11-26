package org.python.bouncycastle.operator.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Signer;

public class BcSignerOutputStream extends OutputStream {
   private Signer sig;

   BcSignerOutputStream(Signer var1) {
      this.sig = var1;
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.sig.update(var1, var2, var3);
   }

   public void write(byte[] var1) throws IOException {
      this.sig.update(var1, 0, var1.length);
   }

   public void write(int var1) throws IOException {
      this.sig.update((byte)var1);
   }

   byte[] getSignature() throws CryptoException {
      return this.sig.generateSignature();
   }

   boolean verify(byte[] var1) {
      return this.sig.verifySignature(var1);
   }
}
