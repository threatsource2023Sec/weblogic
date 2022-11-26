package weblogic.jms.integration.injection;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateRuntimeException;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import weblogic.jms.JMSExceptionLogger;

public abstract class ForwardingJMSContext implements JMSContext {
   protected abstract JMSContext delegate();

   public JMSContext createContext(int sessionMode) {
      return this.delegate().createContext(sessionMode);
   }

   public JMSProducer createProducer() {
      return this.delegate().createProducer();
   }

   public String getClientID() {
      return this.delegate().getClientID();
   }

   public void setClientID(String clientID) {
      throw this.getUnsupportedException("setClientID");
   }

   public ConnectionMetaData getMetaData() {
      return this.delegate().getMetaData();
   }

   public ExceptionListener getExceptionListener() {
      return null;
   }

   public void setExceptionListener(ExceptionListener listener) {
      throw this.getUnsupportedException("setExceptionListener");
   }

   public void start() {
      throw this.getUnsupportedException("start");
   }

   public void stop() {
      throw this.getUnsupportedException("stop");
   }

   public void setAutoStart(boolean autoStart) {
      throw this.getUnsupportedException("setAutoStart");
   }

   public boolean getAutoStart() {
      return true;
   }

   public void close() {
      throw this.getUnsupportedException("close");
   }

   public BytesMessage createBytesMessage() {
      return this.delegate().createBytesMessage();
   }

   public MapMessage createMapMessage() {
      return this.delegate().createMapMessage();
   }

   public Message createMessage() {
      return this.delegate().createMessage();
   }

   public ObjectMessage createObjectMessage() {
      return this.delegate().createObjectMessage();
   }

   public ObjectMessage createObjectMessage(Serializable object) {
      return this.delegate().createObjectMessage(object);
   }

   public StreamMessage createStreamMessage() {
      return this.delegate().createStreamMessage();
   }

   public TextMessage createTextMessage() {
      return this.delegate().createTextMessage();
   }

   public TextMessage createTextMessage(String text) {
      return this.delegate().createTextMessage(text);
   }

   public boolean getTransacted() {
      return this.delegate().getTransacted();
   }

   public int getSessionMode() {
      return this.delegate().getSessionMode();
   }

   public void commit() {
      throw this.getUnsupportedException("commit");
   }

   public void rollback() {
      throw this.getUnsupportedException("rollback");
   }

   public void recover() {
      throw this.getUnsupportedException("recover");
   }

   public JMSConsumer createConsumer(Destination destination) {
      return this.delegate().createConsumer(destination);
   }

   public JMSConsumer createConsumer(Destination destination, String messageSelector) {
      return this.delegate().createConsumer(destination, messageSelector);
   }

   public JMSConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) {
      return this.delegate().createConsumer(destination, messageSelector, noLocal);
   }

   public Queue createQueue(String queueName) {
      return this.delegate().createQueue(queueName);
   }

   public Topic createTopic(String topicName) {
      return this.delegate().createTopic(topicName);
   }

   public JMSConsumer createDurableConsumer(Topic topic, String name) {
      return this.delegate().createDurableConsumer(topic, name);
   }

   public JMSConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) {
      return this.delegate().createDurableConsumer(topic, name, messageSelector, noLocal);
   }

   public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) {
      return this.delegate().createSharedConsumer(topic, sharedSubscriptionName);
   }

   public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) {
      return this.delegate().createSharedConsumer(topic, sharedSubscriptionName, messageSelector);
   }

   public JMSConsumer createSharedDurableConsumer(Topic topic, String name) {
      return this.delegate().createSharedDurableConsumer(topic, name);
   }

   public JMSConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) {
      return this.delegate().createSharedDurableConsumer(topic, name, messageSelector);
   }

   public QueueBrowser createBrowser(Queue queue) {
      return this.delegate().createBrowser(queue);
   }

   public QueueBrowser createBrowser(Queue queue, String messageSelector) {
      return this.delegate().createBrowser(queue, messageSelector);
   }

   public TemporaryQueue createTemporaryQueue() {
      return this.delegate().createTemporaryQueue();
   }

   public TemporaryTopic createTemporaryTopic() {
      return this.delegate().createTemporaryTopic();
   }

   public void unsubscribe(String name) {
      this.delegate().unsubscribe(name);
   }

   public void acknowledge() {
      throw this.getUnsupportedException("acknowledge");
   }

   protected IllegalStateRuntimeException getUnsupportedException(String methodName) {
      String message = JMSExceptionLogger.logMethodForbiddenOnInjectedJMSContextLoggable(methodName).getMessage();
      return new IllegalStateRuntimeException(message);
   }
}
