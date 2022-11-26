package weblogic.net.http;

import java.net.Proxy;
import java.net.URL;

class KeepAliveKey {
   private String protocol;
   private String host;
   private int port;
   private Object clientInfo;
   private Proxy proxy;

   public KeepAliveKey(URL url, Object info) {
      this(url, info, (Proxy)null);
   }

   public KeepAliveKey(URL url, Object info, Proxy proxy) {
      this.protocol = null;
      this.host = null;
      this.port = 0;
      this.clientInfo = null;
      this.proxy = null;
      this.protocol = url.getProtocol();
      this.host = url.getHost();
      this.port = url.getPort();
      this.clientInfo = info;
      this.proxy = proxy == null ? Proxy.NO_PROXY : proxy;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof KeepAliveKey)) {
         return false;
      } else {
         KeepAliveKey kae = (KeepAliveKey)obj;
         return this.host.equals(kae.host) && this.port == kae.port && this.protocol.equals(kae.protocol) && (this.clientInfo == kae.clientInfo || this.clientInfo != null && this.clientInfo.equals(kae.clientInfo)) && this.proxy.equals(kae.proxy);
      }
   }

   public int hashCode() {
      int hc = (this.protocol.hashCode() * 31 + this.host.hashCode()) * 31 + this.port;
      hc *= 31;
      hc += this.proxy.hashCode();
      return hc;
   }

   public String toString() {
      return "protocol: " + this.protocol + ", host: " + this.host + ", port: " + this.port + ", clientInfo: " + this.clientInfo + ", proxy: " + this.proxy;
   }
}
