package org.python.bouncycastle.jcajce.provider.asymmetric;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class IES {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.asymmetric.ies.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameters.IES", "org.python.bouncycastle.jcajce.provider.asymmetric.ies.AlgorithmParametersSpi");
         var1.addAlgorithm("AlgorithmParameters.ECIES", "org.python.bouncycastle.jcajce.provider.asymmetric.ies.AlgorithmParametersSpi");
      }
   }
}
