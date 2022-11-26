package javax.enterprise.context;

public class NonexistentConversationException extends ContextException {
   private static final long serialVersionUID = -3599813072560026919L;

   public NonexistentConversationException() {
   }

   public NonexistentConversationException(String message) {
      super(message);
   }

   public NonexistentConversationException(Throwable cause) {
      super(cause);
   }

   public NonexistentConversationException(String message, Throwable cause) {
      super(message, cause);
   }
}
