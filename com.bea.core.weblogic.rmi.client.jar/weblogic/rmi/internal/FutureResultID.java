package weblogic.rmi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class FutureResultID implements Externalizable {
   private long uniqueID;

   public FutureResultID() {
   }

   public FutureResultID(long id) {
      this.uniqueID = id;
   }

   public long getHash() {
      return this.uniqueID;
   }

   public boolean equals(Object obj) {
      return obj instanceof FutureResultID && this.uniqueID == ((FutureResultID)obj).uniqueID;
   }

   public int hashCode() {
      return (int)(this.uniqueID ^ this.uniqueID >>> 32);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(this.uniqueID);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.uniqueID = in.readLong();
   }
}
