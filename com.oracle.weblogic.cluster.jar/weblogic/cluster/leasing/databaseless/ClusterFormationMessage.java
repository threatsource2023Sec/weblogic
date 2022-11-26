package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.BaseClusterMessage;

public class ClusterFormationMessage extends BaseClusterMessage {
   private final ClusterGroupView groupView;

   public ClusterFormationMessage(ClusterGroupView view) {
      super(view.getLeaderInformation(), 1);
      this.groupView = view;
   }

   ClusterGroupView getGroupView() {
      return this.groupView;
   }
}
