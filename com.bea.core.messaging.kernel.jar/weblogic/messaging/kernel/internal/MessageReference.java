package weblogic.messaging.kernel.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.gxa.GXATransaction;
import weblogic.timers.Timer;

public abstract class MessageReference implements SortListElement {
   static final int VISIBLE = 0;
   static final int SEND = 1;
   static final int RECEIVE = 2;
   static final int TRANSACTION = 4;
   static final int ORDERED = 8;
   static final int DELAYED = 16;
   static final int EXPIRED = 32;
   static final int REDELIVERY_COUNT_EXCEEDED = 64;
   static final int PAUSED = 128;
   static final int SEQUENCED = 256;
   static final int UNIT_OF_WORK_COMPONENT = 512;
   static final int REDIRECTED = 1024;
   public static final int DELETED = 536870912;
   protected QueueImpl queue;
   private SortList queueList;
   private SortListElement queueNext;
   private SortListElement queuePrev;
   protected MessageHandle messageHandle;
   private GroupReference groupRef;
   protected SequenceReference seqRef;
   private GXATransaction transaction;
   private AbstractStatistics statistics;
   private int state = 0;
   protected int deliveryCount;
   private Timer deliveryTimer;
   private Timer expirationTimer;
   protected long sequenceNum;

   MessageReference() {
   }

   protected MessageReference(QueueImpl queue, MessageHandle messageHandle) {
      this.queue = queue;
      this.messageHandle = messageHandle;
   }

   MessageReference(MessageReference element) {
      this.queue = element.queue;
      this.messageHandle = element.messageHandle;
      if (element.groupRef != null) {
         this.groupRef = new GroupReference(this, element.groupRef.getGroup());
      }

      if (element.seqRef != null) {
         this.seqRef = new SequenceReference(this, element.seqRef);
      }

      this.statistics = element.statistics;
      this.state = element.state;
      this.deliveryCount = element.deliveryCount;
      this.sequenceNum = element.sequenceNum;
      this.transaction = element.transaction;
   }

   final void resetState() {
      this.statistics = null;
      this.transaction = null;
      this.state = 0;
   }

   abstract MessageReference duplicate();

   public final QueueImpl getQueue() {
      return this.queue;
   }

   final void setQueue(QueueImpl queue) {
      this.queue = queue;
   }

   final boolean isOnMessageList() {
      return this.queueList != null;
   }

   public final void setNext(SortListElement next) {
      this.queueNext = next;
   }

   public final SortListElement getNext() {
      return this.queueNext;
   }

   public final void setPrev(SortListElement prev) {
      this.queuePrev = prev;
   }

   public final SortListElement getPrev() {
      return this.queuePrev;
   }

   public final SortList getList() {
      return this.queueList;
   }

   public final void setList(SortList list) {
      this.queueList = list;
   }

   final Message getMessage(KernelImpl kernel) throws KernelException {
      this.messageHandle.pin(kernel);
      Message ret = this.messageHandle.getMessage();
      this.messageHandle.unPin(kernel);
      return ret;
   }

   public final MessageHandle getMessageHandle() {
      return this.messageHandle;
   }

   final void setMessageHandle(MessageHandle handle) {
      this.messageHandle = handle;
   }

   final boolean isPersistent() {
      return this.messageHandle.isPersistent() && this.queue.isDurable();
   }

   public final long getSequenceNumber() {
      return this.sequenceNum;
   }

   public final void setSequenceNumber(long sequenceNum) {
      this.sequenceNum = sequenceNum;
   }

   final SequenceReference getSequenceRef() {
      return this.seqRef;
   }

   final void setSequenceRef(SequenceReference seqRef) {
      this.seqRef = seqRef;
   }

   final AbstractStatistics getStatistics() {
      return this.statistics;
   }

   final void setStatistics(AbstractStatistics statistics) {
      AbstractStatistics memberStatistics = this.statistics;

      assert memberStatistics == null ^ statistics == null;

      if (statistics == null) {
         if (!this.isVisible()) {
            memberStatistics.decrementPending(this);
         }

         memberStatistics.decrementCurrent(this);
      } else {
         statistics.incrementCurrent(this);
         if (!this.isVisible()) {
            statistics.incrementPending(this);
         }
      }

      this.statistics = statistics;
   }

   final void clearState(int clearedState) {
      assert clearedState != 0;

      int oldState = this.state;
      this.state &= ~clearedState;
      if (this.statistics != null && this.state == 0 && oldState != 0) {
         this.statistics.decrementPending(this);
      }

   }

   public final int getState() {
      return this.state;
   }

   final void setState(int newState) {
      assert newState != 0;

      int oldState = this.state;
      this.state |= newState;
      if (this.statistics != null && oldState == 0) {
         this.statistics.incrementPending(this);
      }

   }

