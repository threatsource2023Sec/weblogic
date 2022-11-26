package org.python.google.common.net;

import java.io.Serializable;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Strings;

@Beta
@Immutable
@GwtCompatible
public final class HostAndPort implements Serializable {
   private static final int NO_PORT = -1;
   private final String host;
   private final int port;
   private final boolean hasBracketlessColons;
   private static final long serialVersionUID = 0L;

   private HostAndPort(String host, int port, boolean hasBracketlessColons) {
      this.host = host;
      this.port = port;
      this.hasBracketlessColons = hasBracketlessColons;
   }

   public String getHost() {
      return this.host;
   }

   public boolean hasPort() {
      return this.port >= 0;
   }

   public int getPort() {
      Preconditions.checkState(this.hasPort());
      return this.port;
   }

   public int getPortOrDefault(int defaultPort) {
      return this.hasPort() ? this.port : defaultPort;
   }

   public static HostAndPort fromParts(String host, int port) {
      Preconditions.checkArgument(isValidPort(port), "Port out of range: %s", port);
      HostAndPort parsedHost = fromString(host);
      Preconditions.checkArgument(!parsedHost.hasPort(), "Host has a port: %s", (Object)host);
      return new HostAndPort(parsedHost.host, port, parsedHost.hasBracketlessColons);
   }

   public static HostAndPort fromHost(String host) {
      HostAndPort parsedHost = fromString(host);
      Preconditions.checkArgument(!parsedHost.hasPort(), "Host has a port: %s", (Object)host);
      return parsedHost;
   }

   public static HostAndPort fromString(String hostPortString) {
      Preconditions.checkNotNull(hostPortString);
      String portString = null;
      boolean hasBracketlessColons = false;
      String host;
      int port;
      if (hostPortString.startsWith("[")) {
         String[] hostAndPort = getHostAndPortFromBracketedHost(hostPortString);
         host = hostAndPort[0];
         portString = hostAndPort[1];
      } else {
         port = hostPortString.indexOf(58);
         if (port >= 0 && hostPortString.indexOf(58, port + 1) == -1) {
            host = hostPortString.substring(0, port);
            portString = hostPortString.substring(port + 1);
         } else {
            host = hostPortString;
            hasBracketlessColons = port >= 0;
         }
      }

      port = -1;
      if (!Strings.isNullOrEmpty(portString)) {
         Preconditions.checkArgument(!portString.startsWith("+"), "Unparseable port number: %s", (Object)hostPortString);

         try {
            port = Integer.parseInt(portString);
         } catch (NumberFormatException var6) {
            throw new IllegalArgumentException("Unparseable port number: " + hostPortString);
         }

         Preconditions.checkArgument(isValidPort(port), "Port number out of range: %s", (Object)hostPortString);
      }

      return new HostAndPort(host, port, hasBracketlessColons);
   }

   private static String[] getHostAndPortFromBracketedHost(String hostPortString) {
      int colonIndex = false;
      int closeBracketIndex = false;
      Preconditions.checkArgument(hostPortString.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", (Object)hostPortString);
      int colonIndex = hostPortString.indexOf(58);
      int closeBracketIndex = hostPortString.lastIndexOf(93);
      Preconditions.checkArgument(colonIndex > -1 && closeBracketIndex > colonIndex, "Invalid bracketed host/port: %s", (Object)hostPortString);
      String host = hostPortString.substring(1, closeBracketIndex);
      if (closeBracketIndex + 1 == hostPortString.length()) {
         return new String[]{host, ""};
      } else {
         Preconditions.checkArgument(hostPortString.charAt(closeBracketIndex + 1) == ':', "Only a colon may follow a close bracket: %s", (Object)hostPortString);

         for(int i = closeBracketIndex + 2; i < hostPortString.length(); ++i) {
            Preconditions.checkArgument(Character.isDigit(hostPortString.charAt(i)), "Port must be numeric: %s", (Object)hostPortString);
         }

         return new String[]{host, hostPortString.substring(closeBracketIndex + 2)};
      }
   }

   public HostAndPort withDefaultPort(int defaultPort) {
      Preconditions.checkArgument(isValidPort(defaultPort));
      return !this.hasPort() && this.port != defaultPort ? new HostAndPort(this.host, defaultPort, this.hasBracketlessColons) : this;
   }

   public HostAndPort requireBracketsForIPv6() {
      Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", (Object)this.host);
      return this;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof HostAndPort)) {
         return false;
      } else {
         HostAndPort that = (HostAndPort)other;
         return Objects.equal(this.host, that.host) && this.port == that.port && this.hasBracketlessColons == that.hasBracketlessColons;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.host, this.port, this.hasBracketlessColons);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder(this.host.length() + 8);
      if (this.host.indexOf(58) >= 0) {
         builder.append('[').append(this.host).append(']');
      } else {
         builder.append(this.host);
      }

      if (this.hasPort()) {
         builder.append(':').append(this.port);
      }

      return builder.toString();
   }

   private static boolean isValidPort(int port) {
      return port >= 0 && port <= 65535;
   }
}
