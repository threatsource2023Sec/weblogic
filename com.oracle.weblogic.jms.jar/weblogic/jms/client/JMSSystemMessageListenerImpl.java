package weblogic.jms.client;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;

public class JMSSystemMessageListenerImpl implements JMSSystemMessageListener {
   private LinkedList messages = new LinkedList();

   public void onMessage(Message message) {
      this.addMessage(message);
   }

   public Message receive(long timeout, Class c) throws JMSException {
      synchronized(this) {
         return this.removeMessage(c);
      }
   }

   private synchronized void addMessage(Message message) {
      this.messages.add(message);
      this.notify();
   }

   private synchronized Message removeMessage(Class c) throws JMSException {
      try {
         if (c == null) {
            return (Message)this.messages.remove();
         } else {
            Message m = (Message)this.messages.remove();

            try {
               m.getBody(c);
            } catch (MessageFormatException var4) {
               this.messages.addFirst(m);
               throw var4;
            }

            return m;
         }
      } catch (NoSuchElementException var5) {
         return null;
      }
   }
}
