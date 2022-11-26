package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ServerInformation;

public class GroupViewUpdateMessage extends BaseClusterMessage {
   static final int ADD = 1;
   static final int REMOVE = 2;
   private final int operation;
   private final ServerInformation serverInformation;
   private long versionNumber;
   private static final long serialVersionUID = 562510693137563962L;

   public GroupViewUpdateMessage(ServerInformation senderInformation, int operation, ServerInformation otherInfo, long version) {
      super(senderInformation, 6);
      this.operation = operation;
      this.serverInformation = otherInfo;
      this.versionNumber = version;
   }

   public static GroupViewUpdateMessage createMemberAdded(ServerInformation leaderInformation, ServerInformation otherInfo, long version) {
      return new GroupViewUpdateMessage(leaderInformation, 1, otherInfo, version);
   }

   public static GroupViewUpdateMessage createMemberRemoved(ServerInformation leaderInformation, ServerInformation otherInfo, long version) {
      return new GroupViewUpdateMessage(leaderInformation, 2, otherInfo, version);
   }

   int getOperation() {
      return this.operation;
   }

   ServerInformation getServerInformation() {
      return this.serverInformation;
   }

   public String toString() {
      return this.operation == 1 ? "[GroupViewUpdate ADD operation with server info " + this.serverInformation + "]" : "[GroupViewUpdate REMOVE operation with server info " + this.serverInformation + "]";
   }

   public long getVersionNumber() {
      return this.versionNumber;
   }
}
