package javax.resource.spi.work;

public class WorkRejectedException extends WorkException {
   public WorkRejectedException() {
   }

   public WorkRejectedException(String message) {
      super(message);
   }

   public WorkRejectedException(Throwable cause) {
      super(cause);
   }

   public WorkRejectedException(String message, Throwable cause) {
      super(message, cause);
   }

   public WorkRejectedException(String message, String errorCode) {
      super(message, errorCode);
   }
}
