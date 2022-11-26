package org.glassfish.tyrus.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TyrusFuture implements Future {
   private volatile Object result;
   private volatile Throwable throwable = null;
   private final CountDownLatch latch = new CountDownLatch(1);

   public boolean cancel(boolean mayInterruptIfRunning) {
      return false;
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return this.latch.getCount() == 0L;
   }

   public Object get() throws InterruptedException, ExecutionException {
      this.latch.await();
      if (this.throwable != null) {
         throw new ExecutionException(this.throwable);
      } else {
         return this.result;
      }
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (this.latch.await(timeout, unit)) {
         if (this.throwable != null) {
            throw new ExecutionException(this.throwable);
         } else {
            return this.result;
         }
      } else {
         throw new TimeoutException();
      }
   }

   public void setResult(Object result) {
      if (this.latch.getCount() == 1L) {
         this.result = result;
         this.latch.countDown();
      }

   }

   public void setFailure(Throwable throwable) {
      if (this.latch.getCount() == 1L) {
         this.throwable = throwable;
         this.latch.countDown();
      }

   }
}
