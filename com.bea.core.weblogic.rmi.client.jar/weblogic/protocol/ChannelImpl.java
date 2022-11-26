package weblogic.protocol;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import weblogic.rmi.spi.Channel;

public class ChannelImpl implements Channel {
   private final InetSocketAddress address;
   private final String protocol;
   private final int port;
   private final boolean secure;

   public ChannelImpl(String host, int port, String protocol) {
      this.port = port;
      this.address = port < 0 ? new InetSocketAddress(host, 0) : new InetSocketAddress(host, port);
      this.protocol = protocol;
      this.secure = false;
   }

   public InetAddress getInetAddress() {
      return this.address.getAddress();
   }

   public InetSocketAddress getPublicInetAddress() {
      return this.address;
   }

   public String getProtocolPrefix() {
      return this.protocol;
   }

   public boolean supportsTLS() {
      return this.secure;
   }

   public String getPublicAddress() {
      return this.address.getHostName();
   }

   public int getPublicPort() {
      return this.address.getPort() == 0 ? this.port : this.address.getPort();
   }

   public String toString() {
      return this.protocol + "://" + this.getPublicAddress() + ":" + this.getPublicPort();
   }
}
