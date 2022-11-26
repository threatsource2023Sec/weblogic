package weblogic.jms.dotnet.proxy.protocol;

import java.util.Enumeration;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.jms.Message;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.extensions.WLMessage;

public abstract class ProxyMessageImpl implements MarshalReadable, MarshalWritable {
   private static final int EXTVERSION = 1;
   public static final byte NULLMESSAGEIMPL = 0;
   public static final byte BYTESMESSAGEIMPL = 1;
   public static final byte HDRMESSAGEIMPL = 2;
   public static final byte MAPMESSAGEIMPL = 3;
   public static final byte OBJECTMESSAGEIMPL = 4;
   public static final byte STREAMMESSAGEIMPL = 5;
   public static final byte TEXTMESSAGEIMPL = 6;
   static final String[] headerFields = new String[]{"JMSCorrelationID", "JMSDeliveryMode", "JMSDestination", "JMSExpiration", "JMSPriority", "JMSRedelivered", "JMSReplyTo", "JMSTimestamp", "JMSType"};
   private int deliveryMode;
   private int deliveryCount;
   private int redeliveryLimit;
   private int seed;
   private long timestamp;
   private int counter;
   private long expiration;
   private byte priority;
   private String messageIdString;
   private JMSMessageId messageId;
   private String correlationId;
   private ProxyDestinationImpl destination;
   private ProxyDestinationImpl replyTo;
   private String type;
   private PrimitiveMap properties;
   private boolean redelivered;
   private long sequenceNumber;
   private int pipelineGeneration;
   private transient Message message;
   private static final int _IS_PERSISTENT = 1;
   private static final int _HAS_CORRID = 2;
   private static final int _HAS_DESTINATION = 3;
   private static final int _HAS_REPLYTO = 4;
   private static final int _IS_REDELIVERED = 5;
   private static final int _HAS_TYPE = 6;
   private static final int _HAS_EXPIRATION = 7;
   private static final int _HAS_STRMESSAGEID = 8;
   private static final int _HAS_PROPERTIES = 9;
   private static final int _HAS_TIMESTAMP = 10;
   private static final int _HAS_SEQUENCE_NUMBER = 11;
   private static final int _HAS_PIPELINE_GENERATION = 12;
   private static final int _HAS_DELIVERY_COUNT = 13;
   private static final int _HAS_REDELIVERY_LIMIT = 14;
   private static final int _HAS_WLMESSAGEID = 15;

   public ProxyMessageImpl() {
      this.deliveryMode = -1;
      this.deliveryCount = 1;
      this.redeliveryLimit = -1;
      this.timestamp = -1L;
      this.priority = -1;
      this.sequenceNumber = -1L;
      this.pipelineGeneration = 0;
      this.properties = new PrimitiveMap();
   }

   public abstract byte getType();

   public static ProxyMessageImpl createMessageImpl(byte type) {
      switch (type) {
         case 1:
            return new ProxyBytesMessageImpl();
         case 2:
            return new ProxyHdrMessageImpl();
         case 3:
            return new ProxyMapMessageImpl();
         case 4:
            return new ProxyObjectMessageImpl();
         case 5:
            return new ProxyStreamMessageImpl();
         case 6:
            return new ProxyTextMessageImpl();
         default:
            return null;
      }
   }

   public ProxyMessageImpl(Message message) throws JMSException {
      this();
      this.initializeFromMessage(message);
   }

   public void setSequenceNumber(long seq) {
      this.sequenceNumber = seq;
   }

   public void setPipelineGeneration(int gen) {
      this.pipelineGeneration = gen;
   }

   public final void setRedelivered(boolean redelivered) {
      this.redelivered = redelivered;
   }

   public void setDeliveryCount(int deliveryCount) {
      this.deliveryCount = deliveryCount;
   }

