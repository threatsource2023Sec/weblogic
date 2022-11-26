package org.python.netty.handler.ssl;

import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509Certificate;
import java.security.cert.CertPathValidatorException.BasicReason;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.internal.tcnative.CertificateVerifier;
import org.python.netty.internal.tcnative.SSL;
import org.python.netty.internal.tcnative.SSLContext;
import org.python.netty.util.AbstractReferenceCounted;
import org.python.netty.util.ReferenceCounted;
import org.python.netty.util.ResourceLeakDetector;
import org.python.netty.util.ResourceLeakDetectorFactory;
import org.python.netty.util.ResourceLeakTracker;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class ReferenceCountedOpenSslContext extends SslContext implements ReferenceCounted {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslContext.class);
   private static final boolean JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return SystemPropertyUtil.getBoolean("jdk.tls.rejectClientInitiatedRenegotiation", false);
      }
   });
   private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = (Integer)AccessController.doPrivileged(new PrivilegedAction() {
      public Integer run() {
         return Math.max(1, SystemPropertyUtil.getInt("org.python.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048));
      }
   });
   private static final List DEFAULT_CIPHERS;
   private static final Integer DH_KEY_LENGTH;
   private static final ResourceLeakDetector leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslContext.class);
   protected static final int VERIFY_DEPTH = 10;
   protected volatile long ctx;
   private volatile int aprPoolDestroyed;
   private final List unmodifiableCiphers;
   private final long sessionCacheSize;
   private final long sessionTimeout;
   private final OpenSslApplicationProtocolNegotiator apn;
   private final int mode;
   private final ResourceLeakTracker leak;
   private final AbstractReferenceCounted refCnt;
   final Certificate[] keyCertChain;
   final ClientAuth clientAuth;
   final String[] protocols;
   final boolean enableOcsp;
   final OpenSslEngineMap engineMap;
   private volatile boolean rejectRemoteInitiatedRenegotiation;
   private volatile int bioNonApplicationBufferSize;
   static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR = new OpenSslApplicationProtocolNegotiator() {
      public ApplicationProtocolConfig.Protocol protocol() {
         return ApplicationProtocolConfig.Protocol.NONE;
      }

      public List protocols() {
         return Collections.emptyList();
      }

      public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
         return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
      }

      public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
         return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
      }
   };

   ReferenceCountedOpenSslContext(Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, boolean leakDetection) throws SSLException {
      this(ciphers, cipherFilter, toNegotiator(apnCfg), sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, protocols, startTls, enableOcsp, leakDetection);
   }

   ReferenceCountedOpenSslContext(Iterable ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, boolean leakDetection) throws SSLException {
      super(startTls);
      this.refCnt = new AbstractReferenceCounted() {
         public ReferenceCounted touch(Object hint) {
            if (ReferenceCountedOpenSslContext.this.leak != null) {
               ReferenceCountedOpenSslContext.this.leak.record(hint);
            }

            return ReferenceCountedOpenSslContext.this;
         }

         protected void deallocate() {
            ReferenceCountedOpenSslContext.this.destroy();
            if (ReferenceCountedOpenSslContext.this.leak != null) {
               boolean closed = ReferenceCountedOpenSslContext.this.leak.close(ReferenceCountedOpenSslContext.this);

               assert closed;
            }

         }
      };
      this.engineMap = new DefaultOpenSslEngineMap();
      this.bioNonApplicationBufferSize = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE;
      OpenSsl.ensureAvailability();
      if (enableOcsp && !OpenSsl.isOcspSupported()) {
         throw new IllegalStateException("OCSP is not supported.");
      } else if (mode != 1 && mode != 0) {
         throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
      } else {
         this.leak = leakDetection ? leakDetector.track(this) : null;
         this.mode = mode;
         this.clientAuth = this.isServer() ? (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
         this.protocols = protocols;
         this.enableOcsp = enableOcsp;
         if (mode == 1) {
            this.rejectRemoteInitiatedRenegotiation = JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION;
         }

         this.keyCertChain = keyCertChain == null ? null : (Certificate[])keyCertChain.clone();
         ArrayList convertedCiphers;
         if (ciphers == null) {
            convertedCiphers = null;
         } else {
            convertedCiphers = new ArrayList();

            String c;
            for(Iterator var16 = ciphers.iterator(); var16.hasNext(); convertedCiphers.add(c)) {
               c = (String)var16.next();
               if (c == null) {
                  break;
               }

               String converted = CipherSuiteConverter.toOpenSsl(c);
               if (converted != null) {
                  c = converted;
               }
            }
         }

         this.unmodifiableCiphers = Arrays.asList(((CipherSuiteFilter)ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(convertedCiphers, DEFAULT_CIPHERS, OpenSsl.availableOpenSslCipherSuites()));
         this.apn = (OpenSslApplicationProtocolNegotiator)ObjectUtil.checkNotNull(apn, "apn");
         boolean success = false;

         try {
            Class var34 = ReferenceCountedOpenSslContext.class;
            synchronized(ReferenceCountedOpenSslContext.class) {
               try {
                  this.ctx = SSLContext.make(31, mode);
               } catch (Exception var30) {
                  throw new SSLException("failed to create an SSL_CTX", var30);
               }

               SSLContext.setOptions(this.ctx, SSLContext.getOptions(this.ctx) | SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_CIPHER_SERVER_PREFERENCE | SSL.SSL_OP_NO_COMPRESSION | SSL.SSL_OP_NO_TICKET);
               SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER);
               if (DH_KEY_LENGTH != null) {
                  SSLContext.setTmpDHLength(this.ctx, DH_KEY_LENGTH);
               }

               try {
                  SSLContext.setCipherSuite(this.ctx, CipherSuiteConverter.toOpenSsl((Iterable)this.unmodifiableCiphers));
               } catch (SSLException var28) {
                  throw var28;
               } catch (Exception var29) {
                  throw new SSLException("failed to set cipher suite: " + this.unmodifiableCiphers, var29);
               }

               List nextProtoList = apn.protocols();
               if (!nextProtoList.isEmpty()) {
                  String[] appProtocols = (String[])nextProtoList.toArray(new String[nextProtoList.size()]);
                  int selectorBehavior = opensslSelectorFailureBehavior(apn.selectorFailureBehavior());
                  switch (apn.protocol()) {
                     case NPN:
                        SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
                        break;
                     case ALPN:
                        SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
                        break;
                     case NPN_AND_ALPN:
                        SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
                        SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
                        break;
                     default:
                        throw new Error();
                  }
               }

               if (sessionCacheSize > 0L) {
                  this.sessionCacheSize = sessionCacheSize;
                  SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
               } else {
                  this.sessionCacheSize = sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L);
                  SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
               }

               if (sessionTimeout > 0L) {
                  this.sessionTimeout = sessionTimeout;
                  SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
               } else {
                  this.sessionTimeout = sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L);
                  SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
               }

               if (enableOcsp) {
                  SSLContext.enableOcsp(this.ctx, this.isClient());
               }
            }

            success = true;
         } finally {
            if (!success) {
               this.release();
            }

         }

      }
   }

   private static int opensslSelectorFailureBehavior(ApplicationProtocolConfig.SelectorFailureBehavior behavior) {
      switch (behavior) {
         case NO_ADVERTISE:
            return 0;
         case CHOOSE_MY_LAST_PROTOCOL:
            return 1;
         default:
            throw new Error();
      }
   }

   public final List cipherSuites() {
      return this.unmodifiableCiphers;
   }

   public final long sessionCacheSize() {
      return this.sessionCacheSize;
   }

   public final long sessionTimeout() {
      return this.sessionTimeout;
   }

   public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
      return this.apn;
   }

   public final boolean isClient() {
      return this.mode == 0;
   }

   public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
      return this.newEngine0(alloc, peerHost, peerPort);
   }

   SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort) {
      return new ReferenceCountedOpenSslEngine(this, alloc, peerHost, peerPort, true);
   }

   abstract OpenSslKeyMaterialManager keyMaterialManager();

   public final SSLEngine newEngine(ByteBufAllocator alloc) {
      return this.newEngine(alloc, (String)null, -1);
   }

   /** @deprecated */
   @Deprecated
   public final long context() {
      return this.ctx;
   }

   /** @deprecated */
   @Deprecated
   public final OpenSslSessionStats stats() {
      return this.sessionContext().stats();
   }

   public void setRejectRemoteInitiatedRenegotiation(boolean rejectRemoteInitiatedRenegotiation) {
      this.rejectRemoteInitiatedRenegotiation = rejectRemoteInitiatedRenegotiation;
   }

   public boolean getRejectRemoteInitiatedRenegotiation() {
      return this.rejectRemoteInitiatedRenegotiation;
   }

   public void setBioNonApplicationBufferSize(int bioNonApplicationBufferSize) {
      this.bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(bioNonApplicationBufferSize, "bioNonApplicationBufferSize");
   }

   public int getBioNonApplicationBufferSize() {
      return this.bioNonApplicationBufferSize;
   }

   /** @deprecated */
   @Deprecated
   public final void setTicketKeys(byte[] keys) {
      this.sessionContext().setTicketKeys(keys);
   }

   public abstract OpenSslSessionContext sessionContext();

   public final long sslCtxPointer() {
      return this.ctx;
   }

   final void destroy() {
      Class var1 = ReferenceCountedOpenSslContext.class;
      synchronized(ReferenceCountedOpenSslContext.class) {
         if (this.ctx != 0L) {
            if (this.enableOcsp) {
               SSLContext.disableOcsp(this.ctx);
            }

            SSLContext.free(this.ctx);
            this.ctx = 0L;
         }

      }
   }

   protected static X509Certificate[] certificates(byte[][] chain) {
      X509Certificate[] peerCerts = new X509Certificate[chain.length];

      for(int i = 0; i < peerCerts.length; ++i) {
         peerCerts[i] = new OpenSslX509Certificate(chain[i]);
      }

      return peerCerts;
   }

   protected static X509TrustManager chooseTrustManager(TrustManager[] managers) {
      TrustManager[] var1 = managers;
      int var2 = managers.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TrustManager m = var1[var3];
         if (m instanceof X509TrustManager) {
            return (X509TrustManager)m;
         }
      }

      throw new IllegalStateException("no X509TrustManager found");
   }

   protected static X509KeyManager chooseX509KeyManager(KeyManager[] kms) {
      KeyManager[] var1 = kms;
      int var2 = kms.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         KeyManager km = var1[var3];
         if (km instanceof X509KeyManager) {
            return (X509KeyManager)km;
         }
      }

      throw new IllegalStateException("no X509KeyManager found");
   }

   static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config) {
      if (config == null) {
         return NONE_PROTOCOL_NEGOTIATOR;
      } else {
         switch (config.protocol()) {
            case NPN:
            case ALPN:
            case NPN_AND_ALPN:
               switch (config.selectedListenerFailureBehavior()) {
                  case CHOOSE_MY_LAST_PROTOCOL:
                  case ACCEPT:
                     switch (config.selectorFailureBehavior()) {
                        case NO_ADVERTISE:
                        case CHOOSE_MY_LAST_PROTOCOL:
                           return new OpenSslDefaultApplicationProtocolNegotiator(config);
                        default:
                           throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectorFailureBehavior() + " behavior");
                     }
                  default:
                     throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectedListenerFailureBehavior() + " behavior");
               }
            case NONE:
               return NONE_PROTOCOL_NEGOTIATOR;
            default:
               throw new Error();
         }
      }
   }

   static boolean useExtendedTrustManager(X509TrustManager trustManager) {
      return PlatformDependent.javaVersion() >= 7 && trustManager instanceof X509ExtendedTrustManager;
   }

   static boolean useExtendedKeyManager(X509KeyManager keyManager) {
      return PlatformDependent.javaVersion() >= 7 && keyManager instanceof X509ExtendedKeyManager;
   }

   public final int refCnt() {
      return this.refCnt.refCnt();
   }

   public final ReferenceCounted retain() {
      this.refCnt.retain();
      return this;
   }

   public final ReferenceCounted retain(int increment) {
      this.refCnt.retain(increment);
      return this;
   }

   public final ReferenceCounted touch() {
      this.refCnt.touch();
      return this;
   }

   public final ReferenceCounted touch(Object hint) {
      this.refCnt.touch(hint);
      return this;
   }

   public final boolean release() {
      return this.refCnt.release();
   }

   public final boolean release(int decrement) {
      return this.refCnt.release(decrement);
   }

   static void setKeyMaterial(long ctx, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword) throws SSLException {
      long keyBio = 0L;
      long keyCertChainBio = 0L;
      long keyCertChainBio2 = 0L;
      PemEncoded encoded = null;

      try {
         encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, keyCertChain);
         keyCertChainBio = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
         keyCertChainBio2 = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
         if (key != null) {
            keyBio = toBIO(key);
         }

         SSLContext.setCertificateBio(ctx, keyCertChainBio, keyBio, keyPassword == null ? "" : keyPassword);
         SSLContext.setCertificateChainBio(ctx, keyCertChainBio2, true);
      } catch (SSLException var17) {
         throw var17;
      } catch (Exception var18) {
         throw new SSLException("failed to set certificate and key", var18);
      } finally {
         freeBio(keyBio);
         freeBio(keyCertChainBio);
         freeBio(keyCertChainBio2);
         if (encoded != null) {
            encoded.release();
         }

      }

   }

   static void freeBio(long bio) {
      if (bio != 0L) {
         SSL.freeBIO(bio);
      }

   }

   static long toBIO(PrivateKey key) throws Exception {
      if (key == null) {
         return 0L;
      } else {
         ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
         PemEncoded pem = PemPrivateKey.toPEM(allocator, true, key);

         long var3;
         try {
            var3 = toBIO(allocator, pem.retain());
         } finally {
            pem.release();
         }

         return var3;
      }
   }

   static long toBIO(X509Certificate... certChain) throws Exception {
      if (certChain == null) {
         return 0L;
      } else if (certChain.length == 0) {
         throw new IllegalArgumentException("certChain can't be empty");
      } else {
         ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
         PemEncoded pem = PemX509Certificate.toPEM(allocator, true, certChain);

         long var3;
         try {
            var3 = toBIO(allocator, pem.retain());
         } finally {
            pem.release();
         }

         return var3;
      }
   }

   static long toBIO(ByteBufAllocator allocator, PemEncoded pem) throws Exception {
      long var6;
      try {
         ByteBuf content = pem.content();
         if (content.isDirect()) {
            long var3 = newBIO(content.retainedSlice());
            return var3;
         }

         ByteBuf buffer = allocator.directBuffer(content.readableBytes());

         try {
            buffer.writeBytes(content, content.readerIndex(), content.readableBytes());
            var6 = newBIO(buffer.retainedSlice());
         } finally {
            try {
               if (pem.isSensitive()) {
                  SslUtils.zeroout(buffer);
               }
            } finally {
               buffer.release();
            }

         }
      } finally {
         pem.release();
      }

      return var6;
   }

   private static long newBIO(ByteBuf buffer) throws Exception {
      long var4;
      try {
         long bio = SSL.newMemBIO();
         int readable = buffer.readableBytes();
         if (SSL.bioWrite(bio, OpenSsl.memoryAddress(buffer) + (long)buffer.readerIndex(), readable) != readable) {
            SSL.freeBIO(bio);
            throw new IllegalStateException("Could not write data to memory BIO");
         }

         var4 = bio;
      } finally {
         buffer.release();
      }

      return var4;
   }

   static {
      List ciphers = new ArrayList();
      Collections.addAll(ciphers, new String[]{"ECDHE-ECDSA-AES256-GCM-SHA384", "ECDHE-ECDSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "AES128-SHA", "AES256-SHA"});
      DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
      if (logger.isDebugEnabled()) {
         logger.debug("Default cipher suite (OpenSSL): " + ciphers);
      }

      Integer dhLen = null;

      try {
         String dhKeySize = (String)AccessController.doPrivileged(new PrivilegedAction() {
            public String run() {
               return SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
            }
         });
         if (dhKeySize != null) {
            try {
               dhLen = Integer.valueOf(dhKeySize);
            } catch (NumberFormatException var4) {
               logger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + dhKeySize);
            }
         }
      } catch (Throwable var5) {
      }

      DH_KEY_LENGTH = dhLen;
   }

   private static final class DefaultOpenSslEngineMap implements OpenSslEngineMap {
      private final Map engines;

      private DefaultOpenSslEngineMap() {
         this.engines = PlatformDependent.newConcurrentHashMap();
      }

      public ReferenceCountedOpenSslEngine remove(long ssl) {
         return (ReferenceCountedOpenSslEngine)this.engines.remove(ssl);
      }

      public void add(ReferenceCountedOpenSslEngine engine) {
         this.engines.put(engine.sslPointer(), engine);
      }

      public ReferenceCountedOpenSslEngine get(long ssl) {
         return (ReferenceCountedOpenSslEngine)this.engines.get(ssl);
      }

      // $FF: synthetic method
      DefaultOpenSslEngineMap(Object x0) {
         this();
      }
   }

   abstract static class AbstractCertificateVerifier extends CertificateVerifier {
      private final OpenSslEngineMap engineMap;

      AbstractCertificateVerifier(OpenSslEngineMap engineMap) {
         this.engineMap = engineMap;
      }

      public final int verify(long ssl, byte[][] chain, String auth) {
         X509Certificate[] peerCerts = ReferenceCountedOpenSslContext.certificates(chain);
         ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);

         try {
            this.verify(engine, peerCerts, auth);
            return CertificateVerifier.X509_V_OK;
         } catch (Throwable var12) {
            ReferenceCountedOpenSslContext.logger.debug("verification of certificate failed", var12);
            SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
            e.initCause(var12);
            engine.handshakeException = e;
            if (var12 instanceof OpenSslCertificateException) {
               return ((OpenSslCertificateException)var12).errorCode();
            } else if (var12 instanceof CertificateExpiredException) {
               return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
            } else if (var12 instanceof CertificateNotYetValidException) {
               return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
            } else {
               if (PlatformDependent.javaVersion() >= 7) {
                  if (var12 instanceof CertificateRevokedException) {
                     return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                  }

                  for(Throwable wrapped = var12.getCause(); wrapped != null; wrapped = wrapped.getCause()) {
                     if (wrapped instanceof CertPathValidatorException) {
                        CertPathValidatorException ex = (CertPathValidatorException)wrapped;
                        CertPathValidatorException.Reason reason = ex.getReason();
                        if (reason == BasicReason.EXPIRED) {
                           return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                        }

                        if (reason == BasicReason.NOT_YET_VALID) {
                           return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                        }

                        if (reason == BasicReason.REVOKED) {
                           return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                        }
                     }
                  }
               }

               return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
            }
         }
      }

      abstract void verify(ReferenceCountedOpenSslEngine var1, X509Certificate[] var2, String var3) throws Exception;
   }
}
