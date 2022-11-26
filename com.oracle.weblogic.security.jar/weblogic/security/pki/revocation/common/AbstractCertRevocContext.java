package weblogic.security.pki.revocation.common;

import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.security.PrivateKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

public abstract class AbstractCertRevocContext {
   public static final Boolean DEFAULT_CHECKING_ENABLED;
   public static final Boolean DEFAULT_CHECKING_DISABLED;
   public static final Boolean DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS;
   public static final CertRevocCheckMethodList.SelectableMethodList DEFAULT_SELECTABLE_METHOD_LIST;
   public static final CertRevocCheckMethodList DEFAULT_METHOD_ORDER;
   public static final URI DEFAULT_OCSP_RESPONDER_URL;
   public static final String DEFAULT_OCSP_RESPONDER_URL_STRING;
   public static final AbstractCertRevocConstants.AttributeUsage DEFAULT_OCSP_RESPONDER_URL_USAGE;
   public static final X509Certificate DEFAULT_OCSP_RESPONDER_TRUSTED_CERT;
   public static final Boolean DEFAULT_OCSP_NONCE_ENABLED;
   public static final PrivateKey DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY;
   public static final String DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY_ALIAS;
   public static final X509Certificate DEFAULT_OCSP_REQUEST_SIGNING_CERT;
   public static final Boolean DEFAULT_OCSP_RESPONSE_CACHE_ENABLED;
   public static final Integer DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY;
   public static final Integer DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT;
   public static final Long DEFAULT_OCSP_RESPONSE_TIMEOUT;
   public static final long MIN_OCSP_RESPONSE_TIMEOUT = 1L;
   public static final long MAX_OCSP_RESPONSE_TIMEOUT = 300L;
   public static final Integer DEFAULT_OCSP_TIME_TOLERANCE;
   public static final int MIN_OCSP_TIME_TOLERANCE = 0;
   public static final int MAX_OCSP_TIME_TOLERANCE = 900;
   public static final AbstractCertRevocConstants.CrlCacheType DEFAULT_CRL_CACHE_TYPE;
   public static final File DEFAULT_CRL_CACHE_IMPORT_DIR;
   public static final File DEFAULT_CRL_CACHE_TYPE_FILE_DIR;
   public static final String DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME;
   public static final Integer DEFAULT_CRL_CACHE_TYPE_LDAP_PORT;
   public static final Integer DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT;
   public static final int MIN_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT = 1;
   public static final int MAX_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT = 300;
   public static final Integer DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT;
   public static final Boolean DEFAULT_CRL_DP_ENABLED;
   public static final Long DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT;
   public static final long MIN_CRL_DP_DOWNLOAD_TIMEOUT = 1L;
   public static final long MAX_CRL_DP_DOWNLOAD_TIMEOUT = 300L;
   public static final Boolean DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED;
   public static final URI DEFAULT_CRL_DP_URL;
   public static final String DEFAULT_CRL_DP_URL_STRING;
   public static final AbstractCertRevocConstants.AttributeUsage DEFAULT_CRL_DP_URL_USAGE;
   private static final String REVOCATION_SUB_DIRECTORY_NAME = "certrevocation";
   private static final String REVOCATION_CRLCACHE_SUB_DIRECTORY_NAME = "crlcache";
   private static final String REVOCATION_CRLCACHE_STORAGE_SUB_DIRECTORY_NAME = "storage";
   private static final String REVOCATION_CRLCACHE_IMPORT_SUB_DIRECTORY_NAME = "import";
   private final LogListener logListener;
   private final Set trustedCerts;

   protected AbstractCertRevocContext(Set trustedCerts, LogListener logListener) {
      try {
         Util.checkNotNull("trustedCerts", trustedCerts);
         if (trustedCerts.isEmpty()) {
            throw new IllegalArgumentException("Expected populated set of trusted certificates.");
         } else {
            this.trustedCerts = Collections.unmodifiableSet(new HashSet(trustedCerts));
            if (null != logListener) {
               this.logListener = logListener;
            } else {
               this.logListener = DefaultLogListener.getInstance();
            }

         }
      } catch (RuntimeException var4) {
         if (logListener.isLoggable(Level.FINE)) {
            logListener.log(Level.FINE, var4, "AbstractCertRevocContext initialization");
         }

         throw var4;
      }
   }

   public final LogListener getLogListener() {
      return this.logListener;
   }

   public final boolean isLoggable(Level level) {
      return this.getLogListener().isLoggable(level);
   }

