package com.rsa.certj.provider.path;

import com.rsa.asn1.OIDList;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.NameMatcher;
import com.rsa.certj.cert.RDN;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.AuthorityKeyID;
import com.rsa.certj.cert.extensions.BasicConstraints;
import com.rsa.certj.cert.extensions.CertPolicies;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.GeneralSubtrees;
import com.rsa.certj.cert.extensions.InhibitAnyPolicy;
import com.rsa.certj.cert.extensions.IssuerAltName;
import com.rsa.certj.cert.extensions.KeyUsage;
import com.rsa.certj.cert.extensions.NameConstraints;
import com.rsa.certj.cert.extensions.PolicyConstraints;
import com.rsa.certj.cert.extensions.PolicyMappings;
import com.rsa.certj.cert.extensions.PolicyQualifiers;
import com.rsa.certj.cert.extensions.SubjectAltName;
import com.rsa.certj.cert.extensions.SubjectKeyID;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.certj.x.f;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_InvalidKeyException;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/** @deprecated */
public abstract class PKIXCertPathCommon extends CertPathCommon {
   /** @deprecated */
   protected static final String SUITEB_COMPLIANCE_FAILED = "SuiteB compliance checks failed for Certificate with subject: ";
   private final com.rsa.certj.provider.path.a certValidator;

   /** @deprecated */
   protected PKIXCertPathCommon(CertJ var1, String var2) throws InvalidParameterException {
      super(var1, var2);
      this.certValidator = new com.rsa.certj.provider.path.a(var1);
   }

   /** @deprecated */
   protected void getNextCertCandidates(CertPathCtx var1, Object var2, Vector var3) throws CertPathException {
      X500Name var4;
      X509V3Extensions var5;
      if (var2 instanceof X509Certificate) {
         X509Certificate var6 = (X509Certificate)var2;
         var4 = var6.getIssuerName();
         var5 = var6.getExtensions();
      } else {
         if (!(var2 instanceof X509CRL)) {
            throw new CertPathException("PKIXCertPath does not support startObjects other than X509Certificate or X509CRL.");
         }

         X509CRL var7 = (X509CRL)var2;
         var4 = var7.getIssuerName();
         var5 = var7.getExtensions();
      }

      this.getNextCertUsingIssuerAndAuthKeyId(var1, var4, var5, var3);
   }

   /** @deprecated */
   protected boolean verifyPath(CertPathCtx var1, Vector var2, Vector var3, Vector var4, Vector var5, GeneralSubtrees var6, GeneralNames var7, CertPathResult var8) throws CertPathException {
      Vector var10 = var3 == null ? null : new Vector();
      Vector var11 = var4 == null ? null : new Vector();
      int var12 = var2.size();
      int var13 = var12 - 1;
      int var14 = 0;
      X509Certificate var15 = null;
      X509Certificate var16 = (X509Certificate)var2.elementAt(var13);
      f.a(DEBUG_ON, "Start PKIX verifying path, using trusted root first.");
      if (var16 == null) {
         throw new CertPathException("The certificate path was built incorrectly: A null certificate was found.");
      } else {
         a var17 = this.createCertPathState(var1, var16, var12, var6, var7);
         PKIXCertPathResult var9;
         if (var8 != null && var8 instanceof PKIXCertPathResult) {
            var9 = (PKIXCertPathResult)var8;
         } else {
            var9 = new PKIXCertPathResult(this.context);
         }

         while(var13 > 0) {
            --var13;
            ++var14;
            var15 = (X509Certificate)var2.elementAt(var13);
            if (var15 == null) {
               throw new CertPathException("The certificate path was built incorrectly: A null certificate was found.");
            }

            if (DEBUG_ON) {
               f.a("Verifying path with certificate " + var15.getSubjectName().toString());
            }

            JSAFE_PublicKey var18;
            try {
               var18 = this.getWorkingPublicKey(var17, var16);
            } catch (CertificateException var20) {
               throw new CertPathException(var20);
            }

            if (!this.checkCompliance(var15, var18)) {
               f.a(DEBUG_ON, "Compliance check failed.");
               var9.setValidationResult(false);
               var9.b("SuiteB compliance checks failed for Certificate with subject: " + var15.getSubjectName().toString());
               return false;
            }

            f.a(DEBUG_ON, "Compliance check passed.");

            try {
               this.certValidator.b(var15, var1, var18);
            } catch (CertificateException var21) {
               f.a(DEBUG_ON, "Certificate verification failed.");
               var9.setValidationResult(false);
               var9.b("Certificate with subject " + var15.getSubjectName() + " failed basic certificate checks: " + var21.getMessage());
               return false;
            }

            f.a(DEBUG_ON, "Basic certificate verification passed.");
            if (!this.verifyRevocation(var1, var15, var10, var11)) {
               f.a(DEBUG_ON, "Certificate revocation check failed.");
               var9.setValidationResult(false);
               var9.b("Certificate with subject " + var15.getSubjectName() + " is either revoked or the revocation could not be determined" + this.getCRLComplianceFailedMessage());
               return false;
            }

            if (var13 == 0 || !this.isSelfIssued(var15)) {
               if (!this.verifySubjectAndAltNames(var17, var15)) {
                  var9.setValidationResult(false);
                  var9.b("Subject/issuer name chain error!");
                  return false;
               }

               f.a(DEBUG_ON, "Certificate subject name verification passed.");
            }

            if (!this.verifyPolicyInfo(var17, var15, var14, var12 - 1)) {
               f.a(DEBUG_ON, "Certificate policy info verification failed.");
               var9.setValidationResult(false);
               var9.b("Policy info check error!");
               return false;
            }

            f.a(DEBUG_ON, "Certificate policy info verification passed.");
            if (var13 != 0 && !this.prepareForNextCertificate(var1, var17, var15, var14, var9)) {
               return false;
            }

            var16 = var15;
         }

         CertJUtils.mergeLists(var3, var10);
         CertJUtils.mergeLists(var4, var11);
         if (var15 == null) {
            f.a(DEBUG_ON, "Only one certificate in cert path and it is trusted, so validation passed.");
            var9.setValidationResult(true);
            var9.b("Validation completed successfully.");
            return true;
         } else {
            return this.wrapUpVerifyPath(var1, var17, var15, var12, var5, var9);
         }
      }
   }

