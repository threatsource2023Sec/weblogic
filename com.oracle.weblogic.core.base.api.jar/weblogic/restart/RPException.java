package weblogic.restart;

public class RPException extends RuntimeException {
   static final long serialVersionUID = -5904150673307531553L;
   private boolean fatal = false;

   public RPException() {
   }

   public RPException(String message) {
      super(message);
   }

   public RPException(String message, boolean f) {
      super(message);
      this.fatal = f;
   }

   public RPException(Exception cause) {
      super(cause);
   }

   public RPException(Exception cause, boolean f) {
      super(cause);
      this.fatal = f;
   }

   public RPException(String message, Exception cause) {
      super(message, cause);
   }

   public RPException(String message, Exception cause, boolean f) {
      super(message, cause);
      this.fatal = f;
   }

   public boolean isFatal() {
      return this.fatal;
   }
}
