package weblogic.ejb.container.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.activation.Activator;

public interface Ejb3StatefulHome extends Ejb3SessionHome, Remote {
   Object getBusinessImpl(Class var1, Activator var2, Class var3, Class var4) throws RemoteException;

   Object getBusinessImpl(String var1) throws RemoteException;

   Object getComponentImpl(Object var1) throws RemoteException;

   boolean needToConsiderReplicationService() throws RemoteException;
}
