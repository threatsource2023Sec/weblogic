package weblogic.deploy.utils;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;

public class TaskCompletionNotificationFilter implements NotificationFilter {
   private static final Object FAILED = new Integer(3);
   private static final Object COMPLETED = new Integer(2);
   private static final Object DEFERRED = new Integer(4);

   public boolean isNotificationEnabled(Notification n) {
      if (!(n instanceof AttributeChangeNotification)) {
         return false;
      } else {
         AttributeChangeNotification attr = (AttributeChangeNotification)n;
         if (!"State".equals(attr.getAttributeName())) {
            return false;
         } else {
            Object newVal = attr.getNewValue();
            return newVal.equals(FAILED) || newVal.equals(COMPLETED) || newVal.equals(DEFERRED);
         }
      }
   }
}
