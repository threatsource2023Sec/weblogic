package weblogic.messaging.interception.exceptions;

public class InterceptionException extends InterceptionProcessorException {
   public InterceptionException(String reason) {
      super(reason);
   }

   public InterceptionException(String reason, Throwable cause) {
      super(reason, cause);
   }
}
