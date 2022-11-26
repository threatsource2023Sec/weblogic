package org.glassfish.grizzly.threadpool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

final class QueueLimitedThreadPool extends FixedThreadPool {
   private final Semaphore queuePermits;

   QueueLimitedThreadPool(ThreadPoolConfig config) {
      super(config);
      if (config.getQueueLimit() < 0) {
         throw new IllegalArgumentException("maxQueuedTasks < 0");
      } else {
         this.queuePermits = new Semaphore(config.getQueueLimit());
      }
   }

   public final void execute(Runnable command) {
      if (command == null) {
         throw new IllegalArgumentException("Runnable task is null");
      } else if (!this.running) {
         throw new RejectedExecutionException("ThreadPool is not running");
      } else {
         if (!this.queuePermits.tryAcquire()) {
            this.onTaskQueueOverflow();
         }

         if (!this.workQueue.offer(command)) {
            this.queuePermits.release();
            this.onTaskQueueOverflow();
         }

         this.onTaskQueued(command);
      }
   }

   protected final void beforeExecute(AbstractThreadPool.Worker worker, Thread t, Runnable r) {
      super.beforeExecute(worker, t, r);
      this.queuePermits.release();
   }
}
