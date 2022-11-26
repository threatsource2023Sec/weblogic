package weblogic.j2ee;

import java.io.Serializable;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import weblogic.management.logging.WebLogicLogNotification;

public final class DeploymentNotificationFilter extends NotificationFilterSupport implements Serializable {
   private static final long serialVersionUID = 9189305265523348803L;

   public boolean isNotificationEnabled(Notification n) {
      if (n instanceof WebLogicLogNotification) {
         WebLogicLogNotification wln = (WebLogicLogNotification)n;
         String subsystem = wln.getSubsystem();
         return "J2EE".equals(subsystem);
      } else {
         return false;
      }
   }
}
