package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ClusterMasterDiscoverer {
   boolean isClusterMaster();
}
