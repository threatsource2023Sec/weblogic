package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.TwofishEngine;
import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.python.bouncycastle.crypto.macs.GMac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public final class Twofish {
   private Twofish() {
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "Twofish IV";
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new TwofishEngine();
            }
         });
      }
   }

   public static class GMAC extends BaseMac {
      public GMAC() {
         super(new GMac(new GCMBlockCipher(new TwofishEngine())));
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("Twofish", 256, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = Twofish.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.Twofish", PREFIX + "$ECB");
         var1.addAlgorithm("KeyGenerator.Twofish", PREFIX + "$KeyGen");
         var1.addAlgorithm("AlgorithmParameters.Twofish", PREFIX + "$AlgParams");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH-CBC", "PKCS12PBE");
         var1.addAlgorithm("Cipher.PBEWITHSHAANDTWOFISH-CBC", PREFIX + "$PBEWithSHA");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHAANDTWOFISH-CBC", PREFIX + "$PBEWithSHAKeyFactory");
         this.addGMacAlgorithm(var1, "Twofish", PREFIX + "$GMAC", PREFIX + "$KeyGen");
         this.addPoly1305Algorithm(var1, "Twofish", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
      }
   }

   public static class PBEWithSHA extends BaseBlockCipher {
      public PBEWithSHA() {
         super(new CBCBlockCipher(new TwofishEngine()), 2, 1, 256, 16);
      }
   }

   public static class PBEWithSHAKeyFactory extends PBESecretKeyFactory {
      public PBEWithSHAKeyFactory() {
         super("PBEwithSHAandTwofish-CBC", (ASN1ObjectIdentifier)null, true, 2, 1, 256, 128);
      }
   }

   public static class Poly1305 extends BaseMac {
      public Poly1305() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305(new TwofishEngine()));
      }
   }

   public static class Poly1305KeyGen extends BaseKeyGenerator {
      public Poly1305KeyGen() {
         super("Poly1305-Twofish", 256, new Poly1305KeyGenerator());
      }
   }
}
