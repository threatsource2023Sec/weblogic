package weblogic.deployment.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class PooledSession extends WrappedTransactionalSession {
   protected JMSSessionPool pool;
   protected JMSSessionHolder.HolderReference sessionHolderRef;

   protected void init(String poolName, JMSSessionHolder.HolderReference sessionHolderRef, boolean nativeTransactions, WrappedClassManager manager) throws JMSException {
      super.init(poolName, sessionHolderRef.getHolder(), nativeTransactions, manager);
      this.sessionHolderRef = sessionHolderRef;
   }

   public TemporaryQueue createTemporaryQueue() throws JMSException {
      this.checkClosed();
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TemporaryQueue tq = this.session.createTemporaryQueue();
         if (this.parent != null) {
            this.parent.temporaryDestinationCreated(tq);
         }

         return tq;
      }
   }

   public TemporaryTopic createTemporaryTopic() throws JMSException {
      this.checkClosed();
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TemporaryTopic tt = this.session.createTemporaryTopic();
         if (this.parent != null) {
            this.parent.temporaryDestinationCreated(tt);
         }

         return tt;
      }
   }

   public void close() throws JMSException {
      if (this.parent != null) {
         synchronized(this.parent) {
            this.closeInternal();
         }
      } else {
         this.closeInternal();
      }

   }

   private synchronized void closeInternal() throws JMSException {
      synchronized(this.parent == null ? this : this.parent) {
         synchronized(this) {
            if (!this.closed) {
               this.doClose();
               if (this.parent != null) {
                  this.parent.sessionClosed(this);
               }
            }
         }

      }
   }

   public MessageConsumer createConsumer(Destination dest) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(dest);
      MessageConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSession.createConsumer(dest);
      } finally {
         this.popSubject();
      }

      return (MessageConsumer)this.postInvocationHandler("createConsumer", (Object[])null, receiver);
   }

   public MessageConsumer createConsumer(Destination dest, String selector) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(dest);
      MessageConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSession.createConsumer(dest, selector);
      } finally {
         this.popSubject();
      }

      return (MessageConsumer)this.postInvocationHandler("createConsumer", (Object[])null, receiver);
   }

   public MessageConsumer createConsumer(Destination dest, String selector, boolean noLocal) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(dest);
      MessageConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSession.createConsumer(dest, selector, noLocal);
      } finally {
         this.popSubject();
      }

      return (MessageConsumer)this.postInvocationHandler("createConsumer", (Object[])null, receiver);
   }

   public QueueReceiver createReceiver(Queue queue) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(queue);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         QueueReceiver receiver = null;
         this.pushSubject();

         try {
            receiver = ((QueueSession)this.session).createReceiver(queue);
         } finally {
            this.popSubject();
         }

         return (QueueReceiver)this.postInvocationHandler("createReceiver", (Object[])null, receiver);
      }
   }

   public QueueReceiver createReceiver(Queue queue, String selector) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(queue);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         QueueReceiver receiver = null;
         this.pushSubject();

         try {
            receiver = ((QueueSession)this.session).createReceiver(queue, selector);
         } finally {
            this.popSubject();
         }

         return (QueueReceiver)this.postInvocationHandler("createReceiver", (Object[])null, receiver);
      }
   }

   public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TopicSubscriber subscriber = null;
         this.pushSubject();

         try {
            subscriber = ((TopicSession)this.session).createSubscriber(topic);
         } finally {
            this.popSubject();
         }

         return (TopicSubscriber)this.postInvocationHandler("createSubscriber", (Object[])null, subscriber);
      }
   }

   public TopicSubscriber createSubscriber(Topic topic, String selector, boolean noLocal) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TopicSubscriber subscriber = null;
         this.pushSubject();

         try {
            subscriber = ((TopicSession)this.session).createSubscriber(topic, selector, noLocal);
         } finally {
            this.popSubject();
         }

         return (TopicSubscriber)this.postInvocationHandler("createSubscriber", (Object[])null, subscriber);
      }
   }

   synchronized void doClose() throws JMSException {
      this.closed = true;
      this.closeChildren();
      if (this.pool != null) {
         if (this.enlistedTransaction != null) {
            JMSPoolDebug.logger.debug("Saving session for use with transaction");
            this.pool.sessionEnlistedButAvailable(this.enlistedTransaction, this);
         } else {
            JMSPoolDebug.logger.debug("Returning pooled session to the pool");
            this.sessionHolderRef.closePhantomReference();
         }
      }

   }

   protected synchronized void assignFromPool(JMSSessionPool pool) {
      this.pool = pool;
      this.closed = false;
   }

   public synchronized void afterCompletion(int status) {
      try {
         if (this.closed && this.enlistedTransaction != null) {
            JMSPoolDebug.logger.debug("Returning pooled session because transaction completed");
            this.pool.transactionCompleted(this.enlistedTransaction);
            this.sessionHolderRef.closePhantomReference();
         }

         this.enlistedTransaction = null;
      } catch (RuntimeException var3) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Unexpected JMS exception in afterCompletion", var3);
         }
      }

   }

   protected synchronized void setParent(PooledConnection parent) {
      this.parent = parent;
   }
}
