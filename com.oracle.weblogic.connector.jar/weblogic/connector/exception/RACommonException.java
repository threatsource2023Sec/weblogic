package weblogic.connector.exception;

public class RACommonException extends RAException {
   public RACommonException() {
   }

   public RACommonException(String message) {
      super(message);
   }

   public RACommonException(String message, Throwable cause) {
      super(message, cause);
   }

   public RACommonException(Throwable cause) {
      super(cause);
   }
}
