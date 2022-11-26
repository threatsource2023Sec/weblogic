package org.python.netty.resolver;

import org.python.netty.util.concurrent.EventExecutor;

public final class DefaultAddressResolverGroup extends AddressResolverGroup {
   public static final DefaultAddressResolverGroup INSTANCE = new DefaultAddressResolverGroup();

   private DefaultAddressResolverGroup() {
   }

   protected AddressResolver newResolver(EventExecutor executor) throws Exception {
      return (new DefaultNameResolver(executor)).asAddressResolver();
   }
}
