package weblogic.jms.common;

import java.io.ByteArrayInputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import org.w3c.dom.Document;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.client.JMSSession;
import weblogic.jms.extensions.WLAcknowledgeInfo;
import weblogic.jms.extensions.WLMessage;
import weblogic.messaging.MessageID;
import weblogic.messaging.common.MessagingUtilities;
import weblogic.messaging.kernel.PagingByteBufferObjectOutputStream;
import weblogic.store.common.PersistentStoreOutputStream;
import weblogic.utils.expressions.ExpressionMap;
import weblogic.utils.io.FilteringObjectInputStream;

public abstract class MessageImpl implements Message, weblogic.messaging.Message, WLMessage, ExpressionMap, Cloneable, Externalizable {
   static final long serialVersionUID = 7571220996297716034L;
   static final Properties defaultCompressionOption = new Properties();
   public static final String GZIP_COMPRESSION_FACTORY = GZIPCompressionFactoryImpl.class.getName();
   public static final String LZF_COMPRESSION_FACTORY = LZFCompressionFactoryImpl.class.getName();
   private static volatile Method getLengthMethodForWriteExternal = null;
   private static volatile boolean getLengthMethodForWriteExternalChecked = false;
   static final byte defaultCompressionTag;
   static final byte EXTVERSION1 = 1;
   static final byte EXTVERSION2 = 10;
   static final byte EXTVERSION3 = 20;
   static final byte EXTVERSION4 = 30;
   static final byte EXTVERSION5 = 40;
   public static final String USER_ID_PROPERTY_NAME = "JMSXUserID";
   public static final String UNIT_OF_ORDER_PROPERTY_NAME = "JMS_BEA_UnitOfOrder";
   public static final String SAF_SEQUENCE_NAME = "JMS_BEA_SAF_SEQUENCE_NAME";
   public static final String SAF_SEQUENCE_NUMBER = "JMS_BEA_SAF_SEQUENCE";
   public static final String DELIVERY_TIME_PROPERTY_NAME = "JMS_BEA_DeliveryTime";
   public static final String REDELIVERY_LIMIT_PROPERTY_NAME = "JMS_BEA_RedeliveryLimit";
   public static final String DELIVERY_COUNT_PROPERTY_NAME = "JMSXDeliveryCount";
   public static final String FOREIGNTIMESTAMP_MILLIS_PROPERTY_NAME = "WL_JMS_ForeignTimestampMillis";
   public static final String FOREIGNTIMESTAMP_STRING_PROPERTY_NAME = "WL_JMS_ForeignTimestampString";
   public static final String SIZE_PROPERTY_NAME = "JMS_BEA_Size";
   public static final String STATE_PROPERTY_NAME = "JMS_BEA_State";
   public static final String XID_PROPERTY_NAME = "JMS_BEA_Xid";
   public static final String INTERNAL_SEQUENCE_NUMBER_PROPERTY_NAME = "JMS_BEA_SequenceNumber";
   public static final String DD_FORWARDED_PROPERTY_NAME = "JMS_WL_DDForwarded";
   private static final boolean cpDebug = true;
   private static final boolean REPORT_COMPRESSON_TIME = false;
   private static final boolean REPORT_COMPRESSION = false;
   private int deliveryMode;
   private transient int adjustedDeliveryMode;
   private int deliveryCount;
   private long expiration;
   private long deliveryTime;
   private int redeliveryLimit;
   private byte priority;
   private int userdatalen;
   transient long bodySize;
   private boolean clientResponsibleForAcknowledge;
   private String compressionFactory;
   private String compressionFactoryOverride;
   private Properties compressionOption;
   private Properties compressionOptionOverride;
   private boolean storeMessageCompression;
   private boolean pagingMessageCompression;
   private transient long sequenceNumber;
   private transient boolean bodyWritable;
   private transient boolean propertiesWritable;
   private boolean serializeDestination;
   private boolean ddforwarded;
   private String correlationId;
   private DestinationImpl destination;
   private DestinationImpl replyTo;
   private String type;
   private PrimitiveObjectMap properties;
   static final byte NULLMESSAGEIMPL = 0;
   static final byte BYTESMESSAGEIMPL = 1;
   static final byte HDRMESSAGEIMPL = 2;
   static final byte MAPMESSAGEIMPL = 3;
   static final byte OBJECTMESSAGEIMPL = 4;
   static final byte STREAMMESSAGEIMPL = 5;
   static final byte TEXTMESSAGEIMPL = 6;
   static final byte XMLMESSAGEIMPL = 7;
   static final byte COMPRESSION = -128;
   protected static final int SUBFLAG_TOKENIZE = 1;
   protected static final int SUBFLAG_UTF8 = 2;
   protected static final int SUBFLAG_OBJECT = 4;
   protected static final int SUBFLAG_STRING = 128;
   private boolean hasBeenCompressed;
   private Externalizable bexaXid;
   private JMSMessageId messageId;
   private JMSID connectionId;
   private String messageIdString;
   private String userId;
   private String clientId;
   protected byte compressionTag;
   private transient JMSSession session;
   private transient JMSID sessionId;
   private String unitOfOrderName;
   private String safSequenceName;
   private long safSequenceNumber;
   private transient boolean keepSAFSequenceNameAndNumber;
   private Object workContext;
   private transient MessageReference mRef;
   private boolean userIDRequested;
   private transient boolean deliveryCountIncluded;
   private boolean pre90Message;
   private PayloadStream payloadCompressed;
   private boolean compressed;
   protected int originalLength;
   private boolean clean;
   protected transient boolean payloadCopyOnWrite;
   private boolean safNeedReorder;
   private boolean jmsClientForward;
   private int totalForwardsCount;
   private Object asyncSendLock;
   private boolean inAsyncSend;
   private static final int _PRIORITYMASK = 15;
   private static final int _PRIORITYSHIFT = 0;
   private static final int _RESERVEDEXTENSION2 = 16;
   private static final int _DDFORWARDED = 128;
   private static final int _HASREDELIVERYLIMIT = 256;
   private static final int _ISPERSISTENT = 512;
   private static final int _HASCORRID = 1024;
   private static final int _HASDESTINATION = 2048;
   private static final int _HASREPLYTO = 4096;
   private static final int _ISREDELIVERED = 8192;
   private static final int _HASTYPE = 16384;
   private static final int _HASEXPIRATION = 32768;
   private static final int _HASPROPERTIES = 65536;
   private static final int _HASXID = 131072;
   private static final int _HASMESSAGEID = 262144;
   private static final int _HASUSERDATALEN = 524288;
   private static final int _HASDELIVERYTIME = 1048576;
   private static final int _SERIALIZEDEST = 2097152;
   private static final int _CLIENTRESPONSIBLEFORACKNOWLEDGE = 4194304;
   private static final int _RESERVEDEXTENSION1 = 8388608;
   private static final int _VERSIONMASK = -16777216;
   private static final int _VERSIONSHIFT = 24;
   private static final int _DESTINATIONMASK = 7;
   private static final int _REPLYTODESTINATIONMASK = 56;
   private static final int _REPLYTODESTINATIONSHIFT = 3;
   private static final int _JMSCLIENTFORWARD = 1;
   private static final int _WORKCONTEXT = 2;
   private static final int _OLDMESSAGE = 4;
   private static final int _REQUESTUSERID = 16;
   private static final int _UNITOFORDER = 32;
   private static final int _HASSAFSEQUENCE = 64;
   private static final int _HASSAFSEQUENCENUMBER = 128;
   private static final int _HASUSERID = 256;
   private static final int _SAFNEEDREORDER = 512;
   private static final int _HASCLIENTID = 1024;
   protected static final int _CONTROL_MESSAGE_MASK = 16711680;
   public static final int _CONTROL_SEQUENCE_RELEASE_FANOUT = 65536;
   public static final int _CONTROL_SEQUENCE_RELEASE = 131072;
   public static final int _CONTROL_SEQUENCE_RESERVE = 196608;
   static boolean debugWire;

   public final boolean hasBeenCompressed() {
      return this.hasBeenCompressed;
   }

   public MessageImpl() {
      this.deliveryMode = 1;
      this.adjustedDeliveryMode = 1;
      this.deliveryCount = 0;
      this.redeliveryLimit = -1;
      this.userdatalen = -1;
      this.bodySize = -1L;
      this.clientResponsibleForAcknowledge = false;
      this.compressionFactory = defaultCompressionOption.getProperty("weblogic.jms.common.compressionfactory");
      this.compressionFactoryOverride = null;
      this.compressionOption = defaultCompressionOption;
      this.compressionOptionOverride = null;
      this.storeMessageCompression = false;
      this.pagingMessageCompression = false;
      this.serializeDestination = true;
      this.compressionTag = -1;
      this.sessionId = null;
      this.userIDRequested = false;
      this.pre90Message = false;
      this.jmsClientForward = false;
      this.asyncSendLock = new Object();
      this.inAsyncSend = false;
      this.bodyWritable = true;
      this.propertiesWritable = true;
   }

