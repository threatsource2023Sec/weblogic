package weblogic.jms.client;

import javax.jms.JMSException;

public interface MMessageAsyncSession {
   void setMMessageListener(MMessageListener var1) throws JMSException;
}
