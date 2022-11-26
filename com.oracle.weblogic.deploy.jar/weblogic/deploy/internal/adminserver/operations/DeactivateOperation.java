package weblogic.deploy.internal.adminserver.operations;

import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;

public final class DeactivateOperation extends AbstractOperation {
   public DeactivateOperation() {
      this.controlOperation = true;
      this.taskType = 3;
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean alternateDeployable = null;
      String appName = OperationHelper.ensureAppName(name);
      String taskName = OperationHelper.getTaskString(this.taskType);
      String version = null;
      if (isDebugEnabled()) {
         this.printDebugStartMessage(source, appName, (String)version, info, id, taskName, stagingMode);
      }

      OperationHelper.assertNameIsNonNull(appName, taskName);
      OperationHelper.validateVersionForDeprecatedOp(info, name, taskName, 8);
      AppDeploymentMBean deployable = ApplicationUtils.getAppDeployment(this.beanFactory.getEditableDomain(), appName, (String)null);
      if (deployable == null) {
         DomainMBean alternateDomain = this.beanFactory.getAlternateEditableDomain();
         if (alternateDomain != null) {
            alternateDeployable = ApplicationUtils.getAppDeployment(alternateDomain, appName, (String)null);
         }
      }

      OperationHelper.assertAppIsNonNull(deployable, appName, (String)version, taskName, alternateDeployable);
      return deployable;
   }

   protected AbstractOperation createCopy() {
      return new DeactivateOperation();
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }
}
