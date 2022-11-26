package org.glassfish.grizzly.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;

public class FixedThreadPool extends AbstractThreadPool {
   protected final BlockingQueue workQueue;

   public FixedThreadPool(ThreadPoolConfig config) {
      super(config);
      this.workQueue = config.getQueue() != null ? (BlockingQueue)config.getQueue() : (BlockingQueue)config.setQueue(new LinkedTransferQueue()).getQueue();
      int poolSize = config.getMaxPoolSize();
      synchronized(this.stateLock) {
         while(poolSize-- > 0) {
            this.doStartWorker();
         }
      }

      ProbeNotifier.notifyThreadPoolStarted(this);
      super.onMaxNumberOfThreadsReached();
   }

   private void doStartWorker() {
      this.startWorker(new BasicWorker());
   }

   public void execute(Runnable command) {
      if (this.running) {
         if (this.workQueue.offer(command)) {
            if (!this.running && this.workQueue.remove(command)) {
               throw new RejectedExecutionException("ThreadPool is not running");
            } else {
               this.onTaskQueued(command);
            }
         } else {
            this.onTaskQueueOverflow();
         }
      } else {
         throw new RejectedExecutionException("ThreadPool is not running");
      }
   }

   private final class BasicWorker extends AbstractThreadPool.Worker {
      private BasicWorker() {
         super();
      }

      protected final Runnable getTask() throws InterruptedException {
         return (Runnable)FixedThreadPool.this.workQueue.take();
      }

      // $FF: synthetic method
      BasicWorker(Object x1) {
         this();
      }
   }
}
