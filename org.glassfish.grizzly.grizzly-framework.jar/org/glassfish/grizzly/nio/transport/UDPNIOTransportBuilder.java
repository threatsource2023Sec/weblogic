package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.NIOTransportBuilder;
import org.glassfish.grizzly.nio.NIOTransport;

public class UDPNIOTransportBuilder extends NIOTransportBuilder {
   protected UDPNIOTransportBuilder(Class transportClass) {
      super(transportClass);
   }

   public static UDPNIOTransportBuilder newInstance() {
      return new UDPNIOTransportBuilder(UDPNIOTransport.class);
   }

   public UDPNIOTransport build() {
      return (UDPNIOTransport)super.build();
   }

   protected UDPNIOTransportBuilder getThis() {
      return this;
   }

   protected NIOTransport create(String name) {
      return new UDPNIOTransport(name);
   }
}
