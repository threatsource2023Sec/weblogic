package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class JoinResponseMessage extends BaseClusterMessage {
   private static final String ACCEPTED = "accepted";
   private static final String REJECTED = "rejected";
   private final ClusterGroupView groupView;
   private final LeaseView leaseView;
   private final String decision;
   private static final long serialVersionUID = -1916452981168517591L;

   private JoinResponseMessage(ClusterGroupView view, LeaseView leaseView) {
      super(view.getLeaderInformation(), 3);
      this.groupView = view;
      this.leaseView = leaseView;
      this.decision = "accepted";
   }

   private JoinResponseMessage(ServerInformation leader) {
      super(leader, 3);
      this.groupView = null;
      this.leaseView = null;
      this.decision = "rejected";
   }

   static JoinResponseMessage getRejectedResponse(ServerInformation leader) {
      return new JoinResponseMessage(leader);
   }

   static JoinResponseMessage getAcceptedResponse(ClusterGroupView groupView, LeaseView leaseView) {
      return new JoinResponseMessage(groupView, leaseView);
   }

   ClusterGroupView getGroupView() {
      return this.groupView;
   }

   LeaseView getLeaseView() {
      return this.leaseView;
   }

   boolean isAccepted() {
      return "accepted".equals(this.decision);
   }
}
