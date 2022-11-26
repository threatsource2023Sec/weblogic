package weblogic.security.utils;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.TLSMBean;
import weblogic.protocol.ServerChannel;
import weblogic.security.SecurityLogger;
import weblogic.security.SSL.WeblogicSSLEngine;

public class SSLContextWrapper {
   private static final String GET_ENABLENULLCIPHER_METHOD = "enableUnencryptedNullCipher";
   private static final String GET_ISNULLCIPHER_METHOD = "isUnencryptedNullCipherEnabled";
   private static final String GET_SSL_NIO_SSF_METHOD = "getSSLNioServerSocketFactory";
   private static final String GET_SSL_NIO_SOCK_METHOD = "getSSLNioSocketFactory";
   private boolean b1stCall_EnableNullCipher = true;
   private Method mtd_EnableNullCipher = null;
   private boolean b1stCall_isEnableNullCipher = true;
   private Method mtd_isEnableNullCipher = null;
   private boolean b1stCall_getNioServerFact = true;
   private Method mtd_getNioServerFact = null;
   private boolean b1stCall_getNioSockFact = true;
   private Method mtd_getNioSockFact = null;
   private final SSLContextDelegate sslContext = SSLSetup.getSSLDelegateInstance();
   private ConcurrentMap unsupportedCerts;
   private static final int LOG_PERIOD = 300000;

   public static final SSLContextWrapper getInstance() {
      return new SSLContextWrapper((ServerChannel)null, false);
   }

   public static final SSLContextWrapper getImportInstance() {
      return new SSLContextWrapper((ServerChannel)null, true);
   }

   public static final SSLContextWrapper getInstance(ServerChannel serverChannel) {
      return new SSLContextWrapper(serverChannel, false);
   }

   static final SSLContextWrapper getInstance(TLSMBean tlsmBean) {
      return new SSLContextWrapper(tlsmBean);
   }

   private SSLContextWrapper(ServerChannel serverChannel, boolean importOnly) {
      if (!SSLSetup.isJSSEContextDelegate(this.sslContext)) {
         this.sslContext.setProtocolVersion(SSLSetup.getLegacyProtocolVersion());
      } else if (this.sslContext instanceof SSLContextDelegate2) {
         if (serverChannel != null) {
            ((SSLContextDelegate2)this.sslContext).setMinimumTLSProtocolVersion(serverChannel.getMinimumTLSProtocolVersion());
            ((SSLContextDelegate2)this.sslContext).setSSLv2HelloEnabled(serverChannel.isSSLv2HelloEnabled());
         } else if (!Kernel.isServer() && !importOnly) {
            SSLMBean sslMBean = Kernel.getConfig().getSSL();
            ((SSLContextDelegate2)this.sslContext).setMinimumTLSProtocolVersion(sslMBean.getMinimumTLSProtocolVersion());
            ((SSLContextDelegate2)this.sslContext).setSSLv2HelloEnabled(sslMBean.isSSLv2HelloEnabled());
         }
      }

      if (!importOnly) {
         this.sslContext.setTrustManager(new SSLTrustValidator(serverChannel));
         this.sslContext.setHostnameVerifier(new SSLWLSHostnameVerifier(serverChannel));
      }

      this.sslContext.enforceConstraints(SSLSetup.getEnforceConstraints());
      this.unsupportedCerts = new ConcurrentHashMap();
   }

   private SSLContextWrapper(TLSMBean tlsmBean) {
      if (!SSLSetup.isJSSEContextDelegate(this.sslContext)) {
         this.sslContext.setProtocolVersion(SSLSetup.getLegacyProtocolVersion());
      } else if (this.sslContext instanceof SSLContextDelegate2) {
         if (tlsmBean != null) {
            ((SSLContextDelegate2)this.sslContext).setMinimumTLSProtocolVersion(tlsmBean.getMinimumTLSProtocolVersion());
            ((SSLContextDelegate2)this.sslContext).setSSLv2HelloEnabled(tlsmBean.isSSLv2HelloEnabled());
         } else if (!Kernel.isServer()) {
            SSLMBean sslMBean = Kernel.getConfig().getSSL();
            ((SSLContextDelegate2)this.sslContext).setMinimumTLSProtocolVersion(sslMBean.getMinimumTLSProtocolVersion());
            ((SSLContextDelegate2)this.sslContext).setSSLv2HelloEnabled(sslMBean.isSSLv2HelloEnabled());
         }
      }

      this.sslContext.setTrustManager(new TLSTrustValidator(tlsmBean));
      this.sslContext.setHostnameVerifier(new SSLWLSHostnameVerifier(tlsmBean));
      this.sslContext.enforceConstraints(SSLSetup.getEnforceConstraints());
      this.unsupportedCerts = new ConcurrentHashMap();
   }

