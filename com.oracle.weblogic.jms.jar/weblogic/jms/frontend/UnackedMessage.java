package weblogic.jms.frontend;

import weblogic.jms.common.MessageImpl;

public final class UnackedMessage {
   private final FEConsumer consumer;
   private final FEProducer producer;
   private final long messageSize;
   private UnackedMessage next;
   private UnackedMessage prev;

   UnackedMessage(FEConsumer consumer, FEProducer producer, MessageImpl message) {
      this.consumer = consumer;
      this.producer = producer;
      this.messageSize = message.getPayloadSize() + (long)message.getUserPropertySize();
   }

   UnackedMessage getNext() {
      return this.next;
   }

   UnackedMessage getPrev() {
      return this.prev;
   }

   void setNext(UnackedMessage unackedMessage) {
      this.next = unackedMessage;
   }

   void setPrev(UnackedMessage unackedMessage) {
      this.prev = unackedMessage;
   }

   void commitTransactedStatistics(FESession session) {
      session.getStatistics().decrementPendingCount(this.messageSize);
      if (this.consumer != null) {
         this.consumer.statistics.decrementPendingCount(this.messageSize);
         this.consumer.statistics.incrementReceivedCount(this.messageSize);
         session.getStatistics().incrementReceivedCount(this.messageSize);
      } else {
         this.producer.decMessagesPendingCount(this.messageSize);
         this.producer.incMessagesSentCount(this.messageSize);
         session.getStatistics().incrementSentCount(this.messageSize);
      }

   }

   void rollbackTransactedStatistics(FESession session) {
      session.getStatistics().decrementPendingCount(this.messageSize);
      if (this.consumer != null) {
         this.consumer.statistics.decrementPendingCount(this.messageSize);
      } else {
         this.producer.decMessagesPendingCount(this.messageSize);
      }

   }
}
