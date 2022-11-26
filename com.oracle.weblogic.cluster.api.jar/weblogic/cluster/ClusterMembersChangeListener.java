package weblogic.cluster;

import java.util.EventListener;

public interface ClusterMembersChangeListener extends EventListener {
   void clusterMembersChanged(ClusterMembersChangeEvent var1);
}
