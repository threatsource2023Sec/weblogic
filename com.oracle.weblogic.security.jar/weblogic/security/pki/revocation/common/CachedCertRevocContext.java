package weblogic.security.pki.revocation.common;

import java.io.File;
import java.net.URI;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

public final class CachedCertRevocContext extends AbstractCertRevocContext {
   private final Attribute checkingEnabled;
   private final CaAttribute checkingDisabled;
   private final CaAttribute failOnUnknownRevocStatus;
   private final CaAttribute methodOrder;
   private final CaAttribute ocspResponderUrl;
   private final CaAttribute ocspResponderUrlUsage;
   private final CaAttribute ocspResponderTrustedCert;
   private final CaAttribute ocspNonceEnabled;
   private final CaAttribute ocspRequestSigningPrivateKey;
   private final CaAttribute ocspRequestSigningCert;
   private final CaAttribute ocspResponseCacheEnabled;
   private final Attribute ocspResponseCacheCapacity;
   private final Attribute ocspResponseCacheRefreshPeriodPercent;
   private final CaAttribute ocspResponseTimeout;
   private final CaAttribute ocspTimeTolerance;
   private final Attribute crlCacheType;
   private final Attribute crlCacheImportDir;
   private final Attribute crlCacheTypeFileDir;
   private final Attribute crlCacheTypeLdapHostname;
   private final Attribute crlCacheTypeLdapPort;
   private final Attribute crlCacheTypeLdapSearchTimeout;
   private final Attribute crlCacheRefreshPeriodPercent;
   private final CaAttribute crlDpEnabled;
   private final CaAttribute crlDpDownloadTimeout;
   private final CaAttribute crlDpBackgroundDownloadEnabled;
   private final CaAttribute crlDpUrl;
   private final CaAttribute crlDpUrlUsage;
   private final ExecutorService executorService;
   private final java.util.Timer timer;

   public CachedCertRevocContext(Set trustedCerts, ExecutorService executorService) {
      super(trustedCerts, (LogListener)null);
      this.checkingEnabled = new Attribute(DEFAULT_CHECKING_ENABLED, (Long)null, (Long)null);
      this.checkingDisabled = new CaAttribute(DEFAULT_CHECKING_DISABLED, (Long)null, (Long)null);
      this.failOnUnknownRevocStatus = new CaAttribute(DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS, (Long)null, (Long)null);
      this.methodOrder = new CaAttribute(DEFAULT_METHOD_ORDER, (Long)null, (Long)null);
      this.ocspResponderUrl = new CaAttribute(DEFAULT_OCSP_RESPONDER_URL, (Long)null, (Long)null);
      this.ocspResponderUrlUsage = new CaAttribute(DEFAULT_OCSP_RESPONDER_URL_USAGE, (Long)null, (Long)null);
      this.ocspResponderTrustedCert = new CaAttribute(DEFAULT_OCSP_RESPONDER_TRUSTED_CERT, (Long)null, (Long)null);
      this.ocspNonceEnabled = new CaAttribute(DEFAULT_OCSP_NONCE_ENABLED, (Long)null, (Long)null);
      this.ocspRequestSigningPrivateKey = new CaAttribute(DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY, (Long)null, (Long)null);
      this.ocspRequestSigningCert = new CaAttribute(DEFAULT_OCSP_REQUEST_SIGNING_CERT, (Long)null, (Long)null);
      this.ocspResponseCacheEnabled = new CaAttribute(DEFAULT_OCSP_RESPONSE_CACHE_ENABLED, (Long)null, (Long)null);
      this.ocspResponseCacheCapacity = new Attribute(DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY, 1L, (Long)null);
      this.ocspResponseCacheRefreshPeriodPercent = new Attribute(DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT, 1L, 100L);
      this.ocspResponseTimeout = new CaAttribute(DEFAULT_OCSP_RESPONSE_TIMEOUT, 1L, 300L);
      this.ocspTimeTolerance = new CaAttribute(DEFAULT_OCSP_TIME_TOLERANCE, 0L, 900L);
      this.crlCacheType = new Attribute(DEFAULT_CRL_CACHE_TYPE, (Long)null, (Long)null);
      this.crlCacheImportDir = new Attribute(DEFAULT_CRL_CACHE_IMPORT_DIR, (Long)null, (Long)null);
      this.crlCacheTypeFileDir = new Attribute(DEFAULT_CRL_CACHE_TYPE_FILE_DIR, (Long)null, (Long)null);
      this.crlCacheTypeLdapHostname = new Attribute(DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME, (Long)null, (Long)null);
      this.crlCacheTypeLdapPort = new Attribute(DEFAULT_CRL_CACHE_TYPE_LDAP_PORT, -1L, 65535L);
      this.crlCacheTypeLdapSearchTimeout = new Attribute(DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT, 1L, 300L);
      this.crlCacheRefreshPeriodPercent = new Attribute(DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT, 1L, 100L);
      this.crlDpEnabled = new CaAttribute(DEFAULT_CRL_DP_ENABLED, (Long)null, (Long)null);
      this.crlDpDownloadTimeout = new CaAttribute(DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT, 1L, 300L);
      this.crlDpBackgroundDownloadEnabled = new CaAttribute(DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED, (Long)null, (Long)null);
      this.crlDpUrl = new CaAttribute(DEFAULT_CRL_DP_URL, (Long)null, (Long)null);
      this.crlDpUrlUsage = new CaAttribute(DEFAULT_CRL_DP_URL_USAGE, (Long)null, (Long)null);
      this.executorService = executorService;
      boolean asDaemon = true;
      this.timer = new java.util.Timer(true);
   }

