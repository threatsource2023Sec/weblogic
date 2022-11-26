package weblogic.rmi.cluster;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteReplicaService extends Remote {
   String JNDI_NAME = "weblogic.cluster.RemoteReplicaService";

   ReplicaInfo findReplica(ReplicaID var1, ReplicaVersion var2, String var3) throws RemoteException;
}
