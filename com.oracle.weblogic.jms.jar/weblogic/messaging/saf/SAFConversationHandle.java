package weblogic.messaging.saf;

import java.io.Externalizable;

public interface SAFConversationHandle {
   String getConversationName();

   String getDynamicConversationName();

   long getConversationTimeout();

   long getConversationMaxIdleTime();

   SAFConversationInfo getOffer();

   String getCreateConversationMessageID();

   Externalizable getConversationContext();
}
