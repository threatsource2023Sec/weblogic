package weblogic.messaging.saf;

import java.io.Externalizable;

public interface SAFTransport {
   Externalizable send(SAFConversationInfo var1, SAFRequest var2) throws SAFTransportException;

   void sendResult(SAFResult var1);

   int getType();

   boolean isGapsAllowed();

   SAFConversationHandle createConversation(SAFConversationInfo var1) throws SAFTransportException;

   void terminateConversation(SAFConversationInfo var1) throws SAFTransportException;

   Externalizable createSecurityToken(SAFConversationInfo var1) throws SAFTransportException;
}
