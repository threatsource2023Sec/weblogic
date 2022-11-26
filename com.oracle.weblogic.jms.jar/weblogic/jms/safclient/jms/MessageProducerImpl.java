package weblogic.jms.safclient.jms;

import java.util.Random;
import javax.jms.CompletionListener;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.ResourceAllocationException;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import weblogic.jms.client.JMSProducer;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.ProducerSendResponse;
import weblogic.jms.extensions.WLMessage;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.safclient.ClientSAFDelegate;
import weblogic.jms.safclient.agent.DestinationImpl;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.QuotaException;
import weblogic.messaging.kernel.SendOptions;
import weblogic.transaction.TransactionHelper;

public final class MessageProducerImpl implements TopicPublisher, QueueSender, WLMessageProducer {
   private static final String NON_PERSISTENT = "Non-Persistent";
   private SessionImpl session;
   private int id;
   private DestinationImpl destination;
   private long timeToDeliver;
   private int redeliveryLimit = -1;
   private long sendTimeout;
   private String unitOfOrder;
   private int compressionThreshold;
   private boolean disableMessageID = false;
   private boolean disableMessageTimestamp = false;
   private int deliveryMode;
   private int priority;
   private long timeToLive;
   private boolean closed = false;

   MessageProducerImpl(SessionImpl paramSession, int paramID, DestinationImpl paramDestination) {
      this.session = paramSession;
      this.id = paramID;
      this.destination = paramDestination;
      this.timeToDeliver = Long.parseLong(this.session.getDefaultTimeToDeliver());
      this.sendTimeout = this.session.getSendTimeout();
      this.unitOfOrder = this.session.getDefaultUnitOfOrder();
      this.compressionThreshold = this.session.getDefaultCompressionThreshold();
      this.deliveryMode = deliveryModeToInt(this.session.getDefaultDeliveryMode());
      this.priority = this.session.getDefaultPriority();
      this.timeToLive = this.session.getDefaultTimeToLive();
   }

   private static int deliveryModeToInt(String mode) {
      return "Non-Persistent".equals(mode) ? 1 : 2;
   }

   public Topic getTopic() throws JMSException {
      this.checkClosed();
      if (this.destination == null) {
         return null;
      } else if (!this.destination.isTopic()) {
         throw new weblogic.jms.common.JMSException("The destination for this message producer is not a topic");
      } else {
         return this.destination;
      }
   }

   public void publish(Message message) throws JMSException {
      this.send(message, this.deliveryMode, this.priority, this.timeToLive);
   }

   public void publish(Message message, int i, int i1, long l) throws JMSException {
      if (this.destination == null) {
         throw new weblogic.jms.common.JMSException("This is not a pinned message producer, cannot use this API");
      } else {
         this.send((Queue)this.destination, message, i, i1, l);
      }
   }

   public void publish(Topic topic, Message message) throws JMSException {
      this.publish(topic, message, this.deliveryMode, this.priority, this.timeToLive);
   }

   public void publish(Topic topic, Message message, int i, int i1, long l) throws JMSException {
      if (this.destination != null) {
         throw new weblogic.jms.common.JMSException("This is a pinned message producer, cannot use this API");
      } else {
         this.send((Destination)topic, message, i, i1, l);
      }
   }

   public Queue getQueue() throws JMSException {
      this.checkClosed();
      if (this.destination == null) {
         return null;
      } else if (!this.destination.isQueue()) {
         throw new weblogic.jms.common.JMSException("The destination for this message producer is not a queue");
      } else {
         return this.destination;
      }
   }

   public void send(Message message) throws JMSException {
      this.send(message, this.deliveryMode, this.priority, this.timeToLive);
   }

   public void send(Message message, int paramDeliveryMode, int paramPriority, long paramTimeToLive) throws JMSException {
      if (this.destination == null) {
         throw new weblogic.jms.common.JMSException("This is not a pinned message producer, cannot use this API");
      } else {
         this.send((Queue)this.destination, message, paramDeliveryMode, paramPriority, paramTimeToLive);
      }
   }

