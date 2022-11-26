package weblogic.jms.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PrivilegedExceptionAction;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.extensions.ConsumerClosedException;
import weblogic.jms.frontend.FEConsumerSetListenerRequest;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.CompletionListener;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.security.subject.AbstractSubject;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.expressions.ExpressionEvaluator;
import weblogic.utils.expressions.ExpressionParserException;

public final class JMSConsumer implements ConsumerInternal, Reconnectable, Cloneable, Invocable {
   private static final String EXPRESSION_PARSER_CLASS = "weblogic.utils.expressions.ExpressionParser";
   private static String MESSAGE_PREFETCH_2;
   private volatile JMSID consumerId;
   private volatile boolean closeInProgress;
   private long expectedSequenceNumber;
   private final JMSSession session;
   private final DestinationImpl destination;
   private final String selector;
   private final String subscriptionName;
   private final boolean durable;
   private final boolean jms2Share;
   private final boolean noLocal;
   private ExpressionEvaluator expressionEvaluator;
   private int windowMaximum;
   private int windowCurrent;
   private int windowThreshold;
   private String runtimeMBeanName;
   private JMSMessageContext messageListenerContext;
   private boolean debugHybridConsumer = false;
   private boolean isClosed;
   private WLConsumerImpl wlConsumerImpl;
   private JMSConsumer replacementConsumer;
   private final byte destinationFlags;
   private ConsumerReconnectInfo consumerReconnectInfo;

   JMSConsumer(JMSSession session, String subscriptionName, boolean isDurable, boolean isJMS2Share, DestinationImpl destination, String selector, boolean noLocal, int windowMaximum, byte destinationFlags) throws JMSException {
      this.subscriptionName = subscriptionName;
      this.durable = isDurable;
      this.jms2Share = isJMS2Share;
      this.session = session;
      this.destination = destination;
      this.selector = selector;
      this.noLocal = noLocal;
      if (session.getAcknowledgeMode() != 128) {
         this.windowMaximum = windowMaximum;
         this.windowCurrent = windowMaximum;
         this.windowThreshold = windowMaximum + 1 >> 1;
      } else if (selector != null && selector.trim().length() > 0) {
         this.expressionEvaluator = createExpressionEvaluator(selector);
      }

      this.destinationFlags = destinationFlags;
   }

   public Object clone() throws CloneNotSupportedException {
      JMSConsumer jmsConsumer = (JMSConsumer)super.clone();
      return jmsConsumer;
   }

   WLConsumerImpl getWLConsumerImpl() {
      return this.wlConsumerImpl;
   }

   public ReconnectController getReconnectController() {
      return this.wlConsumerImpl;
   }

   public Reconnectable getReconnectState(int reconnectPolicy) throws CloneNotSupportedException {
      JMSConsumer jmsConsumer = (JMSConsumer)this.clone();
      jmsConsumer.replacementConsumer = this;
      this.closeInProgress = true;
      return jmsConsumer;
   }

   public Reconnectable preCreateReplacement(Reconnectable parent) throws JMSException {
      ConsumerReconnectInfo cri = this.consumerReconnectInfo.getClone();
      cri.setLastExposedMsgId(this.session.getLastExposedMsgId());
      cri.setLastAckMsgId(this.session.getLastAckMsgId());
      if (this.isDurable()) {
         cri.setServerDestId((JMSID)null);
      }

      JMSConsumer newConsumer = ((JMSSession)parent).setupConsumer(this.destination, this.selector, this.noLocal, this.subscriptionName, this.durable, this.jms2Share, this.destinationFlags, cri);
      newConsumer.windowCurrent = this.windowCurrent;
      MessageListener messageListener = null;
      if (this.messageListenerContext != null) {
         messageListener = this.messageListenerContext.getMessageListener();
      }

      if (messageListener != null && !(messageListener instanceof JMSSystemMessageListener)) {
         newConsumer.setMessageListener(messageListener);
      }

      ((JMSSession)parent).mapReplacementConsumer(this.replacementConsumer, newConsumer);
      this.replacementConsumer = newConsumer;
      return newConsumer;
   }

   public void postCreateReplacement() {
      this.replacementConsumer.setWlConsumerImpl(this.wlConsumerImpl);
      this.wlConsumerImpl.setPhysicalReconnectable(this.replacementConsumer);
   }

   public void forgetReconnectState() {
      this.replacementConsumer = null;
   }

   public PeerInfo getFEPeerInfo() {
      return this.session.getFEPeerInfo();
   }

   public boolean isReconnectControllerClosed() {
      return this.wlConsumerImpl == null || this.wlConsumerImpl.isClosed();
   }

   public final boolean isClosed() {
      return this.isClosed || this.closeInProgress;
   }

   public String getWLSServerName() {
      return this.session.getConnection().getWLSServerName();
   }

