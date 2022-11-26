package weblogic.net.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.AccessController;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.UnknownProtocolException;
import weblogic.security.SecurityLogger;
import weblogic.security.SSL.HostnameVerifier;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.SSLSocketFactory;
import weblogic.security.SSL.TrustManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.Security;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.KeyStoreConfiguration;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLContextManager;

public class HttpsURLConnection extends HttpURLConnection {
   private static SSLSocketFactory defaultSSLSocketFactory;
   private SSLSocketFactory sslSocketFactory;
   private SSLClientInfo sslinfo;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public HttpsURLConnection(URL u, SSLClientInfo sslI) {
      super(u);
      this.sslinfo = sslI != null ? sslI : (SSLClientInfo)Security.getThreadSSLClientInfo();
   }

   public HttpsURLConnection(URL u) {
      this(u, (Proxy)null);
   }

   public HttpsURLConnection(URL u, Proxy p) {
      this(u, (SSLClientInfo)null);
      this.instProxy = p == null ? Proxy.NO_PROXY : p;
   }

   /** @deprecated */
   @Deprecated
   public void loadLocalIdentity(InputStream[] stream) {
      this.setSSLClientCertificate(stream);
   }

   private SSLClientInfo getSSLInfo() {
      if (this.sslinfo == null) {
         this.sslinfo = new SSLClientInfo();
      }

      return this.sslinfo;
   }

   /** @deprecated */
   @Deprecated
   public void loadLocalIdentity(InputStream certStream, InputStream keyStream, char[] password) {
      this.getSSLInfo().loadLocalIdentity(certStream, keyStream, password);
   }

