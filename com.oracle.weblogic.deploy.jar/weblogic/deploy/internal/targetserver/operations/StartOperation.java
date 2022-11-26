package weblogic.deploy.internal.targetserver.operations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import weblogic.application.Deployment;
import weblogic.application.NonFatalDeploymentException;
import weblogic.application.internal.DeploymentStateChecker;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.StackTraceUtils;

public class StartOperation extends ActivateOperation {
   private String[] moduleIds = null;
   private boolean isModuleLevelStart = false;
   static final long serialVersionUID = -8201889739361876835L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.deploy.internal.targetserver.operations.StartOperation");
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public StartOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean requiresRestart) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, requiresRestart);
      this.operation = 7;
      DeploymentOptions options = this.deploymentData.getDeploymentOptions();
      if (options == null || !options.isDisableModuleLevelStartStop()) {
         this.moduleIds = TargetHelper.getModulesForTarget(this.deploymentData, proposedDomain);
      }

      if (!this.isDistributed()) {
         throw new DeploymentException("Application must be distributed before a start operation.");
      } else {
         this.controlOperation = true;
      }
   }

   private boolean isDistributed() {
      return true;
   }

   protected void createAndPrepareContainer() throws DeploymentException {
      this.appcontainer = this.getApplication().findDeployment();
      if (this.appcontainer != null && this.moduleIds != null) {
         String[] filteredModuleIds = this.getFilteredModIds();
         this.deploymentContext.setUpdatedResourceURIs(filteredModuleIds);
         if (filteredModuleIds.length != 0) {
            this.appcontainer.start(this.deploymentContext);
            this.isModuleLevelStart = true;
         }
      } else if (!this.isAdminState() || this.isAdminMode()) {
         if (this.appcontainer == null || this.getState(this.appcontainer) < 1) {
            super.createAndPrepareContainer();
         }
      }
   }

   protected void doPrepare() throws DeploymentException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[1];
            var3.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.process(var3);
         var3.resetPostBegin();
      }

      this.validatePrepare();
      if (this.isDebugEnabled()) {
         this.debug("Preparing application " + this.getApplication().getName());
      }

      try {
         this.setupPrepare();
         this.createAndPrepareContainer();
      } catch (Throwable var4) {
         if (this.isDebugEnabled()) {
            this.debug("Preparing application " + this.getApplication().getName() + " Failed and Exception is : " + StackTraceUtils.throwable2StackTrace(var4));
         }

         if (!(var4 instanceof NonFatalDeploymentException)) {
            this.silentCancelOnPrepareFailure();
         }

         DeploymentException reportExpn = DeployHelper.convertThrowable(var4);
         this.complete(2, reportExpn);
         throw reportExpn;
      }
   }

   protected void validatePrepare() throws DeploymentException {
      this.initDeploymentCreator();
      this.appcontainer = this.getApplication().findDeployment();
      if (this.appcontainer != null) {
         int state = this.getState(this.appcontainer);
         if (state == 4 || state == 3 && this.isAdminMode()) {
            String msg = DeployerRuntimeLogger.illegalStateForStart(DeploymentStateChecker.state2String(state));
            this.isFailedInPrepareValidation = true;
            throw new DeploymentException(msg);
         }
      }

   }

   protected void doCommit() throws IOException, DeploymentException {
      Deployment d = this.getApplication().findDeployment();
      if (!this.isModuleLevelStart && this.getState(d) != 3) {
         super.doCommit();
      } else {
         this.complete(3, (Exception)null);
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
            this.debug("StartOperation: Invoking undeploy on Container.");
         }

         if (this.getState(this.appcontainer) == 3) {
            this.silentProductionToAdmin(this.appcontainer);
         }

         if (this.getState(this.appcontainer) > 1) {
            this.silentDeactivate(this.appcontainer);
         }

         if (this.getState(this.appcontainer) >= 1) {
            this.silentUnprepare(this.appcontainer);
         }

         this.silentRemove(this.appcontainer);
         if (this.isDebugEnabled()) {
            this.debug("StartOperation: undeploy on Container finished.");
         }
      }

      this.getApplication().remove(false);
   }

   protected final boolean isDeploymentRequestValidForCurrentServer() {
      return this.isTargetListContainsCurrentServer();
   }

   private String[] getFilteredModIds() {
      ArrayList filteredModIds = new ArrayList();
      Map moduleStates = this.app.getAppRuntimeState().getModules();
      Map moduleTargetSpecs = this.deploymentData.getAllModuleTargets();

      for(int i = 0; i < this.moduleIds.length; ++i) {
         if (moduleStates.get(this.moduleIds[i]) != null && moduleTargetSpecs.get(this.moduleIds[i]) != null) {
            Map statOnMod = (Map)moduleStates.get(this.moduleIds[i]);
            String[] targets = (String[])((String[])moduleTargetSpecs.get(this.moduleIds[i]));

            for(int j = 0; j < targets.length; ++j) {
               Map statOnTarget = null;
               if (statOnMod.get(targets[j]) != null) {
                  statOnTarget = (Map)statOnMod.get(targets[j]);
                  Object statOnServer = statOnTarget.get(serverName);
                  if (statOnServer != null) {
                     TargetModuleState st = null;
                     if (statOnServer instanceof TargetModuleState) {
                        st = (TargetModuleState)statOnServer;
                     } else if (statOnServer instanceof Map) {
                        st = (TargetModuleState)((TargetModuleState)((Map)statOnServer).get(serverName));
                     }

                     if (this.isDebugEnabled() && st != null) {
                        this.debug("Module: " + this.moduleIds[i] + " state is " + st.getCurrentState());
                     }

                     if ("STATE_NEW".equals(st.getCurrentState())) {
                        filteredModIds.add(this.moduleIds[i]);
                        break;
                     }
                  }
               }
            }
         }
      }

      return (String[])((String[])filteredModIds.toArray(new String[0]));
   }

   public void initDataUpdate() throws DeploymentException {
   }

   protected void initDeploymentCreator() throws DeploymentException {
      try {
         this.deploymentCreator = appctrManager.getDeploymentCreator((BasicDeploymentMBean)null, (File)null);
      } catch (DeploymentException var3) {
         Throwable cause = var3.getCause();
         throw new DeploymentException(cause.getMessage(), new DeploymentVetoException(cause));
      }
   }

   static {
      _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Prepare_Before_Low");
      _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Cancel_Before_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StartOperation.java", "weblogic.deploy.internal.targetserver.operations.StartOperation", "doPrepare", "()V", 102, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Prepare_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StartOperation.java", "weblogic.deploy.internal.targetserver.operations.StartOperation", "doCancel", "()V", 156, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Cancel_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low};
   }
}
