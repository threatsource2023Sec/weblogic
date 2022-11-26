package org.python.netty.bootstrap;

import java.net.SocketAddress;
import org.python.netty.resolver.AddressResolverGroup;

public final class BootstrapConfig extends AbstractBootstrapConfig {
   BootstrapConfig(Bootstrap bootstrap) {
      super(bootstrap);
   }

   public SocketAddress remoteAddress() {
      return ((Bootstrap)this.bootstrap).remoteAddress();
   }

   public AddressResolverGroup resolver() {
      return ((Bootstrap)this.bootstrap).resolver();
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(super.toString());
      buf.setLength(buf.length() - 1);
      buf.append(", resolver: ").append(this.resolver());
      SocketAddress remoteAddress = this.remoteAddress();
      if (remoteAddress != null) {
         buf.append(", remoteAddress: ").append(remoteAddress);
      }

      return buf.append(')').toString();
   }
}
