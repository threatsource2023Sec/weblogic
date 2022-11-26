package weblogic.management.mbeanservers.domainruntime.internal;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class JMXExecutor implements Executor {
   private static final int DELAY_PERIOD = 1000;
   private Timer timer = null;
   private final TimerManager timerManager;
   private boolean suspended = false;

   JMXExecutor() {
      WorkManager workManager = WorkManagerFactory.getInstance().find("weblogic.admin.RMI");
      TimerManagerFactory timerFactory = TimerManagerFactory.getTimerManagerFactory();
      this.timerManager = timerFactory.getTimerManager("JMXExecutor", workManager);
      this.timerManager.resume();
      this.suspended = false;
   }

   public void execute(Runnable runnable) throws RejectedExecutionException {
      if (!this.suspended) {
         this.timer = this.timerManager.schedule(this.getTimerListener(runnable), 1000L);
      }
   }

   public void cancel() {
      this.suspended = true;
      if (this.timer != null) {
         this.timer.cancel();
      }

   }

   private TimerListener getTimerListener(final Runnable command) {
      return new TimerListener() {
         public void timerExpired(Timer timer) {
            if (!JMXExecutor.this.suspended) {
               command.run();
            }
         }
      };
   }
}
