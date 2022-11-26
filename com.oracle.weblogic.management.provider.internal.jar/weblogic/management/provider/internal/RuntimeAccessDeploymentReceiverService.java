package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.xml.stream.XMLStreamException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.internal.targetserver.datamanagement.ConfigData;
import weblogic.deploy.internal.targetserver.datamanagement.Data;
import weblogic.deploy.internal.targetserver.datamanagement.DataUpdateRequestInfo;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentReceiverV2;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.RegistrationException;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.Deployment.DeploymentType;
import weblogic.deploy.service.internal.CalloutManager;
import weblogic.deploy.service.internal.CalloutsUpdateListener;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.targetserver.TargetCalloutManager;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorCreationListener;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorPreNotifyProcessor;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.BeanScoper;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.SpecialPropertiesProcessor;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationExtensionMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBus;
import weblogic.management.eventbus.apis.InternalEventBusFactory;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.eventbus.apis.InternalEvent.EventType;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.internal.ProductionModeHelper;
import weblogic.management.provider.MSIService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.PropertyService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.UpdateException;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.BeanNameUtil;
import weblogic.management.utils.TargetingAnalyzer;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.management.workflow.DescriptorLock;
import weblogic.management.workflow.DescriptorLockHandle;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.LocatorUtilities;

@Service
@Named
@RunLevel(
   value = 5,
   mode = 0
)
public class RuntimeAccessDeploymentReceiverService extends AbstractServerService implements DeploymentReceiverV2 {
   @Inject
   @Named("EarlySecurityInitializationService")
   private ServerService dependencyOnEarlySecurityInitializationService;
   @Inject
   @Named("DeploymentService")
   private ServerService dependencyOnDeploymentService;
   @Inject
   private Provider msiService;
   @Inject
   @Named("PropertyService")
   private ServerService dependencyOnPropertyService;
   @Inject
   private Provider partitionRuntimeStateManagerProvider;
   @Inject
   private Provider targetingAnalyzerProvider;
   @Inject
   private Provider runtimeAccessProvider;
   @Inject
   private Provider partitionResourceProcessorProvider;
   @Inject
   private ConfiguredDeploymentsAccess configuredDeployments;
   @Inject
   private DescriptorLock descriptorLock;
   private DescriptorLockHandle descriptorLockHandle;
   private final boolean OLD_LIFECYCLE_MODEL = false;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final int DOWNLOAD_ATTEMPTS = 2;
   private static final int DOWNLOAD_SLEEP_SECONDS = 1;
   private final Data dataObject;
   private ServerRuntimeMBean serverBean;
   private long currRequestId;
   private long lastRequestId;
   private Descriptor currRequestTree;
   private Descriptor proposedConfigTree;
   private boolean prepareCalled;
   private HashMap proposedExternalTrees;
   private List currChangeDescriptors;
   private String callbackHandlerId;
   private static RuntimeAccessDeploymentReceiverService singleton;
   private DeploymentService deploymentService = null;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String localServerName;
   private ArrayList restartRequestList = new ArrayList();
   private WeakHashMap temporaryTrees = new WeakHashMap();
   private long pendingCommitRequestId = -1L;
   private Descriptor pendingCommitRequestTree;
   private List pendingChangeDescriptors;
   private DescriptorDiff proposedConfigChanges;
   private final CalloutManager calloutManager = (CalloutManager)LocatorUtilities.getService(TargetCalloutManager.class);
   public static boolean CalloutsListenerAttached = false;
   private static int currTreeIdx = 0;

   public RuntimeAccessDeploymentReceiverService() {
      if (singleton != null) {
         throw new AssertionError("RuntimeAccessDeploymentReceiverService already initialized");
      } else {
         ReferenceManager.registerBeanScoper(new BeanScoper() {
            public String findScope(DescriptorBean bean) {
               for(DescriptorBean parent = bean.getParentBean(); parent != null; parent = parent.getParentBean()) {
                  if (parent instanceof PartitionMBean) {
                     return ((PartitionMBean)parent).getName();
                  }

                  if (parent instanceof DomainMBean && bean instanceof AbstractDescriptorBean && ((AbstractDescriptorBean)bean)._isTransient() && bean instanceof ConfigurationMBean) {
                     PartitionMBean partition = BeanNameUtil.belongsToPartition((ConfigurationMBean)bean);
                     if (partition != null) {
                        return partition.getName();
                     }
                  }

                  bean = parent;
               }

               return null;
            }
         });
         this.dataObject = new ConfigData(DomainDir.getRootDir(), this.getLockFileName(), "stage");
         singleton = this;
      }
   }

