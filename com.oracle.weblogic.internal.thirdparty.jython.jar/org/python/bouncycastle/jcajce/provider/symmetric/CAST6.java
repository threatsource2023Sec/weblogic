package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.CAST6Engine;
import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.python.bouncycastle.crypto.macs.GMac;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;

public final class CAST6 {
   private CAST6() {
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new CAST6Engine();
            }
         });
      }
   }

   public static class GMAC extends BaseMac {
      public GMAC() {
         super(new GMac(new GCMBlockCipher(new CAST6Engine())));
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("CAST6", 256, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = CAST6.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.CAST6", PREFIX + "$ECB");
         var1.addAlgorithm("KeyGenerator.CAST6", PREFIX + "$KeyGen");
         this.addGMacAlgorithm(var1, "CAST6", PREFIX + "$GMAC", PREFIX + "$KeyGen");
         this.addPoly1305Algorithm(var1, "CAST6", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
      }
   }

   public static class Poly1305 extends BaseMac {
      public Poly1305() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305(new CAST6Engine()));
      }
   }

   public static class Poly1305KeyGen extends BaseKeyGenerator {
      public Poly1305KeyGen() {
         super("Poly1305-CAST6", 256, new Poly1305KeyGenerator());
      }
   }
}
