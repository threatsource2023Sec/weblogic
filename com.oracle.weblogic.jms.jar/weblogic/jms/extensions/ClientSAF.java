package weblogic.jms.extensions;

import javax.jms.JMSException;
import javax.naming.Context;

public interface ClientSAF {
   void open(char[] var1) throws JMSException;

   void close();

   boolean isOpen();

   Context getContext() throws JMSException;
}
