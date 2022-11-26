package weblogic.messaging.saf;

import java.io.Externalizable;
import java.util.List;

public interface SAFManager {
   void send(SAFRequest var1) throws SAFException;

   void deliver(SAFConversationInfo var1, SAFRequest var2) throws SAFException;

   SAFResult deliverSync(SAFConversationInfo var1, SAFRequest var2) throws SAFException;

   void registerTransport(SAFTransport var1);

   SAFTransport getTransport(int var1);

   void registerEndpointManager(int var1, SAFEndpointManager var2);

   SAFEndpointManager getEndpointManager(int var1);

   String registerConversationOnSendingSide(SAFConversationInfo var1) throws SAFException;

   String registerConversationOnSendingSide(SAFConversationInfo var1, ConversationNameRefinementCallback var2) throws SAFException;

   SAFConversationInfo getCachedConversationInfoOnSendingSide(String var1) throws SAFException;

   SAFConversationInfo getConversationInfoOnSendingSide(String var1) throws SAFException;

   LocationInfo getLocationInfoForConversationOnSendingSide(String var1);

   void closeConversationOnSendingSide(String var1, boolean var2) throws SAFException;

   SAFConversationHandle registerConversationOnReceivingSide(SAFConversationInfo var1) throws SAFException;

   SAFConversationHandle registerConversationOnReceivingSide(SAFConversationInfo var1, ConversationNameRefinementCallback var2, String var3) throws SAFException;

   LocationInfo getLocationInfoForConversationOnReceivingSide(SAFConversationInfo var1);

   void closeConversationOnReceivingSide(SAFConversationInfo var1) throws SAFException;

   SAFConversationInfo getCachedConversationInfoOnReceivingSide(String var1) throws SAFException;

   SAFConversationInfo getConversationInfoOnReceivingSide(String var1) throws SAFException;

   long getLastAcknowledged(SAFConversationInfo var1) throws SAFException;

   void acknowledge(String var1, long var2, long var4) throws SAFException;

   void handleAsyncFault(String var1, String var2, Exception var3) throws SAFException;

   void createConversationSucceeded(SAFConversationHandle var1) throws SAFException;

   void storeConversationContextOnReceivingSide(String var1, Externalizable var2) throws SAFException;

   void storeConversationContextOnSendingSide(String var1, Externalizable var2) throws SAFException;

   List getAllSequenceNumberRangesOnReceivingSide(String var1) throws SAFException;

   long getLastAssignedSequenceValueOnSendingSide(String var1) throws SAFException;

   List getAllSequenceNumberRangesOnSendingSide(String var1) throws SAFException;

   boolean hasSentLastMessageOnSendingSide(String var1) throws SAFException;

   void setSentLastMessageOnSendingSide(String var1, long var2) throws SAFException;

   boolean hasReceivedLastMessageOnReceivingSide(String var1) throws SAFException;

   long getLastMessageSequenceNumberOnReceivingSide(String var1) throws SAFException;

   long getLastMessageSequenceNumberOnSendingSide(String var1) throws SAFException;

   boolean checkForConversationClosedOnReceivingSide(String var1) throws SAFException;

   boolean checkForConversationClosedOnSendingSide(String var1) throws SAFException;

   void addConversationLifecycleListener(ConversationLifecycleListener var1);

   void removeConversationLifecycleListener(ConversationLifecycleListener var1);

   public interface ConversationNameRefinementCallback {
      void conversationPreStore(SAFConversationInfo var1, LocationInfo var2);
   }

   public static class LocationInfo {
      private String _storeName;

      public LocationInfo(String storeName) {
         this._storeName = storeName;
      }

      public String getStoreName() {
         return this._storeName;
      }
   }

   public interface ConversationLifecycleListener {
      void ack(SAFConversationInfo var1, long var2, long var4);

      void addToCache(boolean var1, String var2, String var3, SAFConversationInfo var4, int var5);

      void removeFromCache(boolean var1, String var2, String var3, SAFConversationInfo var4, int var5);

      void preClose(boolean var1, boolean var2, SAFConversationInfo var3);
   }
}
