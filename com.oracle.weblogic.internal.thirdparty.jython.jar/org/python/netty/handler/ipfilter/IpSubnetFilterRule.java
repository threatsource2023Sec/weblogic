package org.python.netty.handler.ipfilter;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.python.netty.util.internal.SocketUtils;

public final class IpSubnetFilterRule implements IpFilterRule {
   private final IpFilterRule filterRule;

   public IpSubnetFilterRule(String ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
      try {
         this.filterRule = selectFilterRule(SocketUtils.addressByName(ipAddress), cidrPrefix, ruleType);
      } catch (UnknownHostException var5) {
         throw new IllegalArgumentException("ipAddress", var5);
      }
   }

   public IpSubnetFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
      this.filterRule = selectFilterRule(ipAddress, cidrPrefix, ruleType);
   }

   private static IpFilterRule selectFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
      if (ipAddress == null) {
         throw new NullPointerException("ipAddress");
      } else if (ruleType == null) {
         throw new NullPointerException("ruleType");
      } else if (ipAddress instanceof Inet4Address) {
         return new Ip4SubnetFilterRule((Inet4Address)ipAddress, cidrPrefix, ruleType);
      } else if (ipAddress instanceof Inet6Address) {
         return new Ip6SubnetFilterRule((Inet6Address)ipAddress, cidrPrefix, ruleType);
      } else {
         throw new IllegalArgumentException("Only IPv4 and IPv6 addresses are supported");
      }
   }

   public boolean matches(InetSocketAddress remoteAddress) {
      return this.filterRule.matches(remoteAddress);
   }

   public IpFilterRuleType ruleType() {
      return this.filterRule.ruleType();
   }

   private static final class Ip6SubnetFilterRule implements IpFilterRule {
      private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1L);
      private final BigInteger networkAddress;
      private final BigInteger subnetMask;
      private final IpFilterRuleType ruleType;

      private Ip6SubnetFilterRule(Inet6Address ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
         if (cidrPrefix >= 0 && cidrPrefix <= 128) {
            this.subnetMask = prefixToSubnetMask(cidrPrefix);
            this.networkAddress = ipToInt(ipAddress).and(this.subnetMask);
            this.ruleType = ruleType;
         } else {
            throw new IllegalArgumentException(String.format("IPv6 requires the subnet prefix to be in range of [0,128]. The prefix was: %d", cidrPrefix));
         }
      }

      public boolean matches(InetSocketAddress remoteAddress) {
         InetAddress inetAddress = remoteAddress.getAddress();
         if (inetAddress instanceof Inet6Address) {
            BigInteger ipAddress = ipToInt((Inet6Address)inetAddress);
            return ipAddress.and(this.subnetMask).equals(this.networkAddress);
         } else {
            return false;
         }
      }

      public IpFilterRuleType ruleType() {
         return this.ruleType;
      }

      private static BigInteger ipToInt(Inet6Address ipAddress) {
         byte[] octets = ipAddress.getAddress();

         assert octets.length == 16;

         return new BigInteger(octets);
      }

      private static BigInteger prefixToSubnetMask(int cidrPrefix) {
         return MINUS_ONE.shiftLeft(128 - cidrPrefix);
      }

      // $FF: synthetic method
      Ip6SubnetFilterRule(Inet6Address x0, int x1, IpFilterRuleType x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class Ip4SubnetFilterRule implements IpFilterRule {
      private final int networkAddress;
      private final int subnetMask;
      private final IpFilterRuleType ruleType;

      private Ip4SubnetFilterRule(Inet4Address ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
         if (cidrPrefix >= 0 && cidrPrefix <= 32) {
            this.subnetMask = prefixToSubnetMask(cidrPrefix);
            this.networkAddress = ipToInt(ipAddress) & this.subnetMask;
            this.ruleType = ruleType;
         } else {
            throw new IllegalArgumentException(String.format("IPv4 requires the subnet prefix to be in range of [0,32]. The prefix was: %d", cidrPrefix));
         }
      }

      public boolean matches(InetSocketAddress remoteAddress) {
         InetAddress inetAddress = remoteAddress.getAddress();
         if (inetAddress instanceof Inet4Address) {
            int ipAddress = ipToInt((Inet4Address)inetAddress);
            return (ipAddress & this.subnetMask) == this.networkAddress;
         } else {
            return false;
         }
      }

      public IpFilterRuleType ruleType() {
         return this.ruleType;
      }

      private static int ipToInt(Inet4Address ipAddress) {
         byte[] octets = ipAddress.getAddress();

         assert octets.length == 4;

         return (octets[0] & 255) << 24 | (octets[1] & 255) << 16 | (octets[2] & 255) << 8 | octets[3] & 255;
      }

      private static int prefixToSubnetMask(int cidrPrefix) {
         return (int)(-1L << 32 - cidrPrefix & -1L);
      }

      // $FF: synthetic method
      Ip4SubnetFilterRule(Inet4Address x0, int x1, IpFilterRuleType x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
