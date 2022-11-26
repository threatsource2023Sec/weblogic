package org.python.bouncycastle.jcajce.provider.keystore;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class BCFKS {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.keystore.bcfks.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyStore.BCFKS", "org.python.bouncycastle.jcajce.provider.keystore.bcfks.BcFKSKeyStoreSpi$Std");
         var1.addAlgorithm("KeyStore.BCFKS-DEF", "org.python.bouncycastle.jcajce.provider.keystore.bcfks.BcFKSKeyStoreSpi$Def");
      }
   }
}
