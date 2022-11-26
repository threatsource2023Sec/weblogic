package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.SequenceMode;
import weblogic.messaging.kernel.internal.persistence.PersistedSequenceRecord;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.store.gxa.GXATransaction;
import weblogic.utils.collections.EmbeddedList;

public class SequenceImpl implements Sequence, Persistable {
   protected static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   protected String name;
   protected int sequenceMode;
   protected boolean assigning;
   protected boolean passthru;
   protected DestinationImpl actualDestination;
   protected QueueImpl queue;
   protected SequenceUpdateOperation updateOp;
   protected GXATransaction enlistedTran;
   protected long destID;
   protected long id;
   protected long lastValue;
   protected long lastAssignedValue;
   protected Object userData;
   protected boolean deleted;
   protected boolean deleteWhenEmpty;
   protected boolean override;
   protected boolean poisoned;
   private MessageReference lastAssignedMessage;
   private PersistentStoreTransaction curTransaction;
   protected PersistentHandle persHandle;
   protected PersistentHandle numberPersHandle;
   protected PersistedSequenceRecord numberRecord;
   private final OwnableLock lock = new OwnableLock();
   private EmbeddedList assignedList;
   protected int messageCount;
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DELETED_FLAG = 256;

   public SequenceImpl() {
   }

   public Message replaceMessage(Message replacee) {
      return null;
   }

   public void adminDeletedMessage(SequenceReference seqRef) {
   }

   protected SequenceImpl(String name, int sequenceMode, long id, QueueImpl queue) {
      this.name = name;
      this.sequenceMode = sequenceMode;
      this.id = id;
      this.queue = queue;
      this.actualDestination = queue;
      if ((sequenceMode & 1) != 0) {
         this.assigning = true;
         this.assignedList = new EmbeddedList();
      }

   }

   void setActualDestination(DestinationImpl dest) {
      this.actualDestination = dest;
   }

   static SequenceImpl createSequence(String name, int sequenceMode, long id, QueueImpl queue) throws KernelException {
      if ((sequenceMode & -16) != 0) {
         throw new KernelException("Invalid sequence mode " + sequenceMode);
      } else if ((sequenceMode & 4) != 0) {
         return new ReorderingSequenceImpl(name, sequenceMode, id, queue);
      } else if ((sequenceMode & 2) != 0) {
         return new DupEliminationSequenceImpl(name, sequenceMode, id, queue);
      } else {
         return (SequenceImpl)((sequenceMode & 8) != 0 ? new UOWSequenceImpl(name, sequenceMode, id, queue) : new SequenceImpl(name, sequenceMode, id, queue));
      }
   }

   public String getName() {
      return this.name;
   }

   public int getMode() {
      return this.sequenceMode;
   }

   public long getSerialNumber() {
      return this.id;
   }

   boolean isStampRequired() {
      return this.assigning;
   }

   void setNumberPersistentHandle(PersistentHandle handle) {
      this.numberPersHandle = handle;
   }

   PersistentHandle getNumberPersistentHandle() {
      return this.numberPersHandle;
   }

   PersistedSequenceRecord getNumberRecord() {
      return this.numberRecord;
   }

   void setNumberRecord(PersistedSequenceRecord rec) {
      this.numberRecord = rec;
   }

   public final synchronized void setPassthru(boolean passthru) {
      this.passthru = passthru;
   }

   public final synchronized boolean isPassthru() {
      return this.passthru;
   }

   public final void setOverride(boolean override) {
      this.override = override;
   }

   public final boolean isOverride() {
      return this.override;
   }

   final void setPoisoned(boolean poison) {
      this.poisoned = poison;
   }

   final boolean isPoisoned() {
      return this.poisoned;
   }

   final void lock(Object owner) {
      this.lock.lock(owner);
   }

   final void unlock(Object owner) {
      this.lock.unlock(owner);
   }

