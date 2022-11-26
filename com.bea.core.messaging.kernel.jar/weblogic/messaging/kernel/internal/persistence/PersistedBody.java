package weblogic.messaging.kernel.internal.persistence;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.kernel.internal.MessageHandle;
import weblogic.messaging.kernel.internal.Persistable;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStoreException;
import weblogic.store.TestStoreException;

public final class PersistedBody implements Persistable, TestStoreException {
   private static final int EXTERNAL_VERSION = 1;
   private long handleID;
   private Message body;

   public PersistedBody(MessageHandle handle) {
      this.handleID = handle.getID();
      this.body = handle.getMessage();
   }

   public PersistedBody() {
   }

   public Message getMessage() {
      return this.body;
   }

   public long getHandleID() {
      return this.handleID;
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      out.writeInt(1);
      out.writeLong(this.handleID);
      if (handler != null) {
         handler.writeObject(out, this.body);
      } else {
         out.writeObject(this.body);
      }

   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      if (in.readInt() != 1) {
         throw new IOException("External version mismatch");
      } else {
         this.handleID = in.readLong();

         try {
            if (handler != null) {
               this.body = (Message)handler.readObject(in);
            } else {
               this.body = (Message)in.readObject();
            }

         } catch (ClassNotFoundException var5) {
            throw new IOException(var5.toString());
         }
      }
   }

   public PersistentStoreException getTestException() {
      return this.body instanceof TestStoreException ? ((TestStoreException)this.body).getTestException() : null;
   }
}
