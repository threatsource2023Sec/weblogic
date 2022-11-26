package weblogic.security.pki.revocation.common;

import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

public final class RevocationCertPathChecker extends PKIXCertPathChecker {
   private static final String CLASSNAME = RevocationCertPathChecker.class.getName();
   private final AbstractCertRevocContext context;
   private final OcspChecker ocspChecker;
   private final CrlChecker crlChecker;
   private X509Certificate issuerX509Cert;

   public static RevocationCertPathChecker getInstance(AbstractCertRevocContext context) {
      return new RevocationCertPathChecker(context);
   }

   private RevocationCertPathChecker(AbstractCertRevocContext context) {
      Util.checkNotNull("AbstractCertRevocContext", context);
      this.context = context;
      this.ocspChecker = OcspChecker.getInstance(context);
      this.crlChecker = CrlChecker.getInstance(context);
   }

   public void init(boolean forward) throws CertPathValidatorException {
      if (this.context.isLoggable(Level.FINEST)) {
         this.context.log(Level.FINEST, "{0}.init called (forward not supported), forward={1}.", CLASSNAME, forward);
      }

      this.issuerX509Cert = null;
      if (forward) {
         throw new CertPathValidatorException("Forward checking is not supported.");
      }
   }

   public boolean isForwardCheckingSupported() {
      return false;
   }

   public Set getSupportedExtensions() {
      Set result = Collections.emptySet();
      if (this.context.isLoggable(Level.FINEST)) {
         this.context.log(Level.FINEST, "{0}.getSupportedExtensions called.", CLASSNAME);
      }

      return result;
   }

   public void check(Certificate cert, Collection unresolvedCritExts) throws CertPathValidatorException {
      if (!this.isCertToCheckNull(cert) && this.isCertToCheckX509(cert)) {
         X509Certificate x509CertToCheck = (X509Certificate)cert;

         try {
            if (!this.isEnabled()) {
               CrlCacheUpdater.cancelAllMaintenanceTasks(this.context.getLogListener());
               return;
            }

            if (!CrlCacheUpdater.isAllMaintenanceTasksActive()) {
               this.startAllMaintenanceTasks();
            }

            X500Principal certToCheckSubjectDn = x509CertToCheck.getSubjectX500Principal();
            X500Principal certToCheckIssuerDn = x509CertToCheck.getIssuerX500Principal();
            if (this.context.isLoggable(Level.FINE)) {
               this.context.log(Level.FINE, "Revocation status checking X509 certificate with subject \"{0}\" and issuer \"{1}\".", certToCheckSubjectDn, certToCheckIssuerDn);
            }

            if (!this.isIssuerDnMissing(certToCheckIssuerDn)) {
               if (this.isCheckingDisabled(certToCheckIssuerDn)) {
                  return;
               }

               boolean isFailOnUnknownRevocStatus = this.isFailOnUnknownRevocStatus(certToCheckIssuerDn);
               CertRevocStatus revocStatus = null;

               try {
                  this.context.logAttemptingCertRevocCheck(certToCheckSubjectDn);
                  if (!this.ensureIssuerCert(certToCheckIssuerDn, isFailOnUnknownRevocStatus)) {
                     this.context.logUnknownCertRevocStatusNoFail(certToCheckSubjectDn);
                     return;
                  }

                  if (this.isSubjectDnMissing(isFailOnUnknownRevocStatus, certToCheckSubjectDn)) {
                     this.context.logUnknownCertRevocStatusNoFail(certToCheckSubjectDn);
                     return;
                  }

                  if (!this.isExpectedIssuer(certToCheckIssuerDn, isFailOnUnknownRevocStatus)) {
                     this.context.logUnknownCertRevocStatusNoFail(certToCheckSubjectDn);
                     return;
                  }

                  revocStatus = this.runThruMethods(x509CertToCheck, certToCheckIssuerDn);
                  this.logCertRevocStatus(certToCheckSubjectDn, revocStatus);
               } catch (Exception var12) {
                  if (this.context.isLoggable(Level.FINE)) {
                     this.context.log(Level.FINE, var12, "An exception occurred while checking revocation of certificate={0},\nexception={1}", certToCheckSubjectDn, var12.getMessage());
                  }

                  if (!isFailOnUnknownRevocStatus) {
                     this.context.logUnknownCertRevocStatusNoFail(certToCheckSubjectDn);
                     return;
                  }

                  this.context.logUnknownCertRevocStatusFail(certToCheckSubjectDn);
                  this.throwCertPathValidatorException(certToCheckSubjectDn, var12);
               }

               if (null == revocStatus) {
                  if (isFailOnUnknownRevocStatus) {
                     this.context.logUnknownCertRevocStatusFail(certToCheckSubjectDn);
                     throw new CertPathValidatorException("Unknown revocation status for certificate \"" + certToCheckSubjectDn + "\".");
                  }

                  this.context.logUnknownCertRevocStatusNoFail(certToCheckSubjectDn);
                  return;
               }

               if (revocStatus.isRevoked()) {
                  this.context.logRevokedCertRevocStatusFail(certToCheckSubjectDn);
                  throw new CertPathValidatorException("Certificate revoked: \"" + certToCheckSubjectDn + "\".");
               }

               this.context.logNotRevokedCertRevocStatusNotFail(certToCheckSubjectDn);
               return;
            }
         } finally {
            this.issuerX509Cert = x509CertToCheck;
         }

      } else {
         this.issuerX509Cert = null;
      }
   }

