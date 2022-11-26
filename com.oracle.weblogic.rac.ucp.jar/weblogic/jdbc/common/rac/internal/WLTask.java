package weblogic.jdbc.common.rac.internal;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import oracle.ucp.util.Task;

public class WLTask implements Runnable {
   private Task ucpTask;
   private Object result = null;
   private Exception callException = null;
   private CountDownLatch latch = new CountDownLatch(1);

   public WLTask(Task task) {
      this.ucpTask = task;
   }

   Task getTask() {
      return this.ucpTask;
   }

   Object getResult(long timeout) throws InterruptedException {
      this.latch.await(timeout, TimeUnit.MILLISECONDS);
      return this.result;
   }

   void raiseException(long timeout) throws InterruptedException, Exception {
      this.latch.await(timeout, TimeUnit.MILLISECONDS);
      if (this.callException != null) {
         throw this.callException;
      }
   }

   public void run() {
      try {
         this.result = this.ucpTask.call();
      } catch (Exception var5) {
         this.callException = var5;
      } finally {
         this.latch.countDown();
      }

   }
}