   final boolean isLocked(Object owner) {
      return this.lock.isLocked(owner);
   }

   protected synchronized void addMessage() {
      ++this.messageCount;
   }

   synchronized void removeMessage(SequenceReference seqRef) {
      assert seqRef.getSequence() == this;

      --this.messageCount;

      assert this.messageCount >= 0;

      if (this.assigning && seqRef.getMessageReference() == this.lastAssignedMessage) {
         this.lastAssignedMessage = null;
      }

      if (this.deleteWhenEmpty && this.messageCount == 0) {
         this.queue.getKernelImpl().getLimitedWorkManager().schedule(new DeleteCleanUpListener());
      }

   }

   synchronized void setLastValueInternal(long newValue) {
      this.lastValue = newValue;
   }

   synchronized void setLastAssignedValueInternal(long newValue) {
      this.lastAssignedValue = newValue;
   }

   synchronized void setUserDataInternal(Object userData) {
      this.userData = userData;
   }

   public PersistentHandle getPersistentHandle() {
      return this.persHandle;
   }

   public void setPersistentHandle(PersistentHandle persHandle) {
      this.persHandle = persHandle;
   }

   public QueueImpl getQueue() {
      return this.queue;
   }

   public Destination getDestination() {
      return this.actualDestination;
   }

   synchronized boolean isDeleted() {
      return this.deleted;
   }

   public synchronized long getLastValue() {
      return this.lastValue;
   }

   public synchronized long getLastAssignedValue() {
      return this.lastAssignedValue;
   }

   public void setLastValue(long newValue) throws KernelException {
      this.doSetLastValue(newValue, false);
   }

   protected synchronized void doSetLastValue(long newValue, boolean forAssign) throws KernelException {
      boolean isLocalTran = false;
      GXATransaction tran = this.queue.getKernelImpl().getGXATransaction();
      if (tran == null) {
         isLocalTran = true;
         tran = this.queue.getKernelImpl().startLocalGXATransaction();
      }

      boolean needsUnlock = true;

      try {
         this.enlistUpdateOperation((GXATransaction)tran);
         if (forAssign) {
            this.numberRecord.setNewAssignedValue(newValue);
            this.lastAssignedValue = newValue;
         } else {
            this.numberRecord.setNewValue(newValue);
            this.lastValue = newValue;
         }

         needsUnlock = false;
         if (isLocalTran) {
            try {
               ((GXALocalTransaction)tran).commit();
            } catch (GXAException var11) {
               throw new KernelException("Error updating sequence number", var11);
            }
         }
      } finally {
         if (needsUnlock) {
            this.unlock(tran);
         }

      }

   }

   public synchronized Object getUserData() {
      return this.userData;
   }

   public void setUserData(Object newUserData) throws KernelException {
      boolean isLocalTran = false;
      GXATransaction tran = this.queue.getKernelImpl().getGXATransaction();
      if (tran == null) {
         isLocalTran = true;
         tran = this.queue.getKernelImpl().startLocalGXATransaction();
      }

      boolean needsUnlock = true;

      try {
         this.lock(tran);
         synchronized(this) {
            try {
               this.enlistUpdateOperation((GXATransaction)tran);
            } finally {
               this.unlock(tran);
            }

            this.numberRecord.updateUserData(newUserData);
            this.userData = newUserData;
            needsUnlock = false;
            if (isLocalTran) {
               try {
                  ((GXALocalTransaction)tran).commit();
               } catch (GXAException var17) {
                  throw new KernelException("Error updating sequence number", var17);
               }
            }
         }
      } finally {
         if (needsUnlock) {
            this.unlock(tran);
         }

      }

   }

