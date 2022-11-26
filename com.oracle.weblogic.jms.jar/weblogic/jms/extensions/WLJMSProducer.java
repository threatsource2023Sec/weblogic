package weblogic.jms.extensions;

import javax.jms.Destination;
import javax.jms.JMSProducer;
import javax.jms.Message;

public interface WLJMSProducer extends JMSProducer {
   int getRedeliveryLimit();

   WLJMSProducer setRedeliveryLimit(int var1);

   long getSendTimeout();

   WLJMSProducer setSendTimeout(long var1);

   String getUnitOfOrder();

   WLJMSProducer setUnitOfOrder(String var1);

   WLJMSProducer setUnitOfOrder();

   WLJMSProducer forward(Destination var1, Message var2);

   WLJMSProducer setCompressionThreshold(int var1);

   int getCompressionThreshold();
}
