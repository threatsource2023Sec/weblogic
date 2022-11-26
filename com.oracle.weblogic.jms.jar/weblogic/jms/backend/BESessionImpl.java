package weblogic.jms.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.DispatcherCompletionListener;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.InvalidSubscriptionSharingException;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.JMSSessionRecoverResponse;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.NonDurableSubscription;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.WLMessage;
import weblogic.jms.utils.Simple;
import weblogic.jms.utils.tracing.AggregationCounter;
import weblogic.jms.utils.tracing.MessageTimeStamp;
import weblogic.messaging.ID;
import weblogic.messaging.common.MessageIDImpl;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.GroupOwner;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.WLSPrincipals;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicInteger;
import weblogic.work.WorkManager;

public final class BESessionImpl extends BEDeliveryList implements BESession, Listener, NakedTimerListener {
   private final JMSID sessionId;
   private final JMSID sequencerId;
   private BEConnection connection;
   private final InvocableMonitor invocableMonitor;
   private String pushWorkManager;
   private final Set consumers = new HashSet();
   private final Map browsers = new HashMap();
   private final ArrayList pendingMessages = new ArrayList();
   private boolean stopped;
   private boolean closed;
   private boolean isPeerGone;
   private CloseWait closeInProgressLock;
   private final boolean transacted;
   private Object recoveryUnit;
   private final int acknowledgeMode;
   private AtomicInteger totalWindowSize = AtomicFactory.createAtomicInteger();
   private AtomicInteger pipelineGeneration = AtomicFactory.createAtomicInteger();
   private final byte clientVersion;
   private static final boolean PUSH_STATS = false;
   private final AtomicInteger messagesPushed;
   private final AtomicInteger pushRequests;
   private static final int PUSH_DELAY;
   private static final int MAX_TOTAL_DELAY;
   private static final int DEFAULT_PUSH_DELAY = 3;
   private AggregationCounter aggregationCounter;
   private boolean noAggregation = false;
   private String aDestination = null;
   private int throughputEmphasis = -1;
   private WorkManager workManager;
   private long lastSeqAcked = 0L;
   private JMSService jmsService;

   BESessionImpl(BEConnection connection, JMSService jmsService, JMSID sessionId, JMSID sequencerId, boolean transacted, boolean xaSession, int acknowledgeMode, byte clientVersion, String pushWorkManager) {
      super((BackEnd)null);
      this.sessionId = sessionId;
      this.jmsService = jmsService;
      this.sequencerId = sequencerId;
      this.transacted = transacted;
      this.acknowledgeMode = acknowledgeMode;
      this.connection = connection;
      this.pushWorkManager = pushWorkManager;
      this.stopped = connection.isStopped();
      this.clientVersion = clientVersion;
      if (clientVersion >= 5) {
         this.pipelineGeneration.set(15728640);
      }

      this.invocableMonitor = jmsService.getInvocableMonitor();
      this.messagesPushed = this.pushRequests = null;
      if (xaSession) {
         this.recoveryUnit = new XASessionOwner();
      } else {
         this.recoveryUnit = this;
      }

   }

   public JMSID getJMSID() {
      return this.sessionId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.connection.getDispatcherPartition4rmic();
   }

