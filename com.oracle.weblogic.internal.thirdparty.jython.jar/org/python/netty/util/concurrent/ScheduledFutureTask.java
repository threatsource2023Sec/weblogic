package org.python.netty.util.concurrent;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

final class ScheduledFutureTask extends PromiseTask implements ScheduledFuture {
   private static final AtomicLong nextTaskId = new AtomicLong();
   private static final long START_TIME = System.nanoTime();
   private final long id;
   private long deadlineNanos;
   private final long periodNanos;

   static long nanoTime() {
      return System.nanoTime() - START_TIME;
   }

   static long deadlineNanos(long delay) {
      return nanoTime() + delay;
   }

   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Runnable runnable, Object result, long nanoTime) {
      this(executor, toCallable(runnable, result), nanoTime);
   }

   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Callable callable, long nanoTime, long period) {
      super(executor, callable);
      this.id = nextTaskId.getAndIncrement();
      if (period == 0L) {
         throw new IllegalArgumentException("period: 0 (expected: != 0)");
      } else {
         this.deadlineNanos = nanoTime;
         this.periodNanos = period;
      }
   }

   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Callable callable, long nanoTime) {
      super(executor, callable);
      this.id = nextTaskId.getAndIncrement();
      this.deadlineNanos = nanoTime;
      this.periodNanos = 0L;
   }

   protected EventExecutor executor() {
      return super.executor();
   }

   public long deadlineNanos() {
      return this.deadlineNanos;
   }

   public long delayNanos() {
      return Math.max(0L, this.deadlineNanos() - nanoTime());
   }

   public long delayNanos(long currentTimeNanos) {
      return Math.max(0L, this.deadlineNanos() - (currentTimeNanos - START_TIME));
   }

   public long getDelay(TimeUnit unit) {
      return unit.convert(this.delayNanos(), TimeUnit.NANOSECONDS);
   }

   public int compareTo(Delayed o) {
      if (this == o) {
         return 0;
      } else {
         ScheduledFutureTask that = (ScheduledFutureTask)o;
         long d = this.deadlineNanos() - that.deadlineNanos();
         if (d < 0L) {
            return -1;
         } else if (d > 0L) {
            return 1;
         } else if (this.id < that.id) {
            return -1;
         } else if (this.id == that.id) {
            throw new Error();
         } else {
            return 1;
         }
      }
   }

   public void run() {
      assert this.executor().inEventLoop();

      try {
         if (this.periodNanos == 0L) {
            if (this.setUncancellableInternal()) {
               Object result = this.task.call();
               this.setSuccessInternal(result);
            }
         } else if (!this.isCancelled()) {
            this.task.call();
            if (!this.executor().isShutdown()) {
               long p = this.periodNanos;
               if (p > 0L) {
                  this.deadlineNanos += p;
               } else {
                  this.deadlineNanos = nanoTime() - p;
               }

               if (!this.isCancelled()) {
                  Queue scheduledTaskQueue = ((AbstractScheduledEventExecutor)this.executor()).scheduledTaskQueue;

                  assert scheduledTaskQueue != null;

                  scheduledTaskQueue.add(this);
               }
            }
         }
      } catch (Throwable var5) {
         this.setFailureInternal(var5);
      }

   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      boolean canceled = super.cancel(mayInterruptIfRunning);
      if (canceled) {
         ((AbstractScheduledEventExecutor)this.executor()).removeScheduled(this);
      }

      return canceled;
   }

   boolean cancelWithoutRemove(boolean mayInterruptIfRunning) {
      return super.cancel(mayInterruptIfRunning);
   }

   protected StringBuilder toStringBuilder() {
      StringBuilder buf = super.toStringBuilder();
      buf.setCharAt(buf.length() - 1, ',');
      return buf.append(" id: ").append(this.id).append(", deadline: ").append(this.deadlineNanos).append(", period: ").append(this.periodNanos).append(')');
   }
}
