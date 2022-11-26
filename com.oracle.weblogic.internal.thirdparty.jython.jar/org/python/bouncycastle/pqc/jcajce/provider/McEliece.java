package org.python.bouncycastle.pqc.jcajce.provider;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;

public class McEliece {
   private static final String PREFIX = "org.python.bouncycastle.pqc.jcajce.provider.mceliece.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyPairGenerator.McElieceKobaraImai", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyPairGeneratorSpi");
         var1.addAlgorithm("KeyPairGenerator.McEliecePointcheval", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyPairGeneratorSpi");
         var1.addAlgorithm("KeyPairGenerator.McElieceFujisaki", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyPairGeneratorSpi");
         var1.addAlgorithm("KeyPairGenerator.McEliece", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceKeyPairGeneratorSpi");
         var1.addAlgorithm("KeyPairGenerator.McEliece-CCA2", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyPairGeneratorSpi");
         var1.addAlgorithm("KeyFactory.McElieceKobaraImai", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi");
         var1.addAlgorithm("KeyFactory.McEliecePointcheval", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi");
         var1.addAlgorithm("KeyFactory.McElieceFujisaki", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi");
         var1.addAlgorithm("KeyFactory.McEliece", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceKeyFactorySpi");
         var1.addAlgorithm("KeyFactory.McEliece-CCA2", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi");
         var1.addAlgorithm("KeyFactory." + PQCObjectIdentifiers.mcElieceCca2, "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi");
         var1.addAlgorithm("KeyFactory." + PQCObjectIdentifiers.mcEliece, "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceKeyFactorySpi");
         var1.addAlgorithm("Cipher.McEliece", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McEliecePKCSCipherSpi$McEliecePKCS");
         var1.addAlgorithm("Cipher.McEliecePointcheval", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McEliecePointchevalCipherSpi$McEliecePointcheval");
         var1.addAlgorithm("Cipher.McElieceKobaraImai", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceKobaraImaiCipherSpi$McElieceKobaraImai");
         var1.addAlgorithm("Cipher.McElieceFujisaki", "org.python.bouncycastle.pqc.jcajce.provider.mceliece.McElieceFujisakiCipherSpi$McElieceFujisaki");
      }
   }
}
