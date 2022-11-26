package weblogic.transaction.internal;

import java.rmi.Remote;
import java.util.EventListener;

public interface NotificationListener extends EventListener, Remote {
   void handleNotification(Notification var1, Object var2);
}
