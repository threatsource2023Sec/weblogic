package org.glassfish.grizzly.filterchain;

import java.io.IOException;

public interface Filter {
   void onAdded(FilterChain var1);

   void onRemoved(FilterChain var1);

   void onFilterChainChanged(FilterChain var1);

   NextAction handleRead(FilterChainContext var1) throws IOException;

   NextAction handleWrite(FilterChainContext var1) throws IOException;

   NextAction handleConnect(FilterChainContext var1) throws IOException;

   NextAction handleAccept(FilterChainContext var1) throws IOException;

   NextAction handleEvent(FilterChainContext var1, FilterChainEvent var2) throws IOException;

   NextAction handleClose(FilterChainContext var1) throws IOException;

   void exceptionOccurred(FilterChainContext var1, Throwable var2);
}
