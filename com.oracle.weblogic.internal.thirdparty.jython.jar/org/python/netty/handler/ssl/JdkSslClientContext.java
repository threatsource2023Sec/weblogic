package org.python.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

/** @deprecated */
@Deprecated
public final class JdkSslClientContext extends JdkSslContext {
   /** @deprecated */
   @Deprecated
   public JdkSslClientContext() throws SSLException {
      this((File)null, (TrustManagerFactory)null);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File certChainFile) throws SSLException {
      this(certChainFile, (TrustManagerFactory)null);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
      this((File)null, trustManagerFactory);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
      this(certChainFile, trustManagerFactory, (Iterable)null, IdentityCipherSuiteFilter.INSTANCE, (JdkApplicationProtocolNegotiator)JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0L, 0L);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, Iterable nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this(certChainFile, trustManagerFactory, ciphers, IdentityCipherSuiteFilter.INSTANCE, (JdkApplicationProtocolNegotiator)toNegotiator(toApplicationProtocolConfig(nextProtocols), false), sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this(certChainFile, trustManagerFactory, ciphers, cipherFilter, toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this((Provider)null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
   }

   JdkSslClientContext(Provider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      super(newSSLContext(provider, toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, (X509Certificate[])null, (PrivateKey)null, (String)null, (KeyManagerFactory)null, sessionCacheSize, sessionTimeout), true, ciphers, cipherFilter, apn, ClientAuth.NONE, (String[])null, false);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      super(newSSLContext((Provider)null, toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, toX509CertificatesInternal(keyCertChainFile), toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), true, ciphers, cipherFilter, apn, ClientAuth.NONE, (String[])null, false);
   }

   JdkSslClientContext(Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      super(newSSLContext(sslContextProvider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), true, ciphers, cipherFilter, toNegotiator(apn, false), ClientAuth.NONE, protocols, false);
   }

   private static SSLContext newSSLContext(Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, long sessionCacheSize, long sessionTimeout) throws SSLException {
      try {
         if (trustCertCollection != null) {
            trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
         }

         if (keyCertChain != null) {
            keyManagerFactory = buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
         }

         SSLContext ctx = sslContextProvider == null ? SSLContext.getInstance("TLS") : SSLContext.getInstance("TLS", sslContextProvider);
         ctx.init(keyManagerFactory == null ? null : keyManagerFactory.getKeyManagers(), trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(), (SecureRandom)null);
         SSLSessionContext sessCtx = ctx.getClientSessionContext();
         if (sessionCacheSize > 0L) {
            sessCtx.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
         }

         if (sessionTimeout > 0L) {
            sessCtx.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
         }

         return ctx;
      } catch (Exception var13) {
         if (var13 instanceof SSLException) {
            throw (SSLException)var13;
         } else {
            throw new SSLException("failed to initialize the client-side SSL context", var13);
         }
      }
   }
}
