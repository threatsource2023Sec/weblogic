package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.cluster.ClusterHelper;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;

public class PartitionAwareLocalSecondarySelector extends LocalSecondarySelector {
   private final String partitionId;

   protected PartitionAwareLocalSecondarySelector(String partitionId) {
      this.partitionId = partitionId;
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cmce) {
      HostID host = cmce.getClusterMemberInfo().identity();
      switch (cmce.getAction()) {
         case 1:
            this.removeDeadServer(cmce.getClusterMemberInfo());
            ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
            ((ReplicationManager)serviceLocator.getService(ReplicationManager.class, new Annotation[0])).changeSecondary(host);
            ((AsyncReplicationManager)serviceLocator.getService(AsyncReplicationManager.class, new Annotation[0])).changeSecondary(host);
         case 0:
         case 2:
         default:
      }
   }

   protected void logSecondaryServerReset() {
      if (ReplicationDebugLogger.isDebugEnabled() && this.clusterHasSecondarySrvrs) {
         Object candidate = this.getSecondarySrvr();
         ReplicationDebugLogger.debug(" Secondary server reset to " + candidate + " from cadidate list: " + this.getSecondaryCandidates() + " for partition " + ClusterHelper.getPartitionNameFromPartitionId(this.partitionId));
      }

   }

   protected void logRemoveDeadSecondarySrvr(HostID hostID) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         if (this.clusterHasSecondarySrvrs) {
            ReplicationDebugLogger.debug("Unreachable secondary server: " + hostID + " New secondary server " + this.getSecondarySrvr() + " from cadidate list: " + this.getSecondaryCandidates() + " for partition " + ClusterHelper.getPartitionNameFromPartitionId(this.partitionId));
         } else {
            ReplicationDebugLogger.debug("Unreachable secondary server: " + hostID + " and there are no secondary servers currently available to replication for partition " + ClusterHelper.getPartitionNameFromPartitionId(this.partitionId));
         }
      }

   }

   protected void logNewSecondaryServer() {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("New secondary server is " + this.currentSecondary + " from cadidate list: " + this.getSecondaryCandidates() + " for partition " + ClusterHelper.getPartitionNameFromPartitionId(this.partitionId));
      }

   }
}
