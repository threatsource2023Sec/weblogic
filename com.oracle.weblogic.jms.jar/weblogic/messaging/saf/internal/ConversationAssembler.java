package weblogic.messaging.saf.internal;

import java.io.Externalizable;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.management.ManagementException;
import weblogic.messaging.common.SQLExpression;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationHandle;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFEndpointManager;
import weblogic.messaging.saf.SAFErrorAwareEndpointManager;
import weblogic.messaging.saf.SAFErrorAwareTransport;
import weblogic.messaging.saf.SAFErrorHandler;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFInvalidAcknowledgementsException;
import weblogic.messaging.saf.SAFLogger;
import weblogic.messaging.saf.SAFManager;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFTransport;
import weblogic.messaging.saf.SAFTransportException;
import weblogic.messaging.saf.SAFResult.Result;
import weblogic.messaging.saf.common.SAFConversationInfoImpl;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.common.SAFRemoteContext;
import weblogic.messaging.saf.common.SAFRequestImpl;
import weblogic.messaging.saf.store.SAFStore;
import weblogic.messaging.saf.store.SAFStoreException;
import weblogic.messaging.util.DeliveryList;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class ConversationAssembler extends Conversation implements NakedTimerListener, Runnable {
   private static final int STATE_INITIAL = 1;
   private static final int STATE_STARTING = 2;
   private static final int STATE_DESTROYED = 4;
   private static final int STATE_EXPIRED = 8;
   private static final int STATE_CREATING = 16;
   private static final int STATE_CREATED = 32;
   private static final int STATE_STARTED = 64;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private long lastAckedSequenceNumber;
   private TimerManager timerManager;
   private Timer messageRetryTimer;
   private Timer transportRetryTimer;
   private RetryController retryController;
   private double retryDelayMultiplier;
   private long retryDelayBase;
   private long retryDelayMaximum;
   private boolean loggingEnabled;
   private SendingAgentImpl sendingAgent;
   private int windowSize;
   private final SAFTransport transport;
   private Timer timeOutTimer;
   private long timeToLive;
   private long idleTimeMaximum;
   private long absTTL;
   private long timeoutCurrent = Long.MAX_VALUE;
   private ConversationRuntimeDelegate runtimeDelegate;
   private int state = 1;
   private boolean needNotify;
   private RemoteEndpointRuntimeDelegate remoteEndpoint;
   private Queue subQueue;
   private MessageReader reader;
   private TransportRetryTimerListener transportRetryListener;
   private MessageRetryTimerListener messageRetryListener;
   private long lastMsgSequenceNumber = Long.MAX_VALUE;
   private Throwable lastSendError;

   ConversationAssembler(SendingAgentImpl agent, SAFConversationInfo info, SAFStore store, boolean loggingEnabled, int windowSize) throws ManagementException, SAFException {
      super(info, store, SendingAgentImpl.getSAFManager());
      this.transport = this.safManager.getTransport(info.getTransportType());
      if (this.transport == null) {
         throw new SAFException("Internal Error: invalid transport type " + info.getTransportType());
      } else {
         this.timeToLive = info.getTimeToLive();
         if (this.timeToLive == -1L) {
            this.timeToLive = agent.getDefaultTimeToLive();
         }

         if (this.timeToLive == 0L) {
            this.timeToLive = Long.MAX_VALUE;
         }

         info.setTimeToLive(this.timeToLive);
         this.idleTimeMaximum = info.getMaximumIdleTime();
         if (this.idleTimeMaximum == -1L) {
            this.idleTimeMaximum = agent.getDefaultMaximumIdleTime();
         }

         if (this.idleTimeMaximum == 0L) {
            this.idleTimeMaximum = Long.MAX_VALUE;
         }

         info.setMaximumIdleTime(this.idleTimeMaximum);
         this.loggingEnabled = loggingEnabled;
         this.windowSize = windowSize;
         this.ordered = info.isInorder();
         this.sendingAgent = agent;
         TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
         this.timerManager = tmf.getTimerManager("SAFSENDER_" + this.sendingAgent.getName(), this.sendingAgent.getWorkManager());

         try {
            final SAFAgentAdmin parentFinal = this.sendingAgent.getAgentAdmin();
            this.runtimeDelegate = (ConversationRuntimeDelegate)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws ManagementException {
                  return new ConversationRuntimeDelegate(parentFinal, ConversationAssembler.this);
               }
            });
            this.runtimeDelegate.registerMe();
         } catch (PrivilegedActionException var10) {
            throw (ManagementException)var10.getException();
         }

         this.absTTL = ((SAFConversationInfoImpl)info).getTimestamp() + this.timeToLive;
         if (this.absTTL < 0L) {
            this.absTTL = Long.MAX_VALUE;
         }

         if (this.timeToLive != Long.MAX_VALUE) {
            long timeLeft = this.getTimeLeft();
            if (timeLeft <= 0L) {
               this.changeState(8);
            }

            this.timeOutTimer = this.timerManager.schedule(this, timeLeft == 0L ? 100L : timeLeft);
            this.timeoutCurrent = this.timeOutTimer.getTimeout();
         }

         SAFRemoteContext context = info.getRemoteContext();
         if (context != null && context.getRetryDelayBase() != -1L) {
            this.retryDelayBase = context.getRetryDelayBase();
         } else {
            this.retryDelayBase = this.sendingAgent.getDefaultRetryDelayBase();
         }

         if (context != null && context.getRetryDelayMaximum() != -1L) {
            this.retryDelayMaximum = context.getRetryDelayMaximum();
         } else {
            this.retryDelayMaximum = this.sendingAgent.getDefaultRetryDelayMaximum();
         }

         if (this.timeToLive != Long.MAX_VALUE && (double)this.retryDelayMaximum > (double)this.timeToLive * 0.5) {
            this.retryDelayMaximum = (long)((double)this.timeToLive * 0.5);
         }

         if (this.idleTimeMaximum != Long.MAX_VALUE && (double)this.retryDelayMaximum > (double)this.idleTimeMaximum * 0.5) {
            this.retryDelayMaximum = (long)((double)this.idleTimeMaximum * 0.5);
         }

         if (context != null && context.getRetryDelayMultiplier() != -1L) {
            this.retryDelayMultiplier = (double)context.getRetryDelayMultiplier();
         } else {
            this.retryDelayMultiplier = this.sendingAgent.getDefaultRetryDelayMultiplier();
         }

         this.retryController = new RetryController(this.retryDelayBase, this.retryDelayMaximum, this.retryDelayMultiplier);
         this.transportRetryListener = new TransportRetryTimerListener();
         this.messageRetryListener = new MessageRetryTimerListener();
         if (!this.isExpired() && this.idleTimeMaximum != Long.MAX_VALUE) {
            long currentTime = System.currentTimeMillis();
            this.rescheduleTimeoutTimer(currentTime, currentTime + this.idleTimeMaximum, true);
         }

         this.remoteEndpoint = this.sendingAgent.findOrCreateRemoteEndpointRuntime(info.getDestinationURL(), info.getDestinationType(), this.sendingAgent.getKernelTopic(info));
         this.remoteEndpoint.addConversation(this.runtimeDelegate);
      }
   }

   public long getLastAssignedSequenceValue() {
      if (this.isClosed()) {
         return 0L;
      } else {
         Sequence sequence = this.subQueue.findSequence(this.info.getConversationName());
         return sequence.getLastAssignedValue();
      }
   }

   public List getAllSequenceNumberRanges() {
      if (this.isClosed()) {
         return new ArrayList();
      } else {
         Sequence sequence = this.subQueue.findSequence(this.info.getConversationName());
         return sequence.getAllSequenceNumberRanges();
      }
   }

   long getLastMsgSequenceNumber() {
      return this.lastMsgSequenceNumber;
   }

   void setLastMsgSequenceNumber(long seqNum) {
      this.lastMsgSequenceNumber = seqNum;
   }

   void setupSubscriptionQueue() throws SAFException {
      if (!this.isClosed()) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': setting up subscription queue for conversation " + this.info.getConversationName());
         }

         Topic kernelTopic = this.sendingAgent.getKernelTopic(this.info);
         HashMap properties = new HashMap();
         properties.put("Durable", new Boolean(true));

         try {
            Kernel kernel = this.sendingAgent.getKernel();
            synchronized(kernel) {
               this.subQueue = kernel.findQueue(this.info.getConversationName());
               if (this.subQueue == null) {
                  this.subQueue = kernel.createQueue(this.info.getConversationName(), properties);
               }
            }

            this.subQueue.setProperty("DefaultAssignSequence", this.info.getConversationName());
            this.subQueue.setProperty("Quota", kernelTopic.getQuota());
            KernelRequest krequest = new KernelRequest();
            kernelTopic.subscribe(this.subQueue, new SQLExpression("SAFConversationName='" + this.info.getConversationName() + "'"), krequest);
            krequest.getResult();
            this.reader = new MessageReader();
            this.subQueue.resume(16384);
            if (this.sendingAgent.isPausedForForwarding() || this.remoteEndpoint.isPausedForForwarding()) {
               this.subQueue.suspend(2);
            }

            if (this.sendingAgent.isPausedForIncoming() || this.remoteEndpoint.isPausedForIncoming()) {
               this.subQueue.suspend(1);
            }

         } catch (KernelException var7) {
            throw new SAFException(var7);
         }
      }
   }

   private boolean isDynamic() {
      return this.info.isDynamic();
   }

   private void start() throws SAFException {
      if (this.state == 1 && this.info.isConversationAlreadyCreated()) {
         this.changeState(32);
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Recover CREATED state according to conversation info");
         }
      }

      Exception exception = null;
      if (!this.isClosed()) {
         synchronized(this) {
            if (this.isStarted()) {
               return;
            }

            if (this.state == 16) {
               return;
            }

            if (this.state == 2) {
               return;
            }

            if (!this.info.isDynamic() && !this.isCreated()) {
               if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFSendingAgent.debug("Marking non-dynamic conversation '" + this.info.getConversationName() + " CREATED (won't wait for a handle to be returned)");
               }

               this.changeState(32);
            }

            if (!this.isCreated()) {
               this.changeState(16);
            } else {
               this.changeState(2);
            }
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            if (!this.isCreated()) {
               SAFDebug.SAFSendingAgent.debug("Conversation '" + this.getName() + "' == start(): dyanmic conversation has not been created, creating ...");
            } else if (!this.isStarted()) {
               SAFDebug.SAFSendingAgent.debug("Conversation '" + this.getName() + "' == start(): starting the reader ...");
            }
         }

         boolean needToLog = false;

         try {
            if (!this.isCreated()) {
               Externalizable context = this.transport.createSecurityToken(this.info);
               if (context != null) {
                  Externalizable oldContext = null;
                  synchronized(this) {
                     oldContext = this.info.getContext();
                     this.info.setContext(context);
                  }

                  try {
                     this.sendingAgent.storeConversationInfo(this.info);
                  } catch (SAFException var10) {
                     synchronized(this) {
                        this.info.setContext(oldContext);
                     }

                     throw var10;
                  }
               }

               SAFConversationHandle handle = this.transport.createConversation(this.info);
               needToLog = this.processConversationHandle(handle);
            }

            if (this.isCreated() && !this.isStarted()) {
               this.changeState(2);
               if (this.reader != null) {
                  this.reader.start();
               }

               this.cancelTransportRetryTimer();
               this.retryController.reset();
               this.changeState(64);
            }
         } catch (Exception var12) {
            exception = var12;
            this.resetToLastGoodState(var12);
         }

         boolean needToReschedule = false;
         synchronized(this) {
            if (this.isCreated() && this.firstMessage != null && this.messageRetryTimer == null) {
               needToReschedule = true;
            }
         }

         if (needToReschedule) {
            this.rescheduleMessageRetryTimer();
         }

         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            if (this.isStarted()) {
               if (needToLog) {
                  SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': successfully started conversation " + this);
               }
            } else if (exception != null) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': failed to start conversation " + this.info.getConversationName() + " " + exception.toString() + ", will retry later");
               exception.printStackTrace();
            } else {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': waiting for create conversation callback for " + this.info.getConversationName());
            }
         }

         if (exception != null) {
            if (exception instanceof SAFException) {
               throw (SAFException)exception;
            } else {
               throw new SAFException("Failed to start conversation " + this.info.getConversationName(), exception);
            }
         }
      }
   }

   private synchronized void resetToLastGoodState(Throwable cause) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Resetting to last 'good' state for conversation '" + this.info.getConversationName());
         if (cause == null) {
            cause = new Exception("Debug resetToLastGoodState");
         }

         ((Throwable)cause).printStackTrace();
      }

      if (this.state != 2 && this.state != 64) {
         if (this.state == 16) {
            this.changeState(1);
         } else if (this.state != 32 && this.state != 1) {
            this.changeState(1);
         }
      } else {
         this.changeState(32);
      }

   }

   private synchronized void changeState(int newState) {
      this.logStateChange(this.state, newState);
      this.state = newState;
      boolean needToReverse = this.isCreated() && !this.info.isConversationAlreadyCreated() || !this.isCreated() && this.info.isConversationAlreadyCreated();
      if (needToReverse) {
         this.info.setConversationAlreadyCreated(!this.info.isConversationAlreadyCreated());

         try {
            this.sendingAgent.storeConversationInfo(this.info);
         } catch (SAFStoreException var4) {
            var4.printStackTrace();
         }
      }

   }

   private void logStateChange(int oldState, int newState) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         String oldStateStr = getStateStr(oldState);
         String newStateStr = getStateStr(newState);
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.getName() + "' setting state to " + newStateStr + " from state " + oldStateStr);
      }

   }

   private static String getStateStr(int state) {
      String ret;
      switch (state) {
         case 1:
            ret = "STATE_INITIAL";
            break;
         case 2:
            ret = "STATE_STARTING";
            break;
         case 4:
            ret = "STATE_DESTROYED";
            break;
         case 8:
            ret = "STATE_EXPIRED";
            break;
         case 16:
            ret = "STATE_CREATING";
            break;
         case 32:
            ret = "STATE_CREATED";
            break;
         case 64:
            ret = "STATE_STARTED";
            break;
         default:
            ret = "UNKNOWN";
      }

      return ret;
   }

   void startFromADiffThread() {
      if (!this.isStarted()) {
         this.transportRetryTimer = this.timerManager.schedule(this.transportRetryListener, 100L);
      }

   }

   private void processConversationTimeout(long currentTime, SAFConversationHandle handle) {
      long peerConversationTimeoutLeft = handle.getConversationTimeout();
      long absoluteConversationTimeout = Long.MAX_VALUE;
      if (peerConversationTimeoutLeft != Long.MAX_VALUE) {
         absoluteConversationTimeout = currentTime + peerConversationTimeoutLeft;
         if (absoluteConversationTimeout < 0L) {
            absoluteConversationTimeout = Long.MAX_VALUE;
         }
      }

      this.timeToLive = peerConversationTimeoutLeft;
      this.rescheduleTimeoutTimer(currentTime, absoluteConversationTimeout, true);
   }

   private void processConversationMaxIdleTime(long currentTime, SAFConversationHandle handle) {
      long conversationMaxIdleTimeLeft = handle.getConversationMaxIdleTime();
      long absoluteConvMaxIdleTime = Long.MAX_VALUE;
      if (this.idleTimeMaximum > conversationMaxIdleTimeLeft) {
         if (conversationMaxIdleTimeLeft != Long.MAX_VALUE) {
            absoluteConvMaxIdleTime = currentTime + conversationMaxIdleTimeLeft;
         }

         this.idleTimeMaximum = conversationMaxIdleTimeLeft;
      } else if (this.idleTimeMaximum != Long.MAX_VALUE) {
         absoluteConvMaxIdleTime = currentTime + this.idleTimeMaximum;
      }

      if (absoluteConvMaxIdleTime < 0L) {
         absoluteConvMaxIdleTime = Long.MAX_VALUE;
      }

      this.rescheduleTimeoutTimer(currentTime, absoluteConvMaxIdleTime, true);
   }

   void createAndRecordDynamicConversation(String conversationName, String dynamicConversationName) {
      if (this.sendingAgent != null) {
         this.sendingAgent.addDynamicName(conversationName, dynamicConversationName);
         this.safManager.recordDynamicName(conversationName, dynamicConversationName);
      }

   }

   long getTimeLeft() {
      long currentTime = System.currentTimeMillis();
      return this.getTimeLeft(currentTime, this.absTTL);
   }

   private long getTimeLeft(long currentTime, long absoluteTime) {
      if (absoluteTime != 0L) {
         long timeLeft = absoluteTime - currentTime;
         return timeLeft <= 0L ? 0L : timeLeft;
      } else {
         return -1L;
      }
   }

   private MessageReference addMessage(MessageElement msg) {
      if (this.isClosed()) {
         return null;
      } else {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': message reader is about to add message: " + ((SAFRequest)msg.getMessage()).getMessageId() + "' sequence number: " + msg.getSequenceNum() + " to the conversation " + ((SAFRequest)msg.getMessage()).getConversationName());
         }

         SAFRequest request = (SAFRequestImpl)msg.getMessage();
         request.setSequenceNumber(msg.getSequenceNum());
         MessageReference ref = new MessageReference(msg, this.retryDelayMultiplier, this.retryDelayBase, this.retryDelayMaximum);
         if (this.isNotAvail()) {
            this.rejectOneMessage((MessageReference)ref, (SAFResult.Result)Result.CONVERSATIONTERMINATED, (Throwable)null, true);
            return ref;
         } else {
            synchronized(this) {
               this.addMessageToList(ref);
            }

            if (this.idleTimeMaximum != 0L && this.idleTimeMaximum != Long.MAX_VALUE) {
               this.rescheduleTimeoutTimer(System.currentTimeMillis() + this.idleTimeMaximum);
            }

            if (request.isEndOfConversation()) {
               this.setSeenLastMsg(true);
               this.setLastMsgSequenceNumber(request.getSequenceNumber());
            }

            return ref;
         }
      }
   }

   private void removeMessage(MessageReference msgRef) {
      this.removeMessage(msgRef, true);
   }

   private void removeMessage(MessageReference msgRef, boolean acknowledge) {
      if (!this.isClosed()) {
         synchronized(this) {
            this.removeMessageFromList(msgRef);
            if (this.firstMessage == null) {
               this.cancelMessageRetryTimer();
            }
         }

         if (acknowledge) {
            try {
               ArrayList list = new ArrayList(1);
               list.add(msgRef.getElement());
               KernelRequest krequest = this.subQueue.acknowledge(list);
               if (krequest != null) {
                  krequest.getResult();
               }
            } catch (KernelException var5) {
            }
         }

         if (!this.isExpired() && this.idleTimeMaximum != 0L && this.idleTimeMaximum != Long.MAX_VALUE) {
            this.rescheduleTimeoutTimer(System.currentTimeMillis() + this.idleTimeMaximum);
         }

      }
   }

   private void rejectOneMessage(MessageReference msgRef, SAFResult.Result faultCode, Throwable error, boolean purge) {
      msgRef.setFaultCode(faultCode.getErrorCode());
      ArrayList errors = new ArrayList();
      if (error != null) {
         errors.add(error);
      }

      this.rejectOneMessage(msgRef.getMessage(), msgRef.getFaultCodes(), errors, purge);
   }

   private void rejectOneMessage(SAFRequest message, ArrayList faultCodes, ArrayList errors, boolean purge) {
      if (!this.isClosed()) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': rejecting message " + message.getSequenceNumber());
         }

         SAFEndpointManager endpointManager = this.safManager.getEndpointManager(this.info.getDestinationType());
         SAFErrorHandler errorHandler = this.info.getErrorHandler();
         if (errorHandler == null) {
            errorHandler = endpointManager.getErrorHandler(this.info.getDestinationURL());
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': Errorhandler =  " + errorHandler);
         }

         boolean serverLogOnly = false;
         if (errorHandler != null) {
            if (errorHandler.isAlwaysForward() && purge) {
               serverLogOnly = true;
            } else if (endpointManager instanceof SAFErrorAwareEndpointManager) {
               SAFErrorAwareEndpointManager eaEndpointManager = (SAFErrorAwareEndpointManager)endpointManager;
               eaEndpointManager.handleFailure(errorHandler, message, faultCodes, errors);
            } else {
               endpointManager.handleFailure(errorHandler, message, faultCodes);
            }
         } else {
            serverLogOnly = true;
         }

         if (serverLogOnly && this.loggingEnabled) {
            SAFLogger.logExpiredMessage("'" + message.getMessageId() + "'");
         }

         RemoteEndpointRuntimeDelegate remoteEndpoint = this.sendingAgent.getRemoteEndpoint(this.info.getDestinationURL());
         remoteEndpoint.increaseFailedMessagesCount();
         this.sendingAgent.increaseFailedMessagesCount();
      }
   }

   private synchronized boolean isNotAvail() {
      return this.state == 8 || this.state == 4 || this.isClosed();
   }

   public synchronized boolean isNotAvailAndClosed() {
      boolean ret = this.state == 8 || this.state == 4 || this.hasSeenLastMsg();
      return ret;
   }

   private synchronized boolean isExpired() {
      return this.state == 8;
   }

   final void close() {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' is to be closed");
      }

      this.closeInternal(false);
   }

   final synchronized void delete() {
      if (!this.isClosed()) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' is to be destroyed.");
         }

         boolean destroy = true;
         this.safManager.notifyPreConversationClose(true, destroy, this.info);

         try {
            this.closeInternal(destroy);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

         try {
            this.sendingAgent.removeConversation(this.getName(), true);
            if (this.info.getDynamicConversationName() != null) {
               this.sendingAgent.removeConversation(this.info.getDynamicConversationName(), false);
            }

            this.sendingAgent = null;
         } catch (Exception var3) {
            var3.printStackTrace();
         }

      }
   }

   private synchronized void closeInternal(boolean destroy) {
      if (!this.isClosed()) {
         this.cancelAllTimers();
         if (this.reader != null) {
            this.reader.stop();
         }

         this.reader = null;
         if (destroy) {
            try {
               if (this.isCreated() && this.needNotify) {
                  this.transport.terminateConversation(this.info);
               }
            } catch (SAFException var8) {
            }

            try {
               if (this.sendingAgent != null && this.subQueue != null) {
                  Topic kernelTopic = this.sendingAgent.getKernelTopic(this.info);
                  synchronized(this.subQueue) {
                     if (kernelTopic != null) {
                        kernelTopic.unsubscribe(this.subQueue, new KernelRequest());
                     }

                     this.subQueue.delete(new KernelRequest());
                  }
               }
            } catch (KernelException var7) {
               var7.printStackTrace();
            }

            this.subQueue = null;
            this.remoteEndpoint = null;
         }

         try {
            if (this.runtimeDelegate != null) {
               this.runtimeDelegate.unregister();
               this.runtimeDelegate = null;
            }
         } catch (ManagementException var5) {
         }

         if (this.running) {
            this.running = false;
         }

         this.firstMessage = this.lastMessage = null;
      }
   }

   private final void complete() {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==complete(): all acknowledgements are back, we cleanup the conversation and notify receiving side");
      }

      this.needNotify = true;
      this.cancelAllTimers();
      this.delete();
   }

   private synchronized void cancelAllTimers() {
      this.cancelTimeOutTimer();
      this.cancelTransportRetryTimer();
      this.cancelMessageRetryTimer();
   }

   private synchronized boolean isDestroyed() {
      return this.state == 4;
   }

   void expireAllMessages(SAFResult.Result faultCode, Throwable error) throws KernelException {
      if (!this.isClosed()) {
         this.cancelAllTimers();
         MessageReference thisMessage = null;
         synchronized(this) {
            thisMessage = this.firstMessage;
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': expiring all messages left in the conversation firstMessage = " + thisMessage);
         }

         while(thisMessage != null) {
            this.destroyOneMessage(thisMessage, faultCode, error);
            synchronized(this) {
               thisMessage = this.firstMessage;
            }
         }

         Cursor cursor = null;

         try {
            if (this.subQueue != null) {
               cursor = this.subQueue.createCursor(true, (Expression)null, -1);
            }
         } catch (KernelException var13) {
            if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               var13.printStackTrace();
            }

            throw var13;
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': expiring " + cursor.size() + " messages left in the queue");
         }

         if (cursor != null) {
            ArrayList faultCodes = new ArrayList();
            faultCodes.add(new Integer(faultCode.getErrorCode()));
            ArrayList errors = new ArrayList();
            if (error != null) {
               errors.add(error);
            }

            if (this.lastSendError != null) {
               errors.add(this.lastSendError);
            }

            MessageElement element;
            try {
               while((element = cursor.next()) != null) {
                  SAFRequest request = (SAFRequest)element.getMessage();
                  this.rejectOneMessage(request, faultCodes, errors, true);
                  KernelRequest krequest = this.subQueue.delete(element);
                  if (krequest != null) {
                     krequest.getResult();
                  }
               }
            } catch (KernelException var12) {
               throw var12;
            }
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': expired all messages");
         }

      }
   }

   public void destroy() {
      this.needNotify = true;
      this.destroyInternal();
   }

   private void destroyInternal() {
      synchronized(this) {
         if (!this.isExpired()) {
            this.changeState(4);
         }
      }

      this.destroyAllMessages();
      this.delete();
   }

   private void destroyAllMessages() {
      if (!this.isClosed()) {
         KernelRequest completion = new KernelRequest();

         try {
            this.subQueue.empty(completion);
            completion.getResult();
         } catch (KernelException var3) {
         }

      }
   }

   private void disconnected(Exception exception) {
      if (!this.isClosed()) {
         this.remoteEndpoint.disconnected(exception);
      }
   }

   private boolean processConversationHandle(SAFConversationHandle handle) throws SAFException {
      if (handle == null) {
         if (!this.info.isDynamic()) {
            this.changeState(32);
         }

         return false;
      } else {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Processing handle for conversation '" + this.info.getConversationName() + "' in state " + getStateStr(this.state));
         }

         long currentTime = System.currentTimeMillis();
         String conversationName = null;
         String dynamicConversationName = handle.getDynamicConversationName();
         boolean needToRecord = false;
         SAFConversationInfo offer = handle.getOffer();
         Externalizable context = handle.getConversationContext();
         synchronized(this) {
            if (this.info.getDynamicConversationName() != null) {
               if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFSendingAgent.debug("Handle already processed for conversation '" + this.info.getConversationName() + "' in state " + getStateStr(this.state) + ". Ignoring this handle");
               }

               return false;
            } else {
               this.info.setTimeToLive(this.timeToLive);
               this.info.setMaximumIdleTime(this.idleTimeMaximum);
               this.info.setConversationOffer(handle.getOffer());
               if (context != null) {
                  this.info.setContext(context);
               }

               conversationName = this.info.getConversationName();
               this.info.setDynamicConversationName(dynamicConversationName);
               if (dynamicConversationName != null && !dynamicConversationName.equals(this.info.getConversationName())) {
                  needToRecord = true;
               }

               try {
                  if (offer != null) {
                     this.safManager.registerConversationOnReceivingSide(offer, (SAFManager.ConversationNameRefinementCallback)null, (String)null);
                  }

                  this.sendingAgent.storeConversationInfo(this.info);
               } catch (SAFException var15) {
                  synchronized(this) {
                     this.info.setDynamicConversationName((String)null);
                  }

                  throw var15;
               }

               if (needToRecord) {
                  this.createAndRecordDynamicConversation(conversationName, dynamicConversationName);
               }

               this.processConversationTimeout(currentTime, handle);
               this.processConversationMaxIdleTime(currentTime, handle);
               this.changeState(32);
               return true;
            }
         }
      }
   }

   void onCreateConversationSucceed(SAFConversationHandle handle) {
      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': onCreateConversationSucceed " + this.info.getConversationName() + " processing async/dynamic handle");
      }

      synchronized(this) {
         if (this.isClosed()) {
            return;
         }

         if (this.state != 16) {
            throw new IllegalStateException("Ouch. We haven't started creating conversation '" + this.info.getConversationName() + "' but yet we just got an async conversation handle. Cannot proceed");
         }
      }

      this.cancelTransportRetryTimer();
      boolean needToStore = false;
      Exception exception = null;

      SAFException safe;
      try {
         this.processConversationHandle(handle);
      } catch (SAFException var12) {
         safe = var12;
         synchronized(this) {
            this.resetToLastGoodState(safe);
         }

         this.rescheduleTransportRetryTimer();
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': failed to start conversation " + this.info.getConversationName() + " " + var12.getMessage() + ", will retry later");
         }

         return;
      }

      try {
         if (this.isCreated() && !this.isStarted()) {
            this.changeState(2);
            if (this.reader != null) {
               this.reader.start();
            }

            this.changeState(64);
            this.retryController.reset();
         }
      } catch (SAFException var11) {
         safe = var11;
         synchronized(this) {
            this.resetToLastGoodState(safe);
         }
      }

      if (!this.isStarted()) {
         this.rescheduleTransportRetryTimer();
      }

      boolean needToReschedule = false;
      synchronized(this) {
         if (this.isCreated() && this.firstMessage != null && this.messageRetryTimer == null) {
            needToReschedule = true;
         }
      }

      if (needToReschedule) {
         this.rescheduleMessageRetryTimer();
      }

      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         if (this.isStarted()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "' onCreateConversationSucceeded(): successfully started conversation " + this.info + ", retryDelaybase = " + this.retryDelayBase + ", retryDelayMaximum = " + this.retryDelayMaximum + ", retryDelayMultiplier = " + this.retryDelayMultiplier);
         } else {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "' onCreateConversationSucceeded(): couldn't start conversation " + this.info);
         }
      }

   }

   private static void cancelTimer(Timer timer) {
      if (timer != null) {
         timer.cancel();
      }

   }

   private void cancelTimeOutTimer() {
      cancelTimer(this.timeOutTimer);
      this.timeOutTimer = null;
   }

   private void rescheduleTimeoutTimer(long absoluteTime) {
      this.rescheduleTimeoutTimer(System.currentTimeMillis(), absoluteTime, false);
   }

   private void rescheduleTimeoutTimer(long currentTime, long absoluteTime, boolean isConversationSetup) {
      synchronized(this) {
         if (absoluteTime < 0L && currentTime < this.absTTL) {
            absoluteTime = this.absTTL;
         }

         if (isConversationSetup && absoluteTime >= this.timeoutCurrent || absoluteTime == Long.MAX_VALUE || absoluteTime < 0L) {
            return;
         }

         if (this.absTTL < absoluteTime) {
            absoluteTime = this.absTTL;
         }

         cancelTimer(this.timeOutTimer);
         this.timeOutTimer = this.timerManager.schedule(this, new Date(absoluteTime));
         this.timeoutCurrent = absoluteTime;
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': reschedule timeout timer: old  = " + this.timeoutCurrent + " new = " + absoluteTime + " currentTime = " + currentTime + " isConversationSetup = " + isConversationSetup);
      }

   }

   private void cancelMessageRetryTimer() {
      cancelTimer(this.messageRetryTimer);
      this.messageRetryTimer = null;
   }

   private void rescheduleMessageRetryTimer() {
      long delay = -1L;
      synchronized(this) {
         if (this.firstMessage == null) {
            return;
         }

         this.cancelMessageRetryTimer();
         delay = this.firstMessage.getNextRetryDelay();
         this.messageRetryTimer = this.timerManager.schedule(this.messageRetryListener, delay);
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': reschedule message retry timer: delay = " + delay);
      }

   }

   private void rescheduleTransportRetryTimer() {
      this.rescheduleTransportRetryTimer(this.retryController.getNextRetry());
   }

   private void rescheduleTransportRetryTimer(long delay) {
      synchronized(this) {
         if (this.transportRetryTimer != null) {
            return;
         }

         if (this.isStarted()) {
            return;
         }

         this.transportRetryTimer = this.timerManager.schedule(this.transportRetryListener, delay);
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': reschedule transport retry timer: delay = " + delay);
      }

   }

   private synchronized void cancelTransportRetryTimer() {
      cancelTimer(this.transportRetryTimer);
      this.transportRetryTimer = null;
   }

   void acknowledge(long sequenceNumberLow, long sequenceNumberHigh) throws SAFException {
      if (!this.isClosed()) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': acknowledging  low=" + sequenceNumberLow + " high=" + sequenceNumberHigh);
         }

         this.safManager.notifyAckConversation(this.info, sequenceNumberLow, sequenceNumberHigh);
         int acknowledged = false;
         ArrayList ackList = new ArrayList();
         MessageReference currentMsg = null;
         MessageReference savedFirstMessage = null;
         synchronized(this) {
            currentMsg = this.firstMessage;
            savedFirstMessage = this.firstMessage;
         }

         MessageReference nextMessage;
         for(; currentMsg != null && currentMsg.getSequenceNumber() <= sequenceNumberHigh; currentMsg = nextMessage) {
            nextMessage = currentMsg.getNext();
            if (currentMsg.getSequenceNumber() >= sequenceNumberLow) {
               if (currentMsg.getElement() != null) {
                  ackList.add(currentMsg.getElement());
               }

               this.removeMessage(currentMsg, false);
               if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==acknowledge(): removed " + currentMsg.getSequenceNumber() + " from the conversation");
               }
            }
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==acknowledge(): number of messages to be acknowledged = " + ackList.size());
         }

         int acknowledged;
         if ((acknowledged = ackList.size()) > 0) {
            try {
               KernelRequest kernelRequest = this.subQueue != null ? this.subQueue.acknowledge(ackList) : null;
               if (kernelRequest != null) {
                  kernelRequest.getResult();
               }
            } catch (KernelException var21) {
               if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.sendingAgent.getName() + "': acknowledging to kernel: failed ");
                  var21.printStackTrace();
               }
            }
         }

         int debugWindowSize = false;
         boolean needToReschedule = false;
         int debugWindowSize;
         synchronized(this) {
            if (savedFirstMessage != this.firstMessage) {
               needToReschedule = true;
            }

            this.lastAckedSequenceNumber = sequenceNumberHigh;
            this.windowSize += acknowledged;
            debugWindowSize = this.windowSize;
         }

         if (this.reader != null) {
            this.reader.incrementWindow(acknowledged);
         }

         if (needToReschedule) {
            this.rescheduleMessageRetryTimer();
         }

         Sequence sequence = this.subQueue != null ? this.subQueue.findSequence(this.info.getConversationName()) : null;
         if (sequence != null) {
            long savedNextSequenceNumber = sequence.getLastAssignedValue() + 1L;
            long invalidLow = 0L;
            long invalidHigh = 0L;
            boolean invalid = false;
            if (sequenceNumberHigh >= savedNextSequenceNumber) {
               invalid = true;
               invalidHigh = sequenceNumberHigh;
               if (sequenceNumberLow >= savedNextSequenceNumber) {
                  invalidLow = sequenceNumberLow;
               } else {
                  invalidLow = savedNextSequenceNumber;
               }
            }

            if (invalid) {
               throw new SAFInvalidAcknowledgementsException("Conversation '" + this.getName() + " got acknowledgements for  messages that have not been sent " + invalidLow + ":" + invalidHigh);
            }
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': acknowledge(): windowSize = " + debugWindowSize);
         }

         this.checkCompleted();
      }
   }

   synchronized void checkCompleted() {
      if (!this.isClosed()) {
         boolean completed = false;
         if (this.lastAckedSequenceNumber == this.lastMsgSequenceNumber && this.isDone()) {
            completed = true;
         }

         if (completed) {
            this.sendingAgent.getWorkManager().schedule(new WorkAdapter() {
               public void run() {
                  ConversationAssembler.this.complete();
               }
            });
         }
      }
   }

   private int getAvailableSlots() {
      return this.windowSize;
   }

   private final boolean sendOneMessage(MessageReference msgRef) throws SAFException {
      if (!this.isCreated()) {
         throw new SAFException("Conversation Not started");
      } else if (this.isClosed()) {
         return false;
      } else {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==sendOneMessage(): sequence number =" + msgRef.getSequenceNumber());
         }

         if (msgRef.isExpired() && !this.expireOneMessage(msgRef, Result.EXPIRED, !this.transport.isGapsAllowed())) {
            return false;
         } else {
            if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==sendOneMessage(): sending message:" + msgRef.getSequenceNumber() + " to transport");
            }

            try {
               Externalizable context = this.transport.send(this.info, msgRef.getMessage());
               if (context != null) {
                  Externalizable oldContext = null;

                  try {
                     synchronized(this.info) {
                        oldContext = this.info.getContext();
                        this.info.setContext(context);
                     }

                     if (this.sendingAgent != null) {
                        this.sendingAgent.storeConversationInfo(this.info);
                     }
                  } catch (SAFException var9) {
                     synchronized(this.info) {
                        this.info.setContext(oldContext);
                     }
                  }
               }

               if (this.remoteEndpoint != null) {
                  this.remoteEndpoint.connected();
               }

               return true;
            } catch (SAFTransportException var10) {
               if (this.remoteEndpoint != null) {
                  this.remoteEndpoint.disconnected(var10);
               }

               throw var10;
            }
         }
      }
   }

   private void destroyOneMessage(MessageReference message, SAFResult.Result faultCode, Throwable error) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' has been destroyed either because timeout or destroyed by the administrator");
      }

      if (!message.hasBeenHandled()) {
         this.rejectOneMessage(message, faultCode, error, true);
      }

      this.removeMessage(message);
   }

   private boolean expireOneMessage(MessageReference message, SAFResult.Result faultCode, boolean replace) {
      if (this.info.getErrorHandler() != null && this.info.getErrorHandler().isAlwaysForward()) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==expireOneMessage(): " + message.getMessage().getSequenceNumber() + " has expired, but will still be forwarded.");
         }

         return true;
      } else {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': message " + message.getMessage().getSequenceNumber() + " has expired");
         }

         if (!message.hasBeenHandled()) {
            this.rejectOneMessage((MessageReference)message, (SAFResult.Result)faultCode, (Throwable)null, false);
         }

         boolean needToReschedule = false;
         if (!replace) {
            synchronized(this) {
               if (message == this.firstMessage) {
                  needToReschedule = true;
               }
            }

            this.removeMessage(message);
            if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': firstMessage = " + this.firstMessage + " lastMessage = " + this.lastMessage + " hasSeenLastMsg " + this.hasSeenLastMsg());
               if (this.firstMessage != null) {
                  SAFDebug.SAFSendingAgent.debug("first sequence number = " + this.firstMessage.getMessage().getSequenceNumber());
               }
            }

            boolean completed = false;
            synchronized(this) {
               if (this.hasSeenLastMsg() && this.firstMessage == this.lastMessage && this.firstMessage != null && this.firstMessage.getMessage().getPayload() == null) {
                  this.removeMessage(this.firstMessage);
                  completed = true;
                  needToReschedule = false;
               }
            }

            if (completed) {
               this.complete();
            }
         } else {
            synchronized(message) {
               SAFRequest request = message.getMessage();
               request.setPayload((Externalizable)null);
            }
         }

         if (needToReschedule) {
            this.rescheduleMessageRetryTimer();
         }

         return replace;
      }
   }

   ConversationRuntimeDelegate getRuntimeDelegate() {
      return this.runtimeDelegate;
   }

   public void timerExpired(Timer timer) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': timed out");
      }

      synchronized(this) {
         this.changeState(8);
         this.needNotify = false;
      }

      try {
         this.expireAllMessages(Result.CONVERSATIONTIMEOUT, (Throwable)null);
         this.destroyInternal();
      } catch (KernelException var4) {
      }

   }

   private void doRun() {
      if (!this.isClosed()) {
         synchronized(this) {
            if (this.running) {
               return;
            }

            this.running = true;
         }

         this.sendingAgent.getWorkManager().schedule(this);
      }
   }

   public final void run() {
      MessageReference thisMessage = null;
      synchronized(this) {
         if (this.firstMessage == null) {
            this.running = false;
            return;
         }

         thisMessage = this.firstMessage;
      }

      while(thisMessage != null && !this.isDestroyed()) {
         try {
            MessageReference buf = thisMessage.getNext();
            if (!this.sendOneMessage(thisMessage)) {
               synchronized(this) {
                  ++this.windowSize;
               }
            }

            if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "' ==retry(): successfully sent  message: " + thisMessage.getSequenceNumber() + " to the transport");
            }

            thisMessage = buf;
            this.lastSendError = null;
         } catch (SAFException var9) {
            this.disconnected(var9);
            this.handleExceptionFromSendingMessage(var9, thisMessage);
            break;
         }
      }

      boolean needToReschedule = false;
      synchronized(this) {
         if (this.firstMessage != null) {
            needToReschedule = true;
         }

         this.running = false;
      }

      if (needToReschedule) {
         this.rescheduleMessageRetryTimer();
      }

   }

   private void handleExceptionFromSendingMessage(Exception e, MessageReference thisMessage) {
      if (this.transport instanceof SAFErrorAwareTransport) {
         SAFErrorAwareTransport eaTransport = (SAFErrorAwareTransport)this.transport;
         if (eaTransport.isPermanentError(e)) {
            this.rejectOneMessage((MessageReference)thisMessage, (SAFResult.Result)Result.PERMANENTTRANSPORTERROR, (Throwable)e, true);
            this.removeMessage(thisMessage);
            return;
         }

         this.lastSendError = e;
      } else {
         this.lastSendError = e;
      }

   }

   private synchronized boolean isStarted() {
      return this.isCreated() && this.reader != null && this.reader.isStarted();
   }

   private synchronized boolean isCreated() {
      return this.state != 1 && this.state != 16;
   }

   private synchronized boolean isClosed() {
      return this.sendingAgent == null || this.runtimeDelegate == null || this.remoteEndpoint == null;
   }

   private void ensureStarted() {
      boolean retry = true;
      SAFException exception = null;

      try {
         this.start();
         this.lastSendError = null;
      } catch (SAFException var6) {
         retry = this.handleSAFExceptionFromStartingConversation(var6);
      }

      if (retry) {
         synchronized(this) {
            if (!this.isStarted()) {
               this.rescheduleTransportRetryTimer();
            } else {
               this.cancelTransportRetryTimer();
            }
         }
      }

   }

   private boolean handleSAFExceptionFromStartingConversation(Exception safe) {
      boolean retry = true;
      if (this.transport instanceof SAFErrorAwareTransport) {
         SAFErrorAwareTransport eaTransport = (SAFErrorAwareTransport)this.transport;
         if (eaTransport.isPermanentError(safe)) {
            retry = false;

            try {
               this.expireAllMessages(Result.PERMANENTTRANSPORTERROR, safe);
            } catch (KernelException var5) {
            }
         } else {
            this.lastSendError = safe;
         }
      } else {
         this.lastSendError = safe;
      }

      return retry;
   }

   public String toString() {
      return this.info + ", retryMultiplier = " + this.retryDelayMultiplier + ", retryDelayBase = " + this.retryDelayBase + ", retryDelayMaximum = " + this.retryDelayMaximum;
   }

   Queue getSubscriptionQueue() {
      return this.subQueue;
   }

   void resumeReader() throws KernelException {
      if (!this.isClosed()) {
         this.subQueue.resume(2);
      }
   }

   void pauseReader() throws KernelException {
      if (!this.isClosed()) {
         this.subQueue.suspend(2);
      }
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("ConversationAssembler");
      super.dumpAttributes(imageSource, xsw);
      xsw.writeAttribute("lastAckedSequenceNumber", String.valueOf(this.lastAckedSequenceNumber));
      xsw.writeAttribute("retryDelayMultiplier", String.valueOf(this.retryDelayMultiplier));
      xsw.writeAttribute("retryDelayBase", String.valueOf(this.retryDelayBase));
      xsw.writeAttribute("retryDelayMaximum", String.valueOf(this.retryDelayMaximum));
      xsw.writeAttribute("loggingEnabled", String.valueOf(this.loggingEnabled));
      xsw.writeAttribute("windowSize", String.valueOf(this.windowSize));
      xsw.writeAttribute("state", String.valueOf(this.state));
      xsw.writeAttribute("needNotify", String.valueOf(this.needNotify));
      ((SAFConversationInfoImpl)this.getInfo()).dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   void handleAsyncFault(String messageId, Exception ex) throws SAFException {
      if (!this.isClosed()) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + this.info.getConversationName() + "': handling fault related to message =" + messageId + " Exception=" + ex.getMessage());
         }

         if (messageId.equals("-1")) {
            this.handleSAFExceptionFromStartingConversation(ex);
         } else {
            MessageReference currentMsg = null;
            synchronized(this) {
               currentMsg = this.firstMessage;
            }

            while(currentMsg != null && !messageId.equals(currentMsg.getMessage().getMessageId())) {
               currentMsg = currentMsg.getNext();
            }

            if (currentMsg != null) {
               this.handleExceptionFromSendingMessage(ex, currentMsg);
            }
         }
      }
   }

   private final class MessageRetryTimerListener implements NakedTimerListener {
      private MessageRetryTimerListener() {
      }

      public void timerExpired(Timer timer) {
         synchronized(ConversationAssembler.this) {
            ConversationAssembler.this.messageRetryTimer = null;
            if (ConversationAssembler.this.isNotAvail()) {
               return;
            }
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + ConversationAssembler.this.info.getConversationName() + "': message retry timer expired");
         }

         ConversationAssembler.this.doRun();
      }

      // $FF: synthetic method
      MessageRetryTimerListener(Object x1) {
         this();
      }
   }

   private final class TransportRetryTimerListener implements NakedTimerListener {
      private TransportRetryTimerListener() {
      }

      public void timerExpired(Timer timer) {
         synchronized(ConversationAssembler.this) {
            ConversationAssembler.this.transportRetryTimer = null;
            if (ConversationAssembler.this.isNotAvail()) {
               return;
            }
         }

         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Conversation '" + ConversationAssembler.this.info.getConversationName() + "': transport retry timer expired");
         }

         ConversationAssembler.this.ensureStarted();
      }

      // $FF: synthetic method
      TransportRetryTimerListener(Object x1) {
         this();
      }
   }

   private final class MessageReader extends DeliveryList implements Listener {
      private ListenRequest queueConsumer;
      private final KernelRequest completion = new KernelRequest();

      MessageReader() {
         this.setWorkManager(ConversationAssembler.this.sendingAgent.getWorkManager());
         this.initDeliveryList(ConversationAssembler.this.windowSize, 25, 0, 0);
      }

      void start() throws SAFException {
         if (!ConversationAssembler.this.isClosed()) {
            synchronized(ConversationAssembler.this) {
               int count = ConversationAssembler.this.getAvailableSlots();
               if (this.queueConsumer == null && count != 0) {
                  try {
                     this.queueConsumer = ConversationAssembler.this.subQueue.listen((Expression)null, count, false, ConversationAssembler.this, this, ConversationAssembler.this.getConversationName(), WorkManagerFactory.getInstance().getSystem());
                  } catch (KernelException var5) {
                     throw new SAFException("Error creating consumer on kernel queue", var5);
                  }

               }
            }
         }
      }

      void incrementWindow(int count) {
         if (!ConversationAssembler.this.isClosed()) {
            if (count != 0) {
               boolean failed = false;
               synchronized(ConversationAssembler.this) {
                  try {
                     if (this.queueConsumer != null) {
                        this.queueConsumer.incrementCount(count);
                        if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                           SAFDebug.SAFSendingAgent.debug("Sending Agent '" + ConversationAssembler.this.sendingAgent.getName() + "': message reader for " + ConversationAssembler.this.subQueue.getName() + " has incremented its window by " + count);
                        }
                     }
                  } catch (KernelException var6) {
                     failed = true;
                     this.queueConsumer.stopAndWait();
                     this.queueConsumer = null;
                  }
               }

               if (failed) {
                  this.negativeAckAll(new ArrayList());
               }

            }
         }
      }

      boolean isStarted() {
         synchronized(ConversationAssembler.this) {
            return this.queueConsumer != null;
         }
      }

      protected void pushMessages(List messages) {
         Iterator i = messages.iterator();
         ArrayList negAckList = new ArrayList();
         boolean failed = !this.isStarted();

         while(i.hasNext()) {
            if (failed) {
               negAckList.add((MessageElement)i.next());
            }

            while(!failed && i.hasNext()) {
               MessageElement element = (MessageElement)i.next();
               MessageReference ref = null;
               boolean needToReschedule = false;

               try {
                  ref = ConversationAssembler.this.addMessage(element);
                  if (ConversationAssembler.this.sendOneMessage(ref)) {
                     synchronized(ConversationAssembler.this) {
                        if (ref == ConversationAssembler.this.firstMessage) {
                           needToReschedule = true;
                        }

                        ConversationAssembler.this.windowSize--;
                     }

                     if (needToReschedule) {
                        ConversationAssembler.this.rescheduleMessageRetryTimer();
                     }
                  }

                  ConversationAssembler.this.retryController.reset();
                  ConversationAssembler.this.lastSendError = null;
               } catch (SAFException var13) {
                  if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
                     SAFDebug.SAFSendingAgent.debug("Conversation '" + ConversationAssembler.this.getName() + "' failed to send message " + ((SAFRequest)element.getMessage()).getSequenceNumber() + " because of " + var13.getMessage());
                  }

                  synchronized(ConversationAssembler.this) {
                     failed = true;
                     if (this.queueConsumer != null) {
                        this.queueConsumer.stopAndWait();
                        this.queueConsumer = null;
                     }
                  }

                  if (ConversationAssembler.this.transport instanceof SAFErrorAwareTransport) {
                     SAFErrorAwareTransport eaTransport = (SAFErrorAwareTransport)ConversationAssembler.this.transport;
                     if (eaTransport.isPermanentError(var13)) {
                        ConversationAssembler.this.rejectOneMessage((MessageReference)ref, (SAFResult.Result)Result.PERMANENTTRANSPORTERROR, (Throwable)var13, true);
                        ConversationAssembler.this.removeMessage(ref, false);
                     } else {
                        ConversationAssembler.this.lastSendError = var13;
                        ConversationAssembler.this.removeMessage(ref, false);
                        negAckList.add(element);
                     }
                  } else {
                     ConversationAssembler.this.lastSendError = var13;
                  }
               }
            }
         }

         if (failed) {
            this.negativeAckAll(negAckList);
         }

      }

      private void negativeAckAll(ArrayList negAckList) {
         negAckList.addAll(this.getPendingMessages());
         if (negAckList.size() != 0) {
            this.negativeAck(negAckList, 0L, this.completion);
         }

         try {
            this.completion.getResult();
         } catch (KernelException var5) {
            if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Conversation '" + ConversationAssembler.this.info.getConversationName() + "': error NACKing kernel messages: " + var5, var5);
            }
         }

         synchronized(ConversationAssembler.this) {
            ConversationAssembler.this.resetToLastGoodState((Throwable)null);
         }

         ConversationAssembler.this.rescheduleTransportRetryTimer();
      }

      private void negativeAck(List list, long delay, KernelRequest completion) {
         if (!ConversationAssembler.this.isClosed()) {
            completion.reset();

            try {
               ConversationAssembler.this.subQueue.negativeAcknowledge(list, delay, completion);
               completion.getResult();
            } catch (KernelException var6) {
               var6.printStackTrace();
            }

         }
      }

      private void stop() {
         ArrayList negAckList = new ArrayList();
         synchronized(ConversationAssembler.this) {
            if (this.queueConsumer != null) {
               this.queueConsumer.stopAndWait();
               this.queueConsumer = null;
            }
         }

         negAckList.addAll(this.getPendingMessages());
         if (negAckList.size() != 0) {
            this.negativeAck(negAckList, 0L, this.completion);
         }

      }
   }
}
