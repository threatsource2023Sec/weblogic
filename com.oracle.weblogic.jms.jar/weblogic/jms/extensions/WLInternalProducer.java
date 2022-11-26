package weblogic.jms.extensions;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public interface WLInternalProducer extends WLMessageProducer {
   void setSequence(String var1) throws JMSException;

   String getSequence() throws JMSException;

   void reserveUnitOfOrderWithSequence() throws JMSException;

   void reserveSequence(Destination var1, Message var2, int var3, int var4, long var5) throws JMSException;

   void releaseSequenceAndUnitOfOrder(boolean var1) throws JMSException;

   void releaseSequenceAndUnitOfOrder(Destination var1, Message var2, int var3, int var4, long var5, boolean var7) throws JMSException;
}
