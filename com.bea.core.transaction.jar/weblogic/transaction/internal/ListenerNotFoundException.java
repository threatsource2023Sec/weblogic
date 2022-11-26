package weblogic.transaction.internal;

public class ListenerNotFoundException extends Exception {
   public ListenerNotFoundException() {
   }

   public ListenerNotFoundException(String message) {
      super(message);
   }

   public ListenerNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }

   public ListenerNotFoundException(Throwable cause) {
      super(cause);
   }
}
