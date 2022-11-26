package weblogic.jms.extensions;

import javax.jms.JMSException;
import javax.jms.Message;

public interface MDBTransaction {
   void associateTransaction(Message var1) throws JMSException;
}
