package weblogic.cluster.singleton;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSingletonServicesControl extends Remote {
   String NAME = "weblogic.cluster.RemoteSingletonServicesControl";

   void activateService(String var1) throws RemoteException;

   void deactivateService(String var1) throws RemoteException;

   void restartService(String var1) throws RemoteException;

   boolean isServiceActive(String var1) throws RemoteException;

   boolean isServiceRegistered(String var1) throws RemoteException;
}
