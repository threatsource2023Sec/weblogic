package weblogic.management.j2ee;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.j2ee.ListenerRegistration;

public class WLSListenerRegistry implements ListenerRegistration, Serializable {
   private ListenerRegistry service = null;

   public WLSListenerRegistry(ListenerRegistry registry) {
      this.service = registry;
   }

   public WLSListenerRegistry() {
   }

   public void addNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object object) throws InstanceNotFoundException, RemoteException {
      this.service.addListener(objectName, notificationListener, notificationFilter, object);
   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException, RemoteException {
      this.service.removeListener(objectName, notificationListener);
   }
}
