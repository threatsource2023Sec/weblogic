package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.SendOptions;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.utils.collections.AbstractEmbeddedListElement;

public class MessageHandle extends AbstractEmbeddedListElement implements Persistable {
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DELIVERY_FLAG = 256;
   private static final int EXPIRATION_FLAG = 512;
   private static final int GROUP_FLAG = 1024;
   private static final int REDELIVERY_FLAG = 2048;
   private static final int FLAG_MASK = 16711680;
   private static final int PERSISTENT_FLAG = 65536;
   private static final int EXPIRATION_REPORTED_FLAG = 131072;
   private static final int PERSIST_BODY_FLAG = 262144;
   private static final int NO_DELIVERY_DELAY_FLAG = 524288;
   private static final int PAGEABLE_FLAG = 16777216;
   private transient Message message;
   private long id;
   private long deliveryTime;
   private long expirationTime;
   private int redeliveryLimit;
   private long size;
   private String groupName;
   private int flags;
   private QuotaImpl quota;
   private transient int queueReferenceCount;
   private transient int persistentRefCount;
   private transient int quotaReferenceCount;
   private transient int pinCount;
   private transient boolean pagingInProgress;
   private PersistentHandle persistentHandle;
   private transient long pagingHandle;
   private transient MultiSender multiSender;

   public MessageHandle() {
   }

   MessageHandle(KernelImpl kernel, Message message, SendOptions options) {
      this(kernel.getNextHandleID(), message, options);
   }

   public MessageHandle(long id, Message message, SendOptions options) {
      this.deliveryTime = options.getDeliveryTime();
      this.expirationTime = options.getExpirationTime();
      this.redeliveryLimit = options.getRedeliveryLimit();
      this.groupName = options.getGroup();
      this.setNoDeliveryDelay(options.isNoDeliveryDelay());
      if (options.isPersistent()) {
         this.flags |= 65536;
      }

      this.id = id;
      this.message = message;
      this.size = message.size();
      if (this.redeliveryLimit < 0) {
         this.redeliveryLimit = Integer.MAX_VALUE;
      }

      this.pinCount = 1;
   }

   SendOptions createSendOptions() {
      SendOptions op = new SendOptions();
      op.setDeliveryTime(this.deliveryTime);
      op.setNoDeliveryDelay(this.isNoDeliveryDelay());
      op.setExpirationTime(this.expirationTime);
      op.setRedeliveryLimit(this.redeliveryLimit);
      op.setGroup(this.groupName);
      op.setPersistent(this.isPersistent());
      return op;
   }

   public final long getID() {
      return this.id;
   }

   public final Message getMessage() {
      return this.message;
   }

   public final void setMessage(Message message) {
      this.message = message;
   }

   public final long getDeliveryTime() {
      return this.deliveryTime;
   }

   public final void setExpirationTime(long expirationTime) {
      this.expirationTime = expirationTime;
   }

   public final long getExpirationTime() {
      return this.expirationTime;
   }

   public final int getRedeliveryLimit() {
      return this.redeliveryLimit;
   }

   public final long size() {
      return this.size;
   }

   public final String getGroupName() {
      return this.groupName;
   }

   public final void setSAFGroupName(String groupName) {
      this.groupName = groupName;
   }

   public final boolean isPersistent() {
      return (this.flags & 65536) != 0;
   }

   final synchronized QuotaImpl getQuota() {
      return this.quota;
   }

   final synchronized void setQuota(QuotaImpl quota) {
      this.quota = quota;
   }

   final synchronized void setExpirationReported(boolean reported) {
      if (reported) {
         this.flags |= 131072;
      } else {
         this.flags &= -131073;
      }

   }

   final synchronized boolean isExpirationReported() {
      return (this.flags & 131072) != 0;
   }

   final void setPersistBody(boolean persist) {
      if (persist) {
         this.flags |= 262144;
      } else {
         this.flags &= -262145;
      }

   }

   final boolean isPersistBody() {
      return (this.flags & 262144) != 0;
   }

