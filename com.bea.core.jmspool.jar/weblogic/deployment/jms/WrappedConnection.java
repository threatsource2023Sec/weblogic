package weblogic.deployment.jms;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSession;
import weblogic.utils.wrapper.Wrapper;

public class WrappedConnection implements Wrapper {
   protected JMSConnectionHelperService connectionHelper;
   protected Connection vendorConnection;
   protected Object vendorObj;
   protected int wrapStyle;
   protected WrappedClassManager wrapperManager;
   protected boolean closed;

   protected void init(int wrapStyle, WrappedClassManager wrapperManager, JMSConnectionHelperService helper) {
      JMSPoolDebug.logger.debug("Created a new non-pooled WrappedConnection");
      this.connectionHelper = helper;
      this.vendorConnection = helper.getConnection();
      this.vendorObj = this.vendorConnection;
      this.wrapStyle = wrapStyle;
      this.wrapperManager = wrapperManager;
   }

   protected void init(int wrapStyle, WrappedClassManager wrapperManager, Connection vendorConnection) {
      this.vendorConnection = vendorConnection;
      this.vendorObj = vendorConnection;
      this.wrapStyle = wrapStyle;
      this.wrapperManager = wrapperManager;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws JMSException {
      if (!methodName.equals("close")) {
         this.checkClosed();
      }

      if ((this.wrapStyle == 1 || this.wrapStyle == 2) && (methodName.equals("createConnectionConsumer") || methodName.equals("createDurableConnectionConsumer") || methodName.equals("createSharedConnectionConsumer") || methodName.equals("createSharedDurableConnectionConsumer") || methodName.equals("setClientID") || methodName.equals("setExceptionListener") || methodName.equals("stop"))) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }

   public Session createSession(boolean transacted, int ackMode) throws JMSException {
      this.checkClosed();
      return (Session)this.doCreateSession(3, transacted, ackMode);
   }

   public QueueSession createQueueSession(boolean transacted, int ackMode) throws JMSException {
      this.checkClosed();
      return (QueueSession)this.doCreateSession(1, transacted, ackMode);
   }

   public TopicSession createTopicSession(boolean transacted, int ackMode) throws JMSException {
      this.checkClosed();
      return (TopicSession)this.doCreateSession(2, transacted, ackMode);
   }

   public Session createSession(int sessionMode) throws JMSException {
      try {
         this.checkClosed();
         return sessionMode == 0 ? (Session)this.doCreateSession(3, true, 0) : (Session)this.doCreateSession(3, false, sessionMode);
      } catch (AbstractMethodError var3) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSession(int sessionMode)", "javax.jms.Connection"), new Exception(var3));
      }
   }

   public Session createQueueSession(int sessionMode) throws JMSException {
      this.checkClosed();
      return sessionMode == 0 ? (Session)this.doCreateSession(1, true, 0) : (Session)this.doCreateSession(1, false, sessionMode);
   }

   public Session createTopicSession(int sessionMode) throws JMSException {
      this.checkClosed();
      return sessionMode == 0 ? (Session)this.doCreateSession(2, true, 0) : (Session)this.doCreateSession(2, false, sessionMode);
   }

   public Session createSession() throws JMSException {
      try {
         this.checkClosed();
         return (Session)this.doCreateSession(3, false, 1);
      } catch (AbstractMethodError var2) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSession()", "javax.jms.Connection"), new Exception(var2));
      }
   }

   public Session createQueueSession() throws JMSException {
      this.checkClosed();
      return (Session)this.doCreateSession(1, false, 1);
   }

   public Session createTopicSession() throws JMSException {
      this.checkClosed();
      return (Session)this.doCreateSession(2, false, 1);
   }

   public ConnectionConsumer createSharedConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool serverSessionPool, int maxMessages) throws JMSException {
      if (this.wrapStyle != 1 && this.wrapStyle != 2) {
         try {
            return this.vendorConnection.createSharedConnectionConsumer(topic, subscriptionName, messageSelector, serverSessionPool, maxMessages);
         } catch (AbstractMethodError var7) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createSharedConnectionConsumer(Topic topic, String subscriptionName,String messageSelector, ServerSessionPool serverSessionPool, int maxMessages)", "javax.jms.Connection"), new Exception(var7));
         }
      } else {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("createSharedConnectionConsumer"));
      }
   }

   public synchronized void close() throws JMSException {
      if (!this.closed) {
         this.vendorConnection.close();
         this.closed = true;
      }

   }

   protected WrappedSession doCreateSession(int sessionType, boolean transacted, int ackMode) throws JMSException {
      boolean ignoreXA = !transacted && ackMode == 2;
      JMSSessionHolder holder = this.connectionHelper.getNewSession(sessionType, transacted, ackMode, ignoreXA);
      byte type;
      switch (sessionType) {
         case 1:
            type = 7;
            break;
         case 2:
            type = 8;
            break;
         default:
            type = 6;
      }

      WrappedTransactionalSession ret = (WrappedTransactionalSession)this.wrapperManager.getWrappedInstance(type, holder.getSession());
      ret.init(this.connectionHelper.getXAResourceName(), holder, this.connectionHelper.hasNativeTransactions(), this.wrapperManager);
      ret.setWrapStyle(this.wrapStyle);
      ret.setConnectionStarted(true);
      return ret;
   }

   protected int getWrapStyle() {
      return this.wrapStyle;
   }

   protected synchronized void checkClosed() throws JMSException {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public void setVendorObj(Object o) {
      this.vendorConnection = (Connection)o;
      this.vendorObj = o;
   }

   public WrappedClassManager getWrapperManager() {
      return this.wrapperManager;
   }
}
