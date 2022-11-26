package javax.enterprise.inject;

public class UnsatisfiedResolutionException extends ResolutionException {
   private static final long serialVersionUID = 5350603312442756709L;

   public UnsatisfiedResolutionException() {
   }

   public UnsatisfiedResolutionException(String message, Throwable throwable) {
      super(message, throwable);
   }

   public UnsatisfiedResolutionException(String message) {
      super(message);
   }

   public UnsatisfiedResolutionException(Throwable throwable) {
      super(throwable);
   }
}
