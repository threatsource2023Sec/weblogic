package weblogic.management;

import java.rmi.Remote;
import javax.management.Notification;
import javax.management.NotificationListener;

/** @deprecated */
@Deprecated
public interface RemoteNotificationListener extends NotificationListener, Remote {
   void handleNotification(Notification var1, Object var2);
}
