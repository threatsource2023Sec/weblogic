package org.python.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Enumerated;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.x509.AccessDescription;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.python.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.python.bouncycastle.asn1.x509.BasicConstraints;
import org.python.bouncycastle.asn1.x509.CRLDistPoint;
import org.python.bouncycastle.asn1.x509.DistributionPoint;
import org.python.bouncycastle.asn1.x509.DistributionPointName;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.asn1.x509.GeneralSubtree;
import org.python.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.python.bouncycastle.asn1.x509.NameConstraints;
import org.python.bouncycastle.asn1.x509.PolicyInformation;
import org.python.bouncycastle.asn1.x509.X509Extensions;
import org.python.bouncycastle.asn1.x509.qualified.Iso4217CurrencyCode;
import org.python.bouncycastle.asn1.x509.qualified.MonetaryValue;
import org.python.bouncycastle.asn1.x509.qualified.QCStatement;
import org.python.bouncycastle.i18n.ErrorBundle;
import org.python.bouncycastle.i18n.LocaleString;
import org.python.bouncycastle.i18n.filter.TrustedInput;
import org.python.bouncycastle.i18n.filter.UntrustedInput;
import org.python.bouncycastle.i18n.filter.UntrustedUrlInput;
import org.python.bouncycastle.jce.provider.AnnotatedException;
import org.python.bouncycastle.jce.provider.PKIXNameConstraintValidator;
import org.python.bouncycastle.jce.provider.PKIXNameConstraintValidatorException;
import org.python.bouncycastle.jce.provider.PKIXPolicyNode;
import org.python.bouncycastle.util.Integers;
import org.python.bouncycastle.x509.extension.X509ExtensionUtil;

public class PKIXCertPathReviewer extends CertPathValidatorUtilities {
   private static final String QC_STATEMENT;
   private static final String CRL_DIST_POINTS;
   private static final String AUTH_INFO_ACCESS;
   private static final String RESOURCE_NAME = "org.python.bouncycastle.x509.CertPathReviewerMessages";
   protected CertPath certPath;
   protected PKIXParameters pkixParams;
   protected Date validDate;
   protected List certs;
   protected int n;
   protected List[] notifications;
   protected List[] errors;
   protected TrustAnchor trustAnchor;
   protected PublicKey subjectPublicKey;
   protected PolicyNode policyTree;
   private boolean initialized;

