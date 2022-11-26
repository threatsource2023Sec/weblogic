package weblogic.management.j2ee;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

public interface ListenerRegistry extends Remote {
   void addListener(ObjectName var1, NotificationListener var2, NotificationFilter var3, Object var4) throws InstanceNotFoundException, RemoteException;

   void removeListener(ObjectName var1, NotificationListener var2) throws InstanceNotFoundException, ListenerNotFoundException, RemoteException;
}
