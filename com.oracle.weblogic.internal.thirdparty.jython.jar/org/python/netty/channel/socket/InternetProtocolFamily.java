package org.python.netty.channel.socket;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import org.python.netty.util.NetUtil;

public enum InternetProtocolFamily {
   IPv4(Inet4Address.class, 1, NetUtil.LOCALHOST4),
   IPv6(Inet6Address.class, 2, NetUtil.LOCALHOST6);

   private final Class addressType;
   private final int addressNumber;
   private final InetAddress localHost;

   private InternetProtocolFamily(Class addressType, int addressNumber, InetAddress localHost) {
      this.addressType = addressType;
      this.addressNumber = addressNumber;
      this.localHost = localHost;
   }

   public Class addressType() {
      return this.addressType;
   }

   public int addressNumber() {
      return this.addressNumber;
   }

   public InetAddress localhost() {
      return this.localHost;
   }

   public static InternetProtocolFamily of(InetAddress address) {
      if (address instanceof Inet4Address) {
         return IPv4;
      } else if (address instanceof Inet6Address) {
         return IPv6;
      } else {
         throw new IllegalArgumentException("address " + address + " not supported");
      }
   }
}
