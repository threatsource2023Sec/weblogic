package weblogic.messaging.saf.common;

import java.util.List;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFResult.Result;

public class SAFResultImpl implements SAFResult {
   private SAFResult.Result resultCode;
   private List sequenceNumbers;
   private SAFConversationInfo info;
   private String description;
   private SAFException safException;

   public SAFResultImpl(SAFConversationInfo info, List sequenceNumbers, SAFResult.Result resultCode, String description) {
      this.info = info;
      this.sequenceNumbers = sequenceNumbers;
      this.resultCode = resultCode;
      this.description = description;
   }

   public boolean isDuplicate() {
      return this.resultCode == Result.DUPLICATE;
   }

   public boolean isSuccessful() {
      return this.resultCode == Result.SUCCESSFUL;
   }

   public SAFResult.Result getResultCode() {
      return this.resultCode;
   }

   public void setResultCode(SAFResult.Result code) {
      this.resultCode = code;
   }

   public void setSAFException(SAFException safe) {
      this.safException = safe;
   }

   public SAFException getSAFException() {
      return this.safException;
   }

   public String getDescription() {
      return this.description;
   }

   public List getSequenceNumbers() {
      return this.sequenceNumbers;
   }

   public void setSequenceNumbers(List numbers) {
      this.sequenceNumbers = numbers;
   }

   public SAFConversationInfo getConversationInfo() {
      return this.info;
   }

   public void setConversationInfo(SAFConversationInfo info) {
      this.info = info;
   }

   private boolean isValidResultCode(int resultCode) {
      return resultCode == Result.SUCCESSFUL.getErrorCode() || resultCode == Result.DUPLICATE.getErrorCode() || resultCode == Result.OUTOFORDER.getErrorCode() || resultCode == Result.ENDPOITNNOTAVAIL.getErrorCode() || resultCode == Result.NOTPERMITTED.getErrorCode() || resultCode == Result.CONVERSATIONTERMINATED.getErrorCode() || resultCode == Result.UNKNOWNCONVERSATION.getErrorCode() || resultCode == Result.CONVERSATIONREFUSED.getErrorCode() || resultCode == Result.CONVERSATIONTIMEOUT.getErrorCode() || resultCode == Result.ADMINPURGED.getErrorCode() || resultCode == Result.CONVERSATIONPOISENED.getErrorCode() || resultCode == Result.EXPIRED.getErrorCode() || resultCode == Result.SAFSEENLASTMESSAGE.getErrorCode() || resultCode == Result.SAFINTERNALERROR.getErrorCode();
   }

   public SAFResultImpl() {
   }
}
