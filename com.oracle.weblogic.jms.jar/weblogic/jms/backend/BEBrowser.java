package weblogic.jms.backend;

import javax.jms.JMSException;
import weblogic.jms.dispatcher.Invocable;

public interface BEBrowser extends Invocable {
   void close() throws JMSException;
}
