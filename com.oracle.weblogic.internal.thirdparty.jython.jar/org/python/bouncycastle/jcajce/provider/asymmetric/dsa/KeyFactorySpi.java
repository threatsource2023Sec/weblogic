package org.python.bouncycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;

public class KeyFactorySpi extends BaseKeyFactorySpi {
   protected KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      if (var2.isAssignableFrom(DSAPublicKeySpec.class) && var1 instanceof DSAPublicKey) {
         DSAPublicKey var4 = (DSAPublicKey)var1;
         return new DSAPublicKeySpec(var4.getY(), var4.getParams().getP(), var4.getParams().getQ(), var4.getParams().getG());
      } else if (var2.isAssignableFrom(DSAPrivateKeySpec.class) && var1 instanceof DSAPrivateKey) {
         DSAPrivateKey var3 = (DSAPrivateKey)var1;
         return new DSAPrivateKeySpec(var3.getX(), var3.getParams().getP(), var3.getParams().getQ(), var3.getParams().getG());
      } else {
         return super.engineGetKeySpec(var1, var2);
      }
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      if (var1 instanceof DSAPublicKey) {
         return new BCDSAPublicKey((DSAPublicKey)var1);
      } else if (var1 instanceof DSAPrivateKey) {
         return new BCDSAPrivateKey((DSAPrivateKey)var1);
      } else {
         throw new InvalidKeyException("key type unknown");
      }
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getPrivateKeyAlgorithm().getAlgorithm();
      if (DSAUtil.isDsaOid(var2)) {
         return new BCDSAPrivateKey(var1);
      } else {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      }
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws IOException {
      ASN1ObjectIdentifier var2 = var1.getAlgorithm().getAlgorithm();
      if (DSAUtil.isDsaOid(var2)) {
         return new BCDSAPublicKey(var1);
      } else {
         throw new IOException("algorithm identifier " + var2 + " in key not recognised");
      }
   }

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      return (PrivateKey)(var1 instanceof DSAPrivateKeySpec ? new BCDSAPrivateKey((DSAPrivateKeySpec)var1) : super.engineGeneratePrivate(var1));
   }

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof DSAPublicKeySpec) {
         try {
            return new BCDSAPublicKey((DSAPublicKeySpec)var1);
         } catch (final Exception var3) {
            throw new InvalidKeySpecException("invalid KeySpec: " + var3.getMessage()) {
               public Throwable getCause() {
                  return var3;
               }
            };
         }
      } else {
         return super.engineGeneratePublic(var1);
      }
   }
}
