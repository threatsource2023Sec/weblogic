package weblogic.messaging.saf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;
import weblogic.messaging.runtime.MessageInfo;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.utils.io.FilteringObjectInputStream;

public class SAFMessageInfo extends MessageInfo {
   private static final String OPEN_TYPE_NAME = "SAFMessageInfo";
   private static final String OPEN_DESCRIPTION = "This object represents information about a SAFRequest instance.";
   private static final String ITEM_VERSION_NUMBER = "VersionNumber";
   private static final String ITEM_CONVERSATION_NAME = "ConversationName";
   private static final String ITEM_DELIVERY_MODE = "DeliveryMode";
   private static final String ITEM_MESSAGE_ID = "MessageId";
   private static final String ITEM_PAYLOAD_SIZE = "PayloadSize";
   private static final String ITEM_SEQUENCE_NUMBER = "SequenceNumber";
   private static final String ITEM_TIMESTAMP = "Timestamp";
   private static final String ITEM_TIME_TO_LIVE = "TimeToLive";
   private static final String ITEM_END_OF_CONVERSATION = "EndOfConversation";
   private static final String ITEM_DESTINATION_URL = "DestinationURL";
   private static final String ITEM_SERIALIZED_REQUEST = "SerializedRequest";
   private static final int VERSION = 1;
   private String conversationName;
   private int deliveryMode;
   private String messageId;
   private long payloadSize;
   private long sequenceNumber;
   private long timestamp;
   private long timeToLive;
   private boolean endOfConversation;
   private String destinationURL;
   private SAFRequest safRequest;

   public SAFMessageInfo() {
   }

   public SAFMessageInfo(CompositeData cd) throws OpenDataException {
      super(cd);
   }

   public SAFMessageInfo(long handle, int state, String xidString, long sequenceNumber, String consumerID, SAFRequest safRequest, String destinationURL) {
      super(handle, state, xidString, sequenceNumber, consumerID);
      this.safRequest = safRequest;
      this.conversationName = safRequest.getConversationName();
      this.deliveryMode = safRequest.getDeliveryMode();
      this.messageId = safRequest.getMessageId();
      this.payloadSize = safRequest.getPayloadSize();
      this.sequenceNumber = safRequest.getSequenceNumber();
      this.timestamp = safRequest.getTimestamp();
      this.timeToLive = safRequest.getTimeToLive();
      this.endOfConversation = safRequest.isEndOfConversation();
      this.destinationURL = destinationURL;
   }

   protected void initOpenInfo() {
      super.initOpenInfo();
      this.openItemNames.add("VersionNumber");
      this.openItemNames.add("ConversationName");
      this.openItemNames.add("DeliveryMode");
      this.openItemNames.add("MessageId");
      this.openItemNames.add("PayloadSize");
      this.openItemNames.add("Timestamp");
      this.openItemNames.add("TimeToLive");
      this.openItemNames.add("EndOfConversation");
      this.openItemNames.add("DestinationURL");
      this.openItemNames.add("SerializedRequest");
      this.openItemDescriptions.add("The version number.");
      this.openItemDescriptions.add("The name that uniquely identifies the SAF conversation.");
      this.openItemDescriptions.add("Indicates whether the delivery mode is persistent or non-persistent.");
      this.openItemDescriptions.add("The message ID of the request.");
      this.openItemDescriptions.add("The size of the payload in bytes.");
      this.openItemDescriptions.add("The timestamp of the message in milliseconds.");
      this.openItemDescriptions.add("The time-to-live value in milliseconds.");
      this.openItemDescriptions.add("Indicates whether this is the last message in the conversation.");
      this.openItemDescriptions.add("The URL of the destination to which this message is to be forwarded to.");
      this.openItemDescriptions.add("The serialized SAFRequest.");
      this.openItemTypes.add(SimpleType.INTEGER);
      this.openItemTypes.add(SimpleType.STRING);
      this.openItemTypes.add(SimpleType.INTEGER);
      this.openItemTypes.add(SimpleType.STRING);
      this.openItemTypes.add(SimpleType.LONG);
      this.openItemTypes.add(SimpleType.LONG);
      this.openItemTypes.add(SimpleType.LONG);
      this.openItemTypes.add(SimpleType.BOOLEAN);
      this.openItemTypes.add(SimpleType.STRING);
      this.openItemTypes.add(SimpleType.STRING);
   }

   public String getConversationName() {
      return this.conversationName;
   }

   public void setConversationName(String conversationName) {
      this.conversationName = conversationName;
   }

