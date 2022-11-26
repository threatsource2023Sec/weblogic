package weblogic.deploy.api.internal.utils;

import javax.management.Notification;
import weblogic.management.RemoteNotificationListener;

class DeployerHelperListener implements RemoteNotificationListener {
   JMXDeployerHelper helper = null;

   DeployerHelperListener(JMXDeployerHelper helper) {
      this.helper = helper;
   }

   public void handleNotification(Notification notification, Object handback) {
      this.helper.queueNotification(notification);
   }
}
