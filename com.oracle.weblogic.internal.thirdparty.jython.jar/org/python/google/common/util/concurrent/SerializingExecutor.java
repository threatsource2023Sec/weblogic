package org.python.google.common.util.concurrent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtIncompatible
final class SerializingExecutor implements Executor {
   private static final Logger log = Logger.getLogger(SerializingExecutor.class.getName());
   private final Executor executor;
   @GuardedBy("queue")
   private final Deque queue = new ArrayDeque();
   @GuardedBy("queue")
   private boolean isWorkerRunning = false;
   @GuardedBy("queue")
   private int suspensions = 0;
   private final QueueWorker worker = new QueueWorker();

   public SerializingExecutor(Executor executor) {
      this.executor = (Executor)Preconditions.checkNotNull(executor);
   }

   public void execute(Runnable task) {
      synchronized(this.queue) {
         this.queue.addLast(task);
         if (this.isWorkerRunning || this.suspensions > 0) {
            return;
         }

         this.isWorkerRunning = true;
      }

      this.startQueueWorker();
   }

   public void executeFirst(Runnable task) {
      synchronized(this.queue) {
         this.queue.addFirst(task);
         if (this.isWorkerRunning || this.suspensions > 0) {
            return;
         }

         this.isWorkerRunning = true;
      }

      this.startQueueWorker();
   }

   public void suspend() {
      synchronized(this.queue) {
         ++this.suspensions;
      }
   }

   public void resume() {
      synchronized(this.queue) {
         Preconditions.checkState(this.suspensions > 0);
         --this.suspensions;
         if (this.isWorkerRunning || this.suspensions > 0 || this.queue.isEmpty()) {
            return;
         }

         this.isWorkerRunning = true;
      }

      this.startQueueWorker();
   }

   private void startQueueWorker() {
      boolean executionRejected = true;
      boolean var10 = false;

      try {
         var10 = true;
         this.executor.execute(this.worker);
         executionRejected = false;
         var10 = false;
      } finally {
         if (var10) {
            if (executionRejected) {
               synchronized(this.queue) {
                  this.isWorkerRunning = false;
               }
            }

         }
      }

      if (executionRejected) {
         synchronized(this.queue) {
            this.isWorkerRunning = false;
         }
      }

   }

   private final class QueueWorker implements Runnable {
      private QueueWorker() {
      }

      public void run() {
         try {
            this.workOnQueue();
         } catch (Error var5) {
            synchronized(SerializingExecutor.this.queue) {
               SerializingExecutor.this.isWorkerRunning = false;
            }

            throw var5;
         }
      }

      private void workOnQueue() {
         while(true) {
            Runnable task = null;
            synchronized(SerializingExecutor.this.queue) {
               if (SerializingExecutor.this.suspensions == 0) {
                  task = (Runnable)SerializingExecutor.this.queue.pollFirst();
               }

               if (task == null) {
                  SerializingExecutor.this.isWorkerRunning = false;
                  return;
               }
            }

            try {
               task.run();
            } catch (RuntimeException var4) {
               SerializingExecutor.log.log(Level.SEVERE, "Exception while executing runnable " + task, var4);
            }
         }
      }

      // $FF: synthetic method
      QueueWorker(Object x1) {
         this();
      }
   }
}
