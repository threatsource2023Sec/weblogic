package org.jboss.weld.executor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.api.ExecutorServices;

public abstract class AbstractExecutorServices implements ExecutorServices {
   private static final long SHUTDOWN_TIMEOUT = 60L;
   private final ScheduledExecutorService timerExecutor = Executors.newScheduledThreadPool(1, new DaemonThreadFactory(new ThreadGroup("weld-workers"), "weld-timer-"));

   public ScheduledExecutorService getTimerExecutor() {
      return this.timerExecutor;
   }

   public List invokeAllAndCheckForExceptions(Collection tasks) {
      try {
         return this.checkForExceptions(this.getTaskExecutor().invokeAll(this.wrap(tasks)));
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         throw new DeploymentException(var3);
      }
   }

   public List invokeAllAndCheckForExceptions(ExecutorServices.TaskFactory factory) {
      return this.invokeAllAndCheckForExceptions((Collection)factory.createTasks(this.getThreadPoolSize()));
   }

   protected List checkForExceptions(List futures) {
      Iterator var2 = futures.iterator();

      while(var2.hasNext()) {
         Future result = (Future)var2.next();

         try {
            result.get();
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
            throw new WeldException(var6);
         } catch (ExecutionException var7) {
            Throwable cause = var7.getCause();
            if (cause instanceof RuntimeException) {
               throw (RuntimeException)RuntimeException.class.cast(cause);
            }

            throw new WeldException(cause);
         }
      }

      return futures;
   }

   protected abstract int getThreadPoolSize();

   public void cleanup() {
      this.shutdown();
   }

   protected void shutdown() {
      SecurityActions.shutdown(this.getTaskExecutor());
      SecurityActions.shutdown(this.getTimerExecutor());

      try {
         if (!this.getTaskExecutor().awaitTermination(60L, TimeUnit.SECONDS)) {
            SecurityActions.shutdownNow(this.getTaskExecutor());
            if (!this.getTaskExecutor().awaitTermination(60L, TimeUnit.SECONDS)) {
               BootstrapLogger.LOG.timeoutShuttingDownThreadPool(this.getTaskExecutor(), this);
            }
         }
      } catch (InterruptedException var3) {
         SecurityActions.shutdownNow(this.getTaskExecutor());
         Thread.currentThread().interrupt();
      }

      try {
         if (!this.getTimerExecutor().isShutdown()) {
            SecurityActions.shutdownNow(this.getTimerExecutor());
            if (!this.getTimerExecutor().awaitTermination(60L, TimeUnit.SECONDS)) {
               BootstrapLogger.LOG.timeoutShuttingDownThreadPool(this.getTimerExecutor(), this);
            }
         }
      } catch (InterruptedException var2) {
         SecurityActions.shutdownNow(this.getTimerExecutor());
         Thread.currentThread().interrupt();
      }

   }

   public Collection wrap(Collection tasks) {
      return tasks;
   }
}
