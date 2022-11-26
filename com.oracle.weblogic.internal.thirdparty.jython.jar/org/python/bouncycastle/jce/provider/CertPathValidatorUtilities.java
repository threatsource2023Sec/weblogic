package org.python.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Enumerated;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1OutputStream;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x500.style.RFC4519Style;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.python.bouncycastle.asn1.x509.CRLDistPoint;
import org.python.bouncycastle.asn1.x509.DistributionPoint;
import org.python.bouncycastle.asn1.x509.DistributionPointName;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.asn1.x509.PolicyInformation;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.jcajce.PKIXCRLStore;
import org.python.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.python.bouncycastle.jcajce.PKIXCertStore;
import org.python.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.python.bouncycastle.jcajce.PKIXExtendedParameters;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.StoreException;
import org.python.bouncycastle.x509.X509AttributeCertificate;

class CertPathValidatorUtilities {
   protected static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
   protected static final String CERTIFICATE_POLICIES;
   protected static final String BASIC_CONSTRAINTS;
   protected static final String POLICY_MAPPINGS;
   protected static final String SUBJECT_ALTERNATIVE_NAME;
   protected static final String NAME_CONSTRAINTS;
   protected static final String KEY_USAGE;
   protected static final String INHIBIT_ANY_POLICY;
   protected static final String ISSUING_DISTRIBUTION_POINT;
   protected static final String DELTA_CRL_INDICATOR;
   protected static final String POLICY_CONSTRAINTS;
   protected static final String FRESHEST_CRL;
   protected static final String CRL_DISTRIBUTION_POINTS;
   protected static final String AUTHORITY_KEY_IDENTIFIER;
   protected static final String ANY_POLICY = "2.5.29.32.0";
   protected static final String CRL_NUMBER;
   protected static final int KEY_CERT_SIGN = 5;
   protected static final int CRL_SIGN = 6;
   protected static final String[] crlReasons;

   protected static TrustAnchor findTrustAnchor(X509Certificate var0, Set var1) throws AnnotatedException {
      return findTrustAnchor(var0, var1, (String)null);
   }

   protected static TrustAnchor findTrustAnchor(X509Certificate var0, Set var1, String var2) throws AnnotatedException {
      TrustAnchor var3 = null;
      PublicKey var4 = null;
      Exception var5 = null;
      X509CertSelector var6 = new X509CertSelector();
      X500Name var7 = PrincipalUtils.getEncodedIssuerPrincipal(var0);

      try {
         var6.setSubject(var7.getEncoded());
      } catch (IOException var12) {
         throw new AnnotatedException("Cannot set subject search criteria for trust anchor.", var12);
      }

      Iterator var8 = var1.iterator();

      while(var8.hasNext() && var3 == null) {
         var3 = (TrustAnchor)var8.next();
         if (var3.getTrustedCert() != null) {
            if (var6.match(var3.getTrustedCert())) {
               var4 = var3.getTrustedCert().getPublicKey();
            } else {
               var3 = null;
            }
         } else if (var3.getCAName() != null && var3.getCAPublicKey() != null) {
            try {
               X500Name var9 = PrincipalUtils.getCA(var3);
               if (var7.equals(var9)) {
                  var4 = var3.getCAPublicKey();
               } else {
                  var3 = null;
               }
            } catch (IllegalArgumentException var11) {
               var3 = null;
            }
         } else {
            var3 = null;
         }

         if (var4 != null) {
            try {
               verifyX509Certificate(var0, var4, var2);
            } catch (Exception var10) {
               var5 = var10;
               var3 = null;
               var4 = null;
            }
         }
      }

      if (var3 == null && var5 != null) {
         throw new AnnotatedException("TrustAnchor found but certificate validation failed.", var5);
      } else {
         return var3;
      }
   }

