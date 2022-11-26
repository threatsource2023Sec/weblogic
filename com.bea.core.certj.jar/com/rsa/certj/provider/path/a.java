package com.rsa.certj.provider.path;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.AuthorityInfoAccess;
import com.rsa.certj.cert.extensions.AuthorityKeyID;
import com.rsa.certj.cert.extensions.BasicConstraints;
import com.rsa.certj.cert.extensions.CRLDistributionPoints;
import com.rsa.certj.cert.extensions.FreshestCRL;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.GeneralSubtrees;
import com.rsa.certj.cert.extensions.InhibitAnyPolicy;
import com.rsa.certj.cert.extensions.KeyUsage;
import com.rsa.certj.cert.extensions.NameConstraints;
import com.rsa.certj.cert.extensions.PolicyConstraints;
import com.rsa.certj.cert.extensions.PolicyMappings;
import com.rsa.certj.cert.extensions.SubjectAltName;
import com.rsa.certj.cert.extensions.SubjectInfoAccess;
import com.rsa.certj.cert.extensions.SubjectKeyID;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.e;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

final class a {
   private static final int a = 85;
   private static final int b = 29;
   private static final Set c = new HashSet();
   private final CertJ d;

   a(CertJ var1) {
      this.d = var1;
   }

   private static void a(int var0) {
      byte[] var1 = new byte[]{85, 29, (byte)var0};
      a(var1);
   }

   private static void a(byte[] var0) {
      c.add(e.a(var0));
   }

   void a(X509Certificate var1, CertPathCtx var2, JSAFE_PublicKey var3) throws CertPathException, CertificateException {
      this.b(var1, var2, var3);
      this.b(var1.getSerialNumber());
      this.a(var1, var2);
   }

   void b(X509Certificate var1, CertPathCtx var2, JSAFE_PublicKey var3) throws CertificateException, CertPathException {
      var1.getSubjectPublicKey(this.d.getDevice());
      if (var1.getIssuerName() != null && var1.getIssuerName().getRDNCount() != 0) {
         if (!var2.isFlagRaised(2)) {
            Date var4 = var2.getValidationTime();
            if (var4 == null) {
               var4 = new Date();
            }

            if (!var1.checkValidityDate(var4)) {
               throw new CertificateException("Certificate is not valid at validation time.");
            }
         }

         try {
            if (!var2.isFlagRaised(1) && !var1.verifyCertificateSignature(this.d.getDevice(), var3, this.d.getRandomObject())) {
               throw new CertificateException("Certificate signature could not be validated.");
            }
         } catch (NoServiceException var5) {
            throw new CertPathException(var5);
         } catch (RandomException var6) {
            throw new CertPathException(var6);
         }
      } else {
         throw new CertificateException("Issuer name MUST be present");
      }
   }

   private void a(X509Certificate var1, CertPathCtx var2) throws CertPathException, CertificateException {
      X509V3Extensions var3 = var1.getExtensions();
      if (var3 != null && var3.getExtensionCount() != 0) {
         boolean var4 = false;
         BasicConstraints var5 = (BasicConstraints)var3.getExtensionByType(19);
         if (!var2.isFlagRaised(32) && var5 != null && var5.getCA() && var5.getPathLen() >= 0) {
            var4 = true;
         }

         if (var1.getVersion() != 2) {
            throw new CertificateException("Version number MUST be V3 if extensions are present");
         } else {
            this.a(var3);
            this.a(var3, var2);
            this.a(var1, var2, var3, var4);
            this.a(var1, var3);
            this.a(var3, var4);
            this.a(var5, var3, var2, var4);
            InhibitAnyPolicy var6 = (InhibitAnyPolicy)var3.getExtensionByType(54);
            if (var6 != null && !var6.getCriticality()) {
               throw new CertificateException("Inhibit anyPolicy extension MUST be marked as critical");
            } else {
               this.b(var3);
               this.c(var3);
               SubjectInfoAccess var7 = (SubjectInfoAccess)var3.getExtensionByType(125);
               if (var7 != null && var7.getCriticality()) {
                  throw new CertificateException("SubjectInfoAccess extension MUST NOT be set as critical");
               } else {
                  AuthorityInfoAccess var8 = (AuthorityInfoAccess)var3.getExtensionByType(100);
                  if (var8 != null && var8.getCriticality()) {
                     throw new CertificateException("AuthorityInfoAccess extension MUST NOT be set as critical");
                  } else {
                     this.b(var3, var4);
                     this.c(var3, var4);
                     SubjectAltName var9 = (SubjectAltName)var3.getExtensionByType(17);
                     if (var9 != null && var9.getGeneralNames().getNameCount() == 0) {
                        throw new CertificateException("SubjectAltName MUST contain at least one entry");
                     } else {
                        this.d(var3);
                     }
                  }
               }
            }
         }
      } else if (var1.getSubjectName() == null || var1.getSubjectName().getRDNCount() == 0) {
         throw new CertificateException("Subject name MUST NOT be empty when no extensions are specified");
      }
   }

