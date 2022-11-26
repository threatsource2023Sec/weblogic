package weblogic.management.internal;

import javax.management.NotificationFilter;
import javax.management.NotificationListener;

public interface BaseNotificationListener extends NotificationListener {
   void addFilterAndHandback(NotificationFilter var1, Object var2);

   void remove();

   void unregister();
}
