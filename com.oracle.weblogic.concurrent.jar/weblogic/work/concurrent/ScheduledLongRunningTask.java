package weblogic.work.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.work.concurrent.future.AbstractFutureImpl;
import weblogic.work.concurrent.spi.DaemonThreadTask;

public class ScheduledLongRunningTask extends DaemonThreadTask {
   private AbstractFutureImpl work;
   private final LinkedBlockingQueue workQueue;
   private static final Runnable POISON = new Runnable() {
      public void run() {
      }
   };

   public ScheduledLongRunningTask(AbstractFutureImpl work, int priority) {
      super(priority);
      this.work = work;
      this.workQueue = new LinkedBlockingQueue();
   }

   public void run() {
      do {
         try {
            Runnable take = (Runnable)this.workQueue.take();
            if (take == POISON) {
               break;
            }

            take.run();
         } catch (InterruptedException var2) {
         }
      } while(!this.work.isDone());

   }

   public void cancel() {
      try {
         this.workQueue.put(POISON);
      } catch (InterruptedException var2) {
      }

   }

   public void shutdown(String reason) {
      this.cancel();
      this.work.doCancel(true, reason);
   }

   public DebugLogger getDebugLogger() {
      return DebugLogger.getDebugLogger("DebugConcurrentMSES");
   }

   public void addSubTask(Runnable work) {
      try {
         this.workQueue.put(work);
      } catch (InterruptedException var3) {
      }

   }

   public ComponentInvocationContext getSubmittingCICInSharing() {
      return this.work.getSubmittingCICInSharing();
   }
}
