package weblogic.deploy.internal.targetserver.operations;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.Deployment;
import weblogic.application.internal.DeploymentStateChecker;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.datamanagement.DataUpdateRequestInfo;
import weblogic.deploy.utils.ApplicationUtils;
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
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.AssertionError;

public class ActivateOperation extends AbstractOperation {
   private static final AuthenticatedSubject kernelId;
   boolean isFailedInPrepareValidation = false;
   static final long serialVersionUID = 4682598674116379872L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.deploy.internal.targetserver.operations.ActivateOperation");
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public ActivateOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean restartRequired) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, restartRequired);
      this.operation = 1;
   }

   public AbstractOperation refine() throws DeploymentException {
      BasicDeployment app = this.getApplication();
      if (app == null) {
         String errMsg = SlaveDeployerLogger.logFailedToFindDeploymentLoggable(this.internalDeploymentData.getDeploymentName()).getMessage();
         throw new AssertionError(errMsg);
      } else {
         this.appcontainer = app.findDeployment();
         return (AbstractOperation)(this.appcontainer == null ? this : new RedeployOperation(this.requestId, this.taskId, this.internalDeploymentData, this.mbean, this.proposedDomain, this.initiator, this.requiresRestart));
      }
   }

   protected void compatibilityProcessor() throws DeploymentException {
      MBeanConverter.setupNew81MBean((AppDeploymentMBean)this.mbean);
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

      if (!this.isAdminState() || this.isAdminMode()) {
         try {
            this.commitDataUpdate();
            this.setupPrepare();
            this.createAndPrepareContainer();
            this.resetPendingRestartForSystemResource();
         } catch (Throwable var4) {
            this.silentCancelOnPrepareFailure();
            DeploymentException reportExpn = DeployHelper.convertThrowable(var4);
            this.complete(2, reportExpn);
            throw reportExpn;
         }
      }
   }

   protected void doCommit() throws IOException, DeploymentException {
      this.appcontainer = this.getApplication().findDeployment();
      if (this.internalDeploymentData.getDeploymentOperation() != 6) {
         this.activateDeployment();
      }

      this.complete(3, (Exception)null);
   }

   protected void doCancel() {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[1];
            var2.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.process(var2);
         var2.resetPostBegin();
      }

      if (this.appcontainer != null) {
         int currentAppState = this.getState(this.appcontainer);
         if (currentAppState == 1) {
            if (this.isDebugEnabled()) {
               this.debug("ActivateOperation: Invoking unprepare() on Container.");
            }

            this.silentUnprepare(this.appcontainer);
            if (this.isDebugEnabled()) {
               this.debug("ActivateOperation: Invoking unprepare() on Container.");
            }
         }

         this.silentRemove(this.appcontainer);
      }

      this.getApplication().remove();
   }

   protected boolean isCancelNecessary() {
      return !this.isFailedInPrepareValidation;
   }

   private boolean isPartitionAdminState() {
      String partitionName = this.deploymentData.getPartition();
      if (partitionName != null) {
         return State.ADMIN == ApplicationUtils.getPartitionState(partitionName);
      } else {
         return false;
      }
   }

   private boolean isResourceGroupAdminState() {
      String rgName = this.deploymentData.getResourceGroup();
      if (rgName != null) {
         return RGState.ADMIN == ApplicationUtils.getResourceGroupState(this.deploymentData.getPartition(), rgName);
      } else {
         return false;
      }
   }

   protected void activateDeployment() throws DeploymentException {
      if (this.appcontainer != null) {
         if (this.isDebugEnabled()) {
            this.debug(" ActivateOperation - Activating application " + this.getApplication().getName());
         }

         try {
            this.activate(this.appcontainer);
         } catch (DeploymentException var2) {
            this.recoverOnActivateFailure(this.appcontainer);
            throw var2;
         }

         if (this.getState(this.appcontainer) == 3) {
            return;
         }

         if (!this.isAdminMode()) {
            try {
               if (!this.isServerInAdminMode() && !this.isPartitionAdminState() && !this.isResourceGroupAdminState()) {
                  this.appcontainer.adminToProduction(this.deploymentContext);
               } else if (this.getApplication().getState() != null) {
                  this.getApplication().getState().setIntendedState("STATE_ACTIVE");
               }
            } catch (DeploymentException var3) {
               this.silentDeactivate(this.appcontainer);
               this.recoverOnActivateFailure(this.appcontainer);
               throw var3;
            }
         }
      }

   }

   protected void recoverOnActivateFailure(Deployment deployment) {
      this.silentUnprepare(deployment);
      this.silentRemove(deployment);
      if (this.getApplication().getState() != null) {
         this.getApplication().getState().setCurrentState("STATE_FAILED", true);
      }

   }

   protected void unprepareDeployment() throws DeploymentException {
      if (this.appcontainer != null) {
         if (this.isDebugEnabled()) {
            this.debug(" ActivateOperation - unpreparing application " + this.getApplication().getName());
         }

         this.appcontainer.unprepare(this.deploymentContext);
      }

   }

   protected void createAndPrepareContainer() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug(" Creating application container for " + this.getApplication().getName());
      }

      this.appcontainer = this.getApplication().createDeployment(this.deploymentCreator, this.mbean, this.getState());
      this.initializeDeploymentPlan();
      boolean isAdminMode = this.isAdminMode();
      this.deploymentContext.setAdminModeTransition(isAdminMode);
      this.deploymentContext.setAdminModeSpecified(isAdminMode);
      this.appcontainer.prepare(this.deploymentContext);
   }

   protected boolean isAdminState() {
      if (this.appcontainer == null) {
         this.appcontainer = this.getApplication().findDeployment();
      }

      return this.appcontainer != null && this.isAdminState(this.appcontainer);
   }

   private void initializeDeploymentPlan() throws DeploymentException {
      if (this.isAppDeployment() && this.mbean != null) {
         AppDeployment appDeployment = (AppDeployment)this.getApplication();
         String planPath = ((AppDeploymentMBean)this.mbean).getPlanPath();
         DeploymentPlanBean planDescriptor = appDeployment.parsePlan();
         ((AppDeploymentMBean)this.mbean).setDeploymentPlanDescriptor(planDescriptor);
      }

   }

   protected void validatePrepare() throws DeploymentException {
      this.initDeploymentCreator();
      this.appcontainer = this.getApplication().findDeployment();
      if (this.appcontainer != null && this.getState(this.appcontainer) > 0) {
         String msg = SlaveDeployerLogger.illegalStateForDeploy(DeploymentStateChecker.state2String(this.getState(this.appcontainer)));
         this.isFailedInPrepareValidation = true;
         throw new DeploymentException(msg);
      }
   }

   public void initDataUpdate() throws DeploymentException {
      boolean isStaging = false;
      boolean isPlanStaging = false;
      DeploymentData extDeplData = this.internalDeploymentData.getExternalDeploymentData();
      if (extDeplData != null) {
         DeploymentOptions opts = extDeplData.getDeploymentOptions();
         if (opts != null) {
            if ("stage".equals(opts.getStageMode())) {
               isStaging = true;
            }

            if ("stage".equals(opts.getPlanStageMode())) {
               isPlanStaging = true;
            }
         }
      }

      if (isStaging) {
         if (isPlanStaging) {
            this.getApplication().initDataUpdate(new DataUpdateRequestInfo() {
               public List getDeltaFiles() {
                  return new ArrayList();
               }

               public List getTargetFiles() {
                  return new ArrayList();
               }

               public long getRequestId() {
                  return ActivateOperation.this.requestId;
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
                  return true;
               }

               public boolean isPlanStaging() {
                  return true;
               }
            });
         } else {
            this.getApplication().initDataUpdate(new DataUpdateRequestInfo() {
               public List getDeltaFiles() {
                  return new ArrayList();
               }

               public List getTargetFiles() {
                  return new ArrayList();
               }

               public long getRequestId() {
                  return ActivateOperation.this.requestId;
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
                  return true;
               }

               public boolean isPlanStaging() {
                  return false;
               }
            });
         }
      } else if (isPlanStaging) {
         this.getApplication().initDataUpdate(new DataUpdateRequestInfo() {
            public List getDeltaFiles() {
               return new ArrayList();
            }

            public List getTargetFiles() {
               return new ArrayList();
            }

            public long getRequestId() {
               return ActivateOperation.this.requestId;
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
               return true;
            }
         });
      } else {
         this.getApplication().initDataUpdate(new DataUpdateRequestInfo() {
            public List getDeltaFiles() {
               return new ArrayList();
            }

            public List getTargetFiles() {
               return new ArrayList();
            }

            public long getRequestId() {
               return ActivateOperation.this.requestId;
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
      }

   }

   protected void resetPendingRestartForSystemResource() {
      if (this.mbean instanceof SystemResourceMBean) {
         ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         serverRuntime.removePendingRestartSystemResource(this.mbean.getName());
      }

   }

   static {
      _WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Prepare_Before_Low");
      _WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Deployment_Do_Cancel_Before_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ActivateOperation.java", "weblogic.deploy.internal.targetserver.operations.ActivateOperation", "doPrepare", "()V", 90, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Prepare_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Prepare_Before_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ActivateOperation.java", "weblogic.deploy.internal.targetserver.operations.ActivateOperation", "doCancel", "()V", 128, "", "", "", InstrumentationSupport.makeMap(new String[]{"Deployment_Do_Cancel_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.DeploymentAbstractOperationRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Deployment_Do_Cancel_Before_Low};
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
