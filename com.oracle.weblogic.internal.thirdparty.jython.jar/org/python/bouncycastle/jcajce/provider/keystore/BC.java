package org.python.bouncycastle.jcajce.provider.keystore;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class BC {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.keystore.bc.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyStore.BKS", "org.python.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$Std");
         var1.addAlgorithm("KeyStore.BKS-V1", "org.python.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$Version1");
         var1.addAlgorithm("KeyStore.BouncyCastle", "org.python.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$BouncyCastleStore");
         var1.addAlgorithm("Alg.Alias.KeyStore.UBER", "BouncyCastle");
         var1.addAlgorithm("Alg.Alias.KeyStore.BOUNCYCASTLE", "BouncyCastle");
         var1.addAlgorithm("Alg.Alias.KeyStore.bouncycastle", "BouncyCastle");
      }
   }
}
