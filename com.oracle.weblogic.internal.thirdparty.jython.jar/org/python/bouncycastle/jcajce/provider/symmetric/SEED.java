package org.python.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.python.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.SEEDEngine;
import org.python.bouncycastle.crypto.engines.SEEDWrapEngine;
import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.python.bouncycastle.crypto.macs.CMac;
import org.python.bouncycastle.crypto.macs.GMac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class SEED {
   private SEED() {
   }

   public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for SEED parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[16];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("SEED");
            var2.init(new IvParameterSpec(var1));
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "SEED IV";
      }
   }

   public static class CBC extends BaseBlockCipher {
      public CBC() {
         super((BlockCipher)(new CBCBlockCipher(new SEEDEngine())), 128);
      }
   }

   public static class CMAC extends BaseMac {
      public CMAC() {
         super(new CMac(new SEEDEngine()));
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new SEEDEngine();
            }
         });
      }
   }

   public static class GMAC extends BaseMac {
      public GMAC() {
         super(new GMac(new GCMBlockCipher(new SEEDEngine())));
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("SEED", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = SEED.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameters.SEED", PREFIX + "$AlgParams");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + KISAObjectIdentifiers.id_seedCBC, "SEED");
         var1.addAlgorithm("AlgorithmParameterGenerator.SEED", PREFIX + "$AlgParamGen");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + KISAObjectIdentifiers.id_seedCBC, "SEED");
         var1.addAlgorithm("Cipher.SEED", PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", KISAObjectIdentifiers.id_seedCBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher.SEEDWRAP", PREFIX + "$Wrap");
         var1.addAlgorithm("Alg.Alias.Cipher", KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, "SEEDWRAP");
         var1.addAlgorithm("Alg.Alias.Cipher.SEEDKW", "SEEDWRAP");
         var1.addAlgorithm("KeyGenerator.SEED", PREFIX + "$KeyGen");
         var1.addAlgorithm("KeyGenerator", KISAObjectIdentifiers.id_seedCBC, PREFIX + "$KeyGen");
         var1.addAlgorithm("KeyGenerator", KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, PREFIX + "$KeyGen");
         this.addCMacAlgorithm(var1, "SEED", PREFIX + "$CMAC", PREFIX + "$KeyGen");
         this.addGMacAlgorithm(var1, "SEED", PREFIX + "$GMAC", PREFIX + "$KeyGen");
         this.addPoly1305Algorithm(var1, "SEED", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
      }
   }

   public static class Poly1305 extends BaseMac {
      public Poly1305() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305(new SEEDEngine()));
      }
   }

   public static class Poly1305KeyGen extends BaseKeyGenerator {
      public Poly1305KeyGen() {
         super("Poly1305-SEED", 256, new Poly1305KeyGenerator());
      }
   }

   public static class Wrap extends BaseWrapCipher {
      public Wrap() {
         super(new SEEDWrapEngine());
      }
   }
}
