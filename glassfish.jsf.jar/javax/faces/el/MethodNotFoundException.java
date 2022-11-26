package javax.faces.el;

/** @deprecated */
public class MethodNotFoundException extends EvaluationException {
   private static final long serialVersionUID = 5958118161190341304L;

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
