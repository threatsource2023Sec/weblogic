package org.python.bouncycastle.jcajce.provider.asymmetric;

import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyFactorySpi;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class ElGamal {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameterGenerator.ELGAMAL", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.AlgorithmParameterGeneratorSpi");
         var1.addAlgorithm("AlgorithmParameterGenerator.ElGamal", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.AlgorithmParameterGeneratorSpi");
         var1.addAlgorithm("AlgorithmParameters.ELGAMAL", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.AlgorithmParametersSpi");
         var1.addAlgorithm("AlgorithmParameters.ElGamal", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.AlgorithmParametersSpi");
         var1.addAlgorithm("Cipher.ELGAMAL", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.CipherSpi$NoPadding");
         var1.addAlgorithm("Cipher.ElGamal", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.CipherSpi$NoPadding");
         var1.addAlgorithm("Alg.Alias.Cipher.ELGAMAL/ECB/PKCS1PADDING", "ELGAMAL/PKCS1");
         var1.addAlgorithm("Alg.Alias.Cipher.ELGAMAL/NONE/PKCS1PADDING", "ELGAMAL/PKCS1");
         var1.addAlgorithm("Alg.Alias.Cipher.ELGAMAL/NONE/NOPADDING", "ELGAMAL");
         var1.addAlgorithm("Cipher.ELGAMAL/PKCS1", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.CipherSpi$PKCS1v1_5Padding");
         var1.addAlgorithm("KeyFactory.ELGAMAL", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyFactorySpi");
         var1.addAlgorithm("KeyFactory.ElGamal", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyFactorySpi");
         var1.addAlgorithm("KeyPairGenerator.ELGAMAL", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyPairGeneratorSpi");
         var1.addAlgorithm("KeyPairGenerator.ElGamal", "org.python.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyPairGeneratorSpi");
         KeyFactorySpi var2 = new KeyFactorySpi();
         this.registerOid(var1, OIWObjectIdentifiers.elGamalAlgorithm, "ELGAMAL", var2);
         this.registerOidAlgorithmParameterGenerator(var1, OIWObjectIdentifiers.elGamalAlgorithm, "ELGAMAL");
      }
   }
}
