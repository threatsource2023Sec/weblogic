package commonj.work;

public class WorkException extends Exception {
   public WorkException() {
   }

   public WorkException(String message) {
      super(message);
   }

   public WorkException(String message, Throwable cause) {
      super(message, cause);
   }

   public WorkException(Throwable cause) {
      super(cause);
   }
}
