package weblogic.deploy.internal.targetserver.operations;

import java.security.AccessController;
import java.util.Set;
import weblogic.application.Deployment;
import weblogic.application.internal.DeploymentStateChecker;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

public class RedeployOperation extends ActivateOperation {
   private static final AuthenticatedSubject kernelId;
   private final String[] moduleIds;
   static final long serialVersionUID = -2722286710176756039L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.deploy.internal.targetserver.operations.RedeployOperation");
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public RedeployOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean restartRequired) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, restartRequired);
      this.operation = 9;
      this.appcontainer = this.getApplication().findDeployment();
      this.moduleIds = TargetHelper.getModulesForTarget(this.deploymentData, proposedDomain);
   }

   public AbstractOperation refine() throws DeploymentException {
      boolean isDynamicUpdate = false;
      if (this.deploymentData != null) {
         isDynamicUpdate = this.deploymentData.hasFiles();
         if (!isDynamicUpdate) {
            String[] globalTargets = this.deploymentData.getGlobalTargets();
            boolean hasGlobalTargets = globalTargets != null && globalTargets.length > 0;
            isDynamicUpdate = !hasGlobalTargets && this.deploymentData.hasSubModuleTargets();
         }
      }

      if (isDynamicUpdate && this.moduleIds != null) {
         String s = SlaveDeployerLogger.logBothStaticFileRedeployAndModuleRedeployLoggable().getMessage();
         throw new DeploymentException(s);
      } else if (isDynamicUpdate && this.isAppContainerActive(this.appcontainer)) {
         return new DynamicUpdateOperation(this.requestId, this.taskId, this.internalDeploymentData, this.mbean, this.proposedDomain, this.initiator, this.requiresRestart);
      } else {
         return (AbstractOperation)(this.moduleIds != null && !isDynamicUpdate ? new ModuleRedeployOperation(this.requestId, this.taskId, this.internalDeploymentData, this.mbean, this.proposedDomain, this.moduleIds, this.initiator, this.requiresRestart) : this);
      }
   }

   protected void createAndPrepareContainer() throws DeploymentException {
      this.appcontainer = this.getApplication().createDeployment(this.deploymentCreator, this.mbean, this.getState());
      this.initializeDeploymentPlan();
      this.appcontainer.prepare(this.deploymentContext);
   }

   protected void doPrepare() throws DeploymentException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[1];
            var4.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.process(var4);
         var4.resetPostBegin();
      }

      this.validatePrepare();
      this.ensureAppContainerSet();
      if (this.isDebugEnabled()) {
         this.debug("Preparing application " + this.getApplication().getName());
      }

      boolean isAdminMode = this.isAdminMode() || this.isServerInAdminMode();
      if (!this.isAdminState() || isAdminMode) {
         if (this.isDebugEnabled()) {
            this.debug(" Redeploying " + this.getApplication().getName());
         }

         if (this.appcontainer != null) {
            try {
               this.appcontainer.assertUndeployable();
            } catch (DeploymentException var5) {
               this.complete(2, var5);
               throw var5;
            }

            this.unprepareDeployment(this.appcontainer);
         }

         try {
            this.commitDataUpdate();
            this.setupPrepare();
            this.createAndPrepareContainer();
            this.resetPendingRestartForSystemResource();
         } catch (Throwable var6) {
            if (this.isDebugEnabled()) {
               this.debug(StackTraceUtils.throwable2StackTrace(var6));
            }

            this.silentCancelOnPrepareFailure();
            DeploymentException reportExpn = DeployHelper.convertThrowable(var6);
            this.complete(2, reportExpn);
            throw reportExpn;
         }
      }
   }

   protected final void doCancel() {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var1.argsCapture) {
            var1.args = new Object[1];
            var1.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var1);
         InstrumentationSupport.process(var1);
         var1.resetPostBegin();
      }

      if (this.appcontainer != null) {
         if (this.isDebugEnabled()) {
            this.debug("RedeployOperation: Invoking undeploy on Container.");
         }

         this.unprepareDeployment(this.appcontainer);
         this.silentRemove(this.appcontainer);
         if (this.isDebugEnabled()) {
            this.debug("RedeployOperation: undeploy on Container finished.");
         }
      }

      this.getApplication().remove(false);
   }

   protected void validatePrepare() throws DeploymentException {
      this.initDeploymentCreator();
      this.throwIfDistributeDisallowed(this.internalDeploymentData.getDeploymentOperation());
   }

   private void throwIfDistributeDisallowed(int deploymentOperation) throws DeploymentException {
      if (deploymentOperation == 6) {
         int state = this.getState(this.appcontainer);
         if (this.isDebugEnabled()) {
            this.debug("RedeployOperation: verifying if the state of" + this.mbean.getName() + " allows for the distribute operation");
            this.debug("RedeployOperation: current application state is " + DeploymentStateChecker.state2String(state));
         }

         if (state > 2) {
            if (this.isDebugEnabled()) {
               this.debug("RedeployOperation: rejecting distribute because the applidation is in state " + DeploymentStateChecker.state2String(state));
            }

            String stateString = DeploymentStateChecker.state2String(state);
            String s = SlaveDeployerLogger.logInvalidDistributeLoggable(this.mbean.getName(), stateString).getMessage();
            this.isFailedInPrepareValidation = true;
            throw new DeploymentException(s);
         }
      }

   }

   private void initializeDeploymentPlan() throws DeploymentException {
      if (this.isAppDeployment() && this.mbean != null) {
         AppDeployment appDeployment = (AppDeployment)this.getApplication();
         DeploymentPlanBean planDescriptor = appDeployment.parsePlan();
         ((AppDeploymentMBean)this.mbean).setDeploymentPlanDescriptor(planDescriptor);
      }

   }

   protected void compatibilityProcessor() throws DeploymentException {
      MBeanConverter.reconcile81MBeans(this.deploymentData, (AppDeploymentMBean)this.mbean);
   }

   private void unprepareDeployment(Deployment deployment) {
      if (this.getState(deployment) == 3) {
         this.silentProductionToAdmin(deployment);
      }

      if (this.getState(deployment) > 1) {
         this.silentDeactivate(deployment);
      }

      if (this.getState(deployment) >= 1) {
         this.silentUnprepare(deployment);
      }

      if (!this.isHomogenousDeployment()) {
         this.relayState();
      } else {
         try {
            AppRuntimeStateManager.getManager().remove(this.mbean.getName());
         } catch (Throwable var3) {
         }
      }

   }

   protected void recoverOnActivateFailure(Deployment deployment) {
      this.silentUnprepare(deployment);
   }

   private void relayState() {
      TargetMBean tmb = TargetUtils.findLocalTarget(this.mbean, server);
      if (this.getState().getTarget() == null) {
         this.getState().setTarget(tmb.getName());
      }

      if (this.deploymentData == null) {
         this.getState().getIntendedState();
      } else {
         this.deploymentData.getIntendedState();
      }

      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer() && !this.isAppDeleted()) {
         AppRuntimeStateManager.getManager().updateStateForRedeployOperationOnCluster(this.mbean.getName(), this.getState());
      }

      if (this.isDebugEnabled()) {
         this.debug("Relaying updated state for app, " + this.getState().getId() + " to " + this.getState().getCurrentState() + ", taskState: " + this.getState().getTaskState());
      }

      deploymentManager.relayStatus(this.requestId, this.getState());
   }

   private boolean isHomogenousDeployment() {
      if (!(this.mbean instanceof AppDeploymentMBean)) {
         return true;
      } else if (cluster == null) {
         return true;
      } else {
         Set serversInCluster = cluster.getServerNames();
         Set allTargets = TargetHelper.getAllTargetedServers(this.mbean);
         return allTargets.containsAll(serversInCluster);
      }
   }

   static {
      _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Prepare_Before_Low");
      _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Cancel_Before_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "RedeployOperation.java", "weblogic.deploy.internal.targetserver.operations.RedeployOperation", "doPrepare", "()V", 108, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Prepare_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "RedeployOperation.java", "weblogic.deploy.internal.targetserver.operations.RedeployOperation", "doCancel", "()V", 156, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Cancel_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low};
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
