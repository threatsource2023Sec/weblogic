package org.python.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Supplier;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public abstract class AbstractIdleService implements Service {
   private final Supplier threadNameSupplier = new ThreadNameSupplier();
   private final Service delegate = new DelegateService();

   protected AbstractIdleService() {
   }

   protected abstract void startUp() throws Exception;

   protected abstract void shutDown() throws Exception;

   protected Executor executor() {
      return new Executor() {
         public void execute(Runnable command) {
            MoreExecutors.newThread((String)AbstractIdleService.this.threadNameSupplier.get(), command).start();
         }
      };
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

   protected String serviceName() {
      return this.getClass().getSimpleName();
   }

   private final class DelegateService extends AbstractService {
      private DelegateService() {
      }

      protected final void doStart() {
         MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
            public void run() {
               try {
                  AbstractIdleService.this.startUp();
                  DelegateService.this.notifyStarted();
               } catch (Throwable var2) {
                  DelegateService.this.notifyFailed(var2);
               }

            }
         });
      }

      protected final void doStop() {
         MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
            public void run() {
               try {
                  AbstractIdleService.this.shutDown();
                  DelegateService.this.notifyStopped();
               } catch (Throwable var2) {
                  DelegateService.this.notifyFailed(var2);
               }

            }
         });
      }

      public String toString() {
         return AbstractIdleService.this.toString();
      }

      // $FF: synthetic method
      DelegateService(Object x1) {
         this();
      }
   }

   private final class ThreadNameSupplier implements Supplier {
      private ThreadNameSupplier() {
      }

      public String get() {
         return AbstractIdleService.this.serviceName() + " " + AbstractIdleService.this.state();
      }

      // $FF: synthetic method
      ThreadNameSupplier(Object x1) {
         this();
      }
   }
}
