package weblogic.messaging.saf;

import java.io.Externalizable;

public interface SAFRequest extends Externalizable {
   String getConversationName();

   void setConversationName(String var1);

   long getSequenceNumber();

   void setSequenceNumber(long var1);

   int getDeliveryMode();

   void setDeliveryMode(int var1);

   long getTimeToLive();

   void setTimeToLive(long var1);

   long getTimestamp();

   void setTimestamp(long var1);

   boolean isEndOfConversation();

   void setEndOfConversation(boolean var1);

   Externalizable getPayload();

   void setPayload(Externalizable var1);

   void setPayloadContext(Externalizable var1);

   Externalizable getPayloadContext();

   String getMessageId();

   void setMessageId(String var1);

   long getPayloadSize();

   void setPayloadSize(long var1);
}