   public void send(Queue queue, Message message) throws JMSException {
      this.send(queue, message, this.deliveryMode, this.priority, this.timeToLive);
   }

   public void send(Queue queue, Message message, int paramDeliveryMode, int paramPriority, long paramTimeToLive) throws JMSException {
      this.send((Destination)queue, message, paramDeliveryMode, paramPriority, paramTimeToLive);
   }

   public long getTimeToDeliver() throws JMSException {
      this.checkClosed();
      return this.timeToDeliver;
   }

   public void setTimeToDeliver(long paramTimeToDeliver) throws JMSException {
      this.checkClosed();
      this.timeToDeliver = paramTimeToDeliver;
   }

   public int getRedeliveryLimit() throws JMSException {
      this.checkClosed();
      return this.redeliveryLimit;
   }

   public void setRedeliveryLimit(int paramRedeliveryLimit) throws JMSException {
      this.checkClosed();
      this.redeliveryLimit = paramRedeliveryLimit;
   }

   public long getSendTimeout() throws JMSException {
      this.checkClosed();
      return this.sendTimeout;
   }

   public void setSendTimeout(long paramSendTimeout) throws JMSException {
      this.checkClosed();
      this.sendTimeout = paramSendTimeout;
   }

   public String getUnitOfOrder() throws JMSException {
      this.checkClosed();
      return this.unitOfOrder;
   }

   public void setUnitOfOrder(String name) throws JMSException {
      this.checkClosed();
      this.unitOfOrder = name;
   }

   private static char intToHexChar(int convertMe) {
      return convertMe < 10 ? (char)(48 + convertMe) : (char)(97 + (convertMe - 10));
   }

   private static String bitsToString(byte[] toConvert) {
      if (toConvert != null && toConvert.length > 0) {
         StringBuffer sb = new StringBuffer(toConvert.length * 2);

         for(int lcv = 0; lcv < toConvert.length; ++lcv) {
            byte convertMe = toConvert[lcv];
            int lowNibble = convertMe & 15;
            sb.append(intToHexChar(lowNibble));
            int highNibble = convertMe >> 4 & 15;
            sb.append(intToHexChar(highNibble));
         }

         return sb.toString();
      } else {
         return new String();
      }
   }

   public void setUnitOfOrder() throws JMSException {
      this.checkClosed();
      Random random = new Random();
      byte[] uooBits = new byte[16];
      random.nextBytes(uooBits);
      this.setUnitOfOrder(bitsToString(uooBits));
   }

   public void forward(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      throw new weblogic.jms.common.JMSException("Not yet implemented");
   }

   public void forward(Message message) throws JMSException {
      throw new weblogic.jms.common.JMSException("Not yet implemented");
   }

   public void forward(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      throw new weblogic.jms.common.JMSException("Not yet implemented");
   }

   public void forward(Destination destination, Message message) throws JMSException {
      throw new weblogic.jms.common.JMSException("Not yet implemented");
   }

   public void setCompressionThreshold(int limit) throws JMSException {
      this.checkClosed();
      this.compressionThreshold = limit;
   }

   public int getCompressionThreshold() throws JMSException {
      this.checkClosed();
      return this.compressionThreshold;
   }

   public void setDisableMessageID(boolean b) throws JMSException {
      this.checkClosed();
      this.disableMessageID = b;
   }

   public boolean getDisableMessageID() throws JMSException {
      this.checkClosed();
      return this.disableMessageID;
   }

   public void setDisableMessageTimestamp(boolean b) throws JMSException {
      this.checkClosed();
      this.disableMessageTimestamp = b;
   }

   public boolean getDisableMessageTimestamp() throws JMSException {
      this.checkClosed();
      return this.disableMessageTimestamp;
   }

