package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

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
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECParameterSpec;

public class KeyFactorySpi extends BaseKeyFactorySpi implements AsymmetricKeyInfoConverter {
   String algorithm;
   ProviderConfiguration configuration;

   KeyFactorySpi(String var1, ProviderConfiguration var2) {
      this.algorithm = var1;
      this.configuration = var2;
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      if (var1 instanceof ECPublicKey) {
         return new BCECPublicKey((ECPublicKey)var1, this.configuration);
      } else if (var1 instanceof ECPrivateKey) {
         return new BCECPrivateKey((ECPrivateKey)var1, this.configuration);
      } else {
         throw new InvalidKeyException("key type unknown");
      }
   }

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

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof org.python.bouncycastle.jce.spec.ECPrivateKeySpec) {
         return new BCECPrivateKey(this.algorithm, (org.python.bouncycastle.jce.spec.ECPrivateKeySpec)var1, this.configuration);
      } else {
         return (PrivateKey)(var1 instanceof ECPrivateKeySpec ? new BCECPrivateKey(this.algorithm, (ECPrivateKeySpec)var1, this.configuration) : super.engineGeneratePrivate(var1));
      }
   }

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      try {
         if (var1 instanceof org.python.bouncycastle.jce.spec.ECPublicKeySpec) {
            return new BCECPublicKey(this.algorithm, (org.python.bouncycastle.jce.spec.ECPublicKeySpec)var1, this.configuration);
         }

         if (var1 instanceof ECPublicKeySpec) {
            return new BCECPublicKey(this.algorithm, (ECPublicKeySpec)var1, this.configuration);
         }
      } catch (Exception var3) {
         throw new InvalidKeySpecException("invalid KeySpec: " + var3.getMessage(), var3);
      }

      return super.engineGeneratePublic(var1);
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getPrivateKeyAlgorithm().getAlgorithm();
      if (var2.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
         return new BCECPrivateKey(this.algorithm, var1, this.configuration);
      } else {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      }
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getAlgorithm().getAlgorithm();
      if (var2.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
         return new BCECPublicKey(this.algorithm, var1, this.configuration);
      } else {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      }
   }

   public static class EC extends KeyFactorySpi {
      public EC() {
         super("EC", BouncyCastleProvider.CONFIGURATION);
      }
   }

   public static class ECDH extends KeyFactorySpi {
      public ECDH() {
         super("ECDH", BouncyCastleProvider.CONFIGURATION);
      }
   }

   public static class ECDHC extends KeyFactorySpi {
      public ECDHC() {
         super("ECDHC", BouncyCastleProvider.CONFIGURATION);
      }
   }

   public static class ECDSA extends KeyFactorySpi {
      public ECDSA() {
         super("ECDSA", BouncyCastleProvider.CONFIGURATION);
      }
   }

   public static class ECGOST3410 extends KeyFactorySpi {
      public ECGOST3410() {
         super("ECGOST3410", BouncyCastleProvider.CONFIGURATION);
      }
   }

   public static class ECMQV extends KeyFactorySpi {
      public ECMQV() {
         super("ECMQV", BouncyCastleProvider.CONFIGURATION);
      }
   }
}
