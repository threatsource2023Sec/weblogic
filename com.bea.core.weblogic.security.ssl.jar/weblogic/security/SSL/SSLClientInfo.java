package weblogic.security.SSL;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.SSLClientInfoService;
import weblogic.security.utils.InputStreamsCloner;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLContextWrapper;
import weblogic.security.utils.SSLSetup;
import weblogic.utils.Hex;

@Service
@PerLookup
public final class SSLClientInfo implements SSLClientInfoService {
   private String expectedName;
   private HostnameVerifier hostnameVerifier;
   private TrustManager trustManager;
   private String clientKeyPassword;
   private PrivateKey clientPrivateKey;
   private InputStreamsCloner clientCertCloner;
   private X509Certificate[] clientCertChain;
   private X509Certificate[] trustedCA;
   private byte[][] rootCAfingerprints;
   private javax.net.ssl.SSLContext m_sslContext;
   private transient javax.net.ssl.SSLSocketFactory socketFactory;
   private boolean bNio;

   public SSLClientInfo(boolean bNio) {
      this.expectedName = null;
      this.clientKeyPassword = null;
      this.clientPrivateKey = null;
      this.clientCertCloner = null;
      this.clientCertChain = null;
      this.trustedCA = null;
      this.rootCAfingerprints = (byte[][])null;
      this.m_sslContext = null;
      this.bNio = false;
      this.bNio = bNio;
   }

   public SSLClientInfo() {
      this(false);
   }

   public void setNio(boolean enableNio) {
      if (enableNio != this.bNio) {
         this.socketFactory = null;
         this.bNio = enableNio;
      }

   }

   public boolean isNioSet() {
      return this.bNio;
   }

   public synchronized javax.net.ssl.SSLSocketFactory getSSLSocketFactory() throws SocketException {
      if (this.socketFactory == null) {
         if (this.m_sslContext != null) {
            this.socketFactory = this.m_sslContext.getSocketFactory();
         } else if (!this.bNio) {
            this.socketFactory = SSLSetup.getSSLContext(this).getSSLSocketFactory();
         } else {
            this.socketFactory = SSLSetup.getSSLContext(this).getSSLNioSocketFactory();
         }
      }

      return this.socketFactory;
   }

   public boolean isEmpty() {
      return this.expectedName == null && this.hostnameVerifier == null && this.trustManager == null && this.trustedCA == null && this.clientKeyPassword == null && this.clientPrivateKey == null && this.clientCertChain == null && this.clientCertCloner == null && this.rootCAfingerprints == null;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj instanceof SSLClientInfo) {
         SSLClientInfo clientInfo = (SSLClientInfo)obj;
         if (this.isEmpty()) {
            return clientInfo.isEmpty();
         } else {
            return !clientInfo.isEmpty() && equals((Object)this.expectedName, (Object)clientInfo.expectedName) && equals((Object)this.hostnameVerifier, (Object)clientInfo.hostnameVerifier) && equals((Object)this.trustManager, (Object)clientInfo.trustManager) && equals((Object)this.trustedCA, (Object)clientInfo.trustedCA) && equals((Object)this.clientKeyPassword, (Object)clientInfo.clientKeyPassword) && equals((Object)this.clientPrivateKey, (Object)clientInfo.clientPrivateKey) && Arrays.equals(this.clientCertChain, clientInfo.clientCertChain) && equals((Object)this.clientCertCloner, (Object)clientInfo.clientCertCloner) && equals(this.rootCAfingerprints, this.rootCAfingerprints);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.isEmpty()) {
         return 0;
      } else {
         int hash = 1;
         hash = hash * 31 + getHashCode((Object)this.expectedName);
         hash = hash * 31 + getHashCode((Object)this.hostnameVerifier);
         hash = hash * 31 + getHashCode((Object)this.trustManager);
         hash = hash * 31 + getHashCode((Object[])this.trustedCA);
         hash = hash * 31 + getHashCode((Object)this.clientKeyPassword);
         hash = hash * 31 + getHashCode((Object)this.clientPrivateKey);
         hash = hash * 31 + getHashCode((Object[])this.clientCertChain);
         hash = hash * 31 + getHashCode((Object)this.clientCertCloner);
         hash = hash * 31 + getHashCode(this.rootCAfingerprints);
         return hash;
      }
   }

