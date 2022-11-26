package org.python.bouncycastle.pqc.crypto;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.util.Arrays;

public class ExchangePair {
   private final AsymmetricKeyParameter publicKey;
   private final byte[] shared;

   public ExchangePair(AsymmetricKeyParameter var1, byte[] var2) {
      this.publicKey = var1;
      this.shared = Arrays.clone(var2);
   }

   public AsymmetricKeyParameter getPublicKey() {
      return this.publicKey;
   }

   public byte[] getSharedValue() {
      return Arrays.clone(this.shared);
   }
}
