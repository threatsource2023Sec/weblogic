package org.python.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class UnorderedThreadPoolEventExecutor extends ScheduledThreadPoolExecutor implements EventExecutor {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnorderedThreadPoolEventExecutor.class);
   private final Promise terminationFuture;
   private final Set executorSet;

   public UnorderedThreadPoolEventExecutor(int corePoolSize) {
      this(corePoolSize, (ThreadFactory)(new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class)));
   }

   public UnorderedThreadPoolEventExecutor(int corePoolSize, ThreadFactory threadFactory) {
      super(corePoolSize, threadFactory);
      this.terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
      this.executorSet = Collections.singleton(this);
   }

   public UnorderedThreadPoolEventExecutor(int corePoolSize, java.util.concurrent.RejectedExecutionHandler handler) {
      this(corePoolSize, new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class), handler);
   }

   public UnorderedThreadPoolEventExecutor(int corePoolSize, ThreadFactory threadFactory, java.util.concurrent.RejectedExecutionHandler handler) {
      super(corePoolSize, threadFactory, handler);
      this.terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
      this.executorSet = Collections.singleton(this);
   }

   public EventExecutor next() {
      return this;
   }

   public EventExecutorGroup parent() {
      return this;
   }

   public boolean inEventLoop() {
      return false;
   }

   public boolean inEventLoop(Thread thread) {
      return false;
   }

   public Promise newPromise() {
      return new DefaultPromise(this);
   }

   public ProgressivePromise newProgressivePromise() {
      return new DefaultProgressivePromise(this);
   }

   public Future newSucceededFuture(Object result) {
      return new SucceededFuture(this, result);
   }

   public Future newFailedFuture(Throwable cause) {
      return new FailedFuture(this, cause);
   }

   public boolean isShuttingDown() {
      return this.isShutdown();
   }

   public List shutdownNow() {
      List tasks = super.shutdownNow();
      this.terminationFuture.trySuccess((Object)null);
      return tasks;
   }

   public void shutdown() {
      super.shutdown();
      this.terminationFuture.trySuccess((Object)null);
   }

   public Future shutdownGracefully() {
      return this.shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
   }

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      this.shutdown();
      return this.terminationFuture();
   }

   public Future terminationFuture() {
      return this.terminationFuture;
   }

   public Iterator iterator() {
      return this.executorSet.iterator();
   }

   protected RunnableScheduledFuture decorateTask(Runnable runnable, RunnableScheduledFuture task) {
      return (RunnableScheduledFuture)(runnable instanceof NonNotifyRunnable ? task : new RunnableScheduledFutureTask(this, runnable, task));
   }

   protected RunnableScheduledFuture decorateTask(Callable callable, RunnableScheduledFuture task) {
      return new RunnableScheduledFutureTask(this, callable, task);
   }

   public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
      return (ScheduledFuture)super.schedule(command, delay, unit);
   }

   public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
      return (ScheduledFuture)super.schedule(callable, delay, unit);
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      return (ScheduledFuture)super.scheduleAtFixedRate(command, initialDelay, period, unit);
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      return (ScheduledFuture)super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
   }

   public Future submit(Runnable task) {
      return (Future)super.submit(task);
   }

   public Future submit(Runnable task, Object result) {
      return (Future)super.submit(task, result);
   }

   public Future submit(Callable task) {
      return (Future)super.submit(task);
   }

   public void execute(Runnable command) {
      super.schedule(new NonNotifyRunnable(command), 0L, TimeUnit.NANOSECONDS);
   }

   private static final class NonNotifyRunnable implements Runnable {
      private final Runnable task;

      NonNotifyRunnable(Runnable task) {
         this.task = task;
      }

      public void run() {
         this.task.run();
      }
   }

   private static final class RunnableScheduledFutureTask extends PromiseTask implements RunnableScheduledFuture, ScheduledFuture {
      private final RunnableScheduledFuture future;

      RunnableScheduledFutureTask(EventExecutor executor, Runnable runnable, RunnableScheduledFuture future) {
         super(executor, runnable, (Object)null);
         this.future = future;
      }

      RunnableScheduledFutureTask(EventExecutor executor, Callable callable, RunnableScheduledFuture future) {
         super(executor, callable);
         this.future = future;
      }

      public void run() {
         if (!this.isPeriodic()) {
            super.run();
         } else if (!this.isDone()) {
            try {
               this.task.call();
            } catch (Throwable var2) {
               if (!this.tryFailureInternal(var2)) {
                  UnorderedThreadPoolEventExecutor.logger.warn("Failure during execution of task", var2);
               }
            }
         }

      }

      public boolean isPeriodic() {
         return this.future.isPeriodic();
      }

      public long getDelay(TimeUnit unit) {
         return this.future.getDelay(unit);
      }

      public int compareTo(Delayed o) {
         return this.future.compareTo(o);
      }
   }
}