   private static final boolean equals(byte[][] obj1, byte[][] obj2) {
      if (obj1 == obj2) {
         return true;
      } else if (obj1 != null && obj2 != null) {
         for(int i = 0; i < obj1.length; ++i) {
            if (!Arrays.equals(obj1[i], obj2[i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static final boolean equals(Object obj1, Object obj2) {
      return obj1 == obj2 || obj1 != null && obj2 != null && obj1.equals(obj2);
   }

   private static final int getHashCode(byte[][] obj) {
      if (obj == null) {
         return 0;
      } else {
         int hash = 1;

         for(int i = 0; i < obj.length; ++i) {
            int h = 1;

            for(int j = 0; j < obj[i].length; ++j) {
               h = h * 31 + obj[i][j];
            }

            hash = hash * 31 + h;
         }

         return hash;
      }
   }

   private static final int getHashCode(Object[] obj) {
      if (obj == null) {
         return 0;
      } else {
         int hash = 1;

         for(int i = 0; i < obj.length; ++i) {
            hash = hash * 31 + getHashCode(obj[i]);
         }

         return hash;
      }
   }

   private static final int getHashCode(Object obj) {
      return obj == null ? 0 : obj.hashCode();
   }

   public InputStream[] getSSLClientCertificate() {
      if (this.clientCertCloner != null) {
         try {
            return this.clientCertCloner.cloneStreams();
         } catch (IOException var2) {
            SecurityLogger.logStackTrace(var2);
         }
      }

      return null;
   }

   public final synchronized void setSSLClientCertificate(InputStream[] chain) {
      this.socketFactory = null;
      if (chain != null && chain.length > 0) {
         this.clientCertCloner = new InputStreamsCloner(chain);
      } else {
         this.clientCertCloner = null;
      }

   }

   public synchronized void setSSLClientCertificateCloner(InputStreamsCloner sslCC) {
      this.socketFactory = null;
      this.clientCertCloner = sslCC;
   }

   /** @deprecated */
   @Deprecated
   public String getExpectedName() {
      return this.expectedName;
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setExpectedName(String s) {
      this.socketFactory = null;
      this.expectedName = s;
   }

   public HostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   public synchronized void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
      this.socketFactory = null;
      this.hostnameVerifier = hostnameVerifier;
   }

   public TrustManager getTrustManager() {
      return this.trustManager;
   }

   public synchronized void setTrustManager(TrustManager trustManager) {
      this.socketFactory = null;
      this.trustManager = trustManager;
   }

   public byte[][] getRootCAfingerprints() {
      return this.rootCAfingerprints;
   }

   public synchronized void setRootCAfingerprints(byte[][] fingerprints) {
      this.socketFactory = null;
      this.rootCAfingerprints = fingerprints;
   }

   public synchronized void setRootCAfingerprints(String fingerprints) {
      this.socketFactory = null;
      if (fingerprints == null) {
         this.rootCAfingerprints = (byte[][])null;
      } else {
         StringTokenizer tokenizer = new StringTokenizer(fingerprints, ", \t");
         byte[][] res = new byte[tokenizer.countTokens()][];

         for(int i = 0; i < res.length; ++i) {
            res[i] = Hex.fromHexString(tokenizer.nextToken());
         }

         this.rootCAfingerprints = res;
      }
   }

   public final synchronized void setSSLClientKeyPassword(String pass) {
      this.socketFactory = null;
      this.clientKeyPassword = pass;
   }

   public final String getSSLClientKeyPassword() {
      return this.clientKeyPassword;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer("SSLClientInfo: rootCAFingerprints ");
      sb.append(this.rootCAfingerprints != null ? String.valueOf(this.rootCAfingerprints.length) : "null");
      sb.append(", expectedServerName ").append(this.expectedName);
      sb.append(", key/certificates ");
      sb.append(this.clientCertCloner != null ? String.valueOf(this.clientCertCloner.size()) : "null");
      sb.append(", HostnameVerifier ");
      sb.append(this.hostnameVerifier != null ? this.hostnameVerifier.getClass().getName() : "null");
      sb.append(", TrustManager ");
      sb.append(this.trustManager != null ? this.trustManager.getClass().getName() : "null");
      sb.append(", ClientCertChain ");
      sb.append(this.clientCertChain != null ? String.valueOf(this.clientCertChain.length) : "null");
      sb.append(", ClientPrivateKey ").append(this.clientPrivateKey);
      return sb.toString();
   }

   public final void setSSLContext(javax.net.ssl.SSLContext sslctx) {
      this.m_sslContext = sslctx;
   }

   public synchronized void loadLocalIdentity(InputStream certStream, InputStream keyStream, char[] password) {
      this.socketFactory = null;

      try {
         SSLContextWrapper tempSSLCtx = SSLSetup.getSSLContext();
         this.clientCertChain = SSLCertUtility.inputCertificateChain(tempSSLCtx, certStream);
         this.clientPrivateKey = tempSSLCtx.inputPrivateKey(keyStream, password);
      } catch (SocketException var5) {
         SSLSetup.info(var5, "Problem getting SSLContext");
      } catch (KeyManagementException var6) {
         SSLSetup.info(var6, "Problem reading certificate/key");
      } catch (IOException var7) {
         SSLSetup.info(var7, "Problem reading certificate/key");
      }

   }

   public synchronized void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      this.socketFactory = null;

      try {
         this.clientCertChain = SSLCertUtility.toJavaX5092(certs);
      } catch (Exception var4) {
         throw new IllegalArgumentException("Cannot convert to X509 certificates\n" + var4.getMessage());
      }

      this.clientPrivateKey = privateKey;
   }

   public X509Certificate[] getClientLocalIdentityCert() {
      return this.clientCertChain;
   }

   public PrivateKey getClientLocalIdentityKey() {
      return this.clientPrivateKey;
   }

   public boolean isClientCertAvailable() {
      return this.clientCertCloner != null && this.clientCertCloner.size() > 0;
   }

   public boolean isLocalIdentitySet() {
      return this.clientCertChain != null && this.clientCertChain.length > 0;
   }
}
