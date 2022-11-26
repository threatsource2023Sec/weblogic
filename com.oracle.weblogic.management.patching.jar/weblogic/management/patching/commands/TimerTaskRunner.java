package weblogic.management.patching.commands;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import weblogic.management.patching.PatchingDebugLogger;

public class TimerTaskRunner {
   private final ExecutorService service;
   private final Callable task;
   private static final long timeout = 20L;

   public TimerTaskRunner(Callable task) {
      this.task = task;
      this.service = Executors.newSingleThreadExecutor();
   }

   public void execTask() throws Exception {
      Future control = this.service.submit(this.task);

      try {
         control.get(20L, TimeUnit.MINUTES);
      } catch (TimeoutException var7) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Exec task took more than the default allowed time : 20 minutes");
         }

         control.cancel(true);
         throw var7;
      } catch (Exception var8) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Exec task failed with error : " + var8.getMessage());
         }

         control.cancel(true);
         throw var8;
      } finally {
         this.service.shutdown();
      }

   }
}
