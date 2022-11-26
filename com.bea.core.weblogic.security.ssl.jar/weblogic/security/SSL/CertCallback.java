package weblogic.security.SSL;

import java.security.PrivateKey;
import java.security.cert.Certificate;

/** @deprecated */
@Deprecated
public final class CertCallback {
   SSLClientInfo sslInfo;
   private boolean bSSLNio = false;
   private String srcHost;
   private int srcPort = -1;
   private String destHost;
   private int destPort = -1;

   public CertCallback() {
      this.bSSLNio = false;
      this.sslInfo = null;
   }

   public CertCallback(boolean bNio) {
      this.bSSLNio = bNio;
      this.sslInfo = null;
   }

   public CertCallback(boolean bNio, String srcHost, int srcPort, String destHost, int destPort) {
      this.bSSLNio = bNio;
      this.sslInfo = null;
      this.destHost = destHost;
      this.destPort = destPort;
      this.srcHost = srcHost;
      this.srcPort = srcPort;
   }

   public boolean isNioConfigured() {
      return this.bSSLNio;
   }

   public void setSSLClientInfo(Certificate[] cert, PrivateKey key) {
      if (!this.bSSLNio) {
         this.sslInfo = new SSLClientInfo();
      } else {
         this.sslInfo = new SSLClientInfo(true);
      }

      this.sslInfo.loadLocalIdentity(cert, key);
   }

   public SSLClientInfo getSSLClientInfo() {
      return this.sslInfo;
   }

   public String getDestinationHost() {
      return this.destHost;
   }

   public int getDestinationPort() {
      return this.destPort;
   }

   public String getSourceHost() {
      return this.srcHost;
   }

   public int getSourcePort() {
      return this.srcPort;
   }
}
