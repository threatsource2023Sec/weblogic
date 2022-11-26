package weblogic.security.pki.revocation.common;

import java.security.cert.X509Certificate;
import java.util.logging.Level;

abstract class CrlChecker extends AbstractRevocChecker {
   private static final boolean crlMemCacheEnabled = false;
   private static final int CRL_MEM_CACHE_MAX_SIZE = 1024;
   private static final CertRevocStatusCache crlMemCache = CertRevocStatusCache.getInstance();

   public static CrlChecker getInstance(AbstractCertRevocContext context) {
      return new DefaultCrlChecker(context);
   }

   CrlChecker(AbstractCertRevocContext context) {
      super(context);
   }

   final CertRevocStatus getCertRevocStatus(X509Certificate issuerCert, X509Certificate certToCheck) {
      Util.checkNotNull("Issuer X509Certificate.", issuerCert);
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlMemCacheEnabled={0}", false);
      }

      CertRevocStatus status = this.getCrlStatus(issuerCert, certToCheck);
      return status;
   }

   private CertRevocStatus getCachedStatus(X509Certificate certToCheck) {
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      int refreshPeriodPercent = context.getCrlCacheRefreshPeriodPercent();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlCacheRefreshPeriodPercent={0}", refreshPeriodPercent);
      }

      int timeTolerance = false;
      return crlMemCache.getStatus(certToCheck, 0, refreshPeriodPercent, context.getLogListener());
   }

   private void updateCachedStatus(X509Certificate certToCheck, CertRevocStatus status) {
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      int refreshPeriodPercent = context.getCrlCacheRefreshPeriodPercent();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlCacheRefreshPeriodPercent={0}", refreshPeriodPercent);
      }

      int timeTolerance = false;
      crlMemCache.putStatus(certToCheck, status, 0, refreshPeriodPercent, 1024, context.getLogListener());
   }

   abstract CertRevocStatus getCrlStatus(X509Certificate var1, X509Certificate var2);

   abstract CrlCacheAccessor getCrlCacheAccessor();
}
