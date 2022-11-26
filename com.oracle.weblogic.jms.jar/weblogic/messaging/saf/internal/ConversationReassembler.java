package weblogic.messaging.saf.internal;

import java.io.Externalizable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.server.SequenceData;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFConversationNotAvailException;
import weblogic.messaging.saf.SAFEndpoint;
import weblogic.messaging.saf.SAFEndpointManager;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFTransport;
import weblogic.messaging.saf.SAFTransportType;
import weblogic.messaging.saf.SAFResult.Result;
import weblogic.messaging.saf.common.SAFConversationInfoImpl;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.store.SAFStore;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkAdapter;

public final class ConversationReassembler extends Conversation implements NakedTimerListener {
   private SAFEndpoint destination;
   private int windowSize;
   private final ReceivingAgentImpl receivingAgent;
   private final SAFTransport transport;
   private final QOSHandler qosHandler;
   private long ackInterval;
   private long lastAckTime;
   private final int origWindowSize;
   private int qos;
   private QOSWorkRequest workRequest;
   private final SAFEndpointManager safEndpointManager;
   private boolean endpointIsDown = true;
   private long idleTimeMaximum;
   private final TimerManager timerManager;
   private Timer timeOutTimer;
   private long timeoutCurrent = Long.MAX_VALUE;
   private boolean expired = false;
   private boolean closed;
   private long absTTL;
   private boolean establishSAConnection;
   private long lastMsgSequenceNumber = Long.MAX_VALUE;
   private Destination kernelDestination;
   private Sequence sequence;
   private Object operationLock = new Object();
   private boolean firstRun = true;

   ConversationReassembler(ReceivingAgentImpl receivingAgent, int transportType, SAFEndpoint destination, SAFConversationInfo info, SAFStore store, boolean establishSAConnection) throws SAFException {
      super(info, store, AgentImpl.getSAFManager());
      this.safEndpointManager = this.safManager.getEndpointManager(info.getDestinationType());
      this.windowSize = this.origWindowSize = receivingAgent.getWindowSize();
      this.transport = this.safManager.getTransport(transportType);
      this.receivingAgent = receivingAgent;
      this.destination = destination;
      this.qosHandler = QOSHandler.getQOSHandler(info, this.transport, (long)this.windowSize);
      this.initQOS(info);
      this.initAckInterval();
      TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
      this.timerManager = tmf.getTimerManager("SAFRECEIVER_" + receivingAgent.getName(), receivingAgent.getWorkManager());
      this.initTimeToLive();
      this.absTTL = ((SAFConversationInfoImpl)info).getTimestamp() + this.timeToLive;
      if (this.absTTL < 0L) {
         this.absTTL = Long.MAX_VALUE;
      }

      this.initIdleTimeMaximum();
      this.initTimeoutTimer();
      if (SAFTransportType.isConnectionless(info.getTransportType())) {
         this.establishSAConnection = true;
      } else {
         this.establishSAConnection = establishSAConnection;
      }

   }

   private synchronized void ensureStarted() throws SAFException {
      if (this.kernelDestination == null) {
         BEDestinationImpl beDest = JMSServerUtilities.findBEDestinationByJNDIName(JMSService.getJMSServiceWithSAFException(), this.destination.getTargetQueue());
         if (beDest == null) {
            throw new SAFException("SAF conversation " + this.getName() + " is not ready. BEDestination not found for JNDI name: " + this.destination.getTargetQueue());
         } else {
            this.kernelDestination = beDest.getKernelDestination();
            if (this.kernelDestination == null) {
               throw new SAFException("SAF conversation " + this.getName() + " is not ready. BEDestination at JNDI name " + this.destination.getTargetQueue() + " didn't have a kernel destination");
            } else {
               try {
                  synchronized(this.kernelDestination) {
                     this.sequence = this.kernelDestination.findSequence(this.getName());
                     if (this.sequence == null) {
                        this.sequence = this.kernelDestination.createSequence(this.getName(), 4);
                        this.sequence.setPassthru(true);
                     }
                  }

                  SequenceData data = (SequenceData)this.sequence.getUserData();
                  if (data != null) {
                     this.lastMsgSequenceNumber = ((SAFSequenceData)data).getLastMsgSequenceNumber();
                  } else {
                     this.sequence.setUserData(new SAFSequenceData(this.info));
                  }

                  this.qosHandler.setSequence(this.sequence);
               } catch (KernelException var5) {
                  throw new SAFException("Failed to setup conversation " + this.getName(), var5);
               }
            }
         }
      }
   }

