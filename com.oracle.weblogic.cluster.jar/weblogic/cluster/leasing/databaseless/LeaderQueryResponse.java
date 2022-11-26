package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ClusterResponse;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class LeaderQueryResponse implements ClusterResponse {
   private final ServerInformation leaderInformation;
   private static final long serialVersionUID = -2909310366232781684L;

   LeaderQueryResponse() {
      this.leaderInformation = null;
   }

   LeaderQueryResponse(ServerInformation leaderInformation) {
      this.leaderInformation = leaderInformation;
   }

   ServerInformation getLeaderInformation() {
      return this.leaderInformation;
   }

   public String toString() {
      return "[LeaderQueryResponse is " + this.leaderInformation + "]";
   }
}
