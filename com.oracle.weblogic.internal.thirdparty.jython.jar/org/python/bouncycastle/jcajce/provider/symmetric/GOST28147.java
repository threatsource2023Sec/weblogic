package org.python.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.GOST28147Engine;
import org.python.bouncycastle.crypto.macs.GOST28147Mac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.GCFBBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class GOST28147 {
   private GOST28147() {
   }

   public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST28147 parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[16];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("GOST28147");
            var2.init(new IvParameterSpec(var1));
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "GOST IV";
      }
   }

   public static class CBC extends BaseBlockCipher {
      public CBC() {
         super((BlockCipher)(new CBCBlockCipher(new GOST28147Engine())), 64);
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super((BlockCipher)(new GOST28147Engine()));
      }
   }

   public static class GCFB extends BaseBlockCipher {
      public GCFB() {
         super((BufferedBlockCipher)(new BufferedBlockCipher(new GCFBBlockCipher(new GOST28147Engine()))), 64);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         this(256);
      }

      public KeyGen(int var1) {
         super("GOST28147", var1, new CipherKeyGenerator());
      }
   }

   public static class Mac extends BaseMac {
      public Mac() {
         super(new GOST28147Mac());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = GOST28147.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.GOST28147", PREFIX + "$ECB");
         var1.addAlgorithm("Alg.Alias.Cipher.GOST", "GOST28147");
         var1.addAlgorithm("Alg.Alias.Cipher.GOST-28147", "GOST28147");
         var1.addAlgorithm("Cipher." + CryptoProObjectIdentifiers.gostR28147_gcfb, PREFIX + "$GCFB");
         var1.addAlgorithm("KeyGenerator.GOST28147", PREFIX + "$KeyGen");
         var1.addAlgorithm("Alg.Alias.KeyGenerator.GOST", "GOST28147");
         var1.addAlgorithm("Alg.Alias.KeyGenerator.GOST-28147", "GOST28147");
         var1.addAlgorithm("Alg.Alias.KeyGenerator." + CryptoProObjectIdentifiers.gostR28147_gcfb, "GOST28147");
         var1.addAlgorithm("Mac.GOST28147MAC", PREFIX + "$Mac");
         var1.addAlgorithm("Alg.Alias.Mac.GOST28147", "GOST28147MAC");
      }
   }
}
