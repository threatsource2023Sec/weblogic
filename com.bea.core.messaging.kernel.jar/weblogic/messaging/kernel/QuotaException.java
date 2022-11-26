package weblogic.messaging.kernel;

public class QuotaException extends KernelException {
   public static final long serialVersionUID = 2567975474784777702L;

   public QuotaException(String message) {
      super(message);
   }

   public QuotaException(String message, Throwable cause) {
      super(message, cause);
   }
}
