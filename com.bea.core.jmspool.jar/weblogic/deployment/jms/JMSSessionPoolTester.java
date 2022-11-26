package weblogic.deployment.jms;

import java.security.AccessController;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.transaction.SystemException;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.work.WorkAdapter;

class JMSSessionPoolTester extends WorkAdapter implements ExceptionListener, MessageListener, TimerListener {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected static long POLLING_INTERVAL = 120000L;
   protected static final long CLOSE_WAIT = 30000L;
   private JMSSessionPool pool;
   private Connection connection;
   private int sessionType;
   private Session receivingSession;
   private MessageConsumer consumer;
   private Destination tempDest;
   private Timer timer;
   private boolean receivedPendingMessage;
   private boolean closed = true;
   private String pendingMessageID;
   private boolean connectionFailed;
   private JMSException connectionFailure;
   private boolean firstTime = true;
   private int correlationCount;
   private boolean containerAuth;
   private String userName;
   private String password;
   private JMSConnectionHelperService helper = null;
   private Object testerLock = new Object();
   private Object closeLock = new Object();
   private Object timerLock = new Object();

   protected JMSSessionPoolTester(JMSSessionPool pool, JMSConnectionHelperService helper, int sessionType, boolean containerAuth, String userName, String password) {
      this.pool = pool;
      this.connection = helper.getConnection();
      this.sessionType = sessionType;
      this.containerAuth = containerAuth;
      this.userName = userName;
      this.password = password;
      this.helper = helper;
   }

   protected boolean init() throws JMSException {
      try {
         boolean startTrigger = false;
         if (this.sessionType == 2) {
            startTrigger = this.initTopic();
         } else if (this.sessionType == 1) {
            startTrigger = this.initQueue();
         } else if (this.sessionType == 3) {
            startTrigger = this.initConsumer();
         }

         if (!startTrigger) {
            return false;
         }
      } catch (JMSException var2) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Can't initialize JMS session pool tester for pool " + this.pool.getName() + " : " + var2);
         }

