package weblogic.security.utils;

import com.bea.security.utils.keystore.KssAccessor;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.AccessController;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.CommandLine;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ServerChannel;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityMessagesTextFormatter;
import weblogic.security.SSL.HostnameVerifier;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.TrustManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.ServerAuthenticate;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class SSLSetup extends SSLSetupLogging {
   public static final int STANDARD_IO = 0;
   public static final int MUXING_IO = 1;
   public static final int LICENSE_NOT_CHECKED = -1;
   public static final int LICENSE_NONE = 0;
   public static final int LICENSE_DOMESTIC = 1;
   public static final int LICENSE_EXPORT = 2;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String FAILURE_DETAILS = "weblogic.security.ssl.failureDetails";
   private static boolean ioModelAccessed = false;
   private static int ioModel = 0;
   private static boolean enforceConstraintsChecked = false;
   private static int enforceConstraints = 1;
   private static boolean legacyProtocolVersionChecked = false;
   private static int legacyProtocolVersion = -1;
   private static boolean minimumProtocolVersionChecked = false;
   private static String minimumProtocolVersion = null;
   private static final String ENABLE_JSSE_PROPNAME = "weblogic.security.SSL.enableJSSE";
   static final String CERTICOM_DELEGATE = "com.bea.sslplus.CerticomSSLContext";
   static final String JSSE_DELEGATE = "weblogic.security.SSL.jsseadapter.JaSSLContextImpl";
   private static volatile Class sslDelegateClass = null;
   private static final int JKS_MAGIC_NUMBER = -17957139;

   public static SSLContextDelegate getSSLDelegateInstance() {
      Class sslDelegateClass = getDelegateClass();

      String msg;
      try {
         SSLContextDelegate sslDelegate = (SSLContextDelegate)sslDelegateClass.newInstance();
         if (delegateShouldSendEmptyCertRequest()) {
            sslDelegate.setSendEmptyCertRequest(true);
         }

         info("Use " + sslDelegate.getDescription());
         return sslDelegate;
      } catch (IllegalAccessException var3) {
         msg = SecurityLogger.getIllegalAccessOnContextWrapper(sslDelegateClass.getName());
         throw new RuntimeException(msg, var3);
      } catch (InstantiationException var4) {
         msg = SecurityLogger.getInstantiationExcOnContextWrapper(sslDelegateClass.getName());
         throw new RuntimeException(msg, var4);
      }
   }

   private static Class getDelegateClass() {
      if (sslDelegateClass != null) {
         return sslDelegateClass;
      } else {
         SSLImplementationSelection sslSelection = selectSSLImplementation();
         String delegateClassName;
         switch (sslSelection) {
            case CERTICOM:
               delegateClassName = "com.bea.sslplus.CerticomSSLContext";
               break;
            default:
               delegateClassName = "weblogic.security.SSL.jsseadapter.JaSSLContextImpl";
         }

         String msg1;
         try {
            Class delegateClass = loadDelegateClass(delegateClassName);
            if (!SSLContextDelegate.class.isAssignableFrom(delegateClass)) {
               msg1 = "Cannot initialize SSL implementation. " + delegateClassName + " does not implement " + SSLContextDelegate.class.getName();
               throw new IllegalArgumentException(msg1);
            } else {
               if (SSLSetup.SSLImplementationSelection.TEMPORARILY_JSSE != sslSelection) {
                  sslDelegateClass = delegateClass;
               }

               return delegateClass;
            }
         } catch (ClassNotFoundException var4) {
            msg1 = SecurityLogger.getClassNotFound(delegateClassName);
            throw new IllegalArgumentException(msg1, var4);
         }
      }
   }

   private static boolean classExists(String className) {
      try {
         loadDelegateClass(className);
         return true;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   private static Class loadDelegateClass(String className) throws ClassNotFoundException {
      return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
   }

   private static SSLImplementationSelection selectSSLImplementation() {
      boolean foundCerticom = classExists("com.bea.sslplus.CerticomSSLContext");
      SSLImplementationSelection selection = SSLSetup.SSLImplementationSelection.JSSE;
      if (isFatClient()) {
         String strValue = System.getProperty("weblogic.security.SSL.enableJSSE");
         if (strValue != null) {
            if (strValue.equalsIgnoreCase("true")) {
               selection = SSLSetup.SSLImplementationSelection.JSSE;
            } else if (strValue.equalsIgnoreCase("false")) {
               selection = SSLSetup.SSLImplementationSelection.CERTICOM;
            } else {
               selection = SSLSetup.SSLImplementationSelection.JSSE;
               SecurityLogger.logBadSysPropJsseEnabled("weblogic.security.SSL.enableJSSE", strValue);
            }
         }

         if (SSLSetup.SSLImplementationSelection.JSSE == selection && foundCerticom) {
            SecurityLogger.logCerticomAvailJsseEnabled_sysProp("weblogic.security.SSL.enableJSSE");
         } else if (SSLSetup.SSLImplementationSelection.CERTICOM == selection && !foundCerticom) {
            selection = SSLSetup.SSLImplementationSelection.JSSE;
            SecurityLogger.logCerticomNotAvailJsseDisabled_sysProp("weblogic.security.SSL.enableJSSE");
         }
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         if (null == runtimeAccess) {
            SecurityLogger.logNoSSLMBeanPossibleFailure();
            SecurityLogger.logNoSSLMBeanJsseEnabled();
            selection = SSLSetup.SSLImplementationSelection.TEMPORARILY_JSSE;
         } else {
            selection = runtimeAccess.getServer().getSSL().isJSSEEnabled() ? SSLSetup.SSLImplementationSelection.JSSE : SSLSetup.SSLImplementationSelection.CERTICOM;
            if (SSLSetup.SSLImplementationSelection.JSSE == selection && foundCerticom) {
               SecurityLogger.logCerticomAvailJsseEnabled_SSLMBean();
            } else if (SSLSetup.SSLImplementationSelection.CERTICOM == selection && !foundCerticom) {
               selection = SSLSetup.SSLImplementationSelection.JSSE;
               SecurityLogger.logCerticomNotAvailJsseDisabled_SSLMBean();
            }
         }
      }

      return selection;
   }

   private static boolean delegateShouldSendEmptyCertRequest() {
      return Boolean.getBoolean("weblogic.security.SSL.sendEmptyCAList");
   }

   public static synchronized void initForServer() {
      setIOModel(1);
      info("Enabled muxing IO for SSL in server");
   }

   public static int getIOModel() {
      ioModelAccessed = true;
      return ioModel;
   }

   public static boolean logSSLRejections() {
      if (Kernel.isApplet()) {
         return false;
      } else if (!Kernel.isServer()) {
         return true;
      } else {
         try {
            return ManagementService.getRuntimeAccess(kernelId) == null ? false : ManagementService.getRuntimeAccess(kernelId).getServer().getSSL().isSSLRejectionLoggingEnabled();
         } catch (Exception var1) {
            info(var1, "Caught exception in SSLSetup.logSSLRejections");
            return false;
         }
      }
   }

   public static void setIOModel(int newioModel) {
      if (newioModel != 0 && newioModel != 1) {
         debug(2, "Attempt to change SSL IO model to invalid setting");
      } else if (ioModelAccessed) {
         debug(2, "Attempt to change SSL IO model after access");
      } else {
         ioModel = newioModel;
      }
   }

   public static int getLegacyProtocolVersion() {
      if (!legacyProtocolVersionChecked) {
         String version = CommandLine.getCommandLine().getSSLVersion();
         if (version != null) {
            if (version.equalsIgnoreCase("SSL3")) {
               legacyProtocolVersion = 1;
            } else if (version.equalsIgnoreCase("TLS1")) {
               legacyProtocolVersion = 0;
            } else if (version.equalsIgnoreCase("ALL")) {
               legacyProtocolVersion = 3;
            } else {
               debug(3, version + " is not valid.  Using the default " + "TLSv1.2" + " as the minimum protocol version.");
            }
         }

         legacyProtocolVersionChecked = true;
      }

      return legacyProtocolVersion;
   }

   public static String getMinimumProtocolVersion() {
      if (!minimumProtocolVersionChecked) {
         minimumProtocolVersion = CommandLine.getCommandLine().getSSLMinimumProtocolVersion();
         minimumProtocolVersionChecked = true;
      }

      return minimumProtocolVersion;
   }

   public static int getEnforceConstraints() {
      if (!enforceConstraintsChecked) {
         try {
            String value = CommandLine.getCommandLine().getSSLEnforcementConstraint();
            if (value != null) {
               if (!value.equalsIgnoreCase("off") && !value.equalsIgnoreCase("false")) {
                  if (!value.equalsIgnoreCase("strong") && !value.equalsIgnoreCase("true")) {
                     if (value.equalsIgnoreCase("strict")) {
                        enforceConstraints = 2;
                     } else if (SSLContextDelegate.class.isAssignableFrom(sslDelegateClass)) {
                        if (value.equalsIgnoreCase("strong_nov1cas")) {
                           enforceConstraints = 4;
                        } else if (value.equalsIgnoreCase("strict_nov1cas")) {
                           enforceConstraints = 3;
                        }
                     }
                  } else {
                     enforceConstraints = 1;
                  }
               } else {
                  enforceConstraints = 0;
               }
            }
         } catch (SecurityException var1) {
         }

         enforceConstraintsChecked = true;
      }

      return enforceConstraints;
   }

   public static SSLContextWrapper getSSLContext() throws SocketException {
      return getSSLContext((SSLClientInfo)null, (ServerChannel)null);
   }

   public static SSLContextWrapper getSSLContext(SSLClientInfo clientInfo) throws SocketException {
      return getSSLContext(clientInfo, (ServerChannel)null);
   }

   public static SSLContextWrapper getSSLContext(SSLClientInfo clientInfo, ServerChannel channel) throws SocketException {
      SSLContextWrapper sslContext = SSLContextWrapper.getInstance(channel);
      if (!Kernel.isApplet()) {
         X509Certificate[] trustedCAs = getTrustedCAs(sslContext);
         if (trustedCAs != null) {
            try {
               sslContext.addTrustedCA(trustedCAs);
            } catch (Exception var5) {
               debug(2, var5, "Failure loading trusted CA list");
            }
         }
      }

      if (clientInfo != null) {
         applyInfo(sslContext, clientInfo);
      }

      return sslContext;
   }

   private static void applyInfo(SSLContextWrapper sslContext, SSLClientInfo clientInfo) throws SocketException {
      InputStream[] keyCerts = clientInfo.getSSLClientCertificate();
      if (keyCerts != null && keyCerts.length >= 2) {
         info("clientInfo has old style certificate and key");

         try {
            String pwdStr = clientInfo.getSSLClientKeyPassword();
            char[] password = null;
            if (pwdStr != null) {
               password = pwdStr.toCharArray();
            }

            PrivateKey theKey = sslContext.inputPrivateKey(keyCerts[0], password);
            X509Certificate[] certChain = new X509Certificate[keyCerts.length - 1];
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            for(int i = 1; i < keyCerts.length; ++i) {
               certChain[i - 1] = (X509Certificate)cf.generateCertificate(keyCerts[i]);
            }

            sslContext.addIdentity(certChain, theKey);
            info("client identity added");
         } catch (KeyManagementException var9) {
            info(var9, "Problem accessing private key");
            throw new SocketException(SecurityLogger.getProblemAccessingPrivateKey());
         } catch (CertificateException var10) {
            info(var10, "Problem with certificate chain");
            throw new SocketException(SecurityLogger.getProblemWithCertificateChain(var10.getMessage()));
         }
      }

      X509Certificate[] newCerts = clientInfo.getClientLocalIdentityCert();
      PrivateKey newKey = clientInfo.getClientLocalIdentityKey();
      if (newCerts != null && newKey != null) {
         info("clientInfo has new style certificate and key");
         sslContext.addIdentity(newCerts, newKey);
      }

      TrustManager trustMgr = clientInfo.getTrustManager();
      if (trustMgr != null) {
         info("clientInfo has programmatic TrustManager");
         sslContext.getTrustManager().setTrustManager(trustMgr);
      }

      byte[][] fp = clientInfo.getRootCAfingerprints();
      if (fp != null) {
         info("Adding legacy rootCA fingerprints");
         sslContext.getTrustManager().setRootCAFingerPrints(fp);
      }

      HostnameVerifier verifier = clientInfo.getHostnameVerifier();
      if (verifier != null) {
         info("clientInfo has HostnameVerifier");
         sslContext.getHostnameVerifier().setHostnameVerifier(verifier);
      }

      String expName = clientInfo.getExpectedName();
      if (expName != null) {
         info("clientInfo has expectedName");
         sslContext.getHostnameVerifier().setExpectedName(expName);
      }

   }

   private static X509Certificate[] getTrustedCAs(SSLContextWrapper sslContext) {
      X509Certificate[] certs = null;
      String caCertFile;
      if (!Kernel.isServer()) {
         caCertFile = CommandLine.getCommandLine().getSSLTrustCA();
         String trustCAPassPhrase = CommandLine.getCommandLine().getSSLTrustCAPassPhrase();
         KeyStoreInfo[] ksInfo = null;
         if (caCertFile != null && !caCertFile.trim().isEmpty()) {
            File caFile = new File(caCertFile);
            if (caFile.exists()) {
               if (caFile.isFile()) {
                  if (trustCAPassPhrase == null && !isJKSFile(caFile)) {
                     SecurityMessagesTextFormatter textFrmtr = SecurityMessagesTextFormatter.getInstance();
                     trustCAPassPhrase = ServerAuthenticate.promptValue(textFrmtr.getCATrustPasswordPromptMessage(caCertFile), false);
                  }
               } else {
                  debug(2, "SSLSetup: " + caCertFile + " is not a file.");
               }

               ksInfo = new KeyStoreInfo[]{new KeyStoreInfo(caCertFile, "jks", trustCAPassPhrase)};
            } else {
               debug(2, "SSLSetup: " + caCertFile + " does not exist.");
            }
         } else {
            ksInfo = (new KeyStoreConfigurationHelper(ClientKeyStoreConfiguration.getInstance())).getTrustKeyStores();
         }

         Collection caCerts = new ArrayList();

         for(int i = 0; ksInfo != null && i < ksInfo.length; ++i) {
            info("Trusted CA keystore: " + ksInfo[i].getFileName());

            try {
               KeyStore ks = KeyStore.getInstance(ksInfo[i].getType());
               FileInputStream certStream = new FileInputStream(ksInfo[i].getFileName());
               ks.load(certStream, ksInfo[i].getPassPhrase());
               caCerts.addAll(SSLCertUtility.getX509Certificates(ks));
               certStream.close();
            } catch (Exception var10) {
               debug(2, var10, "Failure loading trusted CA list from: " + ksInfo[i].getFileName());
            }
         }

         certs = (X509Certificate[])((X509Certificate[])caCerts.toArray(new X509Certificate[caCerts.size()]));
      } else {
         info("SSLSetup: loading trusted CA certificates");
         if (SecurityServiceManager.isSecurityServiceInitialized()) {
            try {
               certs = SSLContextManager.getServerTrustedCAs();
            } catch (Exception var9) {
               debug("Failed to load server trusted CAs", var9);
            }
         } else {
            debug(2, "SSLSetup: using pre-mbean command line configuration for SSL trust");
            caCertFile = CommandLine.getCommandLine().getSSLTrustCA();
            KeyStoreInfo[] ksInfo;
            if (caCertFile != null) {
               String SCHEME_KSS = "kss://";
               String scheme = caCertFile.substring(0, "kss://".length());
               if ("kss://".equalsIgnoreCase(scheme)) {
                  if (!KssAccessor.isKssAvailable()) {
                     debug(2, "SSLSetup: A KSS trust store is configured.  However the KSS provider is not available.");
                  }

                  ksInfo = new KeyStoreInfo[]{new KeyStoreInfo(caCertFile, "kss")};
               } else {
                  ksInfo = new KeyStoreInfo[]{new KeyStoreInfo(caCertFile, "jks")};
               }
            } else {
               ksInfo = (new KeyStoreConfigurationHelper(PreMBeanKeyStoreConfiguration.getInstance())).getTrustKeyStores();
               KssAccessor.isKssAvailable();
            }

            certs = SSLContextManager.getTrustedCAs(ksInfo);
         }
      }

      return certs != null && certs.length != 0 ? certs : null;
   }

   public static void setFailureDetails(SSLSession session, String message) {
      session.putValue("weblogic.security.ssl.failureDetails", message);
   }

   public static String getFailureDetails(SSLSession session) {
      return (String)session.getValue("weblogic.security.ssl.failureDetails");
   }

   public static void logPlaintextProtocolClientError(SSLSocket sock, String protocol) {
      String peer = getPeerName(sock);
      debug(2, "Connection to SSL port was made from " + peer + " using plaintext protocol: " + protocol);
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logPlaintextProtocolClientErrorLoggable(protocol, peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logProtocolVersionError(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "Connection to SSL port from " + peer + " appears to be either unknown SSL version or maybe is plaintext");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logProtocolVersionErrorLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainConstraintsStrictNonCriticalFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 CA certificate which had basic constraints which were not marked critical, this is being rejected due to the strict enforcement of basic constraints.");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainConstraintsStrictNonCriticalFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainMissingConstraintsFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 CA certificate which was missing the basic constraints extension");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainMissingConstraintsFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainNotACaConstraintsFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 CA certificate which didn't indicate it really is a CA");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainNotACaConstraintsFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainNoV1CAFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V1 CA certificate which cannot be a CA");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainNoV1CAFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainPathLenExceededConstraintsFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 CA certificate which indicated a certificate chain path length in the basic constraints that was exceeded");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainPathLenExceededConstraintsFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainConstraintsConversionFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 CA certificate which couldn't be converted to be checked for basic constraints.");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainConstraintsConversionFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainUnrecognizedExtensionFailure(SSLSocket sock, String oid) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 certificate with unrecognized critical extension: " + oid);
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainUnrecognizedExtensionFailureLoggable(peer, oid);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainAlgKeyUsageFailure(SSLSocket sock) {
      String peer = sock == null ? " peer " : getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 certificate which key usage constraints indicate its key cannot be used in quality required by the key agreement algorithm");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainAlgKeyUsageFailureLoggable(peer);
         logMsg.log();
         if (sock != null) {
            setFailureDetails(sock.getSession(), logMsg.getMessage());
         }
      }

   }

   public static void logCertificateChainCheckKeyUsageFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "Cannot check key usage constraints of certificate recieved from " + peer + " because of the failure to determine the key agreement algorithm");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainCheckKeyUsageFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificateChainCertSignKeyUsageFailure(SSLSocket sock) {
      String peer = getPeerName(sock);
      debug(2, "The certificate chain received from " + peer + " contained a V3 CA certificate which key usage constraints indicate its key cannot be used to sign certificates");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificateChainCertSignKeyUsageFailureLoggable(peer);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logCertificatePolicyIdDoesntExistIntheList(SSLSocket sock, String certPolicyId) {
      String peer = getPeerName(sock);
      debug(2, "Certificate Policies Extension Processing Failed,PolicyId: " + certPolicyId + " doesn't Exist in the allowed list");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logCertificatePolicyIdDoesntExistIntheListLoggable(certPolicyId);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static void logPolicyQualifierIdNotCPS(SSLSocket sock, String policyQualifierId) {
      String peer = getPeerName(sock);
      debug(2, "PolicyQualifier Id Found in the Certificate" + policyQualifierId + " doesn't match with CPS Qualifier Id");
      if (logSSLRejections()) {
         Loggable logMsg = SecurityLogger.logPolicyQualifierIdNotCPSLoggable(policyQualifierId);
         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }

   }

   public static String getPeerName(SSLSocket sock) {
      String peer = "unknown";
      if (sock != null) {
         InetAddress peerAddr = sock.getInetAddress();
         if (peerAddr != null) {
            try {
               peer = peerAddr.getHostName() + " - " + peerAddr.getHostAddress();
            } catch (SecurityException var4) {
               peer = peerAddr.getHostAddress();
            }

            if (peer == null) {
               peer = peerAddr.toString();
            }
         }
      }

      return peer;
   }

   public static void logAlertReceivedFromPeer(SSLSocket sock, int alertType) {
      if (logSSLRejections() && alertType != 0 && alertType != 90) {
         String peer = getPeerName(sock);
         Loggable logMsg;
         switch (alertType) {
            case 10:
               logMsg = SecurityLogger.logUnexpectedMessageAlertReceivedFromPeerLoggable(peer);
               break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            default:
               logMsg = SecurityLogger.logAlertReceivedFromPeerLoggable(peer, Integer.toString(alertType));
               break;
            case 20:
               logMsg = SecurityLogger.logBadRecordMacAlertReceivedFromPeerLoggable(peer);
               break;
            case 21:
               logMsg = SecurityLogger.logDecryptionFailedAlertReceivedFromPeerLoggable(peer);
               break;
            case 22:
               logMsg = SecurityLogger.logRecordOverFlowAlertReceivedFromPeerLoggable(peer);
               break;
            case 30:
               logMsg = SecurityLogger.logDecompressionFailureAlertReceivedFromPeerLoggable(peer);
               break;
            case 40:
               logMsg = SecurityLogger.logHandshakeFailureAlertReceivedFromPeerLoggable(peer);
               break;
            case 41:
               logMsg = SecurityLogger.logNoCertificateAlertReceivedFromPeerLoggable(peer);
               break;
            case 42:
               logMsg = SecurityLogger.logBadCertificateAlertReceivedFromPeerLoggable(peer);
               break;
            case 43:
               logMsg = SecurityLogger.logUnsupportedCertificateAlertReceivedFromPeerLoggable(peer);
               break;
            case 44:
               logMsg = SecurityLogger.logCertificateRevokedAlertReceivedFromPeerLoggable(peer);
               break;
            case 45:
               logMsg = SecurityLogger.logCertificateExpiredAlertReceivedFromPeerLoggable(peer);
               break;
            case 46:
               logMsg = SecurityLogger.logCertificateUnknownAlertReceivedFromPeerLoggable(peer);
               break;
            case 47:
               logMsg = SecurityLogger.logIllegalParameterAlertReceivedFromPeerLoggable(peer);
               break;
            case 48:
               logMsg = SecurityLogger.logUnknownCAAlertReceivedFromPeerLoggable(peer);
               break;
            case 49:
               logMsg = SecurityLogger.logAccessDeniedAlertReceivedFromPeerLoggable(peer);
               break;
            case 50:
               logMsg = SecurityLogger.logDecodeErrorAlertReceivedFromPeerLoggable(peer);
               break;
            case 51:
               logMsg = SecurityLogger.logDecryptErrorAlertReceivedFromPeerLoggable(peer);
               break;
            case 60:
               logMsg = SecurityLogger.logExportRestrictionAlertReceivedFromPeerLoggable(peer);
               break;
            case 70:
               logMsg = SecurityLogger.logProtocolVersionAlertReceivedFromPeerLoggable(peer);
               break;
            case 71:
               logMsg = SecurityLogger.logInsufficientSecurityAlertReceivedFromPeerLoggable(peer);
               break;
            case 80:
               logMsg = SecurityLogger.logInternalErrorAlertReceivedFromPeerLoggable(peer);
               break;
            case 100:
               logMsg = SecurityLogger.logNoRenegotiationAlertReceivedFromPeerLoggable(peer);
         }

         logMsg.log();
         setFailureDetails(sock.getSession(), logMsg.getMessage());
      }
   }

   public static Properties getSSLTrustProperties(ServerTemplateMBean server) {
      Properties props = new Properties();
      String keystores = server.getKeyStores();
      if ("DemoIdentityAndDemoTrust".equals(keystores)) {
         add(props, "TrustKeyStore", "DemoTrust");
         add(props, "JavaStandardTrustKeyStorePassPhrase", server.getJavaStandardTrustKeyStorePassPhrase());
      } else if ("CustomIdentityAndJavaStandardTrust".equals(keystores)) {
         add(props, "TrustKeyStore", "JavaStandardTrust");
         add(props, "JavaStandardTrustKeyStorePassPhrase", server.getJavaStandardTrustKeyStorePassPhrase());
      } else if ("CustomIdentityAndCustomTrust".equals(keystores)) {
         add(props, "TrustKeyStore", "CustomTrust");
         add(props, "CustomTrustKeyStoreFileName", server.getCustomTrustKeyStoreFileName());
         add(props, "CustomTrustKeyStoreType", server.getCustomTrustKeyStoreType());
         add(props, "CustomTrustKeyStorePassPhrase", server.getCustomTrustKeyStorePassPhrase());
      } else if (!"CustomIdentityAndCommandLineTrust".equals(keystores)) {
         throw new RuntimeException(SecurityLogger.getAssertionIllegalKeystoresValue(keystores));
      }

      return props;
   }

   static boolean isFatClient() {
      return !Kernel.isServer();
   }

   public static void logSSLUsingNullCipher() {
      SecurityLogger.logSSLUsingNullCipher();
   }

   private static void add(Properties props, String attr, String value) {
      if (value != null) {
         props.setProperty(attr, value);
      }

   }

   static boolean isJSSEContextDelegate(SSLContextDelegate delegate) {
      return null != delegate && delegate.getClass().getName().equals("weblogic.security.SSL.jsseadapter.JaSSLContextImpl");
   }

   static boolean isJKSFile(File file) {
      boolean result = false;

      try {
         InputStream is = new FileInputStream(file);
         DataInputStream di = new DataInputStream(is);
         if (di.available() > 0) {
            result = di.readInt() == -17957139;
         }

         di.close();
         is.close();
      } catch (IOException var4) {
         debug(2, var4, "Error detecting keystore type of " + file);
      }

      return result;
   }

   static {
      if (!Kernel.isServer()) {
         SecurityUtils.turnOffCryptoJDefaultJCEVerification();
         SecurityUtils.changeCryptoJDefaultPRNG();
      }

   }

   private static enum SSLImplementationSelection {
      JSSE,
      CERTICOM,
      TEMPORARILY_JSSE;
   }
}
