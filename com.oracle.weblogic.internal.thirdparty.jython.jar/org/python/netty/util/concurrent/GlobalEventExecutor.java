package org.python.netty.util.concurrent;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class GlobalEventExecutor extends AbstractScheduledEventExecutor {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
   private static final long SCHEDULE_QUIET_PERIOD_INTERVAL;
   public static final GlobalEventExecutor INSTANCE;
   final BlockingQueue taskQueue = new LinkedBlockingQueue();
   final ScheduledFutureTask quietPeriodTask;
   final ThreadFactory threadFactory;
   private final TaskRunner taskRunner;
   private final AtomicBoolean started;
   volatile Thread thread;
   private final Future terminationFuture;

   private GlobalEventExecutor() {
      this.quietPeriodTask = new ScheduledFutureTask(this, Executors.callable(new Runnable() {
         public void run() {
         }
      }, (Object)null), ScheduledFutureTask.deadlineNanos(SCHEDULE_QUIET_PERIOD_INTERVAL), -SCHEDULE_QUIET_PERIOD_INTERVAL);
      this.threadFactory = new DefaultThreadFactory(DefaultThreadFactory.toPoolName(this.getClass()), false, 5, (ThreadGroup)null);
      this.taskRunner = new TaskRunner();
      this.started = new AtomicBoolean();
      this.terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
      this.scheduledTaskQueue().add(this.quietPeriodTask);
   }

   Runnable takeTask() {
      BlockingQueue taskQueue = this.taskQueue;

      Runnable task;
      do {
         ScheduledFutureTask scheduledTask = this.peekScheduledTask();
         if (scheduledTask == null) {
            Runnable task = null;

            try {
               task = (Runnable)taskQueue.take();
            } catch (InterruptedException var9) {
            }

            return task;
         }

         long delayNanos = scheduledTask.delayNanos();
         if (delayNanos > 0L) {
            try {
               task = (Runnable)taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
            } catch (InterruptedException var10) {
               return null;
            }
         } else {
            task = (Runnable)taskQueue.poll();
         }

         if (task == null) {
            this.fetchFromScheduledTaskQueue();
            task = (Runnable)taskQueue.poll();
         }
      } while(task == null);

      return task;
   }

   private void fetchFromScheduledTaskQueue() {
      long nanoTime = AbstractScheduledEventExecutor.nanoTime();

      for(Runnable scheduledTask = this.pollScheduledTask(nanoTime); scheduledTask != null; scheduledTask = this.pollScheduledTask(nanoTime)) {
         this.taskQueue.add(scheduledTask);
      }

   }

   public int pendingTasks() {
      return this.taskQueue.size();
   }

   private void addTask(Runnable task) {
      if (task == null) {
         throw new NullPointerException("task");
      } else {
         this.taskQueue.add(task);
      }
   }

   public boolean inEventLoop(Thread thread) {
      return thread == this.thread;
   }

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      return this.terminationFuture();
   }

   public Future terminationFuture() {
      return this.terminationFuture;
   }

   /** @deprecated */
   @Deprecated
   public void shutdown() {
      throw new UnsupportedOperationException();
   }

   public boolean isShuttingDown() {
      return false;
   }

   public boolean isShutdown() {
      return false;
   }

   public boolean isTerminated() {
      return false;
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) {
      return false;
   }

   public boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
      if (unit == null) {
         throw new NullPointerException("unit");
      } else {
         Thread thread = this.thread;
         if (thread == null) {
            throw new IllegalStateException("thread was not started");
         } else {
            thread.join(unit.toMillis(timeout));
            return !thread.isAlive();
         }
      }
   }

   public void execute(Runnable task) {
      if (task == null) {
         throw new NullPointerException("task");
      } else {
         this.addTask(task);
         if (!this.inEventLoop()) {
            this.startThread();
         }

      }
   }

   private void startThread() {
      if (this.started.compareAndSet(false, true)) {
         Thread t = this.threadFactory.newThread(this.taskRunner);
         this.thread = t;
         t.start();
      }

   }

   static {
      SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
      INSTANCE = new GlobalEventExecutor();
   }

   final class TaskRunner implements Runnable {
      public void run() {
         while(true) {
            Runnable task = GlobalEventExecutor.this.takeTask();
            if (task != null) {
               try {
                  task.run();
               } catch (Throwable var4) {
                  GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", var4);
               }

               if (task != GlobalEventExecutor.this.quietPeriodTask) {
                  continue;
               }
            }

            Queue scheduledTaskQueue = GlobalEventExecutor.this.scheduledTaskQueue;
            if (GlobalEventExecutor.this.taskQueue.isEmpty() && (scheduledTaskQueue == null || scheduledTaskQueue.size() == 1)) {
               boolean stopped = GlobalEventExecutor.this.started.compareAndSet(true, false);

               assert stopped;

               if (GlobalEventExecutor.this.taskQueue.isEmpty() && (scheduledTaskQueue == null || scheduledTaskQueue.size() == 1) || !GlobalEventExecutor.this.started.compareAndSet(false, true)) {
                  return;
               }
            }
         }
      }
   }
}
