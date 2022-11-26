package org.python.netty.handler.ssl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.ByteBufInputStream;
import org.python.netty.util.internal.EmptyArrays;

public abstract class SslContext {
   static final CertificateFactory X509_CERT_FACTORY;
   private final boolean startTls;

   public static SslProvider defaultServerProvider() {
      return defaultProvider();
   }

   public static SslProvider defaultClientProvider() {
      return defaultProvider();
   }

   private static SslProvider defaultProvider() {
      return OpenSsl.isAvailable() ? SslProvider.OPENSSL : SslProvider.JDK;
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(File certChainFile, File keyFile) throws SSLException {
      return newServerContext((File)certChainFile, keyFile, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
      return newServerContext((SslProvider)null, certChainFile, keyFile, keyPassword);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable ciphers, Iterable nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newServerContext((SslProvider)null, certChainFile, (File)keyFile, (String)keyPassword, (Iterable)ciphers, (Iterable)nextProtocols, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newServerContext((SslProvider)null, certChainFile, keyFile, keyPassword, (Iterable)ciphers, (CipherSuiteFilter)cipherFilter, (ApplicationProtocolConfig)apn, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile) throws SSLException {
      return newServerContext(provider, certChainFile, keyFile, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword) throws SSLException {
      return newServerContext(provider, certChainFile, keyFile, keyPassword, (Iterable)null, (CipherSuiteFilter)IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable ciphers, Iterable nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newServerContext(provider, certChainFile, keyFile, keyPassword, (Iterable)ciphers, (CipherSuiteFilter)IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable ciphers, Iterable nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newServerContext(provider, (File)null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory)null, ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newServerContext(provider, (File)null, (TrustManagerFactory)null, certChainFile, keyFile, keyPassword, (KeyManagerFactory)null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newServerContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      try {
         return newServerContextInternal(provider, (Provider)null, toX509Certificates(trustCertCollectionFile), trustManagerFactory, toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, ClientAuth.NONE, (String[])null, false, false);
      } catch (Exception var15) {
         if (var15 instanceof SSLException) {
            throw (SSLException)var15;
         } else {
            throw new SSLException("failed to initialize the server-side SSL context", var15);
         }
      }
   }

   static SslContext newServerContextInternal(SslProvider provider, Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) throws SSLException {
      if (provider == null) {
         provider = defaultServerProvider();
      }

      switch (provider) {
         case JDK:
            if (enableOcsp) {
               throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + provider);
            }

            return new JdkSslServerContext(sslContextProvider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls);
         case OPENSSL:
            verifyNullSslContextProvider(provider, sslContextProvider);
            return new OpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp);
         case OPENSSL_REFCNT:
            verifyNullSslContextProvider(provider, sslContextProvider);
            return new ReferenceCountedOpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp);
         default:
            throw new Error(provider.toString());
      }
   }

   private static void verifyNullSslContextProvider(SslProvider provider, Provider sslContextProvider) {
      if (sslContextProvider != null) {
         throw new IllegalArgumentException("Java Security Provider unsupported for SslProvider: " + provider);
      }
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext() throws SSLException {
      return newClientContext((SslProvider)null, (File)null, (TrustManagerFactory)null);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(File certChainFile) throws SSLException {
      return newClientContext((SslProvider)null, (File)certChainFile);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext((SslProvider)null, (File)null, trustManagerFactory);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext((SslProvider)null, certChainFile, trustManagerFactory);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, Iterable nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newClientContext((SslProvider)null, (File)certChainFile, (TrustManagerFactory)trustManagerFactory, (Iterable)ciphers, (Iterable)nextProtocols, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newClientContext((SslProvider)null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider) throws SSLException {
      return newClientContext(provider, (File)null, (TrustManagerFactory)null);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider, File certChainFile) throws SSLException {
      return newClientContext(provider, certChainFile, (TrustManagerFactory)null);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider, TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext(provider, (File)null, trustManagerFactory);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
      return newClientContext(provider, certChainFile, trustManagerFactory, (Iterable)null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, Iterable nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newClientContext(provider, certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      return newClientContext(provider, certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
   }

   /** @deprecated */
   @Deprecated
   public static SslContext newClientContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
      try {
         return newClientContextInternal(provider, (Provider)null, toX509Certificates(trustCertCollectionFile), trustManagerFactory, toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, (String[])null, sessionCacheSize, sessionTimeout, false);
      } catch (Exception var15) {
         if (var15 instanceof SSLException) {
            throw (SSLException)var15;
         } else {
            throw new SSLException("failed to initialize the client-side SSL context", var15);
         }
      }
   }

   static SslContext newClientContextInternal(SslProvider provider, Provider sslContextProvider, X509Certificate[] trustCert, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp) throws SSLException {
      if (provider == null) {
         provider = defaultClientProvider();
      }

      switch (provider) {
         case JDK:
            if (enableOcsp) {
               throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + provider);
            }

            return new JdkSslClientContext(sslContextProvider, trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout);
         case OPENSSL:
            verifyNullSslContextProvider(provider, sslContextProvider);
            return new OpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, enableOcsp);
         case OPENSSL_REFCNT:
            verifyNullSslContextProvider(provider, sslContextProvider);
            return new ReferenceCountedOpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, enableOcsp);
         default:
            throw new Error(provider.toString());
      }
   }

   static ApplicationProtocolConfig toApplicationProtocolConfig(Iterable nextProtocols) {
      ApplicationProtocolConfig apn;
      if (nextProtocols == null) {
         apn = ApplicationProtocolConfig.DISABLED;
      } else {
         apn = new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN_AND_ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, nextProtocols);
      }

      return apn;
   }

   protected SslContext() {
      this(false);
   }

   protected SslContext(boolean startTls) {
      this.startTls = startTls;
   }

   public final boolean isServer() {
      return !this.isClient();
   }

   public abstract boolean isClient();

   public abstract List cipherSuites();

   public abstract long sessionCacheSize();

   public abstract long sessionTimeout();

   /** @deprecated */
   @Deprecated
   public final List nextProtocols() {
      return this.applicationProtocolNegotiator().protocols();
   }

   public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();

   public abstract SSLEngine newEngine(ByteBufAllocator var1);

   public abstract SSLEngine newEngine(ByteBufAllocator var1, String var2, int var3);

   public abstract SSLSessionContext sessionContext();

   public final SslHandler newHandler(ByteBufAllocator alloc) {
      return new SslHandler(this.newEngine(alloc), this.startTls);
   }

   public final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort) {
      return new SslHandler(this.newEngine(alloc, peerHost, peerPort), this.startTls);
   }

   protected static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
      if (password == null) {
         return new PKCS8EncodedKeySpec(key);
      } else {
         EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
         PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
         SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
         Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
         cipher.init(2, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());
         return encryptedPrivateKeyInfo.getKeySpec(cipher);
      }
   }

   static KeyStore buildKeyStore(X509Certificate[] certChain, PrivateKey key, char[] keyPasswordChars) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
      KeyStore ks = KeyStore.getInstance("JKS");
      ks.load((InputStream)null, (char[])null);
      ks.setKeyEntry("key", key, keyPasswordChars, certChain);
      return ks;
   }

   static PrivateKey toPrivateKey(File keyFile, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
      return keyFile == null ? null : getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyFile), keyPassword);
   }

   static PrivateKey toPrivateKey(InputStream keyInputStream, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
      return keyInputStream == null ? null : getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyInputStream), keyPassword);
   }

