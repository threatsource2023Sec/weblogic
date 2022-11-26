package org.python.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.Provider;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.jcajce.util.AlgorithmParametersUtils;

class CMSUtils {
   private static final Set mqvAlgs = new HashSet();
   private static final Set ecAlgs = new HashSet();

   static boolean isMQV(ASN1ObjectIdentifier var0) {
      return mqvAlgs.contains(var0);
   }

   static boolean isEC(ASN1ObjectIdentifier var0) {
      return ecAlgs.contains(var0);
   }

   static boolean isRFC2631(ASN1ObjectIdentifier var0) {
      return var0.equals(PKCSObjectIdentifiers.id_alg_ESDH) || var0.equals(PKCSObjectIdentifiers.id_alg_SSDH);
   }

   static IssuerAndSerialNumber getIssuerAndSerialNumber(X509Certificate var0) throws CertificateEncodingException {
      Certificate var1 = Certificate.getInstance(var0.getEncoded());
      return new IssuerAndSerialNumber(var1.getIssuer(), var0.getSerialNumber());
   }

   static byte[] getSubjectKeyId(X509Certificate var0) {
      byte[] var1 = var0.getExtensionValue(Extension.subjectKeyIdentifier.getId());
      return var1 != null ? ASN1OctetString.getInstance(ASN1OctetString.getInstance(var1).getOctets()).getOctets() : null;
   }

   static EnvelopedDataHelper createContentHelper(Provider var0) {
      return var0 != null ? new EnvelopedDataHelper(new ProviderJcaJceExtHelper(var0)) : new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
   }

   static EnvelopedDataHelper createContentHelper(String var0) {
      return var0 != null ? new EnvelopedDataHelper(new NamedJcaJceExtHelper(var0)) : new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
   }

   static ASN1Encodable extractParameters(AlgorithmParameters var0) throws CMSException {
      try {
         return AlgorithmParametersUtils.extractParameters(var0);
      } catch (IOException var2) {
         throw new CMSException("cannot extract parameters: " + var2.getMessage(), var2);
      }
   }

   static void loadParameters(AlgorithmParameters var0, ASN1Encodable var1) throws CMSException {
      try {
         AlgorithmParametersUtils.loadParameters(var0, var1);
      } catch (IOException var3) {
         throw new CMSException("error encoding algorithm parameters.", var3);
      }
   }

   static {
      mqvAlgs.add(X9ObjectIdentifiers.mqvSinglePass_sha1kdf_scheme);
      mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha224kdf_scheme);
      mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha256kdf_scheme);
      mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha384kdf_scheme);
      mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha512kdf_scheme);
      ecAlgs.add(X9ObjectIdentifiers.dhSinglePass_cofactorDH_sha1kdf_scheme);
      ecAlgs.add(X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha224kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha224kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha256kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha256kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha384kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha384kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha512kdf_scheme);
      ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha512kdf_scheme);
   }
}