   public final void setOldMessage(boolean value) {
      this.pre90Message = value;
   }

   public final boolean isOldMessage() {
      return this.pre90Message;
   }

   public final void incForwardsCount() {
      ++this.totalForwardsCount;
   }

   public final void resetForwardsCount() {
      this.totalForwardsCount = 0;
   }

   public final void setForward(boolean value) {
      this.jmsClientForward = value;
   }

   public final void setSAFNeedReorder(boolean value) {
      this.safNeedReorder = value;
   }

   public final boolean isSAFNeedReorder() {
      return this.safNeedReorder;
   }

   public final boolean isForwarded() {
      return this.totalForwardsCount > 0;
   }

   public final boolean isForwardable() {
      return this.jmsClientForward;
   }

   public final int getForwardsCount() {
      return this.totalForwardsCount;
   }

   public abstract byte getType();

   public int getControlOpcode() {
      return 0;
   }

   public void setControlOpcode(int opcode) throws IOException {
      throw new IOException("opcode not allocated");
   }

   public static MessageImpl createMessageImpl(byte type) throws IOException {
      switch (type) {
         case 1:
            return new BytesMessageImpl();
         case 2:
            return new HdrMessageImpl();
         case 3:
            return new MapMessageImpl();
         case 4:
            return new ObjectMessageImpl();
         case 5:
            return new StreamMessageImpl();
         case 6:
            return new TextMessageImpl();
         case 7:
            return new XMLMessageImpl();
         default:
            throw new IOException(JMSClientExceptionLogger.logUnknownMessageTypeLoggable(type).getMessage());
      }
   }

