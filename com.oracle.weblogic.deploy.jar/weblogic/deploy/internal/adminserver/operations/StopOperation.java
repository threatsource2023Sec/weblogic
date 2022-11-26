package weblogic.deploy.internal.adminserver.operations;

import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;

public final class StopOperation extends AbstractOperation {
   public StopOperation() {
      this.controlOperation = true;
      this.taskType = 8;
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean alternateDeployable = null;
      String appName = OperationHelper.ensureAppName(name);
      String taskName = OperationHelper.getTaskString(this.taskType);
      String versionId = OperationHelper.getVersionIdFromData(info, name);
      DomainMBean domain = this.beanFactory.getEditableDomain();
      OperationHelper.assertNameIsNonNull(appName, taskName);
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
      OperationHelper.checkForClusterTargetSubset(domain, info, deployable, taskName, this.controlOperation);
      if (!ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(info)) {
         this.validateAllTargets(deployable, info, appName, versionId, taskName);
      }

      deployable = OperationHelper.getActiveVersionIfNeeded(domain, versionId, deployable, appName, info, taskName);
      return deployable;
   }

   protected AbstractOperation createCopy() {
      return new StopOperation();
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }
}