   public final void log(Level level, String msg, Object... params) {
      this.getLogListener().log(level, msg, params);
   }

   public final void log(Level level, Throwable throwable, String msg, Object... params) {
      this.getLogListener().log(level, throwable, msg, params);
   }

   public abstract void logAttemptingCertRevocCheck(X500Principal var1);

   public abstract void logUnknownCertRevocStatusNoFail(X500Principal var1);

   public abstract void logCertRevocStatus(CertRevocStatus var1);

   public abstract void logIgnoredNonceCertRevocStatus(CertRevocStatus var1);

   public abstract void logUnknownCertRevocStatusFail(X500Principal var1);

   public abstract void logRevokedCertRevocStatusFail(X500Principal var1);

   public abstract void logNotRevokedCertRevocStatusNotFail(X500Principal var1);

   public final Set getTrustedCerts() {
      return this.trustedCerts;
   }

   public final X509Certificate getValidTrustedCert(X500Principal subject) {
      Util.checkNotNull("subject", subject);
      Iterator var2 = this.getTrustedCerts().iterator();

      X509Certificate trustedCert;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         trustedCert = (X509Certificate)var2.next();
      } while(null == trustedCert || !subject.equals(trustedCert.getSubjectX500Principal()) || !isWithinValidityPeriod(trustedCert));

