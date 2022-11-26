package org.python.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.Shacal2Engine;
import org.python.bouncycastle.crypto.macs.CMac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class Shacal2 {
   private Shacal2() {
   }

   public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Shacal2 parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[32];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("Shacal2");
            var2.init(new IvParameterSpec(var1));
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "Shacal2 IV";
      }
   }

   public static class CBC extends BaseBlockCipher {
      public CBC() {
         super((BlockCipher)(new CBCBlockCipher(new Shacal2Engine())), 256);
      }
   }

   public static class CMAC extends BaseMac {
      public CMAC() {
         super(new CMac(new Shacal2Engine()));
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new Shacal2Engine();
            }
         });
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("SHACAL-2", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = Shacal2.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Mac.Shacal-2CMAC", PREFIX + "$CMAC");
         var1.addAlgorithm("Cipher.Shacal2", PREFIX + "$ECB");
         var1.addAlgorithm("Cipher.SHACAL-2", PREFIX + "$ECB");
         var1.addAlgorithm("KeyGenerator.Shacal2", PREFIX + "$KeyGen");
         var1.addAlgorithm("AlgorithmParameterGenerator.Shacal2", PREFIX + "$AlgParamGen");
         var1.addAlgorithm("AlgorithmParameters.Shacal2", PREFIX + "$AlgParams");
         var1.addAlgorithm("KeyGenerator.SHACAL-2", PREFIX + "$KeyGen");
         var1.addAlgorithm("AlgorithmParameterGenerator.SHACAL-2", PREFIX + "$AlgParamGen");
         var1.addAlgorithm("AlgorithmParameters.SHACAL-2", PREFIX + "$AlgParams");
      }
   }
}
