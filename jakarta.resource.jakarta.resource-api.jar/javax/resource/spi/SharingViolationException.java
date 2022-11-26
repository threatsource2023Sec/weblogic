package javax.resource.spi;

import javax.resource.ResourceException;

public class SharingViolationException extends ResourceException {
   public SharingViolationException() {
   }

   public SharingViolationException(String message) {
      super(message);
   }

   public SharingViolationException(Throwable cause) {
      super(cause);
   }

   public SharingViolationException(String message, Throwable cause) {
      super(message, cause);
   }

   public SharingViolationException(String message, String errorCode) {
      super(message, errorCode);
   }
}
