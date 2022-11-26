package weblogic.management.utils.jmsdlb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.DynamicLoadbalancer.Action;
import weblogic.cluster.migration.DynamicLoadbalancer.State;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DynamicDeploymentMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.utils.GenericManagedDeployment;

public class DLDynamicPlacement implements DynamicLoadbalancer, DLClusterRegistration {
   private static final int FAIL_OVER_INVALID = -2;
   private static final String SERVER_LEVEL_PROPERTY = "weblogic.jms.ha.FailOverLimit";
   private static final int SERVER_LEVEL_LIMIT = Integer.getInteger("weblogic.jms.ha.FailOverLimit", -2);
   private static int SYSTEM_DEFAULT_MAX_SERVICE_PER_NODE = -1;
   private static int DEFAULT_MAX_SERVICE_PER_NODE;
   int maxServicePerNode;
   Map deployed;
   private boolean init;
   private boolean dirty;
   private final DLContext context;
   private DLLayout balancer;
   DLUtil.DLLogger logger;
   private ActionPlan currentActionPlan;
   private DLCluster clusterState;
   ReentrantReadWriteLock rwlock;
   ReentrantReadWriteLock.ReadLock readlock;
   ReentrantReadWriteLock.WriteLock writelock;
   private static final String prefix_name = "JMSDlb";
   private static final String default_string = "default";
   private static final String defaultname = "JMSDlb_default";
   private static DLDynamicPlacement defaultDLB;
   private static Map loadBalancers;
   private String partition_name;
   private String cluster_name;
   private String name;

   private static final String getNameForDLB(String partitionName, String clusterName) {
      return partitionName == null ? "JMSDlb_default" : partitionName;
   }

   public static DLDynamicPlacement getSingleton(String partition_name, String cluster_name) {
      String targetName = getNameForDLB(partition_name, cluster_name);
      if (targetName == null) {
         return defaultDLB;
      } else {
         DLDynamicPlacement dlp = (DLDynamicPlacement)loadBalancers.get(targetName);
         if (dlp != null) {
            return dlp;
         } else {
            dlp = new DLDynamicPlacement("JMSDlb_" + targetName, partition_name, cluster_name);
            loadBalancers.put(targetName, dlp);
            return dlp;
         }
      }
   }

   public String getCluster() {
      return this.cluster_name;
   }

   public int getMaxServicesPerNode() {
      return this.maxServicePerNode;
   }

   public void setMaxServerPerNode(int instances) {
      this.maxServicePerNode = instances;
      if (this.balancer != null) {
         this.balancer.setMaxInstancesPerNode(this.maxServicePerNode);
      }

   }

   private DLDynamicPlacement(String name, String partitionName, String clusterName) {
      this.maxServicePerNode = DEFAULT_MAX_SERVICE_PER_NODE;
      this.deployed = new ConcurrentHashMap();
      this.init = false;
      this.currentActionPlan = null;
      this.rwlock = new ReentrantReadWriteLock();
      this.readlock = this.rwlock.readLock();
      this.writelock = this.rwlock.writeLock();
      this.name = name;
      this.cluster_name = clusterName;
      this.partition_name = partitionName;
      this.context = new DLContext(name, clusterName);
      this.logger = this.context.getDMUtil().getLogger();
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Initializing JMSDynamicPlacement:" + name);
      }