   private void initializeFromMessage(Message message) throws JMSException {
      this.message = message;
      this.timestamp = message.getJMSTimestamp();
      this.type = message.getJMSType();
      this.correlationId = message.getJMSCorrelationID();
      if (message.getJMSReplyTo() != null) {
         this.replyTo = new ProxyDestinationImpl(message.getJMSReplyTo());
      } else {
         this.replyTo = null;
      }

      this.redelivered = message.getJMSRedelivered();
      this.expiration = message.getJMSExpiration();
      this.priority = (byte)message.getJMSPriority();
      this.deliveryMode = message.getJMSDeliveryMode();
      Enumeration props = message.getPropertyNames();
      if (props.hasMoreElements()) {
         this.properties = new PrimitiveMap();

         while(props.hasMoreElements()) {
            String name = (String)props.nextElement();
            if (!name.equals("JMSXDeliveryCount") && !name.equals("JMS_BEA_RedeliveryLimit")) {
               Object obj = message.getObjectProperty(name);
               this.setObjectProperty(name, obj);
            }
         }
      }

      if (message instanceof WLMessage) {
         if (((MessageImpl)message).isOldMessage()) {
            this.messageIdString = message.getJMSMessageID();
         } else {
            this.messageId = ((MessageImpl)message).getId();
            this.seed = this.messageId.getSeed();
            this.counter = this.messageId.getCounter();
         }

         this.sequenceNumber = ((WLMessage)message).getSequenceNumber();
         if (((WLMessage)message).getUnitOfOrder() != null) {
            this.properties.put((String)"JMS_BEA_UnitOfOrder", ((WLMessage)message).getUnitOfOrder());
         }

         this.deliveryCount = ((MessageImpl)message).getDeliveryCount();
         this.redeliveryLimit = ((WLMessage)message).getJMSRedeliveryLimit();
      } else {
         this.messageIdString = message.getJMSMessageID();
      }

   }

   public final String getCorrelationID() {
      return this.correlationId;
   }

   public final String getMessageID() {
      if (this.messageIdString != null) {
         return this.messageIdString;
      } else {
         return this.messageId != null ? "ID:" + this.messageId.toString() : "null";
      }
   }

   public final int getDeliveryMode() {
      return this.deliveryMode;
   }

   public final ProxyDestinationImpl getDestination() {
      return this.destination;
   }

   public final long getExpiration() {
      return this.expiration;
   }

   public final long getDeliveryTime() {
      return (Long)this.properties.get("JMS_BEA_DeliveryTime");
   }

