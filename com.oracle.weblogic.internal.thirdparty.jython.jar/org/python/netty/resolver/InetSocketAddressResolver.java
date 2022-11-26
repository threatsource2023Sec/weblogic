package org.python.netty.resolver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.concurrent.Promise;

public class InetSocketAddressResolver extends AbstractAddressResolver {
   final NameResolver nameResolver;

   public InetSocketAddressResolver(EventExecutor executor, NameResolver nameResolver) {
      super(executor, InetSocketAddress.class);
      this.nameResolver = nameResolver;
   }

   protected boolean doIsResolved(InetSocketAddress address) {
      return !address.isUnresolved();
   }

   protected void doResolve(final InetSocketAddress unresolvedAddress, final Promise promise) throws Exception {
      this.nameResolver.resolve(unresolvedAddress.getHostName()).addListener(new FutureListener() {
         public void operationComplete(Future future) throws Exception {
            if (future.isSuccess()) {
               promise.setSuccess(new InetSocketAddress((InetAddress)future.getNow(), unresolvedAddress.getPort()));
            } else {
               promise.setFailure(future.cause());
            }

         }
      });
   }

   protected void doResolveAll(final InetSocketAddress unresolvedAddress, final Promise promise) throws Exception {
      this.nameResolver.resolveAll(unresolvedAddress.getHostName()).addListener(new FutureListener() {
         public void operationComplete(Future future) throws Exception {
            if (future.isSuccess()) {
               List inetAddresses = (List)future.getNow();
               List socketAddresses = new ArrayList(inetAddresses.size());
               Iterator var4 = inetAddresses.iterator();

               while(var4.hasNext()) {
                  InetAddress inetAddress = (InetAddress)var4.next();
                  socketAddresses.add(new InetSocketAddress(inetAddress, unresolvedAddress.getPort()));
               }

               promise.setSuccess(socketAddresses);
            } else {
               promise.setFailure(future.cause());
            }

         }
      });
   }

   public void close() {
      this.nameResolver.close();
   }
}
