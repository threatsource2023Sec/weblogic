package weblogic.deploy.internal.targetserver;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import weblogic.application.metadatacache.ClassesMetadataEntry;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.AggregateDeploymentVersion;
import weblogic.deploy.internal.Deployment;
import weblogic.deploy.internal.DeploymentType;
import weblogic.deploy.internal.DeploymentVersion;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.targetserver.operations.AbstractOperation;
import weblogic.deploy.internal.targetserver.operations.ActivateOperation;
import weblogic.deploy.internal.targetserver.operations.DynamicUpdateOperation;
import weblogic.deploy.internal.targetserver.operations.ExtendLoaderOperation;
import weblogic.deploy.internal.targetserver.operations.RedeployOperation;
import weblogic.deploy.internal.targetserver.operations.RemoveOperation;
import weblogic.deploy.internal.targetserver.operations.RetireOperation;
import weblogic.deploy.internal.targetserver.operations.StartOperation;
import weblogic.deploy.internal.targetserver.operations.StopOperation;
import weblogic.deploy.internal.targetserver.operations.UnprepareOperation;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.DeployerRuntimeExtendedLogger;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.parallel.Bucket;
import weblogic.management.deploy.internal.parallel.BucketInvoker;
import weblogic.management.deploy.internal.parallel.BucketSorter;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class DeploymentManager {
   private DeploymentServiceDispatcher dispatcher;
   private AggregateDeploymentVersion targetServerAggregateDeploymentVersion;
   private final String serverName;
   private final boolean isAdminServer;
   private Map requestInfoTable;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServerRuntimeMBean serverBean;

   private DeploymentManager() {
      this.serverName = ManagementService.getPropertyService(kernelId).getServerName();
      this.isAdminServer = ManagementService.getPropertyService(kernelId).isAdminServer();
      this.requestInfoTable = new HashMap();
   }

   public static DeploymentManager getInstance() {
      return DeploymentManager.Maker.MANAGER;
   }

   private void debug(String s) {
      Debug.deploymentDebug(s);
   }

   private boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public void initialize() {
      this.dispatcher = DeploymentServiceDispatcher.getInstance();
      this.targetServerAggregateDeploymentVersion = AggregateDeploymentVersion.createAggregateDeploymentVersion();
      this.dispatcher.initialize("Application", this.targetServerAggregateDeploymentVersion, this);
   }

   public void shutdown() {
      if (this.dispatcher != null) {
         this.dispatcher.shutdown();
      }

   }

   public void addOrUpdateTargetDeploymentVersion(String id, DeploymentVersion version) {
      if (!this.isAdminServer) {
         this.getTargetServerAggregateDeploymentsVersion().addOrUpdateDeploymentVersion(id, version);
      }

   }

   public void removeTargetDeploymentVersionFor(String id) {
      if (!this.isAdminServer) {
         this.getTargetServerAggregateDeploymentsVersion().removeDeploymentVersionFor(id);
      }

   }

   public void handleUpdateDeploymentContext(DeploymentContext context) {
      DeploymentRequest request = context.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = new DeploymentRequestInfo(requestId, context);
      synchronized(this.requestInfoTable) {
         label38: {
            try {
               if (this.requestInfoTable.get(new Long(requestId)) == null) {
                  this.requestInfoTable.put(new Long(requestId), requestInfo);
                  this.createOperations(context);
                  break label38;
               }

               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: prepare already received for request '" + requestId + "' - ignoring this request");
               }
            } catch (Throwable var9) {
               this.dispatcher.notifyContextUpdateFailed(requestId, var9);
               return;
            }

            return;
         }
      }

      if (this.isDebugEnabled()) {
         this.debug("DeploymentManagerT: notifying 'context updated' for request '" + requestId + "'");
      }

      this.dispatcher.notifyContextUpdated(requestId);
   }

   public void handlePrepare(DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: handlePrepare received for request '" + requestId + " that has already completed");
         }

         String msg = DeployerRuntimeLogger.requestCompletedOrCancelled(requestId);
         this.dispatcher.notifyPrepareFailure(requestId, new Exception(msg));
      } else {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: handlePrepare for request: " + requestId);
         }

         try {
            boolean hasAPreDeploymentHandlerDeployment = this.assignToPreOrPostDeploymentHandlerList(requestInfo);
            if (!hasAPreDeploymentHandlerDeployment) {
               if (requestInfo.postDeploymentHandlerList.size() > 0) {
                  if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT: handlePrepare handling 'postDeploymentHandlerList' of size " + requestInfo.postDeploymentHandlerList.size() + " for request '" + requestId + "'");
                  }

                  this.prepareDeploymentList(requestInfo.postDeploymentHandlerList, deploymentContext);
               }

               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: notifying 'prepare' success for request '" + requestId + "'");
               }

               this.dispatcher.notifyPrepareSuccess(requestId);
               return;
            }

            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: handlePrepare handling 'preDeploymentHandlerList' of size " + requestInfo.preDeploymentHandlerList.size() + " for request '" + requestId + "'");
            }

            if (deploymentContext.getContextComponent("calloutAbortedProposedConfig") == null) {
               this.prepareDeploymentList(requestInfo.preDeploymentHandlerList, deploymentContext);
               if (requestInfo.postDeploymentHandlerList.size() == 0) {
                  if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT: notifying 'prepare' success for request '" + requestId + "'");
                  }

                  this.dispatcher.notifyPrepareSuccess(requestId);
               } else if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: will await for 'configPrepareCompleted' callback to process 'prepare' of postDeploymentHandler list for request '" + requestId + "'");
                  return;
               }

               return;
            }

            if (this.isDebugEnabled()) {
               this.debug("handlePrepare(): Abort due to callout, dispatch notifyPrepareSuccess");
            }

            this.dispatcher.notifyPrepareSuccess(requestId);
         } catch (Throwable var10) {
            this.removeSystemResourcesFromRestartList(requestInfo);
            this.dispatcher.notifyPrepareFailure(requestId, var10);
            return;
         } finally {
            requestInfo.configPrepareCompletedLatch.countDown();
         }

      }
   }

   private boolean assignToPreOrPostDeploymentHandlerList(DeploymentRequestInfo requestInfo) {
      boolean isAPreDeploymentHandlerDeployment = false;
      DeploymentRequest request = requestInfo.context.getDeploymentRequest();
      Iterator iterator = request.getDeployments("Application");
      if (iterator != null) {
         while(true) {
            while(true) {
               while(iterator.hasNext()) {
                  Deployment deployment = (Deployment)iterator.next();
                  if (this.targetedToThisServer(deployment)) {
                     if (deployment.isBeforeDeploymentHandler() && deployment.isDeploy()) {
                        isAPreDeploymentHandlerDeployment = true;
                        if (this.isDebugEnabled()) {
                           this.debug("DeploymentManagerT: adding '" + deployment + "' to pre  DeploymentHandler list for request '" + request.getId() + "'");
                        }

                        requestInfo.preDeploymentHandlerList.add(deployment);
                     } else {
                        if (this.isDebugEnabled()) {
                           this.debug("DeploymentManagerT: adding '" + deployment + "' to post  DeploymentHandler list for request '" + request.getId() + "'");
                        }

                        requestInfo.postDeploymentHandlerList.add(deployment);
                     }
                  } else if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT: application deployment: " + deployment + " of request id: " + request.getId() + " not targeted on  this server");
                  }
               }

               return isAPreDeploymentHandlerDeployment;
            }
         }
      } else {
         return isAPreDeploymentHandlerDeployment;
      }
   }

   private DeploymentRequestInfo getDeploymentRequestInfo(long requestId) {
      synchronized(this.requestInfoTable) {
         return (DeploymentRequestInfo)this.requestInfoTable.get(new Long(requestId));
      }
   }

   public void handleCommit(DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: handleCommit received for request '" + requestId + " that has already completed");
         }

         String msg = DeployerRuntimeLogger.requestCompletedOrCancelled(requestId);
         this.notifyCommitFailure(requestId, (DeploymentRequestInfo)null, new Exception(msg));
      } else {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: handleCommit for request: " + requestId);
         }

         boolean isAPreDeploymentHandlerDeployment = false;
         Iterator iterator = null;

         try {
            iterator = request.getDeployments("Application");
            if (iterator == null) {
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: handleCommit - no deployments to 'commit' - notifying 'commit' success for request '" + requestId + "'");
               }

               this.notifyCommitSuccess(requestId, requestInfo);
            } else {
               isAPreDeploymentHandlerDeployment = this.processDeploymentsForCommit(iterator, request);
               if (isAPreDeploymentHandlerDeployment) {
                  if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT: handleCommit handling 'preDeploymentHandlerList' of size " + requestInfo.preDeploymentHandlerList.size() + " for request '" + requestId + "'");
                  }

                  this.activateDeploymentList(requestInfo.preDeploymentHandlerList, requestInfo);
                  if (requestInfo.postDeploymentHandlerList.size() == 0) {
                     if (this.isDebugEnabled()) {
                        this.debug("DeploymentManagerT: notifying 'commit' success for request '" + requestId + "'");
                     }

                     this.notifyCommitSuccess(requestId, requestInfo);
                  } else if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT: will await for 'configCommitCompleted' callback to process 'commit' of postDeploymentHandler list for request '" + requestId + "'");
                  }
               } else {
                  if (requestInfo.postDeploymentHandlerList.size() > 0) {
                     if (this.isDebugEnabled()) {
                        this.debug("DeploymentManagerT: handleCommit handling 'postDeploymentHandlerList' of size " + requestInfo.postDeploymentHandlerList.size() + " for request '" + requestId + "'");
                     }

                     this.activateDeploymentList(requestInfo.postDeploymentHandlerList, requestInfo);
                  }

                  if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT: notifying 'commit' success for request '" + requestId + "'");
                  }

                  this.notifyCommitSuccess(requestId, requestInfo);
               }
            }
         } catch (Throwable var12) {
            this.notifyCommitFailure(requestId, requestInfo, var12);
         } finally {
            requestInfo.configCommitCompletedLatch.countDown();
         }

      }
   }

   private final ServerRuntimeMBean getServerBean() {
      if (this.serverBean == null) {
         this.serverBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      }

      return this.serverBean;
   }

   private boolean requiresRestart(BasicDeploymentMBean deploymentMBean) {
      if (deploymentMBean == null) {
         return false;
      } else if (this.getServerBean().isRestartPendingForSystemResource(deploymentMBean.getName())) {
         return true;
      } else if (deploymentMBean instanceof SystemResourceMBean && DeployHelper.isMBeanInPartitionScope(deploymentMBean)) {
         PartitionMBean partition = DeployHelper.findContainingPartitionMBean(deploymentMBean);
         String partitionName = partition.getName();
         PartitionRuntimeMBean partitionRuntimeMBean = this.getServerBean().lookupPartitionRuntime(partitionName);
         return partitionRuntimeMBean.isRestartPendingForSystemResource(deploymentMBean.getName());
      } else {
         return false;
      }
   }

   private boolean processDeploymentsForCommit(Iterator iterator, DeploymentRequest request) {
      boolean isAPreDeploymentHandler = false;

      while(iterator.hasNext()) {
         Deployment deployment = (Deployment)iterator.next();
         if (this.targetedToThisServer(deployment)) {
            if (deployment.isBeforeDeploymentHandler() && deployment.isDeploy()) {
               isAPreDeploymentHandler = true;
            }
         } else {
            String taskRuntimeId = deployment.getDeploymentTaskRuntimeId();
            if (taskRuntimeId != null) {
               this.sendTaskCompletedNotification(request.getId(), taskRuntimeId);
            }
         }
      }

      return isAPreDeploymentHandler;
   }

   public void handleCancel(DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         this.notifyCancelSuccess(requestId, requestInfo);
      } else {
         try {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: handleCancel for request: " + request.getId());
            }

            this.removeSystemResourcesFromRestartList(requestInfo);
            Iterator iterator = request.getDeployments("Application");
            if (iterator != null) {
               while(iterator.hasNext()) {
                  Deployment dep = (Deployment)iterator.next();
                  this.handleDeploymentCancel(dep, requestInfo);
               }
            }

            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: notifying 'cancel' success for request '" + requestId + "'");
            }

            this.notifyCancelSuccess(requestId, requestInfo);
         } catch (Throwable var8) {
            this.notifyCancelFailure(requestId, requestInfo, var8);
         }

      }
   }

   public void relayStatus(long deploymentId, DeploymentState statusObject) {
      if (this.isDebugEnabled()) {
         this.debug("DeploymentManagerT: notifying status '" + statusObject + "' for  request '" + deploymentId + "'");
      }

      statusObject.setRelayTime(System.currentTimeMillis());
      this.dispatcher.notifyStatusUpdate(deploymentId, statusObject);
   }

   public void configPrepareCompleted(DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         try {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: configPrepareCompleted about to await " + request.getTimeoutInterval() + " millis for request '" + requestId + "'");
            }

            requestInfo.configPrepareCompletedLatch.await(request.getTimeoutInterval(), TimeUnit.MILLISECONDS);
         } catch (InterruptedException var7) {
         }

         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: configPrepareCompleted for request '" + requestId + "'");
         }

         try {
            if (requestInfo.preDeploymentHandlerList.size() > 0 && requestInfo.postDeploymentHandlerList.size() > 0) {
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: configPrepareCompleted handling 'postDeploymentHandlerList' of size " + requestInfo.postDeploymentHandlerList.size() + " for request '" + requestId + "'");
               }

               this.prepareDeploymentList(requestInfo.postDeploymentHandlerList, deploymentContext);
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: notifying 'prepare' success for request '" + requestId + "'");
               }

               this.dispatcher.notifyPrepareSuccess(requestId);
            }
         } catch (Throwable var8) {
            if (this.isDebugEnabled()) {
               Debug.deploymentLogger.debug("DeploymentManagerT: configPrepareCompleted encountered an exception: " + StackTraceUtils.throwable2StackTrace(var8) + " for request '" + requestId + "' notifying 'prepare' failure");
            }

            this.removeSystemResourcesFromRestartList(requestInfo);
            this.dispatcher.notifyPrepareFailure(requestId, new Exception(var8));
         }

      }
   }

   public void configCommitCompleted(DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      if (requestInfo != null) {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: configCommitCompleted for request " + requestId + "'");
         }

         try {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: configCommitCompleted about to await " + request.getTimeoutInterval() + " millis for request '" + requestId + "'");
            }

            requestInfo.configCommitCompletedLatch.await(request.getTimeoutInterval(), TimeUnit.MILLISECONDS);
         } catch (InterruptedException var7) {
         }

         try {
            if (requestInfo.preDeploymentHandlerList.size() > 0 && requestInfo.postDeploymentHandlerList.size() > 0) {
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: configCommitCompleted handling 'postDeploymentHandlerList' of size " + requestInfo.postDeploymentHandlerList.size() + " for request '" + requestId + "'");
               }

               this.activateDeploymentList(requestInfo.postDeploymentHandlerList, requestInfo);
               if (this.isDebugEnabled()) {
                  this.debug("DeploymentManagerT: notifying 'commit' success for request '" + requestId + "'");
               }

               this.notifyCommitSuccess(requestId, requestInfo);
            }
         } catch (Throwable var8) {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: notifying 'commit' failure for request '" + requestId + "' due to '" + var8.toString());
            }

            this.notifyCommitFailure(requestId, requestInfo, var8);
         }

      }
   }

   private void sendTaskCompletedNotification(long requestId, String taskId) {
      DeploymentState statusObject = new DeploymentState("?", taskId, 0);
      statusObject.setTaskState(3);
      this.relayStatus(requestId, statusObject);
   }

   private void handleDeploymentPrepare(Deployment deployment, DeploymentRequestInfo requestInfo) throws Throwable {
      if (requestInfo != null) {
         if (this.isDebugEnabled()) {
            this.debug("handleDeploymentPrepare for deployment: " + deployment);
         }

         AbstractOperation operation = (AbstractOperation)requestInfo.operationsMap.get(deployment.getDeploymentTaskRuntimeId());
         if (operation == null) {
            throw new DeploymentException("Cannot find operation for deployment : " + deployment);
         } else {
            if (operation.getDeploymentMBean() instanceof SystemResourceMBean && deployment.requiresRestart()) {
               if (this.isDebugEnabled()) {
                  this.debug("handleDeploymentPrepare: adding SystemResource '" + operation.getDeploymentMBean().getName() + "' to pending restart list on server");
               }

               BasicDeploymentMBean deploymentMBean = operation.getDeploymentMBean();
               if (deploymentMBean instanceof SystemResourceMBean && DeployHelper.isMBeanInPartitionScope(deploymentMBean)) {
                  PartitionMBean partitionMBean = DeployHelper.findContainingPartitionMBean(deploymentMBean);
                  String partitionName = partitionMBean.getName();
                  PartitionRuntimeMBean partitionRuntimeMBean = this.getServerBean().lookupPartitionRuntime(partitionName);
                  partitionRuntimeMBean.addPendingRestartSystemResource(deploymentMBean.getName());
                  if (requestInfo.partitionSystemResourcesToBeRestarted.get(partitionName) == null) {
                     requestInfo.partitionSystemResourcesToBeRestarted.put(partitionName, new HashSet());
                  }

                  Set systemResourcesToBeRestarted = (Set)requestInfo.partitionSystemResourcesToBeRestarted.get(partitionName);
                  systemResourcesToBeRestarted.add((SystemResourceMBean)deploymentMBean);
               } else {
                  this.getServerBean().addPendingRestartSystemResource(deploymentMBean.getName());
                  requestInfo.systemResourcesToBeRestarted.add(deploymentMBean);
               }
            }

            operation.prepare();
         }
      }
   }

   private void removeSystemResourcesFromRestartList(DeploymentRequestInfo requestInfo) {
      if (requestInfo != null) {
         Iterator iterator;
         SystemResourceMBean sysResource;
         if (!requestInfo.systemResourcesToBeRestarted.isEmpty()) {
            for(iterator = requestInfo.systemResourcesToBeRestarted.iterator(); iterator.hasNext(); this.getServerBean().removePendingRestartSystemResource(sysResource.getName())) {
               sysResource = (SystemResourceMBean)iterator.next();
               if (this.isDebugEnabled()) {
                  this.debug("removing SystemResource '" + sysResource.getName() + "' from pending restart list on server");
               }
            }
         }

         iterator = requestInfo.partitionSystemResourcesToBeRestarted.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            PartitionRuntimeMBean partitionRuntimeMBean = this.getServerBean().lookupPartitionRuntime((String)entry.getKey());
            Iterator var5 = ((Set)entry.getValue()).iterator();

            while(var5.hasNext()) {
               SystemResourceMBean systemResourceMBean = (SystemResourceMBean)var5.next();
               partitionRuntimeMBean.removePendingRestartSystemResource(systemResourceMBean.getName());
            }
         }

      }
   }

   private void updateRuntimeStateAndAggregateVersion(String id, DeploymentVersion version, boolean isDeploy) throws ManagementException {
      if (!this.isAdminServer) {
         if (isDeploy) {
            if (this.isDebugEnabled()) {
               this.debug("Updating runtime state and aggregate deployment version component on target server for " + id);
            }

            AppRuntimeStateManager.getManager().updateState(id, version);
            this.addOrUpdateTargetDeploymentVersion(id, version);
         } else {
            if (this.isDebugEnabled()) {
               this.debug("Removing runtime state and aggregate deployment version component on target server for " + id);
            }

            AppRuntimeStateManager.getManager().remove(id);
            this.removeTargetDeploymentVersionFor(id);
         }
      }

   }

   private void handleDeploymentCommit(Deployment deployment, AbstractOperation operation) throws Throwable {
      InternalDeploymentData internalDeploymentData = operation.getInternalDeploymentData();
      String deploymentId = deployment.getIdentity();
      if (internalDeploymentData != null && operation.getDeploymentMBean() != null) {
         String deploymentName = internalDeploymentData.getDeploymentName();
         BasicDeploymentMBean deploymentMBean = operation.getDeploymentMBean();
         if (deploymentMBean == null) {
            if (this.isDebugEnabled()) {
               this.debug("Nothing to do to deploy app :" + deploymentName);
            }

            DeploymentState statusObject = new DeploymentState("?", deploymentId, 0);
            statusObject.setTaskState(2);
            this.relayStatus(deployment.getDeploymentRequestId(), statusObject);
            String msg = DeployerRuntimeLogger.nothingToCommit(ApplicationVersionUtils.getDisplayName(operation.getDeploymentMBean()), this.serverName);
            if (Debug.isDeploymentDebugConciseEnabled()) {
               ApplicationRuntimeState appRuntimeState = AppRuntimeStateManager.getManager().get(deployment.getIdentity());
               if (appRuntimeState != null) {
                  Debug.deploymentDebugConcise("Commit Phase Application Runtime State for " + deployment.getIdentity() + ": " + appRuntimeState);
               }
            }

            throw new ManagementException(msg);
         } else {
            operation.commit();
            int task = operation.getOperationType();
            if (!operation.isControlOperation()) {
               boolean isADeploy = deploymentMBean instanceof AppDeploymentMBean && task != 4;
               this.updateRuntimeStateAndAggregateVersion(deployment.getIdentity(), deployment.getProposedDeploymentVersion(), isADeploy);
            }

            if (Debug.isDeploymentDebugConciseEnabled()) {
               ApplicationRuntimeState appRuntimeState = AppRuntimeStateManager.getManager().get(deployment.getIdentity());
               if (appRuntimeState != null) {
                  Debug.deploymentDebugConcise("Commit Phase Application Runtime State for " + deployment.getIdentity() + ": " + appRuntimeState);
               }
            }

         }
      } else {
         if (this.isDebugEnabled()) {
            this.debug("Nothing to do to commit deployment :" + deploymentId + " associated with request id: " + deployment.getDeploymentRequestId());
         }

         DeploymentState statusObject = new DeploymentState("?", deployment.getDeploymentTaskRuntimeId(), 0);
         statusObject.setTaskState(3);
         this.relayStatus(deployment.getDeploymentRequestId(), statusObject);
         if (Debug.isDeploymentDebugConciseEnabled()) {
            ApplicationRuntimeState appRuntimeState = AppRuntimeStateManager.getManager().get(deployment.getIdentity());
            if (appRuntimeState != null) {
               Debug.deploymentDebugConcise("Commit Phase Application Runtime State for " + deployment.getIdentity() + ": " + appRuntimeState);
            }
         }

      }
   }

   private boolean targetedToThisServer(Deployment deployment) {
      if (deployment == null) {
         return false;
      } else {
         InternalDeploymentData internalDeplData = deployment.getInternalDeploymentData();
         if (internalDeplData == null) {
            return false;
         } else {
            boolean specifiedTargetsOnly = false;
            DeploymentData deplData = internalDeplData.getExternalDeploymentData();
            DeploymentOptions deplOpts = deplData.getDeploymentOptions();
            if (deplOpts != null) {
               specifiedTargetsOnly = deplOpts.getSpecifiedTargetsOnly();
            }

            String[] targets = deployment.getTargets();
            ServerMBean thisServer = ManagementService.getRuntimeAccess(kernelId).getServer();
            ClusterMBean thisCluster = thisServer.getCluster();
            boolean isPartOfCluster = thisCluster != null;

            int i;
            for(i = 0; i < targets.length; ++i) {
               if (this.serverName.equals(targets[i])) {
                  if (specifiedTargetsOnly) {
                     return !ApplicationUtils.isPartitionOrRGShutdown(deplData.getPartition(), deplData.getResourceGroup(), this.getServerBean());
                  }

                  return true;
               }
            }

            if (isPartOfCluster && specifiedTargetsOnly) {
               return false;
            } else {
               for(i = 0; i < targets.length; ++i) {
                  if (isPartOfCluster) {
                     Set clusterServers = thisCluster.getServerNames();
                     if (clusterServers.contains(targets[i])) {
                        return true;
                     }
                  }
               }

               return false;
            }
         }
      }
   }

   private void handleDeploymentCancel(Deployment deployment, DeploymentRequestInfo requestInfo) throws Throwable {
      if (!this.targetedToThisServer(deployment)) {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: notifying 'cancel' success for request '" + deployment.getDeploymentRequestId() + "'");
         }

      } else if (requestInfo == null) {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: nothing to cancel on request '" + deployment.getDeploymentRequestId() + "' since there is no requestInfo available");
         }

      } else {
         AbstractOperation operation = (AbstractOperation)requestInfo.operationsMap.get(deployment.getDeploymentTaskRuntimeId());
         if (operation == null) {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: nothing to cancel on request '" + deployment.getDeploymentRequestId() + "' since there is no operation available");
            }

         } else {
            operation.cancel();
         }
      }
   }

   private AggregateDeploymentVersion getTargetServerAggregateDeploymentsVersion() {
      if (this.targetServerAggregateDeploymentVersion != null) {
         this.targetServerAggregateDeploymentVersion = AggregateDeploymentVersion.createAggregateDeploymentVersion();
      }

      return this.targetServerAggregateDeploymentVersion;
   }

   private AbstractOperation createOperation(Deployment deployment, DeploymentRequestInfo requestInfo) throws Exception {
      DeploymentContext ctxt = requestInfo.context;
      String taskId = deployment.getDeploymentTaskRuntimeId();
      InternalDeploymentData internalDeploymentData = deployment.getInternalDeploymentData();
      if (internalDeploymentData == null) {
         if (this.isDebugEnabled()) {
            this.debug("Nothing to do to deploy '" + deployment + "'");
         }

         String msg = DeployerRuntimeLogger.nothingToDoForTask(taskId);
         throw new Exception(msg);
      } else {
         int deploymentOperation = internalDeploymentData.getDeploymentOperation();
         String deploymentName = internalDeploymentData.getDeploymentName();
         String nonVersionedName = ApplicationVersionUtils.getNonVersionedName(deploymentName);
         String deplId = ApplicationVersionUtils.getApplicationId(nonVersionedName, internalDeploymentData.getExternalDeploymentData().getDeploymentOptions().getArchiveVersion(), internalDeploymentData.getExternalDeploymentData().getDeploymentOptions().getPartition());
         if (this.isDebugEnabled()) {
            this.debug("deploymentName:  " + deploymentName);
            this.debug("nonVersionedName:  " + nonVersionedName);
            this.debug("deplId:  " + deplId);
         }

         if (deploymentName != null && deplId != null && deploymentName.equals(nonVersionedName) && !deploymentName.equals(deplId)) {
            if (this.isDebugEnabled()) {
               this.debug("deployment name " + deploymentName + " changed to " + deplId);
            }

            deploymentName = deplId;
         }

         if (this.isDebugEnabled()) {
            this.debug(" +++ ctxt Obj = " + ctxt + " type " + ctxt.getClass().getName());
         }

         DomainMBean proposedDomain = (DomainMBean)ctxt.getContextComponent("PROPOSED_CONFIGURATION");
         long requestId = deployment.getDeploymentRequestId();
         AuthenticatedSubject initiator = ctxt.getDeploymentRequest().getInitiator();
         String depMBean;
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            depMBean = "Creating target server deploy operation '" + DeployHelper.getTaskName(deploymentOperation) + "' for taskID " + taskId + " and application '" + deploymentName + "' initiated by '" + initiator;
            if (this.isDebugEnabled()) {
               this.debug(depMBean);
            } else {
               Debug.deploymentDebugConcise(depMBean);
            }
         }

         if (Debug.isDeploymentDebugConciseEnabled()) {
            ApplicationRuntimeState appRuntimeState = AppRuntimeStateManager.getManager().get(deployment.getIdentity());
            if (appRuntimeState != null) {
               Debug.deploymentDebugConcise("Initial Application Runtime State for " + deployment.getIdentity() + ": " + appRuntimeState);
            }
         }

         BasicDeploymentMBean depMBean;
         switch (deploymentOperation) {
            case 1:
            case 6:
            case 11:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, true, deplId);
               AbstractOperation abstractOperation = (new ActivateOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean))).refine();
               if (abstractOperation instanceof RedeployOperation && deploymentOperation != 6) {
                  abstractOperation = ((RedeployOperation)abstractOperation).refine();
               }

               return abstractOperation;
            case 2:
            case 12:
            default:
               throw new AssertionError("Invalid Deployment operation provided " + internalDeploymentData.getDeploymentOperation());
            case 3:
            case 8:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, false, deplId);
               return new StopOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean));
            case 4:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, false, deplId);
               return new RemoveOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean));
            case 5:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, false, deplId);
               return new UnprepareOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean));
            case 7:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, false, deplId);
               return new StartOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean));
            case 9:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, true, deplId);
               return (new RedeployOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean))).refine();
            case 10:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, true, deplId);
               return (new DynamicUpdateOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean))).refine();
            case 13:
               depMBean = this.findDeploymentMBean(proposedDomain, deploymentName, deploymentOperation, false, deplId);
               return new RetireOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, this.requiresRestart(depMBean));
            case 14:
               depMBean = null;
               return new ExtendLoaderOperation(requestId, taskId, internalDeploymentData, depMBean, proposedDomain, initiator, false);
         }
      }
   }

   private void resetRequest(DeploymentRequestInfo requestInfo) {
      synchronized(this.requestInfoTable) {
         if (requestInfo != null) {
            long reqId = requestInfo.theRequestId;
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: resetting request: " + reqId + "'");
            }

            requestInfo.preDeploymentHandlerList.clear();
            requestInfo.postDeploymentHandlerList.clear();
            requestInfo.operationsMap.clear();
            requestInfo.context = null;
            requestInfo.configPrepareCompletedLatch.countDown();
            requestInfo.configCommitCompletedLatch.countDown();
            this.requestInfoTable.remove(new Long(reqId));
         }

      }
   }

   private BasicDeploymentMBean getDeploymentMBean(DomainMBean domain, String deploymentName) {
      BasicDeploymentMBean deployable = null;
      if (domain != null) {
         deployable = ApplicationVersionUtils.getDeployment(domain, deploymentName);
      }

      return deployable;
   }

   private void resetCaches() {
      ClassesMetadataEntry.clearCheckedStaleCache();
   }

   private void prepareDeploymentList(ArrayList deploymentList, DeploymentContext context) throws Throwable {
      this.resetCaches();
      DeploymentRequest request = context.getDeploymentRequest();
      long requestId = request.getId();
      final DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      synchronized(deploymentList) {
         int deploymentSize = 0;
         if (deploymentList != null) {
            deploymentSize = deploymentList.size();
         }

         if (deploymentSize > 0) {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: prepareDeploymentList - handling deployment list of size: " + deploymentSize + " for request '" + requestId + "'");
            }

            if (deploymentSize == 1) {
               Deployment deployment = (Deployment)deploymentList.get(0);
               this.handleDeploymentPrepare(deployment, requestInfo);
            } else {
               boolean isParallelPrepareAcrossApplications = Boolean.valueOf(System.getProperty("weblogic.application.ParallelPrepare", "true"));
               if (isParallelPrepareAcrossApplications) {
                  Collection buckets = this.sortForParallel(requestInfo, deploymentList, true);
                  WorkManagerFactory fact = WorkManagerFactory.getInstance();
                  String wmName = "wls-internal-parallel-dynamic-deployments-prepare";
                  boolean wmCreated = false;

                  try {
                     int maxParallelBucketItems = 0;
                     Iterator var15 = buckets.iterator();

                     while(var15.hasNext()) {
                        Bucket b = (Bucket)var15.next();
                        if (b.isParallel) {
                           int count = b.contents.size();
                           if (count > maxParallelBucketItems) {
                              maxParallelBucketItems = count;
                           }
                        }
                     }

                     WorkManager wm = null;
                     if (maxParallelBucketItems > 0) {
                        wm = fact.findOrCreate(wmName, maxParallelBucketItems, -1);
                        wmCreated = true;
                     }

                     BucketInvoker invoker = new BucketInvoker() {
                        protected void doItem(Object item) throws Throwable {
                           Deployment deployment = (Deployment)item;
                           DeploymentManager.this.handleDeploymentPrepare(deployment, requestInfo);
                        }
                     };
                     invoker.invoke(buckets, wm);
                  } finally {
                     if (wmCreated) {
                        fact.remove(wmName);
                     }

                  }
               } else {
                  for(int i = 0; i < deploymentSize; ++i) {
                     Deployment deployment = (Deployment)deploymentList.get(i);
                     this.handleDeploymentPrepare(deployment, requestInfo);
                  }
               }
            }
         } else if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: prepareDeploymentList - nothing to do since list is empty for request '" + requestId + "'");
         }

      }
   }

   private Collection sortForParallel(final DeploymentRequestInfo requestInfo, List deploymentList, final boolean isPrepareTransition) {
      BucketSorter sorter = new BucketSorter() {
         protected BasicDeploymentMBean getDeploymentMBean(Object dep) {
            Deployment deployment = (Deployment)dep;
            AbstractOperation operation = (AbstractOperation)requestInfo.operationsMap.get(deployment.getDeploymentTaskRuntimeId());
            return operation.getDeploymentMBean();
         }

         protected boolean isDeploymentTypeParallelForTransition(DeploymentType type) {
            if (type != null) {
               return isPrepareTransition ? type.isDefaultParallelPrepare() : type.isDefaultParallelActivate();
            } else {
               return false;
            }
         }

         protected boolean usesDeploymentOrderForTransition(DeploymentType type) {
            if (type != null) {
               return isPrepareTransition ? type.usesDeploymentOrderPrepare() : type.usesDeploymentOrderActivate();
            } else {
               return false;
            }
         }
      };
      return sorter.sortForParallelTransition(deploymentList, true);
   }

   private void activateDeploymentList(ArrayList deploymentList, final DeploymentRequestInfo requestInfo) throws Throwable {
      long requestId = requestInfo.context.getDeploymentRequest().getId();
      synchronized(deploymentList) {
         int deploymentSize = 0;
         if (deploymentList != null) {
            deploymentSize = deploymentList.size();
         }

         if (deploymentSize > 0) {
            if (this.isDebugEnabled()) {
               this.debug("DeploymentManagerT: activateDeploymentList - handling deployment list of size: " + deploymentSize + " for request '" + requestId + "'");
            }

            if (deploymentSize == 1) {
               Deployment deployment = (Deployment)deploymentList.get(0);
               this.handleDeploymentCommit(deployment, (AbstractOperation)requestInfo.operationsMap.get(deployment.getDeploymentTaskRuntimeId()));
            } else {
               DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
               boolean isParallelActivateAcrossApplications = domain.isParallelDeployApplications();
               if (isParallelActivateAcrossApplications) {
                  Collection buckets = this.sortForParallel(requestInfo, deploymentList, false);
                  WorkManagerFactory fact = WorkManagerFactory.getInstance();
                  String wmName = "wls-internal-parallel-dynamic-deployments-activate";
                  boolean wmCreated = false;

                  try {
                     int maxParallelBucketItems = 0;
                     Iterator var14 = buckets.iterator();

                     while(var14.hasNext()) {
                        Bucket b = (Bucket)var14.next();
                        if (b.isParallel) {
                           int count = b.contents.size();
                           if (count > maxParallelBucketItems) {
                              maxParallelBucketItems = count;
                           }
                        }
                     }

                     WorkManager wm = null;
                     if (maxParallelBucketItems > 0) {
                        wm = fact.findOrCreate(wmName, maxParallelBucketItems, -1);
                        wmCreated = true;
                     }

                     BucketInvoker invoker = new BucketInvoker() {
                        protected void doItem(Object item) throws Throwable {
                           Deployment deployment = (Deployment)item;
                           DeploymentManager.this.handleDeploymentCommit(deployment, (AbstractOperation)requestInfo.operationsMap.get(deployment.getDeploymentTaskRuntimeId()));
                        }
                     };
                     invoker.invoke(buckets, wm);
                  } finally {
                     if (wmCreated) {
                        fact.remove(wmName);
                     }

                  }
               } else {
                  for(int i = 0; i < deploymentSize; ++i) {
                     Deployment deployment = (Deployment)deploymentList.get(i);
                     this.handleDeploymentCommit(deployment, (AbstractOperation)requestInfo.operationsMap.get(deployment.getDeploymentTaskRuntimeId()));
                  }
               }
            }
         } else if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: activateDeploymentList - nothing to do since list is empty for request '" + requestId + "'");
         }

      }
   }

   private void assertDeploymentMBeanIsNonNull(DomainMBean domainTree, BasicDeploymentMBean mbean, String deploymentName, String operation, int deployOperation) throws DeploymentException {
      if (mbean == null) {
         if (this.isDebugEnabled()) {
            this.debug(operation + ": Could not find mbean for " + deploymentName);
         }

         if (deployOperation != 4) {
            Loggable l = DeployerRuntimeLogger.logNullDeploymentMBeanLoggable(operation, deploymentName);
            l.log();
            throw new DeploymentException(l.getMessage());
         }

         DeployerRuntimeExtendedLogger.logNullDeploymentMBeanLenient(operation, deploymentName);
      }

   }

   private BasicDeploymentMBean findDeploymentMBean(DomainMBean proposed, String deploymentName, int deployOperation, boolean isProposed, String deplId) throws DeploymentException {
      String taskName = DeployHelper.getTaskName(deployOperation);
      BasicDeploymentMBean depMBean;
      if (isProposed) {
         if (this.isDebugEnabled()) {
            this.debug(taskName + ": Trying to find mbean for " + deploymentName + " in proposed domain");
         }

         depMBean = this.getDeploymentMBean(proposed, deploymentName);
         if (depMBean != null) {
            if (this.isDebugEnabled()) {
               this.debug(taskName + ": Found MBean for " + deploymentName + " in proposed domain");
            }

            return depMBean;
         }
      }

      DomainMBean runtimeTree = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (this.isDebugEnabled()) {
         this.debug(taskName + ": Trying to find mbean for " + deploymentName + " in runtime domain");
      }

      depMBean = this.getDeploymentMBean(runtimeTree, deploymentName);
      if (depMBean == null) {
         depMBean = this.getDeploymentMBean(runtimeTree, deplId);
      }

      this.assertDeploymentMBeanIsNonNull(runtimeTree, depMBean, deploymentName, taskName, deployOperation);
      if (this.isDebugEnabled()) {
         this.debug(taskName + ": Found mbean for " + deploymentName + " in runtime domain");
      }

      return depMBean;
   }

   private void createOperations(DeploymentContext deploymentContext) throws Exception {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      long requestId = request.getId();
      DeploymentRequestInfo requestInfo = this.getDeploymentRequestInfo(requestId);
      if (requestInfo == null) {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: createOperations() for request '" + requestId + " after prepare completed");
         }

         String msg = DeployerRuntimeLogger.requestCompletedOrCancelled(requestId);
         this.dispatcher.notifyPrepareFailure(requestId, new Exception(msg));
      } else {
         if (this.isDebugEnabled()) {
            this.debug("DeploymentManagerT: createOperations() for request: " + requestId);
         }

         Iterator iterator = request.getDeployments("Application");
         if (iterator != null) {
            while(true) {
               while(iterator.hasNext()) {
                  Deployment deployment = (Deployment)iterator.next();
                  if (this.targetedToThisServer(deployment)) {
                     AbstractOperation operation = this.createOperation(deployment, requestInfo);
                     if (operation.getOperationType() == 14 || operation.getDeploymentMBean() != null) {
                        operation.stageFilesFromAdminServer(deployment.getDataTransferHandlerType());
                     }

                     requestInfo.operationsMap.put(deployment.getDeploymentTaskRuntimeId(), operation);
                     if (this.isDebugEnabled()) {
                        this.debug("DeploymentManagerT.createOperations(): created operation for deployment: " + deployment + " of request id " + request.getId() + " targeted on this server " + operation);
                     }
                  } else if (this.isDebugEnabled()) {
                     this.debug("DeploymentManagerT.createOperations(): application deployment: " + deployment + " of request id: " + request.getId() + " not targeted on this server");
                  }
               }

               return;
            }
         }
      }
   }

   private void notifyCommitSuccess(long requestId, DeploymentRequestInfo info) {
      this.resetRequest(info);
      this.dispatcher.notifyCommitSuccess(requestId);
   }

   private void notifyCommitFailure(long requestId, DeploymentRequestInfo info, Throwable failure) {
      this.resetRequest(info);
      this.dispatcher.notifyCommitFailure(requestId, DeployHelper.convertThrowableForTransfer(failure));
   }

   private void notifyCancelSuccess(long requestId, DeploymentRequestInfo info) {
      this.resetRequest(info);
      this.dispatcher.notifyCancelSuccess(requestId);
   }

   private void notifyCancelFailure(long requestId, DeploymentRequestInfo info, Throwable failure) {
      this.resetRequest(info);
      this.dispatcher.notifyCancelFailure(requestId, DeployHelper.convertThrowable(failure));
   }

   // $FF: synthetic method
   DeploymentManager(Object x0) {
      this();
   }

   private static class DeploymentRequestInfo {
      private final long theRequestId;
      private DeploymentContext context;
      private Map operationsMap;
      private ArrayList preDeploymentHandlerList;
      private ArrayList postDeploymentHandlerList;
      private Set systemResourcesToBeRestarted;
      private Map partitionSystemResourcesToBeRestarted;
      private CountDownLatch configPrepareCompletedLatch;
      private CountDownLatch configCommitCompletedLatch;

      private DeploymentRequestInfo(long requestId, DeploymentContext context) {
         this.systemResourcesToBeRestarted = new HashSet();
         this.partitionSystemResourcesToBeRestarted = new HashMap();
         this.theRequestId = requestId;
         this.context = context;
         this.operationsMap = new HashMap();
         this.preDeploymentHandlerList = new ArrayList();
         this.postDeploymentHandlerList = new ArrayList();
         this.configPrepareCompletedLatch = new CountDownLatch(1);
         this.configCommitCompletedLatch = new CountDownLatch(1);
      }

      // $FF: synthetic method
      DeploymentRequestInfo(long x0, DeploymentContext x1, Object x2) {
         this(x0, x1);
      }
   }

   static class Maker {
      static final DeploymentManager MANAGER = new DeploymentManager();
   }
}