   public KernelRequest delete(boolean deleteAllMessages) throws KernelException {
      boolean cleanUpQueue = false;
      synchronized(this) {
         this.deleted = true;
         if (this.messageCount == 0) {
            if (this.queue.isDurable()) {
               PersistenceImpl pers = this.queue.getKernelImpl().getPersistence();
               if (this.numberPersHandle != null) {
                  pers.deleteSequenceNumber(this.numberPersHandle);
                  this.numberPersHandle = null;
               }

               pers.deleteSequence(this);
            }

            this.userData = null;
            this.queue.sequenceDeleted(this);
         } else {
            if (this.queue.isDurable()) {
               this.queue.getKernelImpl().getPersistence().updateSequence(this);
            }

            if (deleteAllMessages) {
               cleanUpQueue = true;
            } else {
               this.deleteWhenEmpty = true;
            }
         }
      }

      return cleanUpQueue ? this.queue.deleteSequenceMessages(this, new DeleteCleanUpListener()) : null;
   }

   public List getAllSequenceNumberRanges() {
      List list = new ArrayList();
      long last = this.getLastValue();
      if (last >= 1L) {
         list.add(1L);
         list.add(this.getLastValue());
      }

      return list;
   }

   protected void addSequenceReference(MessageReference ref, long sequenceNum) {
      SequenceReference seqRef = new SequenceReference(ref, this);
      seqRef.setSequenceNum(sequenceNum);
      ref.setSequenceRef(seqRef);
   }

   protected void enlistUpdateOperation(GXATransaction transaction) throws KernelException {
      if (transaction != this.enlistedTran) {
         this.lock(transaction);
         this.updateOp = new SequenceUpdateOperation(this.queue.getKernelImpl(), this);
         this.numberRecord.setOldAssignedValue(this.lastAssignedValue);
         this.numberRecord.setNewAssignedValue(this.lastAssignedValue);
         this.numberRecord.setOldValue(this.lastValue);
         this.numberRecord.setNewValue(this.lastValue);
         this.numberRecord.initializeUserData(this.userData);
         this.enlistedTran = transaction;

         try {
            transaction.getGXAResource().addNewOperation(transaction, this.updateOp);
         } catch (GXAException var3) {
            throw new KernelException("Error updating sequence number", var3);
         }
      }
   }

   boolean supportsJTATransactions() {
      return true;
   }

   boolean sendMessage(GXATransaction transaction, MessageReference ref, long sequenceNum) throws KernelException {
      SequenceReference seqRef = new SequenceReference(ref, this);
      ref.setSequenceRef(seqRef);
      if (this.passthru) {
         seqRef.setSequenceNum(sequenceNum);
      }

      this.addMessage();
      return false;
   }

   MessageReference getSubsequentMessage(MessageReference ref) {
      return null;
   }

   List getUnorderedMessages() {
      return null;
   }

   MessageReference updateVisibleMessage(MessageReference ref, KernelImpl kernel, PersistentStoreTransaction storeTran) {
      if (!this.assigning) {
         return null;
      } else {
         assert ref.getSequenceRef() != null;

         assert ref.getSequenceRef().getSequence() == this;

         synchronized(this) {
            SequenceReference seqRef = ref.getSequenceRef();
            long newSeqNum;
            if (this.passthru) {
               newSeqNum = seqRef.getSequenceNum();
            } else {
               newSeqNum = this.lastAssignedValue + 1L;
               seqRef.setSequenceNum(newSeqNum);
            }

            if (this.queue.isDurable()) {
               if (storeTran != this.curTransaction) {
                  this.numberRecord.setOldAssignedValue(this.lastAssignedValue);
                  this.numberRecord.initializeUserData(this.userData);
                  this.curTransaction = storeTran;
               }

               this.numberRecord.setNewAssignedValue(newSeqNum);
               kernel.getPersistence().updateSequenceNumber(storeTran, this.numberPersHandle, this.numberRecord);
            }

            ref.setState(256);
            this.assignedList.add(seqRef);
            this.lastAssignedValue = newSeqNum;
            MessageReference ret = this.lastAssignedMessage;
            this.lastAssignedMessage = ref;
            return ret;
         }
      }
   }

