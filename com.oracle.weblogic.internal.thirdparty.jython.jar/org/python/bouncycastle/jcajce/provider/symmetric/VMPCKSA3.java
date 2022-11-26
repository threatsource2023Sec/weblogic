package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.VMPCKSA3Engine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class VMPCKSA3 {
   private VMPCKSA3() {
   }

   public static class Base extends BaseStreamCipher {
      public Base() {
         super(new VMPCKSA3Engine(), 16);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("VMPC-KSA3", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = VMPCKSA3.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.VMPC-KSA3", PREFIX + "$Base");
         var1.addAlgorithm("KeyGenerator.VMPC-KSA3", PREFIX + "$KeyGen");
      }
   }
}
