package javax.enterprise.context;

public class ContextNotActiveException extends ContextException {
   private static final long serialVersionUID = -3599813072560026919L;

   public ContextNotActiveException() {
   }

   public ContextNotActiveException(String message) {
      super(message);
   }

   public ContextNotActiveException(Throwable cause) {
      super(cause);
   }

   public ContextNotActiveException(String message, Throwable cause) {
      super(message, cause);
   }
}
