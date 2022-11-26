package org.python.bouncycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.oiw.ElGamalParameter;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.DHParameter;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.python.bouncycastle.asn1.sec.ECPrivateKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.DSAParameter;
import org.python.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.ec.CustomNamedCurves;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DSAParameters;
import org.python.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ElGamalParameters;
import org.python.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class PrivateKeyFactory {
   public static AsymmetricKeyParameter createKey(byte[] var0) throws IOException {
      return createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(var0)));
   }

   public static AsymmetricKeyParameter createKey(InputStream var0) throws IOException {
      return createKey(PrivateKeyInfo.getInstance((new ASN1InputStream(var0)).readObject()));
   }

   public static AsymmetricKeyParameter createKey(PrivateKeyInfo var0) throws IOException {
      AlgorithmIdentifier var1 = var0.getPrivateKeyAlgorithm();
      if (var1.getAlgorithm().equals(PKCSObjectIdentifiers.rsaEncryption)) {
         RSAPrivateKey var10 = RSAPrivateKey.getInstance(var0.parsePrivateKey());
         return new RSAPrivateCrtKeyParameters(var10.getModulus(), var10.getPublicExponent(), var10.getPrivateExponent(), var10.getPrime1(), var10.getPrime2(), var10.getExponent1(), var10.getExponent2(), var10.getCoefficient());
      } else {
         ASN1Integer var12;
         if (var1.getAlgorithm().equals(PKCSObjectIdentifiers.dhKeyAgreement)) {
            DHParameter var9 = DHParameter.getInstance(var1.getParameters());
            var12 = (ASN1Integer)var0.parsePrivateKey();
            BigInteger var16 = var9.getL();
            int var17 = var16 == null ? 0 : var16.intValue();
            DHParameters var18 = new DHParameters(var9.getP(), var9.getG(), (BigInteger)null, var17);
            return new DHPrivateKeyParameters(var12.getValue(), var18);
         } else if (var1.getAlgorithm().equals(OIWObjectIdentifiers.elGamalAlgorithm)) {
            ElGamalParameter var8 = ElGamalParameter.getInstance(var1.getParameters());
            var12 = (ASN1Integer)var0.parsePrivateKey();
            return new ElGamalPrivateKeyParameters(var12.getValue(), new ElGamalParameters(var8.getP(), var8.getG()));
         } else if (var1.getAlgorithm().equals(X9ObjectIdentifiers.id_dsa)) {
            ASN1Integer var7 = (ASN1Integer)var0.parsePrivateKey();
            ASN1Encodable var11 = var1.getParameters();
            DSAParameters var13 = null;
            if (var11 != null) {
               DSAParameter var15 = DSAParameter.getInstance(var11.toASN1Primitive());
               var13 = new DSAParameters(var15.getP(), var15.getQ(), var15.getG());
            }

            return new DSAPrivateKeyParameters(var7.getValue(), var13);
         } else if (var1.getAlgorithm().equals(X9ObjectIdentifiers.id_ecPublicKey)) {
            X962Parameters var2 = new X962Parameters((ASN1Primitive)var1.getParameters());
            X9ECParameters var3;
            Object var4;
            if (var2.isNamedCurve()) {
               ASN1ObjectIdentifier var5 = (ASN1ObjectIdentifier)var2.getParameters();
               var3 = CustomNamedCurves.getByOID(var5);
               if (var3 == null) {
                  var3 = ECNamedCurveTable.getByOID(var5);
               }

               var4 = new ECNamedDomainParameters(var5, var3.getCurve(), var3.getG(), var3.getN(), var3.getH(), var3.getSeed());
            } else {
               var3 = X9ECParameters.getInstance(var2.getParameters());
               var4 = new ECDomainParameters(var3.getCurve(), var3.getG(), var3.getN(), var3.getH(), var3.getSeed());
            }

            ECPrivateKey var14 = ECPrivateKey.getInstance(var0.parsePrivateKey());
            BigInteger var6 = var14.getKey();
            return new ECPrivateKeyParameters(var6, (ECDomainParameters)var4);
         } else {
            throw new RuntimeException("algorithm identifier in key not recognised");
         }
      }
   }
}