   private void b(byte[] var1) throws CertificateException {
      if (var1 != null && var1.length != 0) {
         if (var1[0] < 0) {
            throw new CertificateException("Serial number cannot be negative");
         } else if (var1.length > 20) {
            throw new CertificateException("Serial number cannot be have more than 20 bytes");
         }
      }
   }

   private void a(X509V3Extensions var1) throws CertificateException {
      HashSet var2 = new HashSet();

      for(int var3 = 0; var3 < var1.getExtensionCount(); ++var3) {
         X509V3Extension var4 = var1.getExtensionByIndex(var3);
         String var5 = e.a(var4.getOID());
         if (var2.contains(var5)) {
            throw new CertificateException("Found duplicate extension: " + var4.getExtensionTypeString());
         }

         var2.add(var5);
      }

   }

   private void a(X509V3Extensions var1, CertPathCtx var2) throws CertificateException {
      if (!var2.isFlagRaised(128)) {
         for(int var3 = 0; var3 < var1.getExtensionCount(); ++var3) {
            X509V3Extension var4 = var1.getExtensionByIndex(var3);
            if (var4.isExtensionType(-1)) {
               String var5 = e.a(var4.getOID());
               if (c.contains(var5)) {
                  throw new CertificateException("Standard extension found defined as non-standard with OID: " + var5);
               }

               if (var4.getCriticality()) {
                  throw new CertificateException("Non-standard extension is marked as critical.");
               }
            }
         }

      }
   }

   private void a(X509Certificate var1, CertPathCtx var2, X509V3Extensions var3, boolean var4) throws CertificateException {
      if (var1.getSubjectName() == null || var1.getSubjectName().getRDNCount() == 0) {
         if (var4) {
            throw new CertificateException("Subject name MUST be present for CA certificates");
         }

         KeyUsage var5 = (KeyUsage)var3.getExtensionByType(15);
         if (var5 != null && var5.verifyKeyUsage(33554432)) {
            throw new CertificateException("Subject name MUST be present for CRL Issuers");
         }

         SubjectAltName var6 = (SubjectAltName)var3.getExtensionByType(17);
         if (var6 == null || var6.getGeneralNames().getNameCount() == 0) {
            throw new CertificateException("Subject alternate name MUST be present if subject name field is empty");
         }

         if (!var6.getCriticality()) {
            throw new CertificateException("Subject alternate name MUST be critical if subject name is not specified");
         }
      }

   }

   private void a(X509Certificate var1, X509V3Extensions var2) throws CertificateException {
      AuthorityKeyID var3 = (AuthorityKeyID)var2.getExtensionByType(35);
      if (var3 == null) {
         if (var1.getSubjectName() != null && !var1.getSubjectName().equals(var1.getIssuerName())) {
            throw new CertificateException("Authority key identifier extension MUST be present");
         }
      } else if (var3.getCriticality()) {
         throw new CertificateException("Auth key ID MUST NOT be set as critical");
      }

   }

