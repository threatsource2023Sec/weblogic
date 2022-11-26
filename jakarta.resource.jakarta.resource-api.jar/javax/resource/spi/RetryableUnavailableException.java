package javax.resource.spi;

public class RetryableUnavailableException extends UnavailableException implements RetryableException {
   private static final long serialVersionUID = 3730185319227786830L;

   public RetryableUnavailableException() {
   }

   public RetryableUnavailableException(String message) {
      super(message);
   }

   public RetryableUnavailableException(Throwable cause) {
      super(cause);
   }

   public RetryableUnavailableException(String message, Throwable cause) {
      super(message, cause);
   }

   public RetryableUnavailableException(String message, String errorCode) {
      super(message, errorCode);
   }
}
