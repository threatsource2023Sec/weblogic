package weblogic.messaging.interception.exceptions;

public class MessageContextException extends InterceptionProcessorException {
   public MessageContextException(String reason) {
      super(reason);
   }

   public MessageContextException(String reason, Throwable cause) {
      super(reason, cause);
   }
}
