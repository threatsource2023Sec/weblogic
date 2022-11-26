package weblogic.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSession;
import javax.security.auth.Subject;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DestroyConnectionException;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.JMSWorkManager;
import weblogic.jms.common.LostServerException;
import weblogic.jms.common.PasswordStore;
import weblogic.jms.common.PeerVersionable;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.SecurityDispatcherWrapper;
import weblogic.jms.frontend.FEConnectionCloseRequest;
import weblogic.jms.frontend.FEConnectionSetClientIdRequest;
import weblogic.jms.frontend.FEConnectionStartRequest;
import weblogic.jms.frontend.FEConnectionStopRequest;
import weblogic.jms.frontend.FERemoveSubscriptionRequest;
import weblogic.jms.frontend.FESessionCreateRequest;
import weblogic.jms.frontend.FESessionCreateResponse;
import weblogic.jms.frontend.FETemporaryDestinationDestroyRequest;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherImpl;
import weblogic.messaging.dispatcher.DispatcherStateChangeListener;
import weblogic.messaging.dispatcher.DispatcherWrapperState;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.NestedThrowable;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class JMSConnection implements ConnectionInternal, DispatcherStateChangeListener, Externalizable, Invocable, Runnable, Reconnectable, Cloneable, TimerListener {
   static final long serialVersionUID = 7025750175126041724L;
   private static final AbstractSubject KernelID = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   public static final byte EXTVERSION61 = 1;
   public static final byte EXTVERSION70 = 3;
   public static final byte EXTVERSION81 = 4;
   public static final byte EXTVERSION90 = 5;
   public static final byte EXTVERSION910 = 6;
   public static final byte EXTVERSION920 = 7;
   public static final byte EXTVERSION1033 = 8;
   public static final byte EXTVERSION122140 = 9;
   private static final byte VERSION_MASK = 15;
   private static final byte TIMEOUT_MASK = 64;
   private static final byte EXTVERSION = 9;
   private static final byte COMPRESSION_MASK = 1;
   private static final byte UNIT_OF_ORDER_MASK = 2;
   private static final byte SUBJECT_MASK = 4;
   public static final int SYNCHRONOUS_PREFETCH_DISABLED = 0;
   public static final int SYNCHRONOUS_PREFETCH_ENABLED = 1;
   public static final int SYNCHRONOUS_PREFETCH_TOPIC_SUBSCRIBER_ONLY = 2;
   public static final int ONE_WAY_SEND_DISABLED = 0;
   public static final int ONE_WAY_SEND_ENABLED = 1;
   public static final int ONE_WAY_SEND_TOPIC_ONLY = 2;
   static final int TYPE_UNSPECIFIED = 0;
   static final int TYPE_QUEUE = 1;
   static final int TYPE_TOPIC = 2;
   private JMSID connectionId;
   private String clientId;
   private int clientIdPolicy = 0;
   private int subscriptionSharingPolicy = 0;
   private int type;
   private int deliveryMode;
   private int priority;
   private long timeToDeliver;
   private long timeToLive;
   private long sendTimeout;
   private long redeliveryDelay;
   private boolean userTransactionsEnabled;
   private boolean allowCloseInOnMessage;
   private long transactionTimeout;
   private boolean isLocal;
   private int messagesMaximum;
   private int overrunPolicy;
   private int acknowledgePolicy;
   private String wlsServerName;
   private String runtimeMBeanName;
   private transient int refCount;
   private boolean flowControl;
   private int flowMinimum;
   private int flowMaximum;
   private double flowDecrease;
   private int flowIncrease;
   private long flowInterval;
   private boolean xaServerEnabled;
   private String unitOfOrder;
   private int synchronousPrefetchMode = 0;
   private boolean stopped = true;
   private final Object sublock = new Object();
   private LockedMap sessions = new LockedMap("sessions", (Object)null);
   private LockedMap durableSubscribers = new LockedMap("durables", (Object)null);
   private JMSDispatcher dispatcher;
   private DispatcherWrapper dispatcherWrapper;
   private JMSExceptionContext exceptionContext;
   private boolean firedExceptoinListener;
   private PeerVersionable peerVersionable;
   private byte version = 9;
   private PeerInfo peerInfo;
   private String dispatchPolicyName = JMSWorkManager.getJMSSessionOnMessageWMName();
   private int compressionThreshold = Integer.MAX_VALUE;
   private int pipelineGeneration = 0;
   private WLConnectionImpl wlConnectionImpl;
   private JMSConnection preDisconnectState;
   private JMSConnection replacementConnection;
   private JMSConnection originalConnection;
   private PasswordStore pwdStore;
   private Object uHandle;
   private Object pHandle;
   private boolean bReplacement;
   public int oneWaySendMode;
   public int oneWaySendWindowSize;
   private int reconnectPolicy = 7;
   private long reconnectBlockingMillis = 60000L;
   private long totalReconnectPeriodMillis = -1L;
   private boolean wantXAConnection;
   private volatile boolean isClosed;
   private static int INITIAL_RETRY_DELAY = 125;
   private long nextRetry;
   private static final long RETRY_DELAY_MAXIMUM = 300000L;
   private static final int RETRY_DELAY_LEFT_SHIFT = 1;
   private static final int RETRY_DELAY_MIN_INTERVAL = 5000;
   private volatile Timer timer;
   private static final String RECONNECT_ID = "weblogic.jms.Reconnect";
   private static final Object TIMER_WORKMANAGER_LOCK = new Object();
   private static WorkManager RECONNECT_WORK_MANAGER;
   private static TimerManager RECONNECT_TIMER_MANAGER;
   private static final boolean isT3Client;
   private final Object acknowledgePolicyLock;
   private DestinationImpl inboundDest;
   private Hashtable jndiEnv;
   private boolean isWrappedIC;
   private AbstractSubject subject;
   private transient boolean jmsSessionPooledInWrapper;
   transient DispatcherPartitionContext clientDispatcherPartitionContext;

   public JMSConnection(JMSID connectionId, String clientId, int clientIdPolicy, int subscriptionSharingPolicy, int deliveryMode, int priority, long timeToDeliver, long timeToLive, long sendTimeout, long redeliveryDelay, long transactionTimeout, boolean userTransactionsEnabled, boolean allowCloseInOnMessage, int messagesMaximum, int overrunPolicy, int acknowledgePolicy, boolean isLocal, DispatcherWrapper dispatcherWrapper, boolean flowControl, int flowMinimum, int flowMaximum, int flowInterval, int flowSteps, boolean xaServerEnabled, String unitOfOrder, PeerVersionable peerVersionable, String wlsServerName, String runtimeMBeanName, PeerInfo peerInfo, int compressionThreshold, int synchronousPrefetchMode, int oneWaySendMode, int oneWaySendWindowSize, int reconnectPolicy, long reconnectBlockingMillis, long totalReconnectPeriodMillis) {
      this.nextRetry = (long)INITIAL_RETRY_DELAY;
      this.acknowledgePolicyLock = new Object();
      this.isWrappedIC = false;
      this.subject = null;
      this.jmsSessionPooledInWrapper = false;
      this.peerVersionable = peerVersionable;
      this.connectionId = connectionId;
      this.clientId = clientId;
      this.clientIdPolicy = clientIdPolicy;
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
      this.unitOfOrder = unitOfOrder;
      this.deliveryMode = deliveryMode;
      this.priority = priority;
      this.timeToDeliver = timeToDeliver;
      this.timeToLive = timeToLive;
      this.sendTimeout = sendTimeout;
      this.redeliveryDelay = redeliveryDelay;
      this.transactionTimeout = transactionTimeout;
      this.userTransactionsEnabled = userTransactionsEnabled;
      this.allowCloseInOnMessage = allowCloseInOnMessage;
      this.messagesMaximum = messagesMaximum;
      this.overrunPolicy = overrunPolicy;
      this.acknowledgePolicy = acknowledgePolicy;
      this.isLocal = isLocal;
      this.dispatcherWrapper = dispatcherWrapper;
      this.xaServerEnabled = xaServerEnabled;
      this.flowControl = flowControl;
      this.flowMinimum = flowMinimum;
      this.flowMaximum = flowMaximum;
      this.wlsServerName = wlsServerName;
      this.runtimeMBeanName = runtimeMBeanName;
      this.flowIncrease = (flowMaximum - flowMinimum) / flowSteps;
      if (this.flowIncrease < 1) {
         this.flowIncrease = 1;
      }

      this.flowDecrease = (double)flowMinimum / (double)flowMaximum;
      this.flowDecrease = Math.pow(this.flowDecrease, 1.0 / (double)flowSteps);
      this.flowInterval = (long)(flowInterval * 1000 / flowSteps);
      if (this.flowInterval < 1L) {
         this.flowInterval = 1L;
      }

      this.peerInfo = peerInfo;
      if (PeerInfo.VERSION_DIABLO.compareTo(peerInfo) <= 0) {
         this.pipelineGeneration = 15728640;
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("pipelineGeneration is " + JMSPushEntry.displayRecoverGeneration(this.pipelineGeneration));
      }

      this.compressionThreshold = compressionThreshold;
      this.synchronousPrefetchMode = synchronousPrefetchMode;
      this.oneWaySendMode = oneWaySendMode;
      this.oneWaySendWindowSize = oneWaySendWindowSize;
      this.internalSetReconnect(reconnectPolicy, reconnectBlockingMillis, totalReconnectPeriodMillis);
   }

   private void internalSetReconnect(int reconnectPolicy, long reconnectBlockingMillis, long totalReconnectPeriodMillis) {
      if (isT3Client()) {
         this.reconnectPolicy = reconnectPolicy;
         this.reconnectBlockingMillis = reconnectBlockingMillis;
         this.totalReconnectPeriodMillis = totalReconnectPeriodMillis;
      } else {
         this.reconnectPolicy = 0;
         this.reconnectBlockingMillis = 0L;
         this.totalReconnectPeriodMillis = 0L;
      }

   }

   static boolean isT3Client() {
      return isT3Client;
   }

   final void setupDispatcher(Subject connSub, DispatcherPartitionContext clientDPC) throws DispatcherException {
      this.clientDispatcherPartitionContext = clientDPC;
      if (!(this.dispatcherWrapper.getRemoteDispatcher() instanceof DispatcherImpl) && !this.clientDispatcherPartitionContext.isLocal(this.dispatcherWrapper)) {
         DispatcherId newdId = new DispatcherId(this.dispatcherWrapper.getId(), this.connectionId.getCounter());
         this.dispatcherWrapper.setId(newdId);
      }

      this.dispatcher = this.clientDispatcherPartitionContext.dispatcherAdapterOrPartitionAdapter(this.dispatcherWrapper);
      if (connSub != null) {
         this.dispatcher = new SecurityDispatcherWrapper(connSub, this.dispatcher, this.clientDispatcherPartitionContext);
      }

      this.dispatcher.addDispatcherPeerGoneListener(this);
   }

   final void setType(int type) {
      this.type = type;
   }

   final int getType() {
      return this.type;
   }

   public final ConnectionConsumer createConnectionConsumer(Topic topic, String messageSelector, ServerSessionPool serverSessionPool, int messagesMaximum) throws JMSException {
      return this.createConnectionConsumer((Destination)topic, messageSelector, serverSessionPool, messagesMaximum);
   }

   public final ConnectionConsumer createSharedConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool serverSessionPool, int messagesMaximum) throws JMSException {
      this.checkClosed();
      if (this.type == 1) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation5Loggable().getMessage());
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logUnsupportedSharedConnectionConsumerLoggable());
      }
   }

   public final ConnectionConsumer createDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool pool, int maxMessages) throws JMSException {
      this.checkClosed();
      if (this.type == 1) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation5Loggable().getMessage());
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logUnsupportedSubscriptionLoggable());
      }
   }

   public final ConnectionConsumer createSharedDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool pool, int maxMessages) throws JMSException {
      this.checkClosed();
      if (this.type == 1) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnsupportedTopicOperation5Loggable().getMessage());
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logUnsupportedSharedDurableConnectionConsumerLoggable());
      }
   }

   public final QueueSession createQueueSession(boolean transacted, int acknowledgeMode) throws JMSException {
      if (acknowledgeMode == 128) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoMulticastOnQueueSessionsLoggable());
      } else {
         SessionInternal rsession = this.createSessionInternal(transacted, acknowledgeMode, false, 2);
         return rsession;
      }
   }

   public final TopicSession createTopicSession(boolean transacted, int acknowledgeMode) throws JMSException {
      SessionInternal rsession = this.createSessionInternal(transacted, acknowledgeMode, false, 1);
      return rsession;
   }

   public final Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
      return this.createSessionInternal(transacted, acknowledgeMode, false, 0);
   }

   public Session createSession() throws JMSException {
      return this.createSession(1);
   }

   public Session createSession(int sessionMode) throws JMSException {
      return sessionMode == 0 ? this.createSession(true, 0) : this.createSession(false, sessionMode);
   }

   protected final SessionInternal createSessionInternal(boolean transacted, int acknowledgeMode, boolean createXaSession, int sessionType) throws JMSException {
      JMSSession session = this.setupJMSSession(transacted, acknowledgeMode, createXaSession, sessionType);
      Object recSess;
      if (createXaSession) {
         recSess = new XASessionInternalImpl((JMSXASession)session, (XAConnectionInternalImpl)this.wlConnectionImpl);
      } else {
         recSess = new WLSessionImpl(session, this.wlConnectionImpl);
      }

      session.setWLSessionImpl((WLSessionImpl)recSess);
      return (SessionInternal)recSess;
   }

   JMSSession setupJMSSession(boolean transacted, int acknowledgeMode, boolean createXaSession, int sessionType) throws JMSException {
      Object session;
      if (createXaSession) {
         transacted = false;
         acknowledgeMode = 2;
         session = new JMSXASession(this, transacted, this.isStopped());
      } else {
         session = new JMSSession(this, transacted, acknowledgeMode, this.isStopped());
      }

      ((JMSSession)session).setType(sessionType);
      if (this.xaServerEnabled && KernelStatus.isServer()) {
         ((JMSSession)session).setUserTransactionsEnabled(true);
      }

      Object response = this.getFrontEndDispatcher().dispatchSync(new FESessionCreateRequest(this.connectionId, transacted, createXaSession, acknowledgeMode, (String)null));
      ((JMSSession)session).setId(((FESessionCreateResponse)response).getSessionId());
      ((JMSSession)session).setRuntimeMBeanName(((FESessionCreateResponse)response).getRuntimeMBeanName());
      ((JMSSession)session).setUnitOfOrder(this.unitOfOrder);
      synchronized(this) {
         this.sessionAdd((JMSSession)session);
         return (JMSSession)session;
      }
   }

   public final JMSDispatcher getFrontEndDispatcher() throws JMSException {
      if (this.isClosed()) {
         synchronized(this.wlConnectionImpl.getConnectionStateLock()){}

         try {
            if (this.isReconnectControllerClosed()) {
               throw new IllegalStateException(JMSClientExceptionLogger.logClosedConnectionLoggable().getMessage());
            } else {
               throw new LostServerException(JMSClientExceptionLogger.logLostServerConnectionLoggable());
            }
         } finally {
            ;
         }
      } else {
         return this.dispatcher;
      }
   }

   private void sessionAdd(JMSSession session) throws JMSException {
      if (this.sessions.put(session.getJMSID(), session) != null) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logDuplicateSessionLoggable());
      } else {
         if (this.isStopped()) {
            session.stop();
         } else {
            session.start();
         }

         InvocableManagerDelegate delegate = this.clientDispatcherPartitionContext.getInvocableManagerDelegate();
         delegate.invocableAdd(4, session);
      }
   }

   final synchronized void sessionRemove(JMSID sessionId) throws JMSException {
      if (this.sessions.remove(sessionId) != null) {
         InvocableManagerDelegate delegate = this.clientDispatcherPartitionContext.getInvocableManagerDelegate();
         delegate.invocableRemove(4, sessionId);
      }

   }

   public final ConnectionConsumer createConnectionConsumer(Queue queue, String messageSelector, ServerSessionPool serverSessionPool, int messagesMaximum) throws JMSException {
      return this.createConnectionConsumer((Destination)queue, messageSelector, serverSessionPool, messagesMaximum);
   }

   public final synchronized String getClientID() throws JMSException {
      this.checkClosed();
      return this.clientId;
   }

   final synchronized String getClientIDInternal() {
      return this.clientId;
   }

   public final synchronized void setClientID(String clientId) throws JMSException {
      this.setClientIDInternal(clientId, this.clientIdPolicy);
   }

   public final synchronized void setClientID(String clientId, String clientIdPolicy) throws IllegalArgumentException, JMSException {
      this.setClientIDInternal(clientId, WLConnectionImpl.validateAndConvertClientIdPolicy(clientIdPolicy));
   }

   public final synchronized void setClientIDInternal(String clientId, int clientIdPolicy) throws JMSException {
      if (this.clientId != null) {
         throw new IllegalStateException(JMSClientExceptionLogger.logClientIDSetLoggable(clientId, this.clientId).getMessage());
      } else if (clientId == null) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNullClientIDLoggable());
      } else if (clientId.length() == 0) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logZeroClientIDLoggable());
      } else {
         if (this.inboundDest != null && this.inboundDest.isOneCopyPerServer()) {
            String serverId = null;

            try {
               serverId = System.getProperty("weblogic.jms.ra.providers.wl.ServerID");
            } catch (Exception var5) {
               throw new JMSException("Fail to get serverId which is necessary for inbound connection to achieve One-Copy-Per-Server distribution mode.");
            }

            if (serverId == null || serverId.length() == 0) {
               throw new JMSException("serverId cannot be null for inbound connection to achieve One-Copy-Per-Server distribution mode. Please use system property 'weblogic.jms.ra.providers.wl.ServerID' to set it.");
            }

            clientId = clientId + "." + serverId;
         }

         Object response = this.getFrontEndDispatcher().dispatchSync(new FEConnectionSetClientIdRequest(this.connectionId, clientId, clientIdPolicy));
         this.clientId = clientId;
         this.clientIdPolicy = clientIdPolicy;
      }
   }

   public String getClientIDPolicy() {
      return WLConnectionImpl.convertClientIdPolicy(this.clientIdPolicy);
   }

   public String getSubscriptionSharingPolicy() {
      return getSubscriptionSharingPolicyAsString(this.subscriptionSharingPolicy);
   }

   public int getSubscriptionSharingPolicyAsInt() {
      return this.subscriptionSharingPolicy;
   }

   public void setSubscriptionSharingPolicy(String subscriptionSharingPolicy) throws JMSException, IllegalArgumentException {
      int subscriptionSharingPolicyInt = getSubscriptionSharingPolicyAsInt(subscriptionSharingPolicy);
      this.subscriptionSharingPolicy = subscriptionSharingPolicyInt;
   }

   public int getClientIDPolicyInt() {
      return this.clientIdPolicy;
   }

   public static int getSubscriptionSharingPolicyAsInt(String policy) {
      if (policy.equals(JMSConstants.SUBSCRIPTION_EXCLUSIVE)) {
         return 0;
      } else if (policy.equals(JMSConstants.SUBSCRIPTION_SHARABLE)) {
         return 1;
      } else {
         throw new IllegalArgumentException("Unrecognized SubscriptionSharingPolicy " + policy);
      }
   }

   public static String getSubscriptionSharingPolicyAsString(int policy) {
      switch (policy) {
         case 0:
            return JMSConstants.SUBSCRIPTION_EXCLUSIVE;
         case 1:
            return JMSConstants.SUBSCRIPTION_SHARABLE;
         default:
            throw new IllegalArgumentException("Unrecognized SubscriptionSharingPolicy " + policy);
      }
   }

   public final ConnectionMetaData getMetaData() throws JMSException {
      this.checkClosed();
      return this;
   }

   public final synchronized void setExceptionListener(ExceptionListener listener) throws JMSException {
      this.checkClosed();
      this.exceptionContext = new JMSExceptionContext(listener, this.isInbound());
   }

   void copyExceptionContext(JMSConnection old) {
      this.exceptionContext = old.exceptionContext;
   }

   synchronized boolean isStopped() {
      return this.stopped;
   }

   public final synchronized void start() throws JMSException {
      JMSException savedException = null;
      this.checkClosed();
      if (this.stopped) {
         Iterator iterator = this.sessions.values().iterator();

         while(iterator.hasNext()) {
            JMSSession session = (JMSSession)iterator.next();

            try {
               session.start();
            } catch (JMSException var5) {
               if (savedException == null) {
                  savedException = var5;
               }
            }
         }

         Object response = this.getFrontEndDispatcher().dispatchSync(new FEConnectionStartRequest(this.connectionId));
         this.stopped = false;
         if (savedException != null) {
            throw savedException;
         }
      }
   }

   private synchronized void resume(JMSConnection pds) throws JMSException {
      Iterator iterator = this.sessions.values().iterator();

      while(iterator.hasNext()) {
         JMSSession session = (JMSSession)iterator.next();
         session.resume();
      }

      this.stopped = true;
      if (!pds.stopped) {
         Object response = this.getFrontEndDispatcher().dispatchSync(new FEConnectionStartRequest(this.connectionId));
         this.stopped = false;
      }
   }

   public final synchronized void stop() throws JMSException {
      this.checkClosed();
      this.isCloseAllowed("stop");
      JMSException savedException = null;
      if (!this.stopped) {
         this.stopped = true;
         if (this.isConnected()) {
            Response var2 = this.getFrontEndDispatcher().dispatchSync(new FEConnectionStopRequest(this.connectionId));
         }

         Iterator iterator = this.sessions.values().iterator();

         while(iterator.hasNext()) {
            JMSSession session = (JMSSession)iterator.next();

            try {
               session.stop();
            } catch (JMSException var5) {
               if (savedException == null) {
                  savedException = var5;
               }
            }
         }

         if (savedException != null) {
            throw savedException;
         }
      }
   }

   public final void close() throws JMSException {
      this.mergedCloseAndOnException((JMSException)null, true);
   }

   public void isCloseAllowed(String operation) throws JMSException {
      Iterator iterator = this.sessions.cloneValuesIterator();

      while(iterator != null && iterator.hasNext()) {
         JMSSession session = (JMSSession)iterator.next();
         if (operation != null && operation.equals("close")) {
            session.checkOpPermissionForAsyncSend("Connection.close()");
         }

         if (!this.allowCloseInOnMessage && !session.isOperationAllowed()) {
            throw new IllegalStateException(JMSClientExceptionLogger.logInvalidCloseFromListenerLoggable(operation, "Connection").getMessage());
         }
      }

   }

   public final boolean isLocal() {
      return this.isLocal;
   }

   public final ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector, ServerSessionPool serverSessionPool, int messagesMaximum) throws JMSException {
      this.checkClosed();
      return JMSEnvironment.getJMSEnvironment().createConnectionConsumer(destination, messageSelector, serverSessionPool, messagesMaximum, this.getFrontEndDispatcher(), this.getJMSID());
   }

   private void cleanupConnection(JMSException savedException, boolean throwOnServerDisc) throws JMSException {
      synchronized(this) {
         synchronized(this.wlConnectionImpl.getConnectionStateLock()) {
            this.markClosed();
         }

         this.stopped = true;
      }

      boolean var35 = false;

      try {
         try {
            var35 = true;
            if (this.isConnected()) {
               Response var3 = this.dispatcher.dispatchSync(new FEConnectionCloseRequest(this.connectionId));
            }
         } catch (JMSException var42) {
            if (throwOnServerDisc) {
               throw var42;
            }
         }

         if (throwOnServerDisc) {
            if (savedException != null) {
               throw savedException;
            }

            var35 = false;
         } else {
            var35 = false;
         }
      } finally {
         if (var35) {
            if (this.clientDispatcherPartitionContext != null) {
               try {
                  InvocableManagerDelegate delegate = this.clientDispatcherPartitionContext.getInvocableManagerDelegate();
                  delegate.invocableRemove(3, this.getJMSID());
                  this.clientDispatcherPartitionContext.unexportLocalDispatcher();
               } catch (JMSException var39) {
                  if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                     JMSDebug.JMSDispatcherVerbose.debug(var39.getMessage(), var39);
                  }
               }
            }

            try {
               DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().lookupDispatcherPartitionContextById(this.clientDispatcherPartitionContext.getPartitionId());
               if (dpc != null) {
                  dpc.removeDispatcherReference(this.dispatcher);
               }
            } finally {
               this.dispatcher.removeDispatcherPeerGoneListener(this);
            }

         }
      }

      if (this.clientDispatcherPartitionContext != null) {
         try {
            InvocableManagerDelegate delegate = this.clientDispatcherPartitionContext.getInvocableManagerDelegate();
            delegate.invocableRemove(3, this.getJMSID());
            this.clientDispatcherPartitionContext.unexportLocalDispatcher();
         } catch (JMSException var41) {
            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug(var41.getMessage(), var41);
            }
         }
      }

      try {
         DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().lookupDispatcherPartitionContextById(this.clientDispatcherPartitionContext.getPartitionId());
         if (dpc != null) {
            dpc.removeDispatcherReference(this.dispatcher);
         }
      } finally {
         this.dispatcher.removeDispatcherPeerGoneListener(this);
      }

   }

   boolean isConnected() {
      return this.dispatcher.isLocal() || this.dispatcher.getDelegate() instanceof DispatcherWrapperState && !((DispatcherWrapperState)this.dispatcher.getDelegate()).getPeerGoneCache();
   }

   private void mergedCloseAndOnException(JMSException exception, boolean throwOnServerDisc) throws JMSException {
      Iterator iterator = null;
      boolean wasClosed = false;
      boolean canReconnect = false;
      JMSExceptionContext stableExceptionContext = null;
      boolean doNotFireExceptionListener = exception == null;
      if (ReconnectController.TODOREMOVEDebug) {
         displayExceptionCauses("DEBUG JMSConnection onException", exception);
      }

      Object connectionStateLock = this.wlConnectionImpl.getConnectionStateLock();
      boolean var1109 = false;

      boolean scheduleReconnect;
      label18241: {
         label18242: {
            try {
               var1109 = true;
               synchronized(this) {
                  label18226: {
                     label18225: {
                        synchronized(connectionStateLock) {
                           if (this.wlConnectionImpl.getPhysical() == this) {
                              stableExceptionContext = this.exceptionContext;
                              doNotFireExceptionListener = stableExceptionContext == null || exception == null || this.firedExceptoinListener;
                              this.firedExceptoinListener = true;
                              iterator = this.sessions.cloneValuesIterator();
                              if (exception != null) {
                                 this.wlConnectionImpl.setLastProblem(exception);

                                 for(Throwable lcv = exception; lcv != null; lcv = ((Throwable)lcv).getCause()) {
                                    if (lcv instanceof DestroyConnectionException) {
                                       this.setPreDisconnectState((JMSConnection)null);
                                       this.wlConnectionImpl.setRecursiveStateNotify(-2304);
                                       break;
                                    }
                                 }
                              }

                              wasClosed = this.isClosed();
                              if (wasClosed) {
                                 break label18225;
                              }

                              int state = this.wlConnectionImpl.getState();
                              if (exception != null) {
                                 if (state == 0) {
                                    canReconnect = this.wlConnectionImpl.rememberReconnectState(this, 1040);
                                 } else if (state == 1028) {
                                    canReconnect = this.getPreDisconnectState() != null;
                                    if (canReconnect) {
                                       this.wlConnectionImpl.setRecursiveStateNotify(1040);
                                    } else {
                                       this.setPreDisconnectState((JMSConnection)null);
                                       this.recurseSetNoRetry(this.wlConnectionImpl);
                                    }
                                 }
                              } else if (state == 0 || state == 1028) {
                                 this.wlConnectionImpl.setRecursiveStateNotify(1040);
                              }
                              break label18226;
                           }
                        }

                        var1109 = false;
                        break label18241;
                     }

                     var1109 = false;
                     break label18242;
                  }
               }

               this.closeSessions(iterator);
               var1109 = false;
            } finally {
               if (var1109) {
                  boolean var1049 = false;

                  boolean scheduleReconnect;
                  label18248: {
                     try {
                        var1049 = true;
                        if (!wasClosed) {
                           if (this.wlConnectionImpl.getPhysical() == this) {
                              this.cleanupConnection((JMSException)null, throwOnServerDisc);
                              var1049 = false;
                              break label18248;
                           }

                           var1049 = false;
                        } else {
                           var1049 = false;
                        }
                     } catch (JMSException var1152) {
                        var1049 = false;
                        break label18248;
                     } finally {
                        if (var1049) {
                           boolean scheduleReconnect;
                           synchronized(connectionStateLock) {
                              label18074: {
                                 if (!wasClosed && this.wlConnectionImpl.getPhysical() == this) {
                                    scheduleReconnect = this.updateState(canReconnect);
                                    if (!doNotFireExceptionListener) {
                                       break label18074;
                                    }

                                    this.wlConnectionImpl.updateFirstReconnectTime();
                                    if (scheduleReconnect) {
                                       this.scheduleReconnectTimer();
                                    }

                                    return;
                                 }

                                 return;
                              }
                           }

                           boolean var989 = false;

                           try {
                              var989 = true;
                              onException(exception, stableExceptionContext);
                              var989 = false;
                           } finally {
                              if (var989) {
                                 if (scheduleReconnect) {
                                    synchronized(connectionStateLock) {
                                       if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                                          this.wlConnectionImpl.updateFirstReconnectTime();
                                          this.scheduleReconnectTimer();
                                       }
                                    }
                                 }

                              }
                           }

                           if (scheduleReconnect) {
                              synchronized(connectionStateLock) {
                                 if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                                    this.wlConnectionImpl.updateFirstReconnectTime();
                                    this.scheduleReconnectTimer();
                                 }
                              }
                           }

                        }
                     }

                     synchronized(connectionStateLock) {
                        label18049: {
                           if (!wasClosed && this.wlConnectionImpl.getPhysical() == this) {
                              scheduleReconnect = this.updateState(canReconnect);
                              if (!doNotFireExceptionListener) {
                                 break label18049;
                              }

                              this.wlConnectionImpl.updateFirstReconnectTime();
                              if (scheduleReconnect) {
                                 this.scheduleReconnectTimer();
                              }

                              return;
                           }

                           return;
                        }
                     }

                     boolean var869 = false;

                     try {
                        var869 = true;
                        onException(exception, stableExceptionContext);
                        var869 = false;
                     } finally {
                        if (var869) {
                           if (scheduleReconnect) {
                              synchronized(connectionStateLock) {
                                 if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                                    this.wlConnectionImpl.updateFirstReconnectTime();
                                    this.scheduleReconnectTimer();
                                 }
                              }
                           }

                        }
                     }

                     if (scheduleReconnect) {
                        synchronized(connectionStateLock) {
                           if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                              this.wlConnectionImpl.updateFirstReconnectTime();
                              this.scheduleReconnectTimer();
                           }
                        }
                     }

                     return;
                  }

                  synchronized(connectionStateLock) {
                     if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
                        return;
                     }

                     scheduleReconnect = this.updateState(canReconnect);
                     if (doNotFireExceptionListener) {
                        this.wlConnectionImpl.updateFirstReconnectTime();
                        if (scheduleReconnect) {
                           this.scheduleReconnectTimer();
                        }

                        return;
                     }
                  }

                  boolean var929 = false;

                  try {
                     var929 = true;
                     onException(exception, stableExceptionContext);
                     var929 = false;
                  } finally {
                     if (var929) {
                        if (scheduleReconnect) {
                           synchronized(connectionStateLock) {
                              if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                                 this.wlConnectionImpl.updateFirstReconnectTime();
                                 this.scheduleReconnectTimer();
                              }
                           }
                        }

                     }
                  }

                  if (scheduleReconnect) {
                     synchronized(connectionStateLock) {
                        if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                           this.wlConnectionImpl.updateFirstReconnectTime();
                           this.scheduleReconnectTimer();
                        }
                     }
                  }

               }
            }

            boolean var809 = false;

            boolean scheduleReconnect;
            label18256: {
               try {
                  var809 = true;
                  if (wasClosed) {
                     var809 = false;
                     break label18256;
                  }

                  if (this.wlConnectionImpl.getPhysical() != this) {
                     var809 = false;
                     break label18256;
                  }

                  this.cleanupConnection((JMSException)null, throwOnServerDisc);
                  var809 = false;
               } catch (JMSException var1164) {
                  var809 = false;
               } finally {
                  if (var809) {
                     boolean scheduleReconnect;
                     synchronized(connectionStateLock) {
                        if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
                           return;
                        }

                        scheduleReconnect = this.updateState(canReconnect);
                        if (doNotFireExceptionListener) {
                           this.wlConnectionImpl.updateFirstReconnectTime();
                           if (scheduleReconnect) {
                              this.scheduleReconnectTimer();
                           }

                           return;
                        }
                     }

                     boolean var749 = false;

                     try {
                        var749 = true;
                        onException(exception, stableExceptionContext);
                        var749 = false;
                     } finally {
                        if (var749) {
                           if (scheduleReconnect) {
                              synchronized(connectionStateLock) {
                                 if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                                    this.wlConnectionImpl.updateFirstReconnectTime();
                                    this.scheduleReconnectTimer();
                                 }
                              }
                           }

                        }
                     }

                     if (scheduleReconnect) {
                        synchronized(connectionStateLock) {
                           if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                              this.wlConnectionImpl.updateFirstReconnectTime();
                              this.scheduleReconnectTimer();
                           }
                        }
                     }

                  }
               }

               synchronized(connectionStateLock) {
                  if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
                     return;
                  }

                  scheduleReconnect = this.updateState(canReconnect);
                  if (doNotFireExceptionListener) {
                     this.wlConnectionImpl.updateFirstReconnectTime();
                     if (scheduleReconnect) {
                        this.scheduleReconnectTimer();
                     }

                     return;
                  }
               }

               boolean var629 = false;

               try {
                  var629 = true;
                  onException(exception, stableExceptionContext);
                  var629 = false;
               } finally {
                  if (var629) {
                     if (scheduleReconnect) {
                        synchronized(connectionStateLock) {
                           if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                              this.wlConnectionImpl.updateFirstReconnectTime();
                              this.scheduleReconnectTimer();
                           }
                        }
                     }

                  }
               }

               if (scheduleReconnect) {
                  synchronized(connectionStateLock) {
                     if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                        this.wlConnectionImpl.updateFirstReconnectTime();
                        this.scheduleReconnectTimer();
                     }
                  }
               }

               return;
            }

            synchronized(connectionStateLock) {
               label18173: {
                  if (!wasClosed && this.wlConnectionImpl.getPhysical() == this) {
                     scheduleReconnect = this.updateState(canReconnect);
                     if (!doNotFireExceptionListener) {
                        break label18173;
                     }

                     this.wlConnectionImpl.updateFirstReconnectTime();
                     if (scheduleReconnect) {
                        this.scheduleReconnectTimer();
                     }

                     return;
                  }

                  return;
               }
            }

            boolean var509 = false;

            try {
               var509 = true;
               onException(exception, stableExceptionContext);
               var509 = false;
            } finally {
               if (var509) {
                  if (scheduleReconnect) {
                     synchronized(connectionStateLock) {
                        if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                           this.wlConnectionImpl.updateFirstReconnectTime();
                           this.scheduleReconnectTimer();
                        }
                     }
                  }

               }
            }

            if (scheduleReconnect) {
               synchronized(connectionStateLock) {
                  if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                     this.wlConnectionImpl.updateFirstReconnectTime();
                     this.scheduleReconnectTimer();
                  }
               }
            }

            return;
         }

         boolean var689 = false;

         label18263: {
            try {
               var689 = true;
               if (wasClosed) {
                  var689 = false;
                  break label18263;
               }

               if (this.wlConnectionImpl.getPhysical() != this) {
                  var689 = false;
                  break label18263;
               }

               this.cleanupConnection((JMSException)null, throwOnServerDisc);
               var689 = false;
            } catch (JMSException var1160) {
               var689 = false;
            } finally {
               if (var689) {
                  boolean scheduleReconnect;
                  synchronized(connectionStateLock) {
                     if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
                        return;
                     }

                     scheduleReconnect = this.updateState(canReconnect);
                     if (doNotFireExceptionListener) {
                        this.wlConnectionImpl.updateFirstReconnectTime();
                        if (scheduleReconnect) {
                           this.scheduleReconnectTimer();
                        }

                        return;
                     }
                  }

                  boolean var569 = false;

                  try {
                     var569 = true;
                     onException(exception, stableExceptionContext);
                     var569 = false;
                  } finally {
                     if (var569) {
                        if (scheduleReconnect) {
                           synchronized(connectionStateLock) {
                              if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                                 this.wlConnectionImpl.updateFirstReconnectTime();
                                 this.scheduleReconnectTimer();
                              }
                           }
                        }

                     }
                  }

                  if (scheduleReconnect) {
                     synchronized(connectionStateLock) {
                        if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                           this.wlConnectionImpl.updateFirstReconnectTime();
                           this.scheduleReconnectTimer();
                        }
                     }
                  }

               }
            }

            synchronized(connectionStateLock) {
               if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
                  return;
               }

               scheduleReconnect = this.updateState(canReconnect);
               if (doNotFireExceptionListener) {
                  this.wlConnectionImpl.updateFirstReconnectTime();
                  if (scheduleReconnect) {
                     this.scheduleReconnectTimer();
                  }

                  return;
               }
            }

            boolean var389 = false;

            try {
               var389 = true;
               onException(exception, stableExceptionContext);
               var389 = false;
            } finally {
               if (var389) {
                  if (scheduleReconnect) {
                     synchronized(connectionStateLock) {
                        if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                           this.wlConnectionImpl.updateFirstReconnectTime();
                           this.scheduleReconnectTimer();
                        }
                     }
                  }

               }
            }

            if (scheduleReconnect) {
               synchronized(connectionStateLock) {
                  if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                     this.wlConnectionImpl.updateFirstReconnectTime();
                     this.scheduleReconnectTimer();
                  }
               }
            }

            return;
         }

         synchronized(connectionStateLock) {
            label18140: {
               if (!wasClosed && this.wlConnectionImpl.getPhysical() == this) {
                  scheduleReconnect = this.updateState(canReconnect);
                  if (!doNotFireExceptionListener) {
                     break label18140;
                  }

                  this.wlConnectionImpl.updateFirstReconnectTime();
                  if (scheduleReconnect) {
                     this.scheduleReconnectTimer();
                  }

                  return;
               }

               return;
            }
         }

         boolean var269 = false;

         try {
            var269 = true;
            onException(exception, stableExceptionContext);
            var269 = false;
         } finally {
            if (var269) {
               if (scheduleReconnect) {
                  synchronized(connectionStateLock) {
                     if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                        this.wlConnectionImpl.updateFirstReconnectTime();
                        this.scheduleReconnectTimer();
                     }
                  }
               }

            }
         }

         if (scheduleReconnect) {
            synchronized(connectionStateLock) {
               if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                  this.wlConnectionImpl.updateFirstReconnectTime();
                  this.scheduleReconnectTimer();
               }
            }
         }

         return;
      }

      boolean var449 = false;

      label18271: {
         try {
            var449 = true;
            if (!wasClosed) {
               if (this.wlConnectionImpl.getPhysical() == this) {
                  this.cleanupConnection((JMSException)null, throwOnServerDisc);
                  var449 = false;
                  break label18271;
               }

               var449 = false;
            } else {
               var449 = false;
            }
         } catch (JMSException var1156) {
            var449 = false;
            break label18271;
         } finally {
            if (var449) {
               boolean scheduleReconnect;
               synchronized(connectionStateLock) {
                  if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
                     return;
                  }

                  scheduleReconnect = this.updateState(canReconnect);
                  if (doNotFireExceptionListener) {
                     this.wlConnectionImpl.updateFirstReconnectTime();
                     if (scheduleReconnect) {
                        this.scheduleReconnectTimer();
                     }

                     return;
                  }
               }

               boolean var329 = false;

               try {
                  var329 = true;
                  onException(exception, stableExceptionContext);
                  var329 = false;
               } finally {
                  if (var329) {
                     if (scheduleReconnect) {
                        synchronized(connectionStateLock) {
                           if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                              this.wlConnectionImpl.updateFirstReconnectTime();
                              this.scheduleReconnectTimer();
                           }
                        }
                     }

                  }
               }

               if (scheduleReconnect) {
                  synchronized(connectionStateLock) {
                     if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                        this.wlConnectionImpl.updateFirstReconnectTime();
                        this.scheduleReconnectTimer();
                     }
                  }
               }

            }
         }

         synchronized(connectionStateLock) {
            if (wasClosed || this.wlConnectionImpl.getPhysical() != this) {
               return;
            }

            scheduleReconnect = this.updateState(canReconnect);
            if (doNotFireExceptionListener) {
               this.wlConnectionImpl.updateFirstReconnectTime();
               if (scheduleReconnect) {
                  this.scheduleReconnectTimer();
               }

               return;
            }
         }

         boolean var149 = false;

         try {
            var149 = true;
            onException(exception, stableExceptionContext);
            var149 = false;
         } finally {
            if (var149) {
               if (scheduleReconnect) {
                  synchronized(connectionStateLock) {
                     if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                        this.wlConnectionImpl.updateFirstReconnectTime();
                        this.scheduleReconnectTimer();
                     }
                  }
               }

            }
         }

         if (scheduleReconnect) {
            synchronized(connectionStateLock) {
               if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                  this.wlConnectionImpl.updateFirstReconnectTime();
                  this.scheduleReconnectTimer();
               }
            }
         }

         return;
      }

      synchronized(connectionStateLock) {
         label18095: {
            if (!wasClosed && this.wlConnectionImpl.getPhysical() == this) {
               scheduleReconnect = this.updateState(canReconnect);
               if (!doNotFireExceptionListener) {
                  break label18095;
               }

               this.wlConnectionImpl.updateFirstReconnectTime();
               if (scheduleReconnect) {
                  this.scheduleReconnectTimer();
               }

               return;
            }

            return;
         }
      }

      boolean var209 = false;

      try {
         var209 = true;
         onException(exception, stableExceptionContext);
         var209 = false;
      } finally {
         if (var209) {
            if (scheduleReconnect) {
               synchronized(connectionStateLock) {
                  if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
                     this.wlConnectionImpl.updateFirstReconnectTime();
                     this.scheduleReconnectTimer();
                  }
               }
            }

         }
      }

      if (scheduleReconnect) {
         synchronized(connectionStateLock) {
            if (this == this.wlConnectionImpl.getPhysical() && this.wlConnectionImpl.getReconnectPolicyInternal() != 0 && this.wlConnectionImpl.getState() == 512) {
               this.wlConnectionImpl.updateFirstReconnectTime();
               this.scheduleReconnectTimer();
            }
         }
      }

   }

   private boolean updateState(boolean canReconnect) {
      int old = this.wlConnectionImpl.getState();
      int next;
      if (old != 1040 && old != 1028) {
         next = old;
      } else if (canReconnect && this.preDisconnectState != null && this.wlConnectionImpl.getReconnectPolicyInternal() != 0) {
         next = 512;
      } else {
         next = -2304;
      }

      if (next == -2304) {
         recurseSetNoRetry(this.sessions, this);
      }

      this.wlConnectionImpl.setRecursiveStateNotify(next);
      return 512 == next;
   }

   static void displayExceptionCauses(String banner, Throwable current) {
      String type = " exception  ";
      if (current == null) {
         System.err.println(banner + type + " null argument");
      }

      for(int lcv = 1; lcv < 40 && current != null; ++lcv) {
         String extra;
         if (current instanceof LostServerException && ((LostServerException)current).isReplayLastException()) {
            extra = " and isReplay";
         } else {
            extra = "";
         }

         System.err.println(banner + type + lcv + ", " + current.getClass() + extra);
         NestedThrowable nest1;
         if (current instanceof NestedThrowable && (nest1 = (NestedThrowable)current).getNested() != null) {
            if (null != current.getCause()) {
               displayExceptionCauses(banner + "[has funky getCause()" + lcv + "]", current.getCause());
            }

            current = nest1.getNested();
            type = " NestedThrowable.getNested() ";
         } else {
            current = current.getCause();
            type = " with cause ";
         }
      }

   }

   private void closeSessions(Iterator iterator) throws JMSException {
      JMSException savedException = null;

      while(iterator != null && iterator.hasNext()) {
         JMSSession session = (JMSSession)iterator.next();

         try {
            session.close();
         } catch (JMSException var5) {
            if (savedException == null) {
               savedException = var5;
            }
         }
      }

      if (savedException != null) {
         throw savedException;
      }
   }

   Timer clearTimerInfo() {
      Timer oldTimer = this.timer;
      this.timer = null;
      return oldTimer;
   }

   private Timer resetIntervalClearTimer() {
      this.nextRetry = (long)INITIAL_RETRY_DELAY;
      return this.clearTimerInfo();
   }

   boolean scheduleReconnectTimer() {
      if (ReconnectController.TODOREMOVEDebug) {
         (new Exception("DEBUG 1054")).printStackTrace();
      }

      long now = System.currentTimeMillis();
      long currentInterval = this.nextRetry;
      if (this.wlConnectionImpl.getLastReconnectTimer() > 0L && this.wlConnectionImpl.getLastReconnectTimer() - now < currentInterval) {
         this.wlConnectionImpl.clearLastReconnectTimer();
         this.wlConnectionImpl.getConnectionStateLock().notifyAll();
         this.clearTimerInfo();
         return false;
      } else {
         this.nextRetry <<= 1;
         if (this.nextRetry > 300000L) {
            this.nextRetry = 300000L;
         } else if (this.nextRetry < 5000L) {
            this.nextRetry = 5000L;
         }

         if ((long)INITIAL_RETRY_DELAY == currentInterval) {
            getReconnectWorkManager().schedule(this);
         } else {
            this.timer = this.getReconnectTimerManager(getReconnectWorkManager()).schedule(this, currentInterval);
         }

         return true;
      }
   }

   static WorkManager getReconnectWorkManager() {
      synchronized(TIMER_WORKMANAGER_LOCK) {
         if (RECONNECT_WORK_MANAGER == null) {
            RECONNECT_WORK_MANAGER = WorkManagerFactory.getInstance().findOrCreate("weblogic.jms.Reconnect", 1, 5);
         }

         return RECONNECT_WORK_MANAGER;
      }
   }

   private TimerManager getReconnectTimerManager(WorkManager reconnectWorkMgr) {
      synchronized(TIMER_WORKMANAGER_LOCK) {
         if (RECONNECT_TIMER_MANAGER == null) {
            RECONNECT_TIMER_MANAGER = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms.Reconnect", reconnectWorkMgr);
         }

         return RECONNECT_TIMER_MANAGER;
      }
   }

   private boolean isWithinReconnectTime() {
      long now = System.currentTimeMillis();
      long config = this.wlConnectionImpl.getTotalReconnectPeriodMillis();
      return config == -1L || now - this.wlConnectionImpl.getFirstReconnectTime() <= config;
   }

   public void timerExpired(Timer timer) {
      if (this.isWithinReconnectTime()) {
         this.wlConnectionImpl.processReconnectTimer(this);
      } else {
         this.setPreDisconnectState((JMSConnection)null);
         this.recurseSetNoRetry(this.wlConnectionImpl);
         JMSException lse = new LostServerException("Failed to reconnect to Server within the configured reconnect time of " + this.getTotalReconnectPeriodMillis() / 1000L + " seconds");
         if (this.exceptionContext != null) {
            onException(lse, this.exceptionContext);
         } else if (ReconnectController.TODOREMOVEDebug) {
            displayExceptionCauses("Failed to reconnect to Server within the configured reconnect time of " + this.getTotalReconnectPeriodMillis() / 1000L + " seconds", new LostServerException("Network or Server down"));
         }
      }

   }

   public void run() {
      this.wlConnectionImpl.processReconnectTimer(this);
   }

   static final void onException(JMSException exception, JMSExceptionContext exceptionContext) {
      onExceptionInternal(exception, exceptionContext, false);
   }

   static final boolean onExceptionInternal(JMSException exception, JMSExceptionContext exceptionContext, boolean deferIfInUse) {
      if (ReconnectController.TODOREMOVEDebug) {
         (new Exception("DEBUG 1137")).printStackTrace();
      }

      JMSContext savedContext = JMSContext.push(exceptionContext, true);

      try {
         if (deferIfInUse) {
            boolean var4 = exceptionContext.invokeListenerIfItIsIdle(exception);
            return var4;
         }

         exceptionContext.blockTillIdleThenRunExclusively(exception);
      } catch (Exception var8) {
         JMSClientExceptionLogger.logStackTrace(var8);
      } finally {
         JMSContext.pop(savedContext, true);
      }

      return false;
   }

   public final String getJMSVersion() {
      return ProviderInfo.getJMSVersion();
   }

   public final int getJMSMajorVersion() {
      return ProviderInfo.getJMSMajorVersion();
   }

   public final int getJMSMinorVersion() {
      return ProviderInfo.getJMSMinorVersion();
   }

   public final String getJMSProviderName() {
      return ProviderInfo.getJMSProviderName();
   }

   public final String getProviderVersion() {
      return ProviderInfo.getProviderVersion();
   }

   public final int getProviderMajorVersion() {
      return ProviderInfo.getProviderMajorVersion();
   }

   public final int getProviderMinorVersion() {
      return ProviderInfo.getProviderMinorVersion();
   }

   void setWlConnectionImpl(WLConnectionImpl wlConnectionImpl) {
      if (this.wlConnectionImpl == null || wlConnectionImpl.getConnectionStateLock() != this.wlConnectionImpl.getConnectionStateLock()) {
         this.sessions.setLock(wlConnectionImpl.getConnectionStateLock());
         this.durableSubscribers.setLock(wlConnectionImpl.getConnectionStateLock());
      }

      this.wlConnectionImpl = wlConnectionImpl;
   }

   JMSConnection getReplacementConnection() {
      return this.replacementConnection;
   }

   void setReplacementConnection(JMSConnection arg) {
      this.replacementConnection = arg;
   }

   JMSConnection getOriginalConnection() {
      return this.originalConnection;
   }

   void setOriginalConnection(JMSConnection arg) {
      this.originalConnection = arg;
   }

   void markReplacement() {
      this.bReplacement = true;
   }

   public final Enumeration getJMSXPropertyNames() {
      return ProviderInfo.getJMSXPropertyNames();
   }

   public int incrementRefCount() {
      return ++this.refCount;
   }

   public int decrementRefCount() {
      return --this.refCount;
   }

   public void stateChangeListener(DispatcherStateChangeListener arg, Throwable throwable) {
      if (arg == this) {
         synchronized(this) {
            synchronized(this.wlConnectionImpl.getConnectionStateLock()) {
               if (this.wlConnectionImpl.getPhysical() == this && this.wlConnectionImpl.getState() == 0) {
                  this.wlConnectionImpl.rememberReconnectState(this, 1028);
               }
            }

         }
      }
   }

   public boolean holdsLock() {
      if (Thread.holdsLock(this)) {
         return true;
      } else {
         WLConnectionImpl localImpl = this.wlConnectionImpl;
         if (localImpl == null) {
            return false;
         } else {
            Object connectionStateLock = localImpl.getConnectionStateLock();
            return connectionStateLock == null ? false : Thread.holdsLock(connectionStateLock);
         }
      }
   }

   public final void dispatcherPeerGone(Exception e, Dispatcher dispatcher) {
      LostServerException exception = new LostServerException(e);
      AbstractSubject currentSubject = SubjectManager.getSubjectManager().getCurrentSubject(KernelID);

      try {
         if (currentSubject.equals(KernelID)) {
            SubjectManager.getSubjectManager().pushSubject(KernelID, SubjectManager.getSubjectManager().getAnonymousSubject());
         }

         this.mergedCloseAndOnException(exception, false);
      } catch (JMSException var9) {
      } finally {
         if (currentSubject.equals(KernelID)) {
            SubjectManager.getSubjectManager().popSubject(KernelID);
         }

      }

   }

   private void markClosed() {
      this.isClosed = true;
   }

   public ReconnectController getReconnectController() {
      return this.wlConnectionImpl;
   }

   JMSConnection getPreDisconnectState() {
      return this.preDisconnectState;
   }

   void setPreDisconnectState(JMSConnection arg) {
      this.preDisconnectState = arg;
   }

   public Reconnectable getReconnectState(int policy) throws CloneNotSupportedException {
      JMSConnection clone = (JMSConnection)this.clone();
      if (WLConnectionImpl.reconnectPolicyHas(2, policy)) {
         clone.sessions = recurseGetReconnectState(clone.sessions, policy);
      } else {
         clone.sessions = recurseSetNoRetry(clone.sessions, this);
      }

      clone.preDisconnectState = null;
      this.preDisconnectState = clone;
      return clone;
   }

   static LockedMap recurseGetReconnectState(LockedMap oldMap, int reconnectPolicy) throws CloneNotSupportedException {
      Iterator iterator = oldMap.valuesIterator();
      LockedMap newMap = new LockedMap(oldMap.getName(), oldMap.getLock());

      while(iterator.hasNext()) {
         Reconnectable reconnectable = (Reconnectable)iterator.next();
         if (!reconnectable.isReconnectControllerClosed()) {
            reconnectable = reconnectable.getReconnectState(reconnectPolicy);
            if (reconnectable != null) {
               newMap.put(reconnectable, reconnectable);
            }
         }
      }

      return newMap;
   }

   static LockedMap recurseSetNoRetry(LockedMap oldMap, JMSConnection jmsConnection) {
      boolean notifyAll = false;
      Iterator iterator = oldMap.valuesIterator();

      while(iterator.hasNext()) {
         Object next = iterator.next();
         if (next instanceof Reconnectable) {
            ReconnectController rc = ((Reconnectable)next).getReconnectController();
            if (rc != null && rc.getWLConnectionImpl().getPhysical() == jmsConnection) {
               notifyAll = true;
               rc.setRecursiveState(-2304);
            }

            ((Reconnectable)next).forgetReconnectState();
         }
      }

      if (notifyAll) {
         jmsConnection.wlConnectionImpl.getConnectionStateLock().notifyAll();
      }

      return new LockedMap(oldMap.getName(), oldMap.getLock());
   }

   void recurseSetNoRetry(WLConnectionImpl wlConnectionImpl) {
      recurseSetNoRetry(this.sessions, this);
      wlConnectionImpl.setRecursiveStateNotify(-2304);
   }

   boolean hasTemporaryDestination() {
      Iterator sessionIterator = this.sessions.valuesIterator();

      do {
         if (!sessionIterator.hasNext()) {
            return false;
         }
      } while(!((JMSSession)sessionIterator.next()).hasTemporaryDestination());

      return true;
   }

   static void recursePreCreateReplacement(Reconnectable parent, LockedMap oldMap) throws JMSException {
      Iterator iterator = oldMap.cloneValuesIterator();

      while(iterator.hasNext()) {
         Reconnectable reconnectable = (Reconnectable)iterator.next();
         if (!reconnectable.isReconnectControllerClosed()) {
            reconnectable.preCreateReplacement(parent);
         }
      }

   }

   static void recursePostCreateReplacement(LockedMap oldMap) {
      Iterator iterator = oldMap.cloneValuesIterator();

      while(iterator.hasNext()) {
         Reconnectable reconnectable = (Reconnectable)iterator.next();
         if (!reconnectable.isReconnectControllerClosed()) {
            reconnectable.postCreateReplacement();
         }
      }

   }

   void rememberCredentials(String username, String password, boolean wantXAConnection) {
      this.wantXAConnection = wantXAConnection;

      try {
         this.pwdStore = new PasswordStore();
         this.uHandle = this.pwdStore.storePassword(username);
         this.pHandle = this.pwdStore.storePassword(password);
      } catch (GeneralSecurityException var5) {
         this.pwdStore = null;
         this.uHandle = username;
         this.pHandle = password;
      }

   }

   public Reconnectable preCreateReplacement(Reconnectable parent) throws JMSException {
      JMSConnection oldCon = this.preDisconnectState;
      JMSConnection origCon = this.getOriginalConnection();
      if (oldCon == null) {
         return null;
      } else {
         WLConnectionImpl wlConImpl = oldCon.wlConnectionImpl;
         String uname;
         String pwd;
         synchronized(wlConImpl.getConnectionStateLock()) {
            if (oldCon.pwdStore != null) {
               try {
                  uname = (String)oldCon.pwdStore.retrievePassword(oldCon.uHandle);
                  pwd = (String)oldCon.pwdStore.retrievePassword(oldCon.pHandle);
               } catch (GeneralSecurityException var25) {
                  throw new weblogic.jms.common.JMSException(var25);
               } catch (IOException var26) {
                  throw new weblogic.jms.common.JMSException(var26);
               }
            } else {
               uname = (String)oldCon.uHandle;
               pwd = (String)oldCon.pHandle;
            }
         }

         DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().findDispatcherPartitionContextJMSException(oldCon.clientDispatcherPartitionContext.getPartitionId());
         JMSConnection newCon = ((JMSConnectionFactory)parent).setupJMSConnection(uname, pwd, oldCon.wantXAConnection, dpc, oldCon.type);

         try {
            newCon.setWlConnectionImpl(wlConImpl);
            if (oldCon.clientId != null && newCon.clientId == null) {
               newCon.setClientID(oldCon.clientId);
            }

            newCon.setAllowCloseInOnMessage(oldCon.allowCloseInOnMessage);
            newCon.setDispatchPolicy(oldCon.dispatchPolicyName);
            recursePreCreateReplacement(newCon, oldCon.sessions);
            int state;
            synchronized(wlConImpl.getConnectionStateLock()) {
               state = wlConImpl.getState();
               if (state == 1536) {
                  newCon.markReplacement();
                  JMSConnection connectionCopy = newCon;
                  if (origCon != null) {
                     newCon.setOriginalConnection(origCon);
                     origCon.setReplacementConnection(newCon);
                  } else {
                     newCon.setOriginalConnection(this);
                  }

                  this.setReplacementConnection(newCon);
                  newCon = null;
                  JMSConnection var12 = connectionCopy;
                  return var12;
               }
            }

            throw new LostServerException(wlConImpl.wrongStateString(state));
         } finally {
            try {
               if (newCon != null) {
                  newCon.close();
               }
            } catch (JMSException var24) {
            }

         }
      }
   }

   public void postCreateReplacement() {
      if (ReconnectController.TODOREMOVEDebug) {
         System.err.println("debug JMSConnection stale " + this.preDisconnectState.debugMaps());
      }

      Timer oldTimer = this.resetIntervalClearTimer();
      if (oldTimer != null) {
         oldTimer.cancel();
      }

      recursePostCreateReplacement(this.preDisconnectState.sessions);
      JMSContext preDisconnectContext = this.preDisconnectState.wlConnectionImpl.getConnectionEstablishContext();
      this.getReplacementConnection().setWlConnectionImpl(this.preDisconnectState.wlConnectionImpl);
      JMSConnection preDisconnectStateLocal = this.preDisconnectState;
      preDisconnectStateLocal.forgetReconnectState();
      this.preDisconnectState = null;
      this.getReplacementConnection().wlConnectionImpl.setConnectionEstablishContext(preDisconnectContext);
      this.wlConnectionImpl.setPhysicalReconnectable(this.getReplacementConnection());
      if (ReconnectController.TODOREMOVEDebug) {
         System.err.println("debug JMSConnection reconnect " + this.getReplacementConnection().debugMaps());
      }

   }

   String debugMaps() {
      String accumulate = null;
      Iterator iterator = this.sessions.valuesIterator();

      while(iterator.hasNext()) {
         String cur = ((JMSSession)((JMSSession)iterator.next())).debugMaps();
         if (accumulate == null) {
            accumulate = "< " + cur;
         } else {
            accumulate = accumulate + "\n  " + cur;
         }
      }

      return accumulate + "\n>";
   }

   public Object clone() throws CloneNotSupportedException {
      JMSConnection jmsConnection = (JMSConnection)super.clone();
      jmsConnection.sessions = (LockedMap)this.sessions.clone();
      jmsConnection.durableSubscribers = (LockedMap)this.durableSubscribers.clone();
      return jmsConnection;
   }

   public boolean isReconnectControllerClosed() {
      return this.wlConnectionImpl.isClosed();
   }

   public final synchronized ExceptionListener getExceptionListener() throws JMSException {
      this.checkClosed();
      return this.exceptionContext != null ? this.exceptionContext.getExceptionListener() : null;
   }

   public final synchronized JMSExceptionContext getJMSExceptionContext() throws JMSException {
      this.checkClosed();
      return this.exceptionContext;
   }

   public final ClientRuntimeInfo getParentInfo() {
      return this;
   }

   public final String getWLSServerName() {
      return this.wlsServerName;
   }

   public final String getRuntimeMBeanName() {
      return this.runtimeMBeanName;
   }

   public final String toString() {
      return this.getRuntimeMBeanName();
   }

   final int getDeliveryMode() {
      return this.deliveryMode;
   }

   final int getPriority() {
      return this.priority;
   }

   final long getTimeToDeliver() {
      return this.timeToDeliver;
   }

   final long getTimeToLive() {
      return this.timeToLive;
   }

   final long getSendTimeout() {
      return this.sendTimeout;
   }

   final long getRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   public final void setAcknowledgePolicy(int acknowledgePolicy) {
      synchronized(this.acknowledgePolicyLock) {
         this.acknowledgePolicy = acknowledgePolicy;
      }
   }

   public final int getAcknowledgePolicy() {
      synchronized(this.acknowledgePolicyLock) {
         return this.acknowledgePolicy;
      }
   }

   final int getOverrunPolicy() {
      return this.overrunPolicy;
   }

   public final boolean isXAServerEnabled() {
      return this.xaServerEnabled;
   }

   final boolean isFlowControlEnabled() {
      return this.flowControl;
   }

   final int getFlowMinimum() {
      return this.flowMinimum;
   }

   final int getFlowMaximum() {
      return this.flowMaximum;
   }

   final int getFlowIncrease() {
      return this.flowIncrease;
   }

   final double getFlowDecrease() {
      return this.flowDecrease;
   }

   final long getFlowInterval() {
      return this.flowInterval;
   }

   final int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public final boolean getUserTransactionsEnabled() {
      return this.userTransactionsEnabled;
   }

   public final boolean getAllowCloseInOnMessage() {
      return this.allowCloseInOnMessage;
   }

   public final void setAllowCloseInOnMessage(boolean allowCloseInOnMessage) {
      this.allowCloseInOnMessage = allowCloseInOnMessage;
   }

   public void setReconnectPolicy(String reconnectPolicy) {
      this.wlConnectionImpl.setReconnectPolicy(reconnectPolicy);
   }

   public String getReconnectPolicy() {
      return this.wlConnectionImpl.getReconnectPolicy();
   }

   public void setReconnectBlockingMillis(long timeout) {
      this.wlConnectionImpl.setReconnectBlockingMillis(timeout);
   }

   public long getReconnectBlockingMillis() {
      return this.wlConnectionImpl.getReconnectBlockingMillis();
   }

   public long getTotalReconnectPeriodMillis() {
      return this.wlConnectionImpl.getTotalReconnectPeriodMillis();
   }

   public void setTotalReconnectPeriodMillis(long mil) {
      this.wlConnectionImpl.setTotalReconnectPeriodMillis(mil);
   }

   public int getPipelineGeneration() {
      return this.pipelineGeneration;
   }

   final boolean markDurableSubscriber(String subscriptionName) {
      synchronized(this.sublock) {
         if (this.durableSubscribers.get(subscriptionName) != null) {
            return false;
         } else {
            return this.durableSubscribers.put(subscriptionName, new Object()) == null;
         }
      }
   }

   final void addDurableSubscriber(String subscriptionName, Object obj) {
      synchronized(this.sublock) {
         this.durableSubscribers.put(subscriptionName, obj);
      }
   }

   final boolean removeDurableSubscriber(String subscriptionName) {
      synchronized(this.sublock) {
         return this.durableSubscribers.remove(subscriptionName) != null;
      }
   }

   final synchronized String getDispatchPolicy() {
      return this.dispatchPolicyName;
   }

   public final synchronized void setDispatchPolicy(String name) {
      this.dispatchPolicyName = name;
   }

   public final JMSID getJMSID() {
      return this.connectionId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.clientDispatcherPartitionContext;
   }

   final int getCompressionThreshold() {
      return this.compressionThreshold;
   }

   final int getSynchronousPrefetchMode() {
      return this.synchronousPrefetchMode;
   }

   final int getOneWaySendMode() {
      return this.oneWaySendMode;
   }

   final int getOneWaySendWindowSize() {
      return this.oneWaySendWindowSize;
   }

   public final InvocableMonitor getInvocableMonitor() {
      return null;
   }

   final int getReconnectPolicyInternal() {
      if (PeerInfo.VERSION_901.compareTo(this.getFEPeerInfo()) > 0) {
         this.reconnectPolicy = 0;
      }

      return this.reconnectPolicy;
   }

   final long getReconnectBlockingMillisInternal() {
      return this.reconnectBlockingMillis;
   }

   final long getTotalReconnectPeriodMillisInternal() {
      return this.totalReconnectPeriodMillis;
   }

   public final void destroyTemporaryDestination(JMSServerId backEndId, JMSID destinationId) throws JMSException {
      this.checkClosed();
      Object response = this.dispatcher.dispatchSync(new FETemporaryDestinationDestroyRequest(this.getJMSID(), destinationId));
   }

   final void consumerRemove(String name) throws JMSException {
      if (this.clientId == null) {
         PeerInfo pi = this.getFEPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
            throw new JMSException("Operation not supported by front-end server version [" + pi + "]: unsubscribe durable subscription with name " + name + " and null client id");
         }
      }

      Object response = this.dispatcher.dispatchSync(new FERemoveSubscriptionRequest(this.clientId, name));
   }

   final void consumerRemove(DestinationImpl topic, String name) throws JMSException {
      if (this.clientId == null) {
         PeerInfo pi = this.getFEPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
            throw new JMSException("Operation not supported by front-end server version [" + pi + "]: unsubscribe durable subscription with name " + name + " and null client id on Topic " + topic);
         }
      }

      Object response = this.dispatcher.dispatchSync(new FERemoveSubscriptionRequest(this.clientId, name, this.clientIdPolicy, topic));
   }

   public boolean isClosed() {
      return this.isClosed;
   }

   private void checkClosed() throws JMSException {
      this.getFrontEndDispatcher();
   }

   public final void publicCheckClosed() throws JMSException {
      this.checkClosed();
   }

   public final byte getPeerVersion() {
      return this.version;
   }

   public JMSConnection() {
      this.nextRetry = (long)INITIAL_RETRY_DELAY;
      this.acknowledgePolicyLock = new Object();
      this.isWrappedIC = false;
      this.subject = null;
      this.jmsSessionPooledInWrapper = false;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      byte version;
      byte mask = version = this.getVersion(out);
      int extension;
      if (version >= 4) {
         if (this.sendTimeout != 10L) {
            mask = (byte)(mask | 64);
         }

         if (version >= 5) {
            extension = 1 | this.pipelineGeneration;
            if (this.unitOfOrder != null) {
               extension |= 2;
            }

            if (version >= 9 && this.subject != null) {
               extension |= 4;
            }
         } else {
            extension = 0;
         }
      } else {
         extension = 0;
      }

      if (this.peerVersionable != null) {
         this.peerVersionable.setPeerVersion(version);
      }

      out.writeByte(mask);
      this.connectionId.writeExternal(out);
      if (this.clientId == null) {
         out.writeBoolean(false);
      } else {
         out.writeBoolean(true);
         out.writeUTF(this.clientId);
      }

      out.writeInt(this.deliveryMode);
      out.writeInt(this.priority);
      out.writeInt(this.messagesMaximum);
      out.writeInt(this.overrunPolicy);
      out.writeLong(this.timeToDeliver);
      out.writeLong(this.timeToLive);
      out.writeLong(this.redeliveryDelay);
      out.writeLong(this.transactionTimeout);
      out.writeBoolean(this.userTransactionsEnabled);
      out.writeBoolean(this.allowCloseInOnMessage);
      out.writeInt(this.acknowledgePolicy);
      this.dispatcherWrapper.writeExternal(out);
      if (version >= 3) {
         out.writeBoolean(this.flowControl);
         if (this.flowControl) {
            out.writeInt(this.flowMinimum);
            out.writeInt(this.flowMaximum);
            out.writeInt(this.flowIncrease);
            out.writeDouble(this.flowDecrease);
            out.writeLong(this.flowInterval);
         }

         out.writeBoolean(this.xaServerEnabled);
      }

      if (version >= 4) {
         out.writeUTF(this.wlsServerName);
         out.writeUTF(this.runtimeMBeanName);
         if (this.sendTimeout != 10L) {
            out.writeLong(this.sendTimeout);
         }
      }

      if (version >= 5) {
         out.writeInt(extension);
         if ((extension & 1) != 0) {
            out.writeInt(this.compressionThreshold);
         }

         if ((extension & 2) != 0) {
            out.writeUTF(this.unitOfOrder);
         }

         if (version >= 6) {
            out.writeInt(this.synchronousPrefetchMode);
         }

         if (version >= 7) {
            out.writeInt(this.oneWaySendMode);
            out.writeInt(this.oneWaySendWindowSize);
            out.writeInt(this.reconnectPolicy);
            out.writeLong(this.reconnectBlockingMillis);
            out.writeLong(this.totalReconnectPeriodMillis);
         }
      }

      if (version >= 8) {
         out.writeInt(this.clientIdPolicy);
         out.writeInt(this.subscriptionSharingPolicy);
      }

      if (version >= 9 && (extension & 4) != 0) {
         out.writeObject(this.subject);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte mask = in.readByte();
      byte vrsn = (byte)(mask & 15);
      switch (vrsn) {
         case 1:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
            this.version = vrsn;
            this.connectionId = new JMSID();
            this.connectionId.readExternal(in);
            if (in.readBoolean()) {
               this.clientId = in.readUTF();
            }

            this.deliveryMode = in.readInt();
            this.priority = in.readInt();
            this.messagesMaximum = in.readInt();
            this.overrunPolicy = in.readInt();
            this.timeToDeliver = in.readLong();
            this.timeToLive = in.readLong();
            this.redeliveryDelay = in.readLong();
            this.transactionTimeout = in.readLong();
            this.userTransactionsEnabled = in.readBoolean();
            this.allowCloseInOnMessage = in.readBoolean();
            this.acknowledgePolicy = in.readInt();
            this.dispatcherWrapper = new DispatcherWrapper();
            this.dispatcherWrapper.readExternal(in);
            if (vrsn >= 3) {
               if (this.flowControl = in.readBoolean()) {
                  this.flowMinimum = in.readInt();
                  this.flowMaximum = in.readInt();
                  this.flowIncrease = in.readInt();
                  this.flowDecrease = in.readDouble();
                  this.flowInterval = in.readLong();
               }

               this.xaServerEnabled = in.readBoolean();
            }

            if (vrsn >= 4) {
               this.wlsServerName = in.readUTF();
               this.runtimeMBeanName = in.readUTF();
               if ((mask & 64) != 0) {
                  this.sendTimeout = in.readLong();
               } else {
                  this.sendTimeout = 10L;
               }
            }

            if (vrsn >= 5) {
               int extension = in.readInt();
               this.pipelineGeneration = 15728640 & extension;
               if ((extension & 1) != 0) {
                  this.compressionThreshold = in.readInt();
               }

               if ((extension & 2) != 0) {
                  this.unitOfOrder = in.readUTF();
               }

               if (vrsn >= 6) {
                  this.synchronousPrefetchMode = in.readInt();
               }

               if (vrsn >= 7) {
                  this.oneWaySendMode = in.readInt();
                  this.oneWaySendWindowSize = in.readInt();
                  this.internalSetReconnect(in.readInt(), in.readLong(), in.readLong());
               }

               if (vrsn >= 8) {
                  this.clientIdPolicy = in.readInt();
                  this.subscriptionSharingPolicy = in.readInt();
               }

               if (vrsn >= 9 && (extension & 4) != 0) {
                  this.subject = (AbstractSubject)in.readObject();
               }
            }

            return;
         case 2:
         default:
            throw JMSUtilities.versionIOException(vrsn, 1, 9);
      }
   }

   private int pushException(Request invocableRequest) {
      JMSPushExceptionRequest request = (JMSPushExceptionRequest)invocableRequest;

      try {
         this.mergedCloseAndOnException(request.getException(), false);
      } catch (Throwable var4) {
      }

      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   public final int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 15363:
            return this.pushException(request);
         default:
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNoSuchMethodLoggable(request.getMethodId()));
      }
   }

   private byte getVersion(Object oo) {
      PeerInfo pi = this.peerInfo;
      if (oo instanceof PeerInfoable) {
         PeerInfo tmp = ((PeerInfoable)oo).getPeerInfo();
         if (tmp != null) {
            pi = tmp;
         }
      }

      if (pi != null) {
         if (pi.compareTo(PeerInfo.VERSION_122140) >= 0) {
            return 9;
         } else if (pi.compareTo(PeerInfo.VERSION_1033) >= 0) {
            return 8;
         } else if (pi.compareTo(PeerInfo.VERSION_920) >= 0) {
            return 7;
         } else if (pi.compareTo(PeerInfo.VERSION_910) >= 0) {
            return 6;
         } else if (pi.compareTo(PeerInfo.VERSION_901) >= 0) {
            return 6;
         } else if (pi.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
            return 5;
         } else if (pi.compareTo(PeerInfo.VERSION_81) >= 0) {
            return 4;
         } else {
            return (byte)(pi.compareTo(PeerInfo.VERSION_70) >= 0 ? 3 : 1);
         }
      } else {
         return 9;
      }
   }

   public void forgetReconnectState() {
      JMSConnection preDisconnectStateLocal = this.preDisconnectState;
      if (preDisconnectStateLocal != null) {
         preDisconnectStateLocal.forgetReconnectState();
         this.preDisconnectState = null;
      }

      JMSConnection replacementConnectionLocal = this.replacementConnection;
      if (replacementConnectionLocal != null) {
         replacementConnectionLocal.forgetReconnectState();
         this.setReplacementConnection((JMSConnection)null);
      }

      PasswordStore pwdStoreLocal = this.pwdStore;
      if (pwdStoreLocal != null) {
         Object pHandleLocal = this.pHandle;
         Object uHandleLocal = this.uHandle;
         if (pHandleLocal != null) {
            pwdStoreLocal.removePassword(pHandleLocal);
         }

         if (uHandleLocal != null) {
            pwdStoreLocal.removePassword(uHandleLocal);
         }

         this.pwdStore = null;
      }

      this.uHandle = this.pHandle = null;
      this.wlConnectionImpl.setConnectionEstablishContext((JMSContext)null);
   }

   public PeerInfo getFEPeerInfo() {
      return this.dispatcherWrapper.getPeerInfo();
   }

   public static int convertPrefetchMode(String synchronousPrefetchMode) {
      if (synchronousPrefetchMode != null && !synchronousPrefetchMode.equals("disabled")) {
         if (synchronousPrefetchMode.equals("enabled")) {
            return 1;
         } else {
            return synchronousPrefetchMode.equals("topicSubscriberOnly") ? 2 : 0;
         }
      } else {
         return 0;
      }
   }

   public static int convertOneWaySendMode(String oneWaySendMode) {
      if (oneWaySendMode != null && !oneWaySendMode.equals("disabled")) {
         if (oneWaySendMode.equals("enabled")) {
            return 1;
         } else {
            return oneWaySendMode.equals("topicOnly") ? 2 : 0;
         }
      } else {
         return 0;
      }
   }

   void reconnect() {
      JMSConnection replacement = null;
      Throwable connectionProblem = null;
      JMSContext connectContext = this.wlConnectionImpl.getConnectionEstablishContext();

      try {
         JMSContext restoreContext;
         if (connectContext != null) {
            restoreContext = JMSContext.push(connectContext, true);
         } else {
            restoreContext = null;
         }

         try {
            replacement = (JMSConnection)this.preCreateReplacement(this.wlConnectionImpl.getJmsConnectionFactory());
            JMSConnection pds = this.preDisconnectState;
            if (replacement != null && pds != null) {
               replacement.resume(pds);
            }
         } catch (JMSException var18) {
            if (ReconnectController.TODOREMOVEDebug) {
               var18.printStackTrace();
            }

            connectionProblem = var18;
         } catch (Error var19) {
            if (ReconnectController.TODOREMOVEDebug) {
               var19.printStackTrace();
            }

            connectionProblem = var19;
            throw var19;
         } catch (RuntimeException var20) {
            if (ReconnectController.TODOREMOVEDebug) {
               var20.printStackTrace();
            }

            connectionProblem = var20;
            throw var20;
         } finally {
            if (connectContext != null) {
               JMSContext.pop(restoreContext, true);
            }

         }
      } finally {
         this.wlConnectionImpl.reconnectComplete(this, replacement, (Throwable)connectionProblem);
      }

   }

   boolean isInbound() {
      return this.inboundDest != null;
   }

   DestinationImpl getInboundDest() {
      return this.inboundDest;
   }

   public void setInboundDest(Destination inboundDest) throws JMSException {
      if (inboundDest == null) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestinationLoggable().getMessage());
      } else {
         this.inboundDest = (DestinationImpl)inboundDest;
         this.clientIdPolicy = 1;
         this.subscriptionSharingPolicy = 1;
      }
   }

   Hashtable getJndiEnv() {
      return this.jndiEnv;
   }

   void setJndiEnv(Hashtable jndiEnv) {
      this.jndiEnv = jndiEnv;
   }

   public void setWrappedIC(boolean value) {
      this.isWrappedIC = value;
   }

   public boolean isWrappedIC() {
      return this.isWrappedIC;
   }

   void markAsJMSSessionPooledInWrapper() {
      this.jmsSessionPooledInWrapper = true;
   }

   boolean isJMSSessionPooledInWrapper() {
      return this.jmsSessionPooledInWrapper;
   }

   public final String getPartitionName() {
      return this.dispatcherWrapper.getConnectionPartitionName();
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }

   public void setSubject(AbstractSubject subject) {
      this.subject = subject;
   }

   static {
      boolean thick = false;

      try {
         Class.forName("weblogic.rjvm.RJVM");
         thick = true;
      } catch (ClassNotFoundException var2) {
      }

      isT3Client = thick;
   }
}
