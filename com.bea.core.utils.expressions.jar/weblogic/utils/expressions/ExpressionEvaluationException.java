package weblogic.utils.expressions;

public class ExpressionEvaluationException extends Exception {
   private static final long serialVersionUID = -2984105378020916482L;

   public ExpressionEvaluationException(String message) {
      super(message);
   }

   public ExpressionEvaluationException(String message, Throwable nestedException) {
      super(message, nestedException);
   }
}
