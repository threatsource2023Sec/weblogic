package org.python.bouncycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.spec.DHParameterSpec;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.python.bouncycastle.crypto.generators.DHParametersGenerator;
import org.python.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.util.Integers;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
   private static Hashtable params = new Hashtable();
   private static Object lock = new Object();
   DHKeyGenerationParameters param;
   DHBasicKeyPairGenerator engine = new DHBasicKeyPairGenerator();
   int strength = 2048;
   SecureRandom random = new SecureRandom();
   boolean initialised = false;

   public KeyPairGeneratorSpi() {
      super("DH");
   }

   public void initialize(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
      this.initialised = false;
   }

   public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      if (!(var1 instanceof DHParameterSpec)) {
         throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
      } else {
         DHParameterSpec var3 = (DHParameterSpec)var1;
         this.param = new DHKeyGenerationParameters(var2, new DHParameters(var3.getP(), var3.getG(), (BigInteger)null, var3.getL()));
         this.engine.init(this.param);
         this.initialised = true;
      }
   }

   public KeyPair generateKeyPair() {
      if (!this.initialised) {
         Integer var1 = Integers.valueOf(this.strength);
         if (params.containsKey(var1)) {
            this.param = (DHKeyGenerationParameters)params.get(var1);
         } else {
            DHParameterSpec var2 = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters(this.strength);
            if (var2 != null) {
               this.param = new DHKeyGenerationParameters(this.random, new DHParameters(var2.getP(), var2.getG(), (BigInteger)null, var2.getL()));
            } else {
               synchronized(lock) {
                  if (params.containsKey(var1)) {
                     this.param = (DHKeyGenerationParameters)params.get(var1);
                  } else {
                     DHParametersGenerator var4 = new DHParametersGenerator();
                     var4.init(this.strength, PrimeCertaintyCalculator.getDefaultCertainty(this.strength), this.random);
                     this.param = new DHKeyGenerationParameters(this.random, var4.generateParameters());
                     params.put(var1, this.param);
                  }
               }
            }
         }

         this.engine.init(this.param);
         this.initialised = true;
      }

      AsymmetricCipherKeyPair var7 = this.engine.generateKeyPair();
      DHPublicKeyParameters var8 = (DHPublicKeyParameters)var7.getPublic();
      DHPrivateKeyParameters var3 = (DHPrivateKeyParameters)var7.getPrivate();
      return new KeyPair(new BCDHPublicKey(var8), new BCDHPrivateKey(var3));
   }
}
