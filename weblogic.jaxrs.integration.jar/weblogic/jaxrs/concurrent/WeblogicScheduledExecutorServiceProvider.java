package weblogic.jaxrs.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.jersey.spi.ScheduledExecutorServiceProvider;

public class WeblogicScheduledExecutorServiceProvider extends WeblogicExecutorServiceProvider implements ScheduledExecutorServiceProvider {
   public ScheduledExecutorService getExecutorService() {
      ManagedScheduledExecutorService executorService = null;

      try {
         InitialContext ic = new InitialContext();
         executorService = (ManagedScheduledExecutorService)ic.lookup("java:comp/DefaultManagedScheduledExecutorService");
         ic.close();
      } catch (NamingException var3) {
         throw new AssertionError(var3.getMessage());
      }

      return new ManagedScheduledExecutorServiceWrapper(executorService);
   }

   static class ManagedScheduledExecutorServiceWrapper extends WeblogicExecutorServiceProvider.ManagedExecutorServiceWrapper implements ManagedScheduledExecutorService {
      private final ManagedScheduledExecutorService executorService;

      ManagedScheduledExecutorServiceWrapper(ManagedScheduledExecutorService executorService) {
         super(executorService);
         this.executorService = executorService;
      }

      public ScheduledFuture schedule(Runnable command, Trigger trigger) {
         return this.executorService.schedule(command, trigger);
      }

      public ScheduledFuture schedule(Callable callable, Trigger trigger) {
         return this.executorService.schedule(callable, trigger);
      }

      public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
         return this.executorService.schedule(command, delay, unit);
      }

      public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
         return this.executorService.schedule(callable, delay, unit);
      }

      public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
         return this.executorService.scheduleAtFixedRate(command, initialDelay, period, unit);
      }

      public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
         return this.executorService.scheduleWithFixedDelay(command, initialDelay, delay, unit);
      }
   }
}
