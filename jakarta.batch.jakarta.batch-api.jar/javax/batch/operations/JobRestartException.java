package javax.batch.operations;

public class JobRestartException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobRestartException() {
   }

   public JobRestartException(String message) {
      super(message);
   }

   public JobRestartException(Throwable cause) {
      super(cause);
   }

   public JobRestartException(String message, Throwable cause) {
      super(message, cause);
   }
}
