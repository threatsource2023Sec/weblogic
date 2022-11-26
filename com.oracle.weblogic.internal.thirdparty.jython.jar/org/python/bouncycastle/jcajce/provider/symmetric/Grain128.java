package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.Grain128Engine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Grain128 {
   private Grain128() {
   }

   public static class Base extends BaseStreamCipher {
      public Base() {
         super(new Grain128Engine(), 12);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("Grain128", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = Grain128.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.Grain128", PREFIX + "$Base");
         var1.addAlgorithm("KeyGenerator.Grain128", PREFIX + "$KeyGen");
      }
   }
}