   public void setDeliveryMode(int i) throws JMSException {
      this.checkClosed();
      if (i != 2 && i != 1) {
         throw new weblogic.jms.common.JMSException("Invalid delivery mode: " + i);
      } else {
         this.deliveryMode = i;
      }
   }

   public int getDeliveryMode() throws JMSException {
      this.checkClosed();
      return this.deliveryMode;
   }

   public void setPriority(int i) throws JMSException {
      this.checkClosed();
      this.priority = i;
   }

   public int getPriority() throws JMSException {
      this.checkClosed();
      return this.priority;
   }

   public void setTimeToLive(long ttl) throws JMSException {
      this.checkClosed();
      this.timeToLive = ttl;
   }

   public long getTimeToLive() throws JMSException {
      this.checkClosed();
      return this.timeToLive;
   }

   public Destination getDestination() throws JMSException {
      this.checkClosed();
      return this.destination;
   }

   public synchronized void close() {
      if (!this.closed) {
         this.closed = true;
         this.session.closeProducer(this.id);
      }
   }

   public void send(Destination destination, Message message) throws JMSException {
      this.send(destination, message, this.deliveryMode, this.priority, this.timeToLive);
   }

   private static long safeLongAdd(long a, long b) {
      long result;
      if (a > 0L && b > 0L) {
         result = a + b;
         return result < 0L ? Long.MAX_VALUE : result;
      } else if (a < 0L && b < 0L) {
         result = a + b;
         return result >= 0L ? Long.MIN_VALUE : result;
      } else {
         return a + b;
      }
   }

   private void handleJMSMessagePreSend(MessageImpl message, boolean forwarding, long timeToLive, long timeToDeliver, int redeliveryLimit, boolean persistent, int priority, boolean attach, String unitOfOrderName) throws JMSException {
      message.resetUserPropertySize();
      if (!forwarding) {
         message.setForward(false);
         message.resetForwardsCount();
         message.setOldMessage(false);
         message.setJMSXUserID((String)null);
         message.setId(JMSMessageId.create());
      }

      message.setJMSExpiration(timeToLive);
      message.setDeliveryTime(timeToDeliver);
      message.setJMSRedeliveryLimit(redeliveryLimit);
      message.setJMSDestinationImpl((weblogic.jms.common.DestinationImpl)null);
      message.setJMSDeliveryMode(persistent ? 2 : 1);
      message.setJMSPriority(priority);
      message.setDDForwarded(false);
      message.setDeliveryCount(0);
      message.requestJMSXUserID(attach);
      message.setUnitOfOrderName(unitOfOrderName);
   }

