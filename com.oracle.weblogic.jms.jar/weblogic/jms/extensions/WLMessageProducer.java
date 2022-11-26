package weblogic.jms.extensions;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

public interface WLMessageProducer extends MessageProducer {
   long getTimeToDeliver() throws JMSException;

   void setTimeToDeliver(long var1) throws JMSException;

   int getRedeliveryLimit() throws JMSException;

   void setRedeliveryLimit(int var1) throws JMSException;

   long getSendTimeout() throws JMSException;

   void setSendTimeout(long var1) throws JMSException;

   String getUnitOfOrder() throws JMSException;

   void setUnitOfOrder(String var1) throws JMSException;

   void setUnitOfOrder() throws JMSException;

   void forward(Message var1, int var2, int var3, long var4) throws JMSException;

   void forward(Message var1) throws JMSException;

   void forward(Destination var1, Message var2, int var3, int var4, long var5) throws JMSException;

   void forward(Destination var1, Message var2) throws JMSException;

   void setCompressionThreshold(int var1) throws JMSException;

   int getCompressionThreshold() throws JMSException;
}