   public JMSID getSequencerId() {
      return this.sequencerId;
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public void setConnection(BEConnection connection) {
      this.connection = connection;
   }

   public BEConnection getConnection() {
      return this.connection;
   }

   int getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   String getPushWorkManager() {
      return this.pushWorkManager;
   }

   public int getPipelineGeneration() {
      return this.pipelineGeneration.get();
   }

   void adjustWindowSize(int change) {
      this.initDeliveryList(this.totalWindowSize.addAndGet(change), this.throughputEmphasis, PUSH_DELAY, MAX_TOTAL_DELAY);
   }

   Object getRecoveryUnit() {
      return this.recoveryUnit;
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.connection.getService(), "BESessionImpl");
      switch (request.getMethodId()) {
         case 8464:
            return this.createBrowser((BEBrowserCreateRequest)request);
         case 10256:
            return this.createConsumer((BEConsumerCreateRequest)request);
         case 13072:
            return this.acknowledge((BESessionAcknowledgeRequest)request);
         case 13328:
            this.close((BESessionCloseRequest)request);
            break;
         case 13840:
            return this.recover((BESessionRecoverRequest)request);
         case 14096:
            this.setRedeliveryDelay(((BESessionSetRedeliveryDelayRequest)request).getRedeliveryDelay());
            break;
         case 14352:
            this.start();
            break;
         default:
            throw new AssertionError("No such method " + request.getMethodId());
      }

      request.setResult(VoidResponse.THE_ONE);
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   private BEConsumerImpl createBEConsumer(BEConsumerCreateRequest request) throws JMSException {
      synchronized(this) {
         if (this.closedOrPeerGone()) {
            throw new JMSException("Session is closed");
         }
      }

      BEDestinationImpl destination = (BEDestinationImpl)this.connection.getService().getInvocableManagerDelegate().invocableFind(20, request.getDestinationId());
      if (destination == null) {
         throw new weblogic.jms.common.JMSException("Destination not found");
      } else {
         this.aDestination = destination.getName();
         this.throughputEmphasis = Math.max(this.throughputEmphasis, destination.getMessagingPerformancePreference());
         BackEnd be = destination.getBackEnd();
         if (be != null) {
            be.checkShutdownOrSuspendedNeedLock("create consumer");
            if (this.workManager == null) {
               this.workManager = be.getWorkManager();
            }
         }

         boolean isStopped;
         synchronized(this) {
            isStopped = this.stopped;
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Creating consumer to Destination " + destination.getName() + ": clientId = " + request.getClientId() + " subscriptionName=" + request.getName());
         }

         if (destination instanceof BETopicImpl) {
            if (request.isDurable()) {
               DurableSubscription durSub = ((BETopicImpl)destination).findDurableSubscriber(request.getClientId(), request.getName(), request.getSelector(), request.getNoLocal(), request.getFlag(), request.getClientIdPolicy(), request.getSubscriptionSharingPolicy());
               if (durSub != null) {
                  synchronized(durSub) {
                     if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                        JMSDebug.JMSBackEnd.debug("Found a durable subscriber sub.subscriptionPolicy = " + durSub.getSubscriptionSharingPolicy() + " request.subscriptionpolicy= " + request.getSubscriptionSharingPolicy());
                     }

                     if (request.getSubscriptionSharingPolicy() != durSub.getSubscriptionSharingPolicy() && durSub.getSubscribersCount() > 0) {
                        throw new InvalidSubscriptionSharingException("Cannot change the sharing policy on an active durable subscription");
                     }

                     BEConsumerImpl foundConsumer = durSub.getConsumer();
                     if (!WLSPrincipals.isKernelUsername(JMSSecurityHelper.getSimpleAuthenticatedName())) {
                        destination.getJMSDestinationSecurity().checkReceivePermission(JMSSecurityHelper.getCurrentSubject());
                        this.jmsService.registerSecurityParticipant(destination.getJMSDestinationSecurity().getJMSResourceForReceive(), foundConsumer);
                     }

                     if (foundConsumer.restore(request, this, !isStopped)) {
                        return foundConsumer;
                     }

                     if (request.getSubscriptionSharingPolicy() == 0) {
                        throw new InvalidSubscriptionSharingException("Durable Subscription " + request.getName() + " is in use and cannot be shared");
                     }

                     return ((BETopicImpl)destination).createConsumer(this, !isStopped, request, durSub);
                  }
               }

               if (request.getFlag() == 0) {
                  throw new weblogic.jms.common.JMSException("Subscription not found");
               }
            } else if (request.getClientId() != null || request.getName() != null) {
               NonDurableSubscription sub = ((BETopicImpl)destination).findNonDurableSubscriberJMS2(request.getClientId(), request.getName(), request.getSelector(), request.getNoLocal(), request.getClientIdPolicy(), request.getSubscriptionSharingPolicy());
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("createBEConsumer: found non durable " + sub + " for " + request.getClientId() + " " + request.getName() + " " + request.getSelector() + " " + request.getNoLocal() + " " + request.getClientIdPolicy() + " " + request.getSubscriptionSharingPolicy());
               }

               if (sub != null) {
                  if (sub.getSubscriptionSharingPolicy() != request.getSubscriptionSharingPolicy() && sub.getSubscribersCount() > 0) {
                     throw new InvalidSubscriptionSharingException("There is an active subscriber that uses a different subscription sharing policy");
                  }

                  if (sub.getSubscriptionSharingPolicy() != 0) {
                     return ((BETopicImpl)destination).createConsumer(this, !isStopped, request, sub);
                  }
               }
            }
         }

         try {
            return destination.createConsumer(this, !isStopped, request);
         } catch (ClassCastException var10) {
            throw new weblogic.jms.common.JMSException("Invalid destination type", var10);
         }
      }
   }

   private int createConsumer(BEConsumerCreateRequest request) throws JMSException {
      BEConsumerImpl beConsumer = this.createBEConsumer(request);
      this.registerConsumer(beConsumer);
      request.setResult(new BEConsumerCreateResponse(beConsumer.registerConsumerReconnectInfo(request.getConsumerReconnectInfo())));
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   private void registerConsumer(BEConsumerImpl newConsumer) throws JMSException {
      synchronized(this) {
         this.consumers.add(newConsumer);
      }

      this.connection.getService().getInvocableManagerDelegate().invocableAdd(17, newConsumer);
   }

   public void peerGone() throws JMSException {
      this.performDelayedClose();
   }

   private void performDelayedClose() throws JMSException {
      BEConsumerImpl delayedConsumer = null;
      ArrayList consumersCopy;
      ArrayList browsersCopy;
      Iterator iterator;
      BEConsumerImpl beConsumer;
      synchronized(this) {
         if (this.isPeerGone) {
            return;
         }

         this.isPeerGone = true;
         iterator = this.consumers.iterator();

         while(true) {
            if (iterator.hasNext()) {
               beConsumer = (BEConsumerImpl)iterator.next();
               long delay = beConsumer.getDelayServerClose();
               if (delay >= 1L) {
                  if (delayedConsumer == null || delay < delayedConsumer.getDelayServerClose()) {
                     delayedConsumer = beConsumer;
                  }
                  continue;
               }

               delayedConsumer = null;
            }

            if (delayedConsumer != null) {
               consumersCopy = new ArrayList(this.consumers);
               browsersCopy = new ArrayList(this.browsers.values());
               this.browsers.clear();
            } else {
               browsersCopy = null;
               consumersCopy = null;
            }
            break;
         }
      }

      if (delayedConsumer == null) {
         this.close();
      } else {
         this.consumersStop(consumersCopy);
         JMSException firstException = null;
         iterator = consumersCopy.iterator();

         while(true) {
            do {
               if (!iterator.hasNext()) {
                  this.browsersClose(browsersCopy);
                  delayedConsumer.getDestination().getBackEnd().getTimerManager().schedule(this, delayedConsumer.getDelayServerClose());
                  if (firstException != null) {
                     throw firstException;
                  }

                  return;
               }

               beConsumer = (BEConsumerImpl)iterator.next();
            } while(beConsumer.getDelayServerClose() >= 1L);

            try {
               beConsumer.close(0L);
            } catch (JMSException var10) {
               if (firstException == null) {
                  firstException = var10;
               }
            }
         }
      }
   }

   public void timerExpired(Timer timer) {
      synchronized(this) {
         if (this.closed) {
            return;
         }
      }

      try {
         this.close();
      } catch (JMSException var4) {
         JMSLogger.logJMSServerShutdownError(this.getConnection().getDispatcher().getId().getName(), var4.getMessage(), var4);
      }

   }

   long sequenceFromMsgId(MessageIDImpl messageId) {
      long lastSequenceNumber = Long.MAX_VALUE;
      synchronized(this) {
         Iterator iterator = this.pendingMessages.iterator();

         while(iterator.hasNext()) {
            MessageElement me = (MessageElement)iterator.next();
            if (messageId.differentiatedEquals(me.getMessage().getMessageID())) {
               lastSequenceNumber = me.getUserSequenceNum();
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("reconnect closing lastSequenceNumber " + lastSequenceNumber);
               }
               break;
            }

            if (JMSDebug.JMSBackEnd.isDebugEnabled() && !me.getMessage().getMessageID().getClass().equals(messageId.getClass())) {
               JMSDebug.JMSBackEnd.debug("comparing wrong classes me.id " + me.getMessage().getMessageID().getClass() + " against last " + messageId.getClass());
            }
         }

         return lastSequenceNumber;
      }
   }

   private void close(BESessionCloseRequest request) throws JMSException {
      try {
         this.jmsService.checkShutdown();
      } catch (JMSException var3) {
         return;
      }

      if (request.allowDelayClose()) {
         this.performDelayedClose();
      } else {
         this.close(request.getLastSequenceNumber(), false);
      }
   }

   public void close() throws JMSException {
      this.close(Long.MAX_VALUE, false);
   }

   void close(JMSMessageId msgId) throws JMSException {
      this.close(this.sequenceFromMsgId(msgId), true);
   }

   private void close(long lastSequenceNumber, boolean waitUntilClosed) throws JMSException {
      CloseWait localCloseInProgress = null;
      boolean closeInternalThread = false;

      try {
         ArrayList consumersCopy;
         ArrayList browsersCopy;
         synchronized(this) {
            if (this.closed) {
               if (waitUntilClosed) {
                  localCloseInProgress = this.closeInProgressLock;
               }

               return;
            }

            localCloseInProgress = this.closeInProgressLock = new CloseWait();
            closeInternalThread = this.closed = true;
            consumersCopy = new ArrayList(this.consumers);
            this.consumers.clear();
            browsersCopy = new ArrayList(this.browsers.values());
            this.browsers.clear();
         }

         this.closeInternal(consumersCopy, lastSequenceNumber, browsersCopy);
      } finally {
         if (closeInternalThread) {
            localCloseInProgress.complete();
            this.connection.sessionRemove(this.getJMSID());
         } else if (localCloseInProgress != null) {
            localCloseInProgress.waitUntilClosed();
         }

      }

   }

   private void closeInternal(ArrayList consumersCopy, long lastSequenceNumber, ArrayList browsersCopy) throws JMSException {
      this.consumersStop(consumersCopy);
      this.waitUntilIdle();
      this.recover(lastSequenceNumber, this.pipelineGeneration.get());
      JMSException lastException = null;
      Iterator i = consumersCopy.iterator();

      while(i.hasNext()) {
         try {
            ((BEConsumerImpl)i.next()).close(0L);
         } catch (JMSException var8) {
            lastException = var8;
         }
      }

      this.browsersClose(browsersCopy);
      if (lastException != null) {
         throw lastException;
      }
   }

   private void consumersStop(ArrayList consumersCopy) {
      Iterator iterator = consumersCopy.iterator();

      while(iterator.hasNext()) {
         ((BEConsumerImpl)iterator.next()).stop();
      }

   }

   private void browsersClose(ArrayList browsersCopy) {
      Iterator i = browsersCopy.iterator();

      while(i.hasNext()) {
         ((BEBrowserImpl)i.next()).close();
      }

   }

   private int createBrowser(BEBrowserCreateRequest request) throws JMSException {
      synchronized(this) {
         if (this.closedOrPeerGone()) {
            throw new JMSException("Session is closed");
         }
      }

      BEDestinationImpl destination = (BEDestinationImpl)this.connection.getService().getInvocableManagerDelegate().invocableFind(20, request.getDestinationId());
      destination.checkShutdownOrSuspended("create browser");
      BEBrowser browser = destination.createBrowser(this, request.getSelector());
      this.jmsService.registerSecurityParticipant(destination.getJMSDestinationSecurity().getJMSResourceForBrowse(), (BEBrowserImpl)browser);
      this.browserAdd(browser);
      request.setResult(new JMSBrowserCreateResponse(browser.getJMSID()));
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   private void browserAdd(BEBrowser browser) throws JMSException {
      this.connection.getService().getInvocableManagerDelegate().invocableAdd(18, browser);
      synchronized(this) {
         this.browsers.put(browser.getJMSID(), browser);
      }
   }

   public void browserRemove(JMSID browserId) {
      synchronized(this) {
         this.browsers.remove(browserId);
      }

      this.connection.getService().getInvocableManagerDelegate().invocableRemove(18, browserId);
   }

   public void start() throws JMSException {
      this.connection.checkShutdownOrSuspendedNeedLock("start session");
      ArrayList consumerCopy;
      synchronized(this) {
         if (this.closedOrPeerGone() || !this.stopped) {
            return;
         }

         consumerCopy = new ArrayList(this.consumers);
      }

      Iterator iterator = consumerCopy.iterator();

      while(iterator.hasNext()) {
         BEConsumerImpl consumer = (BEConsumerImpl)iterator.next();
         consumer.start();
      }

      synchronized(this) {
         this.stopped = false;
      }
   }

   public void stop() {
      ArrayList consumerCopy;
      synchronized(this) {
         if (this.stopped) {
            return;
         }

         this.stopped = true;
         consumerCopy = new ArrayList(this.consumers);
      }

      Iterator iterator = consumerCopy.iterator();

      while(iterator.hasNext()) {
         BEConsumerImpl consumer = (BEConsumerImpl)iterator.next();
         consumer.stop();
      }

   }

   private void setRedeliveryDelay(long redeliveryDelay) throws JMSException {
      ArrayList consumerCopy;
      synchronized(this) {
         if (this.closedOrPeerGone()) {
            throw new weblogic.jms.common.JMSException("Session is closed");
         }

         consumerCopy = new ArrayList(this.consumers);
      }

      Iterator iterator = consumerCopy.iterator();

      while(iterator.hasNext()) {
         BEConsumerImpl consumer = (BEConsumerImpl)iterator.next();
         consumer.setRedeliveryDelay(redeliveryDelay);
      }

   }

   private synchronized List removeBefore(long sequenceNum) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Removing pending messages through " + (sequenceNum == Long.MAX_VALUE ? "MAX" : "" + sequenceNum) + " sessionId " + this.sessionId + " sequenceId " + this.sequencerId + " connection " + this.connection);
      }

      ArrayList removedList = new ArrayList(this.pendingMessages.size());
      if (!this.pendingMessages.isEmpty()) {
         Collections.sort(this.pendingMessages, new UserSequenceNumComparator());
         Iterator i = this.pendingMessages.iterator();

         while(i.hasNext()) {
            MessageElement pendingMessage = (MessageElement)i.next();
            if (pendingMessage.getUserSequenceNum() > sequenceNum) {
               break;
            }

            i.remove();
            removedList.add(pendingMessage);
         }
      }

      return removedList;
   }

   private synchronized List removeAfter(long sequenceNum, boolean inclusive, BEConsumerImpl consumer) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Removing pending and outgoing messages after " + sequenceNum + ", inclusive=" + inclusive);
      }

      ArrayList removedList = new ArrayList(this.pendingMessages.size());
      if (!this.pendingMessages.isEmpty()) {
         Collections.sort(this.pendingMessages, new UserSequenceNumComparator());
         Iterator i = this.pendingMessages.iterator();

         while(i.hasNext()) {
            MessageElement pendingMessage = (MessageElement)i.next();
            long userseqnum = pendingMessage.getUserSequenceNum();
            boolean seqnumcmp = inclusive ? userseqnum >= sequenceNum : userseqnum > sequenceNum;
            if (seqnumcmp && (BEConsumerImpl)pendingMessage.getUserData() == consumer) {
               i.remove();
               removedList.add(pendingMessage);
            }
         }
      }

      return removedList;
   }

   private void acknowledgeStart(BESessionAcknowledgeRequest request) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Starting an acknowledge request for sequence number " + request.getLastSequenceNumber());
      }

      request.setWorkManager(this.workManager);
      request.setTransaction(TransactionHelper.getTransactionHelper().getTransaction());
      List pendingMessages = this.removeBefore(request.getLastSequenceNumber());
      if (pendingMessages.isEmpty()) {
         synchronized(this) {
            if (this.lastSeqAcked < request.getLastSequenceNumber()) {
               throw new JMSException("Cannot acknowledge messages: likely server is shutting down, consumer is closed, or client application is illegally multi-threading consumer sessions.");
            }

            request.setState(11001);
         }
      } else {
         request.setIterator(pendingMessages.listIterator());
         request.setState(11000);
      }

   }

   private static KernelRequest acknowledgeContinue(BESessionAcknowledgeRequest request) throws JMSException {
      int count = 0;
      Queue queue = null;
      BEConsumerImpl consumer = null;
      ArrayList ackList = new ArrayList();

      ListIterator iterator;
      MessageElement elt;
      for(iterator = request.getIterator(); iterator.hasNext(); queue = elt.getQueue()) {
         elt = (MessageElement)iterator.next();
         if (queue != null && queue != elt.getQueue()) {
            iterator.previous();
            break;
         }

         ackList.add(elt);
         if (consumer == elt.getUserData()) {
            ++count;
         } else {
            if (consumer != null) {
               if (!request.isTransactional()) {
                  consumer.incrementPendingCount(count, false);
               } else {
                  BESessionImpl session = consumer.getSession();
                  if (elt.getGroup() != null && (session == null || session.recoveryUnit == session || !consumer.hasListener())) {
                     throw new JMSException("Cannot change group recover owner.");
                  }

                  consumer.incrementPendingCountTransactionally(request.getTransaction(), count, false);
               }
            }

            consumer = (BEConsumerImpl)elt.getUserData();
            count = 1;
         }
      }

      if (ackList.isEmpty()) {
         request.setState(11001);
         return null;
      } else {
         if (consumer != null) {
            if (request.isTransactional()) {
               consumer.incrementPendingCountTransactionally(request.getTransaction(), count, false);
            } else {
               consumer.incrementPendingCount(count, false);
            }
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Acknowledging " + ackList.size() + " messages for " + queue.getName() + " session " + consumer.getSession());
         }

         KernelRequest kernelRequest = null;

         try {
            if (request.isTransactional()) {
               queue.associate(ackList, consumer);
            } else {
               kernelRequest = queue.acknowledge(ackList);
            }
         } catch (KernelException var8) {
            throw new weblogic.jms.common.JMSException(var8);
         }

         if (!iterator.hasNext()) {
            request.setState(11001);
         }

         return kernelRequest;
      }
   }

   private int acknowledge(BESessionAcknowledgeRequest request) throws JMSException {
      while(true) {
         switch (request.getState()) {
            case 0:
               this.acknowledgeStart(request);
               break;
            case 11000:
               if (request.getKernelRequest() != null) {
                  try {
                     request.getKernelRequest().getResult();
                     request.setKernelRequest((KernelRequest)null);
                  } catch (KernelException var9) {
                     throw new weblogic.jms.common.JMSException(var9);
                  }
               }

               KernelRequest kernelRequest = acknowledgeContinue(request);
               if (kernelRequest == null) {
                  break;
               }

               synchronized(kernelRequest) {
                  if (!kernelRequest.hasResult()) {
                     request.setKernelRequest(kernelRequest);
                     request.needOutsideResult();
                     kernelRequest.addListener(new DispatcherCompletionListener(request));
                     return request.getState();
                  }
                  break;
               }
            case 11001:
               if (request.getKernelRequest() != null) {
                  try {
                     request.getKernelRequest().getResult();
                     request.setKernelRequest((KernelRequest)null);
                  } catch (KernelException var7) {
                     throw new weblogic.jms.common.JMSException(var7);
                  }
               }

               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Acknowledgement complete");
               }

               synchronized(this) {
                  this.lastSeqAcked = request.getLastSequenceNumber();
               }

               request.setIterator((ListIterator)null);
               request.setResult(VoidResponse.THE_ONE);
               request.setState(Integer.MAX_VALUE);
               return Integer.MAX_VALUE;
            default:
               throw new AssertionError("Invalid request state");
         }
      }
   }

   private int recover(BESessionRecoverRequest request) throws JMSException {
      if (this.closed) {
         throw new weblogic.jms.common.JMSException("Session is closed");
      } else {
         this.jmsService.checkShutdown();
         if (request.getPipelineGeneration() == 0) {
            this.recover81(request);
         } else {
            this.recover90(request);
         }

         request.setResult(new JMSSessionRecoverResponse(request.getLastSequenceNumber()));
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      }
   }

   private boolean closedOrPeerGone() {
      return this.closed || this.isPeerGone;
   }

   private void recover81(BESessionRecoverRequest request) throws JMSException {
      this.recover(request.getLastSequenceNumber(), this.pipelineGeneration.get());
   }

   private void recover90(BESessionRecoverRequest request) throws JMSException {
      ArrayList consumersCopy;
      synchronized(this) {
         consumersCopy = new ArrayList(this.consumers);
      }

      Map resumeConsumers = new HashMap(consumersCopy.size());
      Iterator iterator = consumersCopy.iterator();

      BEConsumerImpl consumer;
      while(iterator.hasNext()) {
         consumer = (BEConsumerImpl)iterator.next();
         if (consumer.stopListening()) {
            resumeConsumers.put(consumer.getId(), consumer);
         }
      }

      this.waitUntilIdle();
      this.recover(request.getLastSequenceNumber(), request.getPipelineGeneration());
      iterator = consumersCopy.iterator();

      while(iterator.hasNext()) {
         consumer = (BEConsumerImpl)iterator.next();
         if (resumeConsumers.get(consumer.getId()) != null) {
            consumer.startListening();
         }
      }

   }

   private void replaceMessages(List pendingMessages, boolean incrementRedelivered) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Replacing " + pendingMessages.size() + " messages on the kernel queue," + (incrementRedelivered ? " incrementing" : " without incrementing"));
      }

      KernelRequest completion = new KernelRequest();
      Iterator iterator = pendingMessages.iterator();

      try {
         while(iterator.hasNext()) {
            MessageElement elt = (MessageElement)iterator.next();
            BEConsumerImpl consumer = (BEConsumerImpl)elt.getUserData();
            completion.reset();
            elt.getQueue().negativeAcknowledge(elt, consumer.getRedeliveryDelay(), incrementRedelivered, completion);
            completion.getResult();
            consumer.incrementPendingCount(1, true);
         }

      } catch (KernelException var7) {
         throw new weblogic.jms.common.JMSException(var7);
      }
   }

   private void recover(long recoverSequenceNumber, int newPipelineGeneration) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Got a recover request for sequence num " + recoverSequenceNumber);
      }

      List beforeRecoverSequenceNumber;
      List afterRecoverSequenceNumber;
      synchronized(this) {
         this.pipelineGeneration.set(newPipelineGeneration);
         beforeRecoverSequenceNumber = this.removeBefore(recoverSequenceNumber);
         if (this.pendingMessages.isEmpty()) {
            afterRecoverSequenceNumber = null;
         } else {
            afterRecoverSequenceNumber = this.removeBefore(Long.MAX_VALUE);
         }
      }

      this.replaceMessages(beforeRecoverSequenceNumber, true);
      if (afterRecoverSequenceNumber != null) {
         this.replaceMessages(afterRecoverSequenceNumber, false);
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         if (afterRecoverSequenceNumber == null) {
            JMSDebug.JMSBackEnd.debug("recovered " + beforeRecoverSequenceNumber.size() + " messages before " + recoverSequenceNumber);
         } else {
            JMSDebug.JMSBackEnd.debug("recovered " + beforeRecoverSequenceNumber.size() + " messages before " + recoverSequenceNumber + ", and " + afterRecoverSequenceNumber.size() + " afterwards");
         }
      }

   }

   synchronized void addPendingMessage(MessageElement elt, BEConsumerImpl consumer) {
      assert elt.getUserSequenceNum() > 0L;

      this.pendingMessages.add(elt);
   }

   void removeConsumer(BEConsumerImpl consumer, long sequenceNumber) throws JMSException {
      this.removeConsumer(consumer, sequenceNumber, false);
   }

   void removeConsumer(BEConsumerImpl consumer, long sequenceNumber, boolean inclusive) throws JMSException {
      this.waitUntilIdle();
      this.replaceMessages(this.removeAfter(sequenceNumber, inclusive, consumer), sequenceNumber == 0L);
      this.connection.getService().getInvocableManagerDelegate().invocableRemove(17, consumer.getJMSID());
      synchronized(this) {
         this.consumers.remove(consumer);
      }
   }

   void removeConsumerWithError(BEConsumerImpl consumer, long sequenceNumber, weblogic.jms.common.JMSException exception) throws JMSException {
      this.removeConsumer(consumer, sequenceNumber);
      if (this.connection != null) {
         JMSServerUtilities.anonDispatchNoReply(new JMSPushExceptionRequest(10, consumer.getJMSID(), exception), this.connection.getDispatcher());
      }

   }

   boolean allowsImplicitAcknowledge() {
      if (this.transacted) {
         return false;
      } else {
         return this.clientVersion >= 3;
      }
   }

   private void decorateMessageWithSequence(WLMessage message, String sequenceName, long sequenceNumber) {
      message.setSAFSeqNumber(sequenceNumber);
      message.setSAFSequenceName(sequenceName);
   }

   protected void pushMessages(List messages) {
      JMSPushRequest firstPushRequest = null;
      JMSPushRequest lastPushRequest = null;
      Iterator i = messages.iterator();

      while(true) {
         MessageElement elt;
         BEConsumerImpl consumer;
         boolean implicitAcknowledge;
         do {
            do {
               if (!i.hasNext()) {
                  if (firstPushRequest == null) {
                     return;
                  }

                  if (this.aDestination != null && this.aggregationCounter == null && !this.noAggregation) {
                     MessageTimeStamp.newAggregationCounter(this.aDestination + "-" + this.toString(), 51);
                  }

                  if (this.aggregationCounter != null) {
                     this.aggregationCounter.increment(messages.size());
                  } else {
                     this.noAggregation = true;
                  }

                  try {
                     JMSServerUtilities.anonDispatchNoReply(firstPushRequest, this.getConnection().getDispatcher(), true);
                  } catch (JMSException var12) {
                     JMSLogger.logErrorPushingMessage(var12.toString(), var12);
                  }

                  return;
               }

               elt = (MessageElement)i.next();
               Sequence sequence = elt.getSequence();
               if (sequence != null) {
                  this.decorateMessageWithSequence((WLMessage)elt.getMessage(), sequence.getName(), elt.getSequenceNum());
               }

               consumer = (BEConsumerImpl)elt.getUserData();
               boolean clientResponsible = consumer.allowsImplicitAcknowledge();
               implicitAcknowledge = clientResponsible || this.acknowledgeMode == 4;
               if (!implicitAcknowledge) {
                  this.addPendingMessage(elt, consumer);
                  consumer.adjustUnackedCount(1);
               }

               if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                  JMSDebug.JMSMessagePath.debug("BACKEND/BESession (id: " + this.sessionId + ") : BACKEND/BEConsumer (id: " + consumer.getClientID() + ", sub: " + consumer.getSubscriptionName() + ") : Pushing to the frontend, message " + ((MessageImpl)elt.getMessage()).getJMSMessageID());
               }

               JMSPushRequest pushRequest = new JMSPushRequest(13, this.sequencerId, (MessageImpl)elt.getMessage(), consumer.createPushEntry(elt, clientResponsible, implicitAcknowledge));
               if (firstPushRequest == null) {
                  lastPushRequest = pushRequest;
                  firstPushRequest = pushRequest;
               } else {
                  lastPushRequest.setNext(pushRequest);
                  lastPushRequest = pushRequest;
               }

               MessageTimeStamp.record(4, pushRequest.getMessage());
            } while(!implicitAcknowledge);
         } while(consumer.isKernelAutoAcknowledge());

         try {
            KernelRequest req = consumer.getKernelQueue().acknowledge(elt);
            if (req != null) {
               req.getResult();
            }
         } catch (KernelException var13) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Unexpected exception while implicitly acknowledging: " + var13, var13);
            }
         }
      }
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Session");
      xsw.writeAttribute("id", this.sessionId != null ? this.sessionId.toString() : "");
      xsw.writeAttribute("pendingMessagesCurrentCount", String.valueOf(this.pendingMessages.size()));
      xsw.writeAttribute("browsersCurrentCount", String.valueOf(this.browsers.size()));
      xsw.writeStartElement("Consumers");

      assert this.consumers instanceof HashSet;

      HashSet tempConsumers = (HashSet)((HashSet)this.consumers).clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempConsumers.size()));
      Iterator it = tempConsumers.iterator();

      while(it.hasNext()) {
         BEConsumerCommon consumer = (BEConsumerCommon)it.next();
         consumer.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other != null && other instanceof BESessionImpl) {
         BESessionImpl session = (BESessionImpl)other;
         return session.sessionId != null ? session.sessionId.equals(this.sessionId) : session.sessionId == this.sessionId;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.sessionId != null ? this.sessionId.hashCode() : 0;
   }

   static {
      String delayStr = Simple.getenv("weblogic.jms.PushDelay");
      int delay = 3;
      if (delayStr != null) {
         try {
            delay = Integer.parseInt(delayStr);
         } catch (NumberFormatException var6) {
         }
      }

      PUSH_DELAY = delay;
      int maxDelay = 0;
      if (PUSH_DELAY != 0) {
         String maxDelayStr = Simple.getenv("weblogic.jms.MaxTotalDelay");
         if (maxDelayStr != null) {
            try {
               maxDelay = Integer.parseInt(maxDelayStr);
            } catch (NumberFormatException var5) {
            }
         }
      }

      MAX_TOTAL_DELAY = maxDelay;
   }

   static final class UserSequenceNumComparator implements Comparator {
      private int compareLongs(long l1, long l2) {
         if (l1 < l2) {
            return -1;
         } else {
            return l1 > l2 ? 1 : 0;
         }
      }

      public int compare(MessageElement m1, MessageElement m2) {
         return this.compareLongs(m1.getUserSequenceNum(), m2.getUserSequenceNum());
      }

      public boolean equals(Object o) {
         return o instanceof UserSequenceNumComparator;
      }

      public int hashCode() {
         return 0;
      }
   }

   private static class CloseWait {
      boolean completed;
      boolean waiters;

      private CloseWait() {
      }

      private synchronized void complete() {
         this.completed = true;
         if (this.waiters) {
            this.notifyAll();
         }

      }

      private synchronized void waitUntilClosed() {
         if (!this.completed) {
            this.waiters = true;

            try {
               this.wait();
            } catch (InterruptedException var2) {
               throw new AssertionError(var2);
            }
         }
      }

      // $FF: synthetic method
      CloseWait(Object x0) {
         this();
      }
   }

   private class XASessionOwner implements GroupOwner {
      private XASessionOwner() {
      }

      public boolean exposeOnlyOneMessage() {
         return true;
      }

      // $FF: synthetic method
      XASessionOwner(Object x1) {
         this();
      }
   }
}
