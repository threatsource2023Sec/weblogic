package weblogic.jms.client;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSubscriber;
import javax.jms.TransactionRolledBackException;
import javax.transaction.xa.XAResource;
import org.w3c.dom.Document;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.WLAcknowledgeInfo;
import weblogic.jms.extensions.WLAsyncSession;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.XMLMessage;
import weblogic.messaging.dispatcher.CompletionListener;

public class WLSessionImpl extends ReconnectController implements SessionInternal, WLAsyncSession {
   private WLConnectionImpl parent;

   public WLSessionImpl(JMSSession producer, WLConnectionImpl recConnection) {
      super(recConnection, producer);
      this.parent = recConnection;
   }

   protected ReconnectController getParent() {
      return this.parent;
   }

   protected Object getConnectionStateLock() {
      return this.parent.getConnectionStateLock();
   }

   protected WLConnectionImpl getWLConnectionImpl() {
      return this.parent.getWLConnectionImpl();
   }

   protected JMSConnection getPhysicalJMSConnection() {
      return this.parent.getPhysicalJMSConnection();
   }

   protected JMSSession getJMSSessionWaitForState() {
      return (JMSSession)this.getPhysicalWaitForState();
   }

   protected JMSSession getPhysicalJMSSession() {
      return (JMSSession)this.getPhysical();
   }

