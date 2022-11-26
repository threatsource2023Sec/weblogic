package weblogic.jms.client;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.locks.ReentrantLock;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.transaction.Transaction;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.common.AlreadyClosedException;
import weblogic.jms.common.BytesMessageImpl;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.HdrMessageImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.JMSWorkContextHelper;
import weblogic.jms.common.LostServerException;
import weblogic.jms.common.MapMessageImpl;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.ProducerSendResponse;
import weblogic.jms.common.StreamMessageImpl;
import weblogic.jms.common.TextMessageImpl;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.extensions.JMSForwardHelper;
import weblogic.jms.frontend.FEProducerSendRequest;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.CompletionListener;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.transaction.TransactionHelper;

public final class JMSProducer implements ProducerInternal, Invocable, Reconnectable, Cloneable {
   private static int ONEWAYSENDCONSECUTIVEMESSAGELIMIT = 320000;
   private final JMSSession session;
   private volatile JMSID producerId;
   private volatile boolean closeInProgress;
   private final DestinationImpl destination;
   private int deliveryMode;
   private int priority;
   private long timeToDeliver = -1L;
   private long timeToLive;
   private int redeliveryLimit = -1;
   private long sendTimeout;
   private boolean messageIdsDisabled;
   private boolean messageTimestampsDisabled;
   private PeerInfo peerInfo;
   private final String runtimeMBeanName;
   private String unitOfOrderName;
   private String sequenceName;
   private int compressionThreshold = Integer.MAX_VALUE;
   private WLProducerImpl wlProducerImpl;
   private byte destinationFlags;
   private JMSProducer replacementProducer;
   private int oneWaySendMode;
   private int oneWayWindowSize;
   private static boolean ONEWAYSENDENABLED = false;
   private static int oneWaySendModeConf = 0;
   private static int oneWayWindowSizeConf = 1;
   private static final int MODE_SYNC_TRAN = 0;
   private static final int MODE_SYNC_NO_TRAN = 1;
   private static final int MODE_SYNC_NO_TRAN_WITH_ID = 2;
   private static final int MODE_NO_REPLY_WITH_ID = 3;
   private static final int MODE_ASYNC_TRAN = 4;
   private static final int MODE_ASYNC_NO_TRAN = 5;
   private static final int MODE_ASYNC_TRAN_WITH_ID = 6;
   private static final int MODE_ASYNC_NO_TRAN_WITH_ID = 7;
   private final Object asyncSendCountLock = new Object();
   private long wlAsyncSendCount = 0L;
   private long jmsAsyncSendCount = 0L;
   private ProducerFlowControl producerFlowControl;
   private static final boolean FOREIGNMSGTIMESTAMP_ENABLED = Boolean.valueOf(System.getProperty("weblogic.jms.foreignMessage.originalTimestamp", "false"));
   int count = 0;
   int totalConsecutiveOneWaySendMessageSize = 0;

   JMSProducer(JMSSession session, JMSID producerId, DestinationImpl destination, String runtimeMBeanName) {
      this.session = session;
      this.producerId = producerId;
      this.destination = destination;
      this.runtimeMBeanName = runtimeMBeanName;
      this.peerInfo = session.getConnection().getFEPeerInfo();
      this.deliveryMode = session.getDeliveryMode();
      this.priority = session.getPriority();
      this.timeToLive = session.getTimeToLive();
      this.sendTimeout = session.getSendTimeout();
      JMSConnection con = session.getConnection();
      this.producerFlowControl = new ProducerFlowControl(con);
      this.compressionThreshold = session.getConnection().getCompressionThreshold();
      this.oneWaySendMode = con.getOneWaySendMode();
      this.oneWayWindowSize = con.getOneWaySendWindowSize();
      if (this.oneWaySendMode != 0 && JMSEnvironment.getJMSEnvironment().isThinClient()) {
         this.oneWaySendMode = 0;
      }

   }

   public Object clone() throws CloneNotSupportedException {
      JMSProducer jmsProducer = (JMSProducer)super.clone();
      return jmsProducer;
   }

   public ReconnectController getReconnectController() {
      return this.wlProducerImpl;
   }

   public Reconnectable getReconnectState(int reconnectPolicy) throws CloneNotSupportedException {
      JMSProducer jmsProducer = (JMSProducer)this.clone();
      this.closeInProgress = true;
      return jmsProducer;
   }

   public Reconnectable preCreateReplacement(Reconnectable parent) throws JMSException {
      JMSProducer newProducer = ((JMSSession)parent).setupJMSProducer(this.destination, this.destinationFlags);
      if (this.unitOfOrderName == null && newProducer.getUnitOfOrder() != null || this.unitOfOrderName != null && !this.unitOfOrderName.equals(newProducer.getUnitOfOrder())) {
         newProducer.setUnitOfOrder(this.unitOfOrderName);
      }

      newProducer.sequenceName = this.sequenceName;
      newProducer.messageIdsDisabled = this.messageIdsDisabled;
      newProducer.messageTimestampsDisabled = this.messageTimestampsDisabled;
      newProducer.deliveryMode = this.deliveryMode;
      newProducer.priority = this.priority;
      newProducer.timeToDeliver = this.timeToDeliver;
      newProducer.timeToLive = this.timeToLive;
      newProducer.redeliveryLimit = this.redeliveryLimit;
      newProducer.sendTimeout = this.sendTimeout;
      this.replacementProducer = newProducer;
      return newProducer;
   }

   public void postCreateReplacement() {
      this.replacementProducer.setWlProducerImpl(this.wlProducerImpl);
      this.wlProducerImpl.setPhysicalReconnectable(this.replacementProducer);
   }

   public boolean isReconnectControllerClosed() {
      return this.wlProducerImpl == null || this.wlProducerImpl.isClosed();
   }

   public void forgetReconnectState() {
      this.replacementProducer = null;
   }

   public PeerInfo getFEPeerInfo() {
      return this.session.getFEPeerInfo();
   }

