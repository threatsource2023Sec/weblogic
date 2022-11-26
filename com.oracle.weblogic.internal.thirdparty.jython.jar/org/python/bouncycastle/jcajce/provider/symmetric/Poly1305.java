package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public class Poly1305 {
   private Poly1305() {
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("Poly1305", 256, new Poly1305KeyGenerator());
      }
   }

   public static class Mac extends BaseMac {
      public Mac() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = Poly1305.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Mac.POLY1305", PREFIX + "$Mac");
         var1.addAlgorithm("KeyGenerator.POLY1305", PREFIX + "$KeyGen");
      }
   }
}