   public final int getPriority() {
      return this.priority;
   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public Object getProperty(String name) {
      return this.properties.get(name);
   }

   public final void clearProperties() {
      if (this.properties != null) {
         this.properties.clear();
      }

   }

   boolean hasProperties() {
      return this.properties != null && !this.properties.isEmpty();
   }

   public final boolean propertyExists(String name) {
      return this.properties.get(name) != null;
   }

   public void removeProperty(String propertyName) {
      this.properties.remove(propertyName);
   }

   public final void setObjectProperty(String name, Object value) {
      this.properties.put(name, value);
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void populateJMSMessage(Message jmsMessage) throws JMSException {
      if (this.correlationId != null) {
         jmsMessage.setJMSCorrelationID(this.correlationId);
      }

      jmsMessage.setJMSType(this.type);
      if (this.replyTo != null) {
         jmsMessage.setJMSReplyTo(this.replyTo.getJMSDestination());
      }

      if (this.redelivered) {
         jmsMessage.setJMSRedelivered(true);
      }

      if (this.properties != null) {
         Iterator props = this.properties.keySet().iterator();

         while(props.hasNext()) {
            String name = (String)props.next();
            Object obj = this.properties.get(name);
            jmsMessage.setObjectProperty(name, obj);
         }
      }

   }

   public void marshal(MarshalWriter mw) {
      MarshalBitMask versionFlags = new MarshalBitMask(1);
      if (this.messageIdString != null) {
         versionFlags.setBit(8);
      }

      if (this.messageId != null) {
         versionFlags.setBit(15);
      }

      if (this.timestamp != -1L) {
         versionFlags.setBit(10);
      }

      if (this.deliveryMode == 2) {
         versionFlags.setBit(1);
      }

      if (this.correlationId != null) {
         versionFlags.setBit(2);
      }

      if (this.destination != null) {
         versionFlags.setBit(3);
      }

      if (this.replyTo != null) {
         versionFlags.setBit(4);
      }

      if (this.redelivered) {
         versionFlags.setBit(5);
      }

      if (this.type != null) {
         versionFlags.setBit(6);
      }

      if (this.deliveryCount != 1) {
         versionFlags.setBit(13);
      }

      if (this.redeliveryLimit != -1) {
         versionFlags.setBit(14);
      }

      if (this.expiration != 0L) {
         versionFlags.setBit(7);
      }

      boolean hasProperties = this.hasProperties();
      if (hasProperties) {
         versionFlags.setBit(9);
      }

      if (this.sequenceNumber != -1L) {
         versionFlags.setBit(11);
      }

      if (this.pipelineGeneration != 0) {
         versionFlags.setBit(12);
      }

      versionFlags.marshal(mw);
      if (this.messageIdString != null) {
         mw.writeString(this.messageIdString);
      }

      if (this.messageId != null) {
         mw.writeInt(this.seed);
         mw.writeInt(this.counter);
      }

      if (this.timestamp != -1L) {
         mw.writeLong(this.timestamp);
      }

      if (this.correlationId != null) {
         mw.writeString(this.correlationId);
      }

      if (this.destination != null) {
         this.destination.marshal(mw);
      }

      if (this.replyTo != null) {
         this.replyTo.marshal(mw);
      }

      if (this.type != null) {
         mw.writeString(this.type);
      }

      if (this.expiration != 0L) {
         mw.writeLong(this.expiration);
      }

      if (this.deliveryCount != 1) {
         mw.writeInt(this.deliveryCount);
      }

      if (this.redeliveryLimit != -1) {
         mw.writeInt(this.redeliveryLimit);
      }

      mw.writeByte(this.priority);
      if (hasProperties) {
         this.properties.marshal(mw);
      }

      if (this.sequenceNumber != -1L) {
         mw.writeLong(this.sequenceNumber);
      }

      if (this.pipelineGeneration != 0) {
         mw.writeInt(this.pipelineGeneration);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         this.deliveryMode = 2;
      } else {
         this.deliveryMode = 1;
      }

      if (versionFlags.isSet(8)) {
         this.messageIdString = mr.readString();
      }

      if (versionFlags.isSet(15)) {
         this.seed = mr.readInt();
         this.counter = mr.readInt();
         this.messageIdString = "ID:<" + this.seed + "." + this.timestamp + "." + this.counter + ">";
      }

      if (versionFlags.isSet(10)) {
         this.timestamp = mr.readLong();
      }

      if (versionFlags.isSet(2)) {
         this.correlationId = mr.readString();
      }

      if (versionFlags.isSet(3)) {
         this.destination = new ProxyDestinationImpl();
         this.destination.unmarshal(mr);
      }

      if (versionFlags.isSet(4)) {
         this.replyTo = new ProxyDestinationImpl();
         this.replyTo.unmarshal(mr);
      }

      this.redelivered = versionFlags.isSet(5);
      if (versionFlags.isSet(6)) {
         this.type = mr.readString();
      }

      if (versionFlags.isSet(7)) {
         this.expiration = mr.readLong();
      }

      if (versionFlags.isSet(13)) {
         this.deliveryCount = mr.readInt();
      }

      if (versionFlags.isSet(14)) {
         this.redeliveryLimit = mr.readInt();
      }

      this.priority = mr.readByte();
      if (versionFlags.isSet(9)) {
         this.properties = new PrimitiveMap();
         this.properties.unmarshal(mr);
      }

      if (versionFlags.isSet(11)) {
         this.sequenceNumber = mr.readLong();
      }

      if (versionFlags.isSet(12)) {
         this.pipelineGeneration = mr.readInt();
      }

   }

   public static boolean isHeaderField(String name) {
      if (name.startsWith("JMS")) {
         for(int i = 0; i < headerFields.length; ++i) {
            if (name.equals(headerFields[i])) {
               return true;
            }
         }
      }

      return false;
   }
}
