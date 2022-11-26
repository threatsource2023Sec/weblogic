package weblogic.jms.client;

import java.util.Enumeration;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSBrowserGetEnumerationResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.frontend.FEBrowserGetEnumerationRequest;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class JMSQueueBrowser implements QueueBrowser, Invocable {
   private final JMSDispatcher frontEndDispatcher;
   private JMSID browserId;
   private final Queue queue;
   private final String selector;
   private final JMSSession session;

   public JMSQueueBrowser(Queue queue, String selector, JMSSession session) throws JMSException {
      this.queue = queue;
      this.selector = selector;
      this.session = session;
      this.frontEndDispatcher = session.getConnection().getFrontEndDispatcher();
      this.browserId = session.createBackEndBrowser((DestinationImpl)queue, selector);
   }

   void setId(JMSID browserId) {
      this.browserId = browserId;
   }

   public JMSID getJMSID() {
      return this.browserId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.session.getDispatcherPartition4rmic();
   }

   public InvocableMonitor getInvocableMonitor() {
      return null;
   }

   public Queue getQueue() throws JMSException {
      this.checkClosed();
      return this.queue;
   }

   public String getMessageSelector() throws JMSException {
      this.checkClosed();
      return this.selector;
   }

   public Enumeration getEnumeration() throws JMSException {
      this.checkClosed();
      Object response = this.frontEndDispatcher.dispatchSync(new FEBrowserGetEnumerationRequest(this.browserId));
      return new JMSEnumeration(this.queue, this, ((JMSBrowserGetEnumerationResponse)response).getEnumerationId(), this.frontEndDispatcher);
   }

   public JMSSession getSession() throws JMSException {
      this.checkClosed();
      return this.session;
   }

   boolean isClosed() {
      return this.browserId == null;
   }

   private void checkClosed() throws JMSException {
      if (this.isClosed()) {
         throw new IllegalStateException(JMSClientExceptionLogger.logClosedBrowserLoggable().getMessage());
      }
   }

   public void close() throws JMSException {
      JMSID browserId;
      synchronized(this) {
         if (this.isClosed()) {
            return;
         }

         browserId = this.browserId;
         this.browserId = null;
      }

      this.session.closeBrowser(browserId, false);
   }

   private int pushException(Request invocableRequest) throws JMSException {
      JMSPushExceptionRequest request = (JMSPushExceptionRequest)invocableRequest;
      JMSID browserId;
      synchronized(this) {
         browserId = this.browserId;
         this.browserId = null;
      }

      if (browserId != null) {
         this.session.closeBrowser(browserId, true);
      }

      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   public int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 15382:
            return this.pushException(request);
         default:
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoSuchMethod3Loggable(request.getMethodId(), this.getClass().getName()));
      }
   }
}
