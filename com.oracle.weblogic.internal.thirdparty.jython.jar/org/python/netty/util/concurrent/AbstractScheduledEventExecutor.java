package org.python.netty.util.concurrent;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.python.netty.util.internal.ObjectUtil;

public abstract class AbstractScheduledEventExecutor extends AbstractEventExecutor {
   Queue scheduledTaskQueue;

   protected AbstractScheduledEventExecutor() {
   }

   protected AbstractScheduledEventExecutor(EventExecutorGroup parent) {
      super(parent);
   }

   protected static long nanoTime() {
      return ScheduledFutureTask.nanoTime();
   }

   Queue scheduledTaskQueue() {
      if (this.scheduledTaskQueue == null) {
         this.scheduledTaskQueue = new PriorityQueue();
      }

      return this.scheduledTaskQueue;
   }

   private static boolean isNullOrEmpty(Queue queue) {
      return queue == null || queue.isEmpty();
   }

   protected void cancelScheduledTasks() {
      assert this.inEventLoop();

      Queue scheduledTaskQueue = this.scheduledTaskQueue;
      if (!isNullOrEmpty(scheduledTaskQueue)) {
         ScheduledFutureTask[] scheduledTasks = (ScheduledFutureTask[])scheduledTaskQueue.toArray(new ScheduledFutureTask[scheduledTaskQueue.size()]);
         ScheduledFutureTask[] var3 = scheduledTasks;
         int var4 = scheduledTasks.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ScheduledFutureTask task = var3[var5];
            task.cancelWithoutRemove(false);
         }

         scheduledTaskQueue.clear();
      }
   }

   protected final Runnable pollScheduledTask() {
      return this.pollScheduledTask(nanoTime());
   }

   protected final Runnable pollScheduledTask(long nanoTime) {
      assert this.inEventLoop();

      Queue scheduledTaskQueue = this.scheduledTaskQueue;
      ScheduledFutureTask scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
      if (scheduledTask == null) {
         return null;
      } else if (scheduledTask.deadlineNanos() <= nanoTime) {
         scheduledTaskQueue.remove();
         return scheduledTask;
      } else {
         return null;
      }
   }

   protected final long nextScheduledTaskNano() {
      Queue scheduledTaskQueue = this.scheduledTaskQueue;
      ScheduledFutureTask scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
      return scheduledTask == null ? -1L : Math.max(0L, scheduledTask.deadlineNanos() - nanoTime());
   }

   final ScheduledFutureTask peekScheduledTask() {
      Queue scheduledTaskQueue = this.scheduledTaskQueue;
      return scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
   }

   protected final boolean hasScheduledTasks() {
      Queue scheduledTaskQueue = this.scheduledTaskQueue;
      ScheduledFutureTask scheduledTask = scheduledTaskQueue == null ? null : (ScheduledFutureTask)scheduledTaskQueue.peek();
      return scheduledTask != null && scheduledTask.deadlineNanos() <= nanoTime();
   }

   public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
      ObjectUtil.checkNotNull(command, "command");
      ObjectUtil.checkNotNull(unit, "unit");
      if (delay < 0L) {
         delay = 0L;
      }

      return this.schedule(new ScheduledFutureTask(this, command, (Object)null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
   }

   public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
      ObjectUtil.checkNotNull(callable, "callable");
      ObjectUtil.checkNotNull(unit, "unit");
      if (delay < 0L) {
         delay = 0L;
      }

      return this.schedule(new ScheduledFutureTask(this, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      ObjectUtil.checkNotNull(command, "command");
      ObjectUtil.checkNotNull(unit, "unit");
      if (initialDelay < 0L) {
         throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
      } else if (period <= 0L) {
         throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", period));
      } else {
         return this.schedule(new ScheduledFutureTask(this, Executors.callable(command, (Object)null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
      }
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      ObjectUtil.checkNotNull(command, "command");
      ObjectUtil.checkNotNull(unit, "unit");
      if (initialDelay < 0L) {
         throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
      } else if (delay <= 0L) {
         throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", delay));
      } else {
         return this.schedule(new ScheduledFutureTask(this, Executors.callable(command, (Object)null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
      }
   }

   ScheduledFuture schedule(final ScheduledFutureTask task) {
      if (this.inEventLoop()) {
         this.scheduledTaskQueue().add(task);
      } else {
         this.execute(new Runnable() {
            public void run() {
               AbstractScheduledEventExecutor.this.scheduledTaskQueue().add(task);
            }
         });
      }

      return task;
   }

   final void removeScheduled(final ScheduledFutureTask task) {
      if (this.inEventLoop()) {
         this.scheduledTaskQueue().remove(task);
      } else {
         this.execute(new Runnable() {
            public void run() {
               AbstractScheduledEventExecutor.this.removeScheduled(task);
            }
         });
      }

   }
}
