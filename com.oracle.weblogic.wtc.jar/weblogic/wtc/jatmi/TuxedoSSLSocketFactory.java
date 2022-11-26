package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import javax.net.ssl.SSLServerSocketFactory;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.SSL.SSLSocketFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.pki.keystore.WLSKeyStoreFactory;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLContextManager;
import weblogic.security.utils.SSLContextWrapper;
import weblogic.socket.SocketMuxer;
import weblogic.wtc.WTCLogger;

public final class TuxedoSSLSocketFactory extends SSLSocketFactory {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String[] CIPHER0 = new String[]{"TLS_RSA_WITH_NULL_MD5", "TLS_RSA_WITH_NULL_SHA"};
   public static final String[] CIPHER56 = new String[]{"TLS_RSA_WITH_DES_CBC_SHA"};
   public static final String[] CIPHER112 = new String[]{"TLS_RSA_WITH_3DES_EDE_CBC_SHA"};
   public static final String[] CIPHER128 = new String[]{"TLS_RSA_WITH_RC4_128_SHA", "TLS_RSA_WITH_RC4_128_MD5"};
   public static final String[] CIPHER256 = new String[]{"TLS_RSA_WITH_AES_256_CBC_SHA"};
   private String ksType;
   private String trustKsType;
   private String identityKeyStore;
   private String identityKeyStorePassphrase;
   private String identityKeyAlias;
   private String identityKeyPassphrase;
   private String trustKeyStore;
   private String trustKeyStorePassphrase;

   public TuxedoSSLSocketFactory(String ksType, String iks, String iksp, String ika, String ikp, String trustKsType, String tks, String tksp) {
      super((javax.net.ssl.SSLSocketFactory)null);
      this.ksType = ksType;
      this.identityKeyStore = iks;
      this.identityKeyStorePassphrase = iksp;
      this.identityKeyAlias = ika;
      this.identityKeyPassphrase = ikp;
      this.trustKsType = trustKsType;
      this.trustKeyStore = tks;
      this.trustKeyStorePassphrase = tksp;
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      javax.net.ssl.SSLSocketFactory sf = this.getSocketFactory();
      return sf.createSocket(host, port);
   }

   public String[] getDefaultCipherSuites() {
      try {
         return this.getSocketFactory().getDefaultCipherSuites();
      } catch (IOException var2) {
         throw (RuntimeException)(new IllegalStateException()).initCause(var2);
      }
   }

   public String[] getSupportedCipherSuites() {
      try {
         return this.getSocketFactory().getSupportedCipherSuites();
      } catch (IOException var2) {
         throw (RuntimeException)(new IllegalStateException()).initCause(var2);
      }
   }

   public ServerSocket createServerSocket(int port, int backlog, InetAddress address) throws IOException {
      return this.getServerSocketFactory().createServerSocket(port, backlog, address);
   }

   public Socket createSocket(Socket socket, String host, int port, boolean autoclose) throws IOException {
      return this.getSocketFactory().createSocket(socket, host, port, autoclose);
   }

