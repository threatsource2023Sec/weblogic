package weblogic.management.descriptors;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

public class BaseXMLElementMBeanImpl extends XMLElementMBeanDelegate {
   public MBeanNotificationInfo[] getNotificationInfo() {
      return null;
   }

   public void addNotificationListener(NotificationListener nl, NotificationFilter nf, Object o) throws IllegalArgumentException {
   }

   public void removeNotificationListener(NotificationListener nl) throws ListenerNotFoundException {
   }
}
