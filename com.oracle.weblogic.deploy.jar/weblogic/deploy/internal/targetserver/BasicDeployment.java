package weblogic.deploy.internal.targetserver;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.AdminModeCallback;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.ModuleListener;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.deploy.event.VetoableDeploymentEvent;
import weblogic.deploy.internal.DeploymentType;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.datamanagement.AppData;
import weblogic.deploy.internal.targetserver.datamanagement.Data;
import weblogic.deploy.internal.targetserver.datamanagement.DataUpdateRequestInfo;
import weblogic.deploy.internal.targetserver.operations.AbstractOperation;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.ListenerFactory;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.ConfiguredDeployments;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class BasicDeployment {
   protected BasicDeploymentMBean deploymentMBean;
   protected final String name;
   protected final String appId;
   private File localAppFileOrDir;
   private AppContainerInvoker appctrInvoker;
   protected boolean isStaticDeployment = false;
   private GracefulAdminModeHandler gracefulAdminModeHandler = new GracefulAdminModeHandler();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected final ServerMBean server;
   protected final weblogic.application.DeploymentManager deploymentManager = weblogic.application.DeploymentManager.getDeploymentManager();
   protected AbstractOperation task;
   private DeploymentState state;
   private DeploymentState savedState;
   private ModuleListener ml;
   private Data localData = null;
   private File relativePlan = null;
   private File relativeAltDescriptor = null;
   private boolean deferredRemoveDeloyment = false;
   private boolean removedLocalData = false;
   protected boolean isMSID = false;
   private boolean localDataChanged = false;
   protected boolean isDeployFromPartitionLifecycle = false;
   private boolean serverLifecycleDeploymentFailure = false;

   public BasicDeployment(BasicDeploymentMBean dmbean, ServerMBean server) {
      this.deploymentMBean = dmbean;
      this.server = server;
      this.name = ApplicationVersionUtils.getDisplayName(this.deploymentMBean);
      this.appId = dmbean.getName();
      this.init();
   }

   private void init() {
      this.localData = this.createLocalData();
      this.localAppFileOrDir = this.localData.getSourceFile();
      if (ConfiguredDeployments.getConfigureDeploymentsHandler().getMultiVersionDeployments().getConfiguredAppId(this.appId) != null) {
         this.isMSID = true;
      }

      this.dump();
      this.localDataChanged = true;
   }

   private void initLocalAppFileOrDir() {
      if (this.localAppFileOrDir == null || this.localDataChanged) {
         this.localAppFileOrDir = this.localData.getSourceFile();
         if (this.localData instanceof AppData) {
            AppData appData = (AppData)this.localData;

            try {
               String relativePlanPath = appData.getRelativePlanPath();
               if (relativePlanPath != null) {
                  File relFile = new File(relativePlanPath);
                  if (!relFile.isAbsolute()) {
                     this.relativePlan = new File(this.localAppFileOrDir.getParentFile(), appData.getRelativePlanPath());
                  } else {
                     this.relativePlan = relFile;
                  }
               }

               String relativeAltDescriptorPath = appData.getRelativeAltDescriptorPath();
               if (relativeAltDescriptorPath != null) {
                  File relFile = new File(relativeAltDescriptorPath);
                  if (!relFile.isAbsolute()) {
                     this.relativeAltDescriptor = new File(this.localAppFileOrDir.getParentFile(), appData.getRelativeAltDescriptorPath());
                  } else {
                     this.relativeAltDescriptor = relFile;
                  }
               }
            } catch (IOException var5) {
               throw new RuntimeException(var5);
            }
         }

         this.localDataChanged = false;
      }

   }

   public void setStatic() {
      this.isStaticDeployment = true;
   }

   public void dump() {
      if (isDebugEnabled()) {
         this.initLocalAppFileOrDir();
         debug("Basic Deployment name: " + this.name);
         debug("Staging location: " + this.localAppFileOrDir);
         debug("MSID style deployment: " + this.isMSID);
      }

   }

   public String getName() {
      return this.name;
   }

   public abstract void verifyAppVersionSecurity(AbstractOperation var1) throws DeploymentException;

   public void setTask(AbstractOperation task) {
      if (this.task != null) {
         this.task = task;
      }

   }

   public AbstractOperation getTask() {
      return this.task;
   }

   public Deployment findDeployment() {
      return this.appctrInvoker;
   }

   public void verifyLocalApp() throws DeploymentException {
      this.init();
      this.initLocalAppFileOrDir();
      if (!this.localAppFileOrDir.exists() && this.relativePlan != null && !this.relativePlan.exists() || this.relativeAltDescriptor != null && !this.relativeAltDescriptor.exists()) {
         String errorName;
         if (!this.localAppFileOrDir.exists()) {
            errorName = this.localAppFileOrDir.toString();
         } else if (this.relativePlan != null && !this.relativePlan.exists()) {
            errorName = this.relativePlan.toString();
         } else {
            errorName = this.relativeAltDescriptor.toString();
         }

         String msg = DeployerRuntimeLogger.noAppFilesExist(errorName);
         throw new DeploymentException(DeployerRuntimeLogger.logInvalidSourceLoggable(this.localAppFileOrDir.getAbsolutePath(), this.getName(), msg).getMessage());
      }
   }

   public Deployment createDeployment(weblogic.application.DeploymentManager.DeploymentCreator deploymentCreator, BasicDeploymentMBean mbean, DeploymentState s) throws DeploymentException {
      this.initLocalAppFileOrDir();
      if (isDebugEnabled()) {
         debug("Creating Deployment with Path " + this.localAppFileOrDir);
      }

      this.verifyLocalApp();
      if (deploymentCreator == null) {
         throw new DeploymentException("Deployment could not be created. Deployment creator is null.");
      } else {
         this.appctrInvoker = new AppContainerInvoker(deploymentCreator.createDeployment(mbean, this.localAppFileOrDir), mbean, s);
         this.setStateRef(s);
         return this.appctrInvoker;
      }
   }

   public abstract void removeDeployment();

   public abstract void prepare() throws IOException, DeploymentException;

   private void activate(DeploymentContext deploymentContext) throws DeploymentException {
      try {
         Deployment deployment = this.findDeployment();
         if (deployment != null) {
            if (!TargetHelper.isTargetedLocaly(this.deploymentMBean)) {
               if (isDebugEnabled()) {
                  debug("server not targeted");
               }

               return;
            }

            if (isDebugEnabled()) {
               debug("Activate for app=" + this.name);
            }

            if (deploymentContext == null) {
               if (this.task != null && this.task.getDeploymentContext() != null) {
                  deploymentContext = this.task.getDeploymentContext();
               } else {
                  deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
               }
            }

            deployment.activate((DeploymentContext)deploymentContext);
         }

      } catch (Throwable var5) {
         Exception targetStatusException = DeployHelper.convertThrowable(var5);
         SlaveDeployerLogger.logSetActivationStateFailedLoggable(this.name, true, targetStatusException).log();
         throw (DeploymentException)targetStatusException;
      }
   }

   public void gracefulProductionToAdmin(Deployment deployment, DeploymentContext deploymentContext) throws DeploymentException {
      if (DeployHelper.isActiveState(deployment)) {
         AdminModeCallback callback = this.gracefulAdminModeHandler.create();
         if (callback != null) {
            if (deploymentContext == null) {
               if (this.task != null && this.task.getDeploymentContext() != null) {
                  deploymentContext = this.task.getDeploymentContext();
               } else {
                  deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
               }
            }

            ((DeploymentContextImpl)deploymentContext).setAdminModeCallback(callback);

            try {
               deployment.gracefulProductionToAdmin((DeploymentContext)deploymentContext);
               callback.waitForCompletion(0L);
            } finally {
               if (!this.gracefulAdminModeHandler.isInterrupted()) {
                  this.gracefulAdminModeHandler.remove(callback);
               }

            }

         }
      }
   }

   public void forceProductionToAdmin(Deployment deployment, long timeoutSecs, DeploymentContext deploymentContext) throws DeploymentException {
      if (DeployHelper.isActiveState(deployment) || DeployHelper.isAdminState(deployment)) {
         this.gracefulAdminModeHandler.notifyPending();
         if (timeoutSecs > 0L) {
            AdminModeCallback callback = new AdminModeCallbackImpl();
            if (deploymentContext == null) {
               if (this.task != null && this.task.getDeploymentContext() != null) {
                  deploymentContext = this.task.getDeploymentContext();
               } else {
                  deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
               }
            }

            ((DeploymentContextImpl)deploymentContext).setAdminModeCallback(callback);
            deployment.forceProductionToAdmin((DeploymentContext)deploymentContext);
            callback.waitForCompletion(timeoutSecs);
         } else {
            ((DeploymentContextImpl)deploymentContext).setAdminModeCallback(Deployment.noopAdminModeCallback);
            deployment.forceProductionToAdmin((DeploymentContext)deploymentContext);
         }

      }
   }

   private void deactivate(DeploymentContext deploymentContext) {
      try {
         Deployment deployment = this.findDeployment();
         if (deployment != null && DeployHelper.isAdminState(deployment)) {
            if (isDebugEnabled()) {
               debug("Deactivating base deployment " + this.name);
            }

            if (this.isMSID) {
               ((DeploymentContextImpl)deploymentContext).setDeploymentOperation(8);
            }

            deployment.deactivate(deploymentContext);
         }
      } catch (Throwable var5) {
         Exception targetStatusException = DeployHelper.convertThrowable(var5);
         SlaveDeployerLogger.logSetActivationStateFailedLoggable(this.name, false, targetStatusException).log();
      }

   }

   public void unprepare() {
      if (isDebugEnabled()) {
         debug(" unpreparing application  - " + this.name);
      }

      Deployment deployment = this.findDeployment();
      if (deployment != null && DeployHelper.isPreparedState(deployment)) {
         try {
            DeploymentContextImpl deploymentContext;
            if (this.task != null && this.task.getDeploymentContext() != null) {
               deploymentContext = this.task.getDeploymentContext();
            } else {
               deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
               deploymentContext.setStaticDeploymentOperation(true);
            }

            if (this.isMSID) {
               deploymentContext.setDeploymentOperation(13);
            }

            deployment.unprepare(deploymentContext);
         } catch (Throwable var3) {
            SlaveDeployerLogger.logUnprepareFailed(this.name, DeployHelper.convertThrowable(var3));
         }
      }

      if (isDebugEnabled()) {
         debug(" unprepared application  - " + this.name);
      }

      this.isDeployFromPartitionLifecycle = false;
   }

   public void remove() {
      this.remove(true);
   }

   private void removeFiles() {
      if (!this.removedLocalData) {
         this.removedLocalData = true;
         boolean removedSuccessfully = this.removeStagedFiles();
         if (!removedSuccessfully) {
            this.initLocalAppFileOrDir();
            Loggable l = SlaveDeployerLogger.logRemoveStagedFilesFailedLoggable(this.name, this.localAppFileOrDir.toString());
            l.log();
         }
      }

   }

   public void remove(boolean removeStagedFiles) {
      try {
         if (removeStagedFiles) {
            Deployment deployment = this.findDeployment();
            if (deployment != null && !this.appctrInvoker.isRemoved()) {
               try {
                  if (this.task == null || this.task.getDeploymentContext() == null) {
                     DeploymentContextImpl deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
                     deployment.assertUndeployable();
                     deployment.remove(deploymentContext);
                  }
               } catch (Throwable var4) {
                  SlaveDeployerLogger.logOperationFailed("remove", this.name, DeployHelper.convertThrowable(var4));
               }
            }

            this.removeFiles();
            if (isDebugEnabled()) {
               debug(" removing basic deployment  - " + this.name);
            }

            if (!this.deferredRemoveDeloyment) {
               this.removeDeployment();
               OrderedDeployments.removeDeployment(this.deploymentMBean);
            }

            if (this.appctrInvoker != null && !this.appctrInvoker.isRemoved()) {
               if (!this.deferredRemoveDeloyment) {
                  this.deferredRemoveDeloyment = true;
                  OrderedDeployments.deferRemoveBasicDeployment(this.deploymentMBean);
               }
            } else if (this.deferredRemoveDeloyment) {
               OrderedDeployments.removeDeferredBasicDeployment(this.deploymentMBean);
            } else {
               OrderedDeployments.removeBasicDeployment(this.deploymentMBean);
            }

            if (isDebugEnabled()) {
               debug(" removed basic deployment  - " + this.name);
            }
         }
      } catch (Throwable var5) {
         if (isDebugEnabled()) {
            debug("Unexpected exception while removing a deployment");
            var5.printStackTrace();
         }
      }

   }

   protected static void debug(String m) {
      Debug.deploymentDebug(m);
   }

   protected static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public boolean isInternalApp() {
      return this.deploymentMBean instanceof AppDeploymentMBean && ((AppDeploymentMBean)this.deploymentMBean).isInternalApp();
   }

   public BasicDeploymentMBean getDeploymentMBean() {
      return this.deploymentMBean;
   }

   public void activateFromServerLifecycle() throws Exception {
      if (this.deploymentMBean instanceof AppDeploymentMBean && !DeployHelper.isOkToTransition((AppDeploymentMBean)this.deploymentMBean, this.server, "STATE_ADMIN")) {
         if (isDebugEnabled()) {
            debug("Not activating from server lifecycle because " + this.name + " is internal, is not targeted locally, or has a lower intended state than ADMIN");
         }

      } else {
         Deployment deployment = this.findDeployment();
         if (deployment != null) {
            try {
               DeploymentContextImpl deploymentContext;
               if (this.task != null && this.task.getDeploymentContext() != null) {
                  deploymentContext = this.task.getDeploymentContext();
               } else {
                  deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
               }

               deploymentContext.setAdminModeTransition(true);
               deploymentContext.setStaticDeploymentOperation(true);
               if (this.isMSID) {
                  deploymentContext.setDeploymentOperation(1);
               }

               this.startLifecycleStateManager();
               this.activate(deploymentContext);
            } catch (Exception var9) {
               if (isDebugEnabled()) {
                  Debug.deploymentDebug("Exception while activating application " + this.deploymentMBean.getName(), var9);
               }

               try {
                  this.unprepare();
               } catch (Throwable var8) {
               }

               if (this.getState() != null) {
                  this.getState().setCurrentState("STATE_FAILED", true);
               }

               this.serverLifecycleDeploymentFailure = true;
               throw var9;
            } finally {
               this.finishLifecycleStateManager();
            }

         }
      }
   }

   public void adminToProductionFromServerLifecycle() throws DeploymentException {
      if (this.deploymentMBean instanceof AppDeploymentMBean && !DeployHelper.isOkToTransition((AppDeploymentMBean)this.deploymentMBean, this.server, "STATE_ACTIVE")) {
         if (isDebugEnabled()) {
            debug("Not going to production because " + this.name + " is internal, is not targeted locally, or has a lower intended state than ACTIVE");
         }

      } else if (!this.serverLifecycleDeploymentFailure) {
         if (isDebugEnabled()) {
            debug("AdminToRunning for app=" + this.name);
         }

         Deployment deployment = this.findDeployment();
         if (deployment != null) {
            if (!TargetHelper.isTargetedLocaly(this.deploymentMBean)) {
               if (isDebugEnabled()) {
                  debug("server not targeted");
               }

            } else {
               DeploymentContextImpl deploymentContext;
               if (this.task != null && this.task.getDeploymentContext() != null) {
                  deploymentContext = this.task.getDeploymentContext();
               } else {
                  deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
               }

               deploymentContext.setStaticDeploymentOperation(true);
               if (this.isMSID) {
                  deploymentContext.setDeploymentOperation(1);
               }

               try {
                  this.startLifecycleStateManager();
                  deployment.adminToProduction(deploymentContext);
               } catch (Throwable var12) {
                  try {
                     this.deactivate(deploymentContext);
                  } catch (Throwable var11) {
                  }

                  try {
                     this.unprepare();
                  } catch (Throwable var10) {
                  }

                  this.removeDeployment();
                  DeploymentException de = DeployHelper.convertThrowable(var12);
                  SlaveDeployerLogger.logTransitionAppFromAdminToRunningFailed(ApplicationVersionUtils.getDisplayName(this.deploymentMBean), de);
                  throw de;
               } finally {
                  this.finishLifecycleStateManager();
               }

            }
         }
      }
   }

   public void productionToAdminFromServerLifecycle(boolean graceful) throws DeploymentException {
      if (isDebugEnabled()) {
         debug("RunningToAdmin for app=" + this.name + ", graceful=" + graceful);
      }

      Deployment deployment = this.findDeployment();
      if (deployment != null) {
         try {
            DeploymentContextImpl deploymentContext;
            if (this.task != null && this.task.getDeploymentContext() != null) {
               deploymentContext = this.task.getDeploymentContext();
            } else {
               deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
            }

            deploymentContext.setAdminModeTransition(true);
            deploymentContext.setStaticDeploymentOperation(true);
            if (this.isMSID) {
               deploymentContext.setDeploymentOperation(8);
            }

            this.startLifecycleStateManager();
            if (graceful) {
               this.gracefulProductionToAdmin(deployment, deploymentContext);
            } else {
               this.forceProductionToAdmin(deployment, 0L, deploymentContext);
            }
         } catch (Throwable var8) {
            DeploymentException de = DeployHelper.convertThrowable(var8);
            SlaveDeployerLogger.logTransitionAppFromRunningToAdminFailed(ApplicationVersionUtils.getDisplayName(this.deploymentMBean), de);
            throw de;
         } finally {
            this.finishLifecycleStateManager();
         }

      }
   }

   public void deactivateFromServerLifecycle() throws Exception {
      Deployment deployment = this.findDeployment();
      if (deployment != null) {
         try {
            DeploymentContextImpl deploymentContext;
            if (this.task != null && this.task.getDeploymentContext() != null) {
               deploymentContext = this.task.getDeploymentContext();
            } else {
               deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
            }

            deploymentContext.setAdminModeTransition(true);
            deploymentContext.setStaticDeploymentOperation(true);
            this.startLifecycleStateManager();
            this.deactivate(deploymentContext);
         } catch (Exception var9) {
            try {
               this.unprepare();
            } catch (Throwable var8) {
            }

            throw var9;
         } finally {
            this.finishLifecycleStateManager();
         }

      }
   }

   public void retireFromServerLifecycle() throws DeploymentException {
      try {
         this.startLifecycleStateManager();
         this.getState().setCurrentState("STATE_RETIRED", AppRuntimeStateManager.getManager().getModuleIds(this.appId));
         DeploymentContextImpl deploymentContext;
         if (this.task != null && this.task.getDeploymentContext() != null) {
            deploymentContext = this.task.getDeploymentContext();
         } else {
            deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
         }

         if (this.isMSID) {
            deploymentContext.setDeploymentOperation(13);
         }
      } finally {
         this.finishLifecycleStateManager();
      }

   }

   public void unprepareFromServerLifecycle() throws Exception {
      this.unprepare();
   }

   public void startLifecycleStateManager() {
      if (this.deploymentMBean instanceof AppDeploymentMBean) {
         if (!((AppDeploymentMBean)this.deploymentMBean).isInternalApp()) {
            this.setStateRef(new DeploymentState(this.deploymentMBean.getName(), "__Lifecycle_taskid__", 0));
            this.ml = ListenerFactory.createListener(this.getDeploymentMBean(), "__Lifecycle_taskid__", this.state);
            this.deploymentManager.addModuleListener(this.ml);
         }
      }
   }

   public void finishLifecycleStateManager() {
      if (this.ml != null) {
         this.deploymentManager.removeModuleListener(this.ml);
      }

      this.relayState();
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         this.savedState = this.state;
      }

      this.setStateRef((DeploymentState)null);
      this.ml = null;
   }

   protected final void relayState() {
      if (this.state != null) {
         if (isDebugEnabled()) {
            debug("Relaying updated state for app, " + this.state.getId() + " to " + this.state.getCurrentState());
         }

         this.state.setTarget(TargetUtils.findLocalTarget(this.deploymentMBean, this.server).getName());
         if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            AppRuntimeStateManager.getManager().updateState(this.state.getId(), this.state);
         }

         if (!this.isMSID) {
            DeploymentManager.getInstance().relayStatus(-1L, this.state);
         }

      }
   }

   protected final void setStagingOnRuntimeState(int stagingState) {
      try {
         AppRuntimeStateManager.getManager().setStagingState(this.deploymentMBean.getName(), new String[]{this.server.getName()}, stagingState, this.isInternalApp());
      } catch (ManagementException var3) {
         var3.printStackTrace();
      }

   }

   public final void relayStagingState(int stagingState) {
      this.setStagingOnRuntimeState(stagingState);
      DeploymentState theState = this.getState();
      if (theState != null) {
         theState.setStagingState(stagingState);
         this.relayState();
      }

   }

   public boolean needRetirement() {
      this.gracefulAdminModeHandler.remove();
      if (isDebugEnabled()) {
         debug("needRetirement for " + this.getName() + "=" + !this.gracefulAdminModeHandler.isInterrupted());
      }

      return !this.gracefulAdminModeHandler.isInterrupted();
   }

   public boolean hasPendingGraceful() {
      return this.gracefulAdminModeHandler.hasPending();
   }

   public boolean isGracefulInterrupted() {
      return this.gracefulAdminModeHandler.isInterrupted();
   }

   public final File getLocalAppFileOrDir() {
      this.initLocalAppFileOrDir();
      return this.localAppFileOrDir;
   }

   public void resetMBean(BasicDeploymentMBean mbean) {
      this.deploymentMBean = mbean;
   }

   public void setStateRef(DeploymentState deploymentState) {
      this.state = deploymentState;
      AppContainerInvoker a = (AppContainerInvoker)this.findDeployment();
      if (a != null) {
         a.setStateRef(this.state);
      }

   }

   protected void setState(DeploymentState state) {
      this.state = state;
   }

   public DeploymentState getState() {
      return this.state;
   }

   public DeploymentState getSavedState() {
      return this.savedState;
   }

   protected void fireVetoableDeploymentEvent() throws Throwable {
      try {
         BasicDeploymentMBean mbean = this.getDeploymentMBean();
         if (mbean != null) {
            DeploymentEventManager.sendVetoableDeploymentEvent(VetoableDeploymentEvent.create(this, VetoableDeploymentEvent.APP_DEPLOY, mbean, true, false, (String[])null, TargetHelper.getTargetNames(mbean.getTargets())));
         }
      } catch (ManagementException var2) {
         throw ManagementException.unWrapExceptions(var2);
      }
   }

   public final int getStagingState() {
      TargetMBean tmb = TargetUtils.findLocalTarget(this.deploymentMBean, this.server);
      int stagingState = AppRuntimeStateManager.getManager().getStagingState(this.deploymentMBean.getName(), tmb.getName());
      if (tmb instanceof ClusterMBean && stagingState != 0) {
         if (isDebugEnabled()) {
            debug(" Trying to find Staging State On Application '" + this.deploymentMBean.getName() + "' for target : " + this.server.getName());
         }

         stagingState = AppRuntimeStateManager.getManager().getStagingState(this.deploymentMBean.getName(), this.server.getName());
      }

      if (isDebugEnabled()) {
         debug(" Staging State On Application '" + this.deploymentMBean.getName() + "' : " + stagingState);
      }

      return stagingState;
   }

   public ApplicationRuntimeState getAppRuntimeState() {
      return AppRuntimeStateManager.getManager().get(this.appId);
   }

   public final void initDataUpdate(DataUpdateRequestInfo requestInfo) {
      weblogic.utils.Debug.assertion(this.localData != null);
      this.localData.initDataUpdate(requestInfo);
   }

   public final void prepareDataUpdate(String handlerType) throws DeploymentException {
      this.localData.prepareDataUpdate(handlerType);
   }

   public final void commitDataUpdate() throws DeploymentException {
      this.localData.commitDataUpdate();
   }

   public void cancelDataUpdate(long requestId) {
      this.localData.cancelDataUpdate(requestId);
   }

   public void closeDataUpdate(long requestId, boolean success) {
      this.localData.closeDataUpdate(requestId, success);
   }

   public abstract void updateDescriptorsPathInfo();

   protected final Data getLocalData() {
      return this.localData;
   }

   protected abstract Data createLocalData();

   protected void stageFilesForStatic() throws DeploymentException {
      this.initDataUpdate(new DataUpdateRequestInfo() {
         public List getDeltaFiles() {
            return new ArrayList();
         }

         public List getTargetFiles() {
            return new ArrayList();
         }

         public long getRequestId() {
            return 0L;
         }

         public boolean isStatic() {
            return true;
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
      this.prepareDataUpdate((String)null);
      this.commitDataUpdate();
   }

   public boolean isAppStaged() {
      return this.localData.isStagingEnabled();
   }

   public boolean removeStagedFiles() {
      return this.localData.removeStagedFiles();
   }

   public final long getArchiveTimeStamp() {
      if (isDebugEnabled()) {
         debug(" Getting archive timestamp for app " + this.appId);
      }

      ApplicationRuntimeState ars = AppRuntimeStateManager.getManager().get(this.appId);
      if (ars != null && ars.getDeploymentVersion() != null) {
         long ts = ars.getDeploymentVersion().getArchiveTimeStamp();
         if (isDebugEnabled()) {
            debug(" Returning archive timestamp for app " + this.appId + " = " + ts);
         }

         return ts;
      } else {
         return 0L;
      }
   }

   public final long getPlanTimeStamp() {
      if (isDebugEnabled()) {
         debug(" Getting plan timestamp for app " + this.appId);
      }

      ApplicationRuntimeState ars = AppRuntimeStateManager.getManager().get(this.appId);
      if (ars != null && ars.getDeploymentVersion() != null) {
         long ts = ars.getDeploymentVersion().getPlanTimeStamp();
         if (isDebugEnabled()) {
            debug(" Returning plan timestamp for app " + this.appId + " = " + ts);
         }

         return ts;
      } else {
         return 0L;
      }
   }

   public void setIsDeployFromPartitionLifecycle(boolean b) {
      this.isDeployFromPartitionLifecycle = b;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.appId);
      if (this.deploymentMBean != null) {
         sb.append(", type=");
         DeploymentType type = DeploymentType.findType(this.deploymentMBean);
         sb.append(type != null ? type.toString() : "Unknown");
         sb.append(", deployment-order=");
         sb.append(this.deploymentMBean.getDeploymentOrder());
      }

      return sb.toString();
   }

   private class GracefulAdminModeHandler {
      private AdminModeCallbackImpl pending;
      private boolean interrupted;

      private GracefulAdminModeHandler() {
         this.interrupted = false;
      }

      private synchronized AdminModeCallback create() {
         if (this.pending != null) {
            return this.pending;
         } else {
            this.pending = BasicDeployment.this.new AdminModeCallbackImpl();
            return this.pending;
         }
      }

      private synchronized void remove(AdminModeCallback callback) {
         if (callback == this.pending) {
            this.pending = null;
         }

      }

      private synchronized void remove() {
         this.pending = null;
      }

      private synchronized void notifyPending() {
         if (this.pending != null) {
            if (BasicDeployment.isDebugEnabled()) {
               BasicDeployment.debug("GracefulAdminModeHandler.notifyPending for: " + BasicDeployment.this.getName());
            }

            this.interrupted = true;
            this.pending.stop();
         }
      }

      private synchronized boolean hasPending() {
         return this.pending != null;
      }

      private synchronized boolean isInterrupted() {
         return this.interrupted;
      }

      // $FF: synthetic method
      GracefulAdminModeHandler(Object x1) {
         this();
      }
   }

   private class AdminModeCallbackImpl implements AdminModeCallback {
      private boolean completed;

      private AdminModeCallbackImpl() {
         this.completed = false;
      }

      public synchronized void stop() {
         this.notify();
      }

      public synchronized void completed() {
         this.completed = true;
         this.notify();
      }

      public synchronized void waitForCompletion(long timeoutSecs) {
         if (!this.completed) {
            try {
               this.wait(timeoutSecs * 1000L);
               if (BasicDeployment.isDebugEnabled()) {
                  BasicDeployment.debug("AdminModeCallback.waitForCompletion done for: " + BasicDeployment.this.getName());
               }
            } catch (InterruptedException var4) {
               if (BasicDeployment.isDebugEnabled()) {
                  BasicDeployment.debug("AdminModeCallback.waitForCompletion interrupted for: " + BasicDeployment.this.getName());
               }
            }

         }
      }

      // $FF: synthetic method
      AdminModeCallbackImpl(Object x1) {
         this();
      }
   }
}
