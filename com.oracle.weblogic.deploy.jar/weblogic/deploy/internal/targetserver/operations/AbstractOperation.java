package weblogic.deploy.internal.targetserver.operations;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.Collections;
import java.util.Set;
import weblogic.application.Deployment;
import weblogic.application.ModuleListener;
import weblogic.application.NonFatalDeploymentException;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.event.BaseDeploymentEvent;
import weblogic.deploy.event.DeploymentEvent;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.deploy.event.VetoableDeploymentEvent;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.DeploymentContextImpl;
import weblogic.deploy.internal.targetserver.DeploymentManager;
import weblogic.deploy.internal.targetserver.OrderedDeployments;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.ListenerFactory;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.DeploymentServerService;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class AbstractOperation {
   private boolean completed;
   private static final AuthenticatedSubject kernelId;
   protected static final ServerMBean server;
   private ModuleListener ml;
   protected static final String serverName;
   protected static final ClusterMBean cluster;
   protected static final DeploymentManager deploymentManager;
   protected final long requestId;
   protected final String taskId;
   protected final InternalDeploymentData internalDeploymentData;
   protected final DeploymentData deploymentData;
   protected final BasicDeployment app;
   protected int operation;
   protected Deployment appcontainer;
   protected final BasicDeploymentMBean mbean;
   protected DomainMBean proposedDomain;
   protected static final weblogic.application.DeploymentManager appctrManager;
   protected weblogic.application.DeploymentManager.DeploymentCreator deploymentCreator;
   private DeploymentState state;
   protected AuthenticatedSubject initiator;
   protected DeploymentContextImpl deploymentContext;
   protected boolean requiresRestart;
   protected boolean controlOperation;
   static final long serialVersionUID = 2603215776716483992L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.deploy.internal.targetserver.operations.AbstractOperation");
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Complete_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Abstract_Operation_Before_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   protected AbstractOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean depMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean requiresRestart) throws DeploymentException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[8];
            Object[] var10001 = var11.args;
            var10001[0] = this;
            var10001[1] = InstrumentationSupport.convertToObject(requestId);
            var10001[2] = taskId;
            var10001[3] = internalDeploymentData;
            var10001[4] = depMBean;
            var10001[5] = proposedDomain;
            var10001[6] = initiator;
            var10001[7] = InstrumentationSupport.convertToObject(requiresRestart);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.process(var11);
         var11.resetPostBegin();
      }

      this.requiresRestart = false;
      this.controlOperation = false;
      this.requestId = requestId;
      this.taskId = taskId;
      if (this.isRequireBasicDeploymentMBean() && depMBean == null) {
         DeploymentException de = new DeploymentException("Attempt to create " + this.getClass().getName() + " with a null BasicDeploymentMBean. RequestId:'" + requestId + "' and taskId: '" + taskId + "'");
         this.complete(2, de);
         throw de;
      } else if (!DeploymentServerService.isStarted()) {
         Loggable l = DeployerRuntimeLogger.logDeploymentServiceNotStartedLoggable(ApplicationVersionUtils.getDisplayName(depMBean), taskId);
         DeploymentException ex = new DeploymentException(l.getMessage());
         this.complete(2, ex);
         throw ex;
      } else {
         this.internalDeploymentData = internalDeploymentData;
         this.mbean = depMBean;
         this.deploymentData = internalDeploymentData.getExternalDeploymentData();
         this.proposedDomain = proposedDomain;
         this.initiator = initiator;
         this.requiresRestart = requiresRestart;
         if (this.deploymentContext == null) {
            this.deploymentContext = new DeploymentContextImpl(initiator);
            this.deploymentContext.setProposedDomain(proposedDomain);
            this.deploymentContext.setRequiresRestart(requiresRestart);
            this.deploymentContext.setResourceGroupTemplate(this.deploymentData.getResourceGroupTemplate());
            this.deploymentContext.setResourceGroup(this.deploymentData.getResourceGroup());
            this.deploymentContext.setSpecifiedTargetsOnly(this.deploymentData.getSpecifiedTargetsOnly());
         }

         this.deploymentContext.setUserSuppliedTargets(this.getTargets());
         this.app = this.createBasicDeployment(this.mbean);
         if (this.app != null) {
            this.app.setTask(this);
            this.app.resetMBean(this.mbean);
            this.deploymentContext.setAppStaged(this.app.isAppStaged());
         } else if (this.isRequireBasicDeploymentMBean() && this.isDebugEnabled()) {
            this.debug("BasicDeployment is null for " + depMBean.getName());
         }

      }
   }

   protected boolean isRequireBasicDeploymentMBean() {
      return true;
   }

   private void setTargetsFromConfig() {
      if (!this.deploymentData.isTargetsFromConfig()) {
         this.deploymentData.setTargetsFromConfig(this.deploymentData.getAllModuleTargets().isEmpty() && this.deploymentData.getAllSubModuleTargets().isEmpty());
      }

   }

   protected DeploymentState getState() {
      return this.state;
   }

   protected BasicDeployment createBasicDeployment(BasicDeploymentMBean mbean) {
      return OrderedDeployments.getOrCreateBasicDeployment(mbean);
   }

   public DomainMBean getProposedDomain() {
      return this.proposedDomain;
   }

   public DeploymentContextImpl getDeploymentContext() {
      return this.deploymentContext;
   }

   public long getRequestId() {
      return this.requestId;
   }

   protected DomainMBean getDomain() {
      return this.proposedDomain != null ? this.proposedDomain : ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   protected BasicDeploymentMBean getMBean() {
      return this.mbean;
   }

   public BasicDeploymentMBean getDeploymentMBean() {
      return this.mbean;
   }

   public int getNotificationLevel() {
      return this.internalDeploymentData != null ? this.internalDeploymentData.getNotificationLevel() : 1;
   }

   public InternalDeploymentData getInternalDeploymentData() {
      return this.internalDeploymentData;
   }

   protected void initDeploymentCreator() throws DeploymentException {
      try {
         this.deploymentCreator = appctrManager.getDeploymentCreator(this.mbean, (File)null);
      } catch (DeploymentException var3) {
         Throwable cause = var3.getCause();
         throw new DeploymentException(cause.getMessage(), new DeploymentVetoException(cause));
      }
   }

   public void prepare() throws DeploymentException {
      if (this.isDeploymentRequestValidForCurrentServer()) {
         this.dumpOperation();
         this.deploymentContext.setDeploymentOperation(this.operation);
         this.addContainerListener();

         try {
            this.fireVetoableDeploymentEvent();
            this.doPrepare();
            if (!this.isSupportingServer()) {
               this.relayState(1, (Exception)null);
            }
         } finally {
            this.removeContainerListener();
         }

      }
   }

   private void dumpOperation() {
      if (this.isDebugEnabled()) {
         this.debug("DeploymentData: " + this.deploymentData);
         this.debug("Proposed Mbean: ");
         if (this.mbean == null) {
            this.debug("   removed");
         } else {
            this.dumpModuleTargetInfo(this.mbean);
         }

      }
   }

   private void dumpModuleTargetInfo(ConfigurationMBean mbean) {
      this.dumpTargetsForModule((TargetInfoMBean)mbean);
      SubDeploymentMBean[] subs;
      if (mbean instanceof BasicDeploymentMBean) {
         subs = ((BasicDeploymentMBean)mbean).getSubDeployments();
      } else {
         subs = ((SubDeploymentMBean)mbean).getSubDeployments();
      }

      if (subs != null) {
         for(int i = 0; i < subs.length; ++i) {
            SubDeploymentMBean sub = subs[i];
            this.dumpModuleTargetInfo(sub);
         }
      }

   }

   private void dumpTargetsForModule(TargetInfoMBean mbean) {
      String tnames = "";
      TargetMBean[] targs = mbean.getTargets();

      for(int i = 0; i < targs.length; ++i) {
         TargetMBean targ = targs[i];
         tnames = tnames + targ.getName();
      }

      this.debug("Module: " + mbean.getName() + ", Targets: " + tnames);
   }

   public void stageFilesFromAdminServer(String handlerType) throws DeploymentException {
      this.prepareDataUpdate(handlerType);
   }

   protected void ensureAppContainerSet() throws DeploymentException {
      if (this.appcontainer == null) {
         this.appcontainer = this.getApplication().findDeployment();
      }

   }

   protected void doPrepare() throws DeploymentException {
   }

   protected final void setupPrepare() throws DeploymentException {
      try {
         boolean isCompatibilityMode = true;
         this.getApplication().verifyLocalApp();
         if (this.isAppDeployment() && isCompatibilityMode) {
            this.compatibilityProcessor();
         }

      } catch (Throwable var3) {
         DeploymentException de = DeployHelper.convertThrowable(var3);
         throw de;
      }
   }

   protected void compatibilityProcessor() throws DeploymentException {
   }

   public final boolean isAppDeployment() {
      return this.mbean != null && this.mbean instanceof AppDeploymentMBean;
   }

   public final boolean isInternalApp() {
      return this.mbean instanceof AppDeploymentMBean && ((AppDeploymentMBean)this.mbean).isInternalApp();
   }

   public final void commit() throws DeploymentException {
      if (!this.completed) {
         if (!this.isDeploymentRequestValidForCurrentServer()) {
            this.complete(3, (Exception)null);
         } else {
            this.dumpOperation();
            this.addContainerListener();

            try {
               this.doCommit();
            } catch (Throwable var6) {
               DeploymentException de = DeployHelper.convertThrowable(var6);
               this.complete(2, de);
               SlaveDeployerLogger.logCommitUpdateFailedLoggable("" + this.operation, this.app.getName(), this.getPartitionName()).log();
               throw de;
            } finally {
               this.removeContainerListener();
            }

         }
      }
   }

   public final void cancel() throws DeploymentException {
      if (this.isCancelNecessary()) {
         if (!this.completed) {
            if (this.isDeploymentRequestValidForCurrentServer()) {
               if (this.isDebugEnabled()) {
                  this.debug("Operation.cancel() started for requestId : " + this.requestId);
               }

               this.addContainerListener();

               try {
                  this.doCancel();
               } catch (Throwable var6) {
                  Loggable l = SlaveDeployerLogger.logCancelFailedLoggable(this.taskId, serverName);
                  throw new DeploymentException(l.getMessage(), var6);
               } finally {
                  this.removeContainerListener();
                  this.completed = true;
                  if (this.isDebugEnabled()) {
                     this.debug("Operation.cancel() finished for requestId : " + this.requestId);
                  }

               }

            }
         }
      }
   }

   private void addContainerListener() {
      if (this.mbean != null) {
         this.state = new DeploymentState(this.mbean.getName(), this.taskId, this.getNotificationLevel());
         TargetMBean tmb = TargetUtils.findLocalTarget(this.mbean, server);
         this.state.setTarget(tmb.getName());
         this.ml = ListenerFactory.createListener(this.getDeploymentMBean(), this.taskId, this.state);
         if (this.isDebugEnabled()) {
            this.debug("Adding " + this.ml);
         }

         appctrManager.addModuleListener(this.ml);
         this.getApplication().setStateRef(this.state);
      }
   }

   private void removeContainerListener() {
      if (this.isDebugEnabled()) {
         this.debug("Removing " + this.ml);
      }

      if (this.ml != null) {
         appctrManager.removeModuleListener(this.ml);
         this.ml = null;
      }

   }

   protected ModuleListener getListener() {
      return this.ml;
   }

   protected void doCommit() throws IOException, DeploymentException {
   }

   protected void doCancel() {
   }

   protected boolean isCancelNecessary() {
      return true;
   }

   protected final void silentCancelOnPrepareFailure() {
      try {
         this.doCancel();
      } catch (Throwable var2) {
      }

   }

   protected void complete(int status, Exception ex) {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[3];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = InstrumentationSupport.convertToObject(status);
            var10000[2] = ex;
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.process(var5);
         var5.resetPostBegin();
      }

      this.completed = true;
      boolean success = status != 2;
      if (!success) {
         if (ex == null) {
            ex = new DeploymentException("Task failed with unknown reason");
         }

         Loggable log = SlaveDeployerLogger.logTaskFailedLoggable(Long.toString(this.requestId), this.taskId, this.getPartitionName(), (Exception)ex);
         log.log();
         this.cancelDataUpdate();
      }

      this.closeDataUpdate(success);
      if (!this.isSupportingServer()) {
         this.relayState(status, (Exception)ex);
      }

      this.fireDeploymentEvent();
   }

   private String getPartitionName() {
      return this.getDeploymentMBean() != null && this.getDeploymentMBean().getPartitionName() != null ? this.getDeploymentMBean().getPartitionName() : "DOMAIN";
   }

   protected final void silentRemove(Deployment deployment) {
      this.silentRemove(deployment, true);
   }

   protected final void silentRemove(Deployment deployment, boolean assertUndeployable) {
      try {
         if (assertUndeployable) {
            deployment.assertUndeployable();
         }

         deployment.remove(this.deploymentContext);
      } catch (DeploymentException var4) {
         SlaveDeployerLogger.logOperationFailed("Remove", this.getMBean().getName(), var4);
      }

   }

   private void relayState(int status, Exception ex) {
      if (this.state != null) {
         this.state.setTaskState(status);
         this.state.setException(ex);
         TargetMBean tmb = TargetUtils.findLocalTarget(this.mbean, server);
         if (this.state.getTarget() == null) {
            this.state.setTarget(tmb.getName());
         }

         try {
            if (status == 2 && ex != null && !(ex instanceof NonFatalDeploymentException)) {
               int stagingOff = 0;
               this.state.setStagingState(stagingOff);
               AppRuntimeStateManager.getManager().setStagingState(this.mbean.getName(), new String[]{tmb.getName()}, stagingOff, this.isInternalApp());
            }

            String istate = this.deploymentData == null ? this.state.getIntendedState() : this.deploymentData.getIntendedState();
            if (!this.isAppDeleted()) {
               AppRuntimeStateManager.getManager().setState(this.mbean.getName(), new String[]{tmb.getName()}, istate);
            }

            if (this.isDebugEnabled()) {
               this.debug("Updated intended state for " + this.mbean.getName() + " to " + istate);
            }
         } catch (ManagementException var5) {
            if (this.isDebugEnabled()) {
               this.debug("Failed to update intended state for " + this.mbean.getName());
            }
         }

         if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer() && !this.isAppDeleted()) {
            AppRuntimeStateManager.getManager().updateState(this.mbean.getName(), this.state);
         }

         if (this.isDebugEnabled()) {
            this.debug("Relaying updated state for app, " + this.state.getId() + " to " + this.state.getCurrentState() + ", taskState: " + this.state.getTaskState());
         }

         deploymentManager.relayStatus(this.requestId, this.state);
      }
   }

   protected String[] getFiles() {
      return this.internalDeploymentData != null ? this.deploymentData.getFiles() : null;
   }

   public BasicDeployment getApplication() {
      return this.app;
   }

   public String getTaskId() {
      return this.taskId;
   }

   protected void debug(String m) {
      Debug.deploymentDebug(this.getDebugPrefix() + m);
   }

   private String getDebugPrefix() {
      String c = this.getClass().getName();
      c = c.substring(this.getClass().getPackage().getName().length() + 1);
      return "[op=" + c + ",task=" + this.taskId + "]";
   }

   protected boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   protected boolean isAdminMode() {
      return this.deploymentData != null && this.deploymentData.getDeploymentOptions() != null && this.deploymentData.getDeploymentOptions().isTestMode();
   }

   protected boolean isAppContainerActive(Deployment appContainer) {
      if (appContainer == null) {
         return false;
      } else {
         int state = this.getState(appContainer);
         return state == 3 || state == 4 || state == 2;
      }
   }

   protected void activate(Deployment deployment) throws DeploymentException {
      boolean isAdminMode = this.isAdminMode() || this.isServerInAdminMode();
      this.deploymentContext.setAdminModeTransition(isAdminMode);
      this.deploymentContext.setAdminModeSpecified(isAdminMode);
      if (this.getState(deployment) == 1) {
         deployment.activate(this.deploymentContext);
      }

   }

   protected boolean isGracefulProductionToAdmin() {
      return this.deploymentData != null && this.deploymentData.getDeploymentOptions() != null && this.deploymentData.getDeploymentOptions().isGracefulProductionToAdmin();
   }

   private boolean isIgnoreSessions() {
      return this.deploymentData != null && this.deploymentData.getDeploymentOptions() != null && this.deploymentData.getDeploymentOptions().isGracefulIgnoreSessions();
   }

   private int getRMIGracePeriod() {
      return this.deploymentData != null && this.deploymentData.getDeploymentOptions() != null ? this.deploymentData.getDeploymentOptions().getRMIGracePeriodSecs() : -1;
   }

   protected void gracefulProductionToAdmin(Deployment deployment) throws DeploymentException {
      boolean ignoreSessions = this.isIgnoreSessions();
      boolean adminMode = this.isAdminMode();
      int rmiGracePeriod = this.getRMIGracePeriod();
      this.deploymentContext.setAdminModeTransition(adminMode);
      this.deploymentContext.setAdminModeSpecified(adminMode);
      this.deploymentContext.setIgnoreSessions(ignoreSessions);
      this.deploymentContext.setRMIGracePeriodSecs(rmiGracePeriod);
      this.getApplication().gracefulProductionToAdmin(deployment, this.deploymentContext);
   }

   protected void forceProductionToAdmin(Deployment deployment) throws DeploymentException {
      boolean adminMode = this.isAdminMode();
      this.deploymentContext.setAdminModeTransition(adminMode);
      this.deploymentContext.setAdminModeSpecified(adminMode);
      this.getApplication().forceProductionToAdmin(deployment, this.getForceUndeployTimeoutSecs(), this.deploymentContext);
   }

   protected boolean isNewApplication() {
      return this.deploymentData == null ? false : this.deploymentData.isNewApplication();
   }

   protected String[] getModules() {
      return this.deploymentData == null ? null : this.deploymentData.getModules();
   }

   protected String[] getTargets() {
      return this.deploymentData == null ? null : this.deploymentData.getTargets();
   }

   protected final boolean isTargetListContainsCurrentServer() {
      if (this.deploymentData == null) {
         return false;
      } else {
         DomainMBean domain = this.proposedDomain != null ? this.proposedDomain : ManagementService.getRuntimeAccess(kernelId).getDomain();
         Set targetList = null;

         try {
            targetList = this.deploymentData.getAllTargetedServers(this.deploymentData.getAllLogicalTargets(), domain);
         } catch (InvalidTargetException var4) {
            var4.printStackTrace();
            targetList = Collections.EMPTY_SET;
         }

         if (this.isDebugEnabled()) {
            this.debug(" +++ TargetList : " + targetList);
         }

         String thisServer = server.getName();
         return targetList.contains(thisServer);
      }
   }

   protected final boolean isAppTargetedToCurrentServer() {
      return TargetHelper.isAppTargetedToCurrentServer(this.mbean);
   }

   protected long getForceUndeployTimeoutSecs() {
      if (this.deploymentData == null) {
         return 0L;
      } else {
         return this.deploymentData.getDeploymentOptions() == null ? 0L : this.deploymentData.getDeploymentOptions().getForceUndeployTimeout();
      }
   }

   protected void silentDeactivate(Deployment deployment) {
      try {
         deployment.deactivate(this.deploymentContext);
      } catch (DeploymentException var3) {
         SlaveDeployerLogger.logOperationFailed("Deactivate", this.getMBean().getName(), var3);
      }

   }

   protected void silentUnprepare(Deployment deployment) {
      try {
         deployment.unprepare(this.deploymentContext);
      } catch (DeploymentException var3) {
         SlaveDeployerLogger.logOperationFailed("Unprepare", this.getMBean().getName(), var3);
      }

   }

   protected void silentProductionToAdmin(Deployment deployment) {
      try {
         if (this.isGracefulProductionToAdmin()) {
            this.gracefulProductionToAdmin(deployment);
         } else {
            this.forceProductionToAdmin(deployment);
         }
      } catch (DeploymentException var3) {
         SlaveDeployerLogger.logOperationFailed("ProductionToAdmin", this.getMBean().getName(), var3);
      }

   }

   protected void silentStop(Deployment deployment, String[] ids) {
      try {
         this.deploymentContext.setUpdatedResourceURIs(ids);
         deployment.stop(this.deploymentContext);
      } catch (DeploymentException var4) {
         SlaveDeployerLogger.logOperationFailed("Stop", this.getMBean().getName(), var4);
         if (this.getState(deployment) == 3) {
            this.silentProductionToAdmin(deployment);
         }

         this.silentDeactivate(deployment);
         this.silentUnprepare(deployment);
      }

   }

   protected final void stop(Deployment deployment, String[] ids) throws DeploymentException {
      if (this.isAppContainerActive(deployment)) {
         try {
            this.deploymentContext.setUpdatedResourceURIs(ids);
            deployment.stop(this.deploymentContext);
         } catch (DeploymentException var4) {
            SlaveDeployerLogger.logOperationFailed("Stop", this.getMBean().getName(), var4);
            if (var4 instanceof NonFatalDeploymentException) {
               throw var4;
            }

            if (this.getState(deployment) == 3) {
               this.silentProductionToAdmin(deployment);
            }

            this.silentDeactivate(deployment);
            this.silentUnprepare(deployment);
         }

      }
   }

   protected int getState(Deployment depl) {
      return DeployHelper.getState(depl);
   }

   protected boolean isAdminState(Deployment depl) {
      return DeployHelper.isAdminState(depl);
   }

   protected void fireVetoableDeploymentEvent() throws DeploymentException {
      if (this.isAppDeployment() && !ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         BaseDeploymentEvent.EventType eventType = null;
         switch (this.operation) {
            case 1:
               eventType = VetoableDeploymentEvent.APP_ACTIVATE;
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            default:
               break;
            case 4:
               eventType = VetoableDeploymentEvent.APP_UNDEPLOY;
               break;
            case 7:
               eventType = VetoableDeploymentEvent.APP_START;
               break;
            case 9:
            case 11:
               eventType = VetoableDeploymentEvent.APP_DEPLOY;
         }

         if (eventType != null) {
            try {
               DeploymentEventManager.sendVetoableDeploymentEvent(VetoableDeploymentEvent.create(this, eventType, (AppDeploymentMBean)this.getApplication().getDeploymentMBean(), this.isNewApplication(), this.getModules(), this.getTargets()));
            } catch (DeploymentException var3) {
               this.complete(2, var3);
               throw var3;
            }
         }

      }
   }

   private void fireDeploymentEvent() {
      if (this.isAppDeployment() && !ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         BaseDeploymentEvent.EventType eventType = null;
         switch (this.operation) {
            case 1:
               eventType = DeploymentEvent.APP_ACTIVATED;
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            default:
               break;
            case 4:
               if (this.isAppDeleted()) {
                  eventType = DeploymentEvent.APP_DELETED;
               }
               break;
            case 7:
               eventType = DeploymentEvent.APP_STARTED;
               break;
            case 9:
            case 10:
               eventType = DeploymentEvent.APP_REDEPLOYED;
               break;
            case 11:
               eventType = DeploymentEvent.APP_DEPLOYED;
         }

         if (eventType != null) {
            DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, eventType, (AppDeploymentMBean)this.getApplication().getDeploymentMBean(), this.getModules(), this.getTargets()));
         }

      }
   }

   protected boolean isAppDeleted() {
      if (!this.isAppDeployment()) {
         return false;
      } else {
         DomainMBean domain = this.getProposedDomain();
         BasicDeploymentMBean appMBean = this.getApplication().getDeploymentMBean();
         return domain != null && appMBean != null && domain.lookupAppDeployment(appMBean.getName()) == null;
      }
   }

   protected boolean isServerInAdminMode() {
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      return serverRuntime != null && serverRuntime.getStateVal() == 17;
   }

   protected final boolean isAppSystemResource() {
      return this.mbean instanceof SystemResourceMBean;
   }

   protected boolean isDeploymentRequestValidForCurrentServer() {
      boolean isSuppliedTargetListContainsCurrentServer = this.isTargetListContainsCurrentServer();
      if (this.isDebugEnabled()) {
         this.debug(" +++ Supplied TargetList contains current server : " + isSuppliedTargetListContainsCurrentServer);
      }

      boolean isAppTargetedToCurrentServer = this.isAppTargetedToCurrentServer();
      if (this.isDebugEnabled()) {
         this.debug(" +++ Application targeted to current server : " + isAppTargetedToCurrentServer);
      }

      if (!isSuppliedTargetListContainsCurrentServer && !isAppTargetedToCurrentServer) {
         return !this.isAppSystemResource();
      } else {
         return true;
      }
   }

   public final int getOperationType() {
      return this.operation;
   }

   public final boolean isControlOperation() {
      return this.controlOperation;
   }

   protected void initDataUpdate() throws DeploymentException {
   }

   protected void prepareDataUpdate(String handlerType) throws DeploymentException {
      this.initDataUpdate();
      this.getApplication().prepareDataUpdate(handlerType);
   }

   protected void commitDataUpdate() throws DeploymentException {
      this.getApplication().commitDataUpdate();
   }

   protected void cancelDataUpdate() {
      if (this.getApplication() != null) {
         this.getApplication().cancelDataUpdate(this.getRequestId());
      }

   }

   protected void closeDataUpdate(boolean success) {
      this.getApplication().closeDataUpdate(this.getRequestId(), success);
   }

   private boolean isSupportingServer() {
      if (this.isTargetListContainsCurrentServer()) {
         return false;
      } else if (!(this.mbean instanceof AppDeploymentMBean)) {
         return false;
      } else if (cluster == null) {
         return false;
      } else {
         Set targets = TargetHelper.getAllTargetedServers(this.mbean);
         return !targets.contains(serverName);
      }
   }

   static {
      _WLDF$INST_FLD_Deployment_Complete_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Complete_Before_Low");
      _WLDF$INST_FLD_Deployment_Abstract_Operation_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Abstract_Operation_Before_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "AbstractOperation.java", "weblogic.deploy.internal.targetserver.operations.AbstractOperation", "<init>", "(JLjava/lang/String;Lweblogic/deploy/internal/InternalDeploymentData;Lweblogic/management/configuration/BasicDeploymentMBean;Lweblogic/management/configuration/DomainMBean;Lweblogic/security/acl/internal/AuthenticatedSubject;Z)V", 102, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Abstract_Operation_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("reqId", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("taskId", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("intDep", "weblogic.diagnostics.instrumentation.gathering.DeploymentInternalDataRenderer", false, true), null, null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Abstract_Operation_Before_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "AbstractOperation.java", "weblogic.deploy.internal.targetserver.operations.AbstractOperation", "complete", "(ILjava/lang/Exception;)V", 448, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Complete_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("status", "weblogic.diagnostics.instrumentation.gathering.DeploymentStatusRenderer", false, true), InstrumentationSupport.createValueHandlingInfo("exc", (String)null, false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Complete_Before_Low};
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      server = ManagementService.getRuntimeAccess(kernelId).getServer();
      serverName = server.getName();
      cluster = server.getCluster();
      deploymentManager = DeploymentManager.getInstance();
      appctrManager = weblogic.application.DeploymentManager.getDeploymentManager();
   }
}