   /** @deprecated */
   protected abstract String getCRLComplianceFailedMessage();

   /** @deprecated */
   protected abstract boolean checkCompliance(X509Certificate var1, JSAFE_PublicKey var2) throws CertPathException;

   private boolean prepareForNextCertificate(CertPathCtx var1, a var2, X509Certificate var3, int var4, PKIXCertPathResult var5) throws CertPathException {
      if (!this.verifyPolicyMappings(var2, var3, var4)) {
         var5.setValidationResult(false);
         var5.b("Policy mapping check error!");
         return false;
      } else {
         this.updateWorkingKey(var2, var3);
         this.updateNameConstraints(var1, var2, var3);
         if (!this.isSelfIssued(var3)) {
            this.updateStateVariables(var2);
         }

         this.updatePolicyConstraints(var2, var3);
         this.updateInhibitAnyPolicy(var2, var3);
         if (!this.verifyBasicConstraints(var1, var3)) {
            var5.setValidationResult(false);
            var5.b("Basic constraints error!");
            return false;
         } else if (!this.verifyMaxPathLen(var1, var2, var3)) {
            var5.setValidationResult(false);
            var5.b("Max path length error!");
            return false;
         } else if (!this.verifyKeyUsage(var1, var3)) {
            var5.setValidationResult(false);
            var5.b("Key usage error!");
            return false;
         } else if (!this.verifyOtherCriticalExtensions(var1, var3)) {
            var5.setValidationResult(false);
            var5.b("Other critical extensions error!");
            return false;
         } else {
            return true;
         }
      }
   }

   private boolean verifyMaxPathLen(CertPathCtx var1, a var2, X509Certificate var3) {
      if (var1.isFlagRaised(32)) {
         return true;
      } else {
         if (!this.isSelfIssued(var3)) {
            if (var2.h <= 0) {
               return false;
            }

            --var2.h;
         }

         BasicConstraints var4 = (BasicConstraints)this.getExtension(var3, 19);
         if (var4 != null) {
            int var5 = var4.getPathLen();
            if (var5 != -1 && var5 < var2.h) {
               var2.h = var5;
            }
         }

         return true;
      }
   }

   private boolean wrapUpVerifyPath(CertPathCtx var1, a var2, X509Certificate var3, int var4, Vector var5, PKIXCertPathResult var6) throws CertPathException {
      if (var2.e > 0) {
         --var2.e;
      }

      this.updatePolicyConstraints(var2, var3);
      this.updateWorkingKey(var2, var3);
      if (!this.verifyOtherCriticalExtensions(var1, var3)) {
         var6.setValidationResult(false);
         var6.b("Other critical extensions error!");
         return false;
      } else {
         f.a(DEBUG_ON, "Verification of other critical extensions passed.");
         this.finalizeValidPolicyTree(var2.b, var1, var4 - 1);
         if (var2.b != null && !var2.b.a().d()) {
            var2.b = null;
         }

         Vector var7 = new Vector();
         this.fillInPolicyInfos(var2.b, var7);
         if (var5 != null) {
            var5.addAll(var7);
         }

         if (var2.e == 0 && var2.b == null) {
            f.a(DEBUG_ON, "Policy info check failed, no policy tree found.");
            var6.setValidationResult(false);
            var6.b("Policy info check error!");
            return false;
         } else {
            var6.setValidationResult(true);
            var6.b("Validation completed successfully!");
            var6.a(var7);
            var6.a(var2.j);
            var6.a(var2.i);
            var6.a(var2.k);
            return true;
         }
      }
   }

   private void updateStateVariables(a var1) {
      if (var1.g > 0) {
         --var1.g;
      }

      if (var1.e > 0) {
         --var1.e;
      }

      if (var1.f > 0) {
         --var1.f;
      }

   }

   private boolean verifyPolicyMappings(a var1, X509Certificate var2, int var3) throws CertPathException {
      PolicyMappings var4 = (PolicyMappings)this.getExtension(var2, 33);
      if (var4 == null) {
         return true;
      } else if (!this.verifyPolicyMappingEntries(var4)) {
         return false;
      } else {
         this.updatePolicyMappings(var1, var4, var3);
         return true;
      }
   }

