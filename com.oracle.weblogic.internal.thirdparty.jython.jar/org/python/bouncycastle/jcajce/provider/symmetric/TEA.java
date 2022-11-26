package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.TEAEngine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class TEA {
   private TEA() {
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "TEA IV";
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super((BlockCipher)(new TEAEngine()));
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("TEA", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = TEA.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.TEA", PREFIX + "$ECB");
         var1.addAlgorithm("KeyGenerator.TEA", PREFIX + "$KeyGen");
         var1.addAlgorithm("AlgorithmParameters.TEA", PREFIX + "$AlgParams");
      }
   }
}
