package commonj.work;

public class WorkRejectedException extends WorkException {
   public WorkRejectedException() {
   }

   public WorkRejectedException(String message) {
      super(message);
   }

   public WorkRejectedException(String message, Throwable cause) {
      super(message, cause);
   }

   public WorkRejectedException(Throwable cause) {
      super(cause);
   }
}
