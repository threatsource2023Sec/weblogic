package weblogic.jms.client;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateRuntimeException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
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
import weblogic.jms.common.WLIllegalStateRuntimeException;
import weblogic.jms.common.WLInvalidClientIDRuntimeException;
import weblogic.jms.common.WLInvalidDestinationRuntimeException;
import weblogic.jms.common.WLJMSRuntimeException;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.WLSession;
import weblogic.jms.extensions.XMLMessage;
import weblogic.logging.Loggable;

public class JMSContextImpl implements javax.jms.JMSContext, JMSContextInternal {
   Connection connection;
   Session session;
   MessageProducer messageProducer;
   boolean autoStart = true;
   boolean closed = false;
   String defaultUnitOfOrder = null;
   int defaultCompressionThreshold;
   int defaultRedeliveryLimit;
   Set contextSet;
   Set consumers = new HashSet();
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
         Loggable loggable = JMSClientExceptionLogger.logInvalidSessionModeLoggable(sessionMode);
         throw new JMSRuntimeException(loggable.getMessage(), loggable.getId());
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

   public javax.jms.JMSContext createContext(int sessionMode) {
      if (this.containerType == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.checkNotClosed();
         this.disallowSetClientID();
         return new JMSContextImpl(this.containerType, this.contextSet, this.connection, sessionMode);
      }
   }

