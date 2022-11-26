package weblogic.jms.client;

import java.io.IOException;
import java.io.Serializable;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.jms.BytesMessage;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageFormatException;
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
import javax.naming.Context;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import org.w3c.dom.Document;
import weblogic.common.internal.PeerInfo;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.integration.DiagnosticIntegrationManager;
import weblogic.diagnostics.integration.DiagnosticIntegrationManager.Factory;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.common.AlreadyClosedException;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.Destination;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSConsumerReceiveResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.JMSSessionRecoverResponse;
import weblogic.jms.common.JMSWorkContextHelper;
import weblogic.jms.common.JMSWorkManager;
import weblogic.jms.common.LostServerException;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageList;
import weblogic.jms.common.MessageReference;
import weblogic.jms.common.TextMessageImpl;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.extensions.DataOverrunException;
import weblogic.jms.extensions.JMSMessageFactoryImpl;
import weblogic.jms.extensions.WLAcknowledgeInfo;
import weblogic.jms.extensions.WLAsyncSession;
import weblogic.jms.extensions.WLMessageFactory;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.XMLMessage;
import weblogic.jms.frontend.FEBrowserCloseRequest;
import weblogic.jms.frontend.FEBrowserCreateRequest;
import weblogic.jms.frontend.FEConsumerCloseRequest;
import weblogic.jms.frontend.FEConsumerCreateRequest;
import weblogic.jms.frontend.FEConsumerCreateResponse;
import weblogic.jms.frontend.FEConsumerIncrementWindowCurrentOneWayRequest;
import weblogic.jms.frontend.FEConsumerIncrementWindowCurrentRequest;
import weblogic.jms.frontend.FEConsumerReceiveRequest;
import weblogic.jms.frontend.FEDestinationCreateRequest;
import weblogic.jms.frontend.FEProducerCloseRequest;
import weblogic.jms.frontend.FEProducerCreateRequest;
import weblogic.jms.frontend.FEProducerCreateResponse;
import weblogic.jms.frontend.FESessionAcknowledgeRequest;
import weblogic.jms.frontend.FESessionCloseRequest;
import weblogic.jms.frontend.FESessionRecoverRequest;
import weblogic.jms.frontend.FESessionSetRedeliveryDelayRequest;
import weblogic.jms.frontend.FETemporaryDestinationCreateRequest;
import weblogic.jms.frontend.FETemporaryDestinationCreateResponse;
import weblogic.jms.multicast.JMSTDMSocket;
import weblogic.jms.multicast.JMSTDMSocketIPM;
import weblogic.jms.multicast.JMSTMSocket;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.ID;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.dispatcher.CompletionListener;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.InterposedTransactionManagerXAResource;
import weblogic.transaction.TransactionHelper;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class JMSSession implements SessionInternal, Invocable, Reconnectable, Cloneable, WLAsyncSession, MMessageAsyncSession {
   private static final AbstractSubject KERNEL_ID = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static final WLMessageFactory MESSAGEFACTORY = JMSMessageFactoryImpl.getFactory();
   private static int DUPSACKINTERVAL = 50;
   private static boolean IGNOREWINDOWCURRENT = false;
   private static boolean DECWINDOWCURRENT = true;
   private SubjectManager subjectManager = SubjectManager.getSubjectManager();
   private long expectedSequenceNumber = 1L;
   private long highMark = 1L;
   private JMSPushRequest firstPushRequest;
   private JMSPushRequest lastPushRequest;
   private JMSPushRequest firstReceivePushRequest;
   private JMSPushRequest lastReceivePushRequest;
   private UnackedMessage firstUnackedMessage;
   private PendingWTMessage firstPendingWTMessage;
   private volatile JMSID sessionId;
   private int messagesMaximum;
   private int overrunPolicy;
   private final int acknowledgePolicy;
   private final int subscriptionSharingPolicy;
   private int pushRequestCount;
   private final JMSConnection connection;
   private final boolean transacted;
   private final int acknowledgeMode;
   private int deliveryMode;
   private int priority;
   private long timeToLive;
   private final long sendTimeout;
   private boolean userTransactionsEnabled;
   private final boolean allowCloseInOnMessage;
   private long redeliveryDelay = -1L;
   private final String clientID;
   private boolean decrementWindow;
   private final WorkManager dispatchWorkManager;
   private final String dispatchPolicyName;
   private final Object lockObject = new Object();
   private boolean synchronousListener = false;
   private JMSPushRequest shortCutPushRequest = null;
   private boolean needToRemoveIt = true;
   private JMSPushRequest shortCutPrevPushRequest = null;
   private boolean waitForNewMessage = false;
   private boolean notifyNewMessage = false;
   private final Object synchronousListenerObject = new Object();
   private LockedMap consumers = new LockedMap("consumers", (Object)null);
   private LockedMap producers = new LockedMap("producers", (Object)null);
   private LockedMap browsers = new LockedMap("browsers", (Object)null);
   private static final int IDLE = 0;
   private static final int IN_CLOSE = 1;
   static final int IN_RECEIVE = 2;
   private static final int IN_LISTENER = 4;
   static final int TYPE_UNSPECIFIED = 0;
   static final int TYPE_TOPIC = 1;
   static final int TYPE_QUEUE = 2;
   private JMSMessageContext messageContext;
   private JMSExceptionContext exceptionContext;
   private JMSException deferredException;
   private int consumerListenerCount;
   private int state = 0;
   private Thread listenerThread;
   private String runtimeMBeanName;
   private int waiterCount;
   private boolean stopped;
   private boolean running;
   private boolean recovering;
   private int type;
   private long lastSequenceNumber;
   private long firstSequenceNumberNotSeen = 0L;
   private MessageList clientAckList;
   private JMSPushRequest recoverableClientAckMessages;
   private JMSPushRequest carryForwardOnReconnect;
   private JMSMessageReference mRefCache;
   private JMSTDMSocket dgmSock;
   private JMSTMSocket mSock;
   private int pipelineGeneration;
   private String unitOfOrder;
   private UseForRunnable useForRunnable;
   private AsyncSendCallbackRunnable asyncSendCallbackRunner;
   private WLSessionImpl wlSessionImpl;
   private JMSSession replacementSession;
   private boolean pendingWork;
   private volatile boolean refreshedWithPendingWork;
   private boolean prefetchStarted = false;
   private boolean prefetchDisabled = false;
   private int dupsOKAckCountDown;
   private int dupsOKAckCount = 0;
   private boolean allowDelayAckForDupsOK = true;
   private boolean requireAckForDupsOK = false;
   private boolean session_clientResponsibleForAck = false;
   private boolean connectionOlderThan90;
   private boolean recoversFor90HasBeenCalled = false;
   private JMSMessageId lastExposedMsgId;
   private JMSMessageId previousExposedMsgId;
   private JMSMessageId lastAckMsgId;
   private long lastAckSequenceNumber;
   private HashMap replacementConsumerMap;
   private boolean consumersReconnect;
   private boolean closeStarted;
   private JMSSession staleJMSSession;
   private static boolean IGNORE_JmsAsyncQueue = false;
   private boolean ignoreJmsAsyncQueue = false;
   protected XAResource xaRes;
   private DiagnosticIntegrationManager integrationManager;
   private MMessageListener mmListener;
   private long realLastSequenceNumber;
   private boolean realLastSequenceNumberShouldApply = false;
   private int proxyGenerationForOlderServer = 0;
   private boolean closeWithExternalSequenceNumber = false;
   static final TextMessageImpl ASYNC_RESERVED_MSG;
   private boolean isRemoteDomain;
   private static final AuthenticatedSubject kernelId;
   int checkSeqGap = 1;
   int msgIndex = 1;

   protected JMSSession(JMSConnection connection, boolean transacted, int acknowledgeMode, boolean stopped) throws JMSException {
      this.connection = connection;
      this.transacted = transacted;
      this.useForRunnable = new UseForRunnable(this);
      if (transacted) {
         this.acknowledgeMode = 2;
      } else {
         this.acknowledgeMode = acknowledgeMode;
      }

      this.stopped = stopped;
      this.userTransactionsEnabled = connection.getUserTransactionsEnabled();
      this.allowCloseInOnMessage = connection.getAllowCloseInOnMessage();
      this.messagesMaximum = connection.getMessagesMaximum();
      this.deliveryMode = connection.getDeliveryMode();
      this.priority = connection.getPriority();
      this.timeToLive = connection.getTimeToLive();
      this.acknowledgePolicy = connection.getAcknowledgePolicy();
      this.subscriptionSharingPolicy = connection.getSubscriptionSharingPolicyAsInt();
      this.overrunPolicy = connection.getOverrunPolicy();
      this.clientID = connection.getClientIDInternal();
      this.sendTimeout = connection.getSendTimeout();
      this.pipelineGeneration = connection.getPipelineGeneration();
      this.dispatchPolicyName = connection.getDispatchPolicy();
      this.dispatchWorkManager = WorkManagerFactory.getInstance().find(this.dispatchPolicyName);
      this.asyncSendCallbackRunner = new AsyncSendCallbackRunnable(KernelStatus.isServer() ? this.dispatchWorkManager : null);
      if (IGNORE_JmsAsyncQueue && this.dispatchPolicyName.equals("JmsAsyncQueue")) {
         this.ignoreJmsAsyncQueue = true;
      }

      if (this.userTransactionsEnabled) {
         this.allowDelayAckForDupsOK = false;
      }

      this.dupsOKAckCountDown = this.messagesMaximum == -1 && this.messagesMaximum > 100 ? DUPSACKINTERVAL : this.messagesMaximum / 2;
      this.dupsOKAckCount = this.dupsOKAckCountDown;

      try {
         if (System.getProperty("weblogic.jms.dupsOKCountDownSize") != null) {
            this.dupsOKAckCountDown = Integer.parseInt(System.getProperty("weblogic.jms.dupsOKCountDownSize"));
         }
      } catch (AccessControlException var7) {
      }

      this.connectionOlderThan90 = connection.getPeerVersion() < 5;
      if (this.pipelineGeneration == 0) {
         if (PeerInfo.VERSION_DIABLO.compareTo(this.getFEPeerInfo()) <= 0 && PeerInfo.VERSION_920.compareTo(this.getFEPeerInfo()) > 0) {
            this.pipelineGeneration = 15728640;
         }

         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSSession (id: " + this.sessionId + ") : zero pipelineGeneration 0x" + Integer.toHexString(this.pipelineGeneration) + " peerinfo " + this.getFEPeerInfo());
         }
      }

      if (KernelStatus.isServer()) {
         this.integrationManager = getDiagnosticIntegrationManager();
      }

      try {
         this.isRemoteDomain = CrossDomainSecurityManager.getCrossDomainSecurityUtil().isRemoteDomain(connection.getFrontEndDispatcher());
      } catch (IOException var6) {
      }

   }

   public Object clone() throws CloneNotSupportedException {
      JMSSession jmsSession = (JMSSession)super.clone();
      jmsSession.consumers = (LockedMap)this.consumers.clone();
      jmsSession.producers = (LockedMap)this.producers.clone();
      jmsSession.browsers = (LockedMap)this.browsers.clone();
      return jmsSession;
   }

   public ReconnectController getReconnectController() {
      return this.wlSessionImpl;
   }

   public Reconnectable getReconnectState(int policy) throws CloneNotSupportedException {
      JMSSession session = (JMSSession)this.clone();
      if (WLConnectionImpl.reconnectPolicyHas(4, policy)) {
         session.producers = JMSConnection.recurseGetReconnectState(session.producers, policy);
      } else {
         session.producers = JMSConnection.recurseSetNoRetry(session.producers, this.connection);
      }

      session.staleJMSSession = this;
      if (WLConnectionImpl.reconnectPolicyHas(8, policy)) {
         session.consumers = JMSConnection.recurseGetReconnectState(session.consumers, policy);
         session.consumersReconnect = true;
         session.session_clientResponsibleForAck = this.session_clientResponsibleForAck;
      } else {
         if (ReconnectController.TODOREMOVEDebug && session.consumersCount() > 0) {
            System.out.println("DEBUG JMSSession reconnectState recurseSetNoRetry consumers ignored " + session.consumers);
         }

         session.consumers = JMSConnection.recurseSetNoRetry(session.consumers, this.connection);
         session.session_clientResponsibleForAck = session.consumersReconnect = false;
      }

      if (session.consumersReconnect && !this.transacted && this.firstUnackedMessage != null && this.acknowledgeMode == 2) {
         session.setPendingWork(true);
      }

      return session;
   }

   public Reconnectable preCreateReplacement(Reconnectable parent) throws JMSException {
      this.asyncSendCallbackRunner.onReconnect();
      JMSSession newSession = ((JMSConnection)parent).setupJMSSession(this.transacted, this.acknowledgeMode, this instanceof JMSXASession, this.type);
      newSession.setMapLocks(this.producers.getLock());
      newSession.exceptionContext = this.exceptionContext;
      newSession.messagesMaximum = this.messagesMaximum;
      newSession.overrunPolicy = this.overrunPolicy;
      newSession.deliveryMode = this.deliveryMode;
      newSession.priority = this.priority;
      newSession.timeToLive = this.timeToLive;
      newSession.synchronousListener = this.synchronousListener;
      newSession.unitOfOrder = this.unitOfOrder;
      if (this.redeliveryDelay != -1L && this.redeliveryDelay != newSession.redeliveryDelay) {
         newSession.setRedeliveryDelay(this.redeliveryDelay);
      }

      if (this.staleJMSSession != null) {
         if (this.transacted) {
            newSession.refreshedWithPendingWork = this.staleJMSSession.pendingWork;
         } else {
            newSession.refreshedWithPendingWork = this.staleJMSSession.firstUnackedMessage != null;
         }
      }

      newSession.subjectManager = this.subjectManager;
      newSession.consumersReconnect = this.consumersReconnect;
      newSession.replacementConsumerMap = new HashMap();
      JMSConnection.recursePreCreateReplacement(newSession, this.consumers);
      JMSConnection.recursePreCreateReplacement(newSession, this.producers);
      this.transferClientRspForAckMessages(newSession);
      newSession.replacementConsumerMap.clear();
      this.replacementSession = newSession;
      return newSession;
   }

   String debugMaps() {
      return this.producers + "\n" + this.consumers + "\n" + this.browsers;
   }

   public void postCreateReplacement() {
      JMSConnection.recursePostCreateReplacement(this.consumers);
      JMSConnection.recursePostCreateReplacement(this.producers);
      JMSSession replacementSessionLocal = this.replacementSession;
      replacementSessionLocal.setWLSessionImpl(this.wlSessionImpl);
      replacementSessionLocal.forgetReconnectState();
      this.wlSessionImpl.setPhysicalReconnectable(replacementSessionLocal);
   }

   public void forgetReconnectState() {
      this.staleJMSSession = null;
      Iterator iterator = this.producers.cloneValuesIterator();

      Object next;
      while(iterator.hasNext()) {
         next = iterator.next();
         if (next instanceof Reconnectable) {
            ((Reconnectable)next).forgetReconnectState();
         }
      }

      iterator = this.consumers.cloneValuesIterator();

      while(iterator.hasNext()) {
         next = iterator.next();
         if (next instanceof Reconnectable) {
            ((Reconnectable)next).forgetReconnectState();
         }
      }

      this.replacementSession = null;
   }

   boolean hasTemporaryDestination() {
      Iterator iterator = this.consumers.valuesIterator();

      WLSessionImpl stableWLSession;
      while(iterator.hasNext()) {
         if (((JMSConsumer)iterator.next()).hasTemporaryDestination()) {
            stableWLSession = this.wlSessionImpl;
            return stableWLSession != null && stableWLSession.getState() != -2304 && stableWLSession.getState() != -1280;
         }
      }

      iterator = this.producers.valuesIterator();

      do {
         if (!iterator.hasNext()) {
            return false;
         }
      } while(!((JMSProducer)iterator.next()).hasTemporaryDestination());

      stableWLSession = this.wlSessionImpl;
      return stableWLSession != null && stableWLSession.getState() != -2304 && stableWLSession.getState() != -1280;
   }

   public PeerInfo getFEPeerInfo() {
      return this.connection.getFEPeerInfo();
   }

   public final synchronized int getMessagesMaximum() throws JMSException {
      this.checkClosed();
      return this.messagesMaximum;
   }

   public final synchronized void setMessagesMaximum(int messagesMaximum) throws JMSException {
      this.checkClosed();
      synchronized(this.lockObject) {
         if (messagesMaximum >= -1 && messagesMaximum != 0) {
            this.messagesMaximum = messagesMaximum;
         } else {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidMessagesMaximumValueLoggable());
         }
      }
   }

   public int getSubscriptionSharingPolicy() throws JMSException {
      this.checkClosed();
      return this.subscriptionSharingPolicy;
   }

   public final synchronized int getOverrunPolicy() throws JMSException {
      this.checkClosed();
      return this.overrunPolicy;
   }

   public final synchronized void setOverrunPolicy(int overrunPolicy) throws JMSException {
      this.checkClosed();
      if (overrunPolicy != 0 && overrunPolicy != 1) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidOverrunPolicyLoggable(overrunPolicy));
      } else {
         this.overrunPolicy = overrunPolicy;
      }
   }

   public final long getRedeliveryDelay() throws JMSException {
      this.checkClosed();
      return this.getRedeliveryDelayInternal();
   }

   private long getRedeliveryDelayInternal() {
      return this.redeliveryDelay == -1L ? this.getConnection().getRedeliveryDelay() : this.redeliveryDelay;
   }

   public final void setRedeliveryDelay(long redeliveryDelay) throws JMSException {
      this.checkClosed();
      if (redeliveryDelay < -1L) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidRedeliveryDelayLoggable().getMessage());
      } else {
         long origDelay = this.getRedeliveryDelayInternal();
         if (redeliveryDelay != origDelay && this.consumers.size() != 0) {
            this.redeliveryDelay = redeliveryDelay;
            Response var5 = this.connection.getFrontEndDispatcher().dispatchSync(new FESessionSetRedeliveryDelayRequest(this.sessionId, redeliveryDelay));
         }

      }
   }

   public final int getDeliveryMode() {
      return this.deliveryMode;
   }

   public final void setDeliveryMode(int deliveryMode) {
      this.deliveryMode = deliveryMode;
   }

   public final int getPriority() {
      return this.priority;
   }

   public final void setPriority(int priority) {
      this.priority = priority;
   }

   public final long getTimeToLive() {
      return this.timeToLive;
   }

   public final void setTimeToLive(long timeToLive) {
      this.timeToLive = timeToLive;
   }

   final long getSendTimeout() {
      return this.sendTimeout;
   }

   final void setId(JMSID sessionId) {
      this.sessionId = sessionId;
   }

   public final JMSID getJMSID() {
      return this.sessionId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.connection.getDispatcherPartition4rmic();
   }

   public final void setType(int type) {
      this.type = type;
   }

   public void setPipelineGeneration(int pipelineGeneration) {
      this.pipelineGeneration = pipelineGeneration;
   }

   public synchronized int getPipelineGenerationFromProxy() {
      return this.connectionOlderThan90 ? this.proxyGenerationForOlderServer : this.pipelineGeneration;
   }

   final int getType() {
      return this.type;
   }

   public final InvocableMonitor getInvocableMonitor() {
      return null;
   }

   final long getLastSequenceNumber() {
      return this.realLastSequenceNumberShouldApply ? this.realLastSequenceNumber : this.lastSequenceNumber;
   }

   final synchronized void setRealLastSequenceNumber(long sequenceNumber) {
      this.realLastSequenceNumber = sequenceNumber;
      this.realLastSequenceNumberShouldApply = true;
   }

   final boolean isTransacted() {
      return this.transacted;
   }

   final boolean userTransactionsEnabled() {
      return this.userTransactionsEnabled;
   }

   final void setUserTransactionsEnabled(boolean value) {
      this.userTransactionsEnabled = value;
   }

   public String getWLSServerName() {
      return this.connection.getWLSServerName();
   }

   public ClientRuntimeInfo getParentInfo() {
      return this.connection;
   }

   public final void setRuntimeMBeanName(String runtimeMBeanName) {
      this.runtimeMBeanName = runtimeMBeanName;
   }

   public final String getRuntimeMBeanName() {
      return this.runtimeMBeanName;
   }

   public final JMSConnection getConnection() {
      return this.connection;
   }

   public final String getPartitionName() {
      return this.connection.getPartitionName();
   }

   public final String toString() {
      return this.connection.getRuntimeMBeanName() + "." + this.getRuntimeMBeanName();
   }

   final MessageImpl receiveMessage(ConsumerInternal consumer, long timeout, CompletionListener appListener) throws JMSException {
      return this.receiveMessage(consumer, timeout, appListener, (Class)null);
   }

   final MessageImpl receiveMessage(ConsumerInternal consumer, long timeout, CompletionListener appListener, Class bodyClass) throws JMSException {
      if (bodyClass != null && appListener != null) {
         throw new weblogic.jms.common.JMSException("Unexpected non-null appListener non-null bodyClass passed to JMSSession.receiveMessage");
      } else {
         JMSID consumerId = consumer.getJMSID();
         Object result = null;
         long startTime = System.currentTimeMillis();
         long waitTime = timeout;
         long timeArg;
         synchronized(this) {
            while(true) {
               if (!this.stopped) {
                  synchronized(this.lockObject) {
                     JMSPushRequest prevPushRequest = null;

                     JMSPushRequest pushRequest;
                     for(pushRequest = this.firstReceivePushRequest; pushRequest != null; pushRequest = (JMSPushRequest)pushRequest.getNext()) {
                        JMSPushEntry pushEntry = pushRequest.getFirstPushEntry();
                        if (consumer.getJMSID().equals(pushEntry.getConsumerId())) {
                           if (bodyClass != null) {
                              MessageImpl m = pushRequest.getMessage();

                              try {
                                 if (m.getBody(bodyClass) == null) {
                                    throw new MessageFormatException(JMSClientExceptionLogger.logNoMessageBodyLoggable().getMessage());
                                 }
                              } catch (MessageFormatException var37) {
                                 this.rememberFirstSequenceNumberNotSeen(pushEntry.getFrontEndSequenceNumber());
                                 throw var37;
                              }
                           }

                           pushEntry = pushRequest.removePushEntry();
                           result = new JMSConsumerReceiveResponsePrivate(pushRequest.getMessage(), pushEntry.getFrontEndSequenceNumber(), pushEntry.isTransactional(), pushEntry.getDeliveryCount());
                           break;
                        }

                        prevPushRequest = pushRequest;
                     }

                     if (result != null && pushRequest.getFirstPushEntry() == null) {
                        JMSPushRequest nextPushRequest = (JMSPushRequest)pushRequest.getNext();
                        pushRequest.setNext((Request)null);
                        if (prevPushRequest == null) {
                           this.firstReceivePushRequest = nextPushRequest;
                        } else {
                           prevPushRequest.setNext(nextPushRequest);
                        }

                        if (nextPushRequest == null) {
                           this.lastReceivePushRequest = null;
                        }
                     }
                     break;
                  }
               }

               if (!this.isClosed() && timeout != 9223372036854775806L) {
                  if (this.firstReceivePushRequest != null) {
                     try {
                        this.wait(waitTime);
                     } catch (InterruptedException var38) {
                        throw new weblogic.jms.common.JMSException(var38);
                     }

                     if (timeout == Long.MAX_VALUE) {
                        continue;
                     }

                     timeArg = System.currentTimeMillis() - startTime;
                     if (timeArg >= timeout) {
                        return null;
                     }

                     waitTime = timeout - timeArg;
                     if (waitTime > 0L) {
                        continue;
                     }
                  }

                  if (!this.stopped && !this.isClosed()) {
                     continue;
                  }

                  return null;
               }

               return null;
            }
         }

         if (result != null) {
            return this.proccessReceiveResponse(consumer, result, appListener);
         } else {
            if (waitTime > 0L && timeout != Long.MAX_VALUE && timeout != 9223372036854775806L) {
               waitTime = timeout - (startTime - System.currentTimeMillis());
            } else {
               waitTime = timeout;
            }

            boolean noTran;
            if (!this.transacted && this.userTransactionsEnabled) {
               timeArg = timeout;
               noTran = false;
            } else {
               timeArg = waitTime;
               noTran = true;
            }

            FEConsumerReceiveRequest request = new FEConsumerReceiveRequest(consumerId, timeArg, appListener, consumer);
            if (appListener != null) {
               Transaction transaction;
               if (noTran) {
                  transaction = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();
               } else {
                  transaction = null;
               }

               TextMessageImpl var47;
               try {
                  this.connection.getFrontEndDispatcher().dispatchAsync(request);
                  var47 = ASYNC_RESERVED_MSG;
               } catch (DispatcherException var36) {
                  throw new weblogic.jms.common.JMSException(var36);
               } finally {
                  if (transaction != null) {
                     TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(transaction);
                  }

               }

               return var47;
            } else if (!(this instanceof JMSXASession) && this.connection.isWrappedIC() && TransactionHelper.getTransactionHelper().getTransaction() != null) {
               throw new IllegalStateException(JMSClientExceptionLogger.logUnexpectedTransactionLoggable().getMessage());
            } else {
               JMSConsumerReceiveResponse receiveResponse = (JMSConsumerReceiveResponse)((JMSConsumerReceiveResponse)(noTran ? this.connection.getFrontEndDispatcher().dispatchSyncNoTran(request) : this.connection.getFrontEndDispatcher().dispatchSyncTran(request)));
               MessageImpl origmsg = receiveResponse.getMessage();
               if (bodyClass != null && origmsg != null) {
                  try {
                     if (origmsg.getBody(bodyClass) == null) {
                        throw new MessageFormatException(JMSClientExceptionLogger.logNoMessageBodyLoggable().getMessage());
                     }
                  } catch (MessageFormatException var40) {
                     JMSPushEntry pushEntry = new JMSPushEntry((JMSID)null, consumer.getJMSID(), 0L, receiveResponse.getSequenceNumber(), origmsg.getDeliveryCount(), 0);
                     if (receiveResponse.isTransactional()) {
                        pushEntry.setTransactional();
                     }

                     pushEntry.setClientResponsibleForAcknowledge(origmsg.getClientResponsibleForAcknowledge());
                     JMSPushRequest pushRequest = new JMSPushRequest(0, this.sessionId, origmsg, pushEntry);
                     synchronized(this.lockObject) {
                        this.addPushRequests(pushRequest, true);
                     }

                     synchronized(this) {
                        this.rememberFirstSequenceNumberNotSeen(receiveResponse.getSequenceNumber());
                     }

                     throw var40;
                  }
               }

               return this.proccessReceiveResponse(consumer, receiveResponse, appListener);
            }
         }
      }
   }

   public MessageImpl proccessReceiveResponse(ConsumerInternal consumer, Object result, CompletionListener appListener) throws JMSException {
      JMSConsumerReceiveResponse receiveResponse = (JMSConsumerReceiveResponse)result;
      MessageImpl messageImpl = null;
      MessageImpl originalMessageImpl = receiveResponse.getMessage();
      if (originalMessageImpl != null) {
         this.session_clientResponsibleForAck |= originalMessageImpl.getClientResponsibleForAcknowledge();
         int deliveryCount;
         if (receiveResponse instanceof JMSConsumerReceiveResponsePrivate) {
            deliveryCount = ((JMSConsumerReceiveResponsePrivate)receiveResponse).getDeliveryCount();
         } else {
            deliveryCount = originalMessageImpl.getDeliveryCount();
         }

         if (this.connection.isLocal()) {
            messageImpl = originalMessageImpl.copy();
            messageImpl.setSequenceNumber(originalMessageImpl.getSequenceNumber());
            messageImpl.setClientResponsibleForAcknowledge(originalMessageImpl.getClientResponsibleForAcknowledge());
            messageImpl.setDeliveryCount(deliveryCount);
         } else {
            messageImpl = originalMessageImpl;
            originalMessageImpl.setDeliveryCount(deliveryCount);
         }

         long sequenceNumber = receiveResponse.getSequenceNumber();
         messageImpl.setSequenceNumber(sequenceNumber);
         messageImpl.setDDForwarded(false);
         if (this.transacted) {
            this.setPendingWork(true);
         }

         if (receiveResponse.isTransactional()) {
            synchronized(this) {
               this.rememberLastSequenceNumber(sequenceNumber, messageImpl.getId());
            }
         } else {
            synchronized(this) {
               this.rememberLastSequenceNumber(sequenceNumber, messageImpl.getId());
               this.addUnackedMessage(consumer, messageImpl);
            }

            if (this.acknowledgeMode == 2) {
               messageImpl.setSession(this);
            } else if (this.acknowledgeMode != 4) {
               if (this.acknowledgeMode == 3 && this.checkDelayAckForDupsOK(messageImpl) && --this.dupsOKAckCount > 0) {
                  this.requireAckForDupsOK = true;
               } else {
                  this.acknowledge(messageImpl, this.acknowledgePolicy, false);
                  this.requireAckForDupsOK = false;
                  this.dupsOKAckCount = this.dupsOKAckCountDown;
               }
            }
         }
      }

      if (messageImpl != null) {
         messageImpl.setJMSDestinationImpl((DestinationImpl)consumer.getDestination());
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSSession (id: " + this.sessionId + ") : Received message " + messageImpl.getJMSMessageID());
         }
      }

      return this.afterReceive(messageImpl, consumer.getJMSID(), appListener);
   }

   final MessageImpl afterReceive(MessageImpl message, JMSID consumerId, CompletionListener appListener) {
      if (message != null) {
         message.setSerializeDestination(true);
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSConsumer (id: " + consumerId + ") : Received message " + message.getJMSMessageID());
         }

         message.setForward(true);
         message.setSAFSequenceName((String)null);
         message.setSAFSeqNumber(0L);
      }

      if (appListener != null) {
         appListener.onCompletion(message);
      }

      return message;
   }

   private void setPendingWorkOnMsgRecv() {
      if (this.acknowledgeMode == 2) {
         this.setPendingWork(true);
      }

   }

   private void rememberLastSequenceNumber(long sequenceNumber, JMSMessageId msgId) {
      this.previousExposedMsgId = this.lastExposedMsgId;
      this.lastSequenceNumber = sequenceNumber;
      this.lastExposedMsgId = msgId;
      this.rememberFirstSequenceNumberNotSeen(0L);
   }

   private void rememberFirstSequenceNumberNotSeen(long sequenceNumber) {
      this.firstSequenceNumberNotSeen = sequenceNumber;
   }

   private long getFirstSequenceNumberNotSeen() {
      return this.firstSequenceNumberNotSeen;
   }

   void rememberLastServerAck(JMSMessageId msgId) {
      if (!this.transacted) {
         this.lastAckMsgId = msgId;
      }
   }

   public JMSMessageId getLastAckMsgId() {
      return this.lastAckMsgId;
   }

   public JMSMessageId getLastExposedMsgId() {
      return this.lastExposedMsgId;
   }

   public final BytesMessage createBytesMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createBytesMessage();
   }

   public final MapMessage createMapMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createMapMessage();
   }

   public final Message createMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createMessage();
   }

   public final ObjectMessage createObjectMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createObjectMessage();
   }

   public final ObjectMessage createObjectMessage(Serializable object) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createObjectMessage(object);
   }

   public final StreamMessage createStreamMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createStreamMessage();
   }

   public final TextMessage createTextMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createTextMessage();
   }

   public final TextMessage createTextMessage(String string) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createTextMessage(string);
   }

   public final XMLMessage createXMLMessage() throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createXMLMessage();
   }

   public final XMLMessage createXMLMessage(String string) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createXMLMessage(string);
   }

   public final XMLMessage createXMLMessage(Document doc) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createXMLMessage(doc);
   }

   public final TextMessage createTextMessage(StringBuffer stringBuffer) throws JMSException {
      this.checkClosed();
      return MESSAGEFACTORY.createTextMessage(stringBuffer);
   }

   public boolean getTransacted() throws JMSException {
      this.checkClosed();
      return this.transacted;
   }

   public final int getAcknowledgeMode() throws JMSException {
      this.checkClosed();
      return this.transacted ? 0 : this.acknowledgeMode;
   }

   public void commit() throws JMSException {
      this.commit(-1L);
   }

   synchronized void commit(long sequenceNumber) throws JMSException {
      this.checkClosed();
      if (sequenceNumber != -1L) {
         this.setRealLastSequenceNumber(sequenceNumber);
      }

      if (!this.transacted) {
         throw new IllegalStateException(JMSClientExceptionLogger.logNoTransaction3Loggable().getMessage());
      } else {
         this.checkOpPermissionForAsyncSend("Session.commit()");
         this.waitTillAllAsyncSendProcessed();
         this.firstUnackedMessage = null;
         this.firstPendingWTMessage = null;

         try {
            Object response = this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FESessionAcknowledgeRequest(this.getJMSID(), this.getLastSequenceNumber(), 1, true));
            this.decrementWindow = false;
         } catch (JMSException var7) {
            if ("ReservedRollbackOnly".equals(var7.getErrorCode())) {
               try {
                  this.recoverGuts(false, -1L);
               } catch (JMSException var6) {
               }
            }

            throw var7;
         }
      }
   }

   void rollback(long sequenceNumber) throws JMSException {
      if (!this.transacted) {
         throw new IllegalStateException(JMSClientExceptionLogger.logNoTransaction4Loggable().getMessage());
      } else {
         this.checkOpPermissionForAsyncSend("Session.rollback()");
         this.waitTillAllAsyncSendProcessed();
         this.recoverGuts(false, sequenceNumber);
      }
   }

   public void rollback() throws JMSException {
      this.rollback(-1L);
   }

   final void consumerIncrementWindowCurrent(final JMSID consumerId, final int windowIncrement, final boolean clientResponsibleForAcknowledge) throws JMSException {
      byte peerVersion = this.connection.getPeerVersion();
      if (peerVersion < 3) {
         Response var4 = this.connection.getFrontEndDispatcher().dispatchSync(new FEConsumerIncrementWindowCurrentRequest(consumerId, windowIncrement, clientResponsibleForAcknowledge));
      } else {
         final JMSConnection connectionFinal = this.connection;
         CrossDomainSecurityManager.doAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.getConnection().getFrontEndDispatcher(), CrossDomainSecurityManager.getCurrentSubject(), true), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               connectionFinal.getFrontEndDispatcher().dispatchNoReply(new FEConsumerIncrementWindowCurrentOneWayRequest(consumerId, windowIncrement, clientResponsibleForAcknowledge));
               return null;
            }
         });
      }

   }

   final void stop() throws JMSException {
      synchronized(this) {
         if (!this.stopped) {
            if (!this.inListener() || !this.allowCloseInOnMessage) {
               this.waitForState(-5);
            }

            if (!this.isClosed()) {
               this.stopped = true;
            }
         }
      }
   }

   final void start() throws JMSException {
      synchronized(this) {
         label51: {
            synchronized(this.lockObject) {
               if (!this.stopped || this.isClosed()) {
                  return;
               }

               this.stopped = false;
               this.notifyAll();
               if (!this.running && this.havePushRequests() || this.synchronousListener) {
                  this.running = true;
                  break label51;
               }
            }

            return;
         }
      }

      this.dispatchWorkManager.schedule(this.useForRunnable);
   }

   final void resume() throws JMSException {
      synchronized(this) {
         label45: {
            synchronized(this.lockObject) {
               this.stopped = false;
               if (this.havePushRequests() || this.synchronousListener) {
                  this.running = true;
                  break label45;
               }
            }

            return;
         }
      }

      this.dispatchWorkManager.schedule(this.useForRunnable);
   }

   public final void close() throws JMSException {
      this.close(-1L);
   }

   final void close(long sequenceNumber) throws JMSException {
      this.checkOpPermissionForAsyncSend("Session.close()");
      JMSID sessionId = null;
      boolean removeFromConnection = false;
      boolean var158 = false;

      label1498: {
         try {
            label1499: {
               var158 = true;
               synchronized(this) {
                  if (sequenceNumber == -1L) {
                     this.realLastSequenceNumberShouldApply = false;
                  } else {
                     this.setRealLastSequenceNumber(sequenceNumber);
                  }

                  this.closeStarted = true;
                  this.realLastSequenceNumberShouldApply = false;
                  synchronized(this.synchronousListenerObject) {
                     this.synchronousListenerObject.notifyAll();
                  }

                  this.asyncSendCallbackRunnerClosing();
                  this.waitTillAllAsyncSendProcessed();
                  if (!this.inListener() || !this.allowCloseInOnMessage) {
                     this.waitForState(-5);
                  }

                  if (this.isClosed()) {
                     var158 = false;
                     break label1499;
                  }

                  if (this.requireAckForDupsOK) {
                     this.acknowledge(true, false);
                  }

                  removeFromConnection = true;
                  sessionId = this.poisonSession();
                  if (this.mSock != null) {
                     this.mSock.close();
                     this.mSock = null;
                  }

                  this.poisonConsumersAndCloseLocaly();
                  this.poisonProducers();
                  this.poisonBrowsers();
                  this.setState(1);
               }

               this.dispatchSessionCloseRequest(sessionId);
               var158 = false;
               break label1498;
            }
         } finally {
            if (var158) {
               boolean var121 = false;

               try {
                  var121 = true;
                  synchronized(this) {
                     this.waitForState(-3);

                     try {
                        this.removeDurableConsumers();
                     } finally {
                        this.consumers.clear();
                        this.producers.clear();
                        this.browsers.clear();
                        this.firstUnackedMessage = null;
                        this.firstPendingWTMessage = null;
                        this.clientAckList = null;
                        this.clearState(1);
                        this.notify();
                     }

                     var121 = false;
                  }
               } finally {
                  if (var121) {
                     this.XAClose();
                     synchronized(this.synchronousListenerObject) {
                        this.synchronousListenerObject.notifyAll();
                     }

                     if (removeFromConnection) {
                        this.connection.sessionRemove(sessionId);
                     }

                  }
               }

               this.XAClose();
               synchronized(this.synchronousListenerObject) {
                  this.synchronousListenerObject.notifyAll();
               }

               if (removeFromConnection) {
                  this.connection.sessionRemove(sessionId);
               }

            }
         }

         boolean var66 = false;

         try {
            var66 = true;
            synchronized(this) {
               this.waitForState(-3);

               try {
                  this.removeDurableConsumers();
               } finally {
                  this.consumers.clear();
                  this.producers.clear();
                  this.browsers.clear();
                  this.firstUnackedMessage = null;
                  this.firstPendingWTMessage = null;
                  this.clientAckList = null;
                  this.clearState(1);
                  this.notify();
               }

               var66 = false;
            }
         } finally {
            if (var66) {
               this.XAClose();
               synchronized(this.synchronousListenerObject) {
                  this.synchronousListenerObject.notifyAll();
               }

               if (removeFromConnection) {
                  this.connection.sessionRemove(sessionId);
               }

            }
         }

         this.XAClose();
         synchronized(this.synchronousListenerObject) {
            this.synchronousListenerObject.notifyAll();
         }

         if (removeFromConnection) {
            this.connection.sessionRemove(sessionId);
         }

         return;
      }

      boolean var47 = false;

      try {
         var47 = true;
         synchronized(this) {
            this.waitForState(-3);

            try {
               this.removeDurableConsumers();
            } finally {
               this.consumers.clear();
               this.producers.clear();
               this.browsers.clear();
               this.firstUnackedMessage = null;
               this.firstPendingWTMessage = null;
               this.clientAckList = null;
               this.clearState(1);
               this.notify();
            }

            var47 = false;
         }
      } finally {
         if (var47) {
            this.XAClose();
            synchronized(this.synchronousListenerObject) {
               this.synchronousListenerObject.notifyAll();
            }

            if (removeFromConnection) {
               this.connection.sessionRemove(sessionId);
            }

         }
      }

      this.XAClose();
      synchronized(this.synchronousListenerObject) {
         this.synchronousListenerObject.notifyAll();
      }

      if (removeFromConnection) {
         this.connection.sessionRemove(sessionId);
      }

   }

   private JMSID poisonSession() {
      Object lock = this.wlSessionImpl == null ? this : this.wlSessionImpl.getConnectionStateLock();
      synchronized(lock) {
         JMSID sessionId = this.sessionId;
         this.sessionId = null;
         return sessionId;
      }
   }

   public void isCloseAllowed(String operation) throws JMSException {
      if (!this.isOperationAllowed()) {
         throw new IllegalStateException(JMSClientExceptionLogger.logInvalidCloseFromListenerLoggable(operation, "Session").getMessage());
      }
   }

   boolean isOperationAllowed() {
      return this.allowCloseInOnMessage || !this.inListener();
   }

   private void removeDurableConsumers() {
      Iterator iterator = this.consumers.cloneValuesIterator();

      while(iterator.hasNext()) {
         ConsumerInternal consumer = (ConsumerInternal)iterator.next();
         if (consumer.isDurable()) {
            consumer.removeDurableConsumer();
         }
      }

   }

   private void poisonBrowsers() {
      Iterator iterator = this.browsers.cloneValuesIterator();

      while(iterator.hasNext()) {
         JMSQueueBrowser browser = (JMSQueueBrowser)iterator.next();
         browser.setId((JMSID)null);
      }

   }

   private void poisonConsumersAndCloseLocaly() throws JMSException {
      Iterator iterator = this.consumers.cloneValuesIterator();

      while(iterator.hasNext()) {
         ConsumerInternal consumer = (ConsumerInternal)iterator.next();
         this.consumerCloseLocal(consumer, false);
      }

   }

   private void poisonProducers() {
      Iterator iterator = this.producers.cloneValuesIterator();

      while(iterator.hasNext()) {
         JMSProducer producer = (JMSProducer)iterator.next();
         producer.setId((JMSID)null);
      }

   }

   private void dispatchSessionCloseRequest(JMSID sessionId) throws JMSException {
      if (this.connection.isConnected()) {
         Object response = this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FESessionCloseRequest(sessionId, this.getLastSequenceNumber()));
      }
   }

   private void waitForOutstandingReceives() throws JMSException {
      try {
         this.setState(1);
         this.waitForState(-3);
      } finally {
         this.clearState(1);
      }

   }

   public final void recover() throws JMSException {
      this.recover(-1L);
   }

   final void recover(long sequenceNumber) throws JMSException {
      if (this.transacted) {
         throw new IllegalStateException(JMSClientExceptionLogger.logTransactedLoggable().getMessage());
      } else {
         this.recoverGuts(true, sequenceNumber);
      }
   }

   private void recoverGuts(boolean sessionRecover, long sequenceNumber) throws JMSException {
      this.checkClosed();
      if (this.connection.getPeerVersion() < 5) {
         this.recoverGuts81(sessionRecover, sequenceNumber);
      } else {
         this.recoverGuts90(sessionRecover, sequenceNumber);
      }

   }

   private final synchronized void recoverGuts81(boolean sessionRecover, long seq) throws JMSException {
      if (seq != -1L) {
         this.setRealLastSequenceNumber(seq);
      }

      if (this.requireAckForDupsOK) {
         this.acknowledge(false, false);
      }

      this.decrementWindow = false;
      long sendSequenceNumber = this.getLastSequenceNumber();
      this.rememberLastSequenceNumber(0L, (JMSMessageId)null);
      boolean doFERecover = this.transacted || this.firstUnackedMessage != null;
      ConsumerInternal consumer;
      if (doFERecover) {
         Object response = this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FESessionRecoverRequest(this.getJMSID(), sendSequenceNumber, !sessionRecover, this.pipelineGeneration));
         this.setExpectedSequenceNumber81(((JMSSessionRecoverResponse)response).getSequenceNumber(), false);
         Iterator iterator = this.consumers.cloneValuesIterator();

         while(iterator.hasNext()) {
            consumer = (ConsumerInternal)iterator.next();
            consumer.setWindowCurrent(consumer.getWindowMaximum());
            consumer.setExpectedSequenceNumber(this.expectedSequenceNumber);
         }

         this.firstUnackedMessage = null;
         this.firstPendingWTMessage = null;
      }

      MessageList listCopy = this.clientAckList;
      if (listCopy != null) {
         this.clientAckList = null;
         JMSPushRequest pushRequest = null;
         JMSPushRequest newPushRequest = null;
         JMSPushRequest firstPushRequest = null;
         long sequenceNumber = this.expectedSequenceNumber;

         JMSMessageReference mRef;
         for(mRef = (JMSMessageReference)listCopy.getLast(); mRef != null; mRef = (JMSMessageReference)mRef.getPrev()) {
            consumer = mRef.getConsumer();
            sequenceNumber = mRef.getSequenceNumber();
            consumer.setExpectedSequenceNumber(sequenceNumber, true);
         }

         this.setExpectedSequenceNumber81(sequenceNumber, true);

         for(mRef = (JMSMessageReference)listCopy.getFirst(); mRef != null; mRef = (JMSMessageReference)mRef.getNext()) {
            MessageImpl message = mRef.getMessage();
            message.incrementDeliveryCount();
            message = mRef.getMessage();
            message.reset();
            message.setPropertiesWritable(false);
            newPushRequest = new JMSPushRequest(0, (JMSID)null, message);
            if (pushRequest != null) {
               pushRequest.setNext(newPushRequest);
            } else {
               firstPushRequest = newPushRequest;
            }

            pushRequest = newPushRequest;
            newPushRequest = null;
            consumer = mRef.getConsumer();
            long messageSequenceNumber = mRef.getSequenceNumber();
            JMSPushEntry pushEntry = new JMSPushEntry((JMSID)null, consumer.getJMSID(), 0L, messageSequenceNumber, message.getDeliveryCount(), 2097152);
            pushEntry.setClientResponsibleForAcknowledge(true);
            pushRequest.setPushEntries(pushEntry);
         }

         if (firstPushRequest != null) {
            this.pushMessage(firstPushRequest, this.consumerListenerCount == 0);
         }
      }

      if (this.inState(4)) {
         this.recovering = true;
      }

      if (this.proxyGenerationForOlderServer == Integer.MAX_VALUE) {
         this.proxyGenerationForOlderServer = 0;
      } else {
         ++this.proxyGenerationForOlderServer;
      }

   }

   private final void recoverGuts90(boolean sessionRecover, long sequenceNumber) throws JMSException {
      FESessionRecoverRequest feSessionRecoverRequest;
      synchronized(this) {
         if (sequenceNumber != -1L) {
            this.setRealLastSequenceNumber(sequenceNumber);
         }

         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSSession (id: " + this.sessionId + ") : Recover msg " + this.getLastSequenceNumber() + ", info " + (this.firstUnackedMessage == null) + "/" + (this.clientAckList == null) + "/" + this.transacted);
         }

         if (this.requireAckForDupsOK) {
            this.acknowledge(false, false);
         }

         long sendLastSequenceNumber = this.getLastSequenceNumber();
         this.rememberLastSequenceNumber(0L, (JMSMessageId)null);
         if (this.acknowledgeMode == 4 || this.acknowledgeMode == 128) {
            return;
         }

         if (this.inState(4)) {
            this.recovering = true;
         }

         this.decrementWindow = false;
         synchronized(this.lockObject) {
            int old = this.pipelineGeneration;
            this.pipelineGeneration = JMSPushEntry.nextRecoverGeneration(this.pipelineGeneration);
            this.recoversFor90HasBeenCalled = true;
            JMSPushRequest current;
            if (this.session_clientResponsibleForAck) {
               current = this.firstPushRequest;
               this.setFirstPushRequest(this.lastPushRequest = null);
               this.pushRequestCount = 0;
               this.restoreClientAckMessages(sendLastSequenceNumber, current);
            } else {
               for(current = this.firstPushRequest; current != null; current = (JMSPushRequest)current.getNext()) {
                  if (this.connection.isLocal() && current.getFirstPushEntry().getPipelineGeneration() == 0) {
                     current.getFirstPushEntry().setPipelineGeneration(1048576);
                  }
               }
            }

            Iterator iterator = this.consumers.cloneValuesIterator();

            while(iterator.hasNext()) {
               ConsumerInternal consumer = (ConsumerInternal)iterator.next();
               consumer.setWindowCurrent(consumer.getWindowMaximum());
            }
         }

         this.firstUnackedMessage = null;
         this.firstPendingWTMessage = null;
         feSessionRecoverRequest = new FESessionRecoverRequest(this.getJMSID(), sendLastSequenceNumber, !sessionRecover, this.pipelineGeneration);
      }

      Object response = this.connection.getFrontEndDispatcher().dispatchSyncNoTran(feSessionRecoverRequest);
   }

   private void restoreClientAckMessages(long recoveredSeqNum, JMSPushRequest oldPushRequest) {
      MessageList listCopy = this.clientAckList;
      this.clientAckList = null;
      ConsumerInternal consumer = null;
      JMSID lastConsumerID = null;
      if (listCopy != null) {
         for(JMSMessageReference mRef = (JMSMessageReference)listCopy.getFirst(); mRef != null; mRef = (JMSMessageReference)mRef.getNext()) {
            if (!mRef.getConsumer().isClosed()) {
               if (!mRef.getConsumer().getJMSID().equals(lastConsumerID)) {
                  lastConsumerID = mRef.getConsumer().getJMSID();
                  consumer = this.consumerFind(lastConsumerID);
               }

               if (consumer != null) {
                  MessageImpl message = mRef.getMessage();

                  try {
                     message.setMessageReference((MessageReference)null);
                     message = message.copy();
                     message.setMessageReference(mRef);
                     mRef.setMessage(message);
                     message.reset();
                     message.setPropertiesWritable(false);
                  } catch (JMSException var10) {
                  }

                  if (mRef.getSequenceNumber() <= recoveredSeqNum) {
                     mRef.incrementDeliveryCount();
                  }

                  JMSPushEntry pushEntry = new JMSPushEntry(this.getJMSID(), mRef.getConsumer().getJMSID(), 0L, mRef.getSequenceNumber(), mRef.getDeliveryCount(), 2097152);
                  pushEntry.setClientResponsibleForAcknowledge(true);
                  this.fabricateClientAckPushEntry(consumer, message, pushEntry);
               }
            }
         }
      }

      JMSPushRequest current;
      JMSPushEntry pushEntry;
      for(current = this.recoverableClientAckMessages; current != null; current = (JMSPushRequest)current.getNext()) {
         for(pushEntry = current.removePushEntry(); pushEntry != null; pushEntry = pushEntry.getNext()) {
            if (pushEntry.getClientResponsibleForAcknowledge()) {
               if (!pushEntry.getConsumerId().equals(lastConsumerID)) {
                  lastConsumerID = pushEntry.getConsumerId();
                  consumer = this.consumerFind(lastConsumerID);
               }

               if (consumer != null) {
                  this.fabricateClientAckPushEntry(consumer, current.getMessage(), pushEntry);
               }
            }
         }
      }

      for(current = oldPushRequest; current != null; current = (JMSPushRequest)current.getNext()) {
         for(pushEntry = current.removePushEntry(); pushEntry != null; pushEntry = pushEntry.getNext()) {
            if (pushEntry.getClientResponsibleForAcknowledge()) {
               if (!pushEntry.getConsumerId().equals(lastConsumerID)) {
                  lastConsumerID = pushEntry.getConsumerId();
                  consumer = this.consumerFind(lastConsumerID);
               }

               if (consumer != null) {
                  this.fabricateClientAckPushEntry(consumer, current.getMessage(), pushEntry);
               }
            }
         }
      }

   }

   private void fabricateClientAckPushEntry(ConsumerInternal consumer, MessageImpl message, JMSPushEntry pushEntry) {
      pushEntry.setPipelineGeneration(2097152);
      JMSPushRequest pushRequest = new JMSPushRequest(0, this.getJMSID(), message, pushEntry);
      if (consumer.getMessageListenerContext() == null) {
         this.addPushRequests(pushRequest, true);
         if (consumer.getExpectedSequenceNumber() > this.firstReceivePushRequest.getFrontEndSequenceNumber()) {
            consumer.setExpectedSequenceNumber(this.firstReceivePushRequest.getFrontEndSequenceNumber());
         }

      } else {
         if (this.lastPushRequest != null) {
            this.lastPushRequest.setNext(pushRequest);
         } else {
            this.setFirstPushRequest(pushRequest);
         }

         this.lastPushRequest = pushRequest;
         ++this.pushRequestCount;
      }
   }

   private boolean duplicateMessage(MessageImpl msg, JMSID consumerId, MessageList listCopy) {
      if (listCopy != null) {
         for(JMSMessageReference mref = (JMSMessageReference)listCopy.getFirst(); mref != null; mref = (JMSMessageReference)mref.getNext()) {
            if (mref.getMessage() == msg) {
               return true;
            }
         }
      }

      for(JMSPushRequest pushRequest = this.firstPushRequest; pushRequest != null; pushRequest = (JMSPushRequest)pushRequest.getNext()) {
         if (msg == pushRequest.getMessage()) {
            return true;
         }

         for(JMSPushEntry pushEntry = pushRequest.getFirstPushEntry(); pushEntry != null; pushEntry = pushEntry.getNext()) {
            if (consumerId.equals(pushEntry.getConsumerId()) && msg.getId().equals(pushRequest.getMessage().getId())) {
               return true;
            }
         }
      }

      return false;
   }

   public final void acknowledge() throws JMSException {
      this.acknowledge((WLAcknowledgeInfo)null, 1, false);
   }

   public final void acknowledge(Message message) throws JMSException {
      this.throwForAckRefreshedSessionRules();
      this.acknowledge((WLAcknowledgeInfo)message, this.acknowledgePolicy, false);
   }

   private void acknowledge(boolean forClose, boolean clientResponsibleForAcknowledge) throws JMSException {
      synchronized(this) {
         long sequenceNumber;
         JMSMessageId lastExposed;
         if (this.inListener()) {
            sequenceNumber = this.getLastSequenceNumber() - 1L;
            lastExposed = this.previousExposedMsgId;
         } else {
            sequenceNumber = this.getLastSequenceNumber();
            lastExposed = this.lastExposedMsgId;
         }

         this.checkClosed();

         try {
            if (!clientResponsibleForAcknowledge) {
               this.rememberLastServerAck(lastExposed);
               if (!forClose || this.connection.isConnected()) {
                  this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FESessionAcknowledgeRequest(this.getJMSID(), sequenceNumber, 1, false));
               }

               this.rememberLastServerAck((JMSMessageId)null);
            }
         } catch (Exception var13) {
            handleException(var13);
         } finally {
            this.removePendingWTMessage(sequenceNumber, IGNOREWINDOWCURRENT);
            this.removeUnackedMessage(sequenceNumber, clientResponsibleForAcknowledge, false);
         }

      }
   }

   public final void acknowledge(WLAcknowledgeInfo ackInfo) throws JMSException {
      this.throwForAckRefreshedSessionRules();
      this.acknowledge(ackInfo, this.acknowledgePolicy, false);
   }

   void acknowledge(WLAcknowledgeInfo ackInfo, int acknowledgePolicy, boolean isInfectedAck) throws JMSException {
      synchronized(this) {
         long sequenceNumber;
         JMSMessageId lastExposedMsg;
         boolean clientResponsibleForAcknowledge;
         if (ackInfo == null) {
            clientResponsibleForAcknowledge = false;
            sequenceNumber = this.getLastSequenceNumber();
            lastExposedMsg = this.lastExposedMsgId;
         } else {
            clientResponsibleForAcknowledge = ackInfo.getClientResponsibleForAcknowledge();
            if (acknowledgePolicy == 1 && this.mmListener == null) {
               sequenceNumber = this.getLastSequenceNumber();
               lastExposedMsg = this.lastExposedMsgId;
            } else {
               sequenceNumber = ackInfo.getSequenceNumber();
               lastExposedMsg = ackInfo.getMessageId();
            }
         }

         this.checkClosed();
         if (!this.transacted) {
            try {
               if (!clientResponsibleForAcknowledge) {
                  if (isInfectedAck) {
                     this.connection.getFrontEndDispatcher().dispatchSyncTran(new FESessionAcknowledgeRequest(this.getJMSID(), sequenceNumber, acknowledgePolicy, false));
                  } else {
                     this.rememberLastServerAck(lastExposedMsg);
                     this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FESessionAcknowledgeRequest(this.getJMSID(), sequenceNumber, acknowledgePolicy, false));
                     this.rememberLastServerAck((JMSMessageId)null);
                  }

                  if (sequenceNumber == this.getLastSequenceNumber()) {
                     this.decrementWindow = false;
                  }
               }
            } catch (Exception var15) {
               handleException(var15);
            } finally {
               this.removePendingWTMessage(sequenceNumber, IGNOREWINDOWCURRENT);
               this.removeUnackedMessage(sequenceNumber, clientResponsibleForAcknowledge, isInfectedAck);
            }

         }
      }
   }

   void throwForAckRefreshedSessionRules() throws LostServerException {
      if (this.checkRefreshedWithPendingWork()) {
         throw new LostServerException(JMSClientExceptionLogger.logLostServerConnectionLoggable());
      }
   }

   public final void associateTransaction(Message message) throws JMSException {
      MessageImpl msg = (MessageImpl)message;
      if (!this.userTransactionsEnabled && msg.getUnitOfOrder() != null) {
         throw new JMSException("associateTransaction with Unit of Order requires XASession");
      } else {
         this.acknowledge(msg, 1, true);
      }
   }

   public final MessageListener getMessageListener() throws JMSException {
      this.checkClosed();
      return this.messageContext != null ? this.messageContext.getMessageListener() : null;
   }

   public final synchronized JMSMessageContext getJMSMessageContext() {
      return this.messageContext;
   }

   public final synchronized void setMessageListener(MessageListener messageListener) throws JMSException {
      this.checkClosed();
      if (this.consumerListenerCount > 0) {
         throw new IllegalStateException(JMSClientExceptionLogger.logSessionHasConsumersLoggable().getMessage());
      } else {
         this.messageContext = new JMSMessageContext(messageListener);
      }
   }

   public final synchronized void setMMessageListener(MMessageListener messageListener) {
      this.mmListener = messageListener;
   }

   public final void run() {
      this.inlineHandleOnMessageException();
      JMSPushRequest pushRequest;
      synchronized(this) {
         synchronized(this.lockObject) {
            pushRequest = this.firstPushRequest;
            this.setFirstPushRequest(this.lastPushRequest = null);
            this.pushRequestCount = 0;
         }

         this.setState(4);
      }

      JMSContext savedMessageContext = null;
      boolean popSubject = false;

      try {
         MessageListener messageListener;
         if ((messageListener = this.getMessageListener()) != null) {
            savedMessageContext = JMSContext.push(this.messageContext, true);

            for(popSubject = true; pushRequest != null; pushRequest = (JMSPushRequest)pushRequest.getNext()) {
               MessageImpl message = pushRequest.getMessage().copy();
               message.setDDForwarded(false);
               JMSPushEntry pushEntry = pushRequest.getFirstPushEntry();
               ConsumerInternal consumer = this.consumerFind(pushEntry.getConsumerId());
               message.setSequenceNumber(pushEntry.getFrontEndSequenceNumber());
               message.setDeliveryCount(pushEntry.getDeliveryCount());
               this.onMessage(consumer, messageListener, message);
            }

            return;
         }
      } catch (JMSException var55) {
         JMSClientExceptionLogger.logStackTrace(var55);
         return;
      } finally {
         this.clearState(4);

         try {
            this.inlineHandleOnMessageException();
         } finally {
            if (popSubject) {
               JMSContext.pop(savedMessageContext, true);
            }

         }
      }

   }

   private void inlineHandleOnMessageException() {
      JMSException inlineOnMessageException;
      synchronized(this) {
         if (this.deferredException == null) {
            return;
         }

         inlineOnMessageException = this.deferredException;
         this.deferredException = null;
      }

      this.onException(inlineOnMessageException);
   }

   public final synchronized Topic createTopic(String topicName) throws JMSException {
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperationLoggable().getMessage());
      } else {
         return (Topic)this.createDestination(topicName, 2);
      }
   }

   public final synchronized TopicSubscriber createSubscriber(Topic topic) throws JMSException {
      this.checkClosed();
      byte flags = 5;
      return (TopicSubscriber)this.createConsumer(topic, (String)null, false, (String)null, flags);
   }

   public final synchronized TopicSubscriber createSubscriber(Topic topic, String messageSelector, boolean noLocal) throws JMSException {
      this.checkClosed();
      byte flags = 5;
      return (TopicSubscriber)this.createConsumer(topic, messageSelector, noLocal, (String)null, flags);
   }

   public final TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
      return this.createDurableSubscriber(topic, name, (String)null, false);
   }

   public final TopicSubscriber createDurableSubscriber(Topic topic, String name, String selector, boolean noLocal) throws JMSException {
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation2Loggable("createDurableSubscriber").getMessage());
      } else if (name == null) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoSubscriberNameLoggable());
      } else if (name.length() == 0) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logZeroLengthSubscriberNameLoggable());
      } else if (topic instanceof DistributedDestinationImpl) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidDistributedTopicLoggable());
      } else {
         byte flags = 5;
         return (TopicSubscriber)this.createConsumer(topic, selector, noLocal, name, flags);
      }
   }

   public final TopicPublisher createPublisher(Topic topic) throws JMSException {
      byte flags = 4;
      return (TopicPublisher)this.createProducer(topic, flags);
   }

   public final TemporaryTopic createTemporaryTopic() throws JMSException {
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation3Loggable().getMessage());
      } else {
         return (TemporaryTopic)this.createTemporaryDestination(8);
      }
   }

   public final synchronized void unsubscribe(String name) throws JMSException {
      this.checkClosed();
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation4Loggable().getMessage());
      } else if (name != null && name.length() != 0) {
         if (this.connection.getClientIDPolicyInt() == 1) {
            throw new InvalidDestinationException(JMSClientExceptionLogger.logInvalidUnrestrictedUnsubscribeLoggable(name, this.clientID).getMessage());
         } else {
            this.subscriptionRemove(name);
         }
      } else {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logInvalidSubscriptionLoggable().getMessage());
      }
   }

   public synchronized void unsubscribe(Topic topic, String name) throws JMSException {
      this.checkClosed();
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation4Loggable().getMessage());
      } else if (name != null && name.length() != 0) {
         if (this.connection.getClientIDPolicyInt() != 1 || topic != null && !(topic instanceof DistributedDestinationImpl)) {
            this.subscriptionRemove((DestinationImpl)topic, name);
         } else {
            throw new InvalidDestinationException(JMSClientExceptionLogger.logInvalidUnrestrictedUnsubscribe2Loggable(name, this.clientID).getMessage());
         }
      } else {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logInvalidSubscriptionLoggable().getMessage());
      }
   }

   public final synchronized Queue createQueue(String queueName) throws JMSException {
      if (this.type == 1) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedQueueOperationLoggable().getMessage());
      } else {
         return (Queue)this.createDestination(queueName, 1);
      }
   }

   public final QueueReceiver createReceiver(Queue queue) throws JMSException {
      byte flags = 3;
      return (QueueReceiver)this.createConsumer(queue, (String)null, false, (String)null, flags);
   }

   public final QueueReceiver createReceiver(Queue queue, String messageSelector) throws JMSException {
      byte flags = 3;
      return (QueueReceiver)this.createConsumer(queue, messageSelector, false, (String)null, flags);
   }

   public final QueueSender createSender(Queue queue) throws JMSException {
      byte flags = 2;
      return (QueueSender)this.createProducer(queue, flags);
   }

   public final QueueBrowser createBrowser(Queue queue) throws JMSException {
      return this.createBrowser(queue, (String)null);
   }

   public final synchronized QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
      this.checkClosed();
      if (this.type == 1) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedQueueOperation2Loggable().getMessage());
      } else {
         byte flags = 3;
         Destination.checkDestinationType(queue, flags);
         JMSQueueBrowser browser = new JMSQueueBrowser(queue, messageSelector, this);
         this.browsers.put(browser.getJMSID(), browser);

         try {
            InvocableManagerDelegate delegate = this.connection.clientDispatcherPartitionContext.getInvocableManagerDelegate();
            delegate.invocableAdd(22, browser);
            return browser;
         } catch (Exception var6) {
            JMSClientExceptionLogger.logStackTrace(var6);
            throw var6;
         }
      }
   }

   final synchronized void closeBrowser(JMSID browserId, boolean localOnly) throws JMSException {
      if (this.browsers.remove(browserId) != null) {
         if (!localOnly) {
            Response var3 = this.connection.getFrontEndDispatcher().dispatchSync(new FEBrowserCloseRequest(browserId));
         }

         InvocableManagerDelegate delegate = this.connection.clientDispatcherPartitionContext.getInvocableManagerDelegate();
         delegate.invocableRemove(22, browserId);
      }
   }

   public final MessageConsumer createConsumer(javax.jms.Destination destination) throws JMSException {
      this.checkClosed();
      return this.createConsumer(destination, (String)null, false, (String)null, (byte)0);
   }

   public final MessageConsumer createConsumer(javax.jms.Destination destination, String messageSelector) throws JMSException {
      return this.createConsumer(destination, messageSelector, false, (String)null, (byte)0);
   }

   public final MessageConsumer createConsumer(javax.jms.Destination destination, String messageSelector, boolean noLocal) throws JMSException {
      return this.createConsumer(destination, messageSelector, noLocal, (String)null, (byte)0);
   }

   private MessageConsumer createConsumer(javax.jms.Destination destination, String messageSelector, boolean noLocal, String duraName, byte flags) throws JMSException {
      return this.createConsumer(destination, messageSelector, noLocal, duraName, duraName != null, false, flags);
   }

   private MessageConsumer createConsumer(javax.jms.Destination destination, String messageSelector, boolean noLocal, String subscriptionName, boolean isDurable, boolean isJMS2Share, byte flags) throws JMSException {
      String innerMsgSelector = messageSelector;
      if (destination == null) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestinationLoggable().getMessage());
      } else {
         if (((DestinationImpl)destination).isTopic() && this.connection.isInbound()) {
            String clientId = this.connection.getClientID();
            if (clientId == null || clientId.length() == 0) {
               throw new JMSException("ClientId cannot be null to create topic subscriber for inbound connection.");
            }

            if (this.connection.getInboundDest().isReplicated()) {
               innerMsgSelector = messageSelector == null ? "NOT JMS_WL_DDForwarded" : "(" + messageSelector + ") AND NOT JMS_WL_DDForwarded";
            }
         }

         JMSConsumer jmsConsumer = this.setupConsumer(destination, innerMsgSelector, noLocal, subscriptionName, isDurable, isJMS2Share, flags, this.wlSessionImpl.getWLConnectionImpl().computeConsumerReconnectInfo(JMSEnvironment.getJMSEnvironment().getLocalDispatcherId()));
         WLConsumerImpl wlConsumer = new WLConsumerImpl(jmsConsumer, this.wlSessionImpl);
         jmsConsumer.setWlConsumerImpl(wlConsumer);
         return wlConsumer;
      }
   }

   synchronized JMSConsumer setupConsumer(javax.jms.Destination destination, String messageSelector, boolean noLocal, String subscriptionName, boolean isDurable, boolean isJMS2Share, byte flags, ConsumerReconnectInfo consumerReconnectInfo) throws JMSException {
      this.checkClosed();
      if (destination == null) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestinationLoggable().getMessage());
      } else {
         Destination.checkDestinationType(destination, flags);
         JMSConsumer consumer;
         if (this.acknowledgeMode != 128) {
            subscriptionName = this.throwWhenInvalidSubscriberName(destination, subscriptionName, isJMS2Share);
            consumer = new JMSConsumer(this, subscriptionName, isDurable, isJMS2Share, (DestinationImpl)destination, messageSelector, noLocal, this.messagesMaximum, flags);
         } else {
            if (subscriptionName != null && isDurable) {
               subscriptionName = null;
               isDurable = false;
            }

            String multicastAddress = this.setupMulticastInternal(destination);
            consumer = new JMSConsumer(this, subscriptionName, isDurable, isJMS2Share, (DestinationImpl)destination, messageSelector, noLocal, this.messagesMaximum, flags);

            try {
               this.mSock.joinGroup((DestinationImpl)destination, consumer);
            } catch (IOException var12) {
               throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logCannotJoinMulticastGroupLoggable(multicastAddress, var12));
            }
         }

         if (consumer.isDurable() && consumerReconnectInfo != null) {
            consumerReconnectInfo.setDelayServerClose(0L);
         }

         FEConsumerCreateResponse response = this.consumerCreate(subscriptionName, isDurable, isJMS2Share, (DestinationImpl)destination, messageSelector, noLocal, this.messagesMaximum, consumerReconnectInfo);
         consumer.setId(response.getConsumerId());
         consumer.setRuntimeMBeanName(response.getRuntimeMBeanName());
         consumer.setConsumerReconnectInfo(response.getConsumerReconnectInfo());
         this.consumerAdd(consumer);
         return consumer;
      }
   }

   private String throwWhenInvalidSubscriberName(javax.jms.Destination destination, String subscriptionName, boolean isJMS2Share) throws IllegalStateException {
      if (!((DestinationImpl)destination).isTopic()) {
         subscriptionName = null;
      } else if (subscriptionName != null) {
         if (subscriptionName.length() == 0) {
            subscriptionName = null;
         } else if (this.clientID == null && !isJMS2Share || this.clientID != null && this.clientID.length() == 0) {
            throw new IllegalStateException(JMSClientExceptionLogger.logInvalidConsumerCreationLoggable(subscriptionName).getMessage());
         }
      }

      return subscriptionName;
   }

   private String setupMulticastInternal(javax.jms.Destination destination) throws weblogic.jms.common.JMSException {
      if (((DestinationImpl)destination).isQueue()) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoMulticastForQueuesLoggable());
      } else {
         String multicastAddress = ((DestinationImpl)destination).getMulticastAddress();
         int port = ((DestinationImpl)destination).getPort();
         if (multicastAddress != null && port > 0) {
            try {
               if (this.mSock == null) {
                  this.dgmSock = new JMSTDMSocketIPM(port);
                  this.dgmSock.setSoTimeout(1000);
                  this.mSock = new JMSTMSocket(this, this.dgmSock, 1, port);
                  this.dispatchWorkManager.schedule(this.mSock);
               }

               return multicastAddress;
            } catch (IOException var5) {
               if (this.mSock != null) {
                  this.mSock.close();
               }

               if (this.dgmSock != null) {
                  this.dgmSock.close();
               }

               throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logCannotOpenMulticastSocketLoggable(var5));
            }
         } else {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logTopicNoMulticastLoggable(destination.toString()));
         }
      }
   }

   public final synchronized MessageProducer createProducer(javax.jms.Destination destination) throws JMSException {
      return this.createProducer(destination, (byte)0);
   }

   synchronized JMSProducer setupJMSProducer(javax.jms.Destination destination, byte flags) throws JMSException {
      this.checkClosed();
      Destination.checkDestinationType(destination, flags);
      Object response = this.connection.getFrontEndDispatcher().dispatchSync(new FEProducerCreateRequest(this.sessionId, (DestinationImpl)destination));
      JMSID producerId = ((FEProducerCreateResponse)response).getProducerId();
      JMSProducer producer = new JMSProducer(this, producerId, (DestinationImpl)destination, ((FEProducerCreateResponse)response).getRuntimeMBeanName());
      if (this.unitOfOrder != null) {
         producer.setUnitOfOrder(this.unitOfOrder);
      }

      producer.setDestinationFlags(flags);
      this.producerAdd(producer);
      return producer;
   }

   private MessageProducer createProducer(javax.jms.Destination destination, byte flags) throws JMSException {
      JMSProducer jmsProd = this.setupJMSProducer(destination, flags);
      WLProducerImpl recProd = new WLProducerImpl(jmsProd, this.wlSessionImpl);
      jmsProd.setWlProducerImpl(recProd);
      return recProd;
   }

   public final TemporaryQueue createTemporaryQueue() throws JMSException {
      if (this.type == 1) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedQueueOperation3Loggable().getMessage());
      } else {
         return (TemporaryQueue)this.createTemporaryDestination(4);
      }
   }

   public final synchronized void setExceptionListener(ExceptionListener exceptionListener) throws JMSException {
      this.checkClosed();
      this.exceptionContext = new JMSExceptionContext(exceptionListener, this.connection.isInbound());
   }

   public final synchronized JMSExceptionContext getJMSExceptionContext() {
      return this.exceptionContext;
   }

   public final synchronized ExceptionListener getExceptionListener() {
      return this.exceptionContext != null ? this.exceptionContext.getExceptionListener() : null;
   }

   public final void onException(JMSException exception) {
      this.onExceptionInternal(exception, false);
   }

   private boolean onExceptionInternal(JMSException exception, boolean deferIfInUse) {
      JMSContext savedExceptionContext = null;

      boolean var5;
      try {
         if (this.exceptionContext != null) {
            savedExceptionContext = JMSContext.push(this.exceptionContext);
            boolean var11;
            if (deferIfInUse) {
               var11 = this.exceptionContext.invokeListenerIfItIsIdle(exception);
               return var11;
            }

            this.exceptionContext.blockTillIdleThenRunExclusively(exception);
            var11 = false;
            return var11;
         }

         if (!(exception instanceof weblogic.jms.common.JMSException) || ((weblogic.jms.common.JMSException)exception).isInformational()) {
            return false;
         }

         JMSExceptionContext exceptionContext = this.getConnection().getJMSExceptionContext();
         if (exceptionContext == null) {
            return false;
         }

         var5 = JMSConnection.onExceptionInternal(exception, exceptionContext, deferIfInUse);
      } catch (Throwable var9) {
         JMSClientExceptionLogger.logStackTrace(var9);
         return false;
      } finally {
         if (savedExceptionContext != null) {
            JMSContext.pop(savedExceptionContext);
         }

      }

      return var5;
   }

   private FEConsumerCreateResponse consumerCreate(String subscriptionName, boolean isDurable, boolean isJMS2Share, DestinationImpl destination, String messageSelector, boolean noLocal, int messagesMaximum, ConsumerReconnectInfo consumerReconnectInfo) throws JMSException {
      if ((destination.getType() == 4 || destination.getType() == 8) && (destination.getConnection() == null || !this.connection.getJMSID().equals(destination.getConnection().getJMSID()))) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logInvalidConnectionLoggable().getMessage());
      } else {
         if (isJMS2Share && (!isDurable || this.clientID == null)) {
            PeerInfo pi = this.getFEPeerInfo();
            if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
               if (isDurable) {
                  throw new JMSException("Unsupported operation to front-end server version [" + pi + "]: create shared durable subscription with name " + subscriptionName + " and null client id");
               }

               throw new JMSException("Unsupported operation to front-end server version [" + pi + "]: create shared nondurable subscription with name " + subscriptionName);
            }
         }

         if (subscriptionName != null && isDurable && !isJMS2Share && this.getConnection().getClientIDPolicyInt() == 0 && this.subscriptionSharingPolicy == 0 && !this.connection.markDurableSubscriber(subscriptionName)) {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logSubscriptionNameInUseLoggable(subscriptionName));
         } else {
            Response response;
            try {
               response = this.connection.getFrontEndDispatcher().dispatchSync(new FEConsumerCreateRequest(this.sessionId, this.clientID, subscriptionName, isDurable, destination, messageSelector, noLocal, messagesMaximum, this.getRedeliveryDelay(), consumerReconnectInfo, isJMS2Share ? 1 : this.subscriptionSharingPolicy));
            } catch (JMSException var11) {
               if (subscriptionName != null && isDurable && !isJMS2Share) {
                  this.connection.removeDurableSubscriber(subscriptionName);
               }

               throw var11;
            }

            if (!(response instanceof FEConsumerCreateResponse)) {
               if (subscriptionName != null && isDurable && !isJMS2Share) {
                  this.connection.removeDurableSubscriber(subscriptionName);
               }

               throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidFrontEndResponseLoggable(response));
            } else {
               if (subscriptionName != null && isDurable && this.getConnection().getClientIDPolicyInt() == 0) {
                  this.connection.addDurableSubscriber(subscriptionName, ((FEConsumerCreateResponse)response).getConsumerId());
               }

               return (FEConsumerCreateResponse)response;
            }
         }
      }
   }

   private synchronized void consumerAdd(JMSConsumer consumer) {
      this.consumers.put(consumer.getJMSID(), consumer);

      try {
         InvocableManagerDelegate delegate = this.connection.clientDispatcherPartitionContext.getInvocableManagerDelegate();
         delegate.invocableAdd(6, consumer);
      } catch (Exception var3) {
         JMSClientExceptionLogger.logStackTrace(var3);
      }

   }

   public int consumersCount() {
      return this.consumers.size();
   }

   public int producersCount() {
      return this.producers.size();
   }

   final synchronized void consumerClose(ConsumerInternal consumer) throws JMSException {
      this.consumerClose(consumer, -1L);
   }

   final synchronized void consumerClose(ConsumerInternal consumer, long sequenceNumber) throws JMSException {
      if (sequenceNumber != -1L) {
         this.setRealLastSequenceNumber(sequenceNumber);
      }

      synchronized(this.synchronousListenerObject) {
         this.synchronousListenerObject.notifyAll();
      }

      if (!this.inListener()) {
         this.waitForState(-5);
      }

      JMSID consumerId;
      synchronized(consumer) {
         if (consumer.isClosed()) {
            return;
         }

         consumerId = this.consumerCloseLocal(consumer, true);
      }

      Object response = this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FEConsumerCloseRequest(consumerId, this.getLastSequenceNumber(), this.getFirstSequenceNumberNotSeen()));
      this.waitForOutstandingReceives();
   }

   private JMSID consumerCloseLocal(ConsumerInternal consumer, boolean fromJMSConsumer) throws JMSException {
      synchronized(consumer) {
         this.consumerRemove(consumer, fromJMSConsumer);
         JMSID consumerId = consumer.getJMSID();
         consumer.setClosed(true);
         if (fromJMSConsumer && this.acknowledgeMode == 128) {
            this.leaveGroup((DestinationImpl)consumer.getDestination(), consumer);
         }

         return consumerId;
      }
   }

   private synchronized ConsumerInternal consumerFind(JMSID consumerId) {
      return (ConsumerInternal)this.consumers.get(consumerId);
   }

   private synchronized void consumerRemove(ConsumerInternal consumer, boolean fromJMSConsumer) {
      if (fromJMSConsumer) {
         this.consumers.remove(consumer.getJMSID());
      }

      try {
         InvocableManagerDelegate delegate = this.connection.clientDispatcherPartitionContext.getInvocableManagerDelegate();
         delegate.invocableRemove(6, consumer.getJMSID());
      } catch (JMSException var4) {
         JMSClientExceptionLogger.logStackTrace(var4);
      }

   }

   private synchronized void producerAdd(JMSProducer producer) {
      this.producers.put(producer.getJMSID(), producer);
   }

   final void producerClose(JMSID producerId) throws JMSException {
      this.checkClosed();
      synchronized(this) {
         this.producers.remove(producerId);
      }

      Object response = this.connection.getFrontEndDispatcher().dispatchSync(new FEProducerCloseRequest(producerId));
   }

   static final JMSException handleException(Exception e) throws JMSException {
      if (e instanceof JMSException) {
         throw (JMSException)e;
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logSystemErrorLoggable(e), e);
      }
   }

   private javax.jms.Destination createDestination(String destinationName, int destinationType) throws JMSException {
      this.checkClosed();
      Object response = this.connection.getFrontEndDispatcher().dispatchSyncNoTran(new FEDestinationCreateRequest(destinationName, destinationType, false));
      return ((JMSDestinationCreateResponse)response).getDestination();
   }

   final JMSID createBackEndBrowser(DestinationImpl destination, String messageSelector) throws JMSException {
      Object response = this.connection.getFrontEndDispatcher().dispatchSync(new FEBrowserCreateRequest(this.connection.getJMSID(), this.sessionId, destination, messageSelector));
      return ((JMSBrowserCreateResponse)response).getBrowserId();
   }

   private synchronized javax.jms.Destination createTemporaryDestination(int destinationType) throws JMSException {
      this.checkClosed();
      Object response = this.connection.getFrontEndDispatcher().dispatchSync(new FETemporaryDestinationCreateRequest(this.connection.getJMSID(), destinationType, true));
      ((FETemporaryDestinationCreateResponse)response).getDestination().setConnection(this.connection);
      return ((FETemporaryDestinationCreateResponse)response).getDestination();
   }

   private void subscriptionRemove(String name) throws JMSException {
      if (!this.connection.markDurableSubscriber(name)) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logSubscriptionNameInUse2Loggable(name));
      } else {
         try {
            this.connection.consumerRemove(name);
         } finally {
            this.connection.removeDurableSubscriber(name);
         }

      }
   }

   private void subscriptionRemove(DestinationImpl topic, String name) throws JMSException {
      if (this.getConnection().getClientIDPolicyInt() == 0) {
         this.subscriptionRemove(name);
      } else {
         this.connection.consumerRemove(topic, name);
      }
   }

   public final void pushMessage(MessageImpl message, JMSPushEntry pushEntry) {
      JMSPushRequest pushRequest = new JMSPushRequest(0, this.sessionId, message, pushEntry);
      JMSException runOnExceptionOutsideOfLock;
      synchronized(this.lockObject) {
         runOnExceptionOutsideOfLock = this.addSelfSequencePushRequest(pushRequest);
      }

      if (runOnExceptionOutsideOfLock != null) {
         this.onException(runOnExceptionOutsideOfLock);
      }

   }

   public final JMSPushRequest getFirstPushRequest() {
      return this.firstPushRequest;
   }

   private void setFirstPushRequest(JMSPushRequest arg) {
      this.firstPushRequest = arg;
   }

   private final JMSException addSelfSequencePushRequest(JMSPushRequest pushRequest) {
      JMSException exceptionOutsideOfLock = null;
      if (this.acknowledgeMode == 128) {
         if (this.messagesMaximum != -1 && this.pushRequestCount >= this.messagesMaximum) {
            if (this.overrunPolicy == 0) {
               return new DataOverrunException(JMSClientExceptionLogger.logDropNewerLoggable().getMessage(), pushRequest.getMessage().getJMSMessageID(), pushRequest.getMessage().getJMSCorrelationID(), pushRequest.getMessage().getJMSDestination());
            }

            exceptionOutsideOfLock = new DataOverrunException(JMSClientExceptionLogger.logDropOlderLoggable().getMessage(), this.firstPushRequest.getMessage().getJMSMessageID(), this.firstPushRequest.getMessage().getJMSCorrelationID(), this.firstPushRequest.getMessage().getJMSDestination());
            this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
            --this.pushRequestCount;
         }

         ++this.pushRequestCount;
      }

      if (this.firstPushRequest == null) {
         this.setFirstPushRequest(pushRequest);
      } else {
         this.lastPushRequest.setNext(pushRequest);
      }

      this.lastPushRequest = pushRequest;
      return exceptionOutsideOfLock;
   }

   private final void addPushRequests(JMSPushRequest myPushRequest, boolean forReceive) {
      JMSPushRequest firstPushRequest = myPushRequest;
      JMSPushRequest prevPushRequest = null;
      JMSPushRequest currentPush = myPushRequest;
      this.session_clientResponsibleForAck |= myPushRequest.getFirstPushEntry().getClientResponsibleForAcknowledge();
      JMSPushRequest lastPushRequest;
      if (!this.connectionOlderThan90) {
         for(lastPushRequest = myPushRequest; lastPushRequest != null; lastPushRequest = (JMSPushRequest)lastPushRequest.getNext()) {
            if (!forReceive && this.connection.isLocal() && lastPushRequest.getFirstPushEntry().getPipelineGeneration() == 0 && this.recoversFor90HasBeenCalled) {
               lastPushRequest.getFirstPushEntry().setPipelineGeneration(1048576);
            }
         }
      }

      JMSPushEntry pushEntry;
      while(!forReceive && currentPush != null && currentPush.getFrontEndSequenceNumber() < this.expectedSequenceNumber) {
         pushEntry = null;

         for(JMSPushEntry pushEntry = currentPush.getFirstPushEntry(); pushEntry != null; pushEntry = pushEntry.getNext()) {
            if (pushEntry.getClientResponsibleForAcknowledge()) {
               if (pushEntry == null) {
                  currentPush.setFirstPushEntry(pushEntry);
               } else {
                  pushEntry.setNext(pushEntry);
               }

               pushEntry = pushEntry;
            }
         }

         if (pushEntry == null) {
            if (firstPushRequest == currentPush) {
               firstPushRequest = (JMSPushRequest)currentPush.getNext();
            }

            currentPush = (JMSPushRequest)currentPush.getNext();
         } else {
            pushEntry.setNext((JMSPushEntry)null);
            currentPush.setLastPushEntry(pushEntry);
            if (prevPushRequest == null) {
               firstPushRequest = currentPush;
            } else {
               prevPushRequest.setNext(currentPush);
            }

            prevPushRequest = currentPush;
            currentPush = (JMSPushRequest)currentPush.getNext();
         }
      }

      if (firstPushRequest != null) {
         if (prevPushRequest != null) {
            prevPushRequest.setNext(currentPush);
            currentPush = prevPushRequest;
         }

         while(currentPush != null) {
            for(pushEntry = currentPush.getFirstPushEntry(); pushEntry != null; pushEntry = pushEntry.getNext()) {
               int pushEntryPipelineGeneration = pushEntry.getPipelineGeneration();
               if (pushEntryPipelineGeneration != this.pipelineGeneration && pushEntryPipelineGeneration != 0 && !pushEntry.getClientResponsibleForAcknowledge()) {
                  if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                     JMSDebug.JMSMessagePath.debug("ignore stale pipelineGeneration " + JMSPushEntry.displayRecoverGeneration(pushEntryPipelineGeneration) + " when expecting " + JMSPushEntry.displayRecoverGeneration(this.pipelineGeneration));
                  }

                  pushEntry.setPipelineGeneration(1048576);
               }
            }

            if (currentPush.getNext() == null) {
               break;
            }

            currentPush = (JMSPushRequest)currentPush.getNext();
         }

         lastPushRequest = currentPush;
         if (forReceive) {
            if (this.firstReceivePushRequest == null) {
               this.firstReceivePushRequest = firstPushRequest;
               this.lastReceivePushRequest = currentPush;
               return;
            }
         } else if (this.firstPushRequest == null) {
            this.setFirstPushRequest(firstPushRequest);
            this.lastPushRequest = currentPush;
            return;
         }

         if (forReceive) {
            if (currentPush.getFrontEndSequenceNumber() < this.firstReceivePushRequest.getFrontEndSequenceNumber()) {
               currentPush.setNext(this.firstReceivePushRequest);
               this.firstReceivePushRequest = firstPushRequest;
               return;
            }
         } else if (currentPush.getFrontEndSequenceNumber() < this.firstPushRequest.getFrontEndSequenceNumber()) {
            currentPush.setNext(this.firstPushRequest);
            this.firstPushRequest = firstPushRequest;
            return;
         }

         if (forReceive) {
            if (firstPushRequest.getFrontEndSequenceNumber() > this.lastReceivePushRequest.getFrontEndSequenceNumber()) {
               this.lastReceivePushRequest.setNext(firstPushRequest);
               this.lastReceivePushRequest = currentPush;
               return;
            }
         } else if (firstPushRequest.getFrontEndSequenceNumber() > this.lastPushRequest.getFrontEndSequenceNumber()) {
            this.lastPushRequest.setNext(firstPushRequest);
            this.lastPushRequest = currentPush;
            return;
         }

         JMSPushRequest pushRequest;
         if (forReceive) {
            pushRequest = this.firstReceivePushRequest;
         } else {
            pushRequest = this.firstPushRequest;
         }

         while(pushRequest.getNext() != null) {
            if (forReceive && firstPushRequest.getFrontEndSequenceNumber() == ((JMSPushRequest)pushRequest.getNext()).getFrontEndSequenceNumber()) {
               return;
            }

            if (lastPushRequest.getFrontEndSequenceNumber() < ((JMSPushRequest)pushRequest.getNext()).getFrontEndSequenceNumber() && pushRequest.getFrontEndSequenceNumber() < firstPushRequest.getFrontEndSequenceNumber()) {
               lastPushRequest.setNext(pushRequest.getNext());
               pushRequest.setNext(firstPushRequest);
               return;
            }

            pushRequest = (JMSPushRequest)pushRequest.getNext();
         }

      }
   }

   private final JMSPushRequest removePushRequests() {
      JMSPushRequest pushRequest;
      if ((pushRequest = this.firstPushRequest) == null) {
         return null;
      } else if (this.acknowledgeMode == 128) {
         this.setFirstPushRequest(this.lastPushRequest = null);
         this.pushRequestCount = 0;
         return pushRequest;
      } else if (this.expectedSequenceNumber < pushRequest.getFrontEndSequenceNumber()) {
         return null;
      } else {
         JMSPushRequest prevPushRequest = null;

         while(this.firstPushRequest != null && this.firstPushRequest.getLastPushEntry().getFrontEndSequenceNumber() < this.expectedSequenceNumber) {
            prevPushRequest = this.firstPushRequest;
            this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
         }

         if (this.firstPushRequest == null) {
            this.lastPushRequest = null;
            return pushRequest;
         } else {
            assert this.firstPushRequest.getFrontEndSequenceNumber() >= this.expectedSequenceNumber == this.firstPushRequest.getLastPushEntry().getFrontEndSequenceNumber() >= this.expectedSequenceNumber;

            if (this.expectedSequenceNumber < this.firstPushRequest.getFrontEndSequenceNumber()) {
               if (prevPushRequest != null) {
                  prevPushRequest.setNext((Request)null);
               }

               return pushRequest;
            } else {
               do {
                  this.expectedSequenceNumber = this.firstPushRequest.getLastPushEntry().getFrontEndSequenceNumber() + 1L;
                  prevPushRequest = this.firstPushRequest;
                  this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
               } while(this.firstPushRequest != null && this.firstPushRequest.getFrontEndSequenceNumber() < this.expectedSequenceNumber);

               if (this.firstPushRequest == null) {
                  this.lastPushRequest = null;
               } else {
                  prevPushRequest.setNext((Request)null);
               }

               return pushRequest;
            }
         }
      }
   }

   private final void setExpectedSequenceNumber81(long sequenceNumber, boolean force) {
      synchronized(this.lockObject) {
         JMSPushRequest pushRequest = this.firstPushRequest;
         JMSPushRequest prevPushRequest = null;
         if (force || sequenceNumber > this.expectedSequenceNumber) {
            this.expectedSequenceNumber = sequenceNumber;
         }

         if (!force && pushRequest != null) {
            for(; pushRequest != null && pushRequest.getFrontEndSequenceNumber() < this.expectedSequenceNumber; pushRequest = (JMSPushRequest)pushRequest.getNext()) {
               boolean doRemove = true;
               JMSPushEntry pushEntry = pushRequest.getFirstPushEntry();

               for(JMSPushEntry prevPushEntry = null; pushEntry != null; pushEntry = pushEntry.getNext()) {
                  if (pushEntry.getClientResponsibleForAcknowledge()) {
                     doRemove = false;
                  } else if (prevPushEntry == null) {
                     pushRequest.removePushEntry();
                  } else {
                     prevPushEntry.setNext(pushEntry.getNext());
                  }

                  prevPushEntry = pushEntry;
               }

               if (doRemove) {
                  if (prevPushRequest == null) {
                     this.setFirstPushRequest((JMSPushRequest)pushRequest.getNext());
                     if (this.firstPushRequest == null) {
                        this.lastPushRequest = null;
                        return;
                     }
                  } else {
                     prevPushRequest.setNext(pushRequest.getNext());
                  }
               } else {
                  prevPushRequest = pushRequest;
               }
            }

         }
      }
   }

   private final boolean havePushRequests() {
      if (this.firstPushRequest == null) {
         return false;
      } else if (this.acknowledgeMode == 128) {
         return true;
      } else {
         return this.firstPushRequest.getFrontEndSequenceNumber() <= this.expectedSequenceNumber;
      }
   }

   public final void pushMessage(Request invocableRequest, boolean forReceive) {
      JMSPushRequest pushRequest = (JMSPushRequest)invocableRequest;
      JMSException runOnExceptionOutsideOfLock = null;

      try {
         synchronized(this.lockObject) {
            if (this.acknowledgeMode == 128) {
               runOnExceptionOutsideOfLock = this.addSelfSequencePushRequest(pushRequest);
            } else {
               this.addPushRequests(pushRequest, forReceive);
               if (!forReceive && this.synchronousListener) {
                  this.adjustHighMark();
               }
            }

            if (this.running || this.stopped || this.synchronousListener) {
               return;
            }

            if (!this.havePushRequests()) {
               return;
            }

            this.running = true;
         }
      } finally {
         if (runOnExceptionOutsideOfLock != null) {
            this.onException(runOnExceptionOutsideOfLock);
         }

      }

      Thread t = Thread.currentThread();
      boolean sched;
      if (JMSWorkManager.isThinclient()) {
         sched = true;
      } else if (this.ignoreJmsAsyncQueue && !this.connection.isLocal()) {
         sched = false;
      } else if (this.connection.isLocal() && this.dispatchWorkManager.getType() == 2) {
         sched = true;
      } else if (!this.dispatchWorkManager.isThreadOwner(t)) {
         sched = true;
      } else {
         sched = false;
      }

      if (sched) {
         this.dispatchWorkManager.schedule(this.useForRunnable);
      } else {
         this.executeMessage();
      }

   }

   private void executeMessage() {
      if (this.mmListener == null) {
         this.execute();
      } else {
         this.executeMM();
      }
   }

   private void adjustHighMark() {
      boolean firstNewMessage = true;
      if (this.firstPushRequest != null && this.highMark >= this.firstPushRequest.getFrontEndSequenceNumber()) {
         JMSPushRequest prevPushRequest = null;
         JMSPushRequest pushRequest = this.firstPushRequest;

         while(pushRequest.getFrontEndSequenceNumber() <= this.highMark) {
            if (pushRequest.getFrontEndSequenceNumber() == this.highMark) {
               ++this.highMark;
               if (firstNewMessage && pushRequest.getFirstPushEntry().getPipelineGeneration() != 1048576) {
                  synchronized(this.synchronousListenerObject) {
                     if (this.waitForNewMessage) {
                        if (this.notifyNewMessage) {
                           if (prevPushRequest == null) {
                              this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
                           } else {
                              prevPushRequest.setNext(pushRequest.getNext());
                           }

                           this.shortCutPrevPushRequest = prevPushRequest;
                           this.needToRemoveIt = false;
                        } else {
                           this.shortCutPrevPushRequest = prevPushRequest;
                           this.needToRemoveIt = true;
                        }

                        this.shortCutPushRequest = pushRequest;
                        this.waitForNewMessage = false;
                        this.synchronousListenerObject.notify();
                     }
                  }

                  firstNewMessage = false;
               }
            }

            prevPushRequest = pushRequest;
            if ((pushRequest = (JMSPushRequest)pushRequest.getNext()) == null) {
               break;
            }
         }

         this.expectedSequenceNumber = this.highMark;
      }
   }

   Message getAsyncMessageForConsumer(ConsumerInternal consumer, long timeout, Class bodyClass) throws JMSException {
      JMSPushEntry pushEntry = null;
      MessageImpl message = null;
      boolean fromShortCut = false;
      long startTime = System.currentTimeMillis();
      long waitTime = timeout;
      long lastSequenceNumberOnmfException;
      synchronized(this) {
         while(this.stopped) {
            if (this.isClosed() || timeout == 9223372036854775806L) {
               return null;
            }

            try {
               this.wait(waitTime);
            } catch (InterruptedException var35) {
               throw new weblogic.jms.common.JMSException(var35);
            }

            if (timeout != Long.MAX_VALUE) {
               lastSequenceNumberOnmfException = System.currentTimeMillis() - startTime;
               if (lastSequenceNumberOnmfException >= timeout) {
                  return null;
               }

               waitTime = timeout - lastSequenceNumberOnmfException;
               if (waitTime > 0L) {
               }
            }
         }
      }

      MessageFormatException mfException = null;
      lastSequenceNumberOnmfException = 0L;
      synchronized(this.lockObject) {
         boolean removeMsg = false;

         label266:
         while(true) {
            while(true) {
               if (this.firstPushRequest == null || this.firstPushRequest.getFrontEndSequenceNumber() >= this.highMark) {
                  break label266;
               }

               pushEntry = this.firstPushRequest.getFirstPushEntry();
               if (!pushEntry.getClientResponsibleForAcknowledge()) {
                  if (this.connection.isLocal()) {
                     removeMsg = true;
                     if (pushEntry.getPipelineGeneration() == 0) {
                        removeMsg = false;
                     } else if (pushEntry.getPipelineGeneration() == 1048576) {
                        removeMsg = true;
                     } else if (pushEntry.getPipelineGeneration() == this.pipelineGeneration) {
                        removeMsg = false;
                     }
                  } else {
                     removeMsg = false;
                     if (pushEntry.getPipelineGeneration() == 1048576 || pushEntry.getPipelineGeneration() != this.pipelineGeneration) {
                        removeMsg = true;
                     }
                  }

                  if (removeMsg) {
                     pushEntry = null;
                     this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
                     continue;
                  }
               }

               if (pushEntry.getConsumerId().equals(consumer.getId())) {
                  message = this.firstPushRequest.getMessage();
                  if (bodyClass != null) {
                     try {
                        if (message.getBody(bodyClass) == null) {
                           throw new MessageFormatException(JMSClientExceptionLogger.logNoMessageBodyLoggable().getMessage());
                        }
                     } catch (MessageFormatException var38) {
                        mfException = var38;
                        lastSequenceNumberOnmfException = pushEntry.getFrontEndSequenceNumber();
                        break label266;
                     }
                  }

                  if (this.connection.isLocal()) {
                     message = message.copy();
                  }

                  this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
                  break label266;
               }

               pushEntry = null;
               this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
            }
         }

         if (mfException == null && pushEntry == null) {
            if (timeout != 9223372036854775806L) {
               this.waitForNewMessage = true;
               if (timeout == Long.MAX_VALUE) {
                  this.notifyNewMessage = true;
               } else {
                  this.notifyNewMessage = false;
               }
            } else {
               this.waitForNewMessage = this.notifyNewMessage = false;
            }
         }

         this.shortCutPushRequest = this.shortCutPrevPushRequest = null;
      }

      if (mfException != null) {
         synchronized(this) {
            this.rememberFirstSequenceNumberNotSeen(lastSequenceNumberOnmfException);
         }

         throw mfException;
      } else {
         if (pushEntry == null && timeout != 9223372036854775806L) {
            boolean shouldRemoveFromPushRequest = false;
            JMSPushRequest myPrevPushRequest;
            JMSPushRequest myPushRequest;
            synchronized(this.synchronousListenerObject) {
               if (this.shortCutPushRequest == null) {
                  try {
                     this.synchronousListenerObject.wait(waitTime);
                  } catch (InterruptedException var34) {
                  }
               }

               myPushRequest = this.shortCutPushRequest;
               myPrevPushRequest = this.shortCutPrevPushRequest;
               this.shortCutPushRequest = this.shortCutPrevPushRequest = null;
               shouldRemoveFromPushRequest = this.needToRemoveIt;
               this.needToRemoveIt = false;
            }

            if (myPushRequest != null) {
               message = myPushRequest.getMessage();
               if (bodyClass != null) {
                  try {
                     if (message.getBody(bodyClass) == null) {
                        throw new MessageFormatException(JMSClientExceptionLogger.logNoMessageBodyLoggable().getMessage());
                     }
                  } catch (MessageFormatException var36) {
                     if (!shouldRemoveFromPushRequest) {
                        synchronized(this.lockObject) {
                           if (myPrevPushRequest == null) {
                              this.setFirstPushRequest(myPushRequest);
                           } else {
                              myPushRequest.setNext(myPrevPushRequest.getNext());
                              myPrevPushRequest.setNext(myPushRequest);
                           }
                        }
                     }

                     synchronized(this) {
                        this.rememberFirstSequenceNumberNotSeen(myPushRequest.getFirstPushEntry().getFrontEndSequenceNumber());
                     }

                     throw var36;
                  }
               }

               if (shouldRemoveFromPushRequest) {
                  synchronized(this) {
                     synchronized(this.lockObject) {
                        if (myPrevPushRequest == null) {
                           this.setFirstPushRequest((JMSPushRequest)this.firstPushRequest.getNext());
                        } else {
                           myPrevPushRequest.setNext(myPushRequest.getNext());
                        }
                     }
                  }
               }

               if (this.connection.isLocal()) {
                  message = message.copy();
               }

               pushEntry = myPushRequest.getFirstPushEntry();
            } else {
               synchronized(this) {
                  if (this.isClosed()) {
                     throw new IllegalStateException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
                  }
               }
            }
         }

         if (pushEntry != null) {
            boolean clientResponsibleForAck = pushEntry.getClientResponsibleForAcknowledge();
            message.setJMSDestinationImpl((DestinationImpl)consumer.getDestination());
            message.setSequenceNumber(pushEntry.getFrontEndSequenceNumber());
            message.setDeliveryCount(pushEntry.getDeliveryCount());
            message.setClientResponsibleForAcknowledge(clientResponsibleForAck);
            synchronized(this) {
               this.rememberLastSequenceNumber(pushEntry.getFrontEndSequenceNumber(), message.getId());
            }

            if (this.acknowledgeMode == 2) {
               message.setSession(this);
               this.addUnackedMessage(consumer, message);
               consumer.decrementWindowCurrent(clientResponsibleForAck);
            } else if (this.acknowledgeMode != 1 && this.acknowledgeMode != 3) {
               consumer.decrementWindowCurrent(clientResponsibleForAck);
            } else {
               this.addUnackedMessage(consumer, message);
               if (this.acknowledgeMode == 3 && this.checkDelayAckForDupsOK(message) && --this.dupsOKAckCount > 0) {
                  this.requireAckForDupsOK = true;
               } else {
                  this.requireAckForDupsOK = false;
                  this.acknowledge(message, 1, false);
                  this.dupsOKAckCount = this.dupsOKAckCountDown;
               }

               consumer.decrementWindowCurrent(clientResponsibleForAck);
            }

            return message;
         } else {
            return null;
         }
      }
   }

   protected final void execute() {
      JMSException inlineOnMessageException = null;
      boolean subjectPushed = false;
      AbstractSubject lastListenerSubject = null;
      ClassLoader lastListenerClassLoader = null;
      Context lastListenerContext = null;
      ClassLoader clSave = null;
      if (KernelStatus.isServer()) {
         clSave = Thread.currentThread().getContextClassLoader();
         lastListenerClassLoader = clSave;
      }

      try {
         while(true) {
            if (inlineOnMessageException != null) {
               JMSException jmse = inlineOnMessageException;
               inlineOnMessageException = null;
               this.onException(jmse);
            }

            JMSPushRequest pushRequest;
            synchronized(this) {
               if (this.deferredException != null) {
                  inlineOnMessageException = this.deferredException;
                  this.deferredException = null;
                  continue;
               }

               synchronized(this.lockObject) {
                  if (!this.stopped && !this.closeStarted && !this.isClosed()) {
                     pushRequest = this.removePushRequests();
                     if (!this.connectionOlderThan90 && pushRequest != null) {
                        int pushEntryGen = pushRequest.getFirstPushEntry().getPipelineGeneration();
                        if (pushEntryGen != this.pipelineGeneration && pushEntryGen != 2097152 && pushEntryGen != 0) {
                           pushRequest = (JMSPushRequest)pushRequest.getNext();
                           this.assignClientAckCarryForward(pushRequest);
                           continue;
                        }
                     }

                     this.assignClientAckCarryForward(pushRequest);
                     if (pushRequest == null) {
                        this.running = false;
                        return;
                     }
                  } else {
                     this.running = false;
                     return;
                  }
               }

               this.setState(4);
            }

            boolean abnormalStop = false;

            while(true) {
               boolean var170 = false;

               label3783: {
                  label3784: {
                     try {
                        var170 = true;
                        MessageImpl message = pushRequest.getMessage();
                        if (this.connection.isLocal()) {
                           message = message.copy();
                        }

                        message.setDDForwarded(false);

                        for(JMSPushEntry pushEntry = pushRequest.removePushEntry(); pushEntry != null && pushRequest != null && !abnormalStop; pushEntry = pushRequest == null ? null : pushRequest.removePushEntry()) {
                           ConsumerInternal consumer = this.consumerFind(pushEntry.getConsumerId());
                           boolean clientResponsibleForAck = pushEntry.getClientResponsibleForAcknowledge();
                           if (consumer != null && !consumer.isClosed()) {
                              if (!clientResponsibleForAck) {
                                 if (this.pipelineGeneration == 0) {
                                    if (pushEntry.getFrontEndSequenceNumber() < consumer.getExpectedSequenceNumber() && !clientResponsibleForAck) {
                                       continue;
                                    }
                                 } else if (pushEntry.getPipelineGeneration() == 1048576) {
                                    continue;
                                 }
                              }

                              MessageListener listener;
                              if ((listener = consumer.getMessageListener()) != null) {
                                 message.setJMSDestinationImpl((DestinationImpl)consumer.getDestination());
                                 MessageImpl messageCopy = pushEntry.getNext() == null ? message : message.copy();
                                 messageCopy.setSequenceNumber(pushEntry.getFrontEndSequenceNumber());
                                 messageCopy.setDeliveryCount(pushEntry.getDeliveryCount());
                                 messageCopy.setClientResponsibleForAcknowledge(clientResponsibleForAck);
                                 this.addUnackedMessage(consumer, messageCopy);
                                 JMSMessageContext consumerContext = consumer.getMessageListenerContext();
                                 Context listenerContext = consumerContext.getJNDIContext();
                                 ClassLoader listenerClassLoader = consumerContext.getClassLoader();
                                 if (!this.connection.isInbound()) {
                                    AbstractSubject listenerSubject = consumerContext.getSubject();
                                    if (listenerSubject != lastListenerSubject) {
                                       if (lastListenerSubject != null) {
                                          this.subjectManager.popSubject(KERNEL_ID);
                                       }

                                       this.subjectManager.pushSubject(KERNEL_ID, listenerSubject);
                                       lastListenerSubject = listenerSubject;
                                    }
                                 } else if (!subjectPushed) {
                                    this.subjectManager.pushSubject(KERNEL_ID, this.subjectManager.getAnonymousSubject());
                                    subjectPushed = true;
                                 }

                                 if (KernelStatus.isServer()) {
                                    if (listenerContext != lastListenerContext) {
                                       if (lastListenerContext != null) {
                                          JMSEnvironment.getJMSEnvironment().popLocalJNDIContext();
                                       }

                                       JMSEnvironment.getJMSEnvironment().pushLocalJNDIContext(listenerContext);
                                       lastListenerContext = listenerContext;
                                    }

                                    if (listenerClassLoader != lastListenerClassLoader) {
                                       Thread.currentThread().setContextClassLoader(listenerClassLoader);
                                       lastListenerClassLoader = listenerClassLoader;
                                    }
                                 }

                                 boolean var195 = false;

                                 label3789: {
                                    try {
                                       var195 = true;
                                       ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(consumerContext.getComponentInvocationContext());
                                       Throwable var20 = null;

                                       try {
                                          this.decrementWindow = true;
                                          this.recoverableClientAckMessages = pushRequest;
                                          this.onMessage(consumer, listener, messageCopy);
                                       } catch (Throwable var222) {
                                          var20 = var222;
                                          throw var222;
                                       } finally {
                                          if (mic != null) {
                                             if (var20 != null) {
                                                try {
                                                   mic.close();
                                                } catch (Throwable var221) {
                                                   var20.addSuppressed(var221);
                                                }
                                             } else {
                                                mic.close();
                                             }
                                          }

                                       }

                                       var195 = false;
                                       break label3789;
                                    } catch (Throwable var233) {
                                       JMSClientExceptionLogger.logStackTrace(var233);
                                       var195 = false;
                                    } finally {
                                       if (var195) {
                                          this.recoverableClientAckMessages = null;
                                          if (this.recovering) {
                                             synchronized(this) {
                                                synchronized(this.lockObject) {
                                                   if (this.recovering) {
                                                      this.recovering = false;
                                                      pushRequest = this.carryForwardOnReconnect = null;
                                                   }
                                                }
                                             }
                                          }

                                          if (this.decrementWindow) {
                                             consumer.decrementWindowCurrent(clientResponsibleForAck);
                                          }

                                       }
                                    }

                                    this.recoverableClientAckMessages = null;
                                    if (this.recovering) {
                                       synchronized(this) {
                                          synchronized(this.lockObject) {
                                             if (this.recovering) {
                                                this.recovering = false;
                                                pushRequest = this.carryForwardOnReconnect = null;
                                             }
                                          }
                                       }
                                    }

                                    if (this.decrementWindow) {
                                       consumer.decrementWindowCurrent(clientResponsibleForAck);
                                    }
                                    continue;
                                 }

                                 this.recoverableClientAckMessages = null;
                                 if (this.recovering) {
                                    synchronized(this) {
                                       synchronized(this.lockObject) {
                                          if (this.recovering) {
                                             this.recovering = false;
                                             pushRequest = this.carryForwardOnReconnect = null;
                                          }
                                       }
                                    }
                                 }

                                 if (this.decrementWindow) {
                                    consumer.decrementWindowCurrent(clientResponsibleForAck);
                                 }
                              }
                           } else {
                              synchronized(this) {
                                 abnormalStop |= this.stopped || this.closeStarted;
                                 if (abnormalStop) {
                                    synchronized(this.lockObject) {
                                       pushEntry.setNext(pushRequest.getFirstPushEntry());
                                       pushRequest.setFirstPushEntry(pushEntry);

                                       for(JMSPushEntry tmp = pushEntry; tmp != null; tmp = tmp.getNext()) {
                                          pushRequest.setLastPushEntry(tmp);
                                       }
                                       break;
                                    }
                                 }
                              }
                           }
                        }

                        if (pushRequest != null) {
                           pushRequest = (JMSPushRequest)pushRequest.getNext();
                        }

                        if (pushRequest != null) {
                           continue;
                        }

                        var170 = false;
                        break label3784;
                     } catch (Throwable var238) {
                        JMSClientExceptionLogger.logStackTrace(var238);
                        var170 = false;
                     } finally {
                        if (var170) {
                           synchronized(this) {
                              this.clearState(4);
                              if (!this.stopped && !this.closeStarted && !this.isClosed()) {
                                 ;
                              } else {
                                 this.running = false;
                                 return;
                              }
                           }
                        }
                     }

                     synchronized(this) {
                        this.clearState(4);
                        if (!this.stopped && !this.closeStarted && !this.isClosed()) {
                           break label3783;
                        }

                        this.running = false;
                        return;
                     }
                  }

                  synchronized(this) {
                     this.clearState(4);
                     if (this.stopped || this.closeStarted || this.isClosed()) {
                        this.running = false;
                        return;
                     }
                  }
               }

               synchronized(this.lockObject) {
                  if (!this.havePushRequests()) {
                     this.running = false;
                     return;
                  }
               }

               if (!this.dispatchWorkManager.scheduleIfBusy(this.useForRunnable)) {
                  if (this.integrationManager != null) {
                     Correlation correlation = this.integrationManager.newCorrelation();
                     this.integrationManager.activateCorrelation(correlation);
                  }
                  break;
               }

               return;
            }
         }
      } finally {
         try {
            this.inlineHandleOnMessageException();
         } finally {
            if (KernelStatus.isServer()) {
               Thread.currentThread().setContextClassLoader(clSave);
               if (lastListenerContext != null) {
                  JMSEnvironment.getJMSEnvironment().popLocalJNDIContext();
               }

               if (lastListenerSubject != null || subjectPushed) {
                  this.subjectManager.popSubject(KERNEL_ID);
               }
            }

         }
      }
   }

   private static DiagnosticIntegrationManager getDiagnosticIntegrationManager() {
      try {
         return (DiagnosticIntegrationManager)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return Factory.getInstance();
            }
         });
      } catch (PrivilegedActionException var1) {
         return null;
      }
   }

   protected final void executeMM() {
      // $FF: Couldn't be decompiled
   }

   private void assignClientAckCarryForward(JMSPushRequest pushRequest) {
      if (pushRequest != null && pushRequest.getMessage().getClientResponsibleForAcknowledge()) {
         this.carryForwardOnReconnect = pushRequest;
      } else {
         this.carryForwardOnReconnect = null;
      }

   }

   private void onMessage(ConsumerInternal consumer, MessageListener messageListener, final MessageImpl message) {
      if (!this.transacted && this.acknowledgeMode == 2) {
         message.setSession(this);
      }

      synchronized(this) {
         this.rememberLastSequenceNumber(message.getSequenceNumber(), message.getId());
      }

      boolean clientResponsibleForAcknowledge = message.getClientResponsibleForAcknowledge();

      label155: {
         try {
            JMSWorkContextHelper.infectThread(message);
            message.setSerializeDestination(true);
            message.setForward(true);
            message.setSAFSequenceName((String)null);
            message.setSAFSeqNumber(0L);
            messageListener.onMessage(message);
            break label155;
         } catch (Throwable var16) {
            this.handleOnMessageFailure(var16);
         } finally {
            JMSWorkContextHelper.disinfectThread();
         }

         return;
      }

      if ((this.acknowledgeMode == 3 || this.acknowledgeMode == 1) && this.getLastSequenceNumber() != 0L && !this.transacted) {
         try {
            if (this.acknowledgeMode == 3 && this.checkDelayAckForDupsOK(message) && --this.dupsOKAckCount > 0) {
               this.requireAckForDupsOK = true;
            } else {
               this.requireAckForDupsOK = false;
               this.refreshedWithPendingWork = false;
               if (this.isRemoteDomain) {
                  CrossDomainSecurityManager.doAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.getConnection().getFrontEndDispatcher(), (AbstractSubject)null, false), new PrivilegedExceptionAction() {
                     public Object run() throws JMSException {
                        JMSSession.this.acknowledge((WLAcknowledgeInfo)message);
                        return null;
                     }
                  });
               } else {
                  this.acknowledge((WLAcknowledgeInfo)message);
               }

               this.dupsOKAckCount = this.dupsOKAckCountDown;
            }
         } catch (JMSException var15) {
            JMSException je = var15;
            if (this.onExceptionInternal(var15, true)) {
               synchronized(this) {
                  if (this.deferredException == null) {
                     this.deferredException = je;
                  }
               }
            }
         }

      }
   }

   private void handleOnMessageFailure(Throwable t) {
      JMSClientExceptionLogger.logStackTrace(t);
      boolean var15 = false;

      try {
         var15 = true;
         if (!this.transacted) {
            if (this.acknowledgeMode != 1 && this.acknowledgeMode != 3) {
               var15 = false;
            } else {
               try {
                  if (this.isRemoteDomain) {
                     CrossDomainSecurityManager.doAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.getConnection().getFrontEndDispatcher(), (AbstractSubject)null, false), new PrivilegedExceptionAction() {
                        public Object run() throws JMSException {
                           JMSSession.this.recover();
                           return null;
                        }
                     });
                     var15 = false;
                  } else {
                     this.recover();
                     var15 = false;
                  }
               } catch (JMSException var18) {
                  JMSClientExceptionLogger.logStackTrace(var18);
                  var15 = false;
               }
            }
         } else {
            var15 = false;
         }
      } finally {
         if (var15) {
            RuntimeException re1 = new RuntimeException(JMSClientExceptionLogger.logClientThrowingExceptionLoggable().getMessage(), t);
            weblogic.jms.common.JMSException jmse = new weblogic.jms.common.JMSException(re1);
            if (this.onExceptionInternal(jmse, true)) {
               synchronized(this) {
                  if (this.deferredException == null) {
                     this.deferredException = jmse;
                  }
               }
            }

         }
      }

      RuntimeException re1 = new RuntimeException(JMSClientExceptionLogger.logClientThrowingExceptionLoggable().getMessage(), t);
      JMSException jmse = new weblogic.jms.common.JMSException(re1);
      if (this.onExceptionInternal(jmse, true)) {
         synchronized(this) {
            if (this.deferredException == null) {
               this.deferredException = jmse;
            }
         }
      }

   }

   final void decrementConsumerListenerCount() {
      --this.consumerListenerCount;
   }

   final void incrementConsumerListenerCount() {
      ++this.consumerListenerCount;
   }

   final void setState(int state) {
      this.state |= state;
      if ((state & 4) != 0) {
         this.listenerThread = Thread.currentThread();
      }

   }

   private boolean inState(int state) {
      return (this.state & state) != 0;
   }

   private boolean inListener() {
      return Thread.currentThread().equals(this.listenerThread);
   }

   final synchronized void clearState(int state) {
      this.state &= ~state;
      if ((state & 4) != 0) {
         this.listenerThread = null;
      }

      if (this.waiterCount > 0) {
         this.notifyAll();
      }

   }

   private void waitForState(int allowableStates) throws JMSException {
      while((this.state & ~allowableStates) != 0) {
         ++this.waiterCount;

         try {
            this.wait();
         } catch (InterruptedException var6) {
            throw new weblogic.jms.common.JMSException(var6);
         } finally {
            --this.waiterCount;
         }
      }

   }

   public boolean isClosed() {
      return this.sessionId == null;
   }

   boolean isRemoteDomain() {
      return this.isRemoteDomain;
   }

   public boolean isReconnectControllerClosed() {
      return this.wlSessionImpl == null || this.wlSessionImpl.isClosed();
   }

   final synchronized void checkClosed() throws JMSException {
      if (this.isClosed()) {
         Object lock = this.wlSessionImpl == null ? this : this.wlSessionImpl.getConnectionStateLock();
         synchronized(lock){}

         try {
            if (this.isReconnectControllerClosed()) {
               throw new AlreadyClosedException(JMSClientExceptionLogger.logSessionIsClosedLoggable());
            } else {
               throw new LostServerException(JMSClientExceptionLogger.logLostServerConnectionLoggable());
            }
         } finally {
            ;
         }
      }
   }

   public final void publicCheckClosed() throws JMSException {
      this.checkClosed();
   }

   public final synchronized void checkSAFClosed() throws JMSException {
      if (this.isClosed()) {
         throw new IllegalStateException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
      }
   }

   private void leaveGroup(DestinationImpl destination, ConsumerInternal consumer) throws JMSException {
      try {
         this.mSock.leaveGroup(destination, consumer);
      } catch (IOException var4) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logCannotLeaveMulticastGroupLoggable(destination.getMulticastAddress(), var4));
      }
   }

   private synchronized void addUnackedMessage(ConsumerInternal consumer, MessageImpl message) {
      if (this.acknowledgeMode != 4 && this.acknowledgeMode != 128) {
         if (!message.getClientResponsibleForAcknowledge()) {
            UnackedMessage unackedMessage = new UnackedMessage(consumer, message.getSequenceNumber());
            unackedMessage.next = this.firstUnackedMessage;
            this.firstUnackedMessage = unackedMessage;
            this.setPendingWorkOnMsgRecv();
         } else {
            if (this.clientAckList == null) {
               this.clientAckList = new MessageList();
            }

            long sequenceNumber = message.getSequenceNumber();
            JMSMessageReference mRef;
            if (this.mRefCache != null) {
               mRef = this.mRefCache;
               this.mRefCache = null;
               mRef.reset(message, consumer);
            } else {
               mRef = new JMSMessageReference(message, consumer);
            }

            message.setMessageReference(mRef);
            mRef.setSequenceNumber(sequenceNumber);
            this.clientAckList.addLast(mRef);
         }

      }
   }

   private synchronized void addPendingWTMessage(ConsumerInternal consumer, long seq) {
      PendingWTMessage pendingWTMessage = new PendingWTMessage(consumer, seq);
      pendingWTMessage.next = this.firstPendingWTMessage;
      this.firstPendingWTMessage = pendingWTMessage;
   }

   public synchronized void removePendingWTMessage(long sequenceNumber, boolean adjustWindow) throws JMSException {
      PendingWTMessage savedPendingWTMessage = this.firstPendingWTMessage;

      PendingWTMessage prevPendingWTMessage;
      for(prevPendingWTMessage = null; savedPendingWTMessage != null && sequenceNumber != savedPendingWTMessage.sequenceNumber; savedPendingWTMessage = savedPendingWTMessage.next) {
         prevPendingWTMessage = savedPendingWTMessage;
      }

      if (savedPendingWTMessage != null) {
         if (savedPendingWTMessage == this.firstPendingWTMessage) {
            this.firstPendingWTMessage = null;
         } else {
            prevPendingWTMessage.next = null;
         }

         for(; savedPendingWTMessage != null; savedPendingWTMessage = savedPendingWTMessage.next) {
            ConsumerInternal messageConsumer = savedPendingWTMessage.consumer;
            if (adjustWindow) {
               messageConsumer.decrementWindowCurrent(false);
            }
         }
      }

   }

   private synchronized void removeUnackedMessage(long sequenceNumber, boolean clientResponsibleForAcknowledge, boolean isMbean) {
      if (clientResponsibleForAcknowledge) {
         if (this.clientAckList != null) {
            JMSMessageReference mRef = (JMSMessageReference)this.clientAckList.removeBeforeSequenceNumber(sequenceNumber);
            if (this.mRefCache == null && mRef != null) {
               mRef.prepareForCache();
               this.mRefCache = mRef;
            }

         }
      } else {
         UnackedMessage savedUnackedMessage = this.firstUnackedMessage;

         UnackedMessage prevUnackedMessage;
         for(prevUnackedMessage = null; savedUnackedMessage != null && sequenceNumber != savedUnackedMessage.sequenceNumber; savedUnackedMessage = savedUnackedMessage.next) {
            prevUnackedMessage = savedUnackedMessage;
         }

         if (savedUnackedMessage != null) {
            if (savedUnackedMessage == this.firstUnackedMessage) {
               this.firstUnackedMessage = null;
            } else {
               prevUnackedMessage.next = null;
            }

            for(; savedUnackedMessage != null; savedUnackedMessage = savedUnackedMessage.next) {
               ConsumerInternal messageConsumer = savedUnackedMessage.consumer;
               if (!isMbean && messageConsumer.getWindowCurrent() < messageConsumer.getWindowMaximum()) {
                  messageConsumer.setWindowCurrent(messageConsumer.getWindowCurrent() + 1);
               }
            }
         }

      }
   }

   public final int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 15620:
            this.pushMessage(request, false);
            return Integer.MAX_VALUE;
         default:
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoSuchMethod2Loggable(request.getMethodId(), this.getClass().getName()));
      }
   }

   public static String unitOfOrderFromID(JMSID jmsid) {
      return "weblogicUOO.T" + Long.toString(jmsid.getTimestamp(), 36) + ".S" + Long.toString((long)jmsid.getSeed(), 36) + ".C" + jmsid.getCounter();
   }

   public void setUnitOfOrder(String unitOfOrder) {
      if (".System".equals(unitOfOrder)) {
         unitOfOrder = unitOfOrderFromID(this.getJMSID());
      }

      this.unitOfOrder = unitOfOrder;
   }

   public void markAsSystemMessageListener(boolean val) {
      this.synchronousListener = val;
      this.prefetchStarted = false;
   }

   void setWLSessionImpl(WLSessionImpl recSess) {
      this.wlSessionImpl = recSess;
      this.setMapLocks(this.wlSessionImpl.getConnectionStateLock());
   }

   void setMapLocks(Object connectionStateLock) {
      this.consumers.setLock(connectionStateLock);
      this.producers.setLock(connectionStateLock);
      this.browsers.setLock(connectionStateLock);
   }

   void setPendingWork(boolean inVal) {
      this.pendingWork = inVal;
   }

   boolean checkRefreshedWithPendingWork() {
      this.setPendingWork(false);
      if (this.refreshedWithPendingWork) {
         this.refreshedWithPendingWork = false;
         return true;
      } else {
         return false;
      }
   }

   boolean prefetchStarted() {
      return this.prefetchStarted;
   }

   void startPrefetch() {
      this.prefetchStarted = true;
   }

   boolean prefetchDisabled() {
      return this.prefetchDisabled;
   }

   void disablePrefetch() {
      this.prefetchDisabled = true;
   }

   private boolean checkDelayAckForDupsOK(MessageImpl message) {
      if (this.allowDelayAckForDupsOK && message.getClientResponsibleForAcknowledge()) {
         this.allowDelayAckForDupsOK = false;
      }

      return this.allowDelayAckForDupsOK;
   }

   private void transferClientRspForAckMessages(JMSSession toSess) {
      if (toSess.session_clientResponsibleForAck && this.staleJMSSession != null && this.staleJMSSession.session_clientResponsibleForAck) {
         synchronized(this.staleJMSSession) {
            synchronized(this.staleJMSSession.lockObject) {
               this.carryForwardOnReconnect = this.staleJMSSession.carryForwardOnReconnect;
               if (this.carryForwardOnReconnect != null) {
                  if (this.firstPushRequest == null) {
                     this.setFirstPushRequest(this.firstPushRequest = this.carryForwardOnReconnect);
                     this.lastPushRequest = this.lastPushInList(this.firstPushRequest);
                  } else {
                     JMSPushRequest lastCarryBefore;
                     for(lastCarryBefore = this.lastPushInList(this.carryForwardOnReconnect); lastCarryBefore.getNext() != null && ((JMSPushRequest)lastCarryBefore.getNext()).getFrontEndSequenceNumber() < this.firstPushRequest.getFrontEndSequenceNumber(); lastCarryBefore = (JMSPushRequest)lastCarryBefore.getNext()) {
                     }

                     if (lastCarryBefore.getFrontEndSequenceNumber() < this.firstPushRequest.getFrontEndSequenceNumber()) {
                        lastCarryBefore.setNext(this.firstPushRequest);
                        this.setFirstPushRequest(this.carryForwardOnReconnect);
                     }
                  }
               }
            }
         }
      }

      if (this.consumersReconnect && this.session_clientResponsibleForAck) {
         toSess.session_clientResponsibleForAck = true;
         long lastSeqNo = 0L;
         if (this.clientAckList != null) {
            JMSMessageReference lastRef = (JMSMessageReference)this.clientAckList.getLast();
            if (lastRef != null) {
               lastSeqNo = lastRef.getSequenceNumber();
            }
         }

         boolean doFirstPushRequest = this.firstPushRequest != null && this.firstPushRequest.getMessage().getClientResponsibleForAcknowledge();
         if (doFirstPushRequest) {
            lastSeqNo = this.getMaxSequenceNumber(this.firstPushRequest, lastSeqNo);
         }

         boolean doFirstReceivePushRequest = this.firstReceivePushRequest != null && this.firstReceivePushRequest.getMessage().getClientResponsibleForAcknowledge();
         if (doFirstReceivePushRequest) {
            lastSeqNo = this.getMaxSequenceNumber(this.firstReceivePushRequest, lastSeqNo);
         }

         if (this.clientAckList != null) {
            for(JMSMessageReference mRef = (JMSMessageReference)this.clientAckList.getFirst(); mRef != null; mRef = (JMSMessageReference)mRef.getNext()) {
               MessageImpl mImpl = mRef.getMessage();
               mRef.setSequenceNumber(mRef.getSequenceNumber() - lastSeqNo);
               mImpl.setSequenceNumber(mImpl.getSequenceNumber() - lastSeqNo);
               ConsumerInternal oldConsumer = mRef.getConsumer();
               if (oldConsumer != null) {
                  JMSConsumer newCons = (JMSConsumer)toSess.replacementConsumerMap.get(oldConsumer.getJMSID());
                  if (newCons != null) {
                     mRef.reset(mImpl, newCons);
                  }
               }
            }
         }

         toSess.clientAckList = this.clientAckList;
         if (doFirstPushRequest) {
            toSess.setFirstPushRequest(this.refreshPushRequests(toSess, this.firstPushRequest, lastSeqNo));
            toSess.lastPushRequest = this.lastPushInList(toSess.firstPushRequest);
         }

         if (doFirstReceivePushRequest) {
            toSess.firstReceivePushRequest = this.refreshPushRequests(toSess, this.firstReceivePushRequest, lastSeqNo);
            toSess.lastReceivePushRequest = this.lastPushInList(toSess.firstReceivePushRequest);
         }

      }
   }

   private JMSPushRequest lastPushInList(JMSPushRequest first) {
      JMSPushRequest curPush = first;

      JMSPushRequest prev;
      for(prev = first; curPush != null; curPush = (JMSPushRequest)curPush.getNext()) {
         prev = curPush;
      }

      return prev;
   }

   void mapReplacementConsumer(JMSConsumer oldCons, JMSConsumer newCons) {
      this.replacementConsumerMap.put(oldCons.getJMSID(), newCons);
   }

   private JMSPushRequest refreshPushRequests(JMSSession newSess, JMSPushRequest currPushR, long offsetSeqNo) {
      while(currPushR != null && currPushR.getFirstPushEntry() == null) {
         currPushR = (JMSPushRequest)currPushR.getNext();
      }

      JMSPushRequest retPushR = null;

      for(JMSPushRequest prevPushR = null; currPushR != null; currPushR = (JMSPushRequest)currPushR.getNext()) {
         MessageImpl msgCurr = currPushR.getMessage();
         JMSPushRequest newPushR = new JMSPushRequest(0, (JMSID)null, msgCurr);
         if (retPushR == null) {
            retPushR = newPushR;
         } else {
            prevPushR.setNext(newPushR);
         }

         msgCurr.setSequenceNumber(msgCurr.getSequenceNumber() - offsetSeqNo);
         prevPushR = newPushR;

         for(JMSPushEntry currPushE = currPushR.getFirstPushEntry(); currPushE != null; currPushE = currPushE.getNext()) {
            JMSConsumer newCons = (JMSConsumer)newSess.replacementConsumerMap.get(currPushE.getConsumerId());
            if (newCons != null && currPushE.getClientResponsibleForAcknowledge()) {
               long seqNo = currPushE.getFrontEndSequenceNumber() - offsetSeqNo;
               JMSPushEntry newPushE = new JMSPushEntry((JMSID)null, newCons.getJMSID(), 0L, seqNo, msgCurr.getDeliveryCount(), 2097152);
               newPushE.setClientResponsibleForAcknowledge(true);
               newPushR.addPushEntry(newPushE);
            } else {
               retPushR = retPushR;
            }
         }
      }

      return retPushR;
   }

   private long getMaxSequenceNumber(JMSPushRequest pushR, long init) {
      long ret = init;

      for(JMSPushRequest currPushR = pushR; currPushR != null; currPushR = (JMSPushRequest)currPushR.getNext()) {
         for(JMSPushEntry currPushE = currPushR.getFirstPushEntry(); currPushE != null; currPushE = currPushE.getNext()) {
            if (ret < currPushE.getFrontEndSequenceNumber()) {
               ret = currPushE.getFrontEndSequenceNumber();
            }
         }
      }

      return ret;
   }

   public void acknowledgeAsync(WLAcknowledgeInfo ackInfo, CompletionListener listener) {
      try {
         this.acknowledge(ackInfo);
         listener.onCompletion((Object)null);
      } catch (Throwable var4) {
         listener.onException(var4);
      }

   }

   public void sendAsync(MessageProducer producer, Message message, CompletionListener listener) {
      try {
         producer.send(message);
      } catch (Throwable var5) {
         listener.onException(var5);
      }

   }

   public void sendAsync(WLMessageProducer producer, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      try {
         producer.send(message, deliveryMode, priority, timeToLive);
      } catch (Throwable var9) {
         listener.onException(var9);
      }

   }

   public void sendAsync(WLMessageProducer producer, javax.jms.Destination destination, Message message, CompletionListener listener) {
      try {
         producer.send(destination, message);
      } catch (Throwable var6) {
         listener.onException(var6);
      }

   }

   public void sendAsync(WLMessageProducer producer, javax.jms.Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      try {
         producer.send(destination, message, deliveryMode, priority, timeToLive);
      } catch (Throwable var10) {
         listener.onException(var10);
      }

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
      return null;
   }

   boolean XABegin() {
      return this.xaRes != null && this.xaRes instanceof InterposedTransactionManagerXAResource && ((InterposedTransactionManagerXAResource)this.xaRes).begin(3) == 1;
   }

   void XAFinish() {
      if (this.xaRes != null && this.xaRes instanceof InterposedTransactionManagerXAResource) {
         ((InterposedTransactionManagerXAResource)this.xaRes).finish(3);
      }

   }

   void XAClose() {
      if (this.xaRes != null && this.xaRes instanceof InterposedTransactionManagerXAResource) {
         ((InterposedTransactionManagerXAResource)this.xaRes).close();
      }

      this.xaRes = null;
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name) throws JMSException {
      return this.createDurableConsumer(topic, name, (String)null, false);
   }

   public MessageConsumer createDurableConsumer(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation2Loggable("createDurableConsumer").getMessage());
      } else {
         return this.createDurableSubscriber(topic, name, messageSelector, noLocal);
      }
   }

   public MessageConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName) throws JMSException {
      return (TopicSubscriber)this.createSharedConsumer(topic, sharedSubscriptionName, (String)null);
   }

   public MessageConsumer createSharedConsumer(Topic topic, String sharedSubscriptionName, String messageSelector) throws JMSException {
      this.checkClosed();
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation2Loggable("createSharedConsumer").getMessage());
      } else if (sharedSubscriptionName == null) {
         throw new weblogic.jms.common.JMSException("The shared subscription name parameter passed to createSharedConsumer was null");
      } else if (sharedSubscriptionName.length() == 0) {
         throw new weblogic.jms.common.JMSException("The shared subscription name parameter passed to createSharedConsumer has zero length");
      } else {
         byte flags = 5;
         return (TopicSubscriber)this.createConsumer(topic, messageSelector, false, sharedSubscriptionName, false, true, flags);
      }
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name) throws JMSException {
      return this.createSharedDurableConsumer(topic, name, (String)null);
   }

   public MessageConsumer createSharedDurableConsumer(Topic topic, String name, String messageSelector) throws JMSException {
      this.checkClosed();
      if (this.type == 2) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation2Loggable("createSharedDurableConsumer").getMessage());
      } else if (name == null) {
         throw new weblogic.jms.common.JMSException("The name parameter passed to createSharedDurableConsumer was null");
      } else if (name.length() == 0) {
         throw new weblogic.jms.common.JMSException("The name parameter passed to createSharedDurableConsumer has zero length");
      } else if (topic instanceof DistributedDestinationImpl) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidDistributedTopicLoggable());
      } else {
         byte flags = 5;
         return (TopicSubscriber)this.createConsumer(topic, messageSelector, false, name, true, true, flags);
      }
   }

   void enqueueAsyncSendCallback(AsyncSendCallback cb) throws JMSException {
      this.asyncSendCallbackRunner.enqueueAsyncSendCallback(cb);
   }

   void incrementAsyncSendPendingSize(AsyncSendCallback cb) {
      this.asyncSendCallbackRunner.incrementPendingSize(cb);
   }

   void dequeueAsyncSendCallback(AsyncSendCallback cb) {
      this.asyncSendCallbackRunner.dequeueAsyncSendCallback(cb);
   }

   void wakeupAsyncSendCallback() {
      this.asyncSendCallbackRunner.wakeup();
   }

   void checkOpPermissionForAsyncSend(String op) throws IllegalStateException {
      if (this.asyncSendCallbackRunner.isRunnerThread(Thread.currentThread())) {
         throw new IllegalStateException("Operation " + op + " is not allowed from javax.jms.CompletionListener");
      }
   }

   void asyncSendCallbackRunnerClosing() {
      this.asyncSendCallbackRunner.closing();
   }

   void waitTillAllAsyncSendProcessed() throws JMSException {
      this.asyncSendCallbackRunner.waitTillEmpty();
   }

   void blockAsyncSendIfOverPendingThreshold() throws JMSException {
      this.asyncSendCallbackRunner.blockIfOverPendingThreshold();
   }

   static {
      try {
         IGNORE_JmsAsyncQueue = System.getProperty("weblogic.jms.IGNORE_JmsAsyncQueue", "false").equalsIgnoreCase("true");
      } catch (SecurityException var1) {
      }

      ASYNC_RESERVED_MSG = new TextMessageImpl("internal ASYNC_RESERVED_MSG");
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private class AsyncSendCallbackRunnable implements Runnable {
      private static final long MAX_TOTAL_PENDING_SIZE = 1000000L;
      private static final long PER_MESSAGE_SIZE_BASE = 10000L;
      private long totalPendingSize = 0L;
      private LinkedList asyncSendCallbacks = null;
      private volatile WorkManager myworkManager;
      private Thread mycurrentThread;
      private boolean myrunning;
      private boolean myclosed;
      private boolean myclosing;

      protected AsyncSendCallbackRunnable(WorkManager wm) {
         this.myworkManager = wm;
         this.mycurrentThread = null;
         this.myrunning = false;
         this.myclosed = false;
         this.myclosing = false;
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner created for Session@" + JMSSession.this.hashCode() + ", WorkManager@" + (this.myworkManager == null ? "null" : this.myworkManager.hashCode() + "[" + this.myworkManager + ", " + this.myworkManager.getClass().getName() + "]"));
         }

      }

      void enqueueAsyncSendCallback(AsyncSendCallback cb) throws JMSException {
         if (this.myworkManager == null) {
            Class var2 = JMSSession.class;
            synchronized(JMSSession.class) {
               if (this.isClosing()) {
                  throw new JMSException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
               }

               if (this.myworkManager == null) {
                  int thrcnt = JMSSession.this.connection.clientDispatcherPartitionContext.getConfiguredThreadPoolSizeForClient();
                  if (thrcnt <= 0) {
                     thrcnt = JMSSession.this.dispatchWorkManager.getConfiguredThreadCount();
                  }

                  this.myworkManager = WorkManagerFactory.getInstance().findOrCreate("JmsAsyncSend", 100, thrcnt, -1);
                  if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                     JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner created WorkManager@" + this.myworkManager.hashCode() + "[" + this.myworkManager + ", " + this.myworkManager.getClass().getName() + ",(" + thrcnt + "," + this.myworkManager.getConfiguredThreadCount() + ")] for Session@" + JMSSession.this.hashCode() + "[" + JMSSession.this.sessionId + "]");
                  }
               }
            }
         }

         synchronized(this) {
            if (this.myclosed || this.myclosing) {
               throw new JMSException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
            }

            if (this.asyncSendCallbacks == null) {
               this.asyncSendCallbacks = new LinkedList();
            }

            this.asyncSendCallbacks.add(cb);
            this.notifyAll();
         }

         cb.enqueued();
         if (this.beforeSchedule()) {
            this.myworkManager.schedule(this);
            if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
               JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner scheduled for session=" + JMSSession.this.sessionId + ", wm=" + this.myworkManager);
            }
         }

      }

      synchronized void incrementPendingSize(AsyncSendCallback cb) {
         if (this.asyncSendCallbacks != null) {
            if (this.asyncSendCallbacks.contains(cb)) {
               this.totalPendingSize += 10000L + cb.getMessageSize();
               this.notifyAll();
            }

         }
      }

      void dequeueAsyncSendCallback(AsyncSendCallback cb) {
         synchronized(this) {
            if (this.asyncSendCallbacks == null) {
               return;
            }

            if (this.asyncSendCallbacks.remove(cb)) {
               this.totalPendingSize -= 10000L + cb.getMessageSize();
            }

            this.notifyAll();
         }

         cb.dequeued();
      }

      private boolean hasCallbackPending() {
         AsyncSendCallback head;
         synchronized(this) {
            if (this.asyncSendCallbacks == null) {
               return false;
            }

            head = (AsyncSendCallback)this.asyncSendCallbacks.peek();
         }

         return head == null ? false : head.isCallbackPending();
      }

      synchronized void wakeup() {
         this.notifyAll();
      }

      void onReconnect() {
         AsyncSendCallback[] cbs;
         synchronized(this) {
            if (this.asyncSendCallbacks == null) {
               return;
            }

            cbs = (AsyncSendCallback[])((AsyncSendCallback[])this.asyncSendCallbacks.toArray(new AsyncSendCallback[this.asyncSendCallbacks.size()]));
         }

         JMSException ex = new JMSException("Connection reconnect");

         for(int i = 0; i < cbs.length; ++i) {
            AsyncSendCallback cb = cbs[i];
            cb.processException(ex);
         }

      }

      public void run() {
         boolean var15 = false;

         label142: {
            try {
               var15 = true;
               int cnt;
               synchronized(this) {
                  this.mycurrentThread = Thread.currentThread();
                  cnt = this.asyncSendCallbacks.size();
               }

               if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                  JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner run(), thread[" + this.mycurrentThread + "], AsyncSendCallbacks=" + cnt + ", session=" + JMSSession.this.sessionId + ", wm=" + this.myworkManager);
               }

               this.executeCallback();
               var15 = false;
               break label142;
            } catch (Throwable var20) {
               JMSClientExceptionLogger.logStackTrace(var20);
               var15 = false;
            } finally {
               if (var15) {
                  if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                     JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner run() exit, thread[" + this.mycurrentThread + "], session=" + JMSSession.this.sessionId + ", wm=" + this.myworkManager);
                  }

                  synchronized(this) {
                     this.myrunning = false;
                     this.mycurrentThread = null;
                  }
               }
            }

            if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
               JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner run() exit, thread[" + this.mycurrentThread + "], session=" + JMSSession.this.sessionId + ", wm=" + this.myworkManager);
            }

            synchronized(this) {
               this.myrunning = false;
               this.mycurrentThread = null;
               return;
            }
         }

         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("AsyncSendCallbackRunner run() exit, thread[" + this.mycurrentThread + "], session=" + JMSSession.this.sessionId + ", wm=" + this.myworkManager);
         }

         synchronized(this) {
            this.myrunning = false;
            this.mycurrentThread = null;
         }

      }

      private void executeCallback() {
         while(true) {
            synchronized(this) {
               while(!this.hasCallbackPending()) {
                  if ((this.myclosed || this.myclosing) && (this.asyncSendCallbacks == null || this.asyncSendCallbacks.isEmpty())) {
                     this.myrunning = false;
                     this.mycurrentThread = null;
                     return;
                  }

                  try {
                     this.wait();
                  } catch (InterruptedException var8) {
                     JMSClientExceptionLogger.logStackTrace(var8);
                  }
               }
            }

            while(this.hasCallbackPending()) {
               AsyncSendCallback head;
               synchronized(this) {
                  head = (AsyncSendCallback)this.asyncSendCallbacks.peek();
               }

               if (head != null && head.isCallbackPending()) {
                  head.callCompletionListener();
               }
            }

            synchronized(this) {
               if (this.asyncSendCallbacks.isEmpty()) {
                  this.notifyAll();
                  this.myrunning = false;
                  this.mycurrentThread = null;
                  return;
               }
            }
         }
      }

      synchronized boolean isRunnerThread(Thread thr) {
         return thr == this.mycurrentThread;
      }

      synchronized boolean beforeSchedule() {
         if (this.myrunning) {
            return false;
         } else {
            this.myrunning = true;
            return true;
         }
      }

      private synchronized boolean isClosing() {
         return this.myclosing || this.myclosed;
      }

      synchronized void closing() {
         this.myclosing = true;
         this.notifyAll();
      }

      synchronized void close() {
         this.myclosed = true;
         this.notifyAll();
      }

      void waitTillEmpty() throws JMSException {
         boolean schedule = false;
         synchronized(this) {
            if (this.asyncSendCallbacks == null) {
               return;
            }

            if (!this.asyncSendCallbacks.isEmpty() && this.beforeSchedule()) {
               schedule = true;
            }
         }

         if (schedule) {
            this.myworkManager.schedule(this);
         }

         synchronized(this) {
            while(!this.asyncSendCallbacks.isEmpty()) {
               try {
                  this.wait();
               } catch (InterruptedException var5) {
                  JMSClientExceptionLogger.logStackTrace(var5);
               }
            }

         }
      }

      void blockIfOverPendingThreshold() throws JMSException {
         if (this.isClosing()) {
            throw new JMSException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
         } else {
            boolean schedule = false;
            synchronized(this) {
               if (this.asyncSendCallbacks == null) {
                  return;
               }

               if (!this.asyncSendCallbacks.isEmpty() && this.beforeSchedule()) {
                  schedule = true;
               }
            }

            if (schedule) {
               this.myworkManager.schedule(this);
            }

            synchronized(this) {
               while(!this.isClosing() && this.totalPendingSize > 1000000L) {
                  try {
                     this.wait();
                  } catch (InterruptedException var5) {
                     JMSClientExceptionLogger.logStackTrace(var5);
                  }
               }
            }

            if (this.isClosing()) {
               throw new JMSException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
            }
         }
      }
   }

   private class JMSConsumerReceiveResponsePrivate extends JMSConsumerReceiveResponse {
      static final long serialVersionUID = -7380653133580038280L;
      private int deliveryCount;

      JMSConsumerReceiveResponsePrivate(MessageImpl message, long sequenceNumber, boolean isTransactional, int deliveryCount) {
         super(message, sequenceNumber, isTransactional);
         this.deliveryCount = deliveryCount;
      }

      int getDeliveryCount() {
         return this.deliveryCount;
      }
   }

   private class UseForRunnable implements Runnable {
      private JMSSession session;

      protected UseForRunnable(JMSSession session) {
         this.session = session;
      }

      public void run() {
         this.session.executeMessage();
      }
   }

   static final class PendingWTMessage {
      final ConsumerInternal consumer;
      final long sequenceNumber;
      PendingWTMessage next;

      PendingWTMessage(ConsumerInternal consumer, long sequenceNumber) {
         this.consumer = consumer;
         this.sequenceNumber = sequenceNumber;
      }
   }

   static final class UnackedMessage {
      final ConsumerInternal consumer;
      final long sequenceNumber;
      UnackedMessage next;

      UnackedMessage(ConsumerInternal consumer, long sequenceNumber) {
         this.consumer = consumer;
         this.sequenceNumber = sequenceNumber;
      }
   }
}
