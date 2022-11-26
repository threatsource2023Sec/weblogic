package weblogic.apache.org.apache.log.output.jms;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import weblogic.apache.org.apache.log.ContextMap;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.format.Formatter;

public class TextMessageBuilder implements MessageBuilder {
   private final PropertyInfo[] m_properties;
   private final Formatter m_formatter;

   public TextMessageBuilder(Formatter formatter) {
      this.m_properties = new PropertyInfo[0];
      this.m_formatter = formatter;
   }

   public TextMessageBuilder(PropertyInfo[] properties, Formatter formatter) {
      this.m_properties = properties;
      this.m_formatter = formatter;
   }

   public Message buildMessage(Session session, LogEvent event) throws JMSException {
      synchronized(session) {
         TextMessage message = session.createTextMessage();
         message.setText(this.getText(event));

         for(int i = 0; i < this.m_properties.length; ++i) {
            this.setProperty(message, i, event);
         }

         return message;
      }
   }

   private void setProperty(TextMessage message, int index, LogEvent event) throws JMSException {
      PropertyInfo info = this.m_properties[index];
      String name = info.getName();
      switch (info.getType()) {
         case 1:
            message.setStringProperty(name, info.getAux());
            break;
         case 2:
            message.setStringProperty(name, event.getCategory());
            break;
         case 3:
            message.setStringProperty(name, this.getContextMap(event.getContextMap(), info.getAux()));
            break;
         case 4:
            message.setStringProperty(name, event.getMessage());
            break;
         case 5:
            message.setLongProperty(name, event.getTime());
            break;
         case 6:
            message.setLongProperty(name, event.getRelativeTime());
            break;
         case 7:
            message.setStringProperty(name, this.getStackTrace(event.getThrowable()));
            break;
         case 8:
            message.setStringProperty(name, event.getPriority().getName());
            break;
         default:
            throw new IllegalStateException("Unknown PropertyType: " + info.getType());
      }

   }

   private String getText(LogEvent event) {
      return null == this.m_formatter ? event.getMessage() : this.m_formatter.format(event);
   }

   private String getStackTrace(Throwable throwable) {
      if (null == throwable) {
         return "";
      } else {
         StringWriter stringWriter = new StringWriter();
         PrintWriter printWriter = new PrintWriter(stringWriter);
         throwable.printStackTrace(printWriter);
         return stringWriter.getBuffer().toString();
      }
   }

   private String getContextMap(ContextMap map, String aux) {
      return null == map ? "" : map.get(aux, "").toString();
   }
}
