package weblogic.management.utils;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.MigratableGroupConfig;
import weblogic.cluster.migration.MigrationManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DynamicDeploymentMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.jmsdlb.DLClusterRegistration;
import weblogic.management.utils.jmsdlb.DLDynamicPlacement;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.store.admin.util.StoreExistenceChecker;
import weblogic.store.admin.util.StoreQueryParam;
import weblogic.store.admin.util.StoreQueryResult;
import weblogic.store.admin.util.StoreQueryStatus;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.collections.ConcurrentHashMap;

public class GenericDeploymentManager extends GenericDeploymentConstants {
   private DeploymentMBean mbean;
   private GenericManagedService gms;
   private ConcurrentHashMap instances = new ConcurrentHashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DLDynamicPlacement dlb = null;
   private static final boolean ALLOW_SINGLETON_OFF = System.getProperty("weblogic.management.utils.debug.singleoff") != null;
   private static final boolean debug = System.getProperty("weblogic.management.utils.debug") != null;
   ArrayList knownStoreQueryResults = new ArrayList();
   private static Timer timer = new Timer("GENERIC");
   Set knownServers = new HashSet();
   Monitor monitor = null;

   public GenericDeploymentManager(GenericManagedService gms, DeploymentMBean mbean) {
      this.mbean = mbean;
      this.gms = gms;
      String clusterName = this.getMyClusterName(mbean);
      String serverName = this.getMyServerName();
      String partitionName = getMyPartitionName();
      DynamicDeploymentMBean ddmbean = getAssociatedStoreMBean(mbean);
      this.logDebug("ddmbean=" + ddmbean);
      if (!isClusterTargeted(mbean)) {
         GenericManagedDeployment instance = this.createNamedInstance(this.getInstanceName(mbean, serverName), partitionName, (String)null, clusterName, "Distributed", "Off", (MigratableGroupConfig)null, GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD);
         if (isSingleServerTargeted(mbean)) {
            instance.registerRPService(ddmbean, true);
         }

         if (this.instances.get(instance.getName()) == null) {
            this.instances.put(instance.getName(), instance);
         }

         this.logDebug("Constructed non clustered instances");
      } else {
         boolean clusterDLB = ddmbean != null && !ddmbean.getMigrationPolicy().equalsIgnoreCase("Off") && this.isHandleMigration();
         if (!clusterDLB) {
            GenericManagedDeployment instance = this.createNamedInstance(this.getInstanceName(mbean, serverName), partitionName, serverName, clusterName, "Distributed", "Off", (MigratableGroupConfig)null, GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD);
            if (this.instances.get(instance.getName()) == null) {
               this.instances.put(instance.getName(), instance);
            }

            this.logDebug("Constructed clustered non-migratable instances");
            instance.registerRPService(ddmbean, false);
         } else {
            String distributionPolicy = ddmbean.getDistributionPolicy();
            String migrationPolicy = ddmbean.getMigrationPolicy();
            if (clusterDLB) {
               this.registerDeploymentBean(partitionName, clusterName, ddmbean);
            }

            ServerMBean currentServer = ManagementService.getRuntimeAccess(kernelId).getServer();
            ClusterMBean currentCluster = currentServer.getCluster();
            ServerMBean[] servers = currentCluster.getServers();
            ServerMBean defaultServer = currentCluster.getServers()[0];
            if (ddmbean instanceof PersistentStoreMBean) {
               this.knownStoreQueryResults = (ArrayList)this.getStoreInventory((PersistentStoreMBean)ddmbean, servers);
            }

            if (debug) {
               this.dumpStoreQueryResults(this.knownStoreQueryResults);
            }

            if (distributionPolicy.equalsIgnoreCase("Distributed")) {
               this.monitor = new Monitor(ddmbean, partitionName, clusterName);
               timer.schedule(this.monitor, 10000L, 10000L);
               ServerMBean[] var24 = servers;
               int var25 = servers.length;

               for(int var26 = 0; var26 < var25; ++var26) {
                  ServerMBean server = var24[var26];
                  serverName = server.getName();
                  this.knownServers.add(serverName);
                  this.logDebug("Creating distributed instance on server " + serverName);
                  MigratableGroupConfig mgc = null;
                  if (clusterDLB) {
                     mgc = this.createGetGroupConfig(partitionName, clusterName, ddmbean.getName(), server, -1);
                  }

                  GenericManagedDeployment.NewStatus newStatus = this.getNewStatus(ddmbean, server.getName(), this.knownStoreQueryResults);
                  GenericManagedDeployment instance = this.createNamedInstance(this.getInstanceName(mbean, server.getName()), partitionName, serverName, clusterName, distributionPolicy, migrationPolicy, mgc, newStatus);
                  if (this.instances.get(instance.getName()) == null) {
                     this.instances.put(instance.getName(), instance);
                     DLClusterRegistration dlb = this.getClusterRegistrar(partitionName, clusterName);
                     if (dlb != null) {
                        dlb.registerGMD(mgc.getName(), instance);
                     }
                  }
               }

            } else {
               this.logDebug("Creating singleton instance on server " + defaultServer.getName());
               MigratableGroupConfig mgc = null;
               if (clusterDLB) {
                  mgc = this.createGetGroupConfig(partitionName, clusterName, ddmbean.getName(), defaultServer, 1);
               }

               GenericManagedDeployment.NewStatus newStatus = this.getNewStatus(ddmbean, serverName, this.knownStoreQueryResults);
               GenericManagedDeployment instance = this.createNumberedInstance(this.getInstanceName(mbean, serverName), 1, partitionName, serverName, clusterName, distributionPolicy, migrationPolicy, mgc, newStatus);
               this.instances.put(instance.getName(), instance);
               DLClusterRegistration dlb = this.getClusterRegistrar(partitionName, clusterName);
               if (dlb != null) {
                  dlb.registerGMD(mgc.getName(), instance);
               }

               this.logDebug("Constructed clustered instances");
            }
         }
      }
   }

