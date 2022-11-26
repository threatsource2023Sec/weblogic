package weblogic.management.deploy.internal;

import java.io.File;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import javax.enterprise.deploy.shared.ModuleType;
import javax.management.MBeanException;
import javax.management.Notification;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.tools.ModuleInfo;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AppDeploymentRuntimeMBean;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentProgressObjectMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class AppDeploymentRuntimeImpl extends DomainRuntimeMBeanDelegate implements AppDeploymentRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static Map ngs = new HashMap();
   private final String appName;
   private final String appVersion;
   private final AppDeploymentMBean deployable;
   private NotificationGenerator notificationGenerator;
   private long notificationSequence = 0L;
   private static HashSet startRecognizedOptions = new HashSet();
   private static HashSet stopRecognizedOptions = new HashSet();
   private static HashSet redeployRecognizedOptions = new HashSet();
   private static HashSet undeployRecognizedOptions = new HashSet();
   private static HashSet updateRecognizedOptions = new HashSet();
   private String[] m_Modules;
   private String partitionName = null;
   private DeploymentManagerImpl deploymentManager = null;

   AppDeploymentRuntimeImpl(AppDeploymentMBean deployable, RuntimeMBeanDelegate restParent) throws ManagementException {
      super(ApplicationVersionUtils.getNonPartitionName(deployable.getName()), (RuntimeMBean)("DOMAIN".equals(ApplicationVersionUtils.getPartitionName(deployable.getName())) ? ManagementService.getDomainAccess(kernelId).getDomainRuntime() : restParent));
      this.setRestParent(restParent);
      this.deploymentManager = (DeploymentManagerImpl)restParent;
      this.appName = deployable.getApplicationName();
      this.appVersion = deployable.getVersionIdentifier();
      this.deployable = deployable;
      DomainMBean domain = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().getDomainConfiguration();
      this.partitionName = ApplicationVersionUtils.getPartitionName(deployable.getName());
      if ("DOMAIN".equals(this.partitionName)) {
         this.partitionName = null;
      }

   }

   public String getApplicationName() {
      return this.appName;
   }

   public String getApplicationVersion() {
      return this.appVersion;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public DeploymentProgressObjectMBean start() throws RuntimeException {
      DeploymentData deploymentData = new DeploymentData();
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.START, true, (String)null, deploymentData);
   }

   public DeploymentProgressObjectMBean start(String[] targets, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, startRecognizedOptions, "start");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, (String)null, deploymentOptions);
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.START, false, (String)null, deploymentData);
   }

   public DeploymentProgressObjectMBean stop() throws RuntimeException {
      DeploymentData deploymentData = new DeploymentData();
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.STOP, true, (String)null, deploymentData);
   }

   public DeploymentProgressObjectMBean stop(String[] targets, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, stopRecognizedOptions, "stop");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, (String)null, deploymentOptions);
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.STOP, false, (String)null, deploymentData);
   }

   public String[] getModules() {
      if (this.m_Modules != null) {
         return this.m_Modules;
      } else {
         ArrayList modules = new ArrayList();
         AppDeploymentMBean appDeploymentMBean = ApplicationVersionUtils.getActiveAppDeployment(this.appName);
         if (appDeploymentMBean != null) {
            String sourcePath = appDeploymentMBean.getSourcePath();
            File source = new File(sourcePath);
            File appRoot = new File(source.getParent());
            String planPath = appDeploymentMBean.getPlanPath();
            File plan = null;
            if (planPath != null) {
               plan = new File(planPath);
            }

            try {
               WebLogicDeploymentManager deplMgr = SessionHelper.getDisconnectedDeploymentManager();
               SessionHelper sessionHelper = SessionHelper.getInstance(deplMgr);
               sessionHelper.setFullInit(false);
               sessionHelper.setApplication(source);
               sessionHelper.setApplicationRoot(appRoot);
               if (plan != null) {
                  sessionHelper.setPlan(plan);
               }

               sessionHelper.initializeConfiguration();
               ModuleInfo moduleInfo = sessionHelper.getModuleInfo();
               ModuleType moduleType = moduleInfo.getType();
               int var14;
               int var15;
               if (moduleType == ModuleType.EAR) {
                  ModuleInfo[] subModules = moduleInfo.getSubModules();
                  if (subModules != null) {
                     ModuleInfo[] var13 = subModules;
                     var14 = subModules.length;

                     for(var15 = 0; var15 < var14; ++var15) {
                        ModuleInfo subModule = var13[var15];
                        String moduleName = subModule.getName();
                        moduleType = subModule.getType();
                        if (moduleType == ModuleType.WAR) {
                           String[] contextRoots = subModule.getContextRoots();
                           if (contextRoots != null) {
                              String[] var19 = contextRoots;
                              int var20 = contextRoots.length;

                              for(int var21 = 0; var21 < var20; ++var21) {
                                 String contextRoot = var19[var21];
                                 if (contextRoot != null && contextRoot.length() > 0) {
                                    modules.add(contextRoot);
                                 }
                              }
                           }
                        } else {
                           modules.add(moduleName);
                        }
                     }
                  }
               } else if (moduleType == ModuleType.WAR) {
                  String[] contextRoots = moduleInfo.getContextRoots();
                  if (contextRoots != null) {
                     String[] var25 = contextRoots;
                     var14 = contextRoots.length;

                     for(var15 = 0; var15 < var14; ++var15) {
                        String contextRoot = var25[var15];
                        if (contextRoot != null && contextRoot.length() > 0) {
                           modules.add(contextRoot);
                        }
                     }
                  }
               } else {
                  modules.add(moduleInfo.getName());
               }
            } catch (Exception var23) {
               throw new RuntimeException(var23);
            }
         }

         this.m_Modules = (String[])modules.toArray(new String[0]);
         return this.m_Modules;
      }
   }

   public String getState(String target) {
      AppRuntimeStateRuntimeMBean appRuntimeStateRuntime = null;
      if (this.partitionName == null) {
         appRuntimeStateRuntime = ManagementService.getDomainAccess(kernelId).getAppRuntimeStateRuntime();
      } else {
         DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
         DomainPartitionRuntimeMBean domainPartitionRuntime = domainRuntime.lookupDomainPartitionRuntime(this.partitionName);
         appRuntimeStateRuntime = domainPartitionRuntime.getAppRuntimeStateRuntime();
      }

      return appRuntimeStateRuntime.getCurrentState(this.getName(), target);
   }

   public DeploymentProgressObjectMBean undeploy() throws RuntimeException {
      DeploymentData deploymentData = new DeploymentData();
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.UNDEPLOY, true, (String)null, deploymentData);
   }

   public DeploymentProgressObjectMBean undeploy(String[] targets, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, undeployRecognizedOptions, "undeploy");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, (String)null, deploymentOptions);
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.UNDEPLOY, false, (String)null, deploymentData);
   }

   public DeploymentProgressObjectMBean redeploy() throws RuntimeException {
      DeploymentData deploymentData = new DeploymentData();
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.REDEPLOY, true, (String)null, deploymentData);
   }

   public DeploymentProgressObjectMBean redeploy(String[] targets, String plan, Properties deploymentOptions) throws RuntimeException {
      return this.redeploy(targets, (String)null, plan, deploymentOptions);
   }

   public DeploymentProgressObjectMBean redeploy(String[] targets, String applicationPath, String plan, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, redeployRecognizedOptions, "redeploy");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, plan, deploymentOptions);
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.REDEPLOY, false, applicationPath, deploymentData);
   }

   public DeploymentProgressObjectMBean update(String[] targets, String plan, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, updateRecognizedOptions, "update");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, plan, deploymentOptions);
      deploymentData.setFile(new String[]{plan});
      deploymentData.setPlanUpdate(true);
      return this.doOperation(AppDeploymentRuntimeImpl.OperationType.UPDATE, false, (String)null, deploymentData);
   }

   void setNotificationGenerator(NotificationGenerator ng) {
      this.notificationGenerator = ng;
      ngs.put(this.appName, ng);
   }

   public synchronized void removeNotificationGenerator() {
      ngs.remove(this.appName);
   }

   void sendNotification(DeploymentState deploymentState) {
      if (this.notificationGenerator != null) {
         if (deploymentState.getCurrentState() != null) {
            ++this.notificationSequence;
            Notification notification = new Notification(DeploymentManagerImpl.translateState(deploymentState.getCurrentState()), this.notificationGenerator.getObjectName(), this.notificationSequence);
            String userData = deploymentState.getTarget();
            notification.setUserData(userData);

            try {
               this.notificationGenerator.sendNotification(notification);
            } catch (MBeanException var8) {
               var8.printStackTrace();
            }
         } else {
            TargetModuleState[] moduleStates = deploymentState.getTargetModules();
            if (moduleStates != null) {
               for(int n = 0; n < moduleStates.length; ++n) {
                  ++this.notificationSequence;
                  Notification notification = new Notification(DeploymentManagerImpl.translateState(moduleStates[n].getCurrentState()), this.notificationGenerator.getObjectName(), this.notificationSequence);
                  String userData = deploymentState.getTarget();
                  notification.setUserData(userData);

                  try {
                     this.notificationGenerator.sendNotification(notification);
                  } catch (MBeanException var7) {
                     var7.printStackTrace();
                  }
               }
            }
         }
      }

   }

   private DeploymentProgressObjectMBean doOperation(OperationType operation, boolean sync, String applicationPath, DeploymentData deploymentData) throws RuntimeException {
      DeploymentProgressObjectMBean progressObjectMBean = null;
      DeploymentTaskRuntimeMBean task = null;
      DeploymentOptions options = deploymentData.getDeploymentOptions();
      if (this.partitionName != null) {
         options.setPartition(this.partitionName);
      }

      try {
         DeployerRuntimeMBean deployer = null;
         if (this.partitionName == null) {
            deployer = ManagementService.getDomainAccess(kernelId).getDeployerRuntime();
         } else {
            DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            DomainPartitionRuntimeMBean domainPartitionRuntime = domainRuntime.lookupDomainPartitionRuntime(this.partitionName);
            deployer = domainPartitionRuntime.getDeployerRuntime();
         }

         deploymentData.getDeploymentOptions().setArchiveVersion(this.appVersion);
         switch (operation) {
            case START:
               task = deployer.start(this.name, deploymentData, (String)null, false);
               break;
            case STOP:
               task = deployer.stop(this.name, deploymentData, (String)null, false);
               break;
            case UNDEPLOY:
               task = deployer.undeploy(this.name, deploymentData, (String)null, false);
               break;
            case UPDATE:
               task = deployer.update(this.name, deploymentData, (String)null, false);
               break;
            case REDEPLOY:
               if (applicationPath == null) {
                  task = deployer.redeploy(this.name, deploymentData, (String)null, false);
               } else {
                  task = deployer.redeploy(applicationPath, this.name, deploymentData, (String)null, false);
               }
         }

         progressObjectMBean = this.deploymentManager.allocateDeploymentProgressObject(this.getName(), task, this.deployable);
         if (deploymentData.getDeploymentOptions().isStartTask()) {
            task.start();
            if (sync) {
               task.waitForTaskCompletion(-1L);
            }
         }
      } catch (Throwable var11) {
         RuntimeException rtEx = ExceptionTranslator.translateException(var11);
         if (progressObjectMBean == null || task == null) {
            throw rtEx;
         }

         ((DeploymentProgressObjectImpl)progressObjectMBean).addException(rtEx);
      }

      return progressObjectMBean;
   }

   static {
      startRecognizedOptions.add("adminMode");
      startRecognizedOptions.add("clusterDeploymentTimeout");
      startRecognizedOptions.add("defaultSubmoduleTargets");
      startRecognizedOptions.add("planVersion");
      startRecognizedOptions.add("retireTimeout");
      startRecognizedOptions.add("subModuleTargets");
      startRecognizedOptions.add("timeout");
      startRecognizedOptions.add("partition");
      startRecognizedOptions.add("resourceGroupTemplate");
      startRecognizedOptions.add("startTask");
      stopRecognizedOptions.add("adminMode");
      stopRecognizedOptions.add("clusterDeploymentTimeout");
      stopRecognizedOptions.add("defaultSubmoduleTargets");
      stopRecognizedOptions.add("forceUndeployTimeout");
      stopRecognizedOptions.add("planVersion");
      stopRecognizedOptions.add("gracefulProductionToAdmin");
      stopRecognizedOptions.add("gracefulIgnoreSessions");
      stopRecognizedOptions.add("rmiGracePeriod");
      stopRecognizedOptions.add("subModuleTargets");
      stopRecognizedOptions.add("timeout");
      stopRecognizedOptions.add("partition");
      stopRecognizedOptions.add("resourceGroupTemplate");
      stopRecognizedOptions.add("startTask");
      stopRecognizedOptions.add("appShutdownOnStop");
      redeployRecognizedOptions.add("adminMode");
      redeployRecognizedOptions.add("clusterDeploymentTimeout");
      redeployRecognizedOptions.add("defaultSubmoduleTargets");
      redeployRecognizedOptions.add("deploymentOrder");
      redeployRecognizedOptions.add("forceUndeployTimeout");
      redeployRecognizedOptions.add("planVersion");
      redeployRecognizedOptions.add("retireGracefully");
      redeployRecognizedOptions.add("retireTimeout");
      redeployRecognizedOptions.add("gracefulProductionToAdmin");
      redeployRecognizedOptions.add("gracefulIgnoreSessions");
      redeployRecognizedOptions.add("rmiGracePeriod");
      redeployRecognizedOptions.add("subModuleTargets");
      redeployRecognizedOptions.add("timeout");
      redeployRecognizedOptions.add("noVersion");
      redeployRecognizedOptions.add("useNonExclusiveLock");
      redeployRecognizedOptions.add("partition");
      redeployRecognizedOptions.add("editSession");
      redeployRecognizedOptions.add("resourceGroupTemplate");
      redeployRecognizedOptions.add("removePlanOverride");
      redeployRecognizedOptions.add("specifiedTargetsOnly");
      undeployRecognizedOptions.add("clusterDeploymentTimeout");
      undeployRecognizedOptions.add("defaultSubmoduleTargets");
      undeployRecognizedOptions.add("forceUndeployTimeout");
      undeployRecognizedOptions.add("gracefulProductionToAdmin");
      undeployRecognizedOptions.add("gracefulIgnoreSessions");
      undeployRecognizedOptions.add("rmiGracePeriod");
      undeployRecognizedOptions.add("subModuleTargets");
      undeployRecognizedOptions.add("timeout");
      undeployRecognizedOptions.add("useNonExclusiveLock");
      undeployRecognizedOptions.add("partition");
      undeployRecognizedOptions.add("editSession");
      undeployRecognizedOptions.add("resourceGroupTemplate");
      updateRecognizedOptions.add("clusterDeploymentTimeout");
      updateRecognizedOptions.add("defaultSubmoduleTargets");
      updateRecognizedOptions.add("planVersion");
      updateRecognizedOptions.add("subModuleTargets");
      updateRecognizedOptions.add("timeout");
      updateRecognizedOptions.add("useNonExclusiveLock");
      updateRecognizedOptions.add("partition");
      updateRecognizedOptions.add("editSession");
      updateRecognizedOptions.add("resourceGroupTemplate");
      updateRecognizedOptions.add("removePlanOverride");
   }

   private static enum OperationType {
      START,
      STOP,
      UNDEPLOY,
      UPDATE,
      REDEPLOY;
   }
}
