package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class ClusterLeaderHeartbeatMessage extends BaseClusterMessage {
   private final long groupViewVersion;
   private final long leaseViewVersion;
   private static final long serialVersionUID = -3226950838012588670L;

   public ClusterLeaderHeartbeatMessage(ServerInformation senderInformation, long groupViewVersion, long leaseViewVersion) {
      super(senderInformation, 7);
      this.groupViewVersion = groupViewVersion;
      this.leaseViewVersion = leaseViewVersion;
   }

   public static ClusterLeaderHeartbeatMessage create(ClusterGroupView groupView, LeaseView leaseView) {
      return new ClusterLeaderHeartbeatMessage(groupView.getLeaderInformation(), groupView.getVersionNumber(), leaseView.getVersionNumber());
   }

   public long getGroupViewVersion() {
      return this.groupViewVersion;
   }

   public long getLeaseViewVersion() {
      return this.leaseViewVersion;
   }

   public String toString() {
      return "[leader heartbeat message with group version " + this.groupViewVersion + " and lease version " + this.leaseViewVersion + "]";
   }
}
