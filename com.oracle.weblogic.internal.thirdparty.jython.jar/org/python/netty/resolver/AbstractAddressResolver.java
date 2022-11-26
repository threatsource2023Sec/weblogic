package org.python.netty.resolver;

import java.net.SocketAddress;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Collections;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.TypeParameterMatcher;

public abstract class AbstractAddressResolver implements AddressResolver {
   private final EventExecutor executor;
   private final TypeParameterMatcher matcher;

   protected AbstractAddressResolver(EventExecutor executor) {
      this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
      this.matcher = TypeParameterMatcher.find(this, AbstractAddressResolver.class, "T");
   }

   protected AbstractAddressResolver(EventExecutor executor, Class addressType) {
      this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
      this.matcher = TypeParameterMatcher.get(addressType);
   }

   protected EventExecutor executor() {
      return this.executor;
   }

   public boolean isSupported(SocketAddress address) {
      return this.matcher.match(address);
   }

   public final boolean isResolved(SocketAddress address) {
      if (!this.isSupported(address)) {
         throw new UnsupportedAddressTypeException();
      } else {
         return this.doIsResolved(address);
      }
   }

   protected abstract boolean doIsResolved(SocketAddress var1);

   public final Future resolve(SocketAddress address) {
      if (!this.isSupported((SocketAddress)ObjectUtil.checkNotNull(address, "address"))) {
         return this.executor().newFailedFuture(new UnsupportedAddressTypeException());
      } else if (this.isResolved(address)) {
         return this.executor.newSucceededFuture(address);
      } else {
         try {
            Promise promise = this.executor().newPromise();
            this.doResolve(address, promise);
            return promise;
         } catch (Exception var4) {
            return this.executor().newFailedFuture(var4);
         }
      }
   }

   public final Future resolve(SocketAddress address, Promise promise) {
      ObjectUtil.checkNotNull(address, "address");
      ObjectUtil.checkNotNull(promise, "promise");
      if (!this.isSupported(address)) {
         return promise.setFailure(new UnsupportedAddressTypeException());
      } else if (this.isResolved(address)) {
         return promise.setSuccess(address);
      } else {
         try {
            this.doResolve(address, promise);
            return promise;
         } catch (Exception var4) {
            return promise.setFailure(var4);
         }
      }
   }

   public final Future resolveAll(SocketAddress address) {
      if (!this.isSupported((SocketAddress)ObjectUtil.checkNotNull(address, "address"))) {
         return this.executor().newFailedFuture(new UnsupportedAddressTypeException());
      } else if (this.isResolved(address)) {
         return this.executor.newSucceededFuture(Collections.singletonList(address));
      } else {
         try {
            Promise promise = this.executor().newPromise();
            this.doResolveAll(address, promise);
            return promise;
         } catch (Exception var4) {
            return this.executor().newFailedFuture(var4);
         }
      }
   }

   public final Future resolveAll(SocketAddress address, Promise promise) {
      ObjectUtil.checkNotNull(address, "address");
      ObjectUtil.checkNotNull(promise, "promise");
      if (!this.isSupported(address)) {
         return promise.setFailure(new UnsupportedAddressTypeException());
      } else if (this.isResolved(address)) {
         return promise.setSuccess(Collections.singletonList(address));
      } else {
         try {
            this.doResolveAll(address, promise);
            return promise;
         } catch (Exception var4) {
            return promise.setFailure(var4);
         }
      }
   }

   protected abstract void doResolve(SocketAddress var1, Promise var2) throws Exception;

   protected abstract void doResolveAll(SocketAddress var1, Promise var2) throws Exception;

   public void close() {
   }
}
