package weblogic.messaging.kernel;

public class IllegalStateException extends KernelException {
   public static final long serialVersionUID = -5427535158467333741L;

   public IllegalStateException(String message) {
      super(message);
   }

   public IllegalStateException(String message, Throwable cause) {
      super(message, cause);
   }
}
