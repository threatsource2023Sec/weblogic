package weblogic.security.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.TLSMBean;
import weblogic.protocol.ServerChannel;
import weblogic.security.SecurityLogger;
import weblogic.security.SSL.HostnameVerifier;
import weblogic.utils.NestedRuntimeException;

public class SSLWLSHostnameVerifier implements SSLHostnameVerifier {
   private static final String IGNORE_VERIFICATION_PROP = "weblogic.security.SSL.ignoreHostnameVerification";
   private static final String IGNORE_VERIFICATION2_PROP = "weblogic.security.SSL.ignoreHostnameVerify";
   private static final String VERIFIER_CLASS_PROP = "weblogic.security.SSL.hostnameVerifier";
   private static final String REVERSE_DNS_ALLOWED_PROP = "weblogic.ReverseDNSAllowed";
   private static final String NULL_HOSTNAME_VERIFIER = "weblogic.security.utils.SSLWLSHostnameVerifier$NullHostnameVerifier";
   private static final String DEFAULT_HOSTNAME_VERIFIER_FLAG = "";
   private static final boolean ACCEPT_KSS_DEMOCERTS_ENABLED = Boolean.getBoolean("weblogic.ssl.AcceptKSSDemoCertsEnabled");
   private static final ConcurrentHashMap verifierCache = new ConcurrentHashMap(16);
   private String urlHostName;
   private String proxyHostName;
   private final HostnameVerifier defaultVerifier;
   private HostnameVerifier verifier;
   private String expectedName;
   public static final String URL_HOST_KEY = "wls_hostname_verifier_url_host";

   public SSLWLSHostnameVerifier() {
      this((ServerChannel)null);
   }

   public SSLWLSHostnameVerifier(ServerChannel channel) {
      this.urlHostName = null;
      this.proxyHostName = null;
      this.verifier = null;
      this.expectedName = null;
      this.defaultVerifier = getDefaultVerifier(channel);
      this.verifier = this.defaultVerifier;
   }

   public SSLWLSHostnameVerifier(TLSMBean tlsmBean) {
      this.urlHostName = null;
      this.proxyHostName = null;
      this.verifier = null;
      this.expectedName = null;
      this.defaultVerifier = getDefaultVerifier(tlsmBean);
      this.verifier = this.defaultVerifier;
   }

   public boolean hostnameValidationCallback(String hostName, SSLSocket sslSocket) {
      SSLSession session = sslSocket.getSession();
      boolean isProxying = this.isProxying(hostName, sslSocket);
      if (isProxying) {
         hostName = this.urlHostName;
         if (session != null) {
            session.putValue("wls_hostname_verifier_url_host", this.urlHostName);
         }
      }

      try {
         boolean debug = SSLSetup.isDebugEnabled(3);
         if (debug) {
            SSLSetup.info("Performing hostname validation checks: " + hostName);
            if (isProxying) {
               SSLSetup.info("Proxying through " + this.proxyHostName);
            }
         }

         boolean valid = this.verifier.verify(hostName, session);
         if (!valid) {
            if (SSLSetup.logSSLRejections()) {
               String peerName = this.getPeerName(isProxying, sslSocket);
               Loggable logMsg = SecurityLogger.logHostnameVerificationErrorLoggable(peerName, SSLCertUtility.getCommonName(session), hostName);
               logMsg.log();
               SSLSetup.setFailureDetails(session, logMsg.getMessage());
            }

            if (debug) {
               SSLSetup.info("Hostname Verification failed for certificate with CommonName '" + SSLCertUtility.getCommonName(session) + "' against hostname: " + hostName);
            }

            return false;
         } else {
            if (this.expectedName != null) {
               X509Certificate xcert = SSLCertUtility.getPeerLeafCert(sslSocket);
               String certHostName;
               if (xcert == null) {
                  if (SSLSetup.logSSLRejections()) {
                     certHostName = this.getPeerName(isProxying, sslSocket);
                     Loggable logMsg = SecurityLogger.logHostnameVerificationNoCertificateErrorLoggable(certHostName);
                     logMsg.log();
                     SSLSetup.setFailureDetails(session, logMsg.getMessage());
                  }

                  if (debug) {
                     SSLSetup.info("No identity certificate, cannot verify expected name: " + this.expectedName);
                  }

                  return false;
               }

               certHostName = SSLCertUtility.getCommonName(session);
               if (!this.expectedName.equals(certHostName)) {
                  if (SSLSetup.logSSLRejections()) {
                     String peerName = this.getPeerName(isProxying, sslSocket);
                     Loggable logMsg = SecurityLogger.logHostnameVerificationErrorLoggable(peerName, certHostName, this.expectedName);
                     logMsg.log();
                     SSLSetup.setFailureDetails(session, logMsg.getMessage());
                  }

                  if (debug) {
                     SSLSetup.info("Hostname Verification failed since certificate CommonName '" + certHostName + "' does not match expected name: " + this.expectedName);
                  }

                  return false;
               }
            }

            return true;
         }
      } catch (Exception var11) {
         if (SSLSetup.logSSLRejections()) {
            String peerName = this.getPeerName(isProxying, sslSocket);
            Loggable logMsg = SecurityLogger.logHostnameVerificationExceptionErrorLoggable(peerName);
            logMsg.log();
            SSLSetup.setFailureDetails(session, logMsg.getMessage());
         }

         SSLSetup.debug(1, var11, "Hostname Verification error");
         return false;
      }
   }

