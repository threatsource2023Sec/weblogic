package weblogic.t3.srvr;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.health.HealthMonitorService;
import weblogic.health.LowMemoryNotificationService;
import weblogic.health.MemoryEvent;
import weblogic.health.MemoryListener;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.kernel.Kernel;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.configuration.OverloadProtectionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ExecuteQueueRuntimeMBean;
import weblogic.management.runtime.ExecuteThread;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.ThreadPoolRuntimeMBean;
import weblogic.platform.GCMonitorThread;
import weblogic.platform.VM;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.io.Chunk;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(15)
public final class CoreHealthService extends AbstractServerService implements MemoryListener {
   @Inject
   private RuntimeAccess runtimeAccess;
   private static final String SUBSYSTEM_NAME = "core";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Timer healthTimer;
   private ServerRuntimeMBean serverRuntimeMBean;

   public void start() throws ServiceFailureException {
      try {
         ServerMBean serverMBean = this.runtimeAccess.getServer();
         this.serverRuntimeMBean = this.runtimeAccess.getServerRuntime();
         HealthMonitorService.register("ServerRuntime", this.serverRuntimeMBean, true);
         OverloadProtectionMBean olp = serverMBean.getOverloadProtection();
         int freeMemPercentHighThreshold = olp.getFreeMemoryPercentHighThreshold();
         int freeMemPercentLowThreshold = olp.getFreeMemoryPercentLowThreshold();
         GCMonitorThread.init();
         LowMemoryNotificationService.initialize(freeMemPercentLowThreshold, freeMemPercentHighThreshold);
         LowMemoryNotificationService.addMemoryListener(this);
         long stuckThreadsTimerInterval = (long)serverMBean.getStuckThreadTimerInterval() * 1000L;
         long stuckThreadMaxTime = this.getConfiguredStuckThreadMaxTime(serverMBean);
         TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.health.ThreadMonitor", WorkManagerFactory.getInstance().getSystem());
         this.healthTimer = timerManager.schedule(new ThreadMonitoringTimer(stuckThreadMaxTime, stuckThreadsTimerInterval, this.serverRuntimeMBean), 0L, stuckThreadsTimerInterval);
      } catch (Exception var10) {
         T3SrvrLogger.logWarnRegisterHealthMonitor("ServerRuntime", var10);
         throw new ServiceFailureException(var10);
      }
   }

   private long getConfiguredStuckThreadMaxTime(ServerMBean serverMBean) {
      return serverMBean.getOverloadProtection().getServerFailureTrigger() != null ? (long)serverMBean.getOverloadProtection().getServerFailureTrigger().getMaxStuckThreadTime() * 1000L : (long)serverMBean.getStuckThreadMaxTime() * 1000L;
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      try {
         HealthMonitorService.unregister("ServerRuntime");
         if (this.healthTimer != null) {
            this.healthTimer.cancel();
         }
      } catch (Exception var2) {
         T3SrvrLogger.logWarnUnregisterHealthMonitor("ServerRuntime", var2);
      }

   }

