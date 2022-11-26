package weblogic.jms.safclient.jms;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import org.w3c.dom.Document;
import weblogic.jms.extensions.JMSMessageFactoryImpl;
import weblogic.jms.extensions.WLAcknowledgeInfo;
import weblogic.jms.extensions.WLMessageFactory;
import weblogic.jms.extensions.WLQueueSession;
import weblogic.jms.extensions.WLTopicSession;
import weblogic.jms.extensions.XMLMessage;
import weblogic.jms.safclient.ClientSAFDelegate;
import weblogic.jms.safclient.agent.AgentManager;
import weblogic.jms.safclient.agent.DestinationImpl;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;

public final class SessionImpl implements WLQueueSession, WLTopicSession {
   private static final WLMessageFactory MESSAGEFACTORY = JMSMessageFactoryImpl.getFactory();
   private int id;
   private ConnectionImpl connection;
   private boolean closed = false;
   private ExceptionListener exceptionListener;
   private boolean transacted;
   private int acknowledgeMode;
   private int currentID;
   private HashMap producers = new HashMap();
   private boolean inTx = false;
   private Transaction transaction;

   public SessionImpl(ConnectionImpl paramConnection, int paramID, boolean paramTransacted, int paramAcknowledgeMode) {
      this.connection = paramConnection;
      this.id = paramID;
      this.transacted = paramTransacted;
      this.acknowledgeMode = paramAcknowledgeMode;
   }

   public void setExceptionListener(ExceptionListener paramExceptionListener) {
      this.exceptionListener = paramExceptionListener;
   }

   public int getMessagesMaximum() throws JMSException {
      throw new JMSException("No consumers allowed in client SAF");
   }

   public void setMessagesMaximum(int paramMessagesMaximum) throws JMSException {
      throw new JMSException("No consumers allowed in client SAF");
   }

   public int getOverrunPolicy() throws JMSException {
      throw new JMSException("No consumers allowed in client SAF");
   }

   public void setOverrunPolicy(int policy) throws JMSException {
      throw new JMSException("No consumers allowed in client SAF");
   }

   public long getRedeliveryDelay() throws JMSException {
      throw new JMSException("No consumers allowed in client SAF");
   }

   public void setRedeliveryDelay(long paramRedeliveryDelay) throws JMSException {
      throw new JMSException("No consumers allowed in client SAF");
   }

   public void acknowledge() throws JMSException {
      throw new JMSException("No consumers allowed in the client SAF implementation");
   }

   public void acknowledge(Message message) throws JMSException {
      throw new JMSException("No consumers allowed in the client SAF implementation");
   }

   public void acknowledge(WLAcknowledgeInfo info) throws JMSException {
      throw new JMSException("No consumers allowed in the client SAF implementation");
   }

   public Topic createTopic(String s) throws JMSException {
      DestinationImpl retVal = this.createDestination(s);
      if (!retVal.isTopic()) {
         throw new JMSException(s + " is a queue");
      } else {
         return retVal;
      }
   }

   private DestinationImpl createDestination(String s) throws JMSException {
      this.checkClosed();
      StringTokenizer tokenizer = new StringTokenizer(s, "!");
      if (tokenizer.countTokens() != 2) {
         throw new JMSException("Invalid format for createDestination.  Must beSAFImportedDestinationsName + '!' + DestinationName");
      } else {
         String groupName = tokenizer.nextToken();
         String destinationName = tokenizer.nextToken();
         ClientSAFDelegate safImpl = this.connection.getRoot();
         AgentManager agentManager = safImpl.getAgentManager();
         return agentManager.getDestination(groupName, destinationName);
      }
   }