   private void internalSend(ClientSAFDelegate root, SessionImpl session, DestinationImpl destination, MessageImpl message, boolean forwarding, boolean persistent, int redeliveryLimit, int priority, long timeToLive, long timeToDeliver, long sendTimeout, boolean attach, String unitOfOrderName) throws JMSException {
      SendOptions options = new SendOptions();
      options.setPersistent(persistent);
      if (redeliveryLimit >= 0) {
         options.setRedeliveryLimit(redeliveryLimit);
      }

      long currentTimeOfDay = -1L;
      long adjustedTimeToLive = 0L;
      if (timeToLive > 0L) {
         currentTimeOfDay = System.currentTimeMillis();
         adjustedTimeToLive = safeLongAdd(currentTimeOfDay, timeToLive);
         options.setExpirationTime(adjustedTimeToLive);
      }

      long adjustedTimeToDeliver = 0L;
      if (timeToDeliver > 0L) {
         if (currentTimeOfDay == -1L) {
            currentTimeOfDay = System.currentTimeMillis();
         }

         adjustedTimeToDeliver = safeLongAdd(currentTimeOfDay, timeToDeliver);
         options.setDeliveryTime(adjustedTimeToDeliver);
      }

      options.setNoDeliveryDelay(message.getJMSTimestamp() == message.getJMSTimestamp());
      if (sendTimeout > 0L) {
         options.setTimeout(sendTimeout);
      }

      weblogic.messaging.kernel.Queue kernelQueue = destination.getKernelQueue();
      if (kernelQueue == null) {
         throw new weblogic.jms.common.JMSException("Failed to send messages -- client SAF is not properly started. Check client SAF configuration.");
      } else {
         MessageImpl copiedMessage = null;
         if (message != null) {
            copiedMessage = message.copy();
            this.handleJMSMessagePreSend(copiedMessage, forwarding, adjustedTimeToLive, adjustedTimeToDeliver, redeliveryLimit, persistent, priority, attach, unitOfOrderName);
         }

         TransactionHelper txHelper = root.getTransactionHelper();
         TransactionHelper.pushTransactionHelper(txHelper);
         if (session.getTransacted()) {
            session.beginOrResume(txHelper);
         }

         try {
            KernelRequest kernelRequest;
            ResourceAllocationException rae;
            try {
               options.setSequence(kernelQueue.findOrCreateSequence(this.getSequenceName(copiedMessage, destination), this.getSequenceMode(copiedMessage, destination)));
               kernelRequest = kernelQueue.send(copiedMessage, options);
            } catch (QuotaException var38) {
               rae = new ResourceAllocationException(var38.toString());
               rae.setLinkedException(var38);
               throw rae;
            } catch (KernelException var39) {
               throw new weblogic.jms.common.JMSException(var39);
            }

            ProducerSendResponseImpl psri;
            if (kernelRequest == null) {
               if (copiedMessage != null) {
                  psri = new ProducerSendResponseImpl(copiedMessage.getId(), persistent, priority, timeToLive, timeToDeliver, redeliveryLimit);
                  JMSProducer.sendReturn(psri, message, copiedMessage, forwarding, timeToDeliver, timeToLive, persistent ? 2 : 1, priority, destination);
               }

               return;
            }

            try {
               kernelRequest.getResult();
            } catch (QuotaException var36) {
               rae = new ResourceAllocationException(var36.toString());
               rae.setLinkedException(var36);
               throw rae;
            } catch (KernelException var37) {
               throw new weblogic.jms.common.JMSException(var37);
            }

            if (copiedMessage != null) {
               psri = new ProducerSendResponseImpl(copiedMessage.getId(), persistent, priority, timeToLive, timeToDeliver, redeliveryLimit);
               JMSProducer.sendReturn(psri, message, copiedMessage, forwarding, timeToDeliver, timeToLive, persistent ? 2 : 1, priority, destination);
            }
         } finally {
            if (session.getTransacted()) {
               session.suspend(txHelper);
            }

            TransactionHelper.popTransactionHelper();
         }

      }
   }

   public void send(Destination destination, Message message, int paramDeliveryMode, int paramPriority, long paramTimeToLive) throws JMSException {
      this.checkClosed();
      if (destination == null) {
         throw new weblogic.jms.common.JMSException("The destination is null");
      } else if (!(destination instanceof DestinationImpl)) {
         throw new weblogic.jms.common.JMSException("The destination passed to the client SAF implementation must befrom the proper initial context.  However, this destination is of type: " + destination.getClass().getName());
      } else {
         MessageImpl messageImpl = null;
         if (message != null) {
            if (!(message instanceof MessageImpl)) {
               throw new weblogic.jms.common.JMSException("A message of an unknown type was found.  It is of type " + message.getClass().getName());
            }

            messageImpl = (MessageImpl)message;
         }

         this.internalSend(this.session.getRoot(), this.session, (DestinationImpl)destination, messageImpl, false, paramDeliveryMode == 2, this.redeliveryLimit, paramPriority, paramTimeToLive, this.timeToDeliver, this.sendTimeout, this.session.getAttachJMSXUserId(), this.unitOfOrder);
      }
   }

