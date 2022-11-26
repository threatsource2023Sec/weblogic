package weblogic.cluster.singleton;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClusterMasterRemote extends Remote {
   String JNDI_NAME = "weblogic/cluster/singleton/ClusterMasterRemote";

   void setServerLocation(String var1, String var2) throws RemoteException;

   String getServerLocation(String var1) throws RemoteException;
}
