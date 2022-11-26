package weblogic.management.deploy.status;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.deploy.internal.ComponentTarget;
import weblogic.utils.Debug;

/** @deprecated */
@Deprecated
public class VirtualHostTargetAvailabilityStatus extends BaseTargetAvailabilityStatus {
   private static final long serialVersionUID = 8676607003211923506L;
   private HashSet clusterSet = null;

   public VirtualHostTargetAvailabilityStatus(VirtualHostMBean vhMBean, boolean isStaged) {
      super(vhMBean.getName(), 3, isStaged);
      this.clusterSet = new HashSet();
      TargetMBean[] vhTargets = vhMBean.getTargets();
      if (vhTargets != null) {
         for(int i = 0; i < vhTargets.length; ++i) {
            if (vhTargets[i] instanceof ClusterMBean) {
               ClusterTargetAvailabilityStatus clusterStatus = new ClusterTargetAvailabilityStatus((ClusterMBean)vhTargets[i], isStaged);
               this.clusterSet.add(clusterStatus);
            } else if (isStaged) {
               this.unavailableServersAvailabilityMap.put(vhTargets[i].getName(), new Integer(2));
            } else {
               this.unavailableServersAvailabilityMap.put(vhTargets[i].getName(), new Integer(0));
            }
         }
      }

   }

   public int getDeploymentStatus() {
      int status = 0;
      if (this.clusterSet.size() > 0) {
         Iterator itr = this.clusterSet.iterator();
         int i = 0;

         while(itr.hasNext()) {
            ClusterTargetAvailabilityStatus clusterStatus = (ClusterTargetAvailabilityStatus)itr.next();
            if (i == 0) {
               status = clusterStatus.getDeploymentStatus();
               ++i;
            } else if (status != clusterStatus.getDeploymentStatus()) {
               status = 2;
            }
         }

         if ((this.availableServers.size() > 0 || this.unavailableServersAvailabilityMap.size() > 0) && status != super.getDeploymentStatus()) {
            status = 2;
         }
      } else {
         status = super.getDeploymentStatus();
      }

      return status;
   }

   public Set getClustersAvailabilityStatus() {
      return this.clusterSet;
   }

   public void updateAvailabilityStatus(ComponentTarget compTarget) {
      if (compTarget.isVirtualHostClustered()) {
         String clusterName = compTarget.getClusterTarget();
         Iterator itr = this.clusterSet.iterator();

         while(itr.hasNext()) {
            ClusterTargetAvailabilityStatus clusterStatus = (ClusterTargetAvailabilityStatus)itr.next();
            if (clusterStatus.getTargetName().equals(clusterName)) {
               clusterStatus.updateAvailabilityStatus(compTarget);
               break;
            }
         }
      } else {
         super.updateAvailabilityStatus(compTarget);
      }

   }

   public void updateUnavailabilityStatus(List stagedServersList) {
      if (this.isStaged) {
         Iterator itr = this.clusterSet.iterator();

         while(itr.hasNext()) {
            ClusterTargetAvailabilityStatus clusterStatus = (ClusterTargetAvailabilityStatus)itr.next();
            clusterStatus.updateUnavailabilityStatus(stagedServersList);
         }

         super.updateUnavailabilityStatus(stagedServersList);
      } else {
         Debug.assertion(false, "updateUnavailabilityStatus applicable only for staged applications.");
      }

   }
}
