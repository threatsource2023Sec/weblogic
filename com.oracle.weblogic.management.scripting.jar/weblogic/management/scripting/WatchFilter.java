package weblogic.management.scripting;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;

public class WatchFilter implements NotificationFilter {
   String[] anames = null;

   public WatchFilter(String[] attrNames) {
      this.anames = attrNames;
   }

   public boolean isNotificationEnabled(Notification n) {
      if (n instanceof AttributeChangeNotification) {
         AttributeChangeNotification acn = (AttributeChangeNotification)n;
         String aname = acn.getAttributeName();
         if (this.anames == null) {
            return true;
         }

         for(int i = 0; i < this.anames.length; ++i) {
            if (aname.equals(this.anames[i])) {
               return true;
            }
         }
      } else if (n instanceof MBeanServerNotification) {
         MBeanServerNotification msn = (MBeanServerNotification)n;
         if (msn.getType().equals("JMX.mbean.unregistered")) {
            return true;
         }
      }

      return false;
   }
}
