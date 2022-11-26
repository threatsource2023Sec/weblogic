package org.python.netty.handler.ssl;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.python.netty.internal.tcnative.CertificateRequestedCallback;
import org.python.netty.internal.tcnative.SSLContext;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class ReferenceCountedOpenSslClientContext extends ReferenceCountedOpenSslContext {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslClientContext.class);
   private final OpenSslSessionContext sessionContext;

   ReferenceCountedOpenSslClientContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp) throws SSLException {
      super(ciphers, cipherFilter, (ApplicationProtocolConfig)apn, sessionCacheSize, sessionTimeout, 0, keyCertChain, ClientAuth.NONE, protocols, false, enableOcsp, true);
      boolean success = false;

      try {
         this.sessionContext = newSessionContext(this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory);
         success = true;
      } finally {
         if (!success) {
            this.release();
         }

      }

   }

   OpenSslKeyMaterialManager keyMaterialManager() {
      return null;
   }

   public OpenSslSessionContext sessionContext() {
      return this.sessionContext;
   }

   static OpenSslSessionContext newSessionContext(ReferenceCountedOpenSslContext thiz, long ctx, OpenSslEngineMap engineMap, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory) throws SSLException {
      if (key == null && keyCertChain != null || key != null && keyCertChain == null) {
         throw new IllegalArgumentException("Either both keyCertChain and key needs to be null or none of them");
      } else {
         Class var10 = ReferenceCountedOpenSslContext.class;
         synchronized(ReferenceCountedOpenSslContext.class) {
            try {
               if (!OpenSsl.useKeyManagerFactory()) {
                  if (keyManagerFactory != null) {
                     throw new IllegalArgumentException("KeyManagerFactory not supported");
                  }

                  if (keyCertChain != null) {
                     setKeyMaterial(ctx, keyCertChain, key, keyPassword);
                  }
               } else {
                  if (keyManagerFactory == null && keyCertChain != null) {
                     keyManagerFactory = buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
                  }

                  if (keyManagerFactory != null) {
                     X509KeyManager keyManager = chooseX509KeyManager(keyManagerFactory.getKeyManagers());
                     OpenSslKeyMaterialManager materialManager = useExtendedKeyManager(keyManager) ? new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager)keyManager, keyPassword) : new OpenSslKeyMaterialManager(keyManager, keyPassword);
                     SSLContext.setCertRequestedCallback(ctx, new OpenSslCertificateRequestedCallback(engineMap, (OpenSslKeyMaterialManager)materialManager));
                  }
               }
            } catch (Exception var15) {
               throw new SSLException("failed to set certificate and key", var15);
            }

            SSLContext.setVerify(ctx, 0, 10);

            try {
               if (trustCertCollection != null) {
                  trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
               } else if (trustManagerFactory == null) {
                  trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                  trustManagerFactory.init((KeyStore)null);
               }

               X509TrustManager manager = chooseTrustManager(trustManagerFactory.getTrustManagers());
               if (useExtendedTrustManager(manager)) {
                  SSLContext.setCertVerifyCallback(ctx, new ExtendedTrustManagerVerifyCallback(engineMap, (X509ExtendedTrustManager)manager));
               } else {
                  SSLContext.setCertVerifyCallback(ctx, new TrustManagerVerifyCallback(engineMap, manager));
               }
            } catch (Exception var14) {
               throw new SSLException("unable to setup trustmanager", var14);
            }
         }

         return new OpenSslClientSessionContext(thiz);
      }
   }

   private static final class OpenSslCertificateRequestedCallback implements CertificateRequestedCallback {
      private final OpenSslEngineMap engineMap;
      private final OpenSslKeyMaterialManager keyManagerHolder;

      OpenSslCertificateRequestedCallback(OpenSslEngineMap engineMap, OpenSslKeyMaterialManager keyManagerHolder) {
         this.engineMap = engineMap;
         this.keyManagerHolder = keyManagerHolder;
      }

      public CertificateRequestedCallback.KeyMaterial requested(long ssl, byte[] keyTypeBytes, byte[][] asn1DerEncodedPrincipals) {
         ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);

         try {
            Set keyTypesSet = supportedClientKeyTypes(keyTypeBytes);
            String[] keyTypes = (String[])keyTypesSet.toArray(new String[keyTypesSet.size()]);
            X500Principal[] issuers;
            if (asn1DerEncodedPrincipals == null) {
               issuers = null;
            } else {
               issuers = new X500Principal[asn1DerEncodedPrincipals.length];

               for(int i = 0; i < asn1DerEncodedPrincipals.length; ++i) {
                  issuers[i] = new X500Principal(asn1DerEncodedPrincipals[i]);
               }
            }

            return this.keyManagerHolder.keyMaterial(engine, keyTypes, issuers);
         } catch (Throwable var10) {
            ReferenceCountedOpenSslClientContext.logger.debug("request of key failed", var10);
            SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
            e.initCause(var10);
            engine.handshakeException = e;
            return null;
         }
      }

      private static Set supportedClientKeyTypes(byte[] clientCertificateTypes) {
         Set result = new HashSet(clientCertificateTypes.length);
         byte[] var2 = clientCertificateTypes;
         int var3 = clientCertificateTypes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            byte keyTypeCode = var2[var4];
            String keyType = clientKeyType(keyTypeCode);
            if (keyType != null) {
               result.add(keyType);
            }
         }

         return result;
      }

      private static String clientKeyType(byte clientCertificateType) {
         switch (clientCertificateType) {
            case 1:
               return "RSA";
            case 3:
               return "DH_RSA";
            case 64:
               return "EC";
            case 65:
               return "EC_RSA";
            case 66:
               return "EC_EC";
            default:
               return null;
         }
      }
   }

   private static final class ExtendedTrustManagerVerifyCallback extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
      private final X509ExtendedTrustManager manager;

      ExtendedTrustManagerVerifyCallback(OpenSslEngineMap engineMap, X509ExtendedTrustManager manager) {
         super(engineMap);
         this.manager = manager;
      }

      void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
         this.manager.checkServerTrusted(peerCerts, auth, engine);
      }
   }

   private static final class TrustManagerVerifyCallback extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
      private final X509TrustManager manager;

      TrustManagerVerifyCallback(OpenSslEngineMap engineMap, X509TrustManager manager) {
         super(engineMap);
         this.manager = manager;
      }

      void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
         this.manager.checkServerTrusted(peerCerts, auth);
      }
   }

   static final class OpenSslClientSessionContext extends OpenSslSessionContext {
      OpenSslClientSessionContext(ReferenceCountedOpenSslContext context) {
         super(context);
      }

      public void setSessionTimeout(int seconds) {
         if (seconds < 0) {
            throw new IllegalArgumentException();
         }
      }

      public int getSessionTimeout() {
         return 0;
      }

      public void setSessionCacheSize(int size) {
         if (size < 0) {
            throw new IllegalArgumentException();
         }
      }

      public int getSessionCacheSize() {
         return 0;
      }

      public void setSessionCacheEnabled(boolean enabled) {
      }

      public boolean isSessionCacheEnabled() {
         return false;
      }
   }
}
