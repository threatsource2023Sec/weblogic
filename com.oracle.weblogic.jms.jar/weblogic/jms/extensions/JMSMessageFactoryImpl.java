package weblogic.jms.extensions;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import weblogic.jms.common.BytesMessageImpl;
import weblogic.jms.common.HdrMessageImpl;
import weblogic.jms.common.MapMessageImpl;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.StreamMessageImpl;
import weblogic.jms.common.TextMessageImpl;
import weblogic.jms.common.XMLMessageImpl;

public final class JMSMessageFactoryImpl implements WLMessageFactory {
   private static WLMessageFactory messageFactory = null;

   public static final synchronized WLMessageFactory getFactory() {
      if (messageFactory == null) {
         messageFactory = new JMSMessageFactoryImpl();
      }

      return messageFactory;
   }

   JMSMessageFactoryImpl() {
   }

   public final Message createMessage() {
      return new HdrMessageImpl();
   }

   public final Message createMessage(Document jmsMessageDocument) throws DOMException, JMSException, IOException, ClassNotFoundException {
      try {
         Class clz = Class.forName("weblogic.jms.common.XMLHelper");
         Method m = clz.getMethod("createMessage", Document.class);
         return (Message)m.invoke((Object)null, jmsMessageDocument);
      } catch (InvocationTargetException var4) {
         Throwable target = var4.getTargetException();
         if (target instanceof DOMException) {
            throw (DOMException)target;
         } else if (target instanceof JMSException) {
            throw (JMSException)target;
         } else if (target instanceof IOException) {
            throw (IOException)target;
         } else if (target instanceof ClassNotFoundException) {
            throw (ClassNotFoundException)target;
         } else {
            throw new AssertionError(target);
         }
      } catch (Exception var5) {
         throw new AssertionError(var5);
      }
   }

   public final BytesMessage createBytesMessage() {
      return new BytesMessageImpl();
   }

   public final MapMessage createMapMessage() {
      return new MapMessageImpl();
   }

   public final ObjectMessage createObjectMessage() {
      return new ObjectMessageImpl();
   }

   public final ObjectMessage createObjectMessage(Serializable object) throws JMSException {
      ObjectMessage om = new ObjectMessageImpl();
      om.setObject(object);
      return om;
   }

   public final StreamMessage createStreamMessage() {
      return new StreamMessageImpl();
   }

   public final TextMessage createTextMessage() {
      return new TextMessageImpl();
   }

   public final TextMessage createTextMessage(String string) {
      return new TextMessageImpl(string);
   }

   public final TextMessage createTextMessage(StringBuffer buffer) {
      return new TextMessageImpl(buffer.toString());
   }

   public final XMLMessage createXMLMessage() {
      return new XMLMessageImpl();
   }

   public final XMLMessage createXMLMessage(String string) {
      return new XMLMessageImpl(string);
   }

   public final XMLMessage createXMLMessage(Document document) throws JMSException {
      return new XMLMessageImpl(document);
   }
}
