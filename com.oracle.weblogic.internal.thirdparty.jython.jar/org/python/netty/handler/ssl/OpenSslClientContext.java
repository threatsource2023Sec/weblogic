package org.python.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

public final class OpenSslClientContext extends OpenSslContext {
   private final OpenSslSessionContext sessionContext;

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext() throws SSLException {
      this((File)null, (TrustManagerFactory)null, (File)null, (File)null, (String)null, (KeyManagerFactory)null, (Iterable)null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
   }

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext(File certChainFile) throws SSLException {
      this(certChainFile, (TrustManagerFactory)null);
   }

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
      this((File)null, trustManagerFactory);
   }

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
      this(certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, (Iterable)null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
   }

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this(certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, ciphers, IdentityCipherSuiteFilter.INSTANCE, apn, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this(certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public OpenSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this(toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, toX509CertificatesInternal(keyCertChainFile), toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, (String[])null, sessionCacheSize, sessionTimeout, false);
   }

   OpenSslClientContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp) throws SSLException {
      super(ciphers, cipherFilter, (ApplicationProtocolConfig)apn, sessionCacheSize, sessionTimeout, 0, keyCertChain, ClientAuth.NONE, protocols, false, enableOcsp);
      boolean success = false;

      try {
         this.sessionContext = ReferenceCountedOpenSslClientContext.newSessionContext(this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory);
         success = true;
      } finally {
         if (!success) {
            this.release();
         }

      }

   }

   public OpenSslSessionContext sessionContext() {
      return this.sessionContext;
   }

   OpenSslKeyMaterialManager keyMaterialManager() {
      return null;
   }
}
