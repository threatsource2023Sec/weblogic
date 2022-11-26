package weblogic.timers.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.kernel.KernelStatus;
import weblogic.timers.RuntimeDomainSelector;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class TimerManagerFactoryImpl extends TimerManagerFactory {
   private static final String DEFAULT_TIMER_MANAGER = "weblogic.timers.DefaultTimerManager";
   TimerFactory timerFactory;
   private ConcurrentHashMap defaultTimerManagersMap = new ConcurrentHashMap();

   public TimerManagerFactoryImpl() {
      if (KernelStatus.isServer()) {
         try {
            Class c = Class.forName("weblogic.timers.internal.ServerTimerFactory");
            this.timerFactory = (TimerFactory)c.newInstance();
         } catch (ClassNotFoundException var2) {
         } catch (IllegalAccessException var3) {
         } catch (InstantiationException var4) {
         }
      }

      if (this.timerFactory == null) {
         this.timerFactory = new TimerFactory();
      }

   }

   public synchronized TimerManager getDefaultTimerManager() {
      String domainId = RuntimeDomainSelector.getDomain();
      TimerManager defaultTimerManager = (TimerManager)this.defaultTimerManagersMap.get(domainId);
      if (defaultTimerManager != null) {
         return defaultTimerManager;
      } else {
         TimerManager newDefaultTimerManager = TimerManagerImpl.getTimerManager("weblogic.timers.DefaultTimerManager", new WorkManagerExecutor(WorkManagerFactory.getInstance().getDefault()), this.timerFactory);
         defaultTimerManager = (TimerManager)this.defaultTimerManagersMap.putIfAbsent(domainId, newDefaultTimerManager);
         return (TimerManager)(defaultTimerManager == null ? newDefaultTimerManager : defaultTimerManager);
      }
   }

   public TimerManager getTimerManager(String name, String policy) {
      WorkManager wm = WorkManagerFactory.getInstance().find(policy);
      if (wm == null) {
         throw new IllegalArgumentException("No work manager for policy " + policy);
      } else {
         return this.getTimerManager(name, wm);
      }
   }

   public TimerManager getTimerManager(String name, WorkManager wm) {
      if ("weblogic.timers.DefaultTimerManager".equals(name)) {
         if (wm == WorkManagerFactory.getInstance().getDefault()) {
            return this.getDefaultTimerManager();
         } else {
            throw new IllegalArgumentException("Existing manager has different policy");
         }
      } else {
         if (wm == null) {
            wm = WorkManagerFactory.getInstance().getDefault();
         }

         return TimerManagerImpl.getTimerManager(name, new WorkManagerExecutor(wm), this.timerFactory);
      }
   }

   public TimerManager getTimerManager(String name) {
      return this.getTimerManager(name, (WorkManager)null);
   }

   public commonj.timers.TimerManager getCommonjTimerManager(String name, WorkManager wm) {
      return this.getCommonjTimerManager(this.getTimerManager(name, wm));
   }

   public commonj.timers.TimerManager getCommonjTimerManager(TimerManager manager) {
      return new weblogic.timers.internal.commonj.TimerManagerImpl(manager);
   }

   public void stopAllTimers() {
      Collection defaultTimersManagers = this.defaultTimerManagersMap.values();
      Iterator var2 = defaultTimersManagers.iterator();

      while(var2.hasNext()) {
         TimerManager defaultTimerManager = (TimerManager)var2.next();
         defaultTimerManager.stop();
      }

      TimerManagerImpl.stopAllTimers();
   }

   synchronized void cleanupForPartition() {
      String domainId = RuntimeDomainSelector.getDomain();
      this.defaultTimerManagersMap.remove(domainId);
   }

   class WorkManagerExecutor implements TimerManagerImpl.Executor {
      private final WorkManager workManager;

      WorkManagerExecutor(WorkManager workManager) {
         this.workManager = workManager;
      }

      public void execute(Runnable work) {
         this.workManager.schedule(work);
      }

      public boolean equals(TimerManagerImpl.Executor other) {
         if (!(other instanceof WorkManagerExecutor)) {
            return false;
         } else {
            return this.workManager == ((WorkManagerExecutor)other).workManager;
         }
      }

      public String toString() {
         return "TimerManagerFactoryImpl$WorkManagerExecutor - workManager is " + (this.workManager == null ? "null" : this.workManager.getClass() + ":" + this.workManager.toString());
      }
   }
}
