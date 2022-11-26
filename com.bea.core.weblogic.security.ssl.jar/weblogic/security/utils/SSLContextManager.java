package weblogic.security.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import utils.ValidateCertChain;
import weblogic.kernel.T3SrvrLogger;
import weblogic.logging.Loggable;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.TLSMBean;
import weblogic.management.provider.CommandLine;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerChannelRuntimeProvider;
import weblogic.management.security.RealmMBean;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.UnknownProtocolException;
import weblogic.security.SecurityLogger;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.SSLEngineFactory;
import weblogic.security.SSL.TrustManager;
import weblogic.security.SSL.jsseadapter.JaSSLEngineFactoryBuilder;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.pki.keystore.WLSKeyStoreFactory;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class SSLContextManager {
   private static final int ONE_DAY = 86400000;
   private static final int WARNING_PERIOD = 30;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static SSLSocketFactory defaultFactory = null;
   private static SSLSocketFactory defaultNioFactory = null;
   private static SSLContextWrapper defaultContext = null;
   private static Map channelContexts = new HashMap();
   private static Map clientSSLSocketFactories = new ConcurrentHashMap();
   private static Map clientChannelContexts = new ConcurrentHashMap();
   private static Map sslIdentities = new Hashtable();
   private static X509Certificate[] trustedCACerts = null;
   private static char[] keyFilePwd = null;
   private boolean debug = SSLSetup.isDebugEnabled(3);
   private RuntimeAccess runtimeAccess = null;

   public static SSLServerSocketFactory getSSLServerSocketFactory(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject);
      return context.getSSLServerSocketFactory();
   }

   public static SSLServerSocketFactory getSSLNioServerSocketFactory(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject);
      return context.getSSLNioServerSocketFactory();
   }

   public static SSLSocketFactory getSSLSocketFactory(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject);
      return context.getSSLSocketFactory();
   }

   public static SSLSocketFactory getSSLNioSocketFactory(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject);
      return context.getSSLNioSocketFactory();
   }

   public static SSLClientInfo getChannelSSLClientInfo(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      return getChannelSSLClientInfo(channel, privilegedSubject, true, true);
   }

   public static SSLClientInfo getChannelSSLClientInfo(ServerChannel channel, AuthenticatedSubject privilegedSubject, boolean isServer, boolean useServerCert) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject, isServer);
      SSLContextManager contextMgr = new SSLContextManager(privilegedSubject);
      SSLClientInfo info = new SSLClientInfo();
      if (useServerCert) {
         SSLIdentity identity = contextMgr.getServerSSLIdentity(((ServerChannelRuntimeProvider)channel).getConfig(), context, true);
         info.loadLocalIdentity(identity.certChain, identity.key);
      }

      return info;
   }

   public static SSLClientInfo getNioChannelSSLClientInfo(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject);
      SSLContextManager contextMgr = new SSLContextManager(privilegedSubject);
      SSLIdentity identity = contextMgr.getServerSSLIdentity(((ServerChannelRuntimeProvider)channel).getConfig(), context, true);
      SSLClientInfo info = new SSLClientInfo(true);
      info.loadLocalIdentity(identity.certChain, identity.key);
      return info;
   }

   public static synchronized SSLSocketFactory getDefaultSSLSocketFactory(AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      if (defaultFactory == null) {
         SSLContextWrapper context = getDefaultServerSSLContext(privilegedSubject);
         defaultFactory = context.getSSLSocketFactory();
      } else {
         ManagementService.getRuntimeAccess(privilegedSubject);
      }

      return defaultFactory;
   }

   public static synchronized SSLSocketFactory getDefaultClientSSLSocketFactory(String protocol, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException, SocketException, UnknownProtocolException {
      ServerChannel channel = ProtocolManager.findProtocol(protocol).getHandler().getDefaultServerChannel();
      String channelId = ((ServerChannelRuntimeProvider)channel).getConfig().getName();
      SSLSocketFactory factory = (SSLSocketFactory)clientSSLSocketFactories.get(channelId);
      if (factory == null) {
         SSLContextWrapper context = getDefaultClientSSLContext(protocol, privilegedSubject);
         factory = context.getSSLSocketFactory();
         clientSSLSocketFactories.put(channelId, factory);
      } else {
         ManagementService.getRuntimeAccess(privilegedSubject);
      }

      return factory;
   }

   public static synchronized SSLSocketFactory getDefaultNioSSLSocketFactory(AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      if (defaultNioFactory == null) {
         SSLContextWrapper context = getDefaultServerSSLContext(privilegedSubject);
         defaultNioFactory = context.getSSLNioSocketFactory();
      } else {
         ManagementService.getRuntimeAccess(privilegedSubject);
      }

      return defaultNioFactory;
   }

   public static SSLSocketFactory getSSLSocketFactory(AuthenticatedSubject privilegedSubject, TrustManager trustManager) throws ConfigurationException, CertificateException {
      SSLContextManager contextMgr = new SSLContextManager(privilegedSubject);
      SSLContextWrapper context = contextMgr.createServerSSLContext((ServerChannel)null);
      context.getTrustManager().setTrustManager(trustManager);
      return context.getSSLSocketFactory();
   }

   public static SSLSocketFactory getSSLNioSocketFactory(AuthenticatedSubject privilegedSubject, TrustManager trustManager) throws ConfigurationException, CertificateException {
      SSLContextManager contextMgr = new SSLContextManager(privilegedSubject);
      SSLContextWrapper context = contextMgr.createServerSSLContext((ServerChannel)null);
      context.getTrustManager().setTrustManager(trustManager);
      return context.getSSLNioSocketFactory();
   }

   public static synchronized X509Certificate[] getServerTrustedCAs() throws ConfigurationException, CertificateException {
      if (trustedCACerts != null) {
         X509Certificate[] certs = new X509Certificate[trustedCACerts.length];
         System.arraycopy(trustedCACerts, 0, certs, 0, trustedCACerts.length);
         return certs;
      } else {
         return getDefaultServerSSLContext(kernelId).getTrustedCAs();
      }
   }

   public static synchronized void clearSSLContextCache() {
      trustedCACerts = null;
      defaultFactory = null;
      defaultNioFactory = null;
      defaultContext = null;
      channelContexts.clear();
      sslIdentities.clear();
      clientSSLSocketFactories.clear();
      clientChannelContexts.clear();
      weblogic.security.SSL.SSLSocketFactory.setDefault(kernelId);
   }

   public static X509Certificate[] getTrustedCAs(KeyStoreInfo[] ksInfo) {
      ArrayList caCerts = new ArrayList();

      for(int i = 0; ksInfo != null && i < ksInfo.length; ++i) {
         Collection certs = getTrustedCAs(ksInfo[i].getFileName(), ksInfo[i].getType(), ksInfo[i].getPassPhrase());
         if (certs != null) {
            caCerts.addAll(certs);
         }
      }

      return (X509Certificate[])((X509Certificate[])caCerts.toArray(new X509Certificate[caCerts.size()]));
   }

   public static final SSLEngineFactory getSSLEngineFactory(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      SSLContextWrapper context = getChannelSSLContext(channel, privilegedSubject);
      return JaSSLEngineFactoryBuilder.getFactoryInstance(context);
   }

   public static SSLContextWrapper getSSLContextWrapper(TLSMBean tlsmBean, AuthenticatedSubject privilegedSubject) {
      ManagementService.getRuntimeAccess(privilegedSubject);
      return SSLContextWrapper.getInstance(tlsmBean);
   }

   private static synchronized SSLContextWrapper getDefaultServerSSLContext(AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      if (defaultContext == null) {
         SSLContextManager contextMgr = new SSLContextManager(privilegedSubject);
         defaultContext = contextMgr.createServerSSLContext((ServerChannel)null);
      } else {
         ManagementService.getRuntimeAccess(privilegedSubject);
      }

      return defaultContext;
   }

   private static synchronized SSLContextWrapper getDefaultClientSSLContext(String protocol, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException, SocketException, UnknownProtocolException {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(privilegedSubject);
      ServerChannel channel = ProtocolManager.findProtocol(protocol).getHandler().getDefaultServerChannel();
      SSLContextWrapper sslContextWrapper = getChannelSSLContext(channel, privilegedSubject, false);
      if (sslContextWrapper == null) {
         SSLClientInfo sslClientInfo = null;
         SSLMBean sslMBean = ra.getServer().getSSL();
         boolean serverCertUsed = false;
         if (sslMBean != null) {
            serverCertUsed = sslMBean.isUseServerCerts();
         }

         sslClientInfo = getChannelSSLClientInfo(channel, privilegedSubject, false, serverCertUsed);
         sslContextWrapper = SSLSetup.getSSLContext(sslClientInfo, channel);
      }

      return sslContextWrapper;
   }

   private static synchronized SSLContextWrapper getChannelSSLContext(ServerChannel channel, AuthenticatedSubject privilegedSubject) throws ConfigurationException, CertificateException {
      return getChannelSSLContext(channel, privilegedSubject, true);
   }

   private static synchronized SSLContextWrapper getChannelSSLContext(ServerChannel channel, AuthenticatedSubject privilegedSubject, boolean isServer) throws ConfigurationException, CertificateException {
      Map tmpChannelContexts = isServer ? channelContexts : clientChannelContexts;
      boolean debug = SSLSetup.isDebugEnabled(3);
      String channelId = ((ServerChannelRuntimeProvider)channel).getConfig().getName();
      SSLContextWrapper context = (SSLContextWrapper)tmpChannelContexts.get(channelId);
      if (context == null) {
         if (debug) {
            SSLSetup.info("SSLContextManager: initializing SSL context for channel " + channelId);
         }

         SSLContextManager contextMgr = new SSLContextManager(privilegedSubject);
         context = contextMgr.createSSLContext(channel, isServer);
         tmpChannelContexts.put(channelId, context);
      } else {
         if (debug) {
            SSLSetup.info("SSLContextManager: reusing SSL context of channel " + channelId);
         }

         ManagementService.getRuntimeAccess(privilegedSubject);
      }

      return context;
   }

   private SSLContextManager(AuthenticatedSubject privilegedSubject) {
      if (privilegedSubject == null) {
         throw new NullPointerException("null privileged subject");
      } else {
         this.runtimeAccess = ManagementService.getRuntimeAccess(privilegedSubject);
      }
   }

   private SSLContextWrapper createServerSSLContext(ServerChannel channel) throws ConfigurationException, CertificateException {
      return this.createSSLContext(channel, true);
   }

   private SSLContextWrapper createSSLContext(ServerChannel channel, boolean isServer) throws ConfigurationException, CertificateException {
      SSLContextWrapper sslContext = SSLContextWrapper.getInstance(channel);
      SSLMBean sslMBean = this.runtimeAccess.getServer().getSSL();
      if (null != channel) {
         sslContext.enableUnencryptedNullCipher(channel.isAllowUnencryptedNullCipher());
      } else if (sslMBean != null) {
         sslContext.enableUnencryptedNullCipher(sslMBean.isAllowUnencryptedNullCipher());
      }

      NetworkAccessPointMBean channelConfig = channel == null ? null : ((ServerChannelRuntimeProvider)channel).getConfig();
      if (isServer || sslMBean != null && sslMBean.isUseServerCerts()) {
         SSLIdentity identity = null;

         try {
            identity = this.getServerSSLIdentity(channelConfig, sslContext, false);
         } catch (ConfigurationException var11) {
            if (this.debug) {
               SSLSetup.info(var11, "SSLContextManager: couldnot get server SSL identity");
            }

            if (channel != null) {
               throw var11;
            }
         }

         if (identity != null) {
            this.checkIdentity(sslContext, identity);
            sslContext.addIdentity(identity.certChain, identity.key, identity.ksType);
         }
      }

      X509Certificate[] trustedCAs = trustedCACerts;
      if (trustedCAs == null) {
         trustedCAs = this.getServerTrustedCAs(sslContext);
         Class var7 = SSLContextManager.class;
         synchronized(SSLContextManager.class) {
            trustedCACerts = trustedCAs;
         }

         if (trustedCAs != null && trustedCAs.length > 0 && this.runtimeAccess.getDomain().isProductionModeEnabled()) {
            X509Certificate demoCert = findDemoCert(trustedCAs);
            if (demoCert != null) {
               SecurityLogger.logDemoTrustCertificateUsed(demoCert.toString());
            }
         }
      }

      boolean clientCertEnforced = channelConfig != null ? channelConfig.isClientCertificateEnforced() : sslMBean.isClientCertificateEnforced();
      if (trustedCAs == null || trustedCAs.length == 0) {
         if (clientCertEnforced) {
            fail(SecurityLogger.logClientCertEnforcedNoTrustedCALoggable(), (Throwable)null);
         }

         SecurityLogger.logNoTrustedCAsLoaded();
      }

      if (trustedCAs != null) {
         sslContext.addTrustedCA(trustedCAs);
      }

      SSLTrustValidator validator = sslContext.getTrustManager();
      validator.setPeerCertsRequired(clientCertEnforced);
      validator.setAllowOverride(false);
      sslContext.setTrustManager(validator);
      int exportLifeSpan = sslMBean.getExportKeyLifespan();
      sslContext.setExportRefreshCount(exportLifeSpan);
      T3SrvrLogger.logExportableKeyMaxLifespan(exportLifeSpan);
      return sslContext;
   }

   private X509Certificate[] getServerTrustedCAs(SSLContextWrapper sslContext) {
      if (this.usePerServerKeyStores()) {
         KeyStoreInfo[] ksInfo = (new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance())).getTrustKeyStores();
         return getTrustedCAs(ksInfo);
      } else {
         return this.getOldConfigServerTrustedCAs(sslContext);
      }
   }

   private boolean usePerServerKeyStores() {
      String location = this.runtimeAccess.getServer().getSSL().getIdentityAndTrustLocations();
      return "KeyStores".equals(location);
   }

   private static X509Certificate findDemoCert(X509Certificate[] certs) {
      if (certs != null) {
         for(int i = 0; i < certs.length; ++i) {
            if (isDemoCertificate(certs[i])) {
               return certs[i];
            }
         }
      }

      return null;
   }

   private static boolean isDemoCertificate(X509Certificate cert) {
      String DEMO_CN = "CN=CertGenCA";
      String issuer = cert.getIssuerDN().getName();
      int index = issuer.lastIndexOf("CN=CertGenCA");
      return index >= 0 && (index + "CN=CertGenCA".length() >= issuer.length() || !Character.isLetter(issuer.charAt(index + "CN=CertGenCA".length())));
   }

   private void checkIdentity(SSLContextWrapper sslCtx, SSLIdentity identity) throws ConfigurationException {
      X509Certificate[] serverCerts = identity.certChain;
      PrivateKey serverKey = identity.key;
      String cert;
      if (serverCerts != null && serverCerts.length != 0) {
         cert = null;

         try {
            for(int i = 0; i < serverCerts.length; ++i) {
               X509Certificate cert = serverCerts[i];
               cert.checkValidity();
               if (i + 1 < serverCerts.length) {
                  cert.verify(serverCerts[i + 1].getPublicKey());
               } else if (cert.getIssuerDN().equals(cert.getSubjectDN())) {
                  cert.verify(cert.getPublicKey());
               }

               long daysLeft = (cert.getNotAfter().getTime() - System.currentTimeMillis()) / 86400000L;
               if (daysLeft <= 30L) {
                  T3SrvrLogger.logCertificateExpiresSoon(daysLeft, cert.toString());
               }
            }
         } catch (CertificateExpiredException var9) {
            fail(SecurityLogger.logIdentityCertificateExpiredLoggable(cert.toString()), var9);
         } catch (CertificateNotYetValidException var10) {
            fail(SecurityLogger.logIdentityCertificateNotYetValidLoggable(cert.toString()), var10);
         } catch (SignatureException var11) {
            fail(SecurityLogger.logIdentityCertificateNotValidLoggable(cert.toString()), var11);
         } catch (Exception var12) {
            fail(SecurityLogger.logUnableToVerifyIdentityCertificateLoggable(cert.toString()), var12);
         }

         if (this.runtimeAccess.getDomain().isProductionModeEnabled()) {
            X509Certificate demoCert = findDemoCert(serverCerts);
            if (demoCert != null) {
               SecurityLogger.logDemoIdentityCertificateUsed(demoCert.toString());
            }
         }

      } else {
         cert = T3SrvrLogger.logNoCertificatesSpecified();
         throw new ConfigurationException((new Loggable(cert, (Object[])null)).getMessageText());
      }
   }

   private static Collection getTrustedCAs(String ksFileName, String ksType, char[] password) {
      boolean debug = SSLSetup.isDebugEnabled(3);
      String keyStoreSource;
      if (WLSKeyStoreFactory.isFileBasedKeyStore(ksType)) {
         if (null != ksFileName) {
            File ksFile = new File(ksFileName);
            keyStoreSource = ksFile.getAbsolutePath();
         } else {
            keyStoreSource = ksFileName;
         }
      } else {
         keyStoreSource = ksFileName;
      }

      if (keyStoreSource != null) {
         SecurityLogger.logLoadTrustedCAsFromKeyStore(keyStoreSource, ksType);
         KeyStore ks = WLSKeyStoreFactory.getKeyStoreInstance(kernelId, ksType, keyStoreSource, password);
         if (ks != null) {
            try {
               Collection caCerts = SSLCertUtility.getX509Certificates(ks);
               if (debug) {
                  SSLSetup.info("SSLContextManager: loaded " + caCerts.size() + " trusted CAs from " + keyStoreSource);
                  debugCerts(caCerts);
               }

               return caCerts;
            } catch (KeyStoreException var7) {
               SecurityLogger.logKeyStoreException(ksFileName, ManagementService.getRuntimeAccess(kernelId).getServer().getName());
            }
         } else {
            SecurityLogger.logTrustedCAFromKeyStoreLoadFailed(keyStoreSource, ksType);
         }
      } else {
         SecurityLogger.logTrustedCAKeyStoreNotFound(ksFileName, ManagementService.getRuntimeAccess(kernelId).getServer().getName());
      }

      return null;
   }

   private SSLIdentity getServerSSLIdentity(NetworkAccessPointMBean channel, SSLContextWrapper context, boolean forOutboundSSL) throws ConfigurationException {
      if (this.debug) {
         SSLSetup.info("SSLContextManager: loading server SSL identity, forOutboundSSL=" + forOutboundSSL);
      }

      if (!this.usePerServerKeyStores()) {
         return this.getOldConfigServerSSLIdentity(context);
      } else {
         KeyStoreConfigurationHelper ksHelper = new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance(), channel);
         KeyStoreInfo ksInfo = ksHelper.getIdentityKeyStore();
         if (ksInfo == null) {
            fail(SecurityLogger.logInvalidServerSSLConfigurationLoggable(this.getServerName()), (Throwable)null);
         }

         String ksType = ksInfo.getType();
         char[] ksPwd = ksInfo.getPassPhrase();
         String keyStoreSource;
         if (WLSKeyStoreFactory.isFileBasedKeyStore(ksType)) {
            File ksFile = this.findFile(ksInfo.getFileName());
            if (ksFile == null) {
               fail(SecurityLogger.logIdentityKeyStoreFileNotFoundLoggable(this.getServerName(), ksInfo.getFileName()), (Throwable)null);
            }

            keyStoreSource = ksFile.getAbsolutePath();
         } else {
            keyStoreSource = ksInfo.getFileName();
         }

         String alias;
         char[] keyPwd;
         if (forOutboundSSL) {
            alias = ksHelper.getOutboundPrivateKeyAlias();
            keyPwd = ksHelper.getOutboundPrivateKeyPassPhrase();
         } else {
            alias = ksHelper.getIdentityAlias();
            keyPwd = ksHelper.getIdentityPrivateKeyPassPhrase();
         }

         if (this.debug) {
            SSLSetup.info("forOutboundSSL=" + forOutboundSSL + ", resolved alias=" + alias);
         }

         if (alias == null) {
            fail(SecurityLogger.logIdentityKeyStoreAliasNotSpecifiedLoggable(this.getServerName()), (Throwable)null);
         }

         SSLIdentityKey identityKey = new SSLIdentityKey(keyStoreSource, ksType, alias);
         SSLIdentity identity = (SSLIdentity)sslIdentities.get(identityKey);
         if (identity != null && identity.verify(ksPwd, keyPwd)) {
            if (this.debug) {
               SSLSetup.info("Reusing cached identity certs for keystore " + keyStoreSource + ", and alias " + alias);
            }

            return identity;
         } else {
            SecurityLogger.logLoadIdentityCertificateFromKeyStore(keyStoreSource, ksType, alias);
            KeyStore ks = WLSKeyStoreFactory.getKeyStoreInstance(kernelId, ksType, keyStoreSource, ksPwd);
            if (ks == null) {
               fail(SecurityLogger.logIdentityKeyStoreLoadFailedLoggable(this.getServerName(), keyStoreSource, ksType), (Throwable)null);
            }

            if (keyPwd == null) {
               SecurityLogger.logSSLDidNotFindPrivateKeyPassPhrase(this.getServerName(), this.getRealmName());
            }

            Key key = null;
            Certificate[] cert = null;

            try {
               key = ks.getKey(alias, keyPwd);
               cert = ks.getCertificateChain(alias);
            } catch (Exception var18) {
               fail(SecurityLogger.logFailedReadingIdentityEntryLoggable(this.getServerName(), keyStoreSource, alias), var18);
            }

            if (!(key instanceof PrivateKey) || !(cert instanceof Certificate[])) {
               fail(SecurityLogger.logIdentityEntryNotFoundUnderAliasLoggable(this.getServerName(), keyStoreSource, alias), (Throwable)null);
            }

            X509Certificate[] x509Cert = new X509Certificate[cert.length];

            for(int i = 0; i < x509Cert.length; ++i) {
               x509Cert[i] = (X509Certificate)cert[i];
            }

            if (this.debug) {
               SSLSetup.info("Loaded public identity certificate chain:");
               debugCerts(x509Cert);
            }

            identity = new SSLIdentity((PrivateKey)key, x509Cert, ksPwd, keyPwd, ksType);
            sslIdentities.put(identityKey, identity);
            return identity;
         }
      }
   }

   private static void fail(Loggable msg, Throwable t) throws ConfigurationException {
      msg.log();
      ConfigurationException ex = new ConfigurationException(msg.getMessageText());
      if (t != null) {
         ex.initCause(t);
      }

      throw ex;
   }

   private File findFile(String fileName) {
      File file = null;
      if (fileName != null) {
         file = new File(fileName);
         if (!file.exists()) {
            file = new File(this.runtimeAccess.getServer().getRootDirectory(), fileName);
            if (!file.exists()) {
               file = null;
            }
         }
      }

      return file;
   }

   private final String getServerName() {
      return this.runtimeAccess.getServer().getName();
   }

   private final String getRealmName() {
      RealmMBean realmMB = this.runtimeAccess.getDomain().getSecurityConfiguration().getDefaultRealm();
      return realmMB != null ? realmMB.getName() : SecurityServiceManager.getContextSensitiveRealmName();
   }

   private SSLIdentity getOldConfigServerSSLIdentity(SSLContextWrapper sslContext) throws ConfigurationException {
      SSLMBean sslMBean = this.runtimeAccess.getServer().getSSL();
      PrivateKey key = null;
      String keyFileName;
      if (key == null) {
         if (this.debug) {
            SSLSetup.info("Assuming 6.x file based configuration for SSL Server Identity");
         }

         keyFileName = sslMBean.getServerKeyFileName();
         File keyFile = this.findFile(keyFileName);
         if (keyFile == null) {
            SecurityLogger.logSSLKeyFileNameError(this.getServerName());
            throw new ConfigurationException(SecurityLogger.getCanNotFindPrivateKeyFile(this.getServerName(), keyFileName));
         }

         FileInputStream keyStream = null;

         try {
            keyStream = new FileInputStream(keyFile);
            String timestamp = ManagementService.getPropertyService(kernelId).getTimestamp3();
            if (keyFilePwd == null) {
               if (timestamp == null) {
                  timestamp = sslMBean.getServerPrivateKeyPassPhrase();
               }

               keyFilePwd = timestamp == null ? new char[1] : timestamp.toCharArray();
               ManagementService.getPropertyService(kernelId).updateTimestamp3();
            }

            key = sslContext.inputPrivateKey(keyStream, keyFilePwd);
            SecurityLogger.logFoundPrivateKeyInSSLConfig(this.getServerName());
         } catch (IOException var34) {
            throw new ConfigurationException(SecurityLogger.getCanNotPrivateKeyFile(keyFile.getAbsolutePath(), var34.getMessage()));
         } catch (KeyManagementException var35) {
            CommandLine c = CommandLine.getCommandLine();
            throw new ConfigurationException(SecurityLogger.getCanNotReadPrivateKeyFile(keyFile.getAbsolutePath(), c.getAdminPKPasswordProp(), var35.getMessage()));
         } finally {
            try {
               if (keyStream != null) {
                  keyStream.close();
               }
            } catch (Exception var30) {
            }

         }
      }

      keyFileName = null;
      String certFileName = sslMBean.getServerCertificateFileName();
      File certFile = this.findFile(certFileName);
      if (certFile == null) {
         SecurityLogger.logSSLCertificateFileNameError(this.getServerName(), "ServerCertificateFileName");
         throw new ConfigurationException(SecurityLogger.getUnableToFindServerCertFile(this.getServerName(), certFileName));
      } else {
         FileInputStream certStream = null;

         X509Certificate[] certChain;
         try {
            certStream = new FileInputStream(certFile);
            certChain = SSLCertUtility.inputCertificateChain(sslContext, certStream);
            ValidateCertChain.validateServerCertChain(certChain);
         } catch (Exception var32) {
            throw (ConfigurationException)(new ConfigurationException(var32.getMessage())).initCause(var32);
         } finally {
            try {
               if (certStream != null) {
                  certStream.close();
               }
            } catch (Exception var31) {
            }

         }

         if (this.debug) {
            SSLSetup.info("Loaded public identity certificate chain using old SSL configuration:");
            debugCerts(certChain);
         }

         return new SSLIdentity(key, certChain);
      }
   }

   private X509Certificate[] getOldConfigServerTrustedCAs(SSLContextWrapper sslContext) {
      X509Certificate[] trustedCAList = null;
      String keystoreFile = CommandLine.getCommandLine().getKeyStoreFileName();
      String caNumStr;
      if (keystoreFile != null) {
         if (this.debug) {
            SSLSetup.info("SSLContextManager, loading trusted CAs from cmd line keystore: " + keystoreFile);
         }

         File ksFile = this.findFile(keystoreFile);
         Collection caCerts = getTrustedCAs(ksFile == null ? keystoreFile : ksFile.getAbsolutePath(), "jks", (char[])null);
         if (caCerts != null) {
            trustedCAList = (X509Certificate[])((X509Certificate[])caCerts.toArray(new X509Certificate[caCerts.size()]));
         }

         caNumStr = trustedCAList == null ? "0" : String.valueOf(trustedCAList.length);
         SecurityLogger.logTrustedCAsLoadedFromCmdLnKeyStore(caNumStr, keystoreFile);
         if (this.debug) {
            debugCerts(trustedCAList);
         }

         return trustedCAList;
      } else {
         String trustedCAFileName = this.runtimeAccess.getServer().getSSL().getTrustedCAFileName();
         if (trustedCAFileName != null) {
            if (this.debug) {
               SSLSetup.info("SSLContextManager, loading trusted CAs from TrustedCAFile: " + trustedCAFileName);
            }

            File trustedCAFile = this.findFile(trustedCAFileName);
            if (trustedCAFile != null) {
               try {
                  FileInputStream caCertStream = new FileInputStream(trustedCAFile);
                  trustedCAList = SSLCertUtility.inputCertificateChain(sslContext, caCertStream);
                  caCertStream.close();
               } catch (FileNotFoundException var8) {
                  SecurityLogger.logTrustedCAFileNotFound(trustedCAFileName, this.getServerName());
                  if (this.debug) {
                     SSLSetup.info("Cannot find the specified trusted CA file " + trustedCAFileName);
                  }
               } catch (IOException var9) {
                  SecurityLogger.logCannotAccessTrustedCAFile(trustedCAFileName, this.getServerName());
                  if (this.debug) {
                     SSLSetup.info(var9, "The Server was not able to read trusted CA file " + trustedCAFileName);
                  }
               } catch (KeyManagementException var10) {
                  SecurityLogger.logInvalidTrustedCAFileFormat(trustedCAFileName, this.getServerName());
                  if (this.debug) {
                     SSLSetup.info(var10, "The Server was not able to read certificate from trusted CA file " + trustedCAFileName);
                  }
               }

               caNumStr = trustedCAList == null ? "0" : String.valueOf(trustedCAList.length);
               SecurityLogger.logTrustedCAsLoadedFromTrustedCAFile(caNumStr);
               if (this.debug) {
                  debugCerts(trustedCAList);
               }

               return trustedCAList;
            }

            SecurityLogger.logTrustedCAFileNotFound(trustedCAFileName, this.getServerName());
            if (this.debug) {
               SSLSetup.info("Cannot find the specified trusted CA file " + trustedCAFileName);
            }
         }

         String defFile = BootStrap.getWebLogicHome() + File.separator + "lib" + File.separator + "cacerts";
         if (this.debug) {
            SSLSetup.info("SSLContextManager, loading trusted CAs from default key store: " + defFile);
         }

         Collection caCerts = getTrustedCAs(defFile, "jks", (char[])null);
         if (caCerts != null) {
            trustedCAList = (X509Certificate[])((X509Certificate[])caCerts.toArray(new X509Certificate[caCerts.size()]));
         }

         String caNumStr = trustedCAList == null ? "0" : String.valueOf(trustedCAList.length);
         SecurityLogger.logTrustedCAsLoadedFromDefaultKeyStore(caNumStr, defFile);
         if (this.debug) {
            debugCerts(trustedCAList);
         }

         return trustedCAList;
      }
   }

   private static void debugCerts(Collection certs) {
      Iterator certsIterator = certs.iterator();

      while(certsIterator.hasNext()) {
         SSLSetup.info(toString((X509Certificate)certsIterator.next()));
      }

   }

   private static void debugCerts(X509Certificate[] certs) {
      for(int i = 0; i < certs.length; ++i) {
         SSLSetup.info(toString(certs[i]));
      }

   }

   private static String toString(X509Certificate cert) {
      return "Subject: " + cert.getSubjectDN() + "; Issuer: " + cert.getIssuerDN();
   }

   private static class SSLIdentityKey {
      public final String ksSource;
      public final String ksType;
      public final String alias;

      public SSLIdentityKey(String ksSource, String ksType, String alias) {
         assert ksSource != null : "null keystore source";

         assert ksType != null : "null keystore type";

         assert alias != null : "null key alias";

         this.ksSource = ksSource;
         this.ksType = ksType.toUpperCase(Locale.ENGLISH);
         this.alias = alias;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof SSLIdentityKey)) {
            return false;
         } else {
            SSLIdentityKey key = (SSLIdentityKey)obj;
            return this.alias.equals(key.alias) && this.ksType.equals(key.ksType) && this.ksSource.equals(key.ksSource);
         }
      }

      public int hashCode() {
         int h = this.ksSource.hashCode();
         h = 31 * h + this.ksType.hashCode();
         h = 31 * h + this.alias.hashCode();
         return h;
      }
   }

   private static class SSLIdentity {
      public final PrivateKey key;
      public final X509Certificate[] certChain;
      private final char[] ksPwd;
      private final char[] keyPwd;
      private final String ksType;

      public SSLIdentity(PrivateKey key, X509Certificate[] certChain) {
         this(key, certChain, (char[])null, (char[])null);
      }

      public SSLIdentity(PrivateKey key, X509Certificate[] certChain, char[] ksPwd, char[] keyPwd) {
         this(key, certChain, ksPwd, keyPwd, (String)null);
      }

      public SSLIdentity(PrivateKey key, X509Certificate[] certChain, char[] ksPwd, char[] keyPwd, String ksType) {
         this.key = key;
         this.certChain = certChain;
         this.ksPwd = ksPwd;
         this.keyPwd = keyPwd;
         this.ksType = ksType;
      }

      public boolean verify(char[] ksPwd, char[] keyPwd) {
         return Arrays.equals(this.ksPwd, ksPwd) && Arrays.equals(this.keyPwd, keyPwd);
      }
   }
}
