package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

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
import org.python.bouncycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.python.bouncycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;

public class McElieceCCA2KeyFactorySpi extends KeyFactorySpi {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2";

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof X509EncodedKeySpec) {
         byte[] var2 = ((X509EncodedKeySpec)var1).getEncoded();

         SubjectPublicKeyInfo var3;
         try {
            var3 = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2));
         } catch (IOException var6) {
            throw new InvalidKeySpecException(var6.toString());
         }

         try {
            if (PQCObjectIdentifiers.mcElieceCca2.equals(var3.getAlgorithm().getAlgorithm())) {
               McElieceCCA2PublicKey var4 = McElieceCCA2PublicKey.getInstance(var3.parsePublicKey());
               return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeyParameters(var4.getN(), var4.getT(), var4.getG(), Utils.getDigest(var4.getDigest()).getAlgorithmName()));
            } else {
               throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");
            }
         } catch (IOException var5) {
            throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec: " + var5.getMessage());
         }
      } else {
         throw new InvalidKeySpecException("Unsupported key specification: " + var1.getClass() + ".");
      }
   }

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      if (var1 instanceof PKCS8EncodedKeySpec) {
         byte[] var2 = ((PKCS8EncodedKeySpec)var1).getEncoded();

         PrivateKeyInfo var3;
         try {
            var3 = PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(var2));
         } catch (IOException var6) {
            throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec: " + var6);
         }

         try {
            if (PQCObjectIdentifiers.mcElieceCca2.equals(var3.getPrivateKeyAlgorithm().getAlgorithm())) {
               McElieceCCA2PrivateKey var4 = McElieceCCA2PrivateKey.getInstance(var3.parsePrivateKey());
               return new BCMcElieceCCA2PrivateKey(new McElieceCCA2PrivateKeyParameters(var4.getN(), var4.getK(), var4.getField(), var4.getGoppaPoly(), var4.getP(), Utils.getDigest(var4.getDigest()).getAlgorithmName()));
            } else {
               throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");
            }
         } catch (IOException var5) {
            throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec.");
         }
      } else {
         throw new InvalidKeySpecException("Unsupported key specification: " + var1.getClass() + ".");
      }
   }

   public KeySpec getKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      if (var1 instanceof BCMcElieceCCA2PrivateKey) {
         if (PKCS8EncodedKeySpec.class.isAssignableFrom(var2)) {
            return new PKCS8EncodedKeySpec(var1.getEncoded());
         }
      } else {
         if (!(var1 instanceof BCMcElieceCCA2PublicKey)) {
            throw new InvalidKeySpecException("Unsupported key type: " + var1.getClass() + ".");
         }

         if (X509EncodedKeySpec.class.isAssignableFrom(var2)) {
            return new X509EncodedKeySpec(var1.getEncoded());
         }
      }

      throw new InvalidKeySpecException("Unknown key specification: " + var2 + ".");
   }

   public Key translateKey(Key var1) throws InvalidKeyException {
      if (!(var1 instanceof BCMcElieceCCA2PrivateKey) && !(var1 instanceof BCMcElieceCCA2PublicKey)) {
         throw new InvalidKeyException("Unsupported key type.");
      } else {
         return var1;
      }
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws InvalidKeySpecException {
      try {
         ASN1Primitive var2 = var1.parsePublicKey();
         McElieceCCA2PublicKey var3 = McElieceCCA2PublicKey.getInstance(var2);
         return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeyParameters(var3.getN(), var3.getT(), var3.getG(), Utils.getDigest(var3.getDigest()).getAlgorithmName()));
      } catch (IOException var4) {
         throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec");
      }
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws InvalidKeySpecException {
      try {
         ASN1Primitive var2 = var1.parsePrivateKey().toASN1Primitive();
         McElieceCCA2PrivateKey var3 = McElieceCCA2PrivateKey.getInstance(var2);
         return new BCMcElieceCCA2PrivateKey(new McElieceCCA2PrivateKeyParameters(var3.getN(), var3.getK(), var3.getField(), var3.getGoppaPoly(), var3.getP(), (String)null));
      } catch (IOException var4) {
         throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec");
      }
   }

   protected KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      return null;
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      return null;
   }
}
