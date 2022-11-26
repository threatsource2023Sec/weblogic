package weblogic.messaging.kernel.internal.persistence;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.kernel.internal.AbstractOperation;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.kernel.internal.MessageReference;
import weblogic.messaging.kernel.internal.Persistable;
import weblogic.messaging.kernel.internal.QueueImpl;
import weblogic.store.ObjectHandler;
import weblogic.store.gxa.GXid;

public final class PersistedXARecord implements Persistable {
   private static final int EXTERNAL_VERSION = 2;
   private static final int VERSION_MASK = 255;
   private static final int TYPE_MASK = -65536;
   private static final int TYPE_SHIFT = 16;
   private long queue;
   private long seqNum;
   private int type;
   private long id;
   private GXid xid;
   private String subjectName;
   private String userID = null;

   public PersistedXARecord(AbstractOperation op) {
      this.queue = op.getQueue().getSerialNumber();
      this.seqNum = op.getMessageReference().getSequenceNumber();
      this.type = op.getType();
      this.id = op.getID();
      this.xid = op.getGXid();
      this.subjectName = op.getSubjectName();
      this.userID = op.getUserID();
   }

   public PersistedXARecord(int type, GXid xid, QueueImpl queue, MessageReference element, String subjectName) {
      this.type = type;
      this.xid = xid;
      this.queue = queue.getSerialNumber();
      this.seqNum = element.getSequenceNumber();
      this.subjectName = subjectName;
   }

   public PersistedXARecord() {
   }

   public long getQueue() {
      return this.queue;
   }

   public long getSequenceNumber() {
      return this.seqNum;
   }

   public int getType() {
      return this.type;
   }

   public long getID() {
      return this.id;
   }

   public GXid getXID() {
      return this.xid;
   }

   public String getSubjectName() {
      return this.subjectName;
   }

   public String getUserID() {
      return this.userID;
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      int flags = 2 | this.type << 16;
      out.writeInt(flags);
      out.writeLong(this.queue);
      out.writeLong(this.seqNum);
      out.writeLong(this.id);
      this.xid.write(out);
      out.writeUTF(this.subjectName);
      if (this.userID != null) {
         out.writeBoolean(true);
         out.writeUTF(this.userID);
      } else {
         out.writeBoolean(false);
      }

   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      int flags = in.readInt();
      if ((flags & 255) != 2) {
         throw new IOException("External version mismatch");
      } else {
         this.type = (flags & -65536) >>> 16;
         this.queue = in.readLong();
         this.seqNum = in.readLong();
         this.id = in.readLong();
         this.xid = GXid.read(in);
         this.subjectName = in.readUTF();
         if (in.readBoolean()) {
            this.userID = in.readUTF();
         }

      }
   }
}
