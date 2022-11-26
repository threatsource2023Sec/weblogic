package weblogic.jaxrs.concurrent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.jersey.spi.ExecutorServiceProvider;

public class WeblogicExecutorServiceProvider implements ExecutorServiceProvider {
   public ExecutorService getExecutorService() {
      ManagedExecutorService executorService = null;

      try {
         InitialContext ic = new InitialContext();
         executorService = (ManagedExecutorService)ic.lookup("java:comp/DefaultManagedExecutorService");
         ic.close();
      } catch (NamingException var3) {
         throw new AssertionError(var3.getMessage());
      }

      return new ManagedExecutorServiceWrapper(executorService);
   }

   public void dispose(ExecutorService executorService) {
   }

   static class ManagedExecutorServiceWrapper implements ManagedExecutorService {
      private final ManagedExecutorService executorService;
      private volatile boolean shutdown = false;

      public ManagedExecutorServiceWrapper(ManagedExecutorService executorService) {
         this.executorService = executorService;
      }

      public void shutdown() {
         try {
            this.stopAndTerminate();
         } catch (Throwable var2) {
         }

      }

      public List shutdownNow() {
         try {
            this.stopAndTerminate();
         } catch (Throwable var2) {
         }

         return Collections.emptyList();
      }

      void stopAndTerminate() {
         if (!this.shutdown) {
            synchronized(this) {
               this.shutdown = true;
            }
         }

      }

      public boolean isShutdown() {
         return this.shutdown;
      }

      public boolean isTerminated() {
         return this.shutdown;
      }

      public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
         this.stopAndTerminate();
         return this.shutdown;
      }

      public Future submit(Callable task) {
         return this.executorService.submit(task);
      }

      public Future submit(Runnable task, Object result) {
         return this.executorService.submit(task, result);
      }

      public Future submit(Runnable task) {
         return this.executorService.submit(task);
      }

      public List invokeAll(Collection tasks) throws InterruptedException {
         return this.executorService.invokeAll(tasks);
      }

      public List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
         return this.executorService.invokeAll(tasks, timeout, unit);
      }

      public Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
         return this.executorService.invokeAny(tasks);
      }

      public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
         return this.executorService.invokeAny(tasks, timeout, unit);
      }

      public void execute(Runnable command) {
         this.executorService.execute(command);
      }
   }
}
