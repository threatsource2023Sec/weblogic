package weblogic.deploy.internal.adminserver.operations;

import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.TaskRuntimeValidator;

public final class StartOperation extends AbstractOperation {
   private static final StartTaskRuntimeValidator taskRuntimeValidator = new StartTaskRuntimeValidator();

   public StartOperation() {
      this.controlOperation = true;
      this.taskType = 7;
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String appName, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean alternateDeployable = null;
      String versionId = OperationHelper.getVersionIdFromData(info, (String)null);
      String taskName = OperationHelper.getTaskString(this.taskType);
      DomainMBean domain = this.beanFactory.getEditableDomain();
      String stageMode = null;
      if (isDebugEnabled()) {
         this.printDebugStartMessage(source, appName, versionId, info, id, taskName, (String)stageMode);
      }

      AppDeploymentMBean deployable = ApplicationUtils.getAppDeployment(domain, appName, versionId, info);
      if (deployable == null) {
         DomainMBean alternateDomain = this.beanFactory.getAlternateEditableDomain();
         if (alternateDomain != null) {
            alternateDeployable = ApplicationUtils.getAppDeployment(alternateDomain, appName, versionId, info);
         }
      }

      OperationHelper.assertAppIsNonNull(deployable, appName, versionId, taskName, alternateDeployable);
      OperationHelper.assertAppIsNotRetired(domain, deployable, appName, versionId, taskName);
      OperationHelper.checkForClusterTargetSubset(domain, info, deployable, taskName, this.controlOperation);
      if (!ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(info)) {
         this.validateAllTargets(deployable, info, appName, versionId, taskName);
      }

      deployable = OperationHelper.getActiveVersionIfNeeded(domain, versionId, deployable, appName, info, taskName);
      return deployable;
   }

   protected AbstractOperation createCopy() {
      return new StartOperation();
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }

   protected TaskRuntimeValidator getTaskRuntimeValidator() {
      return taskRuntimeValidator;
   }

   private static class StartTaskRuntimeValidator implements TaskRuntimeValidator {
      private StartTaskRuntimeValidator() {
      }

      public void validate(DeploymentTaskRuntime runningTaskRuntime, DeploymentTaskRuntime newTaskRuntime) throws ManagementException {
         TaskRuntimeValidatorHelper.validateDifferentOperation(runningTaskRuntime, newTaskRuntime);
         TaskRuntimeValidatorHelper.validateStartOperation(runningTaskRuntime, newTaskRuntime);
      }

      // $FF: synthetic method
      StartTaskRuntimeValidator(Object x0) {
         this();
      }
   }
}
