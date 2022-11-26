package javax.batch.operations;

public class BatchRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public BatchRuntimeException() {
   }

   public BatchRuntimeException(String message) {
      super(message);
   }

   public BatchRuntimeException(Throwable cause) {
      super(cause);
   }

   public BatchRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }
}
