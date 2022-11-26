package weblogic.deploy.internal.adminserver;

import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.beans.factory.DeploymentBeanFactory;
import weblogic.deploy.common.Debug;
import weblogic.deploy.compatibility.NotificationBroadcaster;
import weblogic.deploy.internal.AggregateDeploymentVersion;
import weblogic.deploy.internal.Deployment;
import weblogic.deploy.internal.DeploymentVersion;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.adminserver.operations.AbstractOperation;
import weblogic.deploy.internal.adminserver.operations.OperationHelper;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.ConfigurationContext;
import weblogic.deploy.service.DeploymentException;
import weblogic.deploy.service.DeploymentFailureHandler;
import weblogic.deploy.service.DeploymentProvider;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.RequiresRestartFailureDescription;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.datatransferhandlers.SourceCache;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentException;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentRequestCancellerService;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.Loggable;
import weblogic.management.DeferredDeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.TargetStatus;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.AppRuntimeStateRuntimeMBeanImpl;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.DeployerRuntimeImpl;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.DeploymentManagerLogger;
import weblogic.management.deploy.internal.DeploymentServerService;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditSessionLifecycleListener;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.utils.AdminServerDeploymentManagerService;
import weblogic.management.utils.AdminServerDeploymentManagerServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.StackTraceUtils;

public final class DeploymentManager implements DeploymentProvider, AdminServerDeploymentManagerService {
   private static final String DEPLOYMENT_SERVICE_CALLBACK_HANDLER_ID = "Application";
   private static DeploymentServiceDriver driver;
   private static AggregateDeploymentVersion adminServerAggregateDeploymentVersion;
   private static DeploymentBeanFactory beanFactory = DeploymentServerService.getDeploymentBeanFactory();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int REMOVED_REQUEST_INFO_CACHE_SIZE = 3;
   private final List pendingDeploymentsForLockOwner;
   private final List pendingDeploymentsForLockAcquirer;
   private final Map pendingControlDeployments;
   private static Map requestInfoTable;
   private static RequestInfoCacheMap removedRequestInfoCache;
   private static DeployerRuntimeImpl deployerRuntime;
   private static Map taskRuntimeToDeploymentTable;
   private static Map taskRuntimeToDeploymentInfoTable;
   private final String editSessionPartitionName;
   private final String editSessionName;
   private static boolean initialized = false;
   private static final Map partitionDeploymentManagers = new ConcurrentHashMap();

   private DeploymentManager() {
      this("DOMAIN", "default");
      Map globalDepMgrMap = new HashMap();
      globalDepMgrMap.put("default", this);
      partitionDeploymentManagers.put("DOMAIN", globalDepMgrMap);
   }

   private DeploymentManager(String editSessionPartitionName, String editSessionName) {
      this.editSessionPartitionName = editSessionPartitionName;
      this.editSessionName = editSessionName;
      this.pendingDeploymentsForLockOwner = new ArrayList();
      this.pendingDeploymentsForLockAcquirer = new ArrayList();
      this.pendingControlDeployments = new HashMap();
   }

   public static DeploymentManager getInstance(AuthenticatedSubject subject) {
      SecurityServiceManager.checkKernelIdentity(subject);
      return DeploymentManager.Maker.INSTANCE;
   }

   public static synchronized DeploymentManager getInstance(AuthenticatedSubject subject, String editSessionName) {
      if (editSessionName == null) {
         return getInstance(subject);
      } else {
         SecurityServiceManager.checkKernelIdentity(subject);
         String editSessionPartitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         return getInstance(subject, editSessionPartitionName, editSessionName);
      }
   }

   public static synchronized DeploymentManager getInstance(AuthenticatedSubject subject, String editSessionPartitionName, String editSessionName) {
      if (editSessionPartitionName == null) {
         return getInstance(subject, editSessionName);
      } else {
         SecurityServiceManager.checkKernelIdentity(subject);
         return getOrCreateInstance(editSessionPartitionName, editSessionName);
      }
   }

   private static DeploymentManager getOrCreateInstance(String editSessionPartitionName, String editSessionName) {
      Map namedDeploymentManagers = (Map)partitionDeploymentManagers.get(editSessionPartitionName);
      boolean created = false;
      if (editSessionName == null) {
         editSessionName = "default";
      }

      if (namedDeploymentManagers == null) {
         namedDeploymentManagers = new HashMap();
         ((Map)namedDeploymentManagers).put(editSessionName, new DeploymentManager(editSessionPartitionName, editSessionName));
         partitionDeploymentManagers.put(editSessionPartitionName, namedDeploymentManagers);
         created = true;
      }

      DeploymentManager depMgr = (DeploymentManager)((Map)namedDeploymentManagers).get(editSessionName);
      if (depMgr == null) {
         depMgr = new DeploymentManager(editSessionPartitionName, editSessionName);
         created = true;
         ((Map)namedDeploymentManagers).put(editSessionName, depMgr);
      }

      if (created) {
         driver.registerDeploymentProvider(depMgr);
      }

      return depMgr;
   }

