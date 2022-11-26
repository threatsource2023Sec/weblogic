package org.python.netty.resolver;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

public final class DefaultHostsFileEntriesResolver implements HostsFileEntriesResolver {
   private final Map inet4Entries;
   private final Map inet6Entries;

   public DefaultHostsFileEntriesResolver() {
      this(HostsFileParser.parseSilently());
   }

   DefaultHostsFileEntriesResolver(HostsFileEntries entries) {
      this.inet4Entries = entries.inet4Entries();
      this.inet6Entries = entries.inet6Entries();
   }

   public InetAddress address(String inetHost, ResolvedAddressTypes resolvedAddressTypes) {
      String normalized = this.normalize(inetHost);
      switch (resolvedAddressTypes) {
         case IPV4_ONLY:
            return (InetAddress)this.inet4Entries.get(normalized);
         case IPV6_ONLY:
            return (InetAddress)this.inet6Entries.get(normalized);
         case IPV4_PREFERRED:
            Inet4Address inet4Address = (Inet4Address)this.inet4Entries.get(normalized);
            return (InetAddress)(inet4Address != null ? inet4Address : (InetAddress)this.inet6Entries.get(normalized));
         case IPV6_PREFERRED:
            Inet6Address inet6Address = (Inet6Address)this.inet6Entries.get(normalized);
            return (InetAddress)(inet6Address != null ? inet6Address : (InetAddress)this.inet4Entries.get(normalized));
         default:
            throw new IllegalArgumentException("Unknown ResolvedAddressTypes " + resolvedAddressTypes);
      }
   }

   String normalize(String inetHost) {
      return inetHost.toLowerCase(Locale.ENGLISH);
   }
}
