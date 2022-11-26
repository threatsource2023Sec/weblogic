package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.RijndaelEngine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Rijndael {
   private Rijndael() {
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "Rijndael IV";
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new RijndaelEngine();
            }
         });
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("Rijndael", 192, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = Rijndael.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.RIJNDAEL", PREFIX + "$ECB");
         var1.addAlgorithm("KeyGenerator.RIJNDAEL", PREFIX + "$KeyGen");
         var1.addAlgorithm("AlgorithmParameters.RIJNDAEL", PREFIX + "$AlgParams");
      }
   }
}
