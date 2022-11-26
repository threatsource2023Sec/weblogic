package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ClusterResponse;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class ClusterFormationResponse implements ClusterResponse {
   private final ClusterFormationMessage clusterFormationMessage;
   private final ClusterFormationMessage acceptedFormationMessage;
   private final boolean accepted;
   private final ServerInformation leaderInformation;
   private ServerInformation localInformation;
   private final LeaseView leaseView;
   private static final long serialVersionUID = -2750818906106645449L;

   private ClusterFormationResponse(ClusterFormationMessage clusterFormationMessage, ClusterFormationMessage acceptedFormationMessage, ServerInformation leaderInformation, ServerInformation localInformation, LeaseView leaseView, boolean accepted) {
      this.clusterFormationMessage = clusterFormationMessage;
      this.acceptedFormationMessage = acceptedFormationMessage;
      this.leaderInformation = leaderInformation;
      this.localInformation = localInformation;
      this.leaseView = leaseView;
      this.accepted = accepted;
   }

   public static ClusterFormationResponse getAcceptedResponse(ClusterFormationMessage clusterFormationMessage, ClusterFormationMessage acceptedFormationMessage, ServerInformation localInformation, LeaseView leaseView) {
      return new ClusterFormationResponse(clusterFormationMessage, acceptedFormationMessage, (ServerInformation)null, localInformation, leaseView, true);
   }

   public static ClusterResponse getRejectedResponse(ClusterFormationMessage clusterFormationMessage, ClusterFormationMessage acceptedFormationMessage, ServerInformation leaderInformation, ServerInformation localInformation) {
      return new ClusterFormationResponse(clusterFormationMessage, acceptedFormationMessage, leaderInformation, localInformation, (LeaseView)null, false);
   }

   ClusterFormationMessage getAcceptedFormationMessage() {
      return this.acceptedFormationMessage;
   }

   boolean isAccepted() {
      return this.accepted;
   }

   ServerInformation getLeaderInformation() {
      return this.leaderInformation;
   }

   ServerInformation getReceiverInformation() {
      return this.localInformation;
   }

   public String toString() {
      return "[ClusterFormationResponse accepted " + this.accepted + " with leader info " + this.leaderInformation + " and accepted formation msg " + this.acceptedFormationMessage + "]";
   }

   public LeaseView getLeaseView() {
      return this.leaseView;
   }
}
