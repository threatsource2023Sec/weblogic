package org.python.bouncycastle.jcajce.provider.keystore;

import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class PKCS12 {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.";

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyStore.PKCS12", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$BCPKCS12KeyStore");
         var1.addAlgorithm("KeyStore.BCPKCS12", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$BCPKCS12KeyStore");
         var1.addAlgorithm("KeyStore.PKCS12-DEF", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$DefPKCS12KeyStore");
         var1.addAlgorithm("KeyStore.PKCS12-3DES-40RC2", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$BCPKCS12KeyStore");
         var1.addAlgorithm("KeyStore.PKCS12-3DES-3DES", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$BCPKCS12KeyStore3DES");
         var1.addAlgorithm("KeyStore.PKCS12-DEF-3DES-40RC2", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$DefPKCS12KeyStore");
         var1.addAlgorithm("KeyStore.PKCS12-DEF-3DES-3DES", "org.python.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$DefPKCS12KeyStore3DES");
      }
   }
}