   private boolean isProxying(String hostName, SSLSocket socket) {
      if (this.proxyHostName != null && this.urlHostName != null) {
         if (this.proxyHostName.equals(hostName)) {
            return true;
         }

         InetAddress address = socket.getInetAddress();
         if (address != null && (this.proxyHostName.equals(address.getHostAddress()) || this.proxyHostName.equals(address.getHostName()))) {
            return true;
         }
      }

      return false;
   }

   private String getPeerName(boolean isProxying, SSLSocket socket) {
      String peerName = SSLSetup.getPeerName(socket);
      if (isProxying) {
         peerName = peerName + " --> " + this.urlHostName;
      }

      return peerName;
   }

   /** @deprecated */
   @Deprecated
   public void setExpectedName(String expectedName) {
      this.expectedName = expectedName;
   }

   public void setHostnameVerifier(HostnameVerifier verifier) {
      this.verifier = verifier != null ? verifier : this.defaultVerifier;
   }

   protected HostnameVerifier getHostnameVerifier() {
      return this.verifier;
   }

   private static HostnameVerifier getDefaultVerifier(ServerChannel channel) {
      return getDefaultVerifier(channel != null ? channel.getHostnameVerifier() : null, channel == null, channel != null ? channel.isHostnameVerificationIgnored() : false);
   }

   private static HostnameVerifier getDefaultVerifier(TLSMBean tlsmBean) {
      return getDefaultVerifier(tlsmBean != null ? tlsmBean.getHostnameVerifier() : null, tlsmBean == null, tlsmBean != null ? tlsmBean.isHostnameVerificationIgnored() : false);
   }

   private static HostnameVerifier getDefaultVerifier(String hostnameVerifier, boolean isMBeanNull, boolean hostnameVerificationIgnored) {
      KernelMBean kmb = Kernel.getConfig();
      SSLMBean sslConfig = null;
      if (kmb != null) {
         sslConfig = kmb.getSSL();
      }

      String verifierClassName;
      if (isHostnameVerificationIgnored(sslConfig, isMBeanNull, hostnameVerificationIgnored)) {
         verifierClassName = "weblogic.security.utils.SSLWLSHostnameVerifier$NullHostnameVerifier";
      } else {
         verifierClassName = getHostnameVerifierClassName(sslConfig, hostnameVerifier);
      }

      HostnameVerifier verifier = null;
      if (null == verifierClassName) {
         verifier = (HostnameVerifier)verifierCache.get("");
      } else {
         verifier = (HostnameVerifier)verifierCache.get(verifierClassName);
      }

      if (null == verifier) {
         verifier = createHostnameVerifier(verifierClassName);
         if (null == verifier) {
            throw new IllegalStateException("Unable to create HostnameVerifier.");
         }

         if (sslConfig != null || !Kernel.isServer()) {
            if (null == verifierClassName) {
               verifierCache.put("", verifier);
            } else {
               verifierCache.put(verifierClassName, verifier);
            }
         }
      }

      return verifier;
   }

