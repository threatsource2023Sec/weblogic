package weblogic.cluster;

import weblogic.common.internal.PeerInfo;
import weblogic.protocol.ServerIdentity;

public interface ClusterMemberInfo {
   ServerIdentity identity();

   String serverName();

   String hostAddress();

   String machineName();

   String version();

   long joinTime();

   int loadWeight();

   String replicationGroup();

   String preferredSecondaryGroup();

   String domainName();

   String clusterName();

   boolean isMigratableServer();

   String replicationChannel();

   PeerInfo peerInfo();
}
