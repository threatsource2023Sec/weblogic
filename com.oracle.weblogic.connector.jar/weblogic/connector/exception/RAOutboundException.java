package weblogic.connector.exception;

public class RAOutboundException extends RAException {
   public RAOutboundException() {
   }

   public RAOutboundException(String message) {
      super(message);
   }

   public RAOutboundException(String message, Throwable cause) {
      super(message, cause);
   }

   public RAOutboundException(Throwable cause) {
      super(cause);
   }
}
