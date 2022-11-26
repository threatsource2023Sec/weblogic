package javax.batch.operations;

public class JobExecutionNotMostRecentException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobExecutionNotMostRecentException() {
   }

   public JobExecutionNotMostRecentException(String message) {
      super(message);
   }

   public JobExecutionNotMostRecentException(Throwable cause) {
      super(cause);
   }

   public JobExecutionNotMostRecentException(String message, Throwable cause) {
      super(message, cause);
   }
}
