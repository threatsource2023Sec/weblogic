package weblogic.rmi.cluster;

import weblogic.rmi.extensions.server.RemoteReference;

public interface ReplicaInfo {
   RemoteReference getRemoteRef();

   String[] getTargetClusterAddresses();
}
