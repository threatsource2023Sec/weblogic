package weblogic.connector.exception;

public class RAInternalException extends RAException {
   public RAInternalException() {
   }

   public RAInternalException(String message) {
      super(message);
   }

   public RAInternalException(String message, Throwable cause) {
      super(message, cause);
   }

   public RAInternalException(Throwable cause) {
      super(cause);
   }
}
