package weblogic.jms.client;

import java.io.Serializable;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.BytesMessage;
import javax.jms.CompletionListener;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.TypeConverter;
import weblogic.jms.common.WLJMSRuntimeException;
import weblogic.jms.common.WLMessageFormatRuntimeException;
import weblogic.jms.extensions.WLJMSProducer;
import weblogic.jms.extensions.WLMessageProducer;

public class JMSProducerImpl implements WLJMSProducer {
   JMSContextImpl contextImpl;
   boolean disableMessageID = false;
   boolean disableMessageTimestamp = false;
   int deliveryMode = 2;
   int priority = 4;
   long timeToLive = 0L;
   long deliveryDelay = -1L;
   String jmsCorrelationID = null;
   byte[] jmsCorrelationIDAsBytes = null;
   String jmsType = null;
   Destination jmsReplyTo = null;
   Hashtable properties = new Hashtable();
   private CompletionListener completionListener = null;
   private int redeliveryLimit;
   private long sendTimeout;
   private String unitOfOrder;
   private int compressionThreshold;

   public JMSProducerImpl(javax.jms.JMSContext contextImpl) {
      this.contextImpl = (JMSContextImpl)contextImpl;
      this.redeliveryLimit = this.contextImpl.getDefaultRedeliveryLimit();
      this.sendTimeout = ((WLProducerImpl)this.contextImpl.getMessageProducer()).getPhysicalJMSConnection().getSendTimeout();
      this.unitOfOrder = this.contextImpl.getDefaultUnitOfOrder();
      this.compressionThreshold = this.contextImpl.getDefaultCompressionThreshold();
   }

