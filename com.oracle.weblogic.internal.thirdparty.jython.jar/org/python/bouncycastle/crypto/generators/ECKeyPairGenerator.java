package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.python.bouncycastle.math.ec.WNafUtil;

public class ECKeyPairGenerator implements AsymmetricCipherKeyPairGenerator, ECConstants {
   ECDomainParameters params;
   SecureRandom random;

   public void init(KeyGenerationParameters var1) {
      ECKeyGenerationParameters var2 = (ECKeyGenerationParameters)var1;
      this.random = var2.getRandom();
      this.params = var2.getDomainParameters();
      if (this.random == null) {
         this.random = new SecureRandom();
      }

   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      BigInteger var1 = this.params.getN();
      int var2 = var1.bitLength();
      int var3 = var2 >>> 2;

      BigInteger var4;
      do {
         do {
            var4 = new BigInteger(var2, this.random);
         } while(var4.compareTo(TWO) < 0);
      } while(var4.compareTo(var1) >= 0 || WNafUtil.getNafWeight(var4) < var3);

      ECPoint var5 = this.createBasePointMultiplier().multiply(this.params.getG(), var4);
      return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(var5, this.params), new ECPrivateKeyParameters(var4, this.params));
   }

   protected ECMultiplier createBasePointMultiplier() {
      return new FixedPointCombMultiplier();
   }
}
