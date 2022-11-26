package weblogic.jms.client;

import javax.jms.JMSException;
import javax.jms.Message;

public class JMSSystemMessageListenerImpl2 implements JMSSystemMessageListener {
   ConsumerInternal consumer;

   public JMSSystemMessageListenerImpl2(ConsumerInternal consumer) {
      this.consumer = consumer;
   }

   public void onMessage(Message message) {
      throw new AssertionError("This should not happen");
   }

   public Message receive(long timeout, Class c) throws JMSException {
      return this.consumer.getSession().getAsyncMessageForConsumer(this.consumer, timeout, c);
   }
}