   private void throwCertPathValidatorException(X500Principal certToCheckSubjectDn, Exception e) throws CertPathValidatorException {
      if (e instanceof CertPathValidatorException) {
         throw (CertPathValidatorException)e;
      } else {
         throw new CertPathValidatorException("Unknown revocation status for certificate \"" + certToCheckSubjectDn + "\".", e);
      }
   }

   private void logCertRevocStatus(X500Principal certToCheckSubjectDn, CertRevocStatus revocStatus) {
      if (this.context.isLoggable(Level.FINEST)) {
         this.context.log(Level.FINEST, "The revocation status of certificate {0} is:\n{1}.", certToCheckSubjectDn, revocStatus == null ? "Unknown" : revocStatus);
      }

      this.context.logCertRevocStatus(revocStatus);
   }

   private CertRevocStatus runThruMethods(X509Certificate x509Cert, X500Principal certToCheckIssuerDn) {
      CertRevocCheckMethodList methodList = this.context.getMethodOrder(certToCheckIssuerDn);
      Iterator iter = methodList.iterator();
      CertRevocStatus revocStatus = null;

      while(null == revocStatus && iter.hasNext()) {
         CertRevocCheckMethodList.SelectableMethod method = (CertRevocCheckMethodList.SelectableMethod)iter.next();
         if (this.context.isLoggable(Level.FINEST)) {
            this.context.log(Level.FINEST, "Trying revocation check using method {0}.", method);
         }

         if (null == method) {
            if (this.context.isLoggable(Level.FINER)) {
               this.context.log(Level.FINER, "Skipping null revocation check method.");
            }
         } else {
            switch (method) {
               case OCSP:
                  revocStatus = this.ocspChecker.getCertRevocStatus(this.issuerX509Cert, x509Cert);
                  break;
               case CRL:
                  revocStatus = this.crlChecker.getCertRevocStatus(this.issuerX509Cert, x509Cert);
                  break;
               default:
                  if (this.context.isLoggable(Level.FINE)) {
                     this.context.log(Level.FINE, "Skipping unknown SelectableMethod: {0}", method);
                  }
            }
         }
      }

      return revocStatus;
   }

   private void startAllMaintenanceTasks() {
      CrlCacheAccessor accessor = this.crlChecker.getCrlCacheAccessor();
      if (null != accessor) {
         CrlCacheUpdater.startAllMaintenanceTasks(accessor, this.context);
      }
   }

