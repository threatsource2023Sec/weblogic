package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ClusterResponse;

public class StateDumpResponse implements ClusterResponse {
   private final ClusterGroupView groupView;
   private final LeaseView leaseView;
   private static final long serialVersionUID = -8030627564990906434L;

   public StateDumpResponse(ClusterGroupView groupView, LeaseView leaseView) {
      this.groupView = groupView;
      this.leaseView = leaseView;
   }

   public ClusterGroupView getGroupView() {
      return this.groupView;
   }

   public LeaseView getLeaseView() {
      return this.leaseView;
   }
}