   static List getAdditionalStoresFromAltNames(byte[] var0, Map var1) throws CertificateParsingException {
      if (var0 != null) {
         GeneralNames var2 = GeneralNames.getInstance(ASN1OctetString.getInstance(var0).getOctets());
         GeneralName[] var3 = var2.getNames();
         ArrayList var4 = new ArrayList();

         for(int var5 = 0; var5 != var3.length; ++var5) {
            GeneralName var6 = var3[var5];
            PKIXCertStore var7 = (PKIXCertStore)var1.get(var6);
            if (var7 != null) {
               var4.add(var7);
            }
         }

         return var4;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   protected static Date getValidDate(PKIXExtendedParameters var0) {
      Date var1 = var0.getDate();
      if (var1 == null) {
         var1 = new Date();
      }

      return var1;
   }

   protected static boolean isSelfIssued(X509Certificate var0) {
      return var0.getSubjectDN().equals(var0.getIssuerDN());
   }

   protected static ASN1Primitive getExtensionValue(X509Extension var0, String var1) throws AnnotatedException {
      byte[] var2 = var0.getExtensionValue(var1);
      return var2 == null ? null : getObject(var1, var2);
   }

   private static ASN1Primitive getObject(String var0, byte[] var1) throws AnnotatedException {
      try {
         ASN1InputStream var2 = new ASN1InputStream(var1);
         ASN1OctetString var3 = (ASN1OctetString)var2.readObject();
         var2 = new ASN1InputStream(var3.getOctets());
         return var2.readObject();
      } catch (Exception var4) {
         throw new AnnotatedException("exception processing extension " + var0, var4);
      }
   }

   protected static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey var0) throws CertPathValidatorException {
      try {
         ASN1InputStream var1 = new ASN1InputStream(var0.getEncoded());
         SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(var1.readObject());
         return var2.getAlgorithm();
      } catch (Exception var3) {
         throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", var3);
      }
   }

   protected static final Set getQualifierSet(ASN1Sequence var0) throws CertPathValidatorException {
      HashSet var1 = new HashSet();
      if (var0 == null) {
         return var1;
      } else {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         ASN1OutputStream var3 = new ASN1OutputStream(var2);

         for(Enumeration var4 = var0.getObjects(); var4.hasMoreElements(); var2.reset()) {
            try {
               var3.writeObject((ASN1Encodable)var4.nextElement());
               var1.add(new PolicyQualifierInfo(var2.toByteArray()));
            } catch (IOException var6) {
               throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", var6);
            }
         }

         return var1;
      }
   }

   protected static PKIXPolicyNode removePolicyNode(PKIXPolicyNode var0, List[] var1, PKIXPolicyNode var2) {
      PKIXPolicyNode var3 = (PKIXPolicyNode)var2.getParent();
      if (var0 == null) {
         return null;
      } else if (var3 != null) {
         var3.removeChild(var2);
         removePolicyNodeRecurse(var1, var2);
         return var0;
      } else {
         for(int var4 = 0; var4 < var1.length; ++var4) {
            var1[var4] = new ArrayList();
         }

         return null;
      }
   }

   private static void removePolicyNodeRecurse(List[] var0, PKIXPolicyNode var1) {
      var0[var1.getDepth()].remove(var1);
      if (var1.hasChildren()) {
         Iterator var2 = var1.getChildren();

         while(var2.hasNext()) {
            PKIXPolicyNode var3 = (PKIXPolicyNode)var2.next();
            removePolicyNodeRecurse(var0, var3);
         }
      }

   }

   protected static boolean processCertD1i(int var0, List[] var1, ASN1ObjectIdentifier var2, Set var3) {
      List var4 = var1[var0 - 1];

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         PKIXPolicyNode var6 = (PKIXPolicyNode)var4.get(var5);
         Set var7 = var6.getExpectedPolicies();
         if (var7.contains(var2.getId())) {
            HashSet var8 = new HashSet();
            var8.add(var2.getId());
            PKIXPolicyNode var9 = new PKIXPolicyNode(new ArrayList(), var0, var8, var6, var3, var2.getId(), false);
            var6.addChild(var9);
            var1[var0].add(var9);
            return true;
         }
      }

      return false;
   }

