package weblogic.corba.j2ee.naming;

import weblogic.iiop.IIOPRemoteRef;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaVersion;

public interface RemoteReplicaClient {
   IIOPRemoteRef lookupNewReplica(String var1, ReplicaID var2, ReplicaVersion var3, String var4);
}
