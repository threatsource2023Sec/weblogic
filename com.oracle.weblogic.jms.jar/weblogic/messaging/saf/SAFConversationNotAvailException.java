package weblogic.messaging.saf;

public final class SAFConversationNotAvailException extends SAFException {
   static final long serialVersionUID = 4038016285426737267L;

   public SAFConversationNotAvailException(String reason) {
      super(reason);
   }
}
