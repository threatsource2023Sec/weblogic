package weblogic.messaging.saf.internal;

import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.saf.SAFRequest;

public class SAFCursorKey {
   private static final int KEY_TYPE_SAF_CONVERSATIONNAME = 0;
   private static final int KEY_TYPE_SAF_SEQUENCENUMBER = 1;
   private static final int KEY_TYPE_SAF_TIMETOLIVE = 2;
   private static final int KEY_TYPE_SAF_MESSAGEID = 3;
   private static final String PROP_NAME_CONVERSATIONNAME = "ConversationName";
   private static final String PROP_NAME_SEQUENCENUMBER = "SequenceNumber";
   private static final String PROP_NAME_TIMETOLIVE = "TimeToLive";
   private static final String PROP_NAME_MESSAGEID = "MessageId";
   private final boolean asending;
   private int keyType;

   public SAFCursorKey(String property, boolean asending) {
      this.asending = asending;
      if (property.equalsIgnoreCase("ConversationName")) {
         this.keyType = 0;
      } else if (property.equalsIgnoreCase("SequenceNumber")) {
         this.keyType = 1;
      } else if (property.equalsIgnoreCase("TimeToLive")) {
         this.keyType = 2;
      } else if (property.equalsIgnoreCase("MessageId")) {
         this.keyType = 3;
      }

   }

   long compareKey(MessageElement cmref1, MessageElement cmref2) {
      long ret = 0L;
      SAFRequest r1 = (SAFRequest)cmref1.getMessage();
      SAFRequest r2 = (SAFRequest)cmref2.getMessage();
      switch (this.keyType) {
         case 0:
            if (r1.getConversationName() != null && r2.getConversationName() != null) {
               ret = (long)r1.getConversationName().compareTo(r2.getConversationName());
            } else if (r1.getConversationName() != null) {
               ret = 1L;
            } else if (r2.getConversationName() != null) {
               ret = -1L;
            } else {
               ret = 0L;
            }
            break;
         case 1:
            if (r1.getConversationName() != null && r2.getConversationName() != null) {
               ret = r1.getSequenceNumber() - r2.getSequenceNumber();
            }
            break;
         case 2:
            ret = r1.getSequenceNumber() - r2.getSequenceNumber();
            break;
         case 3:
            if (r1.getMessageId() != null && r2.getMessageId() != null) {
               ret = (long)r1.getMessageId().compareTo(r2.getMessageId());
            } else if (r1.getMessageId() != null) {
               ret = 1L;
            } else if (r2.getMessageId() != null) {
               ret = -1L;
            } else {
               ret = 0L;
            }
            break;
         default:
            ret = 0L;
      }

      return this.asending ? ret : -ret;
   }
}