      return trustedCert;
   }

   public final X509Certificate getValidTrustedCert(X500Principal issuer, BigInteger serialNumber) {
      Util.checkNotNull("issuer", issuer);
      Util.checkNotNull("serialNumber", serialNumber);
      Iterator var3 = this.getTrustedCerts().iterator();

      X509Certificate trustedCert;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         trustedCert = (X509Certificate)var3.next();
      } while(null == trustedCert || !issuer.equals(trustedCert.getIssuerX500Principal()) || !serialNumber.equals(trustedCert.getSerialNumber()) || !isWithinValidityPeriod(trustedCert));

      return trustedCert;
   }

   public abstract void schedule(Runnable var1);

   public abstract Timer scheduleWithFixedDelay(Runnable var1, long var2, long var4);

   public abstract boolean isCheckingEnabled();

   public abstract boolean isCheckingDisabled(X500Principal var1);

   public abstract boolean isFailOnUnknownRevocStatus(X500Principal var1);

   public abstract CertRevocCheckMethodList getMethodOrder(X500Principal var1);

   public abstract URI getOcspResponderUrl(X500Principal var1);

   public abstract AbstractCertRevocConstants.AttributeUsage getOcspResponderUrlUsage(X500Principal var1);

   public abstract X509Certificate getOcspResponderTrustedCert(X500Principal var1);

   public abstract boolean isOcspNonceEnabled(X500Principal var1);

   public abstract PrivateKey getOcspRequestSigningPrivateKey(X500Principal var1);

   public abstract X509Certificate getOcspRequestSigningCert(X500Principal var1);

   public abstract boolean isOcspResponseCacheEnabled(X500Principal var1);

   public abstract int getOcspResponseCacheCapacity();

   public abstract int getOcspResponseCacheRefreshPeriodPercent();

   public abstract long getOcspResponseTimeout(X500Principal var1);

   public abstract int getOcspTimeTolerance(X500Principal var1);

   public abstract AbstractCertRevocConstants.CrlCacheType getCrlCacheType();

   public abstract File getCrlCacheImportDir();

   public abstract File getCrlCacheTypeFileDir();

   public abstract String getCrlCacheTypeLdapHostname();

   public abstract int getCrlCacheTypeLdapPort();

   public abstract int getCrlCacheTypeLdapSearchTimeout();

   public abstract int getCrlCacheRefreshPeriodPercent();

   public abstract boolean isCrlDpEnabled(X500Principal var1);

   public abstract long getCrlDpDownloadTimeout(X500Principal var1);

   public abstract boolean isCrlDpBackgroundDownloadEnabled(X500Principal var1);

   public abstract URI getCrlDpUrl(X500Principal var1);

   public abstract AbstractCertRevocConstants.AttributeUsage getCrlDpUrlUsage(X500Principal var1);

   private static boolean isWithinValidityPeriod(X509Certificate cert) {
      Util.checkNotNull("X509Certificate", cert);

      try {
         cert.checkValidity();
         return true;
      } catch (CertificateNotYetValidException var2) {
      } catch (CertificateExpiredException var3) {
      }

      return false;
   }

   private static void checkCrlCacheBaseDirectory(File baseDir) {
      if (null == baseDir) {
         throw new IllegalArgumentException("Unexpected null CRL cache base directory.");
      } else if (!baseDir.exists()) {
         throw new IllegalArgumentException("CRL cache base directory does not exist on the given path \"" + baseDir.getAbsolutePath() + "\".");
      } else if (!baseDir.isDirectory()) {
         throw new IllegalArgumentException("CRL cache base directory path is not pointing to a directory: \"" + baseDir.getAbsolutePath() + "\".");
      }
   }

   protected static File getCrlCacheStorageDirectory(File baseDir) {
      checkCrlCacheBaseDirectory(baseDir);
      StringBuilder relPathBuilder = new StringBuilder(256);
      relPathBuilder.append("certrevocation");
      relPathBuilder.append(File.separator);
      relPathBuilder.append("crlcache");
      relPathBuilder.append(File.separator);
      relPathBuilder.append("storage");
      String relPath = relPathBuilder.toString();
      File storageDir = new File(baseDir, relPath);
      return storageDir;
   }

   protected static File getCrlCacheImportDirectory(File baseDir) {
      checkCrlCacheBaseDirectory(baseDir);
      StringBuilder relPathBuilder = new StringBuilder(256);
      relPathBuilder.append("certrevocation");
      relPathBuilder.append(File.separator);
      relPathBuilder.append("crlcache");
      relPathBuilder.append(File.separator);
      relPathBuilder.append("import");
      String relPath = relPathBuilder.toString();
      File importDir = new File(baseDir, relPath);
      return importDir;
   }

   static {
      DEFAULT_CHECKING_ENABLED = AbstractCertRevocConstants.DEFAULT_CHECKING_ENABLED;
      DEFAULT_CHECKING_DISABLED = AbstractCertRevocConstants.DEFAULT_CHECKING_DISABLED;
      DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS = AbstractCertRevocConstants.DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS;
      DEFAULT_SELECTABLE_METHOD_LIST = AbstractCertRevocConstants.DEFAULT_SELECTABLE_METHOD_LIST;
      DEFAULT_METHOD_ORDER = AbstractCertRevocConstants.DEFAULT_METHOD_ORDER;
      DEFAULT_OCSP_RESPONDER_URL = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_URL;
      DEFAULT_OCSP_RESPONDER_URL_STRING = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_URL_STRING;
      DEFAULT_OCSP_RESPONDER_URL_USAGE = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_URL_USAGE;
      DEFAULT_OCSP_RESPONDER_TRUSTED_CERT = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_TRUSTED_CERT;
      DEFAULT_OCSP_NONCE_ENABLED = AbstractCertRevocConstants.DEFAULT_OCSP_NONCE_ENABLED;
      DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY = AbstractCertRevocConstants.DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY;
      DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY_ALIAS = AbstractCertRevocConstants.DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY_ALIAS;
      DEFAULT_OCSP_REQUEST_SIGNING_CERT = AbstractCertRevocConstants.DEFAULT_OCSP_REQUEST_SIGNING_CERT;
      DEFAULT_OCSP_RESPONSE_CACHE_ENABLED = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONSE_CACHE_ENABLED;
      DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY;
      DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT;
      DEFAULT_OCSP_RESPONSE_TIMEOUT = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONSE_TIMEOUT;
      DEFAULT_OCSP_TIME_TOLERANCE = AbstractCertRevocConstants.DEFAULT_OCSP_TIME_TOLERANCE;
      DEFAULT_CRL_CACHE_TYPE = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_TYPE;
      DEFAULT_CRL_CACHE_IMPORT_DIR = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_IMPORT_DIR;
      DEFAULT_CRL_CACHE_TYPE_FILE_DIR = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_TYPE_FILE_DIR;
      DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME;
      DEFAULT_CRL_CACHE_TYPE_LDAP_PORT = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_TYPE_LDAP_PORT;
      DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT;
      DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT = AbstractCertRevocConstants.DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT;
      DEFAULT_CRL_DP_ENABLED = AbstractCertRevocConstants.DEFAULT_CRL_DP_ENABLED;
      DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT = AbstractCertRevocConstants.DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT;
      DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED = AbstractCertRevocConstants.DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED;
      DEFAULT_CRL_DP_URL = AbstractCertRevocConstants.DEFAULT_CRL_DP_URL;
      DEFAULT_CRL_DP_URL_STRING = AbstractCertRevocConstants.DEFAULT_CRL_DP_URL_STRING;
      DEFAULT_CRL_DP_URL_USAGE = AbstractCertRevocConstants.DEFAULT_CRL_DP_URL_USAGE;
   }
}
