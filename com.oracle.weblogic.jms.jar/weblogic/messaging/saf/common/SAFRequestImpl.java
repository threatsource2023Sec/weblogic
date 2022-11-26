package weblogic.messaging.saf.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import java.util.Properties;
import weblogic.messaging.Message;
import weblogic.messaging.MessageID;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.utils.Util;

public final class SAFRequestImpl implements SAFRequest, Message, Cloneable {
   static final long serialVersionUID = -8254052743194579406L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int _HASPAYLOAD = 131072;
   private static final int _ISLASTMESSAGE = 262144;
   private static final int _HASPAYLOADCONTEXT = 524288;
   private boolean isLastMessage;
   private int deliveryMode = 2;
   private String conversationName;
   private String messageId;
   private long timestamp;
   private long timeToLive;
   private long sequenceNumber;
   private Externalizable payload;
   private Externalizable payloadContext;
   private Properties properties;
   private long userdataSize = 40L;
   private long payloadSize;
   private AgentDeliverRequest agentRequest;

   public final String getMessageId() {
      return this.messageId;
   }

   public final void setMessageId(String messageId) {
      this.messageId = messageId;
      this.userdataSize += (long)messageId.length();
   }

   public final String getConversationName() {
      return this.conversationName;
   }

   public final void setConversationName(String conversationName) {
      this.conversationName = conversationName;
      this.userdataSize += (long)conversationName.length();
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public final void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public final int getDeliveryMode() {
      return this.deliveryMode;
   }

   public final void setDeliveryMode(int deliveryMode) {
      this.deliveryMode = deliveryMode;
   }

   public final long getTimeToLive() {
      return this.timeToLive;
   }

   public final void setTimeToLive(long timeToLive) {
      this.timeToLive = timeToLive;
   }

   public final long getTimestamp() {
      return this.timestamp;
   }

   public final void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public final boolean isEndOfConversation() {
      return this.isLastMessage;
   }

   public final void setEndOfConversation(boolean endOfConversation) {
      this.isLastMessage = endOfConversation;
   }

   public final Externalizable getPayload() {
      return this.payload;
   }

   public final void setPayload(Externalizable payload) {
      this.payload = payload;
   }

   public void setPayloadContext(Externalizable payloadContext) {
      this.payloadContext = payloadContext;
   }

   public Externalizable getPayloadContext() {
      return this.payloadContext;
   }

   public long getPayloadSize() {
      return this.payloadSize;
   }

   public void setPayloadSize(long payloadSize) {
      this.payloadSize = payloadSize;
   }

   public MessageID getMessageID() {
      return new SAFMessageID(this.messageId);
   }

   public long size() {
      return this.userdataSize + this.payloadSize;
   }

   public Message duplicate() {
      try {
         return (Message)this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2);
      }
   }

   public Object getWorkContext() {
      return null;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = 1;
      int flags = version;
      if (this.payload != null) {
         flags = version | 131072;
         if (this.payloadContext != null) {
            flags |= 524288;
         }
      }

      if (this.isLastMessage) {
         flags |= 262144;
      }

      out.writeInt(flags);
      out.writeInt(this.deliveryMode);
      out.writeInt(0);
      out.writeUTF(this.messageId);
      out.writeUTF(this.conversationName);
      out.writeLong(this.sequenceNumber);
      out.writeLong(this.timeToLive);
      out.writeLong(this.timestamp);
      if (this.payload != null) {
         out.writeObject(this.payload);
         out.writeLong(this.payloadSize);
         if (this.payloadContext != null) {
            out.writeObject(this.payloadContext);
         }
      }

      if (this.properties != null && this.properties.size() > 0) {
         out.writeInt(this.properties.size());
         Enumeration enum_ = this.properties.keys();

         while(enum_.hasMoreElements()) {
            String key = (String)enum_.nextElement();
            out.writeObject(key);
            out.writeObject(this.properties.get(key));
         }
      } else {
         out.writeInt(0);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw Util.versionIOException(version, 1, 1);
      } else {
         this.deliveryMode = in.readInt();
         in.readInt();
         this.messageId = in.readUTF();
         this.conversationName = in.readUTF();
         this.sequenceNumber = in.readLong();
         this.timeToLive = in.readLong();
         this.timestamp = in.readLong();
         if ((flags & 262144) != 0) {
            this.isLastMessage = true;
         }

         if ((flags & 131072) != 0) {
            this.payload = (Externalizable)in.readObject();
            this.payloadSize = in.readLong();
            if ((flags & 524288) != 0) {
               this.payloadContext = (Externalizable)in.readObject();
            }
         }

         int size = in.readInt();
         if (size > 0) {
            this.properties = new Properties();

            for(int i = 0; i < size; ++i) {
               String key = (String)in.readObject();
               this.properties.put(key, in.readObject());
            }
         }

      }
   }

   public void setAgentRequest(AgentDeliverRequest agentRequest) {
      this.agentRequest = agentRequest;
   }

   public AgentDeliverRequest getAgentRequest() {
      return this.agentRequest;
   }

   public synchronized boolean isExpired() {
      if (this.timeToLive == 0L) {
         return false;
      } else {
         return System.currentTimeMillis() - this.timestamp > this.timeToLive;
      }
   }
}
