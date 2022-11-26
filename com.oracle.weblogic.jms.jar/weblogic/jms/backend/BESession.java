package weblogic.jms.backend;

import javax.jms.JMSException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.dispatcher.Invocable;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public interface BESession extends Invocable {
   JMSID getJMSID();

   void setConnection(BEConnection var1);

   BEConnection getConnection();

   void start() throws JMSException;

   void stop();

   void peerGone() throws JMSException;

   void close() throws JMSException;

   void browserRemove(JMSID var1);

   void dump(JMSDiagnosticImageSource var1, XMLStreamWriter var2) throws XMLStreamException, DiagnosticImageTimeoutException;
}
