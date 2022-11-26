package org.python.bouncycastle.jcajce.provider.asymmetric.gost;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.crypto.generators.GOST3410ParametersGenerator;
import org.python.bouncycastle.crypto.params.GOST3410Parameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import org.python.bouncycastle.jce.spec.GOST3410ParameterSpec;
import org.python.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract class AlgorithmParameterGeneratorSpi extends BaseAlgorithmParameterGeneratorSpi {
   protected SecureRandom random;
   protected int strength = 1024;

   protected void engineInit(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
   }

   protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST3410 parameter generation.");
   }

   protected AlgorithmParameters engineGenerateParameters() {
      GOST3410ParametersGenerator var1 = new GOST3410ParametersGenerator();
      if (this.random != null) {
         var1.init(this.strength, 2, this.random);
      } else {
         var1.init(this.strength, 2, new SecureRandom());
      }

      GOST3410Parameters var2 = var1.generateParameters();

      try {
         AlgorithmParameters var3 = this.createParametersInstance("GOST3410");
         var3.init(new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(var2.getP(), var2.getQ(), var2.getA())));
         return var3;
      } catch (Exception var5) {
         throw new RuntimeException(var5.getMessage());
      }
   }
}
