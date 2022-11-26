package weblogic.iiop.protocol;

import java.net.InetSocketAddress;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.protocol.ServerIdentity;
import weblogic.utils.net.InetAddressHelper;

public final class ListenPoint {
   public static final short PORT_DISABLED = 0;
   public static final ListenPoint NULL_KEY = new ListenPoint((String)null, -1);
   private String address;
   private int port;
   private int connectedPort;
   private InetSocketAddress inAddr;

   public ListenPoint(String address, int port) {
      if (InetAddressHelper.isIPV6Address(address) && !address.contains("[")) {
         address = "[" + address + "]";
      }

      this.address = address;
      this.connectedPort = this.port = port;
      if (port > 0 && port <= 65535 && address != null) {
         this.inAddr = new InetSocketAddress(address, port);
      }

   }

   public ListenPoint(String address, int port, int connectedPort) {
      if (InetAddressHelper.isIPV6Address(address) && !address.contains("[")) {
         address = "[" + address + "]";
      }

      this.address = address;
      this.connectedPort = connectedPort;
      this.port = port;
      if (port > 0 && port <= 65535 && address != null) {
         this.inAddr = new InetSocketAddress(address, port);
      }

   }

   public ListenPoint(InputStream in) {
      this.read(in);
   }

   public int getPort() {
      return this.port;
   }

   public int getConnectedPort() {
      return this.connectedPort;
   }

   public String getAddress() {
      return this.address;
   }

   public int hashCode() {
      return this.inAddr != null ? this.inAddr.hashCode() : this.address.hashCode() ^ this.port;
   }

   public boolean equals(Object o) {
      if (!(o instanceof ListenPoint)) {
         return false;
      } else {
         ListenPoint other = (ListenPoint)o;
         return this.inAddr != null && this.inAddr.equals(other.inAddr) || other.port == this.port & other.address.equals(this.address);
      }
   }

   private void read(InputStream in) {
      this.address = in.read_string();
      this.port = ProtocolUtils.readUnsignedShort(in);
      if (this.port > 0 && this.address != null) {
         this.inAddr = new InetSocketAddress(this.address, this.port);
      }

   }

   public void write(OutputStream out) {
      out.write_string(this.address);
      out.write_ushort(this.port < 0 ? 0 : (short)this.port);
   }

   public void writeForChannel(OutputStream out, ServerIdentity target) {
      this.getReplacement(out, target).write(out);
   }

   public ListenPoint getReplacement(OutputStream out, ServerIdentity target) {
      return IiopProtocolFacade.getReplacement(this, out, target);
   }

   public ListenPoint replaceFromChannel(InputStream in) {
      return IiopProtocolFacade.getReplacement(this, in);
   }

   public String toString() {
      return this.address + ":" + this.port + "(" + this.connectedPort + ")";
   }

   public InetSocketAddress getSocketAddress() {
      return this.inAddr;
   }
}
