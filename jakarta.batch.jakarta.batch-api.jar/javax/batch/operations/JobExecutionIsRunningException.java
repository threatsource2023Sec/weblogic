package javax.batch.operations;

public class JobExecutionIsRunningException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobExecutionIsRunningException() {
   }

   public JobExecutionIsRunningException(String message) {
      super(message);
   }

   public JobExecutionIsRunningException(Throwable cause) {
      super(cause);
   }

   public JobExecutionIsRunningException(String message, Throwable cause) {
      super(message, cause);
   }
}
