package weblogic.deployment.jms;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSubscriber;
import javax.jms.XASession;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.Transaction;
import weblogic.utils.wrapper.Wrapper;

public abstract class WrappedSession implements Wrapper {
   protected JMSSessionHolder sessionHolder;
   protected Session vendorSession;
   protected Object vendorObj;
   protected XASession xaSession;
   protected Session session;
   protected int wrapStyle;
   protected int acknowledgeMode;
   protected Set openChildren;
   protected WrappedClassManager wrapperManager;
   protected boolean closed;
   protected boolean started;
   protected PooledConnection parent;
   protected AbstractSubject subject = null;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected void init(JMSSessionHolder sessionHolder, WrappedClassManager manager) throws JMSException {
      this.sessionHolder = sessionHolder;
      this.xaSession = sessionHolder.getXASession();
      this.acknowledgeMode = sessionHolder.getAcknowledgeMode();
      this.wrapperManager = manager;
      if (sessionHolder.getConnectionHelper() != null) {
         this.subject = sessionHolder.getConnectionHelper().getSubject();
      }

      this.setVendorObj(sessionHolder.getSession());
      this.openChildren = new HashSet(3);
   }

   protected void enlistInTransaction(boolean isProducer) throws JMSException {
   }

   protected void delistFromTransaction(boolean commit) throws JMSException {
   }

   protected void closeChildren() {
      this.pushSubject();

      try {
         Iterator children = this.openChildren.iterator();

         while(children.hasNext()) {
            Object obj = children.next();
            if (obj instanceof WrappedMessageProducer) {
               WrappedMessageProducer producer = (WrappedMessageProducer)obj;
               producer.setClosed();
               this.sessionHolder.addCachedProducer(producer.getWrapType(), (MessageProducer)producer.getVendorObj());
            } else if (obj instanceof WrappedMessageConsumer) {
               try {
                  WrappedMessageConsumer consumer = (WrappedMessageConsumer)obj;
                  consumer.setClosed();
                  consumer.closeProviderConsumer();
               } catch (JMSException var9) {
               }
            } else if (obj instanceof WrappedQueueBrowser) {
               try {
                  ((WrappedQueueBrowser)obj).closeProviderBrowser();
               } catch (JMSException var8) {
               }
            }
         }

         this.openChildren.clear();
      } finally {
         this.popSubject();
      }
   }

   protected void closeProviderSession() {
      this.pushSubject();

      try {
         this.sessionHolder.destroy();
      } finally {
         this.popSubject();
      }

   }

   protected synchronized void setConnectionStarted(boolean started) {
      this.started = started;
      Iterator allChildren = this.openChildren.iterator();

      while(allChildren.hasNext()) {
         Object next = allChildren.next();
         if (next instanceof WrappedMessageConsumer) {
            this.pushSubject();

            try {
               ((WrappedMessageConsumer)next).setConnectionStarted(started);
            } finally {
               this.popSubject();
            }
         }
      }

   }

   private Object createWrappedMessageProducer(String debugString, Destination dest, int wrapType) throws JMSException {
      this.checkClosed();
      MessageProducer producer = this.sessionHolder.removeCachedProducer(wrapType, dest);
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Create producer, " + debugString + ", wrapType=" + wrapType + ", fromCache=" + (producer != null) + ", dest=" + dest);
      }

