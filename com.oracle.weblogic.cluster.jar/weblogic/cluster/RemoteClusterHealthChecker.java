package weblogic.cluster;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteClusterHealthChecker extends Remote {
   String JNDI_NAME = "weblogic/cluster/RemoteClusterHealthChecker";

   ArrayList checkClusterMembership(long var1) throws RemoteException;
}
