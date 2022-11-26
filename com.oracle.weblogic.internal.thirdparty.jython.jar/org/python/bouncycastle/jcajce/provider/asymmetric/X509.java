package org.python.bouncycastle.jcajce.provider.asymmetric;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class X509 {
   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyFactory.X.509", "org.python.bouncycastle.jcajce.provider.asymmetric.x509.KeyFactory");
         var1.addAlgorithm("Alg.Alias.KeyFactory.X509", "X.509");
         var1.addAlgorithm("CertificateFactory.X.509", "org.python.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory");
         var1.addAlgorithm("Alg.Alias.CertificateFactory.X509", "X.509");
      }
   }
}
