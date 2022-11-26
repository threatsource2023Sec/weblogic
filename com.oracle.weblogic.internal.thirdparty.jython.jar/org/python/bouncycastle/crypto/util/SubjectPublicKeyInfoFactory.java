package org.python.bouncycastle.crypto.util;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.DSAParameter;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.asn1.x9.X9ECPoint;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DSAParameters;
import org.python.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;

public class SubjectPublicKeyInfoFactory {
   private SubjectPublicKeyInfoFactory() {
   }

   public static SubjectPublicKeyInfo createSubjectPublicKeyInfo(AsymmetricKeyParameter var0) throws IOException {
      if (var0 instanceof RSAKeyParameters) {
         RSAKeyParameters var6 = (RSAKeyParameters)var0;
         return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPublicKey(var6.getModulus(), var6.getExponent()));
      } else if (var0 instanceof DSAPublicKeyParameters) {
         DSAPublicKeyParameters var5 = (DSAPublicKeyParameters)var0;
         DSAParameter var7 = null;
         DSAParameters var8 = var5.getParameters();
         if (var8 != null) {
            var7 = new DSAParameter(var8.getP(), var8.getQ(), var8.getG());
         }

         return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, var7), new ASN1Integer(var5.getY()));
      } else if (var0 instanceof ECPublicKeyParameters) {
         ECPublicKeyParameters var1 = (ECPublicKeyParameters)var0;
         ECDomainParameters var2 = var1.getParameters();
         X962Parameters var3;
         if (var2 == null) {
            var3 = new X962Parameters(DERNull.INSTANCE);
         } else if (var2 instanceof ECNamedDomainParameters) {
            var3 = new X962Parameters(((ECNamedDomainParameters)var2).getName());
         } else {
            X9ECParameters var4 = new X9ECParameters(var2.getCurve(), var2.getG(), var2.getN(), var2.getH(), var2.getSeed());
            var3 = new X962Parameters(var4);
         }

         ASN1OctetString var9 = (ASN1OctetString)(new X9ECPoint(var1.getQ())).toASN1Primitive();
         return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var3), var9.getOctets());
      } else {
         throw new IOException("key parameters not recognised.");
      }
   }
}
