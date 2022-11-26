package weblogic.jms.safclient.jms;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import org.w3c.dom.Document;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.client.ContainerType;
import weblogic.jms.common.WLIllegalStateRuntimeException;
import weblogic.jms.common.WLInvalidClientIDRuntimeException;
import weblogic.jms.common.WLJMSRuntimeException;
import weblogic.jms.extensions.WLJMSContext;
import weblogic.jms.extensions.XMLMessage;

public class JMSContextImpl implements JMSContext, WLJMSContext {
   Connection connection;
   Session session;
   MessageProducer messageProducer;
   boolean autoStart = true;
   boolean closed = false;
   String defaultUnitOfOrder = null;
   int defaultCompressionThreshold;
   int defaultRedeliveryLimit;
   long defaultSendTimeOut;
   Set contextSet;
   ContainerType containerType;
   SessionCreator sessionCreator;
   private boolean allowToSetClientID = true;

   protected ContainerType getContainerType() {
      return this.containerType;
   }

   private Session getSession() {
      if (this.session == null) {
         this.session = this.sessionCreator.createSession();
         this.sessionCreator = null;
      }

      return this.session;
   }

   public JMSContextImpl(ConnectionFactory connectionFactory, ContainerType containerType, String userName, String password) {
      this.containerType = containerType;

      try {
         this.connection = connectionFactory.createConnection(userName, password);
      } catch (JMSException var6) {
         throw WLJMSRuntimeException.convertJMSException(var6);
      }

      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return JMSContextImpl.this.connection.createSession();
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForNewConnection();
   }

   public JMSContextImpl(ConnectionFactory connectionFactory, ContainerType containerType) {
      this.containerType = containerType;

      try {
         this.connection = connectionFactory.createConnection();
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }

      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return JMSContextImpl.this.connection.createSession();
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForNewConnection();
   }

   public JMSContextImpl(ConnectionFactory connectionFactory, ContainerType containerType, String userName, String password, final int sessionMode) {
      this.validateSessionMode(sessionMode);
      this.containerType = containerType;

      try {
         this.connection = connectionFactory.createConnection(userName, password);
      } catch (JMSException var7) {
         throw WLJMSRuntimeException.convertJMSException(var7);
      }

      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return JMSContextImpl.this.connection.createSession(sessionMode);
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForNewConnection();
   }

   public JMSContextImpl(ConnectionFactory connectionFactory, ContainerType containerType, final int sessionMode) {
      this.validateSessionMode(sessionMode);
      this.containerType = containerType;

      try {
         this.connection = connectionFactory.createConnection();
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }

      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return JMSContextImpl.this.connection.createSession(sessionMode);
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForNewConnection();
   }

   public JMSContextImpl(ContainerType containerType, Set contextSet, Connection connectionArg, final int sessionMode) {
      this.validateSessionMode(sessionMode);
      this.containerType = containerType;
      this.connection = connectionArg;
      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return JMSContextImpl.this.connection.createSession(sessionMode);
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForExistingConnection(contextSet);
   }

   protected JMSContextImpl(XAConnectionFactory connectionFactory, ContainerType containerType, double unused) {
      this.containerType = containerType;

      try {
         this.connection = connectionFactory.createXAConnection();
      } catch (JMSException var6) {
         throw WLJMSRuntimeException.convertJMSException(var6);
      }

      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return ((XAConnection)JMSContextImpl.this.connection).createXASession();
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForNewConnection();
   }

   protected JMSContextImpl(XAConnectionFactory connectionFactory, ContainerType containerType, String userName, String password, double unused) {
      this.containerType = containerType;

      try {
         this.connection = connectionFactory.createXAConnection(userName, password);
      } catch (JMSException var8) {
         throw WLJMSRuntimeException.convertJMSException(var8);
      }

      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return ((XAConnection)JMSContextImpl.this.connection).createXASession();
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForNewConnection();
   }

   public JMSContextImpl(ContainerType containerType, Set contextSet, Connection connectionArg, double ignored) {
      this.containerType = containerType;
      this.connection = connectionArg;
      this.sessionCreator = new SessionCreator() {
         public Session createSession() {
            try {
               return ((XAConnection)JMSContextImpl.this.connection).createXASession();
            } catch (JMSException var5) {
               JMSRuntimeException newException = WLJMSRuntimeException.convertJMSException(var5);

               try {
                  JMSContextImpl.this.close();
               } catch (JMSRuntimeException var4) {
                  newException.addSuppressed(var4);
               }

               throw newException;
            }
         }
      };
      this.initializeForExistingConnection(contextSet);
   }

