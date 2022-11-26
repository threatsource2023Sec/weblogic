package org.python.netty.resolver;

import java.net.SocketAddress;
import java.util.Collections;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Promise;

public class NoopAddressResolver extends AbstractAddressResolver {
   public NoopAddressResolver(EventExecutor executor) {
      super(executor);
   }

   protected boolean doIsResolved(SocketAddress address) {
      return true;
   }

   protected void doResolve(SocketAddress unresolvedAddress, Promise promise) throws Exception {
      promise.setSuccess(unresolvedAddress);
   }

   protected void doResolveAll(SocketAddress unresolvedAddress, Promise promise) throws Exception {
      promise.setSuccess(Collections.singletonList(unresolvedAddress));
   }
}
