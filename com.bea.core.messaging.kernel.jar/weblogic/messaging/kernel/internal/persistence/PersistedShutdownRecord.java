package weblogic.messaging.kernel.internal.persistence;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.kernel.internal.Persistable;
import weblogic.store.ObjectHandler;

public final class PersistedShutdownRecord implements Persistable {
   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      in.readUTF();
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      out.writeUTF("Messaging kernel clean shutdown");
   }
}
