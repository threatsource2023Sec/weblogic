package weblogic.jms.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public interface JMSSystemMessageListener extends MessageListener {
   Message receive(long var1, Class var3) throws JMSException;
}
