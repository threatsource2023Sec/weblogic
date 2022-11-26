package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.python.bouncycastle.crypto.engines.ChaChaEngine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class ChaCha {
   private ChaCha() {
   }

   public static class Base extends BaseStreamCipher {
      public Base() {
         super(new ChaChaEngine(), 8);
      }
   }

   public static class Base7539 extends BaseStreamCipher {
      public Base7539() {
         super(new ChaCha7539Engine(), 12);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("ChaCha", 128, new CipherKeyGenerator());
      }
   }

   public static class KeyGen7539 extends BaseKeyGenerator {
      public KeyGen7539() {
         super("ChaCha7539", 256, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = ChaCha.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.CHACHA", PREFIX + "$Base");
         var1.addAlgorithm("KeyGenerator.CHACHA", PREFIX + "$KeyGen");
         var1.addAlgorithm("Cipher.CHACHA7539", PREFIX + "$Base7539");
         var1.addAlgorithm("KeyGenerator.CHACHA7539", PREFIX + "$KeyGen7539");
      }
   }
}
