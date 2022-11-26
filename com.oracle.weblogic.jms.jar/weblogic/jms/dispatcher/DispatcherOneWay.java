package weblogic.jms.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DispatcherOneWay extends Remote {
   void dispatchOneWay(Request var1) throws RemoteException;
}
