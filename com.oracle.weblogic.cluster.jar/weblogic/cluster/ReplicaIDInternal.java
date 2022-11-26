package weblogic.cluster;

import java.io.Serializable;
import java.rmi.RemoteException;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaInfo;
import weblogic.rmi.cluster.ReplicaVersion;

public interface ReplicaIDInternal extends Serializable {
   ReplicaID getReplicaID();

   Object getReplicationKey();

   Object getReplicationServiceType();

   Object buildReplicaQueryRequestMessage(ReplicaVersion var1, boolean var2);

   ReplicaInfo processReplicaQueryResponseMessage(Object var1) throws RemoteException;

   ReplicaInfo getReplicaInfoWithTargetClusterAddressForMigratedPartition(String var1);
}