   public void logAttemptingCertRevocCheck(X500Principal certToCheckSubjectDn) {
   }

   public void logUnknownCertRevocStatusNoFail(X500Principal certToCheckSubjectDn) {
   }

   public void logCertRevocStatus(CertRevocStatus status) {
   }

   public void logIgnoredNonceCertRevocStatus(CertRevocStatus status) {
   }

   public void logUnknownCertRevocStatusFail(X500Principal certToCheckSubjectDn) {
   }

   public void logRevokedCertRevocStatusFail(X500Principal certToCheckSubjectDn) {
   }

   public void logNotRevokedCertRevocStatusNotFail(X500Principal certToCheckSubjectDn) {
   }

   public ExecutorService getExecutorService() {
      return this.executorService;
   }

   public void schedule(Runnable task) {
      Util.checkNotNull("Runnable", task);
      if (null == this.executorService) {
         task.run();
      } else {
         this.executorService.execute(task);
      }

   }

   public Timer scheduleWithFixedDelay(final Runnable task, long delay, long period) {
      Util.checkNotNull("Runnable", task);
      Util.checkRange("delay", delay, 0L, (Long)null);
      Util.checkRange("period", period, 0L, (Long)null);
      final TimerTask timerTask = new TimerTask() {
         public void run() {
            try {
               task.run();
            } catch (Exception var2) {
               if (CachedCertRevocContext.this.isLoggable(Level.FINE)) {
                  CachedCertRevocContext.this.log(Level.FINE, var2, "Exception occurred running timer task {0}.", new Object[]{task.getClass().getName()});
               }
            }

         }
      };
      this.timer.schedule(timerTask, delay, period);
      Timer result = new Timer() {
         public void cancel() {
            if (CachedCertRevocContext.this.isLoggable(Level.FINEST)) {
               CachedCertRevocContext.this.log(Level.FINEST, "Cancelling timer task {0}.", new Object[]{task.getClass().getName()});
            }

            boolean foundNCancelled = timerTask.cancel();
            if (CachedCertRevocContext.this.isLoggable(Level.FINEST)) {
               CachedCertRevocContext.this.log(Level.FINEST, "Returned from cancel for timer task {0}, Found/cancelled={1}.", new Object[]{task.getClass().getName(), foundNCancelled});
            }

         }
      };
      return result;
   }

   public boolean isCheckingEnabled() {
      return (Boolean)this.checkingEnabled.getValue();
   }

   public Attribute getAttribute_CheckingEnabled() {
      return this.checkingEnabled;
   }