   private void a(X509V3Extensions var1, boolean var2) throws CertificateException {
      SubjectKeyID var3 = (SubjectKeyID)var1.getExtensionByType(14);
      if (var3 != null && var3.getKeyID().length != 0) {
         if (var3.getCriticality()) {
            throw new CertificateException("SubjectKeyIdentifier MUST NOT be set as critical");
         }
      } else if (var2) {
         throw new CertificateException("SubjectKeyIdentifier extension MUST be present for CA certificates");
      }

   }

   private void a(BasicConstraints var1, X509V3Extensions var2, CertPathCtx var3, boolean var4) throws CertificateException {
      if (!var3.isFlagRaised(64)) {
         KeyUsage var5 = (KeyUsage)var2.getExtensionByType(15);
         if (var5 != null) {
            if (var5.getKeyUsage() == 0) {
               throw new CertificateException("At least one bit in the key usage extension MUST be set");
            } else {
               if (var5.verifyKeyUsage(67108864)) {
                  if (!var4) {
                     throw new CertificateException("BasicConstraints extension MUST be set if keyCertSign bit is set");
                  }

                  if (!var1.getCriticality()) {
                     throw new CertificateException("BasicConstraint extension MUST be marked as critical if the keyCertSign bit is set");
                  }
               }

            }
         }
      }
   }

   private void b(X509V3Extensions var1) throws CertPathException, CertificateException {
      CRLDistributionPoints var2 = (CRLDistributionPoints)var1.getExtensionByType(31);
      if (var2 != null) {
         this.a(var2);
      }
   }

   private void c(X509V3Extensions var1) throws CertPathException, CertificateException {
      FreshestCRL var2 = (FreshestCRL)var1.getExtensionByType(46);
      if (var2 != null) {
         if (var2.getCriticality()) {
            throw new CertificateException("Freshest CRL extension MUST NOT be set as critical");
         } else {
            this.a((CRLDistributionPoints)var2);
         }
      }
   }

   private void a(CRLDistributionPoints var1) throws CertificateException, CertPathException {
      int var2 = var1.getDistributionPointCount();
      boolean var3 = false;

      for(int var4 = 0; var4 < var2; ++var4) {
         try {
            int var5 = var1.getReasonFlags(var4);
            if (var5 == -8388608 || var5 == -1) {
               var3 = true;
            }

            GeneralNames var6 = var1.getCRLIssuer(var4);
            if (var6 != null && (var6.getNameCount() != 1 || var6.getGeneralName(0).getGeneralNameType() != 5)) {
               throw new CertificateException("The CRLIssuer MUST only contain the distinguished name from the issuer field of the CRL");
            }
         } catch (NameException var7) {
            throw new CertPathException(var7);
         }
      }

      if (!var3) {
         throw new CertificateException("At least one Distribution Point MUST cover all reasons");
      }
   }

   private void b(X509V3Extensions var1, boolean var2) throws CertificateException, CertPathException {
      NameConstraints var3 = (NameConstraints)var1.getExtensionByType(30);
      if (var3 != null) {
         GeneralSubtrees var4 = var3.getExcludedSubtrees();
         GeneralSubtrees var5 = var3.getPermittedSubtrees();
         if (var4 == null && var5 == null) {
            throw new CertificateException("Either the permittedSubtrees field or the excludedSubtrees MUST be present");
         } else {
            this.a(var3, var4, var2);
            this.a(var3, var5, var2);
         }
      }
   }

   private void a(NameConstraints var1, GeneralSubtrees var2, boolean var3) throws CertificateException, CertPathException {
      if (var2 != null) {
         if (!var3) {
            throw new CertificateException("Named constraints MUST only be applied to CA certificates");
         } else if (!var1.getCriticality()) {
            throw new CertificateException("Named constraint extension MUST be set as critical");
         } else {
            for(int var4 = 0; var4 < var2.getSubtreeCount(); ++var4) {
               try {
                  if (var2.getMinimum(var4) != 0 || var2.getMaximum(var4) != -1) {
                     throw new CertificateException("Named constraints minimum field MUST be 0 and the maximum field MUST be absent");
                  }
               } catch (NameException var6) {
                  throw new CertPathException(var6);
               }
            }

         }
      }
   }

