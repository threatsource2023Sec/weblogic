package org.python.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Supplier;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public abstract class AbstractExecutionThreadService implements Service {
   private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
   private final Service delegate = new AbstractService() {
      protected final void doStart() {
         Executor executor = MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier() {
            public String get() {
               return AbstractExecutionThreadService.this.serviceName();
            }
         });
         executor.execute(new Runnable() {
            public void run() {
               try {
                  AbstractExecutionThreadService.this.startUp();
                  notifyStarted();
                  if (isRunning()) {
                     try {
                        AbstractExecutionThreadService.this.run();
                     } catch (Throwable var4) {
                        try {
                           AbstractExecutionThreadService.this.shutDown();
                        } catch (Exception var3) {
                           AbstractExecutionThreadService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", var3);
                        }

                        notifyFailed(var4);
                        return;
                     }
                  }

                  AbstractExecutionThreadService.this.shutDown();
                  notifyStopped();
               } catch (Throwable var5) {
                  notifyFailed(var5);
               }

            }
         });
      }

      protected void doStop() {
         AbstractExecutionThreadService.this.triggerShutdown();
      }

      public String toString() {
         return AbstractExecutionThreadService.this.toString();
      }
   };

   protected AbstractExecutionThreadService() {
   }

   protected void startUp() throws Exception {
   }

   protected abstract void run() throws Exception;

   protected void shutDown() throws Exception {
   }

   protected void triggerShutdown() {
   }

   protected Executor executor() {
      return new Executor() {
         public void execute(Runnable command) {
            MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), command).start();
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
}
