package org.python.netty.resolver;

import java.net.UnknownHostException;
import java.util.Arrays;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.SocketUtils;

public class DefaultNameResolver extends InetNameResolver {
   public DefaultNameResolver(EventExecutor executor) {
      super(executor);
   }

   protected void doResolve(String inetHost, Promise promise) throws Exception {
      try {
         promise.setSuccess(SocketUtils.addressByName(inetHost));
      } catch (UnknownHostException var4) {
         promise.setFailure(var4);
      }

   }

   protected void doResolveAll(String inetHost, Promise promise) throws Exception {
      try {
         promise.setSuccess(Arrays.asList(SocketUtils.allAddressesByName(inetHost)));
      } catch (UnknownHostException var4) {
         promise.setFailure(var4);
      }

   }
}