   public void memoryChanged(final MemoryEvent event) {
      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            if (event.getEventType() == 1) {
               Symptom symptom = new Symptom(SymptomType.LOW_MEMORY, Severity.HIGH, CoreHealthService.this.serverRuntimeMBean.getName(), "server is low on memory");
               CoreHealthService.this.serverRuntimeMBean.setHealthState(4, symptom);
               Chunk.signalLowMemoryCondition();
            }

            if (event.getEventType() == 0) {
               CoreHealthService.this.serverRuntimeMBean.setHealthState(0, (Symptom)null);
               Chunk.clearLowMemoryCondition();
            }

            return null;
         }
      });
   }

   private static final class ThreadMonitoringTimer implements TimerListener {
      private final long stuckThreadMaxTime;
      private final long timerInterval;
      private final ServerRuntimeMBean serverRuntimeMBean;
      private boolean alreadyDeadlocked;

      private ThreadMonitoringTimer(long stuckThreadMaxTime, long stuckThreadsTimerInterval, ServerRuntimeMBean serverRuntimeMBean) {
         this.alreadyDeadlocked = false;
         this.stuckThreadMaxTime = stuckThreadMaxTime;
         this.timerInterval = stuckThreadsTimerInterval;
         this.serverRuntimeMBean = serverRuntimeMBean;
      }

      public void timerExpired(Timer timer) {
         SecurityServiceManager.runAs(CoreHealthService.kernelId, CoreHealthService.kernelId, new PrivilegedAction() {
            public Object run() {
               ThreadMonitoringTimer.this.checkDeadlockedThreads();
               ThreadMonitoringTimer.this.checkStuckThreads();
               return null;
            }
         });
      }

      private void checkDeadlockedThreads() {
         if (!this.alreadyDeadlocked) {
            String dump = VM.getVM().dumpDeadlockedThreads();
            if (dump != null) {
               T3SrvrLogger.logDeadlockedThreads(dump);
               Symptom symptom = new Symptom(SymptomType.THREAD_DEADLOCK, Severity.HIGH, this.serverRuntimeMBean.getName(), "Thread deadlock detected.");
               this.serverRuntimeMBean.setHealthState(3, symptom);
               HealthMonitorService.subsystemFailed("core", "Thread deadlock detected");
               this.alreadyDeadlocked = true;
            }
         }
      }

      private void checkStuckThreads() {
         boolean failedServer = true;
         ExecuteQueueRuntimeMBean[] executeQueueRuntimes = this.serverRuntimeMBean.getExecuteQueueRuntimes();
         List applicationDispatchPolicies = Kernel.getApplicationDispatchPolicies();

         boolean fatal;
         for(int i = 0; i < executeQueueRuntimes.length; ++i) {
            if (applicationDispatchPolicies.contains(executeQueueRuntimes[i].getName())) {
               ExecuteQueueRuntimeMBean eqrmb = executeQueueRuntimes[i];
               fatal = this.logStuckThreads(eqrmb.getStuckExecuteThreads(), eqrmb.getExecuteThreadTotalCount(), eqrmb.getName());
               if (!fatal) {
                  failedServer = false;
               }
            }
         }

         ThreadPoolRuntimeMBean tprmb = this.serverRuntimeMBean.getThreadPoolRuntime();
         if (tprmb != null) {
            ExecuteThread[] stuckThreads = tprmb.getStuckExecuteThreads();
            fatal = this.logStuckThreads(stuckThreads, tprmb.getExecuteThreadTotalCount(), tprmb.getName());
            if (!fatal) {
               failedServer = false;
            }
         }

         if (failedServer) {
            HealthMonitorService.subsystemFailed("core", "All execute queues and the self-tuning thread pool are stuck");
         }

      }

      private boolean logStuckThreads(ExecuteThread[] stuckThreads, int totalCount, String queueName) {
         if (stuckThreads == null) {
            return false;
         } else {
            long currentTime = System.currentTimeMillis();

            for(int count = 0; count < stuckThreads.length; ++count) {
               ExecuteThread thread = stuckThreads[count];
               long elapsedTime = currentTime - thread.getCurrentRequestStartTime();
               long maxStuckThreadTime = thread.getStuckThreadActionMaxStuckThreadTime();
               if (maxStuckThreadTime <= 0L) {
                  maxStuckThreadTime = this.stuckThreadMaxTime;
               }

               if (this.logStuckThreadMessage(elapsedTime, maxStuckThreadTime)) {
                  String msg = VM.getVM().threadDumpAsString(thread.getExecuteThread());
                  T3SrvrLogger.logWarnPossibleStuckThread(thread.getName(), elapsedTime / 1000L, thread.getCurrentRequest(), maxStuckThreadTime / 1000L, thread.getStuckThreadActionName(), msg);
               }
            }

            if (totalCount == stuckThreads.length) {
               Symptom symptom = new Symptom(SymptomType.STUCK_THREADS, Severity.HIGH, queueName, "All Threads in the queue " + queueName + " are stuck.");
               this.serverRuntimeMBean.setHealthState(1, symptom);
               return true;
            } else {
               if (this.serverRuntimeMBean.getHealthState().getState() == 1) {
                  this.serverRuntimeMBean.setHealthState(0, "");
               }

               return false;
            }
         }
      }

      private boolean logStuckThreadMessage(long elapsedTime, long maxTime) {
         return elapsedTime > maxTime && elapsedTime < maxTime + 2L * this.timerInterval;
      }

      // $FF: synthetic method
      ThreadMonitoringTimer(long x0, long x1, ServerRuntimeMBean x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
