package weblogic.messaging.kernel.internal.persistence;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.kernel.internal.DupEliminationSequenceImpl;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.kernel.internal.MultiPersistenceHandle;
import weblogic.messaging.kernel.internal.Persistable;
import weblogic.messaging.kernel.internal.QueueImpl;
import weblogic.messaging.kernel.internal.QueueMessageReference;
import weblogic.messaging.kernel.internal.ReorderingSequenceImpl;
import weblogic.messaging.kernel.internal.SequenceImpl;
import weblogic.messaging.kernel.internal.TopicImpl;
import weblogic.messaging.kernel.internal.UOWSequenceImpl;
import weblogic.store.ObjectHandler;

public final class KernelObjectHandler implements ObjectHandler {
   private ObjectHandler userHandler;
   private KernelImpl kernel;
   private static final short Q_REF_ID = 1;
   private static final short BODY_ID = 2;
   private static final short XA_ID = 3;
   private static final short QUEUE_ID = 4;
   private static final short TOPIC_ID = 5;
   private static final short CLEAN_SHUTDOWN_ID = 6;
   private static final short MULTI_REF_ID = 7;
   private static final short LAST_FAILURE_ID = 8;
   private static final short SEQUENCE_NUM_ID = 9;
   private static final short ASSIGNING_SEQUENCE_ID = 10;
   private static final short DUP_ELIM_SEQUENCE_ID = 11;
   private static final short REORDERING_SEQUENCE_ID = 12;
   private static final short UOW_SEQUENCE_ID = 13;

   public KernelObjectHandler(ObjectHandler userHandler, KernelImpl kernel) {
      this.userHandler = userHandler;
      this.kernel = kernel;
   }

   public void writeObject(ObjectOutput out, Object obj) throws IOException {
      byte id;
      if (obj instanceof QueueMessageReference) {
         id = 1;
      } else if (obj instanceof MultiPersistenceHandle) {
         id = 7;
      } else if (obj instanceof PersistedBody) {
         id = 2;
      } else if (obj instanceof PersistedSequenceRecord) {
         id = 9;
      } else if (obj instanceof PersistedXARecord) {
         id = 3;
      } else if (obj instanceof QueueImpl) {
         id = 4;
      } else if (obj instanceof TopicImpl) {
         id = 5;
      } else if (obj instanceof DupEliminationSequenceImpl) {
         id = 11;
      } else if (obj instanceof ReorderingSequenceImpl) {
         id = 12;
      } else if (obj instanceof UOWSequenceImpl) {
         id = 13;
      } else if (obj instanceof SequenceImpl) {
         id = 10;
      } else if (obj instanceof PersistedShutdownRecord) {
         id = 6;
      } else {
         if (!(obj instanceof LastFailureRecord)) {
            throw new AssertionError("Invalid class " + obj.getClass().getName() + " persisted");
         }

         id = 8;
      }

      out.writeShort(id);
      ((Persistable)obj).writeToStore(out, this.userHandler);
   }

   public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
      short id = in.readShort();
      Object ret;
      switch (id) {
         case 1:
            ret = new QueueMessageReference();
            break;
         case 2:
            ret = new PersistedBody();
            break;
         case 3:
            ret = new PersistedXARecord();
            break;
         case 4:
            ret = new QueueImpl();
            break;
         case 5:
            ret = new TopicImpl();
            break;
         case 6:
            ret = new PersistedShutdownRecord();
            break;
         case 7:
            ret = new MultiPersistenceHandle();
            break;
         case 8:
            ret = new LastFailureRecord();
            break;
         case 9:
            ret = new PersistedSequenceRecord();
            break;
         case 10:
            ret = new SequenceImpl();
            break;
         case 11:
            ret = new DupEliminationSequenceImpl();
            break;
         case 12:
            ret = new ReorderingSequenceImpl();
            break;
         case 13:
            ret = new UOWSequenceImpl();
            break;
         default:
            throw new ClassNotFoundException("Invalid object ID " + id);
      }

      ((Persistable)ret).readFromStore(in, this.userHandler, this.kernel);
      return ret;
   }
}
