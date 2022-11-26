package org.python.bouncycastle.pqc.jcajce.provider.rainbow;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.python.bouncycastle.pqc.asn1.RainbowPrivateKey;
import org.python.bouncycastle.pqc.asn1.RainbowPublicKey;
import org.python.bouncycastle.pqc.jcajce.spec.RainbowPrivateKeySpec;
import org.python.bouncycastle.pqc.jcajce.spec.RainbowPublicKeySpec;

public class RainbowKeyFactorySpi extends KeyFactorySpi implements AsymmetricKeyInfoConverter {
   public PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof RainbowPrivateKeySpec) {
         return new BCRainbowPrivateKey((RainbowPrivateKeySpec)var1);
      } else if (var1 instanceof PKCS8EncodedKeySpec) {
         byte[] var2 = ((PKCS8EncodedKeySpec)var1).getEncoded();

         try {
            return this.generatePrivate(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2)));
         } catch (Exception var4) {
            throw new InvalidKeySpecException(var4.toString());
         }
      } else {
         throw new InvalidKeySpecException("Unsupported key specification: " + var1.getClass() + ".");
      }
   }

   public PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof RainbowPublicKeySpec) {
         return new BCRainbowPublicKey((RainbowPublicKeySpec)var1);
      } else if (var1 instanceof X509EncodedKeySpec) {
         byte[] var2 = ((X509EncodedKeySpec)var1).getEncoded();

         try {
            return this.generatePublic(SubjectPublicKeyInfo.getInstance(var2));
         } catch (Exception var4) {
            throw new InvalidKeySpecException(var4.toString());
         }
      } else {
         throw new InvalidKeySpecException("Unknown key specification: " + var1 + ".");
      }
   }

   public final KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      if (var1 instanceof BCRainbowPrivateKey) {
         if (PKCS8EncodedKeySpec.class.isAssignableFrom(var2)) {
            return new PKCS8EncodedKeySpec(var1.getEncoded());
         }

         if (RainbowPrivateKeySpec.class.isAssignableFrom(var2)) {
            BCRainbowPrivateKey var3 = (BCRainbowPrivateKey)var1;
            return new RainbowPrivateKeySpec(var3.getInvA1(), var3.getB1(), var3.getInvA2(), var3.getB2(), var3.getVi(), var3.getLayers());
         }
      } else {
         if (!(var1 instanceof BCRainbowPublicKey)) {
            throw new InvalidKeySpecException("Unsupported key type: " + var1.getClass() + ".");
         }

         if (X509EncodedKeySpec.class.isAssignableFrom(var2)) {
            return new X509EncodedKeySpec(var1.getEncoded());
         }

         if (RainbowPublicKeySpec.class.isAssignableFrom(var2)) {
            BCRainbowPublicKey var4 = (BCRainbowPublicKey)var1;
            return new RainbowPublicKeySpec(var4.getDocLength(), var4.getCoeffQuadratic(), var4.getCoeffSingular(), var4.getCoeffScalar());
         }
      }

      throw new InvalidKeySpecException("Unknown key specification: " + var2 + ".");
   }

   public final Key engineTranslateKey(Key var1) throws InvalidKeyException {
      if (!(var1 instanceof BCRainbowPrivateKey) && !(var1 instanceof BCRainbowPublicKey)) {
         throw new InvalidKeyException("Unsupported key type");
      } else {
         return var1;
      }
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws IOException {
      RainbowPrivateKey var2 = RainbowPrivateKey.getInstance(var1.parsePrivateKey());
      return new BCRainbowPrivateKey(var2.getInvA1(), var2.getB1(), var2.getInvA2(), var2.getB2(), var2.getVi(), var2.getLayers());
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws IOException {
      RainbowPublicKey var2 = RainbowPublicKey.getInstance(var1.parsePublicKey());
      return new BCRainbowPublicKey(var2.getDocLength(), var2.getCoeffQuadratic(), var2.getCoeffSingular(), var2.getCoeffScalar());
   }
}
