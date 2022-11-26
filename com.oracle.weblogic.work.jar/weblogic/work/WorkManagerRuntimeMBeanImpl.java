package weblogic.work;

import java.security.AccessController;
import java.util.ArrayList;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.CapacityRuntimeMBean;
import weblogic.management.runtime.ExecuteThread;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.RequestClassRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ThreadPoolRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WorkManagerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WorkManagerRuntimeMBean {
   private static final HealthState HEALTH_OK = new HealthState(0);
   private final ServerWorkManagerImpl wm;
   private MinThreadsConstraintRuntimeMBean minThreadsConstraintRuntimeMBean;
   private MaxThreadsConstraintRuntimeMBean maxThreadsConstraintRuntimeMBean;
   private RequestClassRuntimeMBean requestClassRuntimeMBean;
   private CapacityRuntimeMBean capacityRuntimeMBean;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int CRITICAL_STUCK_THRESHOLD_COUNT = getMarkCriticalStuckThresholdCount();
   private int pastStuckThreadCount;
   private long lastTime;

   WorkManagerRuntimeMBeanImpl(ServerWorkManagerImpl wm) throws ManagementException {
      super(wm.getName());
      this.wm = wm;
   }

   WorkManagerRuntimeMBeanImpl(ServerWorkManagerImpl wm, RuntimeMBean parent) throws ManagementException {
      super(wm.getName(), parent);
      this.wm = wm;
   }

   public static synchronized WorkManagerRuntimeMBean getWorkManagerRuntime(WorkManager wm, ApplicationRuntimeMBean applicationRuntimeMBean, RuntimeMBean parent) throws ManagementException {
      if (!(wm instanceof ServerWorkManagerImpl)) {
         return null;
      } else {
         ServerWorkManagerImpl swm = (ServerWorkManagerImpl)wm;
         WorkManagerRuntimeMBean wmRuntime = new WorkManagerRuntimeMBeanImpl(swm, parent);
         wmRuntime.setRequestClassRuntime(getRequestClassRuntime(swm.getRequestClass(), applicationRuntimeMBean, wmRuntime));
         wmRuntime.setMinThreadsConstraintRuntime(getMinThreadsConstraintRuntime(swm.getMinThreadsConstraint(), applicationRuntimeMBean, wmRuntime));
         wmRuntime.setMaxThreadsConstraintRuntime(getMaxThreadsConstraintRuntime(swm.getMaxThreadsConstraint(), applicationRuntimeMBean, wmRuntime));
         wmRuntime.setCapacityRuntime(getCapacityRuntime(swm.getOverloadManager(), wmRuntime));
         return wmRuntime;
      }
   }

   private static RequestClassRuntimeMBean getRequestClassRuntime(RequestClass rc, ApplicationRuntimeMBean applicationRuntimeMBean, WorkManagerRuntimeMBean wmRuntime) throws ManagementException {
      if (rc == null) {
         return null;
      } else {
         RequestClassRuntimeMBean requestClassRuntime = null;
         requestClassRuntime = applicationRuntimeMBean.lookupRequestClassRuntime(rc.getName());
         if (requestClassRuntime == null) {
            requestClassRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupRequestClassRuntime(rc.getName());
         }

         if (requestClassRuntime == null) {
            requestClassRuntime = new RequestClassRuntimeMBeanImpl(rc, wmRuntime, true);
         }

         return (RequestClassRuntimeMBean)requestClassRuntime;
      }
   }

   private static MinThreadsConstraintRuntimeMBean getMinThreadsConstraintRuntime(MinThreadsConstraint min, ApplicationRuntimeMBean applicationRuntimeMBean, WorkManagerRuntimeMBean wmRuntime) throws ManagementException {
      if (min == null) {
         return null;
      } else {
         MinThreadsConstraintRuntimeMBean minConstraintRuntime = null;
         minConstraintRuntime = applicationRuntimeMBean.lookupMinThreadsConstraintRuntime(min.getName());
         if (minConstraintRuntime == null) {
            minConstraintRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupMinThreadsConstraintRuntime(min.getName());
         }

         if (minConstraintRuntime == null) {
            minConstraintRuntime = new MinThreadsConstraintRuntimeMBeanImpl(min, wmRuntime, true);
         }

         return (MinThreadsConstraintRuntimeMBean)minConstraintRuntime;
      }
   }

   private static MaxThreadsConstraintRuntimeMBean getMaxThreadsConstraintRuntime(MaxThreadsConstraint max, ApplicationRuntimeMBean applicationRuntimeMBean, WorkManagerRuntimeMBean wmRuntime) throws ManagementException {
      if (max == null) {
         return null;
      } else {
         MaxThreadsConstraintRuntimeMBean maxConstraintRuntime = null;
         maxConstraintRuntime = applicationRuntimeMBean.lookupMaxThreadsConstraintRuntime(max.getName());
         if (maxConstraintRuntime == null) {
            maxConstraintRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupMaxThreadsConstraintRuntime(max.getName());
         }

         if (maxConstraintRuntime == null) {
            maxConstraintRuntime = new MaxThreadsConstraintRuntimeMBeanImpl(max, wmRuntime, true);
         }

         return (MaxThreadsConstraintRuntimeMBean)maxConstraintRuntime;
      }
   }

   private static CapacityRuntimeMBean getCapacityRuntime(OverloadManager overloadMgr, WorkManagerRuntimeMBean wmRuntime) throws ManagementException {
      if (overloadMgr == null) {
         return null;
      } else {
         CapacityRuntimeMBean capacityRuntimeMBean = new CapacityRuntimeMBeanImpl(overloadMgr, wmRuntime, true);
         return capacityRuntimeMBean;
      }
   }

   public String getApplicationName() {
      return this.wm.getApplicationName();
   }

   public String getModuleName() {
      return this.wm.getModuleName();
   }

   public int getPendingRequests() {
      int diff = (int)(this.wm.getAcceptedCount() - this.wm.getCompletedCount());
      int pendingRequestsCount = diff > 0 ? diff : 0;
      return pendingRequestsCount + this.getPendingDaemonRequests();
   }

   public long getCompletedRequests() {
      return this.wm.getCompletedCount() + this.getCompletedDaemonRequests();
   }

   public int getPendingDaemonRequests() {
      int diff = (int)(this.wm.getDaemonTasksStartedCount() - this.wm.getDaemonTasksCompletedCount());
      return diff > 0 ? diff : 0;
   }

   public long getCompletedDaemonRequests() {
      return this.wm.getDaemonTasksCompletedCount();
   }

   public int getStuckThreadCount() {
      if (this.timeToSync()) {
         ThreadPoolRuntimeMBean threadPoolRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getThreadPoolRuntime();
         ExecuteThread[] stuckThreads = threadPoolRuntime.getStuckExecuteThreads();
         if (stuckThreads == null) {
            this.pastStuckThreadCount = 0;
            return 0;
         } else {
            int stuckThreadCount = 0;

            for(int count = 0; count < stuckThreads.length; ++count) {
               if (this.claimThread(stuckThreads[count])) {
                  ++stuckThreadCount;
               }
            }

            this.pastStuckThreadCount = stuckThreadCount;
            return stuckThreadCount;
         }
      } else {
         return this.pastStuckThreadCount;
      }
   }

   private synchronized boolean timeToSync() {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastTime > 10000L) {
         this.lastTime = currentTime;
         return true;
      } else {
         return false;
      }
   }

   private boolean claimThread(ExecuteThread stuckThread) {
      Thread thread = stuckThread.getExecuteThread();
      if (thread != null && thread instanceof weblogic.work.ExecuteThread) {
         weblogic.work.ExecuteThread workExecuteThread = (weblogic.work.ExecuteThread)thread;
         return this.wm.equals(workExecuteThread.getWorkManager());
      } else {
         return compareNames(stuckThread.getWorkManagerName(), this.getName()) && compareNames(stuckThread.getApplicationName(), this.getApplicationName()) && compareNames(stuckThread.getModuleName(), this.getModuleName());
      }
   }

   private static boolean compareNames(String name1, String name2) {
      if (name1 == null && name2 == null) {
         return true;
      } else {
         return name1 != null && name1.equalsIgnoreCase(name2);
      }
   }

   public MinThreadsConstraintRuntimeMBean getMinThreadsConstraintRuntime() {
      return this.minThreadsConstraintRuntimeMBean;
   }

   public MaxThreadsConstraintRuntimeMBean getMaxThreadsConstraintRuntime() {
      return this.maxThreadsConstraintRuntimeMBean;
   }

   public void setMinThreadsConstraintRuntime(MinThreadsConstraintRuntimeMBean minThreadsConstraintRuntimeMBean) {
      this.minThreadsConstraintRuntimeMBean = minThreadsConstraintRuntimeMBean;
   }

   public void setMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean maxThreadsConstraintRuntimeMBean) {
      this.maxThreadsConstraintRuntimeMBean = maxThreadsConstraintRuntimeMBean;
   }

   public void setRequestClassRuntime(RequestClassRuntimeMBean requestClassRuntimeMBean) {
      this.requestClassRuntimeMBean = requestClassRuntimeMBean;
   }

   public RequestClassRuntimeMBean getRequestClassRuntime() {
      return this.requestClassRuntimeMBean;
   }

   public HealthState getHealthState() {
      ArrayList symptoms = new ArrayList();
      int healthState = 0;
      if (this.wm.isInternal()) {
         return HEALTH_OK;
      } else {
         Symptom symptom;
         if (this.wm.getOverloadManager() != null && !this.wm.getOverloadManager().canAcceptMore()) {
            healthState = 4;
            symptom = new Symptom(SymptomType.WORKMANAGER_OVERLOADED, Severity.HIGH, this.wm.getOverloadManager().getName(), ServerWorkManagerImpl.getOverloadMessage(this.wm.getOverloadManager()));
            symptoms.add(symptom);
         }

         if (!ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER.canAcceptMore()) {
            healthState = 4;
            symptom = new Symptom(SymptomType.WORKMANAGER_OVERLOADED, Severity.HIGH, ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER.getName(), ServerWorkManagerImpl.getOverloadMessage(ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER));
            symptoms.add(symptom);
         }

         if (ServerWorkManagerImpl.LOW_MEMORY_LISTENER.lowMemory()) {
            healthState = 4;
            symptom = new Symptom(SymptomType.LOW_MEMORY, Severity.HIGH, this.wm.getName(), this.wm.getLowMemoryMessage());
            symptoms.add(symptom);
         }

         if (this.wm.getStuckThreadManager() != null && this.wm.getStuckThreadManager().getStuckThreadCount() > CRITICAL_STUCK_THRESHOLD_COUNT) {
            healthState = 2;
            symptom = new Symptom(SymptomType.STUCK_THREADS, Severity.HIGH, this.wm.getName(), this.wm.getStuckThreadManager().getStuckThreadCount() + " stuck threads detected in WorkManager '" + this.wm.getName() + "'");
            symptoms.add(symptom);
         }

         if (symptoms.size() == 0) {
            return new HealthState(healthState);
         } else {
            Symptom[] list = new Symptom[symptoms.size()];
            list = (Symptom[])symptoms.toArray(list);
            return new HealthState(healthState, list, this.getPartitionName());
         }
      }
   }

   public String getPartitionName() {
      return this.wm.getPartitionName();
   }

   public CapacityRuntimeMBean getCapacityRuntime() {
      return this.capacityRuntimeMBean;
   }

   public void setCapacityRuntime(CapacityRuntimeMBean capacityRuntime) {
      this.capacityRuntimeMBean = capacityRuntime;
   }

   private static synchronized int getMarkCriticalStuckThresholdCount() {
      int markCriticalStuckCount = 0;

      try {
         if (!ManagementService.isRuntimeAccessInitialized() || null == ManagementService.getRuntimeAccess(kernelId) || null == ManagementService.getRuntimeAccess(kernelId).getServer() || null == ManagementService.getRuntimeAccess(kernelId).getServer().getOverloadProtection().getServerFailureTrigger()) {
            return markCriticalStuckCount;
         }

         int overloadStuckThreadCount = ManagementService.getRuntimeAccess(kernelId).getServer().getOverloadProtection().getServerFailureTrigger().getStuckThreadCount();
         if (overloadStuckThreadCount <= 0) {
            return markCriticalStuckCount;
         }

         int markCriticalStuckThresholdPercent = Integer.getInteger("weblogic.health.markCriticalStuckThresholdPercent", 0);
         if (overloadStuckThreadCount != 0 && markCriticalStuckThresholdPercent > 0 && markCriticalStuckThresholdPercent <= 100) {
            markCriticalStuckCount = overloadStuckThreadCount * markCriticalStuckThresholdPercent / 100;
         }
      } catch (Exception var3) {
      }

      WorkManagerLogger.logMarkCriticalStuckThresholdCount(markCriticalStuckCount);
      return markCriticalStuckCount;
   }
}