   public ClientRuntimeInfo getParentInfo() {
      return this.session;
   }

   public String getRuntimeMBeanName() {
      return this.runtimeMBeanName;
   }

   final void setWlConsumerImpl(WLConsumerImpl rp) {
      this.wlConsumerImpl = rp;
   }

   public String getPartitionName() {
      return this.session.getConnection().getPartitionName();
   }

   public void setConsumerReconnectInfo(ConsumerReconnectInfo cri) {
      this.consumerReconnectInfo = cri;
   }

   boolean hasTemporaryDestination() {
      DestinationImpl stableDestination = this.destination;
      return stableDestination != null && (stableDestination.getType() == 4 || stableDestination.getType() == 8);
   }

   public final JMSSession getSession() {
      return this.session;
   }

   public final void setRuntimeMBeanName(String runtimeMBeanName) {
      this.runtimeMBeanName = runtimeMBeanName;
   }

   public final String toString() {
      return this.session.getConnection().getRuntimeMBeanName() + "." + this.session.getRuntimeMBeanName() + "." + this.getRuntimeMBeanName();
   }

   private void incrementWindowCurrent(int windowIncrement, boolean clientResponsibleForAcknowledge) throws JMSException {
      this.session.consumerIncrementWindowCurrent(this.consumerId, windowIncrement, clientResponsibleForAcknowledge);
      this.windowCurrent += windowIncrement;
      if (this.windowCurrent > this.windowMaximum) {
         this.windowCurrent = this.windowMaximum;
      }

   }

   public final void decrementWindowCurrent(boolean clientResponsibleForAcknowledge) throws JMSException {
      if (--this.windowCurrent < this.windowThreshold) {
         this.incrementWindowCurrent(this.windowMaximum - this.windowCurrent, clientResponsibleForAcknowledge);
      }

   }

   public final void setWindowCurrent(int windowCurrent) {
      this.windowCurrent = windowCurrent;
   }

   public final int getWindowCurrent() {
      return this.windowCurrent;
   }

   public final int getWindowMaximum() {
      return this.windowMaximum;
   }

   public final ExpressionEvaluator getExpressionEvaluator() {
      return this.expressionEvaluator;
   }

   public final void setClosed(boolean arg) {
      this.isClosed = arg;
   }

   public final void setId(JMSID consumerId) {
      this.consumerId = consumerId;
   }

   public final JMSID getJMSID() {
      return this.consumerId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.session.getDispatcherPartition4rmic();
   }

   public final InvocableMonitor getInvocableMonitor() {
      return null;
   }

   public final boolean isDurable() {
      return this.durable;
   }

   public final synchronized long getExpectedSequenceNumber() {
      return this.expectedSequenceNumber;
   }

   public final synchronized void setExpectedSequenceNumber(long expectedSequenceNumber) {
      this.setExpectedSequenceNumber(expectedSequenceNumber, false);
   }

   public final synchronized void setExpectedSequenceNumber(long expectedSequenceNumber, boolean force) {
      if (force || expectedSequenceNumber > this.expectedSequenceNumber) {
         this.expectedSequenceNumber = expectedSequenceNumber;
      }

   }

   public final Destination getDestination() {
      return this.destination;
   }

   public final String getMessageSelector() throws JMSException {
      this.checkClosed();
      return this.selector;
   }

   public final MessageListener getMessageListener() throws JMSException {
      this.checkClosed();
      return this.messageListenerContext != null ? this.messageListenerContext.getMessageListener() : null;
   }

   public final JMSMessageContext getMessageListenerContext() {
      return this.messageListenerContext;
   }

   public final void setMessageListener(MessageListener listener) throws JMSException {
      this.setMessageListener(listener, -1L);
   }

