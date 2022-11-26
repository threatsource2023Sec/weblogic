package weblogic.messaging.interception.exceptions;

public class InterceptionProcessorException extends Exception {
   public InterceptionProcessorException(String reason) {
      super(reason);
   }

   public InterceptionProcessorException(String reason, Throwable cause) {
      super(reason, cause);
   }
}
