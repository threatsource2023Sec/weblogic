package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class LeaderQueryMessage extends BaseClusterMessage {
   private static final long serialVersionUID = -5014982557706100655L;

   private LeaderQueryMessage(ServerInformation sender) {
      super(sender, 10);
   }

   public String toString() {
      return "[LeaderQueryMessage from " + this.getSenderInformation() + "]";
   }

   static LeaderQueryMessage create(ServerInformation sender) {
      return new LeaderQueryMessage(sender);
   }
}