   final void setMessageListener(MessageListener listener, long sequenceNumber) throws JMSException {
      final MessageListener myListener = listener;
      synchronized(this.session) {
         synchronized(this) {
            this.checkClosed();
            if (sequenceNumber != -1L) {
               this.session.setRealLastSequenceNumber(sequenceNumber);
            }

            if (this.session.getMessageListener() != null) {
               throw new IllegalStateException(JMSClientExceptionLogger.logMessageListenerExistsLoggable().getMessage());
            }

            if (listener instanceof JMSSystemMessageListenerImpl2) {
               this.session.markAsSystemMessageListener(true);
            } else {
               this.session.markAsSystemMessageListener(false);
            }

            try {
               if (this.getMessageListener() == null && listener != null) {
                  this.session.incrementConsumerListenerCount();
               } else {
                  if (this.getMessageListener() == null || listener != null) {
                     return;
                  }

                  this.session.decrementConsumerListenerCount();
               }

               try {
                  if (KernelStatus.isServer() && this.session.isRemoteDomain()) {
                     AbstractSubject subject = CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.getSession().getConnection().getFrontEndDispatcher(), CrossDomainSecurityManager.getCurrentSubject(), true);
                     if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
                        JMSDebug.JMSCrossDomainSecurity.debug("setMessageListener:   subject to use = " + subject);
                     }

                     CrossDomainSecurityManager.doAs(subject, new PrivilegedExceptionAction() {
                        public Object run() throws JMSException {
                           Object response = JMSConsumer.this.session.getConnection().getFrontEndDispatcher().dispatchSync(new FEConsumerSetListenerRequest(JMSConsumer.this.consumerId, myListener != null, JMSConsumer.this.session.getLastSequenceNumber()));
                           return null;
                        }
                     });
                  } else {
                     Response var9 = this.session.getConnection().getFrontEndDispatcher().dispatchSync(new FEConsumerSetListenerRequest(this.consumerId, listener != null, this.session.getLastSequenceNumber()));
                  }
               } catch (JMSException var17) {
                  listener = null;
                  throw var17;
               }
            } finally {
               this.messageListenerContext = new JMSMessageContext(listener);
            }
         }

      }
   }

   public final Message receive() throws JMSException {
      return this.receiveInternal(Long.MAX_VALUE, (CompletionListener)null);
   }

   public final Message receiveNoWait() throws JMSException {
      return this.receiveInternal(9223372036854775806L, (CompletionListener)null);
   }

   public final Message receive(long timeout) throws JMSException {
      return this.receiveInternal(timeout, (CompletionListener)null);
   }

   public Object receiveBody(Class c) throws JMSException {
      return this.receiveBodyInternal(c, Long.MAX_VALUE);
   }

   public Object receiveBody(Class c, long timeout) throws JMSException {
      return this.receiveBodyInternal(c, timeout);
   }

   public Object receiveBodyNoWait(Class c) throws JMSException {
      return this.receiveBodyInternal(c, 9223372036854775806L);
   }

   private Object receiveBodyInternal(Class c, long timeout) throws JMSException {
      Message m = null;
      if (this.session.getAcknowledgeMode() != 0 && this.session.getAcknowledgeMode() != 2) {
         m = this.receiveInternal(timeout, (CompletionListener)null, c);
      } else {
         m = this.receiveInternal(timeout, (CompletionListener)null, (Class)null);
      }

      if (m == null) {
         return null;
      } else {
         Object body = m.getBody(c);
         if (body == null) {
            throw new MessageFormatException(JMSClientExceptionLogger.logNoMessageBodyLoggable().getMessage());
         } else {
            return body;
         }
      }
   }

   Message receiveInternal(long timeout, CompletionListener listener) throws JMSException {
      return this.receiveInternal(timeout, listener, (Class)null);
   }

   private Message receiveInternal(long timeout, CompletionListener listener, Class bodyClass) throws JMSException {
      JMSSystemMessageListener jmsListener = null;
      synchronized(this.session) {
         synchronized(this) {
            this.checkClosed();
            if (this.session.isTransacted() || TransactionHelper.getTransactionHelper().getTransaction() != null) {
               bodyClass = null;
            }

            if (this.session.getAcknowledgeMode() == 128) {
               throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoSynchronousMulticastReceiveLoggable());
            }

            if (timeout == 0L) {
               timeout = Long.MAX_VALUE;
            } else if (timeout < 0L) {
               throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidTimeoutLoggable(timeout));
            }

            try {
               jmsListener = (JMSSystemMessageListener)this.getMessageListener();
            } catch (ClassCastException var22) {
               throw new IllegalStateException(JMSClientExceptionLogger.logListenerExistsLoggable().getMessage());
            }

            if (jmsListener == null) {
               if (!this.session.prefetchDisabled()) {
                  int prefetchMode = this.session.getConnection().getSynchronousPrefetchMode();
                  if (this.destination.isTopic() && prefetchMode > 0 || this.destination.isQueue() && prefetchMode == 1 || MESSAGE_PREFETCH_2 != null) {
                     if (this.session.prefetchStarted()) {
                        throw new UnsupportedOperationException(JMSClientExceptionLogger.logMultiplePrefetchConsumerPerSessionLoggable().getMessage());
                     }

                     if (!this.session.isTransacted() && this.session.userTransactionsEnabled() && TransactionHelper.getTransactionHelper().getTransaction() != null) {
                        this.session.disablePrefetch();
                     }

                     if (this.session.consumersCount() > 1) {
                        this.session.disablePrefetch();
                     }

                     if (!this.session.prefetchDisabled()) {
                        this.setMessageListener((MessageListener)(jmsListener = new JMSSystemMessageListenerImpl2(this)));
                        this.session.startPrefetch();
                     }
                  }
               }
            } else {
               if (!this.session.prefetchStarted()) {
                  throw new IllegalStateException(JMSClientExceptionLogger.logListenerExistsLoggable().getMessage());
               }

               if (!this.session.isTransacted() && this.session.userTransactionsEnabled() && TransactionHelper.getTransactionHelper().getTransaction() != null) {
                  throw new UnsupportedOperationException(JMSClientExceptionLogger.logUserTXNotSupportPrefetchConsumerPerSessionLoggable().getMessage());
               }
            }

            this.session.setState(2);
         }
      }

      this.session.XABegin();

      MessageImpl message;
      try {
         if (jmsListener != null) {
            message = (MessageImpl)((JMSSystemMessageListener)jmsListener).receive(timeout, bodyClass);
            MessageImpl var7 = this.session.afterReceive(message, this.consumerId, listener);
            return var7;
         }

         message = this.session.receiveMessage(this, timeout, listener, bodyClass);
      } catch (Exception var23) {
         throw JMSSession.handleException(var23);
      } finally {
         try {
            this.session.clearState(2);
         } catch (Exception var21) {
         }

         this.session.XAFinish();
      }

      return message;
   }

   public void isCloseAllowed(String operation) throws JMSException {
   }

   public final void close() throws JMSException {
      this.close(-1L);
   }

   final void close(long sequenceNumber) throws JMSException {
      this.session.consumerClose(this, sequenceNumber);
      if (this.messageListenerContext != null && this.messageListenerContext.getMessageListener() instanceof JMSSystemMessageListener) {
         this.session.markAsSystemMessageListener(false);
      }

      if (this.isDurable()) {
         this.removeDurableConsumer();
      }

   }

   private synchronized void checkClosed() throws JMSException {
      if (this.isClosed()) {
         throw new IllegalStateException(JMSClientExceptionLogger.logClosedConsumerLoggable().getMessage());
      }
   }

   public final void publicCheckClosed() throws JMSException {
      this.checkClosed();
   }

   public final void removeDurableConsumer() {
      if (this.session.getConnection() != null) {
         this.session.getConnection().removeDurableSubscriber(this.subscriptionName);
      }

   }

   public final Topic getTopic() throws JMSException {
      this.checkClosed();
      return this.destination;
   }

   public final boolean privateGetNoLocal() {
      return this.noLocal;
   }

   public final boolean getNoLocal() throws JMSException {
      this.checkClosed();
      return this.noLocal;
   }

   public final int getSubscriptionSharingPolicy() throws JMSException {
      this.checkClosed();
      return this.session.getSubscriptionSharingPolicy();
   }

   public final Queue getQueue() throws JMSException {
      this.checkClosed();
      return this.destination;
   }

   private int pushException(Request invocableRequest) {
      JMSPushExceptionRequest request = (JMSPushExceptionRequest)invocableRequest;
      JMSException jmse = request.getException();
      if (jmse instanceof ConsumerClosedException) {
         ((ConsumerClosedException)jmse).setConsumer(this);
      }

      if (this.isDurable()) {
         this.removeDurableConsumer();
      }

      try {
         synchronized(this) {
            this.setClosed(true);
         }

         this.session.onException(jmse);
      } catch (Throwable var7) {
         JMSClientExceptionLogger.logStackTrace(var7);
      }

      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   public final int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 15366:
            return this.pushException(request);
         default:
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoSuchMethod4Loggable(request.getMethodId()));
      }
   }

   private static ExpressionEvaluator createExpressionEvaluator(String selector) throws weblogic.jms.common.JMSException, InvalidSelectorException {
      Class c;
      try {
         c = Class.forName("weblogic.utils.expressions.ExpressionParser");
      } catch (ClassNotFoundException var6) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logMulticastSelectorsLoggable());
      }

      try {
         Object parser = c.newInstance();
         Method parseMethod = c.getMethod("parse", String.class);
         ExpressionEvaluator ret = (ExpressionEvaluator)parseMethod.invoke(parser, selector);
         return ret;
      } catch (IllegalAccessException var7) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInternalErrorLoggable(var7));
      } catch (NoSuchMethodException var8) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInternalError2Loggable(var8));
      } catch (InstantiationException var9) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInternalError3Loggable(var9));
      } catch (InvocationTargetException var10) {
         Throwable target = var10.getTargetException();
         String msg;
         if (target instanceof ExpressionParserException) {
            msg = target.getMessage();
         } else {
            msg = JMSClientExceptionLogger.logInvalidSelectorLoggable(target).getMessage();
         }

         throw new InvalidSelectorException(msg);
      }
   }

   static {
      try {
         MESSAGE_PREFETCH_2 = System.getProperty("weblogic.jms.MessagePrefetch2");
      } catch (RuntimeException var1) {
         var1.printStackTrace();
      }

   }
}
