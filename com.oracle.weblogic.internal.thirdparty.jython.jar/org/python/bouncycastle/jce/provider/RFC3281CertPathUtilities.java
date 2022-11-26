package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.x509.CRLDistPoint;
import org.python.bouncycastle.asn1.x509.DistributionPoint;
import org.python.bouncycastle.asn1.x509.DistributionPointName;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.asn1.x509.ReasonFlags;
import org.python.bouncycastle.asn1.x509.TargetInformation;
import org.python.bouncycastle.asn1.x509.X509Extensions;
import org.python.bouncycastle.jcajce.PKIXCRLStore;
import org.python.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.python.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.python.bouncycastle.jcajce.PKIXExtendedParameters;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.python.bouncycastle.x509.PKIXAttrCertChecker;
import org.python.bouncycastle.x509.X509AttributeCertificate;
import org.python.bouncycastle.x509.X509CertStoreSelector;

class RFC3281CertPathUtilities {
   private static final String TARGET_INFORMATION;
   private static final String NO_REV_AVAIL;
   private static final String CRL_DISTRIBUTION_POINTS;
   private static final String AUTHORITY_INFO_ACCESS;

   protected static void processAttrCert7(X509AttributeCertificate var0, CertPath var1, CertPath var2, PKIXExtendedParameters var3, Set var4) throws CertPathValidatorException {
      Set var5 = var0.getCriticalExtensionOIDs();
      if (var5.contains(TARGET_INFORMATION)) {
         try {
            TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, TARGET_INFORMATION));
         } catch (AnnotatedException var7) {
            throw new ExtCertPathValidatorException("Target information extension could not be read.", var7);
         } catch (IllegalArgumentException var8) {
            throw new ExtCertPathValidatorException("Target information extension could not be read.", var8);
         }
      }

      var5.remove(TARGET_INFORMATION);
      Iterator var6 = var4.iterator();

      while(var6.hasNext()) {
         ((PKIXAttrCertChecker)var6.next()).check(var0, var1, var2, var5);
      }

      if (!var5.isEmpty()) {
         throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + var5);
      }
   }

   protected static void checkCRLs(X509AttributeCertificate var0, PKIXExtendedParameters var1, X509Certificate var2, Date var3, List var4, JcaJceHelper var5) throws CertPathValidatorException {
      if (var1.isRevocationEnabled()) {
         if (var0.getExtensionValue(NO_REV_AVAIL) == null) {
            CRLDistPoint var6 = null;

            try {
               var6 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, CRL_DISTRIBUTION_POINTS));
            } catch (AnnotatedException var20) {
               throw new CertPathValidatorException("CRL distribution point extension could not be read.", var20);
            }

            ArrayList var7 = new ArrayList();

            try {
               var7.addAll(CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(var6, var1.getNamedCRLStoreMap()));
            } catch (AnnotatedException var19) {
               throw new CertPathValidatorException("No additional CRL locations could be decoded from CRL distribution point extension.", var19);
            }

            PKIXExtendedParameters.Builder var8 = new PKIXExtendedParameters.Builder(var1);
            Iterator var9 = var7.iterator();

            while(var9.hasNext()) {
               var8.addCRLStore((PKIXCRLStore)var7);
            }

            var1 = var8.build();
            CertStatus var22 = new CertStatus();
            ReasonsMask var10 = new ReasonsMask();
            AnnotatedException var11 = null;
            boolean var12 = false;
            DistributionPoint[] var13;
            PKIXExtendedParameters var15;
            if (var6 != null) {
               var13 = null;

               try {
                  var13 = var6.getDistributionPoints();
               } catch (Exception var18) {
                  throw new ExtCertPathValidatorException("Distribution points could not be read.", var18);
               }

               try {
                  for(int var14 = 0; var14 < var13.length && var22.getCertStatus() == 11 && !var10.isAllReasons(); ++var14) {
                     var15 = (PKIXExtendedParameters)var1.clone();
                     checkCRL(var13[var14], var0, var15, var3, var2, var22, var10, var4, var5);
                     var12 = true;
                  }
               } catch (AnnotatedException var21) {
                  var11 = new AnnotatedException("No valid CRL for distribution point found.", var21);
               }
            }

            if (var22.getCertStatus() == 11 && !var10.isAllReasons()) {
               try {
                  var13 = null;

                  ASN1Primitive var23;
                  try {
                     var23 = (new ASN1InputStream(((X500Principal)var0.getIssuer().getPrincipals()[0]).getEncoded())).readObject();
                  } catch (Exception var16) {
                     throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", var16);
                  }

                  DistributionPoint var25 = new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, var23))), (ReasonFlags)null, (GeneralNames)null);
                  var15 = (PKIXExtendedParameters)var1.clone();
                  checkCRL(var25, var0, var15, var3, var2, var22, var10, var4, var5);
                  var12 = true;
               } catch (AnnotatedException var17) {
                  var11 = new AnnotatedException("No valid CRL for distribution point found.", var17);
               }
            }

            if (!var12) {
               throw new ExtCertPathValidatorException("No valid CRL found.", var11);
            }

            if (var22.getCertStatus() != 11) {
               String var24 = "Attribute certificate revocation after " + var22.getRevocationDate();
               var24 = var24 + ", reason: " + RFC3280CertPathUtilities.crlReasons[var22.getCertStatus()];
               throw new CertPathValidatorException(var24);
            }

            if (!var10.isAllReasons() && var22.getCertStatus() == 11) {
               var22.setCertStatus(12);
            }

            if (var22.getCertStatus() == 12) {
               throw new CertPathValidatorException("Attribute certificate status could not be determined.");
            }
         } else if (var0.getExtensionValue(CRL_DISTRIBUTION_POINTS) != null || var0.getExtensionValue(AUTHORITY_INFO_ACCESS) != null) {
            throw new CertPathValidatorException("No rev avail extension is set, but also an AC revocation pointer.");
         }
      }

   }

   protected static void additionalChecks(X509AttributeCertificate var0, Set var1, Set var2) throws CertPathValidatorException {
      Iterator var3 = var1.iterator();

      String var4;
      do {
         if (!var3.hasNext()) {
            var3 = var2.iterator();

            do {
               if (!var3.hasNext()) {
                  return;
               }

               var4 = (String)var3.next();
            } while(var0.getAttributes(var4) != null);

            throw new CertPathValidatorException("Attribute certificate does not contain necessary attribute: " + var4 + ".");
         }

         var4 = (String)var3.next();
      } while(var0.getAttributes(var4) == null);

      throw new CertPathValidatorException("Attribute certificate contains prohibited attribute: " + var4 + ".");
   }

   protected static void processAttrCert5(X509AttributeCertificate var0, PKIXExtendedParameters var1) throws CertPathValidatorException {
      try {
         var0.checkValidity(CertPathValidatorUtilities.getValidDate(var1));
      } catch (CertificateExpiredException var3) {
         throw new ExtCertPathValidatorException("Attribute certificate is not valid.", var3);
      } catch (CertificateNotYetValidException var4) {
         throw new ExtCertPathValidatorException("Attribute certificate is not valid.", var4);
      }
   }

   protected static void processAttrCert4(X509Certificate var0, Set var1) throws CertPathValidatorException {
      boolean var3 = false;
      Iterator var4 = var1.iterator();

      while(true) {
         TrustAnchor var5;
         do {
            if (!var4.hasNext()) {
               if (!var3) {
                  throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
               }

               return;
            }

            var5 = (TrustAnchor)var4.next();
         } while(!var0.getSubjectX500Principal().getName("RFC2253").equals(var5.getCAName()) && !var0.equals(var5.getTrustedCert()));

         var3 = true;
      }
   }

   protected static void processAttrCert3(X509Certificate var0, PKIXExtendedParameters var1) throws CertPathValidatorException {
      if (var0.getKeyUsage() != null && !var0.getKeyUsage()[0] && !var0.getKeyUsage()[1]) {
         throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
      } else if (var0.getBasicConstraints() != -1) {
         throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
      }
   }

   protected static CertPathValidatorResult processAttrCert2(CertPath var0, PKIXExtendedParameters var1) throws CertPathValidatorException {
      CertPathValidator var2 = null;

      try {
         var2 = CertPathValidator.getInstance("PKIX", "BC");
      } catch (NoSuchProviderException var6) {
         throw new ExtCertPathValidatorException("Support class could not be created.", var6);
      } catch (NoSuchAlgorithmException var7) {
         throw new ExtCertPathValidatorException("Support class could not be created.", var7);
      }

      try {
         return var2.validate(var0, var1);
      } catch (CertPathValidatorException var4) {
         throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", var4);
      } catch (InvalidAlgorithmParameterException var5) {
         throw new RuntimeException(var5.getMessage());
      }
   }

   protected static CertPath processAttrCert1(X509AttributeCertificate var0, PKIXExtendedParameters var1) throws CertPathValidatorException {
      CertPathBuilderResult var2 = null;
      HashSet var3 = new HashSet();
      Principal[] var5;
      int var6;
      if (var0.getHolder().getIssuer() != null) {
         X509CertSelector var4 = new X509CertSelector();
         var4.setSerialNumber(var0.getHolder().getSerialNumber());
         var5 = var0.getHolder().getIssuer();

         for(var6 = 0; var6 < var5.length; ++var6) {
            try {
               if (var5[var6] instanceof X500Principal) {
                  var4.setIssuer(((X500Principal)var5[var6]).getEncoded());
               }

               var3.addAll(CertPathValidatorUtilities.findCertificates((new PKIXCertStoreSelector.Builder(var4)).build(), var1.getCertStores()));
            } catch (AnnotatedException var16) {
               throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", var16);
            } catch (IOException var17) {
               throw new ExtCertPathValidatorException("Unable to encode X500 principal.", var17);
            }
         }

         if (var3.isEmpty()) {
            throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
         }
      }

      if (var0.getHolder().getEntityNames() != null) {
         X509CertStoreSelector var18 = new X509CertStoreSelector();
         var5 = var0.getHolder().getEntityNames();

         for(var6 = 0; var6 < var5.length; ++var6) {
            try {
               if (var5[var6] instanceof X500Principal) {
                  var18.setIssuer(((X500Principal)var5[var6]).getEncoded());
               }

               var3.addAll(CertPathValidatorUtilities.findCertificates((new PKIXCertStoreSelector.Builder(var18)).build(), var1.getCertStores()));
            } catch (AnnotatedException var14) {
               throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", var14);
            } catch (IOException var15) {
               throw new ExtCertPathValidatorException("Unable to encode X500 principal.", var15);
            }
         }

         if (var3.isEmpty()) {
            throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
         }
      }

      PKIXExtendedParameters.Builder var19 = new PKIXExtendedParameters.Builder(var1);
      ExtCertPathValidatorException var20 = null;
      Iterator var21 = var3.iterator();

      while(var21.hasNext()) {
         X509CertStoreSelector var7 = new X509CertStoreSelector();
         var7.setCertificate((X509Certificate)var21.next());
         var19.setTargetConstraints((new PKIXCertStoreSelector.Builder(var7)).build());
         CertPathBuilder var8 = null;

         try {
            var8 = CertPathBuilder.getInstance("PKIX", "BC");
         } catch (NoSuchProviderException var12) {
            throw new ExtCertPathValidatorException("Support class could not be created.", var12);
         } catch (NoSuchAlgorithmException var13) {
            throw new ExtCertPathValidatorException("Support class could not be created.", var13);
         }

         try {
            var2 = var8.build((new PKIXExtendedBuilderParameters.Builder(var19.build())).build());
         } catch (CertPathBuilderException var10) {
            var20 = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", var10);
         } catch (InvalidAlgorithmParameterException var11) {
            throw new RuntimeException(var11.getMessage());
         }
      }

      if (var20 != null) {
         throw var20;
      } else {
         return var2.getCertPath();
      }
   }

   private static void checkCRL(DistributionPoint var0, X509AttributeCertificate var1, PKIXExtendedParameters var2, Date var3, X509Certificate var4, CertStatus var5, ReasonsMask var6, List var7, JcaJceHelper var8) throws AnnotatedException {
      if (var1.getExtensionValue(X509Extensions.NoRevAvail.getId()) == null) {
         Date var9 = new Date(System.currentTimeMillis());
         if (var3.getTime() > var9.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
         } else {
            Set var10 = CertPathValidatorUtilities.getCompleteCRLs(var0, var1, var9, var2);
            boolean var11 = false;
            AnnotatedException var12 = null;
            Iterator var13 = var10.iterator();

            while(var13.hasNext() && var5.getCertStatus() == 11 && !var6.isAllReasons()) {
               try {
                  X509CRL var14 = (X509CRL)var13.next();
                  ReasonsMask var15 = RFC3280CertPathUtilities.processCRLD(var14, var0);
                  if (var15.hasNewReasons(var6)) {
                     Set var16 = RFC3280CertPathUtilities.processCRLF(var14, var1, (X509Certificate)null, (PublicKey)null, var2, var7, var8);
                     PublicKey var17 = RFC3280CertPathUtilities.processCRLG(var14, var16);
                     X509CRL var18 = null;
                     if (var2.isUseDeltasEnabled()) {
                        Set var19 = CertPathValidatorUtilities.getDeltaCRLs(var9, var14, var2.getCertStores(), var2.getCRLStores());
                        var18 = RFC3280CertPathUtilities.processCRLH(var19, var17);
                     }

                     if (var2.getValidityModel() != 1 && var1.getNotAfter().getTime() < var14.getThisUpdate().getTime()) {
                        throw new AnnotatedException("No valid CRL for current time found.");
                     }

                     RFC3280CertPathUtilities.processCRLB1(var0, var1, var14);
                     RFC3280CertPathUtilities.processCRLB2(var0, var1, var14);
                     RFC3280CertPathUtilities.processCRLC(var18, var14, var2);
                     RFC3280CertPathUtilities.processCRLI(var3, var18, var1, var5, var2);
                     RFC3280CertPathUtilities.processCRLJ(var3, var14, var1, var5);
                     if (var5.getCertStatus() == 8) {
                        var5.setCertStatus(11);
                     }

                     var6.addReasons(var15);
                     var11 = true;
                  }
               } catch (AnnotatedException var20) {
                  var12 = var20;
               }
            }

            if (!var11) {
               throw var12;
            }
         }
      }
   }

   static {
      TARGET_INFORMATION = X509Extensions.TargetInformation.getId();
      NO_REV_AVAIL = X509Extensions.NoRevAvail.getId();
      CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
      AUTHORITY_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
   }
}
