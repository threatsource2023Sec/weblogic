package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.Set;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;

public final class MultiPersistenceHandle implements Persistable {
   private MultiMessageReference refFirst;
   private MultiMessageReference refLast;
   private int refCount;
   private PersistentHandle persistentHandle;
   private MessageHandle messageHandle;
   private final OwnableLock lock = new OwnableLock();
   private final transient long flushGroup;
   private final transient long liveSequence;
   private static final int EXTERNAL_VERSION = 1;

   public MultiPersistenceHandle(MessageHandle messageHandle, long flushGroup, long liveSequence) {
      this.messageHandle = messageHandle;
      this.flushGroup = flushGroup;
      this.liveSequence = liveSequence;
   }

   public MultiPersistenceHandle() {
      this.flushGroup = -1L;
      this.liveSequence = -1L;
   }

   public long getFlushGroup() {
      return this.flushGroup;
   }

   public long getLiveSequence() {
      return this.liveSequence;
   }

   public MessageHandle getMessageHandle() {
      return this.messageHandle;
   }

   void setMessageHandle(MessageHandle handle) {
      this.messageHandle = handle;
   }

   void lock(Object owner) {
      this.lock.lock(owner);
   }

   void unlock(Object owner) {
      this.lock.unlock(owner);
   }

   public synchronized void setPersistentHandle(PersistentHandle ph) {
      this.persistentHandle = ph;
   }

   public synchronized PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   public synchronized void addMessageReference(MultiMessageReference ref) {
      if (ref.getPersistenceHandle() != null && ref.getPersistenceHandle() != this) {
         throw new AssertionError();
      } else if (!ref.getMMRInList()) {
         ref.setPersistenceHandle(this);
         ref.setMMRInList(true);
         ++this.refCount;
         if (this.refFirst == null) {
            this.refFirst = ref;
            this.refLast = ref;
            ref.setMMRNext((MultiMessageReference)null);
            ref.setMMRPrev((MultiMessageReference)null);
            this.refCount = 1;
         } else {
            ref.setMMRPrev(this.refLast);
            ref.setMMRNext((MultiMessageReference)null);
            this.refLast.setMMRNext(ref);
            this.refLast = ref;
         }
      }
   }

   public synchronized boolean removeMessageReference(MultiMessageReference ref) {
      if (!ref.getMMRInList()) {
         return false;
      } else if (ref.getPersistenceHandle() != this) {
         return false;
      } else {
         ref.setMMRInList(false);
         --this.refCount;
         if (ref.getMMRPrev() == null) {
            if (this.refFirst != ref) {
               throw new AssertionError();
            } else {
               if (this.refLast == this.refFirst) {
                  this.refFirst = null;
                  this.refLast = null;
               } else {
                  this.refFirst = ref.getMMRNext();
                  this.refFirst.setMMRPrev((MultiMessageReference)null);
               }

               ref.setMMRNext((MultiMessageReference)null);
               ref.setMMRPrev((MultiMessageReference)null);
               return true;
            }
         } else if (ref.getMMRNext() == null) {
            if (this.refLast != ref) {
               throw new AssertionError();
            } else {
               this.refLast = ref.getMMRPrev();
               this.refLast.setMMRNext((MultiMessageReference)null);
               ref.setMMRNext((MultiMessageReference)null);
               ref.setMMRPrev((MultiMessageReference)null);
               return true;
            }
         } else {
            ref.getMMRPrev().setMMRNext(ref.getMMRNext());
            ref.getMMRNext().setMMRPrev(ref.getMMRPrev());
            ref.setMMRNext((MultiMessageReference)null);
            ref.setMMRPrev((MultiMessageReference)null);
            return true;
         }
      }
   }

   public Set getMessageReferences() {
      HashSet hs = new HashSet();

      for(MultiMessageReference ref = this.refFirst; ref != null; ref = ref.getMMRNext()) {
         hs.add(ref);
      }

      return hs;
   }

   public synchronized int size() {
      return this.refCount;
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      out.writeInt(1);
      synchronized(this.messageHandle) {
         synchronized(this) {
            out.writeInt(this.refCount);

            for(MultiMessageReference ref = this.refFirst; ref != null; ref = ref.getMMRNext()) {
               long serialNum = 0L;
               if (ref.getQueue() != null) {
                  serialNum = ref.getQueue().getSerialNumber();
               }

               out.writeLong(serialNum);
               out.writeLong(ref.getSequenceNumber());
               out.writeInt(ref.getDeliveryCount());
            }

            this.messageHandle.writeToStore(out, handler);
         }

      }
   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl kernel) throws IOException {
      if (in.readInt() != 1) {
         throw new IOException("External version mismatch");
      } else {
         int numRefs = in.readInt();
         if (numRefs > 0) {
            for(int inc = 0; inc < numRefs; ++inc) {
               MultiMessageReference ref = new MultiMessageReference();
               ref.setQueue(kernel.findQueueUnsync(in.readLong()));
               ref.setSequenceNumber(in.readLong());
               ref.setDeliveryCount(in.readInt());
               this.addMessageReference(ref);
            }
         }

         this.messageHandle = new MultiMessageHandle();
         this.messageHandle.readFromStore(in, handler, (KernelImpl)null);
      }
   }
}
