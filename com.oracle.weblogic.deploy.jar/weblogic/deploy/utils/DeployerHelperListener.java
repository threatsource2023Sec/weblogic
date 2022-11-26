package weblogic.deploy.utils;

import javax.management.Notification;
import javax.management.NotificationListener;
import weblogic.management.DeploymentNotification;

class DeployerHelperListener implements NotificationListener {
   DeployerHelper helper = null;
   private String taskId;

   DeployerHelperListener(String taskId, DeployerHelper helper) {
      this.taskId = taskId;
      this.helper = helper;
   }

   public void handleNotification(Notification notification, Object handback) {
      DeploymentNotification note = (DeploymentNotification)notification;
      this.helper.showDeploymentNotificationInformation(this.taskId, note);
   }
}
