package org.python.bouncycastle.crypto.util;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.python.bouncycastle.asn1.sec.ECPrivateKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.DSAParameter;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DSAParameters;
import org.python.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class PrivateKeyInfoFactory {
   private PrivateKeyInfoFactory() {
   }

   public static PrivateKeyInfo createPrivateKeyInfo(AsymmetricKeyParameter var0) throws IOException {
      if (var0 instanceof RSAKeyParameters) {
         RSAPrivateCrtKeyParameters var7 = (RSAPrivateCrtKeyParameters)var0;
         return new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPrivateKey(var7.getModulus(), var7.getPublicExponent(), var7.getExponent(), var7.getP(), var7.getQ(), var7.getDP(), var7.getDQ(), var7.getQInv()));
      } else if (var0 instanceof DSAPrivateKeyParameters) {
         DSAPrivateKeyParameters var6 = (DSAPrivateKeyParameters)var0;
         DSAParameters var8 = var6.getParameters();
         return new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(var8.getP(), var8.getQ(), var8.getG())), new ASN1Integer(var6.getX()));
      } else if (var0 instanceof ECPrivateKeyParameters) {
         ECPrivateKeyParameters var1 = (ECPrivateKeyParameters)var0;
         ECDomainParameters var2 = var1.getParameters();
         X962Parameters var3;
         int var4;
         if (var2 == null) {
            var3 = new X962Parameters(DERNull.INSTANCE);
            var4 = var1.getD().bitLength();
         } else if (var2 instanceof ECNamedDomainParameters) {
            var3 = new X962Parameters(((ECNamedDomainParameters)var2).getName());
            var4 = var2.getN().bitLength();
         } else {
            X9ECParameters var5 = new X9ECParameters(var2.getCurve(), var2.getG(), var2.getN(), var2.getH(), var2.getSeed());
            var3 = new X962Parameters(var5);
            var4 = var2.getN().bitLength();
         }

         return new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var3), new ECPrivateKey(var4, var1.getD(), var3));
      } else {
         throw new IOException("key parameters not recognised.");
      }
   }
}
