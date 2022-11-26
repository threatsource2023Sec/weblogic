package weblogic.security.pki.revocation.wls;

import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;
import weblogic.kernel.KernelStatus;
import weblogic.management.DomainDir;
import weblogic.management.configuration.CertRevocCaMBean;
import weblogic.management.configuration.CertRevocMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.internal.ServerPropertyNameService;
import weblogic.security.pki.revocation.common.AbstractCertRevocConstants;
import weblogic.security.pki.revocation.common.AbstractCertRevocContext;
import weblogic.security.pki.revocation.common.CertRevocCheckMethodList;
import weblogic.security.pki.revocation.common.CertRevocStatus;
import weblogic.security.pki.revocation.common.Timer;
import weblogic.security.pki.revocation.common.AbstractCertRevocConstants.AttributeUsage;
import weblogic.security.pki.revocation.common.AbstractCertRevocConstants.CrlCacheType;
import weblogic.security.pki.revocation.wls.WLSCertRevocConstants.ExplicitTrustMethod;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.LocatorUtilities;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class WlsCertRevocContext extends AbstractCertRevocContext {
   public static final WLSCertRevocConstants.ExplicitTrustMethod DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD;
   public static final X500Principal DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME;
   public static final String DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME_STRING;
   public static final X500Principal DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME;
   public static final String DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME_STRING;
   public static final BigInteger DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER;
   public static final String DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER_STRING;

   public WlsCertRevocContext(Set trustedCerts) {
      super(trustedCerts, WlsLogListener.getInstance());
   }

   public void logAttemptingCertRevocCheck(X500Principal certToCheckSubjectDn) {
      String subjectName = nameFrom(certToCheckSubjectDn);
      SecurityLogger.logAttemptingCertRevocCheck(subjectName);
   }

   public void logUnknownCertRevocStatusNoFail(X500Principal certToCheckSubjectDn) {
      String subjectName = nameFrom(certToCheckSubjectDn);
      SecurityLogger.logUnknownCertRevocStatusNoFail(subjectName);
   }

   public void logCertRevocStatus(CertRevocStatus status) {
      String string = stringFrom(status);
      SecurityLogger.logCertRevocStatus(string);
   }

   public void logIgnoredNonceCertRevocStatus(CertRevocStatus status) {
      String string = stringFrom(status);
      SecurityLogger.logIgnoredNonceCertRevocStatus(string);
   }

   public void logUnknownCertRevocStatusFail(X500Principal certToCheckSubjectDn) {
      String subjectName = nameFrom(certToCheckSubjectDn);
      SecurityLogger.logUnknownCertRevocStatusFail(subjectName);
   }

   public void logRevokedCertRevocStatusFail(X500Principal certToCheckSubjectDn) {
      String subjectName = nameFrom(certToCheckSubjectDn);
      SecurityLogger.logRevokedCertRevocStatusFail(subjectName);
   }

   public void logNotRevokedCertRevocStatusNotFail(X500Principal certToCheckSubjectDn) {
      String subjectName = nameFrom(certToCheckSubjectDn);
      SecurityLogger.logNotRevokedCertRevocStatusNotFail(subjectName);
   }

   public void schedule(Runnable task) {
      if (null == task) {
         throw new IllegalArgumentException("Expected non-null Runnable.");
      } else {
         WorkManager wm = WorkManagerFactory.getInstance().getDefault();
         if (null == wm) {
            throw new IllegalStateException("No weblogic.work.WorkManager available.");
         } else {
            wm.schedule(task);
         }
      }
   }

   public Timer scheduleWithFixedDelay(final Runnable task, long delay, long period) {
      if (null == task) {
         throw new IllegalArgumentException("Unexpected null Runnable.");
      } else {
         TimerListener pollerListener = new TimerListener() {
            public final void timerExpired(weblogic.timers.Timer timer) {
               try {
                  task.run();
               } catch (Exception var3) {
                  if (WlsCertRevocContext.this.isLoggable(Level.FINE)) {
                     WlsCertRevocContext.this.log(Level.FINE, var3, "Exception occurred running timer task {0}.", new Object[]{task.getClass().getName()});
                  }
               }

            }
         };
         final weblogic.timers.Timer wlsTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(pollerListener, delay, period);
         Timer timer = new Timer() {
            public void cancel() {
               if (WlsCertRevocContext.this.isLoggable(Level.FINEST)) {
                  WlsCertRevocContext.this.log(Level.FINEST, "Cancelling timer task {0}.", new Object[]{task.getClass().getName()});
               }

               boolean foundNCancelled = wlsTimer.cancel();
               if (WlsCertRevocContext.this.isLoggable(Level.FINEST)) {
                  WlsCertRevocContext.this.log(Level.FINEST, "Returned from cancel for timer task {0}, Found/cancelled={1}.", new Object[]{task.getClass().getName(), foundNCancelled});
               }

            }
         };
         return timer;
      }
   }

   public boolean isCheckingEnabled() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_CHECKING_ENABLED : bean.isCheckingEnabled();
   }

   public boolean isCheckingDisabled(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CHECKING_DISABLED;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? DEFAULT_CHECKING_DISABLED : cabean.isCheckingDisabled();
      }
   }

   public boolean isFailOnUnknownRevocStatus(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.isFailOnUnknownRevocStatus() : cabean.isFailOnUnknownRevocStatus();
      }
   }

   public CertRevocCheckMethodList getMethodOrder(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_METHOD_ORDER;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         String value;
         if (null == cabean) {
            value = bean.getMethodOrder();
         } else {
            value = cabean.getMethodOrder();
         }

         try {
            return new CertRevocCheckMethodList(value);
         } catch (Exception var6) {
            this.logParsingException("MethodOrder", cabean, value, var6);
            return DEFAULT_METHOD_ORDER;
         }
      }
   }

   public URI getOcspResponderUrl(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONDER_URL;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_OCSP_RESPONDER_URL;
         } else {
            String value = cabean.getOcspResponderUrl();

            try {
               return null == value ? null : new URI(value);
            } catch (Exception var6) {
               this.logParsingException("OcspResponderUrl", cabean, value, var6);
               return DEFAULT_OCSP_RESPONDER_URL;
            }
         }
      }
   }

   public AbstractCertRevocConstants.AttributeUsage getOcspResponderUrlUsage(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONDER_URL_USAGE;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_OCSP_RESPONDER_URL_USAGE;
         } else {
            String value = cabean.getOcspResponderUrlUsage();

            try {
               return AttributeUsage.valueOf(value);
            } catch (Exception var6) {
               this.logParsingException("OcspResponderUrlUsage", cabean, value, var6);
               return DEFAULT_OCSP_RESPONDER_URL_USAGE;
            }
         }
      }
   }

   public X509Certificate getOcspResponderTrustedCert(X500Principal caDn) {
      X509Certificate trustedCert = null;
      WLSCertRevocConstants.ExplicitTrustMethod etm = this.getOcspResponderExplicitTrustMethod(caDn);
      switch (etm) {
         case USE_SUBJECT:
            X500Principal subject = this.getOcspResponderCertSubjectName(caDn);
            if (null != subject) {
               trustedCert = this.getValidTrustedCert(subject);
            }

            if (null == trustedCert && this.isLoggable(Level.FINE)) {
               this.log(Level.FINE, "No valid OCSP explicitly trusted certificate for CA \"{0}\" with subject \"{1}\" was found.", new Object[]{caDn, subject});
            }
            break;
         case USE_ISSUER_SERIAL_NUMBER:
            X500Principal issuer = this.getOcspResponderCertIssuerName(caDn);
            BigInteger serialNum = this.getOcspResponderCertSerialNumber(caDn);
            if (null != issuer && null != serialNum) {
               trustedCert = this.getValidTrustedCert(issuer, serialNum);
            }

            if (null == trustedCert && this.isLoggable(Level.FINE)) {
               this.log(Level.FINE, "No valid OCSP explicitly trusted certificate for CA \"{0}\" with issuer \"{1}\" serial number \"{2}\" was found.", new Object[]{caDn, issuer, serialNum});
            }
            break;
         case NONE:
            if (this.isLoggable(Level.FINEST)) {
               this.log(Level.FINEST, "No OCSP explicitly trusted certificate for CA \"{0}\" using method \"{1}\".", new Object[]{caDn, etm});
            }
            break;
         default:
            throw new IllegalStateException("Unknown ExplicitTrustMethod " + etm);
      }

      if (null != trustedCert && this.isLoggable(Level.FINEST)) {
         this.log(Level.FINEST, "Found valid OCSP explicitly trusted certificate for CA \"{0}\" using method \"{1}\" with subject \"{2}\".", new Object[]{caDn, etm, trustedCert.getSubjectX500Principal()});
      }

      return trustedCert;
   }

   WLSCertRevocConstants.ExplicitTrustMethod getOcspResponderExplicitTrustMethod(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD;
         } else {
            String value = cabean.getOcspResponderExplicitTrustMethod();

            try {
               return ExplicitTrustMethod.valueOf(value);
            } catch (Exception var6) {
               this.logParsingException("OcspResponderExplicitTrustMethod", cabean, value, var6);
               return DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD;
            }
         }
      }
   }

   X500Principal getOcspResponderCertSubjectName(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME;
         } else {
            String value = cabean.getOcspResponderCertSubjectName();

            try {
               return null == value ? null : new X500Principal(value);
            } catch (Exception var6) {
               this.logParsingException("OcspResponderCertSubjectName", cabean, value, var6);
               return DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME;
            }
         }
      }
   }

   X500Principal getOcspResponderCertIssuerName(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME;
         } else {
            String value = cabean.getOcspResponderCertIssuerName();

            try {
               return null == value ? null : new X500Principal(value);
            } catch (Exception var6) {
               this.logParsingException("OcspResponderCertIssuerName", cabean, value, var6);
               return DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME;
            }
         }
      }
   }

   BigInteger getOcspResponderCertSerialNumber(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER;
         } else {
            String value = cabean.getOcspResponderCertSerialNumber();

            try {
               return null == value ? null : new BigInteger(value);
            } catch (Exception var6) {
               this.logParsingException("OcspResponderCertSerialNumber", cabean, value, var6);
               return DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER;
            }
         }
      }
   }

   public boolean isOcspNonceEnabled(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_NONCE_ENABLED;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.isOcspNonceEnabled() : cabean.isOcspNonceEnabled();
      }
   }

   public PrivateKey getOcspRequestSigningPrivateKey(X500Principal caDn) {
      return null;
   }

   public X509Certificate getOcspRequestSigningCert(X500Principal caDn) {
      return null;
   }

   public boolean isOcspResponseCacheEnabled(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONSE_CACHE_ENABLED;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.isOcspResponseCacheEnabled() : cabean.isOcspResponseCacheEnabled();
      }
   }

   public int getOcspResponseCacheCapacity() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY : bean.getOcspResponseCacheCapacity();
   }

   public int getOcspResponseCacheRefreshPeriodPercent() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT : bean.getOcspResponseCacheRefreshPeriodPercent();
   }

   public long getOcspResponseTimeout(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_RESPONSE_TIMEOUT;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.getOcspResponseTimeout() : cabean.getOcspResponseTimeout();
      }
   }

   public int getOcspTimeTolerance(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_OCSP_TIME_TOLERANCE;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.getOcspTimeTolerance() : cabean.getOcspTimeTolerance();
      }
   }

   public AbstractCertRevocConstants.CrlCacheType getCrlCacheType() {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CRL_CACHE_TYPE;
      } else {
         String value = bean.getCrlCacheType();

         try {
            return CrlCacheType.valueOf(value);
         } catch (Exception var4) {
            this.logParsingException("CrlCacheType", (CertRevocCaMBean)null, value, var4);
            return DEFAULT_CRL_CACHE_TYPE;
         }
      }
   }

   public File getCrlCacheImportDir() {
      File serverSecurityBaseDir = this.getServerSecurityBaseDir();
      if (null == serverSecurityBaseDir) {
         return DEFAULT_CRL_CACHE_IMPORT_DIR;
      } else {
         File importDir = getCrlCacheImportDirectory(serverSecurityBaseDir);
         return null == importDir ? DEFAULT_CRL_CACHE_IMPORT_DIR : importDir;
      }
   }

   public File getCrlCacheTypeFileDir() {
      File serverSecurityBaseDir = this.getServerSecurityBaseDir();
      if (null == serverSecurityBaseDir) {
         return DEFAULT_CRL_CACHE_TYPE_FILE_DIR;
      } else {
         File storageDir = getCrlCacheStorageDirectory(serverSecurityBaseDir);
         return null == storageDir ? DEFAULT_CRL_CACHE_TYPE_FILE_DIR : storageDir;
      }
   }

   public String getCrlCacheTypeLdapHostname() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME : bean.getCrlCacheTypeLdapHostname();
   }

   public int getCrlCacheTypeLdapPort() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_CRL_CACHE_TYPE_LDAP_PORT : bean.getCrlCacheTypeLdapPort();
   }

   public int getCrlCacheTypeLdapSearchTimeout() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT : bean.getCrlCacheTypeLdapSearchTimeout();
   }

   public int getCrlCacheRefreshPeriodPercent() {
      CertRevocMBean bean = this.getCertRevocMBean();
      return null == bean ? DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT : bean.getCrlCacheRefreshPeriodPercent();
   }

   public boolean isCrlDpEnabled(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CRL_DP_ENABLED;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.isCrlDpEnabled() : cabean.isCrlDpEnabled();
      }
   }

   public long getCrlDpDownloadTimeout(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.getCrlDpDownloadTimeout() : cabean.getCrlDpDownloadTimeout();
      }
   }

   public boolean isCrlDpBackgroundDownloadEnabled(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         return null == cabean ? bean.isCrlDpBackgroundDownloadEnabled() : cabean.isCrlDpBackgroundDownloadEnabled();
      }
   }

   public URI getCrlDpUrl(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CRL_DP_URL;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_CRL_DP_URL;
         } else {
            String value = cabean.getCrlDpUrl();

            try {
               return null == value ? null : new URI(value);
            } catch (Exception var6) {
               this.logParsingException("CrlDpUrl", cabean, value, var6);
               return DEFAULT_CRL_DP_URL;
            }
         }
      }
   }

   public AbstractCertRevocConstants.AttributeUsage getCrlDpUrlUsage(X500Principal caDn) {
      CertRevocMBean bean = this.getCertRevocMBean();
      if (null == bean) {
         return DEFAULT_CRL_DP_URL_USAGE;
      } else {
         CertRevocCaMBean cabean = this.getCertRevocCaMBean(bean, caDn);
         if (null == cabean) {
            return DEFAULT_CRL_DP_URL_USAGE;
         } else {
            String value = cabean.getCrlDpUrlUsage();

            try {
               return AttributeUsage.valueOf(value);
            } catch (Exception var6) {
               this.logParsingException("CrlDpUrlUsage", cabean, value, var6);
               return DEFAULT_CRL_DP_URL_USAGE;
            }
         }
      }
   }

   ServerMBean getServerMBean() {
      ServerMBean result = null;

      try {
         if (KernelStatus.isServer()) {
            SecurityRuntimeAccess runtimeAccess = WlsCertRevocContext.SecurityRuntimeAccessService.getRuntimeAccess(this);
            if (null == runtimeAccess) {
               this.logUnexpectedNullMBean("RuntimeAccess");
            } else {
               ServerMBean serverMBean = runtimeAccess.getServer();
               if (null == serverMBean) {
                  this.logUnexpectedNullMBean("ServerMBean");
               }
            }
         } else if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, "Certificate revocation checking is currently unavailable outside the server.", new Object[0]);
         }

         return (ServerMBean)result;
      } catch (RuntimeException var4) {
         if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, var4, "Failure getting ServerMBean.", new Object[0]);
         }

         throw var4;
      }
   }

   private String getServerName() {
      String result = null;

      try {
         if (KernelStatus.isServer()) {
            ServerPropertyNameService propService = (ServerPropertyNameService)AccessController.doPrivileged(new PrivilegedAction() {
               public ServerPropertyNameService run() {
                  return (ServerPropertyNameService)LocatorUtilities.getService(ServerPropertyNameService.class);
               }
            });
            if (null == propService) {
               if (this.isLoggable(Level.FINE)) {
                  this.log(Level.FINE, "Unexpected null PropertyService.", new Object[0]);
               }
            } else {
               result = propService.getServerName();
            }
         } else if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, "Certificate revocation checking is currently unavailable outside the server.", new Object[0]);
         }

         return result;
      } catch (RuntimeException var3) {
         if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, var3, "Failure getting server name.", new Object[0]);
         }

         throw var3;
      }
   }

   CertRevocMBean getCertRevocMBean() {
      CertRevocMBean result = null;

      try {
         if (KernelStatus.isServer()) {
            if (!SecurityServiceManager.isSecurityServiceInitialized()) {
               return null;
            }

            SecurityRuntimeAccess runtimeAccess = WlsCertRevocContext.SecurityRuntimeAccessService.getRuntimeAccess(this);
            if (null == runtimeAccess) {
               this.logUnexpectedNullMBean("RuntimeAccess");
            } else {
               DomainMBean domainMBean = runtimeAccess.getDomain();
               if (null == domainMBean) {
                  this.logUnexpectedNullMBean("DomainMBean");
               } else {
                  SecurityConfigurationMBean securityMBean = domainMBean.getSecurityConfiguration();
                  if (null == securityMBean) {
                     this.logUnexpectedNullMBean("SecurityConfigurationMBean");
                  } else {
                     result = securityMBean.getCertRevoc();
                     if (null == result) {
                        this.logUnexpectedNullMBean("CertRevocMBean");
                     }
                  }
               }
            }
         } else if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, "Certificate revocation checking is currently unavailable outside the server.", new Object[0]);
         }

         return result;
      } catch (RuntimeException var5) {
         if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, var5, "Failure getting CertRevocMBean.", new Object[0]);
         }

         throw var5;
      }
   }

   CertRevocCaMBean getCertRevocCaMBean(CertRevocMBean certRevocMBean, X500Principal caDn) {
      if (null == certRevocMBean) {
         throw new IllegalArgumentException("Expected non-null CertRevocMBean.");
      } else if (null == caDn) {
         if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, "Non-null caDn expected.", new Object[0]);
         }

         return null;
      } else {
         CertRevocCaMBean[] caBeans = certRevocMBean.getCertRevocCas();
         if (null != caBeans && 0 != caBeans.length) {
            CertRevocCaMBean[] var4 = caBeans;
            int var5 = caBeans.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               CertRevocCaMBean caBean = var4[var6];
               if (null != caBean) {
                  String beanDn = caBean.getDistinguishedName();
                  if (null != beanDn && 0 != beanDn.length()) {
                     X500Principal beanPrincipal;
                     try {
                        beanPrincipal = new X500Principal(beanDn);
                     } catch (Exception var11) {
                        continue;
                     }

                     if (caDn.equals(beanPrincipal)) {
                        return caBean;
                     }
                  }
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   private void logParsingException(String attributeName, CertRevocCaMBean cabean, String value, Exception e) {
      if (this.isLoggable(Level.FINE)) {
         String mbeanName = null == cabean ? "CertRevocMBean" : "CertRevocCaMBean";
         this.log(Level.FINE, e, "Invalid {0}.{1} value {2}", new Object[]{mbeanName, attributeName, value});
      }

   }

   private void logUnexpectedNullMBean(String beanName) {
      if (this.isLoggable(Level.FINE)) {
         this.log(Level.FINE, "Unexpected null {0}.", new Object[]{beanName});
      }

   }

   private File getServerSecurityBaseDir() {
      String serverName = this.getServerName();
      if (null != serverName && serverName.length() != 0) {
         File serverSecurityBaseDir = new File(DomainDir.getSecurityDirForServer(serverName));
         return serverSecurityBaseDir;
      } else {
         if (this.isLoggable(Level.FINE)) {
            this.log(Level.FINE, "Server name is null or empty.", new Object[0]);
         }

         return null;
      }
   }

   private static String nameFrom(X500Principal principal) {
      String name = principal != null ? principal.getName() : null;
      return name;
   }

   private static String stringFrom(CertRevocStatus status) {
      String string = status != null ? status.toString() : null;
      return string;
   }

   static {
      DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD = ExplicitTrustMethod.NONE;
      DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME = null;
      DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME_STRING = null;
      DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME = null;
      DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME_STRING = null;
      DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER = null;
      DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER_STRING = null;
   }

   private static final class SecurityRuntimeAccessService {
      private static volatile SecurityRuntimeAccess runtimeAccess = null;

      private static SecurityRuntimeAccess getRuntimeAccess(AbstractCertRevocContext ctx) {
         if (null == runtimeAccess) {
            try {
               SecurityRuntimeAccess tempRuntimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
                  public SecurityRuntimeAccess run() {
                     return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
                  }
               });
               runtimeAccess = tempRuntimeAccess;
            } catch (Exception var2) {
               if (null != ctx && ctx.isLoggable(Level.FINE)) {
                  ctx.log(Level.FINE, "Unable to obtain SecurityRuntimeAccess, which may be due to a missing config.xml file. " + var2.getClass().getName() + " occurred while getting " + SecurityRuntimeAccess.class.getName() + ", message: " + var2.getMessage(), var2);
               }
            }
         }

         return runtimeAccess;
      }
   }
}
