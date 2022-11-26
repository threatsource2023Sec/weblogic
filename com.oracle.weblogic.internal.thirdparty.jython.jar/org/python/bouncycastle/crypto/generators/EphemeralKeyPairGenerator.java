package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.EphemeralKeyPair;
import org.python.bouncycastle.crypto.KeyEncoder;

public class EphemeralKeyPairGenerator {
   private AsymmetricCipherKeyPairGenerator gen;
   private KeyEncoder keyEncoder;

   public EphemeralKeyPairGenerator(AsymmetricCipherKeyPairGenerator var1, KeyEncoder var2) {
      this.gen = var1;
      this.keyEncoder = var2;
   }

   public EphemeralKeyPair generate() {
      AsymmetricCipherKeyPair var1 = this.gen.generateKeyPair();
      return new EphemeralKeyPair(var1, this.keyEncoder);
   }
}
