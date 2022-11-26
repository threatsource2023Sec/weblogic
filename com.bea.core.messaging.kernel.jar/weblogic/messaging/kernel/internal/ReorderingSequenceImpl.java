package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import weblogic.messaging.kernel.KernelException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXATransaction;
import weblogic.utils.collections.EmbeddedList;

public final class ReorderingSequenceImpl extends SequenceImpl {
   private long recoveryValue = -1L;
   private final EmbeddedList unorderedList = new EmbeddedList();

   public ReorderingSequenceImpl() {
   }

   protected ReorderingSequenceImpl(String name, int sequenceMode, long id, QueueImpl queue) {
      super(name, sequenceMode, id, queue);

      assert (sequenceMode & 4) != 0;

   }

   boolean supportsJTATransactions() {
      return false;
   }

   public synchronized long getLastAssignedValue() {
      return this.lastValue;
   }

   public synchronized void setLastValue(long newValue) throws KernelException {
      long delta = newValue - this.getLastValue();
      long newOffset = this.lastAssignedValue - delta;
      this.doSetLastValue(this.lastValue + delta, false);
      this.doSetLastValue(newOffset, true);
   }

   public synchronized long getLastValue() {
      return this.lastValue - this.lastAssignedValue;
   }

   boolean requiresUpdate() {
      return false;
   }

   MessageReference updateVisibleMessage(MessageReference ref, KernelImpl kernel, PersistentStoreTransaction storeTran) {
      return null;
   }

   synchronized void removeMessage(SequenceReference seqRef) {
      if (this.unorderedList.contains(seqRef)) {
         this.unorderedList.remove(seqRef);
      }

      super.removeMessage(seqRef);
   }

   private synchronized boolean addUnorderedMessage(MessageReference ref, long sequenceNum) {
      ListIterator i = this.unorderedList.listIterator();
      SequenceReference next = null;

      do {
         if (!i.hasNext()) {
            this.unorderedList.add(ref.getSequenceRef());
            return false;
         }

         next = (SequenceReference)i.next();
         if (next.getSequenceNum() == sequenceNum) {
            return true;
         }
      } while(next.getSequenceNum() <= sequenceNum);

      if (i.hasPrevious()) {
         i.previous();
      }

      i.add(ref.getSequenceRef());
      return false;
   }

   synchronized boolean sendMessage(GXATransaction transaction, MessageReference ref, long sequenceNum) throws KernelException {
      assert transaction != null;

      if (sequenceNum < 0L) {
         throw new KernelException("Invalid sequence number");
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Checking ordering of sequence number " + sequenceNum + " against sequence " + this.name);
            if (this.lastAssignedValue != 0L) {
               logger.debug("Will update sequence number for assignment to " + (sequenceNum + this.lastAssignedValue));
            }
         }

         sequenceNum += this.lastAssignedValue;
         if (sequenceNum <= this.lastValue) {
            ref.setState(256);
            if (logger.isDebugEnabled()) {
               logger.debug("Sequence number " + sequenceNum + " is a duplicate compared to " + this.lastValue);
            }

            return true;
         } else {
            if (sequenceNum == this.lastValue + 1L) {
               this.enlistUpdateOperation(transaction);
               this.numberRecord.setNewValue(this.getNextCleanNum(sequenceNum));
               this.addSequenceReference(ref, sequenceNum + this.lastAssignedValue);
               this.addMessage();
               this.lastValue = sequenceNum;
            } else {
               this.addSequenceReference(ref, sequenceNum);
               this.addMessage();
               ref.setState(256);
               if (this.addUnorderedMessage(ref, sequenceNum)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private long getNextCleanNum(long sequenceNum) {
      assert Thread.holdsLock(this);

      long ret = sequenceNum;

      for(Iterator i = this.unorderedList.iterator(); i.hasNext(); ++ret) {
         SequenceReference seqRef = (SequenceReference)i.next();
         if (seqRef.getSequenceNum() != ret + 1L) {
            break;
         }
      }

      return ret;
   }

   synchronized MessageReference getSubsequentMessage(MessageReference ref) {
      assert ref.getSequenceRef() != null;

      assert ref.getSequenceRef().getSequence() == this;

      long newSeqNum = ref.getSequenceRef().getSequenceNum();
      Iterator i = this.unorderedList.iterator();
      SequenceReference next = null;

      while(i.hasNext()) {
         next = (SequenceReference)i.next();
         if (next.getSequenceNum() > newSeqNum) {
            break;
         }
      }

      return next != null && next.getSequenceNum() > newSeqNum ? next.getMessageReference() : null;
   }

   synchronized List getUnorderedMessages() {
      ArrayList wakeUpList = null;
      long realLastValue = this.lastValue;
      Iterator i = this.unorderedList.iterator();

      while(i.hasNext()) {
         SequenceReference seqRef = (SequenceReference)i.next();
         if (seqRef.getSequenceNum() != realLastValue + 1L) {
            break;
         }

         ++realLastValue;
         if (wakeUpList == null) {
            wakeUpList = new ArrayList();
         }

         this.unorderedList.remove(seqRef);
         MessageReference ref = seqRef.getMessageReference();
         ref.clearState(256);
         wakeUpList.add(ref);
      }

      this.lastValue = realLastValue;
      return wakeUpList;
   }

   synchronized void recoverMessage(MessageReference ref) {
      assert ref.getSequenceRef() != null;

      assert ref.getSequenceRef().getSequence() == this;

      this.addMessage();
      long seqNum = ref.getSequenceRef().getSequenceNum();
      if (this.recoveryValue < 0L) {
         this.recoveryValue = this.lastValue;
      }

      long nextVal = this.recoveryValue + 1L;
      if (seqNum == nextVal) {
         assert false : "Why are we getting newer messages on boot?";

         ++this.recoveryValue;
      } else if (seqNum > nextVal) {
         ref.setState(256);
         this.unorderedList.add(ref.getSequenceRef());
      }

   }

   public List getAllSequenceNumberRanges() {
      List list = super.getAllSequenceNumberRanges();
      Object[] a = null;
      synchronized(this) {
         a = this.unorderedList.toArray();
      }

      long start = -1L;
      long prev = -1L;

      for(int i = 0; i < a.length; ++i) {
         SequenceReference seqRef = (SequenceReference)a[i];
         long sequenceNumber = seqRef.getSequenceNum();
         if (start == -1L) {
            start = sequenceNumber;
            prev = sequenceNumber;
         } else if (sequenceNumber > prev + 1L) {
            list.add(start);
            list.add(prev);
            start = sequenceNumber;
            prev = sequenceNumber;
         } else {
            prev = sequenceNumber;
         }
      }

      if (start != -1L) {
         list.add(start);
         list.add(prev);
      }

      return list;
   }
}
