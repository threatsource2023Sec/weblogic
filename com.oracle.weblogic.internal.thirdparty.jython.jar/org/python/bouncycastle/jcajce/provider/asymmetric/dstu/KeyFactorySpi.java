package org.python.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECParameterSpec;

public class KeyFactorySpi extends BaseKeyFactorySpi {
   protected KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      ECParameterSpec var4;
      ECPublicKey var5;
      if (var2.isAssignableFrom(ECPublicKeySpec.class) && var1 instanceof ECPublicKey) {
         var5 = (ECPublicKey)var1;
         if (var5.getParams() != null) {
            return new ECPublicKeySpec(var5.getW(), var5.getParams());
         } else {
            var4 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new ECPublicKeySpec(var5.getW(), EC5Util.convertSpec(EC5Util.convertCurve(var4.getCurve(), var4.getSeed()), var4));
         }
      } else {
         ECPrivateKey var3;
         if (var2.isAssignableFrom(ECPrivateKeySpec.class) && var1 instanceof ECPrivateKey) {
            var3 = (ECPrivateKey)var1;
            if (var3.getParams() != null) {
               return new ECPrivateKeySpec(var3.getS(), var3.getParams());
            } else {
               var4 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
               return new ECPrivateKeySpec(var3.getS(), EC5Util.convertSpec(EC5Util.convertCurve(var4.getCurve(), var4.getSeed()), var4));
            }
         } else if (var2.isAssignableFrom(org.python.bouncycastle.jce.spec.ECPublicKeySpec.class) && var1 instanceof ECPublicKey) {
            var5 = (ECPublicKey)var1;
            if (var5.getParams() != null) {
               return new org.python.bouncycastle.jce.spec.ECPublicKeySpec(EC5Util.convertPoint(var5.getParams(), var5.getW(), false), EC5Util.convertSpec(var5.getParams(), false));
            } else {
               var4 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
               return new org.python.bouncycastle.jce.spec.ECPublicKeySpec(EC5Util.convertPoint(var5.getParams(), var5.getW(), false), var4);
            }
         } else if (var2.isAssignableFrom(org.python.bouncycastle.jce.spec.ECPrivateKeySpec.class) && var1 instanceof ECPrivateKey) {
            var3 = (ECPrivateKey)var1;
            if (var3.getParams() != null) {
               return new org.python.bouncycastle.jce.spec.ECPrivateKeySpec(var3.getS(), EC5Util.convertSpec(var3.getParams(), false));
            } else {
               var4 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
               return new org.python.bouncycastle.jce.spec.ECPrivateKeySpec(var3.getS(), var4);
            }
         } else {
            return super.engineGetKeySpec(var1, var2);
         }
      }
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      throw new InvalidKeyException("key type unknown");
   }

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof org.python.bouncycastle.jce.spec.ECPrivateKeySpec) {
         return new BCDSTU4145PrivateKey((org.python.bouncycastle.jce.spec.ECPrivateKeySpec)var1);
      } else {
         return (PrivateKey)(var1 instanceof ECPrivateKeySpec ? new BCDSTU4145PrivateKey((ECPrivateKeySpec)var1) : super.engineGeneratePrivate(var1));
      }
   }

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof org.python.bouncycastle.jce.spec.ECPublicKeySpec) {
         return new BCDSTU4145PublicKey((org.python.bouncycastle.jce.spec.ECPublicKeySpec)var1, BouncyCastleProvider.CONFIGURATION);
      } else {
         return (PublicKey)(var1 instanceof ECPublicKeySpec ? new BCDSTU4145PublicKey((ECPublicKeySpec)var1) : super.engineGeneratePublic(var1));
      }
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getPrivateKeyAlgorithm().getAlgorithm();
      if (!var2.equals(UAObjectIdentifiers.dstu4145le) && !var2.equals(UAObjectIdentifiers.dstu4145be)) {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      } else {
         return new BCDSTU4145PrivateKey(var1);
      }
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getAlgorithm().getAlgorithm();
      if (!var2.equals(UAObjectIdentifiers.dstu4145le) && !var2.equals(UAObjectIdentifiers.dstu4145be)) {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      } else {
         return new BCDSTU4145PublicKey(var1);
      }
   }
}