   public javax.jms.JMSProducer send(Destination destination, Message message) {
      this.contextImpl.checkNotClosed();
      this.checkMessage(message);
      this.configureMessageProducer();
      this.configureMessage(message);

      try {
         if (this.completionListener == null) {
            this.contextImpl.getMessageProducer().send(destination, message);
         } else {
            this.contextImpl.getMessageProducer().send(destination, message, this.completionListener);
         }

         return this;
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public javax.jms.JMSProducer send(Destination destination, String payload) {
      this.contextImpl.checkNotClosed();
      this.configureMessageProducer();
      TextMessage textMessage;
      if (payload == null) {
         textMessage = this.contextImpl.createTextMessage();
      } else {
         textMessage = this.contextImpl.createTextMessage(payload);
      }

      this.configureMessage(textMessage);

      try {
         if (this.completionListener == null) {
            this.contextImpl.getMessageProducer().send(destination, textMessage);
         } else {
            this.contextImpl.getMessageProducer().send(destination, textMessage, this.completionListener);
         }

         return this;
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }
   }

   public javax.jms.JMSProducer send(Destination destination, Map payload) {
      this.contextImpl.checkNotClosed();
      this.configureMessageProducer();
      MapMessage mapMessage = this.contextImpl.createMapMessage();
      this.configureMessage(mapMessage);
      if (payload != null) {
         try {
            Iterator entryIter = payload.entrySet().iterator();

            while(entryIter.hasNext()) {
               Map.Entry thisEntry = (Map.Entry)entryIter.next();
               mapMessage.setObject((String)thisEntry.getKey(), thisEntry.getValue());
            }
         } catch (JMSException var7) {
            throw WLJMSRuntimeException.convertJMSException(var7);
         }
      }

      try {
         if (this.completionListener == null) {
            this.contextImpl.getMessageProducer().send(destination, mapMessage);
         } else {
            this.contextImpl.getMessageProducer().send(destination, mapMessage, this.completionListener);
         }

         return this;
      } catch (JMSException var6) {
         throw WLJMSRuntimeException.convertJMSException(var6);
      }
   }

   public javax.jms.JMSProducer send(Destination destination, byte[] payload) {
      this.contextImpl.checkNotClosed();
      this.configureMessageProducer();
      BytesMessage bytesMessage = this.contextImpl.createBytesMessage();
      this.configureMessage(bytesMessage);
      if (payload != null) {
         try {
            bytesMessage.writeBytes(payload);
         } catch (JMSException var6) {
            throw WLJMSRuntimeException.convertJMSException(var6);
         }
      }

      try {
         if (this.completionListener == null) {
            this.contextImpl.getMessageProducer().send(destination, bytesMessage);
         } else {
            this.contextImpl.getMessageProducer().send(destination, bytesMessage, this.completionListener);
         }

         return this;
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }
   }

   public javax.jms.JMSProducer send(Destination destination, Serializable payload) {
      this.contextImpl.checkNotClosed();
      this.configureMessageProducer();
      ObjectMessage objectMessage = this.contextImpl.createObjectMessage(payload);
      this.configureMessage(objectMessage);

      try {
         if (this.completionListener == null) {
            this.contextImpl.getMessageProducer().send(destination, objectMessage);
         } else {
            this.contextImpl.getMessageProducer().send(destination, objectMessage, this.completionListener);
         }

         return this;
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }
   }

   private void configureMessageProducer() {
      MessageProducer messageProducer = this.contextImpl.getMessageProducer();

      try {
         messageProducer.setPriority(this.priority);
         if (this.deliveryDelay != -1L) {
            messageProducer.setDeliveryDelay(this.deliveryDelay);
         } else {
            long delay = this.getDeliveryDelay();
            messageProducer.setDeliveryDelay(delay);
         }

         messageProducer.setDeliveryMode(this.deliveryMode);
         messageProducer.setTimeToLive(this.timeToLive);
         messageProducer.setDisableMessageID(this.disableMessageID);
         messageProducer.setDisableMessageTimestamp(this.disableMessageTimestamp);
         WLMessageProducer wlMessageProducer = (WLMessageProducer)messageProducer;
         wlMessageProducer.setRedeliveryLimit(this.redeliveryLimit);
         wlMessageProducer.setSendTimeout(this.sendTimeout);
         wlMessageProducer.setUnitOfOrder(this.unitOfOrder);
         wlMessageProducer.setCompressionThreshold(this.compressionThreshold);
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   private void checkMessage(Message message) {
      if (message == null) {
         throw new WLMessageFormatRuntimeException(JMSClientExceptionLogger.logMessageIsNullLoggable());
      }
   }

   private void configureMessage(Message message) {
      try {
         if (this.jmsCorrelationID != null) {
            message.setJMSCorrelationID(this.jmsCorrelationID);
         }

         if (this.jmsCorrelationIDAsBytes != null) {
            message.setJMSCorrelationIDAsBytes(this.jmsCorrelationIDAsBytes);
         }

         if (this.jmsType != null) {
            message.setJMSType(this.jmsType);
         }

         if (this.jmsReplyTo != null) {
            message.setJMSReplyTo(this.jmsReplyTo);
         }

         Iterator iterator = this.properties.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry thisEntry = (Map.Entry)iterator.next();
            message.setObjectProperty((String)thisEntry.getKey(), thisEntry.getValue());
         }

      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public javax.jms.JMSProducer setDisableMessageID(boolean disableMessageID) {
      this.contextImpl.checkNotClosed();
      this.disableMessageID = disableMessageID;
      return this;
   }

   public boolean getDisableMessageID() {
      this.contextImpl.checkNotClosed();
      return this.disableMessageID;
   }

   public javax.jms.JMSProducer setDisableMessageTimestamp(boolean disableMessageTimestamp) {
      this.contextImpl.checkNotClosed();
      this.disableMessageTimestamp = disableMessageTimestamp;
      return this;
   }

   public boolean getDisableMessageTimestamp() {
      this.contextImpl.checkNotClosed();
      return this.disableMessageTimestamp;
   }

   public javax.jms.JMSProducer setDeliveryMode(int deliveryMode) {
      this.contextImpl.checkNotClosed();
      if (deliveryMode != 1 && deliveryMode != 2) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logInvalidJMSProducerDeliveryModeLoggable(deliveryMode));
      } else {
         this.deliveryMode = deliveryMode;
         return this;
      }
   }

   public int getDeliveryMode() {
      this.contextImpl.checkNotClosed();
      return this.deliveryMode;
   }

   public javax.jms.JMSProducer setPriority(int priority) {
      this.contextImpl.checkNotClosed();
      if (priority >= 0 && priority <= 9) {
         this.priority = priority;
         return this;
      } else {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logInvalidJMSProducerDeliveryModeLoggable(this.deliveryMode));
      }
   }

   public int getPriority() {
      this.contextImpl.checkNotClosed();
      return this.priority;
   }

   public javax.jms.JMSProducer setTimeToLive(long timeToLive) {
      this.contextImpl.checkNotClosed();
      this.timeToLive = timeToLive;
      return this;
   }

   public long getTimeToLive() {
      this.contextImpl.checkNotClosed();
      return this.timeToLive;
   }

   public javax.jms.JMSProducer setDeliveryDelay(long deliveryDelay) {
      this.contextImpl.checkNotClosed();
      if (deliveryDelay < 0L) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logInvalidJMSDeliveryDelayLoggable().getMessage());
      } else {
         this.deliveryDelay = deliveryDelay;
         return this;
      }
   }

