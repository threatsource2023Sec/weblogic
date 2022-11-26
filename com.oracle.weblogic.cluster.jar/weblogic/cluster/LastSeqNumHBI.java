package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class LastSeqNumHBI implements Externalizable {
   private static final long serialVersionUID = -2753700702225040722L;
   MulticastSessionId multicastSessionId;
   long lastSeqNum;
   boolean useHTTPForSD;

   LastSeqNumHBI(MulticastSessionId multicastSessionId, long lastSeqNum, boolean useHTTPForSD) {
      this.multicastSessionId = multicastSessionId;
      this.lastSeqNum = lastSeqNum;
      this.useHTTPForSD = useHTTPForSD;
   }

   public String toString() {
      return "lastSeqNumHBI MulticastSessionId:" + this.multicastSessionId + " seqNum:" + this.lastSeqNum;
   }

   public boolean equals(Object item) {
      if (item == null) {
         return false;
      } else {
         try {
            LastSeqNumHBI other = (LastSeqNumHBI)item;
            return other.multicastSessionId.equals(this.multicastSessionId);
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.multicastSessionId.hashCode();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.multicastSessionId);
      out.writeLong(this.lastSeqNum);
      out.writeBoolean(this.useHTTPForSD);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.multicastSessionId = (MulticastSessionId)in.readObject();
      this.lastSeqNum = in.readLong();
      this.useHTTPForSD = in.readBoolean();
   }

   public LastSeqNumHBI() {
   }
}
