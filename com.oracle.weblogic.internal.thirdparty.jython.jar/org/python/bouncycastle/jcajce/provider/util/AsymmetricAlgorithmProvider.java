package org.python.bouncycastle.jcajce.provider.util;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;

public abstract class AsymmetricAlgorithmProvider extends AlgorithmProvider {
   protected void addSignatureAlgorithm(ConfigurableProvider var1, String var2, String var3, String var4, ASN1ObjectIdentifier var5) {
      String var6 = var2 + "WITH" + var3;
      String var7 = var2 + "with" + var3;
      String var8 = var2 + "With" + var3;
      String var9 = var2 + "/" + var3;
      var1.addAlgorithm("Signature." + var6, var4);
      var1.addAlgorithm("Alg.Alias.Signature." + var7, var6);
      var1.addAlgorithm("Alg.Alias.Signature." + var8, var6);
      var1.addAlgorithm("Alg.Alias.Signature." + var9, var6);
      var1.addAlgorithm("Alg.Alias.Signature." + var5, var6);
      var1.addAlgorithm("Alg.Alias.Signature.OID." + var5, var6);
   }

   protected void registerOid(ConfigurableProvider var1, ASN1ObjectIdentifier var2, String var3, AsymmetricKeyInfoConverter var4) {
      var1.addAlgorithm("Alg.Alias.KeyFactory." + var2, var3);
      var1.addAlgorithm("Alg.Alias.KeyPairGenerator." + var2, var3);
      var1.addKeyInfoConverter(var2, var4);
   }

   protected void registerOidAlgorithmParameters(ConfigurableProvider var1, ASN1ObjectIdentifier var2, String var3) {
      var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + var2, var3);
   }

   protected void registerOidAlgorithmParameterGenerator(ConfigurableProvider var1, ASN1ObjectIdentifier var2, String var3) {
      var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + var2, var3);
      var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + var2, var3);
   }
}
