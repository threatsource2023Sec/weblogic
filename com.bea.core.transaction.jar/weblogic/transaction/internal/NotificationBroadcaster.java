package weblogic.transaction.internal;

import java.rmi.Remote;

public interface NotificationBroadcaster extends Remote {
   void addNotificationListener(NotificationListener var1, Object var2) throws IllegalArgumentException;

   void removeNotificationListener(NotificationListener var1) throws ListenerNotFoundException;
}