   private synchronized void checkClosed() throws JMSException {
      if (this.closed) {
         throw new IllegalStateException("The message producer has been closed");
      }
   }

   private String getSequenceName(WLMessage message, DestinationImpl destination) {
      String sequenceName = message.getSAFSequenceName();
      return sequenceName != null ? sequenceName : destination.getSequenceName();
   }

   protected int getSequenceMode(WLMessage message, DestinationImpl destination) {
      int deliveryMode = ((MessageImpl)message).getJMSDeliveryMode();
      if (deliveryMode == 1 && destination.getNonPersistentQOS() != "Exactly-Once") {
         return 0;
      } else {
         boolean isForwarded = ((MessageImpl)message).isForwarded();
         boolean hasSAFSeqNumber = message.getSAFSeqNumber() != 0L;
         return hasSAFSeqNumber && isForwarded ? 2 : 1;
      }
   }

   public long getDeliveryDelay() throws JMSException {
      return this.getTimeToDeliver();
   }

   public void send(Message message, CompletionListener completionListener) throws JMSException {
      if (completionListener != null) {
         throw new weblogic.jms.common.JMSException("Async send is not allowed in the client SAF implementation");
      } else {
         this.send(message);
      }
   }

   public void send(Destination destination, Message message, CompletionListener completionListener) throws JMSException {
      if (completionListener != null) {
         throw new weblogic.jms.common.JMSException("Async send is not allowed in the client SAF implementation");
      } else {
         this.send(destination, message);
      }
   }

   public void send(Message message, int deliveryMode, int priotiy, long timetoLive, CompletionListener completionListener) throws JMSException {
      if (completionListener != null) {
         throw new weblogic.jms.common.JMSException("Async send is not allowed in the client SAF implementation");
      } else {
         this.send(message, deliveryMode, priotiy, timetoLive);
      }
   }

   public void send(Destination destination, Message message, int deliveryMode, int priotiy, long timetoLive, CompletionListener completionListener) throws JMSException {
      if (completionListener != null) {
         throw new weblogic.jms.common.JMSException("Async send is not allowed in the client SAF implementation");
      } else {
         this.send(destination, message, deliveryMode, priotiy, timetoLive);
      }
   }

   public void setDeliveryDelay(long delay) throws JMSException {
      if (delay < 0L) {
         throw new weblogic.jms.common.JMSException("Invalid delay value , delay value should be >=0");
      } else {
         this.setTimeToDeliver(delay);
      }
   }

   private static class ProducerSendResponseImpl implements ProducerSendResponse {
      private JMSMessageId messageID;
      private boolean persistent;
      private int priority;
      private long timeToLive;
      private long timeToDeliver;
      private int redeliveryLimit;

      private ProducerSendResponseImpl(JMSMessageId paramMessageID, boolean paramPersistent, int paramPriority, long paramTTL, long paramTTD, int paramRDL) {
         this.messageID = paramMessageID;
         this.persistent = paramPersistent;
         this.priority = paramPriority;
         this.timeToLive = paramTTL;
         this.timeToDeliver = paramTTD;
         this.redeliveryLimit = paramRDL;
      }

      public JMSMessageId getMessageId() {
         return this.messageID;
      }

      public boolean get90StyleMessageId() {
         return true;
      }

      public int getDeliveryMode() {
         return this.persistent ? 2 : 1;
      }

      public int getPriority() {
         return this.priority;
      }

      public long getTimeToLive() {
         return this.timeToLive > 0L ? this.timeToLive : -1L;
      }

      public long getTimeToDeliver() {
         return this.timeToDeliver > 0L ? this.timeToDeliver : -1L;
      }

      public int getRedeliveryLimit() {
         return this.redeliveryLimit;
      }

      // $FF: synthetic method
      ProducerSendResponseImpl(JMSMessageId x0, boolean x1, int x2, long x3, long x4, int x5, Object x6) {
         this(x0, x1, x2, x3, x4, x5);
      }
   }
}
