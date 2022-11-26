package org.python.netty.handler.ssl;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class JdkSslContext extends SslContext {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
   static final String PROTOCOL = "TLS";
   static final String[] DEFAULT_PROTOCOLS;
   static final List DEFAULT_CIPHERS;
   static final Set SUPPORTED_CIPHERS;
   private final String[] protocols;
   private final String[] cipherSuites;
   private final List unmodifiableCipherSuites;
   private final JdkApplicationProtocolNegotiator apn;
   private final ClientAuth clientAuth;
   private final SSLContext sslContext;
   private final boolean isClient;

   private static void addIfSupported(Set supported, List enabled, String... names) {
      String[] var3 = names;
      int var4 = names.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String n = var3[var5];
         if (supported.contains(n)) {
            enabled.add(n);
         }
      }

   }

   public JdkSslContext(SSLContext sslContext, boolean isClient, ClientAuth clientAuth) {
      this(sslContext, isClient, (Iterable)null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth, (String[])null, false);
   }

   public JdkSslContext(SSLContext sslContext, boolean isClient, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, ClientAuth clientAuth) {
      this(sslContext, isClient, ciphers, cipherFilter, toNegotiator(apn, !isClient), clientAuth, (String[])null, false);
   }

   JdkSslContext(SSLContext sslContext, boolean isClient, Iterable ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, ClientAuth clientAuth, String[] protocols, boolean startTls) {
      super(startTls);
      this.apn = (JdkApplicationProtocolNegotiator)ObjectUtil.checkNotNull(apn, "apn");
      this.clientAuth = (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth");
      this.cipherSuites = ((CipherSuiteFilter)ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(ciphers, DEFAULT_CIPHERS, SUPPORTED_CIPHERS);
      this.protocols = protocols == null ? DEFAULT_PROTOCOLS : protocols;
      this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
      this.sslContext = (SSLContext)ObjectUtil.checkNotNull(sslContext, "sslContext");
      this.isClient = isClient;
   }

   public final SSLContext context() {
      return this.sslContext;
   }

   public final boolean isClient() {
      return this.isClient;
   }

   public final SSLSessionContext sessionContext() {
      return this.isServer() ? this.context().getServerSessionContext() : this.context().getClientSessionContext();
   }

   public final List cipherSuites() {
      return this.unmodifiableCipherSuites;
   }

   public final long sessionCacheSize() {
      return (long)this.sessionContext().getSessionCacheSize();
   }

   public final long sessionTimeout() {
      return (long)this.sessionContext().getSessionTimeout();
   }

   public final SSLEngine newEngine(ByteBufAllocator alloc) {
      return this.configureAndWrapEngine(this.context().createSSLEngine());
   }

   public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
      return this.configureAndWrapEngine(this.context().createSSLEngine(peerHost, peerPort));
   }

   private SSLEngine configureAndWrapEngine(SSLEngine engine) {
      engine.setEnabledCipherSuites(this.cipherSuites);
      engine.setEnabledProtocols(this.protocols);
      engine.setUseClientMode(this.isClient());
      if (this.isServer()) {
         switch (this.clientAuth) {
            case OPTIONAL:
               engine.setWantClientAuth(true);
               break;
            case REQUIRE:
               engine.setNeedClientAuth(true);
         }
      }

      return this.apn.wrapperFactory().wrapSslEngine(engine, this.apn, this.isServer());
   }

   public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
      return this.apn;
   }

   static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config, boolean isServer) {
      if (config == null) {
         return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
      } else {
         switch (config.protocol()) {
            case NONE:
               return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
            case ALPN:
               if (isServer) {
                  switch (config.selectorFailureBehavior()) {
                     case FATAL_ALERT:
                        return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                     case NO_ADVERTISE:
                        return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                     default:
                        throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
                  }
               } else {
                  switch (config.selectedListenerFailureBehavior()) {
                     case ACCEPT:
                        return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                     case FATAL_ALERT:
                        return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                     default:
                        throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
                  }
               }
            case NPN:
               if (isServer) {
                  switch (config.selectedListenerFailureBehavior()) {
                     case ACCEPT:
                        return new JdkNpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                     case FATAL_ALERT:
                        return new JdkNpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                     default:
                        throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
                  }
               } else {
                  switch (config.selectorFailureBehavior()) {
                     case FATAL_ALERT:
                        return new JdkNpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                     case NO_ADVERTISE:
                        return new JdkNpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                     default:
                        throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
                  }
               }
            default:
               throw new UnsupportedOperationException("JDK provider does not support " + config.protocol() + " protocol");
         }
      }
   }

   /** @deprecated */
   @Deprecated
   protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, File keyFile, String keyPassword, KeyManagerFactory kmf) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
      String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
      if (algorithm == null) {
         algorithm = "SunX509";
      }

      return buildKeyManagerFactory(certChainFile, algorithm, keyFile, keyPassword, kmf);
   }

   /** @deprecated */
   @Deprecated
   protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, String keyAlgorithm, File keyFile, String keyPassword, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
      return buildKeyManagerFactory(toX509Certificates(certChainFile), keyAlgorithm, toPrivateKey(keyFile, keyPassword), keyPassword, kmf);
   }

   static {
      SSLContext context;
      try {
         context = SSLContext.getInstance("TLS");
         context.init((KeyManager[])null, (TrustManager[])null, (SecureRandom)null);
      } catch (Exception var12) {
         throw new Error("failed to initialize the default SSL context", var12);
      }

      SSLEngine engine = context.createSSLEngine();
      String[] supportedProtocols = engine.getSupportedProtocols();
      Set supportedProtocolsSet = new HashSet(supportedProtocols.length);

      int i;
      for(i = 0; i < supportedProtocols.length; ++i) {
         supportedProtocolsSet.add(supportedProtocols[i]);
      }

      List protocols = new ArrayList();
      addIfSupported(supportedProtocolsSet, protocols, "TLSv1.2", "TLSv1.1", "TLSv1");
      if (!protocols.isEmpty()) {
         DEFAULT_PROTOCOLS = (String[])protocols.toArray(new String[protocols.size()]);
      } else {
         DEFAULT_PROTOCOLS = engine.getEnabledProtocols();
      }

      String[] supportedCiphers = engine.getSupportedCipherSuites();
      SUPPORTED_CIPHERS = new HashSet(supportedCiphers.length);

      for(i = 0; i < supportedCiphers.length; ++i) {
         SUPPORTED_CIPHERS.add(supportedCiphers[i]);
      }

      List ciphers = new ArrayList();
      addIfSupported(SUPPORTED_CIPHERS, ciphers, "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA");
      if (ciphers.isEmpty()) {
         String[] var8 = engine.getEnabledCipherSuites();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String cipher = var8[var10];
            if (!cipher.contains("_RC4_")) {
               ciphers.add(cipher);
            }
         }
      }

      DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
      if (logger.isDebugEnabled()) {
         logger.debug("Default protocols (JDK): {} ", (Object)Arrays.asList(DEFAULT_PROTOCOLS));
         logger.debug("Default cipher suites (JDK): {}", (Object)DEFAULT_CIPHERS);
      }

   }
}