   protected static void processCertD1ii(int var0, List[] var1, ASN1ObjectIdentifier var2, Set var3) {
      List var4 = var1[var0 - 1];

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         PKIXPolicyNode var6 = (PKIXPolicyNode)var4.get(var5);
         if ("2.5.29.32.0".equals(var6.getValidPolicy())) {
            HashSet var7 = new HashSet();
            var7.add(var2.getId());
            PKIXPolicyNode var8 = new PKIXPolicyNode(new ArrayList(), var0, var7, var6, var3, var2.getId(), false);
            var6.addChild(var8);
            var1[var0].add(var8);
            return;
         }
      }

   }

   protected static void prepareNextCertB1(int var0, List[] var1, String var2, Map var3, X509Certificate var4) throws AnnotatedException, CertPathValidatorException {
      boolean var5 = false;
      Iterator var6 = var1[var0].iterator();

      PKIXPolicyNode var7;
      while(var6.hasNext()) {
         var7 = (PKIXPolicyNode)var6.next();
         if (var7.getValidPolicy().equals(var2)) {
            var5 = true;
            var7.expectedPolicies = (Set)var3.get(var2);
            break;
         }
      }

      if (!var5) {
         var6 = var1[var0].iterator();

         while(var6.hasNext()) {
            var7 = (PKIXPolicyNode)var6.next();
            if ("2.5.29.32.0".equals(var7.getValidPolicy())) {
               Set var8 = null;
               ASN1Sequence var9 = null;

               try {
                  var9 = DERSequence.getInstance(getExtensionValue(var4, CERTIFICATE_POLICIES));
               } catch (Exception var16) {
                  throw new AnnotatedException("Certificate policies cannot be decoded.", var16);
               }

               Enumeration var10 = var9.getObjects();

               while(var10.hasMoreElements()) {
                  PolicyInformation var11 = null;

                  try {
                     var11 = PolicyInformation.getInstance(var10.nextElement());
                  } catch (Exception var15) {
                     throw new AnnotatedException("Policy information cannot be decoded.", var15);
                  }

                  if ("2.5.29.32.0".equals(var11.getPolicyIdentifier().getId())) {
                     try {
                        var8 = getQualifierSet(var11.getPolicyQualifiers());
                        break;
                     } catch (CertPathValidatorException var14) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be built.", var14);
                     }
                  }
               }

               boolean var17 = false;
               if (var4.getCriticalExtensionOIDs() != null) {
                  var17 = var4.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES);
               }

               PKIXPolicyNode var12 = (PKIXPolicyNode)var7.getParent();
               if ("2.5.29.32.0".equals(var12.getValidPolicy())) {
                  PKIXPolicyNode var13 = new PKIXPolicyNode(new ArrayList(), var0, (Set)var3.get(var2), var12, var8, var2, var17);
                  var12.addChild(var13);
                  var1[var0].add(var13);
               }
               break;
            }
         }
      }

   }

   protected static PKIXPolicyNode prepareNextCertB2(int var0, List[] var1, String var2, PKIXPolicyNode var3) {
      Iterator var4 = var1[var0].iterator();

      while(true) {
         PKIXPolicyNode var5;
         do {
            if (!var4.hasNext()) {
               return var3;
            }

            var5 = (PKIXPolicyNode)var4.next();
         } while(!var5.getValidPolicy().equals(var2));

         PKIXPolicyNode var6 = (PKIXPolicyNode)var5.getParent();
         var6.removeChild(var5);
         var4.remove();

         for(int var7 = var0 - 1; var7 >= 0; --var7) {
            List var8 = var1[var7];

            for(int var9 = 0; var9 < var8.size(); ++var9) {
               PKIXPolicyNode var10 = (PKIXPolicyNode)var8.get(var9);
               if (!var10.hasChildren()) {
                  var3 = removePolicyNode(var3, var1, var10);
                  if (var3 == null) {
                     break;
                  }
               }
            }
         }
      }
   }

   protected static boolean isAnyPolicy(Set var0) {
      return var0 == null || var0.contains("2.5.29.32.0") || var0.isEmpty();
   }

   protected static Collection findCertificates(PKIXCertStoreSelector var0, List var1) throws AnnotatedException {
      LinkedHashSet var2 = new LinkedHashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var4 instanceof Store) {
            Store var5 = (Store)var4;

            try {
               var2.addAll(var5.getMatches(var0));
            } catch (StoreException var7) {
               throw new AnnotatedException("Problem while picking certificates from X.509 store.", var7);
            }
         } else {
            CertStore var9 = (CertStore)var4;

            try {
               var2.addAll(PKIXCertStoreSelector.getCertificates(var0, var9));
            } catch (CertStoreException var8) {
               throw new AnnotatedException("Problem while picking certificates from certificate store.", var8);
            }
         }
      }

      return var2;
   }

   static List getAdditionalStoresFromCRLDistributionPoint(CRLDistPoint var0, Map var1) throws AnnotatedException {
      if (var0 == null) {
         return Collections.EMPTY_LIST;
      } else {
         DistributionPoint[] var2 = null;

         try {
            var2 = var0.getDistributionPoints();
         } catch (Exception var9) {
            throw new AnnotatedException("Distribution points could not be read.", var9);
         }

         ArrayList var3 = new ArrayList();

         for(int var4 = 0; var4 < var2.length; ++var4) {
            DistributionPointName var5 = var2[var4].getDistributionPoint();
            if (var5 != null && var5.getType() == 0) {
               GeneralName[] var6 = GeneralNames.getInstance(var5.getName()).getNames();

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  PKIXCRLStore var8 = (PKIXCRLStore)var1.get(var6[var7]);
                  if (var8 != null) {
                     var3.add(var8);
                  }
               }
            }
         }

         return var3;
      }
   }

   protected static void getCRLIssuersFromDistributionPoint(DistributionPoint var0, Collection var1, X509CRLSelector var2) throws AnnotatedException {
      ArrayList var3 = new ArrayList();
      Iterator var9;
      if (var0.getCRLIssuer() != null) {
         GeneralName[] var4 = var0.getCRLIssuer().getNames();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            if (var4[var5].getTagNo() == 4) {
               try {
                  var3.add(X500Name.getInstance(var4[var5].getName().toASN1Primitive().getEncoded()));
               } catch (IOException var8) {
                  throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", var8);
               }
            }
         }
      } else {
         if (var0.getDistributionPoint() == null) {
            throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
         }

         var9 = var1.iterator();

         while(var9.hasNext()) {
            var3.add(var9.next());
         }
      }

      var9 = var3.iterator();

      while(var9.hasNext()) {
         try {
            var2.addIssuerName(((X500Name)var9.next()).getEncoded());
         } catch (IOException var7) {
            throw new AnnotatedException("Cannot decode CRL issuer information.", var7);
         }
      }

   }

   private static BigInteger getSerialNumber(Object var0) {
      return ((X509Certificate)var0).getSerialNumber();
   }

   protected static void getCertStatus(Date var0, X509CRL var1, Object var2, CertStatus var3) throws AnnotatedException {
      X509CRLEntry var4 = null;

      boolean var5;
      try {
         var5 = X509CRLObject.isIndirectCRL(var1);
      } catch (CRLException var9) {
         throw new AnnotatedException("Failed check for indirect CRL.", var9);
      }

      if (var5) {
         var4 = var1.getRevokedCertificate(getSerialNumber(var2));
         if (var4 == null) {
            return;
         }

         X500Principal var6 = var4.getCertificateIssuer();
         X500Name var7;
         if (var6 == null) {
            var7 = PrincipalUtils.getIssuerPrincipal(var1);
         } else {
            var7 = X500Name.getInstance(var6.getEncoded());
         }

         if (!PrincipalUtils.getEncodedIssuerPrincipal(var2).equals(var7)) {
            return;
         }
      } else {
         if (!PrincipalUtils.getEncodedIssuerPrincipal(var2).equals(PrincipalUtils.getIssuerPrincipal(var1))) {
            return;
         }

         var4 = var1.getRevokedCertificate(getSerialNumber(var2));
         if (var4 == null) {
            return;
         }
      }

      ASN1Enumerated var10 = null;
      if (var4.hasExtensions()) {
         try {
            var10 = ASN1Enumerated.getInstance(getExtensionValue(var4, Extension.reasonCode.getId()));
         } catch (Exception var8) {
            throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", var8);
         }
      }

      if (var0.getTime() >= var4.getRevocationDate().getTime() || var10 == null || var10.getValue().intValue() == 0 || var10.getValue().intValue() == 1 || var10.getValue().intValue() == 2 || var10.getValue().intValue() == 8) {
         if (var10 != null) {
            var3.setCertStatus(var10.getValue().intValue());
         } else {
            var3.setCertStatus(0);
         }

         var3.setRevocationDate(var4.getRevocationDate());
      }

   }

   protected static Set getDeltaCRLs(Date var0, X509CRL var1, List var2, List var3) throws AnnotatedException {
      X509CRLSelector var4 = new X509CRLSelector();

      try {
         var4.addIssuerName(PrincipalUtils.getIssuerPrincipal(var1).getEncoded());
      } catch (IOException var14) {
         throw new AnnotatedException("Cannot extract issuer from CRL.", var14);
      }

      BigInteger var5 = null;

      ASN1Primitive var6;
      try {
         var6 = getExtensionValue(var1, CRL_NUMBER);
         if (var6 != null) {
            var5 = ASN1Integer.getInstance(var6).getPositiveValue();
         }
      } catch (Exception var15) {
         throw new AnnotatedException("CRL number extension could not be extracted from CRL.", var15);
      }

      var6 = null;

      byte[] var16;
      try {
         var16 = var1.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
      } catch (Exception var13) {
         throw new AnnotatedException("Issuing distribution point extension value could not be read.", var13);
      }

      var4.setMinCRLNumber(var5 == null ? null : var5.add(BigInteger.valueOf(1L)));
      PKIXCRLStoreSelector.Builder var7 = new PKIXCRLStoreSelector.Builder(var4);
      var7.setIssuingDistributionPoint(var16);
      var7.setIssuingDistributionPointEnabled(true);
      var7.setMaxBaseCRLNumber(var5);
      PKIXCRLStoreSelector var8 = var7.build();
      Set var9 = CRL_UTIL.findCRLs(var8, var0, var2, var3);
      HashSet var10 = new HashSet();
      Iterator var11 = var9.iterator();

      while(var11.hasNext()) {
         X509CRL var12 = (X509CRL)var11.next();
         if (isDeltaCRL(var12)) {
            var10.add(var12);
         }
      }

      return var10;
   }

   private static boolean isDeltaCRL(X509CRL var0) {
      Set var1 = var0.getCriticalExtensionOIDs();
      return var1 == null ? false : var1.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
   }

   protected static Set getCompleteCRLs(DistributionPoint var0, Object var1, Date var2, PKIXExtendedParameters var3) throws AnnotatedException {
      X509CRLSelector var4 = new X509CRLSelector();

      try {
         HashSet var5 = new HashSet();
         var5.add(PrincipalUtils.getEncodedIssuerPrincipal(var1));
         getCRLIssuersFromDistributionPoint(var0, var5, var4);
      } catch (AnnotatedException var8) {
         throw new AnnotatedException("Could not get issuer information from distribution point.", var8);
      }

      if (var1 instanceof X509Certificate) {
         var4.setCertificateChecking((X509Certificate)var1);
      }

      PKIXCRLStoreSelector var9 = (new PKIXCRLStoreSelector.Builder(var4)).setCompleteCRLEnabled(true).build();
      Date var6 = var2;
      if (var3.getDate() != null) {
         var6 = var3.getDate();
      }

      Set var7 = CRL_UTIL.findCRLs(var9, var6, var3.getCertStores(), var3.getCRLStores());
      checkCRLsNotEmpty(var7, var1);
      return var7;
   }

   protected static Date getValidCertDateFromValidityModel(PKIXExtendedParameters var0, CertPath var1, int var2) throws AnnotatedException {
      if (var0.getValidityModel() == 1) {
         if (var2 <= 0) {
            return getValidDate(var0);
         } else if (var2 - 1 == 0) {
            ASN1GeneralizedTime var3 = null;

            try {
               byte[] var4 = ((X509Certificate)var1.getCertificates().get(var2 - 1)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
               if (var4 != null) {
                  var3 = ASN1GeneralizedTime.getInstance(ASN1Primitive.fromByteArray(var4));
               }
            } catch (IOException var6) {
               throw new AnnotatedException("Date of cert gen extension could not be read.");
            } catch (IllegalArgumentException var7) {
               throw new AnnotatedException("Date of cert gen extension could not be read.");
            }

            if (var3 != null) {
               try {
                  return var3.getDate();
               } catch (ParseException var5) {
                  throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", var5);
               }
            } else {
               return ((X509Certificate)var1.getCertificates().get(var2 - 1)).getNotBefore();
            }
         } else {
            return ((X509Certificate)var1.getCertificates().get(var2 - 1)).getNotBefore();
         }
      } else {
         return getValidDate(var0);
      }
   }

   protected static PublicKey getNextWorkingKey(List var0, int var1, JcaJceHelper var2) throws CertPathValidatorException {
      Certificate var3 = (Certificate)var0.get(var1);
      PublicKey var4 = var3.getPublicKey();
      if (!(var4 instanceof DSAPublicKey)) {
         return var4;
      } else {
         DSAPublicKey var5 = (DSAPublicKey)var4;
         if (var5.getParams() != null) {
            return var5;
         } else {
            for(int var6 = var1 + 1; var6 < var0.size(); ++var6) {
               X509Certificate var7 = (X509Certificate)var0.get(var6);
               var4 = var7.getPublicKey();
               if (!(var4 instanceof DSAPublicKey)) {
                  throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
               }

               DSAPublicKey var8 = (DSAPublicKey)var4;
               if (var8.getParams() != null) {
                  DSAParams var9 = var8.getParams();
                  DSAPublicKeySpec var10 = new DSAPublicKeySpec(var5.getY(), var9.getP(), var9.getQ(), var9.getG());

                  try {
                     KeyFactory var11 = var2.createKeyFactory("DSA");
                     return var11.generatePublic(var10);
                  } catch (Exception var12) {
                     throw new RuntimeException(var12.getMessage());
                  }
               }
            }

            throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
         }
      }
   }

   static Collection findIssuerCerts(X509Certificate var0, List var1, List var2) throws AnnotatedException {
      X509CertSelector var3 = new X509CertSelector();

      try {
         var3.setSubject(PrincipalUtils.getIssuerPrincipal(var0).getEncoded());
      } catch (IOException var10) {
         throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", var10);
      }

      try {
         byte[] var4 = var0.getExtensionValue(AUTHORITY_KEY_IDENTIFIER);
         if (var4 != null) {
            ASN1OctetString var5 = ASN1OctetString.getInstance(var4);
            byte[] var6 = AuthorityKeyIdentifier.getInstance(var5.getOctets()).getKeyIdentifier();
            if (var6 != null) {
               var3.setSubjectKeyIdentifier((new DEROctetString(var6)).getEncoded());
            }
         }
      } catch (Exception var9) {
      }

      PKIXCertStoreSelector var11 = (new PKIXCertStoreSelector.Builder(var3)).build();
      LinkedHashSet var12 = new LinkedHashSet();

      ArrayList var7;
      Iterator var13;
      try {
         var7 = new ArrayList();
         var7.addAll(findCertificates(var11, var1));
         var7.addAll(findCertificates(var11, var2));
         var13 = var7.iterator();
      } catch (AnnotatedException var8) {
         throw new AnnotatedException("Issuer certificate cannot be searched.", var8);
      }

      var7 = null;

      while(var13.hasNext()) {
         X509Certificate var14 = (X509Certificate)var13.next();
         var12.add(var14);
      }

      return var12;
   }

   protected static void verifyX509Certificate(X509Certificate var0, PublicKey var1, String var2) throws GeneralSecurityException {
      if (var2 == null) {
         var0.verify(var1);
      } else {
         var0.verify(var1, var2);
      }

   }

   static void checkCRLsNotEmpty(Set var0, Object var1) throws AnnotatedException {
      if (var0.isEmpty()) {
         if (var1 instanceof X509AttributeCertificate) {
            X509AttributeCertificate var3 = (X509AttributeCertificate)var1;
            throw new AnnotatedException("No CRLs found for issuer \"" + var3.getIssuer().getPrincipals()[0] + "\"");
         } else {
            X509Certificate var2 = (X509Certificate)var1;
            throw new AnnotatedException("No CRLs found for issuer \"" + RFC4519Style.INSTANCE.toString(PrincipalUtils.getIssuerPrincipal(var2)) + "\"");
         }
      }
   }

   static {
      CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
      BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
      POLICY_MAPPINGS = Extension.policyMappings.getId();
      SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
      NAME_CONSTRAINTS = Extension.nameConstraints.getId();
      KEY_USAGE = Extension.keyUsage.getId();
      INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
      ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
      DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
      POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
      FRESHEST_CRL = Extension.freshestCRL.getId();
      CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
      AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
      CRL_NUMBER = Extension.cRLNumber.getId();
      crlReasons = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
   }
}
