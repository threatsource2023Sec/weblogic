package javax.faces.el;

/** @deprecated */
public class ReferenceSyntaxException extends EvaluationException {
   private static final long serialVersionUID = -7230574031883380998L;

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
