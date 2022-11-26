package weblogic.deploy.internal.targetserver.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import weblogic.application.NonFatalDeploymentException;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.datamanagement.ModuleRedeployDataUpdateRequestInfo;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class ModuleRedeployOperation extends ActivateOperation {
   private final String[] moduleIds;
   static final long serialVersionUID = -6892348131306621312L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.deploy.internal.targetserver.operations.ModuleRedeployOperation");
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public ModuleRedeployOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, String[] moduleIds, AuthenticatedSubject initiator, boolean restartRequired) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, restartRequired);
      this.moduleIds = moduleIds;
      this.appcontainer = this.getApplication().findDeployment();
      this.operation = 9;
   }

   protected void compatibilityProcessor() throws DeploymentException {
      MBeanConverter.reconcile81MBeans(this.deploymentData, (AppDeploymentMBean)this.mbean);
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

      if (this.isDebugEnabled()) {
         this.debug("ModuleRedeployOperation: prepare called.");
      }

      this.ensureAppContainerSet();
      if (this.appcontainer == null) {
         super.doPrepare();
      } else {
         if (!this.isAppContainerActive(this.appcontainer)) {
            Loggable l = SlaveDeployerLogger.logInvalidStateForRedeployLoggable(this.getApplication().getName());
            l.log();
            DeploymentException de = new DeploymentException(l.getMessage());
            this.complete(2, de);
            throw de;
         }

         this.moduleLevelRedeploy();
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
            this.debug("ModuleRedeployOperation: Invoking undeploy on Container.");
         }

         this.silentProductionToAdmin(this.appcontainer);
         this.silentDeactivate(this.appcontainer);
         this.silentUnprepare(this.appcontainer);
         this.silentRemove(this.appcontainer);
         if (this.isDebugEnabled()) {
            this.debug("ModuleRedeployOperation: undeploy on Container finished.");
         }
      }

      this.getApplication().remove(false);
   }

   protected void doCommit() throws DeploymentException, IOException {
      this.appcontainer = this.getApplication().findDeployment();
      if (this.appcontainer != null && this.getState(this.appcontainer) == 1) {
         super.doCommit();
      } else {
         this.complete(3, (Exception)null);
      }

   }

   public void initDataUpdate() throws DeploymentException {
      try {
         List moduleIdList = new ArrayList();
         moduleIdList.addAll(Arrays.asList(this.moduleIds));
         this.getApplication().initDataUpdate(new ModuleRedeployDataUpdateRequestInfo(moduleIdList, this.requestId));
      } catch (Throwable var2) {
         var2.printStackTrace();
         throw DeployHelper.convertThrowable(var2);
      }
   }

   private void moduleLevelRedeploy() throws DeploymentException {
      try {
         this.deploymentContext.setUpdatedResourceURIs(this.moduleIds);
         this.appcontainer.stop(this.deploymentContext);
         this.commitDataUpdate();
         this.setupPrepare();
         this.appcontainer.start(this.deploymentContext);
      } catch (Throwable var3) {
         if (!(var3 instanceof NonFatalDeploymentException)) {
            this.silentCancelOnPrepareFailure();
         }

         DeploymentException de = DeployHelper.convertThrowable(var3);
         this.complete(2, de);
         throw de;
      }
   }

   static {
      _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Prepare_Before_Low");
      _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Cancel_Before_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ModuleRedeployOperation.java", "weblogic.deploy.internal.targetserver.operations.ModuleRedeployOperation", "doPrepare", "()V", 59, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Prepare_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ModuleRedeployOperation.java", "weblogic.deploy.internal.targetserver.operations.ModuleRedeployOperation", "doCancel", "()V", 86, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Cancel_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low};
   }
}