   private void c(X509V3Extensions var1, boolean var2) throws CertificateException {
      PolicyConstraints var3 = (PolicyConstraints)var1.getExtensionByType(36);
      if (var3 != null) {
         if (var2 && !var3.getCriticality()) {
            throw new CertificateException("Policy constraints extension for CAs MUST be set as critical");
         } else if (var3.getPolicyMapping() == -1 && var3.getExplicitPolicy() == -1) {
            throw new CertificateException("Either the inhibitPolicyMapping field or the requireExplicitPolicy field MUST be present");
         }
      }
   }

   private void d(X509V3Extensions var1) throws CertificateException {
      PolicyMappings var2 = (PolicyMappings)var1.getExtensionByType(33);
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.getPolicyCount(); ++var3) {
            byte[] var4 = var2.getIssuerDomainPolicy(var3);
            byte[] var5 = var2.getSubjectDomainPolicy(var3);
            if (CertJUtils.byteArraysEqual(X509V3Extension.ANY_POLICY_OID, var4) || CertJUtils.byteArraysEqual(X509V3Extension.ANY_POLICY_OID, var5)) {
               throw new CertificateException("Policies MUST NOT be mapped to or from the special value anyPolicy");
            }
         }

      }
   }

   static {
      a(9);
      a(14);
      a(15);
      a(16);
      a(17);
      a(18);
      a(19);
      a(20);
      a(21);
      a(23);
      a(24);
      a(27);
      a(28);
      a(29);
      a(30);
      a(31);
      a(32);
      a(33);
      a(35);
      a(36);
      a(37);
      a(54);
      a(X509V3Extension.AUTHORITY_INFO_OID);
      a(X509V3Extension.NETSCAPE_CERT_TYPE_OID);
      a(X509V3Extension.NETSCAPE_BASE_URL_OID);
      a(X509V3Extension.NETSCAPE_REVOCATION_URL_OID);
      a(X509V3Extension.NETSCAPE_CA_REVOCATION_URL_OID);
      a(X509V3Extension.NETSCAPE_CERT_RENEWAL_URL_OID);
      a(X509V3Extension.NETSCAPE_CA_POLICY_URL_OID);
      a(X509V3Extension.NETSCAPE_SSL_SERVER_NAME_OID);
      a(X509V3Extension.NETSCAPE_COMMENT_OID);
      a(X509V3Extension.VERISIGN_CZAG_OID);
      a(X509V3Extension.VERISIGN_FIDELITY_ID_OID);
      a(X509V3Extension.VERISIGN_NETSCAPE_INBOX_V1_OID);
      a(X509V3Extension.VERISIGN_NETSCAPE_INBOX_V2_OID);
      a(X509V3Extension.VERISIGN_JURISDICTION_HASH_OID);
      a(X509V3Extension.VERISIGN_TOKEN_TYPE_OID);
      a(X509V3Extension.VERISIGN_SERIAL_NUMBER_OID);
      a(X509V3Extension.VERISIGN_NON_VERIFIED_OID);
      a(X509V3Extension.OCSP_NOCHECK_OID);
      a(X509V3Extension.ARCHIVE_CUTOFF_OID);
      a(X509V3Extension.CRL_REFERENCE_OID);
      a(X509V3Extension.OCSP_NONCE_OID);
      a(X509V3Extension.OCSP_ACCEPTABLE_RESPONSES_OID);
      a(X509V3Extension.OCSP_SERVICE_LOCATOR_OID);
      a(X509V3Extension.QC_STATEMENTS_OID);
      a(X509V3Extension.BIO_INFO_OID);
   }
}