   private static void loadAppRuntimeStates() throws ManagementException {
      AppRuntimeStateManager.getManager().loadStartupState((Map)null);
      Map deploymentVersions = AppRuntimeStateManager.getManager().getDeploymentVersions();
      if (deploymentVersions != null) {
         Iterator iterator = deploymentVersions.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String appId = (String)entry.getKey();
            DeploymentVersion version = (DeploymentVersion)entry.getValue();
            addOrUpdateAdminServerAggregateDeploymentVersion(appId, version);
         }
      }

   }

   private static boolean isInitialized() {
      if (initialized) {
         return true;
      } else {
         initialized = true;
         return false;
      }
   }

   public static void initialize() throws ManagementException {
      if (!isInitialized()) {
         loadAppRuntimeStates();
         driver = DeploymentServiceDriver.getInstance();
         DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
         deployerRuntime = (DeployerRuntimeImpl)domainAccess.getDeployerRuntime();
         driver.initialize("Application", getAdminServerAggregateDeploymentsVersion(), DeploymentManager.Maker.INSTANCE);
         requestInfoTable = new HashMap();
         removedRequestInfoCache = new RequestInfoCacheMap();
         taskRuntimeToDeploymentTable = new HashMap();
         taskRuntimeToDeploymentInfoTable = new HashMap();
         AppRuntimeStateRuntimeMBeanImpl.initialize();
         EditSessionLifecycleListener editSessionLifecycleListener = new EditSessionLifecycleListener() {
            public void onEditSessionDestroyed(EditAccess editAccess) {
               String partitionName = editAccess.getPartitionName();
               String editSessionName = editAccess.getEditSessionName();
               if (partitionName == null) {
                  partitionName = "DOMAIN";
               }

               if (editSessionName == null) {
                  editSessionName = "default";
               }

               Map depMgrForEditSession = (Map)DeploymentManager.partitionDeploymentManagers.get(partitionName);
               if (depMgrForEditSession != null) {
                  DeploymentManager deplMgr = (DeploymentManager)depMgrForEditSession.remove(editSessionName);
                  if (deplMgr != null) {
                     DeploymentManager.driver.unregisterDeploymentProvider(deplMgr);
                  }

                  if (depMgrForEditSession.isEmpty()) {
                     DeploymentManager.partitionDeploymentManagers.remove(partitionName);
                  }
               }

            }

            public void onEditSessionStarted(EditAccess editAccess) {
               String partitionName = editAccess.getPartitionName();
               String editSessionName = editAccess.getEditSessionName();
               if (partitionName == null) {
                  partitionName = "DOMAIN";
               }

               if (editSessionName == null) {
                  editSessionName = "default";
               }

               DeploymentManager.getOrCreateInstance(partitionName, editSessionName);
            }
         };
         ManagementServiceRestricted.addEditSessionLifecycleListener(editSessionLifecycleListener);
      }
   }

   public static void shutdown() {
      DeploymentServiceDriver.getInstance().shutdown();
   }

   public DeployerRuntimeImpl getDeployerRuntime() {
      if (this.editSessionPartitionName != null && !"DOMAIN".equals(this.editSessionPartitionName)) {
         DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
         DomainRuntimeMBean domainRuntime = domainAccess.getDomainRuntime();
         DomainPartitionRuntimeMBean domainPartition = domainRuntime.lookupDomainPartitionRuntime(this.editSessionPartitionName);
         return (DeployerRuntimeImpl)domainPartition.getDeployerRuntime();
      } else {
         return deployerRuntime;
      }
   }

   public void restartSystemResource(SystemResourceMBean systemResource) throws ManagementException {
      ConfigChangesHandler.restartSystemResource(systemResource, this);
   }

   public String getIdentity() {
      return "Application";
   }

   public synchronized void addDeploymentsTo(DeploymentRequest deploymentRequest, ConfigurationContext ctxt) {
      List[] deployments;
      if (this.pendingDeploymentsForLockAcquirer.isEmpty()) {
         deployments = ConfigChangesHandler.configChanged(deploymentRequest, ctxt, this);
      } else {
         Deployment deployment = (Deployment)this.pendingDeploymentsForLockAcquirer.get(0);
         DeploymentData data = deployment.getInternalDeploymentData().getExternalDeploymentData();
         if (ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(data) && !data.getDeploymentOptions().getSpecifiedTargetsOnly()) {
            deployments = this.getConfigChangeDeployments(deploymentRequest, ctxt);
         } else {
            deployments = new List[]{this.pendingDeploymentsForLockAcquirer};
         }
      }

      this.updateRequestAndDeployments(deploymentRequest, deployments);
      this.clearPendingDeployments();
   }

   private void clearPendingDeployments() {
      this.pendingDeploymentsForLockAcquirer.clear();
      this.pendingDeploymentsForLockOwner.clear();
   }

   private void updateRequestAndDeployments(DeploymentRequest deploymentRequest, List[] deployments) {
      if (deployments != null && deployments.length != 0) {
         DeploymentRequestInfo deploymentRequestInfo = null;
         boolean callerOwnsEditLock = true;
         boolean aControlOperation = true;
         AuthenticatedSubject initiator = null;
         EditAccessHelper editAccessHelper = null;

         for(int i = 0; i < deployments.length; ++i) {
            List list = deployments[i];
            if (list != null && !list.isEmpty()) {
               Iterator iterator = list.iterator();

               while(iterator.hasNext()) {
                  if (deploymentRequestInfo == null) {
                     deploymentRequestInfo = new DeploymentRequestInfo(deploymentRequest, this);
                  }

                  Deployment dep = (Deployment)iterator.next();
                  callerOwnsEditLock = dep.isCallerLockOwner();
                  aControlOperation = dep.isAControlOperation();
                  initiator = dep.getInitiator();
                  editAccessHelper = dep.getEditAccessHelper();
                  if (editAccessHelper == null) {
                     editAccessHelper = EditAccessHelper.getInstance(kernelId);
                  }

                  deploymentRequestInfo.setEditAccessHelper(editAccessHelper);
                  deploymentRequest.addDeployment(dep);
                  dep.setDeploymentRequestIdentifier(deploymentRequest.getId());
                  DeploymentTaskRuntime taskRuntime = dep.getDeploymentTaskRuntime();
                  String taskId = taskRuntime.getId();
                  deploymentRequest.getTaskRuntime().addDeploymentRequestSubTask(taskRuntime, taskId);
                  DeploymentData deploymentData = taskRuntime.getDeploymentData();
                  if (deploymentData != null) {
                     deploymentRequest.setTimeoutInterval((long)deploymentData.getTimeOut());
                  }

                  deploymentRequestInfo.addDeploymentStatusContainerFor(taskId);
                  if (dep.isAnAppDeployment()) {
                     dep.setStaged(taskRuntime.getAppDeploymentMBean().getStagingMode());
                  }

                  taskRuntimeToDeploymentInfoTable.put(taskId, deploymentRequestInfo);
                  taskRuntimeToDeploymentTable.put(taskId, dep);
               }
            }
         }

         if (deploymentRequestInfo != null) {
            deploymentRequest.setInitiator(initiator);
            if (!callerOwnsEditLock) {
               deploymentRequest.setStartControl(true);
            }

            deploymentRequest.setControlRequest(aControlOperation);
            if (!aControlOperation) {
               deploymentRequestInfo.setIsEditLockOwner(callerOwnsEditLock);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Request '" + deploymentRequest.getId() + "' initiated by '" + initiator + "'");
               }

               DomainMBean editableDomain = editAccessHelper.getEditDomainBean(initiator);
               deploymentRequestInfo.setEditableDomain(editableDomain);
            }

            synchronized(requestInfoTable) {
               requestInfoTable.put(new Long(deploymentRequest.getId()), deploymentRequestInfo);
            }
         }
      }
   }

   List getPendingDeploymentsForLockAcquirer() {
      synchronized(this.pendingDeploymentsForLockAcquirer) {
         return this.pendingDeploymentsForLockAcquirer;
      }
   }

   void clearPendingDeploymentsForLockAcquirer() {
      synchronized(this.pendingDeploymentsForLockAcquirer) {
         this.pendingDeploymentsForLockAcquirer.clear();
      }
   }

   public Collection getPendingDeploymentsForEditLockOwner() {
      synchronized(this.pendingDeploymentsForLockOwner) {
         if (!this.pendingDeploymentsForLockOwner.isEmpty() && this.pendingDeploymentsForLockOwner.size() > 0) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: getPendingDeployments returning " + this.pendingDeploymentsForLockOwner.size() + " deployments");
            }

            List result = new ArrayList();
            result.addAll(this.pendingDeploymentsForLockOwner);
            return result;
         } else {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: getPendingDeployments no deployments to return");
            }

            return null;
         }
      }
   }

   public DomainMBean getEditableDomainMBean(long requestId) {
      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentManager: getEditableDomainMBean() ' requestInfo for request '" + requestId + "' is null");
         }

         return null;
      } else {
         DomainMBean domain = requestInfo.editableDomain;
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentManager: getEditableDomainMBean() ' editableDomain for request '" + requestId + "' is " + domain);
         }

         return domain;
      }
   }

   private static String getCompositeKey(String beanName, int operation) {
      return beanName + "###" + Integer.toString(operation);
   }

   private boolean lockOwnerMatchesPendingDeploymentsLockOwner(boolean callerOwnsEditLock) {
      synchronized(this.pendingDeploymentsForLockAcquirer) {
         if (callerOwnsEditLock && !this.pendingDeploymentsForLockAcquirer.isEmpty()) {
            return false;
         }
      }

      synchronized(this.pendingDeploymentsForLockOwner) {
         return callerOwnsEditLock || this.pendingDeploymentsForLockOwner.isEmpty();
      }
   }

   private static int getCanonicalOperation(int operation) {
      int canonicalOperation = operation;
      switch (operation) {
         case 1:
         case 6:
         case 11:
            canonicalOperation = 1;
         case 2:
         case 3:
         case 5:
         case 7:
         case 8:
         case 10:
         default:
            break;
         case 4:
         case 12:
            canonicalOperation = 4;
            break;
         case 9:
            canonicalOperation = 9;
      }

      return canonicalOperation;
   }

   public synchronized Deployment createDeployment(String taskId, DeploymentData info, int operation, DeploymentTaskRuntime task, DomainMBean editableDomain, boolean configChangeDeployment, AuthenticatedSubject authenticatedSubject, boolean callerIsEditLockOwner, boolean aControlOperation, boolean requiresRestart) throws ManagementException {
      if (!aControlOperation && !this.lockOwnerMatchesPendingDeploymentsLockOwner(callerIsEditLockOwner)) {
         String msg = DeployerRuntimeLogger.configLocked();
         throw new ManagementException(msg);
      } else {
         Deployment deployment = this.createAndInitializeDeployment(task, editableDomain, info, operation, authenticatedSubject, callerIsEditLockOwner, aControlOperation, requiresRestart);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentManager: deployment '" + deployment + "' initiated by '" + authenticatedSubject + "'");
         }

         if (aControlOperation) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: added deployment: " + deployment + " to list of control opeartions");
            }

            this.pendingControlDeployments.put(task.getId(), deployment);
         } else if (callerIsEditLockOwner) {
            if (configChangeDeployment) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("DeploymentManager: not adding config change: " + deployment + " to list of pending  deployments for edit lock owner");
               }
            } else {
               synchronized(this.pendingDeploymentsForLockOwner) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("DeploymentManager: added deployment: " + deployment + " to list of pending deployments for edit lock owner");
                  }

                  boolean appDeployRemoved = this.removeDeployIfNoop(deployment);
                  if (!appDeployRemoved) {
                     this.pendingDeploymentsForLockOwner.add(deployment);
                  }
               }
            }
         } else {
            synchronized(this.pendingDeploymentsForLockAcquirer) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("DeploymentManager: added deployment: " + deployment + " to list of pending deployments for edit lock acquirer");
               }

               this.pendingDeploymentsForLockAcquirer.add(deployment);
            }
         }

         return deployment;
      }
   }

   private Deployment createAndInitializeDeployment(DeploymentTaskRuntime task, DomainMBean editableDomain, DeploymentData info, int operation, AuthenticatedSubject initiator, boolean callerIsEditLockOwner, boolean aControlOperation, boolean requiresRestart) {
      Deployment deployment = this.createDeployment(task, task.getDeploymentData(), editableDomain, initiator, callerIsEditLockOwner, aControlOperation, requiresRestart);
      Map aggregateVersionComponents = getAdminServerAggregateDeploymentsVersion().getVersionComponents();
      AggregateDeploymentVersion newAggregateVersion = AggregateDeploymentVersion.createAggregateDeploymentVersion(aggregateVersionComponents);
      DeploymentVersion newDeploymentVersion = null;
      if (task.getAppDeploymentMBean() != null) {
         boolean configChange = isAConfigurationChange(task.getAppDeploymentMBean(), info, operation);
         newDeploymentVersion = this.createDeploymentVersion(deployment.getIdentity(), configChange, aControlOperation);
         if (operation != 4 && operation != 12) {
            newAggregateVersion.addOrUpdateDeploymentVersion(deployment.getIdentity(), newDeploymentVersion);
         } else {
            newAggregateVersion.removeDeploymentVersionFor(deployment.getIdentity());
         }
      }

      deployment.setProposedVersion(newAggregateVersion);
      deployment.setProposedDeploymentVersion(newDeploymentVersion);
      ChangeDescriptor changeDescriptor = this.createChangeDescriptor(deployment.getIdentity(), newDeploymentVersion);
      deployment.addChangeDescriptor(changeDescriptor);
      if (Debug.isDeploymentDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "DeploymentManager: Created deployment for '" + DeploymentTaskRuntime.getTaskDescription(operation) + "' operation: '" + deployment + "'";
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      return deployment;
   }

   private static DeploymentRequestInfo getDeploymentRequestInfo(long requestId) {
      return getDeploymentRequestInfo(requestId, false);
   }

   private static DeploymentRequestInfo getDeploymentRequestInfo(long requestId, boolean checkRemovedInfo) {
      synchronized(requestInfoTable) {
         Long requestIdValue = requestId;
         DeploymentRequestInfo requestInfo = (DeploymentRequestInfo)requestInfoTable.get(requestIdValue);
         if (requestInfo == null && checkRemovedInfo) {
            requestInfo = (DeploymentRequestInfo)removedRequestInfoCache.get(requestIdValue);
         }

         return requestInfo;
      }
   }

   public void deploymentRequestSucceeded(long requestId, FailureDescription[] failures) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Received deployment request success:  for deployment request id: " + requestId);
      }

      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("deploymentRequestSucceeded(): Request id: " + requestId + " has completed or timed out since there is no active current deployment");
         }
      } else {
         synchronized(requestInfo) {
            boolean var24 = false;

            try {
               var24 = true;
               this.releaseEditLock(requestInfo);
               var24 = false;
            } finally {
               if (var24) {
                  this.updateTasksWithAccumulatedStatus(requestId);
                  byte status = 3;
                  ArrayList deferredFailures = new ArrayList();
                  if (failures != null && failures.length > 0) {
                     for(int i = 0; i < failures.length; ++i) {
                        FailureDescription eachFailure = failures[i];
                        if (!(eachFailure instanceof RequiresRestartFailureDescription)) {
                           status = 4;
                           String operation = failures[i].getAttemptedOperation();
                           String failedServer = failures[i].getServer();
                           DeferredDeploymentException failureReason = new DeferredDeploymentException(failures[i].getReason());
                           deferredFailures.add(new FailureDescription(failedServer, failureReason, operation));
                        }
                     }
                  }

                  FailureDescription[] deferredFailureArray = new FailureDescription[deferredFailures.size()];
                  deferredFailureArray = (FailureDescription[])((FailureDescription[])deferredFailures.toArray(deferredFailureArray));
                  this.updateTasksWithDeploymentStatus(requestId, deferredFailureArray, status, false);
                  this.updateAdminServerRuntimeStateAndAggregateDeploymentVersion(requestInfo);
               }
            }

            this.updateTasksWithAccumulatedStatus(requestId);
            int status = 3;
            List deferredFailures = new ArrayList();
            if (failures != null && failures.length > 0) {
               for(int i = 0; i < failures.length; ++i) {
                  FailureDescription eachFailure = failures[i];
                  if (!(eachFailure instanceof RequiresRestartFailureDescription)) {
                     status = 4;
                     String operation = failures[i].getAttemptedOperation();
                     String failedServer = failures[i].getServer();
                     DeferredDeploymentException failureReason = new DeferredDeploymentException(failures[i].getReason());
                     deferredFailures.add(new FailureDescription(failedServer, failureReason, operation));
                  }
               }
            }

            FailureDescription[] deferredFailureArray = new FailureDescription[deferredFailures.size()];
            deferredFailureArray = (FailureDescription[])((FailureDescription[])deferredFailures.toArray(deferredFailureArray));
            this.updateTasksWithDeploymentStatus(requestId, deferredFailureArray, status, false);
            this.updateAdminServerRuntimeStateAndAggregateDeploymentVersion(requestInfo);
         }
      }

   }

   private void updateTasksWithDeploymentStatus(long requestId, FailureDescription[] failures, int targetStatus, boolean commitPhase) {
      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentManager: updateTasksWithDeploymentStatus() couldn't find requestInfo for request : " + requestId);
         }

      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentManager: updateTasksWithDeploymentStatus for request : " + requestId + " with status: " + targetStatus);
         }

         synchronized(requestInfo) {
            DeploymentRequest request = requestInfo.request;
            Iterator iterator = request.getDeployments("Application");
            boolean hasConfigurationDeployments = request.getDeployments("Configuration").hasNext();

            while(true) {
               DeploymentTaskRuntime taskRuntime;
               do {
                  if (!iterator.hasNext()) {
                     return;
                  }

                  Deployment deployment = (Deployment)iterator.next();
                  taskRuntime = deployment.getDeploymentTaskRuntime();
                  if (Debug.isDeploymentDebugEnabled()) {
                     StringBuffer theFailures = new StringBuffer();
                     if (failures != null) {
                        theFailures.append(Arrays.asList(failures));
                     } else {
                        theFailures.append("null");
                     }

                     Debug.deploymentDebug("DeploymentManager: Updating task '" + taskRuntime.getId() + "' with failures : " + theFailures.toString());
                  }

                  updateTaskWithFailures(taskRuntime, failures, targetStatus);
                  if (!commitPhase && (targetStatus == 3 || targetStatus == 4)) {
                     this.updateTaskStatusOfRestartTargets(requestId, taskRuntime, request.getTaskRuntime().getServersToBeRestarted());
                  }

                  if (!commitPhase && targetStatus == 2) {
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("DeploymentManager: Calling handleFailure on task '" + taskRuntime.getId() + "'");
                     }

                     taskRuntime.handleFailure();
                  }

                  if (commitPhase) {
                     taskRuntime.updatePendingServersWithSuccess();
                     if (!hasConfigurationDeployments) {
                        boolean success = targetStatus != 2;
                        requestInfo.getEditAccessHelper().nonConfigTaskCompleted(request.getId(), success);
                     }
                  }
               } while(!commitPhase && targetStatus != 2);

               this.reset(taskRuntime.getId());
            }
         }
      }
   }

   private void releaseEditLock(DeploymentRequestInfo requestInfo) {
      if (!requestInfo.isControlOperation()) {
         long requestId = requestInfo.request.getId();
         boolean editLockOwner = requestInfo.ownsEditLock;
         AuthenticatedSubject initiator = requestInfo.request.getInitiator();
         beanFactory.resetDeployerInitiatedBeanUpdates();
         if (initiator != null && !editLockOwner) {
            try {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentLogger.debug("DeploymentManager: stopping edit session for request id: " + requestId);
               }

               requestInfo.getEditAccessHelper().stopEditSession(initiator);
            } catch (ManagementException var7) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentLogger.debug("Failed to stop edit session on a successful deployment of request id: " + requestId);
               }
            }
         }

      }
   }

   private void deploymentRequestFailedBeforeStart(DeploymentRequestInfo requestInfo, DeploymentException exception) {
      if (requestInfo != null) {
         long requestId = requestInfo.getRequestId();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Received deployment request failure : " + exception.toString() + " before start for request id: " + requestId);
         }

         this.clearPendingDeployments();
         synchronized(requestInfo) {
            DeploymentRequest request = requestInfo.request;
            Set failureListeners = request.getRegisteredFailureListeners();
            if (failureListeners != null) {
               Iterator var8 = failureListeners.iterator();

               while(var8.hasNext()) {
                  Object failureListener = var8.next();
                  DeploymentFailureHandler failureHandler = (DeploymentFailureHandler)failureListener;
                  failureHandler.deployFailed(requestId, exception);
               }
            }

            this.deploymentRequestFailed(requestId, (DeploymentException)null, exception.getFailures());
         }
      }
   }

   public void deploymentRequestFailed(long requestId, DeploymentException reason, FailureDescription[] failures) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Received deployment request failure: " + reason + " for deployment request id: " + requestId);
      }

      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager.deploymentRequestFailed(): requestInfo : " + requestInfo);
      }

      if (requestInfo == null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("deploymentRequestFailed(): Request id: " + requestId + " has completed, timed out or was never started since there is no active deployment information for this request");
         }
      } else {
         synchronized(requestInfo) {
            this.undoUnactivatedChanges(requestInfo);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager.deploymentRequestFailed(): Calling updateTasksWithDeploymentStatus() for request '" + requestId + "' with status failed");
            }

            this.updateTasksWithDeploymentStatus(requestId, failures, 2, false);
         }
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager.deploymentRequestFailed(): Returning from deploymentRequestFailed()...");
      }

   }

   public void undoChangesTriggeredByUser(Deployment deployment) {
      this.pendingDeploymentsForLockOwner.remove(deployment);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: Removed entry: '" + deployment + "' from pendingDeploymentsForLockOwner if has one");
      }

   }

   private void undoUnactivatedChanges(DeploymentRequestInfo requestInfo) {
      if (!requestInfo.isControlOperation()) {
         long requestId = requestInfo.request.getId();
         boolean editLockOwner = requestInfo.ownsEditLock;
         AuthenticatedSubject initiator = requestInfo.request.getInitiator();
         if (initiator != null && !editLockOwner) {
            try {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("DeploymentManager: undoUnactivatedChanges for request: " + requestId);
               }

               requestInfo.getEditAccessHelper().undoUnactivatedChanges(initiator);
            } catch (Throwable var15) {
               Debug.deploymentLogger.debug("Failed to undo unactivated changes on a failed deployment of request id: " + requestId + " due to " + StackTraceUtils.throwable2StackTrace(var15));
            } finally {
               try {
                  requestInfo.getEditAccessHelper().stopEditSession(initiator);
               } catch (Exception var14) {
                  Debug.deploymentLogger.debug("Failed to stop edit session on a failed deployment of request id: " + requestId + " due to " + StackTraceUtils.throwable2StackTrace(var14));
               }

            }
         } else if (initiator != null) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: rollbackFailedTasks for request: " + requestId);
            }

            this.rollBackFailedTasks(requestId, initiator);
         }

         this.invalidateCache(requestInfo);
      }
   }

   private void rollBackFailedTasks(long requestId, AuthenticatedSubject initiator) {
      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         synchronized(requestInfo) {
            DeploymentRequest request = requestInfo.request;
            Iterator iterator = request.getDeployments("Application");

            while(iterator.hasNext()) {
               Deployment deployment = (Deployment)iterator.next();
               DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
               AbstractOperation op = taskRuntime.getAdminOperation();
               if (op != null) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("DeploymentManager: rollback for task " + taskRuntime.getId());
                  }

                  op.rollback(initiator);
               }
            }

         }
      }
   }

   private void invalidateCache(DeploymentRequestInfo requestInfo) {
      if (requestInfo != null) {
         synchronized(requestInfo) {
            DeploymentRequest request = requestInfo.request;
            Iterator iterator = request.getDeployments("Application");

            while(iterator.hasNext()) {
               Deployment deployment = (Deployment)iterator.next();
               DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
               if (taskRuntime != null) {
                  AppDeploymentMBean mbean = taskRuntime.getAppDeploymentMBean();
                  if (mbean != null) {
                     SourceCache.invalidateCache(mbean);
                  }
               }
            }

         }
      }
   }

   public Deployment[] getDeployments(Version fromVersion, Version toVersion, String serverName, String partitionName) {
      Deployment deployment = new Deployment();
      deployment.setProposedVersion(getAdminServerAggregateDeploymentsVersion());
      deployment.setCallbackHandlerId(driver.getHandlerIdentity());
      deployment.enableSyncWithAdmin(AppRuntimeStateManager.getManager().getStartupStateForServer(serverName, partitionName));
      Deployment[] result = new Deployment[]{deployment};
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: getDeployments from '" + serverName + "' returning '" + Arrays.toString(result) + "'");
      }

      return result;
   }

   public void deploymentRequestCommitFailed(long requestId, FailureDescription[] failures) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Received 'commit failure' for request id '" + requestId + "''");
      }

      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         synchronized(requestInfo) {
            this.updateTasksWithDeploymentStatus(requestId, failures, 2, true);
         }
      }
   }

   public void deploymentRequestCommitSucceeded(long requestId) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Received 'commit success' for request id '" + requestId + "''");
      }

      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         synchronized(requestInfo) {
            this.updateTasksWithDeploymentStatus(requestId, (FailureDescription[])null, 3, true);
         }
      }
   }

   public void deploymentRequestCancelSucceeded(long requestId, FailureDescription[] failures) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Request id '" + requestId + "' cancel succeeded");
      }

      this.updateTaskWithCancelState(requestId, 8);
   }

   public void deploymentRequestCancelFailed(long requestId, DeploymentException reason, FailureDescription[] failures) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Request id '" + requestId + "' cancel failed due to '" + reason.toString());
      }

      this.updateTaskWithCancelState(requestId, 4);
   }

   public void handleReceivedStatus(long requestId, DeploymentState deploymentStatus, String serverName) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: handleReceivedStatus for: " + deploymentStatus + " from " + serverName + " for request: " + requestId);
      }

      DeploymentTaskRuntime taskRuntime = this.getDeploymentTaskRuntime(deploymentStatus);
      if (taskRuntime == null) {
         if (deploymentStatus != null) {
            if ("__Lifecycle_taskid__".equals(deploymentStatus.getTaskID())) {
               Debug.deploymentLogger.debug("DeploymentManager: handleReceivedStatus received status " + deploymentStatus + " for lifecycle task from " + serverName);
               this.updateRuntimeState(deploymentStatus);
            } else {
               Deployment dep = (Deployment)taskRuntimeToDeploymentTable.get(deploymentStatus.getTaskID());
               if (dep != null) {
                  taskRuntime = dep.getDeploymentTaskRuntime();
               }

               if (taskRuntime != null) {
                  this.updateRuntimeState(deploymentStatus);
               } else {
                  Debug.deploymentLogger.debug("DeploymentManager: handleReceivedStatus ignoring received status " + deploymentStatus + " for nonexisting task from " + serverName);
                  this.updateRuntimeState(deploymentStatus);
               }
            }
         }

      } else {
         this.updateStatus(requestId, taskRuntime, serverName, deploymentStatus);
      }
   }

   private DeploymentTaskRuntime getDeploymentTaskRuntime(DeploymentState deploymentStatus) {
      DeploymentTaskRuntime taskRuntime = null;
      if (deploymentStatus != null) {
         taskRuntime = this.getDeploymentTaskRuntime(deploymentStatus.getTaskID());
      }

      return taskRuntime;
   }

   private DeploymentTaskRuntime getDeploymentTaskRuntime(String id) {
      return (DeploymentTaskRuntime)deployerRuntime.query(id);
   }

   public void startDeploymentTask(DeploymentTaskRuntime taskToBeStarted, String displayName) throws ManagementException {
      if (taskToBeStarted != null) {
         Deployment deployment = (Deployment)taskRuntimeToDeploymentTable.get(taskToBeStarted.getId());
         if (deployment == null) {
            deployment = (Deployment)this.pendingControlDeployments.remove(taskToBeStarted.getId());
            if (deployment == null) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("No deployment object available for task with id: " + taskToBeStarted.getId());
               }

               return;
            }
         }

         deployment.setNotificationLevel(taskToBeStarted.getNotificationLevel());
         long requestId = deployment.getDeploymentRequestId();
         DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
         if (!taskToBeStarted.isAControlOperation() && deployment.isCallerLockOwner()) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Caller owns the edit lock for the task with id : " + taskToBeStarted.getId());
            }

         } else if (requestInfo == null || requestInfo.request == null || !requestInfo.request.isEnqueued()) {
            try {
               DeploymentRequestTaskRuntimeMBean parentTask = (DeploymentRequestTaskRuntimeMBean)taskToBeStarted.getMyParent();
               if (parentTask != null) {
                  if (!parentTask.getDeploymentRequest().isEnqueued()) {
                     parentTask.start();
                  }
               } else if (taskToBeStarted.isAControlOperation()) {
                  this.startDeploymentForControlOperation(taskToBeStarted, deployment);
               } else {
                  throw new AssertionError("DeploymentTaskRuntime: " + this + " does not have an associated parent DeploymentRequestTaskRuntime");
               }
            } catch (Exception var12) {
               ManagementException me = var12 instanceof ManagementException ? (ManagementException)var12 : new ManagementException(var12.getMessage(), var12);
               AuthenticatedSubject initiator = null;
               if (requestInfo != null && requestInfo.request != null) {
                  initiator = requestInfo.request.getInitiator();
               }

               EditAccessHelper editAccessHelper = deployment.getEditAccessHelper();
               if (editAccessHelper == null) {
                  editAccessHelper = EditAccessHelper.getInstance(kernelId);
               }

               this.deploymentFailedBeforeStart(deployment, me, deployment.isCallerLockOwner(), initiator, editAccessHelper, taskToBeStarted.isAControlOperation());
               String logMessage = "An exception occurred while executing task " + taskToBeStarted.getDescription() + " for application " + displayName + ":" + StackTraceUtils.throwable2StackTrace(me);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug(logMessage);
               }

               throw me;
            }
         }
      }
   }

   public final void deploymentFailedBeforeStart(Deployment deployment, Throwable t, boolean callerIsEditLockOwner, AuthenticatedSubject initiator, EditAccessHelper editAccessHelper, boolean controlOperation) {
      beanFactory.resetDeployerInitiatedBeanUpdates();
      this.deploymentFailedBeforeStart(deployment, t);
      abortSessionBeforeStart(initiator, editAccessHelper, callerIsEditLockOwner, controlOperation);
      if (deployment != null) {
         String name = deployment.getInternalDeploymentData().getDeploymentName();
         int taskType = deployment.getInternalDeploymentData().getDeploymentOperation();
         OperationHelper.logTaskFailed(name, taskType);
      }
   }

   public static final void deploymentRequestCancelledBeforeStart(DeploymentRequest request) {
      if (request != null) {
         long requestId = request.getId();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Request id '" + requestId + "' cancelled before start");
         }

         DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
         if (requestInfo != null) {
            synchronized(requestInfo) {
               boolean editLockOwner = requestInfo.ownsEditLock;
               AuthenticatedSubject initiator = requestInfo.request.getInitiator();
               boolean controlOperation = requestInfo.isControlOperation();
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("calling abortEditSessionBeforeStart() for requestId : " + requestId);
               }

               abortSessionBeforeStart(initiator, requestInfo.getEditAccessHelper(), editLockOwner, controlOperation);
               Iterator iterator = requestInfo.request.getDeployments("Application");

               while(iterator.hasNext()) {
                  Deployment deployment = (Deployment)iterator.next();
                  DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
                  if (taskRuntime != null && !taskRuntime.isComplete()) {
                     taskRuntime.setCancelState(8);
                  }
               }

            }
         }
      }
   }

   public final void deploymentFailedBeforeStart(Deployment deployment, Throwable t) {
      if (deployment != null) {
         AdminDeploymentException deploymentException = new AdminDeploymentException();
         FailureDescription failure = new FailureDescription("adminServer", new Exception(t.toString()), OperationHelper.getTaskString(deployment.getInternalDeploymentData().getDeploymentOperation()));
         deploymentException.addFailureDescription(failure);
         DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(deployment.getDeploymentRequestId());
         if (requestInfo != null) {
            this.deploymentRequestFailedBeforeStart(requestInfo, deploymentException);
         } else {
            DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
            if (taskRuntime == null) {
               return;
            }

            this.pendingControlDeployments.remove(taskRuntime.getId());
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: Removed task '" + taskRuntime.getId() + "' from pendingControlDeployments if has one");
            }

            this.pendingDeploymentsForLockOwner.remove(deployment);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: Removed entry: '" + deployment + "' from pendingDeploymentsForLockOwner if has one");
            }

            this.pendingDeploymentsForLockAcquirer.remove(deployment);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: Removed deployment '" + deployment + "' from pendingDeploymentsForLockAcquirer if has one");
            }

            FailureDescription[] failures = deploymentException.getFailures();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: Updating task '" + taskRuntime.getId() + "' with failures : " + Arrays.toString(failures));
            }

            updateTaskWithFailures(taskRuntime, failures, 2);
            taskRuntime.handleFailure();
            this.reset(taskRuntime.getId());
         }

      }
   }

   public final List getExistingOperationsOnSameApp(AbstractOperation theOp) {
      List results = new ArrayList();
      DeploymentTaskRuntime taskRuntime = theOp.getTaskRuntime();
      BasicDeploymentMBean theAppMBean = taskRuntime.getDeploymentMBean();
      synchronized(this.pendingDeploymentsForLockOwner) {
         Iterator var6 = this.pendingDeploymentsForLockOwner.iterator();

         while(var6.hasNext()) {
            Deployment eachDep = (Deployment)var6.next();
            DeploymentTaskRuntime found = eachDep.getDeploymentTaskRuntime();
            if (found != null) {
               BasicDeploymentMBean foundMBean = found.getDeploymentMBean();
               if (foundMBean != null && theAppMBean != null && foundMBean.getName().equals(theAppMBean.getName())) {
                  AbstractOperation foundOp = found.getAdminOperation();
                  if (foundOp != null) {
                     results.add(foundOp);
                  }
               }
            }
         }

         return results;
      }
   }

   public final List getExistingOperationsOnApp(String appId) {
      List results = new ArrayList();
      synchronized(this.pendingDeploymentsForLockOwner) {
         Iterator var4 = this.pendingDeploymentsForLockOwner.iterator();

         while(var4.hasNext()) {
            Deployment eachDep = (Deployment)var4.next();
            DeploymentTaskRuntime found = eachDep.getDeploymentTaskRuntime();
            if (found != null) {
               BasicDeploymentMBean foundMBean = found.getDeploymentMBean();
               if (foundMBean != null && foundMBean.getName().equals(appId)) {
                  AbstractOperation foundOp = found.getAdminOperation();
                  if (foundOp != null) {
                     results.add(foundOp);
                  }
               }
            }
         }

         return results;
      }
   }

   public final void removeDeploymentsForTasks(List removalTasks) {
      if (removalTasks != null && !removalTasks.isEmpty()) {
         Iterator var2 = removalTasks.iterator();

         while(var2.hasNext()) {
            Object removalTask = var2.next();
            String theTaskId = (String)removalTask;
            synchronized(this.pendingDeploymentsForLockOwner) {
               Deployment dep = this.findDeploymentForTask(theTaskId);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Found deployment for '" + theTaskId + "' : " + dep);
               }

               if (dep != null) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Removing deployment for '" + theTaskId + "' : " + dep);
                  }

                  this.pendingDeploymentsForLockOwner.remove(dep);
               }
            }

            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Removing task '" + theTaskId + "' and it's corresponding deployments...");
            }

            taskRuntimeToDeploymentTable.remove(theTaskId);
            taskRuntimeToDeploymentInfoTable.remove(theTaskId);
         }

      }
   }

   private synchronized Deployment findDeploymentForTask(String theTaskId) {
      Iterator var2 = this.pendingDeploymentsForLockOwner.iterator();

      Deployment dep;
      DeploymentTaskRuntime depTask;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         dep = (Deployment)var2.next();
         depTask = dep.getDeploymentTaskRuntime();
      } while(depTask == null || !depTask.getId().equals(theTaskId));

      return dep;
   }

   private void startDeploymentForControlOperation(DeploymentTaskRuntime taskToBeStarted, Deployment deployment) throws ManagementException {
      if (taskToBeStarted != null && deployment != null) {
         String[] deployTargets = deployment.getTargets();
         if (deployTargets != null && deployTargets.length != 0) {
            DeploymentRequest deploymentRequest = driver.createDeploymentRequest();
            List deployments = new ArrayList();
            deployments.add(deployment);
            List[] deploymentsToBeUpdated = new List[]{deployments};
            this.updateRequestAndDeployments(deploymentRequest, deploymentsToBeUpdated);
            DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(deploymentRequest.getId());
            requestInfo.setControlOperation();
            deploymentRequest.getTaskRuntime().start();
         } else {
            taskToBeStarted.updateTargetStatus((String)null, 3, (Exception)null);
         }
      }
   }

   private void resetDeploymentState(long requestId) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Resetting deployment(s) with request id: " + requestId);
      }

      synchronized(requestInfoTable) {
         Long requestIdValue = requestId;
         if (requestInfoTable.containsKey(requestIdValue)) {
            removedRequestInfoCache.put(requestIdValue, requestInfoTable.remove(requestIdValue));
         }

      }
   }

   private void reset(String taskId) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Resetting deployment task ref " + taskId);
      }

      taskRuntimeToDeploymentTable.remove(taskId);
      DeploymentRequestInfo requestInfo = (DeploymentRequestInfo)taskRuntimeToDeploymentInfoTable.remove(taskId);
      if (requestInfo != null) {
         synchronized(requestInfo) {
            requestInfo.removeTask(taskId);
            if (requestInfo.removedAllTasks()) {
               this.resetDeploymentState(requestInfo.request.getId());
            }

         }
      }
   }

   private static void addOrUpdateAdminServerAggregateDeploymentVersion(String identity, DeploymentVersion version) {
      getAdminServerAggregateDeploymentsVersion().addOrUpdateDeploymentVersion(identity, version);
   }

   private void updateAdminServerRuntimeStateAndAggregateDeploymentVersion(DeploymentRequestInfo requestInfo) {
      Iterator iterator = requestInfo.request.getDeployments("Application");

      while(true) {
         Deployment deployment;
         do {
            do {
               if (!iterator.hasNext()) {
                  return;
               }

               deployment = (Deployment)iterator.next();
            } while(!deployment.isAnAppDeployment());
         } while(deployment.isAControlOperation());

         int task = deployment.getOperation();
         boolean isADeploy = task != 4 && task != 12;
         if (isADeploy) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Updating runtime state and aggregate deployment version component for " + deployment.getIdentity());
            }

            boolean needsDeploymentVersionUpdate = !requestInfo.request.isControlRequest();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Needs DeploymentVersion Update : " + needsDeploymentVersionUpdate);
            }

            if (needsDeploymentVersionUpdate) {
               updateRuntimeState(deployment, deployment.getProposedDeploymentVersion());
               addOrUpdateAdminServerAggregateDeploymentVersion(deployment.getIdentity(), deployment.getProposedDeploymentVersion());
            }
         } else {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Removing runtime state and aggregate deployment version component for " + deployment.getIdentity());
            }

            getAdminServerAggregateDeploymentsVersion().removeDeploymentVersionFor(deployment.getIdentity());
         }
      }
   }

   private Deployment createDeployment(DeploymentTaskRuntime task, DeploymentData data, DomainMBean editableDomain, AuthenticatedSubject initiator, boolean callerIsLockOwner, boolean aControlOperation, boolean requiresRestart) {
      Deployment deployment = new Deployment(driver.getHandlerIdentity(), task, data, initiator, callerIsLockOwner, aControlOperation, requiresRestart);
      TargetStatus[] targets = task.getTargets();
      if (targets != null) {
         TargetStatus[] var10 = targets;
         int var11 = targets.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            TargetStatus target = var10[var12];
            String targetName = target.getTarget();
            String[] serverNames = getServerNames(targetName, editableDomain);
            String[] var16 = serverNames;
            int var17 = serverNames.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               String serverName = var16[var18];
               deployment.addTarget(serverName);
            }

            DomainMBean rtDomain = ManagementService.getRuntimeAccess(kernelId).getDomain();
            String[] rtServerNames = getServerNames(targetName, rtDomain);
            String[] var24 = rtServerNames;
            int var25 = rtServerNames.length;

            for(int var20 = 0; var20 < var25; ++var20) {
               String rtServerName = var24[var20];
               deployment.addTarget(rtServerName);
            }
         }
      }

      return deployment;
   }

   private DeploymentVersion createDeploymentVersion(String id, boolean configChange, boolean isControlOperation) {
      DeploymentVersion version = null;
      Map versionContentsMap = getAdminServerAggregateDeploymentsVersion().getVersionComponents();
      if (versionContentsMap.size() > 0) {
         version = (DeploymentVersion)versionContentsMap.get(id);
      }

      long archiveTimeStamp = DeploymentVersion.createTimeStamp();
      long planTimeStamp = archiveTimeStamp;
      if (version != null) {
         archiveTimeStamp = version.getArchiveTimeStamp();
         planTimeStamp = version.getPlanTimeStamp();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentManager: create deployment version for id: " + id + " version: " + version + " archive ts: " + archiveTimeStamp + " plan ts: " + planTimeStamp);
         }
      } else if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: create deployment version for id: " + id + " archive ts: " + archiveTimeStamp + " plan ts: " + archiveTimeStamp);
      }

      DeploymentVersion newVersion = new DeploymentVersion(id, archiveTimeStamp, planTimeStamp);
      newVersion.update(configChange, isControlOperation);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: update version: " + version);
      }

      return newVersion;
   }

   private ChangeDescriptor createChangeDescriptor(Serializable taskId, DeploymentVersion newVersion) {
      return driver.createChangeDescriptor(taskId, newVersion);
   }

   private static AggregateDeploymentVersion getAdminServerAggregateDeploymentsVersion() {
      if (adminServerAggregateDeploymentVersion == null) {
         adminServerAggregateDeploymentVersion = AggregateDeploymentVersion.createAggregateDeploymentVersion();
      }

      return adminServerAggregateDeploymentVersion;
   }

   static DeploymentManager getDeploymentManagerForDeployment(long deploymentIdentifier) {
      DeploymentRequestInfo reqInfo = (DeploymentRequestInfo)requestInfoTable.get(deploymentIdentifier);
      return reqInfo == null ? DeploymentManager.Maker.INSTANCE : reqInfo.getDeploymentManager();
   }

   private static String[] getServerNames(String target, DomainMBean editableDomain) {
      if (editableDomain != null) {
         TargetMBean t = editableDomain.lookupInAllTargets(target);
         Set serverNames = new HashSet();
         if (t != null) {
            serverNames = t.getServerNames();
         }

         return (String[])((String[])((Set)serverNames).toArray(new String[((Set)serverNames).size()]));
      } else {
         return new String[0];
      }
   }

   private static boolean isAlreadyTargeted(String deploymentName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      AppDeploymentMBean app = domain.lookupAppDeployment(deploymentName);
      if (app != null) {
         if (app.getTargets() != null && app.getTargets().length > 0) {
            return true;
         }

         SubDeploymentMBean[] modules = app.getSubDeployments();
         if (modules != null && modules.length > 0) {
            SubDeploymentMBean[] var4 = modules;
            int var5 = modules.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               SubDeploymentMBean module = var4[var6];
               if (module.getTargets() != null && module.getTargets().length > 0) {
                  return true;
               }

               SubDeploymentMBean[] subModules = module.getSubDeployments();
               if (subModules != null && subModules.length > 0) {
                  SubDeploymentMBean[] var9 = subModules;
                  int var10 = subModules.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     SubDeploymentMBean subModule = var9[var11];
                     if (subModule.getTargets() != null && subModule.getTargets().length > 0) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private static boolean isRedeployAConfigChange(AppDeploymentMBean mbean, DeploymentData deploymentData) {
      boolean hasDelta = deploymentData != null && (deploymentData.hasFiles() || deploymentData.hasSubModuleTargets());
      return !hasDelta || !isAlreadyTargeted(mbean.getName());
   }

   private static boolean isAConfigurationChange(AppDeploymentMBean mbean, DeploymentData info, int operation) {
      boolean result = false;
      switch (operation) {
         case 1:
         case 4:
         case 6:
         case 11:
         case 12:
            result = true;
            break;
         case 2:
         case 3:
         case 5:
         case 7:
         case 8:
         case 10:
         default:
            result = false;
            break;
         case 9:
            result = isRedeployAConfigChange(mbean, info);
      }

      return result;
   }

   private static void updateTaskWithFailures(DeploymentTaskRuntime task, FailureDescription[] fds, int status) {
      if (fds != null && fds.length > 0) {
         FailureDescription[] var3 = fds;
         int var4 = fds.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FailureDescription fd = var3[var5];
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentManager: updateTaskWithFailures:  for server: " + fd.getServer() + " task: " + task.getId() + " with status: " + status);
            }

            task.updateTargetStatus(fd.getServer(), status, fd.getReason());
         }
      }

   }

   private void updateTaskWithCancelState(long requestId, int status) {
      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         synchronized(requestInfo) {
            if (status == 8) {
               this.undoUnactivatedChanges(requestInfo);
            }

            Iterator iterator = requestInfo.request.getDeployments("Application");

            while(true) {
               DeploymentTaskRuntime taskRuntime;
               do {
                  do {
                     if (!iterator.hasNext()) {
                        return;
                     }

                     Deployment deployment = (Deployment)iterator.next();
                     taskRuntime = deployment.getDeploymentTaskRuntime();
                  } while(taskRuntime == null);

                  taskRuntime.setCancelState(status);
               } while(status != 8 && status != 4);

               this.reset(taskRuntime.getId());
            }
         }
      }
   }

   private void updateStatus(long requestId, DeploymentTaskRuntime taskRuntime, String serverName, DeploymentState deploymentStatus) {
      sendNotifications(deploymentStatus, taskRuntime);
      if (this.updateTaskStatus(requestId, taskRuntime, serverName, deploymentStatus)) {
         this.updateRuntimeState(deploymentStatus);
      }

   }

   private boolean updateTaskStatus(long requestId, DeploymentTaskRuntime taskRuntime, String serverName, DeploymentState deploymentStatus) {
      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId, true);
      if (requestInfo == null) {
         taskRuntime.updateTargetStatus(serverName, deploymentStatus.getTaskState(), deploymentStatus.getException());
         return true;
      } else {
         synchronized(requestInfo) {
            return requestInfo.updateDeploymentStatus(taskRuntime.getId(), serverName, new DeploymentTargetStatus(requestId, deploymentStatus));
         }
      }
   }

   private static void sendNotifications(DeploymentState deploymentStatus, DeploymentTaskRuntime taskRuntime) {
      try {
         NotificationBroadcaster.sendNotificationsFromManagedServer(deploymentStatus, taskRuntime, Debug.deploymentLogger);
      } catch (Throwable var3) {
         Debug.deploymentLogger.debug("Error during send Notification for " + taskRuntime.getApplicationName());
         var3.printStackTrace();
      }

   }

   private void updateTaskStatusOfRestartTargets(long requestId, DeploymentTaskRuntime taskRuntime, String[] targetsToBeRestarted) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: updateTasksWithRestartTargetStatus for request: " + requestId);
      }

      if (targetsToBeRestarted != null && targetsToBeRestarted.length > 0) {
         String msg = DeployerRuntimeLogger.requiresRestart();
         String[] var6 = targetsToBeRestarted;
         int var7 = targetsToBeRestarted.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String aTargetsToBeRestarted = var6[var8];
            taskRuntime.updateTargetStatus(aTargetsToBeRestarted, 3, new Exception(msg));
         }
      }

   }

   private void updateTasksWithAccumulatedStatus(long requestId) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentManager: updateTasksWithAccumulatedStatus for request: " + requestId);
      }

      DeploymentRequestInfo requestInfo = getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         synchronized(requestInfo) {
            Iterator var5 = requestInfo.deploymentsStatus.keySet().iterator();

            while(var5.hasNext()) {
               Object o = var5.next();
               String taskRuntimeId = (String)o;
               Map taskStatusMap = requestInfo.getDeploymentStatus(taskRuntimeId);
               Iterator var9 = taskStatusMap.keySet().iterator();

               while(var9.hasNext()) {
                  Object o1 = var9.next();
                  String serverName = (String)o1;
                  DeploymentTargetStatus targetStatus = (DeploymentTargetStatus)taskStatusMap.get(serverName);
                  DeploymentTaskRuntime taskRuntime = this.getDeploymentTaskRuntime(taskRuntimeId);
                  if (taskRuntime != null) {
                     taskRuntime.updateTargetStatus(serverName, targetStatus.deploymentStatus.getTaskState(), targetStatus.deploymentStatus.getException());
                  }
               }
            }

         }
      }
   }

   private static void updateRuntimeState(Deployment deployment, DeploymentVersion version) {
      String appId = ApplicationVersionUtils.getApplicationId(deployment.getDeploymentTaskRuntime().getAppDeploymentMBean());
      if (appId != null && version != null && !"?".equals(appId)) {
         try {
            synchronized(AppRuntimeStateManager.getManager()) {
               AppRuntimeStateManager.getManager().updateState(appId, version);
            }
         } catch (NullPointerException var6) {
            var6.printStackTrace();
         }

      }
   }

   private void updateRuntimeState(DeploymentState deploymentStatus) {
      if (deploymentStatus != null) {
         try {
            String appId = deploymentStatus.getId();
            if (appId == null || "?".equals(appId)) {
               return;
            }

            synchronized(AppRuntimeStateManager.getManager()) {
               DeploymentTaskRuntime taskRuntime = this.getDeploymentTaskRuntime(deploymentStatus);
               if (taskRuntime != null && taskRuntime.getState() > 1 && "STATE_UPDATE_PENDING".equals(deploymentStatus.getCurrentState())) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("DeploymentManager: Ignore update app runtime state to STATE_UPDATE_PENDING after task is completed");
                  }

                  return;
               }

               AppRuntimeStateManager mgr = AppRuntimeStateManager.getManager();
               ApplicationRuntimeState state = mgr.get(appId);
               mgr.updateState(appId, deploymentStatus);
               boolean updateRetireTimeNeeded = state != null && !state.isActiveVersion() && !state.markedForRetirement() && "STATE_ACTIVE".equals(state.getIntendedState(deploymentStatus.getTarget()));
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("DeploymentManager: RetireTimeMillis for application " + appId + " needs to be updated : " + updateRetireTimeNeeded);
               }

               if (updateRetireTimeNeeded || state == null && deploymentStatus.getCurrentState() == null) {
                  try {
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("DeploymentManager: Updating RetireTimeMillis to 0 for application " + appId);
                     }

                     mgr.setRetireTimeMillis(appId, 0L);
                  } catch (ManagementException var10) {
                     DeploymentManagerLogger.logStatePersistenceFailed(appId, var10);
                  }
               }
            }
         } catch (NullPointerException var12) {
            var12.printStackTrace();
         }

      }
   }

   private static void abortSessionBeforeStart(AuthenticatedSubject subject, EditAccessHelper editAccessHelper, boolean callerIsEditLockOwner, boolean controlOperation) {
      if (!controlOperation && subject != null && !callerIsEditLockOwner) {
         try {
            editAccessHelper.cancelActivateSession(subject);
            editAccessHelper.undoUnactivatedChanges(subject);
         } catch (ManagementException var9) {
            Loggable logger = DeployerRuntimeLogger.logErrorOnAbortEditSessionLoggable(subject.toString(), var9);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("abortEditSessionBeforeStart: Error occured while aborting the session : " + logger.getMessage());
            }

            logger.log();
         } finally {
            editAccessHelper.cancelEditSession(subject);
         }
      }

   }

   private boolean removeDeployIfNoop(Deployment deployment) {
      boolean removed = false;
      if (deployment != null && deployment.isAnAppDeployment() && deployment.getOperation() == 4 && !this.pendingDeploymentsForLockOwner.isEmpty()) {
         DomainMBean runtimeDomain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         AppDeploymentMBean appMBean = deployment.getDeploymentTaskRuntime().getAppDeploymentMBean();
         if (runtimeDomain != null && appMBean != null && runtimeDomain.lookupAppDeployment(appMBean.getName()) == null) {
            Iterator pendingDeployments = this.pendingDeploymentsForLockOwner.iterator();

            while(pendingDeployments.hasNext()) {
               Deployment eachDep = (Deployment)pendingDeployments.next();
               DeploymentTaskRuntime found = eachDep.getDeploymentTaskRuntime();
               if (found != null) {
                  AppDeploymentMBean foundMBean = found.getAppDeploymentMBean();
                  if (foundMBean != null && foundMBean.getName().equals(appMBean.getName())) {
                     removed = true;
                     pendingDeployments.remove();
                     eachDep.getDeploymentTaskRuntime().setState(2);
                  }
               }
            }

            if (removed) {
               deployment.getDeploymentTaskRuntime().setState(2);
            }

            return removed;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private List[] getConfigChangeDeployments(DeploymentRequest deploymentRequest, ConfigurationContext ctxt) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentLogger.debug("DeploymentManager: getConfigChangeDeployments, post processing after ConfigChangesHandler for request id: " + deploymentRequest.getId());
      }

      Deployment dep = (Deployment)this.pendingDeploymentsForLockAcquirer.get(0);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentLogger.debug("pendingDeploymentsForLockAcquirer is: " + this.pendingDeploymentsForLockAcquirer);
         Debug.deploymentLogger.debug("Original deployment is: " + dep);
         Debug.deploymentLogger.debug("Its id is: " + dep.getIdentity());
      }

      DeploymentTaskRuntime taskRuntime = dep.getDeploymentTaskRuntime();
      taskRuntime.setMyParent(deploymentRequest.getTaskRuntime());
      List[] deployments = ConfigChangesHandler.configChanged(deploymentRequest, ctxt, this);
      boolean isListEmpty = true;
      List[] var7 = deployments;
      int var8 = deployments.length;

      int var9;
      for(var9 = 0; var9 < var8; ++var9) {
         List list = var7[var9];
         if (list != null && !list.isEmpty()) {
            isListEmpty = false;
            Iterator var11 = list.iterator();

            while(var11.hasNext()) {
               Object aList = var11.next();
               Deployment depl = (Deployment)aList;
               String appName = ApplicationVersionUtils.getNonPartitionName(depl.getIdentity());
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentLogger.debug("Current processed deployment is: " + depl);
                  Debug.deploymentLogger.debug("Its name is: " + appName);
                  Debug.deploymentLogger.debug("Its id is: " + depl.getIdentity());
               }

               if (appName.equals(dep.getIdentity())) {
                  depl.setCallerLockOwner(dep.isCallerLockOwner());
                  depl.setEditAccessHelper(dep.getEditAccessHelper());
                  InternalDeploymentData internalDeploymentData = depl.getInternalDeploymentData();
                  if (internalDeploymentData != null) {
                     DeploymentData data = internalDeploymentData.getExternalDeploymentData();
                     if (data != null) {
                        data.setDeploymentOptions(taskRuntime.getDeploymentData().getDeploymentOptions());
                        if (data.getFiles() == null) {
                           data.setFile(taskRuntime.getDeploymentData().getFiles());
                        }
                     }

                     internalDeploymentData.setNotificationLevel(taskRuntime.getNotificationLevel());
                     internalDeploymentData.setDeploymentOperation(taskRuntime.getTask());
                  }

                  depl.setOperation(taskRuntime.getTask());
               }
            }
         }
      }

      if (isListEmpty) {
         String[] var17 = dep.getTargets();
         var8 = var17.length;

         for(var9 = 0; var9 < var8; ++var9) {
            String target = var17[var9];
            dep.removeTarget(target);
         }

         this.pendingDeploymentsForLockAcquirer.add(dep);
         deployments = new List[]{this.pendingDeploymentsForLockAcquirer};
      }

      return deployments;
   }

   public String getEditSessionPartitionName() {
      return this.editSessionPartitionName;
   }

   /** @deprecated */
   @Deprecated
   public String getPartitionName() {
      return this.getEditSessionPartitionName();
   }

   public String getEditSessionName() {
      return this.editSessionName;
   }

   public void removePendingUpdateTasks() {
      Collection deploymentsWithoutConfigChanges = this.getPendingDeploymentsForEditLockOwner();
      if (deploymentsWithoutConfigChanges != null && deploymentsWithoutConfigChanges.size() > 0) {
         List removalTaskIds = new ArrayList();
         Iterator iterator = deploymentsWithoutConfigChanges.iterator();

         while(iterator.hasNext()) {
            Deployment deployment = (Deployment)iterator.next();
            DeploymentTaskRuntime task = deployment.getDeploymentTaskRuntime();
            if (task != null && task.getState() == 0) {
               removalTaskIds.add(task.getId());
               task.remove();
            }
         }

         this.removeDeploymentsForTasks(removalTaskIds);
      }

   }

   // $FF: synthetic method
   DeploymentManager(Object x0) {
      this();
   }

   @Service
   private static class AdminServerDeploymentManagerServiceGeneratorImpl implements AdminServerDeploymentManagerServiceGenerator, AdminDeploymentRequestCancellerService {
      public AdminServerDeploymentManagerService createAdminServerDeploymentManager(AuthenticatedSubject subject) {
         return DeploymentManager.getInstance(subject);
      }

      public void deploymentRequestCancelledBeforeStart(DeploymentRequest request) {
         DeploymentManager.deploymentRequestCancelledBeforeStart(request);
      }

      public AdminServerDeploymentManagerService createAdminServerDeploymentManager(AuthenticatedSubject subject, String editSessionName) {
         return DeploymentManager.getInstance(subject, editSessionName);
      }

      public AdminServerDeploymentManagerService createAdminServerDeploymentManager(AuthenticatedSubject subject, String editSessionPartitionName, String editSessionName) {
         return DeploymentManager.getInstance(subject, editSessionPartitionName, editSessionName);
      }
   }

   private static class RequestInfoCacheMap extends LinkedHashMap {
      private RequestInfoCacheMap() {
      }

      protected boolean removeEldestEntry(Map.Entry eldest) {
         return this.size() > 3;
      }

      // $FF: synthetic method
      RequestInfoCacheMap(Object x0) {
         this();
      }
   }

   private static final class DeploymentRequestInfo {
      final DeploymentRequest request;
      final Map deploymentsStatus;
      boolean ownsEditLock;
      DomainMBean editableDomain;
      boolean controlOperation;
      private EditAccessHelper editAccessHelper;
      private Set tasks;
      private DeploymentManager deploymentManager;

      private DeploymentRequestInfo(DeploymentRequest request, DeploymentManager deploymentManager) {
         this.controlOperation = false;
         this.request = request;
         this.deploymentsStatus = new HashMap();
         this.tasks = new HashSet();
         this.deploymentManager = deploymentManager;
      }

      public DeploymentManager getDeploymentManager() {
         return this.deploymentManager;
      }

      private void setIsEditLockOwner(boolean isOwner) {
         this.ownsEditLock = isOwner;
      }

      private void setEditableDomain(DomainMBean editableDomain) {
         this.editableDomain = editableDomain;
      }

      public EditAccessHelper getEditAccessHelper() {
         return this.editAccessHelper;
      }

      public void setEditAccessHelper(EditAccessHelper editAccessHelper) {
         this.editAccessHelper = editAccessHelper;
      }

      private synchronized void addDeploymentStatusContainerFor(String taskId) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentRequestInfo: Created status containers for taskId: " + taskId + " associated with request: " + this.request.getId());
         }

         this.deploymentsStatus.put(taskId, new HashMap());
         if (this.tasks != null) {
            this.tasks.add(taskId);
         }

      }

      private synchronized void removeTask(String taskId) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentRequestInfo: Removing taskId: " + taskId + " associated with request: " + this.request.getId());
         }

         if (this.tasks != null && this.tasks.contains(taskId)) {
            this.tasks.remove(taskId);
            if (this.tasks.isEmpty()) {
               this.tasks = null;
            }
         }

      }

      private synchronized boolean removedAllTasks() {
         return this.tasks == null;
      }

      private synchronized boolean updateDeploymentStatus(String taskId, String statusSource, DeploymentTargetStatus status) {
         Map statusContainer = (HashMap)this.deploymentsStatus.get(taskId);
         if (statusContainer == null) {
            return true;
         } else {
            if (statusContainer.get(statusSource) != null) {
               DeploymentTargetStatus previousStat = (DeploymentTargetStatus)statusContainer.get(statusSource);
               if (!previousStat.isUpdatableTo(status)) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("DeploymentRequestInfo: ignore update STATE_UPDATE_PENDING since state is already set to a complete state by same target in same deploy request.");
                  }

                  return false;
               }
            }

            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("DeploymentRequestInfo: adding status update from: " + statusSource + " for task: " + taskId + " associated with request: " + this.request.getId());
            }

            statusContainer.put(statusSource, status);
            return true;
         }
      }

      private synchronized Map getDeploymentStatus(String taskId) {
         HashMap statusContainer = (HashMap)this.deploymentsStatus.get(taskId);
         return statusContainer == null ? null : (Map)statusContainer.clone();
      }

      private void setControlOperation() {
         this.controlOperation = true;
      }

      private boolean isControlOperation() {
         return this.controlOperation;
      }

      private long getRequestId() {
         return this.request.getId();
      }

      // $FF: synthetic method
      DeploymentRequestInfo(DeploymentRequest x0, DeploymentManager x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class DeploymentTargetStatus {
      final long requestId;
      final DeploymentState deploymentStatus;

      private DeploymentTargetStatus(long reqId, DeploymentState obj) {
         this.requestId = reqId;
         this.deploymentStatus = obj;
      }

      private boolean isUpdatableTo(DeploymentTargetStatus s) {
         String cur = this.deploymentStatus.getCurrentState();
         String toSet = s.deploymentStatus.getCurrentState();
         return this.requestId != s.requestId || !"STATE_UPDATE_PENDING".equals(toSet) && !"STATE_PREPARED".equals(toSet) || !"STATE_ACTIVE".equals(cur) && !"STATE_FAILED".equals(cur);
      }

      // $FF: synthetic method
      DeploymentTargetStatus(long x0, DeploymentState x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class Maker {
      static final DeploymentManager INSTANCE = new DeploymentManager();
   }
}
