package weblogic.jms.backend;

import javax.jms.JMSException;
import weblogic.jms.common.JMSEnumerationNextElementResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;

final class BEEnumerationImpl implements Invocable {
   private boolean closed;
   private BEBrowserImpl browser;
   private JMSID enumerationId;
   private InvocableMonitor invocableMonitor;
   private Cursor cursor;

   BEEnumerationImpl(BEBrowserImpl browser, JMSID enumerationId, Queue queue, Expression filterExpression) throws JMSException {
      this.browser = browser;
      this.enumerationId = enumerationId;
      this.invocableMonitor = browser.getInvocableMonitor();

      try {
         this.cursor = queue.createCursor(true, filterExpression, 1);
      } catch (KernelException var6) {
         throw new weblogic.jms.common.JMSException(var6);
      }
   }

   void close() {
      synchronized(this) {
         if (this.closed) {
            return;
         }

         this.closed = true;
      }

      this.cursor.close();
      this.browser.enumerationRemove(this.enumerationId);
   }

   private int nextElement(BEEnumerationNextElementRequest request) throws JMSException {
      this.browser.checkShutdownOrSuspended("next element");

      MessageElement ref;
      try {
         ref = this.cursor.next();
      } catch (KernelException var4) {
         throw new weblogic.jms.common.JMSException(var4);
      }

      MessageImpl message = (MessageImpl)((MessageImpl)(ref == null ? null : ref.getMessage()));
      if (message != null) {
         if (ref.getDeliveryCount() > 0) {
            message = message.cloneit();
            message.setDeliveryCount(ref.getDeliveryCount());
         }
      } else {
         this.close();
      }

      request.setResult(new JMSEnumerationNextElementResponse(message));
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
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

   public int invoke(Request request) throws JMSException {
      if (request.getMethodId() == 11795) {
         return this.nextElement((BEEnumerationNextElementRequest)request);
      } else {
         throw new weblogic.jms.common.JMSException("No such method " + this.getClass().getName() + "." + request.getMethodId());
      }
   }
}