   public static boolean isClusterTargeted(DeploymentMBean mbean) {
      TargetMBean[] targets = mbean.getTargets();
      if (targets.length == 1 && targets[0] instanceof ClusterMBean) {
         return true;
      } else {
         return mbean instanceof MessagingBridgeMBean && targets != null && targets[0] != null && targets[0] instanceof ClusterMBean;
      }
   }

   public static boolean isSingleServerTargeted(DeploymentMBean mbean) {
      TargetMBean[] targets = mbean.getTargets();
      return targets != null && targets[0] != null && targets[0] instanceof ServerMBean;
   }

   public static boolean isDistributed(DeploymentMBean mbean) {
      if (!isClusterTargeted(mbean)) {
         return false;
      } else {
         PersistentStoreMBean store = (PersistentStoreMBean)getAssociatedStoreMBean(mbean);
         return store == null || !store.getDistributionPolicy().equals("Singleton");
      }
   }

   public static boolean isMigratable(DeploymentMBean mbean) {
      if (!isClusterTargeted(mbean)) {
         TargetMBean[] targets = mbean.getTargets();
         if (targets != null && targets[0] != null && targets[0] instanceof MigratableTargetMBean) {
            return true;
         }
      } else {
         PersistentStoreMBean store = (PersistentStoreMBean)getAssociatedStoreMBean(mbean);
         if (store != null && !store.getMigrationPolicy().equals("Off")) {
            return true;
         }
      }

      return false;
   }

   private List getStoreInventory(PersistentStoreMBean mbean, ServerMBean... servers) {
      List storeQueryParams = new ArrayList();
      ServerMBean[] var4 = servers;
      int var5 = servers.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerMBean server = var4[var6];
         storeQueryParams.add(this.getStoreQueryParam(server, mbean));
      }

