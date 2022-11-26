package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Service;

@Service
public class ClusterMasterDiscovererImpl implements ClusterMasterDiscoverer {
   public boolean isClusterMaster() {
      MigratableServerService serviceImpl = MigratableServerService.theOne();
      return serviceImpl != null ? serviceImpl.isClusterMaster() : false;
   }
}
