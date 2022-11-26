package javax.batch.operations;

public class JobExecutionNotRunningException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobExecutionNotRunningException() {
   }

   public JobExecutionNotRunningException(String message) {
      super(message);
   }

   public JobExecutionNotRunningException(Throwable cause) {
      super(cause);
   }

   public JobExecutionNotRunningException(String message, Throwable cause) {
      super(message, cause);
   }
}