   public int getDeliveryMode() {
      return this.deliveryMode;
   }

   public void setDeliveryMode(int deliveryMode) {
      this.deliveryMode = deliveryMode;
   }

   public boolean isEndOfConversation() {
      return this.endOfConversation;
   }

   public void setEndOfConversation(boolean endOfConversation) {
      this.endOfConversation = endOfConversation;
   }

   public String getMessageId() {
      return this.messageId;
   }

   public void setMessageId(String messageId) {
      this.messageId = messageId;
   }

   public long getPayloadSize() {
      return this.payloadSize;
   }

   public void setPayloadSize(long payloadSize) {
      this.payloadSize = payloadSize;
   }

   public SAFRequest getSAFRequest() {
      return this.safRequest;
   }

   public void setSAFRequest(SAFRequest safRequest) {
      this.safRequest = safRequest;
   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public long getTimeToLive() {
      return this.timeToLive;
   }

   public void setTimeToLive(long timeToLive) {
      this.timeToLive = timeToLive;
   }

   public String getDestinationURL() {
      return this.destinationURL;
   }

   public void setDestinationURL(String destinationURL) {
      this.destinationURL = destinationURL;
   }

   public Externalizable getMessage() {
      return this.safRequest != null ? this.safRequest.getPayload() : null;
   }

   public static Object from(CompositeData cd) throws OpenDataException {
      SAFMessageInfo info = new SAFMessageInfo();
      info.readCompositeData(cd);
      return info;
   }

   protected void readCompositeData(CompositeData cd) throws OpenDataException {
      super.readCompositeData(cd);
      String cdConversationName = (String)cd.get("ConversationName");
      if (cdConversationName != null) {
         this.setConversationName(cdConversationName);
      }

      Integer cdDeliveryMode = (Integer)cd.get("DeliveryMode");
      if (cdDeliveryMode != null) {
         this.setDeliveryMode(cdDeliveryMode);
      }

      Boolean cdEndOfConversation = (Boolean)cd.get("EndOfConversation");
      if (cdEndOfConversation != null) {
         this.setEndOfConversation(cdEndOfConversation);
      }

      String cdMessageId = (String)cd.get("MessageId");
      if (cdMessageId != null) {
         this.setMessageId(cdMessageId);
      }

      Long cdPayloadSize = (Long)cd.get("PayloadSize");
      if (cdPayloadSize != null) {
         this.setPayloadSize(cdPayloadSize);
      }

      Long cdSequenceNumber = (Long)cd.get("SequenceNumber");
      if (cdSequenceNumber != null) {
         this.setSequenceNumber(cdSequenceNumber);
      }

      Long cdTimestamp = (Long)cd.get("Timestamp");
      if (cdTimestamp != null) {
         this.setTimestamp(cdTimestamp);
      }

      Long cdTimeToLive = (Long)cd.get("TimeToLive");
      if (cdTimeToLive != null) {
         this.setTimeToLive(cdTimeToLive);
      }

      String cdDestinationURL = (String)cd.get("DestinationURL");
      if (cdDestinationURL != null) {
         this.setDestinationURL(cdDestinationURL);
      }

      String cdSAFRequest = (String)cd.get("SerializedRequest");
      if (cdSAFRequest != null) {
         try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(cdSAFRequest);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new FilteringObjectInputStream(bais);
            this.setSAFRequest((SAFRequest)ois.readObject());
         } catch (IOException var16) {
            throw new OpenDataException(var16.toString());
         } catch (ClassNotFoundException var17) {
            throw new OpenDataException(var17.toString());
         }
      }

   }

   protected Map getCompositeDataMap() throws OpenDataException {
      Map data = super.getCompositeDataMap();
      data.put("VersionNumber", new Integer(1));
      data.put("ConversationName", this.conversationName);
      data.put("DeliveryMode", new Integer(this.deliveryMode));
      data.put("EndOfConversation", new Boolean(this.endOfConversation));
      data.put("MessageId", this.messageId);
      data.put("PayloadSize", new Long(this.payloadSize));
      data.put("Timestamp", new Long(this.timestamp));
      data.put("TimeToLive", new Long(this.timeToLive));
      data.put("DestinationURL", this.destinationURL);

      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(this.safRequest);
         BASE64Encoder encoder = new BASE64Encoder();
         String value = encoder.encodeBuffer(baos.toByteArray());
         data.put("SerializedRequest", value);
         return data;
      } catch (IOException var9) {
         throw new OpenDataException(var9.toString());
      } finally {
         ;
      }
   }
}