   public void init(CertPath var1, PKIXParameters var2) throws CertPathReviewerException {
      if (this.initialized) {
         throw new IllegalStateException("object is already initialized!");
      } else {
         this.initialized = true;
         if (var1 == null) {
            throw new NullPointerException("certPath was null");
         } else {
            this.certPath = var1;
            this.certs = var1.getCertificates();
            this.n = this.certs.size();
            if (this.certs.isEmpty()) {
               throw new CertPathReviewerException(new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.emptyCertPath"));
            } else {
               this.pkixParams = (PKIXParameters)var2.clone();
               this.validDate = getValidDate(this.pkixParams);
               this.notifications = null;
               this.errors = null;
               this.trustAnchor = null;
               this.subjectPublicKey = null;
               this.policyTree = null;
            }
         }
      }
   }

   public PKIXCertPathReviewer(CertPath var1, PKIXParameters var2) throws CertPathReviewerException {
      this.init(var1, var2);
   }

   public PKIXCertPathReviewer() {
   }

   public CertPath getCertPath() {
      return this.certPath;
   }

   public int getCertPathSize() {
      return this.n;
   }

   public List[] getErrors() {
      this.doChecks();
      return this.errors;
   }

   public List getErrors(int var1) {
      this.doChecks();
      return this.errors[var1 + 1];
   }

   public List[] getNotifications() {
      this.doChecks();
      return this.notifications;
   }

   public List getNotifications(int var1) {
      this.doChecks();
      return this.notifications[var1 + 1];
   }

   public PolicyNode getPolicyTree() {
      this.doChecks();
      return this.policyTree;
   }

   public PublicKey getSubjectPublicKey() {
      this.doChecks();
      return this.subjectPublicKey;
   }

   public TrustAnchor getTrustAnchor() {
      this.doChecks();
      return this.trustAnchor;
   }

   public boolean isValidCertPath() {
      this.doChecks();
      boolean var1 = true;

      for(int var2 = 0; var2 < this.errors.length; ++var2) {
         if (!this.errors[var2].isEmpty()) {
            var1 = false;
            break;
         }
      }

      return var1;
   }

   protected void addNotification(ErrorBundle var1) {
      this.notifications[0].add(var1);
   }

   protected void addNotification(ErrorBundle var1, int var2) {
      if (var2 >= -1 && var2 < this.n) {
         this.notifications[var2 + 1].add(var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   protected void addError(ErrorBundle var1) {
      this.errors[0].add(var1);
   }

   protected void addError(ErrorBundle var1, int var2) {
      if (var2 >= -1 && var2 < this.n) {
         this.errors[var2 + 1].add(var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   protected void doChecks() {
      if (!this.initialized) {
         throw new IllegalStateException("Object not initialized. Call init() first.");
      } else {
         if (this.notifications == null) {
            this.notifications = new List[this.n + 1];
            this.errors = new List[this.n + 1];

            for(int var1 = 0; var1 < this.notifications.length; ++var1) {
               this.notifications[var1] = new ArrayList();
               this.errors[var1] = new ArrayList();
            }

            this.checkSignatures();
            this.checkNameConstraints();
            this.checkPathLength();
            this.checkPolicy();
            this.checkCriticalExtensions();
         }

      }
   }

   private void checkNameConstraints() {
      X509Certificate var1 = null;
      PKIXNameConstraintValidator var2 = new PKIXNameConstraintValidator();

      try {
         for(int var3 = this.certs.size() - 1; var3 > 0; --var3) {
            int var10000 = this.n - var3;
            var1 = (X509Certificate)this.certs.get(var3);
            int var25;
            if (!isSelfIssued(var1)) {
               X500Principal var5 = getSubjectPrincipal(var1);
               ASN1InputStream var6 = new ASN1InputStream(new ByteArrayInputStream(var5.getEncoded()));

               ASN1Sequence var7;
               ErrorBundle var9;
               try {
                  var7 = (ASN1Sequence)var6.readObject();
               } catch (IOException var18) {
                  var9 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.ncSubjectNameError", new Object[]{new UntrustedInput(var5)});
                  throw new CertPathReviewerException(var9, var18, this.certPath, var3);
               }

               try {
                  var2.checkPermittedDN(var7);
               } catch (PKIXNameConstraintValidatorException var17) {
                  var9 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.notPermittedDN", new Object[]{new UntrustedInput(var5.getName())});
                  throw new CertPathReviewerException(var9, var17, this.certPath, var3);
               }

               try {
                  var2.checkExcludedDN(var7);
               } catch (PKIXNameConstraintValidatorException var16) {
                  var9 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.excludedDN", new Object[]{new UntrustedInput(var5.getName())});
                  throw new CertPathReviewerException(var9, var16, this.certPath, var3);
               }

               ASN1Sequence var8;
               try {
                  var8 = (ASN1Sequence)getExtensionValue(var1, SUBJECT_ALTERNATIVE_NAME);
               } catch (AnnotatedException var15) {
                  ErrorBundle var10 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.subjAltNameExtError");
                  throw new CertPathReviewerException(var10, var15, this.certPath, var3);
               }

               if (var8 != null) {
                  for(var25 = 0; var25 < var8.size(); ++var25) {
                     GeneralName var26 = GeneralName.getInstance(var8.getObjectAt(var25));

                     try {
                        var2.checkPermitted(var26);
                        var2.checkExcluded(var26);
                     } catch (PKIXNameConstraintValidatorException var14) {
                        ErrorBundle var12 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.notPermittedEmail", new Object[]{new UntrustedInput(var26)});
                        throw new CertPathReviewerException(var12, var14, this.certPath, var3);
                     }
                  }
               }
            }

            ASN1Sequence var20;
            try {
               var20 = (ASN1Sequence)getExtensionValue(var1, NAME_CONSTRAINTS);
            } catch (AnnotatedException var13) {
               ErrorBundle var22 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.ncExtError");
               throw new CertPathReviewerException(var22, var13, this.certPath, var3);
            }

            if (var20 != null) {
               NameConstraints var21 = NameConstraints.getInstance(var20);
               GeneralSubtree[] var23 = var21.getPermittedSubtrees();
               if (var23 != null) {
                  var2.intersectPermittedSubtree(var23);
               }

               GeneralSubtree[] var24 = var21.getExcludedSubtrees();
               if (var24 != null) {
                  for(var25 = 0; var25 != var24.length; ++var25) {
                     var2.addExcludedSubtree(var24[var25]);
                  }
               }
            }
         }
      } catch (CertPathReviewerException var19) {
         this.addError(var19.getErrorMessage(), var19.getIndex());
      }

   }

   private void checkPathLength() {
      int var1 = this.n;
      int var2 = 0;
      X509Certificate var3 = null;

      for(int var4 = this.certs.size() - 1; var4 > 0; --var4) {
         int var10000 = this.n - var4;
         var3 = (X509Certificate)this.certs.get(var4);
         if (!isSelfIssued(var3)) {
            if (var1 <= 0) {
               ErrorBundle var6 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.pathLengthExtended");
               this.addError(var6);
            }

            --var1;
            ++var2;
         }

         BasicConstraints var11;
         try {
            var11 = BasicConstraints.getInstance(getExtensionValue(var3, BASIC_CONSTRAINTS));
         } catch (AnnotatedException var9) {
            ErrorBundle var8 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.processLengthConstError");
            this.addError(var8, var4);
            var11 = null;
         }

         if (var11 != null) {
            BigInteger var7 = var11.getPathLenConstraint();
            if (var7 != null) {
               int var12 = var7.intValue();
               if (var12 < var1) {
                  var1 = var12;
               }
            }
         }
      }

      ErrorBundle var10 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.totalPathLength", new Object[]{Integers.valueOf(var2)});
      this.addNotification(var10);
   }

   private void checkSignatures() {
      TrustAnchor var1 = null;
      X500Principal var2 = null;
      ErrorBundle var3 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certPathValidDate", new Object[]{new TrustedInput(this.validDate), new TrustedInput(new Date())});
      this.addNotification(var3);

      ErrorBundle var5;
      ErrorBundle var7;
      X509Certificate var35;
      try {
         var35 = (X509Certificate)this.certs.get(this.certs.size() - 1);
         Collection var37 = this.getTrustAnchors(var35, this.pkixParams.getTrustAnchors());
         if (var37.size() > 1) {
            var5 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.conflictingTrustAnchors", new Object[]{Integers.valueOf(var37.size()), new UntrustedInput(var35.getIssuerX500Principal())});
            this.addError(var5);
         } else if (var37.isEmpty()) {
            var5 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noTrustAnchorFound", new Object[]{new UntrustedInput(var35.getIssuerX500Principal()), Integers.valueOf(this.pkixParams.getTrustAnchors().size())});
            this.addError(var5);
         } else {
            var1 = (TrustAnchor)var37.iterator().next();
            PublicKey var40;
            if (var1.getTrustedCert() != null) {
               var40 = var1.getTrustedCert().getPublicKey();
            } else {
               var40 = var1.getCAPublicKey();
            }

            try {
               CertPathValidatorUtilities.verifyX509Certificate(var35, var40, this.pkixParams.getSigProvider());
            } catch (SignatureException var31) {
               var7 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustButInvalidCert");
               this.addError(var7);
            } catch (Exception var32) {
            }
         }
      } catch (CertPathReviewerException var33) {
         this.addError(var33.getErrorMessage());
      } catch (Throwable var34) {
         ErrorBundle var4 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.unknown", new Object[]{new UntrustedInput(var34.getMessage()), new UntrustedInput(var34)});
         this.addError(var4);
      }

      if (var1 != null) {
         var35 = var1.getTrustedCert();

         try {
            if (var35 != null) {
               var2 = getSubjectPrincipal(var35);
            } else {
               var2 = new X500Principal(var1.getCAName());
            }
         } catch (IllegalArgumentException var30) {
            var5 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustDNInvalid", new Object[]{new UntrustedInput(var1.getCAName())});
            this.addError(var5);
         }

         if (var35 != null) {
            boolean[] var38 = var35.getKeyUsage();
            if (var38 != null && !var38[5]) {
               var5 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustKeyUsage");
               this.addNotification(var5);
            }
         }
      }

      PublicKey var36 = null;
      X500Principal var39 = var2;
      X509Certificate var41 = null;
      AlgorithmIdentifier var6 = null;
      var7 = null;
      ASN1Encodable var8 = null;
      ASN1ObjectIdentifier var42;
      if (var1 != null) {
         var41 = var1.getTrustedCert();
         if (var41 != null) {
            var36 = var41.getPublicKey();
         } else {
            var36 = var1.getCAPublicKey();
         }

         try {
            var6 = getAlgorithmIdentifier(var36);
            var42 = var6.getAlgorithm();
            var8 = var6.getParameters();
         } catch (CertPathValidatorException var29) {
            ErrorBundle var10 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustPubKeyError");
            this.addError(var10);
            var6 = null;
         }
      }

      X509Certificate var9 = null;

      for(int var11 = this.certs.size() - 1; var11 >= 0; --var11) {
         int var43 = this.n - var11;
         var9 = (X509Certificate)this.certs.get(var11);
         ErrorBundle var12;
         ErrorBundle var13;
         if (var36 != null) {
            try {
               CertPathValidatorUtilities.verifyX509Certificate(var9, var36, this.pkixParams.getSigProvider());
            } catch (GeneralSecurityException var19) {
               var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.signatureNotVerified", new Object[]{var19.getMessage(), var19, var19.getClass().getName()});
               this.addError(var13, var11);
            }
         } else if (isSelfIssued(var9)) {
            try {
               CertPathValidatorUtilities.verifyX509Certificate(var9, var9.getPublicKey(), this.pkixParams.getSigProvider());
               var12 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.rootKeyIsValidButNotATrustAnchor");
               this.addError(var12, var11);
            } catch (GeneralSecurityException var28) {
               var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.signatureNotVerified", new Object[]{var28.getMessage(), var28, var28.getClass().getName()});
               this.addError(var13, var11);
            }
         } else {
            var12 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.NoIssuerPublicKey");
            byte[] var45 = var9.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
            if (var45 != null) {
               try {
                  AuthorityKeyIdentifier var14 = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(var45));
                  GeneralNames var15 = var14.getAuthorityCertIssuer();
                  if (var15 != null) {
                     GeneralName var16 = var15.getNames()[0];
                     BigInteger var17 = var14.getAuthorityCertSerialNumber();
                     if (var17 != null) {
                        Object[] var18 = new Object[]{new LocaleString("org.python.bouncycastle.x509.CertPathReviewerMessages", "missingIssuer"), " \"", var16, "\" ", new LocaleString("org.python.bouncycastle.x509.CertPathReviewerMessages", "missingSerial"), " ", var17};
                        var12.setExtraArguments(var18);
                     }
                  }
               } catch (IOException var27) {
               }
            }

            this.addError(var12, var11);
         }

         try {
            var9.checkValidity(this.validDate);
         } catch (CertificateNotYetValidException var25) {
            var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certificateNotYetValid", new Object[]{new TrustedInput(var9.getNotBefore())});
            this.addError(var13, var11);
         } catch (CertificateExpiredException var26) {
            var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certificateExpired", new Object[]{new TrustedInput(var9.getNotAfter())});
            this.addError(var13, var11);
         }

         ErrorBundle var46;
         if (this.pkixParams.isRevocationEnabled()) {
            CRLDistPoint var44 = null;

            try {
               ASN1Primitive var48 = getExtensionValue(var9, CRL_DIST_POINTS);
               if (var48 != null) {
                  var44 = CRLDistPoint.getInstance(var48);
               }
            } catch (AnnotatedException var24) {
               var46 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlDistPtExtError");
               this.addError(var46, var11);
            }

            AuthorityInformationAccess var50 = null;

            try {
               ASN1Primitive var49 = getExtensionValue(var9, AUTH_INFO_ACCESS);
               if (var49 != null) {
                  var50 = AuthorityInformationAccess.getInstance(var49);
               }
            } catch (AnnotatedException var23) {
               ErrorBundle var52 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlAuthInfoAccError");
               this.addError(var52, var11);
            }

            Vector var51 = this.getCRLDistUrls(var44);
            Vector var53 = this.getOCSPUrls(var50);
            Iterator var54 = var51.iterator();

            ErrorBundle var56;
            while(var54.hasNext()) {
               var56 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlDistPoint", new Object[]{new UntrustedUrlInput(var54.next())});
               this.addNotification(var56, var11);
            }

            var54 = var53.iterator();

            while(var54.hasNext()) {
               var56 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.ocspLocation", new Object[]{new UntrustedUrlInput(var54.next())});
               this.addNotification(var56, var11);
            }

            try {
               this.checkRevocation(this.pkixParams, var9, this.validDate, var41, var36, var51, var53, var11);
            } catch (CertPathReviewerException var22) {
               this.addError(var22.getErrorMessage(), var11);
            }
         }

         if (var39 != null && !var9.getIssuerX500Principal().equals(var39)) {
            var12 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certWrongIssuer", new Object[]{var39.getName(), var9.getIssuerX500Principal().getName()});
            this.addError(var12, var11);
         }

         if (var43 != this.n) {
            if (var9 != null && var9.getVersion() == 1) {
               var12 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noCACert");
               this.addError(var12, var11);
            }

            try {
               BasicConstraints var47 = BasicConstraints.getInstance(getExtensionValue(var9, BASIC_CONSTRAINTS));
               if (var47 != null) {
                  if (!var47.isCA()) {
                     var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noCACert");
                     this.addError(var13, var11);
                  }
               } else {
                  var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noBasicConstraints");
                  this.addError(var13, var11);
               }
            } catch (AnnotatedException var21) {
               var46 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.errorProcesingBC");
               this.addError(var46, var11);
            }

            boolean[] var55 = var9.getKeyUsage();
            if (var55 != null && !var55[5]) {
               var46 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noCertSign");
               this.addError(var46, var11);
            }
         }

         var41 = var9;
         var39 = var9.getSubjectX500Principal();

         try {
            var36 = getNextWorkingKey(this.certs, var11);
            var6 = getAlgorithmIdentifier(var36);
            var42 = var6.getAlgorithm();
            var8 = var6.getParameters();
         } catch (CertPathValidatorException var20) {
            var13 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.pubKeyError");
            this.addError(var13, var11);
            var6 = null;
            var7 = null;
            var8 = null;
         }
      }

      this.trustAnchor = var1;
      this.subjectPublicKey = var36;
   }

   private void checkPolicy() {
      Set var1 = this.pkixParams.getInitialPolicies();
      ArrayList[] var2 = new ArrayList[this.n + 1];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var2[var3] = new ArrayList();
      }

      HashSet var37 = new HashSet();
      var37.add("2.5.29.32.0");
      PKIXPolicyNode var4 = new PKIXPolicyNode(new ArrayList(), 0, var37, (PolicyNode)null, new HashSet(), "2.5.29.32.0", false);
      var2[0].add(var4);
      int var5;
      if (this.pkixParams.isExplicitPolicyRequired()) {
         var5 = 0;
      } else {
         var5 = this.n + 1;
      }

      int var6;
      if (this.pkixParams.isAnyPolicyInhibited()) {
         var6 = 0;
      } else {
         var6 = this.n + 1;
      }

      int var7;
      if (this.pkixParams.isPolicyMappingInhibited()) {
         var7 = 0;
      } else {
         var7 = this.n + 1;
      }

      HashSet var8 = null;
      X509Certificate var9 = null;

      try {
         int var10;
         ASN1Sequence var12;
         Enumeration var13;
         ErrorBundle var40;
         int var47;
         ArrayList var54;
         int var59;
         PKIXPolicyNode var68;
         PKIXPolicyNode var69;
         for(var10 = this.certs.size() - 1; var10 >= 0; --var10) {
            int var11 = this.n - var10;
            var9 = (X509Certificate)this.certs.get(var10);

            try {
               var12 = (ASN1Sequence)getExtensionValue(var9, CERTIFICATE_POLICIES);
            } catch (AnnotatedException var33) {
               ErrorBundle var14 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyExtError");
               throw new CertPathReviewerException(var14, var33, this.certPath, var10);
            }

            ErrorBundle var19;
            HashSet var49;
            if (var12 != null && var4 != null) {
               var13 = var12.getObjects();
               HashSet var41 = new HashSet();

               PolicyInformation var15;
               while(var13.hasMoreElements()) {
                  var15 = PolicyInformation.getInstance(var13.nextElement());
                  ASN1ObjectIdentifier var16 = var15.getPolicyIdentifier();
                  var41.add(var16.getId());
                  if (!"2.5.29.32.0".equals(var16.getId())) {
                     Set var17;
                     try {
                        var17 = getQualifierSet(var15.getPolicyQualifiers());
                     } catch (CertPathValidatorException var32) {
                        var19 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyQualifierError");
                        throw new CertPathReviewerException(var19, var32, this.certPath, var10);
                     }

                     boolean var18 = processCertD1i(var11, var2, var16, var17);
                     if (!var18) {
                        processCertD1ii(var11, var2, var16, var17);
                     }
                  }
               }

               if (var8 != null && !var8.contains("2.5.29.32.0")) {
                  Iterator var44 = var8.iterator();
                  var49 = new HashSet();

                  while(var44.hasNext()) {
                     Object var55 = var44.next();
                     if (var41.contains(var55)) {
                        var49.add(var55);
                     }
                  }

                  var8 = var49;
               } else {
                  var8 = var41;
               }

               ArrayList var58;
               int var64;
               if (var6 > 0 || var11 < this.n && isSelfIssued(var9)) {
                  var13 = var12.getObjects();

                  label443:
                  while(var13.hasMoreElements()) {
                     var15 = PolicyInformation.getInstance(var13.nextElement());
                     if ("2.5.29.32.0".equals(var15.getPolicyIdentifier().getId())) {
                        Set var51;
                        try {
                           var51 = getQualifierSet(var15.getPolicyQualifiers());
                        } catch (CertPathValidatorException var31) {
                           ErrorBundle var61 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyQualifierError");
                           throw new CertPathReviewerException(var61, var31, this.certPath, var10);
                        }

                        var58 = var2[var11 - 1];
                        var64 = 0;

                        label437:
                        while(true) {
                           if (var64 >= var58.size()) {
                              break label443;
                           }

                           var69 = (PKIXPolicyNode)var58.get(var64);
                           Iterator var20 = var69.getExpectedPolicies().iterator();

                           while(true) {
                              String var22;
                              while(true) {
                                 if (!var20.hasNext()) {
                                    ++var64;
                                    continue label437;
                                 }

                                 Object var21 = var20.next();
                                 if (var21 instanceof String) {
                                    var22 = (String)var21;
                                    break;
                                 }

                                 if (var21 instanceof ASN1ObjectIdentifier) {
                                    var22 = ((ASN1ObjectIdentifier)var21).getId();
                                    break;
                                 }
                              }

                              boolean var23 = false;
                              Iterator var24 = var69.getChildren();

                              while(var24.hasNext()) {
                                 PKIXPolicyNode var25 = (PKIXPolicyNode)var24.next();
                                 if (var22.equals(var25.getValidPolicy())) {
                                    var23 = true;
                                 }
                              }

                              if (!var23) {
                                 HashSet var83 = new HashSet();
                                 var83.add(var22);
                                 PKIXPolicyNode var26 = new PKIXPolicyNode(new ArrayList(), var11, var83, var69, var51, var22, false);
                                 var69.addChild(var26);
                                 var2[var11].add(var26);
                              }
                           }
                        }
                     }
                  }
               }

               var47 = var11 - 1;

               label413:
               while(true) {
                  if (var47 < 0) {
                     Set var50 = var9.getCriticalExtensionOIDs();
                     if (var50 == null) {
                        break;
                     }

                     boolean var56 = var50.contains(CERTIFICATE_POLICIES);
                     var58 = var2[var11];
                     var64 = 0;

                     while(true) {
                        if (var64 >= var58.size()) {
                           break label413;
                        }

                        var69 = (PKIXPolicyNode)var58.get(var64);
                        var69.setCritical(var56);
                        ++var64;
                     }
                  }

                  var54 = var2[var47];

                  for(var59 = 0; var59 < var54.size(); ++var59) {
                     var68 = (PKIXPolicyNode)var54.get(var59);
                     if (!var68.hasChildren()) {
                        var4 = removePolicyNode(var4, var2, var68);
                        if (var4 == null) {
                           break;
                        }
                     }
                  }

                  --var47;
               }
            }

            if (var12 == null) {
               var4 = null;
            }

            if (var5 <= 0 && var4 == null) {
               var40 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noValidPolicyTree");
               throw new CertPathReviewerException(var40);
            }

            if (var11 != this.n) {
               ASN1Primitive var39;
               ErrorBundle var53;
               try {
                  var39 = getExtensionValue(var9, POLICY_MAPPINGS);
               } catch (AnnotatedException var30) {
                  var53 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyMapExtError");
                  throw new CertPathReviewerException(var53, var30, this.certPath, var10);
               }

               ASN1Sequence var42;
               if (var39 != null) {
                  var42 = (ASN1Sequence)var39;

                  for(var47 = 0; var47 < var42.size(); ++var47) {
                     ASN1Sequence var57 = (ASN1Sequence)var42.getObjectAt(var47);
                     ASN1ObjectIdentifier var66 = (ASN1ObjectIdentifier)var57.getObjectAt(0);
                     ASN1ObjectIdentifier var73 = (ASN1ObjectIdentifier)var57.getObjectAt(1);
                     if ("2.5.29.32.0".equals(var66.getId())) {
                        var19 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.invalidPolicyMapping");
                        throw new CertPathReviewerException(var19, this.certPath, var10);
                     }

                     if ("2.5.29.32.0".equals(var73.getId())) {
                        var19 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.invalidPolicyMapping");
                        throw new CertPathReviewerException(var19, this.certPath, var10);
                     }
                  }
               }

               if (var39 != null) {
                  var42 = (ASN1Sequence)var39;
                  HashMap var60 = new HashMap();
                  var49 = new HashSet();

                  for(var59 = 0; var59 < var42.size(); ++var59) {
                     ASN1Sequence var75 = (ASN1Sequence)var42.getObjectAt(var59);
                     String var76 = ((ASN1ObjectIdentifier)var75.getObjectAt(0)).getId();
                     String var74 = ((ASN1ObjectIdentifier)var75.getObjectAt(1)).getId();
                     if (!var60.containsKey(var76)) {
                        HashSet var81 = new HashSet();
                        var81.add(var74);
                        var60.put(var76, var81);
                        var49.add(var76);
                     } else {
                        Set var82 = (Set)var60.get(var76);
                        var82.add(var74);
                     }
                  }

                  Iterator var71 = var49.iterator();

                  while(var71.hasNext()) {
                     String var79 = (String)var71.next();
                     if (var7 > 0) {
                        ErrorBundle var77;
                        try {
                           prepareNextCertB1(var11, var2, var79, var60, var9);
                        } catch (AnnotatedException var28) {
                           var77 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyExtError");
                           throw new CertPathReviewerException(var77, var28, this.certPath, var10);
                        } catch (CertPathValidatorException var29) {
                           var77 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyQualifierError");
                           throw new CertPathReviewerException(var77, var29, this.certPath, var10);
                        }
                     } else if (var7 <= 0) {
                        var4 = prepareNextCertB2(var11, var2, var79, var4);
                     }
                  }
               }

               if (!isSelfIssued(var9)) {
                  if (var5 != 0) {
                     --var5;
                  }

                  if (var7 != 0) {
                     --var7;
                  }

                  if (var6 != 0) {
                     --var6;
                  }
               }

               try {
                  var42 = (ASN1Sequence)getExtensionValue(var9, POLICY_CONSTRAINTS);
                  if (var42 != null) {
                     Enumeration var65 = var42.getObjects();

                     while(var65.hasMoreElements()) {
                        ASN1TaggedObject var62 = (ASN1TaggedObject)var65.nextElement();
                        switch (var62.getTagNo()) {
                           case 0:
                              var59 = ASN1Integer.getInstance(var62, false).getValue().intValue();
                              if (var59 < var5) {
                                 var5 = var59;
                              }
                              break;
                           case 1:
                              var59 = ASN1Integer.getInstance(var62, false).getValue().intValue();
                              if (var59 < var7) {
                                 var7 = var59;
                              }
                        }
                     }
                  }
               } catch (AnnotatedException var35) {
                  var53 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyConstExtError");
                  throw new CertPathReviewerException(var53, this.certPath, var10);
               }

               try {
                  ASN1Integer var45 = (ASN1Integer)getExtensionValue(var9, INHIBIT_ANY_POLICY);
                  if (var45 != null) {
                     var47 = var45.getValue().intValue();
                     if (var47 < var6) {
                        var6 = var47;
                     }
                  }
               } catch (AnnotatedException var27) {
                  var53 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyInhibitExtError");
                  throw new CertPathReviewerException(var53, this.certPath, var10);
               }
            }
         }

         if (!isSelfIssued(var9) && var5 > 0) {
            --var5;
         }

         try {
            var12 = (ASN1Sequence)getExtensionValue(var9, POLICY_CONSTRAINTS);
            if (var12 != null) {
               var13 = var12.getObjects();

               while(var13.hasMoreElements()) {
                  ASN1TaggedObject var46 = (ASN1TaggedObject)var13.nextElement();
                  switch (var46.getTagNo()) {
                     case 0:
                        var47 = ASN1Integer.getInstance(var46, false).getValue().intValue();
                        if (var47 == 0) {
                           var5 = 0;
                        }
                  }
               }
            }
         } catch (AnnotatedException var34) {
            var40 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyConstExtError");
            throw new CertPathReviewerException(var40, this.certPath, var10);
         }

         PKIXPolicyNode var38;
         if (var4 == null) {
            if (this.pkixParams.isExplicitPolicyRequired()) {
               var40 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.explicitPolicy");
               throw new CertPathReviewerException(var40, this.certPath, var10);
            }

            var38 = null;
         } else {
            HashSet var43;
            int var48;
            Iterator var52;
            int var63;
            String var67;
            ArrayList var70;
            PKIXPolicyNode var72;
            PKIXPolicyNode var78;
            Iterator var80;
            if (isAnyPolicy(var1)) {
               if (this.pkixParams.isExplicitPolicyRequired()) {
                  if (var8.isEmpty()) {
                     var40 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.explicitPolicy");
                     throw new CertPathReviewerException(var40, this.certPath, var10);
                  }

                  var43 = new HashSet();

                  for(var48 = 0; var48 < var2.length; ++var48) {
                     var70 = var2[var48];

                     for(var63 = 0; var63 < var70.size(); ++var63) {
                        var78 = (PKIXPolicyNode)var70.get(var63);
                        if ("2.5.29.32.0".equals(var78.getValidPolicy())) {
                           var80 = var78.getChildren();

                           while(var80.hasNext()) {
                              var43.add(var80.next());
                           }
                        }
                     }
                  }

                  var52 = var43.iterator();

                  while(var52.hasNext()) {
                     var72 = (PKIXPolicyNode)var52.next();
                     var67 = var72.getValidPolicy();
                     if (!var8.contains(var67)) {
                     }
                  }

                  if (var4 != null) {
                     for(var47 = this.n - 1; var47 >= 0; --var47) {
                        var54 = var2[var47];

                        for(var59 = 0; var59 < var54.size(); ++var59) {
                           var68 = (PKIXPolicyNode)var54.get(var59);
                           if (!var68.hasChildren()) {
                              var4 = removePolicyNode(var4, var2, var68);
                           }
                        }
                     }
                  }
               }

               var38 = var4;
            } else {
               var43 = new HashSet();
               var48 = 0;

               while(true) {
                  if (var48 >= var2.length) {
                     var52 = var43.iterator();

                     while(var52.hasNext()) {
                        var72 = (PKIXPolicyNode)var52.next();
                        var67 = var72.getValidPolicy();
                        if (!var1.contains(var67)) {
                           var4 = removePolicyNode(var4, var2, var72);
                        }
                     }

                     if (var4 != null) {
                        for(var47 = this.n - 1; var47 >= 0; --var47) {
                           var54 = var2[var47];

                           for(var59 = 0; var59 < var54.size(); ++var59) {
                              var68 = (PKIXPolicyNode)var54.get(var59);
                              if (!var68.hasChildren()) {
                                 var4 = removePolicyNode(var4, var2, var68);
                              }
                           }
                        }
                     }

                     var38 = var4;
                     break;
                  }

                  var70 = var2[var48];

                  for(var63 = 0; var63 < var70.size(); ++var63) {
                     var78 = (PKIXPolicyNode)var70.get(var63);
                     if ("2.5.29.32.0".equals(var78.getValidPolicy())) {
                        var80 = var78.getChildren();

                        while(var80.hasNext()) {
                           var69 = (PKIXPolicyNode)var80.next();
                           if (!"2.5.29.32.0".equals(var69.getValidPolicy())) {
                              var43.add(var69);
                           }
                        }
                     }
                  }

                  ++var48;
               }
            }
         }

         if (var5 <= 0 && var38 == null) {
            var40 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.invalidPolicy");
            throw new CertPathReviewerException(var40);
         }
      } catch (CertPathReviewerException var36) {
         this.addError(var36.getErrorMessage(), var36.getIndex());
         var4 = null;
      }

   }

   private void checkCriticalExtensions() {
      List var1 = this.pkixParams.getCertPathCheckers();
      Iterator var2 = var1.iterator();

      try {
         try {
            while(var2.hasNext()) {
               ((PKIXCertPathChecker)var2.next()).init(false);
            }
         } catch (CertPathValidatorException var10) {
            ErrorBundle var4 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certPathCheckerError", new Object[]{var10.getMessage(), var10, var10.getClass().getName()});
            throw new CertPathReviewerException(var4, var10);
         }

         X509Certificate var3 = null;

         for(int var12 = this.certs.size() - 1; var12 >= 0; --var12) {
            var3 = (X509Certificate)this.certs.get(var12);
            Set var5 = var3.getCriticalExtensionOIDs();
            if (var5 != null && !var5.isEmpty()) {
               var5.remove(KEY_USAGE);
               var5.remove(CERTIFICATE_POLICIES);
               var5.remove(POLICY_MAPPINGS);
               var5.remove(INHIBIT_ANY_POLICY);
               var5.remove(ISSUING_DISTRIBUTION_POINT);
               var5.remove(DELTA_CRL_INDICATOR);
               var5.remove(POLICY_CONSTRAINTS);
               var5.remove(BASIC_CONSTRAINTS);
               var5.remove(SUBJECT_ALTERNATIVE_NAME);
               var5.remove(NAME_CONSTRAINTS);
               if (var5.contains(QC_STATEMENT) && this.processQcStatements(var3, var12)) {
                  var5.remove(QC_STATEMENT);
               }

               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  try {
                     ((PKIXCertPathChecker)var6.next()).check(var3, var5);
                  } catch (CertPathValidatorException var9) {
                     ErrorBundle var8 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.criticalExtensionError", new Object[]{var9.getMessage(), var9, var9.getClass().getName()});
                     throw new CertPathReviewerException(var8, var9.getCause(), this.certPath, var12);
                  }
               }

               if (!var5.isEmpty()) {
                  Iterator var13 = var5.iterator();

                  while(var13.hasNext()) {
                     ErrorBundle var7 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.unknownCriticalExt", new Object[]{new ASN1ObjectIdentifier((String)var13.next())});
                     this.addError(var7, var12);
                  }
               }
            }
         }
      } catch (CertPathReviewerException var11) {
         this.addError(var11.getErrorMessage(), var11.getIndex());
      }

   }

   private boolean processQcStatements(X509Certificate var1, int var2) {
      try {
         boolean var3 = false;
         ASN1Sequence var13 = (ASN1Sequence)getExtensionValue(var1, QC_STATEMENT);

         for(int var5 = 0; var5 < var13.size(); ++var5) {
            QCStatement var6 = QCStatement.getInstance(var13.getObjectAt(var5));
            ErrorBundle var7;
            if (QCStatement.id_etsi_qcs_QcCompliance.equals(var6.getStatementId())) {
               var7 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcEuCompliance");
               this.addNotification(var7, var2);
            } else if (!QCStatement.id_qcs_pkixQCSyntax_v1.equals(var6.getStatementId())) {
               if (QCStatement.id_etsi_qcs_QcSSCD.equals(var6.getStatementId())) {
                  var7 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcSSCD");
                  this.addNotification(var7, var2);
               } else if (QCStatement.id_etsi_qcs_LimiteValue.equals(var6.getStatementId())) {
                  MonetaryValue var14 = MonetaryValue.getInstance(var6.getStatementInfo());
                  Iso4217CurrencyCode var8 = var14.getCurrency();
                  double var9 = var14.getAmount().doubleValue() * Math.pow(10.0, var14.getExponent().doubleValue());
                  ErrorBundle var11;
                  if (var14.getCurrency().isAlphabetic()) {
                     var11 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcLimitValueAlpha", new Object[]{var14.getCurrency().getAlphabetic(), new TrustedInput(new Double(var9)), var14});
                  } else {
                     var11 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcLimitValueNum", new Object[]{Integers.valueOf(var14.getCurrency().getNumeric()), new TrustedInput(new Double(var9)), var14});
                  }

                  this.addNotification(var11, var2);
               } else {
                  var7 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcUnknownStatement", new Object[]{var6.getStatementId(), new UntrustedInput(var6)});
                  this.addNotification(var7, var2);
                  var3 = true;
               }
            }
         }

         return !var3;
      } catch (AnnotatedException var12) {
         ErrorBundle var4 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcStatementExtError");
         this.addError(var4, var2);
         return false;
      }
   }

   private String IPtoString(byte[] var1) {
      String var2;
      try {
         var2 = InetAddress.getByAddress(var1).getHostAddress();
      } catch (Exception var6) {
         StringBuffer var4 = new StringBuffer();

         for(int var5 = 0; var5 != var1.length; ++var5) {
            var4.append(Integer.toHexString(var1[var5] & 255));
            var4.append(' ');
         }

         var2 = var4.toString();
      }

      return var2;
   }

   protected void checkRevocation(PKIXParameters var1, X509Certificate var2, Date var3, X509Certificate var4, PublicKey var5, Vector var6, Vector var7, int var8) throws CertPathReviewerException {
      this.checkCRLs(var1, var2, var3, var4, var5, var6, var8);
   }

   protected void checkCRLs(PKIXParameters var1, X509Certificate var2, Date var3, X509Certificate var4, PublicKey var5, Vector var6, int var7) throws CertPathReviewerException {
      X509CRLStoreSelector var8 = new X509CRLStoreSelector();

      try {
         var8.addIssuerName(getEncodedIssuerPrincipal(var2).getEncoded());
      } catch (IOException var31) {
         ErrorBundle var10 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlIssuerException");
         throw new CertPathReviewerException(var10, var31);
      }

      var8.setCertificateChecking(var2);

      Iterator var9;
      ArrayList var12;
      ErrorBundle var14;
      try {
         Set var34 = CRL_UTIL.findCRLs(var8, var1);
         var9 = var34.iterator();
         if (var34.isEmpty()) {
            var34 = CRL_UTIL.findCRLs(new X509CRLStoreSelector(), var1);
            Iterator var36 = var34.iterator();
            var12 = new ArrayList();

            while(var36.hasNext()) {
               var12.add(((X509CRL)var36.next()).getIssuerX500Principal());
            }

            int var13 = var12.size();
            var14 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noCrlInCertstore", new Object[]{new UntrustedInput(var8.getIssuerNames()), new UntrustedInput(var12), Integers.valueOf(var13)});
            this.addNotification(var14, var7);
         }
      } catch (AnnotatedException var33) {
         ErrorBundle var11 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlExtractionError", new Object[]{var33.getCause().getMessage(), var33.getCause(), var33.getCause().getClass().getName()});
         this.addError(var11, var7);
         var9 = (new ArrayList()).iterator();
      }

      boolean var35 = false;
      X509CRL var37 = null;

      while(var9.hasNext()) {
         var37 = (X509CRL)var9.next();
         ErrorBundle var38;
         if (var37.getNextUpdate() == null || var1.getDate().before(var37.getNextUpdate())) {
            var35 = true;
            var38 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.localValidCRL", new Object[]{new TrustedInput(var37.getThisUpdate()), new TrustedInput(var37.getNextUpdate())});
            this.addNotification(var38, var7);
            break;
         }

         var38 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.localInvalidCRL", new Object[]{new TrustedInput(var37.getThisUpdate()), new TrustedInput(var37.getNextUpdate())});
         this.addNotification(var38, var7);
      }

      ErrorBundle var15;
      if (!var35) {
         var12 = null;
         Iterator var39 = var6.iterator();

         while(var39.hasNext()) {
            try {
               String var44 = (String)var39.next();
               X509CRL var40 = this.getCRL(var44);
               if (var40 != null) {
                  if (!var2.getIssuerX500Principal().equals(var40.getIssuerX500Principal())) {
                     var15 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.onlineCRLWrongCA", new Object[]{new UntrustedInput(var40.getIssuerX500Principal().getName()), new UntrustedInput(var2.getIssuerX500Principal().getName()), new UntrustedUrlInput(var44)});
                     this.addNotification(var15, var7);
                  } else {
                     if (var40.getNextUpdate() == null || this.pkixParams.getDate().before(var40.getNextUpdate())) {
                        var35 = true;
                        var15 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.onlineValidCRL", new Object[]{new TrustedInput(var40.getThisUpdate()), new TrustedInput(var40.getNextUpdate()), new UntrustedUrlInput(var44)});
                        this.addNotification(var15, var7);
                        var37 = var40;
                        break;
                     }

                     var15 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.onlineInvalidCRL", new Object[]{new TrustedInput(var40.getThisUpdate()), new TrustedInput(var40.getNextUpdate()), new UntrustedUrlInput(var44)});
                     this.addNotification(var15, var7);
                  }
               }
            } catch (CertPathReviewerException var32) {
               this.addNotification(var32.getErrorMessage(), var7);
            }
         }
      }

      ErrorBundle var47;
      if (var37 != null) {
         if (var4 != null) {
            boolean[] var41 = var4.getKeyUsage();
            if (var41 != null && (var41.length < 7 || !var41[6])) {
               var14 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noCrlSigningPermited");
               throw new CertPathReviewerException(var14);
            }
         }

         if (var5 == null) {
            var47 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlNoIssuerPublicKey");
            throw new CertPathReviewerException(var47);
         }

         try {
            var37.verify(var5, "BC");
         } catch (Exception var30) {
            var14 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlVerifyFailed");
            throw new CertPathReviewerException(var14, var30);
         }

         X509CRLEntry var42 = var37.getRevokedCertificate(var2.getSerialNumber());
         ErrorBundle var16;
         if (var42 != null) {
            String var43 = null;
            if (var42.hasExtensions()) {
               ASN1Enumerated var45;
               try {
                  var45 = ASN1Enumerated.getInstance(getExtensionValue(var42, X509Extensions.ReasonCode.getId()));
               } catch (AnnotatedException var29) {
                  var16 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlReasonExtError");
                  throw new CertPathReviewerException(var16, var29);
               }

               if (var45 != null) {
                  var43 = crlReasons[var45.getValue().intValue()];
               }
            }

            if (var43 == null) {
               var43 = crlReasons[7];
            }

            LocaleString var46 = new LocaleString("org.python.bouncycastle.x509.CertPathReviewerMessages", var43);
            if (!var3.before(var42.getRevocationDate())) {
               var15 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certRevoked", new Object[]{new TrustedInput(var42.getRevocationDate()), var46});
               throw new CertPathReviewerException(var15);
            }

            var15 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.revokedAfterValidation", new Object[]{new TrustedInput(var42.getRevocationDate()), var46});
            this.addNotification(var15, var7);
         } else {
            var47 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.notRevoked");
            this.addNotification(var47, var7);
         }

         if (var37.getNextUpdate() != null && var37.getNextUpdate().before(this.pkixParams.getDate())) {
            var47 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlUpdateAvailable", new Object[]{new TrustedInput(var37.getNextUpdate())});
            this.addNotification(var47, var7);
         }

         ASN1Primitive var49;
         try {
            var49 = getExtensionValue(var37, ISSUING_DISTRIBUTION_POINT);
         } catch (AnnotatedException var28) {
            var15 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.distrPtExtError");
            throw new CertPathReviewerException(var15);
         }

         ASN1Primitive var48;
         try {
            var48 = getExtensionValue(var37, DELTA_CRL_INDICATOR);
         } catch (AnnotatedException var27) {
            var16 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.deltaCrlExtError");
            throw new CertPathReviewerException(var16);
         }

         ErrorBundle var17;
         ErrorBundle var55;
         if (var48 != null) {
            X509CRLStoreSelector var51 = new X509CRLStoreSelector();

            try {
               var51.addIssuerName(getIssuerPrincipal(var37).getEncoded());
            } catch (IOException var26) {
               var17 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlIssuerException");
               throw new CertPathReviewerException(var17, var26);
            }

            var51.setMinCRLNumber(((ASN1Integer)var48).getPositiveValue());

            try {
               var51.setMaxCRLNumber(((ASN1Integer)getExtensionValue(var37, CRL_NUMBER)).getPositiveValue().subtract(BigInteger.valueOf(1L)));
            } catch (AnnotatedException var25) {
               var17 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlNbrExtError");
               throw new CertPathReviewerException(var17, var25);
            }

            boolean var50 = false;

            Iterator var53;
            try {
               var53 = CRL_UTIL.findCRLs(var51, var1).iterator();
            } catch (AnnotatedException var24) {
               ErrorBundle var19 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlExtractionError");
               throw new CertPathReviewerException(var19, var24);
            }

            while(var53.hasNext()) {
               X509CRL var18 = (X509CRL)var53.next();

               ASN1Primitive var56;
               try {
                  var56 = getExtensionValue(var18, ISSUING_DISTRIBUTION_POINT);
               } catch (AnnotatedException var23) {
                  ErrorBundle var21 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.distrPtExtError");
                  throw new CertPathReviewerException(var21, var23);
               }

               if (var49 == null) {
                  if (var56 == null) {
                     var50 = true;
                     break;
                  }
               } else if (var49.equals(var56)) {
                  var50 = true;
                  break;
               }
            }

            if (!var50) {
               var55 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noBaseCRL");
               throw new CertPathReviewerException(var55);
            }
         }

         if (var49 != null) {
            IssuingDistributionPoint var52 = IssuingDistributionPoint.getInstance(var49);
            var16 = null;

            BasicConstraints var54;
            try {
               var54 = BasicConstraints.getInstance(getExtensionValue(var2, BASIC_CONSTRAINTS));
            } catch (AnnotatedException var22) {
               var55 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlBCExtError");
               throw new CertPathReviewerException(var55, var22);
            }

            if (var52.onlyContainsUserCerts() && var54 != null && var54.isCA()) {
               var17 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlOnlyUserCert");
               throw new CertPathReviewerException(var17);
            }

            if (var52.onlyContainsCACerts() && (var54 == null || !var54.isCA())) {
               var17 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlOnlyCaCert");
               throw new CertPathReviewerException(var17);
            }

            if (var52.onlyContainsAttributeCerts()) {
               var17 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.crlOnlyAttrCert");
               throw new CertPathReviewerException(var17);
            }
         }
      }

      if (!var35) {
         var47 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.noValidCrlFound");
         throw new CertPathReviewerException(var47);
      }
   }

   protected Vector getCRLDistUrls(CRLDistPoint var1) {
      Vector var2 = new Vector();
      if (var1 != null) {
         DistributionPoint[] var3 = var1.getDistributionPoints();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            DistributionPointName var5 = var3[var4].getDistributionPoint();
            if (var5.getType() == 0) {
               GeneralName[] var6 = GeneralNames.getInstance(var5.getName()).getNames();

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  if (var6[var7].getTagNo() == 6) {
                     String var8 = ((DERIA5String)var6[var7].getName()).getString();
                     var2.add(var8);
                  }
               }
            }
         }
      }

      return var2;
   }

   protected Vector getOCSPUrls(AuthorityInformationAccess var1) {
      Vector var2 = new Vector();
      if (var1 != null) {
         AccessDescription[] var3 = var1.getAccessDescriptions();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if (var3[var4].getAccessMethod().equals(AccessDescription.id_ad_ocsp)) {
               GeneralName var5 = var3[var4].getAccessLocation();
               if (var5.getTagNo() == 6) {
                  String var6 = ((DERIA5String)var5.getName()).getString();
                  var2.add(var6);
               }
            }
         }
      }

      return var2;
   }

   private X509CRL getCRL(String var1) throws CertPathReviewerException {
      X509CRL var2 = null;

      try {
         URL var3 = new URL(var1);
         if (var3.getProtocol().equals("http") || var3.getProtocol().equals("https")) {
            HttpURLConnection var7 = (HttpURLConnection)var3.openConnection();
            var7.setUseCaches(false);
            var7.setDoInput(true);
            var7.connect();
            if (var7.getResponseCode() != 200) {
               throw new Exception(var7.getResponseMessage());
            }

            CertificateFactory var5 = CertificateFactory.getInstance("X.509", "BC");
            var2 = (X509CRL)var5.generateCRL(var7.getInputStream());
         }

         return var2;
      } catch (Exception var6) {
         ErrorBundle var4 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.loadCrlDistPointError", new Object[]{new UntrustedInput(var1), var6.getMessage(), var6, var6.getClass().getName()});
         throw new CertPathReviewerException(var4);
      }
   }

   protected Collection getTrustAnchors(X509Certificate var1, Set var2) throws CertPathReviewerException {
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();
      X509CertSelector var5 = new X509CertSelector();

      try {
         var5.setSubject(getEncodedIssuerPrincipal(var1).getEncoded());
         byte[] var6 = var1.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
         if (var6 != null) {
            ASN1OctetString var12 = (ASN1OctetString)ASN1Primitive.fromByteArray(var6);
            AuthorityKeyIdentifier var8 = AuthorityKeyIdentifier.getInstance(ASN1Primitive.fromByteArray(var12.getOctets()));
            var5.setSerialNumber(var8.getAuthorityCertSerialNumber());
            byte[] var9 = var8.getKeyIdentifier();
            if (var9 != null) {
               var5.setSubjectKeyIdentifier((new DEROctetString(var9)).getEncoded());
            }
         }
      } catch (IOException var10) {
         ErrorBundle var7 = new ErrorBundle("org.python.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustAnchorIssuerError");
         throw new CertPathReviewerException(var7);
      }

      while(var4.hasNext()) {
         TrustAnchor var11 = (TrustAnchor)var4.next();
         if (var11.getTrustedCert() != null) {
            if (var5.match(var11.getTrustedCert())) {
               var3.add(var11);
            }
         } else if (var11.getCAName() != null && var11.getCAPublicKey() != null) {
            X500Principal var13 = getEncodedIssuerPrincipal(var1);
            X500Principal var14 = new X500Principal(var11.getCAName());
            if (var13.equals(var14)) {
               var3.add(var11);
            }
         }
      }

      return var3;
   }

   static {
      QC_STATEMENT = X509Extensions.QCStatements.getId();
      CRL_DIST_POINTS = X509Extensions.CRLDistributionPoints.getId();
      AUTH_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
   }
}
