package weblogic.jms.client;

import weblogic.jms.common.MessageImpl;

public class MessageWrapper {
   private MessageWrapper next = null;
   private long proxyConsumerId;
   private long sequence;
   private int generation;
   private int deliveryCount;
   private MessageImpl messageImpl;

   MessageWrapper(long proxyConsumerId, int generation, long sequence, int deliveryCount, MessageImpl messageImpl) {
      this.proxyConsumerId = proxyConsumerId;
      this.generation = generation;
      this.sequence = sequence;
      this.deliveryCount = deliveryCount;
      this.messageImpl = messageImpl;
   }

   void next(MessageWrapper next) {
      this.next = next;
   }

   public MessageWrapper next() {
      return this.next;
   }

   public long getProxyId() {
      return this.proxyConsumerId;
   }

   public long getSequence() {
      return this.sequence;
   }

   public int getGeneration() {
      return this.generation;
   }

   public int getDeliveryCount() {
      return this.deliveryCount;
   }

   public MessageImpl getMessageImpl() {
      return this.messageImpl;
   }
}
