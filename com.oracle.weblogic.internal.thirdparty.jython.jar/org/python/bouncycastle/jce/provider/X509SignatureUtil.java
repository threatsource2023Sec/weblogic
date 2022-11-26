package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Null;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.python.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;

class X509SignatureUtil {
   private static final ASN1Null derNull;

   static void setSignatureParameters(Signature var0, ASN1Encodable var1) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      if (var1 != null && !derNull.equals(var1)) {
         AlgorithmParameters var2 = AlgorithmParameters.getInstance(var0.getAlgorithm(), var0.getProvider());

         try {
            var2.init(var1.toASN1Primitive().getEncoded());
         } catch (IOException var5) {
            throw new SignatureException("IOException decoding parameters: " + var5.getMessage());
         }

         if (var0.getAlgorithm().endsWith("MGF1")) {
            try {
               var0.setParameter(var2.getParameterSpec(PSSParameterSpec.class));
            } catch (GeneralSecurityException var4) {
               throw new SignatureException("Exception extracting parameters: " + var4.getMessage());
            }
         }
      }

   }

   static String getSignatureName(AlgorithmIdentifier var0) {
      ASN1Encodable var1 = var0.getParameters();
      if (var1 != null && !derNull.equals(var1)) {
         if (var0.getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            RSASSAPSSparams var3 = RSASSAPSSparams.getInstance(var1);
            return getDigestAlgName(var3.getHashAlgorithm().getAlgorithm()) + "withRSAandMGF1";
         }

         if (var0.getAlgorithm().equals(X9ObjectIdentifiers.ecdsa_with_SHA2)) {
            ASN1Sequence var2 = ASN1Sequence.getInstance(var1);
            return getDigestAlgName(ASN1ObjectIdentifier.getInstance(var2.getObjectAt(0))) + "withECDSA";
         }
      }

      return var0.getAlgorithm().getId();
   }

   private static String getDigestAlgName(ASN1ObjectIdentifier var0) {
      if (PKCSObjectIdentifiers.md5.equals(var0)) {
         return "MD5";
      } else if (OIWObjectIdentifiers.idSHA1.equals(var0)) {
         return "SHA1";
      } else if (NISTObjectIdentifiers.id_sha224.equals(var0)) {
         return "SHA224";
      } else if (NISTObjectIdentifiers.id_sha256.equals(var0)) {
         return "SHA256";
      } else if (NISTObjectIdentifiers.id_sha384.equals(var0)) {
         return "SHA384";
      } else if (NISTObjectIdentifiers.id_sha512.equals(var0)) {
         return "SHA512";
      } else if (TeleTrusTObjectIdentifiers.ripemd128.equals(var0)) {
         return "RIPEMD128";
      } else if (TeleTrusTObjectIdentifiers.ripemd160.equals(var0)) {
         return "RIPEMD160";
      } else if (TeleTrusTObjectIdentifiers.ripemd256.equals(var0)) {
         return "RIPEMD256";
      } else {
         return CryptoProObjectIdentifiers.gostR3411.equals(var0) ? "GOST3411" : var0.getId();
      }
   }

   static {
      derNull = DERNull.INSTANCE;
   }
}
