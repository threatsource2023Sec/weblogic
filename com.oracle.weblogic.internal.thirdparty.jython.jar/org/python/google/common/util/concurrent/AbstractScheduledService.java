package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Supplier;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public abstract class AbstractScheduledService implements Service {
   private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
   private final AbstractService delegate = new ServiceDelegate();

   protected AbstractScheduledService() {
   }

   protected abstract void runOneIteration() throws Exception;

   protected void startUp() throws Exception {
   }

   protected void shutDown() throws Exception {
   }

   protected abstract Scheduler scheduler();

   protected ScheduledExecutorService executor() {
      class ThreadFactoryImpl implements ThreadFactory {
         public Thread newThread(Runnable runnable) {
            return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
         }
      }

      final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryImpl());
      this.addListener(new Service.Listener() {
         public void terminated(Service.State from) {
            executor.shutdown();
         }

         public void failed(Service.State from, Throwable failure) {
            executor.shutdown();
         }
      }, MoreExecutors.directExecutor());
      return executor;
   }

   protected String serviceName() {
      return this.getClass().getSimpleName();
   }

   public String toString() {
      return this.serviceName() + " [" + this.state() + "]";
   }

   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   public final Service.State state() {
      return this.delegate.state();
   }

   public final void addListener(Service.Listener listener, Executor executor) {
      this.delegate.addListener(listener, executor);
   }

   public final Throwable failureCause() {
      return this.delegate.failureCause();
   }

   @CanIgnoreReturnValue
   public final Service startAsync() {
      this.delegate.startAsync();
      return this;
   }

   @CanIgnoreReturnValue
   public final Service stopAsync() {
      this.delegate.stopAsync();
      return this;
   }

   public final void awaitRunning() {
      this.delegate.awaitRunning();
   }

   public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
      this.delegate.awaitRunning(timeout, unit);
   }

   public final void awaitTerminated() {
      this.delegate.awaitTerminated();
   }

   public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
      this.delegate.awaitTerminated(timeout, unit);
   }

   @Beta
   public abstract static class CustomScheduler extends Scheduler {
      public CustomScheduler() {
         super(null);
      }

      final Future schedule(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
         ReschedulableCallable task = new ReschedulableCallable(service, executor, runnable);
         task.reschedule();
         return task;
      }

      protected abstract Schedule getNextSchedule() throws Exception;

      @Beta
      protected static final class Schedule {
         private final long delay;
         private final TimeUnit unit;

         public Schedule(long delay, TimeUnit unit) {
            this.delay = delay;
            this.unit = (TimeUnit)Preconditions.checkNotNull(unit);
         }
      }

      private class ReschedulableCallable extends ForwardingFuture implements Callable {
         private final Runnable wrappedRunnable;
         private final ScheduledExecutorService executor;
         private final AbstractService service;
         private final ReentrantLock lock = new ReentrantLock();
         @GuardedBy("lock")
         private Future currentFuture;

         ReschedulableCallable(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
            this.wrappedRunnable = runnable;
            this.executor = executor;
            this.service = service;
         }

         public Void call() throws Exception {
            this.wrappedRunnable.run();
            this.reschedule();
            return null;
         }

         public void reschedule() {
            Schedule schedule;
            try {
               schedule = CustomScheduler.this.getNextSchedule();
            } catch (Throwable var8) {
               this.service.notifyFailed(var8);
               return;
            }

            Throwable scheduleFailure = null;
            this.lock.lock();

            try {
               if (this.currentFuture == null || !this.currentFuture.isCancelled()) {
                  this.currentFuture = this.executor.schedule(this, schedule.delay, schedule.unit);
               }
            } catch (Throwable var9) {
               scheduleFailure = var9;
            } finally {
               this.lock.unlock();
            }

            if (scheduleFailure != null) {
               this.service.notifyFailed(scheduleFailure);
            }

         }

         public boolean cancel(boolean mayInterruptIfRunning) {
            this.lock.lock();

            boolean var2;
            try {
               var2 = this.currentFuture.cancel(mayInterruptIfRunning);
            } finally {
               this.lock.unlock();
            }

            return var2;
         }

         public boolean isCancelled() {
            this.lock.lock();

            boolean var1;
            try {
               var1 = this.currentFuture.isCancelled();
            } finally {
               this.lock.unlock();
            }

            return var1;
         }

         protected Future delegate() {
            throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
         }
      }
   }

   private final class ServiceDelegate extends AbstractService {
      private volatile Future runningTask;
      private volatile ScheduledExecutorService executorService;
      private final ReentrantLock lock;
      private final Runnable task;

      private ServiceDelegate() {
         this.lock = new ReentrantLock();
         this.task = new Task();
      }

      protected final void doStart() {
         this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new Supplier() {
            public String get() {
               return AbstractScheduledService.this.serviceName() + " " + ServiceDelegate.this.state();
            }
         });
         this.executorService.execute(new Runnable() {
            public void run() {
               ServiceDelegate.this.lock.lock();

               try {
                  AbstractScheduledService.this.startUp();
                  ServiceDelegate.this.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                  ServiceDelegate.this.notifyStarted();
               } catch (Throwable var5) {
                  ServiceDelegate.this.notifyFailed(var5);
                  if (ServiceDelegate.this.runningTask != null) {
                     ServiceDelegate.this.runningTask.cancel(false);
                  }
               } finally {
                  ServiceDelegate.this.lock.unlock();
               }

            }
         });
      }

      protected final void doStop() {
         this.runningTask.cancel(false);
         this.executorService.execute(new Runnable() {
            public void run() {
               try {
                  ServiceDelegate.this.lock.lock();

                  label49: {
                     try {
                        if (ServiceDelegate.this.state() == Service.State.STOPPING) {
                           AbstractScheduledService.this.shutDown();
                           break label49;
                        }
                     } finally {
                        ServiceDelegate.this.lock.unlock();
                     }

                     return;
                  }

                  ServiceDelegate.this.notifyStopped();
               } catch (Throwable var5) {
                  ServiceDelegate.this.notifyFailed(var5);
               }

            }
         });
      }

      public String toString() {
         return AbstractScheduledService.this.toString();
      }

      // $FF: synthetic method
      ServiceDelegate(Object x1) {
         this();
      }

      class Task implements Runnable {
         public void run() {
            ServiceDelegate.this.lock.lock();

            try {
               if (ServiceDelegate.this.runningTask.isCancelled()) {
                  return;
               }

               AbstractScheduledService.this.runOneIteration();
            } catch (Throwable var8) {
               try {
                  AbstractScheduledService.this.shutDown();
               } catch (Exception var7) {
                  AbstractScheduledService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", var7);
               }

               ServiceDelegate.this.notifyFailed(var8);
               ServiceDelegate.this.runningTask.cancel(false);
            } finally {
               ServiceDelegate.this.lock.unlock();
            }

         }
      }
   }

   public abstract static class Scheduler {
      public static Scheduler newFixedDelaySchedule(final long initialDelay, final long delay, final TimeUnit unit) {
         Preconditions.checkNotNull(unit);
         Preconditions.checkArgument(delay > 0L, "delay must be > 0, found %s", delay);
         return new Scheduler() {
            public Future schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
               return executor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
            }
         };
      }

      public static Scheduler newFixedRateSchedule(final long initialDelay, final long period, final TimeUnit unit) {
         Preconditions.checkNotNull(unit);
         Preconditions.checkArgument(period > 0L, "period must be > 0, found %s", period);
         return new Scheduler() {
            public Future schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
               return executor.scheduleAtFixedRate(task, initialDelay, period, unit);
            }
         };
      }

      abstract Future schedule(AbstractService var1, ScheduledExecutorService var2, Runnable var3);

      private Scheduler() {
      }

      // $FF: synthetic method
      Scheduler(Object x0) {
         this();
      }
   }
}
