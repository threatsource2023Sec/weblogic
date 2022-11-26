package weblogic.connector.external;

public class ElementNotFoundException extends Exception {
   public ElementNotFoundException() {
   }

   public ElementNotFoundException(String message) {
      super(message);
   }

   public ElementNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }

   public ElementNotFoundException(Throwable cause) {
      super(cause);
   }
}