   private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf encodedKeyBuf, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
      byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
      encodedKeyBuf.readBytes(encodedKey).release();
      PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(keyPassword == null ? null : keyPassword.toCharArray(), encodedKey);

      try {
         return KeyFactory.getInstance("RSA").generatePrivate(encodedKeySpec);
      } catch (InvalidKeySpecException var9) {
         try {
            return KeyFactory.getInstance("DSA").generatePrivate(encodedKeySpec);
         } catch (InvalidKeySpecException var8) {
            try {
               return KeyFactory.getInstance("EC").generatePrivate(encodedKeySpec);
            } catch (InvalidKeySpecException var7) {
               throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", var7);
            }
         }
      }
   }

   /** @deprecated */
   @Deprecated
   protected static TrustManagerFactory buildTrustManagerFactory(File certChainFile, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
      X509Certificate[] x509Certs = toX509Certificates(certChainFile);
      return buildTrustManagerFactory(x509Certs, trustManagerFactory);
   }

   static X509Certificate[] toX509Certificates(File file) throws CertificateException {
      return file == null ? null : getCertificatesFromBuffers(PemReader.readCertificates(file));
   }

   static X509Certificate[] toX509Certificates(InputStream in) throws CertificateException {
      return in == null ? null : getCertificatesFromBuffers(PemReader.readCertificates(in));
   }

   private static X509Certificate[] getCertificatesFromBuffers(ByteBuf[] certs) throws CertificateException {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate[] x509Certs = new X509Certificate[certs.length];
      int i = 0;

      try {
         for(; i < certs.length; ++i) {
            InputStream is = new ByteBufInputStream(certs[i], true);

            try {
               x509Certs[i] = (X509Certificate)cf.generateCertificate(is);
            } finally {
               try {
                  is.close();
               } catch (IOException var17) {
                  throw new RuntimeException(var17);
               }
            }
         }
      } finally {
         while(i < certs.length) {
            certs[i].release();
            ++i;
         }

      }

      return x509Certs;
   }

   static TrustManagerFactory buildTrustManagerFactory(X509Certificate[] certCollection, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
      KeyStore ks = KeyStore.getInstance("JKS");
      ks.load((InputStream)null, (char[])null);
      int i = 1;
      X509Certificate[] var4 = certCollection;
      int var5 = certCollection.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         X509Certificate cert = var4[var6];
         String alias = Integer.toString(i);
         ks.setCertificateEntry(alias, cert);
         ++i;
      }

      if (trustManagerFactory == null) {
         trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      }

      trustManagerFactory.init(ks);
      return trustManagerFactory;
   }

   static PrivateKey toPrivateKeyInternal(File keyFile, String keyPassword) throws SSLException {
      try {
         return toPrivateKey(keyFile, keyPassword);
      } catch (Exception var3) {
         throw new SSLException(var3);
      }
   }

   static X509Certificate[] toX509CertificatesInternal(File file) throws SSLException {
      try {
         return toX509Certificates(file);
      } catch (CertificateException var2) {
         throw new SSLException(var2);
      }
   }

   static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChain, PrivateKey key, String keyPassword, KeyManagerFactory kmf) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
      String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
      if (algorithm == null) {
         algorithm = "SunX509";
      }

      return buildKeyManagerFactory(certChain, algorithm, key, keyPassword, kmf);
   }

   static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChainFile, String keyAlgorithm, PrivateKey key, String keyPassword, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
      char[] keyPasswordChars = keyPassword == null ? EmptyArrays.EMPTY_CHARS : keyPassword.toCharArray();
      KeyStore ks = buildKeyStore(certChainFile, key, keyPasswordChars);
      if (kmf == null) {
         kmf = KeyManagerFactory.getInstance(keyAlgorithm);
      }

      kmf.init(ks, keyPasswordChars);
      return kmf;
   }

   static {
      try {
         X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
      } catch (CertificateException var1) {
         throw new IllegalStateException("unable to instance X.509 CertificateFactory", var1);
      }
   }
}
