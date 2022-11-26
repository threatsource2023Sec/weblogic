package weblogic.work.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AllDoneListener implements BatchTaskListener {
   private final CountDownLatch lock;

   public AllDoneListener(int count) {
      this.lock = new CountDownLatch(count);
   }

   public void taskSuccess(Object result) {
      this.lock.countDown();
   }

   public void taskFailed(ExecutionException ex) {
      this.lock.countDown();
   }

   public void taskCanceled(CancellationException cancelException) {
      this.lock.countDown();
   }

   public void await() throws InterruptedException {
      this.lock.await();
   }

   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
      return this.lock.await(timeout, unit);
   }
}
