package weblogic.messaging.saf.common;

import weblogic.messaging.MessageID;

public final class SAFMessageID implements MessageID {
   private final String messageId;

   public SAFMessageID(String messageId) {
      this.messageId = messageId;
   }

   public int compareTo(Object o) throws ClassCastException {
      if (this.messageId == null) {
         return ((SAFMessageID)o).messageId == null ? 1 : -1;
      } else {
         return ((SAFMessageID)o).messageId == null ? 1 : ((SAFMessageID)o).messageId.compareTo(this.messageId);
      }
   }

   public String toString() {
      return this.messageId;
   }
}
