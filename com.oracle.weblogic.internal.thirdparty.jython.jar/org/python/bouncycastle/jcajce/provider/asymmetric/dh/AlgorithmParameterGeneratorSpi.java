package org.python.bouncycastle.jcajce.provider.asymmetric.dh;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.python.bouncycastle.crypto.generators.DHParametersGenerator;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;

public class AlgorithmParameterGeneratorSpi extends BaseAlgorithmParameterGeneratorSpi {
   protected SecureRandom random;
   protected int strength = 2048;
   private int l = 0;

   protected void engineInit(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
   }

   protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      if (!(var1 instanceof DHGenParameterSpec)) {
         throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
      } else {
         DHGenParameterSpec var3 = (DHGenParameterSpec)var1;
         this.strength = var3.getPrimeSize();
         this.l = var3.getExponentSize();
         this.random = var2;
      }
   }

   protected AlgorithmParameters engineGenerateParameters() {
      DHParametersGenerator var1 = new DHParametersGenerator();
      int var2 = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
      if (this.random != null) {
         var1.init(this.strength, var2, this.random);
      } else {
         var1.init(this.strength, var2, new SecureRandom());
      }

      DHParameters var3 = var1.generateParameters();

      try {
         AlgorithmParameters var4 = this.createParametersInstance("DH");
         var4.init(new DHParameterSpec(var3.getP(), var3.getG(), this.l));
         return var4;
      } catch (Exception var6) {
         throw new RuntimeException(var6.getMessage());
      }
   }
}
