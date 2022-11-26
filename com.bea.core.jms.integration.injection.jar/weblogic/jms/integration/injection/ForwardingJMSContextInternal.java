package weblogic.jms.integration.injection;

import javax.jms.ExceptionListener;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Topic;
import org.w3c.dom.Document;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.client.JMSContextInternal;
import weblogic.jms.extensions.WLJMSContext;
import weblogic.jms.extensions.XMLMessage;

public abstract class ForwardingJMSContextInternal extends ForwardingJMSContext implements JMSContextInternal {
   private JMSContextInternal getJMSContextInternal() {
      JMSContext delegateJMSContext = this.delegate();
      if (!(delegateJMSContext instanceof JMSContextInternal)) {
         String message = JMSExceptionLogger.logJMSContextInternalMethodUnavailableLoggable().getMessage();
         throw new JMSRuntimeException(message);
      } else {
         return (JMSContextInternal)delegateJMSContext;
      }
   }

   private WLJMSContext getWLJMSContext() {
      JMSContext delegateJMSContext = this.delegate();
      if (!(delegateJMSContext instanceof JMSContextInternal)) {
         String message = JMSExceptionLogger.logWLJMSContextMethodUnavailableLoggable().getMessage();
         throw new JMSRuntimeException(message);
      } else {
         return (WLJMSContext)delegateJMSContext;
      }
   }

   public boolean getUserTransactionsEnabled() {
      return this.getJMSContextInternal().getUserTransactionsEnabled();
   }

   public boolean isXAServerEnabled() {
      return this.getJMSContextInternal().isXAServerEnabled();
   }

   public void setReconnectPolicy(String reconnectPolicy) throws IllegalArgumentException {
      this.getWLJMSContext().setReconnectPolicy(reconnectPolicy);
   }

   public String getReconnectPolicy() {
      return this.getWLJMSContext().getReconnectPolicy();
   }

   public void setReconnectBlockingMillis(long timeout) throws IllegalArgumentException {
      this.getWLJMSContext().setReconnectBlockingMillis(timeout);
   }

   public long getReconnectBlockingMillis() {
      return this.getWLJMSContext().getReconnectBlockingMillis();
   }

   public void setTotalReconnectPeriodMillis(long timeout) throws IllegalArgumentException {
      this.getWLJMSContext().setTotalReconnectPeriodMillis(timeout);
   }

   public long getTotalReconnectPeriodMillis() {
      return this.getWLJMSContext().getTotalReconnectPeriodMillis();
   }

   public void setClientID(String clientID, String clientIDPolicy) throws IllegalArgumentException {
      this.getWLJMSContext().setClientID(clientID, clientIDPolicy);
   }

   public String getClientIDPolicy() {
      return this.getWLJMSContext().getClientIDPolicy();
   }

   public String getSubscriptionSharingPolicy() {
      return this.getWLJMSContext().getSubscriptionSharingPolicy();
   }

   public void setSubscriptionSharingPolicy(String subscriptionSharingPolicy) throws IllegalArgumentException {
      this.getWLJMSContext().setSubscriptionSharingPolicy(subscriptionSharingPolicy);
   }

   public XMLMessage createXMLMessage() {
      return this.getWLJMSContext().createXMLMessage();
   }

   public XMLMessage createXMLMessage(String xml) {
      return this.getWLJMSContext().createXMLMessage(xml);
   }

   public XMLMessage createXMLMessage(Document doc) {
      return this.getWLJMSContext().createXMLMessage(doc);
   }

   public int getMessagesMaximum() {
      return this.getWLJMSContext().getMessagesMaximum();
   }

   public void setMessagesMaximum(int messagesMaximum) {
      this.getWLJMSContext().setMessagesMaximum(messagesMaximum);
   }

   public int getOverrunPolicy() {
      return this.getWLJMSContext().getOverrunPolicy();
   }

   public void setOverrunPolicy(int policy) {
      this.getWLJMSContext().setOverrunPolicy(policy);
   }

   public long getRedeliveryDelay() {
      return this.getWLJMSContext().getRedeliveryDelay();
   }

   public void setRedeliveryDelay(long redeliveryDelay) {
      this.getWLJMSContext().setRedeliveryDelay(redeliveryDelay);
   }

   public void acknowledge(Message message) {
      throw this.getUnsupportedException("acknowledge(Message message)");
   }

   public void unsubscribe(Topic topic, String name) {
      this.getWLJMSContext().unsubscribe(topic, name);
   }

   public void setSessionExceptionListener(ExceptionListener exceptionListener) {
      this.getWLJMSContext().setSessionExceptionListener(exceptionListener);
   }
}