      StoreExistenceChecker checker = (StoreExistenceChecker)LocatorUtilities.getService(StoreExistenceChecker.class);
      return checker == null ? Collections.EMPTY_LIST : checker.storesExist(storeQueryParams);
   }

   private StoreQueryParam getStoreQueryParam(ServerMBean server, PersistentStoreMBean mbean) {
      return new StoreQueryParam(this.getInstanceName(mbean, server.getName()), isDistributed(mbean) ? server.getName() : "01", mbean);
   }

   private StoreQueryResult getStoreQueryResult(String storeInstanceName, List storeQueryResults) {
      Iterator it = storeQueryResults.iterator();

      StoreQueryResult storeQueryResult;
      do {
         if (!it.hasNext()) {
            return null;
         }

         storeQueryResult = (StoreQueryResult)it.next();
      } while(!storeQueryResult.getDeploymentName().equals(storeInstanceName));

      return storeQueryResult;
   }

   private GenericManagedDeployment.NewStatus getNewStatus(DynamicDeploymentMBean ddmbean, String serverName, List storeQueryResults) {
      GenericManagedDeployment.NewStatus newStatus;
      if (ddmbean instanceof MessagingBridgeMBean) {
         newStatus = GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD;
      } else {
         String instanceName = this.getInstanceName(ddmbean, serverName);
         StoreQueryResult storeQueryResult = this.getStoreQueryResult(instanceName, storeQueryResults);
         if (storeQueryResult == null) {
            newStatus = GenericManagedDeployment.NewStatus.NEW;
         } else if (storeQueryResult.getQueryStatus() == StoreQueryStatus.NOTFOUND) {
            newStatus = GenericManagedDeployment.NewStatus.NEW;
         } else if (storeQueryResult.getQueryStatus() == StoreQueryStatus.FAILURE) {
            newStatus = GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_NEW;
            if (debug) {
               String errorMessage = new String("Existence check failed for Store Instance:" + instanceName + (storeQueryResult.getThrowable() != null ? ", Reason:" + storeQueryResult.getThrowable().getMessage() : ""));
               this.logDebug(errorMessage);
            }
         } else {
            newStatus = GenericManagedDeployment.NewStatus.OLD;
         }
      }

      return newStatus;
   }

   public static String getDeploymentInstanceName(DeploymentMBean mbean, String serverName) {
      if (!isClusterTargeted(mbean)) {
         return mbean.getName();
      } else {
         DynamicDeploymentMBean ddmbean = getAssociatedStoreMBean(mbean);
         if (ddmbean != null) {
            return ddmbean.getDistributionPolicy().equalsIgnoreCase("Distributed") ? getDecoratedDistributedInstanceName((ConfigurationMBean)mbean, serverName) : getDecoratedSingletonInstanceName((ConfigurationMBean)mbean, "01");
         } else {
            return getDecoratedDistributedInstanceName((ConfigurationMBean)mbean, serverName);
         }
      }
   }

   public String getInstanceName(DeploymentMBean mbean, String serverName) {
      return getDeploymentInstanceName(mbean, serverName);
   }

   private void dumpStoreQueryResults(List storeQueryResults) {
      Iterator it = storeQueryResults.iterator();

      while(it.hasNext()) {
         StoreQueryResult storeQueryResult = (StoreQueryResult)it.next();
         this.logDebug("Store MBean =" + storeQueryResult.getStoreBean());
         this.logDebug("Deployment Instance Name =" + storeQueryResult.getDeploymentName());
         this.logDebug("Server Instance Name =" + storeQueryResult.getServerInstanceName());
         this.logDebug("Status =" + storeQueryResult.getQueryStatus());
      }

   }

   public static DynamicDeploymentMBean getAssociatedStoreMBean(DeploymentMBean deploymentMBean) {
      DynamicDeploymentMBean ddmbean = null;
      if (deploymentMBean instanceof JMSServerMBean) {
         ddmbean = ((JMSServerMBean)deploymentMBean).getPersistentStore();
      } else if (deploymentMBean instanceof SAFAgentMBean) {
         ddmbean = ((SAFAgentMBean)deploymentMBean).getStore();
      } else if (deploymentMBean instanceof PathServiceMBean) {
         ddmbean = ((PathServiceMBean)deploymentMBean).getPersistentStore();
      } else {
         ddmbean = (DynamicDeploymentMBean)deploymentMBean;
      }

      return (DynamicDeploymentMBean)ddmbean;
   }

   private GenericManagedDeployment createNamedInstance(String instanceName, String partitionName, String serverName, String clusterName, String distributionPolicy, String migrationPolicy, MigratableGroupConfig mgc, GenericManagedDeployment.NewStatus newStatus) {
      GenericManagedDeployment instance = null;

      try {
         instance = new GenericManagedDeployment(this, instanceName, partitionName, serverName, clusterName, distributionPolicy, migrationPolicy, mgc, newStatus);
         this.logDebug("Created" + (clusterName != null ? " clustered " : " ") + "instance: name=" + instance.getName() + ", instance=" + instance + ", newStatus=" + newStatus);
      } catch (DeploymentException var11) {
         this.logDebug("Exception while creating the clustered managed deployment for mbean=" + this.mbean.getName() + ":" + var11.getLocalizedMessage());
      }

      return instance;
   }

   private GenericManagedDeployment createNumberedInstance(String instanceName, int instanceNumber, String partitionName, String serverName, String clusterName, String distributionPolicy, String migrationPolicy, MigratableGroupConfig mgc, GenericManagedDeployment.NewStatus newStatus) {
      GenericManagedDeployment instance = null;

      try {
         instance = new GenericManagedDeployment(this, instanceName, instanceNumber, partitionName, serverName, clusterName, distributionPolicy, migrationPolicy, mgc, newStatus);
         this.logDebug("Created non-distributed instance: name=" + instance.getName() + ", instance=" + instance);
      } catch (DeploymentException var12) {
         this.logDebug("Exception while creating the non-distributed managed deployment for mbean=" + this.mbean.getName() + ":" + var12.getLocalizedMessage());
      }

      return instance;
   }

   public DeploymentMBean getBean() {
      return this.mbean;
   }

   public GenericManagedService getGenericManagedService() {
      return this.gms;
   }

   public boolean isStarted() {
      return this.gms.isStarted();
   }

   public boolean isHandleMigration() {
      return this.gms.isHandleMigration();
   }

   public MigrationManager getMigrationManager() {
      return this.gms.getMigrationManager();
   }

   public Class getHandlerClass() {
      return this.gms.getHandlerClass();
   }

   public Class getMBeanClass() {
      return this.gms.getMBeanClass();
   }

   public void start() throws ServiceFailureException {
      DeploymentException lastException = null;
      if (!this.instances.isEmpty()) {
         Iterator it = this.instances.keySet().iterator();

         while(it.hasNext()) {
            GenericManagedDeployment instance = (GenericManagedDeployment)this.instances.get((String)it.next());
            if (instance == null) {
               return;
            }

            synchronized(instance) {
               try {
                  if (instance.isLocal()) {
                     this.logDebug("Starting Local instance: " + instance);
                     instance.start();
                  } else if (instance.isNewManaged()) {
                     this.logDebug("Starting New Clustered instance: " + instance);
                     instance.start();
                  } else {
                     this.logDebug("Skip Starting Non Local instance: " + instance);
                  }
               } catch (DeploymentException var7) {
                  lastException = var7;
               }
            }

            if (lastException != null) {
               throw new ServiceFailureException(lastException);
            }
         }
      }

   }

   public void stop() throws ServiceFailureException {
      UndeploymentException lastException = null;
      if (!this.instances.isEmpty()) {
         Iterator it = this.instances.keySet().iterator();

         while(it.hasNext()) {
            GenericManagedDeployment instance = (GenericManagedDeployment)this.instances.get((String)it.next());
            if (instance == null) {
               return;
            }

            synchronized(instance) {
               try {
                  if (instance.isLocal()) {
                     this.logDebug("Stopping Local instance: " + instance);
                     instance.stop();
                  } else if (instance.isNewManaged()) {
                     this.logDebug("Stopping New Clustered instance: " + instance);
                     instance.stop();
                  } else {
                     this.logDebug("Skip Stopping Non Local instance : " + instance);
                  }
               } catch (UndeploymentException var7) {
                  lastException = var7;
               }
            }

            if (lastException != null) {
               throw new ServiceFailureException(lastException);
            }
         }
      }

   }

   public void prepare(DeploymentMBean bean) throws DeploymentException {
      if (!this.instances.isEmpty()) {
         Iterator it = this.instances.keySet().iterator();

         while(it.hasNext()) {
            GenericManagedDeployment instance = (GenericManagedDeployment)this.instances.get((String)it.next());
            if (instance == null) {
               return;
            }

            synchronized(instance) {
               if (instance.isLocal()) {
                  this.logDebug("Preparing Local instance: " + instance);
                  instance.prepare(bean);
               } else if (!instance.shouldPrepare() && !instance.isNewManaged()) {
                  this.logDebug("Skip Preparing Non Local instance: " + instance);
               } else {
                  this.logDebug("Preparing Clustered instance: " + instance);
                  instance.prepare(bean);
               }
            }
         }
      }

   }

   public void activate(DeploymentMBean bean) throws DeploymentException {
      if (!this.instances.isEmpty()) {
         Iterator it = this.instances.keySet().iterator();

         while(it.hasNext()) {
            GenericManagedDeployment instance = (GenericManagedDeployment)this.instances.get((String)it.next());
            if (instance == null) {
               return;
            }

            synchronized(instance) {
               if (instance.isLocal()) {
                  this.logDebug("Activating Local instance: " + instance);
                  instance.activate(bean);
               } else if (instance.isNewManaged()) {
                  this.logDebug("Activating New Clustered instance: " + instance);
                  instance.activate(bean);
               } else {
                  this.logDebug("Skip activating Non Local instance: " + instance);
               }
            }
         }
      }

   }

   public void deactivate(DeploymentMBean bean) throws UndeploymentException {
      if (!this.instances.isEmpty()) {
         Iterator it = this.instances.keySet().iterator();

         while(it.hasNext()) {
            GenericManagedDeployment instance = (GenericManagedDeployment)this.instances.get((String)it.next());
            if (instance == null) {
               return;
            }

            synchronized(instance) {
               if (instance.isLocal()) {
                  this.logDebug("Deactivating Local instance: " + instance);
                  instance.deactivate(bean);
               } else if (instance.isNewManaged()) {
                  this.logDebug("Dectivating New Clustered instance: " + instance);
                  instance.deactivate(bean);
               } else {
                  this.logDebug("Skip Deactivating Non Local instance: " + instance);
               }
            }
         }
      }

   }

   public void unprepare(DeploymentMBean bean) throws UndeploymentException {
      if (!this.instances.isEmpty()) {
         Iterator it = this.instances.keySet().iterator();

         while(it.hasNext()) {
            GenericManagedDeployment instance = (GenericManagedDeployment)this.instances.get((String)it.next());
            if (instance == null) {
               return;
            }

            synchronized(instance) {
               if (instance.isLocal()) {
                  this.logDebug("Un-preparing Local instance: " + instance);
                  instance.unprepare(bean);
               } else if (instance.isNewManaged()) {
                  this.logDebug("Un-preparing New Clustered instance: " + instance);
                  instance.unprepare(bean);
               } else {
                  this.logDebug("Skip Un-preparing Non Local instance: " + instance);
               }
            }
         }
      }

   }

   public String getMyServerName() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }

   private String getMyClusterName(DeploymentMBean mbean) {
      String clusterName = null;
      TargetMBean[] targets = mbean.getTargets();
      if (targets != null && targets[0] != null && targets[0] instanceof ClusterMBean) {
         clusterName = targets[0].getName();
      }

      return clusterName;
   }

   public static String getMyPartitionName() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return cic == null ? "DOMAIN" : cic.getPartitionName();
   }

   public static String getUndecoratedConfigBeanName(ConfigurationMBean bean) {
      String partitionName = getMyPartitionName();
      String beanName = bean.getName();
      if (partitionName != null && !partitionName.isEmpty() && !partitionName.equalsIgnoreCase("DOMAIN") && beanName.endsWith("$" + partitionName)) {
         int index = beanName.lastIndexOf("$");
         beanName = beanName.substring(0, index);
      }

      return beanName;
   }

   public static String getDecoratedDistributedInstanceName(ConfigurationMBean bean, String serverName) {
      String instanceName = getUndecoratedConfigBeanName(bean);
      return getDecoratedDistributedInstanceName(instanceName, serverName, true);
   }

   public static String getDecoratedSingletonInstanceName(ConfigurationMBean bean, String instanceNumber) {
      String instanceName = getUndecoratedConfigBeanName(bean);
      return getDecoratedSingletonInstanceName(instanceName, instanceNumber, true);
   }

   public static String getDecoratedDistributedInstanceName(ConfigurationMBean bean, String serverName, boolean appendPartitionName) {
      String instanceName = getUndecoratedConfigBeanName(bean);
      return getDecoratedDistributedInstanceName(instanceName, serverName, appendPartitionName);
   }

   public static String getDecoratedSingletonInstanceName(ConfigurationMBean bean, String instanceNumber, boolean appendPartitionName) {
      String instanceName = getUndecoratedConfigBeanName(bean);
      return getDecoratedSingletonInstanceName(instanceName, instanceNumber, appendPartitionName);
   }

   public static String getDecoratedDistributedInstanceName(String name, String serverName) {
      return getDecoratedDistributedInstanceName(name, serverName, true);
   }

   public static String getDecoratedSingletonInstanceName(String name, String instanceNumber) {
      return getDecoratedSingletonInstanceName(name, instanceNumber, true);
   }

   public static String getDecoratedDistributedInstanceName(String name, String serverName, boolean appendPartitionName) {
      String instanceName = name;
      if (serverName != null && !serverName.isEmpty()) {
         instanceName = name + "@" + serverName;
      }

      if (appendPartitionName) {
         String partitionName = getMyPartitionName();
         if (partitionName != null && !partitionName.isEmpty() && !partitionName.equalsIgnoreCase("DOMAIN")) {
            instanceName = instanceName + "$" + partitionName;
         }
      }

      return instanceName;
   }

   public static String getDecoratedSingletonInstanceName(String name, String instanceNumber, boolean appendPartitionName) {
      String instanceName = name;
      if (instanceNumber != null && !instanceNumber.isEmpty()) {
         instanceName = name + "-" + instanceNumber;
      }

      if (appendPartitionName) {
         String partitionName = getMyPartitionName();
         if (partitionName != null && !partitionName.isEmpty() && !partitionName.equalsIgnoreCase("DOMAIN")) {
            instanceName = instanceName + "$" + partitionName;
         }
      }

      return instanceName;
   }

   private void logDebug(String msg) {
      if (debug) {
         System.out.println("GenericDeploymentManager: " + this.mbean.getName() + ": " + msg);
      }

   }

   private void registerDeploymentBean(String partitionName, String clusterName, DynamicDeploymentMBean bean) {
      if (this.dlb == null) {
         this.dlb = DLDynamicPlacement.getSingleton(partitionName, clusterName);
      }

      this.logDebug("registerDeploymentBean: " + bean);
      this.dlb.registerDeploymentBeans(bean);
   }

   public DLClusterRegistration getClusterRegistrar(String partitionName, String clusterName) {
      if (this.dlb == null) {
         this.dlb = DLDynamicPlacement.getSingleton(partitionName, clusterName);
      }

      return this.dlb;
   }

   public DynamicLoadbalancer getDLB(String partitionName, String clusterName) {
      if (this.dlb == null) {
         this.dlb = DLDynamicPlacement.getSingleton(partitionName, clusterName);
      }

      return this.dlb;
   }

   private MigratableGroupConfig createGetGroupConfig(String partitionName, String clusterName, String name, ServerMBean server, int instanceCnt) {
      return this.getClusterRegistrar(partitionName, clusterName).getGroupConfigMBean(name, server, instanceCnt == -1 ? (server == null ? null : server.getName()) : String.valueOf(instanceCnt));
   }

   public class Monitor extends TimerTask {
      String name = null;
      String partitionName;
      String clusterName;
      String migrationPolicy;
      String distributionPolicy;
      DynamicDeploymentMBean ddmbean;

      public Monitor(DynamicDeploymentMBean ddmbean, String partitionName, String clusterName) {
         this.name = ddmbean.getName();
         this.partitionName = partitionName;
         this.clusterName = clusterName;
         this.migrationPolicy = ddmbean.getMigrationPolicy();
         this.distributionPolicy = ddmbean.getDistributionPolicy();
         this.ddmbean = ddmbean;
      }

      public void run() {
         ClusterMBean cluster = ManagementService.getRuntimeAccess(GenericDeploymentManager.kernelId).getServer().getCluster();
         ServerMBean[] servers = cluster.getServers();
         ServerMBean[] var3 = servers;
         int var4 = servers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ServerMBean server = var3[var5];
            String serverName = server.getName();
            MigratableGroupConfig mgc;
            GenericManagedDeployment gmd;
            if (!GenericDeploymentManager.this.knownServers.contains(serverName)) {
               GenericDeploymentManager.this.knownServers.add(serverName);
               GenericDeploymentManager.this.logDebug("Creating NEW distributed instance on server " + serverName);
               mgc = GenericDeploymentManager.this.createGetGroupConfig(this.partitionName, this.clusterName, this.name, server, -1);
               if (this.ddmbean instanceof PersistentStoreMBean) {
                  GenericDeploymentManager.this.knownStoreQueryResults = (ArrayList)GenericDeploymentManager.this.getStoreInventory((PersistentStoreMBean)this.ddmbean, servers);
               }

               GenericManagedDeployment.NewStatus newStatusx = GenericDeploymentManager.this.getNewStatus(this.ddmbean, server.getName(), GenericDeploymentManager.this.knownStoreQueryResults);
               gmd = GenericDeploymentManager.this.createNamedInstance(GenericDeploymentManager.this.getInstanceName(GenericDeploymentManager.this.mbean, serverName), this.partitionName, serverName, this.clusterName, this.distributionPolicy, this.migrationPolicy, mgc, newStatusx);
               if (GenericDeploymentManager.this.instances.get(gmd.getName()) == null) {
                  GenericDeploymentManager.this.instances.put(gmd.getName(), gmd);
                  DLClusterRegistration dlb = GenericDeploymentManager.this.getClusterRegistrar(this.partitionName, this.clusterName);
                  if (dlb != null) {
                     dlb.registerGMD(mgc.getName(), gmd);
                  }
               }

               try {
                  synchronized(gmd) {
                     gmd.prepare(GenericDeploymentManager.this.mbean);
                     gmd.activate(GenericDeploymentManager.this.mbean);
                  }
               } catch (Throwable var14) {
               }
            } else {
               mgc = GenericDeploymentManager.this.createGetGroupConfig(this.partitionName, this.clusterName, this.name, server, -1);
               DLClusterRegistration dlbx = GenericDeploymentManager.this.getClusterRegistrar(this.partitionName, this.clusterName);
               if (dlbx != null && ((DLDynamicPlacement)dlbx).isInit()) {
                  gmd = dlbx.getGMD(mgc.getName());
                  if (!gmd.isOldOrAssumedOld()) {
                     if (this.ddmbean instanceof PersistentStoreMBean) {
                        GenericDeploymentManager.this.knownStoreQueryResults = (ArrayList)GenericDeploymentManager.this.getStoreInventory((PersistentStoreMBean)this.ddmbean, servers);
                     }

                     GenericManagedDeployment.NewStatus newStatus = GenericDeploymentManager.this.getNewStatus(this.ddmbean, server.getName(), GenericDeploymentManager.this.knownStoreQueryResults);
                     if (newStatus == GenericManagedDeployment.NewStatus.OLD) {
                        gmd.setOld();
                     }

                     if (newStatus == GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD) {
                        gmd.setAssumedOld();
                     }
                  }
               }
            }
         }

      }
   }
}
