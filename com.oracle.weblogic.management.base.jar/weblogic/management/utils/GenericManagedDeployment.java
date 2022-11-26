package weblogic.management.utils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigratableGroupConfig;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.ReadyListener;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DynamicDeploymentMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ReplicatedStoreMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.internal.ConfigLogger;
import weblogic.restart.RPDebug;
import weblogic.restart.RPException;
import weblogic.restart.RPManager;
import weblogic.restart.RPService;
import weblogic.server.GlobalServiceLocator;

public class GenericManagedDeployment {
   private static final boolean SUPPORT_ONLY_OLD_MIGRATION = System.getProperty("weblogic.management.utils.debug.oldmigration") != null;
   private static final boolean debug = System.getProperty("weblogic.management.utils.debug") != null;
   private DeploymentMBean bean;
   private GenericAdminHandler handler;
   private GenericDeploymentManager deploymentManager;
   private boolean activated;
   private boolean migrated;
   private boolean active;
   private boolean clustered;
   private boolean distributed;
   private String serverName;
   private String partitionName;
   private String clusterName;
   private String preferredServerName;
   private String distributionPolicy;
   private String migrationPolicy;
   private String name;
   private MigratableGroupConfig mgc;
   private volatile NewStatus newStatus;
   private int skew;
   private RPServiceImpl rpService;
   private static Map lockmap = new HashMap();
   private final Lock saflock;
   private Migratable migratableListener;
   public static final int STORE_TYPE = 1;
   public static final int JMS_SERVER_TYPE = 2;
   public static final int SAF_AGENT_TYPE = 3;
   public static final int PATH_SERVICE_TYPE = 4;
   public static final int JMS_MODULE_TYPE = 5;
   private static final String STORE_HEALTH_NAME_PREFIX = "PersistentStore.";
   private static final String RPPropertyName = "RestartInPlace";

   GenericManagedDeployment(GenericDeploymentManager gdm, String instanceName, String partitionName, String serverName, String clusterName, String distributionPolicy, String migrationPolicy, MigratableGroupConfig mgc, NewStatus newStatus) throws DeploymentException {
      this.newStatus = GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_NEW;
      this.skew = 0;
      this.migratableListener = new GMDMigratable();
      this.deploymentManager = gdm;
      this.mgc = mgc;
      this.bean = gdm.getBean();
      this.name = instanceName;
      this.serverName = serverName;
      this.partitionName = partitionName;
      this.clusterName = clusterName;
      this.distributionPolicy = distributionPolicy;
      this.migrationPolicy = migrationPolicy;
      this.newStatus = newStatus;
      if (SUPPORT_ONLY_OLD_MIGRATION) {
         if (debug) {
            this.logDebug("Defaulting to support only old migration");
         }

         this.mgc = null;
      }

      if (clusterName != null) {
         this.clustered = true;
      }

      if (distributionPolicy.equalsIgnoreCase("Distributed")) {
         this.distributed = true;
         this.preferredServerName = serverName;
      }

      this.process();
      if (this.bean instanceof SAFAgentMBean) {
         synchronized(lockmap) {
            if (lockmap.get(partitionName) == null) {
               lockmap.put(partitionName, new ReentrantLock());
            }

            this.saflock = (Lock)lockmap.get(partitionName);
         }
      } else {
         this.saflock = null;
      }

   }

   GenericManagedDeployment(GenericDeploymentManager gdm, String instanceName, int instanceNumber, String partitionName, String serverName, String clusterName, String distributionPolicy, String migrationPolicy, MigratableGroupConfig mgc, NewStatus newStatus) throws DeploymentException {
      this(gdm, instanceName, partitionName, String.format("%02d", instanceNumber), clusterName, distributionPolicy, migrationPolicy, mgc, newStatus);
   }

   public NewStatus getNewStatus() {
      return this.newStatus;
   }

   public void setOld() {
      this.newStatus = GenericManagedDeployment.NewStatus.OLD;
   }

   public void setAssumedOld() {
      this.newStatus = GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD;
   }

   public boolean isOldOrAssumedOld() {
      return this.newStatus == GenericManagedDeployment.NewStatus.OLD || this.newStatus == GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD;
   }

   public boolean isNewOrAssumedNew() {
      return this.newStatus == GenericManagedDeployment.NewStatus.NEW || this.newStatus == GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_NEW;
   }

