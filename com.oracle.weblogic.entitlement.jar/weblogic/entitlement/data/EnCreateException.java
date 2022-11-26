package weblogic.entitlement.data;

public class EnCreateException extends Exception {
   public EnCreateException() {
   }

   public EnCreateException(String message) {
      super(message);
   }

   public EnCreateException(String message, int targetIndex, Throwable targetException) {
      super(message);
   }
}
