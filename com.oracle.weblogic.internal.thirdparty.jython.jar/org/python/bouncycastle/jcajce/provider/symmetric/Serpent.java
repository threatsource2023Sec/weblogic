package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.asn1.gnu.GNUObjectIdentifiers;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.SerpentEngine;
import org.python.bouncycastle.crypto.engines.TnepresEngine;
import org.python.bouncycastle.crypto.engines.TwofishEngine;
import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.python.bouncycastle.crypto.macs.GMac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.CFBBlockCipher;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.crypto.modes.OFBBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class Serpent {
   private Serpent() {
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "Serpent IV";
      }
   }

   public static class CBC extends BaseBlockCipher {
      public CBC() {
         super((BlockCipher)(new CBCBlockCipher(new SerpentEngine())), 128);
      }
   }

   public static class CFB extends BaseBlockCipher {
      public CFB() {
         super((BufferedBlockCipher)(new BufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128))), 128);
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new SerpentEngine();
            }
         });
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("Serpent", 192, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = Serpent.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.Serpent", PREFIX + "$ECB");
         var1.addAlgorithm("KeyGenerator.Serpent", PREFIX + "$KeyGen");
         var1.addAlgorithm("AlgorithmParameters.Serpent", PREFIX + "$AlgParams");
         var1.addAlgorithm("Cipher.Tnepres", PREFIX + "$TECB");
         var1.addAlgorithm("KeyGenerator.Tnepres", PREFIX + "$TKeyGen");
         var1.addAlgorithm("AlgorithmParameters.Tnepres", PREFIX + "$TAlgParams");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_ECB, PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_ECB, PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_ECB, PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_CBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_CBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_CBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_CFB, PREFIX + "$CFB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_CFB, PREFIX + "$CFB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_CFB, PREFIX + "$CFB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_128_OFB, PREFIX + "$OFB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_192_OFB, PREFIX + "$OFB");
         var1.addAlgorithm("Cipher", GNUObjectIdentifiers.Serpent_256_OFB, PREFIX + "$OFB");
         this.addGMacAlgorithm(var1, "SERPENT", PREFIX + "$SerpentGMAC", PREFIX + "$KeyGen");
         this.addGMacAlgorithm(var1, "TNEPRES", PREFIX + "$TSerpentGMAC", PREFIX + "$TKeyGen");
         this.addPoly1305Algorithm(var1, "SERPENT", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
      }
   }

   public static class OFB extends BaseBlockCipher {
      public OFB() {
         super((BufferedBlockCipher)(new BufferedBlockCipher(new OFBBlockCipher(new SerpentEngine(), 128))), 128);
      }
   }

   public static class Poly1305 extends BaseMac {
      public Poly1305() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305(new TwofishEngine()));
      }
   }

   public static class Poly1305KeyGen extends BaseKeyGenerator {
      public Poly1305KeyGen() {
         super("Poly1305-Serpent", 256, new Poly1305KeyGenerator());
      }
   }

   public static class SerpentGMAC extends BaseMac {
      public SerpentGMAC() {
         super(new GMac(new GCMBlockCipher(new SerpentEngine())));
      }
   }

   public static class TAlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "Tnepres IV";
      }
   }

   public static class TECB extends BaseBlockCipher {
      public TECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new TnepresEngine();
            }
         });
      }
   }

   public static class TKeyGen extends BaseKeyGenerator {
      public TKeyGen() {
         super("Tnepres", 192, new CipherKeyGenerator());
      }
   }

   public static class TSerpentGMAC extends BaseMac {
      public TSerpentGMAC() {
         super(new GMac(new GCMBlockCipher(new TnepresEngine())));
      }
   }
}