   private void logCertError(X509Certificate cert, Exception e) {
      if (cert != null) {
         boolean toLog = false;
         boolean suppressUnsupportedCAs = Boolean.getBoolean("weblogic.security.suppressUnsupportedCANotice");
         String certStr = cert.getIssuerX500Principal().getName() + cert.getSerialNumber().toString();
         long currentTime = System.currentTimeMillis();
         if (this.unsupportedCerts.containsKey(certStr)) {
            Long lastLogTimeStamp = (Long)this.unsupportedCerts.get(certStr);
            if (currentTime - lastLogTimeStamp >= 300000L) {
               toLog = true;
            }
         } else {
            toLog = true;
         }

         if (toLog && !suppressUnsupportedCAs) {
            this.unsupportedCerts.put(certStr, new Long(currentTime));
            SecurityLogger.logFailedToAddaCA2Server(cert.getSubjectX500Principal().getName(), e.getMessage());
         }
      }

   }

   public void addTrustedCA(X509Certificate trustedCert) throws CertificateException {
      this.sslContext.addTrustedCA(trustedCert);
   }

   public void addTrustedCA(X509Certificate[] trustedCerts) throws CertificateException {
      for(int i = 0; i < trustedCerts.length; ++i) {
         try {
            this.sslContext.addTrustedCA(trustedCerts[i]);
         } catch (CertificateParsingException var4) {
            this.logCertError(trustedCerts[i], var4);
         }
      }

   }

   public X509Certificate[] getTrustedCAs() {
      return this.sslContext.getTrustedCAs();
   }

   public PrivateKey inputPrivateKey(InputStream keyStream, char[] password) throws KeyManagementException {
      return this.sslContext.inputPrivateKey(keyStream, password);
   }

   public X509Certificate[] inputCertChain(InputStream certStream) throws KeyManagementException {
      return this.sslContext.inputCertChain(certStream);
   }

   public void loadLocalIdentity(InputStream identityStream, char[] password) throws KeyManagementException {
      this.sslContext.loadLocalIdentity(identityStream, password);
   }

   public void loadTrustedCerts(InputStream certStream) throws CertificateException, KeyManagementException {
      this.sslContext.loadTrustedCerts(certStream);
   }

   public void addIdentity(X509Certificate[] certChain, PrivateKey privateKey) {
      this.sslContext.addIdentity(certChain, privateKey);
   }

   public void addIdentity(X509Certificate[] certChain, PrivateKey privateKey, String keyStoreType) {
      if (this.sslContext instanceof SSLContextDelegate2) {
         ((SSLContextDelegate2)this.sslContext).addIdentity(certChain, privateKey, keyStoreType);
      } else {
         this.sslContext.addIdentity(certChain, privateKey);
      }

   }

   public boolean doKeysMatch(PublicKey publicKey, PrivateKey privateKey) throws KeyManagementException {
      return this.sslContext.doKeysMatch(publicKey, privateKey);
   }

   public void setExportRefreshCount(int count) {
      this.sslContext.setExportRefreshCount(count);
   }

   public SSLServerSocketFactory getSSLServerSocketFactory() {
      return this.sslContext.getSSLServerSocketFactory();
   }

   public SSLSocketFactory getSSLSocketFactory() {
      return this.sslContext.getSSLSocketFactory();
   }

   public void setTrustManager(SSLTrustValidator truster) {
      this.sslContext.setTrustManager(truster);
   }

   public SSLTrustValidator getTrustManager() {
      return (SSLTrustValidator)this.sslContext.getTrustManager();
   }

