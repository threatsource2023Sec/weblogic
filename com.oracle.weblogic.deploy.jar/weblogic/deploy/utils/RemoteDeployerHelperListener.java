package weblogic.deploy.utils;

import weblogic.management.RemoteNotificationListener;

class RemoteDeployerHelperListener extends DeployerHelperListener implements RemoteNotificationListener {
   RemoteDeployerHelperListener(String taskId, DeployerHelper helper) {
      super(taskId, helper);
   }
}