   public long getDeliveryDelay() {
      this.contextImpl.checkNotClosed();
      return this.deliveryDelay != -1L ? this.deliveryDelay : ((WLProducerImpl)this.contextImpl.getMessageProducer()).getPhysicalJMSConnection().getTimeToDeliver();
   }

   public javax.jms.JMSProducer setAsync(CompletionListener completionListener) {
      if (this.contextImpl.getContainerType() == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.contextImpl.checkNotClosed();
         this.completionListener = completionListener;
         return this;
      }
   }

   public CompletionListener getAsync() {
      this.contextImpl.checkNotClosed();
      return this.completionListener;
   }

   public javax.jms.JMSProducer setProperty(String name, boolean value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   private void checkAndSetProperty(String name, Object value) {
      checkPropertyNameSet(name);
      checkValidPropertyValue(name, value);
      checkValidPropertyName(name);
      this.properties.put(name, value);
   }

   public javax.jms.JMSProducer setProperty(String name, byte value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, short value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, int value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, long value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, float value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, double value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, String value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer setProperty(String name, Object value) {
      this.contextImpl.checkNotClosed();
      this.checkAndSetProperty(name, value);
      return this;
   }

   public javax.jms.JMSProducer clearProperties() {
      this.contextImpl.checkNotClosed();
      this.properties.clear();
      return this;
   }

   public boolean propertyExists(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);
      return this.properties.containsKey(name);
   }

   public boolean getBooleanProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toBoolean(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public byte getByteProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toByte(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public short getShortProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toShort(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public int getIntProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toInt(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public long getLongProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toLong(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public float getFloatProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toFloat(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public double getDoubleProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toDouble(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public String getStringProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);

      try {
         return TypeConverter.toString(this.properties.get(name));
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public Object getObjectProperty(String name) {
      this.contextImpl.checkNotClosed();
      checkPropertyNameSet(name);
      Object obj = this.properties.get(name);
      return obj;
   }

   public Set getPropertyNames() {
      this.contextImpl.checkNotClosed();
      return Collections.unmodifiableSet(this.properties.keySet());
   }

   public javax.jms.JMSProducer setJMSCorrelationIDAsBytes(byte[] correlationID) {
      this.contextImpl.checkNotClosed();
      this.jmsCorrelationIDAsBytes = correlationID;
      this.jmsCorrelationID = null;
      return this;
   }

   public byte[] getJMSCorrelationIDAsBytes() {
      this.contextImpl.checkNotClosed();
      return this.jmsCorrelationIDAsBytes;
   }

   public javax.jms.JMSProducer setJMSCorrelationID(String correlationID) {
      this.contextImpl.checkNotClosed();
      this.jmsCorrelationID = correlationID;
      this.jmsCorrelationIDAsBytes = null;
      return this;
   }

   public String getJMSCorrelationID() {
      this.contextImpl.checkNotClosed();
      return this.jmsCorrelationID;
   }

   public javax.jms.JMSProducer setJMSType(String type) {
      this.contextImpl.checkNotClosed();
      this.jmsType = type;
      return this;
   }

   public String getJMSType() {
      this.contextImpl.checkNotClosed();
      return this.jmsType;
   }

   public javax.jms.JMSProducer setJMSReplyTo(Destination replyTo) {
      this.contextImpl.checkNotClosed();
      this.jmsReplyTo = replyTo;
      return this;
   }

   public Destination getJMSReplyTo() {
      this.contextImpl.checkNotClosed();
      return this.jmsReplyTo;
   }

   private static void checkPropertyNameSet(String name) {
      if (name == null) {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logJMSProducerPropertyNameEmpty());
      } else if ("".equals(name)) {
         throw new IllegalArgumentException(JMSClientExceptionLogger.logJMSProducerPropertyNameEmpty());
      }
   }

   private static void checkValidPropertyValue(String name, Object value) {
      if (!(value instanceof Boolean) && !(value instanceof Byte) && !(value instanceof Short) && !(value instanceof Integer) && !(value instanceof Long) && !(value instanceof Float) && !(value instanceof Double) && !(value instanceof String)) {
         throw new WLMessageFormatRuntimeException(JMSClientExceptionLogger.logJMSProducerPropertyValueInvalidLoggable(value.getClass().toString()));
      }
   }

   private static void checkValidPropertyName(String name) {
      if (!"NULL".equalsIgnoreCase(name) && !"TRUE".equalsIgnoreCase(name) && !"FALSE".equalsIgnoreCase(name) && !"NOT".equalsIgnoreCase(name) && !"AND".equalsIgnoreCase(name) && !"OR".equalsIgnoreCase(name) && !"BETWEEN".equalsIgnoreCase(name) && !"LIKE".equalsIgnoreCase(name) && !"IN".equalsIgnoreCase(name) && !"IS".equalsIgnoreCase(name)) {
         char[] namechars = name.toCharArray();
         if (Character.isJavaIdentifierStart(namechars[0])) {
            for(int i = 1; i < namechars.length; ++i) {
               if (!Character.isJavaIdentifierPart(namechars[i])) {
                  throw new WLJMSRuntimeException(JMSClientExceptionLogger.logJMSProducerPropertyNameHasBadCharLoggable(name));
               }
            }

         } else {
            throw new WLJMSRuntimeException(JMSClientExceptionLogger.logJMSProducerPropertyNameHasBadFirstCharLoggable(name));
         }
      } else {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logJMSProducerPropertyNameReservedLoggable(name));
      }
   }

   public int getRedeliveryLimit() {
      this.contextImpl.checkNotClosed();
      return this.redeliveryLimit;
   }

   public WLJMSProducer setRedeliveryLimit(int redeliveryLimit) {
      this.contextImpl.checkNotClosed();
      if (redeliveryLimit < -1) {
         throw new JMSRuntimeException(JMSClientExceptionLogger.logInvalidRedeliveryLimitLoggable().getMessage());
      } else {
         this.redeliveryLimit = redeliveryLimit;
         return this;
      }
   }

   public long getSendTimeout() {
      this.contextImpl.checkNotClosed();
      return this.sendTimeout;
   }

   public WLJMSProducer setSendTimeout(long sendTimeout) {
      this.contextImpl.checkNotClosed();
      if (sendTimeout < 0L) {
         throw new JMSRuntimeException(JMSClientExceptionLogger.logInvalidSendTimeoutLoggable().getMessage());
      } else {
         this.sendTimeout = sendTimeout;
         return this;
      }
   }

   public String getUnitOfOrder() {
      this.contextImpl.checkNotClosed();
      return this.unitOfOrder;
   }

   public WLJMSProducer setUnitOfOrder(String name) {
      this.contextImpl.checkNotClosed();
      this.unitOfOrder = name;
      return this;
   }

   public WLJMSProducer setUnitOfOrder() {
      this.contextImpl.checkNotClosed();

      try {
         WLMessageProducer wlmessageproducer = (WLMessageProducer)this.contextImpl.getMessageProducer();
         String oldUnitOfOrder = wlmessageproducer.getUnitOfOrder();
         wlmessageproducer.setUnitOfOrder();
         String generatedUnitOfOrder = wlmessageproducer.getUnitOfOrder();
         wlmessageproducer.setUnitOfOrder(oldUnitOfOrder);
         this.unitOfOrder = generatedUnitOfOrder;
         return this;
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public WLJMSProducer forward(Destination destination, Message message) {
      this.contextImpl.checkNotClosed();
      this.checkMessage(message);
      this.configureMessageProducer();
      this.configureMessage(message);

      try {
         if (this.completionListener == null) {
            ((WLMessageProducer)this.contextImpl.getMessageProducer()).forward(destination, message);
            return this;
         } else {
            throw new UnsupportedOperationException(JMSClientExceptionLogger.logAsyncForwardUnsupportedLoggable().getMessage());
         }
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public WLJMSProducer setCompressionThreshold(int limit) {
      this.contextImpl.checkNotClosed();
      if (limit < 0) {
         throw new JMSRuntimeException(JMSClientExceptionLogger.logInvalidCompressionThresholdLoggable().getMessage());
      } else {
         this.compressionThreshold = limit;
         return this;
      }
   }

   public int getCompressionThreshold() {
      this.contextImpl.checkNotClosed();
      return this.compressionThreshold;
   }
}