      this.clusterState = new DLCluster(this.context);
   }

   private void setDirty(boolean dirty) {
      this.dirty = dirty;
   }

   private boolean isDirty() {
      return this.dirty;
   }

   public DLMigratableGroupConfigImpl getGroupConfigMBean(String prefix, ServerMBean server, String configName) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Creating migratable group for : " + prefix + " on server at " + (server == null ? "none" : server.getName()));
      }

      DLMigratableGroupConfigImpl di = null;
      String name = configName != null && prefix != null ? prefix + "_" + configName : (prefix == null ? configName : prefix);
      this.writelock.lock();

      try {
         di = this.clusterState.getManagedGroup(name);
         if (di != null) {
            DLMigratableGroupConfigImpl var13 = di;
            return var13;
         } else {
            try {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Calling register instance for [prefix=" + prefix + ", configName=" + configName + "]");
               }

               String serverName = server == null ? null : server.getName();
               di = this.clusterState.registerInstance(prefix, name, serverName);
            } catch (IllegalStateException var11) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Internal Error: groupConfig Bean requested for unregistered config" + configName + " on server " + server);
               }

               Object var7 = null;
               return (DLMigratableGroupConfigImpl)var7;
            }

            this.setDirty(true);
            return di;
         }
      } finally {
         this.writelock.unlock();
      }
   }

   public static String createName(DynamicDeploymentMBean bean, String serverName, String instance) {
      return bean.getName() + "_" + (serverName == null ? serverName : instance);
   }

   public Set registerDeploymentBeans(DynamicDeploymentMBean... deploy) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Calling register deployment Beans");
      }

      this.setDirty(true);
      Set configNames = new HashSet();
      DynamicDeploymentMBean[] var3 = deploy;
      int var4 = deploy.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DynamicDeploymentMBean dbean = var3[var5];
         String name = dbean.getName();
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Registering deployment bean:" + name);
         }

         long failsec = -1L;
         int failOverLimit = true;
         long bootsec = -1L;
         int restartsec = true;
         long deploysec = -1L;
         DLStoreConfig.POLICY policy = null;
         boolean restartInPlace = false;
         int restartCnt = false;
         DLStoreConfig.DISTRIBUTION distribution = null;
         failsec = dbean.getFailbackDelaySeconds();
         String serviceLevelProperty;
         String prefix;
         if (dbean instanceof PersistentStoreMBean) {
            serviceLevelProperty = "weblogic.jms.ha.FailOverLimit.store." + name;
            prefix = "Persistent store '" + name + "'";
         } else {
            serviceLevelProperty = "weblogic.jms.ha.FailOverLimit.bridge." + name;
            prefix = "Messaging bridge '" + name + "'";
         }

         int failOverLimit = Integer.getInteger(serviceLevelProperty, -2);
         if (isValidFailOverLimit(failOverLimit)) {
            if (dbean instanceof PersistentStoreMBean) {
               ManagementLogger.logPersistentStoreFailOverLimit(name, failOverLimit, serviceLevelProperty);
            } else {
               ManagementLogger.logMessagingBridgeFailOverLimit(name, failOverLimit, serviceLevelProperty);
            }
         } else if (isValidFailOverLimit(SERVER_LEVEL_LIMIT)) {
            failOverLimit = SERVER_LEVEL_LIMIT;
         } else {
            failOverLimit = dbean.getFailOverLimit();
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(prefix + " has Fail-Over-Limit value '" + failOverLimit + "' configured via MBean.");
            }
         }

         bootsec = dbean.getInitialBootDelaySeconds();
         bootsec = bootsec == -1L ? 60L : bootsec;
         int restartsec = dbean.getSecondsBetweenRestarts();
         deploysec = dbean.getPartialClusterStabilityDelaySeconds();
         deploysec = deploysec == -1L ? 120L : deploysec;
         policy = DLStoreConfig.POLICY.getPolicy(dbean.getMigrationPolicy());
         if (policy == null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Invalid migration policy: " + dbean.getMigrationPolicy());
            }

            policy = DLStoreConfig.POLICY.OFF;
         }

         restartInPlace = dbean.getRestartInPlace();
         int restartCnt = dbean.getNumberOfRestartAttempts();
         distribution = DLStoreConfig.DISTRIBUTION.getDistribution(dbean.getDistributionPolicy());
         if (distribution == null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Invalid distribution policy: " + dbean.getDistributionPolicy());
            }

            distribution = DLStoreConfig.DISTRIBUTION.SINGLETON;
         }

         if (distribution == null && this.logger.isDebugEnabled()) {
            this.logger.debug("Invalid distribution policy: ");
         }

         this.writelock.lock();

         try {
            DLStoreConfig group = this.clusterState.getConfig(name);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Registered deployment bean info:" + name + "\n\tname=" + name + "\n\tpolicy=" + policy + "\n\tdistribution=" + distribution + "\n\trestartInPlace=" + restartInPlace + "\n\tfailsec=" + failsec + "\n\tfailOverLimit=" + failOverLimit + "\n\tbootsec=" + bootsec + "\n\trestartsec=" + restartsec + "\n\tdeploysec=" + deploysec + "\n\trestartCnt=" + restartCnt);
            }

            if (group == null) {
               group = new DLStoreConfig(this.context, name, policy, distribution, restartInPlace, bootsec, failsec, restartCnt, restartsec, deploysec, failOverLimit);
               this.clusterState.registerConfig(name, group);
            } else if (this.logger.isDebugEnabled()) {
               this.logger.debug("Using Existing Group: " + group);
            }

            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Done Registering: " + group);
            }
         } finally {
            this.writelock.unlock();
         }

         configNames.add(name);
      }

      return configNames;
   }

   private static boolean isValidFailOverLimit(int failOverLimit) {
      return failOverLimit > -2;
   }

   private void initializeRuntime() {
      this.balancer = new DLRulesLayout(this.context, this.clusterState, this.maxServicePerNode);
      this.context.setActive(true);
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return "DLDynamicPlacement(" + this.name + ")";
   }

   public Map adjustServices(Map currentServiceStatus, ClusterMBean cluster, ServerMBean currentServer, Map runningServers) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("adjustServices called:" + this + "with " + runningServers.size() + " running server  and " + currentServiceStatus.size() + " services - clusterSize is " + cluster.getServers().length);
      }

      try {
         if (this.currentActionPlan != null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("adjustServices called unexpetedly, actionPlan is not null");
            }

            this.setDirty(true);
         }

         this.writelock.lock();

         try {
            if (!this.init) {
               this.context.setActive(true);
               int proposedClusterSize = cluster.getServers().length;
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Activating DLB: " + this.context);
               }

               this.initializeRuntime();
               this.clusterState.initializeRunningServers(runningServers, proposedClusterSize);
               if (this.context.isTakeover()) {
                  this.clusterState.overrideStartTime(this.context.getCreateTime());
               }

               this.init = true;
               this.setDirty(true);
            } else if (this.clusterState.changeRunningServers(runningServers)) {
               if (this.logger.isDebugFineEnabled()) {
                  this.logger.fine("changeRunningServers: Servers Changed");
               }

               this.setDirty(true);
            } else if (this.logger.isDebugFineEnabled()) {
               this.logger.fine("changeRunningServers: Servers DID NOT Changed");
            }

            if (this.clusterState.changeServiceStatus(currentServiceStatus)) {
               if (this.logger.isDebugFineEnabled()) {
                  this.logger.fine("changeServiceStatus: Services STATUS Changes");
               }

               this.setDirty(true);
            } else if (this.logger.isDebugFineEnabled()) {
               this.logger.fine("changeServiceStatus: Services STATUS DID NOT Changes");
            }

            if (this.clusterState.hasTimedOut()) {
               if (this.logger.isDebugFineEnabled()) {
                  this.logger.fine("received Timeout since last call: TIMEOUT !!!");
               }

               this.setDirty(true);
            } else if (this.logger.isDebugFineEnabled()) {
               this.logger.fine("received NO TIMEOUT !!!");
            }
         } finally {
            this.writelock.unlock();
         }

         this.currentActionPlan = new ActionPlan();
         if (!this.isDirty()) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("AdjustServices returning empty action plan");
            }

            return null;
         } else {
            this.setDirty(false);
            Map proposal = null;
            this.readlock.lock();

            try {
               proposal = this.balancer.balance(this.clusterState);
            } finally {
               this.readlock.unlock();
            }

            if (this.logger.isDebugFineEnabled()) {
               this.logger.fine("AdjustServices creating valid action plan");
            }

            return this.currentActionPlan.createActionPlan(proposal, currentServiceStatus);
         }
      } catch (RuntimeException var16) {
         this.setDirty(true);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Unexpected excepion in AdjustServices", var16);
         }

         throw var16;
      }
   }

   public boolean isInit() {
      return this.init;
   }

   public Map onFailure(Map completedActions, Map actionFailures, Map status, Map runningServers) {
      try {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("onFailure has been called: " + this.currentActionPlan);
         }

         this.logger.assertCheck(this.currentActionPlan != null, "Calling onFailure incorrectly, currentActionPlan is null");
         this.setDirty(true);
         return this.currentActionPlan.adjustActionPlan(actionFailures);
      } catch (RuntimeException var6) {
         this.dirty = true;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Unexpected excepion in onFailure", var6);
         }

         throw var6;
      }
   }

   public void onSuccess() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("onSuccess has been called: " + this.currentActionPlan);
      }

      this.logger.assertCheck(this.currentActionPlan != null, "Calling OnSuccess incorrectly, currentActionPlan is null");
      Iterator var1 = this.currentActionPlan.actionplan.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         String name = (String)entry.getKey();
         if (this.clusterState.getManagedGroup(name).getStatus() != DLMigratableGroup.STATUS.FAILED) {
            this.clusterState.getManagedGroup(name).resetRestart();
         }
      }

      this.currentActionPlan.complete();
      this.currentActionPlan = null;
   }

   public static void clearSingleton() {
      defaultDLB = new DLDynamicPlacement("JMSDlb_default", (String)null, (String)null);
      loadBalancers.put("default", defaultDLB);
   }

   public void registerGMD(String name, GenericManagedDeployment base) {
      this.deployed.put(name, base);
   }

   public GenericManagedDeployment getGMD(String name) {
      return (GenericManagedDeployment)this.deployed.get(name);
   }

   public boolean isMigratedLocally(String name) {
      GenericManagedDeployment g = this.getGMD(name);
      return g == null ? false : g.isMigratedLocally();
   }

   static {
      DEFAULT_MAX_SERVICE_PER_NODE = Integer.getInteger("weblogic.management.utils.jmsdlb.maxInstance", SYSTEM_DEFAULT_MAX_SERVICE_PER_NODE);
      defaultDLB = null;
      loadBalancers = null;
      defaultDLB = new DLDynamicPlacement("JMSDlb_default", (String)null, (String)null);
      loadBalancers = new HashMap();
      loadBalancers.put("default", defaultDLB);
      if (isValidFailOverLimit(SERVER_LEVEL_LIMIT)) {
         ManagementLogger.logFailOverLimit(SERVER_LEVEL_LIMIT, "weblogic.jms.ha.FailOverLimit");
      }

   }

   class ActionPlan {
      private Map actionplan;

      public Map createActionPlan(Map proposal, Map knownState) {
         this.actionplan = new HashMap();
         Iterator var3 = proposal.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry e = (Map.Entry)var3.next();
            DLMigratableGroup m = (DLMigratableGroup)e.getKey();
            String name = m.getName();
            DLServerStatus targetServer = (DLServerStatus)e.getValue();
            DLServerStatus oldServer = m.getCurrentServer();
            DynamicLoadbalancer.ServiceStatus realStatus = (DynamicLoadbalancer.ServiceStatus)knownState.get(name);
            if (oldServer == null && targetServer == null) {
               throw new AssertionError("\tno server for " + name);
            }

            if (targetServer == null) {
               throw new AssertionError("\tno target server for " + name);
            }

            DynamicLoadbalancer.ActionItem a;
            if (oldServer == null) {
               a = new DynamicLoadbalancer.ActionItem(Action.ACTIVATE, targetServer.getName());
            } else if (oldServer.equals(targetServer)) {
               a = new DynamicLoadbalancer.ActionItem(Action.ACTIVATE, targetServer.getName());
            } else if (oldServer.getState() == DLServerStatus.State.REMOVED) {
               a = new DynamicLoadbalancer.ActionItem(Action.ACTIVATE, targetServer.getName());
            } else if (realStatus.getState() != State.ACTIVE) {
               a = new DynamicLoadbalancer.ActionItem(Action.ACTIVATE, targetServer.getName());
            } else {
               a = new DynamicLoadbalancer.ActionItem(Action.MIGRATE, targetServer.getName());
            }

            m.setPending();
            this.actionplan.put(name, a);
         }

         if (DLDynamicPlacement.this.logger.isDebugFineEnabled()) {
            DLDynamicPlacement.this.logger.fine("Action plan size: " + this.actionplan.size());
            DLDynamicPlacement.this.logger.fine("Action plan is: " + this.actionplan);
         }

         return this.actionplan;
      }

      public void complete() {
         if (DLDynamicPlacement.this.logger.isDebugEnabled()) {
            DLDynamicPlacement.this.logger.debug("ActionPlanComplete");
         }

      }

      public Map adjustActionPlan(Map actionFailures) {
         Iterator var2 = actionFailures.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry e = (Map.Entry)var2.next();
            String name = (String)e.getKey();
            DynamicLoadbalancer.CRITICAL critical = (DynamicLoadbalancer.CRITICAL)actionFailures.get(name);
            DynamicLoadbalancer.ActionItem i = (DynamicLoadbalancer.ActionItem)this.actionplan.get(name);
            if (i != null) {
               DLDynamicPlacement.this.clusterState.markFailure(i.getTargetServer(), name, critical);
            }
         }

         DLDynamicPlacement.this.setDirty(true);
         return null;
      }
   }
}
