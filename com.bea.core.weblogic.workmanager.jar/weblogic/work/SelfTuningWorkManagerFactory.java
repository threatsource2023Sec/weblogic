package weblogic.work;

import com.oracle.core.registryhelper.utils.MonitorableMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.invocation.ComponentInvocationContext;

public class SelfTuningWorkManagerFactory extends WorkManagerFactory {
   protected ConcurrentHashMap partitionWorkManagerMaps = new ConcurrentHashMap();

   SelfTuningWorkManagerFactory() {
   }

   public static synchronized void initialize(int capacity) {
      if (SINGLETON == null) {
         SINGLETON = new SelfTuningWorkManagerFactory();
         SelfTuningWorkManagerImpl.initialize(capacity);
         ((SelfTuningWorkManagerFactory)SINGLETON).initializeHere();
      }
   }

   public static synchronized WorkManagerFactory getInstance() {
      if (SINGLETON != null) {
         return SINGLETON;
      } else {
         initialize(65536);
         return SINGLETON;
      }
   }

   protected void initializeHere() {
      this.REJECTOR = this.create("weblogic.Rejector", 5, -1);
      this.DEFAULT = this.create("weblogic.kernel.Default", -1, -1);
      this.SYSTEM = this.create("weblogic.kernel.System", 5, -1);
      WorkManager nonBlocking = this.create("weblogic.kernel.Non-Blocking", 3, -1);
      WorkManager direct = this.create("direct", -1, -1);
      this.byName.put("weblogic.kernel.System", this.SYSTEM);
      this.byName.put("weblogic.kernel.Non-Blocking", nonBlocking);
      this.byName.put("direct", direct);
      this.createDomainWorkManagers(this.byName);
      RequestManager.initInternalRequests((SelfTuningWorkManagerImpl)this.SYSTEM);
   }

   protected PartitionWorkManagersHolder createPartitionWorkManagerHolder() {
      MonitorableMap map = new MonitorableMap();
      WorkManager rejector = this.create("weblogic.Rejector", 5, -1);
      WorkManager defaultWM = this.create("weblogic.kernel.Default", -1, -1);
      WorkManager system = this.create("weblogic.kernel.System", 5, -1);
      WorkManager nonBlocking = this.create("weblogic.kernel.Non-Blocking", 3, -1);
      WorkManager direct = this.create("direct", -1, -1);
      map.put("weblogic.kernel.System", system);
      map.put("weblogic.kernel.Non-Blocking", nonBlocking);
      map.put("direct", direct);
      this.createDomainWorkManagers(map);
      return new PartitionWorkManagersHolder(map, system, defaultWM, rejector);
   }

   protected void createDomainWorkManagers(Map map) {
   }

   void startPartitionWorkManagerHolder() {
      Map domainWorkManagers = this.getDomainWorkManagersMap();
      this.startDomainWorkManagers(domainWorkManagers);
   }

   protected void startDomainWorkManagers(Map domainWorkManagers) {
   }

   void stopPartitionWorkManagerHolder() {
      Map domainWorkManagers = this.getDomainWorkManagersMap();
      this.stopDomainWorkManagers(domainWorkManagers);
      ComponentInvocationContext componentInvocationContext = PartitionUtility.getCurrentComponentInvocationContext();
      if (!componentInvocationContext.isGlobalRuntime()) {
         String partitionID = componentInvocationContext.getPartitionId();
         this.partitionWorkManagerMaps.remove(partitionID);
      }

   }

   protected void stopDomainWorkManagers(Map domainWorkManagers) {
   }

   public final WorkManager getSystem() {
      PartitionWorkManagersHolder partitionWorkManagersHolder = this.getPartitionWorkManagerHolderForCurrentPartition();
      return partitionWorkManagersHolder == null ? this.SYSTEM : partitionWorkManagersHolder.getSystem();
   }

   public final WorkManager getDefault() {
      PartitionWorkManagersHolder partitionWorkManagersHolder = this.getPartitionWorkManagerHolderForCurrentPartition();
      return partitionWorkManagersHolder == null ? this.DEFAULT : partitionWorkManagersHolder.getDefault();
   }

   protected final WorkManager getRejector() {
      PartitionWorkManagersHolder partitionWorkManagersHolder = this.getPartitionWorkManagerHolderForCurrentPartition();
      return partitionWorkManagersHolder == null ? this.REJECTOR : partitionWorkManagersHolder.getRejector();
   }

