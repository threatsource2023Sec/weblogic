package weblogic.messaging.saf.common;

import java.io.Externalizable;
import weblogic.messaging.saf.SAFConversationHandle;
import weblogic.messaging.saf.SAFConversationInfo;

public final class SAFConversationHandleImpl implements SAFConversationHandle {
   private final String conversationName;
   private final String dynamicConversationName;
   private final long conversationTimeout;
   private final long conversationMaxIdleTime;
   private final SAFConversationInfo offer;
   private final String createConvMsgID;
   private final Externalizable conversationContext;

   public SAFConversationHandleImpl(String dynamicConversationName, long conversationTimeout, long conversationMaxIdleTime, SAFConversationInfo offer, String createConvMsgID, Externalizable context) {
      this((String)null, dynamicConversationName, conversationTimeout, conversationMaxIdleTime, offer, createConvMsgID, context);
   }

   public SAFConversationHandleImpl(String conversationName, String dynamicConversationName, long conversationTimeout, long conversationMaxIdleTime, SAFConversationInfo offer, String createConvMsgID, Externalizable context) {
      this.conversationName = conversationName;
      this.dynamicConversationName = dynamicConversationName;
      this.conversationTimeout = conversationTimeout;
      this.conversationMaxIdleTime = conversationMaxIdleTime;
      this.offer = offer;
      this.createConvMsgID = createConvMsgID;
      this.conversationContext = context;
   }

   public String getConversationName() {
      return this.conversationName;
   }

   public String getDynamicConversationName() {
      return this.dynamicConversationName;
   }

   public long getConversationTimeout() {
      return this.conversationTimeout;
   }

   public long getConversationMaxIdleTime() {
      return this.conversationMaxIdleTime;
   }

   public SAFConversationInfo getOffer() {
      return this.offer;
   }

   public String getCreateConversationMessageID() {
      return this.createConvMsgID;
   }

   public Externalizable getConversationContext() {
      return this.conversationContext;
   }
}
