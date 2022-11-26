package org.python.netty.resolver;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.PlatformDependent;

public class RoundRobinInetAddressResolver extends InetNameResolver {
   private final NameResolver nameResolver;

   public RoundRobinInetAddressResolver(EventExecutor executor, NameResolver nameResolver) {
      super(executor);
      this.nameResolver = nameResolver;
   }

   protected void doResolve(final String inetHost, final Promise promise) throws Exception {
      this.nameResolver.resolveAll(inetHost).addListener(new FutureListener() {
         public void operationComplete(Future future) throws Exception {
            if (future.isSuccess()) {
               List inetAddresses = (List)future.getNow();
               int numAddresses = inetAddresses.size();
               if (numAddresses > 0) {
                  promise.setSuccess(inetAddresses.get(RoundRobinInetAddressResolver.randomIndex(numAddresses)));
               } else {
                  promise.setFailure(new UnknownHostException(inetHost));
               }
            } else {
               promise.setFailure(future.cause());
            }

         }
      });
   }

   protected void doResolveAll(String inetHost, final Promise promise) throws Exception {
      this.nameResolver.resolveAll(inetHost).addListener(new FutureListener() {
         public void operationComplete(Future future) throws Exception {
            if (future.isSuccess()) {
               List inetAddresses = (List)future.getNow();
               if (!inetAddresses.isEmpty()) {
                  List result = new ArrayList(inetAddresses);
                  Collections.rotate(result, RoundRobinInetAddressResolver.randomIndex(inetAddresses.size()));
                  promise.setSuccess(result);
               } else {
                  promise.setSuccess(inetAddresses);
               }
            } else {
               promise.setFailure(future.cause());
            }

         }
      });
   }

   private static int randomIndex(int numAddresses) {
      return numAddresses == 1 ? 0 : PlatformDependent.threadLocalRandom().nextInt(numAddresses);
   }
}
