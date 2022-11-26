package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class AsyncBatch implements Externalizable {
   static final long serialVersionUID = -7477097798536735352L;
   private AsyncUpdate[] updates;

   public AsyncBatch(AsyncUpdate[] updates) {
      this.updates = updates;
   }

   public AsyncBatch() {
   }

   public AsyncUpdate[] getUpdates() {
      return this.updates;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.updates);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.updates = (AsyncUpdate[])((AsyncUpdate[])in.readObject());
   }
}
