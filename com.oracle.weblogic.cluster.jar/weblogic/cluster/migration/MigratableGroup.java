package weblogic.cluster.migration;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterService;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.cluster.singleton.LeaseLostListener;
import weblogic.cluster.singleton.LeaseObtainedListener;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingException;
import weblogic.cluster.singleton.SingletonService;
import weblogic.cluster.singleton.SingletonServicesDebugLogger;
import weblogic.cluster.singleton.SingletonServicesManagerService;
import weblogic.cluster.singleton.SingletonServicesState;
import weblogic.cluster.singleton.SingletonServicesStateManagerRemote;
import weblogic.health.HealthFeedbackCallback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicDeploymentMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.work.WorkManagerFactory;

public class MigratableGroup implements ClusterMembersChangeListener, SingletonService, LeaseLostListener, LeaseObtainedListener, HealthFeedbackCallback {
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private static final String HEALTH_JTA_SUBSYSTEM_NAME = "JTA";
   private final MigratableTargetMBean target;
   private final MigratableGroupConfig config;
   private final Map migratablesToJNDINameMap;
   private final TreeSet migratablesAsList;
   private HostID[] cachedHostList;
   private boolean isActive;
   private int migratableState;
   private final Context ctx;
   private final String partitionName;
   private String migratableGroupName;
   private final Object activationLock;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean isReady;
   private ReadyListener readyListener;
   private boolean isLocalRPTriggering;

   public MigratableGroup(MigratableTargetMBean target) {
      this(target, (MigratableGroupConfig)null);
   }

   public MigratableGroup(MigratableTargetMBean target, MigratableGroupConfig config) {
      this(target, config, (String)null);
   }

   public MigratableGroup(MigratableTargetMBean target, MigratableGroupConfig config, String partitionName) {
      this.migratablesAsList = new TreeSet(new MigratableComparator());
      this.cachedHostList = new HostID[0];
      this.activationLock = new Object();
      this.isReady = false;
      this.readyListener = null;
      this.isLocalRPTriggering = false;
      this.target = target;
      this.config = config;
      this.migratablesToJNDINameMap = Collections.synchronizedMap(new HashMap());
      this.isActive = false;
      this.migratableState = 0;
      this.partitionName = partitionName;
      this.migratableGroupName = target.getName();
      this.updateHostList();
      SingletonServicesManagerService.getInstance().addConfiguredService(this.getName(), this);

      try {
         this.ctx = new InitialContext();
      } catch (NamingException var5) {
         throw new AssertionError("Error creating initial context", var5);
      }
   }

   public MigratableTargetMBean getTarget() {
      return this.target;
   }

   boolean isActive() {
      return this.isActive;
   }

   boolean isLocalRPTriggering() {
      return this.isLocalRPTriggering;
   }

   public int getMigratableState() {
      return this.migratableState;
   }

   public String getMigratableStateString() {
      switch (this.migratableState) {
         case 0:
            return "INACTIVE";
         case 1:
            return "ACTIVE";
         case 2:
            return "ACTIVATING";
         default:
            return "UNKNOWN";
      }
   }

