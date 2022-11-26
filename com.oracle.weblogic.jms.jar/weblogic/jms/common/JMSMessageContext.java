package weblogic.jms.common;

import javax.jms.Message;
import javax.xml.rpc.handler.MessageContext;

public interface JMSMessageContext extends MessageContext {
   Message getMessage();

   void setMessage(Message var1);

   void setReturnedMessageId(JMSMessageId var1);

   Destination getDestination();

   void setDestination(Destination var1);

   String getUser();

   void setUser(String var1);

   JMSFailover getFailover();

   void setFailover(JMSFailover var1);
}
