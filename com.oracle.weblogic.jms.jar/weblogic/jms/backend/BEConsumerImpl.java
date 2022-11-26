package weblogic.jms.backend;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.MessageConsumer;
import javax.jms.Topic;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jms.JMSService;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.DispatcherCompletionListener;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.JMSConsumerReceiveResponse;
import weblogic.jms.common.JMSConsumerSetListenerResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageEventLogListener;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSMessageLogHelper;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.NonDurableSubscription;
import weblogic.jms.common.SingularAggregatableManager;
import weblogic.jms.common.Subscription;
import weblogic.jms.common.TimedSecurityParticipant;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.ConsumerClosedException;
import weblogic.jms.extensions.ConsumerInfo;
import weblogic.logging.jms.JMSMessageLogger;
import weblogic.management.ManagementException;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.Event;
import weblogic.messaging.kernel.EventListener;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageAddEvent;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.MessageEvent;
import weblogic.messaging.kernel.MessageExpirationEvent;
import weblogic.messaging.kernel.MessageReceiveEvent;
import weblogic.messaging.kernel.MessageRedeliveryLimitEvent;
import weblogic.messaging.kernel.MessageRemoveEvent;
import weblogic.messaging.kernel.MessageSendEvent;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.ReceiveRequest;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.PersistentHandle;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.internal.TransactionImpl;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class BEConsumerImpl extends BEDeliveryList implements Listener, BEConsumerCommon, TimedSecurityParticipant, RedeliveryParameters, EventListener, JMSMessageEventLogListener {
   protected JMSID id;
   private InvocableMonitor invocableMonitor;
   private static final AuthenticatedSubject KERNEL_ID;
   private static final int LOG_CONSUMERCREATE = 1;
   private static final int LOG_CONSUMERDESTROY = 2;
   private String name;
   private String clientId;
   private String subscriptionName;
   private boolean durableSubscriber;
   private boolean multicastSubscriber;
   private boolean supportsClientResponsible;
   private boolean kernelAutoAcknowledge;
   private BESessionImpl session;
   protected BEDestinationImpl destination;
   protected Queue queue;
   volatile Queue unsubscribeQueue;
   protected ListenRequest listenRequest;
   private ReceiveRequest receiveRequest;
   private Expression filterExpression;
   private String selector;
   private boolean noLocal;
   private PersistentHandle persistentHandle;
   private long redeliveryDelay;
   private BEDurableSubscriberRuntimeMBeanImpl runtimeMBean;
   private int state = 1;
   protected int windowSize;
   private int pendingWindowSpace;
   private int unackedMessageCount;
   private final Object stateLock = new Object();
   private String subscriberUserInfo = null;
   private static final String CLIENTID_DELIMITER = "cid_";
   private static final String SUBSCRIPTION_DELIMITER = "_sid_";
   private static final int BLOCKING_RECV_PENDING = 101;
   private static final int BLOCKING_RECV_COMPLETE = 102;
   protected static final int STATE_STOPPED = 1;
   protected static final int STATE_HAS_LISTENER = 4;
   protected static final int STATE_BLOCKING_RECV = 8;
   protected static final int STATE_CLOSED = 16;
   protected static final int STATE_READY_FOR_PUSH = 4;
   protected static final int FLAG_CLIENT_MAY_BE_RESPONSIBLE = 4;
   protected static final int FLAG_MULTICAST_CONSUMER = 16;
   public int messageAddEventLogCount;
   public int messageSendEventLogCount;
   public int messageRemoveEventLogCount;
   public int messageReceiveEventLogCount;
   public int messageExpirationEventLogCount;
   public int messageRedeliveryLimitEventLogCount;
   private boolean isRegisteredForSecurity = false;
   private AuthenticatedSubject authenticatedSubject = null;
   private static final boolean debug = false;
   private ConsumerReconnectInfo consumerReconnectInfo;
   private int clientIdPolicy = 0;
   private int subscriptionSharingPolicy = 0;
   private Subscription subscription;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = -807894939480718536L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jms.backend.BEConsumerImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_JMS_Diagnostic_Volume_Before_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   protected BEConsumerImpl(BackEnd backEnd) {
      super(backEnd);
   }

   BEConsumerImpl(BESessionImpl session, BEDestinationImpl destination, Queue queue, Expression filterExpression, int flags, boolean reboot, BEConsumerCreateRequest createRequest) throws JMSException {
      super(destination.getBackEnd());
      createRequest.setName((String)null);
      createRequest.setClientId((String)null);
      createRequest.setNoLocal(false);
      this.init(session, destination, queue, filterExpression, flags, reboot, createRequest, (Subscription)null);
   }

   BEConsumerImpl(BESessionImpl session, BEDestinationImpl destination, Queue queue, int flags, boolean reboot, BEConsumerCreateRequest createRequest) throws JMSException {
      super(destination.getBackEnd());
      this.init(session, destination, queue, (Expression)null, flags, reboot, createRequest, (Subscription)null);
   }

   BEConsumerImpl(BESessionImpl session, BEDestinationImpl destination, Queue queue, int flags, boolean reboot, BEConsumerCreateRequest createRequest, Subscription sub) throws JMSException {
      super(destination.getBackEnd());
      this.init(session, destination, queue, (Expression)null, flags, reboot, createRequest, sub);
   }

   private boolean isWlsKernelId() {
      return WLSPrincipals.isKernelUsername(JMSSecurityHelper.getSimpleAuthenticatedName());
   }

   protected void init(BESessionImpl session, BEDestinationImpl destination, Queue queue, Expression filterExpression, int flags, boolean reboot, BEConsumerCreateRequest createRequest, Subscription sub) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         String queueName = null;
         if (queue != null) {
            queueName = queue.getName();
         }

         JMSDebug.JMSBackEnd.debug("Initialize BEConsumer: destination=" + destination + " subqueue = " + queueName + " clientId =" + createRequest.getClientId() + " client Id policy = " + createRequest.getClientIdPolicy() + " selector=" + this.selector + " noLocal = " + this.noLocal + " reboot " + reboot + " sub: " + sub + " this " + this);
      }

      this.authenticatedSubject = JMSSecurityHelper.getCurrentSubject();
      if (!this.isWlsKernelId()) {
         destination.getJMSDestinationSecurity().checkReceivePermission(JMSSecurityHelper.getCurrentSubject());
         this.checkSecurityRegistration(destination);
      }

      this.destination = destination;
      this.unsubscribeQueue = this.queue = queue;
      this.closeStaleConsumerSession(createRequest);
      this.id = createRequest.getConsumerId();
      this.session = session;
      this.invocableMonitor = this.backEnd.getJmsService().getInvocableMonitor();
      this.setRedeliveryDelay(createRequest.getRedeliveryDelay());
      this.filterExpression = filterExpression;
      this.selector = createRequest.getSelector();
      this.noLocal = createRequest.getNoLocal();
      this.subscriptionSharingPolicy = createRequest.getSubscriptionSharingPolicy();
      this.supportsClientResponsible = (flags & 4) != 0;
      this.setWindowSize(createRequest.getMessagesMaximum());
      this.clientId = createRequest.getClientId();
      this.clientIdPolicy = createRequest.getClientIdPolicy();
      this.subscriptionName = createRequest.getName();
      if (createRequest.isDurable()) {
         this.durableSubscriber = true;
         this.name = clientIdPlusName(this.clientId, this.subscriptionName, this.clientIdPolicy, ((Topic)destination.getDestination()).getTopicName(), destination.getBackEnd().getName());
         this.registerDurableSubscription(reboot, (DurableSubscription)sub);
      } else if (this.clientId == null && this.subscriptionName == null) {
         this.name = null;
      } else {
         this.name = this.clientId + (this.subscriptionName == null ? "" : "_sid_" + this.subscriptionName);
         this.addNonDurableSubscription();
      }

      if ((flags & 16) != 0) {
         this.multicastSubscriber = true;
      }

      this.subscriberUserInfo = JMSMessageLogHelper.addSubscriberInfo(this) + "#" + (createRequest.getSubject() != null ? createRequest.getSubject() : JMSSecurityHelper.getSimpleAuthenticatedName());
      if (!reboot) {
         this.logEvent(1);
      }

   }

   private void closeStaleConsumerSession(BEConsumerCreateRequest request) throws JMSException {
      if (request.getConsumerReconnectInfo() != null && request.getConsumerReconnectInfo().getInvokableID() != null) {
         BEConsumerImpl oldConsumer;
         try {
            oldConsumer = (BEConsumerImpl)this.destination.getBackEnd().getJmsService().getInvocableManagerDelegate().invocableFind(17, request.getConsumerReconnectInfo().getInvokableID());
         } catch (JMSException var6) {
            return;
         }

         if (oldConsumer != null && !oldConsumer.invalidateReconnectingConsumer(request)) {
            JMSMessageId lastAck = request.getConsumerReconnectInfo().getLastAckMsgId();
            if (lastAck != null) {
               long ackSeqNum = oldConsumer.getSession().sequenceFromMsgId(lastAck);
               if (ackSeqNum != Long.MAX_VALUE) {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("refreshed consumer ack stale " + ackSeqNum);
                  }

                  oldConsumer.backEnd.getJmsService().localDispatcherFind().dispatchSyncNoTran(new BESessionAcknowledgeRequest(oldConsumer.getSession().getJMSID(), ackSeqNum));
               }
            }

            if (this.destination instanceof BETopicImpl && request.getPersistentHandle() == null && request.getName() == null) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("transplant stale non-durable " + oldConsumer);
               }

               oldConsumer.unsubscribeQueue = this.queue;
               this.queue = oldConsumer.queue;
            } else {
               if (request.getConsumerReconnectInfo().getLastExposedMsgId() != null) {
                  oldConsumer.getSession().close(request.getConsumerReconnectInfo().getLastExposedMsgId());
               } else {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("closing stale session " + oldConsumer.getSession().getJMSID());
                  }

                  oldConsumer.getSession().close();
               }

            }
         }
      }
   }

   synchronized ConsumerReconnectInfo registerConsumerReconnectInfo(ConsumerReconnectInfo cri) throws JMSException {
      if (cri == null) {
         return this.consumerReconnectInfo = null;
      } else {
         cri = cri.getClone();
         cri.setInvokableID(this.getJMSID());
         cri.setServerDestId(this.destination.getJMSID());
         cri.setServerDispatcherId(this.backEnd.getJmsService().getLocalId());
         this.consumerReconnectInfo = cri;
         return cri.getClone();
      }
   }

   private boolean invalidateReconnectingConsumer(BEConsumerCreateRequest createRequest) throws JMSException {
      ConsumerReconnectInfo cri = createRequest.getConsumerReconnectInfo();
      if (this.consumerReconnectInfo == null) {
         return true;
      } else {
         return !this.consumerReconnectInfo.getClientJMSID().equals(cri.getClientJMSID()) || !this.destination.getJMSID().equals(cri.getServerDestId()) || !this.consumerReconnectInfo.getClientDispatcherId().equals(cri.getClientDispatcherId()) || !this.backEnd.getJmsService().getLocalId().equals(cri.getServerDispatcherId());
      }
   }

   synchronized long getDelayServerClose() {
      return this.consumerReconnectInfo == null ? 0L : this.consumerReconnectInfo.getDelayServerClose();
   }

   private void logEvent(int eventType) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[2];
            Object[] var10000 = var2.args;
            var10000[0] = this;
            var10000[1] = InstrumentationSupport.convertToObject(eventType);
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.process(var2);
         var2.resetPostBegin();
      }

      if (eventType == 1) {
         if (this.destination.isMessageLoggingEnabled() && (this.destination instanceof BEQueueImpl || this.isDurable() || this.backEnd.getJmsService().shouldMessageLogNonDurableSubscriber())) {
            JMSMessageLogHelper.logMessageEvent(this, new MessageConsumerCreationEventImpl((String)null, this.queue, this.selector, this.subscriberUserInfo));
         }
      } else if (eventType == 2 && this.destination.isMessageLoggingEnabled() && (this.destination instanceof BEQueueImpl || this.isDurable() || this.backEnd.getJmsService().shouldMessageLogNonDurableSubscriber())) {
         JMSMessageLogHelper.logMessageEvent(this, new MessageConsumerDestroyEventImpl((String)null, this.queue, this.subscriberUserInfo));
      }

   }

   public static String clientIdPlusName(String clientId, String name) throws JMSException {
      return clientIdPlusName(clientId, name, 0, (String)null, (String)null);
   }

   public static String clientIdPlusName(String clientId, String name, int clientIdPolicy, String destinationName, String backEndName) throws JMSException {
      if (clientId != null && clientId.length() == 0) {
         throw new JMSException("Zero length clientID");
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append("cid_");
         buf.append(clientId == null ? "" : clientId);
         buf.append("_sid_");
         buf.append(name);
         if (clientIdPolicy == 1 && destinationName != null) {
            buf.append("@" + destinationName + "@" + backEndName);
         }

         return buf.toString();
      }
   }

   public static String JNDINameForSubscription(String name) {
      StringBuffer buf = new StringBuffer();
      buf.append("weblogic.jms.internal.subscription");
      buf.append(".");
      buf.append(name);
      return buf.toString();
   }

   Queue getKernelQueue() {
      return this.queue;
   }

   public Queue getUnsubscribeQueue() {
      return this.unsubscribeQueue;
   }

   synchronized Subscription getSubscription() {
      return this.subscription;
   }

   synchronized void setSubscription(Subscription sub) {
      this.subscription = sub;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSubscriptionName() {
      return this.subscriptionName;
   }

   public String getClientID() {
      return this.clientId;
   }

   public int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public BEDestinationImpl getDestination() {
      return this.destination;
   }

   public String getSelector() {
      return this.selector;
   }

   public boolean getNoLocal() {
      return this.noLocal;
   }

   PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   void setPersistentHandle(PersistentHandle handle) {
      this.persistentHandle = handle;
   }

   boolean isKernelAutoAcknowledge() {
      return this.kernelAutoAcknowledge;
   }

   public int getSubscriptionSharingPolicy() {
      return this.subscriptionSharingPolicy;
   }

   public long getMessagesUnackedCount() {
      return this.queue == null ? 0L : (long)this.queue.getStatistics().getMessagesPending();
   }

   public long getMessagesReceivedCount() {
      return this.queue == null ? 0L : this.queue.getStatistics().getMessagesReceived();
   }

   public long getBytesUnackedCount() {
      return this.queue == null ? 0L : this.queue.getStatistics().getBytesPending();
   }

   public long getBytesCurrentCount() {
      return this.queue == null ? 0L : this.queue.getStatistics().getBytesCurrent() - this.queue.getStatistics().getBytesPending();
   }

   public long getLastMessagesReceivedTime() {
      return this.queue == null ? 0L : this.queue.getLastMessagesReceivedTime();
   }

   public int getSize() {
      return this.queue == null ? 0 : this.queue.getStatistics().getMessagesCurrent() - this.queue.getStatistics().getMessagesPending();
   }

   public int getHighSize() {
      return this.queue == null ? 0 : this.queue.getStatistics().getMessagesHigh();
   }

   public long getSubscriptionLimitDeletedCount() {
      return this.queue == null ? 0L : this.queue.getStatistics().getSubscriptionLimitMessagesDeleted();
   }

   private void setWindowSize(int size) {
      if (size < 0) {
         this.windowSize = Integer.MAX_VALUE;
      } else {
         this.windowSize = size;
      }

   }

   public void close(long lastSequenceNumber) throws JMSException {
      this.close(lastSequenceNumber, false);
   }

   public void close(long lastSequenceNumber, boolean isLastSequenceNumberFirstNotSeen) throws JMSException {
      if (!this.checkStateFlag(16)) {
         this.stop();
         if (this.session != null) {
            this.session.removeConsumer(this, lastSequenceNumber, isLastSequenceNumberFirstNotSeen);
         }

         this.closeInternal();
      }

   }

   public void closeWithError(String errorMsg) throws JMSException {
      if (!this.checkStateFlag(16)) {
         this.stop();

         try {
            if (this.session != null) {
               if (this.durableSubscriber && !this.isActive()) {
                  this.session.removeConsumer(this, 0L);
               } else {
                  weblogic.jms.common.JMSException jmse = new ConsumerClosedException((MessageConsumer)null, errorMsg);
                  this.session.removeConsumerWithError(this, 0L, jmse);
               }
            }
         } finally {
            this.closeInternal();
         }
      }

   }

   private void closeInternal() throws JMSException {
      if (this.checkStateFlag(8) && this.receiveRequest != null) {
         this.receiveRequest.cancel();
      }

      if (!this.durableSubscriber) {
         this.removeConsumer(false);
         this.logEvent(2);
      } else {
         DurableSubscription sub = this.destination.getBackEnd().getDurableSubscription(this.name);
         if (sub != null) {
            boolean cleanIt = false;
            synchronized(sub) {
               sub.removeSubscriber(this.getJMSID());
               if (sub.getSubscribersCount() > 0) {
                  cleanIt = true;
               }
            }

            if (cleanIt) {
               ((BETopicImpl)this.destination).removeConsumer(this, false, true);
            }
         }

         synchronized(this.stateLock) {
            this.clearStateFlag(12);
            this.setStateFlag(16);
         }
      }

      this.removeSecurityRegistration();
   }

   private void removeConsumer(boolean blocking) throws JMSException {
      this.setStateFlag(16);
      this.destination.removeConsumer(this, blocking);
   }

   public JMSID getJMSID() {
      return this.id;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.session.getDispatcherPartition4rmic();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public BESessionImpl getSession() {
      return this.session;
   }

   public boolean isDurable() {
      return this.durableSubscriber;
   }

   boolean isMulticastSubscriber() {
      return this.multicastSubscriber;
   }

   public boolean isActive() {
      return this.checkStateFlag(12);
   }

   public boolean isUsed() {
      return !this.checkStateFlag(16);
   }

   public BEDurableSubscriberRuntimeMBeanImpl getDurableSubscriberMbean() {
      return this.runtimeMBean;
   }

   public long getRedeliveryDelay() {
      long override = this.destination.getDirtyRedeliveryDelayOverride();
      return override >= 0L ? override : this.redeliveryDelay;
   }

   synchronized void setRedeliveryDelay(long newDelay) {
      if (newDelay < 0L) {
         this.redeliveryDelay = 0L;
      } else {
         this.redeliveryDelay = newDelay;
      }

   }

   protected void setStateFlag(int flag) {
      synchronized(this.stateLock) {
         this.state |= flag;
      }
   }

   boolean hasListener() {
      synchronized(this.stateLock) {
         return (this.state & 4) != 0;
      }
   }

   private void clearStateFlag(int flag) {
      synchronized(this.stateLock) {
         this.state &= ~flag;
      }
   }

   private boolean checkStateFlag(int flag) {
      synchronized(this.stateLock) {
         return (this.state & flag) != 0;
      }
   }

   private boolean isReadyForPush() {
      synchronized(this.stateLock) {
         return this.state == 4;
      }
   }

   void adjustUnackedCount(int count) {
      synchronized(this.stateLock) {
         this.unackedMessageCount += count;
      }
   }

   private void adjustUnackedCountTransactionally(Transaction tran, int count) throws JMSException {
      CountAdjuster adjuster = new CountAdjuster(-count, false, false);

      try {
         tran.registerSynchronization(adjuster);
      } catch (RollbackException var5) {
         adjuster.afterCompletion(1);
      } catch (IllegalStateException var6) {
         adjuster.afterCompletion(1);
      } catch (SystemException var7) {
         throw new weblogic.jms.common.JMSException(var7);
      }

   }

   private void addNonDurableSubscription() throws JMSException {
      NonDurableSubscription ttsub = new NonDurableSubscription(this.clientId, this.subscriptionName, this.destination.getDestinationImpl(), this.selector, this.noLocal, this.clientIdPolicy, this.subscriptionSharingPolicy, this.queue.getName());
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Adding non-durable sub: clientId = " + this.clientId + " subscriptionName = " + this.subscriptionName + " destination = " + this.destination.getDestinationImpl() + " selector = " + this.selector + " noLocal =  " + this.noLocal + " clientIdPolicy = " + this.clientIdPolicy + " subscriptionSharingPolicy = " + this.subscriptionSharingPolicy + " subQueueName = " + this.queue.getName());
      }

      NonDurableSubscription sub = null;
      if (this.subscriptionSharingPolicy == 0) {
         sub = ((BETopicImpl)this.destination).addNonDurableSubscriber(ttsub);
      } else {
         sub = ((BETopicImpl)this.destination).findNonDurableSubscriberJMS2(this.clientId, this.subscriptionName, this.selector, this.noLocal, this.clientIdPolicy, this.subscriptionSharingPolicy);
      }

      this.setSubscription(sub);
   }

   private void registerDurableSubscription(boolean reboot, DurableSubscription sub) throws JMSException {
      boolean newlyCreated = false;

      while(true) {
         if (sub == null) {
            synchronized(this.destination.getBackEnd().getDurableSubscriptionsMap()) {
               sub = this.destination.getBackEnd().getDurableSubscription(this.name);
               if (sub == null) {
                  sub = new DurableSubscription(this.name, this.destination.getDestinationImpl(), this.selector, this.noLocal, this.clientIdPolicy, this.subscriptionSharingPolicy);
                  newlyCreated = true;
                  this.destination.getBackEnd().addDurableSubscription(this.name, sub);
               }
            }
         }

         synchronized(sub) {
            if (newlyCreated) {
               this.setSubscription(sub);

               try {
                  this.createDurableSubscription(sub, reboot);
               } catch (JMSException var7) {
                  this.doDurableSubscriptionCleanup(sub, true, false, false, false);
                  throw var7;
               }

               sub.addSubscriber(this);
               return;
            }

            if (sub.isPending()) {
               sub.incrementWaits();

               try {
                  sub.wait();
               } catch (InterruptedException var9) {
               }

               sub.decrementWaits();
            }

            if (!sub.isStale()) {
               if (this.subscriptionSharingPolicy == 0 && sub.isActive()) {
                  throw new JMSException("Durable subscription " + this.subscriptionName + " is in use and cannot be shared");
               }

               this.persistentHandle = sub.getConsumer().getPersistentHandle();
               this.runtimeMBean = sub.getConsumer().getDurableSubscriberMbean();
               this.setSubscription(sub);
               sub.addSubscriber(this);
               return;
            }
         }
      }
   }

   private void createDurableSubscription(DurableSubscription sub, boolean reboot) throws JMSException {
      boolean bound = false;
      boolean reachEnd = false;
      JMSSQLExpression expression = null;
      expression = new JMSSQLExpression(this.selector, this.noLocal, this.session == null ? null : this.session.getConnection().getJMSID(), (JMSID)null, this.clientId, this.clientIdPolicy);
      if (!reboot && this.backEnd.isStoreEnabled()) {
         this.persistentHandle = this.backEnd.getDurableSubscriptionStore().createSubscription(this.destination.getName(), this.clientId, this.clientIdPolicy, this.subscriptionName, expression);
      }

      if (this.clientIdPolicy == 0) {
         boolean isBindVersioned = JMSServerUtilities.isBindVersioned();
         if (isBindVersioned) {
            JMSServerUtilities.unsetBindApplicationVersionIdContext();
         }

         try {
            JMSService jmsService = this.destination.getBackEnd().getJmsService();
            SingularAggregatableManager sam = jmsService.getSingularAggregatableManagerWithJMSException();
            String reason;
            if ((reason = sam.singularBind(JNDINameForSubscription(this.name), sub)) != null) {
               throw new NameAlreadyBoundException(reason);
            }

            bound = true;
         } catch (NamingException var22) {
            throw new weblogic.jms.common.JMSException("Error creating durable subscriber", var22);
         } finally {
            if (isBindVersioned) {
               JMSServerUtilities.setBindApplicationVersionIdContext();
            }

         }
      }

      SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

      try {
         this.runtimeMBean = new BEDurableSubscriberRuntimeMBeanImpl(this.getDurableSubscriptionRuntimeMBeanName(this.clientId, this.subscriptionName, this.destination), this.destination, this);
      } catch (ManagementException var20) {
         throw new weblogic.jms.common.JMSException("Error registering durable subscriber RuntimeMBean", var20);
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

      this.runtimeMBean.setMessageManagementDelegate(new BEMessageManagementImpl(this.name, this.queue, this.destination, this.runtimeMBean));
   }

   synchronized boolean restore(BEConsumerCreateRequest request, BESessionImpl newSession, boolean started) throws JMSException {
      if (!this.isClosed()) {
         return false;
      } else if (!this.checkStateFlag(1)) {
         throw new AssertionError("Restarting a consumer that was not stopped");
      } else {
         this.id = request.getConsumerId();
         this.session = newSession;
         this.subscriptionSharingPolicy = request.getSubscriptionSharingPolicy();
         this.checkSecurityRegistration(this.destination);
         this.checkPermission(this.destination, true, true);
         this.clearStateFlag(16);
         DurableSubscription sub = this.destination.getBackEnd().getDurableSubscription(this.name);
         if (sub != null) {
            sub.addSubscriber(this);
         }

         if (this.runtimeMBean != null) {
            this.runtimeMBean.setConsumer(this);
         }

         this.setWindowSize(request.getMessagesMaximum());
         this.subscriberUserInfo = JMSMessageLogHelper.addSubscriberInfo(this) + "#" + (request.getSubject() != null ? request.getSubject() : JMSSecurityHelper.getSimpleAuthenticatedName());
         if (started) {
            this.start();
         }

         return true;
      }
   }

   private void checkSecurityRegistration(BEDestinationImpl dest) {
      synchronized(this.stateLock) {
         if (this.isRegisteredForSecurity || this.isWlsKernelId()) {
            return;
         }

         this.isRegisteredForSecurity = true;
      }

      this.backEnd.getJmsService().registerSecurityParticipant(dest.getJMSDestinationSecurity().getJMSResourceForReceive(), this);
   }

   private void removeSecurityRegistration() {
      boolean needToClear = false;
      synchronized(this.stateLock) {
         needToClear = this.isRegisteredForSecurity;
      }

      if (needToClear) {
         this.backEnd.getJmsService().unregisterSecurityParticipant(this);
      }

   }

   public void delete(boolean inPreDeregister, boolean checkUnackCount) throws JMSException {
      this.delete(inPreDeregister, checkUnackCount, true);
   }

   void delete(boolean inPreDeregister, boolean checkUnackCount, boolean adminMode) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Deleting a durable subiscriber " + this);
      }

      if (this.checkStateFlag(12)) {
         throw new weblogic.jms.common.JMSException("Active topicSubscriber is using this subscription right now");
      } else {
         synchronized(this.stateLock) {
            if (checkUnackCount && this.unackedMessageCount > 0) {
               throw new weblogic.jms.common.JMSException("Subscription " + this.name + " in use, uncommitted/unacknowleged messages " + this.unackedMessageCount);
            }
         }

         this.cleanupDurableSubscription(true, inPreDeregister, true, false, adminMode);
      }
   }

   public void closeDurableSubscription() throws JMSException {
      try {
         this.close(0L);
      } finally {
         this.cleanupDurableSubscription(true, false, false, false, true);
      }

   }

   public void cleanupDurableSubscription(boolean unbinding, boolean inPreDeregister, boolean deleteIt, boolean blockingDelete, boolean adminMode) throws JMSException {
      if (!this.durableSubscriber) {
         throw new weblogic.jms.common.JMSException("Not a durable subscription");
      } else {
         DurableSubscription durSub = null;
         if (!adminMode) {
            durSub = this.destination.getBackEnd().getDurableSubscription(this.name);
            if (durSub == null) {
               throw new weblogic.jms.common.JMSException("Subscription not found");
            }

            synchronized(durSub) {
               if (durSub.isPending() || durSub.getSubscribersCount() > 0) {
                  throw new weblogic.jms.common.JMSException("Subscription " + this.name + " is in use");
               }

               if (durSub.isStale()) {
                  throw new weblogic.jms.common.JMSException("Subscription " + this.name + " is not found");
               }

               durSub.setPending(true);
            }
         } else {
            while(true) {
               durSub = this.destination.getBackEnd().getDurableSubscription(this.name);
               if (durSub == null) {
                  return;
               }

               synchronized(durSub) {
                  if (durSub.isStale()) {
                     return;
                  }

                  if (!durSub.isPending()) {
                     if (durSub.isStale()) {
                        return;
                     }

                     durSub.setPending(true);
                     break;
                  }

                  durSub.incrementWaits();

                  try {
                     durSub.wait();
                  } catch (InterruptedException var11) {
                  }

                  durSub.decrementWaits();
               }
            }
         }

         this.doDurableSubscriptionCleanup(durSub, unbinding, inPreDeregister, deleteIt, blockingDelete);
         this.logEvent(2);
      }
   }

   void doDurableSubscriptionCleanup(DurableSubscription durSub, boolean unbinding, boolean inPreDeregister, boolean deleteIt, boolean blockingDelete) throws JMSException {
      boolean var197 = false;

      try {
         var197 = true;
         if (!inPreDeregister) {
            if (this.runtimeMBean != null) {
               BEDurableSubscriberRuntimeMBeanImpl mBeanCopy = this.runtimeMBean;
               this.runtimeMBean = null;
               PrivilegedActionUtilities.unregister(mBeanCopy, KERNEL_ID);
               var197 = false;
            } else {
               var197 = false;
            }
         } else {
            var197 = false;
         }
      } catch (ManagementException var217) {
         Throwable nestedE = var217.getNestedException();
         if (nestedE == null) {
            nestedE = var217;
         }

         throw new weblogic.jms.common.JMSException("Error closing durable subscription. " + ((Throwable)nestedE).getMessage(), var217);
      } finally {
         if (var197) {
            boolean var174 = false;

            try {
               var174 = true;
               if (deleteIt) {
                  this.removeConsumer(blockingDelete);
                  var174 = false;
               } else {
                  var174 = false;
               }
            } catch (JMSException var201) {
               throw var201;
            } finally {
               if (var174) {
                  boolean isBindVersioned = false;
                  boolean var151 = false;

                  try {
                     var151 = true;
                     if (unbinding) {
                        if (this.clientIdPolicy == 0) {
                           JMSService jmsService = this.destination.getBackEnd().getJmsService();
                           SingularAggregatableManager sam = jmsService.getSingularAggregatableManagerWithJMSException();
                           isBindVersioned = JMSServerUtilities.isBindVersioned();
                           if (isBindVersioned) {
                              JMSServerUtilities.unsetBindApplicationVersionIdContext();
                           }

                           sam.singularUnbind(JNDINameForSubscription(this.name));
                           var151 = false;
                        } else {
                           var151 = false;
                        }
                     } else {
                        var151 = false;
                     }
                  } catch (JMSException var198) {
                     throw var198;
                  } finally {
                     if (var151) {
                        synchronized(durSub) {
                           durSub.setStale(true);
                           this.destination.getBackEnd().removeDurableSubscription(this.name);
                           if (durSub.isPending()) {
                              durSub.setPending(false);
                              if (durSub.hasWaits()) {
                                 durSub.notifyAll();
                              }
                           }
                        }

                        if (isBindVersioned) {
                           JMSServerUtilities.setBindApplicationVersionIdContext();
                        }

                     }
                  }

                  synchronized(durSub) {
                     durSub.setStale(true);
                     this.destination.getBackEnd().removeDurableSubscription(this.name);
                     if (durSub.isPending()) {
                        durSub.setPending(false);
                        if (durSub.hasWaits()) {
                           durSub.notifyAll();
                        }
                     }
                  }

                  if (isBindVersioned) {
                     JMSServerUtilities.setBindApplicationVersionIdContext();
                  }

               }
            }

            boolean isBindVersioned = false;
            boolean var128 = false;

            try {
               var128 = true;
               if (unbinding) {
                  if (this.clientIdPolicy == 0) {
                     JMSService jmsService = this.destination.getBackEnd().getJmsService();
                     SingularAggregatableManager sam = jmsService.getSingularAggregatableManagerWithJMSException();
                     isBindVersioned = JMSServerUtilities.isBindVersioned();
                     if (isBindVersioned) {
                        JMSServerUtilities.unsetBindApplicationVersionIdContext();
                     }

                     sam.singularUnbind(JNDINameForSubscription(this.name));
                     var128 = false;
                  } else {
                     var128 = false;
                  }
               } else {
                  var128 = false;
               }
            } catch (JMSException var200) {
               throw var200;
            } finally {
               if (var128) {
                  synchronized(durSub) {
                     durSub.setStale(true);
                     this.destination.getBackEnd().removeDurableSubscription(this.name);
                     if (durSub.isPending()) {
                        durSub.setPending(false);
                        if (durSub.hasWaits()) {
                           durSub.notifyAll();
                        }
                     }
                  }

                  if (isBindVersioned) {
                     JMSServerUtilities.setBindApplicationVersionIdContext();
                  }

               }
            }

            synchronized(durSub) {
               durSub.setStale(true);
               this.destination.getBackEnd().removeDurableSubscription(this.name);
               if (durSub.isPending()) {
                  durSub.setPending(false);
                  if (durSub.hasWaits()) {
                     durSub.notifyAll();
                  }
               }
            }

            if (isBindVersioned) {
               JMSServerUtilities.setBindApplicationVersionIdContext();
            }

         }
      }

      boolean var105 = false;

      try {
         var105 = true;
         if (deleteIt) {
            this.removeConsumer(blockingDelete);
            var105 = false;
         } else {
            var105 = false;
         }
      } catch (JMSException var203) {
         throw var203;
      } finally {
         if (var105) {
            boolean isBindVersioned = false;
            boolean var82 = false;

            try {
               var82 = true;
               if (unbinding) {
                  if (this.clientIdPolicy == 0) {
                     JMSService jmsService = this.destination.getBackEnd().getJmsService();
                     SingularAggregatableManager sam = jmsService.getSingularAggregatableManagerWithJMSException();
                     isBindVersioned = JMSServerUtilities.isBindVersioned();
                     if (isBindVersioned) {
                        JMSServerUtilities.unsetBindApplicationVersionIdContext();
                     }

                     sam.singularUnbind(JNDINameForSubscription(this.name));
                     var82 = false;
                  } else {
                     var82 = false;
                  }
               } else {
                  var82 = false;
               }
            } catch (JMSException var199) {
               throw var199;
            } finally {
               if (var82) {
                  synchronized(durSub) {
                     durSub.setStale(true);
                     this.destination.getBackEnd().removeDurableSubscription(this.name);
                     if (durSub.isPending()) {
                        durSub.setPending(false);
                        if (durSub.hasWaits()) {
                           durSub.notifyAll();
                        }
                     }
                  }

                  if (isBindVersioned) {
                     JMSServerUtilities.setBindApplicationVersionIdContext();
                  }

               }
            }

            synchronized(durSub) {
               durSub.setStale(true);
               this.destination.getBackEnd().removeDurableSubscription(this.name);
               if (durSub.isPending()) {
                  durSub.setPending(false);
                  if (durSub.hasWaits()) {
                     durSub.notifyAll();
                  }
               }
            }

            if (isBindVersioned) {
               JMSServerUtilities.setBindApplicationVersionIdContext();
            }

         }
      }

      boolean isBindVersioned = false;
      boolean var59 = false;

      try {
         var59 = true;
         if (unbinding) {
            if (this.clientIdPolicy == 0) {
               JMSService jmsService = this.destination.getBackEnd().getJmsService();
               SingularAggregatableManager sam = jmsService.getSingularAggregatableManagerWithJMSException();
               isBindVersioned = JMSServerUtilities.isBindVersioned();
               if (isBindVersioned) {
                  JMSServerUtilities.unsetBindApplicationVersionIdContext();
               }

               sam.singularUnbind(JNDINameForSubscription(this.name));
               var59 = false;
            } else {
               var59 = false;
            }
         } else {
            var59 = false;
         }
      } catch (JMSException var202) {
         throw var202;
      } finally {
         if (var59) {
            synchronized(durSub) {
               durSub.setStale(true);
               this.destination.getBackEnd().removeDurableSubscription(this.name);
               if (durSub.isPending()) {
                  durSub.setPending(false);
                  if (durSub.hasWaits()) {
                     durSub.notifyAll();
                  }
               }
            }

            if (isBindVersioned) {
               JMSServerUtilities.setBindApplicationVersionIdContext();
            }

         }
      }

      synchronized(durSub) {
         durSub.setStale(true);
         this.destination.getBackEnd().removeDurableSubscription(this.name);
         if (durSub.isPending()) {
            durSub.setPending(false);
            if (durSub.hasWaits()) {
               durSub.notifyAll();
            }
         }
      }

      if (isBindVersioned) {
         JMSServerUtilities.setBindApplicationVersionIdContext();
      }

   }

   protected boolean allowsImplicitAcknowledge() {
      if (this.session == null) {
         return false;
      } else if (!this.supportsClientResponsible) {
         return false;
      } else if (!this.session.allowsImplicitAcknowledge()) {
         return false;
      } else if (this.getRedeliveryDelay() != 0L) {
         return false;
      } else if (this.destination.getDirtyRedeliveryLimit() >= 0 && this.destination.getDirtyRedeliveryLimit() != Integer.MAX_VALUE) {
         return false;
      } else {
         return this.subscriptionSharingPolicy != 1 || this.clientId == null;
      }
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.destination.getBackEnd().getJmsService(), "BEConsumerImpl");
      switch (request.getMethodId()) {
         case 10001:
            long lastSequenceNumber = ((BEConsumerCloseRequest)request).getLastSequenceNumber();
            boolean isLastSequenceNumberFirstNotSeen = ((BEConsumerCloseRequest)request).isLastSequenceNumberFirstNotSeen();
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Close backend consumer request: " + this.getJMSID() + ", backend lastSequenceNumber=" + lastSequenceNumber + ", backend isLastSequenceNumberFirstNotSeen=" + isLastSequenceNumberFirstNotSeen);
            }

            this.close(lastSequenceNumber, isLastSequenceNumberFirstNotSeen);
            break;
         case 10513:
            this.destination.checkShutdownOrSuspendedNeedLock("increment consumer window");
            this.incrementWindowCurrent((BEConsumerIncrementWindowCurrentRequest)request);
            break;
         case 10769:
            request.setResult(new BEConsumerIsActiveResponse(this.isActive()));
            request.setState(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
         case 11025:
            this.destination.checkShutdownOrSuspendedNeedLock("receive message");
            return this.receive((BEConsumerReceiveRequest)request);
         case 11281:
            return this.setListener((BEConsumerSetListenerRequest)request);
         case 17169:
            throw new AssertionError("Not implemented");
         default:
            throw new AssertionError("No such method " + request.getMethodId());
      }

      request.setResult(VoidResponse.THE_ONE);
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   private boolean blockingReceiveStart(BEConsumerReceiveRequest request) throws JMSException {
      request.setTransaction((TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction());
      boolean startRequest;
      synchronized(this.stateLock) {
         if (this.checkStateFlag(12)) {
            throw new weblogic.jms.common.JMSException("Invalid blocking receive when another receive is in progress");
         }

         this.setStateFlag(8);
         startRequest = !this.checkStateFlag(1);
      }

      long timeout;
      if (request.getTimeout() == 9223372036854775806L) {
         timeout = 0L;
      } else if (request.getTimeout() == Long.MAX_VALUE) {
         timeout = Long.MAX_VALUE;
      } else {
         timeout = request.getTimeout();
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Starting blocking receive for consumer");
      }

      try {
         this.receiveRequest = this.queue.receive(this.filterExpression, 1, this.allowsImplicitAcknowledge(), this.recoveryUnit(request.getTransaction()), timeout, startRequest, this.subscriberUserInfo);
      } catch (KernelException var8) {
         throw new weblogic.jms.common.JMSException(var8);
      }

      request.setState(101);
      request.setKernelRequest(this.receiveRequest);
      synchronized(this.receiveRequest) {
         if (!this.receiveRequest.hasResult()) {
            request.needOutsideResult();
            request.setWorkManager(this.getBackEnd().getWorkManager());
            this.receiveRequest.addListener(new DispatcherCompletionListener(request), this.getBackEnd().getWorkManager());
            return true;
         } else {
            return false;
         }
      }
   }

   private Object recoveryUnit(TransactionImpl transactionImpl) {
      if (transactionImpl != null) {
         return transactionImpl;
      } else {
         return this.session == null ? this : this.session.getRecoveryUnit();
      }
   }

   private boolean blockingReceiveProcessMessage(BEConsumerReceiveRequest request) throws JMSException {
      List messageList;
      try {
         messageList = (List)request.getKernelRequest().getResult();
      } catch (KernelException var15) {
         this.clearStateFlag(8);
         throw new weblogic.jms.common.JMSException("Error in blocking receive", var15);
      }

      request.setState(102);
      request.setKernelRequest((KernelRequest)null);
      if (messageList != null && !messageList.isEmpty()) {
         if (!$assertionsDisabled && messageList.size() != 1) {
            throw new AssertionError();
         } else {
            MessageElement ref = (MessageElement)messageList.get(0);
            MessageImpl message = (MessageImpl)ref.getMessage();
            ref.setUserData(this);
            boolean clientResponsibleForAck = this.allowsImplicitAcknowledge();
            boolean acknowledgeLocally = clientResponsibleForAck || request.isTransactional() || this.session.getAcknowledgeMode() == 4;
            long sequenceNum = this.session.getNextSequenceNumber();
            ref.setUserSequenceNum(sequenceNum);
            if (!acknowledgeLocally) {
               this.session.addPendingMessage(ref, this);
            }

            if (ref.getDeliveryCount() > 0 || clientResponsibleForAck) {
               message = message.cloneit();
               message.setDeliveryCount(ref.getDeliveryCount());
               message.setClientResponsibleForAcknowledge(clientResponsibleForAck);
            }

            request.setResult(new JMSConsumerReceiveResponse(message, sequenceNum, request.isTransactional()));
            if (request.isTransactional()) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Associating message with transaction");
               }

               try {
                  this.queue.associate(ref, this);
                  this.adjustUnackedCount(1);
                  this.adjustUnackedCountTransactionally(request.getTransaction(), -1);
               } catch (KernelException var14) {
                  this.clearStateFlag(8);
                  throw new weblogic.jms.common.JMSException(var14);
               }
            } else if (acknowledgeLocally) {
               if (!clientResponsibleForAck) {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("Acknowledging message");
                  }

                  try {
                     KernelRequest req = this.queue.acknowledge(ref);
                     if (req != null) {
                        synchronized(req) {
                           if (!req.hasResult()) {
                              request.setKernelRequest(req);
                              request.needOutsideResult();
                              req.addListener(new DispatcherCompletionListener(request), this.getBackEnd().getWorkManager());
                              return true;
                           }
                        }
                     }
                  } catch (KernelException var13) {
                     this.clearStateFlag(8);
                     throw new weblogic.jms.common.JMSException(var13);
                  }
               }
            } else {
               this.adjustUnackedCount(1);
            }

            return false;
         }
      } else {
         request.setResult(new JMSConsumerReceiveResponse((MessageImpl)null, 0L, request.isTransactional()));
         return false;
      }
   }

   private void blockingReceiveComplete(BEConsumerReceiveRequest request) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Blocking receive for consumer complete");
      }

      if (request.getKernelRequest() != null) {
         try {
            request.getKernelRequest().getResult();
         } catch (KernelException var3) {
            this.clearStateFlag(8);
            throw new weblogic.jms.common.JMSException(var3);
         }
      }

      this.clearStateFlag(8);
      this.receiveRequest = null;
      request.setState(Integer.MAX_VALUE);
   }

   private int receive(BEConsumerReceiveRequest request) throws JMSException {
      this.checkSecurityRegistration(this.destination);
      this.checkPermission(this.destination, true, true);

      while(true) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Blocking receive request: state = " + request.getState());
         }

         switch (request.getState()) {
            case 0:
               if (this.blockingReceiveStart(request)) {
                  return request.getState();
               }
               break;
            case 101:
               if (this.blockingReceiveProcessMessage(request)) {
                  return request.getState();
               }
               break;
            case 102:
               this.blockingReceiveComplete(request);
               this.checkPermission(this.destination, true, true);
               return Integer.MAX_VALUE;
            default:
               throw new AssertionError("Invalid request state");
         }
      }
   }

   private int setListener(BEConsumerSetListenerRequest request) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Consumer got a setListener request: " + request.getHasListener());
      }

      if (!WLSPrincipals.isAnonymousUsername(JMSSecurityHelper.getSimpleAuthenticatedName())) {
         this.checkSecurityRegistration(this.destination);
      }

      request.setResult(new JMSConsumerSetListenerResponse(this.session.getSequenceNumber()));
      synchronized(this) {
         if (!(request.getHasListener() ^ this.checkStateFlag(4))) {
            return Integer.MAX_VALUE;
         }

         if (request.getHasListener()) {
            this.setStateFlag(4);
            if (this.isReadyForPush()) {
               this.startListening();
            }
         } else {
            this.clearStateFlag(4);
            this.stopListening();
         }
      }

      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   private void incrementWindowCurrent(BEConsumerIncrementWindowCurrentRequest request) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Consumer got incrementWindowCurrent. Increment = " + request.getWindowIncrement());
      }

      this.makeWindowSpace(request.getWindowIncrement(), true);
   }

   private void makeWindowSpace(int increment, boolean force) throws JMSException {
      int toIncrement = 0;
      ListenRequest localListenRequest = null;
      synchronized(this) {
         if (this.isReadyForPush() && this.listenRequest != null) {
            int maxIncrement = this.windowSize - this.listenRequest.getCount();
            this.pendingWindowSpace += increment;
            if (force || this.pendingWindowSpace >= this.windowSize / 2) {
               toIncrement = Math.min(this.pendingWindowSpace, maxIncrement);
               if (toIncrement > 0) {
                  localListenRequest = this.listenRequest;
               }

               this.pendingWindowSpace = 0;
            }
         }
      }

      if (localListenRequest != null) {
         try {
            localListenRequest.incrementCount(toIncrement);
         } catch (KernelException var8) {
            throw new weblogic.jms.common.JMSException(var8);
         }
      }

   }

   void incrementPendingCount(int count, boolean force) throws JMSException {
      this.adjustUnackedCount(-count);
      this.makeWindowSpace(count, force);
   }

   void incrementPendingCountTransactionally(Transaction tran, int count, boolean force) throws JMSException {
      CountAdjuster adjuster = new CountAdjuster(count, true, force);

      try {
         tran.registerSynchronization(adjuster);
      } catch (RollbackException var6) {
         adjuster.afterCompletion(1);
      } catch (IllegalStateException var7) {
         adjuster.afterCompletion(1);
      } catch (SystemException var8) {
         throw new weblogic.jms.common.JMSException(var8);
      }

   }

   JMSPushEntry createPushEntry(MessageElement ref, boolean clientResponsible, boolean implicitAcknowledge) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Pushing " + ((MessageImpl)ref.getMessage()).getJMSMessageID() + ". implicitAcknowledge = " + implicitAcknowledge);
      }

      JMSPushEntry pushEntry = new JMSPushEntry(this.session.getSequencerId(), this.id, ref.getUserSequenceNum(), 0L, ref.getDeliveryCount(), this.session.getPipelineGeneration());
      pushEntry.setClientResponsibleForAcknowledge(clientResponsible);
      return pushEntry;
   }

   void checkPermission(boolean terminateInDifferentThread) throws JMSSecurityException {
      this.checkPermission(this.destination, terminateInDifferentThread);
   }

   void checkPermission(boolean terminateInDifferentThread, boolean synchronousCall) throws JMSSecurityException {
      this.checkPermission(this.destination, terminateInDifferentThread, synchronousCall);
   }

   /** @deprecated */
   @Deprecated
   void checkPermission(BEDestinationImpl destination, boolean terminateInDifferentThread) throws JMSSecurityException {
      this.checkPermission(destination, terminateInDifferentThread, true);
   }

   void checkPermission(BEDestinationImpl destination, boolean terminateInDifferentThread, boolean synchronousCall) throws JMSSecurityException {
      if (!this.isWlsKernelId()) {
         AuthenticatedSubject authSubject = this.getSubject();
         AuthenticatedSubject currentSubject = JMSSecurityHelper.getCurrentSubject();
         if (!this.backEnd.getJmsService().isSecurityCheckerStop()) {
            if (synchronousCall && authSubject != currentSubject && (authSubject == null || !authSubject.equals(currentSubject))) {
               destination.getJMSDestinationSecurity().checkReceivePermission(currentSubject);
               this.setSubject(currentSubject);
            }
         } else {
            try {
               destination.getJMSDestinationSecurity().checkReceivePermission(authSubject);
            } catch (JMSSecurityException var7) {
               if (terminateInDifferentThread) {
                  WorkManagerFactory.getInstance().getSystem().schedule(new ConsumerCloseThread());
               }

               throw var7;
            }
         }

      }
   }

   void checkPermission(boolean terminateInDifferentThread, MessageImpl message) throws JMSSecurityException {
      this.checkPermission(this.destination, terminateInDifferentThread, false, message);
   }

   void checkPermission(boolean terminateInDifferentThread, boolean synchronousCall, MessageImpl message) throws JMSSecurityException {
      this.checkPermission(this.destination, terminateInDifferentThread, false, message);
   }

   void checkPermission(BEDestinationImpl destination, boolean terminateInDifferentThread, boolean synchronousCall, MessageImpl message) throws JMSSecurityException {
      this.checkPermission(destination, terminateInDifferentThread, synchronousCall);
      if (message != null && message.getJMSType() != null && message.getJMSType().equals("abcXXX")) {
         if (terminateInDifferentThread) {
            WorkManagerFactory.getInstance().getSystem().schedule(new ConsumerCloseThread());
         }

         throw new JMSSecurityException("security check simulation negative result");
      }
   }

   public Runnable deliver(ListenRequest request, List messageList) {
      try {
         this.checkPermission(this.destination, true, false);
      } catch (JMSSecurityException var5) {
         return null;
      }

      Iterator i = messageList.iterator();

      while(i.hasNext()) {
         MessageElement elt = (MessageElement)i.next();
         elt.setUserData(this);
         elt.setUserSequenceNum(this.session.getNextSequenceNumber());
      }

      return this.session.deliver(request, messageList);
   }

   public Runnable deliver(ListenRequest request, MessageElement elt) {
      try {
         this.checkPermission(this.destination, true, false);
      } catch (JMSSecurityException var4) {
         return null;
      }

      elt.setUserData(this);
      elt.setUserSequenceNum(this.session.getNextSequenceNumber());
      return this.session.deliver(request, elt);
   }

   protected void pushMessages(List messages) {
      if (!$assertionsDisabled) {
         throw new AssertionError();
      }
   }

   private WorkManager findPushWorkManager() {
      WorkManager ret = null;
      if (this.session != null && this.session.getPushWorkManager() != null) {
         ret = WorkManagerFactory.getInstance().find(this.session.getPushWorkManager());
      }

      if (ret == null) {
         ret = this.getBackEnd().getAsyncPushWorkManager();
      }

      return ret;
   }

   synchronized void startListening() throws JMSException {
      if (!this.multicastSubscriber) {
         WorkManager wm = this.findPushWorkManager();

         try {
            if (this.session != null) {
               this.session.setBackEnd(this.destination.getBackEnd());
               this.session.adjustWindowSize(this.windowSize);
               this.session.setWorkManager(wm);
            }

            this.kernelAutoAcknowledge = this.allowsImplicitAcknowledge();
            this.setWorkManager(wm);
            this.listenRequest = this.queue.listen(this.filterExpression, this.windowSize, this.kernelAutoAcknowledge, this.recoveryUnit((TransactionImpl)null), this, this.destination.getBackEnd().getMultiSender(), this.subscriberUserInfo, wm);
         } catch (KernelException var3) {
            throw new weblogic.jms.common.JMSException(var3);
         }
      }
   }

   synchronized boolean stopListening() {
      if (this.listenRequest != null) {
         if (this.session != null) {
            this.session.adjustWindowSize(-this.windowSize);
         }

         this.listenRequest.stop();
         this.listenRequest = null;
         return true;
      } else {
         return false;
      }
   }

   private synchronized void startInternal(int stoppedState) throws JMSException {
      boolean wasReady = this.isReadyForPush();
      this.clearStateFlag(stoppedState);
      if (this.isReadyForPush() && !wasReady) {
         this.startListening();
      } else if (this.checkStateFlag(8) && this.receiveRequest != null) {
         try {
            this.receiveRequest.start();
         } catch (KernelException var4) {
            throw new weblogic.jms.common.JMSException(var4);
         }
      }

   }

   public void start() throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Got a start request on the consumer " + this);
      }

      this.startInternal(1);
   }

   private synchronized void stopInternal(int stopState) {
      this.setStateFlag(stopState);
      if (this.checkStateFlag(8) && this.receiveRequest != null) {
         this.receiveRequest.stop();
      } else if (this.listenRequest != null) {
         this.stopListening();
      }

   }

   public void stop() {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Got a stop request on the consumer");
      }

      this.stopInternal(1);
   }

   public void securityLapsed() {
      try {
         this.closeWithError("ERROR: Security has lapsed for this consumer");
      } catch (JMSException var2) {
         System.out.println("ERROR: Could not push security exception to consumer: " + var2);
      }

   }

   public boolean isClosed() {
      return this.checkStateFlag(16);
   }

   public HashSet getAcceptedDestinations() {
      return null;
   }

   public void onEvent(Event event) {
      if (event instanceof MessageSendEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageAddEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageReceiveEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageExpirationEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageRedeliveryLimitEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageRemoveEvent) {
         this.onMessageEvent((MessageEvent)event);
      }

   }

   private final void onMessageEvent(MessageEvent event) {
      JMSMessageLogHelper.logMessageEvent(this, event);
   }

   public JMSMessageLogger getJMSMessageLogger() {
      return this.destination.getBackEnd().getJMSMessageLogger();
   }

   public final List getMessageLoggingJMSHeaders() {
      return this.destination.getMessageLoggingJMSHeaders();
   }

   public final List getMessageLoggingUserProperties() {
      return this.destination.getMessageLoggingUserProperties();
   }

   public final String getListenerName() {
      return this.destination.getName();
   }

   private String getDurableSubscriptionRuntimeMBeanName(String clientId, String subscriptionName, BEDestinationImpl destination) {
      String ret = (clientId == null ? "" : clientId) + "_" + subscriptionName;
      if (this.clientIdPolicy == 1) {
         ret = ret + "@" + destination.getName() + "@" + destination.getBackEnd().getShortName();
      }

      return ret;
   }

   public void dumpRef(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeAttribute("id", this.id != null ? this.id.toString() : "");
      String sId = "";
      String cId = "";
      if (this.session != null) {
         ID sessionID = this.session.getId();
         if (sessionID != null) {
            sId = sessionID.toString();
         }

         BEConnection c = this.session.getConnection();
         if (c != null) {
            ID connectionID = c.getId();
            if (connectionID != null) {
               cId = connectionID.toString();
            }
         }
      }

      xsw.writeAttribute("sessionID", sId);
      xsw.writeAttribute("connectionID", cId);
   }

   protected void dumpCommon(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeAttribute("name", this.name != null ? this.name : "");
      xsw.writeAttribute("id", this.id != null ? this.id.toString() : "");
      xsw.writeAttribute("state", String.valueOf(this.state));
      xsw.writeAttribute("subscriptionName", this.subscriptionName != null ? this.subscriptionName : "");
      xsw.writeAttribute("isDurable", String.valueOf(this.durableSubscriber));
      xsw.writeAttribute("isActive", String.valueOf(this.isActive()));
      xsw.writeAttribute("isUsed", String.valueOf(this.isUsed()));
      xsw.writeAttribute("selector", this.selector != null ? this.selector : "");
      xsw.writeAttribute("clientID", this.clientId != null ? this.clientId : "");
      xsw.writeAttribute("noLocal", String.valueOf(this.noLocal));
      if (this.queue != null) {
         xsw.writeAttribute("queueName", this.queue.getName());
      }

      if (this.persistentHandle != null) {
         xsw.writeAttribute("persistentStoreHandle", this.persistentHandle.toString());
      }

      xsw.writeAttribute("isMulticast", String.valueOf(this.isMulticastSubscriber()));
      xsw.writeAttribute("supportsClientResponsible", String.valueOf(this.supportsClientResponsible));
      xsw.writeAttribute("unackedMessageCount", String.valueOf(this.unackedMessageCount));
      xsw.writeStartElement("Session");
      xsw.writeAttribute("sessionID", this.session.getId().toString());
      xsw.writeAttribute("connectionID", this.session.getConnection().getId().toString());
      xsw.writeEndElement();
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Consumer");
      this.dumpCommon(xsw);
      xsw.writeEndElement();
   }

   public CompositeData getCompositeData() throws OpenDataException {
      String addr = null;
      BESession sess = this.getSession();
      if (sess != null) {
         BEConnection conn = sess.getConnection();
         if (conn != null) {
            addr = conn.getAddress();
         }
      }

      ConsumerInfo info = new ConsumerInfo(this.getSubscriptionName(), this.isDurable(), this.getSelector(), this.getClientID(), this.getNoLocal(), addr);
      return info.toCompositeData();
   }

   private void setSubject(AuthenticatedSubject subject) {
      if (subject != null) {
         synchronized(this.stateLock) {
            this.authenticatedSubject = subject;
         }
      }

   }

   public AuthenticatedSubject getSubject() {
      synchronized(this.stateLock) {
         return this.authenticatedSubject;
      }
   }

   static {
      _WLDF$INST_FLD_JMS_Diagnostic_Volume_Before_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JMS_Diagnostic_Volume_Before_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BEConsumerImpl.java", "weblogic.jms.backend.BEConsumerImpl", "logEvent", "(I)V", 545, "", "", "", InstrumentationSupport.makeMap(new String[]{"JMS_Diagnostic_Volume_Before_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.JMSConsumerImplRenderer", false, true), (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("eventType", "weblogic.diagnostics.instrumentation.gathering.JMSEventTypeStringRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JMS_Diagnostic_Volume_Before_Medium};
      $assertionsDisabled = !BEConsumerImpl.class.desiredAssertionStatus();
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private class ConsumerCloseThread implements Runnable {
      private ConsumerCloseThread() {
      }

      public void run() {
         BEConsumerImpl.this.securityLapsed();
      }

      // $FF: synthetic method
      ConsumerCloseThread(Object x1) {
         this();
      }
   }

   private final class CountAdjuster implements Synchronization {
      private int count;
      private boolean incrementWindow;
      private boolean force;

      CountAdjuster(int count, boolean incrementWindow, boolean force) {
         this.count = count;
         this.incrementWindow = incrementWindow;
         this.force = force;
      }

      public void afterCompletion(int status) {
         BEConsumerImpl.this.adjustUnackedCount(-this.count);
         if (this.incrementWindow) {
            try {
               BEConsumerImpl.this.makeWindowSpace(this.count, this.force);
            } catch (JMSException var3) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("cannot make window space ", var3);
               }
            }
         }

      }

      public void beforeCompletion() {
      }
   }
}
