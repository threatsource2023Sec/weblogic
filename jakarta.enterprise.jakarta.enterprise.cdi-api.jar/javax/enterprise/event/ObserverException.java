package javax.enterprise.event;

public class ObserverException extends RuntimeException {
   private static final long serialVersionUID = -801836224808304381L;

   public ObserverException() {
   }

   public ObserverException(String message) {
      super(message);
   }

   public ObserverException(Throwable cause) {
      super(cause);
   }

   public ObserverException(String message, Throwable cause) {
      super(message, cause);
   }
}
