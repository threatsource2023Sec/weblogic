package org.glassfish.grizzly.portunif;

import org.glassfish.grizzly.filterchain.FilterChainContext;

public interface ProtocolFinder {
   Result find(PUContext var1, FilterChainContext var2);

   public static enum Result {
      FOUND,
      NOT_FOUND,
      NEED_MORE_DATA;
   }
}
