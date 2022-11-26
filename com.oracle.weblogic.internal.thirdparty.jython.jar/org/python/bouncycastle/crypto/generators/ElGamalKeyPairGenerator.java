package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.ElGamalParameters;
import org.python.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

public class ElGamalKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private ElGamalKeyGenerationParameters param;

   public void init(KeyGenerationParameters var1) {
      this.param = (ElGamalKeyGenerationParameters)var1;
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      DHKeyGeneratorHelper var1 = DHKeyGeneratorHelper.INSTANCE;
      ElGamalParameters var2 = this.param.getParameters();
      DHParameters var3 = new DHParameters(var2.getP(), var2.getG(), (BigInteger)null, var2.getL());
      BigInteger var4 = var1.calculatePrivate(var3, this.param.getRandom());
      BigInteger var5 = var1.calculatePublic(var3, var4);
      return new AsymmetricCipherKeyPair(new ElGamalPublicKeyParameters(var5, var2), new ElGamalPrivateKeyParameters(var4, var2));
   }
}
