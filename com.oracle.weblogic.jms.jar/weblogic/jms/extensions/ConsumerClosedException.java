package weblogic.jms.extensions;

import javax.jms.MessageConsumer;
import weblogic.jms.common.JMSException;

public final class ConsumerClosedException extends JMSException {
   static final long serialVersionUID = -1819873727815556850L;
   private MessageConsumer consumer;

   public ConsumerClosedException(MessageConsumer consumer, String reason, String errorCode) {
      super(reason, errorCode);
      this.consumer = consumer;
   }

   public ConsumerClosedException(MessageConsumer consumer, String reason) {
      super(reason);
      this.consumer = consumer;
   }

   public MessageConsumer getConsumer() {
      return this.consumer;
   }

   public void setConsumer(MessageConsumer consumer) {
      this.consumer = consumer;
   }
}
