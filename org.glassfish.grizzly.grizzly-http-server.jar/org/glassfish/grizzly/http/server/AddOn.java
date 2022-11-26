package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;

public interface AddOn {
   void setup(NetworkListener var1, FilterChainBuilder var2);
}
