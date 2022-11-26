package weblogic.rmi.extensions.server;

import weblogic.rjvm.JVMID;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.cluster.ReplicaAwareInfo;
import weblogic.rmi.cluster.ReplicaHandler;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.internal.ServerReference;

public interface ClusterAwareRemoteReference extends RemoteReference {
   boolean pin(JVMID var1);

   ReplicaList getReplicaList();

   RemoteReference getCurrentReplica();

   ReplicaHandler getReplicaHandler();

   boolean isInitialized();

   ClusterableRemoteRef initialize(ReplicaAwareInfo var1);

   void initialize(ServerReference var1, String var2);

   RemoteReference getPrimaryRef();
}
