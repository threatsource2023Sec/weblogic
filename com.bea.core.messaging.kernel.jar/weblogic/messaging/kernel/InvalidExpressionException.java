package weblogic.messaging.kernel;

public class InvalidExpressionException extends KernelException {
   public static final long serialVersionUID = 8944678923568013743L;

   public InvalidExpressionException(String message) {
      super("Expression : \"" + message + "\"");
   }

   public InvalidExpressionException(String message, Throwable cause) {
      super("Expression : \"" + message + "\"", cause);
   }
}
