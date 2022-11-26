package weblogic.management.runtime;

public class BatchJobRepositoryException extends RuntimeException {
   public BatchJobRepositoryException() {
   }

   public BatchJobRepositoryException(String msg) {
      super(msg);
   }

   public BatchJobRepositoryException(Throwable th) {
      super(th);
   }

   public BatchJobRepositoryException(String msg, Throwable th) {
      super(msg, th);
   }
}