   private boolean verifyPolicyMappingEntries(PolicyMappings var1) throws CertPathException {
      int var2 = var1.getPolicyCount();

      try {
         for(int var3 = 0; var3 < var2; ++var3) {
            if (CertJUtils.byteArraysEqual(X509V3Extension.ANY_POLICY_OID, var1.getIssuerDomainPolicy(var3)) || CertJUtils.byteArraysEqual(X509V3Extension.ANY_POLICY_OID, var1.getSubjectDomainPolicy(var3))) {
               return false;
            }
         }

         return true;
      } catch (CertificateException var4) {
         throw new CertPathException(var4);
      }
   }

   private void updatePolicyMappings(a var1, PolicyMappings var2, int var3) throws CertPathException {
      int var4 = var2.getPolicyCount();

      try {
         byte[] var5;
         int var6;
         if (var1.f > 0) {
            for(var6 = 0; var6 < var4; ++var6) {
               var5 = var2.getIssuerDomainPolicy(var6);
               this.updateValidPolicyTree(var5, var2, var1, var3);
            }
         } else {
            for(var6 = 0; var6 < var4; ++var6) {
               var5 = var2.getIssuerDomainPolicy(var6);
               this.pruneValidPolicyTree(var5, var1, var3);
            }
         }

      } catch (CertificateException var7) {
         throw new CertPathException(var7);
      }
   }

   private void updateValidPolicyTree(byte[] var1, PolicyMappings var2, a var3, int var4) throws CertPathException {
      Vector var5 = var2.getSubjectDomainPolicies(var1);
      Vector var6 = new Vector(var3.b.b(var4));
      boolean var7 = false;
      Iterator var8 = var6.iterator();

      ValidPolicyTreeNode var9;
      while(var8.hasNext()) {
         var9 = (ValidPolicyTreeNode)var8.next();
         if (var9.b(var1)) {
            Vector var10 = var9.f();
            var10.clear();
            var10.addAll(var5);
            var7 = true;
         }
      }

      if (!var7) {
         ValidPolicyTreeNode var12 = findNodeByOID(X509V3Extension.ANY_POLICY_OID, var6);
         if (var12 != null) {
            var9 = var12.a();
            ValidPolicyTreeNode var11 = ValidPolicyTreeNode.a(var1, var12.g(), var5);
            var9.a(var11);
         }

      }
   }

   private void pruneValidPolicyTree(byte[] var1, a var2, int var3) {
      Vector var4 = new Vector(var2.b.b(var3));
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         ValidPolicyTreeNode var6 = (ValidPolicyTreeNode)var5.next();
         if (var6.b(var1)) {
            var6.a().b(var6);
         }
      }

