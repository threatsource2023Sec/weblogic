package weblogic.servlet.cluster.wan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class BatchedSessionState implements Externalizable {
   static final long serialVersionUID = 9157854138389175601L;
   private Update[] updates;

   public BatchedSessionState(Update[] updates) {
      this.updates = updates;
   }

   public Update[] getUpdates() {
      return this.updates;
   }

   public BatchedSessionState() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.updates);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.updates = (Update[])((Update[])in.readObject());
   }
}
