package org.python.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.python.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.python.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.ElGamalParameters;
import org.python.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ElGamalParameterSpec;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
   ElGamalKeyGenerationParameters param;
   ElGamalKeyPairGenerator engine = new ElGamalKeyPairGenerator();
   int strength = 1024;
   int certainty = 20;
   SecureRandom random = new SecureRandom();
   boolean initialised = false;

   public KeyPairGeneratorSpi() {
      super("ElGamal");
   }

   public void initialize(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
   }

   public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      if (!(var1 instanceof ElGamalParameterSpec) && !(var1 instanceof DHParameterSpec)) {
         throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec or an ElGamalParameterSpec");
      } else {
         if (var1 instanceof ElGamalParameterSpec) {
            ElGamalParameterSpec var3 = (ElGamalParameterSpec)var1;
            this.param = new ElGamalKeyGenerationParameters(var2, new ElGamalParameters(var3.getP(), var3.getG()));
         } else {
            DHParameterSpec var4 = (DHParameterSpec)var1;
            this.param = new ElGamalKeyGenerationParameters(var2, new ElGamalParameters(var4.getP(), var4.getG(), var4.getL()));
         }

         this.engine.init(this.param);
         this.initialised = true;
      }
   }

   public KeyPair generateKeyPair() {
      if (!this.initialised) {
         DHParameterSpec var1 = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters(this.strength);
         if (var1 != null) {
            this.param = new ElGamalKeyGenerationParameters(this.random, new ElGamalParameters(var1.getP(), var1.getG(), var1.getL()));
         } else {
            ElGamalParametersGenerator var2 = new ElGamalParametersGenerator();
            var2.init(this.strength, this.certainty, this.random);
            this.param = new ElGamalKeyGenerationParameters(this.random, var2.generateParameters());
         }

         this.engine.init(this.param);
         this.initialised = true;
      }

      AsymmetricCipherKeyPair var4 = this.engine.generateKeyPair();
      ElGamalPublicKeyParameters var5 = (ElGamalPublicKeyParameters)var4.getPublic();
      ElGamalPrivateKeyParameters var3 = (ElGamalPrivateKeyParameters)var4.getPrivate();
      return new KeyPair(new BCElGamalPublicKey(var5), new BCElGamalPrivateKey(var3));
   }
}
