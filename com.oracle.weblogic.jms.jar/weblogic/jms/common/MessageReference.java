package weblogic.jms.common;

public class MessageReference {
   private MessageImpl message;
   private long sequenceNumber;
   private int deliveryCount = 1;
   private MessageReference prev;
   private MessageReference next;

   public MessageReference(MessageImpl message) {
      this.message = message;
      this.deliveryCount = message.getDeliveryCount();
   }

   public MessageReference(MessageReference mRef) {
      this.message = mRef.message;
      this.deliveryCount = mRef.deliveryCount;
   }

   public final void setMessage(MessageImpl message) {
      this.message = message;
   }

   public final MessageImpl getMessage() {
      return this.message;
   }

   public final void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public final void setPrev(MessageReference prev) {
      this.prev = prev;
   }

   public final MessageReference getPrev() {
      return this.prev;
   }

   public final void setNext(MessageReference next) {
      this.next = next;
   }

   public final MessageReference getNext() {
      return this.next;
   }

   public final int getDeliveryCount() {
      return this.deliveryCount;
   }

   public final void incrementDeliveryCount() {
      if (this.deliveryCount != -1) {
         ++this.deliveryCount;
      }

   }

   public final boolean getRedelivered() {
      return this.deliveryCount != 1;
   }

   public final void setDeliveryCount(int newDeliveryCount) {
      this.deliveryCount = newDeliveryCount;
   }

   public void prepareForCache() {
      if (this.message != null) {
         this.message.setMessageReference((MessageReference)null);
      }

      this.message = null;
      this.sequenceNumber = 0L;
      this.deliveryCount = 1;
      this.prev = null;
      this.next = null;
   }

   public final void reset(MessageImpl message) {
      this.message = message;
      if (message.getJMSRedelivered()) {
         this.deliveryCount = -1;
      } else {
         this.deliveryCount = 1;
      }

   }

   public String toString() {
      return "(mRef id=" + this.message.getId() + ")";
   }
}