   private void initTimeoutTimer() {
      long currentTime = System.currentTimeMillis();
      if (this.timeToLive != 0L) {
         if (this.timeToLive != Long.MAX_VALUE) {
            this.timeOutTimer = this.timerManager.schedule(this, this.timeToLive);
            this.timeoutCurrent = this.timeOutTimer.getTimeout();
         }

         this.info.setConversationTimeout(this.timeToLive);
         if (this.idleTimeMaximum != Long.MAX_VALUE) {
            this.rescheduleTimeoutTimer(currentTime, currentTime + this.idleTimeMaximum, false);
         }

      } else {
         this.expired = true;
         this.timeoutCurrent = currentTime;
         this.info.setConversationTimeout(0L);
      }
   }

   private void initIdleTimeMaximum() {
      long sendingIdleTimeMax = this.info.getMaximumIdleTime();
      if (sendingIdleTimeMax == 0L) {
         this.idleTimeMaximum = this.receivingAgent.getConversationIdleTimeMaximum();
      } else {
         this.idleTimeMaximum = sendingIdleTimeMax;
      }

      this.info.setMaximumIdleTime(this.idleTimeMaximum);
   }

   private void initTimeToLive() {
      long sendingTTL = this.info.getTimeToLive();
      if (sendingTTL == 0L) {
         this.timeToLive = Long.MAX_VALUE;
      } else {
         this.timeToLive = sendingTTL;
      }

      long rattl = this.receivingAgent.getDefaultTimeToLive();
      boolean override = (this.info.getTimeoutPolicy() & 1) != 0;
      boolean useAgentTimeout = (this.info.getTimeoutPolicy() & 2) != 0;
      long conversationTimeout = this.info.getConversationTimeout();
      if (useAgentTimeout) {
         conversationTimeout = rattl;
      }

      if (override) {
         this.timeToLive = conversationTimeout;
      } else if (this.timeToLive > conversationTimeout) {
         this.timeToLive = conversationTimeout;
      }

      this.info.setTimeToLive(this.timeToLive);
   }

   private void initQOS(SAFConversationInfo info) {
      switch (this.qos = info.getQOS()) {
         case 1:
            this.workRequest = new ExactlyOnceQOSWorkRequest();
            break;
         case 2:
            this.workRequest = new AtLeastOnceQOSWorkRequest();
            break;
         case 3:
            this.workRequest = new AtmostOnceQOSWorkRequest();
            break;
         default:
            throw new Error(" Unknown QOS. Contact BEA Support");
      }

   }

   private void initAckInterval() {
      this.ackInterval = this.receivingAgent.getAckInterval();
      if (this.qos == 1 && this.ackInterval == -1L) {
         this.ackInterval = Long.MAX_VALUE;
      }

   }

   private void rescheduleTimeoutTimer(long currentTime, long absoluteTime, boolean msgAdd) {
      if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Conversation '" + this.info.getConversationName() + "' == rescheduleTimeoutTimer(): old  = " + this.timeoutCurrent + " new = " + absoluteTime + " absTTL = " + this.absTTL);
      }

