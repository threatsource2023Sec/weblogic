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
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.pqc.asn1.McEliecePrivateKey;
import org.python.bouncycastle.pqc.asn1.McEliecePublicKey;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;

public class McElieceKeyFactorySpi extends KeyFactorySpi {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";

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
            if (PQCObjectIdentifiers.mcEliece.equals(var3.getAlgorithm().getAlgorithm())) {
               McEliecePublicKey var4 = McEliecePublicKey.getInstance(var3.parsePublicKey());
               return new BCMcEliecePublicKey(new McEliecePublicKeyParameters(var4.getN(), var4.getT(), var4.getG()));
            } else {
               throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");
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
            if (PQCObjectIdentifiers.mcEliece.equals(var3.getPrivateKeyAlgorithm().getAlgorithm())) {
               McEliecePrivateKey var4 = McEliecePrivateKey.getInstance(var3.parsePrivateKey());
               return new BCMcEliecePrivateKey(new McEliecePrivateKeyParameters(var4.getN(), var4.getK(), var4.getField(), var4.getGoppaPoly(), var4.getP1(), var4.getP2(), var4.getSInv()));
            } else {
               throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");
            }
         } catch (IOException var5) {
            throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec.");
         }
      } else {
         throw new InvalidKeySpecException("Unsupported key specification: " + var1.getClass() + ".");
      }
   }

   public KeySpec getKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      if (var1 instanceof BCMcEliecePrivateKey) {
         if (PKCS8EncodedKeySpec.class.isAssignableFrom(var2)) {
            return new PKCS8EncodedKeySpec(var1.getEncoded());
         }
      } else {
         if (!(var1 instanceof BCMcEliecePublicKey)) {
            throw new InvalidKeySpecException("Unsupported key type: " + var1.getClass() + ".");
         }

         if (X509EncodedKeySpec.class.isAssignableFrom(var2)) {
            return new X509EncodedKeySpec(var1.getEncoded());
         }
      }

      throw new InvalidKeySpecException("Unknown key specification: " + var2 + ".");
   }

   public Key translateKey(Key var1) throws InvalidKeyException {
      if (!(var1 instanceof BCMcEliecePrivateKey) && !(var1 instanceof BCMcEliecePublicKey)) {
         throw new InvalidKeyException("Unsupported key type.");
      } else {
         return var1;
      }
   }

   public PublicKey generatePublic(SubjectPublicKeyInfo var1) throws InvalidKeySpecException {
      try {
         ASN1Primitive var2 = var1.parsePublicKey();
         McEliecePublicKey var3 = McEliecePublicKey.getInstance(var2);
         return new BCMcEliecePublicKey(new McEliecePublicKeyParameters(var3.getN(), var3.getT(), var3.getG()));
      } catch (IOException var4) {
         throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec");
      }
   }

   public PrivateKey generatePrivate(PrivateKeyInfo var1) throws InvalidKeySpecException {
      try {
         ASN1Primitive var2 = var1.parsePrivateKey().toASN1Primitive();
         McEliecePrivateKey var3 = McEliecePrivateKey.getInstance(var2);
         return new BCMcEliecePrivateKey(new McEliecePrivateKeyParameters(var3.getN(), var3.getK(), var3.getField(), var3.getGoppaPoly(), var3.getP1(), var3.getP2(), var3.getSInv()));
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

   private static Digest getDigest(AlgorithmIdentifier var0) {
      return new SHA256Digest();
   }
}
