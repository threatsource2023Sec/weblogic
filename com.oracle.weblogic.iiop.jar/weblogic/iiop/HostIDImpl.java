package weblogic.iiop;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;

public final class HostIDImpl implements HostID, Channel {
   private final InetSocketAddress address;
   private final String host;
   private final int port;

   public HostIDImpl(String host, int port) {
      this.host = host;
      this.port = port;
      this.address = new InetSocketAddress(host, port);
   }

   public int hashCode() {
      return this.getInetAddress().hashCode() ^ this.port;
   }

   public boolean equals(Object o) {
      if (!(o instanceof HostIDImpl)) {
         return false;
      } else {
         HostIDImpl other = (HostIDImpl)o;
         return other.getInetAddress().equals(this.getInetAddress()) && other.port == this.port;
      }
   }

   public int compareTo(Object object) {
      try {
         HostIDImpl other = (HostIDImpl)object;
         int inetHash = this.getInetAddress().hashCode();
         int otherInetHash = other.getInetAddress().hashCode();
         if (inetHash == otherInetHash) {
            if (this.port == other.port) {
               return 0;
            } else {
               return this.port < other.port ? -1 : 1;
            }
         } else {
            return inetHash < otherInetHash ? -1 : 1;
         }
      } catch (ClassCastException var5) {
         throw new AssertionError(object + " is not an instanceof HostIDImpl");
      }
   }

   public String getPublicAddress() {
      return this.host;
   }

   public InetSocketAddress getPublicInetAddress() {
      return this.address;
   }

   public int getPublicPort() {
      return this.port;
   }

   public String toString() {
      return this.host + "/" + this.port;
   }

   public boolean isLocal() {
      return false;
   }

   public String objectToString() {
      return this.toString();
   }

   public InetAddress getInetAddress() {
      if (this.address.isUnresolved()) {
         throw new AssertionError("Invalid address: " + this.address);
      } else {
         return this.address.getAddress();
      }
   }

   public String getProtocolPrefix() {
      return ProtocolHandlerIIOP.PROTOCOL_IIOP.getAsURLPrefix();
   }

   public boolean supportsTLS() {
      return false;
   }

   public ListenPoint getConnectionKey() {
      return new ListenPoint(this.host, this.port);
   }

   public String getServerName() {
      return "";
   }

   public String getHostURI() {
      return this.getInetAddress() instanceof Inet6Address && this.host.indexOf(58) != -1 && this.host.indexOf(91) == -1 ? "iiop://[" + this.host + "]:" + this.port : "iiop://" + this.host + ':' + this.port;
   }
}
