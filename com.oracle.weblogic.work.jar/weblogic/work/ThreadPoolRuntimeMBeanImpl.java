package weblogic.work;

import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ExecuteThread;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ThreadPoolRuntimeMBean;
import weblogic.platform.VM;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;

public final class ThreadPoolRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ThreadPoolRuntimeMBean {
   private static final boolean DESTRUCTIVE_DUMP = false;
   private static final String SHARED_CAPACITY_EXCEEDED = "Shared WorkManager capacity exceeded";
   private static final String STUCK_THREADS = "ThreadPool has stuck threads";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final HealthState HEALTH_OK = new HealthState(0);
   private static HealthState healthOverloaded;
   private static HealthState healthStuckThreads;
   private final transient RequestManager requestManager;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private HealthState healthState;

   ThreadPoolRuntimeMBeanImpl(RequestManager rm) throws ManagementException {
      super("ThreadPoolRuntime");
      this.healthState = HEALTH_OK;
      this.requestManager = rm;
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setThreadPoolRuntime(this);
   }

   public ExecuteThread[] getExecuteThreads() {
      AuthenticatedSubject subject = (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(KERNEL_ID);
      boolean includeDetails = subject != null ? SubjectUtils.isUserAnAdministrator(subject) : false;
      weblogic.work.ExecuteThread[] threads = this.requestManager.getExecuteThreads(PartitionUtility.getCurrentPartitionName(true));
      int length = threads.length;
      ExecuteThread[] returnThreads = new ExecuteThread[length];

      for(int i = 0; i < length; ++i) {
         returnThreads[i] = new ExecuteThreadRuntime(threads[i], includeDetails);
      }

      return returnThreads;
   }

   public ExecuteThread getExecuteThread(String threadName) {
      if (threadName != null) {
         weblogic.work.ExecuteThread[] var2 = this.requestManager.getExecuteThreads(PartitionUtility.getCurrentPartitionName(true));
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            weblogic.work.ExecuteThread thread = var2[var4];
            if (thread.getName() != null && threadName.equals(thread.getName())) {
               return new ExecuteThreadRuntime(thread);
            }
         }
      }

      return null;
   }

   public int getStuckThreadCount() {
      long stuckThreadMaxTime = getConfiguredStuckThreadMaxTime(ManagementService.getRuntimeAccess(kernelId).getServer());
      List list = this.requestManager.getStuckThreads(stuckThreadMaxTime, PartitionUtility.getCurrentPartitionName(true));
      return list != null ? list.size() : 0;
   }

   public ExecuteThread[] getStuckExecuteThreads() {
      long stuckThreadMaxTime = getConfiguredStuckThreadMaxTime(ManagementService.getRuntimeAccess(kernelId).getServer());
      List list = this.requestManager.getStuckThreads(stuckThreadMaxTime, PartitionUtility.getCurrentPartitionName(true));
      if (list != null && list.size() != 0) {
         int length = list.size();
         ExecuteThread[] returnThreads = new ExecuteThread[length];

         for(int i = 0; i < length; ++i) {
            returnThreads[i] = new ExecuteThreadRuntime((weblogic.work.ExecuteThread)list.get(i));
         }

         return returnThreads;
      } else {
         return null;
      }
   }

   private static final long getConfiguredStuckThreadMaxTime(ServerMBean serverMBean) {
      return serverMBean.getOverloadProtection().getServerFailureTrigger() != null ? (long)serverMBean.getOverloadProtection().getServerFailureTrigger().getMaxStuckThreadTime() * 1000L : (long)serverMBean.getStuckThreadMaxTime() * 1000L;
   }

   public int getExecuteThreadTotalCount() {
      return this.requestManager.getExecuteThreadCount(PartitionUtility.getCurrentPartitionName(true));
   }

   public int getHoggingThreadCount() {
      return this.requestManager.getHogSize();
   }

   public int getStandbyThreadCount() {
      return this.requestManager.getStandbyCount();
   }

   public int getExecuteThreadIdleCount() {
      return this.requestManager.getIdleThreadCount();
   }

   public int getPendingUserRequestCount() {
      return ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER.getQueueDepth();
   }

   public int getQueueLength() {
      return this.requestManager.getQueueDepth();
   }

   public int getSharedCapacityForWorkManagers() {
      return ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER.getCapacity();
   }

   public long getCompletedRequestCount() {
      return this.requestManager.getQueueDepartures();
   }

   public double getThroughput() {
      double value = this.requestManager.getThroughput();
      return value > 0.0 ? value : 0.0;
   }

   public int getMinThreadsConstraintsPending() {
      return this.requestManager.getMustRunCount();
   }

   public long getMinThreadsConstraintsCompleted() {
      return this.requestManager.getMinThreadsConstraintsCompleted();
   }

   public boolean isSuspended() {
      return false;
   }

   public HealthState getHealthState() {
      HealthState state = HEALTH_OK;
      if (!ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER.canAcceptMore()) {
         if (healthOverloaded == null) {
            healthOverloaded = new HealthState(4, new Symptom(SymptomType.WORKMANAGER_SHARED_CAPACITY_EXCEEDED, Severity.HIGH, this.getName(), "Shared WorkManager capacity exceeded"));
         }

         state = healthOverloaded;
      } else if (this.getStuckThreadCount() > 0) {
         if (healthStuckThreads == null) {
            healthStuckThreads = new HealthState(1, new Symptom(SymptomType.STUCK_THREADS, Severity.HIGH, this.getName(), "ThreadPool has stuck threads"));
         }

         state = healthStuckThreads;
      }

      if (state != this.healthState) {
         this._postSet("HealthState", this.healthState, state);
         this.healthState = state;
      }

      return this.healthState;
   }

   public int getOverloadRejectedRequestsCount() {
      return ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER.getRejectedRequestsCount();
   }

   private void dumpAndDestroy() {
      synchronized(RequestManager.getInstance()) {
         this.log("\n\n" + VM.getVM().threadDumpAsString() + "\n\n");
         PriorityRequestQueue queue = RequestManager.getInstance().queue;

         for(int count = 0; count < queue.size(); ++count) {
            WorkAdapter obj = (WorkAdapter)queue.pop();
            this.log("---- count " + count + " ------------- ");
            this.log(obj.dump() + "\n");
         }

         this.log("###### PRINTING MIN THREADS CONSTRAINTS #######");
         List mtcs = RequestManager.getInstance().minThreadsConstraints;
         Iterator var9 = mtcs.iterator();

         while(var9.hasNext()) {
            MinThreadsConstraint mtc = (MinThreadsConstraint)var9.next();
            this.log("@@@@@@@@ MTC @@@@@@ " + mtc.getName());
            mtc.dumpAndDestroy();
         }

      }
   }

   private void log(String s) {
      System.out.println(s);
   }
}