   private void process() throws DeploymentException {
      if (this.bean instanceof PersistentStoreMBean) {
         this.skew -= 100;
      }

      if (this.bean instanceof JMSServerMBean || this.bean instanceof SAFAgentMBean || this.bean instanceof PathServiceMBean || this.bean instanceof MessagingBridgeMBean) {
         this.skew += 100;
      }

      try {
         this.handler = (GenericAdminHandler)this.deploymentManager.getHandlerClass().newInstance();
         this.migrated = true;
      } catch (Exception var2) {
         throw new DeploymentException("Can't instantiate handler class: " + var2, var2);
      }

      this.logDebug("Constructed");
   }

   void start() throws DeploymentException {
      if (this.activated && this.migrated && !this.active) {
         this.logDebug("Calling activate inside start");
         this.handler.activate(this);
         this.active = true;
      }

   }

   void stop() throws UndeploymentException {
      if (this.active) {
         this.logDebug("Calling deactivate inside stop");

         try {
            this.handler.deactivate(this);
         } catch (UndeploymentException var2) {
            if (!(var2.getCause() instanceof ManagedServiceShutdownException) && !(var2.getCause() instanceof ManagedDeploymentNotPreparedException)) {
               throw var2;
            }

            this.logDebug("GenericManagedDeployment.stop(): deactivate handler [" + this.handler + "] exception: " + var2);
         }

         this.active = false;
      }

   }

   void prepare(DeploymentMBean bean) throws DeploymentException {
      boolean migrationHandled = false;
      this.bean = bean;
      if (this.deploymentManager.isHandleMigration()) {
         TargetMBean[] targets = bean.getTargets();

         for(int inc = 0; targets != null && inc < targets.length; ++inc) {
            if (targets[inc] instanceof MigratableTargetMBean) {
               this.registerMigratableTarget((MigratableTargetMBean)targets[inc]);
               migrationHandled = true;
               this.mgc = null;
            }
         }

         if (this.mgc != null && this.isClustered()) {
            this.registerNewMigratableTarget();
            migrationHandled = true;
         }
      }

      if (!migrationHandled) {
         this.handler.prepare(this);
      }

   }

   void activate(DeploymentMBean bean) throws DeploymentException {
      this.logDebug("Calling activate: activated=" + this.activated + " active=" + this.active + " migrated=" + this.migrated + " started=" + this.deploymentManager.isStarted());
      this.bean = bean;
      this.activated = true;
      if (this.deploymentManager.isStarted() && this.migrated && !this.active) {
         this.logDebug("Calling activate");
         this.handler.activate(this);
         this.active = true;
      }

   }

   void deactivate(DeploymentMBean bean) throws UndeploymentException {
      this.logDebug("Calling deactivate: activated=" + this.activated + " active=" + this.active + " migrated=" + this.migrated + " started=" + this.deploymentManager.isStarted());
      this.bean = bean;
      this.activated = false;
      if (this.active) {
         if (debug) {
            this.logDebug("Calling deactivate");
         }

         this.handler.deactivate(this);
         this.active = false;
      }

   }