   protected SelfTuningWorkManagerImpl create(String workManagerName, int minThreadsConstraint, int maxThreadsConstraint) {
      return this.create(workManagerName, -1, -1, minThreadsConstraint, maxThreadsConstraint);
   }

   protected SelfTuningWorkManagerImpl create(String workManagerName, int fairshare, int responseTime, int minThreadsConstraint, int maxThreadsConstraint) {
      MinThreadsConstraint min = null;
      if (minThreadsConstraint != -1) {
         min = new MinThreadsConstraint(workManagerName, minThreadsConstraint);
         min.setShared(false);
      }

      MaxThreadsConstraint max = null;
      if (maxThreadsConstraint != -1) {
         max = new MaxThreadsConstraint(workManagerName, maxThreadsConstraint);
         max.setShared(false);
      }

      RequestClass requestClass = null;
      if (responseTime > 0) {
         requestClass = new ResponseTimeRequestClass(workManagerName, responseTime);
      } else if (fairshare > 0) {
         requestClass = new FairShareRequestClass(workManagerName, fairshare);
      }

      return create(workManagerName, (String)null, (String)null, (RequestClass)requestClass, max, min, (OverloadManager)null, (StuckThreadManager)null);
   }

   protected WorkManager create(String workManagerName, int priority, int minThreadsConstraint, int maxThreadsConstraint) {
      return this.create(workManagerName, priority, -1, minThreadsConstraint, maxThreadsConstraint);
   }

   protected WorkManager createResponseTime(String workManagerName, int responseTime, int minThreadsConstraint, int maxThreadsConstraint) {
      return this.create(workManagerName, -1, responseTime, minThreadsConstraint, maxThreadsConstraint);
   }

   protected static SelfTuningWorkManagerImpl create(String name, String appName, String moduleName, RequestClass p, MaxThreadsConstraint max, MinThreadsConstraint min, OverloadManager overload, StuckThreadManager stm) {
      return new SelfTuningWorkManagerImpl(name, appName, moduleName, p, max, min, overload, stm);
   }

   private PartitionWorkManagersHolder getPartitionWorkManagerHolderForCurrentPartition() {
      ComponentInvocationContext componentInvocationContext = PartitionUtility.getCurrentComponentInvocationContext();
      if (componentInvocationContext.isGlobalRuntime()) {
         return null;
      } else {
         String partitionID = componentInvocationContext.getPartitionId();
         PartitionWorkManagersHolder partitionWorkManagersHolder = (PartitionWorkManagersHolder)this.partitionWorkManagerMaps.get(partitionID);
         if (partitionWorkManagersHolder != null) {
            return partitionWorkManagersHolder;
         } else {
            synchronized(this.partitionWorkManagerMaps) {
               partitionWorkManagersHolder = (PartitionWorkManagersHolder)this.partitionWorkManagerMaps.get(partitionID);
               if (partitionWorkManagersHolder != null) {
                  return partitionWorkManagersHolder;
               } else {
                  PartitionWorkManagersHolder newPartitionWorkManagersHolder = this.createPartitionWorkManagerHolder();
                  this.partitionWorkManagerMaps.put(partitionID, newPartitionWorkManagersHolder);
                  return newPartitionWorkManagersHolder;
               }
            }
         }
      }
   }

   protected MonitorableMap getDomainWorkManagersMap() {
      PartitionWorkManagersHolder partitionWorkManagersHolder = this.getPartitionWorkManagerHolderForCurrentPartition();
      return partitionWorkManagersHolder == null ? this.byName : partitionWorkManagersHolder.getWorkManagerMap();
   }

   static class PartitionWorkManagersHolder {
      private MonitorableMap workManagerMap;
      private WorkManager system;
      private WorkManager defaultWM;
      private WorkManager rejector;

      PartitionWorkManagersHolder(MonitorableMap workManagerMap, WorkManager system, WorkManager defaultWM, WorkManager rejector) {
         this.workManagerMap = workManagerMap;
         this.system = system;
         this.defaultWM = defaultWM;
         this.rejector = rejector;
      }

      public MonitorableMap getWorkManagerMap() {
         return this.workManagerMap;
      }

      public WorkManager getSystem() {
         return this.system;
      }

      public WorkManager getDefault() {
         return this.defaultWM;
      }

      public WorkManager getRejector() {
         return this.rejector;
      }
   }
}
