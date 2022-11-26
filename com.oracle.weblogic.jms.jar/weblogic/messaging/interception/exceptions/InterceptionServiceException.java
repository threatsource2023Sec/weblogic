package weblogic.messaging.interception.exceptions;

public class InterceptionServiceException extends Exception {
   public InterceptionServiceException(String reason) {
      super(reason);
   }

   public InterceptionServiceException(String reason, Throwable cause) {
      super(reason, cause);
   }
}
