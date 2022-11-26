package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.DerivationParameters;

public class KDFParameters implements DerivationParameters {
   byte[] iv;
   byte[] shared;

   public KDFParameters(byte[] var1, byte[] var2) {
      this.shared = var1;
      this.iv = var2;
   }

   public byte[] getSharedSecret() {
      return this.shared;
   }

   public byte[] getIV() {
      return this.iv;
   }
}
