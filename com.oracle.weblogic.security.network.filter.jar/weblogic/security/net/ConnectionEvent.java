package weblogic.security.net;

import java.net.InetAddress;
import java.net.Socket;
import java.util.EventObject;

public final class ConnectionEvent extends EventObject {
   private static final long serialVersionUID = -8861858081041858122L;
   private InetAddress remAddr;
   private int remPort;
   private InetAddress localAddr;
   private int localPort;
   private String protocol;

   public ConnectionEvent(Socket s, String protocol) {
      this(s.getInetAddress(), s.getPort(), s.getLocalAddress(), s.getLocalPort(), protocol);
   }

   public ConnectionEvent(InetAddress remAddr, int remPort, InetAddress localAddr, int localPort, String protocol) {
      super(ConnectionFilterImpl.impl);
      this.remAddr = remAddr;
      this.remPort = remPort;
      this.localAddr = localAddr;
      this.localPort = localPort;
      this.protocol = protocol;
   }

   public InetAddress getRemoteAddress() {
      return this.remAddr;
   }

   public int getRemotePort() {
      return this.remPort;
   }

   public InetAddress getLocalAddress() {
      return this.localAddr;
   }

   public int getLocalPort() {
      return this.localPort;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public int hashCode() {
      return this.remPort ^ this.localPort ^ this.remAddr.hashCode() ^ this.localAddr.hashCode() ^ (this.protocol != null ? this.protocol.hashCode() : 0);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof ConnectionEvent)) {
         return false;
      } else {
         boolean var10000;
         label37: {
            ConnectionEvent that = (ConnectionEvent)obj;
            if (this.remPort == that.remPort && this.localPort == that.localPort && this.remAddr.equals(that.remAddr) && this.localAddr.equals(that.localAddr)) {
               if (this.protocol != null) {
                  if (this.protocol.equals(that.protocol)) {
                     break label37;
                  }
               } else if (that.protocol == null) {
                  break label37;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }
}
