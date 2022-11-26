package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.Message;
import weblogic.messaging.MessagingLogger;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.store.TestStoreException;

public final class QueueMessageReference extends MessageReference implements Persistable, TestStoreException {
   private PersistentHandle persistentHandle;
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int BODY_FLAG = 256;
   private static final int SEQUENCED_FLAG = 512;

   public QueueMessageReference() {
   }

   public QueueMessageReference(QueueImpl queue, MessageHandle messageHandle) {
      super(queue, messageHandle);
   }

   private QueueMessageReference(QueueMessageReference ref) {
      super(ref);
      this.persistentHandle = ref.persistentHandle;
   }

   MessageReference duplicate() {
      return new QueueMessageReference(this);
   }

   public final PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   final void setPersistentHandle(PersistentHandle handle) {
      this.persistentHandle = handle;
   }

   public PersistentStoreException getTestException() {
      return this.getMessageHandle().getMessage() instanceof TestStoreException ? ((TestStoreException)this.getMessageHandle().getMessage()).getTestException() : null;
   }

   public final void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      boolean persistBody = this.messageHandle.isPersistBody();
      int flags = 1;
      if (persistBody) {
         flags |= 256;
      }

      if (this.seqRef != null) {
         flags |= 512;
      }

      out.writeInt(flags);
      out.writeLong(this.queue.getSerialNumber());
      out.writeLong(this.sequenceNum);
      out.writeInt(this.deliveryCount);
      this.messageHandle.writeToStore(out, handler);
      if (this.seqRef != null) {
         out.writeLong(this.seqRef.getSequence().getSerialNumber());
         out.writeLong(this.seqRef.getSequenceNum());
      }

      if (persistBody) {
         if (this.messageHandle.getMessage() == null) {
            String info = "[seqNum=" + this.sequenceNum + ", deliveryCount=" + this.deliveryCount + ", qSerialNum=" + this.queue.getSerialNumber() + ", MessageHandle=" + this.messageHandle + "]";
            MessagingLogger.logErrorWritingToStore(this.queue.getName(), info);
            IOException ioe = new IOException("Attempted to write a null message to store for destination " + this.queue.getName());
            throw ioe;
         }

         if (handler != null) {
            handler.writeObject(out, this.messageHandle.getMessage());
         } else {
            out.writeObject(this.messageHandle.getMessage());
         }
      }

   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl kernel) throws IOException {
      int flags = in.readInt();
      if ((flags & 255) != 1) {
         throw new IOException("External version mismatch");
      } else {
         boolean persistBody = (flags & 256) != 0;
         this.queue = kernel.findQueueUnsync(in.readLong());
         this.sequenceNum = in.readLong();
         this.deliveryCount = in.readInt();
         this.messageHandle = new MessageHandle();
         this.messageHandle.readFromStore(in, handler, (KernelImpl)null);
         if ((flags & 512) != 0) {
            long seqID = in.readLong();
            long seqNum = in.readLong();
            if (this.queue != null) {
               SequenceImpl sequence = this.queue.findSequenceUnsync(seqID);
               if (sequence != null) {
                  this.seqRef = new SequenceReference(this, sequence);
                  this.seqRef.setSequenceNum(seqNum);
               }
            }
         }

         if (persistBody) {
            Message msg;
            try {
               if (handler != null) {
                  msg = (Message)handler.readObject(in);
               } else {
                  msg = (Message)in.readObject();
               }
            } catch (ClassNotFoundException var11) {
               throw new IOException(var11.toString());
            }

            this.messageHandle.setMessage(msg);
         }

      }
   }
}
