package weblogic.messaging.interception.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.WLMessageProducer;

public class JMSHelper {
   public static final void Send(WLMessageProducer producer, Message message) throws JMSException {
      SendInternal(producer, (Destination)null, message);
   }

   public static final void Send(WLMessageProducer producer, Destination destination, Message message) throws JMSException {
      SendInternal(producer, destination, message);
   }

   public static final void Send(WLMessageProducer producer, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      SendInternal(producer, (Destination)null, message, deliveryMode, priority, timeToLive);
   }

   public static final void Send(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      SendInternal(producer, destination, message, deliveryMode, priority, timeToLive);
   }

   public static final void SendFromMessage(WLMessageProducer producer, Message message) throws JMSException {
      SendInternal(producer, (Destination)null, message, message.getJMSDeliveryMode(), message.getJMSPriority(), getRelativeTimeToLive(message));
   }

   public static final void SendFromMessage(WLMessageProducer producer, Destination destination, Message message) throws JMSException {
      SendInternal(producer, destination, message, message.getJMSDeliveryMode(), message.getJMSPriority(), getRelativeTimeToLive(message));
   }

   private static final void SendInternal(WLMessageProducer producer, Destination destination, Message message) throws JMSException {
      CopiedMessage savedMessage = null;

      try {
         savedMessage = new CopiedMessage(message);
         if (destination == null) {
            producer.send(message);
         } else {
            producer.send(destination, message);
         }
      } finally {
         if (savedMessage != null) {
            savedMessage.restoreMessage();
         }

      }

   }

   private static final void SendInternal(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long ttl) throws JMSException {
      CopiedMessage savedMessage = null;

      try {
         savedMessage = new CopiedMessage(message);
         if (destination == null) {
            producer.send(message, deliveryMode, priority, ttl);
         } else {
            producer.send(destination, message, deliveryMode, priority, ttl);
         }
      } finally {
         if (savedMessage != null) {
            savedMessage.restoreMessage();
         }

      }

   }

   public static final void Forward(WLMessageProducer producer, Message message) throws JMSException {
      ForwardInternal(producer, (Destination)null, message, false);
   }

   public static final void Forward(WLMessageProducer producer, Destination destination, Message message) throws JMSException {
      ForwardInternal(producer, destination, message, false);
   }

   public static final void Forward(WLMessageProducer producer, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      ForwardInternal(producer, (Destination)null, message, deliveryMode, priority, timeToLive, false);
   }

   public static final void Forward(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      ForwardInternal(producer, destination, message, deliveryMode, priority, timeToLive, false);
   }

   public static final void ForwardFromMessage(WLMessageProducer producer, Message message) throws JMSException {
      ForwardInternal(producer, (Destination)null, message, message.getJMSDeliveryMode(), message.getJMSPriority(), getRelativeTimeToLive(message), true);
   }

   public static final void ForwardFromMessage(WLMessageProducer producer, Destination destination, Message message) throws JMSException {
      ForwardInternal(producer, destination, message, message.getJMSDeliveryMode(), message.getJMSPriority(), getRelativeTimeToLive(message), true);
   }

   private static final void ForwardInternal(WLMessageProducer producer, Destination destination, Message message, boolean fromMessage) throws JMSException {
      CopiedMessage savedMessage = null;

      try {
         if (fromMessage) {
            savedMessage = new CopiedMessage(producer, message);
         } else {
            savedMessage = new CopiedMessage(message);
         }

         if (destination == null) {
            producer.forward(message);
         } else {
            producer.forward(destination, message);
         }
      } finally {
         if (savedMessage != null) {
            savedMessage.restoreMessage();
         }

      }

   }

   private static final void ForwardInternal(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long ttl, boolean fromMessage) throws JMSException {
      CopiedMessage savedMessage = null;

      try {
         if (fromMessage) {
            savedMessage = new CopiedMessage(producer, message);
         } else {
            savedMessage = new CopiedMessage(message);
         }

         if (destination == null) {
            producer.forward(message, deliveryMode, priority, ttl);
         } else {
            producer.forward(destination, message, deliveryMode, priority, ttl);
         }
      } finally {
         if (savedMessage != null) {
            savedMessage.restoreMessage();
         }

      }

   }

