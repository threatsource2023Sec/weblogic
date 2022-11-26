package weblogic.deploy.api.internal.utils;

import java.io.Serializable;
import javax.management.Notification;
import javax.management.NotificationFilter;
import weblogic.management.DeploymentNotification;

class DeployerHelperFilter implements NotificationFilter, Serializable {
   public boolean isNotificationEnabled(Notification n) {
      return n instanceof DeploymentNotification;
   }
}
