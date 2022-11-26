package javax.enterprise.context;

public class BusyConversationException extends ContextException {
   private static final long serialVersionUID = -3599813072560026919L;

   public BusyConversationException() {
   }

   public BusyConversationException(String message) {
      super(message);
   }

   public BusyConversationException(Throwable cause) {
      super(cause);
   }

   public BusyConversationException(String message, Throwable cause) {
      super(message, cause);
   }
}