   List getAssignedMessages(int clearedState) {
      if (!this.assigning) {
         return null;
      } else {
         ArrayList visList = null;
         Iterator i = this.assignedList.iterator();

         while(i.hasNext()) {
            MessageReference msgRef = ((SequenceReference)i.next()).getMessageReference();
            if ((msgRef.getState() & clearedState) != 0) {
               break;
            }

            if (visList == null) {
               visList = new ArrayList();
            }

            msgRef.clearState(256);
            i.remove();
            visList.add(msgRef);
         }

         return visList;
      }
   }

   boolean requiresUpdate() {
      return this.assigning;
   }

   synchronized void recoverMessage(MessageReference ref) {
      this.addMessage();
      if (this.assigning) {
         SequenceReference seqRef = ref.getSequenceRef();

         assert seqRef.getSequence() == this;

         if (this.lastAssignedMessage == null || seqRef.getSequenceNum() > this.lastAssignedMessage.getSequenceRef().getSequenceNum()) {
            this.lastAssignedMessage = ref;
         }

      }
   }

   synchronized void recoveryComplete(Queue queue) {
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      int flags = 1;
      if (this.deleted) {
         flags |= 256;
      }

      out.writeInt(flags);
      out.writeUTF(this.name);
      out.writeInt(this.sequenceMode);
      out.writeLong(this.id);
      out.writeLong(this.queue.getSerialNumber());
   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      int flags = in.readInt();
      if ((flags & 255) != 1) {
         throw new IOException("Version mismatch");
      } else {
         this.deleted = (flags & 256) != 0;
         this.name = in.readUTF();
         this.sequenceMode = in.readInt();
         if ((this.sequenceMode & 1) != 0) {
            this.assigning = true;
            this.assignedList = new EmbeddedList();
         }

         this.id = in.readLong();
         this.destID = in.readLong();
      }
   }

   void restoreDestination(KernelImpl kernel) {
      this.queue = kernel.findQueueUnsync(this.destID);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ name=");
      buf.append(this.name);
      buf.append(" sequenceMode=");
      buf.append(SequenceMode.modeToString(this.sequenceMode));
      buf.append(" destination=");
      buf.append(this.queue.toString());
      buf.append(" lastValue=");
      buf.append(this.lastValue);
      buf.append(" lastAssignedValue=");
      buf.append(this.lastAssignedValue);
      buf.append(" ]");
      return buf.toString();
   }

   public synchronized void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Sequence");
      xsw.writeAttribute("name", this.name);
      xsw.writeAttribute("sequenceMode", SequenceMode.modeToString(this.sequenceMode));
      xsw.writeAttribute("id", String.valueOf(this.id));
      xsw.writeAttribute("messageCount", String.valueOf(this.messageCount));
      xsw.writeAttribute("lastValue", String.valueOf(this.lastValue));
      xsw.writeAttribute("lastAssignedValue", String.valueOf(this.lastAssignedValue));
      xsw.writeEndElement();
   }

   private final class DeleteCleanUpListener implements Runnable {
      private DeleteCleanUpListener() {
      }

      public void run() {
         if (SequenceImpl.this.queue.isDurable()) {
            try {
               PersistenceImpl pers = SequenceImpl.this.queue.getKernelImpl().getPersistence();
               if (SequenceImpl.this.numberPersHandle != null) {
                  pers.deleteSequenceNumber(SequenceImpl.this.numberPersHandle);
                  SequenceImpl.this.numberPersHandle = null;
               }

               pers.deleteSequence(SequenceImpl.this);
            } catch (KernelException var2) {
            }
         }

         SequenceImpl.this.userData = null;
         SequenceImpl.this.queue.sequenceDeleted(SequenceImpl.this);
      }

      // $FF: synthetic method
      DeleteCleanUpListener(Object x1) {
         this();
      }
   }
}
