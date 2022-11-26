package weblogic.jms.extensions;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.transaction.xa.XAResource;
import org.w3c.dom.Document;

public interface WLSession extends Session {
   int NO_ACKNOWLEDGE = 4;
   int MULTICAST_NO_ACKNOWLEDGE = 128;
   int KEEP_OLD = 0;
   int KEEP_NEW = 1;

   XMLMessage createXMLMessage() throws JMSException;

   XMLMessage createXMLMessage(String var1) throws JMSException;

   XMLMessage createXMLMessage(Document var1) throws JMSException;

   void setExceptionListener(ExceptionListener var1) throws JMSException;

   int getMessagesMaximum() throws JMSException;

   void setMessagesMaximum(int var1) throws JMSException;

   int getOverrunPolicy() throws JMSException;

   void setOverrunPolicy(int var1) throws JMSException;

   long getRedeliveryDelay() throws JMSException;

   void setRedeliveryDelay(long var1) throws JMSException;

   void acknowledge() throws JMSException;

   void acknowledge(Message var1) throws JMSException;

   void acknowledge(WLAcknowledgeInfo var1) throws JMSException;

   void unsubscribe(Topic var1, String var2) throws JMSException;

   XAResource getXAResource(String var1);
}