   public MessageImpl(Message message) throws javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public MessageImpl(Message message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws javax.jms.JMSException {
      this();
      this.initializeFromMessage(message, destination, replyDestination);
   }

   public void initializeFromMessage(Message message) throws javax.jms.JMSException {
      this.initializeFromMessage(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public void initializeFromMessage(Message message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws javax.jms.JMSException {
      this.setJMSCorrelationID(message.getJMSCorrelationID());
      this.setJMSType(message.getJMSType());
      if (replyDestination == null) {
         this.setJMSReplyTo(message.getJMSReplyTo());
      } else {
         this.setJMSReplyTo(replyDestination);
      }

      this.userdatalen = 0;
      if (this.correlationId != null) {
         this.userdatalen = this.correlationId.length();
      }

      if (this.type != null) {
         this.userdatalen += this.type.length();
      }

      if (destination == null) {
         this.setJMSDestination(message.getJMSDestination());
      } else {
         this.setJMSDestination(destination);
      }

      if (message.getJMSRedelivered()) {
         this.setDeliveryCount(2);
      }

      this.setJMSExpiration(message.getJMSExpiration());
      if (message instanceof WLMessage) {
         this.setJMSPriority(message.getJMSPriority());
         this.setJMSDeliveryMode(message.getJMSDeliveryMode());
         this.setJMSDeliveryTime(((WLMessage)message).getJMSDeliveryTime());
         this.setJMSRedeliveryLimit(((WLMessage)message).getJMSRedeliveryLimit());
      }

      Enumeration props = message.getPropertyNames();

      while(props.hasMoreElements()) {
         String name = (String)props.nextElement();
         Object obj = message.getObjectProperty(name);
         this.userdatalen += 4 + (name.length() << 2);
         this.userdatalen += MessagingUtilities.calcObjectSize(obj);
         this.setObjectProperty(name, obj);
      }

   }

   private void copyToMessageReference() {
      if (this.mRef != null) {
         try {
            MessageImpl copiedMessage = this.copy();
            this.mRef.setMessage(copiedMessage);
         } catch (javax.jms.JMSException var2) {
            JMSClientExceptionLogger.logStackTrace(var2);
         }

         this.mRef = null;
      }
   }

   public final String getJMSMessageID() {
      if (this.messageIdString == null && this.messageId != null) {
         if (this.pre90Message) {
            this.messageIdString = (this.deliveryMode == 1 ? "ID:N" : "ID:P") + this.messageId;
         } else {
            this.messageIdString = "ID:" + this.messageId;
         }
      }

      return this.messageIdString;
   }

   public final void setJMSMessageID(String messageIdString) {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.messageIdString = messageIdString;
      this.jmsClientForward = false;
   }

   public final long getJMSTimestamp() {
      return this.messageId != null ? this.messageId.getTimestamp() : 0L;
   }

   public final void setJMSTimestamp(long timestamp) {
   }

   public final byte[] getJMSCorrelationIDAsBytes() throws javax.jms.JMSException {
      if (this.correlationId == null) {
         return null;
      } else {
         byte[] ret = new byte[this.correlationId.length()];

         try {
            ret = this.correlationId.getBytes("UTF-16BE");
         } catch (UnsupportedEncodingException var3) {
         }

         return ret;
      }
   }

   public final void setJMSCorrelationIDAsBytes(byte[] correlationID) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      try {
         if (correlationID == null) {
            this.setJMSCorrelationID((String)null);
         } else {
            this.setJMSCorrelationID(new String(correlationID, "UTF-16BE"));
         }
      } catch (UnsupportedEncodingException var3) {
      }

   }

   public final void setJMSCorrelationID(String correlationId) {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.jmsClientForward = false;
      this.correlationId = correlationId;
   }

   public final String getJMSCorrelationID() {
      return this.correlationId;
   }

   public final javax.jms.Destination getJMSReplyTo() {
      return this.replyTo;
   }

   public final void setJMSReplyTo(javax.jms.Destination replyTo) {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      if (replyTo != null && !(replyTo instanceof DestinationImpl)) {
         this.replyTo = null;
      } else {
         this.replyTo = (DestinationImpl)replyTo;
      }

   }

   public final javax.jms.Destination getJMSDestination() {
      return this.destination;
   }

   public final DestinationImpl getDestination() {
      return this.destination;
   }

   public final void setJMSDestination(javax.jms.Destination destination) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      if (destination != null && !(destination instanceof DestinationImpl)) {
         this.destination = null;
      } else {
         this.destination = (DestinationImpl)destination;
      }

   }

   public final void setJMSDestinationImpl(DestinationImpl destinationImpl) {
      this.destination = destinationImpl;
   }

   public final int getJMSDeliveryMode() {
      return this.deliveryMode;
   }

   public final void setJMSDeliveryMode(int deliveryMode) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      if (deliveryMode != 2 && deliveryMode != 1) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidDeliveryMode2Loggable(deliveryMode).getMessage());
      } else {
         this.deliveryMode = deliveryMode;
         this.adjustedDeliveryMode = this.deliveryMode;
      }
   }

   public final int getAdjustedDeliveryMode() {
      return this.adjustedDeliveryMode;
   }

   public final void setAdjustedDeliveryMode(int adjustedDeliveryMode) {
      this.adjustedDeliveryMode = adjustedDeliveryMode;
   }

   public final boolean getJMSRedelivered() {
      return this.deliveryCount > 1;
   }

   public void setDeliveryCount(int paramDeliveryCount) {
      this.deliveryCount = paramDeliveryCount;
   }

   public int getDeliveryCount() {
      return this.deliveryCount;
   }

   public final void setJMSRedelivered(boolean redeliveredArg) throws javax.jms.JMSException {
      if (redeliveredArg != this.getJMSRedelivered()) {
         if (this.mRef != null) {
            this.copyToMessageReference();
         }

         if (redeliveredArg) {
            this.setDeliveryCount(2);
         } else {
            this.setDeliveryCount(0);
         }

         assert redeliveredArg == this.getJMSRedelivered();

      }
   }

   public final void incrementDeliveryCount() {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.setDeliveryCount(++this.deliveryCount);
   }

   public final void setClientResponsibleForAcknowledge(boolean clientResponsibleForAcknowledge) {
      this.clientResponsibleForAcknowledge = clientResponsibleForAcknowledge;
   }

   public final boolean getClientResponsibleForAcknowledge() {
      return this.clientResponsibleForAcknowledge;
   }

   public final String getJMSType() {
      return this.type;
   }

   public final void setJMSType(String type) throws javax.jms.JMSException {
      this.jmsClientForward = false;
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.type = type;
   }

   public final long getJMSExpiration() {
      return this.expiration;
   }

   public final void setJMSExpiration(long expiration) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.expiration = expiration;
   }

   public final void _setJMSExpiration(long expiration) {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.expiration = expiration;
   }

   public final void setJMSDeliveryTime(long deliveryTime) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.deliveryTime = deliveryTime;
   }

   public final void setDeliveryTime(long deliveryTime) {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.deliveryTime = deliveryTime;
   }

   public final long getJMSDeliveryTime() throws javax.jms.JMSException {
      return this.deliveryTime;
   }

   public final long getDeliveryTime() {
      return this.deliveryTime;
   }

   public final void setJMSRedeliveryLimit(int redeliveryLimit) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      if (redeliveryLimit < -1) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidRedeliveryLimit2Loggable().getMessage());
      } else {
         this.redeliveryLimit = redeliveryLimit;
      }
   }

   public final void _setJMSRedeliveryLimit(int redeliveryLimit) {
      this.redeliveryLimit = redeliveryLimit;
   }

   public final int getJMSRedeliveryLimit() throws javax.jms.JMSException {
      return this.redeliveryLimit;
   }

   public final int _getJMSRedeliveryLimit() {
      return this.redeliveryLimit;
   }

   public final int getJMSPriority() {
      return this.priority;
   }

   public final void setJMSPriority(int priority) throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      if (priority >= 0 && priority <= 9) {
         this.priority = (byte)priority;
      } else {
         throw new JMSException(JMSClientExceptionLogger.logInvalidPriority2Loggable(priority).getMessage());
      }
   }

   public final void setMessageReference(MessageReference mRef) {
      this.mRef = mRef;
   }

   public final void clearProperties() {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.jmsClientForward = false;
      this.propertiesWritable = true;
      this.deliveryCountIncluded = false;
      this.properties = null;
   }

   private boolean isUnitOfOrderSet() {
      return this.unitOfOrderName != null;
   }

   private boolean isUserIDSet() {
      return this.userId != null;
   }

   private boolean isSAFSequenceNameSet() {
      return this.safSequenceName != null;
   }

   private boolean isSAFSequenceNumberSet() {
      return this.safSequenceNumber != 0L;
   }

   private boolean isDeliveryTimeSet() {
      return this.deliveryTime != 0L;
   }

   private boolean isRedeliveryLimitSet() {
      return this.redeliveryLimit != -1;
   }

   boolean hasProperties() {
      return this.properties != null && !this.properties.isEmpty();
   }

   public final boolean propertyExists(String name) throws javax.jms.JMSException {
      if (name.equals("JMS_BEA_UnitOfOrder")) {
         return this.isUnitOfOrderSet();
      } else if (name.equals("JMSXUserID")) {
         return this.isUserIDSet();
      } else if (name.equals("JMS_BEA_SAF_SEQUENCE_NAME")) {
         return this.isSAFSequenceNameSet();
      } else if (name.equals("JMS_BEA_SAF_SEQUENCE")) {
         return this.isSAFSequenceNumberSet();
      } else if (name.equals("JMS_BEA_DeliveryTime")) {
         return this.isDeliveryTimeSet();
      } else if (name.equals("JMS_BEA_RedeliveryLimit")) {
         return this.isRedeliveryLimitSet();
      } else if (name.equals("JMSXDeliveryCount")) {
         return true;
      } else {
         return this.properties != null && this.properties.containsKey(name);
      }
   }

   public final boolean getBooleanProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toBoolean(this.getObjectProperty(name));
   }

   public final byte getByteProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toByte(this.getObjectProperty(name));
   }

   public final short getShortProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toShort(this.getObjectProperty(name));
   }

   public final int getIntProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toInt(this.getObjectProperty(name));
   }

   public final long getLongProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toLong(this.getObjectProperty(name));
   }

   public final float getFloatProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toFloat(this.getObjectProperty(name));
   }

   public final double getDoubleProperty(String name) throws javax.jms.JMSException {
      return TypeConverter.toDouble(this.getObjectProperty(name));
   }

   public final String getStringProperty(String name) throws javax.jms.JMSException {
      Object obj = this.getObjectProperty(name);
      if (obj != null && obj instanceof byte[]) {
         try {
            byte[] str = (byte[])((byte[])obj);
            ByteArrayInputStream bais = new ByteArrayInputStream(str);
            ObjectInputStream ois = new FilteringObjectInputStream(bais);
            return (String)ois.readObject();
         } catch (Exception var6) {
            throw new JMSException(JMSClientExceptionLogger.logInvalidStringPropertyLoggable());
         }
      } else {
         return TypeConverter.toString(this.getObjectProperty(name));
      }
   }

   public final Object getObjectProperty(String name) throws javax.jms.JMSException {
      if (name.equals("JMS_BEA_UnitOfOrder")) {
         return this.unitOfOrderName;
      } else if (name.equals("JMS_BEA_SAF_SEQUENCE_NAME")) {
         return this.safSequenceName;
      } else if (name.equals("JMS_BEA_SAF_SEQUENCE")) {
         return new Long(this.safSequenceNumber);
      } else if (name.equals("JMS_BEA_DeliveryTime")) {
         return new Long(this.deliveryTime);
      } else if (name.equals("JMS_BEA_RedeliveryLimit")) {
         return new Integer(this.redeliveryLimit);
      } else if (name.equals("JMSXDeliveryCount")) {
         return new Integer(this.deliveryCount);
      } else if (name.equals("JMSXUserID")) {
         return this.userId;
      } else {
         return this.properties != null ? this.properties.get(name) : null;
      }
   }

   public final Collection getPropertyNameCollection() throws javax.jms.JMSException {
      ArrayList names;
      if (this.properties == null) {
         names = new ArrayList();
      } else {
         names = new ArrayList(this.properties.keySet());
      }

      if (this.isUnitOfOrderSet()) {
         names.add("JMS_BEA_UnitOfOrder");
      }

      if (this.isSAFSequenceNameSet()) {
         names.add("JMS_BEA_SAF_SEQUENCE_NAME");
      }

      if (this.isSAFSequenceNumberSet()) {
         names.add("JMS_BEA_SAF_SEQUENCE");
      }

      if (this.isDeliveryTimeSet()) {
         names.add("JMS_BEA_DeliveryTime");
      }

      if (this.isRedeliveryLimitSet()) {
         names.add("JMS_BEA_RedeliveryLimit");
      }

      if (this.isUserIDSet()) {
         names.add("JMSXUserID");
      }

      if (this.deliveryCount > 0 && !this.propertiesWritable || this.deliveryCountIncluded) {
         names.add("JMSXDeliveryCount");
      }

      return names;
   }

   private PrimitiveObjectMap getInteropProperties() throws IOException {
      PrimitiveObjectMap ret = new PrimitiveObjectMap();

      try {
         Iterator allNames = this.getPropertyNameCollection().iterator();

         while(allNames.hasNext()) {
            String name = (String)allNames.next();
            ret.put(name, this.getObjectProperty(name));
         }

         return ret;
      } catch (javax.jms.JMSException var4) {
         IOException ioe = new IOException(var4.toString());
         ioe.initCause(var4);
         throw ioe;
      }
   }

   public void removeProperty(String propertyName) {
      try {
         this.properties.remove(propertyName);
      } catch (javax.jms.JMSException var3) {
      }

   }

   private void readInteropProperties(ObjectInput in, int messageVersion) throws IOException {
      this.properties = new PrimitiveObjectMap(in, messageVersion);

      try {
         this.properties.remove("JMSXDeliveryCount");
         this.properties.remove("JMS_BEA_DeliveryTime");
         this.properties.remove("JMS_BEA_RedeliveryLimit");
         this.properties.remove("JMS_BEA_SAF_SEQUENCE_NAME");
         this.properties.remove("JMS_BEA_SAF_SEQUENCE");
         this.properties.remove("JMS_BEA_UnitOfOrder");
         this.properties.remove("JMSXUserID");
         if (this.properties.isEmpty()) {
            this.properties = null;
         }

      } catch (javax.jms.JMSException var5) {
         IOException ioe = new IOException(var5.toString());
         ioe.initCause(var5);
         throw ioe;
      }
   }

   public final Enumeration getPropertyNames() throws javax.jms.JMSException {
      return Collections.enumeration(this.getPropertyNameCollection());
   }

   public final void setBooleanProperty(String name, boolean value) throws javax.jms.JMSException {
      this.setObjectProperty(name, value);
   }

   public final void setByteProperty(String name, byte value) throws javax.jms.JMSException {
      this.setObjectProperty(name, new Byte(value));
   }

   public final void setShortProperty(String name, short value) throws javax.jms.JMSException {
      this.setObjectProperty(name, new Short(value));
   }

   public final void setIntProperty(String name, int value) throws javax.jms.JMSException {
      this.setObjectProperty(name, new Integer(value));
   }

   public final void setLongProperty(String name, long value) throws javax.jms.JMSException {
      this.setObjectProperty(name, new Long(value));
   }

   public final void setFloatProperty(String name, float value) throws javax.jms.JMSException {
      this.setObjectProperty(name, new Float(value));
   }

   public final void setDoubleProperty(String name, double value) throws javax.jms.JMSException {
      this.setObjectProperty(name, new Double(value));
   }

   public final void setStringProperty(String name, String value) throws javax.jms.JMSException {
      this.setObjectProperty(name, value);
   }

   private String illegalPropertyName(String name) {
      return JMSClientExceptionLogger.logInvalidPropertyName2Loggable(name).getMessage();
   }

   public final void setObjectProperty(String name, Object value) throws javax.jms.JMSException {
      if (name != null && name.length() != 0) {
         if (isHeaderField(name)) {
            throw new javax.jms.MessageFormatException(this.illegalPropertyName(name));
         } else if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            throw new javax.jms.MessageFormatException(this.illegalPropertyName(name));
         } else {
            int limit;
            for(limit = 1; limit < name.length(); ++limit) {
               if (!Character.isJavaIdentifierPart(name.charAt(limit))) {
                  throw new javax.jms.MessageFormatException(this.illegalPropertyName(name));
               }
            }

            if (!this.propertiesWritable) {
               throw new javax.jms.MessageNotWriteableException(JMSClientExceptionLogger.logWriteInReadModeLoggable().getMessage());
            } else if (!(value instanceof Number) && !(value instanceof String) && !(value instanceof Boolean) && value != null) {
               throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logInvalidPropertyValueLoggable(value.toString()).getMessage());
            } else if (name.equals("JMS_BEA_UnitOfOrder")) {
               this.unitOfOrderName = TypeConverter.toString(value);
            } else if (name.equals("JMS_BEA_SAF_SEQUENCE_NAME")) {
               this.safSequenceName = TypeConverter.toString(value);
               this.keepSAFSequenceNameAndNumber = true;
            } else if (name.equals("JMS_BEA_SAF_SEQUENCE")) {
               this.safSequenceNumber = TypeConverter.toLong(value);
               this.keepSAFSequenceNameAndNumber = true;
            } else if (name.equals("JMS_BEA_DeliveryTime")) {
               this.deliveryTime = TypeConverter.toLong(value);
            } else if (name.equals("JMS_BEA_RedeliveryLimit")) {
               limit = TypeConverter.toInt(value);
               if (limit < -1) {
                  throw new JMSException(JMSClientExceptionLogger.logInvalidRedeliveryLimit2Loggable());
               } else {
                  this.redeliveryLimit = limit;
               }
            } else if (name.equals("JMSXDeliveryCount")) {
               this.deliveryCount = TypeConverter.toInt(value);
            } else if (name.equals("JMSXUserID")) {
               this.userId = TypeConverter.toString(value);
            } else {
               if (this.properties == null) {
                  this.properties = new PrimitiveObjectMap();
               }

               this.properties.put(name, value);
            }
         }
      } else {
         throw new IllegalArgumentException(this.illegalPropertyName(name));
      }
   }

   public final void acknowledge() throws javax.jms.JMSException {
      if (this.session != null) {
         this.session.acknowledge((WLAcknowledgeInfo)this);
         this.session = null;
      }

   }

   public final void clearBody() throws javax.jms.JMSException {
      if (this.mRef != null) {
         this.copyToMessageReference();
      }

      this.jmsClientForward = false;
      this.bodyWritable = true;
      this.bodySize = -1L;
      this.nullBody();
      this.cleanupCompressedMessageBody();
   }

   public abstract void nullBody();

   public final void setId(JMSMessageId messageId) {
      this.messageId = messageId;
      this.messageIdString = null;
   }

   public final JMSMessageId getId() {
      return this.messageId;
   }

   public final JMSMessageId getMessageId() {
      return this.getId();
   }

   public final void setSession(JMSSession session) {
      this.session = session;
   }

   public final void setConnectionId(JMSID jmsid) {
      this.connectionId = jmsid;
   }

   public final JMSID getConnectionId() {
      return this.connectionId;
   }

   public final void setSessionId(JMSID jmsid) {
      this.sessionId = jmsid;
   }

   public final JMSID getSessionId() {
      return this.sessionId;
   }

   public final void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public final String getClientId() {
      return this.clientId;
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public final void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public final Object get(String key) {
      try {
         if (key.startsWith("JMS")) {
            if (key.equals("JMSCorrelationID")) {
               return this.getJMSCorrelationID();
            }

            if (key.equals("JMSDeliveryMode")) {
               if (this.getJMSDeliveryMode() == 2) {
                  return "PERSISTENT";
               }

               return "NON_PERSISTENT";
            }

            if (key.equals("JMSDeliveryTime")) {
               return new Long(this.getDeliveryTime());
            }

            if (key.equals("JMSExpiration")) {
               return new Long(this.getJMSExpiration());
            }

            if (key.equals("JMSMessageID")) {
               return this.getJMSMessageID();
            }

            if (key.equals("JMSPriority")) {
               return new Integer(this.getJMSPriority());
            }

            if (key.equals("JMSRedelivered")) {
               return this.getJMSRedelivered();
            }

            if (key.equals("JMSRedeliveryLimit")) {
               return new Integer(this.getJMSRedeliveryLimit());
            }

            if (key.equals("JMSTimestamp")) {
               return new Long(this.getJMSTimestamp());
            }

            if (key.equals("JMSType")) {
               return this.getJMSType();
            }

            if (key.equals("JMS_BEA_Size")) {
               return new Long(this.size());
            }
         }

         return this.getObjectProperty(key);
      } catch (javax.jms.JMSException var3) {
         return null;
      }
   }

   public Object parse() throws Exception {
      return null;
   }

   public final synchronized MessageImpl cloneit() {
      try {
         return (MessageImpl)this.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public abstract MessageImpl copy() throws javax.jms.JMSException;

   final void copy(MessageImpl message) throws javax.jms.JMSException {
      boolean checkuserdatalen = false;
      message.destination = this.destination;
      message.replyTo = this.replyTo;
      message.deliveryMode = this.deliveryMode;
      message.correlationId = this.correlationId;
      message.deliveryCount = this.deliveryCount;
      message.type = this.type;
      message.deliveryTime = this.deliveryTime;
      message.redeliveryLimit = this.redeliveryLimit;
      message.expiration = this.expiration;
      message.priority = this.priority;
      message.jmsClientForward = this.jmsClientForward;
      message.totalForwardsCount = this.totalForwardsCount;
      message.safNeedReorder = this.safNeedReorder;
      message.compressionFactory = this.compressionFactory;
      message.compressionOption = this.compressionOption;
      message.compressionOptionOverride = this.compressionOptionOverride;
      message.compressionTag = this.compressionTag;
      message.storeMessageCompression = this.storeMessageCompression;
      message.pagingMessageCompression = this.pagingMessageCompression;
      if (message.compressed = this.compressed) {
         message.originalLength = this.originalLength;
         message.payloadCompressed = this.payloadCompressed.copyPayloadWithoutSharedStream();
      }

      message.clientResponsibleForAcknowledge = this.clientResponsibleForAcknowledge;
      if (this.userdatalen == -1) {
         checkuserdatalen = true;
         message.userdatalen = 0;
         if (message.correlationId != null) {
            message.userdatalen = message.correlationId.length();
         }

         if (message.type != null) {
            message.userdatalen += message.type.length();
         }
      } else {
         message.userdatalen = this.userdatalen;
      }

      message.connectionId = this.connectionId;
      message.clientId = this.clientId;
      message.messageId = this.messageId;
      if (this.properties != null) {
         message.properties = new PrimitiveObjectMap(this.properties);
         if (checkuserdatalen) {
            message.userdatalen += message.properties.getSizeInBytes();
         }
      }

      message.bodyWritable = this.bodyWritable;
      message.propertiesWritable = this.propertiesWritable;
      message.serializeDestination = this.serializeDestination;
      message.ddforwarded = this.ddforwarded;
      message.unitOfOrderName = this.unitOfOrderName;
      message.safSequenceName = this.safSequenceName;
      message.safSequenceNumber = this.safSequenceNumber;
      message.userId = this.userId;
      message.workContext = this.workContext;
      message.setOldMessage(this.isOldMessage());
   }

   protected boolean needToDecompressDueToInterop(Object oo) throws IOException {
      if (!(oo instanceof PeerInfoable)) {
         return false;
      } else {
         PeerInfoable p = (PeerInfoable)oo;
         PeerInfo pi = p.getPeerInfo();
         int majorVer = pi.getMajor();
         if (pi.compareTo(PeerInfo.VERSION_1212) >= 0) {
            return false;
         } else if (this.compressionTag == defaultCompressionTag) {
            return false;
         } else {
            return this.compressionTag >= 0;
         }
      }
   }

   protected int getVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         int majorVer = pi.getMajor();
         if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("MessageImpl PeerInfo " + pi);
         }

         if (majorVer < 6) {
            throw new IOException(JMSClientExceptionLogger.logIncompatibleVersion9Loggable((byte)1, (byte)10, (byte)20, (byte)30, pi.toString()).getMessage());
         }

         switch (majorVer) {
            case 6:
               return 10;
            case 7:
            case 8:
               return 20;
            default:
               if (pi.getMajor() == 9 || pi.getMajor() > 9 && pi.compareTo(PeerInfo.VERSION_1033) < 0) {
                  return 30;
               }

               if (pi.compareTo(PeerInfo.VERSION_1033) >= 0) {
                  return 40;
               }
         }
      }

      if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("MessageImpl write NOT! PeerInfoable");
      }

      return 40;
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      ObjectOutput out;
      if (tOut instanceof JMSObjectOutputWrapper) {
         out = ((JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
      } else {
         out = tOut;
      }

      int flags = 0;
      int peerVersion = this.getVersion(out);
      flags |= peerVersion << 24;
      if (this.deliveryMode == 2) {
         flags |= 512;
      }

      String writeCorrelationId = this.correlationId;
      if (writeCorrelationId != null) {
         flags |= 1024;
      }

      if (this.serializeDestination) {
         flags |= 2097152;
      }

      if (this.ddforwarded) {
         flags |= 128;
      }

      DestinationImpl writeDestination = this.destination;
      if (writeDestination != null) {
         if (this.serializeDestination) {
            flags |= 2048;
         } else if (!(out instanceof WLObjectOutput) && (!(out instanceof JMSOutputStream) || !((JMSOutputStream)out).isJMSMulticastOutputStream() && !((JMSOutputStream)out).isBypassOutputStream())) {
            flags |= 2048;
         }
      }

      int newDestinationFlag = 0;
      if (peerVersion >= 20 && (flags & 2048) != 0) {
         newDestinationFlag |= Destination.getDestinationType(writeDestination, 0);
         if (newDestinationFlag != 0) {
            flags |= 8388608;
         }
      }

      int extendedFlags;
      if (peerVersion >= 30) {
         extendedFlags = this.getControlOpcode();
         if (this.jmsClientForward) {
            extendedFlags |= 1;
         }

         if (this.workContext != null) {
            extendedFlags |= 2;
         }

         if (this.userIDRequested) {
            extendedFlags |= 16;
         }

         if (this.isUnitOfOrderSet()) {
            extendedFlags |= 32;
         }

         if (this.isSAFSequenceNameSet()) {
            extendedFlags |= 64;
         }

         if (this.isSAFSequenceNumberSet()) {
            extendedFlags |= 128;
         }

         if (this.isUserIDSet()) {
            extendedFlags |= 256;
         }

         if (this.safNeedReorder) {
            extendedFlags |= 512;
         }

         if (this.pre90Message) {
            extendedFlags |= 4;
         }

         if (peerVersion >= 40 && this.clientId != null) {
            extendedFlags |= 1024;
         }

         if (extendedFlags != 0) {
            flags |= 16;
         }
      } else {
         extendedFlags = 0;
      }

      DestinationImpl localReplyTo = this.replyTo;
      if (localReplyTo != null) {
         flags |= 4096;
      }

      if (peerVersion >= 20 && localReplyTo != null) {
         newDestinationFlag |= Destination.getDestinationType(localReplyTo, 3);
         if ((newDestinationFlag & 56) != 0) {
            flags |= 8388608;
         }
      }

      if (this.getJMSRedelivered()) {
         flags |= 8192;
      }

      if (this.type != null) {
         flags |= 16384;
      }

      if (this.deliveryTime != 0L) {
         flags |= 1048576;
      }

      if (peerVersion >= 20 && this.redeliveryLimit != -1) {
         flags |= 256;
      }

      if (this.expiration != 0L) {
         flags |= 32768;
      }

      flags |= this.priority << 0;
      boolean hasProperties = peerVersion < 30 || this.hasProperties();
      if (hasProperties) {
         flags |= 65536;
      }

      if (this.messageId != null) {
         flags |= 262144;
      }

      if (this.bexaXid != null) {
         flags |= 131072;
      }

      if ((flags & 82944) != 0 || (extendedFlags & 32) != 0) {
         flags |= 524288;
      }

      if (this.clientResponsibleForAcknowledge) {
         flags |= 4194304;
      }

      this.writeFlags(flags, out);
      int localUserdatalen;
      if (writeCorrelationId != null) {
         out.writeUTF(writeCorrelationId);
         localUserdatalen = writeCorrelationId.length();
      } else {
         localUserdatalen = 0;
      }

      if (peerVersion >= 20 && (flags & 8388608) != 0) {
         out.writeByte(newDestinationFlag);
      }

      if ((flags & 2048) != 0) {
         writeDestination.writeExternal(out);
      }

      if (localReplyTo != null) {
         localReplyTo.writeExternal(out);
      }

      if (peerVersion >= 30 && extendedFlags != 0) {
         out.writeInt(extendedFlags);
         if (this.jmsClientForward) {
            out.writeInt(this.totalForwardsCount);
         }
      }

      if (this.type != null) {
         out.writeUTF(this.type);
         localUserdatalen += this.type.length();
      }

      if (this.isDeliveryTimeSet()) {
         out.writeLong(this.deliveryTime);
      }

      if (peerVersion >= 20 && this.isRedeliveryLimitSet()) {
         out.writeInt(this.redeliveryLimit);
      }

      if (this.expiration != 0L) {
         out.writeLong(this.expiration);
      }

      if (peerVersion >= 30) {
         out.writeInt(this.deliveryCount);
      }

      if (hasProperties) {
         if (peerVersion >= 30) {
            this.properties.writeToStream(out, peerVersion);
         } else {
            PeerInfo peerInfo = out instanceof PeerInfoable ? ((PeerInfoable)out).getPeerInfo() : PeerInfo.getPeerInfo();
            this.getInteropProperties().writeToStream(out, peerInfo);
         }
      }

      if (this.messageId != null) {
         this.messageId.writeExternal(out);
      }

      if (peerVersion >= 30) {
         if (this.isUnitOfOrderSet()) {
            out.writeUTF(this.unitOfOrderName);
         }

         if (this.isUserIDSet()) {
            out.writeUTF(this.userId);
            localUserdatalen += this.userId.length();
         }

         if (this.isSAFSequenceNameSet()) {
            out.writeUTF(this.safSequenceName);
         }

         if (this.isSAFSequenceNumberSet()) {
            out.writeLong(this.safSequenceNumber);
         }

         if (this.workContext != null) {
            JMSWorkContextHelper.writeWorkContext(this.workContext, out);
         }
      }

      if (this.bexaXid != null) {
         out.writeObject(this.bexaXid);
      }

      if ((flags & 524288) != 0) {
         out.writeInt(localUserdatalen);
      }

      this.userdatalen = localUserdatalen;
      if ((extendedFlags & 1024) != 0) {
         out.writeUTF(this.clientId);
      }

   }

   private void writeFlags(int flags, ObjectOutput out) throws IOException {
      if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("MessageImpl.write versionInt 0x" + Integer.toHexString(flags).toUpperCase());
      }

      out.writeInt(flags);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      byte vrsn = (byte)((flags & -16777216) >>> 24 & 255);
      if (debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("MessageImpl.read  versionInt 0x" + Integer.toHexString(flags).toUpperCase());
      }

      if (vrsn < 30) {
         this.pre90Message = true;
         if (vrsn == 1) {
            if (!(in instanceof PutBackable)) {
               throw new IOException(JMSClientExceptionLogger.logUnknownStreamTypeLoggable().getMessage());
            }

            ((PutBackable)in).unput();
            ((PutBackable)in).unput();
            ((PutBackable)in).unput();
            this.readExternalVersion1(in);
            return;
         }

         if (vrsn == 10) {
            this.readExternalVersion2(in, flags);
            return;
         }
      }

      if (vrsn > 40) {
         throw JMSUtilities.versionIOException(vrsn, 1, 40);
      } else {
         if ((flags & 512) != 0) {
            this.deliveryMode = 2;
         } else {
            this.deliveryMode = 1;
         }

         this.adjustedDeliveryMode = this.deliveryMode;
         if ((flags & 1024) != 0) {
            this.correlationId = in.readUTF();
         }

         int extendedFlags;
         int opcode;
         if ((flags & 8388608) != 0) {
            extendedFlags = in.readByte();
            if ((flags & 2048) != 0) {
               opcode = (byte)(extendedFlags & 7);
               this.destination = Destination.createDestination((byte)opcode, in);
            }

            if ((flags & 4096) != 0) {
               opcode = (byte)((extendedFlags & 56) >>> 3);
               this.replyTo = Destination.createDestination((byte)opcode, in);
            }
         }

         extendedFlags = 0;
         if ((flags & 16) != 0) {
            extendedFlags = in.readInt();
            this.pre90Message = (extendedFlags & 4) != 0;
            this.userIDRequested = (extendedFlags & 16) != 0;
            this.jmsClientForward = (extendedFlags & 1) != 0;
            this.safNeedReorder = (extendedFlags & 512) != 0;
            opcode = 16711680 & extendedFlags;
            if (opcode != 0) {
               this.setControlOpcode(opcode);
            }

            if ((extendedFlags & 1) != 0) {
               this.totalForwardsCount = in.readInt();
            }
         }

         if ((flags & 16384) != 0) {
            this.type = in.readUTF();
         }

         if ((flags & 1048576) != 0) {
            this.deliveryTime = in.readLong();
         }

         if ((flags & 256) != 0) {
            this.redeliveryLimit = in.readInt();
         }

         if ((flags & '耀') != 0) {
            this.expiration = in.readLong();
         }

         if (vrsn >= 30) {
            this.deliveryCount = in.readInt();
         }

         this.priority = (byte)((flags & 15) >>> 0 & 255);
         if ((flags & 65536) != 0) {
            if (vrsn >= 30) {
               this.properties = new PrimitiveObjectMap(in, vrsn);
            } else {
               this.readInteropProperties(in, vrsn);
            }
         }

         if ((flags & 262144) != 0) {
            this.messageId = new JMSMessageId();
            this.messageId.readExternal(in);
         }

         if ((extendedFlags & 32) != 0) {
            this.unitOfOrderName = in.readUTF().intern();
         }

         if ((extendedFlags & 256) != 0) {
            this.userId = in.readUTF().intern();
         }

         if ((extendedFlags & 64) != 0) {
            this.safSequenceName = in.readUTF().intern();
         }

         if ((extendedFlags & 128) != 0) {
            this.safSequenceNumber = in.readLong();
         }

         if ((extendedFlags & 2) != 0) {
            this.workContext = JMSWorkContextHelper.readWorkContext(in);
         }

         if ((flags & 131072) != 0) {
            this.bexaXid = (Externalizable)in.readObject();
         }

         if ((flags & 524288) != 0) {
            this.userdatalen = in.readInt();
         } else {
            this.userdatalen = 0;
         }

         this.clientResponsibleForAcknowledge = (flags & 4194304) != 0;
         this.bodyWritable = false;
         this.propertiesWritable = false;
         this.serializeDestination = (flags & 2097152) != 0;
         this.ddforwarded = (flags & 128) != 0;
         if ((extendedFlags & 1024) != 0) {
            this.clientId = in.readUTF();
         }

      }
   }

   private void readExternalVersion2(ObjectInput in, int flags) throws IOException, ClassNotFoundException {
      if ((flags & 512) != 0) {
         this.deliveryMode = 2;
      } else {
         this.deliveryMode = 1;
      }

      this.adjustedDeliveryMode = this.deliveryMode;
      if ((flags & 1024) != 0) {
         this.correlationId = in.readUTF();
      }

      if ((flags & 2048) != 0) {
         this.destination = new DestinationImpl();
         this.destination.readExternal(in);
      }

      if ((flags & 4096) != 0) {
         this.replyTo = new DestinationImpl();
         this.replyTo.readExternal(in);
      }

      boolean redelivered = (flags & 8192) != 0;
      if (redelivered) {
         this.setDeliveryCount(2);
      } else {
         this.setDeliveryCount(1);
      }

      if ((flags & 16384) != 0) {
         this.type = in.readUTF();
      }

      if ((flags & 1048576) != 0) {
         this.deliveryTime = in.readLong();
      }

      if ((flags & '耀') != 0) {
         this.expiration = in.readLong();
      }

      this.priority = (byte)((flags & 15) >>> 0 & 255);
      if ((flags & 65536) != 0) {
         this.readInteropProperties(in, 10);
      }

      if ((flags & 262144) != 0) {
         this.messageId = new JMSMessageId();
         this.messageId.readExternal(in);
      }

      if ((flags & 131072) != 0) {
         this.bexaXid = (Externalizable)in.readObject();
      }

      if ((flags & 524288) != 0) {
         this.userdatalen = in.readInt();
      } else {
         this.userdatalen = 0;
      }

      this.bodyWritable = false;
      this.propertiesWritable = false;
      this.serializeDestination = (flags & 2097152) != 0;
   }

   private void readExternalVersion1(ObjectInput in) throws IOException, ClassNotFoundException {
      this.deliveryMode = in.readByte();
      this.adjustedDeliveryMode = this.deliveryMode;
      if (this.deliveryMode != 2) {
         throw new IOException(JMSClientExceptionLogger.logCorruptedStreamLoggable().getMessage());
      } else {
         if (in.readBoolean()) {
            this.correlationId = in.readUTF();
         } else {
            this.correlationId = null;
         }

         in.readLong();
         if (in.readBoolean()) {
            this.destination = new DestinationImpl();
            this.destination.readExternal(in);
         }

         if (in.readBoolean()) {
            this.replyTo = new DestinationImpl();
            this.replyTo.readExternal(in);
         }

         boolean redelivered = in.readBoolean();
         if (redelivered) {
            this.setDeliveryCount(2);
         } else {
            this.setDeliveryCount(1);
         }

         if (in.readBoolean()) {
            this.type = in.readUTF();
         } else {
            this.type = null;
         }

         this.expiration = in.readLong();
         this.priority = in.readByte();
         if (in.readBoolean()) {
            this.readInteropProperties(in, 1);
         }

         if (in.readBoolean()) {
            this.messageId = new JMSMessageId();
            this.messageId.readExternal(in);
         }

         if (this.messageId == null && this.expiration != 0L) {
            throw new IOException(JMSClientExceptionLogger.logVersionErrorLoggable().getMessage());
         } else {
            if (in.readBoolean()) {
               this.bexaXid = (Externalizable)in.readObject();
            }

            this.userdatalen = in.readInt();
            this.connectionId = new JMSID();
            this.clientResponsibleForAcknowledge = false;
            this.connectionId.readExternal(in);
            this.bodyWritable = false;
            this.propertiesWritable = false;
         }
      }
   }

   public static boolean isHeaderField(String name) {
      return name.startsWith("JMS") && (name.equals("JMSCorrelationID") || name.equals("JMSDeliveryMode") || name.equals("JMSDestination") || name.equals("JMSExpiration") || name.equals("JMSPriority") || name.equals("JMSRedelivered") || name.equals("JMSReplyTo") || name.equals("JMSTimestamp") || name.equals("JMSType"));
   }

   public void reset() throws javax.jms.JMSException {
   }

   public final void setBodyWritable() {
      this.setBodyWritable(true);
      this.jmsClientForward = false;
   }

   public final void setBodyWritable(boolean writable) {
      this.bodyWritable = writable;
   }

   public final void setPropertiesWritable(boolean writable) {
      this.propertiesWritable = writable;
   }

   final void readMode() throws javax.jms.JMSException {
      if (this.bodyWritable) {
         throw new javax.jms.MessageNotReadableException(JMSClientExceptionLogger.logReadInWriteModeLoggable().getMessage());
      }
   }

   public void setJMSXUserID(String userID) {
      this.userId = userID;
   }

   public boolean isJMSXUserIDRequested() {
      return this.userIDRequested;
   }

   public void requestJMSXUserID(boolean requested) {
      this.userIDRequested = requested;
   }

   public boolean includeJMSXDeliveryCount(boolean incl) {
      boolean hold = this.deliveryCountIncluded;
      this.deliveryCountIncluded = incl;
      return hold;
   }

   final void writeMode() throws javax.jms.JMSException {
      if (!this.bodyWritable) {
         throw new javax.jms.MessageNotWriteableException(JMSClientExceptionLogger.logWriteInReadMode2Loggable().getMessage());
      }
   }

   public final Externalizable getBEXAXid() {
      return this.bexaXid;
   }

   public final void setBEXAXid(Externalizable o) {
      this.bexaXid = o;
   }

   public abstract long getPayloadSize();

   public final int getUserPropertySize() {
      return this.userdatalen;
   }

   public final void resetUserPropertySize() {
      this.bodySize = (long)(this.userdatalen = -1);
   }

   public final void setSerializeDestination(boolean serializeDestination) {
      this.serializeDestination = serializeDestination;
   }

   public final void setDDForwarded(boolean ddforwarded) {
      this.ddforwarded = ddforwarded;
   }

   public final boolean getDDForwarded() {
      return this.ddforwarded;
   }

   public final void setUnitOfOrderName(String name) {
      this.unitOfOrderName = name;
   }

   public final String getUnitOfOrder() {
      return this.unitOfOrderName;
   }

   public final boolean getKeepSAFSequenceNameAndNumber() {
      return this.keepSAFSequenceNameAndNumber;
   }

   public final void setSAFSequenceName(String name) {
      this.safSequenceName = name;
      this.keepSAFSequenceNameAndNumber = true;
   }

   public final String getSAFSequenceName() {
      return this.safSequenceName;
   }

   public void setSAFSeqNumber(long seqNumber) {
      this.safSequenceNumber = seqNumber;
      this.keepSAFSequenceNameAndNumber = true;
   }

   public long getSAFSeqNumber() {
      return this.safSequenceNumber;
   }

   public final String getGroup() {
      return this.unitOfOrderName;
   }

   public final void setWorkContext(Object workContext) {
      this.workContext = workContext;
   }

   public final Object getWorkContext() {
      return this.workContext;
   }

   public MessageID getMessageID() {
      return this.messageId;
   }

   public long getExpirationTime() {
      return this.getJMSExpiration();
   }

   public int getRedeliveryLimit() {
      return this._getJMSRedeliveryLimit();
   }

   public long size() {
      int propSize = this.getUserPropertySize();
      if (propSize == -1) {
         propSize = 0;
      }

      return this.getPayloadSize() + (long)propSize;
   }

   public weblogic.messaging.Message duplicate() {
      return this.cloneit();
   }

   public Document getJMSMessageDocument() throws javax.jms.JMSException {
      try {
         Class clz = Class.forName("weblogic.jms.common.XMLHelper");
         Method m = clz.getMethod("getDocument", WLMessage.class);
         return (Document)m.invoke((Object)null, this);
      } catch (InvocationTargetException var3) {
         Throwable target = var3.getTargetException();
         if (target instanceof javax.jms.JMSException) {
            throw (javax.jms.JMSException)target;
         } else {
            throw new AssertionError(target);
         }
      } catch (Exception var4) {
         throw new AssertionError(var4);
      }
   }

   public final boolean isCompressed() {
      return this.compressed;
   }

   protected final boolean shouldCompress(ObjectOutput out, int limit) throws IOException {
      if (this.compressed) {
         return true;
      } else if (this.getVersion(out) < 30) {
         return false;
      } else {
         long size = this.getPayloadSize();
         return size > (long)limit ? true : this.shouldCompressionInternal(out);
      }
   }

   private boolean shouldCompressionInternal(ObjectOutput out) {
      return out instanceof PersistentStoreOutputStream && this.storeMessageCompression || out instanceof PagingByteBufferObjectOutputStream && this.pagingMessageCompression;
   }

   public byte getCompressionTagInternal() {
      return this.compressionTag;
   }

   private byte getCompressionTag(ObjectOutput out) {
      if (!(out instanceof PersistentStoreOutputStream) && !(out instanceof PagingByteBufferObjectOutputStream)) {
         return defaultCompressionTag;
      } else {
         return this.compressionFactory != null ? CompressionFactory.getCompressionFactory(this.compressionFactoryOverride != null ? this.compressionFactoryOverride : this.compressionFactory).getCompressionTag() : defaultCompressionTag;
      }
   }

   protected void cleanupCompressedMessageBody() {
      if (!this.clean) {
         this.originalLength = 0;
         this.payloadCompressed = null;
         this.compressed = false;
         this.clean = true;
      }
   }

   protected void readExternalCompressedMessageBody(ObjectInput in) throws IOException {
      this.compressionTag = in.readByte();
      this.compressed = true;
      this.originalLength = in.readInt();
      this.payloadCompressed = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
   }

   public void setStoreCompression(boolean value) {
      this.storeMessageCompression = value;
   }

   public static Properties convertCompressionOptionOverrideStringToProperty(String value) {
      Properties prop = new Properties();
      if (value == null) {
         return prop;
      } else {
         String[] elements = value.split(",");
         String[] var3 = elements;
         int var4 = elements.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String element = var3[var5];
            String[] nameValue = element.split("=");
            if (nameValue.length == 2) {
               prop.setProperty(nameValue[0].trim(), nameValue[1].trim());
            } else {
               if (nameValue.length != 1) {
                  throw new AssertionError("Lllegal format for compression option " + value);
               }

               prop.setProperty(nameValue[0].trim(), (String)null);
            }
         }

         return prop;
      }
   }

   public static Properties convertCompressionOptionStringToProperty(String value) {
      Properties prop = new Properties();
      if (value.equals("GZIP_DEFAULT_COMPRESSION")) {
         prop.setProperty("weblogic.jms.common.compressionfactory", GZIP_COMPRESSION_FACTORY);
         prop.setProperty("weblogic.jms.common.gzip.level", "DEFAULT_COMPRESSION");
      } else if (value.equals("GZIP_BEST_COMPRESSION")) {
         prop.setProperty("weblogic.jms.common.compressionfactory", GZIP_COMPRESSION_FACTORY);
         prop.setProperty("weblogic.jms.common.gzip.level", "BEST_COMPRESSION");
      } else if (value.equals("GZIP_BEST_SPEED")) {
         prop.setProperty("weblogic.jms.common.compressionfactory", GZIP_COMPRESSION_FACTORY);
         prop.setProperty("weblogic.jms.common.gzip.level", "BEST_SPEED");
      } else {
         if (!value.equals("LZF")) {
            throw new AssertionError("Lllegal format for compression option " + value);
         }

         prop.setProperty("weblogic.jms.common.compressionfactory", LZF_COMPRESSION_FACTORY);
      }

      return prop;
   }

   public void setCompressionOption(Properties option) {
      if (option == null) {
         this.compressionOption = defaultCompressionOption;
      } else {
         this.compressionOption = option;
      }

      this.compressionFactory = this.compressionOption.getProperty("weblogic.jms.common.compressionfactory");
   }

   public void setCompressionOptionOverride(Properties optionOverride) {
      this.compressionOptionOverride = optionOverride;
      if (this.compressionOptionOverride != null && this.compressionOptionOverride.size() > 0) {
         this.compressionFactoryOverride = this.compressionOptionOverride.getProperty("weblogic.jms.common.compressionfactory");
      }

   }

   public void setPagingCompression(boolean value) {
      this.pagingMessageCompression = value;
   }

   public int getCompressedMessageBodySize() {
      return this.payloadCompressed.getLength();
   }

   public int getOriginalMessageBodySize() {
      return this.originalLength;
   }

   protected void flushCompressedMessageBody(ObjectOutput out) throws IOException {
      if (this.compressionTag != -1) {
         out.writeByte(this.compressionTag);
      } else {
         out.writeByte(this.getCompressionTag(out));
      }

      out.writeInt(this.originalLength);
      this.payloadCompressed.writeLengthAndData(out);
   }

   public abstract void decompressMessageBody() throws javax.jms.JMSException;

   protected Payload decompress() throws IOException {
      this.hasBeenCompressed = true;
      CompressionFactory cf = this.getCompressionFactory(this.compressionTag);
      InputStream cpInput = cf.createDecompressionInputStream(this.payloadCompressed.getInputStream(), this.payloadCompressed.getLength(), (Properties)null);

      Payload var3;
      try {
         var3 = PayloadFactoryImpl.copyPayloadFromStream(cpInput, this.originalLength);
      } finally {
         cpInput.close();
      }

      return var3;
   }

   private CompressionFactory getCompressionFactory(byte id) {
      return CompressionFactory.getCompressionFactory(id);
   }

   private CompressionFactory getCompressionFactory(ObjectOutput out) {
      if (!(out instanceof PersistentStoreOutputStream) && !(out instanceof PagingByteBufferObjectOutputStream)) {
         return CompressionFactory.getCompressionFactory(defaultCompressionOption.getProperty("weblogic.jms.common.compressionfactory"));
      } else {
         return this.compressionFactory != null ? CompressionFactory.getCompressionFactory(this.compressionFactoryOverride != null ? this.compressionFactoryOverride : this.compressionFactory) : CompressionFactory.getCompressionFactory(defaultCompressionOption.getProperty("weblogic.jms.common.compressionfactory"));
      }
   }

   private Properties getCompressionOption(ObjectOutput out) {
      if (!(out instanceof PersistentStoreOutputStream) && !(out instanceof PagingByteBufferObjectOutputStream)) {
         return defaultCompressionOption;
      } else {
         return this.compressionOptionOverride != null && this.compressionOptionOverride.size() > 0 ? this.compressionOptionOverride : this.compressionOption;
      }
   }

   private void writeExternalCompressPayload(ObjectOutput out, Payload payload, OutputStream outputFromCP) throws IOException {
      CompressionFactory cf = this.getCompressionFactory(out);
      OutputStream writeIntoCPStream = cf.createCompressionOutputStream(outputFromCP, this.getCompressionOption(out));
      int startPos = 0;
      if (outputFromCP instanceof BufferOutputStream) {
         startPos = ((BufferOutputStream)outputFromCP).size();
      }

      long startTime = 0L;

      try {
         payload.writeTo(writeIntoCPStream);
      } finally {
         writeIntoCPStream.close();
      }

      boolean var9 = false;
      if (outputFromCP instanceof BufferOutputStream) {
         int endPos = ((BufferOutputStream)outputFromCP).size() - startPos;
      }

      out.writeByte(cf.getCompressionTag());
      out.writeInt(payload.getLength());
      if (outputFromCP instanceof BufferOutputStream) {
         ((BufferOutputStream)outputFromCP).writeLengthAndData(out);
      }

   }

   protected final void writeExternalCompressPayload(ObjectOutput out, Payload payload) throws IOException {
      this.writeExternalCompressPayload(out, payload, PayloadFactoryImpl.createOutputStream());
   }

   public static final JMSObjectOutputWrapper createJMSObjectOutputWrapper(ObjectOutput out, int compressionThreshold, boolean readStringAsObject) {
      assert out instanceof PeerInfoable;

      return new JMSObjectOutputWrapper(out, compressionThreshold, readStringAsObject);
   }

   public static int getPosition(ObjectOutput out) {
      if (!getLengthMethodForWriteExternalChecked) {
         Class var1 = MessageImpl.class;
         synchronized(MessageImpl.class) {
            Method m;
            try {
               Class clz = Class.forName("weblogic.protocol.AsyncOutgoingMessage");
               m = clz.getMethod("getLength");
            } catch (Exception var5) {
               JMSDebug.JMSDispatcher.debug(var5.toString());
               m = null;
            }

            getLengthMethodForWriteExternal = m;
            getLengthMethodForWriteExternalChecked = true;
         }
      }

      if (getLengthMethodForWriteExternal == null) {
         return -1;
      } else {
         try {
            return (Integer)getLengthMethodForWriteExternal.invoke(out);
         } catch (Exception var7) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               DebugLogger var10000 = JMSDebug.JMSDispatcher;
               DebugLogger.println(var7.toString());
               JMSDebug.JMSDispatcher.debug(var7.toString());
            }

            return -1;
         }
      }
   }

   public Object getBody(Class c) throws javax.jms.JMSException {
      return _getBody(this, c);
   }

   public static Object _getBody(Message message, Class c) throws javax.jms.JMSException {
      if (message instanceof TextMessage) {
         TextMessage textMessage = (TextMessage)message;
         String payload = textMessage.getText();
         if (payload == null) {
            return null;
         } else if (c.isAssignableFrom(String.class)) {
            return payload;
         } else {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logMessageBodyCannotBeAssignedToSpecifiedType(String.class, c));
         }
      } else {
         HashMap payload;
         if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage)message;
            payload = null;

            Serializable payload;
            try {
               payload = objectMessage.getObject();
            } catch (javax.jms.JMSException var6) {
               javax.jms.MessageFormatException mfe = new javax.jms.MessageFormatException(var6.getMessage());
               mfe.setLinkedException(var6);
               throw mfe;
            }

            if (payload == null) {
               return null;
            } else if (c.isAssignableFrom(payload.getClass())) {
               return payload;
            } else {
               throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logMessageBodyCannotBeAssignedToSpecifiedType(payload.getClass(), c));
            }
         } else if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage)message;
            bytesMessage.reset();
            long numBytes = bytesMessage.getBodyLength();
            if (numBytes == 0L) {
               return null;
            } else {
               byte[] payload = new byte[(int)numBytes];
               bytesMessage.readBytes(payload);
               bytesMessage.reset();
               if (c.isAssignableFrom(byte[].class)) {
                  return payload;
               } else {
                  throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logMessageBodyCannotBeAssignedToSpecifiedType(byte[].class, c));
               }
            }
         } else if (!(message instanceof MapMessage)) {
            if (message instanceof StreamMessage) {
               throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logGetBodyDoesNotSupportStreamMessage());
            } else {
               return null;
            }
         } else {
            MapMessage mapMessage = (MapMessage)message;
            if (!mapMessage.getMapNames().hasMoreElements()) {
               return null;
            } else {
               payload = new HashMap();
               Enumeration mapNamesEnum = mapMessage.getMapNames();

               while(mapNamesEnum.hasMoreElements()) {
                  String thisName = (String)mapNamesEnum.nextElement();
                  payload.put(thisName, mapMessage.getObject(thisName));
               }

               if (c.isAssignableFrom(Map.class)) {
                  return payload;
               } else {
                  throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logMessageBodyCannotBeAssignedToSpecifiedType(Map.class, c));
               }
            }
         }
      }
   }

   public boolean isBodyAssignableTo(Class c) throws javax.jms.JMSException {
      return _isBodyAssignableTo(this, c);
   }

   public static boolean _isBodyAssignableTo(Message message, Class c) throws javax.jms.JMSException {
      try {
         message.getBody(c);
         return true;
      } catch (javax.jms.MessageFormatException var3) {
         return false;
      }
   }

   public boolean isInAsyncSend() {
      synchronized(this.asyncSendLock) {
         return this.inAsyncSend;
      }
   }

   public void setInAsyncSend(boolean b) {
      synchronized(this.asyncSendLock) {
         if (this.inAsyncSend != b) {
            this.inAsyncSend = b;
         }

      }
   }

   static {
      defaultCompressionOption.setProperty("weblogic.jms.common.compressionfactory", GZIP_COMPRESSION_FACTORY);
      defaultCompressionOption.setProperty("weblogic.jms.common.gzip.level", "DEFAULT_COMPRESSION");
      defaultCompressionTag = CompressionFactory.getCompressionFactory(defaultCompressionOption.getProperty("weblogic.jms.common.compressionfactory")).getCompressionTag();
      debugWire = true;
   }

   public static final class JMSObjectOutputWrapper implements ObjectOutput, PeerInfoable {
      private final ObjectOutput out;
      private int compressionThreshold;
      private boolean readStringAsObject;

      private JMSObjectOutputWrapper(ObjectOutput out, int compressionThreshold, boolean readStringAsObject) {
         this.compressionThreshold = Integer.MAX_VALUE;
         this.readStringAsObject = false;
         this.out = out;
         this.compressionThreshold = compressionThreshold;
         this.readStringAsObject = readStringAsObject;
      }

      private JMSObjectOutputWrapper(ObjectOutput out, int compressionThreshold) {
         this.compressionThreshold = Integer.MAX_VALUE;
         this.readStringAsObject = false;
         this.out = out;
         this.compressionThreshold = compressionThreshold;
      }

      public PeerInfo getPeerInfo() {
         return ((PeerInfoable)this.out).getPeerInfo();
      }

      final ObjectOutput getInnerObjectOutput() {
         return this.out;
      }

      final boolean getReadStringAsObject() {
         return this.readStringAsObject;
      }

      final int getCompressionThreshold() {
         return this.compressionThreshold;
      }

      public void close() throws IOException {
      }

      public void flush() throws IOException {
      }

      public void writeObject(Object arg) throws IOException {
      }

      public void writeDouble(double arg) throws IOException {
      }

      public void writeFloat(float arg) throws IOException {
      }

      public void writeByte(int arg) throws IOException {
      }

      public void writeChar(int arg) throws IOException {
      }

      public void writeInt(int arg) throws IOException {
      }

      public void writeShort(int arg) throws IOException {
      }

      public void writeLong(long arg) throws IOException {
      }

      public void writeBoolean(boolean arg) throws IOException {
      }

      public void write(int arg) throws IOException {
      }

      public void write(byte[] arg) throws IOException {
      }

      public void write(byte[] arg1, int arg2, int arg3) throws IOException {
      }

      public void writeBytes(String arg) throws IOException {
      }

      public void writeChars(String arg) throws IOException {
      }

      public void writeUTF(String arg) throws IOException {
      }

      // $FF: synthetic method
      JMSObjectOutputWrapper(ObjectOutput x0, int x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
