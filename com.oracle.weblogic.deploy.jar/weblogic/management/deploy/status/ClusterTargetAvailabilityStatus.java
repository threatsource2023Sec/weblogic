package weblogic.management.deploy.status;

import java.io.Serializable;
import weblogic.management.TargetAvailabilityStatus;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;

/** @deprecated */
@Deprecated
public class ClusterTargetAvailabilityStatus extends BaseTargetAvailabilityStatus implements Serializable, TargetAvailabilityStatus {
   private static final long serialVersionUID = -2825946542858790432L;

   public ClusterTargetAvailabilityStatus(ClusterMBean clusterMBean, boolean isStaged) {
      super(clusterMBean.getName(), 2, isStaged);
      ServerMBean[] clusterServers = clusterMBean.getServers();
      if (clusterServers != null) {
         for(int i = 0; i < clusterServers.length; ++i) {
            if (isStaged) {
               this.unavailableServersAvailabilityMap.put(clusterServers[i].getName(), new Integer(2));
            } else {
               this.unavailableServersAvailabilityMap.put(clusterServers[i].getName(), new Integer(0));
            }
         }
      }

   }
}
