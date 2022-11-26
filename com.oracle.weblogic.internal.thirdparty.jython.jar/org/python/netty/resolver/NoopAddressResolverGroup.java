package org.python.netty.resolver;

import org.python.netty.util.concurrent.EventExecutor;

public final class NoopAddressResolverGroup extends AddressResolverGroup {
   public static final NoopAddressResolverGroup INSTANCE = new NoopAddressResolverGroup();

   private NoopAddressResolverGroup() {
   }

   protected AddressResolver newResolver(EventExecutor executor) throws Exception {
      return new NoopAddressResolver(executor);
   }
}