   private boolean isCheckingDisabled(X500Principal issuerDn) {
      if (this.context.isCheckingDisabled(issuerDn)) {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, "Revocation status checking is disabled for issuer \"{0}\".", issuerDn);
         }

         return true;
      } else {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, "Revocation status checking is enabled for issuer \"{0}\".", issuerDn);
         }

         return false;
      }
   }

   private boolean isSubjectDnMissing(boolean isFailOnUnknownRevocStatus, X500Principal subjectDn) throws CertPathValidatorException {
      if (null != subjectDn && null != subjectDn.getName() && subjectDn.getName().length() != 0) {
         return false;
      } else if (isFailOnUnknownRevocStatus) {
         throw new CertPathValidatorException("Unknown Revocation Status: Certificate to check has no subject.");
      } else {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, "Skipping revocation status checking since certificate to check has no subject.");
         }

         return true;
      }
   }

   private boolean isIssuerDnMissing(X500Principal issuerDn) {
      if (null != issuerDn && null != issuerDn.getName() && issuerDn.getName().length() != 0) {
         return false;
      } else {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, "Unable to check revocation status, missing issuer DN.");
         }

         return true;
      }
   }

   private boolean isCertToCheckX509(Certificate cert) {
      if (cert instanceof X509Certificate) {
         return true;
      } else {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, "Unable to check revocation of certificate of type {0}.", null == cert ? null : cert.getClass().getName());
         }

         return false;
      }
   }

   private boolean isCertToCheckNull(Certificate cert) {
      if (null == cert) {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, "Given null certificate, no revocation checking is needed.");
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isEnabled() {
      boolean enabled = this.context.isCheckingEnabled();
      if (this.context.isLoggable(Level.FINE)) {
         this.context.log(Level.FINE, "Certificate revocation checking is {0}.", enabled ? "enabled" : "disabled");
      }

      return enabled;
   }

   private boolean isFailOnUnknownRevocStatus(X500Principal certToCheckIssuerDn) {
      boolean isFailOnUnknownRevocStatus = this.context.isFailOnUnknownRevocStatus(certToCheckIssuerDn);
      if (this.context.isLoggable(Level.FINE)) {
         this.context.log(Level.FINE, "Certificate validation will {0} if revocation status is indeterminable.", isFailOnUnknownRevocStatus ? "FAIL" : "not be affected");
      }

      return isFailOnUnknownRevocStatus;
   }

   private boolean ensureIssuerCert(X500Principal certToCheckIssuerDn, boolean failOnUnknownRevocStatus) throws CertPathValidatorException {
      if (null == this.issuerX509Cert) {
         this.issuerX509Cert = this.context.getValidTrustedCert(certToCheckIssuerDn);
         if (null == this.issuerX509Cert) {
            if (failOnUnknownRevocStatus) {
               throw new CertPathValidatorException("Unknown Revocation Status: Could not find trusted issuer certificate with subject \"" + certToCheckIssuerDn + "\".");
            }

            if (this.context.isLoggable(Level.FINE)) {
               this.context.log(Level.FINE, "Skipping revocation status checking since cannot find trusted issuer certificate with subject \"{0}\".", certToCheckIssuerDn);
            }

            return false;
         }
      }

      return true;
   }

   private boolean isExpectedIssuer(X500Principal certToCheckIssuerDn, boolean failOnUnknownRevocStatus) throws CertPathValidatorException {
      X500Principal expectedIssuerDn = this.issuerX509Cert.getSubjectX500Principal();
      if (!certToCheckIssuerDn.equals(expectedIssuerDn)) {
         if (failOnUnknownRevocStatus) {
            throw new CertPathValidatorException("Unexpected issuer for certificate to check, expected issuer=\"" + expectedIssuerDn + "\".");
         } else {
            if (this.context.isLoggable(Level.FINE)) {
               this.context.log(Level.FINE, "Unexpected issuer for certificate to check, expected issuer=\n{0}.", expectedIssuerDn);
            }

            return false;
         }
      } else {
         return true;
      }
   }
}
