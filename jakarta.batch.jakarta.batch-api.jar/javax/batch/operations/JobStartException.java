package javax.batch.operations;

public class JobStartException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobStartException() {
   }

   public JobStartException(String message) {
      super(message);
   }

   public JobStartException(Throwable cause) {
      super(cause);
   }

   public JobStartException(String message, Throwable cause) {
      super(message, cause);
   }
}
