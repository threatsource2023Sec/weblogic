package org.python.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.generators.DHKeyPairGenerator;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;

public class DHAgreement {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private DHPrivateKeyParameters key;
   private DHParameters dhParams;
   private BigInteger privateValue;
   private SecureRandom random;

   public void init(CipherParameters var1) {
      AsymmetricKeyParameter var3;
      if (var1 instanceof ParametersWithRandom) {
         ParametersWithRandom var2 = (ParametersWithRandom)var1;
         this.random = var2.getRandom();
         var3 = (AsymmetricKeyParameter)var2.getParameters();
      } else {
         this.random = new SecureRandom();
         var3 = (AsymmetricKeyParameter)var1;
      }

      if (!(var3 instanceof DHPrivateKeyParameters)) {
         throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
      } else {
         this.key = (DHPrivateKeyParameters)var3;
         this.dhParams = this.key.getParameters();
      }
   }

   public BigInteger calculateMessage() {
      DHKeyPairGenerator var1 = new DHKeyPairGenerator();
      var1.init(new DHKeyGenerationParameters(this.random, this.dhParams));
      AsymmetricCipherKeyPair var2 = var1.generateKeyPair();
      this.privateValue = ((DHPrivateKeyParameters)var2.getPrivate()).getX();
      return ((DHPublicKeyParameters)var2.getPublic()).getY();
   }

   public BigInteger calculateAgreement(DHPublicKeyParameters var1, BigInteger var2) {
      if (!var1.getParameters().equals(this.dhParams)) {
         throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
      } else {
         BigInteger var3 = this.dhParams.getP();
         BigInteger var4 = var1.getY().modPow(this.privateValue, var3);
         if (var4.compareTo(ONE) == 0) {
            throw new IllegalStateException("Shared key can't be 1");
         } else {
            return var2.modPow(this.key.getX(), var3).multiply(var4).mod(var3);
         }
      }
   }
}
