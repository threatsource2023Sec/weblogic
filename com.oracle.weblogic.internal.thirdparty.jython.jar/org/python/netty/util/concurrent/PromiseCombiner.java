package org.python.netty.util.concurrent;

import org.python.netty.util.internal.ObjectUtil;

public final class PromiseCombiner {
   private int expectedCount;
   private int doneCount;
   private boolean doneAdding;
   private Promise aggregatePromise;
   private Throwable cause;
   private final GenericFutureListener listener = new GenericFutureListener() {
      public void operationComplete(Future future) throws Exception {
         ++PromiseCombiner.this.doneCount;
         if (!future.isSuccess() && PromiseCombiner.this.cause == null) {
            PromiseCombiner.this.cause = future.cause();
         }

         if (PromiseCombiner.this.doneCount == PromiseCombiner.this.expectedCount && PromiseCombiner.this.doneAdding) {
            PromiseCombiner.this.tryPromise();
         }

      }
   };

   /** @deprecated */
   @Deprecated
   public void add(Promise promise) {
      this.add((Future)promise);
   }

   public void add(Future future) {
      this.checkAddAllowed();
      ++this.expectedCount;
      future.addListener(this.listener);
   }

   /** @deprecated */
   @Deprecated
   public void addAll(Promise... promises) {
      this.addAll((Future[])promises);
   }

   public void addAll(Future... futures) {
      Future[] var2 = futures;
      int var3 = futures.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Future future = var2[var4];
         this.add(future);
      }

   }

   public void finish(Promise aggregatePromise) {
      if (this.doneAdding) {
         throw new IllegalStateException("Already finished");
      } else {
         this.doneAdding = true;
         this.aggregatePromise = (Promise)ObjectUtil.checkNotNull(aggregatePromise, "aggregatePromise");
         if (this.doneCount == this.expectedCount) {
            this.tryPromise();
         }

      }
   }

   private boolean tryPromise() {
      return this.cause == null ? this.aggregatePromise.trySuccess((Object)null) : this.aggregatePromise.tryFailure(this.cause);
   }

   private void checkAddAllowed() {
      if (this.doneAdding) {
         throw new IllegalStateException("Adding promises is not allowed after finished adding");
      }
   }
}
