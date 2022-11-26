package weblogic.deployment.jms;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;

public class PooledSecondaryContext extends WrappedTransactionalSecondaryContext {
   protected JMSSessionPool pool;
   protected PooledPrimaryContext parent;
   protected SecondaryContextHolder.HolderReference secondaryContextHolderRef;

   protected void init(String poolName, SecondaryContextHolder.HolderReference sessionHolderRef, boolean nativeTransactions, WrappedClassManager manager) {
      super.init(poolName, sessionHolderRef.getHolder(), nativeTransactions, manager);
      this.secondaryContextHolderRef = sessionHolderRef;
   }

   public TemporaryQueue createTemporaryQueue() {
      this.checkClosed();
      if (this.context == null) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TemporaryQueue tq = this.context.createTemporaryQueue();
         this.secondaryContextHolder.temporaryDestinationCreated(tq);
         return tq;
      }
   }

   public TemporaryTopic createTemporaryTopic() {
      this.checkClosed();
      if (this.context == null) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TemporaryTopic tt = this.context.createTemporaryTopic();
         this.secondaryContextHolder.temporaryDestinationCreated(tt);
         return tt;
      }
   }

   public void close() {
      if (this.parent != null) {
         synchronized(this.parent) {
            this.closeInternal();
         }
      } else {
         this.closeInternal();
      }

   }

   private synchronized void closeInternal() {
      synchronized(this.parent == null ? this : this.parent) {
         synchronized(this) {
            if (!this.closed) {
               this.doClose();
               if (this.parent != null) {
                  this.parent.secondaryContextClosed(this);
               }
            }
         }

      }
   }

   public JMSConsumer createConsumer(Destination destination) {
      this.checkClosed();
      this.checkValidTemporaryDest(destination);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createConsumer(destination);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createConsumer(Destination destination, String messageSelector) {
      this.checkClosed();
      this.checkValidTemporaryDest(destination);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createConsumer(destination, messageSelector);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) {
      this.checkClosed();
      this.checkValidTemporaryDest(destination);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createConsumer(destination, messageSelector, noLocal);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createDurableConsumer(Topic topic, String name) {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createDurableConsumer(topic, name);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createDurableConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createDurableConsumer(topic, name, messageSelector, noLocal);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createDurableConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createSharedConsumer(topic, sharedSubscriptionName);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createSharedConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createSharedConsumer(topic, sharedSubscriptionName, messageSelector);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createSharedConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createSharedDurableConsumer(Topic topic, String name) {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createSharedDurableConsumer(topic, name);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createSharedDurableConsumer", (Object[])null, receiver);
   }

   public JMSConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      JMSConsumer receiver = null;
      this.pushSubject();

      try {
         receiver = this.vendorSecondaryContext.createSharedDurableConsumer(topic, name, messageSelector);
      } finally {
         this.popSubject();
      }

      return (JMSConsumer)this.postInvocationHandler("createSharedDurableConsumer", (Object[])null, receiver);
   }

   private void checkValidTemporaryDest(Destination dest) {
      if ((dest instanceof TemporaryQueue || dest instanceof TemporaryTopic) && this.parent != null && !this.secondaryContextHolder.isValidTemporary(dest)) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logTemporaryDestinationUnrecognizedLoggable());
      }
   }

   synchronized void doClose() {
      this.closed = true;
      this.closeChildren();
      if (this.pool != null) {
         this.secondaryContextHolderRef.deleteTemporaryDestinations();
         if (this.enlistedTransaction != null) {
            JMSPoolDebug.logger.debug("Saving secondary context for use with transaction");
            this.pool.secondaryContextEnlistedButAvailable(this.enlistedTransaction, this);
         } else {
            JMSPoolDebug.logger.debug("Returning pooled secondary context to the pool");
            this.secondaryContextHolderRef.closePhantomReference();
         }

         this.setAutoStart(true);
      }

   }

   protected synchronized void assignFromPool(JMSSessionPool pool) {
      this.pool = pool;
      this.closed = false;
   }

   public synchronized void afterCompletion(int status) {
      if (this.closed && this.enlistedTransaction != null) {
         JMSPoolDebug.logger.debug("Returning pooled secondary context because transaction completed");
         this.pool.transactionOnSecondaryContextCompleted(this.enlistedTransaction);
         this.secondaryContextHolderRef.closePhantomReference();
      }

      this.enlistedTransaction = null;
   }

   protected synchronized void setParent(PooledPrimaryContext parent) {
      this.parent = parent;
   }

   public void setAutoStart(boolean autoStart) {
      this.autoStart = autoStart;
   }

   public boolean getAutoStart() {
      return this.autoStart;
   }
}