   final void setNoDeliveryDelay(boolean noDeliveryDelay) {
      if (noDeliveryDelay) {
         this.flags |= 524288;
      } else {
         this.flags &= -524289;
      }

   }

   final boolean isNoDeliveryDelay() {
      return (this.flags & 524288) != 0;
   }

   final synchronized boolean setPageable(boolean pageable) {
      boolean ret = (this.flags & 16777216) != 0;
      if (pageable) {
         this.flags |= 16777216;
      } else {
         this.flags &= -16777217;
      }

      return ret;
   }

   final synchronized boolean isPageable() {
      return (this.flags & 16777216) != 0;
   }

   final synchronized int getPersistentRefCount() {
      return this.persistentRefCount;
   }

   final synchronized int adjustPersistentRefCount(int adjustment) {
      assert this.persistentRefCount + adjustment >= 0;

      return this.persistentRefCount += adjustment;
   }

   final PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   final void setPersistentHandle(PersistentHandle handle) {
      this.persistentHandle = handle;
   }

   final long getPagingHandle() {
      return this.pagingHandle;
   }

   final void setPagingHandle(long handle) {
      this.pagingHandle = handle;
   }

   final synchronized int adjustQueueReferenceCount(int referenceCount) {
      assert this.queueReferenceCount + referenceCount >= 0;

      return this.queueReferenceCount += referenceCount;
   }

   final synchronized void setQueueReferenceCount(int referenceCount) {
      assert referenceCount >= 0;

      this.queueReferenceCount = referenceCount;
   }

   final synchronized int getQueueReferenceCount() {
      return this.queueReferenceCount;
   }

   final synchronized int adjustQuotaReferenceCount(int referenceCount) {
      assert this.quotaReferenceCount + referenceCount >= 0;

      return this.quotaReferenceCount += referenceCount;
   }

   final synchronized void setQuotaReferenceCount(int referenceCount) {
      assert referenceCount >= 0;

      this.quotaReferenceCount = referenceCount;
   }

   final synchronized int getQuotaReferenceCount() {
      return this.quotaReferenceCount;
   }

   final synchronized MultiSender startMultiSend(KernelImpl kernel) {
      if (this.multiSender == null) {
         this.multiSender = new MultiSender();
         return this.multiSender;
      } else {
         return null;
      }
   }

   final synchronized void multiSendComplete() {
      this.multiSender = null;
   }

   final synchronized MultiSender getMultiSender() {
      return this.multiSender;
   }

   public final synchronized void setPagedOut() {
      this.message = null;
   }

   final synchronized int getPinCount() {
      return this.pinCount;
   }

   final void pin(KernelImpl kernel) throws KernelException {
      synchronized(this) {
         this.waitForPaging();
         ++this.pinCount;
         if (this.pinCount > 1) {
            return;
         }

         this.flags &= -16777217;
         this.pagingInProgress = true;
      }

      try {
         kernel.getPaging().makeUnpageable(this);
         if (this.message == null) {
            if (this.pagingHandle != 0L) {
               kernel.getPaging().pageIn(this);
            } else {
               if (this.persistentHandle == null) {
                  throw new KernelException("Internal error: The message body does not exist in the store");
               }

               kernel.getPersistence().readMessageBody(this);
            }
         }
      } finally {
         this.setPagingInProgress(false);
      }

   }

   final void unPin(KernelImpl kernel) {
      synchronized(this) {
         this.waitForPaging();
         --this.pinCount;

         assert this.pinCount >= 0;

         if (this.pinCount > 0 || this.queueReferenceCount == 0 || (this.flags & 16777216) != 0) {
            return;
         }

         this.flags |= 16777216;
         this.pagingInProgress = true;
      }

      try {
         kernel.getPaging().makePageable(this);
      } finally {
         this.setPagingInProgress(false);
      }

   }

   final void makePageable(KernelImpl kernel) {
      synchronized(this) {
         this.waitForPaging();
         if ((this.flags & 16777216) != 0) {
            return;
         }

         this.flags |= 16777216;
         this.pagingInProgress = true;
      }

      try {
         kernel.getPaging().makePageable(this);
      } finally {
         this.setPagingInProgress(false);
      }

   }