   public boolean isClosed() {
      return this.producerId == null || this.closeInProgress;
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

   public String getPartitionName() {
      return this.session.getConnection().getPartitionName();
   }

   public final void setCompressionThreshold(int compressionThreshold) throws JMSException {
      if (compressionThreshold < 0) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidCompressionThresholdLoggable().getMessage());
      } else {
         this.compressionThreshold = compressionThreshold;
      }
   }

   public final int getCompressionThreshold() {
      return this.compressionThreshold;
   }

   void setId(JMSID producerId) {
      this.producerId = producerId;
   }

   public JMSID getJMSID() {
      return this.producerId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.session.getDispatcherPartition4rmic();
   }

   public InvocableMonitor getInvocableMonitor() {
      return null;
   }

   public Destination getDestination() throws JMSException {
      this.checkClosed();
      return this.destination;
   }

   public void send(Destination destination, Message message) throws JMSException {
      this.sendWithListener((CompletionListener)null, destination, message);
   }

   private void sendWithListener(CompletionListener appListener, Destination destination, Message message) throws JMSException {
      this.sendWithListenerUnified((javax.jms.CompletionListener)null, appListener, destination, message);
   }

   private void sendWithListenerUnified(javax.jms.CompletionListener jmsAppListener, CompletionListener appListener, Destination destination, Message message) throws JMSException {
      if (destination == null) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestination2Loggable().getMessage());
      } else if (this.destination != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logCannotOverrideDestinationLoggable().getMessage());
      } else {
         this.sendInternal(destination, message, this.deliveryMode, this.priority, this.timeToLive, jmsAppListener, appListener);
      }
   }

   public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.sendWithListener((CompletionListener)null, destination, message, deliveryMode, priority, timeToLive);
   }

   private void sendWithListener(CompletionListener appListener, Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.sendWithListenerUnified((javax.jms.CompletionListener)null, appListener, destination, message, deliveryMode, priority, timeToLive);
   }

   private void sendWithListenerUnified(javax.jms.CompletionListener jmsAppListener, CompletionListener appListener, Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (destination == null) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestination2Loggable().getMessage());
      } else if (this.destination != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logCannotOverrideDestination2Loggable().getMessage());
      } else if (priority >= 0 && priority <= 9) {
         if (deliveryMode != 2 && deliveryMode != 1) {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidDeliveryModeSendLoggable(deliveryMode));
         } else {
            this.sendInternal(destination, message, deliveryMode, priority, timeToLive, jmsAppListener, appListener);
         }
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidPrioritySendLoggable(priority));
      }
   }

   public void send(Message message) throws JMSException {
      this.sendWithListener((CompletionListener)null, message);
   }

   private void sendWithListener(CompletionListener appListener, Message message) throws JMSException {
      this.sendWithListenerUnified((javax.jms.CompletionListener)null, appListener, message);
   }

   private void sendWithListenerUnified(javax.jms.CompletionListener jmsAppListener, CompletionListener appListener, Message message) throws JMSException {
      if (this.destination == null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logNeedDestinationLoggable().getMessage());
      } else {
         this.sendInternal(this.destination, message, this.deliveryMode, this.priority, this.timeToLive, jmsAppListener, appListener);
      }
   }

   public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.sendWithListener((CompletionListener)null, message, deliveryMode, priority, timeToLive);
   }

   private void sendWithListener(CompletionListener appListener, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.sendWithListenerUnified((javax.jms.CompletionListener)null, appListener, message, deliveryMode, priority, timeToLive);
   }

   private void sendWithListenerUnified(javax.jms.CompletionListener jmsAppListener, CompletionListener appListener, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.destination == null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logNeedDestination2Loggable().getMessage());
      } else if (priority >= 0 && priority <= 9) {
         if (deliveryMode != 2 && deliveryMode != 1) {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidDeliveryModeSendLoggable(deliveryMode));
         } else {
            this.sendInternal(this.destination, message, deliveryMode, priority, timeToLive, jmsAppListener, appListener);
         }
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidPrioritySendLoggable(priority));
      }
   }

   public void send(Queue queue, Message message) throws JMSException {
      this.send((Destination)queue, (Message)message);
   }

   public void send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.send((Destination)queue, message, deliveryMode, priority, timeToLive);
   }

   public Queue getQueue() throws JMSException {
      return (Queue)this.getDestination();
   }

   public Topic getTopic() throws JMSException {
      return (Topic)this.getDestination();
   }

   public void publish(Message message) throws JMSException {
      this.send(message);
   }

   public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.send(message, deliveryMode, priority, timeToLive);
   }

   public void publish(Topic topic, Message message) throws JMSException {
      this.send((Destination)topic, (Message)message);
   }

   public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.send((Destination)topic, message, deliveryMode, priority, timeToLive);
   }

   private void forwardInternal(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (!(message instanceof MessageImpl)) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNotForwardable3Loggable());
      } else if (this.peerInfo.getMajor() < 9) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logUnsupportedLoggable());
      } else if (!((MessageImpl)message).isForwardable()) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logNotForwardable2Loggable());
      } else {
         this.deliveryInternal(destination, message, deliveryMode, priority, timeToLive, true, (CompletionListener)null);
      }
   }

   private void sendInternal(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, javax.jms.CompletionListener jmsAppListener, CompletionListener appListener) throws JMSException {
      if (this.session.XABegin() && jmsAppListener != null && this.session.userTransactionsEnabled()) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logUnsupportedAsyncSendInXALoggable().getMessage());
      } else {
         try {
            this.deliveryInternalUnified(destination, message, deliveryMode, priority, timeToLive, false, jmsAppListener, appListener);
         } finally {
            this.session.XAFinish();
         }

      }
   }

   public static void sendReturn(ProducerSendResponse sendResponse, Message message, MessageImpl messageImpl, boolean forwarding, long timeToDeliver, long timeToLive, int deliveryMode, int priority, Destination destination) throws JMSException {
      JMSMessageId messageId = sendResponse.getMessageId();
      if (!forwarding) {
         messageImpl.setId(messageId);
         messageImpl.setOldMessage(!sendResponse.get90StyleMessageId());
      }

      if (message == messageImpl) {
         messageImpl.setJMSDestinationImpl((DestinationImpl)destination);
      } else {
         try {
            message.setJMSDestination(destination);
         } catch (ClassCastException var15) {
         } catch (InvalidDestinationException var16) {
         } catch (JMSException var17) {
         }

         message.setJMSDeliveryMode(messageImpl.getJMSDeliveryMode());
         message.setJMSPriority(messageImpl.getJMSPriority());
         if (message instanceof MessageImpl) {
            if (!forwarding) {
               ((MessageImpl)message).setId(messageId);
               ((MessageImpl)message).setOldMessage(messageImpl.isOldMessage());
            }
         } else {
            message.setJMSTimestamp(messageImpl.getJMSTimestamp());

            try {
               message.setJMSMessageID(messageImpl.getJMSMessageID());
            } catch (Throwable var14) {
            }
         }
      }

      if (messageImpl.getJMSExpiration() != 0L) {
         message.setJMSExpiration(messageId.getTimestamp() + timeToLive);
      } else {
         message.setJMSExpiration(0L);
      }

      if (timeToDeliver >= 0L) {
         try {
            message.setJMSDeliveryTime(messageId.getTimestamp() + timeToDeliver);
         } catch (AbstractMethodError var18) {
         } catch (NoSuchMethodError var19) {
         } catch (UnsupportedOperationException var20) {
         } catch (JMSException var21) {
            if (!(var21.getLinkedException() instanceof Exception) || !(var21.getLinkedException().getCause() instanceof AbstractMethodError) && !(var21.getLinkedException().getCause() instanceof NoSuchMethodError) && !(var21.getLinkedException().getCause() instanceof UnsupportedOperationException)) {
               throw var21;
            }
         }
      }

      try {
         deliveryMode = sendResponse.getDeliveryMode();
         if (deliveryMode != -1) {
            message.setJMSDeliveryMode(deliveryMode);
         }
      } catch (JMSException var13) {
      }

      priority = sendResponse.getPriority();
      if (priority != -1) {
         message.setJMSPriority(priority);
      }

      timeToLive = sendResponse.getTimeToLive();
      if (timeToLive != -1L) {
         message.setJMSExpiration(messageId.getTimestamp() + timeToLive);
      }

      if (message instanceof MessageImpl) {
         timeToDeliver = sendResponse.getTimeToDeliver();
         if (timeToDeliver != -1L) {
            ((MessageImpl)message).setDeliveryTime(messageId.getTimestamp() + timeToDeliver);
         }

         int redeliveryLimit = sendResponse.getRedeliveryLimit();
         if (redeliveryLimit != 0) {
            ((MessageImpl)message).setJMSRedeliveryLimit(redeliveryLimit);
         }
      }

   }

   private void deliveryInternal(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, boolean forwarding, CompletionListener appListener) throws JMSException {
      this.deliveryInternalUnified(destination, message, deliveryMode, priority, timeToLive, forwarding, (javax.jms.CompletionListener)null, appListener);
   }

   private void deliveryInternalUnified(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, boolean forwarding, javax.jms.CompletionListener jmsAppListener, CompletionListener appListener) throws JMSException {
      if (!(this.session instanceof JMSXASession) && this.session.getConnection().isWrappedIC() && TransactionHelper.getTransactionHelper().getTransaction() != null) {
         throw new IllegalStateException(JMSClientExceptionLogger.logUnexpectedTransactionLoggable().getMessage());
      } else if (jmsAppListener != null && this.session.userTransactionsEnabled() && TransactionHelper.getTransactionHelper().getTransaction() != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logUnsupportedAsyncSendInXALoggable().getMessage());
      } else if (destination == null) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestination2Loggable().getMessage());
      } else if (!(destination instanceof DestinationImpl)) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logForeignDestination2Loggable().getMessage());
      } else if (this.session.getType() == 2 && !((DestinationImpl)destination).isQueue()) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logMustBeAQueueLoggable(destination.toString()).getMessage());
      } else if (this.session.getType() == 1 && !((DestinationImpl)destination).isTopic()) {
         throw new InvalidDestinationException(JMSClientExceptionLogger.logMustBeATopicLoggable(destination.toString()).getMessage());
      } else {
         if (appListener == null) {
            this.producerFlowControl.doFlowControl();
         } else {
            this.producerFlowControl.markWLAsyncSendFlowControlTime();
         }

         MessageImpl messageImpl;
         try {
            messageImpl = (MessageImpl)message;
            messageImpl.resetUserPropertySize();
            if (!forwarding) {
               messageImpl.setForward(false);
               messageImpl.resetForwardsCount();
               messageImpl.setOldMessage(false);
               messageImpl.setJMSXUserID((String)null);
               if ((messageImpl.getSAFSequenceName() != null || messageImpl.getSAFSeqNumber() != 0L) && !messageImpl.getKeepSAFSequenceNameAndNumber()) {
                  messageImpl.setSAFSequenceName((String)null);
                  messageImpl.setSAFSeqNumber(0L);
               }
            }

            if (this.session.getConnection().isLocal() || jmsAppListener != null) {
               messageImpl = messageImpl.copy();
               Destination replyto = messageImpl.getJMSReplyTo();
               if (replyto != null && replyto instanceof DestinationImpl) {
                  try {
                     messageImpl.setJMSReplyTo((Destination)((DestinationImpl)replyto).clone());
                  } catch (CloneNotSupportedException var14) {
                  }
               }
            }
         } catch (ClassCastException var15) {
            messageImpl = convertMessage(message);
            if (FOREIGNMSGTIMESTAMP_ENABLED) {
               messageImpl.setLongProperty("WL_JMS_ForeignTimestampMillis", message.getJMSTimestamp());
               messageImpl.setStringProperty("WL_JMS_ForeignTimestampString", JMSForwardHelper.getFormattedTime(message.getJMSTimestamp()));
            }
         }

         if (timeToLive == 0L) {
            timeToLive = 0L;
         }

         messageImpl.setJMSExpiration(timeToLive);
         long timeToDeliver = this.getTimeToDeliverInternal();
         messageImpl.setDeliveryTime(timeToDeliver);
         messageImpl.setJMSRedeliveryLimit(this.redeliveryLimit);
         messageImpl.setJMSDestinationImpl((DestinationImpl)null);
         if (!forwarding) {
            messageImpl.setId((JMSMessageId)null);
         }

         messageImpl.setJMSDeliveryMode(deliveryMode);
         messageImpl.setJMSPriority(priority);
         messageImpl.setDDForwarded(false);
         messageImpl.setDeliveryCount(0);
         messageImpl.setUnitOfOrderName(this.unitOfOrderName);
         JMSWorkContextHelper.infectMessage(messageImpl);
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer (id: " + this.producerId + ") : Sending message deliveryMode = " + deliveryMode + " priority = " + priority + " timeToLive = " + timeToLive + " timeToDeliver = " + timeToDeliver + " redeliveryLimit = " + this.redeliveryLimit);
         }

         JMSProducerSendResponse var13 = (JMSProducerSendResponse)this.toFEProducer(destination, message, messageImpl, deliveryMode, timeToDeliver, priority, timeToLive, forwarding, jmsAppListener, appListener);
      }
   }

   private void wrappedSendReturn(JMSProducerSendResponse sendResponse, Destination destination, Message message, MessageImpl messageImpl, int deliveryMode, long timeToDeliver, int priority, long timeToLive, boolean forwarding) throws JMSException {
      sendReturn(sendResponse, message, messageImpl, forwarding, timeToDeliver, timeToLive, deliveryMode, priority, destination);
      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer (id: " + this.producerId + ") : Successfully sent message " + message.getJMSMessageID());
      }

      this.producerFlowControl.updateFlowControl(sendResponse);
   }

   private static MessageImpl convertMessage(Message message) throws JMSException {
      try {
         if (message instanceof BytesMessage) {
            return new BytesMessageImpl((BytesMessage)message);
         } else if (message instanceof MapMessage) {
            return new MapMessageImpl((MapMessage)message);
         } else if (message instanceof ObjectMessage) {
            return new ObjectMessageImpl((ObjectMessage)message);
         } else if (message instanceof StreamMessage) {
            return new StreamMessageImpl((StreamMessage)message);
         } else {
            return (MessageImpl)(message instanceof TextMessage ? new TextMessageImpl((TextMessage)message) : new HdrMessageImpl(message));
         }
      } catch (IOException var2) {
         throw JMSUtilities.jmsExceptionThrowable(JMSClientExceptionLogger.logErrorConvertingForeignMessageLoggable().getMessage(), var2);
      }
   }

   public void setDisableMessageID(boolean messageIdsDisabled) throws JMSException {
      this.checkClosed();
      this.messageIdsDisabled = messageIdsDisabled;
   }

   public boolean getDisableMessageID() throws JMSException {
      this.checkClosed();
      return this.messageIdsDisabled;
   }

   public void setDisableMessageTimestamp(boolean messageTimestampsDisabled) throws JMSException {
      this.checkClosed();
      this.messageTimestampsDisabled = messageTimestampsDisabled;
   }

   public boolean getDisableMessageTimestamp() throws JMSException {
      this.checkClosed();
      return this.messageTimestampsDisabled;
   }

   public void setDeliveryMode(int deliveryMode) throws JMSException {
      this.checkClosed();
      if (deliveryMode != 2 && deliveryMode != 1) {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidDeliveryModeLoggable());
      } else {
         this.deliveryMode = deliveryMode;
      }
   }

   public int getDeliveryMode() throws JMSException {
      this.checkClosed();
      return this.deliveryMode;
   }

   public void setPriority(int priority) throws JMSException {
      this.checkClosed();
      if (priority >= 0 && priority <= 9) {
         this.priority = priority;
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidPriorityLoggable());
      }
   }

   public int getPriority() throws JMSException {
      this.checkClosed();
      return this.priority;
   }

   public long getTimeToDeliver() throws JMSException {
      this.checkClosed();
      return this.getTimeToDeliverInternal();
   }

   private long getTimeToDeliverInternal() {
      return this.timeToDeliver == -1L ? this.session.getConnection().getTimeToDeliver() : this.timeToDeliver;
   }

   public void setTimeToDeliver(long timeToDeliver) throws JMSException {
      this.checkClosed();
      if (timeToDeliver < -1L) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidTimeToDeliverLoggable().getMessage());
      } else {
         this.timeToDeliver = timeToDeliver;
      }
   }

   public int getRedeliveryLimit() throws JMSException {
      this.checkClosed();
      return this.redeliveryLimit;
   }

   public void setRedeliveryLimit(int redeliveryLimit) throws JMSException {
      this.checkClosed();
      if (redeliveryLimit < -1) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidRedeliveryLimitLoggable().getMessage());
      } else {
         this.redeliveryLimit = redeliveryLimit;
      }
   }

   public long getSendTimeout() throws JMSException {
      this.checkClosed();
      return this.sendTimeout;
   }

   public void setSendTimeout(long sendTimeout) throws JMSException {
      this.checkClosed();
      if (sendTimeout < 0L) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidSendTimeoutLoggable().getMessage());
      } else {
         this.sendTimeout = sendTimeout;
      }
   }

   public void setTimeToLive(long timeToLive) throws JMSException {
      this.checkClosed();
      this.timeToLive = timeToLive;
   }

   public long getTimeToLive() throws JMSException {
      this.checkClosed();
      return this.timeToLive;
   }

   public void close() throws JMSException {
      this.session.checkOpPermissionForAsyncSend("JMSProducer/MessageProducer.close()");
      JMSID producerId;
      synchronized(this) {
         if (this.isClosed()) {
            return;
         }

         Object lock = this.wlProducerImpl == null ? this : this.wlProducerImpl.getConnectionStateLock();
         synchronized(lock) {
            producerId = this.producerId;
            this.producerId = null;
         }
      }

      this.waitTillAllJMSAsyncSendProcessed();
      this.session.producerClose(producerId);
   }

   public void isCloseAllowed(String operation) throws JMSException {
   }

   boolean hasTemporaryDestination() {
      DestinationImpl stableDestination = this.destination;
      return stableDestination != null && (stableDestination.getType() == 4 || stableDestination.getType() == 8);
   }

   JMSSession getSession() {
      return this.session;
   }

   public String toString() {
      return this.session.getConnection().getRuntimeMBeanName() + "." + this.session.getRuntimeMBeanName() + "." + this.getRuntimeMBeanName();
   }

   private void checkClosed() throws JMSException {
      if (this.isClosed()) {
         Object lock = this.wlProducerImpl == null ? this : this.wlProducerImpl.getConnectionStateLock();
         synchronized(lock){}

         try {
            if (this.isReconnectControllerClosed()) {
               throw new AlreadyClosedException(JMSClientExceptionLogger.logClosedProducerLoggable());
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

   public int invoke(Request request) {
      return Integer.MAX_VALUE;
   }

   public void setUnitOfOrder(String name) throws JMSException {
      this.checkClosed();
      if (this.peerInfo.getMajor() < 9) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logUnsupportedLoggable().getMessage());
      } else {
         this.unitOfOrderName = name;
      }
   }

   public void setUnitOfOrder() throws JMSException {
      this.setUnitOfOrder(this.session.getJMSID().toString());
   }

   public String getUnitOfOrder() throws JMSException {
      this.checkClosed();
      return this.unitOfOrderName;
   }

   private Object toFEProducer(Destination destination, Message message, MessageImpl messageImpl, int deliveryMode, long timeToDeliver, int priority, long timeToLive, boolean forwarding, javax.jms.CompletionListener jmsAppListener, CompletionListener appListener) throws JMSException {
      if (AsyncSendCallback.isMessageInAsyncSend(message)) {
         throw new IllegalStateException(JMSClientExceptionLogger.logAsyncSentMessageConcurrentSendLoggable("@" + message.hashCode()).getMessage());
      } else {
         boolean jmsAsyncOff = false;
         if (jmsAppListener != null && KernelStatus.isApplet()) {
            jmsAsyncOff = true;
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer (id: " + this.producerId + ") : In Applet Client,  turn off JMS 2.0 async send. destination=" + destination);
         }

         if (jmsAppListener != null && KernelStatus.isThinIIOPClient()) {
            jmsAsyncOff = true;
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer (id: " + this.producerId + ") : In Thin IIOP Client,  turn off JMS 2.0 async send. destination=" + destination);
         }

         JMSDispatcher dispatcher = this.session.getConnection().getFrontEndDispatcher();
         if (ONEWAYSENDENABLED) {
            this.oneWaySendMode = oneWaySendModeConf;
            this.oneWayWindowSize = oneWayWindowSizeConf;
         }

         boolean doOneWay = (this.oneWaySendMode == 1 || this.oneWaySendMode == 2 && ((DestinationImpl)destination).isTopic()) && !(destination instanceof DistributedDestinationImpl) && this.getUnitOfOrder() == null && messageImpl.getJMSDeliveryMode() == 1 && !((DestinationImpl)destination).isStale();
         doOneWay = doOneWay && ((DestinationImpl)destination).getDispatcherId().isSameServer(dispatcher.getId());
         doOneWay = doOneWay && jmsAppListener == null;
         JMSProducerSendResponse res = null;
         boolean messageHasId = false;
         AsyncSendCallback asyncSendCallback = null;
         if (appListener != null || jmsAppListener != null) {
            Object l = appListener == null ? jmsAppListener : appListener;
            asyncSendCallback = new AsyncSendCallback(this.destination == null ? (DestinationImpl)destination : this.destination, message, deliveryMode, timeToDeliver, priority, timeToLive, forwarding, l, jmsAsyncOff, this);
         }

         DestinationImpl destImpl = this.destination == null ? (DestinationImpl)destination : null;
         FEProducerSendRequest request = new FEProducerSendRequest(this.producerId, messageImpl, destImpl, this.sendTimeout, this.compressionThreshold, (destImpl == null || destImpl.isTopic()) && this.session.getConnection().isJMSSessionPooledInWrapper(), jmsAsyncOff ? null : asyncSendCallback);
         byte dispatchMode;
         if (!this.session.isTransacted() && this.session.userTransactionsEnabled()) {
            if (appListener != null) {
               dispatchMode = 4;
            } else if (jmsAppListener != null & !jmsAsyncOff) {
               dispatchMode = 6;
            } else {
               dispatchMode = 0;
            }
         } else if (!this.session.isTransacted() && doOneWay) {
            this.producerFlowControl.disableFlowControl();
            if (this.count == Integer.MAX_VALUE) {
               this.count = 0;
            }

            if (++this.count % this.oneWayWindowSize != 1 && this.oneWayWindowSize > 1 && this.totalConsecutiveOneWaySendMessageSize <= ONEWAYSENDCONSECUTIVEMESSAGELIMIT) {
               JMSMessageId id = messageImpl.getId();
               messageHasId = id != null;
               if (id == null) {
                  id = JMSMessageId.create();
               }

               res = new JMSProducerSendResponse(id);
               res.set90StyleMessageId();
               res.setDeliveryMode(1);
               res.setPriority(priority);
               res.setTimeToLive(timeToLive);
               res.setTimeToDeliver(timeToDeliver);
               res.setMessage(messageImpl);
               res.setRequest(request);
               request.setNoResponse(true);
               messageImpl.setId(id);
               if (messageImpl.getDeliveryTime() > 0L) {
                  res.setTimeToDeliver(messageImpl.getDeliveryTime());
               }

               dispatchMode = 3;
            } else {
               this.totalConsecutiveOneWaySendMessageSize = 0;
               if (appListener == null) {
                  dispatchMode = 2;
               } else {
                  dispatchMode = 5;
               }
            }
         } else {
            if (this.session.isTransacted()) {
               this.session.setPendingWork(true);
            }

            if (appListener != null) {
               dispatchMode = 5;
            } else if (jmsAppListener != null && !jmsAsyncOff) {
               dispatchMode = 7;
            } else {
               dispatchMode = 1;
            }
         }

         messageImpl.setSerializeDestination(false);
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            String debugstr = jmsAppListener == null ? ", appListener=" + appListener : ", jmsAppListener=" + jmsAppListener + ", jmsAsyncOff=" + jmsAsyncOff;
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer (id: " + this.producerId + ") : Dispatching message to FRONTEND/FEProducer for destination: " + destination + debugstr + ", dispatch mode=" + dispatchMode + ", isJMSSessionPooledInWrapper=" + this.session.getConnection().isJMSSessionPooledInWrapper());
         }

         if (jmsAppListener != null && (dispatchMode == 7 || dispatchMode == 6)) {
            PeerInfo pi = this.getFEPeerInfo();
            if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
               throw new JMSException("Operation not supported by front-end server version [" + pi + "]: send message asynchronously");
            }
         }

         if (dispatchMode != 0 && dispatchMode != 1 && dispatchMode != 2) {
            if (jmsAppListener != null && !jmsAsyncOff) {
               this.session.blockAsyncSendIfOverPendingThreshold();
            }
         } else {
            this.session.waitTillAllAsyncSendProcessed();
         }

         if (jmsAppListener != null) {
            this.session.enqueueAsyncSendCallback(asyncSendCallback);
         }

         Throwable sendCallThrowable = null;

         Object var50;
         try {
            if (jmsAppListener != null) {
               asyncSendCallback.markAsyncSendStart();
            }

            JMSID mysessionId = this.getSession().getJMSID();
            if (mysessionId == null) {
               throw new JMSException(JMSClientExceptionLogger.logSessionIsClosedLoggable().getMessage());
            }

            int mydispatchId = mysessionId.getCounter();
            Object response;
            Transaction transaction;
            Object var27;
            switch (dispatchMode) {
               case 0:
                  response = dispatcher.dispatchSyncTran(request);
                  break;
               case 1:
                  response = dispatcher.dispatchSyncNoTran(request);
                  break;
               case 2:
                  response = dispatcher.dispatchSyncNoTranWithId(request, mydispatchId);
                  break;
               case 3:
                  request.setResult(res);
                  dispatcher.dispatchNoReplyWithId(request, mydispatchId);
                  this.totalConsecutiveOneWaySendMessageSize += request.getDataLen();
                  response = res;
                  break;
               case 4:
                  dispatcher.dispatchAsync(request);
                  transaction = null;
                  return transaction;
               case 5:
                  transaction = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

                  try {
                     dispatcher.dispatchAsync(request);
                  } finally {
                     if (transaction != null) {
                        TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(transaction);
                     }

                  }

                  var27 = null;
                  return var27;
               case 6:
                  dispatcher.dispatchAsyncWithId(request, mydispatchId);
                  var27 = null;
                  return var27;
               case 7:
                  transaction = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

                  try {
                     dispatcher.dispatchAsyncWithId(request, mydispatchId);
                  } finally {
                     if (transaction != null) {
                        TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(transaction);
                     }

                  }

                  var27 = null;
                  return var27;
               default:
                  throw new JMSException("JMSProducer: Unexpected dispatchMode=" + dispatchMode);
            }

            this.wrappedSendReturn((JMSProducerSendResponse)response, destination, message, messageImpl, deliveryMode, timeToDeliver, priority, timeToLive, forwarding);
            if (appListener != null) {
               request.onCompletion(response);
            }

            var50 = response;
         } catch (Throwable var44) {
            sendCallThrowable = var44;
            if (dispatchMode == 3 && !messageHasId) {
               messageImpl.setId((JMSMessageId)null);
            }

            if (var44 instanceof JMSException) {
               throw (JMSException)var44;
            }

            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logErrorSendingMessageLoggable(), var44);
         } finally {
            messageImpl.setSerializeDestination(true);
            if (asyncSendCallback != null) {
               if (jmsAppListener != null) {
                  asyncSendCallback.setMessageSize((long)request.getDataLen(), messageImpl.size());
                  if (sendCallThrowable == null) {
                     asyncSendCallback.onSendCallSuccessReturn();
                  } else {
                     asyncSendCallback.onSendCallReturn();
                  }
               } else if (appListener != null && sendCallThrowable != null) {
                  request.onException(sendCallThrowable);
               }
            }

         }

         return var50;
      }
   }

   JMSProducerSendResponse completeAsyncSend(Object response, FEProducerSendRequest request, DestinationImpl destination, Message messageState, MessageImpl message, int deliveryModeState, long timeToDeliverState, int priorityState, long timeToLiveState, boolean forwardingState) throws Exception {
      request.getMessage().setSerializeDestination(true);
      JMSProducerSendResponse sendResponse = (JMSProducerSendResponse)response;
      this.wrappedSendReturn(sendResponse, destination, messageState, message, deliveryModeState, timeToDeliverState, priorityState, timeToLiveState, forwardingState);
      sendResponse.setMessage(messageState);
      sendResponse.setAsyncFlowControlTime(this.producerFlowControl.getWLAsyncSendFlowControlTime());
      return sendResponse;
   }

   private boolean hasAsyncSendPending() {
      synchronized(this.asyncSendCountLock) {
         return this.jmsAsyncSendCount > 0L || this.wlAsyncSendCount > 0L;
      }
   }

   void incrementWLAsyncSendCount() {
      synchronized(this.asyncSendCountLock) {
         ++this.wlAsyncSendCount;
      }
   }

   void decrementWLAsyncSendCount() {
      synchronized(this.asyncSendCountLock) {
         --this.wlAsyncSendCount;
      }
   }

   void incrementJMSAsyncSendCount() {
      synchronized(this.asyncSendCountLock) {
         ++this.jmsAsyncSendCount;
      }
   }

   void decrementJMSAsyncSendCount() {
      synchronized(this.asyncSendCountLock) {
         --this.jmsAsyncSendCount;
         this.asyncSendCountLock.notifyAll();
      }
   }

   void waitTillAllJMSAsyncSendProcessed() throws JMSException {
      synchronized(this.asyncSendCountLock) {
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer.waitTillAllJMSAsyncSendProcessed(): jmsAsyncSendCount=" + this.jmsAsyncSendCount + ", this=" + this);
         }

         while(this.jmsAsyncSendCount != 0L) {
            try {
               this.asyncSendCountLock.wait();
               if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                  JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer.waitTillAllJMSAsyncSendProcessed(): wait waked up: jmsAsyncSendCount=" + this.jmsAsyncSendCount + ", this=" + this);
               }
            } catch (InterruptedException var4) {
               JMSClientExceptionLogger.logStackTrace(var4);
            }
         }

      }
   }

   public void forward(Destination destination, Message message) throws JMSException {
      if (this.destination != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logCannotOverrideDestinationLoggable().getMessage());
      } else {
         this.forwardInternal(destination, message, this.deliveryMode, this.priority, this.timeToLive);
      }
   }

   public void forward(Message message) throws JMSException {
      if (this.destination == null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logNeedDestinationLoggable().getMessage());
      } else {
         this.forwardInternal(this.destination, message, this.deliveryMode, this.priority, this.timeToLive);
      }
   }

   public void forward(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.destination == null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logNeedDestinationLoggable().getMessage());
      } else {
         this.forwardInternal(this.destination, message, deliveryMode, priority, timeToLive);
      }
   }

   public void forward(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.destination != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logCannotOverrideDestination2Loggable().getMessage());
      } else {
         this.forwardInternal(destination, message, deliveryMode, priority, timeToLive);
      }
   }

   public void setSequence(String name) throws JMSException {
      if (this.peerInfo.getMajor() < 9) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logUnsupportedLoggable().getMessage());
      } else {
         this.sequenceName = name;
      }
   }

   public String getSequence() throws JMSException {
      return this.sequenceName;
   }

   public void reserveUnitOfOrderWithSequence() throws JMSException {
      this.processSequenceInternal(196608, this.destination, this.deliveryMode, this.priority, this.timeToLive);
   }

   public void reserveSequence(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.destination != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logCannotOverrideDestinationLoggable().getMessage());
      } else {
         this.processSequenceInternal(196608, destination, deliveryMode, priority, timeToLive);
      }
   }

   public void releaseSequenceAndUnitOfOrder(boolean fanout) throws JMSException {
      int opcode;
      if (fanout) {
         opcode = 65536;
      } else {
         opcode = 131072;
      }

      this.processSequenceInternal(opcode, this.destination, this.deliveryMode, this.priority, this.timeToLive);
   }

   public void releaseSequenceAndUnitOfOrder(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, boolean fanout) throws JMSException {
      if (this.destination != null) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logCannotOverrideDestinationLoggable().getMessage());
      } else {
         int opcode;
         if (fanout) {
            opcode = 65536;
         } else {
            opcode = 131072;
         }

         this.processSequenceInternal(opcode, destination, deliveryMode, priority, timeToLive);
      }
   }

   private void processSequenceInternal(int opcode, Destination destination, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.peerInfo.getMajor() < 9) {
         throw new UnsupportedOperationException(JMSClientExceptionLogger.logUnsupportedLoggable().getMessage());
      } else if (this.sequenceName == null) {
         throw new UnsupportedOperationException("null sequence not permitted");
      } else {
         HdrMessageImpl hdrMessage = new HdrMessageImpl();
         hdrMessage.setSAFSequenceName(this.sequenceName);
         hdrMessage.setControlOpcode(opcode);
         this.deliveryInternal(destination, hdrMessage, deliveryMode, priority, timeToLive, false, (CompletionListener)null);
      }
   }

   void setWlProducerImpl(WLProducerImpl rp) {
      this.wlProducerImpl = rp;
   }

   void setDestinationFlags(byte destFlags) {
      this.destinationFlags = destFlags;
   }

   public void sendAsync(Message message, CompletionListener listener) {
      try {
         this.sendWithListener(listener, message);
      } catch (JMSException var4) {
         listener.onException(var4);
      }

   }

   public void sendAsync(Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      try {
         this.sendWithListener(listener, message, deliveryMode, priority, timeToLive);
      } catch (JMSException var8) {
         listener.onException(var8);
      }

   }

   public void sendAsync(Destination destination, Message message, CompletionListener listener) {
      try {
         this.sendWithListener(listener, destination, message);
      } catch (JMSException var5) {
         listener.onException(var5);
      }

   }

   public void sendAsync(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      try {
         this.sendWithListener(listener, destination, message, deliveryMode, priority, timeToLive);
      } catch (JMSException var9) {
         listener.onException(var9);
      }

   }

   public long getDeliveryDelay() throws JMSException {
      this.checkClosed();
      return this.timeToDeliver;
   }

   public void send(Message message, javax.jms.CompletionListener jmsAppListener) throws JMSException {
      if (jmsAppListener == null) {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logCompletionListenerIsNullLoggable().getMessage());
      } else {
         this.sendWithListenerUnified(jmsAppListener, (CompletionListener)null, message);
      }
   }

   public void send(Destination destination, Message message, javax.jms.CompletionListener jmsAppListener) throws JMSException {
      if (jmsAppListener == null) {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logCompletionListenerIsNullLoggable().getMessage());
      } else {
         this.sendWithListenerUnified(jmsAppListener, (CompletionListener)null, destination, message);
      }
   }

   public void send(Message message, int deliveryMode, int priority, long timeToLive, javax.jms.CompletionListener jmsAppListener) throws JMSException {
      if (jmsAppListener == null) {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logCompletionListenerIsNullLoggable().getMessage());
      } else {
         this.sendWithListenerUnified(jmsAppListener, (CompletionListener)null, message, deliveryMode, priority, timeToLive);
      }
   }

   public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, javax.jms.CompletionListener jmsAppListener) throws JMSException {
      if (jmsAppListener == null) {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logCompletionListenerIsNullLoggable().getMessage());
      } else {
         this.sendWithListenerUnified(jmsAppListener, (CompletionListener)null, destination, message, deliveryMode, priority, timeToLive);
      }
   }

   public void setDeliveryDelay(long delay) throws JMSException {
      this.setTimeToDeliver(delay);
   }

   static {
      try {
         if (System.getProperty("weblogic.jms.client.onewaysendconfigs") != null) {
            ONEWAYSENDENABLED = true;
            String conf = System.getProperty("weblogic.jms.client.onewaysendconfigs");
            StringTokenizer st = new StringTokenizer(conf);
            if (st.hasMoreTokens()) {
               oneWaySendModeConf = JMSConnection.convertOneWaySendMode(st.nextToken());
            }

            if (st.hasMoreTokens()) {
               oneWayWindowSizeConf = Integer.parseInt(st.nextToken());
            }

            if (st.hasMoreTokens()) {
               ONEWAYSENDCONSECUTIVEMESSAGELIMIT = Integer.parseInt(st.nextToken());
            }
         }
      } catch (RuntimeException var2) {
      }

   }

   private class ProducerFlowControl {
      private boolean flowControlEnabled;
      private final int flowMinimum;
      private final int flowMaximum;
      private final double flowDecrease;
      private final int flowIncrease;
      private final long flowInterval;
      private double flowRateCurrent;
      private long backOffTime;
      private boolean needsFlowControl;
      private long currentTime;
      private long elapsedTime;
      private long lastTimeChanged;
      private long lastTimeLeave;
      private long wlAsyncSendFlowControlTime;
      private final ReentrantLock flowcontrolLock = new ReentrantLock();

      public ProducerFlowControl(JMSConnection con) {
         this.flowControlEnabled = con.isFlowControlEnabled();
         this.flowMinimum = con.getFlowMinimum();
         this.flowMaximum = con.getFlowMaximum();
         this.flowIncrease = con.getFlowIncrease();
         this.flowDecrease = con.getFlowDecrease();
         this.flowInterval = con.getFlowInterval();
         this.flowRateCurrent = (double)this.flowMaximum;
         this.backOffTime = (long)(1000.0 / this.flowRateCurrent);
      }

      void disableFlowControl() {
         if (this.flowControlEnabled) {
            this.flowControlEnabled = false;
         }

      }

      final void updateFlowControl(JMSProducerSendResponse sendResponse) {
         if (JMSProducer.this.hasAsyncSendPending()) {
            this.flowcontrolLock.lock();

            try {
               this.updateFlowControlInternal(sendResponse);
            } finally {
               this.flowcontrolLock.unlock();
            }
         } else {
            this.updateFlowControlInternal(sendResponse);
         }

      }

      private final void updateFlowControlInternal(JMSProducerSendResponse sendResponse) {
         if (this.flowControlEnabled && sendResponse != null) {
            if (sendResponse.getNeedsFlowControl()) {
               this.needsFlowControl = true;
               if (sendResponse.getFlowControlTime() == -1L) {
                  if (this.flowRateCurrent > (double)this.flowMaximum) {
                     this.flowRateCurrent = (double)this.flowMaximum;
                  }

                  if (this.currentTime - this.lastTimeChanged >= this.flowInterval && this.flowRateCurrent > (double)this.flowMinimum) {
                     this.flowRateCurrent *= this.flowDecrease;
                     if (this.flowRateCurrent < (double)this.flowMinimum) {
                        this.flowRateCurrent = (double)this.flowMinimum;
                     }

                     this.lastTimeChanged = this.currentTime;
                     this.backOffTime = (long)(1000.0 / this.flowRateCurrent);
                  }
               } else {
                  this.backOffTime = sendResponse.getFlowControlTime();
               }

               this.lastTimeLeave = this.currentTime;
            } else {
               this.needsFlowControl = false;
               if (this.currentTime - this.lastTimeChanged >= this.flowInterval && this.flowRateCurrent < (double)this.flowMaximum) {
                  this.flowRateCurrent += (double)this.flowIncrease;
                  this.lastTimeChanged = this.currentTime;
                  this.backOffTime = (long)(1000.0 / this.flowRateCurrent);
               }
            }

         }
      }

      final void doFlowControl() {
         long sleepTime = this.getFlowControlSleepTime();
         if (sleepTime != 0L) {
            JMSDebug.JMSMessagePath.debug("CLIENT/JMSProducer (id: " + JMSProducer.this.producerId + ") : doFlowControl sleep(" + sleepTime + ")");

            try {
               Thread.sleep(sleepTime);
            } catch (Exception var4) {
            }

         }
      }

      private final long getFlowControlSleepTime() {
         if (JMSProducer.this.hasAsyncSendPending()) {
            this.flowcontrolLock.lock();

            long var1;
            try {
               var1 = this.getFlowControlSleepTimeInternal();
            } finally {
               this.flowcontrolLock.unlock();
            }

            return var1;
         } else {
            return this.getFlowControlSleepTimeInternal();
         }
      }

      private final long getFlowControlSleepTimeInternal() {
         if (!this.flowControlEnabled) {
            return 0L;
         } else {
            this.currentTime = System.currentTimeMillis();
            this.elapsedTime = this.currentTime - this.lastTimeLeave;
            long sleepTime = 0L;
            if (this.flowControlEnabled && (this.needsFlowControl || this.flowRateCurrent <= (double)this.flowMaximum) && this.elapsedTime <= this.backOffTime) {
               sleepTime = this.backOffTime - this.elapsedTime;
               this.currentTime = System.currentTimeMillis() + sleepTime;
            }

            return sleepTime;
         }
      }

      final void markWLAsyncSendFlowControlTime() {
         this.wlAsyncSendFlowControlTime = this.getFlowControlSleepTime();
      }

      final long getWLAsyncSendFlowControlTime() {
         return this.wlAsyncSendFlowControlTime;
      }
   }
}
