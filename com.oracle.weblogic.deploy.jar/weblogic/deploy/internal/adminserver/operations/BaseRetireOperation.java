package weblogic.deploy.internal.adminserver.operations;

import java.security.AccessController;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.DeployerRuntimeImpl;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class BaseRetireOperation extends AbstractOperation {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public BaseRetireOperation() {
      this.controlOperation = true;
   }

   public final DeploymentTaskRuntimeMBean executeControlOperation(String source, String name, String stagingMode, DeploymentData info, String taskId, boolean startIt, AuthenticatedSubject authenticatedSubject) throws ManagementException {
      try {
         String appName = OperationHelper.ensureAppName(name);
         String taskName = OperationHelper.getTaskString(this.taskType);
         String versionId = OperationHelper.getVersionIdFromData(info, name);
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         OperationHelper.assertNameIsNonNull(appName, taskName);
         if (isDebugEnabled()) {
            this.printDebugStartMessage(source, appName, versionId, info, taskId, taskName, stagingMode);
         }

         AppDeploymentMBean appMBean = ApplicationUtils.getAppDeployment(domain, appName, versionId);
         AppDeploymentMBean alternateAppMBean = null;
         if (appMBean == null) {
            DomainMBean alternateDomain = this.beanFactory.getAlternateEditableDomain();
            if (alternateDomain != null) {
               alternateAppMBean = ApplicationUtils.getAppDeployment(alternateDomain, appName, versionId);
            }
         }

         OperationHelper.assertAppIsNonNull(appMBean, appName, versionId, taskName, alternateAppMBean);
         this.createRuntimeObjects(source, taskId, appMBean, info, this.getCreateTaskType(), domain, authenticatedSubject, true);
         DeployerRuntimeImpl deployerRuntime = this.deploymentManager.getDeployerRuntime();
         deployerRuntime.registerTaskRuntime(taskId, this.deploymentTask);
         this.createAndInitDeploymentRequest();
         if (startIt) {
            this.deploymentTask.start();
         }

         return this.deploymentTask;
      } catch (Throwable var15) {
         this.deploymentManager.deploymentFailedBeforeStart(this.deployment, var15);
         OperationHelper.logTaskFailed(name, this.taskType, var15);
         if (var15 instanceof ManagementException) {
            throw (ManagementException)var15;
         } else {
            throw new ManagementException(var15.getMessage(), var15);
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