   public void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      this.getSSLInfo().loadLocalIdentity(certs, privateKey);
   }

   /** @deprecated */
   @Deprecated
   public void setSSLClientCertificate(InputStream[] certs) {
      if (certs != null) {
         this.getSSLInfo().setSSLClientCertificate(certs);
         this.updateFactoryClientInfo();
      }

   }

   public void setTrustManager(TrustManager tm) {
      this.getSSLInfo().setTrustManager(tm);
      this.updateFactoryClientInfo();
   }

   public TrustManager getTrustManager() {
      return this.sslinfo != null ? this.sslinfo.getTrustManager() : ((SSLClientInfo)Security.getThreadSSLClientInfo()).getTrustManager();
   }

   public void setHostnameVerifier(HostnameVerifier hv) {
      this.getSSLInfo().setHostnameVerifier(hv);
      this.updateFactoryClientInfo();
   }

   public HostnameVerifier getHostnameVerifier() {
      return this.sslinfo != null ? this.sslinfo.getHostnameVerifier() : ((SSLClientInfo)Security.getThreadSSLClientInfo()).getHostnameVerifier();
   }

   public void setSSLSocketFactory(SSLSocketFactory sslSF) {
      this.sslSocketFactory = sslSF;
   }

   public SSLSocketFactory getSSLSocketFactory() {
      return this.sslSocketFactory != null ? this.sslSocketFactory : getDefaultSSLSocketFactory();
   }

   public static void setDefaultSSLSocketFactory(javax.net.ssl.SSLSocketFactory factory) {
      defaultSSLSocketFactory = new WLSSSLSocketFactoryAdapter(factory);
   }

   public static void setDefaultSSLSocketFactory(SSLSocketFactory factory) {
      defaultSSLSocketFactory = factory;
   }

   public static SSLSocketFactory getDefaultSSLSocketFactory() {
      return defaultSSLSocketFactory != null ? defaultSSLSocketFactory : (SSLSocketFactory)SSLSocketFactory.getDefault();
   }

   public String getCipherSuite() {
      SSLSession sslSession = this.getSSLSession();
      return sslSession != null ? sslSession.getCipherSuite() : null;
   }

   public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
      SSLSession sslSession = this.getSSLSession();
      return sslSession != null ? sslSession.getPeerCertificates() : null;
   }

   public SSLSession getSSLSession() {
      return ((HttpsClient)this.http).getSSLSession();
   }

   public void connect() throws IOException {
      if (!this.connected) {
         this.checkClientSSLInfo();

         try {
            this.http = HttpsClient.New(this.url, this.instProxy, this.getSocketFactory(), this.sslinfo, this.sslSocketFactory != null ? this.sslSocketFactory : defaultSSLSocketFactory, this.useHttp11, this.getConnectTimeout(), this.getReadTimeout(), this.requests.findValue("Proxy-Authorization"), this.ignoreSystemNonProxyHosts, this.ignoreProxy);
         } catch (SocketTimeoutException var2) {
            this.rememberedException = var2;
            throw var2;
         }

         this.http.setConnection(this);
         this.connected = true;
         HttpsClient https = (HttpsClient)this.http;
         if (this.sslSocketFactory == null) {
            this.setSSLSocketFactory(https.getSSLSocketFactory());
         }

      }
   }

   private void checkClientSSLInfo() {
      if (KernelStatus.isServer()) {
         if (this.sslinfo == null || !this.sslinfo.isLocalIdentitySet()) {
            RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
            if (ra == null) {
               if (this.sslinfo != null) {
                  this.loadIdentityToSSLClientInfo(this.sslinfo);
               }

            } else {
               SSLMBean sslbean = NETEnvironment.getNETEnvironment().getSSLMBean(kernelId);
               if (sslbean != null) {
                  if (sslbean.isUseServerCerts() || sslbean.isUseClientCertForOutbound()) {
                     if (debug) {
                        SecurityLogger.logUsingServerCerts();
                     }

                     try {
                        ServerChannel channel = ProtocolManager.findProtocol(this.getProtocol()).getHandler().getDefaultServerChannel();
                        SSLClientInfo newsslinfo = SSLContextManager.getChannelSSLClientInfo(channel, kernelId);
                        this.sslinfo = newsslinfo;
                     } catch (ConfigurationException var4) {
                        SecurityLogger.logCantUseServerCerts();
                     } catch (CertificateException var5) {
                        SecurityLogger.logCantUseServerCerts();
                     } catch (UnknownProtocolException var6) {
                        SecurityLogger.logCantUseServerCerts();
                     } catch (Exception var7) {
                        SecurityLogger.logCantUseServerCerts();
                     }

                  }
               }
            }
         }
      }
   }

   protected String getProtocol() {
      return "https";
   }

   HttpClient getHttp() {
      return this.http;
   }

   protected HttpClient getHttpClient() throws IOException {
      HttpClient h = HttpsClient.New(this.url, this.instProxy, this.getSocketFactory(), this.sslinfo, this.sslSocketFactory != null ? this.sslSocketFactory : defaultSSLSocketFactory, this.useHttp11, this.getConnectTimeout(), this.getReadTimeout(), false, this.requests.findValue("Proxy-Authorization"), this.ignoreSystemNonProxyHosts, this.ignoreProxy);
      h.setConnection(this);
      this.connected = true;
      return h;
   }

   public SSLClientInfo getSSLClientInfo() {
      return this.sslinfo;
   }

   private void updateFactoryClientInfo() {
      if (this.sslSocketFactory != null) {
         this.sslSocketFactory.setSSLClientInfo(this.sslinfo);
      }

   }

   private void loadIdentityToSSLClientInfo(SSLClientInfo sslinfo) {
      String type = null;
      String fileName = null;

      try {
         KeyStoreConfig config = new KeyStoreConfig();
         fileName = config.getCustomIdentityKeyStoreFileName();
         if (fileName == null) {
            return;
         }

         type = config.getCustomIdentityKeyStoreType();
         type = type != null && type.length() > 0 ? type : KeyStore.getDefaultType();
         KeyStore keyStore = KeyStore.getInstance(type);
         InputStream in = new FileInputStream(fileName);
         String passphrase = config.getCustomIdentityKeyStorePassPhrase();
         char[] passphraseArray = passphrase != null && passphrase.length() > 0 ? passphrase.toCharArray() : null;

         try {
            keyStore.load(in, passphraseArray);
         } finally {
            try {
               in.close();
            } catch (IOException var24) {
            }

         }

         String alias = config.getCustomIdentityAlias();
         String pkpassphrase = config.getCustomIdentityPrivateKeyPassPhrase();
         char[] pkArray = pkpassphrase != null && pkpassphrase.length() > 0 ? pkpassphrase.toCharArray() : null;
         PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, pkArray);
         if (privateKey == null) {
            SecurityLogger.logCommandLineKeyStoreConfigError(fileName, type, alias);
            return;
         }

         Certificate[] certs = keyStore.getCertificateChain(alias);
         X509Certificate[] cert = SSLCertUtility.toJavaX5092(certs);
         sslinfo.loadLocalIdentity(cert, privateKey);
      } catch (KeyStoreException var26) {
         SecurityLogger.logLoadKeyStoreKeyStoreException(type, var26.toString());
      } catch (NoSuchAlgorithmException var27) {
         SecurityLogger.logLoadKeyStoreNoSuchAlgorithmException(fileName, type, var27.toString());
      } catch (UnrecoverableKeyException var28) {
         SecurityLogger.logLoadKeyStoreUnrecoverableKeyException(fileName, type, var28.toString());
      } catch (CertificateException var29) {
         SecurityLogger.logStoreKeyStoreCertificateException(fileName, type, var29.toString());
      } catch (IOException var30) {
         SecurityLogger.logStoreKeyStoreIOException(fileName, type, var30.toString());
      } catch (Exception var31) {
         SecurityLogger.logCommandLineKeyStoreConfigException(fileName, type, var31);
      }

   }

   static class WLSSSLSocketFactoryAdapter extends SSLSocketFactory {
      public WLSSSLSocketFactoryAdapter(javax.net.ssl.SSLSocketFactory factory) {
         super(factory);
      }
   }

   private static final class KeyStoreConfig implements KeyStoreConfiguration {
      private static String rootDir = getDomainDir();
      private ClearOrEncryptedService ces;

      private KeyStoreConfig() {
         try {
            this.ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService(rootDir));
         } catch (Exception var2) {
            var2.printStackTrace();
         }

      }

      private static String getDomainDir() {
         String rootDirNonCanonical = SecurityHelper.getSystemProperty("weblogic.RootDirectory", ".");

         try {
            return (new File(rootDirNonCanonical)).getCanonicalPath();
         } catch (IOException var2) {
            throw new AssertionError(var2);
         }
      }

      public String getKeyStores() {
         return "";
      }

      public String getCustomIdentityKeyStoreFileName() {
         return this.getAbsolutePath(SecurityHelper.getSystemProperty("weblogic.security.CustomIdentityKeyStoreFileName"));
      }

      public String getCustomIdentityKeyStoreType() {
         return this.emptyToNull(SecurityHelper.getSystemProperty("weblogic.security.CustomIdentityKeyStoreType"));
      }

      public String getCustomIdentityKeyStorePassPhrase() {
         return this.emptyToNull(this.decrypt(SecurityHelper.getSystemProperty("weblogic.security.CustomIdentityKeyStorePassPhrase")));
      }

      public String getCustomIdentityAlias() {
         return this.emptyToNull(SecurityHelper.getSystemProperty("weblogic.security.CustomIdentityKeyStoreAlias"));
      }

      public String getCustomIdentityPrivateKeyPassPhrase() {
         return this.emptyToNull(this.decrypt(SecurityHelper.getSystemProperty("weblogic.security.CustomIdentityPrivateKeyPassPhrase")));
      }

      public String getCustomTrustKeyStoreFileName() {
         return SecurityHelper.getSystemProperty("weblogic.security.CustomTrustKeyStoreFileName");
      }

      public String getCustomTrustKeyStoreType() {
         return SecurityHelper.getSystemProperty("weblogic.security.CustomTrustKeyStoreType");
      }

      public String getCustomTrustKeyStorePassPhrase() {
         return this.decrypt(SecurityHelper.getSystemProperty("weblogic.security.CustomTrustKeyStorePassPhrase"));
      }

      public String getJavaStandardTrustKeyStorePassPhrase() {
         return null;
      }

      public String getOutboundPrivateKeyAlias() {
         return null;
      }

      public String getOutboundPrivateKeyPassPhrase() {
         return null;
      }

      private String decrypt(String value) {
         if (value != null && this.ces.isEncrypted(value)) {
            value = this.ces.decrypt(value);
         }

         return value;
      }

      private String getAbsolutePath(String path) {
         return this.emptyToNull(path) == null ? null : (new File(path)).getAbsolutePath();
      }

      private String emptyToNull(String val) {
         return val != null && val.length() < 1 ? null : val;
      }

      // $FF: synthetic method
      KeyStoreConfig(Object x0) {
         this();
      }
   }
}