   void unprepare(DeploymentMBean bean) throws UndeploymentException {
      this.bean = bean;
      if (this.deploymentManager.isHandleMigration()) {
         if (this.mgc != null && this.isClustered()) {
            this.logDebug("This is a new migratableTarget, we only unregister if it is removed");
            this.unregisterNewMigratableTarget();
         } else {
            TargetMBean[] targets = bean.getTargets();

            for(int inc = 0; targets != null && inc < targets.length; ++inc) {
               if (targets[inc] instanceof MigratableTargetMBean) {
                  this.logDebug("Calling unregisterMigratableTarget");
                  this.unregisterMigratableTarget((MigratableTargetMBean)targets[inc]);
               }
            }
         }
      }

      if (this.migrated) {
         this.logDebug("Calling unprepare");
         this.handler.unprepare(this);
      }

      if (this.rpService != null) {
         try {
            String rpGroupName = this.rpService.getStoreName();
            RPManager rpManager = getRPManager();
            rpManager.removeServiceFromGroup(rpGroupName, this.rpService);
            if (bean instanceof PersistentStoreMBean) {
               rpManager.removeRPGroup(rpGroupName);
            }
         } catch (RPException var4) {
            throw var4;
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public DeploymentMBean getMBean() {
      return this.bean;
   }

   public String getMBeanName() {
      return this.bean.getName();
   }

   public String getInstanceName() {
      return this.serverName;
   }

   public int getInstanceNumber() {
      return !this.distributed ? Integer.parseInt(this.serverName) : -1;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getPreferredServerName() {
      return this.preferredServerName;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getDistributionPolicy() {
      return this.distributionPolicy;
   }

   public String getMigrationPolicy() {
      return this.migrationPolicy;
   }

   public boolean isDistributed() {
      return this.distributed;
   }

   public boolean isLocal() {
      boolean isLocal = true;
      if (this.mgc == null) {
         isLocal = !this.clustered || this.serverName.equals(this.deploymentManager.getMyServerName()) || this.migrationPolicy.equalsIgnoreCase("Off");
      } else if (this.clustered) {
         isLocal = false;
      }

      return isLocal;
   }

   public boolean isNewManaged() {
      if (this.isLocal()) {
         return true;
      } else {
         return this.clustered ? true : true;
      }
   }

   public boolean shouldPrepare() {
      return !this.clustered || this.clustered && this.mgc != null;
   }

   public boolean isClustered() {
      return this.clustered;
   }

   synchronized void registerMigratableTarget(MigratableTargetMBean target) throws DeploymentException {
      this.migrated = false;

      try {
         this.logDebug("Registering with MigrationManager" + this.getName() + "[" + target + "]");
         this.deploymentManager.getMigrationManager().register(this.migratableListener, target);
      } catch (MigrationException var3) {
         throw new DeploymentException(var3);
      }
   }

   void unregisterMigratableTarget(MigratableTargetMBean target) throws UndeploymentException {
      try {
         this.logDebug("Unregistering with MigrationManager");
         this.deploymentManager.getMigrationManager().unregister(this.migratableListener, target);
      } catch (MigrationException var3) {
         throw new UndeploymentException(var3);
      }
   }

   private void setMigrationStatusWithServiceShutdownException(boolean migrationInProgress) throws MigrationException, ManagedServiceShutdownException {
      if (this.handler instanceof MigratableGenericAdminHandler) {
         try {
            ((MigratableGenericAdminHandler)this.handler).setMigrationInProgress(migrationInProgress);
         } catch (IllegalStateException var3) {
            this.logDebug("GenericManagedDeployment.setMigrationStatus(" + migrationInProgress + ") got exception: " + var3 + " for " + this);
            if (var3.getCause() instanceof ManagedServiceShutdownException) {
               throw (ManagedServiceShutdownException)var3.getCause();
            } else {
               throw new MigrationException(var3);
            }
         }
      }
   }

   private void setMigrationStatusIgnoreServiceShutdownException(boolean migrationInProgress) throws MigrationException {
      try {
         this.setMigrationStatusWithServiceShutdownException(migrationInProgress);
      } catch (ManagedServiceShutdownException var3) {
      }

   }

   private void setMigrationStatus(boolean migrationInProgress) throws MigrationException {
      try {
         this.setMigrationStatusWithServiceShutdownException(migrationInProgress);
      } catch (ManagedServiceShutdownException var3) {
         throw new MigrationException(var3);
      }
   }

   public String toString() {
      return "[MBean Type:" + this.bean.getType() + ", MBean Name:" + this.bean.getName() + ", Distribution Policy:" + this.distributionPolicy + ", Migration Policy:" + this.migrationPolicy + ", Instance name:" + this.name + ", Partition name:" + this.partitionName + ", Server name:" + this.serverName + ", Cluster name:" + this.clusterName + ", migrated:" + this.migrated + ", active:" + this.active + ", activated:" + this.activated + ", skew:" + this.skew + ", Handler Class:" + this.deploymentManager.getHandlerClass() + ")]";
   }

   private void logDebug(String msg) {
      if (debug) {
         System.out.println("GenericManagedDeployment: " + this + ": " + msg);
      }

   }

   public synchronized boolean isMigratedLocally() {
      return this.migrated;
   }

   synchronized void registerNewMigratableTarget() throws DeploymentException {
      this.migrated = false;

      try {
         this.logDebug("Registering with MigrationManager(new): " + this);
         if (this.bean instanceof MessagingBridgeMBean) {
            this.deploymentManager.getMigrationManager().register((ReadyListener)null, true, this.partitionName, this.mgc, this.deploymentManager.getDLB(this.partitionName, this.clusterName), new Migratable[]{this.migratableListener});
         } else {
            this.deploymentManager.getMigrationManager().register(new JMSMigratableGroupReadyListenerImpl(), false, this.partitionName, this.mgc, this.deploymentManager.getDLB(this.partitionName, this.clusterName), new Migratable[]{this.migratableListener});
         }

      } catch (MigrationException var2) {
         throw new DeploymentException(var2);
      } catch (Throwable var3) {
         throw new DeploymentException(var3);
      }
   }

   void unregisterNewMigratableTarget() throws UndeploymentException {
      try {
         this.logDebug("Unregistering with MigrationManager(new): " + this);
         this.deploymentManager.getMigrationManager().unregister(this.partitionName, this.mgc, new Migratable[]{this.migratableListener});
      } catch (MigrationException var2) {
         throw new UndeploymentException(var2);
      }
   }

   void registerRPService(DynamicDeploymentMBean ddmbean, boolean isSingleServerTargeted) {
      if (ddmbean != null && ddmbean.getRestartInPlace() && ddmbean.getNumberOfRestartAttempts() != 0 && !(ddmbean instanceof MessagingBridgeMBean)) {
         if (ddmbean instanceof ReplicatedStoreMBean) {
            if (this.getMBean() instanceof PersistentStoreMBean && ddmbean.isSet("RestartInPlace")) {
               ConfigLogger.logInvalidReplicatedStoreRestartInPlaceConfiguration(ddmbean.getName());
            }

         } else {
            if (isSingleServerTargeted) {
               ServerMBean singleTarget = (ServerMBean)this.getMBean().getTargets()[0];
               if (singleTarget.getCluster() != null) {
                  if (this.getMBean() instanceof PersistentStoreMBean && ddmbean.isSet("RestartInPlace")) {
                     ConfigLogger.logInvalidPersistentStoreRestartInPlaceConfiguration(ddmbean.getName());
                  }

                  return;
               }
            }

            String fullStoreName;
            if (isSingleServerTargeted) {
               if (RPDebug.isDebugEnabled()) {
                  RPDebug.debug("GenericManagedDeployment: register a RPService targeted to singleServer, name=" + this.name);
               }

               fullStoreName = "PersistentStore." + ddmbean.getName();
            } else {
               if (RPDebug.isDebugEnabled()) {
                  RPDebug.debug("GenericManagedDeployment: register a RPService targeted to cluster with MP=Off, name=" + this.name);
               }

               fullStoreName = "PersistentStore." + GenericDeploymentManager.getDecoratedDistributedInstanceName((ConfigurationMBean)ddmbean, this.serverName);
            }

            RPManager rpManager = getRPManager();
            if (this.getMBean() instanceof PersistentStoreMBean) {
               rpManager.addRPGroup(fullStoreName, ddmbean.getSecondsBetweenRestarts(), ddmbean.getNumberOfRestartAttempts());
            }

            this.rpService = new RPServiceImpl();
            this.rpService.setStoreName(fullStoreName);
            int serviceType = 0;
            if (this.bean instanceof PersistentStoreMBean) {
               serviceType = 1;
            } else if (this.bean instanceof JMSServerMBean) {
               serviceType = 2;
            } else if (this.bean instanceof SAFAgentMBean) {
               serviceType = 3;
            } else if (this.bean instanceof PathServiceMBean) {
               serviceType = 4;
            }

            this.rpService.setType(serviceType);

            try {
               rpManager.addServiceToGroup(fullStoreName, this.rpService);
            } catch (RPException var7) {
               throw var7;
            }
         }
      }
   }

   private static RPManager getRPManager() {
      try {
         return (RPManager)GlobalServiceLocator.getServiceLocator().getService(RPManager.class, new Annotation[0]);
      } catch (RPException var1) {
         throw var1;
      }
   }

   private class RPServiceImpl implements RPService {
      private String storeName;
      private int type;

      private RPServiceImpl() {
      }

      public String getStoreName() {
         return this.storeName;
      }

      public void setStoreName(String storeName) {
         this.storeName = storeName;
      }

      public String getName() {
         return GenericManagedDeployment.this.name;
      }

      public int getType() {
         return this.type;
      }

      public void setType(int type) {
         this.type = type;
      }

      public void rpDeactivate(String groupName) throws RPException {
         if (GenericManagedDeployment.this.saflock != null) {
            GenericManagedDeployment.this.saflock.lock();
         }

         try {
            try {
               GenericManagedDeployment.this.handler.deactivate(GenericManagedDeployment.this);
            } catch (UndeploymentException var8) {
               if (RPDebug.isDebugEnabled()) {
                  RPDebug.debug("RPServiceGroup <" + groupName + ">, error deactiving service " + this.getName(), var8);
               }
            }

            try {
               GenericManagedDeployment.this.handler.unprepare(GenericManagedDeployment.this);
            } catch (UndeploymentException var7) {
               if (RPDebug.isDebugEnabled()) {
                  RPDebug.debug("RPServiceGroup <" + groupName + ">, error unpreparing service " + this.getName(), var7);
               }
            }
         } finally {
            if (GenericManagedDeployment.this.saflock != null) {
               GenericManagedDeployment.this.saflock.unlock();
            }

         }

      }

      public void rpActivate(String groupName) throws RPException {
         if (GenericManagedDeployment.this.saflock != null) {
            GenericManagedDeployment.this.saflock.lock();
         }

         try {
            GenericManagedDeployment.this.handler.prepare(GenericManagedDeployment.this);
            GenericManagedDeployment.this.handler.activate(GenericManagedDeployment.this);
         } catch (Exception var6) {
            throw new RPException(var6);
         } finally {
            if (GenericManagedDeployment.this.saflock != null) {
               GenericManagedDeployment.this.saflock.unlock();
            }

         }

      }

      public int getOrder() {
         return GenericManagedDeployment.this.skew;
      }

      // $FF: synthetic method
      RPServiceImpl(Object x1) {
         this();
      }
   }

   class JMSMigratableGroupReadyListenerImpl implements ReadyListener {
      boolean isReady = false;

      public boolean isReady() {
         if (!this.isReady) {
            this.isReady = !this.isReady;
            return !this.isReady;
         } else {
            return this.isReady;
         }
      }
   }

   public class GMDMigratable implements Migratable {
      public String getName() {
         return GenericManagedDeployment.this.getName();
      }

      public void migratableInitialize() {
      }

      public void migratableActivate() throws MigrationException {
         synchronized(GenericManagedDeployment.this) {
            GenericManagedDeployment.this.logDebug("Got migratableActivate:" + this.getName());
            GenericManagedDeployment.this.migrated = true;
            if (GenericManagedDeployment.this.saflock != null) {
               GenericManagedDeployment.this.saflock.lock();
            }

            try {
               GenericManagedDeployment.this.logDebug("Calling Migratable prepare");
               GenericManagedDeployment.this.setMigrationStatus(true);

               try {
                  GenericManagedDeployment.this.handler.prepare(GenericManagedDeployment.this);
               } catch (DeploymentException var13) {
                  throw new MigrationException(var13);
               }

               if (GenericManagedDeployment.this.activated && GenericManagedDeployment.this.deploymentManager.isStarted() && !GenericManagedDeployment.this.active) {
                  GenericManagedDeployment.this.logDebug("Calling Migratable activate (handler=" + GenericManagedDeployment.this.handler + ")");

                  try {
                     GenericManagedDeployment.this.handler.activate(GenericManagedDeployment.this);
                     GenericManagedDeployment.this.active = true;
                  } catch (DeploymentException var12) {
                     try {
                        GenericManagedDeployment.this.handler.unprepare(GenericManagedDeployment.this);
                     } catch (UndeploymentException var11) {
                        throw new MigrationException(var11);
                     }

                     throw new MigrationException(var12);
                  }
               }
            } finally {
               if (GenericManagedDeployment.this.saflock != null) {
                  GenericManagedDeployment.this.saflock.unlock();
               }

               GenericManagedDeployment.this.setMigrationStatus(false);
            }

         }
      }

      public void migratableDeactivate() throws MigrationException {
         synchronized(GenericManagedDeployment.this) {
            GenericManagedDeployment.this.logDebug("Got migratableDeactivate");
            GenericManagedDeployment.this.migrated = false;
            if (GenericManagedDeployment.this.saflock != null) {
               GenericManagedDeployment.this.saflock.lock();
            }

            try {
               try {
                  try {
                     GenericManagedDeployment.this.setMigrationStatusWithServiceShutdownException(true);
                  } catch (ManagedServiceShutdownException var9) {
                     return;
                  }

                  if (GenericManagedDeployment.this.active) {
                     GenericManagedDeployment.this.logDebug("Calling Migratable deactivate");
                     GenericManagedDeployment.this.handler.deactivate(GenericManagedDeployment.this);
                     GenericManagedDeployment.this.active = false;
                  }

                  GenericManagedDeployment.this.logDebug("Calling Migratable unprepare");
                  GenericManagedDeployment.this.handler.unprepare(GenericManagedDeployment.this);
               } catch (UndeploymentException var10) {
                  if (!(var10.getCause() instanceof ManagedServiceShutdownException) && !(var10.getCause() instanceof ManagedDeploymentNotPreparedException)) {
                     throw new MigrationException(var10);
                  }
               }

            } finally {
               if (GenericManagedDeployment.this.saflock != null) {
                  GenericManagedDeployment.this.saflock.unlock();
               }

               GenericManagedDeployment.this.setMigrationStatusIgnoreServiceShutdownException(false);
            }
         }
      }

      public int getOrder() {
         return -1900 + GenericManagedDeployment.this.skew;
      }

      public DeploymentMBean getMBean() {
         return GenericManagedDeployment.this.getMBean();
      }
   }

   public static enum NewStatus {
      NEW,
      OLD,
      UNKNOWN_ASSUME_NEW,
      UNKNOWN_ASSUME_OLD;
   }
}