   public void acknowledge() throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.throwForAckRefreshedSessionRules();
      physicalJMSSession.acknowledge();
   }

   public void commit() throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      if (physicalJMSSession.checkRefreshedWithPendingWork()) {
         throw new TransactionRolledBackException(JMSClientExceptionLogger.logLostServerConnectionLoggable().getMessage(), "ReservedRollbackOnly");
      } else {
         physicalJMSSession.commit();
      }
   }

   public void recover() throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.checkRefreshedWithPendingWork();
      physicalJMSSession.recover();
   }

   public void rollback() throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.checkRefreshedWithPendingWork();
      physicalJMSSession.rollback();
   }

   public void run() {
      this.getPhysicalJMSSession().run();
   }

   public void acknowledge(Message message) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.throwForAckRefreshedSessionRules();
      physicalJMSSession.acknowledge(message);
   }

   public void acknowledge(WLAcknowledgeInfo ackInfo) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.throwForAckRefreshedSessionRules();
      physicalJMSSession.acknowledge(ackInfo);
   }

   public long getLastSequenceNumber() {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      return physicalJMSSession.getLastSequenceNumber();
   }

   public void setMMessageListener(MMessageListener listener) {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.setMMessageListener(listener);
   }

   public void close(long sequenceNumber) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.close(sequenceNumber);
   }

   public void commit(long sequenceNumber) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.commit(sequenceNumber);
   }

   public int rollback(long sequenceNumber) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.rollback(sequenceNumber);
      return physicalJMSSession.getPipelineGenerationFromProxy();
   }

   public int recover(long sequenceNumber) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.recover(sequenceNumber);
      return physicalJMSSession.getPipelineGenerationFromProxy();
   }

   public void removePendingWTMessage(long sequenceNumber) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.removePendingWTMessage(sequenceNumber, true);
   }

   public int getPipelineGenerationFromProxy() throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      return physicalJMSSession.getPipelineGenerationFromProxy();
   }

   public void acknowledge(Message message, int acknowledgePolicy, boolean isInfectedAck) throws JMSException {
      JMSSession physicalJMSSession = this.getPhysicalJMSSession();
      physicalJMSSession.throwForAckRefreshedSessionRules();
      physicalJMSSession.acknowledge((MessageImpl)message, acknowledgePolicy, isInfectedAck);
   }

   public void pushMessage(MessageImpl message, JMSPushEntry pushEntry) {
      this.getPhysicalJMSSession().pushMessage(message, pushEntry);
   }

   public void checkSAFClosed() throws JMSException {
      this.getPhysicalJMSSession().checkSAFClosed();
   }

   public int consumersCount() {
      return this.getJMSSessionWaitForState().consumersCount();
   }

   public int producersCount() {
      return this.getJMSSessionWaitForState().producersCount();
   }

   public String getWLSServerName() {
      return this.getJMSSessionWaitForState().getWLSServerName();
   }

   public String getRuntimeMBeanName() {
      return this.getJMSSessionWaitForState().getRuntimeMBeanName();
   }

   public ClientRuntimeInfo getParentInfo() {
      return this.getJMSSessionWaitForState().getParentInfo();
   }

   public String getPartitionName() {
      return this.getPhysicalJMSConnection().getPartitionName();
   }

   public JMSID getJMSID() {
      return this.getJMSSessionWaitForState().getJMSID();
   }

   public void setPipelineGeneration(int pipelineGeneration) {
      this.getJMSSessionWaitForState().setPipelineGeneration(pipelineGeneration);
   }

   public XMLMessage createXMLMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createXMLMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createXMLMessage();
      }
   }

   public XMLMessage createXMLMessage(String xml) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createXMLMessage(xml);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createXMLMessage(xml);
      }
   }

   public XMLMessage createXMLMessage(Document doc) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createXMLMessage(doc);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createXMLMessage(doc);
      }
   }

   public void setExceptionListener(ExceptionListener exceptionListener) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.setExceptionListener(exceptionListener);
      } catch (weblogic.jms.common.JMSException var6) {
         this.computeJMSSession(startTime, physical, var6).setExceptionListener(exceptionListener);
      }

   }

   public int getMessagesMaximum() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.getMessagesMaximum();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).getMessagesMaximum();
      }
   }

   public void setMessagesMaximum(int messagesMaximum) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.setMessagesMaximum(messagesMaximum);
      } catch (weblogic.jms.common.JMSException var6) {
         this.computeJMSSession(startTime, physical, var6).setMessagesMaximum(messagesMaximum);
      }

   }

   public int getOverrunPolicy() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.getOverrunPolicy();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).getOverrunPolicy();
      }
   }

   public void setOverrunPolicy(int policy) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.setOverrunPolicy(policy);
      } catch (weblogic.jms.common.JMSException var6) {
         this.computeJMSSession(startTime, physical, var6).setOverrunPolicy(policy);
      }

   }

   public long getRedeliveryDelay() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.getRedeliveryDelay();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).getRedeliveryDelay();
      }
   }

   public void setRedeliveryDelay(long redeliveryDelay) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.setRedeliveryDelay(redeliveryDelay);
      } catch (weblogic.jms.common.JMSException var7) {
         this.computeJMSSession(startTime, physical, var7).setRedeliveryDelay(redeliveryDelay);
      }

   }

   public BytesMessage createBytesMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createBytesMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createBytesMessage();
      }
   }

   public MapMessage createMapMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createMapMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createMapMessage();
      }
   }

   public Message createMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createMessage();
      }
   }

   public ObjectMessage createObjectMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createObjectMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createObjectMessage();
      }
   }

   public ObjectMessage createObjectMessage(Serializable serializable) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createObjectMessage(serializable);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createObjectMessage(serializable);
      }
   }

   public StreamMessage createStreamMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createStreamMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createStreamMessage();
      }
   }

   public TextMessage createTextMessage() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createTextMessage();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createTextMessage();
      }
   }

   public TextMessage createTextMessage(String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createTextMessage(uoo);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createTextMessage(uoo);
      }
   }

   public boolean getTransacted() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.getTransacted();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).getTransacted();
      }
   }

   public int getAcknowledgeMode() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.getAcknowledgeMode();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).getAcknowledgeMode();
      }
   }

   public MessageListener getMessageListener() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.getMessageListener();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).getMessageListener();
      }
   }

   public void setMessageListener(MessageListener messageListener) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.setMessageListener(messageListener);
      } catch (weblogic.jms.common.JMSException var6) {
         this.computeJMSSession(startTime, physical, var6).setMessageListener(messageListener);
      }

   }

   public MessageProducer createProducer(Destination destination) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createProducer(destination);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createProducer(destination);
      }
   }

   public MessageConsumer createConsumer(Destination destination) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createConsumer(destination);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createConsumer(destination);
      }
   }

   public MessageConsumer createConsumer(Destination destination, String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createConsumer(destination, uoo);
      } catch (weblogic.jms.common.JMSException var7) {
         return this.computeJMSSession(startTime, physical, var7).createConsumer(destination, uoo);
      }
   }

   public MessageConsumer createConsumer(Destination destination, String uoo, boolean b) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createConsumer(destination, uoo, b);
      } catch (weblogic.jms.common.JMSException var8) {
         return this.computeJMSSession(startTime, physical, var8).createConsumer(destination, uoo, b);
      }
   }

   public Queue createQueue(String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createQueue(uoo);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createQueue(uoo);
      }
   }

   public QueueReceiver createReceiver(Queue queue) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createReceiver(queue);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createReceiver(queue);
      }
   }

   public QueueReceiver createReceiver(Queue queue, String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createReceiver(queue, uoo);
      } catch (weblogic.jms.common.JMSException var7) {
         return this.computeJMSSession(startTime, physical, var7).createReceiver(queue, uoo);
      }
   }

   public QueueSender createSender(Queue queue) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSender(queue);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createSender(queue);
      }
   }

   public QueueBrowser createBrowser(Queue queue) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createBrowser(queue);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createBrowser(queue);
      }
   }

   public QueueBrowser createBrowser(Queue queue, String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createBrowser(queue, uoo);
      } catch (weblogic.jms.common.JMSException var7) {
         return this.computeJMSSession(startTime, physical, var7).createBrowser(queue, uoo);
      }
   }

   public TemporaryQueue createTemporaryQueue() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createTemporaryQueue();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createTemporaryQueue();
      }
   }

   public Topic createTopic(String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createTopic(uoo);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createTopic(uoo);
      }
   }

   public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSubscriber(topic);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createSubscriber(topic);
      }
   }

   public TopicSubscriber createSubscriber(Topic topic, String uoo, boolean b) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSubscriber(topic, uoo, b);
      } catch (weblogic.jms.common.JMSException var8) {
         return this.computeJMSSession(startTime, physical, var8).createSubscriber(topic, uoo, b);
      }
   }

   public TopicSubscriber createDurableSubscriber(Topic topic, String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createDurableSubscriber(topic, uoo);
      } catch (weblogic.jms.common.JMSException var7) {
         return this.computeJMSSession(startTime, physical, var7).createDurableSubscriber(topic, uoo);
      }
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name) throws JMSException {
      return this.createDurableSubscriber(topic, name);
   }

   public TopicSubscriber createDurableSubscriber(Topic topic, String uoo, String uoo1, boolean b) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createDurableSubscriber(topic, uoo, uoo1, b);
      } catch (weblogic.jms.common.JMSException var9) {
         return this.computeJMSSession(startTime, physical, var9).createDurableSubscriber(topic, uoo, uoo1, b);
      }
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
      return this.createDurableSubscriber(topic, name, messageSelector, noLocal);
   }

   public TopicPublisher createPublisher(Topic topic) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createPublisher(topic);
      } catch (weblogic.jms.common.JMSException var6) {
         return this.computeJMSSession(startTime, physical, var6).createPublisher(topic);
      }
   }

   public TemporaryTopic createTemporaryTopic() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createTemporaryTopic();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.computeJMSSession(startTime, physical, var5).createTemporaryTopic();
      }
   }

   public void unsubscribe(String uoo) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.unsubscribe(uoo);
      } catch (weblogic.jms.common.JMSException var6) {
         this.computeJMSSession(startTime, physical, var6).unsubscribe(uoo);
      }

   }

   public void unsubscribe(Topic topic, String name) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.unsubscribe(topic, name);
      } catch (weblogic.jms.common.JMSException var7) {
         this.computeJMSSession(startTime, physical, var7).unsubscribe(topic, name);
      }

   }

   public void associateTransaction(Message message) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         physical.associateTransaction(message);
      } catch (weblogic.jms.common.JMSException var6) {
         this.computeJMSSession(startTime, physical, var6).associateTransaction(message);
      }

   }

   public void acknowledgeAsync(WLAcknowledgeInfo ackInfo, CompletionListener listener) {
      this.getPhysicalJMSSession().acknowledgeAsync(ackInfo, listener);
   }

   public void sendAsync(MessageProducer producer, Message message, CompletionListener listener) {
      ((WLProducerImpl)producer).sendAsync(message, listener);
   }

   public void sendAsync(WLMessageProducer producer, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      ((WLProducerImpl)producer).sendAsync(message, deliveryMode, priority, timeToLive, listener);
   }

   public void sendAsync(WLMessageProducer producer, Destination destination, Message message, CompletionListener listener) {
      ((WLProducerImpl)producer).sendAsync(destination, message, listener);
   }

   public void sendAsync(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      ((WLProducerImpl)producer).sendAsync(destination, message, deliveryMode, priority, timeToLive, listener);
   }

   public void receiveAsync(MessageConsumer consumer, CompletionListener listener) {
      ((WLConsumerImpl)consumer).receiveAsync(listener);
   }

   public void receiveAsync(MessageConsumer consumer, long timeout, CompletionListener listener) {
      ((WLConsumerImpl)consumer).receiveAsync(timeout, listener);
   }

   public void receiveNoWaitAsync(MessageConsumer consumer, CompletionListener listener) {
      ((WLConsumerImpl)consumer).receiveNoWaitAsync(listener);
   }

   public XAResource getXAResource(String serverName) {
      return this.getPhysicalJMSSession().getXAResource(serverName);
   }

   public MessageConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSharedConsumer(topic, sharedSubscriptionName);
      } catch (weblogic.jms.common.JMSException var7) {
         return this.computeJMSSession(startTime, physical, var7).createSharedConsumer(topic, sharedSubscriptionName);
      }
   }

   public MessageConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSharedConsumer(topic, sharedSubscriptionName, messageSelector);
      } catch (weblogic.jms.common.JMSException var8) {
         return this.computeJMSSession(startTime, physical, var8).createSharedConsumer(topic, sharedSubscriptionName, messageSelector);
      }
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSharedDurableConsumer(topic, name);
      } catch (weblogic.jms.common.JMSException var7) {
         return this.computeJMSSession(startTime, physical, var7).createSharedDurableConsumer(topic, name);
      }
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSSession physical = this.getPhysicalJMSSession();

      try {
         return physical.createSharedDurableConsumer(topic, name, messageSelector);
      } catch (weblogic.jms.common.JMSException var8) {
         return this.computeJMSSession(startTime, physical, var8).createSharedDurableConsumer(topic, name, messageSelector);
      }
   }
}
