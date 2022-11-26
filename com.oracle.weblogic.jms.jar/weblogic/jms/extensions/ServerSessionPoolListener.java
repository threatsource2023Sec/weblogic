package weblogic.jms.extensions;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.MessageListener;

public interface ServerSessionPoolListener extends MessageListener {
   void initialize(Serializable var1) throws JMSException;
}
