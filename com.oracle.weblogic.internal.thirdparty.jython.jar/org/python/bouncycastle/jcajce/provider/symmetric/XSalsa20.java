package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.XSalsa20Engine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class XSalsa20 {
   private XSalsa20() {
   }

   public static class Base extends BaseStreamCipher {
      public Base() {
         super(new XSalsa20Engine(), 24);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("XSalsa20", 256, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = XSalsa20.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.XSALSA20", PREFIX + "$Base");
         var1.addAlgorithm("KeyGenerator.XSALSA20", PREFIX + "$KeyGen");
      }
   }
}
