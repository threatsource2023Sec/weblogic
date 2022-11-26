package weblogic.work.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class AnyDoneListener implements BatchTaskListener {
   private final CountDownLatch lock = new CountDownLatch(1);
   private Object result;
   private final int count;
   private final AtomicInteger completed = new AtomicInteger();
   private ExecutionException exception;
   private CancellationException cancelException;

   public AnyDoneListener(int count) {
      this.count = count;
   }

   public void taskSuccess(Object result) {
      this.result = result;
      this.lock.countDown();
   }

   public void taskFailed(ExecutionException ex) {
      if (this.completed.incrementAndGet() == this.count) {
         this.exception = ex;
         this.lock.countDown();
      }

   }

   public void taskCanceled(CancellationException cancelException) {
      if (this.completed.incrementAndGet() == this.count) {
         this.cancelException = cancelException;
         this.lock.countDown();
      }

   }

   public Object await() throws InterruptedException, ExecutionException {
      this.lock.await();
      if (this.cancelException != null) {
         throw this.cancelException;
      } else if (this.exception != null) {
         throw this.exception;
      } else {
         return this.result;
      }
   }

   public Object await(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (!this.lock.await(timeout, unit)) {
         throw new TimeoutException();
      } else if (this.cancelException != null) {
         throw this.cancelException;
      } else if (this.exception != null) {
         throw this.exception;
      } else {
         return this.result;
      }
   }
}