      var2.b.a(var3 - 1);
   }

   private boolean isSelfIssued(X509Certificate var1) {
      return var1.getIssuerName().equals(var1.getSubjectName());
   }

   private void updateInhibitAnyPolicy(a var1, X509Certificate var2) {
      InhibitAnyPolicy var3 = (InhibitAnyPolicy)this.getExtension(var2, 54);
      if (var3 != null) {
         if (var3.getSkipCerts() < var1.g) {
            var1.g = var3.getSkipCerts();
         }

      }
   }

   private void finalizeValidPolicyTree(ValidPolicyTree var1, CertPathCtx var2, int var3) throws CertPathException {
      if (var1 != null) {
         f.a(DEBUG_ON, "Finalizing valid policy tree.");
         Vector var4 = new Vector(Arrays.asList(var2.getPolicies()));
         if (!CertJUtils.containsByteArray(var4, X509V3Extension.ANY_POLICY_OID)) {
            Vector var5 = this.detrmineValidPolicyNodeSet(var1);
            Iterator var6 = var5.iterator();

            ValidPolicyTreeNode var8;
            while(var6.hasNext()) {
               ValidPolicyTreeNode var7 = (ValidPolicyTreeNode)var6.next();
               if (!var7.i() && !CertJUtils.containsByteArray(var4, var7.e())) {
                  var8 = var7.a();
                  if (var8 != null) {
                     var8.b(var7);
                  }
               }
            }

            Vector var15 = var1.b(var3);
            var8 = findNodeByOID(X509V3Extension.ANY_POLICY_OID, var15);
            if (var8 == null) {
               var1.a(var3 - 1);
            } else {
               ValidPolicyTreeNode var9 = var8.a();
               PolicyQualifiers var10 = var8.g();
               Iterator var11 = var4.iterator();

               while(var11.hasNext()) {
                  byte[] var12 = (byte[])var11.next();
                  if (findNodeByOID(var12, var5) == null) {
                     Vector var13 = new Vector();
                     var13.add(var12);
                     ValidPolicyTreeNode var14 = ValidPolicyTreeNode.a(var12, var10, var13);
                     var9.a(var14);
                  }
               }

               var9.b(var8);
               var1.a(var3 - 1);
            }
         }
      }
   }

   private Vector detrmineValidPolicyNodeSet(ValidPolicyTree var1) {
      Vector var2 = new Vector();
      this.determineValidPolicyNodeSetHelper(var1.a(), var2);
      return var2;
   }

   private void determineValidPolicyNodeSetHelper(ValidPolicyTreeNode var1, Vector var2) {
      ValidPolicyTreeNode var3 = var1.a();
      if (var3 != null && var3.i()) {
         var2.add(var1);
      }

      if (var1.d()) {
         Iterator var4 = var1.c().iterator();

         while(var4.hasNext()) {
            ValidPolicyTreeNode var5 = (ValidPolicyTreeNode)var4.next();
            this.determineValidPolicyNodeSetHelper(var5, var2);
         }

      }
   }

   private void fillInPolicyInfos(ValidPolicyTree var1, Vector var2) throws CertPathException {
      if (var1 != null) {
         if (var2 != null) {
            f.a(DEBUG_ON, "Creating new valid policy info list.");
            ValidPolicyTreeNode var3 = var1.a();
            this.fillInPolicyInfoListHelper(var3, var2, new boolean[]{false});
         }
      }
   }

   private void fillInPolicyInfoListHelper(ValidPolicyTreeNode var1, Vector var2, boolean[] var3) throws CertPathException {
      if (var1.i()) {
         if (!var1.d()) {
            var2.clear();
            var2.add(var1.h());
            var3[0] = true;
            return;
         }
      } else if (var1.a().i()) {
         var2.add(var1.h());
      }

      if (var1.d()) {
         Vector var4 = var1.c();
         Iterator var5 = var4.iterator();

         while(var5.hasNext() && !var3[0]) {
            this.fillInPolicyInfoListHelper((ValidPolicyTreeNode)var5.next(), var2, var3);
         }

      }
   }

   private boolean verifySubjectAndAltNames(a var1, X509Certificate var2) {
      X500Name var3 = var2.getSubjectName();
      SubjectAltName var4 = (SubjectAltName)this.getExtension(var2, 17);
      if (var3 != null && var3.getRDNCount() > 0) {
         GeneralName var5 = new GeneralName();

         try {
            var5.setGeneralName(var3, 5);
         } catch (NameException var7) {
            return false;
         }

         if (!this.verifyNameSubtrees(var1, var5)) {
            return false;
         }

         if (!this.verifyEmailAddressConstraints(var1, var3)) {
            return false;
         }
      } else if (var4 == null) {
         f.a(DEBUG_ON, "Certificate subject name verification failed (no name found).");
         return false;
      }

      return var4 == null || this.verifyGeneralNames(var1, var4.getGeneralNames());
   }

   private boolean verifyPolicyInfo(a var1, X509Certificate var2, int var3, int var4) throws CertPathException {
      if (var1.b == null) {
         return true;
      } else {
         CertPolicies var5 = (CertPolicies)this.getExtension(var2, 32);
         if (var5 == null) {
            var1.b = null;
            return var1.e > 0;
         } else {
            Vector var6 = var1.b.b(var3 - 1);

            for(int var7 = 0; var7 < var5.getPoliciesCount(); ++var7) {
               byte[] var8;
               try {
                  var8 = var5.getCertPolicyId(var7);
               } catch (CertificateException var10) {
                  throw new CertPathException(var10);
               }

               if (!CertJUtils.byteArraysEqual(X509V3Extension.ANY_POLICY_OID, var8)) {
                  boolean var9 = this.processPolicyInExpectedSet(var8, var5, var7, var6);
                  if (!var9) {
                     this.processPolicyNotInExpectedSet(var8, var5, var7, var6);
                  }
               }
            }

            this.processAnyPolicy(var1, var5, var6, var3, var4, this.isSelfIssued(var2));
            Vector var11 = var1.b.b(var3);
            if (var11.isEmpty()) {
               var1.b = null;
            } else {
               var1.b.a(var3 - 1);
            }

            return var1.b != null || var1.e != 0;
         }
      }
   }

   private boolean processPolicyInExpectedSet(byte[] var1, CertPolicies var2, int var3, Vector var4) throws CertPathException {
      Iterator var5 = var4.iterator();

      ValidPolicyTreeNode var6;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         var6 = (ValidPolicyTreeNode)var5.next();
      } while(!CertJUtils.containsByteArray(var6.f(), var1));

      Vector var7 = new Vector();
      var7.add(var1);

      try {
         ValidPolicyTreeNode var8 = ValidPolicyTreeNode.a(var1, var2.getPolicyQualifiers(var3), var7);
         var6.a(var8);
         return true;
      } catch (CertificateException var9) {
         throw new CertPathException(var9);
      }
   }

   private void processPolicyNotInExpectedSet(byte[] var1, CertPolicies var2, int var3, Vector var4) throws CertPathException {
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         ValidPolicyTreeNode var6 = (ValidPolicyTreeNode)var5.next();
         if (var6.i()) {
            Vector var7 = new Vector();
            var7.add(var1);

            try {
               ValidPolicyTreeNode var8 = ValidPolicyTreeNode.a(var1, var2.getPolicyQualifiers(var3), var7);
               var6.a(var8);
               break;
            } catch (CertificateException var9) {
               throw new CertPathException(var9);
            }
         }
      }

   }

   private void processAnyPolicy(a var1, CertPolicies var2, Vector var3, int var4, int var5, boolean var6) throws CertPathException {
      int var7 = -1;

      try {
         for(int var8 = 0; var8 < var2.getPoliciesCount(); ++var8) {
            byte[] var9 = var2.getCertPolicyId(var8);
            if (CertJUtils.byteArraysEqual(var9, X509V3Extension.ANY_POLICY_OID)) {
               var7 = var8;
               break;
            }
         }
      } catch (CertificateException var16) {
         throw new CertPathException(var16);
      }

      if (var7 >= 0 && (var1.g > 0 || var6 && var4 < var5)) {
         PolicyQualifiers var17;
         try {
            var17 = var2.getPolicyQualifiers(var7);
         } catch (CertificateException var15) {
            throw new CertPathException(var15);
         }

         Iterator var18 = var3.iterator();

         while(var18.hasNext()) {
            ValidPolicyTreeNode var10 = (ValidPolicyTreeNode)var18.next();
            Iterator var11 = var10.f().iterator();

            while(var11.hasNext()) {
               byte[] var12 = (byte[])var11.next();
               if (var10.a(var12) == null) {
                  Vector var13 = new Vector();
                  var13.add(var12);
                  ValidPolicyTreeNode var14 = ValidPolicyTreeNode.a(var12, var17, var13);
                  var10.a(var14);
               }
            }
         }
      }

   }

   private X509V3Extension getExtension(X509Certificate var1, int var2) {
      X509V3Extension var3 = null;
      X509V3Extensions var4 = var1.getExtensions();
      if (var4 != null) {
         try {
            var3 = var4.getExtensionByType(var2);
         } catch (CertificateException var6) {
         }
      }

      return var3;
   }

   private boolean verifyOtherCriticalExtensions(CertPathCtx var1, X509Certificate var2) {
      X509V3Extensions var3 = var2.getExtensions();
      if (var3 == null) {
         return true;
      } else if (var1.isFlagRaised(128)) {
         return true;
      } else {
         int var4 = 0;

         while(var4 < var3.getExtensionCount()) {
            X509V3Extension var5;
            try {
               var5 = var3.getExtensionByIndex(var4);
            } catch (CertificateException var7) {
               return false;
            }

            if (!var5.getCriticality()) {
               break;
            }

            switch (var5.getExtensionType()) {
               case 9:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 30:
               case 31:
               case 32:
               case 33:
               case 35:
               case 36:
               case 37:
                  ++var4;
                  break;
               case 10:
               case 11:
               case 12:
               case 13:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 27:
               case 28:
               case 29:
               case 34:
               default:
                  if (DEBUG_ON) {
                     byte[] var6 = var5.getOID();
                     f.a("An unknown critical extension with OID " + OIDList.getTrans(var6, 0, var6.length, -1) + " has been found in certificate " + var2.getSubjectName().toString());
                  }

                  return false;
            }
         }

         return true;
      }
   }

   private boolean verifyBasicConstraints(CertPathCtx var1, X509Certificate var2) {
      if (var1.isFlagRaised(32)) {
         return true;
      } else {
         BasicConstraints var3 = (BasicConstraints)this.getExtension(var2, 19);
         return var3 != null && var3.getCA();
      }
   }

   private void updateNameConstraints(CertPathCtx var1, a var2, X509Certificate var3) {
      if (!var1.isFlagRaised(16)) {
         NameConstraints var4 = (NameConstraints)this.getExtension(var3, 30);
         if (var4 != null) {
            GeneralSubtrees var5 = var4.getPermittedSubtrees();
            this.intersectNameSubtrees(var2.c, var5);
            this.uniteNameSubtrees(var2.d, var4.getExcludedSubtrees());
         }
      }
   }

   private void updatePolicyConstraints(a var1, X509Certificate var2) {
      PolicyConstraints var3 = (PolicyConstraints)this.getExtension(var2, 36);
      if (var3 != null) {
         int var4 = var3.getExplicitPolicy();
         int var5 = var3.getPolicyMapping();
         if (var4 != -1 && var4 < var1.e) {
            var1.e = var4;
         }

         if (var5 != -1 && var5 < var1.f) {
            var1.f = var5;
         }

      }
   }

   private boolean verifyKeyUsage(CertPathCtx var1, X509Certificate var2) {
      if (var1.isFlagRaised(64)) {
         return true;
      } else {
         KeyUsage var3 = (KeyUsage)this.getExtension(var2, 15);
         return var3 == null || (var3.getKeyUsage() & 67108864) != 0;
      }
   }

   private boolean verifyGeneralNames(a var1, GeneralNames var2) {
      try {
         for(int var3 = 0; var3 < var2.getNameCount(); ++var3) {
            if (!this.verifyNameSubtrees(var1, var2.getGeneralName(var3))) {
               return false;
            }
         }

         return true;
      } catch (NameException var4) {
         return false;
      }
   }

   private boolean verifyNameSubtrees(a var1, GeneralName var2) {
      try {
         if (var2.getDERLen(0) == 0) {
            return true;
         }
      } catch (NameException var9) {
         throw new IllegalStateException("Internal errror!");
      }

      int var3 = var2.getGeneralNameType();

      try {
         GeneralNames var4 = (GeneralNames)var1.c.get(new Integer(var3));
         if (var4 != null) {
            boolean var5 = false;

            for(int var6 = 0; var6 < var4.getNameCount(); ++var6) {
               GeneralName var7 = var4.getGeneralName(var6);
               if (NameMatcher.matchGeneralNames(var2, var7, var3)) {
                  var5 = true;
                  break;
               }
            }

            if (!var5) {
               f.a(DEBUG_ON, "Certificate subject name verification failed ", " because of unmatched constraint names", var4.toString());
               return false;
            }
         }

         for(int var10 = 0; var10 < var1.d.getNameCount(); ++var10) {
            GeneralName var11 = var1.d.getGeneralName(var10);
            if (var3 == var11.getGeneralNameType() && NameMatcher.matchGeneralNames(var2, var11, var3)) {
               f.a(DEBUG_ON, "Certificate subject name verification failed ", " because of matched excluded name", var11.toString());
               return false;
            }
         }

         return true;
      } catch (NameException var8) {
         return false;
      }
   }

   private boolean verifyEmailAddressConstraints(a var1, X500Name var2) {
      for(int var3 = 0; var3 < var2.getRDNCount(); ++var3) {
         try {
            RDN var4 = var2.getRDN(var3);
            AttributeValueAssertion var5 = var4.getAttribute(7);
            if (var5 != null) {
               String var6 = var5.getStringAttribute();
               GeneralNames var7 = (GeneralNames)var1.c.get(new Integer(2));
               if (var7 != null) {
                  boolean var8 = false;

                  for(int var9 = 0; var9 < var7.getNameCount(); ++var9) {
                     GeneralName var10 = var7.getGeneralName(var9);
                     if (NameMatcher.matchRfc822Names(var6, (String)var10.getGeneralName())) {
                        var8 = true;
                        break;
                     }
                  }

                  if (!var8) {
                     return false;
                  }
               }

               for(int var12 = 0; var12 < var1.d.getNameCount(); ++var12) {
                  GeneralName var13 = var1.d.getGeneralName(var12);
                  if (var13.getGeneralNameType() == 2 && NameMatcher.matchRfc822Names(var6, (String)var13.getGeneralName())) {
                     return false;
                  }
               }
            }
         } catch (NameException var11) {
         }
      }

      return true;
   }

   private void intersectNameSubtrees(Map var1, GeneralSubtrees var2) {
      // $FF: Couldn't be decompiled
   }

   private void uniteNameSubtrees(GeneralNames var1, GeneralSubtrees var2) {
      // $FF: Couldn't be decompiled
   }

   private void getNextCertUsingIssuerAndAuthKeyId(CertPathCtx var1, X500Name var2, X509V3Extensions var3, Vector var4) throws CertPathException {
      if ((var1.getPathOptions() & 512) != 0) {
         this.findCertBySubject(var1, var2, var4);
      } else {
         AuthorityKeyID var5 = null;

         try {
            if (var3 != null) {
               var5 = (AuthorityKeyID)var3.getExtensionByType(35);
            }
         } catch (CertificateException var7) {
         }

         if (var5 != null) {
            this.getIssuersWithAuthKeyId(var1, var2, var5, var4);
         } else {
            this.findCertBySubject(var1, var2, var4);
         }

      }
   }

   private void getIssuersWithAuthKeyId(CertPathCtx var1, X500Name var2, AuthorityKeyID var3, Vector var4) throws CertPathException {
      byte[] var5 = var3.getKeyID();
      if (var5 != null) {
         this.getIssuersWithKeyId(var1, var2, var5, var4);
      } else {
         this.getIssuersWithoutKeyId(var1, var3, var4);
      }

   }

   private void getIssuersWithKeyId(CertPathCtx var1, X500Name var2, byte[] var3, Vector var4) throws CertPathException {
      Vector var5 = new Vector();
      this.findCertBySubject(var1, var2, var5);
      int var8 = var5.size();

      while(var8 > 0) {
         --var8;
         X509Certificate var6 = (X509Certificate)var5.elementAt(var8);
         X509V3Extensions var7 = var6.getExtensions();
         if (var7 == null) {
            var5.removeElementAt(var8);
         } else {
            SubjectKeyID var9 = null;

            try {
               var9 = (SubjectKeyID)var7.getExtensionByType(14);
            } catch (CertificateException var11) {
            }

            if (var9 == null) {
               var5.removeElementAt(var8);
            } else if (!Arrays.equals(var3, var9.getKeyID())) {
               var5.removeElementAt(var8);
            }
         }
      }

      CertJUtils.mergeLists(var4, var5);
   }

   private void getIssuersWithoutKeyId(CertPathCtx var1, AuthorityKeyID var2, Vector var3) throws CertPathException {
      byte[] var4 = var2.getSerialNumber();
      if (var4.length != 0) {
         GeneralNames var5 = var2.getAuthorityCertIssuer();
         if (var5 != null) {
            try {
               for(int var6 = 0; var6 < var5.getNameCount(); ++var6) {
                  GeneralName var7 = var5.getGeneralName(var6);
                  if (var7.getGeneralNameType() == 5) {
                     this.findCertByIssuerSerial(var1, (X500Name)var7.getGeneralName(), var4, var3);
                  } else {
                     this.getIssuersWithIssuerAltNameExtension(var1, var7, var4, var3);
                  }
               }
            } catch (NameException var8) {
            }

         }
      }
   }

   private void getIssuersWithIssuerAltNameExtension(CertPathCtx var1, GeneralName var2, byte[] var3, Vector var4) throws CertPathException {
      GeneralNames var5 = new GeneralNames();
      var5.addGeneralName(var2);

      X509V3Extensions var6;
      try {
         IssuerAltName var7 = new IssuerAltName(var5, false);
         var6 = new X509V3Extensions(1);
         var6.addV3Extension(var7);
      } catch (CertificateException var10) {
         return;
      }

      Vector var11 = new Vector();
      this.findCertByExtensions(var1, (X500Name)null, var6, var11);
      int var8 = var11.size();

      while(var8 > 0) {
         --var8;
         X509Certificate var9 = (X509Certificate)var11.elementAt(var8);
         if (!Arrays.equals(var3, var9.getSerialNumber())) {
            var11.removeElementAt(var8);
         }
      }

      CertJUtils.mergeLists(var4, var11);
   }

   private void findCertByIssuerSerial(CertPathCtx var1, X500Name var2, byte[] var3, Vector var4) throws CertPathException {
      Certificate[] var5 = var1.getTrustedCerts();
      if (var5 != null) {
         for(int var6 = 0; var6 < var5.length; ++var6) {
            Certificate var7 = var5[var6];
            if (var7 instanceof X509Certificate) {
               X509Certificate var8 = (X509Certificate)var7;
               if (var2.equals(var8.getIssuerName()) && Arrays.equals(var3, var8.getSerialNumber())) {
                  if (!var4.contains(var8)) {
                     var4.addElement(var8);
                  }

                  return;
               }
            }
         }
      }

      try {
         var1.getDatabase().selectCertificateByIssuerAndSerialNumber(var2, var3, var4);
      } catch (NoServiceException var9) {
         throw new CertPathException("PKIXCertPath$Implementation.findCertByIssuerAndSerial: ", var9);
      } catch (DatabaseException var10) {
         throw new CertPathException("PKIXCertPath$Implementation.findCertByIssuerAndSerial: ", var10);
      }
   }

   private void findCertByExtensions(CertPathCtx var1, X500Name var2, X509V3Extensions var3, Vector var4) throws CertPathException {
      Certificate[] var5 = var1.getTrustedCerts();
      if (var5 != null) {
         for(int var6 = 0; var6 < var5.length; ++var6) {
            Certificate var7 = var5[var6];
            if (var7 instanceof X509Certificate) {
               X509Certificate var8 = (X509Certificate)var7;
               if ((var2 == null || var8.getSubjectName().contains(var2)) && CertJUtils.compareExtensions(var3, var8.getExtensions()) && !var4.contains(var8)) {
                  var4.addElement(var8);
               }
            }
         }
      }

      try {
         var1.getDatabase().selectCertificateByExtensions(var2, var3, var4);
      } catch (NoServiceException var9) {
         throw new CertPathException("PKIXCertPath$Implementation.findCertByExtension,", var9);
      } catch (DatabaseException var10) {
         throw new CertPathException("PKIXCertPath$Implementation.findCertByExtension.", var10);
      }
   }

   private Vector createPolicySet(byte[][] var1) {
      Vector var2 = new Vector();
      if (var1 == null) {
         return var2;
      } else {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            byte[] var4 = var1[var3];
            if (!CertJUtils.containsByteArray(var2, var4)) {
               var2.addElement(var4);
            }
         }

         return var2;
      }
   }

   private JSAFE_PublicKey getWorkingPublicKey(Object var1, X509Certificate var2) throws CertificateException {
      a var3 = (a)var1;
      if ("DSA".equals(var3.i) && var3.k != null) {
         JSAFE_PublicKey var4;
         try {
            var4 = h.h(var3.i, this.certJ.getDevice(), this.context.b);
         } catch (JSAFE_Exception var9) {
            throw new CertificateException(var9);
         }

         int var5 = var3.k.length;
         byte[][] var6 = new byte[var5 + 1][];
         System.arraycopy(var3.k, 0, var6, 0, var5);
         var6[var5] = var3.j;

         try {
            var4.setKeyData(var6);
            return var4;
         } catch (JSAFE_InvalidKeyException var8) {
            throw new CertificateException(var8);
         }
      } else {
         return var2.getSubjectPublicKey(this.certJ.getDevice());
      }
   }

   /** @deprecated */
   protected void getNextCertInPathInternal(CertPathCtx var1, Object var2, Vector var3) throws CertPathException {
      Vector var4 = new Vector();
      this.getNextCertCandidates(var1, var2, var4);
      CertJUtils.mergeLists(var3, var4);
   }

   private void updateWorkingKey(a var1, X509Certificate var2) throws CertPathException {
      JSAFE_PublicKey var3;
      try {
         var3 = var2.getSubjectPublicKey(this.certJ.getDevice());
      } catch (CertificateException var7) {
         throw new CertPathException(var7);
      }

      if (!"DSA".equals(var3.getAlgorithm())) {
         var1.i = var3.getAlgorithm();
         var1.j = null;
         var1.k = (byte[][])null;
      } else {
         byte[][] var4;
         try {
            var4 = var3.getKeyData("DSAPublicValue");
         } catch (JSAFE_UnimplementedException var6) {
            throw new CertPathException(var6);
         }

         if (var4.length == 0) {
            throw new CertPathException("Could not retrieve DSA public key form certificate!");
         } else {
            var1.j = var4[0];
            var4 = var3.getKeyData();
            if (var4.length == 0) {
               if (!"DSA".equals(var1.i)) {
                  var1.i = "DSA";
                  var1.k = (byte[][])null;
               }

            } else {
               if (var1.k == null) {
                  var1.k = new byte[var4.length - 1][];
               }

               System.arraycopy(var4, 0, var1.k, 0, var4.length - 1);
               var1.i = "DSA";
            }
         }
      }
   }

   private a createCertPathState(CertPathCtx var1, X509Certificate var2, int var3, GeneralSubtrees var4, GeneralNames var5) throws CertPathException {
      byte[][] var6 = var1.getPolicies();
      Vector var7 = null;
      int var8 = var3 + 1;
      int var9 = var3 + 1;
      int var10 = var3 + 1;
      byte[] var12 = null;
      byte[][] var13 = (byte[][])null;
      if (var6 != null) {
         var7 = this.createPolicySet(var6);
      }

      if (var1.isFlagRaised(65536)) {
         var8 = 0;
      }

      if (var1.isFlagRaised(32768)) {
         var9 = 0;
      }

      if (var1.isFlagRaised(131072)) {
         var10 = 0;
      }

      JSAFE_PublicKey var14;
      try {
         var14 = var2.getSubjectPublicKey(this.certJ.getDevice());
      } catch (CertificateException var19) {
         throw new CertPathException(var19);
      }

      String var11 = var14.getAlgorithm();
      if ("DSA".equals(var11)) {
         byte[][] var15 = var14.getKeyData();
         if (var15.length == 0) {
            throw new CertPathException("Anchor certificate must have valid public key parameters in subjectPublicKeyInfo!");
         }

         var13 = new byte[var15.length - 1][];
         System.arraycopy(var15, 0, var13, 0, var15.length - 1);
         var12 = var15[var15.length - 1];
      }

      ValidPolicyTree var21 = new ValidPolicyTree();
      HashMap var17 = new HashMap();

      GeneralNames var16;
      try {
         if (var5 != null) {
            var16 = (GeneralNames)var5.clone();
         } else {
            var16 = new GeneralNames();
         }

         if (var4 != null) {
            this.intersectNameSubtrees(var17, (GeneralSubtrees)var4.clone());
         }
      } catch (CloneNotSupportedException var20) {
         throw new CertPathException(var20);
      }

      if (DEBUG_ON) {
         f.a("Creating Certificate path state.");
         f.a("    Trust anchor       : " + var2.getSubjectName().toString());
         f.a("    No of certs in path: " + var3);
         if (var4 != null) {
            f.a("    Init perm subtrees : " + var4.toString());
         }

         if (var5 != null) {
            f.a("    Init excl subtrees : " + var5.toString());
         }
      }

      return new a(var7, var21, var17, var16, var9, var8, var10, var3, var11, var12, var13);
   }

   /** @deprecated */
   protected CertPathResult createCertPathResult() {
      return new PKIXCertPathResult(this.context);
   }

   /** @deprecated */
   public boolean validateCertificate(CertPathCtx var1, Certificate var2, JSAFE_PublicKey var3) throws NotSupportedException, CertPathException {
      if (!(var2 instanceof X509Certificate)) {
         throw new NotSupportedException("Only X509Certificate validation is supported.");
      } else {
         try {
            this.certValidator.a((X509Certificate)var2, var1, var3);
            return true;
         } catch (CertificateException var5) {
            return false;
         }
      }
   }

   private static ValidPolicyTreeNode findNodeByOID(byte[] var0, Vector var1) {
      Iterator var2 = var1.iterator();

      ValidPolicyTreeNode var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (ValidPolicyTreeNode)var2.next();
      } while(!Arrays.equals(var3.e(), var0));

      return var3;
   }

   private class a {
      /** @deprecated */
      protected Vector a;
      /** @deprecated */
      protected ValidPolicyTree b;
      /** @deprecated */
      protected Map c;
      /** @deprecated */
      protected GeneralNames d;
      /** @deprecated */
      protected int e;
      /** @deprecated */
      protected int f;
      /** @deprecated */
      protected int g;
      /** @deprecated */
      protected int h;
      /** @deprecated */
      protected String i;
      /** @deprecated */
      protected byte[] j;
      /** @deprecated */
      protected byte[][] k;

      protected a(Vector var2, ValidPolicyTree var3, Map var4, GeneralNames var5, int var6, int var7, int var8, int var9, String var10, byte[] var11, byte[][] var12) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
         this.d = var5;
         this.e = var6;
         this.f = var7;
         this.g = var8;
         this.h = var9;
         this.i = var10;
         this.j = var11;
         this.k = var12;
      }
   }
}
