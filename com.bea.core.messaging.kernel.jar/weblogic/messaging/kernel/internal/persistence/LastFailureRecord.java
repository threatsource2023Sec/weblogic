package weblogic.messaging.kernel.internal.persistence;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.kernel.internal.Persistable;
import weblogic.store.ObjectHandler;

public class LastFailureRecord implements Persistable {
   private long id;

   public void setID(long id) {
      this.id = id;
   }

   public long getID() {
      return this.id;
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      out.writeLong(this.id);
   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      this.id = in.readLong();
   }
}