   private SSLServerSocketFactory getServerSocketFactory() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoSSLSocketFactory/getServerSocketFactory()");
      }

      SSLContextWrapper sslContext = SSLContextWrapper.getInstance();
      String method = "getServerSocketFactory";
      File ksFile = null;
      KeyStore ks = null;
      Key key = null;
      Certificate[] cert = null;
      String fname = this.identityKeyStore == null ? "unspecified" : this.identityKeyStore;
      if (this.identityKeyStore != null) {
         ksFile = new File(this.identityKeyStore);
         if (ksFile.exists()) {
            ks = WLSKeyStoreFactory.getKeyStoreInstance(KERNEL_ID, this.ksType, ksFile.getAbsolutePath(), this.identityKeyStorePassphrase.toCharArray());
         }
      }

      if (ks == null) {
         WTCLogger.logErrorInvalidPrivateKeyStoreInfo(fname, method);
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoSSLSocketFactory/getServerSocketFactory(10)/bad key store");
         }

         throw new IOException("Problem with private key store " + fname);
      } else {
         try {
            key = ks.getKey(this.identityKeyAlias, this.identityKeyPassphrase.toCharArray());
            cert = ks.getCertificateChain(this.identityKeyAlias);
         } catch (Exception var15) {
            if (traceEnabled) {
               var15.printStackTrace();
            }
         }

         if (key != null && key instanceof PrivateKey && cert instanceof Certificate[]) {
            X509Certificate[] x509Cert = new X509Certificate[cert.length];

            for(int i = 0; i < x509Cert.length; ++i) {
               x509Cert[i] = (X509Certificate)cert[i];
            }

            sslContext.addIdentity(x509Cert, (PrivateKey)key);
            KeyStore ks2 = null;
            ksFile = null;
            if (this.trustKeyStore == null && this.trustKeyStorePassphrase == null && this.trustKsType == null) {
               try {
                  sslContext.addTrustedCA(SSLContextManager.getServerTrustedCAs());
               } catch (Exception var14) {
                  WTCLogger.logErrorInvalidServerTrustCertificate(method);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoSSLSocketFactory/getServerSocketFactory(50)/bad trust cert");
                  }

                  throw new IOException(var14.getMessage());
               }
            } else {
               fname = this.trustKeyStore == null ? "unspecified" : this.trustKeyStore;
               if (this.trustKeyStore != null) {
                  ksFile = new File(fname);
                  if (ksFile.exists()) {
                     ks2 = WLSKeyStoreFactory.getKeyStoreInstance(KERNEL_ID, this.trustKsType, ksFile.getAbsolutePath(), this.trustKeyStorePassphrase.toCharArray());
                  }
               }

               if (ks2 == null) {
                  WTCLogger.logErrorInvalidTrustCertStoreInfo(fname, method);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoSSLSocketFactory/getServerSocketFactory(30)/bad trust store");
                  }

                  throw new IOException("Problem with trust certificate store " + fname);
               }

               try {
                  X509Certificate[] certs = null;
                  Collection caCerts = new ArrayList();
                  caCerts.addAll(SSLCertUtility.getX509Certificates(ks2));
                  certs = (X509Certificate[])((X509Certificate[])caCerts.toArray(new X509Certificate[caCerts.size()]));
                  sslContext.addTrustedCA(certs);
               } catch (Exception var13) {
                  WTCLogger.logErrorInvalidTrustCertificate(fname, method);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoSSLSocketFactory/getServerSocketFactory(40)/bad trust cert");
                  }

                  throw new IOException(var13.getMessage());
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoSSLSocketFactory/getServerSocketFactory(60)/success");
            }

            SSLServerSocketFactory sf;
            if (SocketMuxer.getMuxer().isAsyncMuxer()) {
               sf = sslContext.getSSLNioServerSocketFactory();
            } else {
               sf = sslContext.getSSLServerSocketFactory();
            }

            return sf;
         } else {
            WTCLogger.logErrorInvalidPrivateKeyInfo(this.identityKeyAlias, fname, method);
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoSSLSocketFactory/getServerSocketFactory(20)/bad key or cert");
            }

            throw new IOException("Problem with key or certificate");
         }
      }
   }

   private javax.net.ssl.SSLSocketFactory getSocketFactory() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoSSLSocketFactory/getSocketFactory()");
      }

      SSLContextWrapper sslContext = SSLContextWrapper.getInstance();
      String method = "getSocketFactory";
      File ksFile = null;
      KeyStore ks = null;
      Key key = null;
      Certificate[] cert = null;
      String fname = this.identityKeyStore == null ? "unspecified" : this.identityKeyStore;
      if (this.identityKeyStore != null) {
         ksFile = new File(this.identityKeyStore);
         if (ksFile.exists()) {
            ks = WLSKeyStoreFactory.getKeyStoreInstance(KERNEL_ID, this.ksType, ksFile.getAbsolutePath(), this.identityKeyStorePassphrase.toCharArray());
         }
      }

      if (ks == null) {
         WTCLogger.logErrorInvalidPrivateKeyStoreInfo(fname, method);
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoSSLSocketFactory/getSocketFactory(10)/bad key store");
         }

         throw new IOException("Problem with private key store " + fname);
      } else {
         try {
            key = ks.getKey(this.identityKeyAlias, this.identityKeyPassphrase.toCharArray());
            cert = ks.getCertificateChain(this.identityKeyAlias);
         } catch (Exception var15) {
            if (traceEnabled) {
               var15.printStackTrace();
            }
         }

         if (key != null && key instanceof PrivateKey && cert instanceof Certificate[]) {
            X509Certificate[] x509Cert = new X509Certificate[cert.length];

            for(int i = 0; i < x509Cert.length; ++i) {
               x509Cert[i] = (X509Certificate)cert[i];
            }

            sslContext.addIdentity(x509Cert, (PrivateKey)key);
            KeyStore ks2 = null;
            ksFile = null;
            X509Certificate[] certs;
            if (this.trustKeyStore == null && this.trustKeyStorePassphrase == null && this.trustKsType == null) {
               try {
                  certs = SSLContextManager.getServerTrustedCAs();
                  sslContext.addTrustedCA(certs);
               } catch (Exception var13) {
                  WTCLogger.logErrorInvalidServerTrustCertificate(method);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoSSLSocketFactory/getSocketFactory(50)/bad trust cert");
                  }

                  throw new IOException(var13.getMessage());
               }
            } else {
               fname = this.trustKeyStore == null ? "unspecified" : this.trustKeyStore;
               if (this.trustKeyStore != null) {
                  ksFile = new File(fname);
                  if (ksFile.exists()) {
                     ks2 = WLSKeyStoreFactory.getKeyStoreInstance(KERNEL_ID, this.trustKsType, ksFile.getAbsolutePath(), this.trustKeyStorePassphrase.toCharArray());
                  }
               }

               if (ks2 == null) {
                  WTCLogger.logErrorInvalidTrustCertStoreInfo(fname, method);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoSSLSocketFactory/getSocketFactory(30)/bad trust store");
                  }

                  throw new IOException("Problem with trust certiticate store " + fname);
               }

               try {
                  certs = null;
                  Collection caCerts = new ArrayList();
                  caCerts.addAll(SSLCertUtility.getX509Certificates(ks2));
                  certs = (X509Certificate[])((X509Certificate[])caCerts.toArray(new X509Certificate[caCerts.size()]));
                  sslContext.addTrustedCA(certs);
               } catch (Exception var14) {
                  WTCLogger.logErrorInvalidTrustCertificate(fname, method);
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoSSLSocketFactory/getSocketFactory(40)/bad trust cert");
                  }

                  throw new IOException(var14.getMessage());
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoSSLSocketFactory/getSocketFactory(60)/get Factory");
            }

            return sslContext.getSSLSocketFactory();
         } else {
            WTCLogger.logErrorInvalidPrivateKeyInfo(this.identityKeyAlias, fname, method);
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoSSLSocketFactory/getSocketFactory(20)/bad key store");
            }

            throw new IOException("Problem with key or certificate");
         }
      }
   }

   public static String[] getCiphers(int minEncryptBits, int maxEncryptBits) {
      int cipherSize = 0;
      boolean nullCiphersAllowed = false;
      if (minEncryptBits == 0) {
         SSLMBean sslMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getSSL();
         nullCiphersAllowed = sslMBean.isAllowUnencryptedNullCipher();
         if (!nullCiphersAllowed) {
            if (maxEncryptBits == 0) {
               WTCLogger.logErrorNoNullCiphersAllowed();
               return null;
            }

            WTCLogger.logWarnNoNullCiphersAllowed();
         }
      }

      if (minEncryptBits <= 256 && maxEncryptBits >= 256) {
         cipherSize += CIPHER256.length;
      }

      if (minEncryptBits <= 128 && maxEncryptBits >= 128) {
         cipherSize += CIPHER128.length;
      }

      if (minEncryptBits <= 112 && maxEncryptBits >= 112) {
         cipherSize += CIPHER112.length;
      }

      if (minEncryptBits <= 56 && maxEncryptBits >= 56) {
         cipherSize += CIPHER56.length;
      }

      if (nullCiphersAllowed) {
         cipherSize += CIPHER0.length;
      }

      String[] ciphers = new String[cipherSize];
      int i = 0;
      int j;
      if (minEncryptBits <= 256 && maxEncryptBits >= 256) {
         for(j = 0; j < CIPHER256.length; ++j) {
            ciphers[i] = CIPHER256[j];
            ++i;
         }
      }

      if (minEncryptBits <= 128 && maxEncryptBits >= 128) {
         for(j = 0; j < CIPHER128.length; ++j) {
            ciphers[i] = CIPHER128[j];
            ++i;
         }
      }

      if (minEncryptBits <= 112 && maxEncryptBits >= 112) {
         for(j = 0; j < CIPHER112.length; ++j) {
            ciphers[i] = CIPHER112[j];
            ++i;
         }
      }

      if (minEncryptBits <= 56 && maxEncryptBits >= 56) {
         for(j = 0; j < CIPHER56.length; ++j) {
            ciphers[i] = CIPHER56[j];
            ++i;
         }
      }

      if (nullCiphersAllowed) {
         for(j = 0; j < CIPHER0.length; ++j) {
            ciphers[i] = CIPHER0[j];
            ++i;
         }
      }

      return ciphers;
   }
}
