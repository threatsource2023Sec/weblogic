package org.python.bouncycastle.pqc.jcajce.provider.rainbow;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowKeyGenerationParameters;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowKeyPairGenerator;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowParameters;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;
import org.python.bouncycastle.pqc.jcajce.spec.RainbowParameterSpec;

public class RainbowKeyPairGeneratorSpi extends KeyPairGenerator {
   RainbowKeyGenerationParameters param;
   RainbowKeyPairGenerator engine = new RainbowKeyPairGenerator();
   int strength = 1024;
   SecureRandom random = new SecureRandom();
   boolean initialised = false;

   public RainbowKeyPairGeneratorSpi() {
      super("Rainbow");
   }

   public void initialize(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
   }

   public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      if (!(var1 instanceof RainbowParameterSpec)) {
         throw new InvalidAlgorithmParameterException("parameter object not a RainbowParameterSpec");
      } else {
         RainbowParameterSpec var3 = (RainbowParameterSpec)var1;
         this.param = new RainbowKeyGenerationParameters(var2, new RainbowParameters(var3.getVi()));
         this.engine.init(this.param);
         this.initialised = true;
      }
   }

   public KeyPair generateKeyPair() {
      if (!this.initialised) {
         this.param = new RainbowKeyGenerationParameters(this.random, new RainbowParameters((new RainbowParameterSpec()).getVi()));
         this.engine.init(this.param);
         this.initialised = true;
      }

      AsymmetricCipherKeyPair var1 = this.engine.generateKeyPair();
      RainbowPublicKeyParameters var2 = (RainbowPublicKeyParameters)var1.getPublic();
      RainbowPrivateKeyParameters var3 = (RainbowPrivateKeyParameters)var1.getPrivate();
      return new KeyPair(new BCRainbowPublicKey(var2), new BCRainbowPrivateKey(var3));
   }
}
