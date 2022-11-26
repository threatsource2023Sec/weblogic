package org.python.bouncycastle.jcajce.provider.asymmetric;

import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class ECGOST {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.asymmetric.ecgost.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyFactory.ECGOST3410", "org.python.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi");
         var1.addAlgorithm("Alg.Alias.KeyFactory.GOST-3410-2001", "ECGOST3410");
         var1.addAlgorithm("Alg.Alias.KeyFactory.ECGOST-3410", "ECGOST3410");
         this.registerOid(var1, CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410", new KeyFactorySpi());
         this.registerOidAlgorithmParameters(var1, CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410");
         var1.addAlgorithm("KeyPairGenerator.ECGOST3410", "org.python.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyPairGeneratorSpi");
         var1.addAlgorithm("Alg.Alias.KeyPairGenerator.ECGOST-3410", "ECGOST3410");
         var1.addAlgorithm("Alg.Alias.KeyPairGenerator.GOST-3410-2001", "ECGOST3410");
         var1.addAlgorithm("Signature.ECGOST3410", "org.python.bouncycastle.jcajce.provider.asymmetric.ecgost.SignatureSpi");
         var1.addAlgorithm("Alg.Alias.Signature.ECGOST-3410", "ECGOST3410");
         var1.addAlgorithm("Alg.Alias.Signature.GOST-3410-2001", "ECGOST3410");
         this.addSignatureAlgorithm(var1, "GOST3411", "ECGOST3410", "org.python.bouncycastle.jcajce.provider.asymmetric.ecgost.SignatureSpi", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
      }
   }
}
