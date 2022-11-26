package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException;

public class KeyFactorySpi extends BaseKeyFactorySpi {
   protected KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      if (var2.isAssignableFrom(RSAPublicKeySpec.class) && var1 instanceof RSAPublicKey) {
         RSAPublicKey var5 = (RSAPublicKey)var1;
         return new RSAPublicKeySpec(var5.getModulus(), var5.getPublicExponent());
      } else if (var2.isAssignableFrom(RSAPrivateKeySpec.class) && var1 instanceof RSAPrivateKey) {
         RSAPrivateKey var4 = (RSAPrivateKey)var1;
         return new RSAPrivateKeySpec(var4.getModulus(), var4.getPrivateExponent());
      } else if (var2.isAssignableFrom(RSAPrivateCrtKeySpec.class) && var1 instanceof RSAPrivateCrtKey) {
         RSAPrivateCrtKey var3 = (RSAPrivateCrtKey)var1;
         return new RSAPrivateCrtKeySpec(var3.getModulus(), var3.getPublicExponent(), var3.getPrivateExponent(), var3.getPrimeP(), var3.getPrimeQ(), var3.getPrimeExponentP(), var3.getPrimeExponentQ(), var3.getCrtCoefficient());
      } else {
         return super.engineGetKeySpec(var1, var2);
      }
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      if (var1 instanceof RSAPublicKey) {
         return new BCRSAPublicKey((RSAPublicKey)var1);
      } else if (var1 instanceof RSAPrivateCrtKey) {
         return new BCRSAPrivateCrtKey((RSAPrivateCrtKey)var1);
      } else if (var1 instanceof RSAPrivateKey) {
         return new BCRSAPrivateKey((RSAPrivateKey)var1);
      } else {
         throw new InvalidKeyException("key type unknown");
      }
   }

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof PKCS8EncodedKeySpec) {
         try {
            return this.generatePrivate(PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec)var1).getEncoded()));
         } catch (Exception var5) {
            try {
               return new BCRSAPrivateCrtKey(org.python.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(((PKCS8EncodedKeySpec)var1).getEncoded()));
            } catch (Exception var4) {
               throw new ExtendedInvalidKeySpecException("unable to process key spec: " + var5.toString(), var5);
            }
         }
      } else if (var1 instanceof RSAPrivateCrtKeySpec) {
         return new BCRSAPrivateCrtKey((RSAPrivateCrtKeySpec)var1);
      } else if (var1 instanceof RSAPrivateKeySpec) {
         return new BCRSAPrivateKey((RSAPrivateKeySpec)var1);
      } else {
         throw new InvalidKeySpecException("Unknown KeySpec type: " + var1.getClass().getName());
      }
   }

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      return (PublicKey)(var1 instanceof RSAPublicKeySpec ? new BCRSAPublicKey((RSAPublicKeySpec)var1) : super.engineGeneratePublic(var1));
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getPrivateKeyAlgorithm().getAlgorithm();
      if (RSAUtil.isRsaOid(var2)) {
         org.python.bouncycastle.asn1.pkcs.RSAPrivateKey var3 = org.python.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(var1.parsePrivateKey());
         return (PrivateKey)(var3.getCoefficient().intValue() == 0 ? new BCRSAPrivateKey(var3) : new BCRSAPrivateCrtKey(var1));
      } else {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      }
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getAlgorithm().getAlgorithm();
      if (RSAUtil.isRsaOid(var2)) {
         return new BCRSAPublicKey(var1);
      } else {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      }
   }
}
