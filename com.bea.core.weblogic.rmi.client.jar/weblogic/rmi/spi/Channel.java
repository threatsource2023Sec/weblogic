package weblogic.rmi.spi;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public interface Channel {
   InetAddress getInetAddress();

   InetSocketAddress getPublicInetAddress();

   String getProtocolPrefix();

   boolean supportsTLS();

   String getPublicAddress();

   int getPublicPort();
}
