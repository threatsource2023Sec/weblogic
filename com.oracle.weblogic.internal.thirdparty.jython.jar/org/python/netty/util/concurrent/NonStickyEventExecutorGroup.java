package org.python.netty.util.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

public final class NonStickyEventExecutorGroup implements EventExecutorGroup {
   private final EventExecutorGroup group;
   private final int maxTaskExecutePerRun;

   public NonStickyEventExecutorGroup(EventExecutorGroup group) {
      this(group, 1024);
   }

   public NonStickyEventExecutorGroup(EventExecutorGroup group, int maxTaskExecutePerRun) {
      this.group = verify(group);
      this.maxTaskExecutePerRun = ObjectUtil.checkPositive(maxTaskExecutePerRun, "maxTaskExecutePerRun");
   }

   private static EventExecutorGroup verify(EventExecutorGroup group) {
      Iterator executors = ((EventExecutorGroup)ObjectUtil.checkNotNull(group, "group")).iterator();

      EventExecutor executor;
      do {
         if (!executors.hasNext()) {
            return group;
         }

         executor = (EventExecutor)executors.next();
      } while(!(executor instanceof OrderedEventExecutor));

      throw new IllegalArgumentException("EventExecutorGroup " + group + " contains OrderedEventExecutors: " + executor);
   }

   private NonStickyOrderedEventExecutor newExecutor(EventExecutor executor) {
      return new NonStickyOrderedEventExecutor(executor, this.maxTaskExecutePerRun);
   }

   public boolean isShuttingDown() {
      return this.group.isShuttingDown();
   }

   public Future shutdownGracefully() {
      return this.group.shutdownGracefully();
   }

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      return this.group.shutdownGracefully(quietPeriod, timeout, unit);
   }

   public Future terminationFuture() {
      return this.group.terminationFuture();
   }

   public void shutdown() {
      this.group.shutdown();
   }

   public List shutdownNow() {
      return this.group.shutdownNow();
   }

   public EventExecutor next() {
      return this.newExecutor(this.group.next());
   }

   public Iterator iterator() {
      final Iterator itr = this.group.iterator();
      return new Iterator() {
         public boolean hasNext() {
            return itr.hasNext();
         }

         public EventExecutor next() {
            return NonStickyEventExecutorGroup.this.newExecutor((EventExecutor)itr.next());
         }

         public void remove() {
            itr.remove();
         }
      };
   }

   public Future submit(Runnable task) {
      return this.group.submit(task);
   }

   public Future submit(Runnable task, Object result) {
      return this.group.submit(task, result);
   }

   public Future submit(Callable task) {
      return this.group.submit(task);
   }

   public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
      return this.group.schedule(command, delay, unit);
   }

   public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
      return this.group.schedule(callable, delay, unit);
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      return this.group.scheduleAtFixedRate(command, initialDelay, period, unit);
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      return this.group.scheduleWithFixedDelay(command, initialDelay, delay, unit);
   }

   public boolean isShutdown() {
      return this.group.isShutdown();
   }

   public boolean isTerminated() {
      return this.group.isTerminated();
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return this.group.awaitTermination(timeout, unit);
   }

   public List invokeAll(Collection tasks) throws InterruptedException {
      return this.group.invokeAll(tasks);
   }

   public List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
      return this.group.invokeAll(tasks, timeout, unit);
   }

   public Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
      return this.group.invokeAny(tasks);
   }

   public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.group.invokeAny(tasks, timeout, unit);
   }

   public void execute(Runnable command) {
      this.group.execute(command);
   }

   private static final class NonStickyOrderedEventExecutor extends AbstractEventExecutor implements Runnable, OrderedEventExecutor {
      private final EventExecutor executor;
      private final Queue tasks = PlatformDependent.newMpscQueue();
      private static final int NONE = 0;
      private static final int SUBMITTED = 1;
      private static final int RUNNING = 2;
      private final AtomicInteger state = new AtomicInteger();
      private final int maxTaskExecutePerRun;

      NonStickyOrderedEventExecutor(EventExecutor executor, int maxTaskExecutePerRun) {
         super(executor);
         this.executor = executor;
         this.maxTaskExecutePerRun = maxTaskExecutePerRun;
      }

      public void run() {
         if (this.state.compareAndSet(1, 2)) {
            while(true) {
               int i = 0;

               try {
                  while(i < this.maxTaskExecutePerRun) {
                     Runnable task = (Runnable)this.tasks.poll();
                     if (task == null) {
                        break;
                     }

                     safeExecute(task);
                     ++i;
                  }
               } finally {
                  if (i == this.maxTaskExecutePerRun) {
                     try {
                        this.state.set(1);
                        this.executor.execute(this);
                        return;
                     } catch (Throwable var8) {
                        this.state.set(2);
                     }
                  }

                  this.state.set(0);
                  return;
               }
            }
         }
      }

      public boolean inEventLoop(Thread thread) {
         return false;
      }

      public boolean inEventLoop() {
         return false;
      }

      public boolean isShuttingDown() {
         return this.executor.isShutdown();
      }

      public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
         return this.executor.shutdownGracefully(quietPeriod, timeout, unit);
      }

      public Future terminationFuture() {
         return this.executor.terminationFuture();
      }

      public void shutdown() {
         this.executor.shutdown();
      }

      public boolean isShutdown() {
         return this.executor.isShutdown();
      }

      public boolean isTerminated() {
         return this.executor.isTerminated();
      }

      public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
         return this.executor.awaitTermination(timeout, unit);
      }

      public void execute(Runnable command) {
         if (!this.tasks.offer(command)) {
            throw new RejectedExecutionException();
         } else {
            if (this.state.compareAndSet(0, 1)) {
               try {
                  this.executor.execute(this);
               } catch (Throwable var3) {
                  this.tasks.remove(command);
                  PlatformDependent.throwException(var3);
               }
            }

         }
      }
   }
}
