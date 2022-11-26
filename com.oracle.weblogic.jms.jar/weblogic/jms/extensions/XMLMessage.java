package weblogic.jms.extensions;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.w3c.dom.Document;

public interface XMLMessage extends TextMessage {
   void setDocument(Document var1) throws JMSException;

   Document getDocument() throws JMSException;
}
