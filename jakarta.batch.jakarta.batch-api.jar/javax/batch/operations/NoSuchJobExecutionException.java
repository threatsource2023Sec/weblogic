package javax.batch.operations;

public class NoSuchJobExecutionException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public NoSuchJobExecutionException() {
   }

   public NoSuchJobExecutionException(String message) {
      super(message);
   }

   public NoSuchJobExecutionException(Throwable cause) {
      super(cause);
   }

   public NoSuchJobExecutionException(String message, Throwable cause) {
      super(message, cause);
   }
}
