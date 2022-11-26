package weblogic.security.service;

public class InvalidParameterException extends SecurityServiceRuntimeException {
   public InvalidParameterException() {
   }

   public InvalidParameterException(String msg) {
      super(msg);
   }

   public InvalidParameterException(Throwable nested) {
      super(nested);
   }

   public InvalidParameterException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
