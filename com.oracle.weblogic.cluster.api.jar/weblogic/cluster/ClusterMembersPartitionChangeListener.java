package weblogic.cluster;

import java.util.EventListener;

public interface ClusterMembersPartitionChangeListener extends EventListener {
   void clusterMembersPartitionChanged(ClusterMembersPartitionChangeEvent var1);
}
