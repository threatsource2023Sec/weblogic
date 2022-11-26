package weblogic.security.SSL;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public final class SSLContext {
   private SSLClientInfo clientInfo;
   private String protocol;

   public static SSLContext getInstance(String secureProtocol) {
      return new SSLContext(secureProtocol);
   }

   public SSLContext(String secureProtocol) {
      this.protocol = secureProtocol;
   }

   public SSLContext() {
      this.protocol = "https";
   }

   public void setTrustManager(TrustManager tm) {
      this.getClientInfo().setTrustManager(tm);
   }

   public void setHostnameVerifier(HostnameVerifier hv) {
      this.getClientInfo().setHostnameVerifier(hv);
   }

   /** @deprecated */
   @Deprecated
   public void loadLocalIdentity(InputStream[] streams) {
      if (streams != null) {
         this.getClientInfo().setSSLClientCertificate(streams);
      }

   }

   /** @deprecated */
   @Deprecated
   public void loadLocalIdentity(InputStream[] streams, String password) {
      if (streams != null) {
         this.getClientInfo().setSSLClientCertificate(streams);
         this.getClientInfo().setSSLClientKeyPassword(password);
      }

   }

   public void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      if (certs != null && privateKey != null) {
         this.getClientInfo().loadLocalIdentity(certs, privateKey);
      }

   }

   public SSLSocketFactory getSocketFactory() {
      return SSLSocketFactory.getInstance(this.clientInfo);
   }

   public SSLSocketFactory getNioSocketFactory() {
      return SSLNioSocketFactory.getInstance(this.clientInfo);
   }

   public String getProtocol() {
      return this.protocol;
   }

   public String getProvider() {
      return "weblogic.net";
   }

   protected SSLClientInfo getSSLClientInfo() {
      return this.clientInfo;
   }

   protected void setSSLClientInfo(SSLClientInfo sslCI) {
      this.clientInfo = sslCI;
   }

   private SSLClientInfo getClientInfo() {
      if (this.clientInfo == null) {
         this.clientInfo = new SSLClientInfo();
      }

      return this.clientInfo;
   }
}
