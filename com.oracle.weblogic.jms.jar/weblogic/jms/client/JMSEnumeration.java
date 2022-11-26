package weblogic.jms.client;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.jms.JMSException;
import javax.jms.Queue;
import weblogic.jms.common.JMSEnumerationNextElementResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.frontend.FEEnumerationNextElementRequest;

final class JMSEnumeration implements Enumeration {
   private final Queue queue;
   private final JMSQueueBrowser browser;
   private JMSID enumerationId;
   private final JMSDispatcher frontEndDispatcher;
   private MessageImpl message;

   public JMSEnumeration(Queue queue, JMSQueueBrowser browser, JMSID enumerationId, JMSDispatcher frontEndDispatcher) throws JMSException {
      this.queue = queue;
      this.browser = browser;
      this.enumerationId = enumerationId;
      this.frontEndDispatcher = frontEndDispatcher;
   }

   public boolean hasMoreElements() {
      if (this.message != null) {
         return true;
      } else {
         if (!this.isClosed()) {
            this.message = this.getNextMessage();
         }

         return this.message != null;
      }
   }

   public Object nextElement() {
      if (this.message == null) {
         if (!this.isClosed()) {
            this.message = this.getNextMessage();
         }

         if (this.message == null) {
            throw new NoSuchElementException();
         }
      }

      MessageImpl message = this.message;
      this.message = null;

      try {
         message.setJMSDestination(this.queue);
         if (this.isClosed() || this.browser.getSession().getConnection().isLocal()) {
            boolean old = message.isOldMessage();
            message = message.copy();
            message.setOldMessage(old);
         }

         message.includeJMSXDeliveryCount(true);
         return message;
      } catch (JMSException var3) {
         throw new AssertionError();
      }
   }

   private boolean isClosed() {
      if (this.enumerationId == null) {
         return true;
      } else {
         if (this.browser.isClosed()) {
            this.close();
         }

         return this.enumerationId == null;
      }
   }

   private void close() {
      this.enumerationId = null;
   }

   private MessageImpl getNextMessage() {
      MessageImpl message = null;

      try {
         Object response = this.frontEndDispatcher.dispatchSync(new FEEnumerationNextElementRequest(this.enumerationId));
         message = ((JMSEnumerationNextElementResponse)response).getMessage();
      } catch (JMSException var3) {
         return null;
      }

      if (message == null) {
         this.close();
      }

      return message;
   }
}
