package org.python.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.RC532Engine;
import org.python.bouncycastle.crypto.engines.RC564Engine;
import org.python.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.python.bouncycastle.crypto.macs.CFBBlockCipherMac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class RC5 {
   private RC5() {
   }

   public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC5 parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[8];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("RC5");
            var2.init(new IvParameterSpec(var1));
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "RC5 IV";
      }
   }

   public static class CBC32 extends BaseBlockCipher {
      public CBC32() {
         super((BlockCipher)(new CBCBlockCipher(new RC532Engine())), 64);
      }
   }

   public static class CFB8Mac32 extends BaseMac {
      public CFB8Mac32() {
         super(new CFBBlockCipherMac(new RC532Engine()));
      }
   }

   public static class ECB32 extends BaseBlockCipher {
      public ECB32() {
         super((BlockCipher)(new RC532Engine()));
      }
   }

   public static class ECB64 extends BaseBlockCipher {
      public ECB64() {
         super((BlockCipher)(new RC564Engine()));
      }
   }

   public static class KeyGen32 extends BaseKeyGenerator {
      public KeyGen32() {
         super("RC5", 128, new CipherKeyGenerator());
      }
   }

   public static class KeyGen64 extends BaseKeyGenerator {
      public KeyGen64() {
         super("RC5-64", 256, new CipherKeyGenerator());
      }
   }

   public static class Mac32 extends BaseMac {
      public Mac32() {
         super(new CBCBlockCipherMac(new RC532Engine()));
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = RC5.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.RC5", PREFIX + "$ECB32");
         var1.addAlgorithm("Alg.Alias.Cipher.RC5-32", "RC5");
         var1.addAlgorithm("Cipher.RC5-64", PREFIX + "$ECB64");
         var1.addAlgorithm("KeyGenerator.RC5", PREFIX + "$KeyGen32");
         var1.addAlgorithm("Alg.Alias.KeyGenerator.RC5-32", "RC5");
         var1.addAlgorithm("KeyGenerator.RC5-64", PREFIX + "$KeyGen64");
         var1.addAlgorithm("AlgorithmParameters.RC5", PREFIX + "$AlgParams");
         var1.addAlgorithm("AlgorithmParameters.RC5-64", PREFIX + "$AlgParams");
         var1.addAlgorithm("Mac.RC5MAC", PREFIX + "$Mac32");
         var1.addAlgorithm("Alg.Alias.Mac.RC5", "RC5MAC");
         var1.addAlgorithm("Mac.RC5MAC/CFB8", PREFIX + "$CFB8Mac32");
         var1.addAlgorithm("Alg.Alias.Mac.RC5/CFB8", "RC5MAC/CFB8");
      }
   }
}