   private static HostnameVerifier createHostnameVerifier(String verifierClass) {
      Object verifier;
      Loggable logMsg;
      if (verifierClass == null) {
         if (SSLSetup.isDebugEnabled(3)) {
            SSLSetup.info("HostnameVerifier: using SSLWLSWildcard as default hostnameverifier");
         }

         verifier = new SSLWLSWildcardHostnameVerifier();
         logMsg = SecurityLogger.logUsingDefaultHVLoggable();
         logMsg.log();
      } else {
         logMsg = null;

         Object verObj;
         try {
            verObj = Class.forName(verifierClass).newInstance();
         } catch (Exception var5) {
            Loggable logMsg = SecurityLogger.logHostnameVerifierInitErrorLoggable(verifierClass);
            logMsg.log();
            throw new NestedRuntimeException(logMsg.getMessage(), var5);
         }

         Loggable logMsg;
         if (!(verObj instanceof HostnameVerifier)) {
            logMsg = SecurityLogger.logHostnameVerifierInvalidErrorLoggable(verifierClass);
            logMsg.log();
            throw new NestedRuntimeException(logMsg.getMessage());
         }

         verifier = (HostnameVerifier)verObj;
         if (SSLSetup.isDebugEnabled(3)) {
            SSLSetup.info("HostnameVerifier: using configured hostnameverifier: " + verifier.getClass().getName());
         }

         logMsg = SecurityLogger.logUsingConfiguredHVLoggable(verifier.getClass().getName());
         logMsg.log();
      }

      return (HostnameVerifier)verifier;
   }

   private static boolean isHostnameVerificationIgnored(SSLMBean sslConfig, ServerChannel serverChannel) {
      return isHostnameVerificationIgnored(sslConfig, serverChannel == null, serverChannel != null ? serverChannel.isHostnameVerificationIgnored() : false);
   }

   private static boolean isHostnameVerificationIgnored(SSLMBean sslConfig, TLSMBean tlsmBean) {
      return isHostnameVerificationIgnored(sslConfig, tlsmBean == null, tlsmBean != null ? tlsmBean.isHostnameVerificationIgnored() : false);
   }

   private static boolean isHostnameVerificationIgnored(SSLMBean sslConfig, boolean isMBeanNull, boolean hostnameVerificationIgnored) {
      boolean ignored = false;

      try {
         if (!isMBeanNull) {
            ignored = hostnameVerificationIgnored;
         } else {
            ignored = sslConfig != null && sslConfig.isHostnameVerificationIgnored();
         }

         if (!ignored) {
            ignored = Boolean.getBoolean("weblogic.security.SSL.ignoreHostnameVerification") || Boolean.getBoolean("weblogic.security.SSL.ignoreHostnameVerify");
         }
      } catch (SecurityException var5) {
      }

      return ignored;
   }

   public void setProxyMapping(String theProxyHost, String theUrlHost) {
      this.urlHostName = theUrlHost;
      this.proxyHostName = theProxyHost;
   }

   private static String getHostnameVerifierClassName(SSLMBean sslConfig, ServerChannel serverChannel) {
      return getHostnameVerifierClassName(sslConfig, serverChannel != null ? serverChannel.getHostnameVerifier() : null);
   }

   private static String getHostnameVerifierClassName(SSLMBean sslConfig, TLSMBean tlsmBean) {
      return getHostnameVerifierClassName(sslConfig, tlsmBean != null ? tlsmBean.getHostnameVerifier() : null);
   }

   private static String getHostnameVerifierClassName(SSLMBean sslConfig, String hostnameVerifier) {
      String verifier = null;

      try {
         verifier = System.getProperty("weblogic.security.SSL.hostnameVerifier");
      } catch (SecurityException var4) {
      }

      if (null == verifier) {
         if (null != hostnameVerifier) {
            verifier = hostnameVerifier;
         } else if (sslConfig != null) {
            verifier = sslConfig.getHostnameVerifier();
         }
      }

      return verifier;
   }

