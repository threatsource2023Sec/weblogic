package javax.security.jacc;

public class PolicyContextException extends Exception {
   private static final long serialVersionUID = 3925692572777572935L;

   public PolicyContextException() {
   }

   public PolicyContextException(String message) {
      super(message);
   }

   public PolicyContextException(String message, Throwable cause) {
      super(message, cause);
   }

   public PolicyContextException(Throwable cause) {
      super(cause);
   }
}