   void failed(String service, HealthState hs) {
      if (DEBUG) {
         p("Failed " + this.getName());
      }

      String state = hs.getState() == 3 ? "Failed" : (hs.getState() == 2 ? "Critical" : HealthState.mapToString(hs.getState()));
      ClusterExtensionLogger.logMigratableGroupFailed(service, state, hs.getReasonCodeSummary());
      if (this.target.getMigrationPolicy().equals("manual") && SingletonServicesManagerService.getInstance().isRestartInPlaceEnabled(this.getName())) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               if (!MigratableGroup.this.isJTAMigratableGroup()) {
                  MigratableGroup.this.isLocalRPTriggering = true;
               }

               if (!SingletonServicesManagerService.getInstance().restartInPlace(MigratableGroup.this.getName()) && MigratableGroup.this.target.isCritical()) {
                  HealthMonitorService.subsystemFailed("MigratableGroup: " + MigratableGroup.this.getName(), "failed to restart MigratableGroup restart in place. move to FAILED state.");
               }

               if (!MigratableGroup.this.isJTAMigratableGroup()) {
                  MigratableGroup.this.isLocalRPTriggering = false;
               }

            }
         });
      } else {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               synchronized(MigratableGroup.this.activationLock) {
                  if (MigratableGroup.this.isActive) {
                     boolean var42 = false;

                     try {
                        var42 = true;
                        MigratableGroup.this.deactivateAllMigratables();
                        var42 = false;
                     } finally {
                        if (var42) {
                           boolean postScriptExecuted = true;

                           try {
                              postScriptExecuted = MigratableGroup.this.executePostScript();
                           } finally {
                              if (!MigratableGroup.this.target.getMigrationPolicy().equals("manual")) {
                                 if (postScriptExecuted) {
                                    try {
                                       MigratableGroup.this.updateState(0, ManagementService.getRuntimeAccess(MigratableGroup.getKernelId()).getServer().getName());
                                    } catch (Exception var44) {
                                       ClusterLogger.logLeasingError(MigratableGroup.this.getName(), var44);
                                    }
                                 }

                                 try {
                                    ClusterService.getServices().getSingletonLeasingService().release(MigratableGroup.this.getName());
                                 } catch (LeasingException var43) {
                                    ClusterExtensionLogger.logReleaseLeaseError(MigratableGroup.this.getName(), var43);
                                 }
                              }

                           }
                        }
                     }

                     boolean postScriptExecutedx = true;

                     try {
                        postScriptExecutedx = MigratableGroup.this.executePostScript();
                     } finally {
                        if (!MigratableGroup.this.target.getMigrationPolicy().equals("manual")) {
                           if (postScriptExecutedx) {
                              try {
                                 MigratableGroup.this.updateState(0, ManagementService.getRuntimeAccess(MigratableGroup.getKernelId()).getServer().getName());
                              } catch (Exception var46) {
                                 ClusterLogger.logLeasingError(MigratableGroup.this.getName(), var46);
                              }
                           }

                           try {
                              ClusterService.getServices().getSingletonLeasingService().release(MigratableGroup.this.getName());
                           } catch (LeasingException var45) {
                              ClusterExtensionLogger.logReleaseLeaseError(MigratableGroup.this.getName(), var45);
                           }
                        }

                     }

                  }
               }
            }
         });
      }
   }

   public void healthStateChange(HealthState hs) {
      if (hs != null) {
         if (hs.getSubsystemName() != null) {
            if (hs.getState() == 3 || hs.getState() == 2) {
               Iterator iter;
               if (DEBUG) {
                  p("try to process healthStateChange event in migratableGroup '" + this.getName() + "'. subsystemName = " + hs.getSubsystemName());
                  iter = this.migratablesAsList.iterator();
                  List migNameList = new ArrayList();

                  while(iter.hasNext()) {
                     migNameList.add(((Migratable)iter.next()).getName());
                  }

                  p("all migratables in migratableGroup '" + this.getName() + "' are " + migNameList);
               }

               iter = this.migratablesAsList.iterator();

               while(iter.hasNext()) {
                  String service = ((Migratable)iter.next()).getName();
                  if (service != null) {
                     if (hs.getSubsystemName().equals(service)) {
                        this.failed(service, hs);
                     } else if (hs.getSubsystemName().startsWith("PersistentStore.")) {
                        String name = hs.getSubsystemName().substring("PersistentStore.".length(), hs.getSubsystemName().length());
                        if (service.equals(name)) {
                           this.failed(service, hs);
                        }
                     }
                  }
               }

            }
         }
      }
   }

   public boolean add(final Migratable migratable, String jndiName) throws MigrationException {
      synchronized(this.activationLock) {
         if (this.contains(migratable)) {
            return false;
         } else {
            try {
               if (jndiName != null) {
                  this.ctx.bind(jndiName, migratable);
               }
            } catch (NamingException var6) {
            }

            if (jndiName != null) {
               this.migratablesToJNDINameMap.put(migratable, jndiName);
            }

            if (DEBUG) {
               p("adding migratable " + migratable.getName() + " to group " + this.getName() + " Migratable class - " + migratable.getClass().getName());
            }

            boolean addFlag = this.migratablesAsList.add(migratable);
            if (this.isActive && !this.isLocalRPTriggering) {
               PartitionAwareObject.runUnderPartition(this.partitionName, new Runnable() {
                  public void run() {
                     migratable.migratableActivate();
                  }
               });
            }

            return addFlag;
         }
      }
   }

   public boolean contains(Migratable migratable) {
      return this.migratablesAsList.contains(migratable);
   }

   public boolean remove(final Migratable migratable) throws MigrationException {
      synchronized(this.activationLock) {
         if (this.isActive) {
            PartitionAwareObject.runUnderPartition(this.partitionName, new Runnable() {
               public void run() {
                  migratable.migratableDeactivate();
               }
            });
         }

         this.migratablesToJNDINameMap.remove(migratable);
         return this.migratablesAsList.remove(migratable);
      }
   }

   String clearUpJNDIMap(Migratable migratable) {
      return (String)this.migratablesToJNDINameMap.remove(migratable);
   }

   int size() {
      return this.migratablesAsList.size();
   }

   public String getName() {
      return this.migratableGroupName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void onAcquire(String leaseName) {
      Leasing leasingService = ClusterService.getServices().getSingletonLeasingService();
      leasingService.addLeaseLostListener(this);
   }

   public void onException(Exception e, String leaseName) {
   }

   public void restart() throws MigrationException {
      this.deactivate();
      this.activate();
   }

   public void activate() {
      synchronized(this.activationLock) {
         if (!this.isActive) {
            if (!this.target.getMigrationPolicy().equals("manual")) {
               Leasing leasingService = ClusterService.getServices().getSingletonLeasingService();
               if (leasingService == null) {
                  if (DEBUG) {
                     p("No lease manager, cannot start auto-migratable service.");
                  }

                  throw new MigrationException("No lease manager, cannot start auto-migratable service.");
               }

               try {
                  if (!leasingService.tryAcquire(this.getName())) {
                     if (DEBUG) {
                        p("Could not claim lease for " + this.getName() + ", someone else must have it already.");
                     }

                     if (!this.permitActivateWithoutLease(this.target)) {
                        throw new MigrationException("Could not claim lease, someone else must have it already.");
                     }

                     try {
                        leasingService.acquire(this.getName(), this);
                     } catch (LeasingException var5) {
                        throw new MigrationException(var5);
                     }
                  } else {
                     leasingService.addLeaseLostListener(this);
                  }
               } catch (LeasingException var6) {
                  ClusterLogger.logLeasingError(this.getName(), var6);
                  if (DEBUG) {
                     p("Could not claim lease due to error: " + var6);
                  }

                  throw new MigrationException("Could not claim lease due to error: " + var6, var6);
               }
            }

            try {
               if (!ScriptExecutor.runNMScript(this.target.getPreScript(), this.target)) {
                  if (DEBUG) {
                     p("couldn't run prescript");
                  }

                  throw new MigrationException("couldn't run prescript", true);
               }

               this.updateHostList();
               this.activateAllMigratables();
               HealthMonitorService.registerForCallback(this);
            } catch (MigrationException var7) {
               if (DEBUG) {
                  p("activation failed. Call releaseLease() to release an acquired lease.");
               }

               this.releaseLease();
               throw var7;
            }

         }
      }
   }

   void activateAllMigratables() {
      this.migratableState = 2;
      synchronized(this.migratablesAsList) {
         this.checkSingletonStoreComplete();
         if (!this.isReady && this.readyListener != null) {
            this.isReady = this.readyListener.isReady();
         }

         if (DEBUG) {
            p("activating " + this.migratablesAsList.size() + " migratables for " + this.getName());
         }

         final Iterator it = this.migratablesAsList.iterator();
         final GroupActivationException gae = new GroupActivationException();
         final List targetsActivated = new ArrayList();
         PartitionAwareObject.runUnderPartition(this.partitionName, new Runnable() {
            public void run() {
               while(it.hasNext()) {
                  Migratable migratable = (Migratable)it.next();
                  if (MigratableGroup.DEBUG) {
                     MigratableGroup.p("activating migratable '" + migratable.getName() + "' for " + MigratableGroup.this.getName());
                  }

                  MigratableGroup.this.activateTarget(migratable, gae);
                  if (gae.getCauses().size() > 0) {
                     MigratableGroup.this.handleFailedStateChange(targetsActivated, true);
                     throw gae;
                  }

                  targetsActivated.add(migratable);
               }

            }
         });
         this.isActive = true;
         this.migratableState = 1;
      }
   }

   private void checkSingletonStoreComplete() {
      if (this.isSingletonStore()) {
         boolean complete = true;
         String missingJMSModules = "";
         if (DEBUG) {
            p("checkSingletonStoreComplete started for " + this.getName() + " which contains " + this.migratablesAsList.size() + " migratables");
         }

         Set jmsModuleNamesInThisMG = this.getJMSModuleNamesInThisMG();
         Set expectedJMSModuleNamesInThisMG = this.getExpectedJMSModuleNamesInThisMG();
         Iterator var5 = expectedJMSModuleNamesInThisMG.iterator();

         String thisJmsModuleNameInMG;
         while(var5.hasNext()) {
            thisJmsModuleNameInMG = (String)var5.next();
            if (jmsModuleNamesInThisMG.contains(thisJmsModuleNameInMG)) {
               if (DEBUG) {
                  p("checkSingletonStoreComplete for " + this.getName() + ": expected JMS module " + thisJmsModuleNameInMG + " found in this MigratableGroup");
               }
            } else {
               missingJMSModules = (complete ? "(" : ",") + missingJMSModules + " " + thisJmsModuleNameInMG;
               complete = false;
               if (DEBUG) {
                  p("checkSingletonStoreComplete for " + this.getName() + ": expected JMS module " + thisJmsModuleNameInMG + " NOT found in this MigratableGroup");
               }
            }
         }

         if (!complete) {
            missingJMSModules = missingJMSModules + ")";
         }

         if (DEBUG) {
            var5 = jmsModuleNamesInThisMG.iterator();

            while(var5.hasNext()) {
               thisJmsModuleNameInMG = (String)var5.next();
               if (!expectedJMSModuleNamesInThisMG.contains(thisJmsModuleNameInMG)) {
                  p("MigratableGroup#checkSingletonStoreComplete for " + this.getName() + ": UNEXPECTED JMS module " + thisJmsModuleNameInMG + " found in this MigratableGroup");
               }
            }
         }

         if (complete) {
            if (DEBUG) {
               p("checkSingletonStoreComplete succeeded for " + this.getName());
            }

         } else {
            if (DEBUG) {
               p("checkSingletonStoreComplete failed for " + this.getName() + ": throwing MigratableGroupNotReadyException");
            }

            throw new MigratableGroupNotReadyException("MigratableGroup " + this.getName() + " is not complete: missing JMS modules " + missingJMSModules);
         }
      }
   }

   private boolean isSingletonStore() {
      Iterator iterator = this.migratablesAsList.iterator();

      Migratable thisMigratable;
      do {
         do {
            if (!iterator.hasNext()) {
               return false;
            }

            thisMigratable = (Migratable)iterator.next();
         } while(!(thisMigratable instanceof GenericManagedDeployment.GMDMigratable));
      } while(!(((GenericManagedDeployment.GMDMigratable)thisMigratable).getMBean() instanceof FileStoreMBean) && !(((GenericManagedDeployment.GMDMigratable)thisMigratable).getMBean() instanceof JDBCStoreMBean));

      return ((DynamicDeploymentMBean)((GenericManagedDeployment.GMDMigratable)thisMigratable).getMBean()).getDistributionPolicy().equals("Singleton");
   }

   private Set getJMSServerMBeans() {
      Set result = new HashSet();
      Iterator iterator = this.migratablesAsList.iterator();

      while(iterator.hasNext()) {
         Migratable thisMigratable = (Migratable)iterator.next();
         if (thisMigratable instanceof GenericManagedDeployment.GMDMigratable && ((GenericManagedDeployment.GMDMigratable)thisMigratable).getMBean() instanceof JMSServerMBean) {
            result.add((JMSServerMBean)((GenericManagedDeployment.GMDMigratable)thisMigratable).getMBean());
         }
      }

      return result;
   }

   private Set getJMSModuleNamesInThisMG() {
      Set jmsModuleNamesInThisMG = new HashSet();
      String asmActivateHandlerClassName = "weblogic.jms.module.ModuleCoordinator$ASMActivateHandler";
      Iterator var3 = this.migratablesAsList.iterator();

      while(var3.hasNext()) {
         Migratable thisMigratable = (Migratable)var3.next();
         if (thisMigratable.getClass().getName().equals(asmActivateHandlerClassName)) {
            String jmsModuleName = thisMigratable.getName();
            if (DEBUG) {
               p("getJMSModuleNamesInThisMG for " + this.getName() + ": MigratableGroup contains the JMS module " + jmsModuleName);
            }

            jmsModuleNamesInThisMG.add(jmsModuleName);
         }
      }

      return jmsModuleNamesInThisMG;
   }

   private Set getExpectedJMSModuleNamesInThisMG() {
      Set jmsServerMBeans = this.getJMSServerMBeans();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(getKernelId());
      DomainMBean domainMBean = runtimeAccess.getDomain();
      ClusterMBean clusterMBean = runtimeAccess.getServer().getCluster();
      Set expectedJMSModuleNames = new HashSet();
      JMSSystemResourceMBean[] allJmsSystemResources = domainMBean.getJMSSystemResources();
      JMSSystemResourceMBean[] var7 = allJmsSystemResources;
      int var8 = allJmsSystemResources.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         JMSSystemResourceMBean thisJmsSystemResourceMBean = var7[var9];
         if (this.isJMSModuleNeeded(thisJmsSystemResourceMBean, jmsServerMBeans, clusterMBean)) {
            expectedJMSModuleNames.add(thisJmsSystemResourceMBean.getName());
         }
      }

      return expectedJMSModuleNames;
   }

   private boolean isJMSModuleNeeded(JMSSystemResourceMBean thisJmsSystemResourceMBean, Set jmsServerMBeans, ClusterMBean clusterMBean) {
      JMSBean jmsBean = thisJmsSystemResourceMBean.getJMSResource();
      if (jmsBean.getQueues().length == 0 && jmsBean.getTopics().length == 0) {
         if (DEBUG) {
            p("isJMSModuleNeeded for " + this.getName() + ", JMS module " + thisJmsSystemResourceMBean.getName() + ", contains no queues or topics, so it is not required by this MigratableGroup");
         }

         return false;
      } else {
         Set possibleTargets = new HashSet();
         Iterator var6 = jmsServerMBeans.iterator();

         while(var6.hasNext()) {
            JMSServerMBean thisJMSServerMBean = (JMSServerMBean)var6.next();
            possibleTargets.add(thisJMSServerMBean.getName());
         }

         possibleTargets.add(clusterMBean.getName());
         QueueBean[] var10 = jmsBean.getQueues();
         int var12 = var10.length;

         int var8;
         for(var8 = 0; var8 < var12; ++var8) {
            QueueBean thisQueueBean = var10[var8];
            if (this.isDestinationTargetedAt(thisQueueBean, thisJmsSystemResourceMBean, possibleTargets)) {
               return true;
            }
         }

         TopicBean[] var11 = jmsBean.getTopics();
         var12 = var11.length;

         for(var8 = 0; var8 < var12; ++var8) {
            TopicBean thisTopicBean = var11[var8];
            if (this.isDestinationTargetedAt(thisTopicBean, thisJmsSystemResourceMBean, possibleTargets)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isDestinationTargetedAt(DestinationBean thisDestinationBean, JMSSystemResourceMBean thisJmsSystemResourceMBean, Set validTargets) {
      int var6;
      if (thisDestinationBean.isDefaultTargetingEnabled()) {
         TargetMBean[] var4 = thisJmsSystemResourceMBean.getTargets();
         int var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            TargetMBean thisTargetMBean = var4[var6];
            if (validTargets.contains(thisTargetMBean.getName())) {
               if (DEBUG) {
                  p("isDestinationTargetedAt for " + this.getName() + ", JMS module " + thisJmsSystemResourceMBean.getName() + ", dest " + thisDestinationBean.getName() + " has default-targeted target " + thisTargetMBean.getName() + " which makes it required by this MigratableGroup");
               }

               return true;
            }
         }
      } else {
         SubDeploymentMBean thisSubdeploymentMBean = thisJmsSystemResourceMBean.lookupSubDeployment(thisDestinationBean.getSubDeploymentName());
         if (thisSubdeploymentMBean != null) {
            TargetMBean[] var10 = thisSubdeploymentMBean.getTargets();
            var6 = var10.length;

            for(int var11 = 0; var11 < var6; ++var11) {
               TargetMBean thisSubdeploymentTargetMBean = var10[var11];
               if (validTargets.contains(thisSubdeploymentTargetMBean.getName())) {
                  if (DEBUG) {
                     p("isDestinationTargetedAt for " + this.getName() + ", JMS module " + thisJmsSystemResourceMBean.getName() + ", dest " + thisDestinationBean.getName() + " has subdeployment-targeted target " + thisSubdeploymentTargetMBean.getName() + " which makes it required by this MigratableGroup");
                  }

                  return true;
               }
            }
         }
      }

      if (DEBUG) {
         p("isDestinationTargetedAt for " + this.getName() + ", dest " + thisDestinationBean.getName() + " in JMS module " + thisJmsSystemResourceMBean.getName() + "is not targeted at any of " + validTargets + " so is not required by this MigratableGroup");
      }

      return false;
   }

   private void handleFailedStateChange(List migratableList, boolean activationFailed) {
      for(int i = migratableList.size() - 1; i >= 0; --i) {
         Migratable migratable = (Migratable)migratableList.get(i);

         try {
            if (activationFailed) {
               if (DEBUG) {
                  p("Going to call migratableDeactivate on " + migratable + " for " + this.getName());
               }

               migratable.migratableDeactivate();
            } else {
               if (DEBUG) {
                  p("Going to call migratableActivate on " + migratable + " for " + this.getName());
               }

               this.activateTarget(migratable, new GroupActivationException());
            }
         } catch (MigrationException var6) {
         }
      }

   }

   private void activateTarget(Migratable migratable, GroupActivationException gae) throws MigrationException {
      try {
         migratable.migratableActivate();
         String jndiName = (String)this.migratablesToJNDINameMap.get(migratable);
         if (jndiName != null) {
            this.ctx.rebind(jndiName, migratable);
         }
      } catch (NamingException var4) {
         throw new MigrationException("Failed to migrate", var4);
      } catch (MigrationException var5) {
         if (DEBUG) {
            p("Failed to activate " + migratable.getName() + " - " + var5.getCause());
         }

         gae.addCause(var5);
      }

   }

   void shutdown() {
      this.deactivate(true);
   }

   private void deactivateAllMigratables() {
      synchronized(this.migratablesAsList) {
         if (DEBUG) {
            p("deactivating " + this.migratablesAsList.size() + " migratables for " + this.getName());
         }

         final Migratable[] migratables = new Migratable[this.migratablesAsList.size()];
         this.migratablesAsList.toArray(migratables);
         final GroupDeactivationException gde = new GroupDeactivationException();
         PartitionAwareObject.runUnderPartition(this.partitionName, new Runnable() {
            public void run() {
               for(int i = migratables.length - 1; i >= 0; --i) {
                  Migratable migratable = migratables[i];

                  try {
                     if (MigratableGroup.DEBUG) {
                        MigratableGroup.p("Going to call migratableDeactivate on " + migratable.getName() + " for " + MigratableGroup.this.getName());
                     }

                     migratable.migratableDeactivate();
                  } catch (MigrationException var4) {
                     if (MigratableGroup.DEBUG) {
                        MigratableGroup.p("Failed to deactivate " + migratable.getName() + " - " + var4.getCause());
                     }

                     gde.addCause(var4);
                  }
               }

            }
         });
         this.isActive = false;
         this.migratableState = 0;
         if (!gde.getCauses().isEmpty()) {
            throw gde;
         }
      }
   }

   public void destroy() {
      if (DEBUG) {
         p("destroying " + this.getName());
      }

      this.deactivate();
      SingletonServicesManagerService.getInstance().remove(this.getName());
   }

   public void deactivate() {
      if (DEBUG) {
         p("deactivating " + this.getName());
      }

      this.deactivate(false);
   }

   private void deactivate(boolean shutdown) {
      synchronized(this.activationLock) {
         if (this.isActive) {
            this.updateHostList();
            HealthMonitorService.deregisterForCallback(this);

            try {
               this.deactivateAllMigratables();
            } finally {
               try {
                  if (!this.executePostScript() && !shutdown) {
                     throw new MigrationException("Execution of post deactivation script failed");
                  }
               } finally {
                  this.releaseLease();
               }

            }

         }
      }
   }

   private void releaseLease() {
      if (!this.target.getMigrationPolicy().equals("manual")) {
         try {
            ClusterService.getServices().getSingletonLeasingService().release(this.getName());
         } catch (LeasingException var2) {
            ClusterExtensionLogger.logReleaseLeaseError(this.getName(), var2);
         }

      }
   }

   public void onRelease() {
      Leasing leasingService = ClusterService.getServices().getSingletonLeasingService();
      leasingService.removeLeaseLostListener(this);
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(getKernelId()).getServer().getCluster();
      if (this.isActive && this.isLocalJTAMigratableTarget() && this.isLocalJTAAutoMigratable()) {
         HealthMonitorService.subsystemFailedForceShutdown("JTAMigratableGroup", "JTA migratable target lost a lease");
      } else {
         this.deactivate();
      }

   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof MigratableGroup && ((MigratableGroup)o).getName().equals(this.getName());
   }

   public String toString() {
      return "MigratableGroup " + this.getName() + "[" + this.getMigratableStateString() + "] with " + this.migratablesToJNDINameMap.toString();
   }

   public HostID[] getHostList() {
      this.updateHostList();
      return this.cachedHostList;
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent event) {
      this.updateHostList();
   }

   private void updateHostList() {
      try {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(getKernelId());
         MigratableTargetMBean t = runtimeAccess.getDomain().lookupMigratableTarget(this.target.getName());
         if (t == null) {
            ServerMBean server = runtimeAccess.getDomain().lookupServer(this.target.getName());
            t = server.getJTAMigratableTarget();
         }

         Debug.assertion(t != null, "Could find neither MigratableTarget nor JTAMigratableTarget " + this.target.getName());
         ServerMBean[] beanList = ((MigratableTargetMBean)t).getAllCandidateServers();
         Collection members = ClusterService.getServices().getRemoteMembers();
         ArrayList idList = new ArrayList();

         for(int i = 0; i < beanList.length; ++i) {
            String serverName = beanList[i].getName();
            if (ManagementService.getRuntimeAccess(getKernelId()).getServerName().equals(serverName)) {
               idList.add(LocalServerIdentity.getIdentity());
            } else {
               Iterator it = members.iterator();

               while(it.hasNext()) {
                  ClusterMemberInfo info = (ClusterMemberInfo)it.next();
                  if (info.serverName().equals(serverName)) {
                     idList.add(info.identity());
                     break;
                  }
               }
            }
         }

         this.cachedHostList = (HostID[])((HostID[])idList.toArray(this.cachedHostList));
      } catch (Exception var10) {
      }

   }

   private boolean permitActivateWithoutLease(MigratableTargetMBean target) {
      if (!this.isJTAMigratableGroup()) {
         return false;
      } else {
         ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(getKernelId()).getServer().getCluster();
         return clusterMBean != null && "consensus".equals(clusterMBean.getMigrationBasis()) && isAutoServiceMigrationEnabled(clusterMBean);
      }
   }

   private static boolean isAutoServiceMigrationEnabled(ClusterMBean cluster) {
      MigratableTargetMBean[] targets = cluster.getMigratableTargets();
      if (targets == null) {
         return false;
      } else {
         for(int i = 0; i < targets.length; ++i) {
            if (!"manual".equals(targets[i].getMigrationPolicy())) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isJTAMigratableGroup() {
      return this.target instanceof JTAMigratableTargetMBean;
   }

   private boolean isLocalJTAMigratableTarget() {
      if (!this.isJTAMigratableGroup()) {
         return false;
      } else {
         JTAMigratableTargetMBean localJTAMT = ManagementService.getRuntimeAccess(getKernelId()).getServer().getJTAMigratableTarget();
         return localJTAMT == null ? false : localJTAMT.getName().equals(this.target.getName());
      }
   }

   private boolean isLocalJTAAutoMigratable() {
      ServerMBean localServer = ManagementService.getRuntimeAccess(getKernelId()).getServer();
      return localServer.getJTAMigratableTarget() != null && "failure-recovery".equals(localServer.getJTAMigratableTarget().getMigrationPolicy());
   }

   private static void p(Object o) {
      SingletonServicesDebugLogger.debug("MigratableGroup: " + o.toString());
   }

   private boolean executePostScript() {
      String postScript = this.target.getPostScript();
      if (postScript == null) {
         return true;
      } else {
         boolean autoMigratable = !this.target.getMigrationPolicy().equals("manual");
         if (!ScriptExecutor.runNMScript(postScript, this.target) && this.target.isPostScriptFailureFatal()) {
            this.logPostScriptExecutionFailed();
            if (autoMigratable) {
               try {
                  if (this.isDynamicService()) {
                     this.updateState(4, ManagementService.getRuntimeAccess(getKernelId()).getServer().getName());
                  } else {
                     this.updateState(4, (Serializable)null);
                  }
               } catch (Exception var4) {
                  ClusterExtensionLogger.logFailedToNotifyPostScriptFailureToStateManager(this.getName(), postScript, var4);
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private boolean isDynamicService() {
      return this.config != null;
   }

   private static SingletonServicesStateManagerRemote getLocalSingletonServicesStateManager() {
      SingletonServicesStateManagerRemote remote = null;

      try {
         ManagedInvocationContext mic = ((ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0])).setInvocationContext("DOMAIN");
         Throwable var2 = null;

         try {
            Environment env = new Environment();
            Context ctx = env.getInitialContext();
            remote = (SingletonServicesStateManagerRemote)ctx.lookup("weblogic.cluster.singleton.SingletonServicesStateManager");
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (NamingException var15) {
         if (DEBUG) {
            p("MigratableGroup.getLocalSingletonServicesStateManager ne: " + var15);
         }
      }

      return remote;
   }

   public void handlePriorityShutDownTasks() {
      if (this.isActive && this.target.getMigrationPolicy().equals("failure-recovery") && this.target.isManualActiveOn(ManagementService.getRuntimeAccess(getKernelId()).getServer())) {
         if (this.isJTAMigratableGroup() && this.isJTAHealthFailed()) {
            if (DEBUG) {
               p("Not marking the state of jta migratable group " + this.getName() + " as shutdown because it is failed");
            }

            return;
         }

         try {
            this.updateState(3, (Serializable)null);
         } catch (Exception var3) {
            ClusterLogger.logLeasingError(this.getName(), var3);
         }
      } else if (this.isActive && this.isDynamicService()) {
         try {
            this.updateState(3, ManagementService.getRuntimeAccess(getKernelId()).getServer().getName());
         } catch (Exception var2) {
            ClusterLogger.logLeasingError(this.getName(), var2);
         }
      }

   }

   private boolean isJTAHealthFailed() {
      HealthState[] healthStates = HealthMonitorService.getHealthStates();

      for(int i = 0; i < healthStates.length; ++i) {
         HealthState state = healthStates[i];
         if (state.getSubsystemName().equals("JTA")) {
            if (state.getState() == 3) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   private void updateState(int state, Serializable data) throws Exception {
      SingletonServicesStateManagerRemote smr = getLocalSingletonServicesStateManager();
      if (smr != null) {
         SingletonServicesState svcState = new SingletonServicesState(state);
         svcState.setStateData(data);
         HashMap map = new HashMap();
         map.put("Sender", ManagementService.getRuntimeAccess(getKernelId()).getServerName());
         map.put("SvcName", this.getName());
         map.put("SvcState", svcState);
         Boolean status = (Boolean)smr.invoke(1001, map);
         if (status) {
            if (DEBUG) {
               p("Updated state of " + this.getName() + " to " + svcState);
            }

         } else {
            throw new Exception("Failed to update state of " + this.getName() + " to " + svcState);
         }
      } else {
         throw new Exception("Failed to lookup local state manager to update state of " + this.getName());
      }
   }

   private void logPostScriptExecutionFailed() {
      ClusterExtensionLogger.logPostDeactivationScriptFailure(this.target.getPostScript(), this.getName());
   }

   Migratable[] getAllMgratables() {
      synchronized(this.migratablesAsList) {
         Migratable[] migratables = new Migratable[this.migratablesAsList.size()];
         this.migratablesAsList.toArray(migratables);
         return migratables;
      }
   }

   Object getActivationLock() {
      return this.activationLock;
   }

   public void setReadyListener(ReadyListener readyListener) {
      synchronized(this.migratablesAsList) {
         if (readyListener != null) {
            this.readyListener = readyListener;
         }

      }
   }

   public void setReady(boolean isReady) {
      synchronized(this.migratablesAsList) {
         if (!this.isReady) {
            this.isReady = isReady;
         }
      }
   }

   public boolean isReady() {
      return this.isReady;
   }

   static AuthenticatedSubject getKernelId() {
      return kernelId;
   }

   private static class MigratableComparator implements Comparator {
      private MigratableComparator() {
      }

      public int compare(Object a, Object b) {
         Migratable am = (Migratable)a;
         Migratable bm = (Migratable)b;
         if (am.getOrder() < bm.getOrder()) {
            return -1;
         } else {
            return am.getOrder() > bm.getOrder() ? 1 : am.toString().compareTo(bm.toString());
         }
      }

      public boolean equals(Object obj) {
         return obj instanceof MigratableComparator;
      }

      // $FF: synthetic method
      MigratableComparator(Object x0) {
         this();
      }
   }
}
