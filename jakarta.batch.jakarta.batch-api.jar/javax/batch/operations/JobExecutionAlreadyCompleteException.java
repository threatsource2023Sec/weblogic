package javax.batch.operations;

public class JobExecutionAlreadyCompleteException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobExecutionAlreadyCompleteException() {
   }

   public JobExecutionAlreadyCompleteException(String message) {
      super(message);
   }

   public JobExecutionAlreadyCompleteException(Throwable cause) {
      super(cause);
   }

   public JobExecutionAlreadyCompleteException(String message, Throwable cause) {
      super(message, cause);
   }
}