   public static long getRelativeTimeToLive(Message message) throws JMSException {
      long timeToLive = message.getJMSExpiration();
      if (timeToLive != 0L) {
         if (timeToLive < 0L) {
            if (Long.MAX_VALUE + timeToLive <= System.currentTimeMillis()) {
               timeToLive = Long.MIN_VALUE;
            } else {
               timeToLive -= System.currentTimeMillis();
            }
         } else {
            timeToLive -= System.currentTimeMillis();
         }
      } else {
         timeToLive = 0L;
      }

      return timeToLive;
   }

   public static final void ForwardPreserveMsgProperty(WLMessageProducer producer, Message message) throws JMSException {
      producer.setUnitOfOrder(message.getStringProperty("JMS_BEA_UnitOfOrder"));
      producer.setRedeliveryLimit(message.getIntProperty("JMS_BEA_RedeliveryLimit"));
      producer.forward(message, message.getJMSDeliveryMode(), message.getJMSPriority(), getRelativeTimeToLive(message));
   }

   private static class CopiedMessage {
      private long absoluteExpirationTime;
      private long absoluteTimeToDeliver;
      private Destination destination;
      private int deliveryMode;
      private boolean redelivered;
      private int priority;
      private int redeliveryLimit;
      private JMSID connectionId;
      private JMSMessageId msgId;
      private boolean ddforwarded;
      private String unitOfOrderName;
      private boolean forwardFlag;
      private MessageImpl message;
      private WLMessageProducer producer;
      private String unitOfOrderName_p;
      private long timeToDelivery_p;
      private int redeliveryLimit_p;

      CopiedMessage(WLMessageProducer producer, Message msg) throws JMSException {
         this(msg);
         this.producer = producer;
         this.unitOfOrderName_p = producer.getUnitOfOrder();
         this.timeToDelivery_p = producer.getTimeToDeliver();
         this.redeliveryLimit_p = producer.getRedeliveryLimit();
         producer.setUnitOfOrder(this.unitOfOrderName);
         producer.setRedeliveryLimit(this.redeliveryLimit);
         if (this.absoluteTimeToDeliver > 0L && this.absoluteTimeToDeliver > System.currentTimeMillis()) {
            producer.setTimeToDeliver(this.absoluteTimeToDeliver - System.currentTimeMillis());
         } else {
            producer.setTimeToDeliver(0L);
         }

      }

      CopiedMessage(Message msg) throws JMSException {
         this.message = (MessageImpl)msg;
         this.absoluteExpirationTime = this.message.getJMSExpiration();
         this.absoluteTimeToDeliver = this.message.getJMSDeliveryTime();
         this.destination = this.message.getJMSDestination();
         this.deliveryMode = this.message.getJMSDeliveryMode();
         this.redelivered = this.message.getJMSRedelivered();
         this.priority = this.message.getJMSPriority();
         this.redeliveryLimit = this.message.getJMSRedeliveryLimit();
         this.connectionId = this.message.getConnectionId();
         this.msgId = this.message.getId();
         this.ddforwarded = this.message.getDDForwarded();
         this.unitOfOrderName = this.message.getUnitOfOrder();
         this.forwardFlag = this.message.isForwardable();
      }

      void restoreMessage() throws JMSException {
         this.message.setJMSExpiration(this.absoluteExpirationTime);
         this.message.setJMSDeliveryTime(this.absoluteTimeToDeliver);
         this.message.setJMSDestination(this.destination);
         this.message.setJMSDeliveryMode(this.deliveryMode);
         this.message.setJMSRedelivered(this.redelivered);
         this.message.setJMSPriority(this.priority);
         this.message.setJMSRedeliveryLimit(this.redeliveryLimit);
         this.message.setConnectionId(this.connectionId);
         this.message.setId(this.msgId);
         this.message.setDDForwarded(this.ddforwarded);
         this.message.setUnitOfOrderName(this.unitOfOrderName);
         this.message.setForward(this.forwardFlag);
         if (this.producer != null) {
            this.producer.setUnitOfOrder(this.unitOfOrderName_p);
            this.producer.setTimeToDeliver(this.timeToDelivery_p);
            this.producer.setRedeliveryLimit(this.redeliveryLimit_p);
         }

      }
   }
}
