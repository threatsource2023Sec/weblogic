package weblogic.application.descriptor;

public class MissingRootElementException extends RuntimeException {
   public MissingRootElementException() {
   }

   public MissingRootElementException(String s) {
      super(s);
   }

   public MissingRootElementException(String message, Throwable cause) {
      super(message, cause);
   }

   public MissingRootElementException(Throwable cause) {
      super(cause);
   }
}