   public javax.jms.JMSProducer createProducer() {
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
      this.disallowSetClientID();

      try {
         this.connection.start();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void stop() {
      this.checkNotClosed();
      if (!((WLSessionImpl)this.getSession()).getPhysicalJMSSession().isOperationAllowed()) {
         throw new IllegalStateRuntimeException(JMSClientExceptionLogger.logInvalidCloseFromListenerLoggable("stop", "JMSContext").getMessage());
      } else {
         this.disallowSetClientID();

         try {
            this.connection.stop();
         } catch (JMSException var2) {
            throw WLJMSRuntimeException.convertJMSException(var2);
         }
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
      javax.jms.JMSConsumer[] consumersArray;
      synchronized(this.consumers) {
         if (this.closed) {
            return;
         }

         if (!((WLSessionImpl)this.getSession()).getPhysicalJMSSession().isOperationAllowed()) {
            throw new IllegalStateRuntimeException(JMSClientExceptionLogger.logInvalidCloseFromListenerLoggable("close", "JMSContext").getMessage());
         }

         this.closed = true;
         consumersArray = (javax.jms.JMSConsumer[])this.consumers.toArray(new javax.jms.JMSConsumer[this.consumers.size()]);
      }

      for(int i = 0; i < consumersArray.length; ++i) {
         consumersArray[i].close();
      }

      if (this.messageProducer != null) {
         try {
            this.messageProducer.close();
            this.messageProducer = null;
         } catch (JMSException var8) {
            throw WLJMSRuntimeException.convertJMSException(var8);
         }
      }

      if (this.session != null) {
         try {
            this.session.close();
         } catch (JMSException var7) {
            throw WLJMSRuntimeException.convertJMSException(var7);
         }
      }

      synchronized(this.contextSet) {
         this.contextSet.remove(this);
         if (this.contextSet.isEmpty()) {
            try {
               this.connection.close();
            } catch (JMSException var5) {
               throw WLJMSRuntimeException.convertJMSException(var5);
            }
         }

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

   public javax.jms.JMSConsumer createConsumer(Destination destination) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseConsumer(this, destination);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createConsumer(Destination destination, String messageSelector) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseConsumer(this, destination, messageSelector);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseConsumer(this, destination, messageSelector, noLocal);
      this.addConsumer(consumer);
      return consumer;
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

   public javax.jms.JMSConsumer createDurableConsumer(Topic topic, String name) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseDurableConsumer(this, topic, name);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseDurableConsumer(this, topic, name, messageSelector, noLocal);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createSharedDurableConsumer(Topic topic, String name) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseSharedDurableConsumer(this, topic, name);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseSharedDurableConsumer(this, topic, name, messageSelector);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseSharedConsumer(this, topic, sharedSubscriptionName);
      this.addConsumer(consumer);
      return consumer;
   }

   public javax.jms.JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) {
      this.checkNotClosed();
      this.disallowSetClientID();
      JMSConsumerImpl consumer = new JMSConsumerImpl();
      consumer.initialiseSharedConsumer(this, topic, sharedSubscriptionName, messageSelector);
      this.addConsumer(consumer);
      return consumer;
   }

   public QueueBrowser createBrowser(Queue queue) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createBrowser(queue);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public QueueBrowser createBrowser(Queue queue, String messageSelector) {
      this.checkNotClosed();
      this.disallowSetClientID();

      try {
         return this.getSession().createBrowser(queue, messageSelector);
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
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
      this.checkNotClosed();

      try {
         this.getSession().unsubscribe(name);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public void acknowledge() {
      if (this.closed) {
         throw new WLIllegalStateRuntimeException(JMSClientExceptionLogger.logJMSContextIsClosedLoggable());
      } else {
         try {
            if (this.getSession().getAcknowledgeMode() != 2) {
               return;
            }
         } catch (JMSException var3) {
            throw WLJMSRuntimeException.convertJMSException(var3);
         }

         this.disallowSetClientID();
         if (this.getSession() instanceof WLSession) {
            try {
               ((WLSession)this.getSession()).acknowledge();
            } catch (JMSException var2) {
               throw WLJMSRuntimeException.convertJMSException(var2);
            }
         } else {
            throw new IllegalStateRuntimeException("Session implementation " + this.getSession().getClass() + " not yet supported");
         }
      }
   }

   private void addConsumer(javax.jms.JMSConsumer consumer) {
      boolean added = false;
      synchronized(this.consumers) {
         if (!this.closed) {
            this.consumers.add(consumer);
            added = true;
         }
      }

      if (!added) {
         try {
            consumer.close();
         } catch (Exception var5) {
         }

         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logJMSContextIsClosedLoggable());
      }
   }

   protected void removeConsumer(javax.jms.JMSConsumer consumer) {
      synchronized(this.consumers) {
         this.consumers.remove(consumer);
      }
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
            this.defaultUnitOfOrder = ((WLMessageProducer)this.messageProducer).getUnitOfOrder();
            this.defaultRedeliveryLimit = ((WLMessageProducer)this.messageProducer).getRedeliveryLimit();
            this.defaultCompressionThreshold = ((WLMessageProducer)this.messageProducer).getCompressionThreshold();
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

   public boolean getUserTransactionsEnabled() {
      return ((ConnectionInternal)this.connection).getUserTransactionsEnabled();
   }

   public boolean isXAServerEnabled() {
      return ((ConnectionInternal)this.connection).isXAServerEnabled();
   }

   public void setReconnectPolicy(String reconnectPolicy) throws IllegalArgumentException {
      ((WLConnection)this.connection).setReconnectPolicy(reconnectPolicy);
   }

   public String getReconnectPolicy() {
      return ((WLConnection)this.connection).getReconnectPolicy();
   }

   public void setReconnectBlockingMillis(long timeout) throws IllegalArgumentException {
      ((WLConnection)this.connection).setReconnectBlockingMillis(timeout);
   }

   public long getReconnectBlockingMillis() {
      return ((WLConnection)this.connection).getReconnectBlockingMillis();
   }

   public void setTotalReconnectPeriodMillis(long timeout) throws IllegalArgumentException {
      ((WLConnection)this.connection).setTotalReconnectPeriodMillis(timeout);
   }

   public long getTotalReconnectPeriodMillis() {
      return ((WLConnection)this.connection).getTotalReconnectPeriodMillis();
   }

   public void setClientID(String clientID, String clientIDPolicy) throws IllegalArgumentException {
      try {
         ((WLConnection)this.connection).setClientID(clientID, clientIDPolicy);
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public String getClientIDPolicy() {
      return ((WLConnection)this.connection).getClientIDPolicy();
   }

   public String getSubscriptionSharingPolicy() {
      return ((WLConnection)this.connection).getSubscriptionSharingPolicy();
   }

   public void setSubscriptionSharingPolicy(String subscriptionSharingPolicy) throws IllegalArgumentException {
      try {
         ((WLConnection)this.connection).setSubscriptionSharingPolicy(subscriptionSharingPolicy);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public XMLMessage createXMLMessage() {
      try {
         return ((WLSession)this.getSession()).createXMLMessage();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public XMLMessage createXMLMessage(String xml) {
      try {
         return ((WLSession)this.getSession()).createXMLMessage(xml);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public XMLMessage createXMLMessage(Document doc) {
      try {
         return ((WLSession)this.getSession()).createXMLMessage(doc);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public int getMessagesMaximum() {
      try {
         return ((WLSession)this.getSession()).getMessagesMaximum();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void setMessagesMaximum(int messagesMaximum) {
      try {
         ((WLSession)this.getSession()).setMessagesMaximum(messagesMaximum);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public int getOverrunPolicy() {
      try {
         return ((WLSession)this.getSession()).getOverrunPolicy();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void setOverrunPolicy(int policy) {
      try {
         ((WLSession)this.getSession()).setOverrunPolicy(policy);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public long getRedeliveryDelay() {
      try {
         return ((WLSession)this.getSession()).getRedeliveryDelay();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public void setRedeliveryDelay(long redeliveryDelay) {
      try {
         ((WLSession)this.getSession()).setRedeliveryDelay(redeliveryDelay);
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public void acknowledge(Message message) {
      try {
         ((WLSession)this.getSession()).acknowledge(message);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public void unsubscribe(Topic topic, String name) {
      try {
         ((WLSession)this.getSession()).unsubscribe(topic, name);
      } catch (InvalidDestinationException var4) {
         throw new WLInvalidDestinationRuntimeException(var4);
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }
   }

   public void setSessionExceptionListener(ExceptionListener exceptionListener) {
      try {
         ((WLSession)this.getSession()).setExceptionListener(exceptionListener);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public void markAsJMSSessionPooledInWrapper() {
      ((WLConnectionImpl)this.connection).markAsJMSSessionPooledInWrapper();
   }

   private interface SessionCreator {
      Session createSession();
   }
}
