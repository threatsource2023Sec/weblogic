package weblogic.nodemanager.server;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.util.SSLProtocolsUtil;

public class SSLContextConfigurator {
   SSLContext sslContext;
   SSLConfig sslConfig;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   public static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public SSLContextConfigurator(SSLConfig sslConfig) throws IOException {
      this.sslConfig = sslConfig;
      this.init();
   }

   private void init() throws IOException {
      try {
         String ssl_protocol = SSLProtocolsUtil.getSSLContextProtocol();
         if (nmLog.isLoggable(Level.FINEST)) {
            nmLog.finest("Expected SSLContext service protocol: " + ssl_protocol);
         }

         this.sslContext = SSLContext.getInstance(ssl_protocol);
         if (nmLog.isLoggable(Level.FINEST) && null != this.sslContext) {
            nmLog.finest("Actual SSLContext service protocol: " + this.sslContext.getProtocol());
         }

      } catch (NoSuchAlgorithmException var2) {
         throw (IOException)(new IOException("Could not instantiate SSLContext")).initCause(var2);
      }
   }

   public SSLContext createSSLContext() throws IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, CertificateException {
      KeyStore identityKs = KeyStore.getInstance(KeyStore.getDefaultType());
      identityKs.load((InputStream)null, (char[])null);
      char[] pwd = null;
      PrivateKey identityPrivateKey = this.sslConfig.getPrivateKey();
      if (null != identityPrivateKey) {
         String alias = Integer.toString(identityPrivateKey.hashCode());
         pwd = alias.toCharArray();
         Certificate[] certChain = this.sslConfig.getCertificateChain();
         identityKs.setKeyEntry(alias, identityPrivateKey, pwd, certChain);
      }

      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(identityKs, pwd);
      this.sslContext.init(kmf.getKeyManagers(), (TrustManager[])null, (SecureRandom)null);
      return this.sslContext;
   }
}
