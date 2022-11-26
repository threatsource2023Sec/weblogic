package weblogic.jms.extensions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import weblogic.jms.common.MessageImpl;

public class JMSForwardHelper {
   private static int Default = 0;
   private static long LDefault = 0L;
   private static final String TIMESTAMP_FORMAT = System.getProperty("weblogic.jms.timeFormat");
   private static String timeFormat = null;

   public static final void Forward(WLMessageProducer producer, Message message, boolean restoreAfterDone) throws JMSException {
      ForwardInternal(producer, (Destination)null, message, Default, Default, LDefault, false, true, restoreAfterDone);
   }

   public static final void Forward(WLMessageProducer producer, Destination destination, Message message, boolean restoreAfterDone) throws JMSException {
      ForwardInternal(producer, destination, message, Default, Default, LDefault, false, true, restoreAfterDone);
   }

   public static final void Forward(WLMessageProducer producer, Message message, int deliveryMode, int priority, long timeToLive, boolean restoreAfterDone) throws JMSException {
      ForwardInternal(producer, (Destination)null, message, deliveryMode, priority, timeToLive, false, false, restoreAfterDone);
   }

   public static final void Forward(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long timeToLive, boolean restoreAfterDone) throws JMSException {
      ForwardInternal(producer, destination, message, deliveryMode, priority, timeToLive, false, false, restoreAfterDone);
   }

   public static final void ForwardFromMessage(WLMessageProducer producer, Message message, boolean restoreAfterDone) throws JMSException {
      ForwardFromMessage(producer, (Destination)null, message, restoreAfterDone, false);
   }

   public static final void ForwardFromMessage(WLMessageProducer producer, Destination destination, Message message, boolean restoreAfterDone) throws JMSException {
      ForwardFromMessage(producer, destination, message, restoreAfterDone, false);
   }

   public static final void ForwardFromMessage(WLMessageProducer producer, Message message, boolean restoreAfterDone, boolean unpackUOW) throws JMSException {
      ForwardFromMessage(producer, (Destination)null, message, restoreAfterDone, unpackUOW);
   }

   public static final void ForwardFromMessage(WLMessageProducer producer, Destination destination, Message message, boolean restoreAfterDone, boolean unpackUOW) throws JMSException {
      if (unpackUOW && message.getStringProperty("JMS_BEA_UnitOfWork") != null && message.getBooleanProperty("JMS_BEA_IsUnitOfWorkEnd") && message instanceof ObjectMessage && ((ObjectMessage)message).getObject() instanceof ArrayList) {
         boolean forward = ((MessageImpl)message).isForwardable();
         ArrayList msgList = (ArrayList)((ArrayList)((ObjectMessage)message).getObject());
         Iterator itr = msgList.iterator();
         MessageImpl uowComponent = null;

         while(itr.hasNext()) {
            uowComponent = (MessageImpl)itr.next();
            uowComponent.setForward(forward);
            ForwardInternal(producer, destination, uowComponent, uowComponent.getJMSDeliveryMode(), uowComponent.getJMSPriority(), getRelativeTimeToLive(uowComponent), true, false, restoreAfterDone);
         }
      } else {
         ForwardInternal(producer, destination, message, message.getJMSDeliveryMode(), message.getJMSPriority(), getRelativeTimeToLive(message), true, false, restoreAfterDone);
      }

   }

   private static final void ForwardInternal(WLMessageProducer producer, Destination destination, Message message, int deliveryMode, int priority, long ttl, boolean fromMessage, boolean defaultCommandLine, boolean restoreAfterDone) throws JMSException {
      Preserve preserve = null;

      try {
         preserve = new Preserve(producer, message, fromMessage, restoreAfterDone);
         if (destination == null) {
            if (defaultCommandLine) {
               producer.forward(message);
            } else {
               producer.forward(message, deliveryMode, priority, ttl);
            }
         } else if (defaultCommandLine) {
            producer.forward(destination, message);
         } else {
            producer.forward(destination, message, deliveryMode, priority, ttl);
         }
      } finally {
         if (preserve != null) {
            preserve.restore();
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
            if (timeToLive == 0L) {
               timeToLive = -1L;
            }
         }
      } else {
         timeToLive = 0L;
      }

      return timeToLive;
   }