   public static class NullHostnameVerifier implements HostnameVerifier {
      public boolean verify(String urlhostname, SSLSession session) {
         return true;
      }
   }

   public static class DefaultHostnameVerifier implements HostnameVerifier {
      private static final String LOCALHOST_HOSTNAME = "localhost";
      private static final String LOCALHOST_IPADDRESS = "127.0.0.1";
      private boolean allowReverseDNS = false;
      private static final boolean VERIFY_CN_AFTER_SAN = Boolean.parseBoolean(System.getProperty("weblogic.security.SSL.verifyCNAfterSAN", "true"));
      private static final String OPSS_DOMAIN_CA_COMMON_NAME = "cn=domainca";
      private static final String OPSS_DOMAIN_CA_ORG = "o=oracle";
      private static final boolean DEMO_CERT_CHECK_ENABLED = Boolean.valueOf(System.getProperty("weblogic.security.SSL.demoCertCheckEnabled", "true"));

      public DefaultHostnameVerifier() {
         if (!Kernel.isApplet() && System.getProperty("weblogic.ReverseDNSAllowed") != null) {
            this.allowReverseDNS = Boolean.getBoolean("weblogic.ReverseDNSAllowed");
         } else if (Kernel.getConfig() != null) {
            this.allowReverseDNS = Kernel.getConfig().isReverseDNSAllowed();
         }

         if (SSLSetup.isDebugEnabled(3)) {
            SSLSetup.info("HostnameVerifier: allowReverseDNS=" + this.allowReverseDNS);
         }

      }

      public boolean verify(String urlhostname, SSLSession session) {
         boolean matched = false;
         if (urlhostname != null && urlhostname.length() > 0 && session != null) {
            Collection subaltnames = SSLCertUtility.getDNSSubjAltNames(session, false, true);
            String certhostname = SSLCertUtility.getCommonName(session);
            if (subaltnames != null && subaltnames.size() > 0) {
               matched = this.verifyCNAfterSAN() ? this.doDNSSubjAltNamesVerify(urlhostname, subaltnames) || this.doVerify(urlhostname, session, certhostname) : this.doDNSSubjAltNamesVerify(urlhostname, subaltnames);
            } else {
               matched = this.doVerify(urlhostname, session, certhostname);
            }
         }

         return matched;
      }

      protected boolean verifyCNAfterSAN() {
         return VERIFY_CN_AFTER_SAN;
      }

      private boolean doVerify(String urlhostname, SSLSession session, String certhostname) {
         if (SSLSetup.isDebugEnabled(3)) {
            SSLSetup.info("doVerify: urlhostname=" + urlhostname + " certhostname=" + certhostname);
         }

         if (certhostname != null && certhostname.length() != 0) {
            if (urlhostname.equalsIgnoreCase(certhostname)) {
               return true;
            } else {
               if (DEMO_CERT_CHECK_ENABLED) {
                  X509Certificate cert = SSLCertUtility.getPeerLeafCert(session);
                  if (isDemoCert(cert)) {
                     if (SSLSetup.isDebugEnabled(3)) {
                        SSLSetup.info("isDemocert true: check urlhostname=" + urlhostname.toLowerCase(Locale.ENGLISH) + " and certhostname=" + certhostname.toLowerCase(Locale.ENGLISH));
                     }

                     if (urlhostname.toLowerCase(Locale.ENGLISH).startsWith(certhostname.toLowerCase(Locale.ENGLISH) + ".") || certhostname.toLowerCase(Locale.ENGLISH).startsWith(urlhostname.toLowerCase(Locale.ENGLISH) + ".")) {
                        return true;
                     }

                     if ((Kernel.getConfig() != null && Kernel.getConfig().getSSL().isAcceptKSSDemoCertsEnabled() || Kernel.getConfig() == null && SSLWLSHostnameVerifier.ACCEPT_KSS_DEMOCERTS_ENABLED) && certhostname.startsWith("DemoCertFor_")) {
                        return true;
                     }
                  }
               }

               try {
                  InetAddress localhost_addr = InetAddress.getLocalHost();
                  String this_machines_hostname = localhost_addr.getHostName();
                  if (this_machines_hostname.equalsIgnoreCase(certhostname)) {
                     if (localhost_addr.getHostAddress().equalsIgnoreCase(urlhostname)) {
                        return true;
                     }

                     if (this.allowReverseDNS) {
                        InetAddress urladdr = InetAddress.getByName(urlhostname);
                        if (urladdr.isLoopbackAddress()) {
                           return true;
                        }
                     } else if ("localhost".equalsIgnoreCase(urlhostname) || "127.0.0.1".equalsIgnoreCase(urlhostname)) {
                        return true;
                     }
                  }
               } catch (UnknownHostException var7) {
                  SSLSetup.info("HostnameVerifier: unknown host");
               }

               return false;
            }
         } else {
            return false;
         }
      }

