package weblogic.messaging.kernel;

public class KernelException extends Exception {
   public static final long serialVersionUID = 8059297125552649265L;

   public KernelException(String message) {
      super(message);
   }

   public KernelException(String message, Throwable cause) {
      super(message, cause);
   }
}
