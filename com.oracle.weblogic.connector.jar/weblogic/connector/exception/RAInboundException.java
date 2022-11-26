package weblogic.connector.exception;

public class RAInboundException extends RAException {
   public RAInboundException() {
   }

   public RAInboundException(String message) {
      super(message);
   }

   public RAInboundException(String message, Throwable cause) {
      super(message, cause);
   }

   public RAInboundException(Throwable cause) {
      super(cause);
   }
}
