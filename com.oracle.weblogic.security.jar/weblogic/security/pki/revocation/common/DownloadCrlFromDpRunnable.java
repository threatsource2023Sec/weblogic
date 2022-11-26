package weblogic.security.pki.revocation.common;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

final class DownloadCrlFromDpRunnable implements Runnable {
   private volatile boolean running = true;
   private volatile boolean crlCacheUpdated = false;
   private final CrlCacheAccessor crlCacheAccessor;
   private final Object waitingObject;
   private final X509Certificate certWithDp;
   private final LogListener logger;
   private final URI alternateUri;
   private final AbstractCertRevocConstants.AttributeUsage alternateUriUsage;
   private final long dpDownloadTimeout;

   DownloadCrlFromDpRunnable(CrlCacheAccessor crlCacheAccessor, Object waitingObject, X509Certificate certWithDp, LogListener logger, URI alternateUri, AbstractCertRevocConstants.AttributeUsage alternateUriUsage, long dpDownloadTimeout) {
      Util.checkNotNull("crlCacheAccessor", crlCacheAccessor);
      Util.checkNotNull("certWithDp", certWithDp);
      Util.checkNotNull("waitingObject", waitingObject);
      this.crlCacheAccessor = crlCacheAccessor;
      this.waitingObject = waitingObject;
      this.certWithDp = certWithDp;
      this.logger = logger;
      this.alternateUri = alternateUri;
      this.alternateUriUsage = alternateUriUsage;
      this.dpDownloadTimeout = dpDownloadTimeout;
   }

   public boolean isRunning() {
      return this.running;
   }

   public boolean isCrlCacheUpdated() {
      return this.crlCacheUpdated;
   }

   public void run() {
      synchronized(this.waitingObject) {
         X500Principal certSubject = this.certWithDp.getSubjectX500Principal();
         String certSubjectName = null == certSubject ? "" : certSubject.getName();

         try {
            long readTimeout = this.dpDownloadTimeout;
            long connectionTimeout = this.dpDownloadTimeout;
            CrlDpFetcher fetcher = CrlDpFetcher.getInstance();
            this.crlCacheUpdated = fetcher.updateCrls(this.certWithDp, this.crlCacheAccessor, this.alternateUri, this.alternateUriUsage, readTimeout, connectionTimeout, this.logger);
         } catch (Exception var14) {
            if (null != this.logger && this.logger.isLoggable(Level.FINE)) {
               this.logger.log(Level.FINE, var14, "Trying to download CRLs from Distribution Point for cert subject \"{0}\".", certSubjectName);
            }
         } finally {
            this.running = false;
            if (null != this.waitingObject) {
               this.waitingObject.notifyAll();
            }

         }

      }
   }
}