   private void validateSessionMode(int sessionMode) {
      if (sessionMode != 1 && sessionMode != 2 && sessionMode != 3 && sessionMode != 0 && sessionMode != 4 && sessionMode != 128) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logInvalidSessionModeLoggable(sessionMode));
      }
   }

   protected void initializeForNewConnection() {
      this.contextSet = new HashSet();
      this.contextSet.add(this);
   }

   private void initializeForExistingConnection(Set existingContextSet) {
      synchronized(existingContextSet) {
         this.contextSet = existingContextSet;
         this.contextSet.add(this);
      }
   }

   public JMSContext createContext(int sessionMode) {
      if (this.containerType == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.checkNotClosed();
         this.disallowSetClientID();
         return new JMSContextImpl(this.containerType, this.contextSet, this.connection, sessionMode);
      }
   }

   public JMSProducer createProducer() {
      this.checkNotClosed();
      this.disallowSetClientID();
      return new JMSProducerImpl(this);
   }

   public String getClientID() {
      this.checkNotClosed();

      try {
         return this.connection.getClientID();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void setClientID(String clientID) {
      if (this.containerType == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.checkNotClosed();
         this.checkSetClientIDAllowed();
         this.checkClientID(clientID);

         try {
            this.connection.setClientID(clientID);
         } catch (JMSException var3) {
            throw WLJMSRuntimeException.convertJMSException(var3);
         }

         this.disallowSetClientID();
      }
   }

   public ConnectionMetaData getMetaData() {
      this.checkNotClosed();

      try {
         return this.connection.getMetaData();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public ExceptionListener getExceptionListener() {
      this.checkNotClosed();

      try {
         return this.connection.getExceptionListener();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void setExceptionListener(ExceptionListener listener) {
      this.checkNotClosed();

      try {
         this.connection.setExceptionListener(listener);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }

      this.disallowSetClientID();
   }

   public void start() {
      this.checkNotClosed();

      try {
         this.connection.start();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void stop() {
      this.checkNotClosed();

      try {
         this.connection.stop();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void setAutoStart(boolean autoStart) {
      this.checkNotClosed();
      this.disallowSetClientID();
      this.autoStart = autoStart;
   }

   public boolean getAutoStart() {
      this.checkNotClosed();
      return this.autoStart;
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         if (this.messageProducer != null) {
            try {
               this.messageProducer.close();
               this.messageProducer = null;
            } catch (JMSException var6) {
               throw WLJMSRuntimeException.convertJMSException(var6);
            }
         }

         if (this.session != null) {
            try {
               this.session.close();
            } catch (JMSException var5) {
               throw WLJMSRuntimeException.convertJMSException(var5);
            }
         }

         synchronized(this.contextSet) {
            this.contextSet.remove(this);
            if (this.contextSet.isEmpty()) {
               try {
                  this.connection.close();
               } catch (JMSException var4) {
                  throw WLJMSRuntimeException.convertJMSException(var4);
               }
            }
         }

         this.closed = true;
      }
   }

   public BytesMessage createBytesMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createBytesMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public MapMessage createMapMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createMapMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public Message createMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public ObjectMessage createObjectMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createObjectMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public ObjectMessage createObjectMessage(Serializable object) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createObjectMessage(object);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public StreamMessage createStreamMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createStreamMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public TextMessage createTextMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createTextMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public TextMessage createTextMessage(String text) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createTextMessage(text);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public boolean getTransacted() {
      this.checkNotClosed();

      try {
         return this.getSession().getTransacted();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public int getSessionMode() {
      this.checkNotClosed();

      try {
         return this.getSession().getAcknowledgeMode();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void commit() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         this.getSession().commit();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void rollback() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         this.getSession().rollback();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void recover() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         this.getSession().recover();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public JMSConsumer createConsumer(Destination destination) {
      throw new WLJMSRuntimeException("No consumer allowed in client SAF implementation");
   }

   public JMSConsumer createConsumer(Destination destination, String messageSelector) {
      throw new WLJMSRuntimeException("No consumer allowed in client SAF implementation");
   }

   public JMSConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) {
      throw new WLJMSRuntimeException("No consumer allowed in client SAF implementation");
   }

   public Queue createQueue(String queueName) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createQueue(queueName);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public Topic createTopic(String topicName) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createTopic(topicName);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public JMSConsumer createDurableConsumer(Topic topic, String name) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public JMSConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public JMSConsumer createSharedDurableConsumer(Topic topic, String name) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public JMSConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public QueueBrowser createBrowser(Queue queue) {
      throw new WLJMSRuntimeException("No browser allowed in the client SAF implementation");
   }

   public QueueBrowser createBrowser(Queue queue, String messageSelector) {
      throw new WLJMSRuntimeException("No browser allowed in the client SAF implementation");
   }

   public TemporaryQueue createTemporaryQueue() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createTemporaryQueue();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public TemporaryTopic createTemporaryTopic() {
      try {
         return this.getSession().createTemporaryTopic();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void unsubscribe(String name) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   private void checkSetClientIDAllowed() {
      if (!this.allowToSetClientID) {
         throw new WLIllegalStateRuntimeException(JMSClientExceptionLogger.logSetClientIDCalledInInvalidStateLoggable());
      } else {
         String existingClientID = null;

         try {
            existingClientID = this.connection.getClientID();
         } catch (JMSException var3) {
            throw WLJMSRuntimeException.convertJMSException(var3);
         }

         if (existingClientID != null) {
            throw new WLIllegalStateRuntimeException(JMSClientExceptionLogger.logSetClientIDCalledInInvalidStateLoggable());
         }
      }
   }

   protected void checkClientID(String clientID) {
      if (clientID == null) {
         throw new WLInvalidClientIDRuntimeException(JMSClientExceptionLogger.logInvalidClientIDNullLoggable());
      } else if (clientID.trim().length() == 0) {
         throw new WLInvalidClientIDRuntimeException(JMSClientExceptionLogger.logInvalidClientIDEmptyStringLoggable());
      }
   }

   protected void disallowSetClientID() {
      this.allowToSetClientID = false;
   }

   protected MessageProducer getMessageProducer() {
      if (this.messageProducer == null) {
         try {
            this.messageProducer = this.getSession().createProducer((Destination)null);
            this.defaultUnitOfOrder = ((MessageProducerImpl)this.messageProducer).getUnitOfOrder();
            this.defaultRedeliveryLimit = ((MessageProducerImpl)this.messageProducer).getRedeliveryLimit();
            this.defaultCompressionThreshold = ((MessageProducerImpl)this.messageProducer).getCompressionThreshold();
            this.defaultSendTimeOut = ((MessageProducerImpl)this.messageProducer).getSendTimeout();
         } catch (JMSException var2) {
            throw WLJMSRuntimeException.convertJMSException(var2);
         }
      }

      return this.messageProducer;
   }

   protected String getDefaultUnitOfOrder() {
      this.getMessageProducer();
      return this.defaultUnitOfOrder;
   }

   protected int getDefaultRedeliveryLimit() {
      this.getMessageProducer();
      return this.defaultRedeliveryLimit;
   }

   protected int getDefaultCompressionThreshold() {
      this.getMessageProducer();
      return this.defaultCompressionThreshold;
   }

   protected long getSendTimeout() {
      this.getMessageProducer();
      return this.defaultSendTimeOut;
   }

   protected void checkNotClosed() {
      if (this.closed) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logJMSContextIsClosedLoggable());
      }
   }

   protected Session _getSession() {
      return this.getSession();
   }

   protected Connection _getConnection() {
      return this.connection;
   }

   public void setReconnectPolicy(String reconnectPolicy) throws IllegalArgumentException {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public String getReconnectPolicy() {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public void setReconnectBlockingMillis(long timeout) throws IllegalArgumentException {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public long getReconnectBlockingMillis() {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public void setTotalReconnectPeriodMillis(long timeout) throws IllegalArgumentException {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public long getTotalReconnectPeriodMillis() {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public void setClientID(String clientID, String clientIDPolicy) throws IllegalArgumentException {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public String getClientIDPolicy() {
      throw new WLJMSRuntimeException("feature not supported for SAF client");
   }

   public String getSubscriptionSharingPolicy() {
      throw new WLJMSRuntimeException("subscription not supported for SAF client");
   }

   public void setSubscriptionSharingPolicy(String subscriptionSharingPolicy) throws IllegalArgumentException {
      throw new WLJMSRuntimeException("subscription not supported for SAF client");
   }

   public XMLMessage createXMLMessage() {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return ((SessionImpl)this.getSession()).createXMLMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public XMLMessage createXMLMessage(String xml) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return ((SessionImpl)this.getSession()).createXMLMessage(xml);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public XMLMessage createXMLMessage(Document doc) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return ((SessionImpl)this.getSession()).createXMLMessage(doc);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public void setSessionExceptionListener(ExceptionListener exceptionListener) {
      throw new WLJMSRuntimeException("No supported for SAF client");
   }

   public int getMessagesMaximum() {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public void setMessagesMaximum(int messagesMaximum) {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public int getOverrunPolicy() {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public void setOverrunPolicy(int policy) {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public long getRedeliveryDelay() {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public void setRedeliveryDelay(long redeliveryDelay) {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public void acknowledge(Message message) {
      throw new WLJMSRuntimeException("No consumers allowed in client SAF");
   }

   public void unsubscribe(Topic topic, String name) {
      throw new WLJMSRuntimeException("No subscriptions allowed in the client SAF implementation");
   }

   public void acknowledge() {
      throw new WLJMSRuntimeException("No consumers allowed in the client SAF implementation");
   }

   private interface SessionCreator {
      Session createSession();
   }
}
