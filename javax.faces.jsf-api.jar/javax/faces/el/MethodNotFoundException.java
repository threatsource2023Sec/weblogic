package javax.faces.el;

/** @deprecated */
public class MethodNotFoundException extends EvaluationException {
   public MethodNotFoundException() {
   }

   public MethodNotFoundException(String message) {
      super(message);
   }

   public MethodNotFoundException(Throwable cause) {
      super(cause);
   }

   public MethodNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
