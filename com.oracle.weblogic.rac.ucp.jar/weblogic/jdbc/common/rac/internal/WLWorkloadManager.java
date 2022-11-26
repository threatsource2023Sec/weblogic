package weblogic.jdbc.common.rac.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import oracle.ons.spi.WorkloadManager;
import weblogic.kernel.WLSThread;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class WLWorkloadManager implements WorkloadManager {
   private ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());

   public void schedule(Runnable r) {
      this.executorService.submit(r);
   }

   public void scheduleDelayed(final Runnable r, long delay) {
      TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      tm.schedule(new TimerListener() {
         public void timerExpired(Timer timer) {
            WLWorkloadManager.this.schedule(r);
         }
      }, delay);
   }

   class DaemonThreadFactory implements ThreadFactory {
      public Thread newThread(Runnable r) {
         Thread thread = new WLSThread(r);
         thread.setDaemon(true);
         return thread;
      }
   }
}
