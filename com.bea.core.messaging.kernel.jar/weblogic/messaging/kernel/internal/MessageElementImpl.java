package weblogic.messaging.kernel.internal;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Group;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Sequence;
import weblogic.store.gxa.GXATransaction;
import weblogic.utils.expressions.ExpressionMap;

public final class MessageElementImpl implements MessageElement {
   private Message message;
   private MessageReference ref;
   private Object userData;
   private long userSequenceNum;
   private String consumerID;
   private boolean hideSequence;

   public MessageElementImpl(Message message) {
      this.message = message;
   }

   MessageElementImpl(MessageReference elt, Message message) {
      this.ref = elt;
      this.message = message;
   }

   MessageElementImpl(MessageReference elt, Message message, boolean hideSequence) {
      this.ref = elt;
      this.message = message;
      this.hideSequence = hideSequence;
   }

   MessageElementImpl(MessageReference elt, KernelImpl kernel) throws KernelException {
      this(elt, kernel, false);
   }

   MessageElementImpl(MessageReference elt, KernelImpl kernel, boolean delete) throws KernelException {
      this.ref = elt;
      MessageHandle handle = elt.getMessageHandle();
      if (!delete) {
         handle.pin(kernel);
         this.message = handle.getMessage();
         handle.unPin(kernel);
      }

   }

   MessageReference getMessageReference() {
      return this.ref;
   }

   public Message getMessage() {
      return this.message;
   }

   public Object parse() throws Exception {
      return ((ExpressionMap)this.message).parse();
   }

   public Queue getQueue() {
      return this.ref == null ? null : this.ref.getQueue();
   }

   public int getState() {
      if (this.ref == null) {
         return 1;
      } else {
         int state = this.ref.getState();
         return state == 0 ? 1 : state << 1;
      }
   }

   public int getDeliveryCount() {
      return this.ref == null ? 0 : this.ref.getDeliveryCount();
   }

   public Group getGroup() {
      return this.ref == null ? null : this.ref.getGroup();
   }

   public void setUserData(Object userData) {
      this.userData = userData;
   }

   public Object getUserData() {
      return this.userData;
   }

   public void setUserSequenceNum(long sequence) {
      this.userSequenceNum = sequence;
   }

   public long getUserSequenceNum() {
      return this.userSequenceNum;
   }

   void setConsumerID(String id) {
      this.consumerID = id;
   }

   public String getConsumerID() {
      return this.consumerID;
   }

   public Sequence getSequence() {
      if (this.ref == null) {
         return null;
      } else {
         SequenceReference seqRef = this.ref.getSequenceRef();
         return seqRef == null || this.hideSequence && !seqRef.getSequence().isStampRequired() ? null : seqRef.getSequence();
      }
   }

   public long getSequenceNum() {
      if (this.ref == null) {
         return 0L;
      } else {
         SequenceReference seqRef = this.ref.getSequenceRef();
         return seqRef == null || this.hideSequence && !seqRef.getSequence().isStampRequired() ? 0L : seqRef.getSequenceNum();
      }
   }

   public long getInternalSequenceNumber() {
      return this.ref == null ? 0L : this.ref.getSequenceNumber();
   }

   public Xid getXid() {
      if (this.ref == null) {
         return null;
      } else {
         GXATransaction tran = this.ref.getTransaction();
         return tran != null ? tran.getGXid().getXAXid() : null;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (this.message != null && this.message.getMessageID() != null) {
         buf.append("[ message=");
         buf.append(this.message.getMessageID().toString());
      } else {
         buf.append("[ message=null");
      }

      if (this.consumerID != null) {
         buf.append(" consumer=");
         buf.append(this.consumerID);
      }

      if (this.userData != null) {
         buf.append(" userData=");
         buf.append(this.userData.toString());
      }

      if (this.ref != null) {
         buf.append(' ');
         buf.append(this.ref.toString());
      }

      buf.append(']');
      return buf.toString();
   }
}
