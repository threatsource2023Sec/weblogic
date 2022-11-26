package javax.faces.el;

/** @deprecated */
public class PropertyNotFoundException extends EvaluationException {
   public PropertyNotFoundException() {
   }

   public PropertyNotFoundException(String message) {
      super(message);
   }

   public PropertyNotFoundException(Throwable cause) {
      super(cause);
   }

   public PropertyNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
