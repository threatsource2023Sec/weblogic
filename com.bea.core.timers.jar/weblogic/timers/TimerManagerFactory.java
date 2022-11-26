package weblogic.timers;

import weblogic.kernel.KernelInitializer;
import weblogic.kernel.KernelStatus;
import weblogic.timers.internal.TimerManagerFactoryImpl;
import weblogic.work.WorkManager;

public abstract class TimerManagerFactory {
   private static TimerManagerFactory timerManagerFactory;

   public static TimerManagerFactory getTimerManagerFactory() {
      if (timerManagerFactory != null) {
         return timerManagerFactory;
      } else {
         initialize();
         return timerManagerFactory;
      }
   }

   private static synchronized void initialize() {
      if (timerManagerFactory == null) {
         timerManagerFactory = new TimerManagerFactoryImpl();
         if (!KernelStatus.isInitialized()) {
            if (KernelStatus.isServer()) {
               throw new AssertionError("Attempt to access TimerManagerFactory before kernel is initialized on the server");
            }

            KernelInitializer.initializeWebLogicKernel();
         }

      }
   }

   public abstract TimerManager getDefaultTimerManager();

   public abstract TimerManager getTimerManager(String var1, String var2);

   public abstract TimerManager getTimerManager(String var1, WorkManager var2);

   public abstract TimerManager getTimerManager(String var1);

   public abstract commonj.timers.TimerManager getCommonjTimerManager(String var1, WorkManager var2);

   public abstract commonj.timers.TimerManager getCommonjTimerManager(TimerManager var1);

   public abstract void stopAllTimers();
}
