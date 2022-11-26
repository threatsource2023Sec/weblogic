package weblogic.security.pki.revocation.common;

import java.security.cert.X509Certificate;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

abstract class OcspChecker extends AbstractRevocChecker {
   private static final CertRevocStatusCache ocspStatusCache = CertRevocStatusCache.getInstance();

   public static OcspChecker getInstance(AbstractCertRevocContext context) {
      return new DefaultOcspChecker(context);
   }

   OcspChecker(AbstractCertRevocContext context) {
      super(context);
   }

   final CertRevocStatus getCertRevocStatus(X509Certificate issuerCert, X509Certificate certToCheck) {
      Util.checkNotNull("Issuer X509Certificate.", issuerCert);
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      X500Principal certToCheckIssuerDn = certToCheck.getIssuerX500Principal();
      boolean cacheEnabled = context.isOcspResponseCacheEnabled(certToCheckIssuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponseCacheEnabled={0}", cacheEnabled);
      }

      boolean nonceEnabled = context.isOcspNonceEnabled(certToCheckIssuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspNonceEnabled={0}", nonceEnabled);
      }

      CertRevocStatus status = null;
      if (cacheEnabled) {
         if (nonceEnabled) {
            this.updateCachedStatus(certToCheck, (CertRevocStatus)null);
         } else {
            status = this.getCachedStatus(certToCheck);
            if (null != status) {
               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "Revocation status found in OCSP cache.");
               }

               return status;
            }

            if (context.isLoggable(Level.FINEST)) {
               context.log(Level.FINEST, "Revocation status not found in OCSP cache.");
            }
         }
      }

      status = this.getRemoteStatus(issuerCert, certToCheck);
      if (null != status && nonceEnabled) {
         status = this.checkRequiredNonce(status);
      }

      if (cacheEnabled && !nonceEnabled) {
         this.updateCachedStatus(certToCheck, status);
      }

      return status;
   }

   private CertRevocStatus getCachedStatus(X509Certificate certToCheck) {
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      X500Principal issuerDn = certToCheck.getIssuerX500Principal();
      int timeTolerance = context.getOcspTimeTolerance(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspTimeTolerance={0}", timeTolerance);
      }

      int refreshPeriodPercent = context.getOcspResponseCacheRefreshPeriodPercent();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponseCacheRefreshPeriodPercent={0}", refreshPeriodPercent);
      }

      return ocspStatusCache.getStatus(certToCheck, timeTolerance, refreshPeriodPercent, context.getLogListener());
   }

   private void updateCachedStatus(X509Certificate certToCheck, CertRevocStatus status) {
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      X500Principal issuerDn = certToCheck.getIssuerX500Principal();
      int timeTolerance = context.getOcspTimeTolerance(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspTimeTolerance={0}", timeTolerance);
      }

      int refreshPeriodPercent = context.getOcspResponseCacheRefreshPeriodPercent();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponseCacheRefreshPeriodPercent={0}", refreshPeriodPercent);
      }

      int capacity = context.getOcspResponseCacheCapacity();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponseCacheCapacity={0}", capacity);
      }

      ocspStatusCache.putStatus(certToCheck, status, timeTolerance, refreshPeriodPercent, capacity, context.getLogListener());
   }

   private CertRevocStatus checkRequiredNonce(CertRevocStatus status) {
      Util.checkNotNull("CertRevocStatus", status);
      if (!status.isNonceIgnored()) {
         return status;
      } else {
         AbstractCertRevocContext context = this.getContext();
         if (context.isLoggable(Level.FINE)) {
            context.log(Level.FINE, "OCSP responder ignored nonce, so response was ignored, which was:\n{0}", status);
         }

         context.logIgnoredNonceCertRevocStatus(status);
         return null;
      }
   }

   abstract CertRevocStatus getRemoteStatus(X509Certificate var1, X509Certificate var2);
}
