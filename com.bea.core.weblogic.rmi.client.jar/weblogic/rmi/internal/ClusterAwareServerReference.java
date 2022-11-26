package weblogic.rmi.internal;

import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.cluster.ReplicaAwareInfo;
import weblogic.rmi.extensions.server.RemoteReference;

public interface ClusterAwareServerReference extends ServerReference {
   RemoteReference getGenericReplicaAwareRemoteRef();

   PiggybackResponse handlePiggybackRequest(Object var1);

   ReplicaAwareInfo getInfo();
}