   public void setHostnameVerifier(SSLWLSHostnameVerifier verifier) {
      this.sslContext.setHostnameVerifier(verifier);
   }

   public SSLWLSHostnameVerifier getHostnameVerifier() {
      return (SSLWLSHostnameVerifier)this.sslContext.getHostnameVerifier();
   }

   public void enableUnencryptedNullCipher(boolean enable) {
      if (this.b1stCall_EnableNullCipher) {
         Class ctxClass = this.sslContext.getClass();

         try {
            this.mtd_EnableNullCipher = ctxClass.getMethod("enableUnencryptedNullCipher", Boolean.TYPE);
         } catch (NoSuchMethodException var4) {
            SSLSetup.info(var4, "Method enableUnencryptedNullCipher() does not exist for class " + ctxClass.getName());
         }
      }

      String msg;
      try {
         if (this.mtd_EnableNullCipher != null) {
            this.mtd_EnableNullCipher.invoke(this.sslContext, new Boolean(enable));
         }
      } catch (IllegalAccessException var5) {
         msg = "Method enableUnencryptedNullCipher() can not be accessed; detail: " + var5.getMessage();
         if (this.b1stCall_EnableNullCipher) {
            SSLSetup.info(var5, msg);
         }

         throw new RuntimeException(msg, var5);
      } catch (InvocationTargetException var6) {
         msg = "Method enableUnencryptedNullCipher() can not be involked with object " + this.sslContext.toString() + " detail: " + var6.getMessage();
         if (this.b1stCall_EnableNullCipher) {
            SSLSetup.info(var6, msg);
         }

         throw new RuntimeException(msg, var6);
      }

      if (this.b1stCall_EnableNullCipher) {
         this.b1stCall_EnableNullCipher = false;
      }

   }

   public boolean isUnencryptedNullCipherEnabled() {
      Object enable = null;
      if (this.b1stCall_isEnableNullCipher) {
         Class ctxClass = this.sslContext.getClass();

         try {
            this.mtd_isEnableNullCipher = ctxClass.getMethod("isUnencryptedNullCipherEnabled");
         } catch (NoSuchMethodException var4) {
            SSLSetup.info(var4, "Method isUnencryptedNullCipher() does not exist for class " + ctxClass.getName());
         }
      }

      String msg;
      try {
         if (this.mtd_isEnableNullCipher != null) {
            enable = this.mtd_isEnableNullCipher.invoke(this.sslContext);
         }
      } catch (IllegalAccessException var5) {
         msg = "Method isUnencryptedNullCipher() can not be accessed; detail: " + var5.getMessage();
         if (this.b1stCall_isEnableNullCipher) {
            SSLSetup.info(var5, msg);
         }

         throw new RuntimeException(msg, var5);
      } catch (InvocationTargetException var6) {
         msg = "Method isUnencryptedNullCipher() can not be involked with object " + this.sslContext.toString() + " detail: " + var6.getMessage();
         if (this.b1stCall_isEnableNullCipher) {
            SSLSetup.info(var6, msg);
         }

         throw new RuntimeException(msg, var6);
      }

      if (this.b1stCall_isEnableNullCipher) {
         this.b1stCall_isEnableNullCipher = false;
      }

      return enable != null ? (Boolean)enable : false;
   }

   public SSLServerSocketFactory getSSLNioServerSocketFactory() {
      Object ssf = null;
      if (this.b1stCall_getNioServerFact) {
         Class ctxClass = this.sslContext.getClass();

         try {
            this.mtd_getNioServerFact = ctxClass.getMethod("getSSLNioServerSocketFactory");
         } catch (NoSuchMethodException var4) {
            SSLSetup.info(var4, "Method getSSLNioServerSocketFactory() does not exist for class " + ctxClass.getName());
         }
      }

      String msg;
      try {
         if (this.mtd_getNioServerFact != null) {
            ssf = this.mtd_getNioServerFact.invoke(this.sslContext);
         }
      } catch (IllegalAccessException var5) {
         msg = "Method getSSLNioServerSocketFactory() can not be accessed; detail: " + var5.getMessage();
         if (this.b1stCall_getNioServerFact) {
            SSLSetup.info(var5, msg);
         }

         throw new RuntimeException(msg, var5);
      } catch (InvocationTargetException var6) {
         msg = "Method getSSLNioServerSocketFactory() can not be involked with object " + this.sslContext.toString() + " detail: " + var6.getMessage();
         if (this.b1stCall_getNioServerFact) {
            SSLSetup.info(var6, msg);
         }

         throw new RuntimeException(msg, var6);
      }

      if (this.b1stCall_getNioServerFact) {
         this.b1stCall_getNioServerFact = false;
      }

      if (ssf != null) {
         if (this.b1stCall_getNioServerFact) {
            SSLSetup.info("SSL Nio version of SSLServerSocketFactory is created");
         }

         return (SSLServerSocketFactory)ssf;
      } else {
         throw new UnsupportedOperationException("Method of getSSLNioServerSocketFactory() is not supported");
      }
   }

