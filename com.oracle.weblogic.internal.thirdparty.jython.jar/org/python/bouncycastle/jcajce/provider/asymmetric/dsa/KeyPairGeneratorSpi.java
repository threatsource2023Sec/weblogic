package org.python.bouncycastle.jcajce.provider.asymmetric.dsa;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.util.Hashtable;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.generators.DSAKeyPairGenerator;
import org.python.bouncycastle.crypto.generators.DSAParametersGenerator;
import org.python.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import org.python.bouncycastle.crypto.params.DSAParameters;
import org.python.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import org.python.bouncycastle.util.Integers;
import org.python.bouncycastle.util.Properties;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
   private static Hashtable params = new Hashtable();
   private static Object lock = new Object();
   DSAKeyGenerationParameters param;
   DSAKeyPairGenerator engine = new DSAKeyPairGenerator();
   int strength = 2048;
   SecureRandom random = new SecureRandom();
   boolean initialised = false;

   public KeyPairGeneratorSpi() {
      super("DSA");
   }

   public void initialize(int var1, SecureRandom var2) {
      if (var1 >= 512 && var1 <= 4096 && (var1 >= 1024 || var1 % 64 == 0) && (var1 < 1024 || var1 % 1024 == 0)) {
         this.strength = var1;
         this.random = var2;
         this.initialised = false;
      } else {
         throw new InvalidParameterException("strength must be from 512 - 4096 and a multiple of 1024 above 1024");
      }
   }

   public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      if (!(var1 instanceof DSAParameterSpec)) {
         throw new InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
      } else {
         DSAParameterSpec var3 = (DSAParameterSpec)var1;
         this.param = new DSAKeyGenerationParameters(var2, new DSAParameters(var3.getP(), var3.getQ(), var3.getG()));
         this.engine.init(this.param);
         this.initialised = true;
      }
   }

   public KeyPair generateKeyPair() {
      if (!this.initialised) {
         Integer var1 = Integers.valueOf(this.strength);
         if (params.containsKey(var1)) {
            this.param = (DSAKeyGenerationParameters)params.get(var1);
         } else {
            synchronized(lock) {
               if (params.containsKey(var1)) {
                  this.param = (DSAKeyGenerationParameters)params.get(var1);
               } else {
                  int var3 = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
                  DSAParametersGenerator var4;
                  DSAParameterGenerationParameters var5;
                  if (this.strength == 1024) {
                     var4 = new DSAParametersGenerator();
                     if (Properties.isOverrideSet("org.bouncycastle.dsa.FIPS186-2for1024bits")) {
                        var4.init(this.strength, var3, this.random);
                     } else {
                        var5 = new DSAParameterGenerationParameters(1024, 160, var3, this.random);
                        var4.init(var5);
                     }
                  } else if (this.strength > 1024) {
                     var5 = new DSAParameterGenerationParameters(this.strength, 256, var3, this.random);
                     var4 = new DSAParametersGenerator(new SHA256Digest());
                     var4.init(var5);
                  } else {
                     var4 = new DSAParametersGenerator();
                     var4.init(this.strength, var3, this.random);
                  }

                  this.param = new DSAKeyGenerationParameters(this.random, var4.generateParameters());
                  params.put(var1, this.param);
               }
            }
         }

         this.engine.init(this.param);
         this.initialised = true;
      }

      AsymmetricCipherKeyPair var8 = this.engine.generateKeyPair();
      DSAPublicKeyParameters var2 = (DSAPublicKeyParameters)var8.getPublic();
      DSAPrivateKeyParameters var9 = (DSAPrivateKeyParameters)var8.getPrivate();
      return new KeyPair(new BCDSAPublicKey(var2), new BCDSAPrivateKey(var9));
   }
}
