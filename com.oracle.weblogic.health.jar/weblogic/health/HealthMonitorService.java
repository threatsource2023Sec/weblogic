package weblogic.health;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.Loggable;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.work.ComponentWorkAdapter;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
public final class HealthMonitorService extends AbstractServerService implements TimerListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugHealth");
   private static AtomicLong panicTime = new AtomicLong(0L);
   @Inject
   private RuntimeAccess runtimeAccess;
   private static ArrayList callbackListeners = new ArrayList();
   private static final ConcurrentHashMap monSysTbl = new ConcurrentHashMap();
   private TimerManager timerManager;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager cicMgr;
   private static ServerFailureNotificationHandler serverFailureNotificationHandler;
   private static OOMENotifier OOME_NOTIFIER = new OOMENotifier() {
      public void notifyOOME(OutOfMemoryError oome) {
         HealthMonitorService.panic(oome);
      }
   };

   public void start() {
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("HealthMonitorTask", WorkManagerFactory.getInstance().getSystem());
      int interval = this.runtimeAccess.getServer().getHealthCheckIntervalSeconds();
      this.timerManager.scheduleAtFixedRate(this, (long)(interval * 1000), (long)(interval * 1000));
   }

   public void stop() {
      this.shutdown();
   }

   public void halt() {
      this.shutdown();
   }

   private void shutdown() {
      if (this.timerManager != null) {
         this.timerManager.stop();
      }

   }

   private static ComponentInvocationContextManager getComponentInvocationContextManager() {
      if (cicMgr == null) {
         cicMgr = ComponentInvocationContextManager.getInstance();
      }

      return cicMgr;
   }

   public static synchronized void setServerFailureNotificationHandler(ServerFailureNotificationHandler handler) {
      if (serverFailureNotificationHandler != null) {
         throw new IllegalStateException("ServerFailureNotificationHandler already set");
      } else {
         serverFailureNotificationHandler = handler;
      }
   }

   private static String getCurrentPartitionName() {
      String pname = null;
      ComponentInvocationContext compCtx = getComponentInvocationContextManager().getCurrentComponentInvocationContext();
      if (compCtx != null) {
         pname = compCtx.getPartitionName();
      }

      return pname;
   }

   public static OOMENotifier getOomeNotifier() {
      return OOME_NOTIFIER;
   }

   public static void register(String keyArg, HealthFeedback healthFeedback, boolean isCriticalArg) {
      String partition = getCurrentPartitionName();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("> HealthMonitorService::register keyArg=" + keyArg + " isCritical=" + isCriticalArg + " healthFeedback=" + healthFeedback);
      }

      Debug.assertion(keyArg != null && healthFeedback != null);
      MonitoredSystemTableKey key = new MonitoredSystemTableKey(keyArg, partition);
      if (healthFeedback instanceof RuntimeMBean) {
         synchronized(monSysTbl) {
            monSysTbl.put(key, new MonitoredSystemTableEntry(key, (RuntimeMBean)healthFeedback, isCriticalArg));
         }
      } else {
         synchronized(monSysTbl) {
            monSysTbl.put(key, new MonitoredSystemTableEntry(key, healthFeedback, isCriticalArg));
         }
      }

   }

   public static void registerForCallback(HealthFeedbackCallback hfc) {
      synchronized(callbackListeners) {
         callbackListeners.add(hfc);
      }
   }

   public static void deregisterForCallback(HealthFeedbackCallback hfc) {
      synchronized(callbackListeners) {
         callbackListeners.remove(hfc);
      }
   }

   private static void notifyListeners(HealthState hs) {
      synchronized(callbackListeners) {
         Iterator iter = callbackListeners.iterator();

         while(iter.hasNext()) {
            ((HealthFeedbackCallback)iter.next()).healthStateChange(hs);
         }

      }
   }

   public static void unregister(String keyArg) {
      String partition = getCurrentPartitionName();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("> HealthMonitorService::unregister keyArg = " + keyArg);
      }

      Debug.assertion(keyArg != null);
      MonitoredSystemTableKey key = new MonitoredSystemTableKey(keyArg, partition);
      synchronized(monSysTbl) {
         monSysTbl.remove(key);
      }
   }

   public static synchronized void subsystemFailedNonFatal(String name, String reason) {
      String partition = getCurrentPartitionName();
      MonitoredSystemTableKey key = new MonitoredSystemTableKey(name, partition);
      MonitoredSystemTableEntry entry = (MonitoredSystemTableEntry)monSysTbl.get(key);
      HealthLogger.logNonCriticalSubsystemFailedWithReason(name, partition, reason);
      if (entry != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new SubsystemFailedNonFatalHandler(partition, entry));
      } else {
         HealthLogger.logNoRegisteredSubsystem(name, partition, reason);
      }

   }

   public static synchronized void subsystemFailed(final String name, final String reason) {
      final String partition = getCurrentPartitionName();
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            Loggable loggable = HealthLogger.logErrorSubsystemFailedWithReasonLoggable(name, partition, reason);
            loggable.log();
            if (HealthMonitorService.isGlobalPartition(partition) && HealthMonitorService.serverFailureNotificationHandler != null) {
               HealthMonitorService.serverFailureNotificationHandler.failed(loggable.getMessage());
            }

         }
      });
   }

   public static synchronized void subsystemFailedForceShutdown(final String name, final String reason) {
      final String partition = getCurrentPartitionName();
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            Loggable loggable = HealthLogger.logErrorSubsystemFailedWithReasonLoggable(name, partition, reason);
            loggable.log();
            if (HealthMonitorService.serverFailureNotificationHandler != null) {
               HealthMonitorService.serverFailureNotificationHandler.failedForceShutdown(loggable.getMessage());
            }

         }
      });
   }

   public static void panic(Throwable th) {
      long currentTime = System.currentTimeMillis();
      boolean firstPanicCall = panicTime.compareAndSet(0L, currentTime);
      ServerMBean smb = ManagementService.getRuntimeAccess(kernelId).getServer();
      if ((smb.getAutoKillIfFailed() || "system-exit".equals(smb.getOverloadProtection().getPanicAction())) && serverFailureNotificationHandler != null) {
         if (firstPanicCall) {
            serverFailureNotificationHandler.exitImmediately(th);
         } else {
            long timeoutMillis = 1000L * (long)smb.getServerLifeCycleTimeoutVal();
            long haltTime = currentTime + timeoutMillis;

            while(System.currentTimeMillis() < haltTime) {
               try {
                  Thread.sleep(100L);
               } catch (InterruptedException var10) {
               }
            }

            serverFailureNotificationHandler.haltImmediately();
         }
      }

   }

   public void timerExpired(Timer timer) {
      DEBUG.debug("> HealthMonitorTask::run (10)");
      synchronized(monSysTbl) {
         if (monSysTbl.size() == 0) {
            return;
         }

         Iterator i = monSysTbl.values().iterator();

         while(i.hasNext()) {
            MonitoredSystemTableEntry entry = (MonitoredSystemTableEntry)i.next();
            String subsystem = entry.getKey().getId();
            String partition = entry.getKey().getPartition();
            HealthState hs = entry.getHealthFeedback().getHealthState();
            hs.setSubsystemName(subsystem);
            hs.setCritical(entry.getIsCritical());
            hs.setMBeanName(entry.getMBeanName());
            hs.setMBeanType(entry.getMBeanType());
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Health state of " + entry.getKey() + " is " + HealthState.mapToString(hs.getState()));
            }

            if (hs.getState() == 3) {
               notifyListeners(hs);
            }

            if (entry.getIsCritical() && hs.getState() == 3) {
               Loggable loggable = HealthLogger.logErrorSubsystemFailedLoggable(subsystem, partition);
               loggable.log();
               if (isGlobalPartition(partition) && serverFailureNotificationHandler != null) {
                  serverFailureNotificationHandler.failed(loggable.getMessage());
               }
               break;
            }
         }
      }

      DEBUG.debug("< HealthMonitorTask::run (20)");
   }

   private static boolean isGlobalPartition(String partition) {
      return partition == null || partition.equals("DOMAIN");
   }

   public static HealthState[] getHealthStates() {
      ArrayList list = new ArrayList();
      Iterator var1 = monSysTbl.values().iterator();

      while(var1.hasNext()) {
         MonitoredSystemTableEntry entry = (MonitoredSystemTableEntry)var1.next();
         String partition = entry.getKey().getPartition();
         if (isGlobalPartition(partition)) {
            list.add(entry.getHealthState());
         }
      }

      return (HealthState[])list.toArray(new HealthState[list.size()]);
   }

   public static HealthState[] getPartitionHealthStates(String partition) {
      ArrayList list = new ArrayList();
      Iterator var2 = monSysTbl.values().iterator();

      while(var2.hasNext()) {
         MonitoredSystemTableEntry entry = (MonitoredSystemTableEntry)var2.next();
         if (partition.equals(entry.getKey().getPartition())) {
            list.add(entry.getHealthState());
         }
      }

      return (HealthState[])list.toArray(new HealthState[list.size()]);
   }

   private static class SubsystemFailedNonFatalHandler extends ComponentWorkAdapter {
      private String partition;
      private MonitoredSystemTableEntry entry;

      SubsystemFailedNonFatalHandler(String partition, MonitoredSystemTableEntry entry) {
         this.partition = partition;
         this.entry = entry;
      }

      public void run() {
         HealthState hs = this.entry.getHealthFeedback().getHealthState();
         hs.setSubsystemName(this.entry.getKey().getId());
         hs.setCritical(this.entry.getIsCritical());
         hs.setMBeanName(this.entry.getMBeanName());
         hs.setMBeanType(this.entry.getMBeanType());
         hs.setPartitionName(this.partition);
         HealthMonitorService.notifyListeners(hs);
      }
   }
}
