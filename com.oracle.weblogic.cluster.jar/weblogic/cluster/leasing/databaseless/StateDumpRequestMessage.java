package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class StateDumpRequestMessage extends BaseClusterMessage {
   private final long groupVersion;
   private final long leaseVersion;
   private static final long serialVersionUID = 2701981993990033628L;

   public StateDumpRequestMessage(ServerInformation senderInformation, long groupVersion, long leaseVersion) {
      super(senderInformation, 8);
      this.groupVersion = groupVersion;
      this.leaseVersion = leaseVersion;
   }

   public static StateDumpRequestMessage create(ServerInformation localInformation, ClusterGroupView groupView, LeaseView leaseView) {
      return new StateDumpRequestMessage(localInformation, groupView == null ? 0L : groupView.getVersionNumber(), leaseView == null ? 0L : leaseView.getVersionNumber());
   }

   public String toString() {
      return "[StateDumpRequestMessage with group version " + this.groupVersion + ", leaseVersion " + this.leaseVersion + "]";
   }
}
