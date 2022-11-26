package org.python.bouncycastle.pqc.crypto.newhope;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;

public class NHKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private SecureRandom random;

   public void init(KeyGenerationParameters var1) {
      this.random = var1.getRandom();
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      byte[] var1 = new byte[1824];
      short[] var2 = new short[1024];
      NewHope.keygen(this.random, var1, var2);
      return new AsymmetricCipherKeyPair(new NHPublicKeyParameters(var1), new NHPrivateKeyParameters(var2));
   }
}
