package javax.faces.el;

import javax.faces.FacesException;

/** @deprecated */
public class EvaluationException extends FacesException {
   private static final long serialVersionUID = 6143696299509378423L;

   public EvaluationException() {
   }

   public EvaluationException(String message) {
      super(message);
   }

   public EvaluationException(Throwable cause) {
      super(cause);
   }

   public EvaluationException(String message, Throwable cause) {
      super(message, cause);
   }
}
