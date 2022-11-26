package org.python.bouncycastle.jcajce.provider.asymmetric;

import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class GM {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.asymmetric.ec.";
   private static final Map generalSm2Attributes = new HashMap();

   static {
      generalSm2Attributes.put("SupportedKeyClasses", "java.security.interfaces.ECPublicKey|java.security.interfaces.ECPrivateKey");
      generalSm2Attributes.put("SupportedKeyFormats", "PKCS#8|X.509");
   }

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Signature.SM3WITHSM2", "org.python.bouncycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi$sm3WithSM2");
         var1.addAlgorithm("Alg.Alias.Signature." + GMObjectIdentifiers.sm2sign_with_sm3, "SM3WITHSM2");
      }
   }
}
