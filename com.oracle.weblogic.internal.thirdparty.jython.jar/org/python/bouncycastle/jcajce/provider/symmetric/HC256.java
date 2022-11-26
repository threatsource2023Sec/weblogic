package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.HC256Engine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class HC256 {
   private HC256() {
   }

   public static class Base extends BaseStreamCipher {
      public Base() {
         super(new HC256Engine(), 32);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("HC256", 256, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = HC256.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.HC256", PREFIX + "$Base");
         var1.addAlgorithm("KeyGenerator.HC256", PREFIX + "$KeyGen");
      }
   }
}