   public static void copyHeaders(Message in, Message out) throws JMSException {
      if (in.propertyExists("JMS_BEA_UnitOfWork")) {
         out.setStringProperty("JMS_BEA_UnitOfWork", in.getStringProperty("JMS_BEA_UnitOfWork"));
      }

      if (in.propertyExists("JMS_BEA_UnitOfWorkSequenceNumber")) {
         out.setIntProperty("JMS_BEA_UnitOfWorkSequenceNumber", in.getIntProperty("JMS_BEA_UnitOfWorkSequenceNumber"));
      }

      if (in.propertyExists("JMS_BEA_IsUnitOfWorkEnd")) {
         out.setBooleanProperty("JMS_BEA_IsUnitOfWorkEnd", in.getBooleanProperty("JMS_BEA_IsUnitOfWorkEnd"));
      }

   }

   public static final String getFormattedTime(long millis) {
      return isCustomDateFormat() ? (new SimpleDateFormat(timeFormat)).format(millis) : (new Date(millis)).toString();
   }

   private static final boolean isCustomDateFormat() {
      return timeFormat != null;
   }

   static {
      try {
         if (TIMESTAMP_FORMAT != null && TIMESTAMP_FORMAT.length() > 0) {
            timeFormat = TIMESTAMP_FORMAT.replace(":space:", " ");
            new SimpleDateFormat(timeFormat);
         }
      } catch (Throwable var1) {
         timeFormat = null;
      }

   }

   private static class Preserve {
      private long absoluteExpirationTime;
      private long absoluteTimeToDeliver;
      private Destination destination;
      private int deliveryMode;
      private boolean redelivered;
      private int priority;
      private int redeliveryLimit;
      private String unitOfOrderName;
      private boolean forwardFlag;
      private WLMessage message;
      private WLMessageProducer producer;
      private String unitOfOrderName_p;
      private long timeToDelivery_p;
      private int redeliveryLimit_p;
      private boolean restoreAfterDone;
      private boolean fromMessage;
      private String jmsxuserid = null;

      Preserve(WLMessageProducer producer, Message msg, boolean fromMessage, boolean restoreAfterDone) throws JMSException {
         this.producer = producer;
         this.message = (WLMessage)msg;
         this.fromMessage = fromMessage;
         this.restoreAfterDone = restoreAfterDone;
         if (restoreAfterDone) {
            this.absoluteExpirationTime = this.message.getJMSExpiration();
            this.destination = this.message.getJMSDestination();
            this.deliveryMode = this.message.getJMSDeliveryMode();
            this.redelivered = this.message.getJMSRedelivered();
            this.priority = this.message.getJMSPriority();
            this.forwardFlag = ((MessageImpl)this.message).isForwardable();
            if (this.message.propertyExists("JMSXUserID")) {
               this.jmsxuserid = this.message.getStringProperty("JMSXUserID");
            }
         }

         if (restoreAfterDone || fromMessage) {
            this.unitOfOrderName = ((MessageImpl)this.message).getUnitOfOrder();
            this.redeliveryLimit = this.message.getJMSRedeliveryLimit();
            this.absoluteTimeToDeliver = this.message.getJMSDeliveryTime();
         }

         if (fromMessage) {
            this.unitOfOrderName_p = producer.getUnitOfOrder();
            this.timeToDelivery_p = producer.getTimeToDeliver();
            this.redeliveryLimit_p = producer.getRedeliveryLimit();
            producer.setUnitOfOrder(this.unitOfOrderName);
            producer.setRedeliveryLimit(this.redeliveryLimit);
            if (this.absoluteTimeToDeliver > System.currentTimeMillis()) {
               producer.setTimeToDeliver(this.absoluteTimeToDeliver - System.currentTimeMillis());
            } else {
               producer.setTimeToDeliver(0L);
            }
         }

      }

      void restore() throws JMSException {
         if (this.restoreAfterDone) {
            this.message.setJMSExpiration(this.absoluteExpirationTime);
            this.message.setJMSDeliveryTime(this.absoluteTimeToDeliver);
            this.message.setJMSDestination(this.destination);
            this.message.setJMSDeliveryMode(this.deliveryMode);
            this.message.setJMSRedelivered(this.redelivered);
            this.message.setJMSPriority(this.priority);
            this.message.setJMSRedeliveryLimit(this.redeliveryLimit);
            ((MessageImpl)this.message).setUnitOfOrderName(this.unitOfOrderName);
            ((MessageImpl)this.message).setForward(this.forwardFlag);
            ((MessageImpl)this.message).setJMSXUserID(this.jmsxuserid);
         }

         if (this.fromMessage) {
            this.producer.setUnitOfOrder(this.unitOfOrderName_p);
            this.producer.setTimeToDeliver(this.timeToDelivery_p);
            this.producer.setRedeliveryLimit(this.redeliveryLimit_p);
         }

      }
   }
}
