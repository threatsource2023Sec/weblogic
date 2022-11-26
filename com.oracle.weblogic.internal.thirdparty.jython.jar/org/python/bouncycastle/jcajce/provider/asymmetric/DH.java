package org.python.bouncycastle.jcajce.provider.asymmetric;

import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.jcajce.provider.asymmetric.dh.KeyFactorySpi;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class DH {
   private static final String PREFIX = "org.python.bouncycastle.jcajce.provider.asymmetric.dh.";
   private static final Map generalDhAttributes = new HashMap();

   static {
      generalDhAttributes.put("SupportedKeyClasses", "javax.crypto.interfaces.DHPublicKey|javax.crypto.interfaces.DHPrivateKey");
      generalDhAttributes.put("SupportedKeyFormats", "PKCS#8|X.509");
   }

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("KeyPairGenerator.DH", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.KeyPairGeneratorSpi");
         var1.addAlgorithm("Alg.Alias.KeyPairGenerator.DIFFIEHELLMAN", "DH");
         var1.addAttributes("KeyAgreement.DH", DH.generalDhAttributes);
         var1.addAlgorithm("KeyAgreement.DH", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi");
         var1.addAlgorithm("Alg.Alias.KeyAgreement.DIFFIEHELLMAN", "DH");
         var1.addAlgorithm("KeyAgreement", PKCSObjectIdentifiers.id_alg_ESDH, "org.python.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithRFC2631KDF");
         var1.addAlgorithm("KeyAgreement", PKCSObjectIdentifiers.id_alg_SSDH, "org.python.bouncycastle.jcajce.provider.asymmetric.dh.KeyAgreementSpi$DHwithRFC2631KDF");
         var1.addAlgorithm("KeyFactory.DH", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.KeyFactorySpi");
         var1.addAlgorithm("Alg.Alias.KeyFactory.DIFFIEHELLMAN", "DH");
         var1.addAlgorithm("AlgorithmParameters.DH", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.AlgorithmParametersSpi");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.DIFFIEHELLMAN", "DH");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.DIFFIEHELLMAN", "DH");
         var1.addAlgorithm("AlgorithmParameterGenerator.DH", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.AlgorithmParameterGeneratorSpi");
         var1.addAlgorithm("Cipher.IES", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IES");
         var1.addAlgorithm("Cipher.IESwithAES-CBC", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IESwithAESCBC");
         var1.addAlgorithm("Cipher.IESWITHAES-CBC", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IESwithAESCBC");
         var1.addAlgorithm("Cipher.IESWITHDESEDE-CBC", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IESwithDESedeCBC");
         var1.addAlgorithm("Cipher.DHIES", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IES");
         var1.addAlgorithm("Cipher.DHIESwithAES-CBC", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IESwithAESCBC");
         var1.addAlgorithm("Cipher.DHIESWITHAES-CBC", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IESwithAESCBC");
         var1.addAlgorithm("Cipher.DHIESWITHDESEDE-CBC", "org.python.bouncycastle.jcajce.provider.asymmetric.dh.IESCipher$IESwithDESedeCBC");
         this.registerOid(var1, PKCSObjectIdentifiers.dhKeyAgreement, "DH", new KeyFactorySpi());
         this.registerOid(var1, X9ObjectIdentifiers.dhpublicnumber, "DH", new KeyFactorySpi());
      }
   }
}
