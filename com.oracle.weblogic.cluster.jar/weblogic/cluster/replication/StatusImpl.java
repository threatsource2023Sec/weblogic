package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class StatusImpl implements Status, Externalizable {
   int status = -1;
   String info = "No Info";

   public StatusImpl() {
   }

   public StatusImpl(int status, String info) {
      this.status = status;
      this.info = info;
   }

   public int getStatus() {
      return this.status;
   }

   public String getInfo() {
      return this.info;
   }

   public String toString() {
      return "[status:" + this.status + ", " + this.info + "]";
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this.status);
      out.writeUTF(this.info);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.status = in.readInt();
      this.info = in.readUTF();
   }
}
