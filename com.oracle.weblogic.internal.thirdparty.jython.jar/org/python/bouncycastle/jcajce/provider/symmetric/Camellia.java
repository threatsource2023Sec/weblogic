package org.python.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.python.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.CamelliaEngine;
import org.python.bouncycastle.crypto.engines.CamelliaWrapEngine;
import org.python.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
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

public final class Camellia {
   private Camellia() {
   }

   public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Camellia parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[16];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("Camellia");
            var2.init(new IvParameterSpec(var1));
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "Camellia IV";
      }
   }

   public static class CBC extends BaseBlockCipher {
      public CBC() {
         super((BlockCipher)(new CBCBlockCipher(new CamelliaEngine())), 128);
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new CamelliaEngine();
            }
         });
      }
   }

   public static class GMAC extends BaseMac {
      public GMAC() {
         super(new GMac(new GCMBlockCipher(new CamelliaEngine())));
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         this(256);
      }

      public KeyGen(int var1) {
         super("Camellia", var1, new CipherKeyGenerator());
      }
   }

   public static class KeyGen128 extends KeyGen {
      public KeyGen128() {
         super(128);
      }
   }

   public static class KeyGen192 extends KeyGen {
      public KeyGen192() {
         super(192);
      }
   }

   public static class KeyGen256 extends KeyGen {
      public KeyGen256() {
         super(256);
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = Camellia.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameters.CAMELLIA", PREFIX + "$AlgParams");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters", NTTObjectIdentifiers.id_camellia128_cbc, "CAMELLIA");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters", NTTObjectIdentifiers.id_camellia192_cbc, "CAMELLIA");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters", NTTObjectIdentifiers.id_camellia256_cbc, "CAMELLIA");
         var1.addAlgorithm("AlgorithmParameterGenerator.CAMELLIA", PREFIX + "$AlgParamGen");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", NTTObjectIdentifiers.id_camellia128_cbc, "CAMELLIA");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", NTTObjectIdentifiers.id_camellia192_cbc, "CAMELLIA");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", NTTObjectIdentifiers.id_camellia256_cbc, "CAMELLIA");
         var1.addAlgorithm("Cipher.CAMELLIA", PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", NTTObjectIdentifiers.id_camellia128_cbc, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", NTTObjectIdentifiers.id_camellia192_cbc, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", NTTObjectIdentifiers.id_camellia256_cbc, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher.CAMELLIARFC3211WRAP", PREFIX + "$RFC3211Wrap");
         var1.addAlgorithm("Cipher.CAMELLIAWRAP", PREFIX + "$Wrap");
         var1.addAlgorithm("Alg.Alias.Cipher", NTTObjectIdentifiers.id_camellia128_wrap, "CAMELLIAWRAP");
         var1.addAlgorithm("Alg.Alias.Cipher", NTTObjectIdentifiers.id_camellia192_wrap, "CAMELLIAWRAP");
         var1.addAlgorithm("Alg.Alias.Cipher", NTTObjectIdentifiers.id_camellia256_wrap, "CAMELLIAWRAP");
         var1.addAlgorithm("KeyGenerator.CAMELLIA", PREFIX + "$KeyGen");
         var1.addAlgorithm("KeyGenerator", NTTObjectIdentifiers.id_camellia128_wrap, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NTTObjectIdentifiers.id_camellia192_wrap, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NTTObjectIdentifiers.id_camellia256_wrap, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NTTObjectIdentifiers.id_camellia128_cbc, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NTTObjectIdentifiers.id_camellia192_cbc, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NTTObjectIdentifiers.id_camellia256_cbc, PREFIX + "$KeyGen256");
         this.addGMacAlgorithm(var1, "CAMELLIA", PREFIX + "$GMAC", PREFIX + "$KeyGen");
         this.addPoly1305Algorithm(var1, "CAMELLIA", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
      }
   }

   public static class Poly1305 extends BaseMac {
      public Poly1305() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305(new CamelliaEngine()));
      }
   }

   public static class Poly1305KeyGen extends BaseKeyGenerator {
      public Poly1305KeyGen() {
         super("Poly1305-Camellia", 256, new Poly1305KeyGenerator());
      }
   }

   public static class RFC3211Wrap extends BaseWrapCipher {
      public RFC3211Wrap() {
         super(new RFC3211WrapEngine(new CamelliaEngine()), 16);
      }
   }

   public static class Wrap extends BaseWrapCipher {
      public Wrap() {
         super(new CamelliaWrapEngine());
      }
   }
}
