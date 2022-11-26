package weblogic.messaging.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DispatcherOneWay extends Remote {
   void dispatchOneWay(Request var1) throws RemoteException;

   void dispatchOneWayWithId(Request var1, int var2) throws RemoteException;
}
