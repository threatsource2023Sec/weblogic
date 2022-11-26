package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.GOST3410Parameters;
import org.python.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.python.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import org.python.bouncycastle.math.ec.WNafUtil;

public class GOST3410KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private GOST3410KeyGenerationParameters param;

   public void init(KeyGenerationParameters var1) {
      this.param = (GOST3410KeyGenerationParameters)var1;
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      GOST3410Parameters var1 = this.param.getParameters();
      SecureRandom var2 = this.param.getRandom();
      BigInteger var3 = var1.getQ();
      BigInteger var4 = var1.getP();
      BigInteger var5 = var1.getA();
      byte var6 = 64;

      BigInteger var7;
      do {
         do {
            var7 = new BigInteger(256, var2);
         } while(var7.signum() < 1);
      } while(var7.compareTo(var3) >= 0 || WNafUtil.getNafWeight(var7) < var6);

      BigInteger var8 = var5.modPow(var7, var4);
      return new AsymmetricCipherKeyPair(new GOST3410PublicKeyParameters(var8, var1), new GOST3410PrivateKeyParameters(var7, var1));
   }
}