   final void removePagedState(KernelImpl kernel) {
      boolean pageable;
      synchronized(this) {
         this.waitForPaging();
         if (this.queueReferenceCount > 0) {
            return;
         }

         pageable = (this.flags & 16777216) != 0;
         this.pagingInProgress = true;
      }

      try {
         if (pageable) {
            kernel.getPaging().makeUnpageable(this);
         }

         if (this.pagingHandle != 0L) {
            kernel.getPaging().removePagedState(this);
            this.pagingHandle = 0L;
         }
      } finally {
         this.setPagingInProgress(false);
      }

   }

   private void waitForPaging() {
      assert Thread.holdsLock(this);

      while(this.pagingInProgress) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   final synchronized void setPagingInProgress(boolean inProgress) {
      assert this.pagingInProgress != inProgress;

      this.pagingInProgress = inProgress;
      if (!inProgress) {
         this.notifyAll();
      }

   }

   final synchronized boolean isPagingInProgress() {
      return this.pagingInProgress;
   }

   boolean incrementLowCurrent() {
      return true;
   }

   boolean decrementLowCurrent() {
      return true;
   }

   boolean incrementLowPending() {
      return true;
   }

   boolean decrementLowPending() {
      return true;
   }

   boolean incrementLowReceived() {
      return true;
   }

   boolean incrementHighCurrent() {
      return true;
   }

   boolean decrementHighCurrent() {
      return true;
   }

   boolean incrementHighPending() {
      return true;
   }

   boolean decrementHighPending() {
      return true;
   }

   boolean incrementHighReceived() {
      return true;
   }

   public final void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      int extFlags;
      synchronized(this) {
         extFlags = 1 | this.flags & 16711680;
      }

      if (this.deliveryTime != 0L) {
         extFlags |= 256;
      }

      if (this.expirationTime != 0L) {
         extFlags |= 512;
      }

      if (this.groupName != null) {
         extFlags |= 1024;
      }

      if (this.redeliveryLimit != 0) {
         extFlags |= 2048;
      }

      out.writeInt(extFlags);
      out.writeLong(this.id);
      out.writeLong(this.size);
      if (!this.isPersistBody()) {
         this.persistentHandle.writeExternal(out);
      }

      if (this.deliveryTime != 0L) {
         out.writeLong(this.deliveryTime);
      }

      if (this.expirationTime != 0L) {
         out.writeLong(this.expirationTime);
      }

      if (this.redeliveryLimit != 0) {
         out.writeInt(this.redeliveryLimit);
      }

      if (this.groupName != null) {
         out.writeUTF(this.groupName);
      }

   }

   public final void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      int extFlags = in.readInt();
      if ((extFlags & 255) != 1) {
         throw new IOException("External version mismatch. Flags = " + extFlags + " version = " + (extFlags & 255));
      } else {
         this.flags = extFlags & 16711680;
         this.id = in.readLong();
         this.size = in.readLong();
         if (!this.isPersistBody()) {
            this.persistentHandle = PersistentHandle.read(in);
         }

         if ((extFlags & 256) != 0) {
            this.deliveryTime = in.readLong();
         }

         if ((extFlags & 512) != 0) {
            this.expirationTime = in.readLong();
         }

         if ((extFlags & 2048) != 0) {
            this.redeliveryLimit = in.readInt();
         }

         if ((extFlags & 1024) != 0) {
            this.groupName = in.readUTF();
         }

      }
   }

   public synchronized String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ id=");
      buf.append(this.id);
      buf.append(" queueRefCount=");
      buf.append(this.queueReferenceCount);
      buf.append(" quotaRefCount=");
      buf.append(this.quotaReferenceCount);
      buf.append(" persistentRefCount=");
      buf.append(this.persistentRefCount);
      buf.append(" pinCount=");
      buf.append(this.pinCount);
      buf.append(" size=");
      buf.append(this.size);
      buf.append(" persistent=");
      buf.append(this.isPersistent());
      buf.append(" persistBody=");
      buf.append(this.isPersistBody());
      buf.append(" ]");
      return buf.toString();
   }
}