      private boolean doDNSSubjAltNamesVerify(String urlhostname, Collection dnsAltNames) {
         if (dnsAltNames != null && !dnsAltNames.isEmpty()) {
            Iterator iter = dnsAltNames.iterator();

            while(iter.hasNext()) {
               String dnsName = (String)iter.next();
               if (SSLSetup.isDebugEnabled(3)) {
                  SSLSetup.info("doDNSSubjAltNamesVerify: compare dnsName=" + dnsName + " to urlhostname=" + urlhostname);
               }

               if (dnsName.equalsIgnoreCase(urlhostname)) {
                  return true;
               }
            }
         }

         return false;
      }

      private static boolean isDemoCert(X509Certificate cert) {
         boolean debugLoggingEnabled = SSLSetup.isDebugEnabled(3);
         if (null == cert) {
            if (debugLoggingEnabled) {
               SSLSetup.info("Hostname Verification detected null certificate.");
            }

            return false;
         } else {
            String issuerDN_v1 = "CN=CertGenCAB,OU=FOR TESTING ONLY,O=MyOrganization,L=MyTown,ST=MyState,C=US";
            String issuerDN_v2 = "CN=CertGenCA,OU=FOR TESTING ONLY,O=MyOrganization,L=MyTown,ST=MyState,C=US";
            String subjectDN_suffix = ",OU=FOR TESTING ONLY,O=MyOrganization,L=MyTown,ST=MyState,C=US";
            String subjectDN_prefix_bug16775745 = "C=US,ST=MyState,L=MyTown,O=MyOrganization,OU=FOR TESTING ONLY,";
            String certIssuerDN = cert.getIssuerX500Principal().getName("RFC2253");
            String certSubjectDN = cert.getSubjectX500Principal().getName("RFC2253");
            boolean issuerMatch = certIssuerDN.equals("CN=CertGenCA,OU=FOR TESTING ONLY,O=MyOrganization,L=MyTown,ST=MyState,C=US") || certIssuerDN.equals("CN=CertGenCAB,OU=FOR TESTING ONLY,O=MyOrganization,L=MyTown,ST=MyState,C=US") || isIssuerOPSSDomainCA(certIssuerDN);
            boolean subjectMatch = certSubjectDN.endsWith(",OU=FOR TESTING ONLY,O=MyOrganization,L=MyTown,ST=MyState,C=US") || certSubjectDN.startsWith("C=US,ST=MyState,L=MyTown,O=MyOrganization,OU=FOR TESTING ONLY,") || certSubjectDN.startsWith("CN=DemoCertFor_");
            if (issuerMatch && subjectMatch) {
               if (debugLoggingEnabled) {
                  SSLSetup.info("Hostname Verification detected a Demo certificate.");
               }

               return true;
            } else {
               if (debugLoggingEnabled) {
                  SSLSetup.info("Hostname Verification did not detect a Demo certificate.");
               }

               return false;
            }
         }
      }

      private static boolean isIssuerOPSSDomainCA(String issuer) {
         boolean matched = false;
         if (issuer != null) {
            String lowerCaseIssuer = issuer.toLowerCase();
            if (lowerCaseIssuer.startsWith("cn=domainca") && lowerCaseIssuer.endsWith("o=oracle")) {
               matched = true;
            }
         }

         return matched;
      }
   }
}
