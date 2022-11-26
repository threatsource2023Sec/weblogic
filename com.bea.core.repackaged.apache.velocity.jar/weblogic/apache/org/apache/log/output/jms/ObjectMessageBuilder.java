package weblogic.apache.org.apache.log.output.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import weblogic.apache.org.apache.log.LogEvent;

public class ObjectMessageBuilder implements MessageBuilder {
   public Message buildMessage(Session session, LogEvent event) throws JMSException {
      synchronized(session) {
         ObjectMessage message = session.createObjectMessage();
         message.setObject(event);
         return message;
      }
   }
}