   public SSLSocketFactory getSSLNioSocketFactory() {
      Object ssf = null;
      if (this.b1stCall_getNioSockFact) {
         Class ctxClass = this.sslContext.getClass();

         try {
            this.mtd_getNioSockFact = ctxClass.getMethod("getSSLNioSocketFactory");
         } catch (NoSuchMethodException var4) {
            SSLSetup.info(var4, "Method getSSLNioServerSocketFactory() does not exist for class " + ctxClass.getName());
         }
      }

      String msg;
      try {
         if (this.mtd_getNioSockFact != null) {
            ssf = this.mtd_getNioSockFact.invoke(this.sslContext);
         }
      } catch (IllegalAccessException var5) {
         msg = "Method getSSLNioSocketFactory() can not be accessed; detail: " + var5.getMessage();
         if (this.b1stCall_getNioSockFact) {
            SSLSetup.info(var5, msg);
         }

         throw new RuntimeException(msg, var5);
      } catch (InvocationTargetException var6) {
         msg = "Method getSSLNioSocketFactory() can not be involked with object " + this.sslContext.toString() + " detail: " + var6.getMessage();
         if (this.b1stCall_getNioSockFact) {
            SSLSetup.info(var6, msg);
         }

         throw new RuntimeException(msg, var6);
      }

      if (this.b1stCall_getNioSockFact) {
         this.b1stCall_getNioSockFact = false;
      }

      if (ssf != null) {
         if (this.b1stCall_getNioServerFact) {
            SSLSetup.info("SSL Nio version of SSLSocketFactory is created");
         }

         return (SSLSocketFactory)ssf;
      } else {
         throw new UnsupportedOperationException("Method of getSSLNioSocketFactory() is not supported");
      }
   }

   public WeblogicSSLEngine createSSLEngine() throws SSLException {
      if (this.sslContext instanceof SSLContextDelegate2) {
         SSLContextDelegate2 delegate2 = (SSLContextDelegate2)this.sslContext;
         return delegate2.createSSLEngine();
      } else {
         throw new UnsupportedOperationException("createSSLEngine is not supported by selected SSL implementation.");
      }
   }

   public WeblogicSSLEngine createSSLEngine(String peerHost, int peerPort) throws SSLException {
      if (this.sslContext instanceof SSLContextDelegate2) {
         SSLContextDelegate2 delegate2 = (SSLContextDelegate2)this.sslContext;
         return delegate2.createSSLEngine(peerHost, peerPort);
      } else {
         throw new UnsupportedOperationException("createSSLEngine is not supported by selected SSL implementation.");
      }
   }

   public String[] getDefaultCipherSuites() {
      if (this.sslContext instanceof SSLContextDelegate2) {
         SSLContextDelegate2 delegate2 = (SSLContextDelegate2)this.sslContext;
         return delegate2.getDefaultCipherSuites();
      } else {
         throw new UnsupportedOperationException("getDefaultCipherSuites is not supported by selected SSL implementation.");
      }
   }

   public String[] getSupportedCipherSuites() {
      if (this.sslContext instanceof SSLContextDelegate2) {
         SSLContextDelegate2 delegate2 = (SSLContextDelegate2)this.sslContext;
         return delegate2.getSupportedCipherSuites();
      } else {
         throw new UnsupportedOperationException("getSupportedCipherSuites is not supported by selected SSL implementation.");
      }
   }
}
