package weblogic.application.custom;

public class CustomizationException extends Exception {
   public CustomizationException(String message) {
      super(message);
   }

   public CustomizationException(Throwable cause) {
      super(cause);
   }
}
