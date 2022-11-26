package org.python.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Null;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.jcajce.util.MessageDigestUtils;

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
            RSASSAPSSparams var7 = RSASSAPSSparams.getInstance(var1);
            return getDigestAlgName(var7.getHashAlgorithm().getAlgorithm()) + "withRSAandMGF1";
         }

         if (var0.getAlgorithm().equals(X9ObjectIdentifiers.ecdsa_with_SHA2)) {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var1);
            return getDigestAlgName((ASN1ObjectIdentifier)var6.getObjectAt(0)) + "withECDSA";
         }
      }

      Provider var2 = Security.getProvider("BC");
      if (var2 != null) {
         String var3 = var2.getProperty("Alg.Alias.Signature." + var0.getAlgorithm().getId());
         if (var3 != null) {
            return var3;
         }
      }

      Provider[] var8 = Security.getProviders();

      for(int var4 = 0; var4 != var8.length; ++var4) {
         String var5 = var8[var4].getProperty("Alg.Alias.Signature." + var0.getAlgorithm().getId());
         if (var5 != null) {
            return var5;
         }
      }

      return var0.getAlgorithm().getId();
   }

   private static String getDigestAlgName(ASN1ObjectIdentifier var0) {
      String var1 = MessageDigestUtils.getDigestName(var0);
      int var2 = var1.indexOf(45);
      return var2 > 0 && !var1.startsWith("SHA3") ? var1.substring(0, var2) + var1.substring(var2 + 1) : MessageDigestUtils.getDigestName(var0);
   }

   static {
      derNull = DERNull.INSTANCE;
   }
}
