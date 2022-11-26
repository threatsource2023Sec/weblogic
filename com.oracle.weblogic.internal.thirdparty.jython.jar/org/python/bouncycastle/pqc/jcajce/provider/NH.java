package org.python.bouncycastle.pqc.jcajce.provider;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.jcajce.provider.newhope.NHKeyFactorySpi;

public class NH {
   private static final String PREFIX = "org.python.bouncycastle.pqc.jcajce.provider.newhope.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyFactory.NH", "org.python.bouncycastle.pqc.jcajce.provider.newhope.NHKeyFactorySpi");
         var1.addAlgorithm("KeyPairGenerator.NH", "org.python.bouncycastle.pqc.jcajce.provider.newhope.NHKeyPairGeneratorSpi");
         var1.addAlgorithm("KeyAgreement.NH", "org.python.bouncycastle.pqc.jcajce.provider.newhope.KeyAgreementSpi");
         NHKeyFactorySpi var2 = new NHKeyFactorySpi();
         this.registerOid(var1, PQCObjectIdentifiers.newHope, "NH", var2);
      }
   }
}
