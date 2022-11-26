package org.python.netty.resolver;

import org.python.netty.util.concurrent.EventExecutor;

public abstract class InetNameResolver extends SimpleNameResolver {
   private volatile AddressResolver addressResolver;

   protected InetNameResolver(EventExecutor executor) {
      super(executor);
   }

   public AddressResolver asAddressResolver() {
      AddressResolver result = this.addressResolver;
      if (result == null) {
         synchronized(this) {
            result = this.addressResolver;
            if (result == null) {
               this.addressResolver = (AddressResolver)(result = new InetSocketAddressResolver(this.executor(), this));
            }
         }
      }

      return (AddressResolver)result;
   }
}
