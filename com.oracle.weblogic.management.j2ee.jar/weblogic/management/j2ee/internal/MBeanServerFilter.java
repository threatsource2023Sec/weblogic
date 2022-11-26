package weblogic.management.j2ee.internal;

import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.ObjectName;

class MBeanServerFilter implements NotificationFilter {
   public boolean isNotificationEnabled(Notification notification) {
      if (notification instanceof MBeanServerNotification) {
         MBeanServerNotification not = (MBeanServerNotification)notification;
         ObjectName objectName = not.getMBeanName();
         boolean result = Types.isValidWLSType(objectName.getKeyProperty("Type"));
         return result;
      } else {
         return false;
      }
   }
}
