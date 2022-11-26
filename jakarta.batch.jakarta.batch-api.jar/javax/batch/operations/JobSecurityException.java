package javax.batch.operations;

public class JobSecurityException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public JobSecurityException() {
   }

   public JobSecurityException(String message) {
      super(message);
   }

   public JobSecurityException(Throwable cause) {
      super(cause);
   }

   public JobSecurityException(String message, Throwable cause) {
      super(message, cause);
   }
}
