package weblogic.messaging.kernel.internal.persistence;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.kernel.internal.Persistable;
import weblogic.messaging.kernel.internal.QueueImpl;
import weblogic.messaging.kernel.internal.SequenceImpl;
import weblogic.store.ObjectHandler;
import weblogic.store.gxa.GXid;

public final class PersistedSequenceRecord implements Persistable {
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int HAS_XID_MASK = 256;
   private static final int HAS_USERDATA_MASK = 512;
   private static final int HAS_OLD_USERDATA_MASK = 1024;
   private static final int USER_DATA_UPDATED_MASK = 2048;
   private SequenceImpl sequence;
   private long newValue;
   private long oldValue;
   private long newAssignedValue;
   private long oldAssignedValue;
   private GXid xid;
   private Object userData;
   private Object oldUserData;
   private boolean userDataUpdated;

   public PersistedSequenceRecord() {
   }

   public PersistedSequenceRecord(SequenceImpl sequence) {
      this.sequence = sequence;
   }

   public SequenceImpl getSequence() {
      return this.sequence;
   }

   public void setSequence(SequenceImpl sequence) {
      this.sequence = sequence;
   }

   public synchronized long getNewValue() {
      return this.newValue;
   }

   public synchronized void setNewValue(long newValue) {
      this.newValue = newValue;
   }

   public synchronized long getOldValue() {
      return this.oldValue;
   }

   public synchronized void setOldValue(long oldValue) {
      this.oldValue = oldValue;
   }

   public synchronized long getNewAssignedValue() {
      return this.newAssignedValue;
   }

   public synchronized void setNewAssignedValue(long newValue) {
      this.newAssignedValue = newValue;
   }

   public synchronized long getOldAssignedValue() {
      return this.oldAssignedValue;
   }

   public synchronized void setOldAssignedValue(long oldValue) {
      this.oldAssignedValue = oldValue;
   }

   public synchronized GXid getXid() {
      return this.xid;
   }

   public synchronized void setXid(GXid xid) {
      this.xid = xid;
   }

   public synchronized Object getUserData() {
      return this.userData;
   }

   public synchronized Object getOldUserData() {
      return this.userDataUpdated ? this.oldUserData : this.userData;
   }

   public synchronized void initializeUserData(Object userData) {
      this.userDataUpdated = false;
      this.userData = userData;
   }

   public synchronized void updateUserData(Object newData) {
      if (!this.userDataUpdated) {
         this.oldUserData = this.userData;
         this.userDataUpdated = true;
      }

      this.userData = newData;
   }

   private void writeUserObject(ObjectOutput out, ObjectHandler handler, Object obj) throws IOException {
      if (handler != null) {
         handler.writeObject(out, obj);
      } else {
         out.writeObject(obj);
      }

   }

   private Object readUserObject(ObjectInput in, ObjectHandler handler) throws IOException {
      try {
         return handler != null ? handler.readObject(in) : in.readObject();
      } catch (ClassNotFoundException var5) {
         IOException ioe = new IOException(var5.toString());
         ioe.initCause(var5);
         throw ioe;
      }
   }

   public synchronized void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      int flags = 1;
      if (this.xid != null) {
         flags |= 256;
      }

      if (this.userDataUpdated) {
         flags |= 2048;
      }

      if (this.userData != null) {
         flags |= 512;
      }

      if (this.oldUserData != null) {
         flags |= 1024;
      }

      out.writeInt(flags);
      out.writeLong(this.sequence.getQueue().getSerialNumber());
      out.writeLong(this.sequence.getSerialNumber());
      out.writeLong(this.newValue);
      out.writeLong(this.oldValue);
      out.writeLong(this.newAssignedValue);
      out.writeLong(this.oldAssignedValue);
      if (this.xid != null) {
         this.xid.write(out);
      }

      if (this.userData != null) {
         this.writeUserObject(out, handler, this.userData);
      }

      if (this.oldUserData != null) {
         this.writeUserObject(out, handler, this.oldUserData);
      }

   }

   public synchronized void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl kernel) throws IOException {
      int flags = in.readInt();
      if ((flags & 255) != 1) {
         throw new IOException("External version mismatch");
      } else {
         QueueImpl queue = kernel.findQueueUnsync(in.readLong());
         long sequenceId = in.readLong();
         if (queue != null) {
            this.sequence = queue.findSequenceUnsync(sequenceId);
         }

         this.newValue = in.readLong();
         this.oldValue = in.readLong();
         this.newAssignedValue = in.readLong();
         this.oldAssignedValue = in.readLong();
         if ((flags & 256) != 0) {
            this.xid = GXid.read(in);
         }

         this.userDataUpdated = (flags & 2048) != 0;
         if ((flags & 512) != 0) {
            this.userData = this.readUserObject(in, handler);
         }

         if ((flags & 1024) != 0) {
            this.oldUserData = this.readUserObject(in, handler);
         }

      }
   }
}
