package javax.resource.spi.work;

import javax.resource.spi.RetryableException;

public class RetryableWorkRejectedException extends WorkRejectedException implements RetryableException {
   private static final long serialVersionUID = 8198870267352154108L;

   public RetryableWorkRejectedException() {
   }

   public RetryableWorkRejectedException(String message) {
      super(message);
   }

   public RetryableWorkRejectedException(Throwable cause) {
      super(cause);
   }

   public RetryableWorkRejectedException(String message, Throwable cause) {
      super(message, cause);
   }

   public RetryableWorkRejectedException(String message, String errorCode) {
      super(message, errorCode);
   }
}
