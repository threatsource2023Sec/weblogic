package javax.resource.spi;

import javax.resource.ResourceException;

public class EISSystemException extends ResourceException {
   public EISSystemException() {
   }

   public EISSystemException(String message) {
      super(message);
   }

   public EISSystemException(Throwable cause) {
      super(cause);
   }

   public EISSystemException(String message, Throwable cause) {
      super(message, cause);
   }

   public EISSystemException(String message, String errorCode) {
      super(message, errorCode);
   }
}
