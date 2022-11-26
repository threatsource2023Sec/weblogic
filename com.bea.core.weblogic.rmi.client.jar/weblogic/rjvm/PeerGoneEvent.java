package weblogic.rjvm;

import java.io.IOException;
import java.util.EventObject;

public final class PeerGoneEvent extends EventObject {
   private RJVM rjvm;
   private String partitionName;
   private IOException reason;
   private boolean suppress;

   public String getPartitionName() {
      return this.partitionName;
   }

   PeerGoneEvent(RJVM rjvm, IOException reason, boolean suppress) {
      super(rjvm);
      this.reason = reason;
      this.suppress = suppress;
   }

   PeerGoneEvent(RJVM rjvm, IOException reason, boolean suppress, String partitionName) {
      super(rjvm);
      this.partitionName = partitionName;
      this.reason = reason;
      this.suppress = suppress;
   }

   PeerGoneEvent(RJVM rjvm, IOException reason) {
      super(rjvm);
      this.reason = reason;
      this.suppress = false;
   }

   public JVMID getID() {
      return ((RJVM)this.getSource()).getID();
   }

   public IOException getReason() {
      return this.reason;
   }

   public String toString() {
      return super.toString() + " - id: '" + this.getID() + "', reason: '" + this.getReason() + "'";
   }

   public boolean suppressPeerGoneEvent() {
      return this.suppress;
   }
}
