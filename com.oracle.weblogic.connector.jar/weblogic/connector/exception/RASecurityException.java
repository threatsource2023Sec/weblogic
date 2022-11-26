package weblogic.connector.exception;

public class RASecurityException extends RAException {
   public RASecurityException() {
   }

   public RASecurityException(String message) {
      super(message);
   }

   public RASecurityException(String message, Throwable cause) {
      super(message, cause);
   }

   public RASecurityException(Throwable cause) {
      super(cause);
   }
}