   public final GXATransaction getTransaction() {
      return this.transaction;
   }

   final void setTransaction(GXATransaction transaction) {
      if ((this.transaction = transaction) == null) {
         this.clearState(4);
      } else {
         this.setState(4);
      }

   }

   final boolean isVisible() {
      return this.state == 0;
   }

   final boolean isOrdered() {
      return (this.state & 8) != 0;
   }

   final boolean isReceived() {
      return (this.state & 2) != 0;
   }

   final boolean isConsumed() {
      return (this.state & 6) != 0;
   }

   final boolean isExpired() {
      return (this.state & 32) != 0;
   }

   final boolean isRedeliveryCountExceeded() {
      return (this.state & 64) != 0;
   }

   final boolean isRedirectable() {
      return (this.state & 96) != 0 && (this.state & 1024) == 0 && this.isRemovable();
   }

   final boolean isRemovable() {
      return (this.state & 263) == 0;
   }

   final boolean isRemovableByLimit() {
      return (this.state & 536871175) == 0;
   }

   final boolean isOutOfOrder() {
      return (this.state & 256) != 0;
   }

   public final int getDeliveryCount() {
      return this.deliveryCount;
   }

   final void incrementDeliveryCount() {
      ++this.deliveryCount;
   }

   final void decrementDeliveryCount() {
      --this.deliveryCount;
   }

   public final void setDeliveryCount(int count) {
      this.deliveryCount = count;
   }

   public final Timer getDeliveryTimer() {
      return this.deliveryTimer;
   }

   public final void setDeliveryTimer(Timer deliveryTimer) {
      this.deliveryTimer = deliveryTimer;
   }

   public final Timer getExpirationTimer() {
      return this.expirationTimer;
   }

   public final void setExpirationTimer(Timer expirationTimer) {
      this.expirationTimer = expirationTimer;
   }

   final GroupImpl getGroup() {
      return this.groupRef != null ? this.groupRef.getGroup() : null;
   }

   final GroupReference getGroupRef() {
      return this.groupRef;
   }

   final void setGroupRef(GroupReference ref) {
      this.groupRef = ref;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ sequence=");
      buf.append(this.sequenceNum);
      if (this.queue != null) {
         buf.append(" queue=");
         buf.append(this.queue.getSerialNumber());
         buf.append(" name=");
         buf.append(this.queue.getName());
      }

      buf.append(" state=");
      buf.append(this.getStateString());
      buf.append(" deliveryCount=");
      buf.append(this.deliveryCount);
      if (this.groupRef != null) {
         buf.append(" group=");
         buf.append(this.groupRef.getGroup());
      }

      if (this.seqRef != null) {
         buf.append(" sequence=");
         buf.append(this.seqRef.getSequence().getName());
         buf.append(" number=");
         buf.append(this.seqRef.getSequenceNum());
      }

      buf.append(" ]");
      return buf.toString();
   }

   String getStateString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.state);
      buf.append(":");
      if (this.state == 0) {
         buf.append("VISIBLE");
      } else {
         if ((this.state & 1) != 0) {
            buf.append(" SEND|");
         }

         if ((this.state & 2) != 0) {
            buf.append("RECEIVE|");
         }

         if ((this.state & 4) != 0) {
            buf.append("TRANSACTION|");
         }

         if ((this.state & 8) != 0) {
            buf.append("ORDERED|");
         }

         if ((this.state & 16) != 0) {
            buf.append("DELAYED|");
         }

         if ((this.state & 32) != 0) {
            buf.append("EXPIRED|");
         }

         if ((this.state & 64) != 0) {
            buf.append(" REDELIVERY_COUNT_EXCEEDED|");
         }

         if ((this.state & 128) != 0) {
            buf.append("PAUSED|");
         }

         if ((this.state & 256) != 0) {
            buf.append("SEQUENCED|");
         }

         if ((this.state & 512) != 0) {
            buf.append("UNIT_OF_WORK_COMPONENT ");
         }
      }

      return buf.toString();
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Message");
      if (this.messageHandle != null) {
         xsw.writeAttribute("messageHandle", this.messageHandle.toString());
         Message tmpMessage = this.messageHandle.getMessage();
         if (tmpMessage != null) {
            Object tmpID = tmpMessage.getMessageID();
            if (tmpID != null) {
               xsw.writeAttribute("messageID", tmpID.toString());
            }
         }
      }

      xsw.writeAttribute("deliveryCount", String.valueOf(this.deliveryCount));
      MessagingKernelDiagnosticImageSource.dumpMessageStatesAttribute(xsw, this.state);
      if (this.groupRef != null) {
         xsw.writeAttribute("groupName", this.groupRef.getGroup().getName());
      }

      if (this.transaction != null) {
         xsw.writeAttribute("xid", this.transaction.getGXid().toString());
      }

      xsw.writeEndElement();
   }
}
