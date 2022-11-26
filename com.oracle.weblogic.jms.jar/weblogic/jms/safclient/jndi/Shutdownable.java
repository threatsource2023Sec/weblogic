package weblogic.jms.safclient.jndi;

import javax.jms.JMSException;

public interface Shutdownable {
   void shutdown(JMSException var1);
}
