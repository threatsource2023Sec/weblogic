package weblogic.deploy.internal.targetserver.operations;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.ModuleListener;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.datamanagement.DataUpdateRequestInfo;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.deploy.DeployerRuntimeTextTextFormatter;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DynamicUpdateOperation extends AbstractOperation {
   private String[] files = new String[0];
   private final Map subModuleTargets;
   private DeploymentPlanBean dpb = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DynamicUpdateOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean restartRequired) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, restartRequired);
      if (this.deploymentData == null || !this.deploymentData.hasFiles() && !this.deploymentData.hasSubModuleTargets()) {
         throw new AssertionError();
      } else {
         if (this.deploymentData.hasFiles()) {
            this.files = this.deploymentData.getFiles();
         } else {
            this.files = null;
         }

         this.subModuleTargets = this.deploymentData.getAllSubModuleTargets();
         this.appcontainer = this.getApplication().findDeployment();
         this.operation = internalDeploymentData == null ? 9 : internalDeploymentData.getDeploymentOperation();
      }
   }

   protected void compatibilityProcessor() throws DeploymentException {
      MBeanConverter.reconcile81MBeans(this.deploymentData, (AppDeploymentMBean)this.mbean);
   }

   public AbstractOperation refine() throws DeploymentException {
      if (this.isAppContainerActive(this.appcontainer)) {
         return this;
      } else {
         this.internalDeploymentData.setDeploymentOperation(6);
         return new RedeployOperation(this.requestId, this.taskId, this.internalDeploymentData, this.mbean, this.proposedDomain, this.initiator, this.requiresRestart);
      }
   }

   public void doPrepare() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("DynamicUpdateOperation: prepare called.");
      }

      try {
         this.commitDataUpdate();
         this.setupPrepare();
         this.ensureAppContainerSet();
         if (this.isASystemResourceRequiringRestart()) {
            if (this.isDebugEnabled()) {
               this.debug("System Resource '" + this.mbean.getName() + "' requires a restart for the changes to take effect - prepare returning without further validation");
            }

            if (this.getState() != null) {
               this.getState().setCurrentState(ModuleListener.STATE_UPDATE_PENDING.toString());
            }

         } else {
            if (!this.subModuleTargets.isEmpty()) {
               this.convertSubModuleTargetsToFiles();
            }

            this.initializeDeploymentPlan();
            if (this.isDebugEnabled()) {
               this.debug("Files: " + Arrays.asList(this.files));
            }

            this.deploymentContext.setUpdatedResourceURIs(this.files);
            this.appcontainer.prepareUpdate(this.deploymentContext);
         }
      } catch (Throwable var3) {
         this.silentCancelOnPrepareFailure();
         DeploymentException de = DeployHelper.convertThrowable(var3);
         this.complete(2, de);
         throw de;
      }
   }

   private void initializeDeploymentPlan() throws DeploymentException {
      if (this.isAppDeployment() && this.operation == 10 && this.mbean != null) {
         this.dpb = ((AppDeployment)this.getApplication()).parsePlan();
         ((AppDeploymentMBean)this.mbean).setDeploymentPlanDescriptor(this.dpb);
      }

   }

   private void convertSubModuleTargetsToFiles() {
      ArrayList filelist = new ArrayList();
      if (this.files != null) {
         filelist.addAll(Arrays.asList((Object[])this.files));
      }

      Iterator it = this.subModuleTargets.keySet().iterator();

      while(it.hasNext()) {
         String module = (String)it.next();
         Map targets = (Map)this.subModuleTargets.get(module);
         Iterator it2 = targets.keySet().iterator();

         while(it2.hasNext()) {
            String submod = (String)it2.next();
            if (module.equals("_the_standalone_module")) {
               filelist.add(submod);
            } else {
               filelist.add(module + '/' + submod);
            }
         }
      }

      this.files = (String[])((String[])filelist.toArray(new String[filelist.size()]));
   }

   private final boolean isASystemResourceRequiringRestart() {
      if (this.mbean instanceof SystemResourceMBean) {
         ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         if (serverRuntime.isRestartRequired() || serverRuntime.isRestartPendingForSystemResource(this.mbean.getName())) {
            return true;
         }

         if (DeployHelper.isMBeanInPartitionScope(this.mbean)) {
            PartitionMBean partition = DeployHelper.findContainingPartitionMBean(this.mbean);
            String partitionName = partition.getName();
            PartitionRuntimeMBean partitionRuntimeMBean = serverRuntime.lookupPartitionRuntime(partitionName);
            return partitionRuntimeMBean.isRestartPendingForSystemResource(this.mbean.getName());
         }
      }

      return false;
   }

   protected void doCommit() throws IOException, DeploymentException {
      if (this.isASystemResourceRequiringRestart()) {
         if (this.isDebugEnabled()) {
            this.debug("System Resource '" + this.mbean.getName() + "' requires a restart for the changes to take effect - commit returning without proceeding further");
         }

         if (this.getState() != null) {
            this.getState().setCurrentState(ModuleListener.STATE_ACTIVE.toString());
         }
      } else {
         this.deploymentContext.setUpdatedResourceURIs(this.files);
         if (this.isDebugEnabled()) {
            this.debug("DynamicUpdateOperation: Invoking activateUpdate() on Container.");
         }

         this.appcontainer.activateUpdate(this.deploymentContext);
      }

      this.complete(3, (Exception)null);
   }

   protected final void doCancel() {
      if (this.appcontainer != null) {
         this.cancelDataUpdate();
         int currentAppState = this.getState(this.appcontainer);
         if (currentAppState == 4) {
            this.deploymentContext.setUpdatedResourceURIs(this.files);
            if (this.isDebugEnabled()) {
               this.debug("DynamicUpdateOperation: Invoking rollbackUpdate() on Container.");
            }

            this.appcontainer.rollbackUpdate(this.deploymentContext);
            if (this.isDebugEnabled()) {
               this.debug("DynamicUpdateOperation: rollbackUpdate() on Container finished.");
            }
         }

      }
   }

   protected void initDataUpdate() throws DeploymentException {
      try {
         final List deltaFiles = new ArrayList();
         if (this.files != null) {
            deltaFiles.addAll(Arrays.asList(this.files));
         }

         if (this.isDebugEnabled()) {
            this.debug("DynamicUpdateOperation.initDataUpdate: delta-files : " + deltaFiles);
         }

         final boolean isPlanUpdate = this.deploymentData.isPlanUpdate();
         if (isPlanUpdate) {
            if (!(this.getApplication() instanceof AppDeployment)) {
               throw new DeploymentException("PlanUpdate cannot be applied for SystemResources");
            }

            AppDeployment appDeployment = (AppDeployment)this.getApplication();
            appDeployment.updateDescriptorsPathInfo();
            String relativePlanPath = appDeployment.getRelativePlanPath();
            if (relativePlanPath == null || relativePlanPath.length() == 0) {
               throw new DeploymentException("Application " + appDeployment.getName() + " does not contain plan path to update");
            }

            if (this.isDebugEnabled()) {
               this.debug("DynamicUpdateOperation: deltaFiles is : " + deltaFiles + " : " + deltaFiles.getClass().getName());
            }

            deltaFiles.add(relativePlanPath);
         }

         if (!deltaFiles.isEmpty()) {
            final boolean isDelete = this.deploymentData != null && this.deploymentData.getDelete();
            this.getApplication().initDataUpdate(new DataUpdateRequestInfo() {
               public List getDeltaFiles() {
                  return deltaFiles;
               }

               public List getTargetFiles() {
                  return new ArrayList();
               }

               public long getRequestId() {
                  return DynamicUpdateOperation.this.requestId;
               }

               public boolean isStatic() {
                  return false;
               }

               public boolean isDelete() {
                  return isDelete;
               }

               public boolean isPlanUpdate() {
                  return isPlanUpdate;
               }

               public boolean isStaging() {
                  return false;
               }

               public boolean isPlanStaging() {
                  return false;
               }
            });
         }
      } catch (IOException var5) {
         var5.printStackTrace();
         throw new DeploymentException("Error occured while initiating data update", var5);
      }
   }

   protected void ensureAppContainerSet() throws DeploymentException {
      super.ensureAppContainerSet();
      if (this.appcontainer == null) {
         Loggable l = DeployerRuntimeLogger.logNullAppLoggable(this.mbean.getName(), DeployerRuntimeTextTextFormatter.getInstance().messageRedeploy());
         l.log();
         DeploymentException de = new DeploymentException(l.getMessage());
         this.complete(2, de);
         throw de;
      }
   }
}