      synchronized(this) {
         if (absoluteTime < 0L && currentTime < this.absTTL) {
            absoluteTime = this.absTTL;
         }

         if ((msgAdd || absoluteTime < this.timeoutCurrent) && absoluteTime != Long.MAX_VALUE && absoluteTime >= 0L) {
            if (this.absTTL < absoluteTime) {
               absoluteTime = this.absTTL;
            }

            cancelTimer(this.timeOutTimer);
            this.timeOutTimer = this.timerManager.schedule(this, new Date(absoluteTime));
            this.timeoutCurrent = absoluteTime;
         }
      }
   }

   private static void cancelTimer(Timer timer) {
      if (timer != null) {
         timer.cancel();
      }

   }

   public void timerExpired(Timer timer) {
      if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Conversation '" + this.info.getConversationName() + "': timed out");
      }

      try {
         synchronized(this) {
            this.expired = true;
         }

         this.receivingAgent.removeConversation(this.info);
      } catch (SAFException var5) {
         if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFReceivingAgent.debug("Conversation '" + this.info.getConversationName() + "': failed to expire " + var5.getStackTrace());
         }
      }

   }

   public void addMessage(SAFRequest msg) throws SAFException {
      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Conversation '" + this.info.getConversationName() + "' == addMessage(): " + msg.getSequenceNumber());
      }

      this.ensureStarted();
      boolean needToSetUserData = false;
      long savedLastMsgSequenceNumber = -1L;
      synchronized(this) {
         if (msg.getSequenceNumber() > this.lastMsgSequenceNumber) {
            throw new SAFException("Cannot send more messages after a message that is marked as last message in the conversation/sequence", Result.SAFSEENLASTMESSAGE);
         }

         if (msg.isEndOfConversation() && !this.hasSeenLastMsg()) {
            this.lastMsgSequenceNumber = msg.getSequenceNumber();
            needToSetUserData = true;
            savedLastMsgSequenceNumber = this.lastMsgSequenceNumber;
         }
      }

      if (needToSetUserData) {
         SAFSequenceData data = (SAFSequenceData)this.sequence.getUserData();
         data.setLastMsgSequenceNumber(savedLastMsgSequenceNumber);

         try {
            this.sequence.setUserData(data);
         } catch (KernelException var9) {
            throw new SAFException(var9.getMessage(), var9);
         }

         this.setSeenLastMsg(true);
      }

      MessageReference ref = new MessageReference(msg);
      synchronized(this) {
         this.addMessageToListInorder(ref);
      }

      if (this.idleTimeMaximum != Long.MAX_VALUE) {
         long currentTime = System.currentTimeMillis();
         this.rescheduleTimeoutTimer(currentTime, currentTime + this.idleTimeMaximum, true);
      }

      this.scheduleWorkRequestRun();
   }

   long getLastMsgSequenceNumber() {
      return this.lastMsgSequenceNumber;
   }

   public int getWindowSize() {
      return this.windowSize;
   }

   public final void close() throws SAFException {
      this.close(false);
   }

   public final void close(boolean destroy) throws SAFException {
      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Conversation '" + this.info.getConversationName() + "' == close(): destroy = " + destroy);
      }

      synchronized(this.operationLock) {
         Sequence savedSequence;
         label52: {
            cancelTimer(this.timeOutTimer);
            this.safManager.notifyPreConversationClose(false, destroy, this.info);
            this.safManager.closeRAConversation(this.info);
            savedSequence = null;
            synchronized(this) {
               if (!this.closed) {
                  this.closed = true;
                  savedSequence = this.sequence;
                  if (this.running) {
                     this.running = false;
                  }

                  this.firstMessage = this.lastMessage = null;
                  this.establishSAConnection = false;
                  this.kernelDestination = null;
                  this.sequence = null;
                  this.timeOutTimer = null;
                  this.workRequest = null;
                  break label52;
               }
            }

            return;
         }

         if (destroy) {
            this.removeConversationInfo();

            try {
               if (savedSequence != null) {
                  savedSequence.delete(false);
               }
            } catch (Exception var7) {
               throw new SAFException(var7.toString(), var7);
            }
         }

      }
   }

   public void finishConversation() throws SAFException {
      this.close(true);
   }

   void removeConversationInfo() throws SAFException {
      this.store.removeConversationInfo(this.info);
   }

   public final long getLastAcked() throws SAFException {
      this.ensureStarted();
      return this.sequence == null ? 0L : this.sequence.getLastValue();
   }

   public final synchronized void setAgentConnectionEstablished() {
      this.establishSAConnection = true;
   }

   private boolean refreshDestination() {
      SAFEndpoint destination = this.safEndpointManager.getEndpoint(this.info.getDestinationURL());
      if (destination == null) {
         return this.endpointIsDown = true;
      } else {
         this.endpointIsDown = !destination.isAvailable();
         if (this.endpointIsDown) {
            this.safEndpointManager.removeEndpoint(this.info.getDestinationURL());
            return this.endpointIsDown;
         } else {
            this.safEndpointManager.addEndpoint(this.info.getDestinationURL(), destination);
            this.destination = destination;
            return this.endpointIsDown;
         }
      }
   }

   private void scheduleWorkRequestRun() {
      if (!this.isClosed() && !this.workRequest.isScheduled()) {
         boolean endpointWasDown;
         synchronized(this) {
            endpointWasDown = this.endpointIsDown && !this.firstRun;
            this.firstRun = false;
            if (this.isConversationNotRunnable()) {
               return;
            }

            this.running = true;
         }

         if (endpointWasDown) {
            synchronized(this.workRequest.scheduledMonitor) {
               if (this.workRequest.setScheduled()) {
                  this.timerManager.schedule(new DelayedScheduleTimerListener(), 10000L);
               }
            }
         } else {
            synchronized(this.workRequest.scheduledMonitor) {
               if (this.workRequest.setScheduled()) {
                  this.receivingAgent.getWorkManager().schedule(this.workRequest);
               }
            }
         }

      }
   }

   private boolean isConversationNotRunnable() {
      boolean notRunnable = !this.establishSAConnection || this.endpointIsDown && (this.endpointIsDown = this.refreshDestination()) || this.running || this.expired;
      return notRunnable;
   }

   public synchronized boolean isClosed() {
      return this.closed;
   }

   public List getAllSequenceNumberRanges() {
      if (this.sequence == null) {
         return new ArrayList();
      } else {
         List ret = new ArrayList();
         Iterator var2 = this.sequence.getAllSequenceNumberRanges().iterator();

         while(var2.hasNext()) {
            Object obj = var2.next();
            ret.add((Long)obj);
         }

         return ret;
      }
   }

   public String toString() {
      return this.info.toString();
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("ConversationReassembler");
      super.dumpAttributes(imageSource, xsw);
      xsw.writeAttribute("destination", this.destination.toString());
      xsw.writeAttribute("windowSize", String.valueOf(this.windowSize));
      xsw.writeAttribute("ackInterval", String.valueOf(this.ackInterval));
      xsw.writeAttribute("lastAckTime", String.valueOf(this.lastAckTime));
      xsw.writeAttribute("endpointIsDown", String.valueOf(this.endpointIsDown));
      ((SAFConversationInfoImpl)this.getInfo()).dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   private final class AtmostOnceQOSWorkRequest extends QOSWorkRequest {
      private AtmostOnceQOSWorkRequest() {
         super(null);
      }

      protected final boolean processRequestBeforeAck(MessageReference msgRef) {
         this.updateQOSHandler(msgRef, Result.SUCCESSFUL);
         return false;
      }

      protected final boolean processRequestsAfterAck(MessageReference startMsgRef, MessageReference currentMsgRef) {
         MessageReference msgRef = startMsgRef;
         boolean ret = false;

         do {
            if (ret = this.processRequest(msgRef, true)) {
               return ret;
            }
         } while(msgRef != currentMsgRef && (msgRef = msgRef.getNext()) != null);

         return false;
      }

      // $FF: synthetic method
      AtmostOnceQOSWorkRequest(Object x1) {
         this();
      }
   }

   private final class AtLeastOnceQOSWorkRequest extends QOSWorkRequest {
      private AtLeastOnceQOSWorkRequest() {
         super(null);
      }

      // $FF: synthetic method
      AtLeastOnceQOSWorkRequest(Object x1) {
         this();
      }
   }

   private final class ExactlyOnceQOSWorkRequest extends QOSWorkRequest {
      private ExactlyOnceQOSWorkRequest() {
         super(null);
      }

      // $FF: synthetic method
      ExactlyOnceQOSWorkRequest(Object x1) {
         this();
      }
   }

   private class QOSWorkRequest extends WorkAdapter {
      private final Object scheduledMonitor;
      private boolean scheduled;

      private QOSWorkRequest() {
         this.scheduledMonitor = "ScheduledMonitor";
      }

      protected boolean setScheduled() {
         synchronized(this.scheduledMonitor) {
            if (this.scheduled) {
               return false;
            } else {
               this.scheduled = true;
               return true;
            }
         }
      }

      protected boolean isScheduled() {
         synchronized(this.scheduledMonitor) {
            return this.scheduled;
         }
      }

      public final void run() {
         MessageReference msgRef = null;
         MessageReference startMsgRef = null;
         MessageReference lastMRef = null;
         boolean endpointIsDownl = false;

         while(true) {
            boolean var42 = false;

            boolean keepRunning;
            label533: {
               boolean shouldRollback;
               label498: {
                  label499: {
                     try {
                        var42 = true;
                        synchronized(ConversationReassembler.this) {
                           if (!ConversationReassembler.this.running) {
                              var42 = false;
                              break label533;
                           }

                           ConversationReassembler.this.endpointIsDown = endpointIsDownl;
                           if (ConversationReassembler.this.firstMessage == null || ConversationReassembler.this.endpointIsDown || ConversationReassembler.this.windowSize < 1 || ConversationReassembler.this.poisoned) {
                              this.stopRunning();
                              var42 = false;
                              break label498;
                           }

                           msgRef = this.getOrderedListWithNoGap(ConversationReassembler.this.firstMessage);
                        }

                        shouldRollback = false;
                        this.preProcessSAFRequests(msgRef);
                        startMsgRef = msgRef;
                        int count = 0;

                        do {
                           ConversationReassembler.this.qosHandler.setCurrentSAFRequest(msgRef.getMessage());
                           if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
                              this.debugProcessingSplitList(msgRef);
                           }

                           keepRunning = this.checkIfSequenceNumberBiggerThanLastMsgOfConv(msgRef);
                           if (keepRunning) {
                              this.handleInvalidMessage(new SAFException("Cannot send more messages after a message that is marked as last message in the conversation/sequence", Result.SAFSEENLASTMESSAGE), msgRef);
                              synchronized(ConversationReassembler.this) {
                                 msgRef = msgRef.getNext();
                                 ConversationReassembler.this.removeMessageFromList(msgRef);
                                 ConversationReassembler.this.windowSize--;
                              }

                              ++count;
                              break;
                           }

                           if (shouldRollback = this.processRequestBeforeAck(msgRef)) {
                              synchronized(ConversationReassembler.this) {
                                 ConversationReassembler.this.endpointIsDown = true;
                                 break;
                              }
                           }

                           synchronized(ConversationReassembler.this) {
                              ConversationReassembler.this.windowSize--;
                           }

                           ++count;
                           lastMRef = msgRef;
                           msgRef = msgRef.getNext();
                        } while(!this.mustAck() && msgRef != null);

                        if (lastMRef == null) {
                           this.postProcessSAFRequests(count, msgRef, shouldRollback);
                        } else {
                           this.postProcessSAFRequests(count, lastMRef.getNext(), shouldRollback);
                        }

                        endpointIsDownl = this.processRequestsAfterAck(startMsgRef, lastMRef);
                        continue;
                     } catch (SAFException var57) {
                        if (lastMRef == null) {
                           this.handleSAFException(var57, msgRef);
                           var42 = false;
                        } else {
                           this.handleSAFException(var57, lastMRef);
                           var42 = false;
                        }
                     } catch (Throwable var58) {
                        var58.printStackTrace();
                        var42 = false;
                        break label499;
                     } finally {
                        if (var42) {
                           boolean keepRunningx = false;
                           synchronized(ConversationReassembler.this) {
                              ConversationReassembler.this.running = false;
                              if (ConversationReassembler.this.firstMessage != null) {
                                 keepRunningx = true;
                              }
                           }

                           synchronized(this.scheduledMonitor) {
                              this.scheduled = false;
                              if (keepRunningx) {
                                 ConversationReassembler.this.scheduleWorkRequestRun();
                              }

                           }
                        }
                     }

                     shouldRollback = false;
                     synchronized(ConversationReassembler.this) {
                        ConversationReassembler.this.running = false;
                        if (ConversationReassembler.this.firstMessage != null) {
                           shouldRollback = true;
                        }
                     }

                     synchronized(this.scheduledMonitor) {
                        this.scheduled = false;
                        if (shouldRollback) {
                           ConversationReassembler.this.scheduleWorkRequestRun();
                        }

                        return;
                     }
                  }

                  shouldRollback = false;
                  synchronized(ConversationReassembler.this) {
                     ConversationReassembler.this.running = false;
                     if (ConversationReassembler.this.firstMessage != null) {
                        shouldRollback = true;
                     }
                  }

                  synchronized(this.scheduledMonitor) {
                     this.scheduled = false;
                     if (shouldRollback) {
                        ConversationReassembler.this.scheduleWorkRequestRun();
                     }

                     return;
                  }
               }

               shouldRollback = false;
               synchronized(ConversationReassembler.this) {
                  ConversationReassembler.this.running = false;
                  if (ConversationReassembler.this.firstMessage != null) {
                     shouldRollback = true;
                  }
               }

               synchronized(this.scheduledMonitor) {
                  this.scheduled = false;
                  if (shouldRollback) {
                     ConversationReassembler.this.scheduleWorkRequestRun();
                  }
               }

               return;
            }

            keepRunning = false;
            synchronized(ConversationReassembler.this) {
               ConversationReassembler.this.running = false;
               if (ConversationReassembler.this.firstMessage != null) {
                  keepRunning = true;
               }
            }

            synchronized(this.scheduledMonitor) {
               this.scheduled = false;
               if (keepRunning) {
                  ConversationReassembler.this.scheduleWorkRequestRun();
               }

               return;
            }
         }
      }

      private void stopRunning() throws SAFException {
         ConversationReassembler.this.running = false;
         if (ConversationReassembler.this.windowSize < 1) {
            throw new SAFException(" Sending Side not honoring the windowSize");
         } else if (ConversationReassembler.this.poisoned) {
            throw new SAFException("Conversation poisoned for Conversation = " + ConversationReassembler.this.info);
         }
      }

      private void handleInvalidMessage(SAFException se, MessageReference msgRef) {
         SAFResult.Result resultCode = se.getResultCode();
         ConversationReassembler.this.qosHandler.setSAFException(se);
         ConversationReassembler.this.qosHandler.update(msgRef, resultCode);
         ConversationReassembler.this.qosHandler.sendNack();
      }

      private void handleSAFException(SAFException se, MessageReference msgRef) {
         SAFResult.Result resultCode = se.getResultCode();
         ConversationReassembler.this.qosHandler.setSAFException(se);
         ConversationReassembler.this.qosHandler.update(msgRef, resultCode);
         ConversationReassembler.this.qosHandler.sendNack();
         boolean poisonedl = Conversation.isPoisoned(resultCode);
         synchronized(ConversationReassembler.this) {
            ConversationReassembler.this.poisoned = poisonedl;
            if (!ConversationReassembler.this.poisoned && msgRef != null) {
               ConversationReassembler.this.restoreMessages(msgRef);
            }

            ConversationReassembler.this.running = false;
         }
      }

      private void sendAck(int count, MessageReference msgRef) {
         synchronized(ConversationReassembler.this) {
            ConversationReassembler.this.windowSize = ConversationReassembler.this.windowSize + count;
            if (msgRef != null) {
               ConversationReassembler.this.restoreMessages(msgRef);
            }
         }

         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
            SAFDebug.SAFReceivingAgent.debug("Conversation '" + ConversationReassembler.this.info.getConversationName() + "' == sendAck(): windowSize = " + ConversationReassembler.this.windowSize);
         }

         ConversationReassembler.this.qosHandler.sendAck();
      }

      private void debugProcessingSplitList(MessageReference msgRef) {
         SAFDebug.SAFReceivingAgent.debug("###################################################");
         SAFDebug.SAFReceivingAgent.debug("Conversation '" + ConversationReassembler.this.info.getConversationName() + "' == execute(): current sequence= " + msgRef.getSequenceNumber() + " first message = " + (ConversationReassembler.this.firstMessage == null ? -1L : ConversationReassembler.this.firstMessage.getSequenceNumber()));
         SAFDebug.SAFReceivingAgent.debug("###################################################");
      }

      private MessageReference getOrderedListWithNoGap(MessageReference firstMessagel) {
         MessageReference itr = firstMessagel;
         long currentSequenceNumber = 0L;

         long prevSequenceNumber;
         do {
            prevSequenceNumber = itr.getSequenceNumber();

            do {
               itr = itr.getNext();
               if (itr == null) {
                  break;
               }

               currentSequenceNumber = itr.getSequenceNumber();
            } while(prevSequenceNumber == currentSequenceNumber);
         } while(itr != null && currentSequenceNumber == prevSequenceNumber + 1L);

         ConversationReassembler.this.firstMessage = itr;
         if (itr != null) {
            itr.getPrev().setNext((MessageReference)null);
            itr.setPrev((MessageReference)null);
         } else {
            ConversationReassembler.this.lastMessage = itr;
         }

         return firstMessagel;
      }

      private boolean mustAck() {
         long currentTime = System.currentTimeMillis();
         if (currentTime - ConversationReassembler.this.lastAckTime < ConversationReassembler.this.ackInterval && ConversationReassembler.this.windowSize != ConversationReassembler.this.origWindowSize / 2) {
            return false;
         } else {
            ConversationReassembler.this.lastAckTime = currentTime;
            return true;
         }
      }

      protected void preProcessSAFRequests(MessageReference msgRef) {
         ConversationReassembler.this.qosHandler.preProcess(msgRef);
      }

      protected boolean postProcessSAFRequests(int count, MessageReference msgRef, boolean rollback) throws SAFException {
         if (!rollback) {
            this.sendAck(count, msgRef);
            return true;
         } else {
            SAFException se1 = new SAFException(" Failed to deliver message to the Endpoint for Conversation = " + ConversationReassembler.this.info, ConversationReassembler.this.qosHandler.getResult().getSAFException(), ConversationReassembler.this.qosHandler.getResult().getResultCode());
            throw se1;
         }
      }

      private boolean checkIfSequenceNumberBiggerThanLastMsgOfConv(MessageReference msgRef) {
         synchronized(ConversationReassembler.this) {
            if (msgRef != null && msgRef.getMessage() != null) {
               return msgRef.getMessage().getSequenceNumber() > ConversationReassembler.this.lastMsgSequenceNumber;
            } else {
               return false;
            }
         }
      }

      protected boolean processRequestBeforeAck(MessageReference msgRef) {
         return this.processRequest(msgRef, false);
      }

      protected boolean processRequestsAfterAck(MessageReference startMsgRef, MessageReference curentMsgReg) {
         synchronized(ConversationReassembler.this) {
            return ConversationReassembler.this.endpointIsDown;
         }
      }

      protected final boolean processRequest(MessageReference msgRef, boolean afterAck) {
         SAFResult.Result resultCode = Result.SUCCESSFUL;
         int acknowledgedCount = 0;
         SAFRequest request = msgRef.getMessage();
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
            SAFDebug.SAFReceivingAgent.debug("Conversation '" + ConversationReassembler.this.info.getConversationName() + "' == processRequest(): conversation: " + request.getConversationName() + " request: " + request.getSequenceNumber());
         }

         Externalizable data = request.getPayload();
         if (data == null) {
            if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
               SAFDebug.SAFReceivingAgent.debug("Conversation '" + ConversationReassembler.this.info.getConversationName() + "' == processRequest(): request: " + request.getSequenceNumber() + " is the close conversation request or gap message");
            }

            ++acknowledgedCount;
         } else {
            try {
               this.deliverToEndpoint(request);
               ++acknowledgedCount;
            } catch (SAFException var8) {
               resultCode = ConversationReassembler.this.qosHandler.handleEndpointDeliveryFailure(var8, request);
            }
         }

         resultCode = resultCode == Result.SUCCESSFUL ? this.checkNumMessagesAcked(acknowledgedCount, 1, request) : resultCode;
         if (!afterAck) {
            this.updateQOSHandler(msgRef, resultCode);
         }

         return resultCode != Result.SUCCESSFUL;
      }

      private void deliverToEndpoint(SAFRequest request) throws SAFException {
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
            SAFDebug.SAFReceivingAgent.debug("Conversation '" + ConversationReassembler.this.info.getConversationName() + "' == processRequest(): request: " + request.getSequenceNumber());
         }

         synchronized(ConversationReassembler.this.operationLock) {
            if (ConversationReassembler.this.isClosed()) {
               throw new SAFConversationNotAvailException("Failed to send a message: conversation " + request.getConversationName() + " has completed, expired or terminated, or has been destroyed administratively");
            }

            ConversationReassembler.this.destination.deliver(ConversationReassembler.this.info, request);
         }

         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
            SAFDebug.SAFReceivingAgent.debug("Conversation '" + ConversationReassembler.this.info.getConversationName() + "' == processRequest(): request: " + request.getSequenceNumber() + " has been delivered to the endpoint");
         }

      }

      protected final boolean updateQOSHandler(MessageReference mref, SAFResult.Result resultCode) {
         ConversationReassembler.this.qosHandler.update(mref, resultCode);
         return resultCode == Result.SUCCESSFUL;
      }

      private SAFResult.Result checkNumMessagesAcked(int acknowledgedCount, int numRequests, SAFRequest request) {
         if (acknowledgedCount != 0 && acknowledgedCount == numRequests) {
            return Result.SUCCESSFUL;
         } else {
            String errorString = " Number of Messages acknowledged is not equal to number of messages processed acknowlegdedCount = " + acknowledgedCount + " numRequests = " + numRequests;
            SAFException safe = new SAFException(errorString);
            return ConversationReassembler.this.qosHandler.handleEndpointDeliveryFailure(safe, request);
         }
      }

      // $FF: synthetic method
      QOSWorkRequest(Object x1) {
         this();
      }
   }

   public class DelayedScheduleTimerListener implements NakedTimerListener {
      public void timerExpired(Timer timer) {
         if (!ConversationReassembler.this.isClosed()) {
            ConversationReassembler.this.receivingAgent.getWorkManager().schedule(ConversationReassembler.this.workRequest);
         }
      }
   }
}
