package org.python.netty.resolver;

import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.ObjectUtil;

public abstract class SimpleNameResolver implements NameResolver {
   private final EventExecutor executor;

   protected SimpleNameResolver(EventExecutor executor) {
      this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
   }

   protected EventExecutor executor() {
      return this.executor;
   }

   public final Future resolve(String inetHost) {
      Promise promise = this.executor().newPromise();
      return this.resolve(inetHost, promise);
   }

   public Future resolve(String inetHost, Promise promise) {
      ObjectUtil.checkNotNull(promise, "promise");

      try {
         this.doResolve(inetHost, promise);
         return promise;
      } catch (Exception var4) {
         return promise.setFailure(var4);
      }
   }

   public final Future resolveAll(String inetHost) {
      Promise promise = this.executor().newPromise();
      return this.resolveAll(inetHost, promise);
   }

   public Future resolveAll(String inetHost, Promise promise) {
      ObjectUtil.checkNotNull(promise, "promise");

      try {
         this.doResolveAll(inetHost, promise);
         return promise;
      } catch (Exception var4) {
         return promise.setFailure(var4);
      }
   }

   protected abstract void doResolve(String var1, Promise var2) throws Exception;

   protected abstract void doResolveAll(String var1, Promise var2) throws Exception;

   public void close() {
   }
}
