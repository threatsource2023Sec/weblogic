package org.python.netty.util.concurrent;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/** @deprecated */
@Deprecated
public class PromiseAggregator implements GenericFutureListener {
   private final Promise aggregatePromise;
   private final boolean failPending;
   private Set pendingPromises;

   public PromiseAggregator(Promise aggregatePromise, boolean failPending) {
      if (aggregatePromise == null) {
         throw new NullPointerException("aggregatePromise");
      } else {
         this.aggregatePromise = aggregatePromise;
         this.failPending = failPending;
      }
   }

   public PromiseAggregator(Promise aggregatePromise) {
      this(aggregatePromise, true);
   }

   @SafeVarargs
   public final PromiseAggregator add(Promise... promises) {
      if (promises == null) {
         throw new NullPointerException("promises");
      } else if (promises.length == 0) {
         return this;
      } else {
         synchronized(this) {
            if (this.pendingPromises == null) {
               int size;
               if (promises.length > 1) {
                  size = promises.length;
               } else {
                  size = 2;
               }

               this.pendingPromises = new LinkedHashSet(size);
            }

            Promise[] var9 = promises;
            int var4 = promises.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Promise p = var9[var5];
               if (p != null) {
                  this.pendingPromises.add(p);
                  p.addListener(this);
               }
            }

            return this;
         }
      }
   }

   public synchronized void operationComplete(Future future) throws Exception {
      if (this.pendingPromises == null) {
         this.aggregatePromise.setSuccess((Object)null);
      } else {
         this.pendingPromises.remove(future);
         if (!future.isSuccess()) {
            Throwable cause = future.cause();
            this.aggregatePromise.setFailure(cause);
            if (this.failPending) {
               Iterator var3 = this.pendingPromises.iterator();

               while(var3.hasNext()) {
                  Promise pendingFuture = (Promise)var3.next();
                  pendingFuture.setFailure(cause);
               }
            }
         } else if (this.pendingPromises.isEmpty()) {
            this.aggregatePromise.setSuccess((Object)null);
         }
      }

   }
}
