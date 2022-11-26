package weblogic.jms.frontend;

import javax.jms.JMSException;
import weblogic.jms.backend.BEEnumerationNextElementRequest;
import weblogic.jms.common.JMSEnumerationNextElementResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class FEEnumeration implements Invocable {
   private final FEBrowser browser;
   private final JMSID enumerationId;
   private final JMSDispatcher dispatcher;
   private final InvocableMonitor invocableMonitor;

   public FEEnumeration(FEBrowser browser, JMSID enumerationId, JMSDispatcher dispatcher) {
      this.browser = browser;
      this.enumerationId = enumerationId;
      this.dispatcher = dispatcher;
      this.invocableMonitor = browser.getInvocableMonitor();
   }

   public JMSID getJMSID() {
      return this.enumerationId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.browser.getDispatcherPartition4rmic();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   private int nextElement(Request invocableRequest) throws JMSException {
      FEEnumerationNextElementRequest request = (FEEnumerationNextElementRequest)invocableRequest;
      switch (request.getState()) {
         case 0:
            BEEnumerationNextElementRequest child = new BEEnumerationNextElementRequest(this.enumerationId);
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               request.dispatchAsync(this.dispatcher, child);
            } catch (DispatcherException var6) {
               throw new weblogic.jms.common.JMSException("Error getting next element", var6);
            }

            return request.getState();
         case 1:
            JMSEnumerationNextElementResponse response = (JMSEnumerationNextElementResponse)request.useChildResult(JMSEnumerationNextElementResponse.class);
            if (response.getMessage() == null) {
               this.browser.enumerationRemove(this.enumerationId);
            }

            MessageImpl message = response.getMessage();
            if (message != null) {
               response.setCompressionThreshold(this.browser.getConnection().getCompressionThreshold());
            }

            request.setResult(response);
            return Integer.MAX_VALUE;
         default:
            return request.getState();
      }
   }

   public int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 4108:
            return this.nextElement(request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + this.getClass().getName() + "." + request.getMethodId());
      }
   }
}
