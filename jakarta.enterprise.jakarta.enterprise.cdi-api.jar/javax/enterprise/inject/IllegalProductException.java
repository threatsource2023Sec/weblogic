package javax.enterprise.inject;

public class IllegalProductException extends InjectionException {
   private static final long serialVersionUID = -6280627846071966243L;

   public IllegalProductException() {
   }

   public IllegalProductException(String message, Throwable cause) {
      super(message, cause);
   }

   public IllegalProductException(String message) {
      super(message);
   }

   public IllegalProductException(Throwable cause) {
      super(cause);
   }
}
