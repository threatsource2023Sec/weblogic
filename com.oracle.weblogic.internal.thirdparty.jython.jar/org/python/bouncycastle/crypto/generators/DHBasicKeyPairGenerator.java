package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;

public class DHBasicKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private DHKeyGenerationParameters param;

   public void init(KeyGenerationParameters var1) {
      this.param = (DHKeyGenerationParameters)var1;
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      DHKeyGeneratorHelper var1 = DHKeyGeneratorHelper.INSTANCE;
      DHParameters var2 = this.param.getParameters();
      BigInteger var3 = var1.calculatePrivate(var2, this.param.getRandom());
      BigInteger var4 = var1.calculatePublic(var2, var3);
      return new AsymmetricCipherKeyPair(new DHPublicKeyParameters(var4, var2), new DHPrivateKeyParameters(var3, var2));
   }
}