   public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
      throw new JMSException("No subscriptions allowed in the client SAF implementation");
   }

   public TopicSubscriber createSubscriber(Topic topic, String s, boolean b) throws JMSException {
      throw new JMSException("No subscriptions allowed in the client SAF implementation");
   }

   public TopicSubscriber createDurableSubscriber(Topic topic, String s) throws JMSException {
      throw new JMSException("No subscriptions allowed in the client SAF implementation");
   }

   public TopicSubscriber createDurableSubscriber(Topic topic, String s, String s1, boolean b) throws JMSException {
      throw new JMSException("No subscriptions allowed in the client SAF implementation");
   }

   public TopicPublisher createPublisher(Topic topic) throws JMSException {
      return (TopicPublisher)this.createProducer(topic);
   }

   public TemporaryTopic createTemporaryTopic() throws JMSException {
      throw new JMSException("No temporary destination allowed in the client SAF implementation");
   }

   public void unsubscribe(String s) throws JMSException {
      throw new JMSException("No subscriptions allowed in the client SAF implementation");
   }

   public void unsubscribe(Topic topic, String name) throws JMSException {
      throw new JMSException("No subscriptions allowed in the client SAF implementation");
   }

   public Queue createQueue(String s) throws JMSException {
      DestinationImpl retVal = this.createDestination(s);
      if (!retVal.isQueue()) {
         throw new JMSException(s + " is a topic");
      } else {
         return retVal;
      }
   }

   public QueueReceiver createReceiver(Queue queue) throws JMSException {
      throw new JMSException("No receiver allowed in the client SAF implementation");
   }

   public QueueReceiver createReceiver(Queue queue, String s) throws JMSException {
      throw new JMSException("No receiver allowed in the client SAF implementation");
   }

   public QueueSender createSender(Queue queue) throws JMSException {
      return (QueueSender)this.createProducer(queue);
   }

   public QueueBrowser createBrowser(Queue queue) throws JMSException {
      throw new JMSException("No browser allowed in the client SAF implementation");
   }

   public QueueBrowser createBrowser(Queue queue, String s) throws JMSException {
      throw new JMSException("No browser allowed in the client SAF implementation");
   }

   public TemporaryQueue createTemporaryQueue() throws JMSException {
      throw new JMSException("No temporary destination allowed in the client SAF implementation");
   }

   public XMLMessage createXMLMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createXMLMessage();
   }

   public XMLMessage createXMLMessage(String xml) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createXMLMessage(xml);
   }

   public XMLMessage createXMLMessage(Document doc) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createXMLMessage(doc);
   }

   public BytesMessage createBytesMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createBytesMessage();
   }

   public MapMessage createMapMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createMapMessage();
   }

   public Message createMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createMessage();
   }

   public ObjectMessage createObjectMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createObjectMessage();
   }

   public ObjectMessage createObjectMessage(Serializable serializable) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createObjectMessage(serializable);
   }

   public StreamMessage createStreamMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createStreamMessage();
   }

   public TextMessage createTextMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createTextMessage();
   }

   public TextMessage createTextMessage(String s) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createTextMessage(s);
   }

   public boolean getTransacted() throws JMSException {
      this.checkClosed();
      return this.transacted;
   }

   public int getAcknowledgeMode() throws JMSException {
      this.checkClosed();
      return this.acknowledgeMode;
   }

   synchronized void beginOrResume(TransactionHelper helper) throws JMSException {
      try {
         ClientTransactionManager manager;
         if (!this.inTx) {
            manager = helper.getTransactionManager();
            manager.begin();
            this.inTx = true;
         } else if (this.transaction != null) {
            manager = helper.getTransactionManager();
            manager.resume(this.transaction);
            this.transaction = null;
         }

      } catch (NotSupportedException var3) {
         throw new weblogic.jms.common.JMSException(var3);
      } catch (SystemException var4) {
         throw new weblogic.jms.common.JMSException(var4);
      } catch (InvalidTransactionException var5) {
         throw new weblogic.jms.common.JMSException(var5);
      }
   }

   synchronized void suspend(TransactionHelper helper) throws JMSException {
      if (this.inTx) {
         if (this.transaction == null) {
            ClientTransactionManager manager = helper.getTransactionManager();

            try {
               this.transaction = manager.suspend();
            } catch (SystemException var4) {
               throw new weblogic.jms.common.JMSException(var4);
            }
         }
      }
   }

   public synchronized void commit() throws JMSException {
      if (!this.transacted) {
         throw new JMSException("This is not a transacted session");
      } else {
         ClientSAFDelegate delegate = this.getRoot();
         TransactionHelper helper = delegate.getTransactionHelper();
         TransactionHelper.pushTransactionHelper(helper);

         try {
            this.beginOrResume(helper);
            ClientTransactionManager manager = helper.getTransactionManager();

            try {
               manager.commit();
            } catch (RollbackException var11) {
               throw new weblogic.jms.common.JMSException(var11);
            } catch (HeuristicMixedException var12) {
               throw new weblogic.jms.common.JMSException(var12);
            } catch (HeuristicRollbackException var13) {
               throw new weblogic.jms.common.JMSException(var13);
            } catch (SystemException var14) {
               throw new weblogic.jms.common.JMSException(var14);
            }
         } finally {
            this.inTx = false;
            TransactionHelper.popTransactionHelper();
         }

      }
   }

   public synchronized void rollback() throws JMSException {
      if (!this.transacted) {
         throw new JMSException("This is not a transacted session");
      } else {
         ClientSAFDelegate delegate = this.getRoot();
         TransactionHelper helper = delegate.getTransactionHelper();
         TransactionHelper.pushTransactionHelper(helper);

         try {
            this.beginOrResume(helper);
            ClientTransactionManager manager = helper.getTransactionManager();

            try {
               manager.rollback();
            } catch (SystemException var8) {
               throw new weblogic.jms.common.JMSException(var8);
            }
         } finally {
            this.inTx = false;
            TransactionHelper.popTransactionHelper();
         }

      }
   }

   public synchronized void close() throws JMSException {
      if (!this.closed) {
         this.closed = true;
         synchronized(this.producers) {
            Collection collection = this.producers.values();
            Iterator it = collection.iterator();

            while(true) {
               if (!it.hasNext()) {
                  this.producers.clear();
                  break;
               }

               MessageProducer producer = (MessageProducer)it.next();
               producer.close();
            }
         }

         this.connection.sessionClosed(this.id);
      }
   }

   void preClose(JMSException reason) {
      if (this.exceptionListener != null) {
         try {
            this.exceptionListener.onException(reason);
         } catch (Throwable var5) {
            Throwable cause = var5;

            for(int level = 0; cause != null; cause = cause.getCause()) {
               System.out.println("User onException listener threw an exception.  Level " + level++);
               cause.printStackTrace();
            }
         }
      }

   }

   public void recover() throws JMSException {
      throw new JMSException("Not yet implemented");
   }

   public MessageListener getMessageListener() throws JMSException {
      throw new JMSException("No listener allowed in client SAF implementation");
   }

   public void setMessageListener(MessageListener messageListener) throws JMSException {
      throw new JMSException("No listener allowed in client SAF implementation");
   }

   public void run() {
   }

   public MessageProducer createProducer(Destination destination) throws JMSException {
      this.checkClosed();
      if (destination != null && !(destination instanceof DestinationImpl)) {
         throw new JMSException("The destination passed into the client SAF implementation must be from the file context.  This destination is of type " + destination.getClass().getName());
      } else {
         MessageProducerImpl retVal = new MessageProducerImpl(this, this.currentID, (DestinationImpl)destination);
         synchronized(this.producers) {
            this.producers.put(new Integer(this.currentID), retVal);
         }

         ++this.currentID;
         return retVal;
      }
   }

   void closeProducer(int id) {
      Integer key = new Integer(id);
      synchronized(this.producers) {
         this.producers.remove(key);
      }
   }

   public MessageConsumer createConsumer(Destination destination) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public MessageConsumer createConsumer(Destination destination, String s) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public MessageConsumer createConsumer(Destination destination, String s, boolean b) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   private synchronized void checkClosed() throws JMSException {
      if (this.closed) {
         throw new IllegalStateException("The session has been closed");
      }
   }

   String getDefaultTimeToDeliver() {
      return this.connection.getDefaultTimeToDeliver();
   }

   long getSendTimeout() {
      return this.connection.getSendTimeout();
   }

   String getDefaultUnitOfOrder() {
      return this.connection.getDefaultUnitOfOrder();
   }

   int getDefaultCompressionThreshold() {
      return this.connection.getDefaultCompressionThreshold();
   }

   String getDefaultDeliveryMode() {
      return this.connection.getDefaultDeliveryMode();
   }

   int getDefaultPriority() {
      return this.connection.getDefaultPriority();
   }

   long getDefaultTimeToLive() {
      return this.connection.getDefaultTimeToLive();
   }

   boolean getAttachJMSXUserId() {
      return this.connection.getAttachJMSXUserId();
   }

   ClientSAFDelegate getRoot() {
      return this.connection.getRoot();
   }

   public XAResource getXAResource(String serverName) {
      return null;
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name) throws JMSException {
      return this.createDurableSubscriber(topic, name);
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
      return this.createDurableSubscriber(topic, name, messageSelector, noLocal);
   }

   public MessageConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public MessageConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }
}