   public void start() throws ServiceFailureException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Starting Runtime Access deployment receiver service " + this);
      }

      try {
         PropertyService propService = ManagementService.getPropertyService(kernelId);
         if (!propService.isAdminServer()) {
            this.registerHandlerWithRetries(propService.getServerName());
         }

      } catch (RegistrationException var2) {
         ManagementLogger.logCouldNotRegisterWithAdminServer(var2.getMessage());
         ((MSIService)this.msiService.get()).setAdminServerAvailable(false);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Error registering deployment receiver: ", var2);
         }

      }
   }

   public void stop() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Stopping Runtime Access deployment receiver service " + this);
      }

      DeploymentService.getDeploymentService().unregisterHandler(this.callbackHandlerId);
      if (!this.temporaryTrees.isEmpty()) {
         System.gc();
         synchronized(this.temporaryTrees) {
            Iterator it = this.temporaryTrees.values().iterator();

            while(it.hasNext()) {
               String treeType = (String)it.next();
               ManagementLogger.logTemporaryBeanTreeNotGarbageCollected(treeType);
            }

         }
      }
   }

   public void halt() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Halting Runtime Access deployment receiver service " + this);
      }

      this.commitAnyPendingRequests();
   }

   public String getHandlerIdentity() {
      return this.callbackHandlerId;
   }

   public synchronized void prepare(DeploymentContext context) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      Descriptor currTree = runtimeAccess.getDomain().getDescriptor();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Handling prepare of configuration deployment request for " + context.getDeploymentRequest() + " with context " + context);
      }

      DeploymentRequest request = context.getDeploymentRequest();
      if (this.proposedConfigTree == null && this.proposedExternalTrees == null && !CommonAdminConfigurationManager.getInstance().hasWork("" + request.getId())) {
         this.notifyPrepareSuccess(request, false);
      } else if (context.getContextComponent("calloutAbortedProposedConfig") != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeAccessDeploymentReciverService.prepare(): skipping, call notifyPrepareSuccess()");
         }

         this.notifyPrepareSuccess(request, false);
      } else {
         Deployment lastDeployment = null;
         int tmpFileIdx = true;

         try {
            Iterator iterator = request.getDeployments(this.callbackHandlerId);
            Deployment deployment = (Deployment)iterator.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("RuntimeDeploymentReceiver: preparing deployment " + deployment);
            }

            Iterator listIt = this.currChangeDescriptors.iterator();

            while(listIt != null && listIt.hasNext()) {
               ChangeDescriptor change = (ChangeDescriptor)listIt.next();
               if (!this.isConfigChange(change) && !this.isNonWLSChange(change)) {
                  this.handleExternalTreePrepare(context, change, deployment);
               }
            }

            if (this.proposedConfigTree != null) {
               this.proposedConfigChanges = this.prepareUpdateDiff(currTree, this.proposedConfigTree, false);
               this.prepareCalled = true;
            }

            CommonAdminConfigurationManager.getInstance().prepare("" + request.getId());
            if (request.concurrentAppPrepareEnabled()) {
               this.doCommit(context, false);
            }

            this.notifyPrepareSuccess(request, context.isRestartRequired());
         } catch (Throwable var11) {
            Loggable loggable = ManagementLogger.logPrepareConfigUpdateFailedLoggable(var11);
            loggable.log();
            this.notifyPrepareFailure(request, new UpdateException(loggable.getMessageBody(), var11));
         }

      }
   }

   private static synchronized int getNextCurrTreeIdx() {
      return currTreeIdx++;
   }

   public synchronized void commit(DeploymentContext context) {
      if (context.getDeploymentRequest().getConfigCommitCalled()) {
         this.notifyCommitSuccess(context.getDeploymentRequest());
      } else {
         boolean var10 = false;

         try {
            var10 = true;
            this.doCommit(context, true);
            var10 = false;
         } finally {
            if (var10) {
               SituationalConfigManager situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);

               try {
                  situationalConfigManager.reload();
               } catch (Exception var11) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Failed reloading situationalconfig " + var11);
                  }
               }

            }
         }

         SituationalConfigManager situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);

         try {
            situationalConfigManager.reload();
         } catch (Exception var13) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failed reloading situationalconfig " + var13);
            }
         }

      }
   }

   private void doCommit(final DeploymentContext context, boolean notify) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Handling commit of runtime configuration deployment for " + context.getDeploymentRequest() + " with context " + context);
      }

      DescriptorUpdateFailedException updateFailedException = null;
      DeploymentRequest request = context.getDeploymentRequest();
      if (this.currRequestId != request.getId()) {
         if (request.getId() != this.lastRequestId) {
            IllegalArgumentException illegal = new IllegalArgumentException("Commit request id " + request.getId() + " does not match outstanding request id " + this.currRequestId);
            if (notify) {
               this.notifyCommitFailure(request, illegal);
               request.setConfigCommitCalled();
               return;
            }

            throw illegal;
         }

         if (notify) {
            this.notifyCommitSuccess(request);
         }

         request.setConfigCommitCalled();
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: committing changes");
         }

         List internalEventPayload = new ArrayList();
         Iterator changes = null;
         if (this.currChangeDescriptors != null) {
            changes = this.currChangeDescriptors.iterator();
         }

         while(true) {
            while(changes != null && changes.hasNext()) {
               ChangeDescriptor change = (ChangeDescriptor)changes.next();
               internalEventPayload.add(change);
               if (change.getChangeOperation().equals("delete")) {
                  this.dataObject.deleteFile(change.getChangeTarget(), request.getId());
               } else if (this.isUpdateChange(change) && !this.isConfigChange(change) && !this.isNonWLSChange(change)) {
                  String filePath = change.getChangeTarget();

                  try {
                     this.handleExternalTreeCommit(filePath, context);
                  } catch (RuntimeException var18) {
                     if (!(var18.getCause() instanceof DescriptorUpdateFailedException)) {
                        throw var18;
                     }

                     updateFailedException = (DescriptorUpdateFailedException)var18.getCause();
                  }
               }
            }

            boolean publishRequired = ManagementService.getPropertyService(kernelId).isAdminServer() ? this.isPublishRequired(this.proposedConfigChanges) : false;
            RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
            final DomainMBean current = runtimeAccess.getDomain();
            final Descriptor currTree = current.getDescriptor();
            Object prop = context.getContextComponent("PROPOSED_CONFIGURATION");
            final DomainMBean proposed = prop instanceof DomainMBean ? (DomainMBean)prop : null;
            if (this.abortDoCommitForCallout(this.isHearbeatRequest(request), current, proposed)) {
               this.dataObject.cancelDataUpdate(request.getId());
               this.deploymentService.addOrUpdateCurrentDomainVersion(this.getHandlerIdentity(), new ConfigurationVersion(true));
               if (request instanceof TargetRequestImpl) {
                  TargetRequestImpl targetRequest = (TargetRequestImpl)request;
                  if (proposed != null && targetRequest.getSyncToAdminDeployments() != null) {
                     context.addContextComponent("calloutAbortedProposedConfig", proposed);
                  }
               }

               request.setConfigCommitCalled();
               if (notify) {
                  this.notifyCommitSuccess(request);
               }

               this.resetState();
               return;
            }

            if (this.currRequestTree != null) {
               final TargetingAnalyzer analyzer = proposed == null ? null : (this.targetingAnalyzerProvider != null ? (TargetingAnalyzer)this.targetingAnalyzerProvider.get() : null);
               if (analyzer != null) {
                  analyzer.init(current, proposed);
               }

               try {
                  AuthenticatedSubject initiator = context.getDeploymentRequest().getInitiator();
                  if (initiator != null) {
                     SecurityServiceManager.runAs(kernelId, initiator, new PrivilegedAction() {
                        public Object run() {
                           try {
                              RuntimeAccessDeploymentReceiverService.this.processChanges(analyzer, current, proposed, currTree, context);
                              return null;
                           } catch (ServiceFailureException | ManagementException | PartitionLifeCycleException | ResourceGroupLifecycleException | DescriptorUpdateFailedException var2) {
                              throw new RuntimeException("Failure Processing Changes", var2);
                           }
                        }
                     });
                  } else {
                     this.processChanges(analyzer, current, proposed, currTree, context);
                  }
               } catch (DescriptorUpdateFailedException var19) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("doCommitFailed : " + request.getId() + " due to " + var19, var19);
                  }

                  updateFailedException = var19;
               } catch (RuntimeException var20) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("doCommitFailed : " + request.getId() + " due to " + var20, var20);
                  }

                  if (!(var20.getCause() instanceof DescriptorUpdateFailedException)) {
                     throw var20;
                  }

                  updateFailedException = (DescriptorUpdateFailedException)var20.getCause();
               }
            }

            this.dataObject.closeDataUpdate(request.getId(), true);
            DescriptorInfoUtils.removeAllDeletedDescriptorInfos(currTree);
            if (updateFailedException != null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("doCommitFailed with updateFailedException: " + request.getId() + " due to " + updateFailedException, updateFailedException);
               }

               throw updateFailedException;
            }

            CommonAdminConfigurationManager.getInstance().commit("" + request.getId());
            InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
            Map eventPayload = new HashMap();
            eventPayload.put("changes", internalEventPayload);
            if (publishRequired) {
               eventPayload.put("publish", true);
            }

            ConfigurationDeployment configDeployment = this.getConfigDeployment(request);
            eventPayload.put("session_name", configDeployment.getEditSessionName());
            eventPayload.put("partition_name", configDeployment.getPartitionName());
            InternalEvent commitSuccessEvent = new InternalEventImpl(EventType.MANAGEMENT_EDIT_SESSION_COMMITTED_SUCCESS, eventPayload);
            Future result = internalEventbus.send(commitSuccessEvent);
            result.get();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("doCommit was successful: " + request.getId());
            }

            if (notify) {
               this.notifyCommitSuccess(request);
            }

            request.setConfigCommitCalled();
            this.resetState();
            break;
         }
      } catch (Throwable var21) {
         Loggable loggable = ManagementLogger.logCommitConfigUpdateFailedLoggable(var21);
         loggable.log();
         request.setConfigCommitCalled();
         if (!notify) {
            if (var21 instanceof RuntimeException) {
               throw (RuntimeException)var21;
            }

            throw new RuntimeException("Commit Failed " + loggable.getMessageBody(), var21);
         }

         this.notifyCommitFailure(request, new UpdateException(loggable.getMessageBody(), var21));
      }

   }

   private void processChanges(TargetingAnalyzer analyzer, DomainMBean current, DomainMBean proposed, Descriptor currTree, DeploymentContext context) throws ManagementException, DescriptorUpdateFailedException, ServiceFailureException, PartitionLifeCycleException, ResourceGroupLifecycleException {
      this.preApplyChanges(analyzer, current);
      this.commitCurrentTreeAndSaveRevertDiffs(currTree, context);
      this.postApplyChanges(analyzer, proposed);
      if (!this.serverBean.isAdminServer() && proposed != null) {
         this.haltShutdownPartitionsWithNoTargetedActiveAdminRGs(proposed);
         this.bootHaltedPartitionsWithTargetedActiveAdminRGs(proposed);
      }

   }

   private void preApplyChanges(TargetingAnalyzer analyzer, DomainMBean current) throws DescriptorUpdateFailedException, ServiceFailureException, DeploymentException {
      String serverName = this.getServerBean().getName();
      if (analyzer == null) {
         ;
      }
   }

   private void postApplyChanges(TargetingAnalyzer analyzer, DomainMBean proposed) throws ManagementException, DescriptorUpdateFailedException {
      if (analyzer != null) {
         String serverName = this.getServerBean().getName();
         ((PartitionRuntimeStateManager)this.partitionRuntimeStateManagerProvider.get()).prune(proposed);
      }
   }

   private void haltShutdownPartitionsWithNoTargetedActiveAdminRGs(DomainMBean domain) throws ResourceGroupLifecycleException, PartitionLifeCycleException {
      if (domain != null) {
         ;
      }
   }

   private void bootHaltedPartitionsWithTargetedActiveAdminRGs(DomainMBean domain) throws ResourceGroupLifecycleException, PartitionLifeCycleException {
      if (domain != null) {
         ;
      }
   }

   private ServerRuntimeMBean getServerBean() {
      if (this.serverBean == null) {
         this.serverBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      }

      return this.serverBean;
   }

   private final PartitionRuntimeMBean getPartitionRuntimeBean(String partitionName) {
      return this.getServerBean().lookupPartitionRuntime(partitionName);
   }

   private final String getLocalServerName() {
      if (localServerName == null) {
         localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      }

      return localServerName;
   }

   private boolean containsLocalServer(String[] list) {
      if (list == null) {
         return false;
      } else {
         for(int i = 0; i < list.length; ++i) {
            if (this.getLocalServerName().equals(list[i])) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean requiresRestart(Deployment deployment) {
      if (deployment == null) {
         return false;
      } else {
         return this.getServerBean().isRestartRequired() ? true : this.containsLocalServer(deployment.getServersToBeRestarted());
      }
   }

   private boolean isConfigChange(ChangeDescriptor change) {
      return change.getIdentity() != null && change.getIdentity().equals("config");
   }

   private boolean isUpdateChange(ChangeDescriptor change) {
      return change.getChangeOperation().equals("update");
   }

   private boolean isNonWLSChange(ChangeDescriptor change) {
      return change.getIdentity() != null && change.getIdentity().equals("non-wls");
   }

   public synchronized void cancel(DeploymentContext context) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Handling cancel of runtime configuration deployment for " + context.getDeploymentRequest() + " with context " + context);
      }

      DeploymentRequest request = context.getDeploymentRequest();

      try {
         DescriptorDiff revertDiff = (DescriptorDiff)context.getContextComponent("beanRevertDescriptorDiffId");
         if (revertDiff != null) {
            this.revertCommittedConfigChanges(revertDiff, context);
         } else {
            this.dataObject.cancelDataUpdate(request.getId());
            this.removeFromRestartList(request);
            if (this.currRequestId == request.getId() && this.currRequestTree != null && this.prepareCalled) {
               this.rollbackUpdate(this.currRequestTree);
            } else if (this.pendingCommitRequestId == request.getId()) {
               if (this.pendingCommitRequestTree != null) {
                  this.rollbackUpdate(this.pendingCommitRequestTree);
               }

               this.resetPendingCommitState();
            }
         }

         Iterator iterator = request.getDeployments(this.callbackHandlerId);
         if (iterator.hasNext()) {
            Deployment deployment = (Deployment)iterator.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("RuntimeDeploymentReceiver: handle cancel " + deployment);
            }
         }

         if (this.currRequestId == request.getId()) {
            this.resetState();
         }

         CommonAdminConfigurationManager.getInstance().rollback("" + request.getId());
         DeploymentService.getDeploymentService().notifyCancelSuccess(request.getId(), this.callbackHandlerId);
      } catch (Exception var6) {
         Loggable loggable = ManagementLogger.logCancelConfigUpdateFailedLoggable(var6);
         loggable.log();
         DeploymentService.getDeploymentService().notifyCancelFailure(request.getId(), this.callbackHandlerId, var6);
      }

   }

   private void removeFromRestartList(DeploymentRequest request) {
      if (request != null) {
         ServerRuntimeMBean serverRuntimeMBean = this.getServerBean();
         if (this.restartRequestList.remove(new Long(request.getId())) && this.restartRequestList.isEmpty()) {
            serverRuntimeMBean.setRestartRequired(false);
         }

         try {
            ConfigurationDeployment deployment = this.getConfigDeployment(request);
            this.setPartitionAndSystemResourcesRestartList(deployment, false);
         } catch (Exception var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception removing request from restart list: " + request.getId(), var4);
            }
         }

      }
   }

   public void prepareCompleted(DeploymentContext deploymentContext, String callbackIdentifier) {
   }

   public void commitCompleted(DeploymentContext deploymentContext, String callbackIdentifier) {
   }

   public void commitSkipped(DeploymentContext deploymentContext) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Handling commit skipped request for " + deploymentContext.getDeploymentRequest() + " with context " + deploymentContext);
      }

      this.commitAnyPendingRequests();
   }

   public synchronized void updateDeploymentContext(DeploymentContext context) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      Descriptor currTree = runtimeAccess.getDomain().getDescriptor();
      boolean abortDueToCallout = false;
      this.proposedConfigTree = null;
      this.proposedConfigChanges = null;
      this.prepareCalled = false;
      this.proposedExternalTrees = null;
      if (this.descriptorLockHandle != null) {
         this.descriptorLockHandle.unlock();
         this.descriptorLockHandle = null;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(" Handling update deployment of configuration deployment request for " + context.getDeploymentRequest() + " with context " + context);
      }

      DeploymentRequest request = context.getDeploymentRequest();
      if (this.currRequestId == request.getId()) {
         this.notifyUpdateSuccess(request);
      } else {
         this.commitAnyPendingRequests();

         try {
            Loggable deployment;
            try {
               Iterator iterator = request.getDeployments(this.callbackHandlerId);
               if (iterator == null || !iterator.hasNext()) {
                  this.notifyUpdateSuccess(request);
                  return;
               }

               deployment = null;

               ConfigurationDeployment deployment;
               try {
                  deployment = this.getConfigDeployment(request);
               } catch (Exception var16) {
                  this.notifyUpdateFailure(request, var16);
                  return;
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("RuntimeDeploymentReceiver: update deployment context " + deployment);
               }

               List changes = deployment.getChangeDescriptors();
               this.currChangeDescriptors = changes;
               if (changes == null) {
                  IllegalArgumentException e = new IllegalArgumentException("No changes found.");
                  e.fillInStackTrace();
                  this.notifyUpdateFailure(request, e);
                  return;
               }

               this.downloadFiles(request.getId(), deployment);
               Iterator listIt = changes.iterator();

               while(listIt != null && listIt.hasNext()) {
                  ChangeDescriptor change = (ChangeDescriptor)listIt.next();
                  if (!this.isConfigChange(change)) {
                     if (this.proposedExternalTrees == null) {
                        this.proposedExternalTrees = new HashMap();
                     }

                     this.handleExternalTreeLoad(request.getId(), change);
                  } else {
                     this.proposedConfigTree = this.handleConfigTreeLoad(context, change, deployment);
                     DomainMBean root = (DomainMBean)this.proposedConfigTree.getRootBean();
                     if (this.abortDoCommitForCallout(false, runtimeAccess.getDomain(), root)) {
                        context.addContextComponent("calloutAbortedProposedConfig", root);
                        abortDueToCallout = true;
                     }

                     if (ManagementService.getPropertyService(kernelId).isAdminServer()) {
                        if (ProductionModeHelper.isProductionModePropertySet()) {
                           root.setProductionModeEnabled(ProductionModeHelper.getProductionModeProperty());
                        } else if (root.isProductionModeEnabled()) {
                           DescriptorHelper.setDescriptorTreeProductionMode(this.proposedConfigTree, true);
                        }
                     } else if (ProductionModeHelper.isGlobalProductionModeSet()) {
                        root.setProductionModeEnabled(ProductionModeHelper.getGlobalProductionMode());
                     } else if (root.isProductionModeEnabled()) {
                        DescriptorHelper.setDescriptorTreeProductionMode(this.proposedConfigTree, true);
                     }
                  }
               }

               if (this.proposedConfigTree != null) {
                  this.addTemporaryTree(this.proposedConfigTree, "updateDeploymentContext");
                  DescriptorInfoUtils.setDescriptorLoadExtensions(this.proposedConfigTree, true);
                  DescriptorInfoUtils.setExtensionTemporaryFiles(this.proposedConfigTree, this.dataObject.getTemporaryFiles());
                  DomainMBean root = (DomainMBean)this.proposedConfigTree.getRootBean();
                  DynamicServersProcessor.updateConfiguration(root);
                  PartitionProcessor.updateConfiguration(root);
                  SpecialPropertiesProcessor.updateConfiguration(root, true);
                  DynamicMBeanProcessor.getInstance().updateConfiguration(root);
                  this.configuredDeployments.updateMultiVersionConfiguration(root);
                  this.currRequestTree = currTree;
                  context.addContextComponent("PROPOSED_CONFIGURATION", root);
               }

               this.setCurrentRequestId(request.getId());
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Setting request id to " + this.currRequestId);
               }

               if (abortDueToCallout) {
                  debugLogger.debug("Abort due to callout, send notifyUpdateSuccess");
                  this.notifyUpdateSuccess(request);
                  return;
               }

               boolean serverRestartRequired = this.requiresRestart(deployment);
               if (serverRestartRequired) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("RuntimeDeploymentReceiver: setting restart required for request " + request.getId());
                  }

                  context.setRestartRequired(true);
                  this.getServerBean().setRestartRequired(true);
                  this.restartRequestList.add(new Long(request.getId()));
               }

               this.setRestartRequiredForSituationalConfig(context, request);
               this.setPartitionAndSystemResourcesRestartList(deployment, true);
               this.dataObject.commitDataUpdate();
               CommonAdminConfigurationManager.getInstance().sync("" + request.getId());
               this.applySitConfigFilesToTemporaryTrees();
               this.notifyUpdateSuccess(request);
            } catch (Throwable var17) {
               deployment = ManagementLogger.logPrepareConfigUpdateFailedLoggable(var17);
               deployment.log();
               this.removeFromRestartList(request);
               this.dataObject.cancelDataUpdate(request.getId());
               this.notifyUpdateFailure(request, new UpdateException(deployment.getMessageBody(), var17));
            }
         } finally {
            this.dataObject.releaseLock(request.getId());
         }

      }
   }

   private void setPartitionAndSystemResourcesRestartList(ConfigurationDeployment deployment, boolean toSetOrResetStatus) {
      String setOrResetString = toSetOrResetStatus ? "set" : "reset";
      int var6;
      if (deployment.getPartitionsToBeRestarted().length > 0) {
         String[] var4 = deployment.getPartitionsToBeRestarted();
         int var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            String partitionName = var4[var6];
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("RuntimeDeploymentReceiver: restart required" + setOrResetString + " for partition " + partitionName);
            }

            if (toSetOrResetStatus) {
               this.getServerBean().setPartitionRestartRequired(partitionName, true);
            } else {
               this.getServerBean().setPartitionRestartRequired(partitionName, false);
            }
         }
      }

      Collection systemResources = deployment.getServerSystemResourcesToBeRestarted(this.getServerBean().getName());
      if (systemResources != null) {
         Iterator var12 = systemResources.iterator();

         while(var12.hasNext()) {
            String systemResource = (String)var12.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("RuntimeDeploymentReceiver: restart required" + setOrResetString + " for system-resource [" + systemResource + "] of server [" + this.getServerBean().getName() + "]");
            }

            if (toSetOrResetStatus) {
               this.getServerBean().addPendingRestartSystemResource(systemResource);
            } else {
               this.getServerBean().removePendingRestartSystemResource(systemResource);
            }
         }
      }

      PartitionRuntimeMBean[] var13 = this.getServerBean().getPartitionRuntimes();
      var6 = var13.length;

      for(int var15 = 0; var15 < var6; ++var15) {
         PartitionRuntimeMBean partitionRuntimeMBean = var13[var15];
         systemResources = deployment.getPartitionSystemResourcesToBeRestarted(partitionRuntimeMBean.getName());
         if (systemResources != null) {
            Iterator var9 = systemResources.iterator();

            while(var9.hasNext()) {
               String systemResource = (String)var9.next();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("RuntimeDeploymentReceiver: restart required" + setOrResetString + " for system-resource [" + systemResource + "] of partition [" + partitionRuntimeMBean.getName() + "]");
               }

               if (toSetOrResetStatus) {
                  partitionRuntimeMBean.addPendingRestartSystemResource(systemResource);
               } else {
                  partitionRuntimeMBean.removePendingRestartSystemResource(systemResource);
               }
            }
         }
      }

   }

   private void setRestartRequiredForSituationalConfig(DeploymentContext context, DeploymentRequest request) {
      SituationalConfigManager situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
      if (situationalConfigManager.isDeferringNonDynamicChanges()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: setting restart required for request " + request.getId() + " due to deferring non-dynamic changes.");
         }

         context.setRestartRequired(true);
         this.getServerBean().setRestartRequired(true);
         this.restartRequestList.add(new Long(request.getId()));
      }

   }

   /** @deprecated */
   @Deprecated
   public static RuntimeAccessDeploymentReceiverService getService() {
      return singleton;
   }

   private void registerHandlerWithRetries(String serverName) throws RegistrationException, ServiceFailureException {
      try {
         this.registerHandler();
      } catch (RegistrationException var10) {
         ServerMBean mbeanServer = this.getServerMBean(serverName);
         int numOfRetries = mbeanServer != null ? mbeanServer.getNumOfRetriesBeforeMSIMode() : 3;
         int retryInterval = mbeanServer != null ? mbeanServer.getRetryIntervalBeforeMSIMode() : 5;
         if (numOfRetries == 0) {
            throw var10;
         }

         int currentRetries = 0;

         while(currentRetries < numOfRetries) {
            try {
               ++currentRetries;
               DeploymentService.getDeploymentService().unregisterHandler(this.callbackHandlerId);
               ManagementLogger.logRetryRegisterDeploymentService(currentRetries, retryInterval, numOfRetries);

               try {
                  Thread.sleep((long)(retryInterval * 1000));
               } catch (InterruptedException var8) {
               }

               this.registerHandler();
               return;
            } catch (RegistrationException var9) {
               if (currentRetries == numOfRetries) {
                  throw var9;
               }
            }
         }
      }

   }

   private ServerMBean getServerMBean(String serverName) {
      try {
         ServerMBean[] mbeanServers = ((RuntimeAccessImpl)this.runtimeAccessProvider.get()).getDomain().getServers();

         for(int i = 0; i < mbeanServers.length; ++i) {
            if (serverName.equals(mbeanServers[i].getName())) {
               return mbeanServers[i];
            }
         }

         return null;
      } catch (Exception var4) {
         return null;
      }
   }

   public void registerHandler() throws RegistrationException, ServiceFailureException {
      this.callbackHandlerId = "Configuration";
      ConfigurationVersion startVersion = new ConfigurationVersion(true);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Registering handler for configuration with version " + startVersion);
      }

      this.deploymentService = DeploymentService.getDeploymentService();
      DeploymentContext registrationResponse = this.deploymentService.registerHandler(startVersion, this);
      this.handleRegistrationResponse(startVersion, registrationResponse);
      RuntimeAccess runtimeAccess = (RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class);
      if (runtimeAccess != null && !runtimeAccess.isAdminServer()) {
         try {
            this.calloutManager.init(runtimeAccess.getDomain());
            if (!CalloutsListenerAttached) {
               debugLogger.debug("RuntimeAccessDeploymentReceiverService.registerHandler(): add CalloutsUpdateListener");
               DomainMBean domainMBean = runtimeAccess.getDomain();
               domainMBean.addBeanUpdateListener(new CalloutsUpdateListener());
               CalloutsListenerAttached = true;
            }
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   private void handleRegistrationResponse(Version startVersion, DeploymentContext registrationResponse) throws ServiceFailureException {
      DeploymentRequest request = registrationResponse.getDeploymentRequest();

      try {
         Iterator iterator = request.getDeployments(this.callbackHandlerId);
         if (iterator == null || !iterator.hasNext()) {
            return;
         }

         Deployment deployment = (Deployment)iterator.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: handle registration " + deployment);
         }

         List changes = deployment.getChangeDescriptors();
         if (changes == null) {
            return;
         }

         if (!this.updateFiles(request.getId(), deployment)) {
            this.deploymentService.addOrUpdateCurrentDomainVersion(this.getHandlerIdentity(), startVersion);
         }
      } catch (Throwable var10) {
         Loggable loggable = ManagementLogger.logRegisterConfigUpdateFailedLoggable(var10);
         loggable.log();
         this.dataObject.cancelDataUpdate(request.getId());
         throw new ServiceFailureException(loggable.getMessageBody(), var10);
      } finally {
         this.dataObject.closeDataUpdate(request.getId(), true);
      }

   }

   private void notifyUpdateSuccess(DeploymentRequest request) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Update deployment context succeeded for " + request);
      }

      this.deploymentService.notifyContextUpdated(request.getId(), this.callbackHandlerId);
   }

   private void notifyUpdateFailure(DeploymentRequest request, Exception e) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Update deployment context failed for " + request + " with exception:", e);
      }

      this.deploymentService.notifyContextUpdateFailed(request.getId(), this.callbackHandlerId, e);
   }

   private void notifyPrepareSuccess(DeploymentRequest request, boolean requiresRestart) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Prepare succeeded for " + request);
      }

      boolean savedPendingCommit = false;
      Iterator changes;
      if (requiresRestart) {
         changes = request.getDeployments();
         changes.next();
         if (changes.hasNext()) {
            this.savePendingCommitState();
            savedPendingCommit = true;
         }
      }

      this.deploymentService.notifyPrepareSuccess(request.getId(), this.callbackHandlerId);
      if (requiresRestart) {
         this.deploymentService.notifyStatusUpdate(request.getId(), this.callbackHandlerId, "COMMIT_PENDING");
         if (!savedPendingCommit) {
            changes = this.currChangeDescriptors != null ? this.currChangeDescriptors.iterator() : null;

            while(changes != null && changes.hasNext()) {
               ChangeDescriptor change = (ChangeDescriptor)changes.next();
               if (change.getChangeOperation().equals("delete")) {
                  this.dataObject.deleteFile(change.getChangeTarget(), this.currRequestId);
               }
            }

            this.dataObject.closeDataUpdate(this.currRequestId, true);
         }

         this.resetState();
      }

   }

   private void notifyPrepareFailure(DeploymentRequest request, Exception e) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Prepare failed for " + request + " with exception:", e);
      }

      this.removeFromRestartList(request);
      this.deploymentService.notifyPrepareFailure(request.getId(), this.callbackHandlerId, e);
   }

   private void notifyCommitSuccess(DeploymentRequest request) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Commit succeeded for " + request);
      }

      this.deploymentService.notifyCommitSuccess(request.getId(), this.callbackHandlerId);
   }

   private void notifyCommitFailure(DeploymentRequest request, Exception e) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Commit failed for " + request + " with exception:", e);
      }

      this.deploymentService.notifyCommitFailure(request.getId(), this.callbackHandlerId, e);
   }

   private void resetState() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Resetting state for id: " + this.currRequestId);
      }

      this.lastRequestId = this.currRequestId;
      this.setCurrentRequestId(-1L);
      this.currRequestTree = null;
      this.currChangeDescriptors = null;
      this.proposedConfigTree = null;
      this.proposedExternalTrees = null;
      this.prepareCalled = false;
      this.proposedConfigChanges = null;
   }

   private void setCurrentRequestId(long requestId) {
      this.currRequestId = requestId;
   }

   private String getLockFileName() {
      return "config/config.lok";
   }

   private boolean updateFiles(long requestId, Deployment deployment) throws ManagementException {
      boolean var7;
      try {
         DomainMBean proposed = null;
         DomainMBean current = null;
         this.downloadFiles(requestId, deployment);
         String configFilePath = "config" + File.separator + BootStrap.getDefaultConfigFileName();
         if (this.dataObject.getTemporaryFiles().get(configFilePath) != null) {
            proposed = (DomainMBean)DescriptorManagerHelper.loadDescriptor((String)this.dataObject.getTemporaryFiles().get(configFilePath), false, false, (List)null).getRootBean();
            this.calloutManager.init(proposed);
         } else {
            current = (DomainMBean)DescriptorManagerHelper.loadDescriptor(BootStrap.getConfigFile().getAbsolutePath(), false, false, (List)null).getRootBean();
            this.calloutManager.init(current);
         }

         if (!this.calloutManager.abortForExpectedChangeCallout("boot")) {
            if (deployment.getDeploymentType() == DeploymentType.CONFIGURATION) {
               try {
                  if (current == null) {
                     current = (DomainMBean)DescriptorManagerHelper.loadDescriptor(BootStrap.getConfigFile().getAbsolutePath(), false, false, (List)null).getRootBean();
                  }

                  if (current != null && proposed != null && this.calloutManager.abortForChangeReceivedCallout("boot", current, proposed)) {
                     this.dataObject.cancelDataUpdate(requestId);
                     var7 = false;
                     return var7;
                  }
               } catch (FileNotFoundException var13) {
                  debugLogger.debug("config.xml is not available, booting will continue, any Callouts configured will be ignored", var13);
               } catch (Exception var14) {
                  debugLogger.debug("Issue when loading config.xml, booting will continue, any Callouts configured will be ignored", var14);
               }
            }

            this.dataObject.commitDataUpdate();
            var7 = true;
            return var7;
         }

         this.dataObject.cancelDataUpdate(requestId);
         var7 = false;
      } catch (Throwable var15) {
         this.dataObject.cancelDataUpdate(requestId);
         if (var15 instanceof ManagementException) {
            throw (ManagementException)var15;
         }

         throw new ManagementException("Unable to Update Files for " + requestId, var15);
      } finally {
         this.dataObject.closeDataUpdate(requestId, true);
      }

      return var7;
   }

   private void downloadFiles(final long requestId, Deployment deployment) throws ManagementException {
      Iterator changes = deployment.getChangeDescriptors().iterator();
      if (changes != null && changes.hasNext()) {
         final List updateFilePaths = new ArrayList();
         final List targetFilePaths = new ArrayList();

         while(true) {
            ChangeDescriptor eachChange;
            String sourceFilePath;
            do {
               if (!changes.hasNext()) {
                  int itercnt = 0;
                  boolean success = false;

                  do {
                     try {
                        this.dataObject.initDataUpdate(new DataUpdateRequestInfo() {
                           public List getDeltaFiles() {
                              return updateFilePaths;
                           }

                           public List getTargetFiles() {
                              return targetFilePaths;
                           }

                           public long getRequestId() {
                              return requestId;
                           }

                           public boolean isStatic() {
                              return false;
                           }

                           public boolean isDelete() {
                              return false;
                           }

                           public boolean isPlanUpdate() {
                              return false;
                           }

                           public boolean isStaging() {
                              return false;
                           }

                           public boolean isPlanStaging() {
                              return false;
                           }
                        });
                        this.dataObject.prepareDataUpdate(deployment.getDataTransferHandlerType());
                        success = true;
                     } catch (RuntimeException var13) {
                        Loggable loggable = ManagementLogger.logConfigWriteRetryLoggable(var13);
                        loggable.log();

                        try {
                           Thread.currentThread();
                           Thread.sleep(1000L);
                        } catch (InterruptedException var12) {
                        }

                        ++itercnt;
                     }
                  } while(!success && itercnt < 2);

                  return;
               }

               eachChange = (ChangeDescriptor)changes.next();
               sourceFilePath = eachChange.getChangeSource();
            } while("delete".equals(eachChange.getChangeOperation()) && "non-wls".equals(eachChange.getIdentity()));

            updateFilePaths.add(sourceFilePath);
            targetFilePaths.add(eachChange.getChangeTarget());
         }
      }
   }

   private Descriptor handleConfigTreeLoad(DeploymentContext context, ChangeDescriptor change, Deployment deployment) throws IOException, DescriptorUpdateRejectedException {
      Descriptor proposedTree = null;
      String filePath = change.getChangeTarget();
      String oper = change.getChangeOperation();
      InputStream is = null;

      String esname;
      try {
         File fileObject = this.dataObject.getFileFor(context.getDeploymentRequest().getId(), filePath);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: processing change, target: " + filePath);
         }

         if (oper.equals("add") || oper.equals("update")) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("RuntimeDeploymentReceiver: loading tree from stream, uri: " + filePath + " deployment: " + deployment);
            }

            is = this.getInputStream(fileObject);
            DescriptorManager configDescMgr = DescriptorManagerHelper.getDescriptorManager(false);
            configDescMgr.setDescriptorCreationListener(new RuntimeAccessDescriptorCreationListener());
            String preferredPendingDirPath = null;
            if (deployment instanceof ConfigurationDeployment) {
               esname = ((ConfigurationDeployment)deployment).getEditSessionName();
               String espartition = ((ConfigurationDeployment)deployment).getPartitionName();
               if (esname != null && !"default".equals(esname) || espartition != null && !"DOMAIN".equals(espartition)) {
                  EditDirectoryManager dirManager = EditDirectoryManager.getDirectoryManager(espartition, esname);
                  preferredPendingDirPath = dirManager.getPendingDirectory();
               } else {
                  preferredPendingDirPath = null;
               }
            }

            try {
               ArrayList errs = new ArrayList();
               proposedTree = configDescMgr.createDescriptor(new ConfigReader(is), errs, true, preferredPendingDirPath);
               EditAccessImpl.checkErrors(filePath, errs);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("RuntimeDeploymentReceiver: created proposed tree from IS : ");
               }

               return proposedTree;
            } catch (XMLStreamException var28) {
               XMLStreamException e = var28;
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("RuntimeDeploymentReceiver: exception loading tree from stream, uri: " + filePath + " deployment: " + deployment, var28);
               }

               IOException ioe = new IOException(var28.getMessage());
               ioe.initCause(var28);
               throw ioe;
            } finally {
               configDescMgr.setDescriptorCreationListener((DescriptorCreationListener)null);
            }
         }

         DeploymentRequest request = context.getDeploymentRequest();
         Exception err = new IllegalArgumentException("Only update or add operations supported.");
         err.fillInStackTrace();
         this.notifyUpdateFailure(request, err);
         esname = null;
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var27) {
            }
         }

      }

      return esname;
   }

   private void handleExternalTreeLoad(long requestId, ChangeDescriptor change) throws IOException, DescriptorUpdateRejectedException {
      String filePath = change.getChangeTarget();
      String oper = change.getChangeOperation();
      InputStream is = null;

      try {
         Descriptor currTree = null;
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         currTree = runtimeAccess.getDomain().getDescriptor();
         if (oper.equals("update")) {
            is = this.getInputStream(this.dataObject.getFileFor(requestId, filePath));
            String proposedTree;
            if (debugLogger.isDebugEnabled()) {
               proposedTree = "RuntimeDeploymentReceiver: processing external tree load, target: " + filePath + " oper: " + oper;
               debugLogger.debug(proposedTree);
            }

            proposedTree = null;
            List descInfos = this.findDescriptorInfoForFileName(currTree, filePath);
            if (descInfos != null && descInfos.size() > 0) {
               DescriptorInfo descInfo = (DescriptorInfo)descInfos.get(0);
               DescriptorManager descMgr = descInfo.getDescriptorManager();
               ArrayList errs = new ArrayList();
               Descriptor proposedTree = descMgr.createDescriptor(new ConfigReader(is));
               EditAccessImpl.checkErrors(filePath, errs);
               this.proposedExternalTrees.put(filePath, proposedTree);
               this.addTemporaryTree(proposedTree, "updateDeploymentContext." + filePath);
               if (ManagementService.getPropertyService(kernelId).isAdminServer()) {
                  if (ProductionModeHelper.isProductionModePropertySet() || runtimeAccess.getDomain().isProductionModeEnabled()) {
                     DescriptorHelper.setDescriptorTreeProductionMode(proposedTree, ProductionModeHelper.getProductionModeProperty());
                  }
               } else if (ProductionModeHelper.isGlobalProductionModeSet() || runtimeAccess.getDomain().isProductionModeEnabled()) {
                  DescriptorHelper.setDescriptorTreeProductionMode(proposedTree, ProductionModeHelper.getGlobalProductionMode());
               }
            }
         }
      } catch (XMLStreamException var21) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: exception processing external tree load from stream, uri: " + filePath + " oper: " + oper, var21);
         }

         IOException ioe = new IOException(var21.getMessage());
         ioe.initCause(var21);
         throw ioe;
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var20) {
            }
         }

      }

   }

   private void handleExternalTreePrepare(DeploymentContext context, ChangeDescriptor change, Deployment deployment) throws IOException, DescriptorUpdateRejectedException {
      String filePath = change.getChangeTarget();
      String oper = change.getChangeOperation();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("RuntimeDeploymentReceiver: processing external prepare, target: " + filePath + " oper: " + oper);
      }

      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      Descriptor currTree = runtimeAccess.getDomain().getDescriptor();
      if (oper.equals("update")) {
         List descInfos = this.findDescriptorInfoForFileName(currTree, filePath);
         if (descInfos != null && descInfos.size() > 0) {
            Iterator var9 = descInfos.iterator();

            while(var9.hasNext()) {
               DescriptorInfo descInfo = (DescriptorInfo)var9.next();
               Descriptor proposedTree = (Descriptor)this.proposedExternalTrees.get(filePath);
               if (proposedTree != null) {
                  Descriptor externalTree = descInfo.getDescriptor();
                  this.prepareUpdate(externalTree, proposedTree, false);
               }
            }
         }
      } else if (oper.equals("add")) {
         DescriptorManager descMgr = DescriptorManagerHelper.getDescriptorManager(false);
         InputStream in = null;

         try {
            in = this.getInputStream(new File(DomainDir.getRootDir(), filePath));
            ArrayList errs = new ArrayList();
            descMgr.createDescriptor(new ConfigReader(in), errs, true);
            EditAccessImpl.checkErrors(filePath, errs);
         } catch (XMLStreamException var20) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("RuntimeDeploymentReceiver: exception processing external tree prepare from stream, uri: " + filePath + " oper: " + oper, var20);
            }

            IOException ioe = new IOException(var20.getMessage());
            ioe.initCause(var20);
            throw ioe;
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var19) {
               }
            }

         }
      }

   }

   private void handleExternalTreeCommit(String filePath, DeploymentContext context) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("RuntimeDeploymentReceiver: processing external commit, target: " + filePath);
      }

      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      Descriptor currTree = runtimeAccess.getDomain().getDescriptor();
      AuthenticatedSubject initiator = context.getDeploymentRequest().getInitiator();
      List descInfos = this.findDescriptorInfoForFileName(currTree, filePath);

      try {
         if (descInfos != null && descInfos.size() > 0) {
            Iterator var7 = descInfos.iterator();

            while(var7.hasNext()) {
               DescriptorInfo descInfo = (DescriptorInfo)var7.next();
               final Descriptor externalTree = descInfo.getDescriptor();

               try {
                  if (initiator != null) {
                     SecurityServiceManager.runAs(kernelId, initiator, new PrivilegedAction() {
                        public Object run() {
                           try {
                              RuntimeAccessDeploymentReceiverService.this.activateUpdate(externalTree);
                              return null;
                           } catch (DescriptorUpdateFailedException var2) {
                              throw new RuntimeException("Unable to activate the Tree", var2);
                           }
                        }
                     });
                  } else {
                     this.activateUpdate(externalTree);
                  }
               } catch (DescriptorUpdateFailedException var14) {
                  throw new RuntimeException("Failure Activating the Tree", var14);
               }
            }
         }
      } finally {
         if (this.descriptorLockHandle != null) {
            this.descriptorLockHandle.unlock();
            this.descriptorLockHandle = null;
         }

      }

   }

   private List findDescriptorInfoForFileName(Descriptor currTree, String filePath) {
      ArrayList infos = new ArrayList();
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(currTree);

      DescriptorInfo descInfo;
      ConfigurationExtensionMBean extBean;
      String fpath;
      String epath;
      String epathInConfig;
      while(it != null && it.hasNext()) {
         descInfo = (DescriptorInfo)it.next();
         extBean = descInfo.getConfigurationExtension();
         fpath = (new File(filePath)).getPath();
         epath = (new File(extBean.getDescriptorFileName())).getPath();
         if (fpath.endsWith(epath)) {
            epathInConfig = (new File("config/" + epath)).getPath();
            if (fpath.endsWith(epathInConfig)) {
               infos.add(descInfo);
            }
         }
      }

      it = DescriptorInfoUtils.getTransientDescriptorInfos(currTree);

      while(it != null && it.hasNext()) {
         descInfo = (DescriptorInfo)it.next();
         extBean = descInfo.getConfigurationExtension();
         fpath = (new File(filePath)).getPath();
         epath = (new File(extBean.getDescriptorFileName())).getPath();
         if (fpath.endsWith(epath)) {
            epathInConfig = (new File("config/" + epath)).getPath();
            if (fpath.endsWith(epathInConfig)) {
               infos.add(descInfo);
            }
         }
      }

      return infos;
   }

   private void addTemporaryTree(Object beanTree, String name) {
      this.temporaryTrees.put(beanTree, name + "(" + new Date() + ")");
   }

   private void savePendingCommitState() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Saving pending commit state info: " + this.currRequestId);
      }

      this.pendingCommitRequestId = this.currRequestId;
      this.pendingCommitRequestTree = this.currRequestTree;
      this.pendingChangeDescriptors = this.currChangeDescriptors;
   }

   private void resetPendingCommitState() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Reset pending commit state info: " + this.pendingCommitRequestId);
      }

      this.pendingCommitRequestId = -1L;
      this.pendingCommitRequestTree = null;
      this.pendingChangeDescriptors = null;
   }

   public void commitAnyPendingRequests() {
      if (this.pendingCommitRequestId != -1L) {
         Iterator changes = this.pendingChangeDescriptors != null ? this.pendingChangeDescriptors.iterator() : null;

         while(changes != null && changes.hasNext()) {
            ChangeDescriptor change = (ChangeDescriptor)changes.next();
            if (change.getChangeOperation().equals("delete")) {
               this.dataObject.deleteFile(change.getChangeTarget(), this.pendingCommitRequestId);
            }
         }

         this.dataObject.closeDataUpdate(this.pendingCommitRequestId, true);
         this.resetPendingCommitState();
      }

   }

   private boolean isPublishRequired(DescriptorDiff changes) {
      if (changes == null) {
         return false;
      } else {
         Iterator i = changes.iterator();

         while(true) {
            BeanUpdateEvent event;
            BeanUpdateEvent.PropertyUpdate[] updates;
            do {
               if (!i.hasNext()) {
                  return false;
               }

               event = (BeanUpdateEvent)i.next();
               updates = event.getUpdateList();
            } while(updates == null);

            BeanUpdateEvent.PropertyUpdate[] var5 = updates;
            int var6 = updates.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               BeanUpdateEvent.PropertyUpdate update = var5[var7];
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("PropertyUpdate update: " + update.toString());
                  debugLogger.debug("Source bean: " + event.getSourceBean());
               }

               Object removedObject;
               if (update.getUpdateType() == 2) {
                  if (event.getSourceBean() instanceof AppDeploymentMBean) {
                     return true;
                  }

                  if (event.getSourceBean() instanceof DomainMBean || event.getSourceBean() instanceof ServerMBean) {
                     removedObject = update.getAddedObject();
                     if (removedObject instanceof ServerMBean || removedObject instanceof AppDeploymentMBean || removedObject instanceof NetworkAccessPointMBean) {
                        return true;
                     }
                  }
               } else if (update.getUpdateType() == 3) {
                  if (event.getSourceBean() instanceof DomainMBean || event.getSourceBean() instanceof ServerMBean) {
                     removedObject = update.getRemovedObject();
                     if (removedObject instanceof ServerMBean || removedObject instanceof AppDeploymentMBean || removedObject instanceof NetworkAccessPointMBean) {
                        return true;
                     }
                  }
               } else {
                  DescriptorBean sourceBean = event.getSourceBean();
                  if (sourceBean instanceof ServerMBean) {
                     if ("Cluster".equals(update.getPropertyName()) || "ListenPortEnabled".equals(update.getPropertyName()) || "ListenPort".equals(update.getPropertyName()) || "ListenAddress".equals(update.getPropertyName()) || "ExternalDNSName".equals(update.getPropertyName())) {
                        return true;
                     }
                  } else if (sourceBean instanceof SSLMBean) {
                     if ("ListenPort".equals(update.getPropertyName()) || "Enabled".equals(update.getPropertyName())) {
                        return true;
                     }
                  } else if (sourceBean instanceof NetworkAccessPointMBean) {
                     if ("Protocol".equals(update.getPropertyName()) || "PublicPort".equals(update.getPropertyName()) || "PublicAddress".equals(update.getPropertyName()) || "ListenPort".equals(update.getPropertyName())) {
                        return true;
                     }
                  } else if (sourceBean instanceof ClusterMBean) {
                     if ("ClusterAddress".equals(update.getPropertyName())) {
                        return true;
                     }
                  } else if (sourceBean instanceof AppDeploymentMBean && "Targets".equals(update.getPropertyName())) {
                     return true;
                  }
               }
            }
         }
      }
   }

   private ConfigurationDeployment getConfigDeployment(DeploymentRequest request) {
      Iterator iterator = request.getDeployments(this.callbackHandlerId);
      if (iterator != null && iterator.hasNext()) {
         Deployment deployment = (Deployment)iterator.next();
         if (!ManagementService.getPropertyService(kernelId).isAdminServer() && deployment instanceof ConfigurationDeployment) {
            Set localFMWServerNames = DomainDir.getLocalServers();
            localFMWServerNames.add(this.getLocalServerName());
            ((ConfigurationDeployment)ConfigurationDeployment.class.cast(deployment)).narrowToManagedServers(localFMWServerNames);
         }

         if (iterator.hasNext()) {
            throw new IllegalArgumentException("Only one configuration deployment expected.");
         } else {
            return (ConfigurationDeployment)deployment;
         }
      } else {
         return null;
      }
   }

   private void commitCurrentTreeAndSaveRevertDiffs(Descriptor currTree, DeploymentContext context) throws DescriptorUpdateFailedException {
      DeploymentRequest request = context.getDeploymentRequest();
      Descriptor origTree = currTree;
      if (request.concurrentAppPrepareEnabled()) {
         origTree = (Descriptor)currTree.clone();
      }

      this.activateUpdate(currTree, (DescriptorPreNotifyProcessor)this.partitionResourceProcessorProvider.get());
      if (request.concurrentAppPrepareEnabled()) {
         DescriptorDiff revertDiff = currTree.computeDiff(origTree);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Reverting diff " + revertDiff);
         }

         context.addContextComponent("beanRevertDescriptorDiffId", revertDiff);
      }

   }

   private void revertCommittedConfigChanges(DescriptorDiff revertDiff, DeploymentContext context) throws Exception {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      Descriptor currTree = runtimeAccess.getDomain().getDescriptor();
      PartitionDiff partitionRevertDiff = new PartitionDiff(revertDiff);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Reverting partition diff " + partitionRevertDiff);
      }

      currTree.applyDiff(partitionRevertDiff);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Write the current tree with reverted changes to file");
      }

      DescriptorHelper.saveConfigDescriptorTree(currTree, DomainDir.getConfigDir(), (String)null);
   }

   private void prepareUpdate(Descriptor originalDescriptor, Descriptor proposedDescriptor, boolean failOnNonDynamicChanges) throws DescriptorUpdateRejectedException {
      if (this.descriptorLockHandle == null) {
         this.descriptorLockHandle = this.descriptorLock.lock(-1L);
      }

      originalDescriptor.prepareUpdate(proposedDescriptor, failOnNonDynamicChanges);
   }

   private DescriptorDiff prepareUpdateDiff(Descriptor originalDescriptor, Descriptor proposedDescriptor, boolean failOnNonDynamicChanges) throws DescriptorUpdateRejectedException {
      if (this.descriptorLockHandle == null) {
         this.descriptorLockHandle = this.descriptorLock.lock(-1L);
      }

      return originalDescriptor.prepareUpdateDiff(proposedDescriptor, failOnNonDynamicChanges);
   }

   private void activateUpdate(Descriptor originalDescriptor) throws DescriptorUpdateFailedException {
      originalDescriptor.activateUpdate();
   }

   private void activateUpdate(Descriptor originalDescriptor, DescriptorPreNotifyProcessor preNotifyProcessor) throws DescriptorUpdateFailedException {
      try {
         originalDescriptor.activateUpdate(preNotifyProcessor);
      } finally {
         if (this.descriptorLockHandle != null) {
            this.descriptorLockHandle.unlock();
            this.descriptorLockHandle = null;
         }

      }

   }

   private void rollbackUpdate(Descriptor originalDescriptor) {
      try {
         originalDescriptor.rollbackUpdate();
      } finally {
         if (this.descriptorLockHandle != null) {
            this.descriptorLockHandle.unlock();
            this.descriptorLockHandle = null;
         }

      }

   }

   private FileInputStream getInputStream(File file) throws IOException {
      FileInputStream fis = null;
      int cnt = 0;

      while(true) {
         try {
            fis = new FileInputStream(file);
            break;
         } catch (IOException var7) {
            if (cnt == 2) {
               throw var7;
            }

            try {
               Thread.currentThread();
               Thread.sleep(1000L);
            } catch (InterruptedException var6) {
            }

            ++cnt;
            if (fis != null) {
               break;
            }
         }
      }

      return fis;
   }

   public static Object getLockObject() {
      return singleton;
   }

   public void applySitConfigFilesToTemporaryTrees() throws IOException {
      SituationalConfigManager situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
      if (!situationalConfigManager.hasActiveConfiguration()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: do not apply any changes since no active files");
         }

      } else if (situationalConfigManager.isDeferringNonDynamicChanges()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeDeploymentReceiver: do not apply any changes since deferring non-dynamic changes ");
         }

      } else {
         situationalConfigManager.applyChangesToTemporaryDescriptors(this.proposedConfigTree, this.proposedExternalTrees);
      }
   }

   boolean abortDoCommitForCallout(boolean isHeartbeatRequest, DomainMBean current, DomainMBean proposed) {
      if (isHeartbeatRequest) {
         return this.calloutManager.abortForChangeReceivedCallout("poll", current, proposed);
      } else {
         return this.calloutManager.abortForExpectedChangeCallout("commit") || this.calloutManager.abortForChangeReceivedCallout("commit", current, proposed);
      }
   }

   private boolean isHearbeatRequest(DeploymentRequest deploymentRequest) {
      return deploymentRequest != null && deploymentRequest instanceof TargetRequestImpl ? ((TargetRequestImpl)deploymentRequest).isHeartbeatRequest() : false;
   }

   private class RuntimeAccessDescriptorCreationListener implements DescriptorCreationListener {
      private RuntimeAccessDescriptorCreationListener() {
      }

      public void descriptorCreated(Descriptor descriptor) {
         DescriptorInfoUtils.setDescriptorLoadExtensions(descriptor, false);
      }

      // $FF: synthetic method
      RuntimeAccessDescriptorCreationListener(Object x1) {
         this();
      }
   }
}
