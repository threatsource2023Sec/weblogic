package javax.faces.el;

/** @deprecated */
public class ReferenceSyntaxException extends EvaluationException {
   public ReferenceSyntaxException() {
   }

   public ReferenceSyntaxException(String message) {
      super(message);
   }

   public ReferenceSyntaxException(Throwable cause) {
      super(cause);
   }

   public ReferenceSyntaxException(String message, Throwable cause) {
      super(message, cause);
   }
}
