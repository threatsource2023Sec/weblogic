package weblogic.messaging.saf;

import java.util.List;

public interface SAFResult {
   boolean isDuplicate();

   boolean isSuccessful();

   SAFConversationInfo getConversationInfo();

   List getSequenceNumbers();

   Result getResultCode();

   void setSAFException(SAFException var1);

   SAFException getSAFException();

   String getDescription();

   public static enum Result {
      SUCCESSFUL(0, " Operation Successful."),
      DUPLICATE(1, " Duplicate Message in the Conversation."),
      OUTOFORDER(2, " Out Of Order Message in the Conversation."),
      ENDPOITNNOTAVAIL(3, " Endpoint is not available."),
      NOTPERMITTED(4, " Operation not permitted."),
      CONVERSATIONTERMINATED(5, " Conversation is terminated."),
      UNKNOWNCONVERSATION(6, " Unknown Conversation."),
      CONVERSATIONREFUSED(7, " Conversation refused."),
      CONVERSATIONTIMEOUT(8, " Conversation timed out."),
      ADMINPURGED(9, " Conversation is purged Adminstratively."),
      CONVERSATIONPOISENED(10, " Conversation is poisoned."),
      EXPIRED(11, " Conversation is expired."),
      SAFINTERNALERROR(12, " Internal Error."),
      SAFHRWRITEFAILURE(13, " Internal Error: History Record Write Failure."),
      SAFNOCURRENTTX(14, " Internal Error: No Current Transaction available."),
      SAFTXCOMMITFAILURE(15, " Internal Error: Transaction Commit Failure."),
      SAFTXROLLBACKFAILURE(16, " Internal Error: Transaction Rollback Failure."),
      SAFTXNOTSTARTED(17, " Internal Error: Transaction not started."),
      SAFNOTALLOWED(18, " Endpoint does not allow store-and-forward operation."),
      SAFSEENLASTMESSAGE(19, " Cannot send more messages after Last Message of a conversation/sequence."),
      PERMANENTTRANSPORTERROR(20, " Transport indicated a permanent error, no retry allowed");

      private final String description;
      private final int errorCode;

      private Result(int errorCode, String description) {
         this.description = description;
         this.errorCode = errorCode;
      }

      public String getDescription() {
         return this.description;
      }

      public int getErrorCode() {
         return this.errorCode;
      }

      public static Result getByErrorCode(int code) {
         Result[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Result r = var1[var3];
            if (code == r.errorCode) {
               return r;
            }
         }

         return null;
      }
   }
}
