package org.python.netty.resolver;

import java.io.Closeable;
import java.util.IdentityHashMap;
import java.util.Map;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class AddressResolverGroup implements Closeable {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
   private final Map resolvers = new IdentityHashMap();

   protected AddressResolverGroup() {
   }

   public AddressResolver getResolver(final EventExecutor executor) {
      if (executor == null) {
         throw new NullPointerException("executor");
      } else if (executor.isShuttingDown()) {
         throw new IllegalStateException("executor not accepting a task");
      } else {
         synchronized(this.resolvers) {
            AddressResolver r = (AddressResolver)this.resolvers.get(executor);
            if (r == null) {
               final AddressResolver newResolver;
               try {
                  newResolver = this.newResolver(executor);
               } catch (Exception var7) {
                  throw new IllegalStateException("failed to create a new resolver", var7);
               }

               this.resolvers.put(executor, newResolver);
               executor.terminationFuture().addListener(new FutureListener() {
                  public void operationComplete(Future future) throws Exception {
                     synchronized(AddressResolverGroup.this.resolvers) {
                        AddressResolverGroup.this.resolvers.remove(executor);
                     }

                     newResolver.close();
                  }
               });
               r = newResolver;
            }

            return r;
         }
      }
   }

   protected abstract AddressResolver newResolver(EventExecutor var1) throws Exception;

   public void close() {
      AddressResolver[] rArray;
      synchronized(this.resolvers) {
         rArray = (AddressResolver[])((AddressResolver[])this.resolvers.values().toArray(new AddressResolver[this.resolvers.size()]));
         this.resolvers.clear();
      }

      AddressResolver[] var1 = rArray;
      int var3 = rArray.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         AddressResolver r = var1[var4];

         try {
            r.close();
         } catch (Throwable var7) {
            logger.warn("Failed to close a resolver:", var7);
         }
      }

   }
}
