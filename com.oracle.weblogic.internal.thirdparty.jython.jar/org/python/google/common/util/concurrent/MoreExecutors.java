package org.python.google.common.util.concurrent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Supplier;
import org.python.google.common.base.Throwables;
import org.python.google.common.collect.Lists;
import org.python.google.common.collect.Queues;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class MoreExecutors {
   private MoreExecutors() {
   }

   @Beta
   @GwtIncompatible
   public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
      return (new Application()).getExitingExecutorService(executor, terminationTimeout, timeUnit);
   }

   @Beta
   @GwtIncompatible
   public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
      return (new Application()).getExitingScheduledExecutorService(executor, terminationTimeout, timeUnit);
   }

   @Beta
   @GwtIncompatible
   public static void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
      (new Application()).addDelayedShutdownHook(service, terminationTimeout, timeUnit);
   }

   @Beta
   @GwtIncompatible
   public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
      return (new Application()).getExitingExecutorService(executor);
   }

   @Beta
   @GwtIncompatible
   public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
      return (new Application()).getExitingScheduledExecutorService(executor);
   }

   @GwtIncompatible
   private static void useDaemonThreadFactory(ThreadPoolExecutor executor) {
      executor.setThreadFactory((new ThreadFactoryBuilder()).setDaemon(true).setThreadFactory(executor.getThreadFactory()).build());
   }

   @GwtIncompatible
   public static ListeningExecutorService newDirectExecutorService() {
      return new DirectExecutorService();
   }

   public static Executor directExecutor() {
      return MoreExecutors.DirectExecutor.INSTANCE;
   }

   @GwtIncompatible
   public static ListeningExecutorService listeningDecorator(ExecutorService delegate) {
      return (ListeningExecutorService)(delegate instanceof ListeningExecutorService ? (ListeningExecutorService)delegate : (delegate instanceof ScheduledExecutorService ? new ScheduledListeningDecorator((ScheduledExecutorService)delegate) : new ListeningDecorator(delegate)));
   }

   @GwtIncompatible
   public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService delegate) {
      return (ListeningScheduledExecutorService)(delegate instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService)delegate : new ScheduledListeningDecorator(delegate));
   }

   @GwtIncompatible
   static Object invokeAnyImpl(ListeningExecutorService executorService, Collection tasks, boolean timed, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      Preconditions.checkNotNull(executorService);
      Preconditions.checkNotNull(unit);
      int ntasks = tasks.size();
      Preconditions.checkArgument(ntasks > 0);
      List futures = Lists.newArrayListWithCapacity(ntasks);
      BlockingQueue futureQueue = Queues.newLinkedBlockingQueue();
      long timeoutNanos = unit.toNanos(timeout);
      boolean var28 = false;

      Object var19;
      try {
         var28 = true;
         ExecutionException ee = null;
         long lastTime = timed ? System.nanoTime() : 0L;
         Iterator it = tasks.iterator();
         futures.add(submitAndAddQueueListener(executorService, (Callable)it.next(), futureQueue));
         --ntasks;
         int active = 1;

         while(true) {
            Future f = (Future)futureQueue.poll();
            if (f == null) {
               if (ntasks > 0) {
                  --ntasks;
                  futures.add(submitAndAddQueueListener(executorService, (Callable)it.next(), futureQueue));
                  ++active;
               } else {
                  if (active == 0) {
                     if (ee == null) {
                        ee = new ExecutionException((Throwable)null);
                     }

                     throw ee;
                  }

                  if (timed) {
                     f = (Future)futureQueue.poll(timeoutNanos, TimeUnit.NANOSECONDS);
                     if (f == null) {
                        throw new TimeoutException();
                     }

                     long now = System.nanoTime();
                     timeoutNanos -= now - lastTime;
                     lastTime = now;
                  } else {
                     f = (Future)futureQueue.take();
                  }
               }
            }

            if (f != null) {
               --active;

               try {
                  var19 = f.get();
                  var28 = false;
                  break;
               } catch (ExecutionException var29) {
                  ee = var29;
               } catch (RuntimeException var30) {
                  ee = new ExecutionException(var30);
               }
            }
         }
      } finally {
         if (var28) {
            Iterator var23 = futures.iterator();

            while(var23.hasNext()) {
               Future f = (Future)var23.next();
               f.cancel(true);
            }

         }
      }

      Iterator var20 = futures.iterator();

      while(var20.hasNext()) {
         Future f = (Future)var20.next();
         f.cancel(true);
      }

      return var19;
   }

   @GwtIncompatible
   private static ListenableFuture submitAndAddQueueListener(ListeningExecutorService executorService, Callable task, final BlockingQueue queue) {
      final ListenableFuture future = executorService.submit(task);
      future.addListener(new Runnable() {
         public void run() {
            queue.add(future);
         }
      }, directExecutor());
      return future;
   }

   @Beta
   @GwtIncompatible
   public static ThreadFactory platformThreadFactory() {
      if (!isAppEngine()) {
         return Executors.defaultThreadFactory();
      } else {
         try {
            return (ThreadFactory)Class.forName("org.python.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory").invoke((Object)null);
         } catch (IllegalAccessException var1) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", var1);
         } catch (ClassNotFoundException var2) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", var2);
         } catch (NoSuchMethodException var3) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", var3);
         } catch (InvocationTargetException var4) {
            throw Throwables.propagate(var4.getCause());
         }
      }
   }

   @GwtIncompatible
   private static boolean isAppEngine() {
      if (System.getProperty("org.python.google.appengine.runtime.environment") == null) {
         return false;
      } else {
         try {
            return Class.forName("org.python.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment").invoke((Object)null) != null;
         } catch (ClassNotFoundException var1) {
            return false;
         } catch (InvocationTargetException var2) {
            return false;
         } catch (IllegalAccessException var3) {
            return false;
         } catch (NoSuchMethodException var4) {
            return false;
         }
      }
   }

   @GwtIncompatible
   static Thread newThread(String name, Runnable runnable) {
      Preconditions.checkNotNull(name);
      Preconditions.checkNotNull(runnable);
      Thread result = platformThreadFactory().newThread(runnable);

      try {
         result.setName(name);
      } catch (SecurityException var4) {
      }

      return result;
   }

   @GwtIncompatible
   static Executor renamingDecorator(final Executor executor, final Supplier nameSupplier) {
      Preconditions.checkNotNull(executor);
      Preconditions.checkNotNull(nameSupplier);
      return isAppEngine() ? executor : new Executor() {
         public void execute(Runnable command) {
            executor.execute(Callables.threadRenaming(command, nameSupplier));
         }
      };
   }

   @GwtIncompatible
   static ExecutorService renamingDecorator(ExecutorService service, final Supplier nameSupplier) {
      Preconditions.checkNotNull(service);
      Preconditions.checkNotNull(nameSupplier);
      return (ExecutorService)(isAppEngine() ? service : new WrappingExecutorService(service) {
         protected Callable wrapTask(Callable callable) {
            return Callables.threadRenaming(callable, nameSupplier);
         }

         protected Runnable wrapTask(Runnable command) {
            return Callables.threadRenaming(command, nameSupplier);
         }
      });
   }

   @GwtIncompatible
   static ScheduledExecutorService renamingDecorator(ScheduledExecutorService service, final Supplier nameSupplier) {
      Preconditions.checkNotNull(service);
      Preconditions.checkNotNull(nameSupplier);
      return (ScheduledExecutorService)(isAppEngine() ? service : new WrappingScheduledExecutorService(service) {
         protected Callable wrapTask(Callable callable) {
            return Callables.threadRenaming(callable, nameSupplier);
         }

         protected Runnable wrapTask(Runnable command) {
            return Callables.threadRenaming(command, nameSupplier);
         }
      });
   }

   @Beta
   @CanIgnoreReturnValue
   @GwtIncompatible
   public static boolean shutdownAndAwaitTermination(ExecutorService service, long timeout, TimeUnit unit) {
      long halfTimeoutNanos = unit.toNanos(timeout) / 2L;
      service.shutdown();

      try {
         if (!service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS)) {
            service.shutdownNow();
            service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS);
         }
      } catch (InterruptedException var7) {
         Thread.currentThread().interrupt();
         service.shutdownNow();
      }

      return service.isTerminated();
   }

   static Executor rejectionPropagatingExecutor(final Executor delegate, final AbstractFuture future) {
      Preconditions.checkNotNull(delegate);
      Preconditions.checkNotNull(future);
      return delegate == directExecutor() ? delegate : new Executor() {
         volatile boolean thrownFromDelegate = true;

         public void execute(final Runnable command) {
            try {
               delegate.execute(new Runnable() {
                  public void run() {
                     thrownFromDelegate = false;
                     command.run();
                  }
               });
            } catch (RejectedExecutionException var3) {
               if (this.thrownFromDelegate) {
                  future.setException(var3);
               }
            }

         }
      };
   }

   @GwtIncompatible
   private static final class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService {
      final ScheduledExecutorService delegate;

      ScheduledListeningDecorator(ScheduledExecutorService delegate) {
         super(delegate);
         this.delegate = (ScheduledExecutorService)Preconditions.checkNotNull(delegate);
      }

      public ListenableScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
         TrustedListenableFutureTask task = TrustedListenableFutureTask.create(command, (Object)null);
         ScheduledFuture scheduled = this.delegate.schedule(task, delay, unit);
         return new ListenableScheduledTask(task, scheduled);
      }

      public ListenableScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
         TrustedListenableFutureTask task = TrustedListenableFutureTask.create(callable);
         ScheduledFuture scheduled = this.delegate.schedule(task, delay, unit);
         return new ListenableScheduledTask(task, scheduled);
      }

      public ListenableScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
         NeverSuccessfulListenableFutureTask task = new NeverSuccessfulListenableFutureTask(command);
         ScheduledFuture scheduled = this.delegate.scheduleAtFixedRate(task, initialDelay, period, unit);
         return new ListenableScheduledTask(task, scheduled);
      }

      public ListenableScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
         NeverSuccessfulListenableFutureTask task = new NeverSuccessfulListenableFutureTask(command);
         ScheduledFuture scheduled = this.delegate.scheduleWithFixedDelay(task, initialDelay, delay, unit);
         return new ListenableScheduledTask(task, scheduled);
      }

      @GwtIncompatible
      private static final class NeverSuccessfulListenableFutureTask extends AbstractFuture implements Runnable {
         private final Runnable delegate;

         public NeverSuccessfulListenableFutureTask(Runnable delegate) {
            this.delegate = (Runnable)Preconditions.checkNotNull(delegate);
         }

         public void run() {
            try {
               this.delegate.run();
            } catch (Throwable var2) {
               this.setException(var2);
               throw Throwables.propagate(var2);
            }
         }
      }

      private static final class ListenableScheduledTask extends ForwardingListenableFuture.SimpleForwardingListenableFuture implements ListenableScheduledFuture {
         private final ScheduledFuture scheduledDelegate;

         public ListenableScheduledTask(ListenableFuture listenableDelegate, ScheduledFuture scheduledDelegate) {
            super(listenableDelegate);
            this.scheduledDelegate = scheduledDelegate;
         }

         public boolean cancel(boolean mayInterruptIfRunning) {
            boolean cancelled = super.cancel(mayInterruptIfRunning);
            if (cancelled) {
               this.scheduledDelegate.cancel(mayInterruptIfRunning);
            }

            return cancelled;
         }

         public long getDelay(TimeUnit unit) {
            return this.scheduledDelegate.getDelay(unit);
         }

         public int compareTo(Delayed other) {
            return this.scheduledDelegate.compareTo(other);
         }
      }
   }

   @GwtIncompatible
   private static class ListeningDecorator extends AbstractListeningExecutorService {
      private final ExecutorService delegate;

      ListeningDecorator(ExecutorService delegate) {
         this.delegate = (ExecutorService)Preconditions.checkNotNull(delegate);
      }

      public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
         return this.delegate.awaitTermination(timeout, unit);
      }

      public final boolean isShutdown() {
         return this.delegate.isShutdown();
      }

      public final boolean isTerminated() {
         return this.delegate.isTerminated();
      }

      public final void shutdown() {
         this.delegate.shutdown();
      }

      public final List shutdownNow() {
         return this.delegate.shutdownNow();
      }

      public final void execute(Runnable command) {
         this.delegate.execute(command);
      }
   }

   private static enum DirectExecutor implements Executor {
      INSTANCE;

      public void execute(Runnable command) {
         command.run();
      }

      public String toString() {
         return "MoreExecutors.directExecutor()";
      }
   }

   @GwtIncompatible
   private static final class DirectExecutorService extends AbstractListeningExecutorService {
      private final Object lock;
      @GuardedBy("lock")
      private int runningTasks;
      @GuardedBy("lock")
      private boolean shutdown;

      private DirectExecutorService() {
         this.lock = new Object();
         this.runningTasks = 0;
         this.shutdown = false;
      }

      public void execute(Runnable command) {
         this.startTask();

         try {
            command.run();
         } finally {
            this.endTask();
         }

      }

      public boolean isShutdown() {
         synchronized(this.lock) {
            return this.shutdown;
         }
      }

      public void shutdown() {
         synchronized(this.lock) {
            this.shutdown = true;
            if (this.runningTasks == 0) {
               this.lock.notifyAll();
            }

         }
      }

      public List shutdownNow() {
         this.shutdown();
         return Collections.emptyList();
      }

      public boolean isTerminated() {
         synchronized(this.lock) {
            return this.shutdown && this.runningTasks == 0;
         }
      }

      public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
         long nanos = unit.toNanos(timeout);
         synchronized(this.lock) {
            while(!this.shutdown || this.runningTasks != 0) {
               if (nanos <= 0L) {
                  return false;
               }

               long now = System.nanoTime();
               TimeUnit.NANOSECONDS.timedWait(this.lock, nanos);
               nanos -= System.nanoTime() - now;
            }

            return true;
         }
      }

      private void startTask() {
         synchronized(this.lock) {
            if (this.shutdown) {
               throw new RejectedExecutionException("Executor already shutdown");
            } else {
               ++this.runningTasks;
            }
         }
      }

      private void endTask() {
         synchronized(this.lock) {
            int numRunning = --this.runningTasks;
            if (numRunning == 0) {
               this.lock.notifyAll();
            }

         }
      }

      // $FF: synthetic method
      DirectExecutorService(Object x0) {
         this();
      }
   }

   @GwtIncompatible
   @VisibleForTesting
   static class Application {
      final ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
         MoreExecutors.useDaemonThreadFactory(executor);
         ExecutorService service = Executors.unconfigurableExecutorService(executor);
         this.addDelayedShutdownHook(service, terminationTimeout, timeUnit);
         return service;
      }

      final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
         MoreExecutors.useDaemonThreadFactory(executor);
         ScheduledExecutorService service = Executors.unconfigurableScheduledExecutorService(executor);
         this.addDelayedShutdownHook(service, terminationTimeout, timeUnit);
         return service;
      }

      final void addDelayedShutdownHook(final ExecutorService service, final long terminationTimeout, final TimeUnit timeUnit) {
         Preconditions.checkNotNull(service);
         Preconditions.checkNotNull(timeUnit);
         this.addShutdownHook(MoreExecutors.newThread("DelayedShutdownHook-for-" + service, new Runnable() {
            public void run() {
               try {
                  service.shutdown();
                  service.awaitTermination(terminationTimeout, timeUnit);
               } catch (InterruptedException var2) {
               }

            }
         }));
      }

      final ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
         return this.getExitingExecutorService(executor, 120L, TimeUnit.SECONDS);
      }

      final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
         return this.getExitingScheduledExecutorService(executor, 120L, TimeUnit.SECONDS);
      }

      @VisibleForTesting
      void addShutdownHook(Thread hook) {
         Runtime.getRuntime().addShutdownHook(hook);
      }
   }
}
