package weblogic.jms.client;

import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageReference;

final class JMSMessageReference extends MessageReference {
   private ConsumerInternal consumer;

   JMSMessageReference(MessageImpl message, ConsumerInternal consumer) {
      super(message);
      this.consumer = consumer;
   }

   ConsumerInternal getConsumer() {
      return this.consumer;
   }

   public void prepareForCache() {
      super.prepareForCache();
      this.consumer = null;
   }

   public void reset(MessageImpl message, ConsumerInternal consumer) {
      super.reset(message);
      this.consumer = consumer;
   }

   public String toString() {
      return "(JMSmRef id=" + this.getMessage().getId() + " consumer=" + (this.consumer == null ? "null" : "" + this.consumer.getJMSID()) + ")";
   }
}
