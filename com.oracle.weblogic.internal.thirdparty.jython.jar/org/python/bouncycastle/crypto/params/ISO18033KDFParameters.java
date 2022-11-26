package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.DerivationParameters;

public class ISO18033KDFParameters implements DerivationParameters {
   byte[] seed;

   public ISO18033KDFParameters(byte[] var1) {
      this.seed = var1;
   }

   public byte[] getSeed() {
      return this.seed;
   }
}