      if (producer == null) {
         this.pushSubject();

         try {
            producer = this.sessionHolder.createProducer(this.vendorSession, dest, wrapType);
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Created a new producer= " + producer + ", as there is no cached producer found for the destination " + dest + ", with wrapType=" + wrapType);
            }
         } finally {
            this.popSubject();
         }
      } else if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Found a cached producer=" + producer + ", for the destination " + dest + ", with wrapType=" + wrapType);
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug(this.sessionHolder.getCachedProducersDebugString());
      }

      Object wrappedRet = this.wrapperManager.getWrappedInstance(wrapType, producer);
      synchronized(this) {
         ((WrappedMessageProducer)wrappedRet).init(this, producer, dest, wrapType);
         this.openChildren.add(wrappedRet);
         return wrappedRet;
      }
   }

   public void setVendorObj(Object o) {
      this.vendorObj = o;
      this.vendorSession = (Session)o;
      this.session = this.vendorSession;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws JMSException {
      if (!methodName.equals("close")) {
         this.checkClosed();
      }

      if (this.wrapStyle != 3 || !methodName.equals("recover") && !methodName.equals("commit") && !methodName.equals("rollback")) {
         if (this.wrapStyle != 0 && (methodName.equals("getMessageListener") || methodName.equals("setMessageListener"))) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
         }
      } else {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws JMSException {
      byte wrapType;
      if (methodName.equals("createConsumer")) {
         wrapType = 10;
      } else if (methodName.equals("createProducer")) {
         wrapType = 9;
      } else if (methodName.equals("createReceiver")) {
         wrapType = 12;
      } else if (methodName.equals("createSender")) {
         wrapType = 11;
      } else if (methodName.equals("createBrowser")) {
         wrapType = 15;
      } else if (!methodName.equals("createSubscriber") && !methodName.equals("createDurableSubscriber") && !methodName.equals("createDurableConsumer") && !methodName.equals("createSharedConsumer") && !methodName.equals("createSharedDurableConsumer")) {
         if (!methodName.equals("createPublisher")) {
            return ret;
         }

         wrapType = 13;
      } else {
         wrapType = 14;
      }

      Object wrappedRet = this.wrapperManager.getWrappedInstance(wrapType, ret);
      synchronized(this) {
         switch (wrapType) {
            case 10:
            case 12:
            case 14:
               ((WrappedMessageConsumer)wrappedRet).init(this, (MessageConsumer)ret, this.wrapperManager);
               if (this.started) {
                  ((WrappedMessageConsumer)wrappedRet).setConnectionStarted(true);
               }
            case 11:
            case 13:
            default:
               break;
            case 15:
               ((WrappedQueueBrowser)wrappedRet).init(this, (QueueBrowser)ret);
         }

         this.openChildren.add(wrappedRet);
         return wrappedRet;
      }
   }

   public MessageConsumer createSharedConsumer(Topic topic, String name) throws JMSException {
      return this.createSharedConsumer(topic, name, (String)null);
   }

   public MessageConsumer createSharedConsumer(Topic topic, String name, String selector) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         MessageConsumer subscriber = null;
         this.pushSubject();

         try {
            subscriber = this.session.createSharedConsumer(topic, name, selector);
         } catch (AbstractMethodError var9) {
            if (selector == null) {
               throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSharedConsumer(Topic topic,String name)", "javax.jms.Session"), new Exception(var9));
            }

            throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSharedConsumer(Topic topic,String name,String selector)", "javax.jms.Session"), new Exception(var9));
         } finally {
            this.popSubject();
         }

         return (MessageConsumer)this.postInvocationHandler("createSharedConsumer", (Object[])null, subscriber);
      }
   }

   public void checkValidTemporaryDest(Destination dest) throws JMSException {
      if ((dest instanceof TemporaryQueue || dest instanceof TemporaryTopic) && this.parent != null && !this.parent.isValidTemporary(dest)) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logTemporaryDestinationUnrecognizedLoggable());
      }
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name) throws JMSException {
      try {
         return this.createDurableSubscriber(topic, name);
      } catch (AbstractMethodError var4) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createDurableConsumer(Topic topic,String name)", "javax.jms.Session"), new Exception(var4));
      }
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name, String selector, boolean noLocal) throws JMSException {
      try {
         return this.createDurableSubscriber(topic, name, selector, noLocal);
      } catch (AbstractMethodError var6) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createDurableConsumer(Topic topic,String name,String selector, boolean noLocal)", "javax.jms.Session"), new Exception(var6));
      }
   }

   public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TopicSubscriber subscriber = null;
         this.pushSubject();

         try {
            subscriber = this.session.createDurableSubscriber(topic, name);
         } finally {
            this.popSubject();
         }

         return (TopicSubscriber)this.postInvocationHandler("createDurableSubscriber", (Object[])null, subscriber);
      }
   }

   public TopicSubscriber createDurableSubscriber(Topic topic, String name, String selector, boolean noLocal) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         TopicSubscriber subscriber = null;
         this.pushSubject();

         try {
            subscriber = this.session.createDurableSubscriber(topic, name, selector, noLocal);
         } finally {
            this.popSubject();
         }

         return (TopicSubscriber)this.postInvocationHandler("createDurableSubscriber", (Object[])null, subscriber);
      }
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name) throws JMSException {
      return this.createSharedDurableConsumer(topic, name, (String)null);
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name, String selector) throws JMSException {
      this.checkClosed();
      this.checkValidTemporaryDest(topic);
      if (this.session == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSDestinationWrongTypeLoggable());
      } else {
         MessageConsumer subscriber = null;
         this.pushSubject();

         try {
            subscriber = this.session.createSharedDurableConsumer(topic, name, selector);
         } catch (AbstractMethodError var9) {
            if (selector == null) {
               throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSharedDurableConsumer(Topic topic,String name)", "javax.jms.Session"), new Exception(var9));
            }

            throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSharedDurableConsumer(Topic topic,String name,String selector)", "javax.jms.Session"), new Exception(var9));
         } finally {
            this.popSubject();
         }

         return (MessageConsumer)this.postInvocationHandler("createSharedDurableConsumer", (Object[])null, subscriber);
      }
   }

   public MessageProducer createProducer(Destination dest) throws JMSException {
      return (MessageProducer)this.createWrappedMessageProducer("MessageProducer", dest, 9);
   }

   public QueueSender createSender(Queue queue) throws JMSException {
      return (QueueSender)this.createWrappedMessageProducer("QueueSender", queue, 11);
   }

   public TopicPublisher createPublisher(Topic topic) throws JMSException {
      return (TopicPublisher)this.createWrappedMessageProducer("TopicPublisher", topic, 13);
   }

   public void run() {
      if (this.wrapStyle != 0) {
         throw new IllegalArgumentException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("run").getMessage());
      } else {
         this.vendorSession.run();
      }
   }

   public int getAcknowledgeMode() throws JMSException {
      return this.acknowledgeMode;
   }

   protected synchronized void producerClosed(WrappedMessageProducer producer) throws JMSException {
      this.openChildren.remove(producer);
      this.sessionHolder.addCachedProducer(producer.getWrapType(), (MessageProducer)producer.getVendorObj());
   }

   protected synchronized void consumerClosed(WrappedMessageConsumer consumer) throws JMSException {
      this.openChildren.remove(consumer);
      consumer.closeProviderConsumer();
   }

   protected synchronized void browserClosed(WrappedQueueBrowser browser) throws JMSException {
      this.openChildren.remove(browser);
      browser.closeProviderBrowser();
   }

   protected void setWrapStyle(int style) {
      this.wrapStyle = style;
   }

   protected int getWrapStyle() {
      return this.wrapStyle;
   }

   protected JMSSessionHolder getSessionHolder() {
      return this.sessionHolder;
   }

   protected AbstractSubject getSubject() {
      return this.subject;
   }

   protected Transaction getEnlistedTransaction() {
      return null;
   }

   protected synchronized void checkClosed() throws JMSException {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSObjectClosedLoggable());
      } else if (!this.sessionHolder.isEnabled()) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSSessionPoolDisabledLoggable());
      }
   }

   public synchronized String toString() {
      return "JMS Session: " + this.vendorSession;
   }

   public synchronized void pushSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().pushSubject(kernelID, this.subject);
      }

   }

   public synchronized void popSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().popSubject(kernelID);
      }

   }
}
