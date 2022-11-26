package weblogic.jms.extensions;

import java.io.IOException;
import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

public interface WLMessageFactory {
   Message createMessage();

   Message createMessage(Document var1) throws DOMException, JMSException, IOException, ClassNotFoundException;

   BytesMessage createBytesMessage();

   MapMessage createMapMessage();

   ObjectMessage createObjectMessage();

   ObjectMessage createObjectMessage(Serializable var1) throws JMSException;

   StreamMessage createStreamMessage();

   TextMessage createTextMessage();

   TextMessage createTextMessage(String var1);

   TextMessage createTextMessage(StringBuffer var1);

   XMLMessage createXMLMessage();

   XMLMessage createXMLMessage(String var1);

   XMLMessage createXMLMessage(Document var1) throws JMSException;
}