         return false;
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("JMSSessionPool " + this.pool.getName() + " using temporary destination " + this.tempDest + " to test the pooled JMS connection");
      }

      this.setClosed(false);
      this.timer = this.pool.getTimerManager().schedule(this, POLLING_INTERVAL, POLLING_INTERVAL);
      return true;
   }

   protected void close() {
      synchronized(this.timerLock) {
         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }
      }

      synchronized(this.closeLock) {
         if (this.isClosed()) {
            return;
         }

         this.setClosed(true);
         Session var2 = this.getReceivingSession();
      }

      this.helper.pushSubject();

      try {
         if (this.receivingSession != null) {
            this.receivingSession.close();
         }
      } catch (JMSException var9) {
      } finally {
         this.helper.popSubject();
      }

   }

   private boolean initQueue() throws JMSException {
      QueueConnection qConnection = (QueueConnection)this.connection;
      QueueSession qSession = null;
      TemporaryQueue tempQueue = null;
      if (this.pool.isConnectionHealthCheckingEnabled()) {
         qSession = qConnection.createQueueSession(false, 1);

         try {
            tempQueue = qSession.createTemporaryQueue();
            QueueReceiver receiver = qSession.createReceiver(tempQueue);
            receiver.setMessageListener(this);
            qConnection.start();
            this.setReceivingSession(qSession);
            this.tempDest = tempQueue;
            this.consumer = receiver;
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Queue Session Pool Tester for poolName=" + this.helper.getPoolName() + " is enabled.");
            }

            return true;
         } catch (Throwable var5) {
            JMSPoolDebug.logger.debug("Can't test JMS connection for pool " + this.helper.getPoolName() + " -- temporary destination can't be created");
         }
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Queue Session Pool Tester for poolName=" + this.helper.getPoolName() + " is disabled.");
      }

      this.setClosed(false);
      this.setReceivingSession((Session)null);
      if (qSession != null) {
         qSession.close();
      }

      qConnection.start();
      return false;
   }

   private boolean initConsumer() throws JMSException {
      Connection con = this.connection;
      Session session = null;
      TemporaryQueue tempQueue = null;
      if (this.pool.isConnectionHealthCheckingEnabled()) {
         session = con.createSession(false, 1);

         try {
            tempQueue = session.createTemporaryQueue();
            MessageConsumer receiver = session.createConsumer(tempQueue);
            receiver.setMessageListener(this);
            con.start();
            this.setReceivingSession(session);
            this.tempDest = tempQueue;
            this.consumer = receiver;
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Consumer Session Pool Tester for poolName=" + this.helper.getPoolName() + " is enabled.");
            }

            return true;
         } catch (Throwable var5) {
            JMSPoolDebug.logger.debug("Can't test JMS connection for pool " + this.helper.getPoolName() + " -- temporary destination can't be created");
         }
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Consumer Session Pool Tester for poolName=" + this.helper.getPoolName() + " is disabled.");
      }

      this.setClosed(false);
      this.setReceivingSession((Session)null);
      if (session != null) {
         session.close();
      }

      con.start();
      return false;
   }

   private boolean initTopic() throws JMSException {
      TopicConnection tConnection = (TopicConnection)this.connection;
      TopicSession tSession = null;
      TemporaryTopic tempTopic = null;
      if (this.pool.isConnectionHealthCheckingEnabled()) {
         tSession = tConnection.createTopicSession(false, 1);

         try {
            tempTopic = tSession.createTemporaryTopic();
            TopicSubscriber subscriber = tSession.createSubscriber(tempTopic, (String)null, false);
            subscriber.setMessageListener(this);
            tConnection.start();
            this.setReceivingSession(tSession);
            this.consumer = subscriber;
            this.tempDest = tempTopic;
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Topic Session Pool Tester for poolName=" + this.helper.getPoolName() + " is enabled.");
            }

            return true;
         } catch (Throwable var5) {
            JMSPoolDebug.logger.debug("Can't test JMS connection for pool " + this.helper.getPoolName() + " -- temporary destination can't be created");
         }
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Topic Session Pool Tester for poolName=" + this.helper.getPoolName() + " is disabled.");
      }

      this.setClosed(false);
      this.setReceivingSession((Session)null);
      if (tSession != null) {
         tSession.close();
      }

      tConnection.start();
      return false;
   }

   public void timerExpired(Timer t) {
      if (!this.isClosed()) {
         synchronized(this.pool) {
            synchronized(this) {
               synchronized(this.testerLock) {
                  if (JMSPoolDebug.logger.isDebugEnabled()) {
                     JMSPoolDebug.logger.debug("JMSSessionPoolTester.timerExpired(timer=@" + (t == null ? "null" : t.hashCode()) + ") for pool " + this.pool.getName());
                  }

                  if (!this.firstTime && !this.receivedPendingMessage) {
                     this.connectionFailed = true;
                  }

                  if (!this.connectionFailed) {
                     try {
                        this.pendingMessageID = "Tester." + this.pool.getName() + "." + this.correlationCount++;
                        this.receivedPendingMessage = false;
                        if (this.sessionType == 2) {
                           this.sendTopicMessage(this.pendingMessageID);
                        } else if (this.sessionType == 1) {
                           this.sendQueueMessage(this.pendingMessageID);
                        } else if (this.sessionType == 3) {
                           this.sendMessage(this.pendingMessageID);
                        }

                        if (JMSPoolDebug.logger.isDebugEnabled()) {
                           JMSPoolDebug.logger.debug("JMSSessionPoolTester for pool " + this.pool.getName() + " sent message " + this.pendingMessageID);
                        }
                     } catch (JMSException var9) {
                        if (JMSPoolDebug.logger.isDebugEnabled()) {
                           JMSPoolDebug.logger.debug("Exception caught  while sending in JMSSessionPoolTester: " + JMSPoolDebug.getWholeJMSException(var9), var9);
                        }

                        this.connectionFailed = true;
                        this.setConnectionFailure(var9);
                     }
                  }

                  if (this.connectionFailed) {
                     this.shutDownConnection();
                  }

                  this.firstTime = false;
               }
            }

         }
      }
   }

   private void setConnectionFailure(JMSException jmse) {
      synchronized(this.closeLock) {
         this.connectionFailure = jmse;
      }
   }

   private JMSException getConnectionFailure() {
      synchronized(this.closeLock) {
         return this.connectionFailure;
      }
   }

   private void setClosed(boolean closed) {
      synchronized(this.closeLock) {
         this.closed = closed;
      }
   }

   public boolean isClosed() {
      synchronized(this.closeLock) {
         return this.closed;
      }
   }

   private Session getReceivingSession() {
      synchronized(this.closeLock) {
         return this.receivingSession;
      }
   }

   private void setReceivingSession(Session receivingSession) {
      synchronized(this.closeLock) {
         this.receivingSession = receivingSession;
      }
   }

   private void sendQueueMessage(String correlId) throws JMSException {
      QueueSession session = null;
      this.helper.pushSubject();

      try {
         session = (QueueSession)this.pool.getNonXASession(this.sessionType, 0, 1, this.containerAuth, this.userName, this.password);
         QueueSender sender = session.createSender((TemporaryQueue)this.tempDest);
         Message message = session.createMessage();
         message.setJMSCorrelationID(correlId);
         sender.send(message, 1, 5, POLLING_INTERVAL * 2L);
      } finally {
         if (session != null) {
            session.close();
         }

         this.helper.popSubject();
      }

   }

   private void sendMessage(String correlId) throws JMSException {
      Session session = null;
      this.helper.pushSubject();

      try {
         session = (Session)this.pool.getNonXASession(this.sessionType, 0, 1, this.containerAuth, this.userName, this.password);
         MessageProducer sender = session.createProducer((TemporaryQueue)this.tempDest);
         Message message = session.createMessage();
         message.setJMSCorrelationID(correlId);
         sender.send(message, 1, 5, POLLING_INTERVAL * 2L);
      } finally {
         if (session != null) {
            session.close();
         }

         this.helper.popSubject();
      }

   }

   private void sendTopicMessage(String correlId) throws JMSException {
      TopicSession session = null;
      this.helper.pushSubject();

      try {
         session = (TopicSession)this.pool.getNonXASession(this.sessionType, 0, 1, this.containerAuth, this.userName, this.password);
         TopicPublisher sender = session.createPublisher((TemporaryTopic)this.tempDest);
         Message message = session.createMessage();
         message.setJMSCorrelationID(correlId);
         sender.publish(message, 1, 5, POLLING_INTERVAL * 2L);
      } finally {
         if (session != null) {
            session.close();
         }

         this.helper.popSubject();
      }

   }

   public void onMessage(Message msg) {
      synchronized(this.testerLock) {
         try {
            String correlId = msg.getJMSCorrelationID();
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("JMSSessionPoolTester for pool " + this.pool.getName() + " received message " + correlId);
            }

            if (correlId != null && correlId.equals(this.pendingMessageID)) {
               this.receivedPendingMessage = true;
            }
         } catch (JMSException var5) {
            this.receivedPendingMessage = false;
         }

      }
   }

   public void onException(JMSException jmse) {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Exception caught in PoolTester.onException for pool " + this.pool.getName() + " in JMSSessionPoolTester: " + this + JMSPoolDebug.getWholeJMSException(jmse), jmse);
      }

      Exception jmsLinked = jmse.getLinkedException();
      if (jmsLinked != null && jmsLinked instanceof SystemException) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("PoolTester.onException(), bypassing pool shutdown, not a connection failure");
         }

      } else if (jmsLinked != null && jmsLinked.getClass().getName().equals("com.ibm.mq.MQException") && jmsLinked.toString().indexOf("MQRC_NO_MSG_AVAILABLE") >= 0) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("PoolTester.onException(), bypassing pool shutdown due to IBM MQ MQRC_NO_MSG_AVAILABLE exception, not a connection failure");
         }

      } else {
         this.setConnectionFailure(jmse);
         if (this.helper.getSubject() != null) {
            this.helper.pushSubject();
         } else {
            pushAnonymousSubject();
         }

         try {
            this.shutDownConnection();
         } finally {
            if (this.helper.getSubject() != null) {
               this.helper.popSubject();
            } else {
               popAnonymousSubject();
            }

         }

      }
   }

   public static synchronized void pushAnonymousSubject() {
      SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, SubjectManager.getSubjectManager().getAnonymousSubject());
   }

   public static synchronized void popAnonymousSubject() {
      SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
   }

   public void shutDownConnection() {
      synchronized(this.timerLock) {
         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }
      }

      if (!this.isClosed()) {
         this.pool.getWorkManager().schedule(this);
      }
   }

   public void run() {
      if (!this.isClosed()) {
         try {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("JMSSessionPoolTester.run(): Closing JMSSessionPool " + this.pool.getName() + "[poolManager=" + this.pool.getSessionPoolManager() + "], [current cic=" + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext() + "]");
            }

            JMSException connectionFailure = this.getConnectionFailure();
            if (connectionFailure != null) {
               JMSPoolLogger.logJMSConnectionFailure(JMSPoolDebug.getWholeJMSException(connectionFailure));
            } else if (!this.pool.isClosed()) {
               JMSPoolLogger.logJMSConnectionFailure("Unknown reason");
            }

            this.close();
            if (!this.pool.isClosed()) {
               this.pool.close(30000L);
            }
         } catch (JMSException var2) {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Exception while closing session", var2);
            }
         }

      }
   }

   static {
      try {
         Long pollingIntervalLong = Long.getLong("weblogic.deployment.jms.JMSSessionPoolTester.POLLING_INTERVAL");
         if (pollingIntervalLong != null) {
            POLLING_INTERVAL = pollingIntervalLong;
         }
      } catch (RuntimeException var1) {
      }

   }
}
