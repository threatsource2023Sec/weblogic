package weblogic.deploy.internal.adminserver.operations;

import java.security.AccessController;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.DeployerRuntimeImpl;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ExtendLoaderOperation extends AbstractOperation {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ExtendLoaderOperation() {
      this.controlOperation = true;
      this.taskType = 14;
   }

   protected AbstractOperation createCopy() {
      return new ExtendLoaderOperation();
   }

   public final DeploymentTaskRuntimeMBean executeControlOperation(String source, String name, String stagingMode, DeploymentData info, String taskId, boolean startIt, AuthenticatedSubject authenticatedSubject) throws ManagementException {
      try {
         String taskName = OperationHelper.getTaskString(this.taskType);
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         if (isDebugEnabled()) {
            this.printDebugStartMessage(source, (String)null, (String)null, info, taskId, taskName, stagingMode);
         }

         AppDeploymentMBean app = this.createMBeans(source, "extend-loader", info, (String)null);
         DeploymentOptions deplOpts = info.getDeploymentOptions();
         deplOpts.setStageMode(stagingMode);
         if (stagingMode != null) {
            app.setStagingMode(stagingMode);
         }

         info.addFiles(new String[]{source});
         this.createRuntimeObjects(source, taskId, app, info, this.getCreateTaskType(), domain, authenticatedSubject, true);
         DeployerRuntimeImpl deployerRuntime = this.deploymentManager.getDeployerRuntime();
         deployerRuntime.registerTaskRuntime(taskId, this.deploymentTask);
         this.createAndInitDeploymentRequest();
         if (startIt) {
            this.deploymentTask.start();
         }

         return this.deploymentTask;
      } catch (Throwable var13) {
         this.deploymentManager.deploymentFailedBeforeStart(this.deployment, var13);
         OperationHelper.logTaskFailed(name, this.taskType, var13);
         if (var13 instanceof ManagementException) {
            throw (ManagementException)var13;
         } else {
            throw new ManagementException(var13.getMessage(), var13);
         }
      }
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      return null;
   }

   private void createAndInitDeploymentRequest() throws ManagementException {
      DeploymentRequest req = DeploymentService.getDeploymentService().createDeploymentRequest();
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }
}