   public boolean isCheckingDisabled(X500Principal caDn) {
      return (Boolean)this.checkingDisabled.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_CheckingDisabled() {
      return this.checkingDisabled;
   }

   public boolean isFailOnUnknownRevocStatus(X500Principal caDn) {
      return (Boolean)this.failOnUnknownRevocStatus.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_FailOnUnknownRevocStatus() {
      return this.failOnUnknownRevocStatus;
   }

   public CertRevocCheckMethodList getMethodOrder(X500Principal caDn) {
      return (CertRevocCheckMethodList)this.methodOrder.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_MethodOrder() {
      return this.methodOrder;
   }

   public URI getOcspResponderUrl(X500Principal caDn) {
      return (URI)this.ocspResponderUrl.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspResponderUrl() {
      return this.ocspResponderUrl;
   }

   public AbstractCertRevocConstants.AttributeUsage getOcspResponderUrlUsage(X500Principal caDn) {
      return (AbstractCertRevocConstants.AttributeUsage)this.ocspResponderUrlUsage.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspResponderUrlUsage() {
      return this.ocspResponderUrlUsage;
   }

   public X509Certificate getOcspResponderTrustedCert(X500Principal caDn) {
      return (X509Certificate)this.ocspResponderTrustedCert.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspResponderTrustedCert() {
      return this.ocspResponderTrustedCert;
   }

   public boolean isOcspNonceEnabled(X500Principal caDn) {
      return (Boolean)this.ocspNonceEnabled.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspNonceEnabled() {
      return this.ocspNonceEnabled;
   }

   public PrivateKey getOcspRequestSigningPrivateKey(X500Principal caDn) {
      return (PrivateKey)this.ocspRequestSigningPrivateKey.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspRequestSigningPrivateKey() {
      return this.ocspRequestSigningPrivateKey;
   }

   public X509Certificate getOcspRequestSigningCert(X500Principal caDn) {
      return (X509Certificate)this.ocspRequestSigningCert.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspRequestSigningCert() {
      return this.ocspRequestSigningCert;
   }

   public boolean isOcspResponseCacheEnabled(X500Principal caDn) {
      return (Boolean)this.ocspResponseCacheEnabled.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspResponseCacheEnabled() {
      return this.ocspResponseCacheEnabled;
   }

   public int getOcspResponseCacheCapacity() {
      return (Integer)this.ocspResponseCacheCapacity.getValue();
   }

   public Attribute getAttribute_OcspResponseCacheCapacity() {
      return this.ocspResponseCacheCapacity;
   }

   public int getOcspResponseCacheRefreshPeriodPercent() {
      return (Integer)this.ocspResponseCacheRefreshPeriodPercent.getValue();
   }

   public Attribute getAttribute_OcspResponseCacheRefreshPeriodPercent() {
      return this.ocspResponseCacheRefreshPeriodPercent;
   }

   public long getOcspResponseTimeout(X500Principal caDn) {
      return (Long)this.ocspResponseTimeout.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspResponseTimeout() {
      return this.ocspResponseTimeout;
   }

   public int getOcspTimeTolerance(X500Principal caDn) {
      return (Integer)this.ocspTimeTolerance.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_OcspTimeTolerance() {
      return this.ocspTimeTolerance;
   }

   public AbstractCertRevocConstants.CrlCacheType getCrlCacheType() {
      return (AbstractCertRevocConstants.CrlCacheType)this.crlCacheType.getValue();
   }

   public Attribute getAttribute_CrlCacheType() {
      return this.crlCacheType;
   }

   public File getCrlCacheImportDir() {
      return (File)this.crlCacheImportDir.getValue();
   }

   public Attribute getAttribute_CrlCacheImportDir() {
      return this.crlCacheImportDir;
   }

   public File getCrlCacheTypeFileDir() {
      return (File)this.crlCacheTypeFileDir.getValue();
   }

   public Attribute getAttribute_CrlCacheTypeFileDir() {
      return this.crlCacheTypeFileDir;
   }

   public String getCrlCacheTypeLdapHostname() {
      return (String)this.crlCacheTypeLdapHostname.getValue();
   }

   public Attribute getAttribute_CrlCacheTypeLdapHostname() {
      return this.crlCacheTypeLdapHostname;
   }

   public int getCrlCacheTypeLdapPort() {
      return (Integer)this.crlCacheTypeLdapPort.getValue();
   }

   public Attribute getAttribute_CrlCacheTypeLdapPort() {
      return this.crlCacheTypeLdapPort;
   }

   public int getCrlCacheTypeLdapSearchTimeout() {
      return (Integer)this.crlCacheTypeLdapSearchTimeout.getValue();
   }

   public Attribute getAttribute_CrlCacheTypeLdapSearchTimeout() {
      return this.crlCacheTypeLdapSearchTimeout;
   }

   public int getCrlCacheRefreshPeriodPercent() {
      return (Integer)this.crlCacheRefreshPeriodPercent.getValue();
   }

   public Attribute getAttribute_CrlCacheRefreshPeriodPercent() {
      return this.crlCacheRefreshPeriodPercent;
   }

   public boolean isCrlDpEnabled(X500Principal caDn) {
      return (Boolean)this.crlDpEnabled.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_CrlDpEnabled() {
      return this.crlDpEnabled;
   }

   public long getCrlDpDownloadTimeout(X500Principal caDn) {
      return (Long)this.crlDpDownloadTimeout.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_CrlDpDownloadTimeout() {
      return this.crlDpDownloadTimeout;
   }

   public boolean isCrlDpBackgroundDownloadEnabled(X500Principal caDn) {
      return (Boolean)this.crlDpBackgroundDownloadEnabled.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_CrlDpBackgroundDownloadEnabled() {
      return this.crlDpBackgroundDownloadEnabled;
   }

   public URI getCrlDpUrl(X500Principal caDn) {
      return (URI)this.crlDpUrl.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_CrlDpUrl() {
      return this.crlDpUrl;
   }

   public AbstractCertRevocConstants.AttributeUsage getCrlDpUrlUsage(X500Principal caDn) {
      return (AbstractCertRevocConstants.AttributeUsage)this.crlDpUrlUsage.getResolvedValue(caDn);
   }

   public CaAttribute getAttribute_CrlDpUrlUsage() {
      return this.crlDpUrlUsage;
   }

   public static class CaAttribute {
      private final Attribute defaultValue;
      private static final int CA_VALUES_INIT_CAPACITY = 16;
      private final ConcurrentHashMap caValues = new ConcurrentHashMap(16);

      CaAttribute(Object initValue, Long minValue, Long maxValue) {
         this.defaultValue = new Attribute(initValue, minValue, maxValue);
      }

      public Object getResolvedValue(X500Principal caDn) {
         Object caValue = this.getCaValue(caDn);
         return null != caValue ? caValue : this.getDefaultValue();
      }

      public Object getDefaultValue() {
         return this.defaultValue.getValue();
      }

      public Object setDefaultValue(Object value) {
         return this.defaultValue.setValue(value);
      }

      public Object getCaValue(X500Principal caDn) {
         if (null == caDn) {
            throw new IllegalArgumentException("Non-null CA DN expected.");
         } else {
            return this.caValues.get(caDn);
         }
      }

      public Object setCaValue(X500Principal caDn, Object value) {
         if (null == caDn) {
            throw new IllegalArgumentException("Non-null CA DN expected.");
         } else if (null == value) {
            return this.caValues.remove(caDn);
         } else {
            this.defaultValue.checkRange(value);
            return this.caValues.put(caDn, value);
         }
      }
   }

   public static class Attribute {
      private volatile Object value;
      private final Long minValue;
      private final Long maxValue;

      Attribute(Object initValue, Long minValue, Long maxValue) {
         this.minValue = minValue;
         this.maxValue = maxValue;
         this.setValue(initValue);
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         this.checkRange(value);
         Object old = this.value;
         this.value = value;
         return old;
      }

      private void checkRange(Object value) {
         if (null != value) {
            if (null != this.minValue || null != this.maxValue) {
               boolean haveValue = false;
               long longValue = 0L;
               if (value instanceof Integer) {
                  longValue = (long)(Integer)value;
                  haveValue = true;
               }

               if (value instanceof Long) {
                  longValue = (Long)value;
                  haveValue = true;
               }

               if (haveValue) {
                  Util.checkRange((String)null, longValue, this.minValue, this.maxValue);
               }
            }

         }
      }
   }
}
