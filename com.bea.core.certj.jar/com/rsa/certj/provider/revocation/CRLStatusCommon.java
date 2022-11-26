package com.rsa.certj.provider.revocation;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.NameMatcher;
import com.rsa.certj.cert.RDN;
import com.rsa.certj.cert.RevokedCertificates;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.BasicConstraints;
import com.rsa.certj.cert.extensions.CRLDistributionPoints;
import com.rsa.certj.cert.extensions.CertificateIssuer;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.IssuingDistributionPoint;
import com.rsa.certj.cert.extensions.KeyUsage;
import com.rsa.certj.cert.extensions.ReasonCode;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.certj.spi.revocation.CertStatusInterface;
import com.rsa.certj.x.f;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public abstract class CRLStatusCommon extends ProviderImplementation implements CertStatusInterface {
   /** @deprecated */
   protected static final String SUITEB_COMPLIANCE_FAILED = "SuiteB compliance checks failed for CRL with issuer: ";
   private static final boolean DEBUG_ON = f.a();

   /** @deprecated */
   protected CRLStatusCommon(CertJ var1, String var2) throws InvalidParameterException {
      super(var1, var2);
   }

   /** @deprecated */
   public CertRevocationInfo checkCertRevocation(CertPathCtx var1, Certificate var2) throws NotSupportedException, CertStatusException {
      if (!(var2 instanceof X509Certificate)) {
         throw new NotSupportedException("CRLCertStatus$Implementation.checkCertRevocation: does not support certificate types other than X509Certificate.");
      } else {
         X509Certificate var3 = (X509Certificate)var2;
         CRLDistributionPoints var4 = (CRLDistributionPoints)this.getExtension((X509Certificate)var3, 31);
         CertRevocationInfo var5;
         if (var4 != null && !var1.isFlagRaised(1024)) {
            var5 = this.doCheckCertRevocation(var1, var3, var4);
         } else {
            var4 = new CRLDistributionPoints();
            GeneralNames var6 = new GeneralNames();
            GeneralName var7 = new GeneralName();

            try {
               var7.setGeneralName(var3.getIssuerName(), 5);
            } catch (NameException var9) {
               throw new CertStatusException(var9);
            }

            var6.addGeneralName(var7);
            var4.addDistributionPoints((GeneralNames)var6, -1, (GeneralNames)null);
            var5 = this.doCheckCertRevocation(var1, var3, var4);
         }

         return var5;
      }
   }

   private CertRevocationInfo doCheckCertRevocation(CertPathCtx var1, X509Certificate var2, CRLDistributionPoints var3) throws CertStatusException {
      CertRevocationInfo var4 = new CertRevocationInfo();
      a var5 = new a();
      Vector var6 = new Vector();
      Vector var7 = new Vector();
      Vector var8 = new Vector();
      Date var9;
      if (var1.getValidationTime() == null) {
         var9 = new Date();
      } else {
         var9 = var1.getValidationTime();
      }

      int var10 = var3.getDistributionPointCount();
      int var11 = 0;

      label131:
      while(var11 < var10) {
         GeneralNames var13 = null;

         try {
            var13 = var3.getCRLIssuer(var11);
         } catch (NameException var20) {
            this.internalError(var20);
         }

         boolean var15 = false;
         Vector var12;
         if (var13 != null) {
            X500Name var14 = this.findX500Name(var13);
            this.validateIssuerDn(var14);
            var12 = this.obtainCrls(var1, var14, var9);
            var15 = true;
         } else {
            var12 = this.obtainCrls(var1, var2.getIssuerName(), var9);
         }

         Iterator var16 = var12.iterator();

         while(true) {
            X509CRL var17;
            do {
               do {
                  while(true) {
                     do {
                        do {
                           if (!var16.hasNext()) {
                              ++var11;
                              continue label131;
                           }

                           if (var5.a() || !var5.b()) {
                              break label131;
                           }

                           var17 = (X509CRL)var16.next();
                        } while(this.isCRLObsolete(var1, var17, var9));
                     } while(var15 && !this.assertsIndirectCRL(var17));

                     this.updateLocalCrlCache();

                     try {
                        if (!this.verifyIssuerAndScope(var1, var17, var2, var3, var11)) {
                           continue;
                        }
                     } catch (NameException var21) {
                        this.internalError(var21);
                     }
                     break;
                  }

                  this.verifyIssuerAndScopeOnDeltaCrl();
                  if (var1.isFlagRaised(16384)) {
                     break;
                  }

                  this.updateInterimReasonsMask(var5, var17, var3, var11);
               } while(!this.verifyInterimReasonsMask(var5));
            } while(!this.verifyPath(var1, var17, var6, var7, var8));

            if (var7 != null && !var7.contains(var17)) {
               var7.add(var17);
            }

            CertJUtils.mergeLists(var8, var6);
            if (this.findSerialNumberInCrl(var2, var17, var15)) {
               ReasonCode var23 = (ReasonCode)this.getExtension((X509CRL)var17, 21);
               if (var23 != null) {
                  var5.c = var23.getReasonCode();
               } else {
                  var5.c = 0;
               }

               var4.setStatus(1);
               var4.setType(1);
               CRLEvidence var19 = new CRLEvidence(var17, (Vector)null, (Vector)null);
               var4.setEvidence(var19);
            } else {
               CRLEvidence var18;
               if (!var1.isFlagRaised(128) && this.hasUnknownCriticalExtension(var17)) {
                  var5.c = 0;
                  var4.setStatus(2);
                  var4.setType(1);
                  var18 = new CRLEvidence(var17, (Vector)null, (Vector)null);
                  var4.setEvidence(var18);
               } else if (var1.isFlagRaised(16384)) {
                  var4.setStatus(0);
                  var4.setType(1);
                  var18 = new CRLEvidence((CRL)null, var8, var7);
                  var4.setEvidence(var18);
               }
            }

            if (var5.c == 8) {
               var5.c();
            }

            var5.b |= var5.d;
         }
      }

      if (var1.isFlagRaised(16384)) {
         return var4;
      } else {
         if (!var5.a() && var5.b()) {
            var4.setStatus(2);
            var4.setEvidence((Object)null);
            var4.setType(0);
         } else if (var5.b()) {
            CRLEvidence var22 = new CRLEvidence((CRL)null, var8, var7);
            var4.setStatus(0);
            var4.setType(1);
            var4.setEvidence(var22);
         }

         return var4;
      }
   }

   /** @deprecated */
   protected abstract boolean checkCompliance(X509CRL var1) throws CertStatusException;

   private boolean assertsIndirectCRL(X509CRL var1) {
      IssuingDistributionPoint var2 = (IssuingDistributionPoint)this.getExtension((X509CRL)var1, 28);
      return var2 != null && var2.getIndirectCRL();
   }

   private X500Name findX500Name(GeneralNames var1) {
      Vector var2 = var1.getGeneralNames();

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         GeneralName var4 = (GeneralName)var2.get(var3);
         Object var5 = var4.getGeneralName();
         if (var5 instanceof X500Name) {
            return (X500Name)var5;
         }
      }

      return null;
   }

   private void internalError(Exception var1) throws CertStatusException {
      throw new CertStatusException("Internal error! ", var1);
   }

   private boolean verifyInterimReasonsMask(a var1) {
      return (var1.d & ~var1.b) != 0;
   }

   private void updateInterimReasonsMask(a var1, X509CRL var2, CRLDistributionPoints var3, int var4) throws CertStatusException {
      int var5;
      try {
         var5 = var3.getReasonFlags(var4);
      } catch (NameException var8) {
         throw new CertStatusException("Internal error! ", var8);
      }

      IssuingDistributionPoint var6 = (IssuingDistributionPoint)this.getExtension((X509CRL)var2, 28);
      if (var6 == null) {
         if (var5 != -1) {
            var1.d = var5;
         } else {
            var1.d = -8388608;
         }

      } else {
         int var7 = var6.getReasonFlags();
         if (var7 == -1) {
            if (var5 != -1) {
               var1.d = var5;
            } else {
               var1.d = -8388608;
            }

         } else {
            var1.d = var7;
            if (var5 != -1) {
               var1.d &= var5;
            }

         }
      }
   }

   private Vector obtainCrls(CertPathCtx var1, X500Name var2, Date var3) throws CertStatusException {
      Vector var4 = new Vector();

      try {
         DatabaseService var5 = var1.getDatabase();
         var5.setupCRLIterator();

         while(var5.hasMoreCRLs()) {
            CRL var6 = var5.nextCRL();
            if (var6 instanceof X509CRL) {
               X509CRL var7 = (X509CRL)var6;
               X500Name var8 = var7.getIssuerName();
               if (var2.equals(var8)) {
                  Date var9 = var7.getThisUpdate();
                  if (!var9.after(var3) && !var4.contains(var7) && this.checkCompliance(var7)) {
                     var4.add((X509CRL)var7.clone());
                  }
               }
            }
         }
      } catch (Exception var10) {
         throw new CertStatusException("CRLCertStatus$Implementation.checkCertRevocation.", var10);
      }

      return this.getBestCrls(var4);
   }

   private boolean verifyIssuerAndScopeOnDeltaCrl() {
      return true;
   }

   private boolean verifyIssuerAndScope(CertPathCtx var1, X509CRL var2, X509Certificate var3, CRLDistributionPoints var4, int var5) throws NameException, CertStatusException {
      GeneralNames var6 = var4.getCRLIssuer(var5);
      IssuingDistributionPoint var7 = (IssuingDistributionPoint)this.getExtension((X509CRL)var2, 28);
      if (var6 != null) {
         X500Name var8 = this.findX500Name(var6);
         this.validateIssuerDn(var8);
         if (!NameMatcher.matchDirectoryNames(var2.getIssuerName(), var8)) {
            return false;
         }

         if (!var1.isFlagRaised(16384) && (var7 == null || !var7.getIndirectCRL())) {
            return false;
         }
      } else if (!NameMatcher.matchDirectoryNames(var2.getIssuerName(), var3.getIssuerName())) {
         return false;
      }

      if (var7 != null && !var1.isFlagRaised(16384)) {
         if (!this.verifyIssuingDistributionPointName(var6, var2, var3, var7, var4, var5)) {
            return false;
         } else {
            BasicConstraints var9 = (BasicConstraints)this.getExtension((X509Certificate)var3, 19);
            if (var7.getUserCerts() && var9 != null && var9.getCA()) {
               return false;
            } else if (!var7.getCACerts() || var9 != null && var9.getCA()) {
               return !var7.getAttributeCerts();
            } else {
               return false;
            }
         }
      } else {
         return true;
      }
   }

   private boolean verifyIssuingDistributionPointName(GeneralNames var1, X509CRL var2, X509Certificate var3, IssuingDistributionPoint var4, CRLDistributionPoints var5, int var6) throws NameException, CertStatusException {
      Object var7 = var4.getDistributionPointName();
      Object var8 = var5.getDistributionPointName(var6);
      if (var7 == null) {
         return true;
      } else {
         X500Name var10;
         GeneralNames var12;
         if (var8 != null) {
            GeneralNames var13;
            if (var7 instanceof RDN) {
               X500Name var9 = var2.getIssuerName();
               var9.addRDN((RDN)var7);
               if (var8 instanceof RDN) {
                  if (var1 != null) {
                     var10 = this.findX500Name(var1);
                     this.validateIssuerDn(var10);
                  } else {
                     var10 = var3.getIssuerName();
                  }

                  var10.addRDN((RDN)var8);
                  if (!var9.equals(var10)) {
                     return false;
                  }
               } else {
                  var13 = (GeneralNames)var8;
                  GeneralName var11 = new GeneralName();
                  var11.setGeneralName(var9, 5);
                  if (!this.existsMatchingName(var11, var13)) {
                     return false;
                  }
               }
            } else {
               var12 = (GeneralNames)var7;
               if (var8 instanceof GeneralNames) {
                  var13 = (GeneralNames)var8;
               } else {
                  X500Name var14;
                  if (var1 != null) {
                     var14 = this.findX500Name(var1);
                     this.validateIssuerDn(var14);
                  } else {
                     var14 = var3.getIssuerName();
                  }

                  var14.addRDN((RDN)var8);
                  var13 = this.convertDnToGeneralNames(var14);
               }

               if (!this.existsMatchingNamePair(var12, var13)) {
                  return false;
               }
            }
         } else {
            if (var1 == null) {
               return false;
            }

            if (var7 instanceof RDN) {
               var10 = var2.getIssuerName();
               var10.addRDN((RDN)var7);
               var12 = this.convertDnToGeneralNames(var10);
            } else {
               var12 = (GeneralNames)var7;
            }

            if (!this.existsMatchingNamePair(var12, var1)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean existsMatchingName(GeneralName var1, GeneralNames var2) throws NameException {
      if (var1 != null && var2 != null) {
         for(int var3 = 0; var3 < var2.getNameCount(); ++var3) {
            if (var1.equals(var2.getGeneralName(var3))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean existsMatchingNamePair(GeneralNames var1, GeneralNames var2) throws NameException {
      if (var1 == null) {
         return false;
      } else {
         for(int var3 = 0; var3 < var1.getNameCount(); ++var3) {
            GeneralName var4 = var1.getGeneralName(var3);
            if (this.existsMatchingName(var4, var2)) {
               return true;
            }
         }

         return false;
      }
   }

   private GeneralNames convertDnToGeneralNames(X500Name var1) throws NameException {
      GeneralNames var2 = new GeneralNames();
      GeneralName var3 = new GeneralName();
      var3.setGeneralName(var1, 5);
      var2.addGeneralName(var3);
      return var2;
   }

   private void validateIssuerDn(X500Name var1) throws CertStatusException {
      if (var1 == null) {
         throw new CertStatusException("the cRLIssuer MUST contain at least one X.500 distinguished name.");
      }
   }

   private void updateLocalCrlCache() {
   }

   private Vector getBestCrls(Vector var1) {
      HashMap var2 = new HashMap();
      IssuingDistributionPoint var3 = new IssuingDistributionPoint();
      Iterator var4 = var1.iterator();

      while(true) {
         X509CRL var5;
         IssuingDistributionPoint var6;
         X509CRL var7;
         do {
            if (!var4.hasNext()) {
               return new Vector(var2.values());
            }

            var5 = (X509CRL)var4.next();
            var6 = (IssuingDistributionPoint)this.getExtension((X509CRL)var5, 28);
            if (var6 == null) {
               var6 = var3;
            }

            var7 = (X509CRL)var2.get(var6);
         } while(var7 != null && !var5.getThisUpdate().after(var7.getThisUpdate()));

         var2.put(var6, var5);
      }
   }

   private boolean verifyPath(CertPathCtx var1, X509CRL var2, Vector var3, Vector var4, Vector var5) throws CertStatusException {
      Vector var6 = (Vector)var1.getAttribute(var2);
      if (var6 != null) {
         CertJUtils.mergeLists(var5, var6);
         return true;
      } else {
         var1.setAttribute(var2, new Vector());
         Vector var7 = new Vector();

         try {
            this.certJ.getNextCertInPath(var1, var2, var7);
         } catch (Exception var14) {
            var1.removeAttribute(var2);
            throw new CertStatusException(var14);
         }

         if (var7.isEmpty()) {
            var1.removeAttribute(var2);
            return false;
         } else {
            if (DEBUG_ON) {
               f.a("Validating certificate path for CRL issued by " + var2.getIssuerName().toString());
            }

            Iterator var8 = var7.iterator();

            while(var8.hasNext()) {
               Certificate var9 = (Certificate)var8.next();
               X509Certificate var10 = (X509Certificate)var9;

               CertPathResult var11;
               try {
                  var11 = this.certJ.buildCertPath(var1, var10, var3, var4, var5);
               } catch (Exception var13) {
                  var1.removeAttribute(var2);
                  throw new CertStatusException(var13);
               }

               if (var11.getValidationResult() && this.verifyCRLSignature(var2, var10, var11) && this.verifyKeyUsage(var1, var10)) {
                  var1.setAttribute(var2, var3.clone());
                  if (DEBUG_ON) {
                     f.a("Certificate path validation for CRL issued by " + var2.getIssuerName().toString() + " passed.");
                  }

                  return true;
               }
            }

            var1.removeAttribute(var2);
            if (DEBUG_ON) {
               f.a("Certificate path validation for CRL issued by " + var2.getIssuerName().toString() + " failed.");
            }

            return false;
         }
      }
   }

   private boolean verifyCRLSignature(X509CRL var1, X509Certificate var2, CertPathResult var3) {
      try {
         String var4 = this.certJ.getDevice();
         JSAFE_PublicKey var5 = var3.getSubjectPublicKey(var4);
         if (var5 == null) {
            var5 = var2.getSubjectPublicKey(var4);
         }

         return var1.verifyCRLSignature(var4, var5, this.certJ.getRandomObject());
      } catch (NoServiceException var6) {
         return false;
      } catch (RandomException var7) {
         return false;
      } catch (CertificateException var8) {
         return false;
      }
   }

   private boolean verifyKeyUsage(CertPathCtx var1, X509Certificate var2) {
      if (var1.isFlagRaised(64)) {
         return true;
      } else {
         KeyUsage var3 = (KeyUsage)this.getExtension((X509Certificate)var2, 15);
         return var3 == null || (var3.getKeyUsage() & 33554432) != 0;
      }
   }

   private X509V3Extension getExtension(X509V3Extensions var1, int var2) {
      X509V3Extension var3 = null;
      if (var1 != null) {
         try {
            var3 = var1.getExtensionByType(var2);
         } catch (CertificateException var5) {
         }
      }

      return var3;
   }

   private X509V3Extension getExtension(X509CRL var1, int var2) {
      return var1 == null ? null : this.getExtension(var1.getExtensions(), var2);
   }

   private X509V3Extension getExtension(X509Certificate var1, int var2) {
      return var1 == null ? null : this.getExtension(var1.getExtensions(), var2);
   }

   private boolean hasUnknownCriticalExtension(X509CRL var1) throws CertStatusException {
      X509V3Extensions var2 = var1.getExtensions();
      if (var2 == null) {
         return false;
      } else {
         try {
            for(int var3 = 0; var3 < var2.getExtensionCount(); ++var3) {
               X509V3Extension var4 = var2.getExtensionByIndex(var3);
               int var5 = var4.getExtensionType();
               if (var5 != 28 && var4.getCriticality()) {
                  return true;
               }
            }
         } catch (CertificateException var6) {
            this.internalError(var6);
         }

         return false;
      }
   }

   private boolean findSerialNumberInCrl(X509Certificate var1, X509CRL var2, boolean var3) throws CertStatusException {
      RevokedCertificates var4 = var2.getRevokedCertificates();
      byte[] var5 = var1.getSerialNumber();
      if (var4 == null) {
         return false;
      } else {
         X500Name var6 = var2.getIssuerName();

         try {
            for(int var7 = 0; var7 < var4.getCertificateCount(); ++var7) {
               if (var3) {
                  CertificateIssuer var8 = (CertificateIssuer)this.getExtension((X509V3Extensions)var4.getExtensions(var7), 29);
                  if (var8 != null) {
                     var6 = this.findX500Name(var8.getGeneralNames());
                  }
               }

               if (CertJUtils.byteArraysEqual(var5, var4.getSerialNumber(var7))) {
                  if (var3) {
                     return var1.getIssuerName().equals(var6);
                  }

                  return true;
               }
            }
         } catch (CertificateException var9) {
            this.internalError(var9);
         }

         return false;
      }
   }

   private boolean isCRLObsolete(CertPathCtx var1, X509CRL var2, Date var3) {
      if (var1.isFlagRaised(262144)) {
         return false;
      } else {
         Date var4 = var2.getNextUpdate();
         return var4 != null && var3.after(var4);
      }
   }

   private final class a {
      static final int a = 100;
      int b;
      int c;
      int d;

      private a() {
         this.c = 100;
      }

      boolean a() {
         return this.b == -8388608;
      }

      boolean b() {
         return this.c == 100;
      }

      void c() {
         this.c = 100;
      }

      // $FF: synthetic method
      a(Object var2) {
         this();
      }
   }
}
