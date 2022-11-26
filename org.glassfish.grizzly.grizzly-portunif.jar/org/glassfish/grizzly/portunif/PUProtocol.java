package org.glassfish.grizzly.portunif;

import org.glassfish.grizzly.filterchain.FilterChain;

public class PUProtocol {
   private final ProtocolFinder finder;
   private final FilterChain filterChain;

   public PUProtocol(ProtocolFinder finder, FilterChain processor) {
      this.finder = finder;
      this.filterChain = processor;
   }

   public ProtocolFinder getProtocolFinder() {
      return this.finder;
   }

   public FilterChain getFilterChain() {
      return this.filterChain;
   }
}
